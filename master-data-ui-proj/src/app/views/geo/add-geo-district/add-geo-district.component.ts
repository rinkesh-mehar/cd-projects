import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { GeoDistrictService } from '../services/geo-district.service';
import { GeoStateService } from '../services/geo-state.service';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';

@Component({
  selector: 'app-add-geo-district',
  templateUrl: './add-geo-district.component.html',
  styleUrls: ['./add-geo-district.component.scss']
})
export class AddGeoDistrictComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  districtForm: FormGroup;
  districtArr: any = [];
  stateList : any = [];
  StateList: {};

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  ngOnInit() {
    this.loadAllState();
    this.addDistrict()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public geoDistrictService: GeoDistrictService,
    public geoStateService : GeoStateService
  ){ }

  addDistrict() {
    this.districtForm = this.fb.group({
      stateCode: ['',Validators.required],
      districtCode: ['',Validators.required],
      name: ['',Validators.required],
      status: ['Inactive']
    })
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {


    for(let controller in this.districtForm.controls){
      this.districtForm.get(controller).markAsTouched();
    }
  
    if(this.districtForm.invalid){
      return;
    }

    if(this.districtForm.get('stateCode').value == 0){
      alert('Please Select State');
      return;
    }

   

    this.geoDistrictService.CreateDistrict(this.districtForm.value).subscribe(res => {
      this.isSubmitted = true;
     
      if(res){
        this.isSuccess = res.success;
        if(res.success){
          // this._statusMsg = res.message;
          // this.districtForm.reset();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

     //State list
     loadAllState(){
      return this.geoStateService.GetAllState().subscribe((data: {}) => {
        this.StateList = data;
      })
    }

  modalSuccess($event: any) {
    this.router.navigate(['/geo/district']);
  }
}

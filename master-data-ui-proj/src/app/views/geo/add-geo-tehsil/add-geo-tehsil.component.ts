import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { GeoTehsilService } from '../services/geo-tehsil.service';
import { GeoDistrictService } from '../services/geo-district.service';
import { GeoStateService } from '../services/geo-state.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-geo-tehsil',
  templateUrl: './add-geo-tehsil.component.html',
  styleUrls: ['./add-geo-tehsil.component.scss']
})
export class AddGeoTehsilComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  DistrictList: any = [];
  StateList: any = [];
  tehsilForm: FormGroup;
  tehsilArr: any = [];

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  ngOnInit() {
    this.addTehsil();
   // this.loadAllDistrict();
    this.loadAllState()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public geoTehsilService: GeoTehsilService,
    public geoDistrictService : GeoDistrictService,
    public geoStateService : GeoStateService
  ){ }

  addTehsil() {
    this.tehsilForm = this.fb.group({
      name: ['',Validators.required],
      stateCode: ['',Validators.required],
      districtCode: ['',Validators.required],
      tehsilCode: [''],
      status: ['Inactive']
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  submitForm() {


    for(let controller in this.tehsilForm.controls){
      this.tehsilForm.get(controller).markAsTouched();
    }
  
    if(this.tehsilForm.invalid){
      return;
    }

    if(this.tehsilForm.get('stateCode').value == 0){
      alert('Please Select State');
      return;
    }

    if(this.tehsilForm.get('districtCode').value == 0){
      alert('Please Select District');
      return;
    }


    this.geoTehsilService.CreateTehsil(this.tehsilForm.value).subscribe(res => {
      this.isSubmitted = true;
     
      if(res){
        this.isSuccess = res.success;
        if(res.success){
          // this._statusMsg = res.message;
          // this.tehsilForm.reset();

          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }
 
  //District list
 loadAllDistrict(){
  return this.geoDistrictService.GetAllDistrict().subscribe((data: {}) => {
    this.DistrictList = data;
  })
}

  //State list
  loadAllState(){
    return this.geoStateService.GetAllState().subscribe((data: {}) => {
      this.StateList = data;
    })
  }

  loadAllDistrictsByState(event:Event) : void {
    let index:number = event.target["selectedIndex"] - 1;
   if(index ==-1) {
       return;
    }
    let stateCode = this.StateList[index].stateCode;
    this.geoDistrictService.GetAllDistrictByStateCode(stateCode).subscribe(
      (data: {}) => {
        this.DistrictList = data;
        console.log(this.DistrictList);
      }
    );
  }

  modalSuccess($event: any) {
    this.router.navigate(['/geo/tehsil']);
  }
}

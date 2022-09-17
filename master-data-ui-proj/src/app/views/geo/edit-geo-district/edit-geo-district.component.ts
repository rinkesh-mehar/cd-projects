import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { GeoDistrictService } from '../services/geo-district.service';
import { GeoStateService } from '../services/geo-state.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-geo-district',
  templateUrl: './edit-geo-district.component.html',
  styleUrls: ['./edit-geo-district.component.scss']
})
export class EditGeoDistrictComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  DistrictList: any = [];
  StateList: any = [];
  updateDistrictForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.loadAllState();
    this.updateForm();
    
  }

  constructor(
    private actRoute: ActivatedRoute,    
    public geoDistrictService: GeoDistrictService,
    public geoStateService: GeoStateService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.geoDistrictService.GetDistrict(id).subscribe((data) => {
      this.updateDistrictForm = this.fb.group({
        stateCode: [data.stateCode,Validators.required],
        districtCode: [data.districtCode,Validators.required],
        name: [data.name,Validators.required],
        status: [data.status]
      })
    })
  }

  updateForm(){
    this.updateDistrictForm = this.fb.group({
      stateCode: ['',Validators.required],
      districtCode: ['',Validators.required],
      name: ['',Validators.required],
      status:['Inactive']
    })    
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {

    for (let controller in this.updateDistrictForm.controls) {
      this.updateDistrictForm.get(controller).markAsTouched();
    }

    if (this.updateDistrictForm.invalid) {
      return;
    }

    if (this.updateDistrictForm.get('stateCode').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select State', '');
      return;
    }

    for (let controller in this.updateDistrictForm.controls) {
      this.updateDistrictForm.get(controller).markAsTouched();
    }

    if (this.updateDistrictForm.invalid) {
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.geoDistrictService.UpdateDistrict(id, this.updateDistrictForm.value).subscribe(res => {
      this.isSubmitted = true;

      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

   //state list
   loadAllState(){
    return this.geoStateService.GetAllState().subscribe((data: {}) => {
      this.StateList = data;
    })
  }

  modalSuccess($event: any) {
    this.router.navigate(['/geo/district']);
  }
}

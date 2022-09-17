import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FarmerEnrollmentPlaceService } from '../services/farmer-enrollment-place.service';
import { ApiUtilService } from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-add-farmer-enrollment-place',
  templateUrl: './add-farmer-enrollment-place.component.html',
  styleUrls: ['./add-farmer-enrollment-place.component.scss']
})
export class AddFarmerEnrollmentPlaceComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  
  enrollmentPlaceForm: FormGroup;
  enrollmentPlaceArr: any = [];

  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.addEnrollmentPlace()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public farmerEnrollmentPlaceService: FarmerEnrollmentPlaceService,
    public apiUtilService: ApiUtilService
 
  ){ }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  addEnrollmentPlace() {
    this.enrollmentPlaceForm = this.fb.group({
      name: ['',Validators.required],
      status: ['Inactive']
      
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {

    for(let controller in this.enrollmentPlaceForm.controls){
      this.enrollmentPlaceForm.get(controller).markAsTouched();
    }

    if(this.enrollmentPlaceForm.invalid){
      return;
    }


    this.farmerEnrollmentPlaceService.CreateEnrollmentPlace(this.enrollmentPlaceForm.value).subscribe(res => {
      this.isSubmitted = true;
     
      if(res){
        this.isSuccess = res.success;
        if(res.success){
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

  downloadExcelFormat(){
    var tableName = "farmer_enrollment_place";
    this.apiUtilService.downloadExcelFormat(tableName);
  }//downloadExcelFormat


  uploadExcel(element) {
    var file: File = element.target.files[0];
    this.fileUpload = file;
  }

  submitExcelForm() {
    this.apiUtilService.uploadExcelFile(this.fileUpload).subscribe(res => {
      this.isSubmittedBulk = true;
     
      if(res){
        this.isSuccessBulk = res.success;
        if(res.success){
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
}

  modalSuccess($event: any) {
    this.router.navigate(['/farmer/enrollment-place']);
  }
}

import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FarmerInsuranceService } from '../services/farmer-insurance.service';
import { ApiUtilService } from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-add-farmer-insurance',
  templateUrl: './add-farmer-insurance.component.html',
  styleUrls: ['./add-farmer-insurance.component.scss']
})
export class AddFarmerInsuranceComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  
  insuranceForm: FormGroup;
  insuranceArr: any = [];

  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.addInsurance()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public farmerInsuranceService: FarmerInsuranceService,
    public apiUtilService: ApiUtilService

 
  ){ }

  addInsurance() {
    this.insuranceForm = this.fb.group({
      name: ['',Validators.required],
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

    for(let controller in this.insuranceForm.controls){
      this.insuranceForm.get(controller).markAsTouched();
    }

    if(this.insuranceForm.invalid){
      return;
    }


    this.farmerInsuranceService.CreateInsurance(this.insuranceForm.value).subscribe(res => {
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
    var tableName = "farmer_insurance_type";
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
    this.router.navigate(['/farmer/insurance-type']);
  }
}

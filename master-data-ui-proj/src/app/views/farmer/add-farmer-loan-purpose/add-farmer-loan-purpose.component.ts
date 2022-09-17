import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FarmerLoanPurposeService } from '../services/farmer-loan-purpose.service';
import { ApiUtilService } from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-farmer-loan-purpose',
  templateUrl: './add-farmer-loan-purpose.component.html',
  styleUrls: ['./add-farmer-loan-purpose.component.scss']
})
export class AddFarmerLoanPurposeComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  loanPurposeForm: FormGroup;
  loanPurposeArr: any = [];

  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  
  ngOnInit() {
    this.addLoanPurpose()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public farmerLoanPurposeService: FarmerLoanPurposeService,
    public apiUtilService: ApiUtilService

 
  ){ }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  addLoanPurpose() {
    this.loanPurposeForm = this.fb.group({
      name: ['',Validators.required],
      status: ['Inactive']
      
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {

    for(let controller in this.loanPurposeForm.controls){
      this.loanPurposeForm.get(controller).markAsTouched();
    }

    if(this.loanPurposeForm.invalid){
      return;
    }


    this.farmerLoanPurposeService.CreateLoanPurpose(this.loanPurposeForm.value).subscribe(res => {
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

  downloadExcelFormat(){
    var tableName = "farmer_loan_purpose";
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
        this.router.navigate(['/farmer/loan-purpose']);
    }
}

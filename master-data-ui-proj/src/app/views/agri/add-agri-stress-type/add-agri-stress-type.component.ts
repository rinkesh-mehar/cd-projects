import { ViewChild } from '@angular/core';
import { Component, OnInit, NgZone } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AgriStressTypeService } from '../services/agri-stress-type.service';
import { ApiUtilService } from '../../services/api-util.service';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-add-agri-stress-type',
  templateUrl: './add-agri-stress-type.component.html',
  styleUrls: ['./add-agri-stress-type.component.scss']
})
export class AddAgriStressTypeComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  stressTypeForm: FormGroup;
  stressTypeArr: any = [];
  uploadFile: File = null;
  imgPerview: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any; 
  
  ngOnInit() {
    this.addStressType()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public agriStressTypeService: AgriStressTypeService,
    public apiUtilService: ApiUtilService
 
  ){ }

  addStressType() {
    this.stressTypeForm = this.fb.group({
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

    for(let controller in this.stressTypeForm.controls){
      this.stressTypeForm.get(controller).markAsTouched();
    }

    if(this.stressTypeForm.invalid){
      return;
    }


    this.agriStressTypeService.CreateStressType(this.stressTypeForm.value).subscribe(res => {
      this.isSubmitted = true;
      if(res) {
      
        this.isSuccess = res.success;
        if (res.success) {
          this.stressTypeForm.reset();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
      // if(res){
      //   this.isSuccess = res.success;
      //   if(res.success){
      //     this._statusMsg = res.message;
      //     this.stressTypeForm.reset();

      //     this.delay(4000).then(any => {
      //         this.isSubmitted = false;
      //         this.isSuccess = false;
      //       });
      //   }else{
      //     this._statusMsg = res.error;
      //   }
      // }
    });
  }

  downloadExcelFormat(){
    var tableName = "agri_stress_type";
    this.apiUtilService.downloadExcelFormat(tableName);
  }//downloadExcelFormat


  uploadExcel(element) {
    var file: File = element.target.files[0];
    this.fileUpload = file;
  }

  submitExcelForm() {
    this.apiUtilService.uploadExcelFile(this.fileUpload).subscribe(res => {
      this.isSubmittedBulk = true;
      if(res) {
      
        this.isSuccess = res.success;
        if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
      // if(res){
      //   this.isSuccessBulk = res.success;
      //   if(res.success){
      //     this._statusMsg = res.message;
      //     this.stressTypeForm.reset();

      //     this.delay(4000).then(any => {
      //         this.isSubmittedBulk = false;
      //         this.isSuccessBulk = false;
      //       });
      //   }else{
      //     this._statusMsg = res.error;
      //   }
      // }
    });
  }

  modalSuccess($event: any) {
    this.router.navigate(['/stress/stress-type']);
    }

}

import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AgriStressSeverityService } from '../../services/agri-stress-severity.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-stress-severity',
  templateUrl: './add-stress-severity.component.html',
  styleUrls: ['./add-stress-severity.component.scss']
})
export class AddStressSeverityComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  stressSeverityForm: FormGroup;
  stressSeverityArr: any = [];
  uploadFile: File = null;
  imgPerview: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any; 

  ngOnInit() {
    this.addStressSeverity()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public agriStressSeverityService: AgriStressSeverityService,
    public apiUtilService: ApiUtilService
  ){ }
  numberPattern = '^[0-9][0-9]*$'

  addStressSeverity() {
    this.stressSeverityForm = this.fb.group({
      name: ['',Validators.required],
      value: ['',[Validators.required,Validators.pattern(this.numberPattern)]],
      status: ['Inactive']
      // end: ['',Validators.required],
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }
  //get stress() {return this.stressSeverityForm.controls}
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  submitForm() {

    
    for(let controller in this.stressSeverityForm.controls){
      this.stressSeverityForm.get(controller).markAsTouched();
    }

    if(this.stressSeverityForm.invalid){
      return;
    }

    this.agriStressSeverityService.CreateStressSeverity(this.stressSeverityForm.value).subscribe(res => {
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
    var tableName = "agri_stress_severity";
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
    this.router.navigate(['/stress/stress-severity']);
  }
}

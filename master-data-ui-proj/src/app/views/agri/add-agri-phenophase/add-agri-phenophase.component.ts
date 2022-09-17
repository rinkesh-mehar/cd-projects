import { ErrorModalComponent } from './../../global/error-modal/error-modal.component';
import { Component, OnInit, NgZone, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AgriPhenophaseService } from '../services/agri-phenophase.service';
import { ApiUtilService } from '../../services/api-util.service';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';

@Component({
  selector: 'app-add-agri-phenophase',
  templateUrl: './add-agri-phenophase.component.html',
  styleUrls: ['./add-agri-phenophase.component.scss']
})
export class AddAgriPhenophaseComponent implements OnInit {
  
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  phenophaseForm: FormGroup;
  phenophaseArr: any = [];
  uploadFile: File = null;
  imgPerview: any;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  ngOnInit() {
    this.addPhenophase()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public agriPhenophaseService: AgriPhenophaseService,
    public apiUtilService: ApiUtilService
  ){ }

  addPhenophase() {
    this.phenophaseForm = this.fb.group({
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

    
    for(let controller in this.phenophaseForm.controls){
      this.phenophaseForm.get(controller).markAsTouched();
    }

    if(this.phenophaseForm.invalid){
      return;
    }

    this.agriPhenophaseService.CreatePhenophase(this.phenophaseForm.value).subscribe(res => {
      this.isSubmitted = true;
      if(res) {
      
        this.isSuccess = res.success;
        if (res.success) {
          this.phenophaseForm.reset();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
      // if(res){
      //   this.isSuccess = res.success;
      //   if(res.success){
      //     this._statusMsg = res.message;
      //     this.phenophaseForm.reset();

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
    var tableName = "agri_phenophase";
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
          this.phenophaseForm.reset();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
      // if(res){
      //   this.isSuccessBulk = res.success;
      //   if(res.success){
      //     this._statusMsg = res.message;
      //     this.phenophaseForm.reset();

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
    this.router.navigate(['/phenophase/phenophase']);
    }

}

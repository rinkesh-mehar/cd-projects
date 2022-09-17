import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AgriGeneralCommodityService } from '../services/agri-general-commodity.service';
import { ApiUtilService } from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-agri-general-commodity',
  templateUrl: './add-agri-general-commodity.component.html',
  styleUrls: ['./add-agri-general-commodity.component.scss']
})
export class AddAgriGeneralCommodityComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  generalCommodityForm: FormGroup;
  generalCommodityArr: any = [];
  uploadFile: File = null;
  imgPerview: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  ngOnInit() {
    this.addGeneralCommodity()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public agriGeneralCommodityService: AgriGeneralCommodityService,
    public apiUtilService: ApiUtilService

  ) { }

  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  
  addGeneralCommodity() {
    this.generalCommodityForm = this.fb.group({
      name: ['', Validators.required],
      status: ['Inactive']
    })
  }


  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {

    for (let controller in this.generalCommodityForm.controls) {
      this.generalCommodityForm.get(controller).markAsTouched();
    }

    if (this.generalCommodityForm.invalid) {
      return;
    }


    this.agriGeneralCommodityService.CreateGeneralCommodity(this.generalCommodityForm.value).subscribe(res => {
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
    var tableName = "agri_general_commodity";
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
        this.router.navigate(['/commodity/general-commodity']);
    }
}

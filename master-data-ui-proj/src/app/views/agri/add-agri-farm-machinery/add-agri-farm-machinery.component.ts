import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AgriFarmMachineryService } from '../services/agri-farm-machinery.service';
import { ApiUtilService } from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-add-agri-farm-machinery',
  templateUrl: './add-agri-farm-machinery.component.html',
  styleUrls: ['./add-agri-farm-machinery.component.scss']
})
export class AddAgriFarmMachineryComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  
  farmMachineryForm: FormGroup;
  farmMachineryArr: any = [];

  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.addFarmMachinery()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public agriFarmMachineryService: AgriFarmMachineryService,
    public apiUtilService: ApiUtilService 
 
  ){ }

  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  addFarmMachinery() {
    this.farmMachineryForm = this.fb.group({
      name: ['',Validators.required],
      status: ['Inactive']
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {

    for(let controller in this.farmMachineryForm.controls){
      this.farmMachineryForm.get(controller).markAsTouched();
    }

    if(this.farmMachineryForm.invalid){
      return;
    }


    this.agriFarmMachineryService.CreateFarmMachinery(this.farmMachineryForm.value).subscribe(res => {
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
    var tableName = "agri_farm_machinery";
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
    this.router.navigate(['/agri/farm-machinery']);
  }
}

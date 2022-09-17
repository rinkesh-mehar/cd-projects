import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AgriAgrochemicalTypeService } from '../../services/agri-agrochemical-type.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';



@Component({
  selector: 'app-add-agrochemical-type',
  templateUrl: './add-agrochemical-type.component.html',
  styleUrls: ['./add-agrochemical-type.component.scss']
})
export class AddAgrochemicalTypeComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  agrochemicalTypeForm: FormGroup;
  agrochemicalTypeArr: any = [];
  uploadFile: File = null;
  imgPerview: any;
  
  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  ngOnInit() {
    this.addAgrochemicalType();
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public agriAgrochemicalTypeService: AgriAgrochemicalTypeService,
    public apiUtilService: ApiUtilService
  ){ }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  addAgrochemicalType() {
    this.agrochemicalTypeForm = this.fb.group({
      name: ['',Validators.required],
      status:['Inactive']
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {
    for(let controller in this.agrochemicalTypeForm.controls){
     this.agrochemicalTypeForm.get(controller).markAsTouched();
    }

    if(this.agrochemicalTypeForm.invalid){
      return;
    }


    this.agriAgrochemicalTypeService.CreateAgrochemicalType(this.agrochemicalTypeForm.value).subscribe(res => {
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
    var tableName = "agri_agrochemical_type";
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
        this.router.navigate(['/agrochemicals/agrochemical-type']);
    }
}

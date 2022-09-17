import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AgriAgroChemicalApplicationTypeService } from '../../services/agri-agro-chemical-application-type.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-agrochemical-application',
  templateUrl: './add-agrochemical-application.component.html',
  styleUrls: ['./add-agrochemical-application.component.scss']
})
export class AddAgrochemicalApplicationComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  agroChemicalApplictionForm: FormGroup;
  agroChemicalApplictionArr: any = [];
  imgPerview: any;
  uploadFile: File;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  ngOnInit() {
    this.addAgroChemicalAppliction()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public agriAgroChemicalApplictionService: AgriAgroChemicalApplicationTypeService,
    public apiUtilService: ApiUtilService
  ){ }

  addAgroChemicalAppliction() {
    this.agroChemicalApplictionForm = this.fb.group({
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

    
    for(let controller in this.agroChemicalApplictionForm.controls){
      this.agroChemicalApplictionForm.get(controller).markAsTouched();
    }

    if(this.agroChemicalApplictionForm.invalid){
      return;
    }

    this.agriAgroChemicalApplictionService.CreateAgroChemicalApplicationType(this.agroChemicalApplictionForm.value).subscribe(res => {
      this.isSubmitted = true;
      if (res) {
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
    var tableName = "agri_agrochemical_application_type";
    this.apiUtilService.downloadExcelFormat(tableName);
  }//downloadExcelFormat


  uploadExcel(element) {
    var file: File = element.target.files[0];
    this.fileUpload = file;
  }

  submitExcelForm() {
    this.apiUtilService.uploadExcelFile(this.fileUpload).subscribe(res => {
      this.isSubmittedBulk = true;
      if (res) {
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
        this.router.navigate(['/stress/agrochemical-application-type']);
    }
}



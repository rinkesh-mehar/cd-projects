import {Component, OnInit, ViewChild} from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { GeneralCompanyService } from '../../services/general-campany-service';
import {ActivatedRoute, Router} from '@angular/router';
import { ApiUtilService } from '../../../services/api-util.service';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';

@Component({
  selector: 'app-add-edit-general-company',
  templateUrl: './add-edit-general-company.component.html',
  styleUrls: ['./add-edit-general-company.component.scss']
})
export class AddEditGeneralCompanyComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  companyForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  editId: string;
  mode: string = "add";

  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  ngOnInit() {
    this.companyForm = this.fb.group({
      id: [],
      name: ['', Validators.required],
      description: ['', Validators.required],
      status: ['Inactive']

    })

    this.editId = this.actRoute.snapshot.paramMap.get('id');
    if (this.editId) {
      this.mode = "edit";
      this.generalCompanyService.GetCompany(this.editId).subscribe(data => {
        this.companyForm.patchValue(data);
      })
    }
  }

  constructor(
    public fb: FormBuilder,
    public generalCompanyService: GeneralCompanyService,
    private actRoute: ActivatedRoute,
    public apiUtilService: ApiUtilService,
    public router: Router

  ) { }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  submitForm() {

    for (let controller in this.companyForm.controls) {
      this.companyForm.get(controller).markAsTouched();
    }
    if (this.companyForm.invalid) {
      return;
    }
    if (this.mode == 'add') {
      this.add();
    } else {
      this.update();
    }
  }

  add() {
    this.generalCompanyService.CreateCompany(this.companyForm.value).subscribe(res => {
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

  update() {
    this.generalCompanyService.UpdateCompany(this.companyForm.value.id, this.companyForm.value).subscribe(res => {
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

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }


  downloadExcelFormat(){
    var tableName = "general_company";
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
        this.router.navigate(['/general/company']);
    }
}

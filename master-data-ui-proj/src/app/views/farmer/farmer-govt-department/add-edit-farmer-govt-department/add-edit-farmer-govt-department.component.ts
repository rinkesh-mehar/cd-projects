import {Component, OnInit, ViewChild} from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { FarmerGovtDepartmentService } from '../../services/farmer-govt-department.service';
import {ActivatedRoute, Router} from '@angular/router';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-edit-farmer-govt-department',
  templateUrl: './add-edit-farmer-govt-department.component.html',
  styleUrls: ['./add-edit-farmer-govt-department.component.scss']
})
export class AddEditFarmerGovtDepartmentComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  
  govtDepartmentForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  editId: string;
  mode: any="add";
  
  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  ngOnInit() {
    this.govtDepartmentForm = this.fb.group({
      id: [],
      departmentName: ['', Validators.required],
      status: ['Inactive']

    })

    this.editId = this.actRoute.snapshot.paramMap.get('id');
    if (this.editId) {
      this.mode = "edit";
      this.govtDepartmentService.GetGovtDepartment(this.editId).subscribe(data => {
        this.govtDepartmentForm.patchValue(data);
      })
    }
  }

  constructor(
    public fb: FormBuilder,
    public govtDepartmentService: FarmerGovtDepartmentService,
    private actRoute: ActivatedRoute,
    public apiUtilService: ApiUtilService,
    public router: Router

  ) { }

  submitForm() {

    for (let controller in this.govtDepartmentForm.controls) {
      this.govtDepartmentForm.get(controller).markAsTouched();
    }
    if (this.govtDepartmentForm.invalid) {
      return;
    }
    if (this.mode == 'add') {
      this.add();
    } else {
      this.update();
    }
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  add() {
    this.govtDepartmentService.CreateGovtDepartment(this.govtDepartmentForm.value).subscribe(res => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          // this.govtDepartmentForm.reset();
          this.mode="add";
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

  update() {
    this.govtDepartmentService.UpdateGovtDepartment(this.govtDepartmentForm.value.id, this.govtDepartmentForm.value).subscribe(res => {
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
    var tableName = "farmer_govt_department";
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
    this.router.navigate(['/farmer/govt-department']);
  }
}

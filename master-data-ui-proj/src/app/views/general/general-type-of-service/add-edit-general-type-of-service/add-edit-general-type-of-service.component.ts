import {Component, OnInit, ViewChild} from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { GeneralTypeOfServiceService } from '../../services/general-type-of-service.service';
import {ActivatedRoute, Router} from '@angular/router';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-edit-general-type-of-service',
  templateUrl: './add-edit-general-type-of-service.component.html',
  styleUrls: ['./add-edit-general-type-of-service.component.scss']
})
export class AddEditGeneralTypeOfServiceComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  typeOfServiceForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  editId: string;
  mode: string="add";

  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  ngOnInit() {
    this.typeOfServiceForm = this.fb.group({
      id: [],
      typeOfService: ['', Validators.required],
      status: ['Inactive']

    })

    this.editId = this.actRoute.snapshot.paramMap.get('id');
    if (this.editId) {
      this.mode = "edit";
      this.generalTypeOfServiceService.GetTypeOfService(this.editId).subscribe(data => {
        this.typeOfServiceForm.patchValue(data);
      })
    }
  }

  constructor(
    public fb: FormBuilder,
    public generalTypeOfServiceService: GeneralTypeOfServiceService,
    private actRoute: ActivatedRoute,
    public apiUtilService: ApiUtilService,
    public router: Router

  ) { }

  submitForm() {

    for (let controller in this.typeOfServiceForm.controls) {
      this.typeOfServiceForm.get(controller).markAsTouched();
    }
    if (this.typeOfServiceForm.invalid) {
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
    this.generalTypeOfServiceService.CreateTypeOfService(this.typeOfServiceForm.value).subscribe(res => {
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
    this.generalTypeOfServiceService.UpdateTypeOfService(this.typeOfServiceForm.value.id, this.typeOfServiceForm.value).subscribe(res => {
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
    var tableName = "general_type_of_service";
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
    this.router.navigate(['/general/type-of-service']);
  }
}

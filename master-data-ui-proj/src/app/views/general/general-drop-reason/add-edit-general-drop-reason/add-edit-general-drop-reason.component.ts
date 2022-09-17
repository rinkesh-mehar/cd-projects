import {Component, OnInit, ViewChild} from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { GeneralDropReasonService } from '../../services/general-drop-reason.service';
import {ActivatedRoute, Router} from '@angular/router';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-edit-general-drop-reason',
  templateUrl: './add-edit-general-drop-reason.component.html',
  styleUrls: ['./add-edit-general-drop-reason.component.scss']
})
export class AddEditGeneralDropReasonComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  dropReasonForm: FormGroup;

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
    this.dropReasonForm = this.fb.group({
      id: [],
      dropReason: ['', Validators.required],
      status: ['Inactive']

    })

    this.editId = this.actRoute.snapshot.paramMap.get('id');
    if (this.editId) {
      this.mode = "edit";
      this.generalDropReasonService.GetDropReason(this.editId).subscribe(data => {
        this.dropReasonForm.patchValue(data);
      })
    }
  }

  constructor(
    public fb: FormBuilder,
    public generalDropReasonService: GeneralDropReasonService,
    private actRoute: ActivatedRoute,
    public apiUtilService: ApiUtilService,
    public router: Router

  ) { }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  submitForm() {

    for (let controller in this.dropReasonForm.controls) {
      this.dropReasonForm.get(controller).markAsTouched();
    }
    if (this.dropReasonForm.invalid) {
      return;
    }
    if (this.mode == 'add') {
      this.add();
    } else {
      this.update();
    }
  }

  add() {
    this.generalDropReasonService.CreateDropReason(this.dropReasonForm.value).subscribe(res => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          // this.dropReasonForm.reset();
          this.mode="add";
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

  update() {
    this.generalDropReasonService.UpdateDropReason(this.dropReasonForm.value.id, this.dropReasonForm.value).subscribe(res => {
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
    var tableName = "general_drop_reason";
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
    this.router.navigate(['/general/drop-reason']);
  }
}

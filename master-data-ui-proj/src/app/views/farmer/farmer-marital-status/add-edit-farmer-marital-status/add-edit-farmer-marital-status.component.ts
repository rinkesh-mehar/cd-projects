import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import { FarmerMaritalStatusService } from '../../services/farmer-marital-status.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-edit-farmer-marital-status',
  templateUrl: './add-edit-farmer-marital-status.component.html',
  styleUrls: ['./add-edit-farmer-marital-status.component.scss']
})
export class AddEditFarmerMaritalStatusComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  MaritalStatusForm: FormGroup;
  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  editId: string;
  mode: string="add";

  ngOnInit() {
    this.MaritalStatusForm = this.fb.group({
      id: [],
      name: ['', Validators.required],
      status: ['Inactive']

    })

    this.editId = this.actRoute.snapshot.paramMap.get('id');
    if (this.editId) {
      this.mode = "edit";
      this.farmerMaritalStatusService.GetMaritalStatus(this.editId).subscribe(data => {
        this.MaritalStatusForm.patchValue(data);
      })
    }
  }

  constructor(
    public fb: FormBuilder,
    public farmerMaritalStatusService: FarmerMaritalStatusService,
    private actRoute: ActivatedRoute,
    public apiUtilService: ApiUtilService,
    public router: Router
  ) { }

  submitForm() {

    for (let controller in this.MaritalStatusForm.controls) {
      this.MaritalStatusForm.get(controller).markAsTouched();
    }
    if (this.MaritalStatusForm.invalid) {
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
    this.farmerMaritalStatusService.CreateMaritalStatus(this.MaritalStatusForm.value).subscribe(res => {
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
    this.farmerMaritalStatusService.UpdateMaritalStatus(this.MaritalStatusForm.value.id, this.MaritalStatusForm.value).subscribe(res => {
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

  downloadExcelFormat() {
    var tableName = "farmer_marital_status";
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
        if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

    modalSuccess($event: any) {
        this.router.navigate(['/farmer/marital-status']);
    }
}

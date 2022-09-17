import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import { FarmerMobileTypeService } from '../../services/farmer-mobile-type.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-add-edit-farmer-mobile-type',
  templateUrl: './add-edit-farmer-mobile-type.component.html',
  styleUrls: ['./add-edit-farmer-mobile-type.component.scss']
})
export class AddEditFarmerMobileTypeComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  MobileTypeForm: FormGroup;

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
    this.MobileTypeForm = this.fb.group({
      id: [],
      mobileType: ['', Validators.required],
      status: ['Inactive']

    })

    this.editId = this.actRoute.snapshot.paramMap.get('id');
    if (this.editId) {
      this.mode = "edit";
      this.farmerMobileTypeService.GetMobileType(this.editId).subscribe(data => {
        this.MobileTypeForm.patchValue(data);
      })
    }
  }

  constructor(
    public fb: FormBuilder,
    public farmerMobileTypeService: FarmerMobileTypeService,
    private actRoute: ActivatedRoute,
    public apiUtilService: ApiUtilService,
    public router: Router


  ) { }

  submitForm() {

    for (let controller in this.MobileTypeForm.controls) {
      this.MobileTypeForm.get(controller).markAsTouched();
    }
    if (this.MobileTypeForm.invalid) {
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
    this.farmerMobileTypeService.CreateMobileType(this.MobileTypeForm.value).subscribe(res => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          // this.MobileTypeForm.reset();
          this.mode="add";
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

  update() {
    this.farmerMobileTypeService.UpdateMobileType(this.MobileTypeForm.value.id, this.MobileTypeForm.value).subscribe(res => {
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
    var tableName = "farmer_mobile_type";
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
       this.router.navigate(['/farmer/mobile-type']);
    }
}

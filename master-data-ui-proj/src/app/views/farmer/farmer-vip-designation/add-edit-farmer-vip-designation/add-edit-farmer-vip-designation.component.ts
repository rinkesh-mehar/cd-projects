import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { FarmerVipDesignationService } from '../../services/farmer-vip-designation.service';
import {ActivatedRoute, Router} from '@angular/router';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-edit-farmer-vip-designation',
  templateUrl: './add-edit-farmer-vip-designation.component.html',
  styleUrls: ['./add-edit-farmer-vip-designation.component.scss']
})
export class AddEditFarmerVIPDesignationComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  
  vipDesignationForm: FormGroup;
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
    this.vipDesignationForm = this.fb.group({
      id: [],
      name: ['', Validators.required],
      status: ['Inactive']

    })

    this.editId = this.actRoute.snapshot.paramMap.get('id');
    if (this.editId) {
      this.mode = "edit";
      this.farmerVipDesignationService.GetVipDesignation(this.editId).subscribe(data => {
        this.vipDesignationForm.patchValue(data);
      })
    }
  }

  constructor(
    public fb: FormBuilder,
    public farmerVipDesignationService: FarmerVipDesignationService,
    private actRoute: ActivatedRoute,
    public apiUtilService: ApiUtilService,
    public router: Router
  ) { }

  submitForm() {

    for (let controller in this.vipDesignationForm.controls) {
      this.vipDesignationForm.get(controller).markAsTouched();
    }
    if (this.vipDesignationForm.invalid) {
      return;
    }
    if (this.mode == 'add') {
      this.add();
    } else {
      this.update();
    }
  }

  add() {
    this.farmerVipDesignationService.CreateVipDesignation(this.vipDesignationForm.value).subscribe(res => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          // this._statusMsg = res.message;
          // this.vipDesignationForm.reset();
          this.mode="add";
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  

  update() {
    this.farmerVipDesignationService.UpdateVipDesignation(this.vipDesignationForm.value.id, this.vipDesignationForm.value).subscribe(res => {
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
    var tableName = "farmer_vip_designation";
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
    this.router.navigate(['/farmer/vip-designation']);
  }
}

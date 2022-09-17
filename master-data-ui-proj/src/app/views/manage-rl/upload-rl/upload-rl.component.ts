import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { RlService } from '../service/rl.service';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { ApiUtilService } from '../../services/api-util.service';
import { ActivatedRoute, Router } from '@angular/router';
import { RlUser } from '../model/rlUser';


@Component({
  selector: 'app-upload-rl',
  templateUrl: './upload-rl.component.html',
  styleUrls: ['./upload-rl.component.css']
})
export class UploadRlComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  @ViewChild('profileclosebutton') profileclosebutton: any;
  @ViewChild('aadharclosebutton') closeaadharbutton: any;
  @ViewChild('panclosebutton') closepanbutton: any;
  @ViewChild('drivingclosebutton') closedrivingbutton: any;
  @ViewChild('accountclosebutton') accountclosebutton: any;
  constructor(private rlService: RlService, private fromBuilder: FormBuilder, public apiUtilService: ApiUtilService,
    private actRoute: ActivatedRoute, public router: Router) { }

  rlRoles: any = [];
  fileUpload: any;
  statuses: any = [];
  uploadFile: File = null;
  imgPerview: any;
  panImage: File = null;
  drivingImage: File = null;
  accountImage: File = null;
  editId: string;
  userImage: any;
  mode: string = 'add';
  adhaarImage: any;
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  isProfileImage: boolean;
  isAadharImage: boolean;
  isPanImage: boolean;
  isDrivingImage: boolean;
  isAccountImage: boolean;
  userProfileImage: any;
  userAadharImage: any;
  userPanImage: any;
  userDrivingImage: any;
  userAccountImage: any;
  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  rlFormGroup: FormGroup;
  rlGroup: FormGroup;
  regionList: any = [];

  ngOnInit() {
    this.statuses = ['Pending', 'Working', 'Expired', 'Completed'];
    this.rlRoles = ['FLS','MLS','MLT','PRS'];
    this.getAllGeoRegion();
    this.rlRoleForm();
    this.isProfileImage = true;
    this.isAadharImage = true;
    this.isDrivingImage = true;
    this.isPanImage = true;
    this.isAccountImage = true;

    this.editId = this.actRoute.snapshot.paramMap.get('id');
    if (this.editId) {
      this.mode = 'edit';
      this.rlService.getRlManageId(this.editId).subscribe(data => {
        this.userProfileImage = data.userImageUrl;
        this.userAadharImage = data.aadharImageUrl;
        this.userPanImage = data.panImageUrl;
        this.userDrivingImage = data.drivingLicenseImageUrl;
        this.userAccountImage = data.bankImageUrl;
        if (this.userProfileImage != undefined && this.userProfileImage != "") {
          this.isProfileImage = false;
          this.rlGroup.get('userImageUrl').disable();
        }
        if (this.userAadharImage != undefined && this.userAadharImage != "") {
          this.isAadharImage = false;
          this.rlGroup.get('aadharImageUrl').disable();
        }
        if (this.userDrivingImage != undefined && this.userDrivingImage != "") {
          this.isDrivingImage = false;
          this.rlGroup.get('drivingImage').disable();
        }
        if (this.userPanImage != undefined && this.userPanImage != "") {
          this.isPanImage = false;
          this.rlGroup.get('panImage').disable();
        }
        if (this.userAccountImage != undefined && this.userAccountImage != "") {
          this.isAccountImage = false;
          this.rlGroup.get('accountImage').disable();
        }

        this.rlGroup.patchValue({
          roleName: data.roleName, regionId: data.regionId, email: data.email, aggrementAccepted: data.aggrementAccepted,
          pan: data.panNo, aadharNo: data.aadharNo, name: data.name, mobileNumber: data.mobileNumber,
          bankName: data.bankName, accountNumber: data.accountNumber, ifscCode: data.ifsccode, status: data.status
        });
        this.rlGroup.patchValue(data);
      });
      console.log('id ' + this.editId);
      this.rlFormEdit();
    } else {
      this.rlFormAdd();
    }
  }

  getAllGeoRegion(){

    return this.rlService.getAllGeoRegion().subscribe((data: {}) => {
      this.regionList = data;
    })
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  rlRoleForm() {
    this.rlFormGroup = this.fromBuilder.group({
      fileInput: ['', Validators.required],
      roleName: ['', Validators.required]
    });
  }

  rlFormAdd() {
    this.rlGroup = this.fromBuilder.group({
      regionId: ['', Validators.required],
      roleName: ['', Validators.required],
      name: ['', Validators.required],
      email: ['', [Validators.required,Validators.email]],
      mobileNumber: [''],
      userImageUrl: [''],
      aadharNo: [''],
      aadharImageUrl: [''],
      status: [''],
      aggrementAccepted: [''],
      pan: [''],
      panImage: [''],
      drivingImage: [''],
      bankName: [''],
      ifscCode: [''],
      accountNumber: [''],
      accountImage: ['']
    });
  }

  rlFormEdit() {
    this.rlGroup = this.fromBuilder.group({
      regionId: ['', Validators.required],
      roleName: ['', Validators.required],
      name: ['', Validators.required],
      email: ['', [Validators.required,Validators.email]],
      mobileNumber: ['', Validators.required],
      userImageUrl: [''],
      aadharNo: ['',Validators.required],
      aadharImageUrl: ['',Validators.required],
      status: ['', Validators.required],
      aggrementAccepted: [''],
      pan: [''],
      panImage: [''],
      drivingImage: [''],
      bankName: ['', Validators.required],
      ifscCode: ['', Validators.required],
      accountNumber: ['', Validators.required],
      accountImage: ['']
    });
  }

  downloadExcelFormat() {
    var tableName = 'rl_users';
    this.apiUtilService.downloadExcelFormat(tableName);
  }

  uploadExcel(element) {
    this.rlFormGroup.patchValue({ fileInput: element.target.value });
    var file: File = element.target.files[0];
    this.fileUpload = file;
  }

  uploadUserImage(element) {
    var file: File = element.target.files[0];
    this.userImage = file;
  }

  uploadAdhaarImage(element) {
    var file: File = element.target.files[0];
    this.adhaarImage = file;
  }

  uploadPanImage(element) {
    var file: File = element.target.files[0];
    this.panImage = file;
  }

  uploadDrivingImage(element) {
    var file: File = element.target.files[0];
    this.drivingImage = file;
  }

  uploadAccountImage(element) {
    var file: File = element.target.files[0];
    this.accountImage = file;
  }

  submitExcelForm() {
    for (const controller in this.rlFormGroup.controls) {
      this.rlFormGroup.get(controller).markAllAsTouched();
    }
    if (this.rlFormGroup.invalid) {
      return;
    }
    console.log(this.rlFormGroup.value);
    this.rlService.uploadExcelFile(this.fileUpload, this.rlFormGroup.value.roleName).subscribe(res => {
      this.isSubmittedBulk = true;
      if (res) {
        this.isSuccessBulk = res.success;
        if (res.success) {
          console.log(res);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

  submitForm() {
    for (const controller in this.rlGroup.controls) {
      this.rlGroup.get(controller).markAllAsTouched();
    }
    if (this.rlGroup.invalid) {
      if (this.mode == 'edit') {
        this.errorModal.showModal('ERROR', 'Please update all mandatory details.', '');
      }
      return;
    }
    if (this.mode == 'add') {
      this.add();
    } else {
      this.update();
    }
  }

  add() {
    this.rlGroup.patchValue({ aggrementAccepted: 'No' });
    this.rlService.storeUser(this.rlGroup.value).subscribe(res => {
      this.isSubmittedBulk = true;
      if (res) {
        this.isSuccessBulk = res.success;
        if (res.success) {
          console.log(res);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

  update() {
    let data = { bankName: this.rlGroup.value.bankName, accountNumber: this.rlGroup.value.accountNumber, ifscCode: this.rlGroup.value.ifscCode };

    return this.rlService.updateRlUser(this.editId, this.rlGroup.value, data, this.userImage, this.adhaarImage,
      this.panImage, this.drivingImage, this.accountImage).subscribe(res => {
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

  hide() {
    this.rlGroup.get('userImageUrl').enable();
    this.profileclosebutton.nativeElement.click();
    this.isProfileImage = true;
  }

  aadharHide() {
    this.rlGroup.get('aadharImageUrl').enable();
    this.closeaadharbutton.nativeElement.click();
    this.isAadharImage = true;
  }

  panHide() {
    this.rlGroup.get('panImage').enable();
    this.closepanbutton.nativeElement.click();
    this.isPanImage = true;
  }

  drivingHide() {
    this.rlGroup.get('drivingImage').enable();
    this.closedrivingbutton.nativeElement.click();
    this.isDrivingImage = true;
  }

  accountHide() {
    this.rlGroup.get('accountImage').enable();
    this.accountclosebutton.nativeElement.click();
    this.isAccountImage = true;
  }

  handleEvent($event: any) {
    this.router.navigate(['rl/rl-users']);
  }

}

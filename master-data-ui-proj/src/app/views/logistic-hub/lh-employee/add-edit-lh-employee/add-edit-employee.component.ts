import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {EmployeeService} from '../../service/employee.service';
import {ApiUtilService} from '../../../services/api-util.service';
import {ActivatedRoute, Router} from '@angular/router';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
    selector: 'app-logistic-employee',
    templateUrl: './add-edit-employee.component.html',
    styleUrls: ['./add-edit-employee.component.css']
})
export class AddEditEmployeeComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;
    @ViewChild('profileclosebutton') profileclosebutton: any;
    @ViewChild('aadharclosebutton') closeaadharbutton: any;
    @ViewChild('panclosebutton') closepanbutton: any;
    @ViewChild('drivingclosebutton') closedrivingbutton: any;
    @ViewChild('accountclosebutton') accountclosebutton: any;

    constructor(private employeeService: EmployeeService, private fromBuilder: FormBuilder, public apiUtilService: ApiUtilService,
                private actRoute: ActivatedRoute, public router: Router) {
    }

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
    employeeGroup: FormGroup;
    geoRegionList: any;
    activeLhList: any;
    empName: any;
    drkUserList: any;
    lhRole: any;

    ngOnInit(): void {
        this.employeeForm();
        this.getGeoRegion();
        this.getLhRole();
        this.isProfileImage = true;
        this.isAadharImage = true;
        this.isDrivingImage = true;
        this.isPanImage = true;
        this.isAccountImage = true;
    }

    employeeForm() {
        this.employeeGroup = this.fromBuilder.group({
            regionId: ['', Validators.required],
            lhWarehouseId: ['', Validators.required],
            empType: ['', Validators.required],
            userRole: ['', Validators.required],
            name: [''],
            empId: ['',Validators.email],
            email: [''],
            contactNumber: [''],
            userImage: [''],
            aadharNo: [''],
            aadharImage: [''],
            // status: ['', Validators.required],
            pan: [''],
            panImage: [''],
            drivingImageUrl: [''],
            bankName: [''],
            ifscCode: [''],
            accountNumber: [''],
            accountImage: ['']
        });
        this.editId = this.actRoute.snapshot.paramMap.get('id');
        if (this.editId) {
            this.mode = 'edit';
            this.employeeService.getLhEmployeeById(this.editId).subscribe(data =>{
                this.employeeGroup.patchValue(data);

                this.getActiveLhAndDrkUserByRegionId();
                if (data.empType == 'EXT'){
                    this.userProfileImage = data.userImageUrl;
                    this.userAadharImage = data.aadharImageUrl;
                    this.userPanImage = data.panImageUrl;
                    this.userDrivingImage = data.drivingLicenseImageUrl;
                    this.userAccountImage = data.bankImageUrl;
                    if (this.userProfileImage != undefined && this.userProfileImage != "") {
                        this.isProfileImage = false;
                        this.employeeGroup.get('userImage').disable();
                    }
                    if (this.userAadharImage != undefined && this.userAadharImage != "") {
                        this.isAadharImage = false;
                        this.employeeGroup.get('aadharImage').disable();
                    }
                    if (this.userPanImage != undefined && this.userPanImage != "") {
                        this.isPanImage = false;
                        this.employeeGroup.get('panImage').disable();
                    }
                    if (this.userDrivingImage != undefined && this.userDrivingImage != "") {
                        this.isDrivingImage = false;
                        this.employeeGroup.get('drivingImageUrl').disable();
                    }
                    if (this.userAccountImage != undefined && this.userAccountImage != "") {
                        this.isAccountImage = false;
                        this.employeeGroup.get('accountImage').disable();
                    }
                }
            });
        }
    }

    trimValue(formControl) { 
        formControl.setValue(formControl.value.trim()); 
      }
    employeeType(type) {
        if (type == 'DRK') {
            this.employeeGroup.get('name').clearValidators();
            this.employeeGroup.get('empId').setValidators([Validators.required]);
            this.employeeGroup.get('bankName').clearValidators();
            this.employeeGroup.get('ifscCode').clearValidators();
            this.employeeGroup.get('accountNumber').clearValidators();
            this.employeeGroup.get('aadharNo').clearValidators();
            this.employeeGroup.get('contactNumber').clearValidators();
            this.employeeGroup.get('email').clearValidators();
            this.employeeGroup.get('name').updateValueAndValidity();
        } else {
            this.employeeGroup.get('empId').clearValidators();
            this.employeeGroup.get('name').setValidators([Validators.required]);
            this.employeeGroup.get('bankName').setValidators([Validators.required]);
            this.employeeGroup.get('ifscCode').setValidators([Validators.required]);
            this.employeeGroup.get('accountNumber').setValidators([Validators.required]);
            this.employeeGroup.get('aadharNo').setValidators([Validators.required]);
            this.employeeGroup.get('contactNumber').setValidators([Validators.required]);
            this.employeeGroup.get('email').setValidators([Validators.required]);
        }
        this.employeeGroup.get('empId').updateValueAndValidity();
        this.employeeGroup.get('name').updateValueAndValidity();
        this.employeeGroup.get('bankName').updateValueAndValidity();
        this.employeeGroup.get('ifscCode').updateValueAndValidity();
        this.employeeGroup.get('accountNumber').updateValueAndValidity();
        this.employeeGroup.get('aadharNo').updateValueAndValidity();
        this.employeeGroup.get('contactNumber').updateValueAndValidity();
        this.employeeGroup.get('email').updateValueAndValidity();
        this.employeeGroup.updateValueAndValidity();
    }

    getGeoRegion() {
        this.employeeService.getRegion().subscribe(data => {
            if (data) {
                this.geoRegionList = data;
            }
        });
    }

    getActiveLhAndDrkUserByRegionId() {
        this.employeeService.getActiveLhDrkUser(this.employeeGroup.value.regionId).subscribe(data => {
            if (data) {
                this.activeLhList = data['ActiveLh'];
                this.drkUserList = data['drkUser'];
            }
        });
    }

    /*getDrkUserList(){
        return this.employeeService.getDrkUser(this.employeeGroup.value.regionId).subscribe(data =>{
            if (data){
                this.drkUserList = data;
            }
        });
    }*/

    getLhRole() {
        this.employeeService.getLhRole().subscribe(data => {
            if (data) {
                this.lhRole = data;
            }
        });
    }

    uploadUserImage(element) {
        const file: File = element.target.files[0];
        this.userImage = file;
    }

    uploadAdhaarImage(element) {
        const file: File = element.target.files[0];
        this.adhaarImage = file;
    }

    uploadPanImage(element) {
        const file: File = element.target.files[0];
        this.panImage = file;
    }

    uploadDrivingImage(element) {
        const file: File = element.target.files[0];
        this.drivingImage = file;
    }

    uploadAccountImage(element) {
        const file: File = element.target.files[0];
        this.accountImage = file;
    }

    submitForm() {
        for (const controller in this.employeeGroup.controls) {
            this.employeeGroup.get(controller).markAsTouched();
        }
        if (this.employeeGroup.invalid) {
            return;
        }
        if (this.mode == 'add') {
            this.add();
        } else {
            this.update();
        }
    }

    add() {
        if (this.employeeGroup.value.empType == 'DRK') {
            this.employeeService.storeDrkEmployee(this.employeeGroup.value)
                .subscribe(res => {
                    if (res.success) {
                        this.successModal.showModal('SUCCESS', res.message, '');
                    } else {
                        this.errorModal.showModal('ERROR', res.error, '');
                    }
                });
        } else {
            let data = { bankName: this.employeeGroup.value.bankName, accountNumber: this.employeeGroup.value.accountNumber,
                ifscCode: this.employeeGroup.value.ifscCode };
            this.employeeService.storeExtEmployeeDetails(this.employeeGroup.value, data, this.userImage, this.adhaarImage,
                this.panImage, this.drivingImage, this.accountImage).subscribe(res =>{
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
    }

    update() {
        if (this.employeeGroup.value.empType == 'DRK') {
            this.employeeService.updateDrkEmployee(this.editId, this.employeeGroup.value)
                .subscribe(res => {
                    if (res.success) {
                        this.successModal.showModal('SUCCESS', res.message, '');
                    } else {
                        this.errorModal.showModal('ERROR', res.error, '');
                    }
                });
        } else {
            let data = { bankName: this.employeeGroup.value.bankName, accountNumber: this.employeeGroup.value.accountNumber,
                ifscCode: this.employeeGroup.value.ifscCode };
            this.employeeService.updateExtEmployeeDetails(this.editId, this.employeeGroup.value, data, this.userImage, this.adhaarImage,
                this.panImage, this.drivingImage, this.accountImage)
                .subscribe(res =>{
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
    }

    hide() {
        this.employeeGroup.get('userImage').enable();
        this.profileclosebutton.nativeElement.click();
        this.isProfileImage = true;
    }
    aadharHide() {
        this.employeeGroup.get('aadharImage').enable();
        this.closeaadharbutton.nativeElement.click();
        this.isAadharImage = true;
    }

    panHide() {
        this.employeeGroup.get('panImage').enable();
        this.closepanbutton.nativeElement.click();
        this.isPanImage = true;
    }

    drivingHide() {
        this.employeeGroup.get('drivingImageUrl').enable();
        this.closedrivingbutton.nativeElement.click();
        this.isDrivingImage = true;
    }

    accountHide() {
        this.employeeGroup.get('accountImage').enable();
        this.accountclosebutton.nativeElement.click();
        this.isAccountImage = true;
    }

    handleEvent($event: any) {
        this.router.navigate(['logistic-hub/lh-employee-list']);
    }
}

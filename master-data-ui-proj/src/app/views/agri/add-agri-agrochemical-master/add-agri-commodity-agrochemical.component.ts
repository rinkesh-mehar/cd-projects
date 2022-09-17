import { AgriAgrochemicalMasterService } from './../services/agri-agrochemical-master.service';
import {Component, OnInit, NgZone, ViewChild} from '@angular/core';

import {FormBuilder, FormGroup, Validators, FormArray, FormControl} from '@angular/forms';
import {Router} from '@angular/router';
import {AgriCommodityAgrochemicalService} from '../services/agri-commodity-agrochemical.service';
import {AgriAgrochemicalTypeService} from '../services/agri-agrochemical-type.service';
import {ZonalStressDurationService} from '../services/zonal-stress-duration.service';
import {AgriStressTypeService} from '../services/agri-stress-type.service';
import {AgriCommodityService} from '../services/agri-commodity.service';
import {ApiUtilService} from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
    selector: 'app-add-agri-commodity-agrochemical-master',
    templateUrl: './add-agri-commodity-agrochemical.component.html',
    styleUrls: ['./add-agri-commodity-agrochemical.component.scss']
})
export class AddAgriCommodityAgrochemicalComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    agrochemicalMasterForm: FormGroup;
    agrochemicalMasterArr: any = [];
    AgrochemicalTypeList: any = [];
    StressTypeList: {};
    StressList: any = [];
    uploadFile: File = null;
    imgPerview: any;

    AgroChemicalNameList: any = [];

    isSubmittedBulk: boolean = false;
    isSuccessBulk: boolean = false;
    fileUpload: any;

    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    _statusMsg: string;
    CommodityList: {};


    ngOnInit() {
        this.addAgrochemicalMaster();
        this.loadAllAgrochemicalType();
        this.loadAllStressType();
        this.loadAllCommodities();
        this.loadAllAgroChemicalNames();
    }

    constructor(
        public fb: FormBuilder,
        private ngZone: NgZone,
        private router: Router,
        public agriAgrochemicalMasterService: AgriCommodityAgrochemicalService,
        public agriAgrochemicalTypeService: AgriAgrochemicalTypeService,
        public zonalStressService:ZonalStressDurationService,
        public agriStressTypeService: AgriStressTypeService,
        public commodityService: AgriCommodityService,
        public apiUtilService: ApiUtilService,
        public agriAgrochemicalService: AgriAgrochemicalMasterService
    ) {
    }

    addAgrochemicalMaster() {
        this.agrochemicalMasterForm = this.fb.group({
            agrochemicalTypeId: ['', Validators.required],
            agrochemicalId: ['', Validators.required],
            cibrcApproved: ['No'],
            waitingPeriod: ['', Validators.required],
            commodityId: ['', Validators.required],
            // stressTypeId: ['', Validators.required],
            // stressNameList: [''],
            status: ['Inactive'],
            name: [''],
            stressNameChkBox: new FormArray([]),
        });
    }

    async delay(ms: number) {
        await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
    }

    trimValue(formControl) { 
        formControl.setValue(formControl.value.trim()); 
      }
      
      
    //StressType list
    loadAllStressType() {
        return this.agriStressTypeService.GetAllStressType().subscribe((data: {}) => {
            this.StressTypeList = data;
        });
    }

    //Commodity list
    loadAllCommodities() {
        return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
            this.CommodityList = data;
        });
    }


    getDataByStressType() {
        if (this.agrochemicalMasterForm.value.stressTypeId) {
            this.zonalStressService.GetAllStressByStressTypeId(this.agrochemicalMasterForm.value.commodityId, this.agrochemicalMasterForm.value.stressTypeId).subscribe(res => {

                this.StressList = res;
                while ((this.agrochemicalMasterForm.controls.stressNameChkBox as FormArray).length !== 0) {
                    (this.agrochemicalMasterForm.controls.stressNameChkBox as FormArray).removeAt(0);
                }
                for (let subRes of this.StressList) {
                    let control1: any = new FormControl();
                    (this.agrochemicalMasterForm.controls.stressNameChkBox as FormArray).push(control1);
                }
            });
        } else {
            while ((this.agrochemicalMasterForm.controls.stressNameChkBox as FormArray).length !== 0) {
                (this.agrochemicalMasterForm.controls.stressNameChkBox as FormArray).removeAt(0);
            }
            this.StressList = [];
        }
    }

    getDataByCommodityIdAndStressTypeId() {
        if (this.agrochemicalMasterForm.value.commodityId, this.agrochemicalMasterForm.value.stressTypeId) {
            this.agriAgrochemicalMasterService.getAgrochemicalMasterByCommodityIdAndStressTypeId(this.agrochemicalMasterForm.value.commodityId, this.agrochemicalMasterForm.value.stressTypeId).subscribe(res => {

                this.StressList = res;
                while ((this.agrochemicalMasterForm.controls.stressNameChkBox as FormArray).length !== 0) {
                    (this.agrochemicalMasterForm.controls.stressNameChkBox as FormArray).removeAt(0);
                }
                for (let subRes of this.StressList) {
                    let control1: any = new FormControl();
                    (this.agrochemicalMasterForm.controls.stressNameChkBox as FormArray).push(control1);
                }
            });
        } else {
            while ((this.agrochemicalMasterForm.controls.stressNameChkBox as FormArray).length !== 0) {
                (this.agrochemicalMasterForm.controls.stressNameChkBox as FormArray).removeAt(0);
            }
            this.StressList = [];
        }
    }


    submitForm() {
      

        for (let controller in this.agrochemicalMasterForm.controls) {
            this.agrochemicalMasterForm.get(controller).markAsTouched();
        }

        if (this.agrochemicalMasterForm.invalid) {
            return;
        }
        
        if (this.agrochemicalMasterForm.get('agrochemicalTypeId').value == 0) {
            this.errorModal.showModal('ERROR', 'Please Select AgrochemicalType', '');
            return;
        }

        const stressNameChkBox = this.agrochemicalMasterForm.value.stressNameChkBox.map((v, i) => v ? this.StressList[i] : null)
            .filter(v => v !== null);
        this.agrochemicalMasterForm.patchValue({stressNameList: stressNameChkBox});


        this.agriAgrochemicalMasterService.CreateAgrochemicalMaster(this.agrochemicalMasterForm.value).subscribe(res => {
            this.isSubmitted = true;

            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                    this.addAgrochemicalMaster();
                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            }
        });

    }

    //agrohemical list
    loadAllAgrochemicalType() {
        return this.agriAgrochemicalTypeService.GetAllAgrochemicalType().subscribe((data: {}) => {
            this.AgrochemicalTypeList = data;
        });
    }

    //commodity list
    loadAllCommodity() {
        return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
            this.CommodityList = data;
        });
    }
    loadAllAgroChemicalNames() {
        return this.agriAgrochemicalService.GetAllAgrochemicalMaster().subscribe((data: {}) => {
            this.AgroChemicalNameList = data;
        });
    }
    downloadExcelFormat() {
        var tableName = 'agri_commodity_agrochemical';
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
        this.router.navigate(['/agrochemicals/commodity-agrochemical']);
    }
}



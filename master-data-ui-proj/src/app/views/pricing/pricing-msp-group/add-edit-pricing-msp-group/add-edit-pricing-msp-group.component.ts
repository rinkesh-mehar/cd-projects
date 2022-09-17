import {Component, OnInit, Input, Output, EventEmitter, SimpleChanges, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, NgForm, NgModel, Validators} from '@angular/forms';
// import custom validator to validate that password and confirm password fields match
import {ActivatedRoute, Router} from '@angular/router';
import {ApiUtilService} from '../../../services/api-util.service';
import {AgriCommodityService} from '../../../agri/services/agri-commodity.service';
import {PricingMspGroupService} from '../../services/pricing-msp-group.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {GeoStateService} from '../../../geo/services/geo-state.service';
import { ZonalCommodityService } from '../../../zonal/services/zonal-commodity.service';
import { AczService } from '../../../zonal/services/acz.service';
import { ZonalVarietyService } from '../../../regional/services/zonal-variety.service';

@Component({
    selector: 'app-add-edit-pricing-msp-group',
    templateUrl: './add-edit-pricing-msp-group.component.html',
    styleUrls: ['./add-edit-pricing-msp-group.component.scss']
})
export class AddEditPricingMspGroupComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    MspGroupForm: FormGroup;

    commodityList: any;
    mode: any;
    MspGroup: any;
    stateList: any;
    data: any;

    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    _statusMsg: string;
    data1: any;
    regionList: any[];
    varietyList: any[];
    zonalCommodityList:any = [];
    selectedState: string;
    selectedCommodity: string;
    selectedRegion: string;
    selectedAcz:string;
    selectedVarietyIDs: any[];
    aczList:any=[];
    // numberPattern = '^[0-9][.0-9]*$';
    varietyName: any;
    commodityId:number;

    constructor(public formBuilder: FormBuilder,
                public pricingMspGroupService: PricingMspGroupService,
                private agriCommodityService: AgriCommodityService,
                private actRoute: ActivatedRoute,
                public apiUtilService: ApiUtilService,
                public router: Router,
                public zonalCommodityService: ZonalCommodityService,
                public zonalVarietyService: ZonalVarietyService,
                public aczService :AczService,
                public geoStateService: GeoStateService) {


        this.createMspGroupForm();


    }

    getChanges() {
        console.log(this.MspGroupForm.value);
    }

    createMspGroupForm() {
        this.MspGroupForm = this.formBuilder.group({
            // commodityID: ['', Validators.required],
            // stateCode: ['', Validators.required],
            // regionID: ['', Validators.required],
            // varietyID: ['', Validators.required],
            // status: ['Inactive'],
            // msp: ['', Validators.required],
            // mfp: ['', Validators.required],

        });
    }


    equals(objOne, objTwo) {
        if (typeof objOne !== 'undefined' && typeof objTwo !== 'undefined') {
            return objOne.id === objTwo.id;
        }
    }

    selectAll(select: NgModel, values) {
        select.update.emit(values);
    }

    deselectAll(select: NgModel) {
        select.update.emit([]);
    }

    ngOnInit() {
        this.data1 = this.pricingMspGroupService.getData;

        this.loadAllCommodity();
        this.loadActiveState();


        this.MspGroupForm.patchValue({commodityID: (this.data1[0].commodityID)});
        const splittedStates = this.data1[0].stateCodes.split(',').map(Number);
        this.MspGroupForm.patchValue({stateCode: (splittedStates)});
        this.MspGroupForm.patchValue({msp: (this.data1[0].msp)});

        if (this.data1.length >= 1) {
            this.mode = 'edit';

        }

    }

    loadActiveState() {
        return this.geoStateService.GetAllState().subscribe((data: any) => {
            this.stateList = data;
        }, err => {
            alert(err);
        });
    }


    loadAllCommodity() {
        return this.pricingMspGroupService.GetAllCommodities().subscribe((data: any) => {
            this.commodityList = data;
        }, err => {
            alert(err);
        });
    }

    submitMspGroupForm(form: NgForm) {

        for (const controller in form.form.controls) {
            form.form.get(controller).markAsTouched();
        }

        if (this.MspGroupForm.invalid) {
            return;
        }
        if (form.invalid) {
            return;
        }

        this.addMspMfp(form);
    }

    addMspMfp(formData: NgForm) {

        const data = {
            'commodityId': formData.value.commodityID,
            'stateCode': formData.value.stateCode,
            'regionId': formData.value.regionID,
            'varietyId': formData.value.varietyID,
            'msp': formData.value.msp,
            'mfp': formData.value.mfp,
            'screen': 'MspMfp'
        };

        this.pricingMspGroupService.addPrice(data).subscribe((res: any) => {
            this.isSubmitted = true;
            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                    this._statusMsg = res.message;
                    this.MspGroup = {};
                    this.mode = 'add';
                    this.successModal.showModal('SUCCESS', 'MSP and MFP has been added successfully.', '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            }
        }, err => {
            console.log(err);
        });
    }


    loadRegion(stateCode) {
        if (this.selectedState === undefined) {
            return this.errorModal.showModal('ERROR', 'Please Select State', '');
        }

        return this.pricingMspGroupService.GetAllRegion(this.selectedState).subscribe((data: any) => {
            this.selectedRegion = '';
            this.selectedVarietyIDs = [];
            this.regionList = data;
        }, err => {
            alert(err);
        });
    }

    loadVariety() {
        // if (this.selectedState === undefined) {
        //     return this.errorModal.showModal('ERROR', 'Please Select State', '');
        // }
        this.selectedVarietyIDs = [];
        this.varietyName = [];

        if (this.selectedCommodity === undefined) {
            return this.errorModal.showModal('ERROR', 'Please Select Commodity', '');
        }

        if (this.selectedState !== undefined) {
            return this.pricingMspGroupService.GetAllVariety(this.selectedState, this.selectedCommodity).subscribe((data: any) => {
                this.varietyList = data;
            }, err => {
                alert(err);
            });
        }
    }

    async delay(ms: number) {
        await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
    }

    modalSuccess($event: any) {
        this.router.navigate(['/pricing/msp-mfp']);
    }

    getVarietyName() {
        const varietyNameTemp = [];
        for (const selectedVarietyID of this.selectedVarietyIDs){
            varietyNameTemp.push(' '.concat(selectedVarietyID.name));
        }
        this.varietyName = varietyNameTemp;
    }


    getZonalCommodityByAczId() {
        return this.zonalCommodityService.getZonalCommodityListByAczId(this.selectedAcz).subscribe((data: {}) => {
          this.zonalCommodityList = data;
        })
      }

    getAczByStateCode() {
    // alert(`Selected state is ${this.selectedState}`)
    return this.aczService.getAllAczByStateCode(this.selectedState).subscribe((data: {}) => {
        this.aczList = data;
    })
    }

    getZonalVarietyByZonalCommodity() {

        for(let arr of this.zonalCommodityList){
          console.log(` in outer loop = ${arr.id}`)             
             if(arr.id == this.selectedCommodity){
              console.log(` in loop = ${arr.id}`)             
                   this.commodityId =arr.commodityId;
             }
        }
  
      return this.zonalVarietyService.getZonalVarietyListByZonalCommodityId(this.selectedCommodity).subscribe((data: any) => {
        this.varietyList = data;
      })
    }
}

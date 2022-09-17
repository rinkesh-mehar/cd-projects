import { Component, OnInit, ViewChild } from '@angular/core';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { FormBuilder, FormGroup, NgForm, NgModel, Validators } from '@angular/forms';
import { PricingMspGroupService } from '../../services/pricing-msp-group.service';
import { GeoStateService } from '../../../geo/services/geo-state.service';
import { Router } from '@angular/router';
import { AczService } from '../../../zonal/services/acz.service';
import { ZonalCommodityService } from '../../../zonal/services/zonal-commodity.service';
import { ZonalVarietyService } from '../../../regional/services/zonal-variety.service';

@Component({
    selector: 'app-add-edit-mbep-pmp',
    templateUrl: './add-edit-mbep-pmp.component.html',
    styleUrls: ['./add-edit-mbep-pmp.component.scss']
})
export class AddEditMbepPmpComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    MbepPmpForm: FormGroup;
    commodityList: any;
    data: any;
    stateList: any;
    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    _statusMsg: string;
    mode: any;
    regionList: any[];
    varietyList: any[];
    selectedState: string;
    selectedCommodity: string;
    selectedRegion: string;
    selectedVarietyIDs: any[];
    varietyName: any;
    zonalCommodityList: any = [];
    aczList: any = [];
    selectedAcz: number;
    commodityId: number;

    constructor(public formBuilder: FormBuilder,
        public pricingMspGroupService: PricingMspGroupService,
        public geoStateService: GeoStateService,
        public router: Router, public aczService: AczService,
        public zonalCommodityService: ZonalCommodityService,
        public zonalVarietyService: ZonalVarietyService
    ) {
        this.createMbepPmpForm();
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

    ngOnInit(): void {
        this.data = this.pricingMspGroupService.getData;

        this.loadAllCommodity();
        this.loadActiveState();
    }


    createMbepPmpForm() {
        this.MbepPmpForm = this.formBuilder.group({
            // commodityID: ['', Validators.required],
            // stateCode: ['', Validators.required],
            // regionID: ['', Validators.required],
            // varietyID: ['', Validators.required],
            // status: ['Inactive'],
            // mbep: ['', Validators.required],
            // pmp: ['', Validators.required],
            // spread: ['', Validators.required]
        });
    }

    loadAllCommodity() {
        return this.pricingMspGroupService.GetAllCommodities().subscribe((data: any) => {
            this.commodityList = data;
        }, err => {
            alert(err);
        });
    }

    loadActiveState() {
        return this.geoStateService.GetAllState().subscribe((data: any) => {
            this.stateList = data;
        }, err => {
            alert(err);
        });
    }

    submitMbepPmpForm(form: NgForm) {
        for (const controller in form.form.controls) {
            form.form.get(controller).markAsTouched();
        }

        if (this.MbepPmpForm.invalid) {
            return;
        }
        if (form.invalid) {
            return;
        }
        this.addMbepPmp(form);
    }

    addMbepPmp(formData: NgForm) {

        const data = {
            'commodityId': formData.value.commodityID,
            'stateCode': formData.value.stateCode,
            'aczId': formData.value.acz,
            'regionId': formData.value.regionID,
            'varietyId': formData.value.varietyID,
            'mbep': formData.value.mbep,
            'pmp': formData.value.pmp,
            'priceSpread': formData.value.spread,
            'screen': 'MbepPmpSpread'
        };

        this.pricingMspGroupService.addPrice(data).subscribe((res: any) => {
            this.isSubmitted = true;
            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                    this._statusMsg = res.message;
                    // this.Spread = {};
                    this.mode = 'add';
                    this.successModal.showModal('SUCCESS', 'MBEP, PMP and Spread has been added successfully.', '');
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
        this.selectedVarietyIDs = [];
        this.varietyName = [];

        // if (this.selectedState === undefined) {
        //     return this.errorModal.showModal('ERROR', 'Please Select State', '');
        // }

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


    modalSuccess($event: any) {
        this.router.navigate(['/pricing/mbep-pmp']);
    }

    getVarietyName() {
        const varietyNameTemp = [];
        for (const selectedVarietyID of this.selectedVarietyIDs) {
            varietyNameTemp.push(' '.concat(selectedVarietyID.name));
        }
        this.varietyName = varietyNameTemp;
    }

    getAczByStateCode() {
        // alert(`Selected state is ${this.selectedState}`)
        return this.aczService.getAllAczByStateCode(this.selectedState).subscribe((data: {}) => {
            this.aczList = data;
        })
    }

    getZonalCommodityByAczId() {
        return this.zonalCommodityService.getZonalCommodityListByAczId(this.selectedAcz).subscribe((data: {}) => {
            this.zonalCommodityList = data;
        })
    }


    getZonalVarietyByZonalCommodity() {

        for (let arr of this.zonalCommodityList) {
            if (arr.id == this.selectedCommodity) {
                this.commodityId = arr.commodityId;
            }
        }

        return this.zonalVarietyService.getZonalVarietyListByZonalCommodityId(this.selectedCommodity).subscribe((data: any) => {
            this.varietyList = data;
        })
    }

}

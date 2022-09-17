import {Component, OnInit, ViewChild} from '@angular/core';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {FormBuilder, FormGroup, NgForm, NgModel, Validators} from '@angular/forms';
import {PricingMspGroupService} from '../../services/pricing-msp-group.service';
import {GeoStateService} from '../../../geo/services/geo-state.service';
import {Router} from '@angular/router';
import { ZonalCommodityService } from '../../../zonal/services/zonal-commodity.service';
import { AczService } from '../../../zonal/services/acz.service';
import { ZonalVarietyService } from '../../../regional/services/zonal-variety.service';

@Component({
    selector: 'app-add-edit-constants',
    templateUrl: './add-edit-constants.component.html',
    styleUrls: ['./add-edit-constants.component.scss']
})
export class AddEditConstantsComponent implements OnInit {

    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    ConstantsForm: FormGroup;
    commodityList: any;
    data: any;
    stateList: any;
    regionList: any[];
    varietyList: any[];
    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    _statusMsg: string;
    mode: any;
    selectedState: string;
    selectedAcz:string;
    selectedCommodity: string;
    selectedRegion: string;
    selectedVarietyIDs: any[];
    varietyName: any;
    zonalCommodityList:any = [];
    zonalVarietyList:any = [];
    aczList:any=[];
    commodityId:number;

    constructor(public formBuilder: FormBuilder,
                public pricingService: PricingMspGroupService,
                public geoStateService: GeoStateService,
                public router: Router,public zonalCommodityService: ZonalCommodityService,
                public aczService :AczService, public zonalVarietyService: ZonalVarietyService
    ) {
        this.createConstantsForm();
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
        this.data = this.pricingService.getData;

        this.loadAllCommodity();
        this.loadActiveState();
    }

    createConstantsForm() {
        this.ConstantsForm = this.formBuilder.group({
            // commodityID: ['', Validators.required],
            // stateCode: ['', Validators.required],
            // regionID: ['', Validators.required],
            // varietyID: ['', Validators.required],
            // status: ['Inactive'],
            // sellerConstant: ['', Validators.required],
            // buyerConstant: ['', Validators.required],
            // marginConstant: ['', Validators.required]
        });
    }


    loadAllCommodity() {
        return this.pricingService.GetAllCommodities().subscribe((data: any) => {
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

    loadRegion(stateCode) {
      
        if (this.selectedState === undefined) {
            return this.errorModal.showModal('ERROR', 'Please Select State', '');
        }

        return this.pricingService.GetAllRegion(this.selectedState).subscribe((data: any) => {
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
            return this.zonalVarietyService.getZonalVarietyListByZonalCommodityId(this.selectedCommodity).subscribe((data: any) => {
                this.varietyList = data;
            }, err => {
                alert(err);
            });
        }
    }

    submitConstantsForm(form: NgForm) {

      //   console.log(`form ${JSON.stringify(form)}`)

        for (const controller in form.form.controls) {
            form.form.get(controller).markAsTouched();
        }

        if (this.ConstantsForm.invalid) {
            return;
        }
        if (form.invalid) {
            return;
        }
        this.addConstants(form);
    }

    addConstants(formData: NgForm) {
        const data = {
            'commodityId': this.commodityId,
            'aczId': formData.value.acz,
            'stateCode': formData.value.stateCode,
            'regionId': formData.value.regionID,
            'varietyId': formData.value.varietyID,
            'sellerConstant': formData.value.sellerConstant,
            'buyerConstant': formData.value.buyerConstant,
            'marginConstant': formData.value.marginConstant,
            'screen': 'constants'
        };

        this.pricingService.addPrice(data).subscribe((res: any) => {
            this.isSubmitted = true;
            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                    this._statusMsg = res.message;
                    // this.Spread = {};
                    this.mode = 'add';
                    this.successModal.showModal('SUCCESS', 'Constants has been added successfully.', '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            }
        }, err => {
            console.log(err);
        });
    }


    modalSuccess($event: any) {
        this.router.navigate(['/pricing/constant']);
    }

    getVarietyName() {
        const varietyNameTemp = [];
        for (const selectedVarietyID of this.selectedVarietyIDs){
            varietyNameTemp.push(' '.concat(selectedVarietyID.name));
        }
        this.varietyName = varietyNameTemp;
    }


   // ACZ list
   //ACZ list
getAczByStateCode() {
    // alert(`Selected state is ${this.selectedState}`)
    return this.aczService.getAllAczByStateCode(this.selectedState).subscribe((data: {}) => {
      this.aczList = data;
    })
  }


    //ZonalCommodity list
getZonalCommodityByAczId() {
    return this.zonalCommodityService.getZonalCommodityListByAczId(this.selectedAcz).subscribe((data: {}) => {
      this.zonalCommodityList = data;
    })
  }
  // ZonalVariety by ZonalCommodity
  getZonalVarietyByZonalCommodity() {

      for(let arr of this.zonalCommodityList){          
           if(arr.id == this.selectedCommodity){ 
                 this.commodityId =arr.commodityId;
           }
      }

    return this.zonalVarietyService.getZonalVarietyListByZonalCommodityId(this.selectedCommodity).subscribe((data: any) => {
      this.varietyList = data;
    })
  }
}



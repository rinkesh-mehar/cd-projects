import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TickerService} from '../../services/ticker.service';
import {AgriCommodityService} from '../../../agri/services/agri-commodity.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
    selector: 'app-market-price',
    templateUrl: './add-edit-market-price.component.html',
    styleUrls: ['./add-edit-market-price.component.scss']
})

export class AddEditMarketPriceComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    isSubmittedBulk: boolean = false;
    isSuccessBulk: boolean = false;
    _statusMsg: string;

    marketPlaceFrom: FormGroup;
    commodityList: any = [];
    marketNameList: any;
    varietyList: any;
    stateList: any;
    districtList: any;
    id: any;
    name: any;
    state: number;
    mode: string = 'add';
    editId: string;


    constructor(private actRoute: ActivatedRoute, private router: Router, public formBuilder: FormBuilder, private agriCommodityService: AgriCommodityService, private tickerService: TickerService) {

    }

    ngOnInit() {
        this.loadAllCommodity();
        this.loadAllStates();

        this.marketPlaceFrom = this.formBuilder.group({
            commodityId: ['', Validators.required],
            marketId: ['', Validators.required],
            varietyId: ['', Validators.required],
            minPrice: ['', Validators.required],
            maxPrice: ['', Validators.required],
            modalPrice: ['', Validators.required],
            stateId: ['', Validators.required],
            districtId: ['', Validators.required],
            status: ['Inactive']
        });

        this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
            this.mode = 'edit';
            this.tickerService.getMarketPriceById(this.editId).subscribe(data => {
                this.marketPlaceFrom.patchValue(data);
                this.loadAllVariety(this.marketPlaceFrom.value.commodityId);
                this.loadAllDistrict(this.marketPlaceFrom.value.stateId);
                this.loadAllMarket(this.marketPlaceFrom.value.districtId);
            });
            console.log('id ' + this.editId);
        }
    }

    submitMarketPriceForm() {

        for (const controller in this.marketPlaceFrom.controls) {
            this.marketPlaceFrom.get(controller).markAsTouched();
        }

        if (this.marketPlaceFrom.invalid) {
            return;
        }

        if (this.mode == 'add') {
            this.addMarketPrice();
        } else {
            this.update();
        }
    }

    update() {

        this.loadAllVariety(this.marketPlaceFrom.value.commodityId);

        const data = {
            // Id: this.editId,
            commodityId: this.marketPlaceFrom.value.commodityId,
            marketId: this.marketPlaceFrom.value.marketId,
            varietyId: this.marketPlaceFrom.value.varietyId,
            minPrice: this.marketPlaceFrom.value.minPrice,
            maxPrice: this.marketPlaceFrom.value.maxPrice,
            modalPrice: this.marketPlaceFrom.value.modalPrice,
            'status': this.marketPlaceFrom.value.status

        };

        return this.tickerService.updateMarketPrice(this.editId, data).subscribe(res => {
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

    loadAllCommodity() {
        return this.tickerService.getAllCommodity().subscribe((data: {}) => {
            this.commodityList = data;
            console.log('commodityList is ', this.commodityList);
        });
    }

    loadAllVariety(commodityId) {
        return this.tickerService.getAllVarietyByCommodity(this.marketPlaceFrom.value.commodityId).subscribe((data: any) => {
            this.varietyList = data;
            console.log('list of variety is ', this.varietyList);
        }, err => {
            alert(err);
        });
    }

    addMarketPrice() {
        this.tickerService.addMarketPrice(this.marketPlaceFrom.value).subscribe(res => {

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

    loadAllStates() {
        return this.tickerService.getAllState().subscribe((data: any) => {
            this.stateList = data;
            console.log('list of state is ', this.stateList);
        }, err => {
            alert(err);
        });
    }

    loadAllDistrict(StateCode) {
        return this.tickerService.getAllDistrict(this.marketPlaceFrom.value.stateId).subscribe((data: any) => {
            this.districtList = data;
            this.state = StateCode.target['value'];
            console.log('list of district is ', this.districtList);
        }, err => {
            alert(err);
        });
    }

    loadAllMarket(event) {
        return this.tickerService.loadAllMarket(this.marketPlaceFrom.value.districtId, this.marketPlaceFrom.value.stateId).subscribe((data: any) => {
            this.marketNameList = data;
            console.log('list of marketNameList is ', this.marketNameList);
        }, err => {
            alert(err);
        });
    }

    modalSuccess($event: any) {
        this.router.navigate(['/tickers/market-price']);
    }

}

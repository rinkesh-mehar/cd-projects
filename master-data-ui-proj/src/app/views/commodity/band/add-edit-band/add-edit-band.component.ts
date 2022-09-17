import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AgriCommodityService} from '../../../agri/services/agri-commodity.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {ActivatedRoute, Router} from '@angular/router';
import {BandService} from '../../service/band.service';

@Component({
    selector: 'app-market-price',
    templateUrl: './add-edit-band.component.html',
    styleUrls: ['./add-edit-band.component.scss']
})

export class AddEditBandComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    isSubmittedBulk: boolean = false;
    isSuccessBulk: boolean = false;
    _statusMsg: string;

    bandFrom: FormGroup;
    bandList: any = [];
    varietyList: any;
    stateList: any;
    id: any;
    name: any;
    state: number;
    mode: string = 'add';
    editId: string;


    constructor(private actRoute: ActivatedRoute,
                private router: Router,
                public formBuilder: FormBuilder,
                private agriCommodityService: AgriCommodityService,
                private bandService: BandService) {

    }

    ngOnInit() {
        // this.loadAllCommodity();
        // this.loadAllStates();
        this.getBandList();

        this.bandFrom = this.formBuilder.group({
            name: ['', Validators.required],
            status: ['Inactive']

        });

        this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
            this.mode = 'edit';
            this.bandService.getBandById(this.editId).subscribe(data => {
                this.bandFrom.patchValue(data);
                // this.loadAllVariety(this.marketPlaceFrom.value.commodityId);
                // this.loadAllDistrict(this.marketPlaceFrom.value.stateId);
                // this.loadAllMarket(this.marketPlaceFrom.value.districtId);
            });
            console.log('id ' + this.editId);
        }
    }

    submitBandForm() {

        for (const controller in this.bandFrom.controls) {
            this.bandFrom.get(controller).markAsTouched();
        }

        if (this.bandFrom.invalid) {
            return;
        }

        if (this.mode == 'add') {
            this.addBand();
        } else {
            this.update();
        }
    }

    update() {

        // this.getBandList();

        const data = {
            'name': this.bandFrom.value.name,
        };

        return this.bandService.updateBand(this.editId, data).subscribe(res => {
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


    getBandList() {
        return this.bandService.getBandList().subscribe((data: {}) => {
            this.bandList = data;
        });
    }

    addBand() {
        this.bandService.addBand(this.bandFrom.value).subscribe(res => {

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

    // async delay(ms: number) {
    //     await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
    // }
    //
    // loadAllStates() {
    //     return this.tickerService.getAllState().subscribe((data: any) => {
    //         this.stateList = data;
    //         console.log('list of state is ', this.stateList);
    //     }, err => {
    //         alert(err);
    //     });
    // }
    //
    // loadAllDistrict(StateCode) {
    //     return this.tickerService.getAllDistrict(this.marketPlaceFrom.value.stateId).subscribe((data: any) => {
    //         this.districtList = data;
    //         this.state = StateCode.target['value'];
    //         console.log('list of district is ', this.districtList);
    //     }, err => {
    //         alert(err);
    //     });
    // }
    //
    // loadAllMarket(event) {
    //     return this.tickerService.loadAllMarket(this.marketPlaceFrom.value.districtId, this.marketPlaceFrom.value.stateId).subscribe((data: any) => {
    //         this.marketNameList = data;
    //         console.log('list of marketNameList is ', this.marketNameList);
    //     }, err => {
    //         alert(err);
    //     });
    // }

    modalSuccess($event: any) {
        this.router.navigate(['/tickers/market-price']);
    }

}

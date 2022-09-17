import {Component, OnInit, ViewChild} from '@angular/core';
import {WarehouseService} from '../../service/warehouse.service';
import {ActivatedRoute} from '@angular/router';
import {ConfirmationMadalComponent} from '../../../global/confirmation-madal/confirmation-madal.component';
import {globalConstants} from '../../../global/globalConstants';
import {Alert} from 'selenium-webdriver';
import {environment} from '../../../../../environments/environment';

@Component({
    selector: 'app-warehouse-details',
    templateUrl: './warehouse-details.component.html',
    styleUrls: ['./warehouse-details.component.scss']
})
export class WarehouseDetailsComponent implements OnInit {

    @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;

    isSubmitted: boolean;
    isSuccess: any;
    _statusMsg: any;
    isSelected: boolean;

    kycStatus: string;
    statusResponse: any;
    warehouseImgUrl: string;
    warehouseId: any;
    warehouseDetails: any = {};

    structuralInfo: any = {
        chkBox: [
            {name: 'Fencing', code: 'WMS_020', value: false},
            {name: 'Wall Compound', code: 'WMS_011', value: false},
            {name: 'Gate', code: 'WMS_024', value: false},
            {name: 'Storage worthy', code: 'WMS_049', value: false},
            {name: 'Fire Extinguisher', code: 'WMS_022', value: false},
            {name: 'Firebucket', code: 'WMS_021', value: false},
            {name: 'Live wire inside the warehouse', code: 'WMS_028', value: false},
            {name: 'Electricity Supply', code: 'WMS_018', value: false},
            {name: 'Drainage', code: 'WMS_016', value: false},
            {name: 'Insurance Facility', code: 'WMS_025', value: false},
            {name: 'Dunnage', code: 'WMS_017', value: false,},
        ],

    };

    constructor(public warehouseService: WarehouseService,
                private actRoute: ActivatedRoute) {
    }

    ngOnInit() {

        this.warehouseImgUrl = environment.warehouseImgUrl;

        this.warehouseDetails = {
            warehouseName: '',
            registrar: '',
            address: {},
            warehouseAddress: {},
            metaExcerpt: {},
            metaExcerptModel: {},
            warehouseSlots: [],
            extra: {},
            metaExcerptPhoto: [],
            metaExcerptPhotoMap: {}
        };


        this.warehouseId = this.actRoute.snapshot.paramMap.get('id');

        if (this.warehouseId) {
            this.warehouseService.getWarehouse(this.warehouseId).subscribe(data => {
                this.warehouseDetails = data.data;
                this.warehouseDetails.metaExcerptModel = JSON.parse(JSON.stringify(data.data.metaExcerpt));
                if (data.data.metaExcerpt) {
                    for (const meta of data.data.metaExcerptModel) {
                        this.warehouseDetails.metaExcerpt[meta.MetaDescription] = meta.MetaValue;
                    }
                } else {
                    this.warehouseDetails.metaExcerpt = {};
                }
                for (const chk of this.structuralInfo.chkBox) {
                    if (this.warehouseDetails.metaExcerpt[chk.code]) {
                        chk.value = true;
                    }
                }
            });
        }
    }


    getCommodityName(metaDesc) {
        const commodity = this.warehouseDetails.metaExcerptModel.find(x => x.MetaDescription == metaDesc);
        return commodity.Commodity;
    }

    getImage(id) {
        if (id) {
            return this.warehouseImgUrl + id;
        } else {
            return 'assets/img/property.jpg';
        }

    }


    submit(kycStatus) {
        console.log(kycStatus);
        if (kycStatus == 'Rejected') {
            this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + ' ' + this.warehouseDetails.warehouseName, this.warehouseDetails);
        } else if (kycStatus == 'Approved') {
            this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + ' ' + this.warehouseDetails.warehouseName, this.warehouseDetails);
        } else {
            alert('Please Change KYC Status!');
        }

    }


    modalConfirmation(event) {
        console.log(event);
        if (event) {
            this.isSubmitted = true;
            if (!(event.kycStatus === 'Rejected')) {
                event.statusResponse = null;
            }
            this.warehouseService.updateStatus(event.id, event.kycStatus, event.statusResponse).subscribe(res => {
                if (res) {
                    this.isSuccess = res.success;
                    if (res.success) {
                        this._statusMsg = res.message;
                        window.scrollTo(0, 0);
                        setTimeout(() => {
                            this.isSubmitted = false;
                            this.isSuccess = false;
                            this._statusMsg = '';
                        }, 4000);
                    } else {
                        this._statusMsg = res.error;
                    }
                }
            }, err => {
                this._statusMsg = err.error;
            });
        }
    }


}

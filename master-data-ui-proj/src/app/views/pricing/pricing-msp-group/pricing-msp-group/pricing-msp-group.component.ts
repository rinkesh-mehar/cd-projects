import {Component, OnInit, ViewChild} from '@angular/core';

import {ConfirmationMadalComponent} from '../../../global/confirmation-madal/confirmation-madal.component';
import {UserRightsService} from '../../../services/user-rights.service';
import {globalConstants} from '../../../global/globalConstants';
import {PricingMspGroupService} from '../../services/pricing-msp-group.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {Sort} from '@angular/material';
import {PagePricingMsp} from '../../models/PagePricingMsp';
import {BulkDataService} from '../../../agri/services/bulk-data.service';

// import { PagePricingMspGroup } from '../../models/PagePricingMspGroup';

@Component({
    selector: 'app-pricing-msp-group',
    templateUrl: './pricing-msp-group.component.html',
    styleUrls: ['./pricing-msp-group.component.scss']
})
export class PricingMspGroupComponent implements OnInit {
    @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    _statusMsg: string;
    mspGroupStatus;
    MspGroupList: any = [];
    pagePricingMsp: PagePricingMsp;
    stateRegionList: any;
    flag: number;
    selectedPage: number = 1;
    maxSize: number = 10;
    searchText: any = '';
    selectedStateCode: number;
    selectedRegionId: number;
    selectedCommodityId: number;
    records: any = [];
    recordsPerPage: number = 10;

    ngOnInit() {
        // this.getPaginatedPricingMSP(1);
        // this.records = ['20', '50', '100', '200', '250'];
        this.getStateAndRegion();
        this.flag = 0;
        this.mspGroupStatus = globalConstants;
        this.pricingMspGroupService.cleanStoredData();
    }

    constructor(
        private pricingMspGroupService: PricingMspGroupService,
        public bulkDatas: BulkDataService,
        private userRightsService: UserRightsService,
    ) {
    }

    // loadAllPricingMspGroup list
    loadAllPricingMspGroup() {
        return this.pricingMspGroupService.GetAllMspGroup().subscribe((data: any) => {
            this.MspGroupList = data;
        });
    }

    // GetAllMspGroupPaginated(page) {
    //   return this.PricingMspGroupService.GetAllMspGroupPaginated(page).subscribe((data: {}) => {
    //     this.MspGroupList = data;
    //   });
    // }

    // // Delete PricingMspGroup
    // deletePricingMspGroup(data){
    //   var index = index = this.EcosystemList.map(x => {return x.name}).indexOf(data.name);
    //    return this.PricingMspGroupService.DeletePricingMspGroup(data.id).subscribe(res => {
    //     this.EcosystemList.splice(index, 1)
    //      console.log('Agri Ecosystem deleted!')
    //    })
    // }

    // onSelect(page: number): void {
    //   console.log('selected page : ' + page);
    //   this.selectedPage = page;
    //   this.getPaginatedPricingMSP(page);
    // }

    // getPaginatedPricingMSP(page): void {
    //    this.pricingMspGroupService.getPagePricingMspGroup(page)
    //    .subscribe(page => this.pagePricingMsp = page);
    //  }

    getStateAndRegion(): any {
        this.pricingMspGroupService.getStateAndRegion()
            .subscribe(page => this.stateRegionList = page);
    }

    // loadData(event: any) {
    //   console.log('pages ', event.target.value);
    //   this.recordsPerPage = event.target.value || 10;
    //   this.pricingMspGroupService.getStateAndRegion(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    //       .subscribe(page => this.pagePricingMsp = page);
    // }
    // onSelect(page: number): void {
    //   console.log('selected page : ' + page);
    //   this.selectedPage = page;
    //   this.getStateAndRegion(page);
    // }

    getMSPAndMFP(sCode, rId, cId): any {

        this.selectedStateCode = sCode;
        this.selectedRegionId = rId;
        this.selectedCommodityId = cId;

        if (cId !== undefined) {
            this.flag = 2;
        }

        if (cId === undefined) {
            this.flag = 1;
            cId = 0;
        }

        this.pricingMspGroupService.getMSPAndMFP(sCode, rId, cId, this.flag)
            .subscribe(page => this.stateRegionList = page);
    }

    manageFlag() {
        this.flag = this.flag - 1;

        if (this.selectedCommodityId === undefined) {
            this.selectedCommodityId = 0;
        }

        if (this.flag === 0) {
            this.pricingMspGroupService.getStateAndRegion()
                .subscribe(page => this.stateRegionList = page);
        }

        if (this.flag !== 0) {

            this.pricingMspGroupService.getConstants(this.selectedStateCode, this.selectedRegionId, this.selectedCommodityId, this.flag)
                .subscribe(page => this.stateRegionList = page);
        }
    }


    editMSP(commodityID, stateCodes, msp) {
        const data = {
            'commodityID': commodityID,
            'stateCodes': stateCodes,
            'msp': msp
        };

        this.pricingMspGroupService.storeData(data);

        this.pricingMspGroupService.editMSP(data)
            .subscribe(page => {
                this.pagePricingMsp = page;
            });
    }

    // Delete
    delete(data, i) {
        data.index = i;
        data.flag = 'delete';
        this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + ' ' + data.variety, data);
    }

    // Reject
    reject(data, i) {
        data.index = i;
        data.flag = 'reject';
        this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + ' ' + data.variety, data);
    }

    approve(data, i) {
        data.index = i;
        data.flag = 'approve';
        this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + ' ' + data.variety, data);
    }

    finalize(data, i) {
        data.index = i;
        data.flag = 'finalize';
        this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + ' ' + data.variety, data);
    }

    modalConfirmation(event) {
        console.log(event);
        let observable: any;
        if (event) {
            this.isSubmitted = true;
            if (event.flag == 'approve') {
                observable = this.pricingMspGroupService.ApproveMspGroup(event.id);
            } else if (event.flag == 'finalize') {
                observable = this.pricingMspGroupService.FinalizeMspGroup(event.id);
            } else if (event.flag == 'delete') {
                observable = this.pricingMspGroupService.DeleteMspGroup(event.id);
            } else if (event.flag == 'reject') {
                observable = this.pricingMspGroupService.RejectMspGroup(event.id);
            }
            observable.subscribe(res => {
                if (res) {
                    this.isSuccess = res.success;
                    if (res.success) {
                        this.successModal.showModal('SUCCESS', res.message, '');
                    } else {
                        this.errorModal.showModal('ERROR', res.error, '');
                    }
                }
            }, err => {
                this.errorModal.showModal('ERROR', err.error, '');
            });
        }

    }

    // searchMspGroup(){
    //   this.selectedPage = 1
    //   console.log(this.searchText);
    //   this.getPagePricingMspGroup(this.selectedPage - 1);
    // }

    sortData(sort: Sort, flag: number) {
        const data = this.stateRegionList.slice();
        if (!sort.active || sort.direction == '') {
            this.stateRegionList = data;
            return;
        }

        function compare(firstValue, secondValue, isAsc: boolean) {
            return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }

        if (flag === 0) {
            this.stateRegionList = data.sort((firstValue, secondValue) => {
                const isAsc = sort.direction == 'asc';
                switch (sort.active) {
                    case 'stateName':
                        return compare(firstValue.stateName, secondValue.stateName, isAsc);
                    case 'regionName':
                        return compare(firstValue.regionName, secondValue.regionName, isAsc);
                    default:
                        return 0;
                }
            });
        }
        if (flag === 1) {
            this.stateRegionList = data.sort((firstValue, secondValue) => {
                const isAsc = sort.direction == 'asc';
                switch (sort.active) {
                    case 'stateName':
                        return compare(firstValue.stateName, secondValue.stateName, isAsc);
                    case 'regionName':
                        return compare(firstValue.regionName, secondValue.regionName, isAsc);
                    case 'commodityName':
                        return compare(firstValue.commodityName, secondValue.commodityName, isAsc);
                    default:
                        return 0;
                }
            });
        }
        if (flag === 2) {
            this.stateRegionList = data.sort((firstValue, secondValue) => {
                const isAsc = sort.direction == 'asc';
                switch (sort.active) {
                    case 'stateName':
                        return compare(firstValue.stateName, secondValue.stateName, isAsc);
                    case 'regionName':
                        return compare(firstValue.regionName, secondValue.regionName, isAsc);
                    case 'varietyName':
                        return compare(firstValue.varietyName, secondValue.varietyName, isAsc);
                    case 'gradeName':
                        return compare(firstValue.gradeName, secondValue.gradeName, isAsc);
                    case 'msp':
                        return compare(firstValue.msp, secondValue.msp, isAsc);
                    case 'mfp':
                        return compare(firstValue.mfp, secondValue.mfp, isAsc);
                    case 'isBenchmark':
                        return compare(firstValue.isBenchmark, secondValue.isBenchmark, isAsc);
                    default:
                        return 0;
                }
            });
        }
    }

    modalSuccess($event: any) {
        this.ngOnInit();
    }

    download() {
        this.pricingMspGroupService.exportMspMfpToExcel(this.stateRegionList[0].stateCode, this.stateRegionList[0].regionID, this.stateRegionList[0].commodityID);
    }

    // searchRegionState() {
    //   console.log(this.searchText);
    //   this.getStateAndRegion(this.selectedPage - 1);
    // }

}

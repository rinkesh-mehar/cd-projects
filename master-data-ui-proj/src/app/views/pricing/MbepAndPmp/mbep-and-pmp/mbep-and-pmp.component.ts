import {Component, OnInit} from '@angular/core';
import {PricingMspGroupService} from '../../services/pricing-msp-group.service';
import {BulkDataService} from '../../../agri/services/bulk-data.service';
import {Sort} from '@angular/material';

@Component({
    selector: 'app-mbep-and-pmp',
    templateUrl: './mbep-and-pmp.component.html',
    styleUrls: ['./mbep-and-pmp.component.scss']
})
export class MbepAndPmpComponent implements OnInit {
    stateRegionList: any = [];
    flag: number;
    selectedStateCode: number;
    selectedRegionId: number;
    selectedCommodityId: number;
    searchText: any = '';
    recordsPerPage: number = 10;
    selectedPage: number = 1;
    maxSize: number = 10;

    constructor(private pricingMspGroupService: PricingMspGroupService,
                public bulkDatas: BulkDataService) {
    }

    ngOnInit(): void {
        this.getStateAndRegion();
        this.flag = 0;
    }


    getStateAndRegion(): any {
        this.pricingMspGroupService.getStateAndRegion()
            .subscribe(page => this.stateRegionList = page);
    }

    getMbepAndPmp(sCode, rId, cId): any {

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

        this.pricingMspGroupService.getMbepAndPmp(sCode, rId, cId, this.flag)
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
                    case 'commodityName':
                        return compare(firstValue.commodityName, secondValue.commodityName, isAsc);
                    case 'varietyName':
                        return compare(firstValue.varietyName, secondValue.varietyName, isAsc);
                    case 'gradeName':
                        return compare(firstValue.gradeName, secondValue.gradeName, isAsc);
                    case 'mbep':
                        return compare(firstValue.mbep, secondValue.mbep, isAsc);
                    case 'pmp':
                        return compare(firstValue.pmp, secondValue.pmp, isAsc);
                    case 'priceSpread':
                        return compare(firstValue.priceSpread, secondValue.priceSpread, isAsc);
                    case 'isBenchmark':
                        return compare(firstValue.isBenchmark, secondValue.isBenchmark, isAsc);
                    default:
                        return 0;
                }
            });
        }
    }

    download() {
        this.pricingMspGroupService.exportMbepPmpSpreadToExcel(this.stateRegionList[0].stateCode, this.stateRegionList[0].regionID, this.stateRegionList[0].commodityID);
    }

}

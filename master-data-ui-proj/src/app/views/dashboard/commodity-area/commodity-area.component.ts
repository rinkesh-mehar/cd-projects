import {Component, OnInit} from '@angular/core';
import {Chart} from 'chart.js';
import {FormBuilder, NgForm, NgModel} from '@angular/forms';
import {DashboardService} from '../service/dashboard.service';
import {getStyle} from '@coreui/coreui/dist/js/coreui-utilities';

@Component({
    selector: 'app-commodity-area',
    templateUrl: './commodity-area.component.html',
    styleUrls: ['./commodity-area.component.scss']
})
export class CommodityAreaComponent implements OnInit {

    commodityAreaChart: any;

    selectedCommodityID: any[];
    selectedRegionID: any[];
    overAllData: any;
    landSize: any;
    commodityName: any;
    commodityIdNameMap: any;
    editFlag: number = 0;
    changedLHAreaList: any;
    changedCommodityList: any;
    customLandSize: any;
    customCommodityName: any;
    regionIdNameMap: any;

    constructor(public dashboardService: DashboardService, public formBuilder: FormBuilder) {
    }

    ngOnInit(): void {
        this.loadCommodityAreaChart(undefined, undefined, undefined, undefined);
    }

    loadCommodityAreaChart(commodityId: any, regionId: any, changedCommodityIdMap: any, changedRegionIdMap: any) {

        /**
         * Code use to generate commodity area wise chart
         */

        const requestData = {
            'editFlag': this.editFlag,
            'regionIds': regionId,
            'commodityIds': commodityId
        };

        // this.dashboardService.getOverallChart(requestData).subscribe(data => {
        //     this.overAllData = data;
        //
        //     this.landSize = data.landSize;
        //     this.commodityName = data.commodityNames;
        //     this.commodityIdNameMap = data.commodityIdName;
        //     this.regionIdNameMap = data.allRegionList;
        //
        //     this.commodityIdNameMap.sort(function (a, b) {
        //         return a.name.localeCompare(b.name);
        //     });
        //
        //     this.regionIdNameMap.sort(function (a, b) {
        //         return a.name.localeCompare(b.name);
        //     });
        //
        //     if (this.editFlag === 0) {
        //         this.selectedCommodityID = this.commodityIdNameMap;
        //         this.selectedRegionID = this.regionIdNameMap;
        //     }
        //
        //
        //     if (this.editFlag === 1) {
        //         const editedFarmSize = [];
        //         const editedCommodityName = [];
        //
        //         for (const x of data.commodityAreaList) {
        //             editedFarmSize.push(x.FarmSize);
        //             editedCommodityName.push(x.name);
        //         }
        //
        //         this.commodityName = editedCommodityName;
        //         this.landSize = editedFarmSize;
        //
        //         this.selectedCommodityID = changedCommodityIdMap;
        //         this.selectedRegionID = changedRegionIdMap;
        //
        //     }
        //
        //     this.commodityAreaChart = new Chart('commodityAreaChart', {
        //         type: 'bar',
        //         data: {
        //             labels: this.commodityName,
        //             datasets: [
        //                 {
        //                     label: 'Crop Area (Acre)',
        //                     data: this.landSize,
        //                     backgroundColor: '#2935E8',
        //                     borderColor: '#0919ff',
        //                     borderWidth: 1,
        //                     barPercentage: 0.8,
        //                 }
        //             ]
        //         },
        //         options: {
        //             responsive: true,
        //             maintainAspectRatio: false,
        //             animation: {
        //                 duration: 4000
        //             },
        //             hover: {
        //                 animationDuration: 500
        //             },
        //             scales: {
        //                 xAxes: [{
        //                     stacked: true,
        //                 }],
        //                 yAxes: [{
        //                     stacked: true,
        //                     ticks: {
        //                         beginAtZero: true
        //                     }
        //                 }]
        //             }
        //         },
        //     });
        // });
    }


    changeOverallChart(form: NgForm) {

        const selectedCommodity = form.value.overallCommodityID;

        this.changedLHAreaList = [];
        this.changedCommodityList = [];

        if (selectedCommodity !== null) {
            for (const x of selectedCommodity) {
                const index = this.commodityName.indexOf(x.name);

                this.changedLHAreaList.push(this.landSize[index]);
                this.changedCommodityList.push(this.commodityName[index]);
            }
        }

        this.commodityAreaChart.destroy();

        const commodityId = [];
        const regionId = [];

        this.editFlag = 1;

        for (const x of form.value.overallCommodityID) {
            commodityId.push(x.id);
        }

        for (const x of form.value.regionID) {
            regionId.push(x.id);
        }

        this.loadCommodityAreaChart(commodityId, regionId, form.value.overallCommodityID, form.value.regionID);
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


}

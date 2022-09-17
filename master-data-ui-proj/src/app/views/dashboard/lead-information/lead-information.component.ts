import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, NgForm, NgModel} from '@angular/forms';
import {Chart} from 'chart.js';
import {getStyle} from '@coreui/coreui/dist/js/coreui-utilities';
import {DashboardService} from '../service/dashboard.service';

@Component({
    selector: 'app-lead-information',
    templateUrl: './lead-information.component.html',
    styleUrls: ['./lead-information.component.scss']
})
export class LeadInformationComponent implements OnInit {


    leadInformationChart: any;

    leadData: any;
    regionNameList: any;
    changedRegionNameList: any[];
    selectedRegionIDs: any[];
    selectedCommodityIDs: any[];
    totalLeadsList: any[];
    rejectedLeadsList: any[];
    pendingLeadsList: any[];
    changedRejectedLeadsList: any[];
    changedPendingLeadsList: any[];
    verifiedLeadsList: any[];
    changedVerifiedLeadsList: any[];
    regionIdNameList: any[];
    commodityNameIdList: any[];
    editRegion: number = 0;
    editFlag: number = 0;
    regionIdList: any[];
    finalVerifiedLead: any[];
    finalRejectedLead: any[];
    finalPendingLead: any[];
    finalRegionName: any[];
    changedRegionNameIdMap: any;
    changedCommodityNameIdMap: any;

    totalLeads: any;

    constructor(public dashboardService: DashboardService, public formBuilder: FormBuilder) {
    }

    ngOnInit(): void {
        const editData = {
            'editFlag': this.editFlag,
            'regionIds': this.selectedRegionIDs,
            'commodityIds': this.selectedCommodityIDs
        };

        // this.dashboardService.getLeadData(editData).subscribe(data => {
        //     this.leadData = data;
        //
        //     const selectedRegionNameTemp = [];
        //     const totalLeadsTemp = [];
        //     const rejectedLeadsTemp = [];
        //     const pendingLeadsTemp = [];
        //     const verifiedLeadsTemp = [];
        //     const regionIdTemp = [];
        //
        //     for (const x of this.leadData) {
        //         selectedRegionNameTemp.push(x.regionName);
        //     }
        //
        //     for (const x of this.leadData) {
        //         regionIdTemp.push(x.regionId);
        //     }
        //
        //     for (const x of this.leadData) {
        //         totalLeadsTemp.push(x.totalLeads);
        //     }
        //
        //     for (const x of this.leadData) {
        //         rejectedLeadsTemp.push(x.rejectedLeads);
        //     }
        //
        //     for (const x of this.leadData) {
        //         pendingLeadsTemp.push(x.pendingLead);
        //     }
        //
        //     for (const x of this.leadData) {
        //         verifiedLeadsTemp.push(x.verifiedLeads);
        //     }
        //
        //     const regionNameIdTemp = [];
        //
        //     for (const x of this.leadData) {
        //
        //         const regionIdNameListTemp = {
        //             id: x.regionId,
        //             name: x.regionName
        //         };
        //         regionNameIdTemp.push(regionIdNameListTemp);
        //     }
        //
        //     regionNameIdTemp.sort(function (a, b) {
        //         return a.name.localeCompare(b.name);
        //     });
        //
        //     const commodityNameIdTemp = [];
        //     const commodityName = this.leadData[0].commodityNames;
        //     const commodityId = this.leadData[0].commodityIds;
        //
        //     for (let i = 0; i < commodityName.length; i++) {
        //
        //         const commodityIdNameListTemp = {
        //             name: commodityName[i],
        //             id: commodityId[i]
        //         };
        //
        //         commodityNameIdTemp.push(commodityIdNameListTemp);
        //     }
        //
        //     commodityNameIdTemp.sort(function (a, b) {
        //         return a.name.localeCompare(b.name);
        //     });
        //
        //     if (this.editFlag !== 1) {
        //         this.regionIdNameList = regionNameIdTemp;
        //         this.commodityNameIdList = commodityNameIdTemp;
        //
        //     }
        //
        //     this.regionNameList = selectedRegionNameTemp;
        //     // this.changedRegionNameList = selectedRegionNameTemp;
        //     this.regionIdList = regionIdTemp;
        //     this.totalLeadsList = totalLeadsTemp;
        //     this.rejectedLeadsList = rejectedLeadsTemp;
        //     this.pendingLeadsList = pendingLeadsTemp;
        //     this.verifiedLeadsList = verifiedLeadsTemp;
        //
        //     this.finalVerifiedLead = this.verifiedLeadsList;
        //     this.finalRejectedLead = this.rejectedLeadsList;
        //     this.finalPendingLead = this.pendingLeadsList;
        //     this.finalRegionName = this.regionNameList;
        //
        //     if (this.editRegion === 1) {
        //         this.finalRegionName = this.changedRegionNameList;
        //         this.finalVerifiedLead = this.changedVerifiedLeadsList;
        //         this.finalRejectedLead = this.changedRejectedLeadsList;
        //         this.finalPendingLead = this.changedPendingLeadsList;
        //     }
        //
        //     this.selectedRegionIDs = this.regionIdNameList;
        //     this.selectedCommodityIDs = this.commodityNameIdList;
        //
        //     if (this.editFlag === 1) {
        //         this.selectedRegionIDs = this.changedRegionNameIdMap;
        //         this.selectedCommodityIDs = this.changedCommodityNameIdMap;
        //     }
        //
        //     const addOnLeadList = [200, 500, 300, 400, 300, 400, 900];
        //
        //     this.leadInformationChart = new Chart('leadInformationChart', {
        //         type: 'bar',
        //         data: {
        //             labels: this.finalRegionName,
        //             datasets: [
        //                 {
        //                     label: 'Verified Leads',
        //                     data: this.finalVerifiedLead,
        //                     backgroundColor: '#0f0098',
        //                     borderColor: '#1a02d5',
        //                     borderWidth: 1,
        //                     barPercentage: 0.8,
        //                     // order: 1
        //                 },
        //                 {
        //                     label: 'Pending Leads',
        //                     data: this.finalPendingLead,
        //                     backgroundColor: '#0066ff',
        //                     borderColor: '#2980ff',
        //                     borderWidth: 1,
        //                     barPercentage: 0.8,
        //                     // order: 2
        //                 },
        //                 {
        //                     label: 'Rejected Leads',
        //                     data: this.finalRejectedLead,
        //                     backgroundColor: '#4eb2ff',
        //                     borderColor: '#66bcff',
        //                     borderWidth: 1,
        //                     barPercentage: 0.8,
        //                     // order: 2
        //                 },
        //                 {
        //                     label: 'Add-ons',
        //                     data: addOnLeadList,
        //                     backgroundColor: '#ff25f3',
        //                     borderColor: '#ff4ef5',
        //                     borderWidth: 1,
        //                     barPercentage: 0.8,
        //                     // order: 2
        //                 }
        //                 // , {
        //                 //     type: 'line',
        //                 //     label: 'total leads',
        //                 //     data: [40, 5, 19, 22, 45, 25, 60],
        //                 //     borderColor: '#3e95cd',
        //                 //     fill: false,
        //                 //     //order: 1
        //                 // }
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

    changeLeadInformationChart(form: NgForm) {

        // if (form.invalid) {
        //     return;
        // }

        const selectedRegion = form.value.regionID;

        this.changedRegionNameIdMap = selectedRegion;

        this.changedRegionNameList = [];
        this.changedVerifiedLeadsList = [];
        this.changedRejectedLeadsList = [];
        this.changedPendingLeadsList = [];

        if (selectedRegion !== undefined) {
            for (const x of selectedRegion) {
                const index = this.regionNameList.indexOf(x.name);

                this.changedRegionNameList.push(this.regionNameList[index]);
                this.changedVerifiedLeadsList.push(this.verifiedLeadsList[index]);
                this.changedRejectedLeadsList.push(this.rejectedLeadsList[index]);
                this.changedPendingLeadsList.push(this.pendingLeadsList[index]);
            }

            this.selectedRegionIDs = [];
            for (const x of form.value.regionID) {
                this.selectedRegionIDs.push(x.id);
            }
            this.editFlag = 1;
        }

        this.changedCommodityNameIdMap = form.value.commodityID;

        if (form.value.commodityID !== undefined) {
            this.editFlag = 1;
            this.selectedCommodityIDs = [];

            for (const x of form.value.commodityID) {
                this.selectedCommodityIDs.push(x.id);
            }
        }

        this.leadInformationChart.destroy();

        this.ngOnInit();

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

import {Component, OnInit} from '@angular/core';
import {Chart} from 'chart.js';
import {FormBuilder, NgForm, NgModel} from '@angular/forms';
import {CustomTooltips} from '@coreui/coreui-plugin-chartjs-custom-tooltips';
import {DashboardService} from '../service/dashboard.service';

@Component({
    selector: 'app-dashboard-header',
    templateUrl: './dashboard-header.component.html',
    styleUrls: ['./dashboard-header.component.scss']
})
export class DashboardHeaderComponent implements OnInit {

    unverifiedLeadHeaderChart: any;
    unverifiedLeadAreaChart: any;
    verifiedLeadChart: any;
    verifiedLeadAreaChart: any;

    headerData: any;

    verifiedLeadAreaMap: any;
    verifiedLeadAreaCount: any;
    verifiedLeadAreaRegion: any;

    leadVerifiedList: any;
    leadVerifiedCount: any;
    leadVerifiedRegion: any;

    leadUnverifiedAreaList: any;
    leadUnverifiedAreaCount: any;
    leadUnverifiedAreaRegionName: any;
    UnverifiedLeadCount: any;
    UnverifiedLeadRegion: any;

    regionIdNameMap: any;

    selectedHeaderRegionIDs: any[];

    constructor(public dashboardService: DashboardService, public formBuilder: FormBuilder) {

    }

    ngOnInit(): void {
        this.loadHeaderChart(undefined, 0);

    }

    private loadHeaderChart(commodityIdList: any, editFlag: any) {

        const editData = {
            'editFlag': editFlag,
            'regionIds': commodityIdList,
        };

        // this.dashboardService.getHeaderChart(editData).subscribe(data => {
        //     this.headerData = data;
        //
        //     this.regionIdNameMap = data.allRegionList;
        //
        //     if (commodityIdList === undefined) {
        //         this.selectedHeaderRegionIDs = this.regionIdNameMap;
        //     }
        //
        //     const UnverifiedLeadCountTemp = [];
        //     const UnverifiedLeadRegionTemp = [];
        //
        //
        //     for (const x of data.leadUnverifiedList) {
        //         UnverifiedLeadCountTemp.push(x.count);
        //         UnverifiedLeadRegionTemp.push(x.name);
        //     }
        //
        //     this.UnverifiedLeadCount = UnverifiedLeadCountTemp;
        //     this.UnverifiedLeadRegion = UnverifiedLeadRegionTemp;
        //
        //
        //     this.unverifiedLeadHeaderChart = new Chart('unverifiedLeadHeaderChart', {
        //         type: 'bar',
        //         data: {
        //             labels: this.UnverifiedLeadRegion,
        //             datasets: [
        //                 {
        //                     label: 'Unverified Lead Count',
        //                     data: this.UnverifiedLeadCount,
        //                     backgroundColor: 'rgba(255,255,255,.3)',
        //                     borderWidth: 1,
        //                     barPercentage: 0.8,
        //                 }
        //             ]
        //         },
        //         options: {
        //             tooltips: {
        //                 enabled: false,
        //                 custom: CustomTooltips
        //             },
        //             maintainAspectRatio: false,
        //             scales: {
        //                 xAxes: [{
        //                     display: false,
        //                 }],
        //                 yAxes: [{
        //                     display: false
        //                 }]
        //             },
        //             legend: {
        //                 display: false
        //             }
        //         },
        //     });
        //
        //     /**
        //      * Generate Header Card Graphs of Area Registered In Past Days
        //      */
        //
        //     this.leadUnverifiedAreaList = data.leadUnverifiedAreaList;
        //
        //     const leadUnverifiedAreaCountTemp = [];
        //     const leadUnverifiedAreaNameTemp = [];
        //
        //     for (const x of this.leadUnverifiedAreaList) {
        //         leadUnverifiedAreaCountTemp.push(x.count);
        //         leadUnverifiedAreaNameTemp.push(x.name);
        //     }
        //
        //     this.leadUnverifiedAreaCount = leadUnverifiedAreaCountTemp;
        //     this.leadUnverifiedAreaRegionName = leadUnverifiedAreaNameTemp;
        //
        //     this.unverifiedLeadAreaChart = new Chart('unverifiedLeadAreaChart', {
        //         type: 'bar',
        //         data: {
        //             labels: this.leadUnverifiedAreaRegionName,
        //             datasets: [
        //                 {
        //                     label: 'Unverified Area Count',
        //                     data: this.leadUnverifiedAreaCount,
        //                     backgroundColor: 'rgba(255,255,255,.3)',
        //                     borderWidth: 1,
        //                     barPercentage: 0.8,
        //                 }
        //             ]
        //         },
        //         options: {
        //             tooltips: {
        //                 enabled: false,
        //                 custom: CustomTooltips
        //             },
        //             maintainAspectRatio: false,
        //             scales: {
        //                 xAxes: [{
        //                     display: false,
        //                 }],
        //                 yAxes: [{
        //                     display: false
        //                 }]
        //             },
        //             legend: {
        //                 display: false
        //             }
        //         },
        //     });
        //
        //     /**
        //      * Generate Header Card Graphs of Lead Collected In Past Days
        //      */
        //
        //     this.leadVerifiedList = data.leadVerifiedList;
        //
        //     const leadVerifiedCountTemp = [];
        //     const leadVerifiedRegionTemp = [];
        //
        //     for (const x of this.leadVerifiedList) {
        //         leadVerifiedCountTemp.push(x.count);
        //         leadVerifiedRegionTemp.push(x.name);
        //     }
        //
        //     this.leadVerifiedCount = leadVerifiedCountTemp;
        //     this.leadVerifiedRegion = leadVerifiedRegionTemp;
        //
        //     this.verifiedLeadChart = new Chart('verifiedLeadChart', {
        //         type: 'bar',
        //         data: {
        //             labels: this.leadVerifiedRegion,
        //             datasets: [
        //                 {
        //                     label: 'Verified Leads ',
        //                     data: this.leadVerifiedCount,
        //                     backgroundColor: 'rgba(255,255,255,.3)',
        //                     borderWidth: 1,
        //                     barPercentage: 0.8,
        //                 }
        //             ]
        //         },
        //         options: {
        //             tooltips: {
        //                 enabled: false,
        //                 custom: CustomTooltips
        //             },
        //             maintainAspectRatio: false,
        //             scales: {
        //                 xAxes: [{
        //                     display: false,
        //                 }],
        //                 yAxes: [{
        //                     display: false
        //                 }]
        //             },
        //             legend: {
        //                 display: false
        //             }
        //         },
        //     });
        //
        //     /**
        //      * Generate Header Card Graphs of Verified Lead Collected In Past Days
        //      */
        //
        //     this.verifiedLeadAreaMap = data.leadVerifiedCropAreaList;
        //
        //     const verifiedLeadAreaCountTemp = [];
        //     const verifiedLeadAreaRegionTemp = [];
        //
        //     for (const x of data.leadVerifiedCropAreaList) {
        //         verifiedLeadAreaCountTemp.push(x.count);
        //         verifiedLeadAreaRegionTemp.push(x.name);
        //     }
        //
        //     this.verifiedLeadAreaCount = verifiedLeadAreaCountTemp;
        //     this.verifiedLeadAreaRegion = verifiedLeadAreaRegionTemp;
        //
        //     this.verifiedLeadAreaChart = new Chart('verifiedLeadAreaChart', {
        //         type: 'bar',
        //         data: {
        //             labels: this.verifiedLeadAreaRegion,
        //             datasets: [
        //                 {
        //                     label: 'Verified Lead Area Registered ',
        //                     data: this.verifiedLeadAreaCount,
        //                     backgroundColor: 'rgba(255,255,255,.3)',
        //                     borderWidth: 1,
        //                     barPercentage: 0.8,
        //                 }
        //             ]
        //         },
        //         options: {
        //             tooltips: {
        //                 enabled: false,
        //                 custom: CustomTooltips
        //             },
        //             maintainAspectRatio: false,
        //             scales: {
        //                 xAxes: [{
        //                     display: false,
        //                 }],
        //                 yAxes: [{
        //                     display: false
        //                 }]
        //             },
        //             legend: {
        //                 display: false
        //             }
        //         },
        //     });
        // });
    }

    changeHeaderChart(form: NgForm) {

        const commodityIdList = [];

        for (const x of form.value.headerRegionID) {
            commodityIdList.push(x.id);
        }

        this.unverifiedLeadHeaderChart.destroy();
        this.unverifiedLeadAreaChart.destroy();
        this.verifiedLeadChart.destroy();
        this.verifiedLeadAreaChart.destroy();

        this.loadHeaderChart(commodityIdList, 1);

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

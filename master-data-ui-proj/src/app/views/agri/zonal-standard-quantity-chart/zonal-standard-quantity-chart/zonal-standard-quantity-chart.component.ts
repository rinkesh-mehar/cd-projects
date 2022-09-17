import { PageZonalStandardQuantityChart } from './../../models/PageZonalStandardQuantityChart';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import { ZonalStandardQuantityChartService } from '../../services/zonal-standard-quantity-chart.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {DrkServiceService} from '../../../services/drk-service.service';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../services/bulk-data.service';

@Component({
  selector: 'app-zonal-standard-quantity-chart',
  templateUrl: './zonal-standard-quantity-chart.component.html',
  styleUrls: ['./zonal-standard-quantity-chart.component.scss']
})
export class ZonalStandardQuantityChartComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  quantityChartStatus;
  StandardQuantityChartList: any = [];
  pageZonalStandardQuantityChart: PageZonalStandardQuantityChart;
  selectedPage: number = 1;
  maxSize: number = 10;
  searchText: any = "";
  isValid: number = 1;
  missing : any="";
  recordsPerPage: number = 10;
   records: any = [];

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageZonalStandardQuantityChart(0, this.isValid);
    //this.loadAllStandardQuantityChart();
    this.quantityChartStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public zonalStandardQuantityChartService: ZonalStandardQuantityChartService,
    private userRightsService: UserRightsService,
    private drkServiceService: DrkServiceService
  ) { }

  // StandardQuantityChart list
  loadAllStandardQuantityChart() {
    return this.zonalStandardQuantityChartService.GetAllStandardQuantityChart().subscribe((data: {}) => {
      this.StandardQuantityChartList = data;
    })
  }


  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : " + page);
    this.selectedPage = page;
    this.getPageZonalStandardQuantityChart(page, this.isValid);
  }

  getPageZonalStandardQuantityChart(page: number, isValid: number): void {
    this.zonalStandardQuantityChartService.getPageZonalStandardQuantityChart(page, this.recordsPerPage, this.searchText, isValid,this.missing)
      .subscribe(page => this.pageZonalStandardQuantityChart = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.zonalStandardQuantityChartService.getPageZonalStandardQuantityChart(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing)
      .subscribe(page => this.pageZonalStandardQuantityChart = page);
  }

  // // Delete StandardQuantityChart
  // deleteStandardQuantityChart(data){
  //   var index = index = this.StandardQuantityChartList.map(x => {return x.name}).indexOf(data.name);
  //    return this.agriStandardQuantityChartService.DeleteStandardQuantityChart(data.id).subscribe(res => {
  //     this.StandardQuantityChartList.splice(index, 1)
  //      console.log('StandardQuantityChart deleted!')
  //    })
  // }

  // Delete 
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg , data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg , data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg , data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        observable = this.zonalStandardQuantityChartService.ApproveStandardQuantityChart(event.id)
      } else if (event.flag == "finalize") {
        observable = this.zonalStandardQuantityChartService.FinalizeStandardQuantityChart(event.id)
      } else if (event.flag == "delete") {
        observable = this.zonalStandardQuantityChartService.DeleteStandardQuantityChart(event.id)
      } else if (event.flag == "reject") {
        observable = this.zonalStandardQuantityChartService.RejectStandardQuantityChart(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
            this.loadAllStandardQuantityChart();
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

  searchStandardQuantityChart() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageZonalStandardQuantityChart(this.selectedPage - 1, this.isValid);
  }

  modalSuccess($event: any) {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // this.ngOnInit();
    // this.selectedPage = 1;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getPageZonalStandardQuantityChart(this.selectedPage - 1,this.maxSize);
    this.quantityChartStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('agri_standard_quantity_chart').subscribe(res => {
      this.isSubmitted = true;
      if (res) {
        this.ngOnInit();
        this.isSuccess = res.success;
        if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
        }
      }
    });
  }

  onClickMissing(){
    this.missing = 1;
    this.getPageZonalStandardQuantityChart(0,this.isValid);
  }

  moveToMaster(id){
    this.zonalStandardQuantityChartService.moveToMaster(id).subscribe(res => {
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
    this.missing = 0;
    this.ngOnInit();
  }

  sortData(sort: Sort) {
    const data = this.pageZonalStandardQuantityChart.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageZonalStandardQuantityChart.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageZonalStandardQuantityChart.content = data.sort((firstValue, secondValue) => {

      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'state':
          return compare(firstValue.state, secondValue.state, isAsc);
        case 'acz':
          return compare(firstValue.acz, secondValue.acz, isAsc);
        case 'zonalCommodity':
          return compare(firstValue.zonalCommodity, secondValue.zonalCommodity, isAsc);
        case 'zonalVariety':
          return compare(firstValue.zonalVariety, secondValue.zonalVariety, isAsc);
        case 'standardQuantityPerAcre':
          return compare(firstValue.standardQuantityPerAcre, secondValue.standardQuantityPerAcre, isAsc);
        case 'standardPositiveVariancePercent':
          return compare(firstValue.standardPositiveVariancePercent, secondValue.standardPositiveVariancePercent, isAsc);
        case 'standardPositiveVariancePerAcre':
          return compare(firstValue.standardPositiveVariancePerAcre, secondValue.standardPositiveVariancePerAcre, isAsc);
        case 'standardNegativeVariancePercent':
          return compare(firstValue.standardNegativeVariancePercent, secondValue.standardNegativeVariancePercent, isAsc);
        case 'standardNegativeVariancePerAcre':
          return compare(firstValue.standardNegativeVariancePerAcre, secondValue.standardNegativeVariancePerAcre, isAsc);
        case globalConstants.STATUS:
          return compare(firstValue.status, secondValue.status, isAsc);
        default:
          return 0;
      }
    });
  }
  bulkData(key,tableName){

    let Values = []
    let getValue = document.querySelectorAll<HTMLInputElement>('table tbody input:checked')
   
    getValue.forEach(function(data,i){
      Values.push(data.value)
    })
    let AllData = {status:key, tableName:tableName, ids:Values.toString()}

    this.bulkDatas.getData(AllData)
        .subscribe( data => {
          data
          if(data.success == true){
            this.successModal.showModal('SUCCESS', data.message, '');

          }else {
            this.errorModal.showModal('ERROR', data.error, '');

          }

        })

  }
}

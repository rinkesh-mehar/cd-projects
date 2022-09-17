import { Component, OnInit, ViewChild } from '@angular/core';

import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import { AgriQuantityLossChartService } from '../../services/agri-quantity-loss-chart.service';
import { PageAgriQuantityLossChart } from '../../models/PageAgriQuantityLossChart';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {DrkServiceService} from '../../../services/drk-service.service';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../services/bulk-data.service';


@Component({
  selector: 'app-agri-quantity-loss-chart',
  templateUrl: './agri-quantity-loss-chart.component.html',
  styleUrls: ['./agri-quantity-loss-chart.component.scss']
})
export class AgriQuantityLossChartComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  quantityLossChartStatus;
  QuantityLossChartList: any = [];

  pageAgriQuantityLossChart: PageAgriQuantityLossChart;
  selectedPage: number = 1;
  maxSize: number = 10;
  searchText: any = "";
  isValid: number = 1;
  missing : any="";

  recordsPerPage: number = 10;
   records: any = [];



  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageAgriQuantityLossChart(0, this.isValid);
    //  this.loadAllQuantityLossChart();
    this.quantityLossChartStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public agriQuantityLossChartService: AgriQuantityLossChartService,
    private userRightsService: UserRightsService,
    private drkServiceService: DrkServiceService
  ) { }

  // QuantityLossChart list
  loadAllQuantityLossChart() {
    return this.agriQuantityLossChartService.GetAllQuantityLossChart().subscribe((data: {}) => {
      this.QuantityLossChartList = data;
    })
  }


  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : " + page);
    this.selectedPage = page;
    this.getPageAgriQuantityLossChart(page, this.isValid);
  }

  getPageAgriQuantityLossChart(page: number, isValid: number): void {
    this.agriQuantityLossChartService.getPageAgriQuantityLossChart(page, this.recordsPerPage, this.searchText, isValid, this.missing)
      .subscribe(page => this.pageAgriQuantityLossChart = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriQuantityLossChartService.getPageAgriQuantityLossChart(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid, this.missing)
      .subscribe(page => this.pageAgriQuantityLossChart = page);
  }

  // // Delete QuantityLossChart
  // deleteQuantityLossChart(data){
  //   var index = index = this.QuantityLossChartList.map(x => {return x.name}).indexOf(data.name);
  //    return this.agriQuantityLossChartService.DeleteQuantityLossChart(data.id).subscribe(res => {
  //     this.QuantityLossChartList.splice(index, 1)
  //      console.log('QuantityLossChart deleted!')
  //    })
  // }

  // Delete 
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.commodity, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.commodity, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.commodity, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.commodity, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        observable = this.agriQuantityLossChartService.ApproveQuantityLossChart(event.id)
      } else if (event.flag == "finalize") {
        observable = this.agriQuantityLossChartService.FinalizeQuantityLossChart(event.id)
      } else if (event.flag == "delete") {
        observable = this.agriQuantityLossChartService.DeleteQuantityLossChart(event.id)
      } else if (event.flag == "reject") {
        observable = this.agriQuantityLossChartService.RejectQuantityLossChart(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
            this.loadAllQuantityLossChart();
            this.successModal.showModal('SUCCESS', res.message, '');
          } else {
            this.errorModal.showModal('ERROR', res.error, '');
          }
        }
      }, err => {
        this.errorModal.showModal('ERROR', err.error, '');
      })
    }
  }

  searchQuantityLossChart() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageAgriQuantityLossChart(this.selectedPage - 1, this.isValid);
  }

  modalSuccess($event: any) {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // this.ngOnInit();
    // this.selectedPage = 1;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getPageAgriQuantityLossChart(this.selectedPage - 1,this.isValid);
    this.quantityLossChartStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('agri_quantity_loss_chart').subscribe(res => {
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
    this.getPageAgriQuantityLossChart(0,this.isValid);
  }

  moveToMaster(id){
    this.agriQuantityLossChartService.moveToMaster(id).subscribe(res => {
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
    const data = this.pageAgriQuantityLossChart.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriQuantityLossChart.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageAgriQuantityLossChart.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case 'commodity':
            return compare(firstValue.commodity, secondValue.commodity, isAsc);
        case 'phenophase':
          return compare(firstValue.phenophase, secondValue.phenophase, isAsc);
        case 'stress':
          return compare(firstValue.stress, secondValue.stress, isAsc);
        case 'minQuantityCorrectionPercent':
          return compare(firstValue.minQuantityCorrectionPercent, secondValue.minQuantityCorrectionPercent, isAsc);
        case 'maxQuantityCorrectionPercent':
          return compare(firstValue.maxQuantityCorrectionPercent, secondValue.maxQuantityCorrectionPercent, isAsc);
          case 'maxBandValue':
            return compare(firstValue.maxBandValue, secondValue.maxBandValue, isAsc);
          case 'minBandValue':
          return compare(firstValue.minBandValue, secondValue.minBandValue, isAsc);
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

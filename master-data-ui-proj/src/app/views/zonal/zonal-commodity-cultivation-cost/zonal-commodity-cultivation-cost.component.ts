import { Sort } from '@angular/material';
import { UserRightsService } from './../../services/user-rights.service';
import { ZonalCommodityCultivationCostService } from './../services/zonal-commodity-cultivation-cost.service';
import { BulkDataService } from './../../agri/services/bulk-data.service';
import { globalConstants } from './../../global/globalConstants';
import { ErrorModalComponent } from './../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from './../../global/success-modal/success-modal.component';
import { ConfirmationMadalComponent } from './../../global/confirmation-madal/confirmation-madal.component';
import { Component, OnInit, ViewChild } from '@angular/core';
import { PageZonalCommodityCultivationCost } from '../models/PageZonalCommodityCultivationCost';

@Component({
  selector: 'app-zonal-commodity-cultivation-cost',
  templateUrl: './zonal-commodity-cultivation-cost.component.html',
  styleUrls: ['./zonal-commodity-cultivation-cost.component.scss']
})
export class ZonalCommodityCultivationCostComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  cultivationCostStatus;
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  StressList: any = [];

  pageZonalCommodityCultivationCost : PageZonalCommodityCultivationCost
  selectedPage: number = 1;
  maxSize = 10;
  searchText: any="";

  recordsPerPage: number = 10;
  records: any = [];

  constructor(public bulkDatas: BulkDataService,
    public zonalCommodityCultivationCostService: ZonalCommodityCultivationCostService,
    private userRightsService: UserRightsService) { }

  ngOnInit(): void {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageZonalCommodityCultivationCost(0);
    this.cultivationCostStatus = globalConstants;
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

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    this.getPageZonalCommodityCultivationCost(page);
  }
  getPageZonalCommodityCultivationCost(page: number): void {
    this.zonalCommodityCultivationCostService.getPageZonalCommodityCultivationCost(page, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageZonalCommodityCultivationCost = page)
  }
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.zonalCommodityCultivationCostService.getPageZonalCommodityCultivationCost(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageZonalCommodityCultivationCost = page);
  }

  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg, data);
  }

  sortData(sort: Sort) {
    const data = this.pageZonalCommodityCultivationCost.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageZonalCommodityCultivationCost.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageZonalCommodityCultivationCost.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'zonalCommodity':
          return compare(firstValue.zonalCommodity, secondValue.zonalCommodity, isAsc);
        case 'stateName':
          return compare(firstValue.stateName, secondValue.stateName, isAsc);
        case 'acz':
          return compare(firstValue.acz, secondValue.acz, isAsc);       
        case 'costOfCultivation':
          return compare(firstValue.costOfCultivation, secondValue.costOfCultivation, isAsc);
        case 'costOfProduction':
          return compare(firstValue.costOfProduction, secondValue.costOfProduction, isAsc);
        case globalConstants.STATUS:
          return compare(firstValue.status, secondValue.status, isAsc);
        default:
          return 0;
      }
    });
  }

  searchCultivationCost() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageZonalCommodityCultivationCost(this.selectedPage - 1);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'approve') {
        observable = this.zonalCommodityCultivationCostService.ApproveCultivationCost(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.zonalCommodityCultivationCostService.FinalizeCultivationCost(event.id);
      } else if (event.flag == 'delete') {
        observable = this.zonalCommodityCultivationCostService.DeleteCultivationCost(event.id);
      } else if (event.flag == 'reject') {
        observable = this.zonalCommodityCultivationCostService.RejectCultivationCost(event.id);
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

  modalSuccess($event: any) {
    if (this.selectedPage > 0){

      this.onSelect((this.selectedPage - 1));
      (document.querySelector('thead th input') as HTMLInputElement).checked = false
    } else {
      this.onSelect(this.selectedPage);
      (document.querySelector('thead th input') as HTMLInputElement).checked = false
    }
  }
}

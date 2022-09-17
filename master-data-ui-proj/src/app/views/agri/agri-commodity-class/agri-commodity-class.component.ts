import { PageAgriCommodityClass } from './../models/PageAgriCommodityClass';
import { Component, OnInit, NgZone, ViewChild } from '@angular/core';
import { AgriCommodityClassService } from '../services/agri-commodity-class.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { globalConstants } from '../../global/globalConstants';
import { UserRightsService } from '../../services/user-rights.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {Sort} from '@angular/material';
import { BulkDataService } from '../services/bulk-data.service';

@Component({
  selector: 'app-agri-commodity-class',
  templateUrl: './agri-commodity-class.component.html',
  styleUrls: ['./agri-commodity-class.component.scss']
})
export class AgriCommodityClassComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  commodityClassStatus;
  CommodityClassList: any = [];

  searchText: any = '';
   selectedPage: number = 1;
   maxSize =10;
   recordsPerPage: number = 10;
   records: any = [];

   pageAgriCommodityClass: PageAgriCommodityClass;



  ngOnInit() {
    // this.loadAllCommodityClass();
    this.records = ['20', '50', '100', '200', '250'];
   this.getCommodityClassPagenatedList(0);
    this.commodityClassStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public agriCommodityClassService: AgriCommodityClassService,
    private userRightsService: UserRightsService,
  ){ }

   // CommodityClass list
   loadAllCommodityClass() {
    return this.agriCommodityClassService.GetAllCommodityClass().subscribe((data: {}) => {
      this.CommodityClassList = data;
    })
  }

  getCommodityClassPagenatedList(page: number): void {
    this.agriCommodityClassService.getCommodityClassPagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageAgriCommodityClass = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriCommodityClassService.getCommodityClassPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageAgriCommodityClass = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;      
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getCommodityClassPagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getCommodityClassPagenatedList(this.selectedPage - 1);
}

    // // Delete CommodityClass
    // deleteCommodityClass(data){
    //   var index = index = this.CommodityClassList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriCommodityClassService.DeleteCommodityClass(data.id).subscribe(res => {
    //     this.CommodityClassList.splice(index, 1)
    //      console.log('CommodityClass deleted!')
    //    })
    // }

      // Delete Commodity
  delete(data, i) {
    data.index = i;
    data.flag = 'delete';
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + ' ' + data.name, data);
  }

  // Reject
  reject(data, i) {
    data.index = i;
    data.flag = 'reject';
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + ' ' + data.name, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = 'approve';
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + ' ' + data.name, data);
  }

  finalize(data, i) {
    data.index = i;
    data.flag = 'finalize';
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + ' ' + data.name, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'approve') {
        observable = this.agriCommodityClassService.ApproveCommodityClass(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.agriCommodityClassService.FinalizeCommodityClass(event.id);
      } else if (event.flag == 'delete') {
        observable = this.agriCommodityClassService.DeleteCommodityClass(event.id);
      } else if (event.flag == 'reject') {
        observable = this.agriCommodityClassService.RejectCommodityClass(event.id);
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

  sortData(sort: Sort) {
    const data = this.pageAgriCommodityClass.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriCommodityClass.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageAgriCommodityClass.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case globalConstants.NAME:
          return compare(firstValue.name, secondValue.name, isAsc);
        case globalConstants.STATUS:
          return compare(firstValue.status, secondValue.status, isAsc);
        default:
          return 0;
      }
    });
  }


  modalSuccess($event: any) {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // this.ngOnInit();
    // this.selectedPage = 1;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getCommodityClassPagenatedList(this.selectedPage - 1);
    this.commodityClassStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
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

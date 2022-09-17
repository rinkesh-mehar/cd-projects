import { Sort } from '@angular/material';
import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriGeneralCommodityService } from '../services/agri-general-commodity.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { globalConstants } from '../../global/globalConstants';
import { UserRightsService } from '../../services/user-rights.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { BulkDataService } from '../services/bulk-data.service';
import { PageAgriGeneralCommodity } from '../models/PageAgriGeneralCommodity';

@Component({
  selector: 'app-agri-general-commodity',
  templateUrl: './agri-general-commodity.component.html',
  styleUrls: ['./agri-general-commodity.component.scss']
})
export class AgriGeneralCommodityComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  generalCommodityStatus;
  GeneralCommodityList: any = [];


  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];

  pageAgriGeneralCommodity: PageAgriGeneralCommodity;


  ngOnInit() {
    // this.loadAllGeneralCommodity();
    this.records = ['20', '50', '100', '200', '250'];
   this.getGeneralCommodityPagenatedList(0);
    this.generalCommodityStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public agriGeneralCommodityService: AgriGeneralCommodityService,
    private userRightsService: UserRightsService,
  ){ }

   // GeneralCommodity list
   loadAllGeneralCommodity() {
    return this.agriGeneralCommodityService.GetAllGeneralCommodity().subscribe((data: {}) => {
      this.GeneralCommodityList = data;
    })
  }

  getGeneralCommodityPagenatedList(page: number): void {
    this.agriGeneralCommodityService.getGeneralCommodityPagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageAgriGeneralCommodity = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriGeneralCommodityService.getGeneralCommodityPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageAgriGeneralCommodity = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getGeneralCommodityPagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getGeneralCommodityPagenatedList(this.selectedPage - 1);
}

    // // Delete GeneralCommodity
    // deleteGeneralCommodity(data){
    //   var index = index = this.GeneralCommodityList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriGeneralCommodityService.DeleteGeneralCommodity(data.id).subscribe(res => {
    //     this.GeneralCommodityList.splice(index, 1)
    //      console.log('GeneralCommodity deleted!')
    //    })
    // }

          // Delete Commodity
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.name, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.name, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.name, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.name, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        observable = this.agriGeneralCommodityService.ApproveGeneralCommodity(event.id)
      } else if (event.flag == "finalize") {
        observable = this.agriGeneralCommodityService.FinalizeGeneralCommodity(event.id)
      } else if (event.flag == "delete") {
        observable = this.agriGeneralCommodityService.DeleteGeneralCommodity(event.id)
      } else if (event.flag == "reject") {
        observable = this.agriGeneralCommodityService.RejectGeneralCommodity(event.id)
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
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // this.ngOnInit();
    // this.selectedPage = 1;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getGeneralCommodityPagenatedList(this.selectedPage - 1);
    this.generalCommodityStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }

  sortData(sort: Sort) {
    const data = this.pageAgriGeneralCommodity.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriGeneralCommodity.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageAgriGeneralCommodity.content = data.sort((firstValue, secondValue) => {
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

import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { PageAgriCommodity } from '../models/PageAgriCommodity';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { globalConstants } from '../../global/globalConstants'
import { UserRightsService } from '../../services/user-rights.service';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { BulkDataService } from '../services/bulk-data.service';
import { AgriCommodityGroupService } from '../../commodity/service/agri-commodity-group.service';
@Component({
  selector: 'app-agri-commodity',
  templateUrl: './agri-commodity.component.html',
  styleUrls: ['./agri-commodity.component.scss']
})
export class AgriCommodityComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  CommodityList: any = [];
  pageCommodity: PageAgriCommodity;
  selected : number = 1;
  selectedPage: number = 1;
  maxSize = 10;
  searchText: any="";
  commodityStatus;
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  logoUrl : string = '';
  recordsPerPage: number = 10;
   records: any = [];



  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageAgriCommodity(0);
    // this.loadAllCommodities();
    this.commodityStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public agriCommodityService: AgriCommodityService,
    private userRightsService: UserRightsService,

  ) { }

  // Commodity list
  loadAllCommodities() {
    return this.agriCommodityService.GetAllCommoditise().subscribe((data: {}) => {
      this.CommodityList = data;
    })
  }


  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : "+page);
    this.selectedPage=page;
    this.getPageAgriCommodity(page);
  }

  getPageAgriCommodity(page:number): void {
    this.agriCommodityService.getPageAgriCommodity(page, this.recordsPerPage,this.searchText)
        .subscribe(page => this.pageCommodity = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriCommodityService.getPageAgriCommodity(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageCommodity = page);
  }

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
        observable = this.agriCommodityService.ApproveCommodity(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.agriCommodityService.FinalizeCommodity(event.id);
      } else if (event.flag == 'delete') {
        observable = this.agriCommodityService.DeleteCommodity(event.id);
      } else if (event.flag == 'reject') {
        observable = this.agriCommodityService.RejectCommodity(event.id);
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

  searchCommodity() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageAgriCommodity(this.selectedPage - 1);
  }

  // ///----------------------

  // approveCommodity(event: any) {
  //   return this.agriCommodityService.ApproveCommodity(event.id)
  // }
  // finalizeCommodity(event: any) {
  //   return this.agriCommodityService.FinalizeCommodity(event.id)
  // }

  // deleteCommodity(event) {
  //   return this.agriCommodityService.DeleteCommodity(event.id)
  // }

  // rejectCommodity(event: any) {
  //   return this.agriCommodityService.RejectCommodity(event.id)
  // }
  // ///------------------------

  sortData(sort: Sort) {
    const data = this.pageCommodity.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageCommodity.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageCommodity.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case globalConstants.NAME:
          return compare(firstValue.name, secondValue.name, isAsc);
        case 'scientificName':
          return compare(firstValue.scientificName, secondValue.scientificName, isAsc);
          case 'commodityGroup':
          return compare(firstValue.commodityGroup, secondValue.commodityGroup, isAsc);
          case 'commodityGroupIndex':
          return compare(firstValue.commodityGroupIndex, secondValue.commodityGroupIndex, isAsc);
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
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      console.log("Inside if");
    this.getPageAgriCommodity(this.selectedPage - 1);
    this.commodityStatus = globalConstants;
    }else{
      console.log("Inside else");
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


  getLogoUrl(event: any){
    this.logoUrl = event.target.src;
    console.log("image found..." + this.logoUrl);
  }

}

import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriSeedSourceService } from '../services/agri-seed-source.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../services/bulk-data.service';
import { PageAgriSeedSource } from '../models/PageAgriSeedSource';

@Component({
  selector: 'app-agri-seed-source',
  templateUrl: './agri-seed-source.component.html',
  styleUrls: ['./agri-seed-source.component.scss']
})
export class AgriSeedSourceComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  seedSourceStatus;
  SeedSourceList: any = [];

  searchText: any = '';
   selectedPage: number = 1;
   maxSize =10;
   recordsPerPage: number = 10;
   records: any = [];

   pageAgriSeedSource: PageAgriSeedSource;


  ngOnInit() {
    // this.loadAllSeedSource();
    this.records = ['2', '50', '100', '200', '250'];
   this.getSeedSourcePagenatedList(0);
    this.seedSourceStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,private userRightsService: UserRightsService,
    public agriSeedSourceService: AgriSeedSourceService
  ) { }

  getSeedSourcePagenatedList(page: number): void {
    this.agriSeedSourceService.getSeedSourcePagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageAgriSeedSource = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriSeedSourceService.getSeedSourcePagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageAgriSeedSource = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;

    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getSeedSourcePagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getSeedSourcePagenatedList(this.selectedPage - 1);
}

  // SeedSource list
  loadAllSeedSource() {
    return this.agriSeedSourceService.GetAllSeedSource().subscribe((data: {}) => {
      this.SeedSourceList = data;
    })
  }

  // Delete SeedSource
  // deleteSeedSource(data){
  //   var index = index = this.SeedSourceList.map(x => {return x.name}).indexOf(data.name);
  //    return this.agriSeedSourceService.DeleteSeedSource(data.id).subscribe(res => {
  //     this.SeedSourceList.splice(index, 1)
  //      console.log('SeedSource deleted!')
  //    })
  // }


  // Delete SeedSource
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
        observable = this.agriSeedSourceService.ApproveSeedSource(event.id)
      } else if (event.flag == "finalize") {
        observable = this.agriSeedSourceService.FinalizeSeedSource(event.id)
      } else if (event.flag == "delete") {
        observable = this.agriSeedSourceService.DeleteSeedSource(event.id)
      } else if (event.flag == "reject") {
        observable = this.agriSeedSourceService.RejectSeedSource(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            // this._statusMsg = res.message;
            this.loadAllSeedSource();
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
    this.getSeedSourcePagenatedList(this.selectedPage - 1);
    this.seedSourceStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }

  sortData(sort: Sort) {
    const data = this.pageAgriSeedSource.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriSeedSource.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageAgriSeedSource.content = data.sort((firstValue, secondValue) => {
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

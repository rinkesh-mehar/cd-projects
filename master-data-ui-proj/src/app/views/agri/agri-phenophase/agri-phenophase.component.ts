import { PageAgriPhenophase } from './../models/PageAgriPhenophase';
import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriPhenophaseService } from '../services/agri-phenophase.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { BulkDataService } from '../services/bulk-data.service';

@Component({
  selector: 'app-agri-phenophase',
  templateUrl: './agri-phenophase.component.html',
  styleUrls: ['./agri-phenophase.component.scss']
})
export class AgriPhenophaseComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  phenophaseStatus;
  PhenophaseList: any = [];

  searchText: any = '';
   selectedPage: number = 1;
   maxSize =10;
   recordsPerPage: number = 10;
   records: any = [];

   pageAgriPhenophase: PageAgriPhenophase;


  ngOnInit() {
    // this.loadAllPhenophases();
    this.records = ['20', '50', '100', '200', '250'];
	   this.getPhenophasePagenatedList(0);
   
    this.phenophaseStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public agriPhenophaseService: AgriPhenophaseService,
    private userRightsService: UserRightsService,
  ){ }


  getPhenophasePagenatedList(page: number): void {
    this.agriPhenophaseService.getPhenophasePagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageAgriPhenophase = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriPhenophaseService.getPhenophasePagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageAgriPhenophase = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPhenophasePagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getPhenophasePagenatedList(this.selectedPage - 1);
}

   // Phenophase list
   loadAllPhenophases() {
    return this.agriPhenophaseService.GetAllPhenophase().subscribe((data: {}) => {
      this.PhenophaseList = data;
    })
  }

    // // Delete Phenophase
    // deletePhenophase(data){
    //   var index = index = this.PhenophaseList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriPhenophaseService.DeletePhenophase(data.id).subscribe(res => {
    //     this.PhenophaseList.splice(index, 1)
    //      console.log('Phenophase deleted!')
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
        observable = this.agriPhenophaseService.ApprovePhenophase(event.id)
      } else if (event.flag == "finalize") {
        observable = this.agriPhenophaseService.FinalizePhenophase(event.id)
      } else if (event.flag == "delete") {
        observable = this.agriPhenophaseService.DeletePhenophase(event.id)
      } else if (event.flag == "reject") {
        observable = this.agriPhenophaseService.RejectPhenophase(event.id)
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
    const data = this.pageAgriPhenophase.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriPhenophase.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageAgriPhenophase.content = data.sort((firstValue, secondValue) => {
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
  console.log("page : " + this.selectedPage);
  if(this.selectedPage >= 2){
    // console.log("Inside if");
  this.getPhenophasePagenatedList(this.selectedPage - 1);
  this.phenophaseStatus = globalConstants;
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

import { PageGeneralCallingStatus } from './../models/PageGeneralCallingStatus';
import { Component, OnInit, ViewChild } from '@angular/core';
import { GeneralCallingStatusService } from '../services/general-calling-status.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { BulkDataService } from '../../agri/services/bulk-data.service';

@Component({
  selector: 'app-general-calling-status',
  templateUrl: './general-calling-status.component.html',
  styleUrls: ['./general-calling-status.component.scss']
})
export class GeneralCallingStatusComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
   isSuccess: boolean = false;
   _statusMsg: string;
  callingStatusStatus;
  CallingStatusList: any = [];

  searchText: any = '';
   selectedPage: number = 1;
   maxSize =10;
   recordsPerPage: number = 10;
   records: any = [];

   pageGeneralCallingStatus: PageGeneralCallingStatus;


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getCallingStatusPagenatedList(0);
    // this.loadAllCallingStatus();
    this.callingStatusStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public generalCallingStatusService: GeneralCallingStatusService,
    private userRightsService: UserRightsService,
  ) { }

  getCallingStatusPagenatedList(page: number): void {
    this.generalCallingStatusService.getCallingStatusPagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageGeneralCallingStatus = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.generalCallingStatusService.getCallingStatusPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageGeneralCallingStatus = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getCallingStatusPagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getCallingStatusPagenatedList(this.selectedPage - 1);
}

  // Calling Status list
  loadAllCallingStatus() {
    return this.generalCallingStatusService.GetAllCallingStatus().subscribe((data: {}) => {
      this.CallingStatusList = data;
    })
  }

  // // Delete Calling Status
  // deleteCallingStatus(data) {
  //   var index = index = this.CallingStatusList.map(x => { return x.name }).indexOf(data.name);
  //   return this.generalCallingStatusService.DeleteCallingStatus(data.id).subscribe(res => {
  //     this.CallingStatusList.splice(index, 1)
  //     console.log('Calling Status deleted!')
  //   })
  // }

  // Delete
  delete(data, i) {
    data.index = i;
    data.flag = 'delete';
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + ' ' + data.callingStatus, data);
  }

  // Reject
  reject(data, i) {
    data.index = i;
    data.flag = 'reject';
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + ' ' + data.callingStatus, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = 'approve';
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + ' ' + data.callingStatus, data);
  }

  finalize(data, i) {
    data.index = i;
    data.flag = 'finalize';
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + ' ' + data.callingStatus, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'approve') {
        observable = this.generalCallingStatusService.ApproveCallingStatus(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.generalCallingStatusService.FinalizeCallingStatus(event.id);
      } else if (event.flag == 'delete') {
        observable = this.generalCallingStatusService.DeleteCallingStatus(event.id);
      } else if (event.flag == 'reject') {
        observable = this.generalCallingStatusService.RejectCallingStatus(event.id);
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
    const data = this.pageGeneralCallingStatus.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageGeneralCallingStatus.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageGeneralCallingStatus.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'callingStatus':
          return compare(firstValue.callingStatus, secondValue.callingStatus, isAsc);
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
    this.getCallingStatusPagenatedList(this.selectedPage - 1);
    this.callingStatusStatus = globalConstants;
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

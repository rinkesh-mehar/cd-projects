import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../agri/services/bulk-data.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { globalConstants } from '../../global/globalConstants';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { PageAgriStressStage } from '../model/PageAgriStressStage';
import { AgriStressStageService } from '../services/agri-stress-stage.service';

@Component({
  selector: 'app-agri-stress-stage',
  templateUrl: './agri-stress-stage.component.html',
  styleUrls: ['./agri-stress-stage.component.scss']
})
export class AgriStressStageComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  pageAgriStressStage: PageAgriStressStage;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  stressStageStatus;

  recordsPerPage: number = 10;
  records: any = [];
  

  constructor(private agriStressStageService: AgriStressStageService, private userRightsService: UserRightsService,public bulkDatas: BulkDataService) { }

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageStressStageList(0);
    this.stressStageStatus = globalConstants;
  }

  getPageStressStageList(page: number): void {
    this.agriStressStageService.getPageStressStageList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageAgriStressStage = page);
}

loadData(event: any) {
  console.log('pages ', event.target.value);
  this.recordsPerPage = event.target.value || 10;
  this.agriStressStageService.getPageStressStageList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    .subscribe(page => this.pageAgriStressStage = page);
}

onSelect(page: number): void {
  (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPageStressStageList(page);
}

searchStressStage() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageStressStageList(this.selectedPage - 1);
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

modalConfirmation(event) {
  console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'approve') {
        observable = this.agriStressStageService.approveStressStage(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.agriStressStageService.finalizeStressStage(event.id);
      } else if (event.flag == 'reject') {
        observable = this.agriStressStageService.rejectStressStage(event.id);
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
  const data = this.pageAgriStressStage.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageAgriStressStage.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageAgriStressStage.content = data.sort((firstValue, secondValue) => {
    const isAsc = sort.direction == 'asc';
    switch (sort.active) {
      case globalConstants.ID:
        return compare(+firstValue.id, +secondValue.id, isAsc);
      case 'stress':
        return compare(firstValue.stress, secondValue.stress, isAsc);
        case 'stage':
        return compare(firstValue.stage, secondValue.stage, isAsc);
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

  // console.log("page : " + this.selectedPage);
  if(this.selectedPage >= 2){
    // console.log("Inside if");
  this.getPageStressStageList(this.selectedPage - 1);
  this.stressStageStatus = globalConstants;
  }else{
    // console.log("Inside else");
  this.ngOnInit();
  }
}

}

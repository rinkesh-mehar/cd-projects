import { PageGeneralPackagingType } from './../models/PageGeneralPackagingType';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { GeneralPackagingTypeService } from '../services/general-packaging-type.service';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../agri/services/bulk-data.service';

@Component({
  selector: 'app-general-packaging-type',
  templateUrl: './general-packaging-type.component.html',
  styleUrls: ['./general-packaging-type.component.scss']
})
export class GeneralPackagingTypeComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  pageGeneralPackagingType: PageGeneralPackagingType;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  packagingTypeStatus;

  recordsPerPage: number = 10;
  records: any = [];
  

  constructor(private generalPackagingTypeService: GeneralPackagingTypeService, private userRightsService: UserRightsService,public bulkDatas: BulkDataService) { }

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPackagingTypePagenatedList(0);
    this.packagingTypeStatus = globalConstants;
  }

  getPackagingTypePagenatedList(page: number): void {
    this.generalPackagingTypeService.getPackagingTypePagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageGeneralPackagingType = page);
}

loadData(event: any) {
  console.log('pages ', event.target.value);
  this.recordsPerPage = event.target.value || 10;
  this.generalPackagingTypeService.getPackagingTypePagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    .subscribe(page => this.pageGeneralPackagingType = page);
}

onSelect(page: number): void {
  (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
  this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPackagingTypePagenatedList(page);
}

searchQualityParameter() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPackagingTypePagenatedList(this.selectedPage - 1);
}

 // Reject 
 reject(data, i) {
  data.index = i;
  data.flag = "reject"
  this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + '"' + data.name + '"', data);
}

approve(data, i) {
  data.index = i;
  data.flag = "approve"
  this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + '"' + data.name + '"', data);
}

finalize(data, i) {
  data.index = i;
  data.flag = "finalize"
  this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + '"' + data.name + '"', data);
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
        observable = this.generalPackagingTypeService.approvePackagingType(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.generalPackagingTypeService.finalizePackagingType(event.id);
      } else if (event.flag == 'reject') {
        observable = this.generalPackagingTypeService.rejectPackagingType(event.id);
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
  const data = this.pageGeneralPackagingType.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageGeneralPackagingType.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageGeneralPackagingType.content = data.sort((firstValue, secondValue) => {
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
  this.getPackagingTypePagenatedList(this.selectedPage - 1);
  this.packagingTypeStatus = globalConstants;
  }else{
    // console.log("Inside else");
  this.ngOnInit();
  }
}

}

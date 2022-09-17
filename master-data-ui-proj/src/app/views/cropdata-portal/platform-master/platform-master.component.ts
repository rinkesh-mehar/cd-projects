import { PlatformMasterService } from './../services/platform-master.service';
import { PagePlatformMaster } from './../models/page-platform-master';
import { ConfirmationMadalComponent } from './../../global/confirmation-madal/confirmation-madal.component';
import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { globalConstants } from '../../global/globalConstants';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { Sort } from '@angular/material';

@Component({
  selector: 'app-platform-master',
  templateUrl: './platform-master.component.html',
  styleUrls: ['./platform-master.component.scss']
})
export class PlatformMasterComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  pagePlatformMaster: PagePlatformMaster;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  platformStatus;
  engineList: any = [];

  recordsPerPage: number = 10;
  records: any = [];

  logoUrl : string = '';

  constructor(private platformMasterService: PlatformMasterService, private userRightsService: UserRightsService) { }

ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPagePlatformList(0);
    this.platformStatus = globalConstants;
  }

  getPagePlatformList(page: number): void {
    this.platformMasterService.getPagePlatformList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pagePlatformMaster = page);
}

loadData(event: any) {
  console.log('pages ', event.target.value);
  this.recordsPerPage = event.target.value || 10;
  this.platformMasterService.getPagePlatformList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    .subscribe(page => this.pagePlatformMaster = page);
}

onSelect(page: number): void {
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPagePlatformList(page);
}

searchPlatform() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPagePlatformList(this.selectedPage - 1);
}

// Deactive Position
deactive(data, i) {
 
      data.index = i;
      data.flag = "deactive"
      this.confirmModal.showModal(globalConstants.deactiveDataTitle, globalConstants.deactiveDataMsg + " " + data.name, data);
}

// active Position
active(data, i) {
    data.index = i;
    data.flag = "active"
    this.confirmModal.showModal(globalConstants.activeDataTitle, globalConstants.activeDataMsg + " " + data.name, data);
}

// delete Department
delete(data, i) { 
      data.index = i;
      data.flag = "delete"
      this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.name, data);
}

deactivePlatform(event) {
  return this.platformMasterService.deactivePlatform(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.engineList.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}

activePlatform(event) {
  return this.platformMasterService.activePlatform(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.engineList.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}

deletePlatform(event) {
  return this.platformMasterService.deletePlatform(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.engineList.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}

modalConfirmation(event) {
  console.log(event);
  if (event) {
    this.isSubmitted = true;
    if (event.flag == "active") {
      this.activePlatform(event);
    } else if (event.flag == "deactive") {
      this.deactivePlatform(event);
    } else if (event.flag == "delete") {
      this.deletePlatform(event);
    } 
  }
}

sortData(sort: Sort) {
  const data = this.pagePlatformMaster.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pagePlatformMaster.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pagePlatformMaster.content = data.sort((firstValue, secondValue) => {
    const isAsc = sort.direction == 'asc';
    switch (sort.active) {
      case globalConstants.ID:
        return compare(+firstValue.id, +secondValue.id, isAsc);
      case globalConstants.NAME:
        return compare(firstValue.name, secondValue.name, isAsc);
      case 'description':
        return compare(firstValue.description, secondValue.description, isAsc);
      case 'viewOrder':
        return compare(+firstValue.viewOrder, +secondValue.viewOrder, isAsc);
      case globalConstants.STATUS:
        return compare(firstValue.status, secondValue.status, isAsc);
      default:
        return 0;
    }
  });
}

getLogoUrl(event: any){
  this.logoUrl = event.target.src;
  console.log("image found..." + this.logoUrl);
}

modalSuccess($event: any) {
  //  this.ngOnInit();
  // this.selectedPage = 1;

  if(this.selectedPage >= 2){
    this.getPagePlatformList(this.selectedPage - 1);
    this.platformStatus = globalConstants;
    }else{
    this.ngOnInit();
    }

}

}

import { IndicesService } from './../services/indices.service';
import { SuccessModalComponent } from './../../global/success-modal/success-modal.component';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import { PageIndices } from '../models/page-indices';
import { Sort } from '@angular/material';

@Component({
  selector: 'app-indices',
  templateUrl: './indices.component.html',
  styleUrls: ['./indices.component.scss']
})
export class IndicesComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  pageIndices: PageIndices;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  indicesStatus;
  indicesList: any = [];

  recordsPerPage: number = 10;
  records: any = [];

  constructor(private indicesService: IndicesService, private userRightsService: UserRightsService) { }

ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageIndicesList(0);
    this.indicesStatus = globalConstants;
  }

getPageIndicesList(page: number): void {
    this.indicesService.getPageIndicesList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageIndices = page);
}

loadData(event: any) {
  console.log('pages ', event.target.value);
  this.recordsPerPage = event.target.value || 10;
  this.indicesService.getPageIndicesList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    .subscribe(page => this.pageIndices = page);
}

onSelect(page: number): void {
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPageIndicesList(page);
}

searchIndices() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageIndicesList(this.selectedPage - 1);
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

deactiveIndices(event) {
  return this.indicesService.deactiveIndices(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.indicesList.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}

activeIndices(event) {
  return this.indicesService.activeIndices(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.indicesList.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}

deleteIndices(event) {
  return this.indicesService.deleteIndices(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.indicesList.splice(event.index, 1);
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
      this.activeIndices(event);
    } else if (event.flag == "deactive") {
      this.deactiveIndices(event);
    } else if (event.flag == "delete") {
      this.deleteIndices(event);
    } 
  }
}

sortData(sort: Sort) {
  const data = this.pageIndices.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageIndices.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageIndices.content = data.sort((firstValue, secondValue) => {
    const isAsc = sort.direction == 'asc';
    switch (sort.active) {
      case globalConstants.ID:
        return compare(+firstValue.id, +secondValue.id, isAsc);
      case globalConstants.NAME:
        return compare(firstValue.name, secondValue.name, isAsc);
      case 'descrioption':
        return compare(firstValue.description, secondValue.description, isAsc);
      case 'platform':
        return compare(firstValue.platform, secondValue.platform, isAsc);
      case globalConstants.STATUS:
        return compare(firstValue.status, secondValue.status, isAsc);
      default:
        return 0;
    }
  });
}

modalSuccess($event: any) {
  //  this.ngOnInit();
  // this.selectedPage = 1;

  if(this.selectedPage >= 2){
    this.getPageIndicesList(this.selectedPage - 1);
    this.indicesStatus = globalConstants;
    }else{
    this.ngOnInit();
    }

}

}

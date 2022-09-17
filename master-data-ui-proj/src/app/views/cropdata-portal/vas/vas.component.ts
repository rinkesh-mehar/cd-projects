import { VasService } from './../services/vas.service';
import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { PageVas } from '../models/page-vas';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import { Sort } from '@angular/material';

@Component({
  selector: 'app-vas',
  templateUrl: './vas.component.html',
  styleUrls: ['./vas.component.scss']
})
export class VasComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  pageVas: PageVas;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  vasStatus;
  engineList: any = [];

  recordsPerPage: number = 10;
  records: any = [];

  logoUrl : string = '';

  constructor(private vasService: VasService, private userRightsService: UserRightsService) { }

ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageVasList(0);
    this.vasStatus = globalConstants;
  }

  getPageVasList(page: number): void {
    this.vasService.getPageVasList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageVas = page);
}

loadData(event: any) {
  console.log('pages ', event.target.value);
  this.recordsPerPage = event.target.value || 10;
  this.vasService.getPageVasList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    .subscribe(page => this.pageVas = page);
}

onSelect(page: number): void {
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPageVasList(page);
}

searchTool() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageVasList(this.selectedPage - 1);
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

deactiveVas(event) {
  return this.vasService.deactiveVas(event.id).subscribe(res => {
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

activeVas(event) {
  return this.vasService.activeVas(event.id).subscribe(res => {
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

deleteVas(event) {
  return this.vasService.deleteVas(event.id).subscribe(res => {
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
      this.activeVas(event);
    } else if (event.flag == "deactive") {
      this.deactiveVas(event);
    } else if (event.flag == "delete") {
      this.deleteVas(event);
    } 
  }
}

sortData(sort: Sort) {
  const data = this.pageVas.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageVas.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageVas.content = data.sort((firstValue, secondValue) => {
    const isAsc = sort.direction == 'asc';
    switch (sort.active) {
      case globalConstants.ID:
        return compare(+firstValue.id, +secondValue.id, isAsc);
      case globalConstants.NAME:
        return compare(firstValue.name, secondValue.name, isAsc);
      case 'description':
        return compare(firstValue.description, secondValue.description, isAsc);
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
    this.getPageVasList(this.selectedPage - 1);
    this.vasStatus = globalConstants;
    }else{
    this.ngOnInit();
    }

}

}

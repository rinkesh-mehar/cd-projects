import { Sort } from '@angular/material';
import { globalConstants } from './../../global/globalConstants';
import { EnginesService } from './../services/engines.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { PageEngines } from '../models/page-engines';
import { UserRightsService } from '../../services/user-rights.service';

@Component({
  selector: 'app-engines',
  templateUrl: './engines.component.html',
  styleUrls: ['./engines.component.scss']
})
export class EnginesComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  pageEngines: PageEngines;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  enginesStatus;
  engineList: any = [];

  recordsPerPage: number = 10;
  records: any = [];

  constructor(private enginesService: EnginesService, private userRightsService: UserRightsService) { }

ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageEngineList(0);
    this.enginesStatus = globalConstants;
  }

getPageEngineList(page: number): void {
    this.enginesService.getPageEngineList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageEngines = page);
}

loadData(event: any) {
  console.log('pages ', event.target.value);
  this.recordsPerPage = event.target.value || 10;
  this.enginesService.getPageEngineList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    .subscribe(page => this.pageEngines = page);
}

onSelect(page: number): void {
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPageEngineList(page);
}

searchEngine() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageEngineList(this.selectedPage - 1);
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

deactiveEngin(event) {
  return this.enginesService.deactiveEngine(event.id).subscribe(res => {
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

activeEngin(event) {
  return this.enginesService.activeEngine(event.id).subscribe(res => {
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

deleteEngin(event) {
  return this.enginesService.deleteEngine(event.id).subscribe(res => {
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
      this.activeEngin(event);
    } else if (event.flag == "deactive") {
      this.deactiveEngin(event);
    } else if (event.flag == "delete") {
      this.deleteEngin(event);
    } 
  }
}

sortData(sort: Sort) {
  const data = this.pageEngines.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageEngines.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageEngines.content = data.sort((firstValue, secondValue) => {
    const isAsc = sort.direction == 'asc';
    switch (sort.active) {
      case globalConstants.ID:
        return compare(+firstValue.id, +secondValue.id, isAsc);
      case globalConstants.NAME:
        return compare(firstValue.name, secondValue.name, isAsc);
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
    this.getPageEngineList(this.selectedPage - 1);
    this.enginesStatus = globalConstants;
    }else{
    this.ngOnInit();
    }

}

}

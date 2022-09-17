import { Router } from '@angular/router';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { globalConstants } from '../../global/globalConstants';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { PagePosition } from '../models/page-position';
import { PositionService } from '../services/position.service';
import { Sort } from '@angular/material';

@Component({
  selector: 'app-position',
  templateUrl: './position.component.html',
  styleUrls: ['./position.component.css']
})
export class PositionComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  pagePosition: PagePosition;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  positionStatus;
  positionList: any = [];

  recordsPerPage: number = 10;
  records: any = [];

  constructor(private positionService: PositionService, private userRightsService: UserRightsService) { }

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPagePositionList(0);
    this.positionStatus = globalConstants;
  }

  getPagePositionList(page: number): void {
    this.positionService.getPagePositionList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pagePosition = page);
}

loadData(event: any) {
  console.log('pages ', event.target.value);
  this.recordsPerPage = event.target.value || 10;
  this.positionService.getPagePositionList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    .subscribe(page => this.pagePosition = page);
}

onSelect(page: number): void {
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPagePositionList(page);
}

searchPosition() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPagePositionList(this.selectedPage - 1);
}

// Deactive Position
deactive(data, i) {

  this.positionService.opportunityCountByPosition(data.id).subscribe(opportunityCount => {
    console.log("opportunityCount : " + opportunityCount);

    if(opportunityCount > 0){
       this.errorModal.showModal('ERROR', "You can not Deactivate the '" + data.name + "' position as Opportunity is active for this position.", '');
       return;
    }else{  
      data.index = i;
      data.flag = "deactive"
      this.confirmModal.showModal(globalConstants.deactiveDataTitle, globalConstants.deactiveDataMsg + " " + data.name, data);
    }
   });
}

// active Position
active(data, i) {
    data.index = i;
    data.flag = "active"
    this.confirmModal.showModal(globalConstants.activeDataTitle, globalConstants.activeDataMsg + " " + data.name, data);
}

// delete Department
delete(data, i) {
  this.positionService.opportunityCountByPosition(data.id).subscribe(opportunityCount => {
    console.log("opportunityCount : " + opportunityCount);

    if(opportunityCount > 0){
       this.errorModal.showModal('ERROR', "You can not Delete the '" + data.name + "' position as Opportunity is active for this position.", '');
       return;
    }else{ 
      data.index = i;
      data.flag = "delete"
      this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.name, data);
    }
  });
}

  deactivePosition(event) {
  return this.positionService.deactivePosition(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.positionList.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}

activePosition(event) {
  return this.positionService.activePosition(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.positionList.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}

deletePosition(event) {
  return this.positionService.deletePosition(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.positionList.splice(event.index, 1);
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
      this.activePosition(event);
    } else if (event.flag == "deactive") {
      this.deactivePosition(event);
    } else if (event.flag == "delete") {
      this.deletePosition(event);
    } 
  }
}

sortData(sort: Sort) {
  const data = this.pagePosition.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pagePosition.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pagePosition.content = data.sort((firstValue, secondValue) => {
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
  //  this.ngOnInit();
  // this.selectedPage = 1;

  if(this.selectedPage >= 2){
    this.getPagePositionList(this.selectedPage - 1);
    this.positionStatus = globalConstants;
    }else{
    this.ngOnInit();
    }

}

}

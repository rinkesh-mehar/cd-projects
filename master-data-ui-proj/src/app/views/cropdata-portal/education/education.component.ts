import { Router } from '@angular/router';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { globalConstants } from '../../global/globalConstants';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { PageEducation } from '../models/page-education';
import { EducationService } from '../services/education.service';
import { Sort } from '@angular/material';

@Component({
  selector: 'app-education',
  templateUrl: './education.component.html',
  styleUrls: ['./education.component.css']
})
export class EducationComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  pageEducation: PageEducation;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  educationStatus;
  educationList: any = [];

  recordsPerPage: number = 10;
  records: any = [];

  constructor(private educationService: EducationService, private userRightsService: UserRightsService) { }

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageEducationList(0);
    this.educationStatus = globalConstants;
  }

  getPageEducationList(page: number): void {
    this.educationService.getPageEducationList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageEducation = page);
}

loadData(event: any) {
  console.log('pages ', event.target.value);
  this.recordsPerPage = event.target.value || 10;
  this.educationService.getPageEducationList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    .subscribe(page => this.pageEducation = page);
}

onSelect(page: number): void {
    console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPageEducationList(page);
}

searchEducation() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageEducationList(this.selectedPage - 1);
}

// Deactive Education
deactive(data, i) {
  this.educationService.opportunityCountByEducation(data.id).subscribe(opportunityCount => {
    console.log("opportunityCount : " + opportunityCount);

    if(opportunityCount > 0){
     this.errorModal.showModal('ERROR', "You can not Deactivate the '" + data.name + "' education as Opportunity is active for this education.", '');
     return;
    }else{
      data.index = i;
      data.flag = "deactive"
      this.confirmModal.showModal(globalConstants.deactiveDataTitle, globalConstants.deactiveDataMsg + " " + data.name, data);
    }
   });
}

  // active Education
  active(data, i) {
    data.index = i;
    data.flag = "active"
    this.confirmModal.showModal(globalConstants.activeDataTitle, globalConstants.activeDataMsg + " " + data.name, data);
  }

  // delete Department
  delete(data, i) {
    this.educationService.opportunityCountByEducation(data.id).subscribe(opportunityCount => {
      console.log("opportunityCount : " + opportunityCount);
  
      if(opportunityCount > 0){
       this.errorModal.showModal('ERROR', "You can not Delete the '" + data.name + "' education as Opportunity is active for this education.", '');
       return;
      }else{
       data.index = i;
       data.flag = "delete"
       this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.name, data);
      }
    });
  }

  deactiveEducation(event) {
  return this.educationService.deactiveEducation(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.educationList.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}

activeEducation(event) {
  return this.educationService.activeEducation(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.educationList.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}

deleteEducation(event) {
  return this.educationService.deleteEducation(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.educationList.splice(event.index, 1);
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
      this.activeEducation(event);
    } else if (event.flag == "deactive") {
      this.deactiveEducation(event);
    } else if (event.flag == "delete") {
      this.deleteEducation(event);
    } 
  }
}

sortData(sort: Sort) {
  const data = this.pageEducation.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageEducation.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageEducation.content = data.sort((firstValue, secondValue) => {
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
  // this.ngOnInit();
  // this.selectedPage = 1;

  if(this.selectedPage >= 2){
  this.getPageEducationList(this.selectedPage - 1);
  this.educationStatus = globalConstants;
  }else{
  this.ngOnInit();
  }
}

}

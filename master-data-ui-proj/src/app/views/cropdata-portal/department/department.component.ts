import { DepartmentService } from './../services/department.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { UserRightsService } from '../../services/user-rights.service';
import { PageDepartment } from '../models/PageDepartment';
import { globalConstants } from '../../global/globalConstants';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { Router } from '@angular/router';
import { Sort } from '@angular/material';

@Component({
  selector: 'app-department',
  templateUrl: './department.component.html',
  styleUrls: ['./department.component.css']
})
export class DepartmentComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  pageDepartment: PageDepartment;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  deptmentStatus;
  departmentList: any = [];

  recordsPerPage: number = 10;
  records: any = [];
  

  constructor(private departmentService: DepartmentService, private userRightsService: UserRightsService) { }

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageDepartmentList(0);
    this.deptmentStatus = globalConstants;
  }

  getPageDepartmentList(page: number): void {
    this.departmentService.getPageDepartmentList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageDepartment = page);
}

loadData(event: any) {
  console.log('pages ', event.target.value);
  this.recordsPerPage = event.target.value || 10;
  this.departmentService.getPageDepartmentList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    .subscribe(page => this.pageDepartment = page);
}

onSelect(page: number): void {
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPageDepartmentList(page);
}

searchDepartment() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageDepartmentList(this.selectedPage - 1);
}

  // deactive Department
  deactive(data, i) {

    this.departmentService.opportunityCountByDepartment(data.id).subscribe(opportunityCount => {
     console.log("opportunityCount : " + opportunityCount);

     if(opportunityCount > 0){
      this.errorModal.showModal('ERROR', "You can not Deactivate the '" + data.name + "' department as Opportunity is active for this department.", '');
      return;
     }else{
      data.index = i;
      data.flag = "deactive"
      this.confirmModal.showModal(globalConstants.deactiveDataTitle, globalConstants.deactiveDataMsg + " " + data.name, data);
     }
    });
  }

  // active Department
active(data, i) {
  data.index = i;
  data.flag = "active"
  this.confirmModal.showModal(globalConstants.activeDataTitle, globalConstants.activeDataMsg + " " + data.name, data);
}

  // delete Department
  delete(data, i) {

    this.departmentService.opportunityCountByDepartment(data.id).subscribe(opportunityCount => {
      console.log("opportunityCount : " + opportunityCount);
 
      if(opportunityCount > 0){
       this.errorModal.showModal('ERROR', "You can not Delete the '" + data.name + "' department as Opportunity is active for this department.", '');
       return;
      }else{
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.name, data);
      }
    });
  }

  deactiveDepartment(event) {
    return this.departmentService.deactiveDepartment(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.departmentList.splice(event.index, 1);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
    });
  }

  activeDepartment(event) {
    return this.departmentService.activeDepartment(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.departmentList.splice(event.index, 1);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
    });
  }

  deleteDepartment(event) {
    return this.departmentService.deleteDepartment(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.departmentList.splice(event.index, 1);
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
      this.activeDepartment(event);
    } else if (event.flag == "deactive") {
      this.deactiveDepartment(event);
    } else if (event.flag == "delete") {
      this.deleteDepartment(event);
    }
  }
}

sortData(sort: Sort) {
  const data = this.pageDepartment.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageDepartment.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageDepartment.content = data.sort((firstValue, secondValue) => {
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

  console.log("page : " + this.selectedPage);
  if(this.selectedPage >= 2){
    console.log("Inside if");
  this.getPageDepartmentList(this.selectedPage - 1);
  this.deptmentStatus = globalConstants;
  }else{
    console.log("Inside else");
  this.ngOnInit();
  }
}
}

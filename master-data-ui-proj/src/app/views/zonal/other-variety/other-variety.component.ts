import { PageOtherVariety } from './../models/PageOtherVariety';
import { Router } from '@angular/router';
import { Sort } from '@angular/material';
import { OtherVarietyService } from '../services/other-variety.service';
import { globalConstants } from '../../global/globalConstants';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { Component, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-other-variety',
  templateUrl: './other-variety.component.html',
  styleUrls: ['./other-variety.component.scss']
})
export class OtherVarietyComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  otherVarietyStatus;
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  StressList: any = [];

  pageOtherVariety : PageOtherVariety;
  selectedPage: number = 1;
  maxSize = 10;
  searchText: any="";

  recordsPerPage: number = 10;
  records: any = [];

  constructor(public otherVarietyService:OtherVarietyService,
    public router: Router) { }

  ngOnInit(): void {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageOtherVariety(0);
    this.otherVarietyStatus = globalConstants;
  }

  getPageOtherVariety(page: number): void {
    this.otherVarietyService.getPageOtherVariety(page, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageOtherVariety = page)
  }

  searchOtherVariety() {
    this.selectedPage = 1;
    this.getPageOtherVariety(this.selectedPage - 1);
  }

  onSelect(page: number): void {
    // (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    // this.bulkDatas.disbled = true;
    this.getPageOtherVariety(page);
  }
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.otherVarietyService.getPageOtherVariety(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageOtherVariety = page);
  }
  sortData(sort: Sort) {
    const data = this.pageOtherVariety.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageOtherVariety.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageOtherVariety.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case 'commodity':
          return compare(firstValue.commodity, secondValue.commodity, isAsc);
        case 'state':
          return compare(firstValue.state, secondValue.state, isAsc);       
        case 'otherVariety':
          return compare(firstValue.otherVariety, secondValue.otherVariety, isAsc);
        case globalConstants.STATUS:
          return compare(firstValue.status, secondValue.status, isAsc);
        default:
          return 0;
      }
    });
  }
  completed(otherVariety:any){
    // console.log(alternativeVariety)
    // console.log("Called and Inside component")
    otherVariety.flag = "completed";
    this.confirmModal.showModal(globalConstants.completedDataTitle, globalConstants.completedDataMsg, otherVariety);
}

modalConfirmation(otherVariety) {
  // console.log(alternativeVariety);
  let observable: any;
  if (otherVariety) {
    this.isSubmitted = true;
    if (otherVariety.flag == 'completed') {
      observable = this.otherVarietyService.completed(otherVariety);
    }
    observable.subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
          this.ngOnInit();
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
    });
  }
}

modalSuccess($event: any) {
  if (this.selectedPage > 0){
    this.onSelect((this.selectedPage - 1));
  } else {
    this.onSelect(this.selectedPage);
  }
}
}

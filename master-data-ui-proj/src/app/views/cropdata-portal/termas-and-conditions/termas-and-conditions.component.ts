import { globalConstants } from './../../global/globalConstants';
import { PageTermsAndConditions } from './../models/pageTermsAndConditions';
import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { Sort } from '@angular/material';
import { TermsAndConditionsService } from '../services/terms-and-conditions.service';
import { UserRightsService } from '../../services/user-rights.service';

@Component({
  selector: 'app-termas-and-conditions',
  templateUrl: './termas-and-conditions.component.html',
  styleUrls: ['./termas-and-conditions.component.css']
})
export class TermasAndConditionsComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  searchText: any = '';
  pageTermsAndConditions: PageTermsAndConditions;
  termsAndConditionsList: any = [];
  selectedPage: number = 1;
  maxSize: number = 10;
  tacUrl : string = '';
  ppUrl : string = '';
  termsAndConditionsStatus;
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  recordsPerPage: number = 10;
  records: any = [];

  ngOnInit(): void {
      // this.loadAllNews();
      this.records = ['20', '50', '100', '200', '250'];
      this.getTermsAndConditionsListByPagination(0);
      this.termsAndConditionsStatus = globalConstants;

  }

  constructor(public termsAndConditionsService: TermsAndConditionsService,  private userRightsService: UserRightsService) {
  }


  getTermsAndConditionsListByPagination(page: number): void {
      this.termsAndConditionsService.getTermsAndConditionsListByPagination(page, this.recordsPerPage, this.searchText)
          .subscribe(page => this.pageTermsAndConditions = page);
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.termsAndConditionsService.getTermsAndConditionsListByPagination(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageTermsAndConditions = page);
  }

  onSelect(page: number): void {
      console.log('selected page : ' + page);
      this.selectedPage = page;
      this.getTermsAndConditionsListByPagination(page);
  }

  searchTermsAndConditions() {
      this.selectedPage = 1;
      console.log(this.searchText);
      this.getTermsAndConditionsListByPagination(this.selectedPage - 1);
  }

  getTACUrl(event: any){
      this.tacUrl = event.target.src;
      console.log("tacUrl found..." + this.tacUrl);
  }

  getPPUrl(event: any){
    this.ppUrl = event.target.src;
    console.log("ppUrl found..." + this.ppUrl);
}

  sortData(sort: Sort) {
      const data = this.pageTermsAndConditions.content.slice();
      if (!sort.active || sort.direction == '') {
        this.pageTermsAndConditions.content = data;
        return;
      }
    
      function compare(firstValue, secondValue, isAsc: boolean) {
        return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
      }
    
      this.pageTermsAndConditions.content = data.sort((firstValue, secondValue) => {
        const isAsc = sort.direction == 'asc';
        switch (sort.active) {
          case globalConstants.ID:
            return compare(+firstValue.id, +secondValue.id, isAsc);
            case 'platform':
              return compare(firstValue.platform, secondValue.platform, isAsc);
          case 'tandCUrl':
            return compare(firstValue.tandCUrl, secondValue.tandCUrl, isAsc);
          case 'privacyPolicyUrl':
            return compare(firstValue.privacyPolicyUrl, secondValue.privacyPolicyUrl, isAsc);
          case 'version':
            return compare(firstValue.version, secondValue.version, isAsc);
          case globalConstants.STATUS:
              return compare(firstValue.status, secondValue.status, isAsc);
          default:
            return 0;
        }
      });
    }

    // deactive News
deactive(data, i) {
  data.index = i;
  data.flag = "deactive"
  this.confirmModal.showModal(globalConstants.deactiveDataTitle, globalConstants.deactiveDataMsg, data);
}

// active News
active(data, i) {
data.index = i;
data.flag = "active"
this.confirmModal.showModal(globalConstants.activeDataTitle, globalConstants.activeDataMsg, data);
}

deactiveNews(event) {
return this.termsAndConditionsService.deactiveTermsAndConditions(event.id).subscribe(res => {
  if (res) {
    this.isSuccess = res.success;
    if (res.success) {
      this.termsAndConditionsList.splice(event.index, 1);
      this.successModal.showModal('SUCCESS', res.message, '');
    } else {
      this.errorModal.showModal('ERROR', res.error, '');
    }
  }
}, err => {
  this.errorModal.showModal('ERROR', err.error, '');
});
}

activeNews(event) {
return this.termsAndConditionsService.activeTermsAndConditions(event.id).subscribe(res => {
  if (res) {
    this.isSuccess = res.success;
    if (res.success) {
      this.termsAndConditionsList.splice(event.index, 1);
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
    this.activeNews(event);
  } else if (event.flag == "deactive") {
    this.deactiveNews(event);
  }
}
}

modalSuccess($event: any) {
// this.ngOnInit();
// this.selectedPage = 1;

if(this.selectedPage >= 2){
this.getTermsAndConditionsListByPagination(this.selectedPage - 1);
this.termsAndConditionsStatus = globalConstants;
}else{
this.ngOnInit();
}
}

}

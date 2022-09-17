import { BuyerPreApplicationService } from './../services/buyer-pre-application.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { PageBuyerPreApplication } from '../models/page-buyer-pre-application';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {Sort} from '@angular/material';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-buyer-pre-application',
  templateUrl: './buyer-pre-application.component.html',
  styleUrls: ['./buyer-pre-application.component.css']
})
export class BuyerPreApplicationComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  buyerPreApplicationList: any;
  searchText: any = '';
  pageBuyerPreApplication: PageBuyerPreApplication;
  selectedPage: number = 1;

  maxSize =10;

  recordsPerPage: number = 10;
  records: any = [];

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  applicantStatus;

  constructor(private buyerPreApplicationService: BuyerPreApplicationService, private userRightsService: UserRightsService) { }

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageBuyerPreApplicationList(0);
    this.applicantStatus = globalConstants;
  }

  getPageBuyerPreApplicationList(page: number): void {
    this.buyerPreApplicationService.getPageBuyerPreApplicationList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageBuyerPreApplication = page);
}

loadData(event: any) {
  console.log('pages ', event.target.value);
  this.recordsPerPage = event.target.value || 10;
  this.buyerPreApplicationService.getPageBuyerPreApplicationList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    .subscribe(page => this.pageBuyerPreApplication = page);
}

onSelect(page: number): void {
    this.selectedPage = page;
    this.getPageBuyerPreApplicationList(page);
}

searchBuyerPreApplication() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageBuyerPreApplicationList(this.selectedPage - 1);
}

sortData(sort: Sort) {
  const data = this.pageBuyerPreApplication.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageBuyerPreApplication.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageBuyerPreApplication.content = data.sort((firstValue, secondValue) => {
    const isAsc = sort.direction == 'asc';
    switch (sort.active) {
      case globalConstants.ID:
        return compare(+firstValue.id, +secondValue.id, isAsc);
      case 'applicationType':
        return compare(firstValue.applicationType, secondValue.applicationType, isAsc);
        case 'applicantType':
          return compare(firstValue.companyType, secondValue.companyType, isAsc);
      case 'companyName':
        return compare(firstValue.companyName, secondValue.companyName, isAsc);
      case globalConstants.NAME:
        return compare(firstValue.name, secondValue.name, isAsc);
      case 'telephoneNumber':
        return compare(firstValue.telephoneNumber, secondValue.telephoneNumber, isAsc);
      case 'mobileNumber':
        return compare(firstValue.mobileNumber, secondValue.mobileNumber, isAsc);
      case 'emailAddress':
        return compare(firstValue.emailAddress, secondValue.emailAddress, isAsc);    
      // case 'companyRepresentativeName':
      //   return compare(firstValue.companyRepresentativeName, secondValue.companyRepresentativeName, isAsc);
      // case 'companyRepresentativeEmailID':
      //   return compare(firstValue.companyRepresentativeEmailID, secondValue.companyRepresentativeEmailID, isAsc);
      //   case 'companyRepresentativeContactNumber':
      //   return compare(firstValue.companyRepresentativeContactNumber, secondValue.companyRepresentativeContactNumber, isAsc);
      // case 'commodity':
      //   return compare(firstValue.commodity, secondValue.commodity, isAsc);
      default:
        return 0;
    }
  });
}

// delete Applicant
delete(data) {

  data.flag = "delete"
  this.confirmModal.showModal(globalConstants.deleteDataTitle, "Are you sure want to delete this record?", data);

}

deleteApplicant(event) {
  return this.buyerPreApplicationService.deleteApplicant(event.id).subscribe(res => {
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

convertToFrenchies(data){

  data.flag = "convertToFrenchies"
  this.confirmModal.showModal(globalConstants.deleteDataTitle, "Are you sure want to convert this record to Frenchies?", data);

}

convertApplicanyToFrenchies(event) {
  return this.buyerPreApplicationService.convertApplicanyToFrenchies(event.id).subscribe(res => {
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

modalConfirmation(event) {
  console.log(event);
  if (event) {
    this.isSubmitted = true;
      
    if(event.flag == "delete"){

      this.deleteApplicant(event);

    }else{

      this.convertApplicanyToFrenchies(event);

    }
 
  }
}

modalSuccess($event: any) {

  console.log("page : " + this.selectedPage);
  if(this.selectedPage >= 2){
    console.log("Inside if");
  this.getPageBuyerPreApplicationList(this.selectedPage - 1);
  this.applicantStatus = globalConstants;
  }else{
    console.log("Inside else");
  this.ngOnInit();
  }
}

}

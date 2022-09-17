import { Opportunities } from './../models/opportunities';
import { Component, OnInit, ViewChild } from '@angular/core';
import { UserRightsService } from '../../services/user-rights.service';
import { PageOpportunities } from '../models/page-opportunities';
import { OppotunitiesService } from '../services/oppotunities.service';
import { globalConstants } from '../../global/globalConstants';
import { Sort } from '@angular/material';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-oppoortunity',
  templateUrl: './oppoortunity.component.html',
  styleUrls: ['./oppoortunity.component.css']
})
export class OppoortunityComponent implements OnInit {
  
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  constructor(private opportunitiesService: OppotunitiesService,
    private userRightsService: UserRightsService) { }

  opsList: any;
  searchText: any = '';
  pageOpportunities: PageOpportunities;
  selectedPage: number = 1;
  test: any;
  opportunityStatus;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  opportunityList: any = [];
      
  maxSize =10;

  recordsPerPage: number = 10;
  records: any = [];
  
  ngOnInit() {
  //this.getOpportunitiesList();
  console.log('Inside ngOnInit..');
  this.records = ['20', '50', '100', '200', '250'];
  this.getPageOpportunities(0);
  this.opportunityStatus = globalConstants;
  }

  getOpportunitiesList() {
    this.opportunitiesService.getOpportunityList().subscribe(res => {
        this.opsList = res;
        console.log('opsList ', this.opsList);
    }, err => {
      console.log('error ', err);
    });
  }

  getPageOpportunities(page: number): void {
    console.log('Inside getPageOpportunities of ts..');
    this.opportunitiesService.getPageOpportunities(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageOpportunities = page);
}

loadData(event: any) {
  console.log('pages ', event.target.value);
  this.recordsPerPage = event.target.value || 10;
  this.opportunitiesService.getPageOpportunities(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    .subscribe(page => this.pageOpportunities = page);
}

onSelect(page: number): void {
    console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPageOpportunities(page);
}

searchOpportunities() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageOpportunities(this.selectedPage - 1);
}

closeOpportunity(event) {
  return this.opportunitiesService.closeOpportunity(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.opportunityList.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}

activeOpportunity(event) {
  return this.opportunitiesService.activeOpportunity(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.opportunityList.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}

 // close opportunity
 close(data, i) {
  data.index = i;
  data.flag = "close"
  this.confirmModal.showModal(globalConstants.closeDataTitle, globalConstants.closeDataMsg + " '" + data.position + "' Opportunity", data);
}

// active opportunity
active(data, i) {
  data.index = i;
  data.flag = "active"
  this.confirmModal.showModal(globalConstants.activeDataTitle, globalConstants.activeDataMsg + " '" + data.position + "' Opportunity", data);
}

sortData(sort: Sort) {
  const data = this.pageOpportunities.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageOpportunities.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageOpportunities.content = data.sort((firstValue, secondValue) => {
    const isAsc = sort.direction == 'asc';
    switch (sort.active) {
      case globalConstants.ID:
        return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'platform':
          return compare(firstValue.platform, secondValue.platform, isAsc);
      case 'department':
        return compare(firstValue.department, secondValue.department, isAsc);
      case 'position':
        return compare(firstValue.position, secondValue.position, isAsc);
      case 'education':
        return compare(firstValue.education, secondValue.education, isAsc);
      case 'experience':
        return compare(firstValue.experience, secondValue.experience, isAsc);
        case 'location':
          return compare(firstValue.location, secondValue.location, isAsc);
      // case 'state':
      //   return compare(firstValue.state, secondValue.state, isAsc);
      //   case 'district':
      //   return compare(firstValue.district, secondValue.district, isAsc);
      // case 'description':
      //   return compare(firstValue.description, secondValue.description, isAsc);
      //   case 'profile':
      //   return compare(firstValue.profile, secondValue.profile, isAsc);
        case 'remuneration':
        return compare(firstValue.remuneration, secondValue.remuneration, isAsc);
        case globalConstants.STATUS:
        return compare(firstValue.status, secondValue.status, isAsc);
      default:
        return 0;
    }
  });
}

modalConfirmation(event) {
  console.log(event);
  if (event) {
    this.isSubmitted = true;

    if (event.flag == "active") {
      this.activeOpportunity(event);
    } else if (event.flag == "close") {
      this.closeOpportunity(event);
    }
  }
}

modalSuccess($event: any) {
  // this.ngOnInit();
  // this.selectedPage = 1;

  if(this.selectedPage >= 2){
    this.getPageOpportunities(this.selectedPage - 1);
    this.opportunityStatus = globalConstants;
    }else{
    this.ngOnInit();
    }
}

}

import { Component, OnInit } from '@angular/core';
import { Sort } from '@angular/material';
import { globalConstants } from '../../global/globalConstants';
import { PagePartnershipRequest } from '../models/page-partnership-request';
import { PartnershipRequestService } from '../services/partnership-request.service';

@Component({
  selector: 'app-partnership-request',
  templateUrl: './partnership-request.component.html',
  styleUrls: ['./partnership-request.component.css']
})
export class PartnershipRequestComponent implements OnInit {

  pagePartnershipRequest: PagePartnershipRequest;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;

  recordsPerPage: number = 10;
  records: any = [];

  constructor(private partnershipRequestService: PartnershipRequestService) { }

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPartnershipRequestList(0);
  }
  getPartnershipRequestList(page: number): void {
    this.partnershipRequestService.getPartnershipRequestList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pagePartnershipRequest = page);
  }
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.partnershipRequestService.getPartnershipRequestList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pagePartnershipRequest = page);
  }

  onSelect(page: number): void {
    console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPartnershipRequestList(page);
}

searchPartnershipRequest() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPartnershipRequestList(this.selectedPage - 1);
}

sortData(sort: Sort) {
  const data = this.pagePartnershipRequest.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pagePartnershipRequest.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pagePartnershipRequest.content = data.sort((firstValue, secondValue) => {
    const isAsc = sort.direction == 'asc';
    switch (sort.active) {
      case globalConstants.ID:
        return compare(+firstValue.id, +secondValue.id, isAsc);
        case globalConstants.NAME:
        return compare(firstValue.name, secondValue.name, isAsc);
        case 'email':
        return compare(firstValue.email, secondValue.email, isAsc);
        case 'mobile':
        return compare(firstValue.mobile, secondValue.mobile, isAsc);
        case 'organisationName':
        return compare(firstValue.organisationName, secondValue.organisationName, isAsc);
        case 'requestDate':
        return compare(firstValue.requestDate, secondValue.requestDate, isAsc);
      default:
        return 0;
    }
  });
}

}

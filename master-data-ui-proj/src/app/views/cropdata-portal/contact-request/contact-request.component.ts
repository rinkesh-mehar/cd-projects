import { Component, OnInit } from '@angular/core';
import { Sort } from '@angular/material';
import { globalConstants } from '../../global/globalConstants';
import { PageContactRequest } from '../models/page-contact-request';
import { ContactRequestService } from '../services/contact-request.service';

@Component({
  selector: 'app-contact-request',
  templateUrl: './contact-request.component.html',
  styleUrls: ['./contact-request.component.css']
})
export class ContactRequestComponent implements OnInit {

  pageContactRequest: PageContactRequest;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];

  constructor(private contactRequestService: ContactRequestService) { }

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getContactRequestList(0);
  }
  getContactRequestList(page: number): void {
    this.contactRequestService.getContactRequestList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageContactRequest = page);
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.contactRequestService.getContactRequestList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageContactRequest = page);
  }

  onSelect(page: number): void {
    console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getContactRequestList(page);
}

searchContactRequest() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getContactRequestList(this.selectedPage - 1);
}

sortData(sort: Sort) {
  const data = this.pageContactRequest.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageContactRequest.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageContactRequest.content = data.sort((firstValue, secondValue) => {
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
        case 'query':
        return compare(firstValue.query, secondValue.query, isAsc);
      default:
        return 0;
    }
  });
}

}

import { Component, OnInit } from '@angular/core';
import { Sort } from '@angular/material';
import { globalConstants } from '../../global/globalConstants';
import { PageReportDownloadRequest } from '../models/page-report-download-request';
import { ReportDownloadRequestService } from '../services/report-download-request.service';

@Component({
  selector: 'app-report-download-request',
  templateUrl: './report-download-request.component.html',
  styleUrls: ['./report-download-request.component.css']
})
export class ReportDownloadRequestComponent implements OnInit {

  pageReportDownloadRequest: PageReportDownloadRequest;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];

  constructor(private reportDownloadRequestService: ReportDownloadRequestService) { }

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getReportDownloadRequestList(0);
  }
  getReportDownloadRequestList(page: number): void {
    this.reportDownloadRequestService.getReportDownloadRequestList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageReportDownloadRequest = page);
  }
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.reportDownloadRequestService.getReportDownloadRequestList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageReportDownloadRequest = page);
  }
  onSelect(page: number): void {
    console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getReportDownloadRequestList(page);
}

searchReportDownloadRequest() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getReportDownloadRequestList(this.selectedPage - 1);
}

sortData(sort: Sort) {
  const data = this.pageReportDownloadRequest.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageReportDownloadRequest.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageReportDownloadRequest.content = data.sort((firstValue, secondValue) => {
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
        case 'platform':
        return compare(firstValue.platform, secondValue.platform, isAsc);
        case 'title':
        return compare(firstValue.title, secondValue.title, isAsc);
        case 'requestDate':
        return compare(firstValue.requestDate, secondValue.requestDate, isAsc);
      default:
        return 0;
    }
  });
}

}

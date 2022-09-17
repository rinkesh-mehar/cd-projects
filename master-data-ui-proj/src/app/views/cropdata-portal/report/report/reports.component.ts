import { newsReportsService } from './../../services/news-reports.service';
import {Component, OnInit, ViewChild} from '@angular/core';
import {ConfirmationMadalComponent} from '../../../global/confirmation-madal/confirmation-madal.component';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {UserRightsService} from '../../../services/user-rights.service';
import {PageNewsReports} from '../../models/pageNewsReports';
import { globalConstants } from '../../../global/globalConstants';
import { Sort } from '@angular/material';

@Component({
    selector: 'app-report',
    templateUrl: './reports.component.html',
    styleUrls: ['./reports.component.css']
})

export class ReportsComponent implements OnInit {
    @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    searchText: any = '';
    pageReport: PageNewsReports;
    reportList: any = [];
    selectedPage: number = 1;
    maxSize: number = 10;
    reportStatus;
    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    recordsPerPage: number = 10;
    records: any = [];
    ngOnInit(): void {
        // this.loadAllNews();
        this.records = ['20', '50', '100', '200', '250'];
        this.getPageReport(0);
        this.reportStatus = globalConstants;

    }

    constructor(public reportService: newsReportsService,  private userRightsService: UserRightsService) {
    }
    loadAllNews() {
        return this.reportService.getAllReport().subscribe((data: {}) => {
            this.reportList = data;
        });
    }
    getPageReport(page: number): void {
        this.reportService.getPageReport(page, this.recordsPerPage, this.searchText)
            .subscribe(page => this.pageReport = page);
    }
    loadData(event: any) {
      console.log('pages ', event.target.value);
      this.recordsPerPage = event.target.value || 10;
      this.reportService.getPageReport(this.selectedPage - 1, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageReport = page);
    }
    onSelect(page: number): void {
        console.log('selected page : ' + page);
        this.selectedPage = page;
        this.getPageReport(page);
    }

    searchReports() {
        this.selectedPage = 1;
        console.log(this.searchText);
        this.getPageReport(this.selectedPage - 1);
    }

    sortData(sort: Sort) {
        const data = this.pageReport.content.slice();
        if (!sort.active || sort.direction == '') {
          this.pageReport.content = data;
          return;
        }
      
        function compare(firstValue, secondValue, isAsc: boolean) {
          return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }
      
        this.pageReport.content = data.sort((firstValue, secondValue) => {
          const isAsc = sort.direction == 'asc';
          switch (sort.active) {
            case globalConstants.ID:
              return compare(+firstValue.id, +secondValue.id, isAsc);
              case 'platform':
                return compare(firstValue.platform, secondValue.platform, isAsc);
            case 'title':
              return compare(firstValue.title, secondValue.title, isAsc);
            case 'fileUrl':
              return compare(firstValue.fileUrl, secondValue.fileUrl, isAsc);
            case 'authenticate':
              return compare(firstValue.authenticate, secondValue.authenticate, isAsc);
            case globalConstants.STATUS:
              return compare(+firstValue.status, +secondValue.status, isAsc);
            default:
              return 0;
          }
        });
      }

  // deactive Report
  deactive(data, i) {
    data.index = i;
    data.flag = "deactive"
    this.confirmModal.showModal(globalConstants.deactiveDataTitle, globalConstants.deactiveDataMsg + ' "' + data.title + '"', data);
  }

// active Report
active(data, i) {
  data.index = i;
  data.flag = "active"
  this.confirmModal.showModal(globalConstants.activeDataTitle, globalConstants.activeDataMsg + ' "' + data.title + '"', data);
}

deactiveReport(event) {
  return this.reportService.deactiveReport(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.reportList.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}

activeReport(event) {
  return this.reportService.activeReport(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.reportList.splice(event.index, 1);
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
      this.activeReport(event);
    } else if (event.flag == "deactive") {
      this.deactiveReport(event);
    }
  }
}

modalSuccess($event: any) {
  // this.ngOnInit();
  // this.selectedPage = 1;

  if(this.selectedPage >= 2){
  this.getPageReport(this.selectedPage - 1);
  this.reportStatus = globalConstants;
  }else{
  this.ngOnInit();
  }
}

}

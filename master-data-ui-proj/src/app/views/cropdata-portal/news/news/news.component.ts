// @ts-ignore
import { Component, OnInit, ViewChild } from '@angular/core';
import {ConfirmationMadalComponent} from '../../../global/confirmation-madal/confirmation-madal.component';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {newsReportsService} from '../../services/news-reports.service';
import {UserRightsService} from '../../../services/user-rights.service';
import {PageNewsReports} from '../../models/pageNewsReports';
import { Sort } from '@angular/material';
import { globalConstants } from '../../../global/globalConstants';


@Component({
    selector: 'app-news',
    templateUrl: './news.component.html',
    styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit {

    @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    searchText: any = '';
    pageNews: PageNewsReports;
    newsList: any = [];
    selectedPage: number = 1;
    maxSize: number = 10;
    imgUrl : string = '';
    newsStatus;
    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    recordsPerPage: number = 10;
    records: any = [];

    ngOnInit(): void {
        // this.loadAllNews();
        this.records = ['20', '50', '100', '200', '250'];
        this.getPageNews(0);
        this.newsStatus = globalConstants;

    }

    constructor(public newsService: newsReportsService,  private userRightsService: UserRightsService) {
    }
    
    loadAllNews() {
        return this.newsService.getAllNews().subscribe((data: {}) => {
            this.newsList = data;
        });
    }

    getPageNews(page: number): void {
        this.newsService.getPageNews(page, this.recordsPerPage, this.searchText)
            .subscribe(page => this.pageNews = page);
    }

    loadData(event: any) {
      console.log('pages ', event.target.value);
      this.recordsPerPage = event.target.value || 10;
      this.newsService.getPageNews(this.selectedPage - 1, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageNews = page);
    }

    onSelect(page: number): void {
        console.log('selected page : ' + page);
        this.selectedPage = page;
        this.getPageNews(page);
    }

    searchNews() {
        this.selectedPage = 1;
        console.log(this.searchText);
        this.getPageNews(this.selectedPage - 1);
    }

    getImageUrl(event: any){
        this.imgUrl = event.target.src;
        console.log("image found..." + this.imgUrl);
    }

    sortData(sort: Sort) {
        const data = this.pageNews.content.slice();
        if (!sort.active || sort.direction == '') {
          this.pageNews.content = data;
          return;
        }
      
        function compare(firstValue, secondValue, isAsc: boolean) {
          return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }
      
        this.pageNews.content = data.sort((firstValue, secondValue) => {
          const isAsc = sort.direction == 'asc';
          switch (sort.active) {
            case globalConstants.ID:
              return compare(+firstValue.id, +secondValue.id, isAsc);
              case 'platform':
                return compare(firstValue.platform, secondValue.platform, isAsc);
            case 'subject':
              return compare(firstValue.subject, secondValue.subject, isAsc);
            case 'publishedDate':
              return compare(firstValue.publishedDate, secondValue.publishedDate, isAsc);
            case globalConstants.STATUS:
                return compare(+firstValue.status, +secondValue.status, isAsc);
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
  return this.newsService.deactiveNews(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.newsList.splice(event.index, 1);
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
  return this.newsService.activeNews(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.newsList.splice(event.index, 1);
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
  this.getPageNews(this.selectedPage - 1);
  this.newsStatus = globalConstants;
  }else{
  this.ngOnInit();
  }
}
}

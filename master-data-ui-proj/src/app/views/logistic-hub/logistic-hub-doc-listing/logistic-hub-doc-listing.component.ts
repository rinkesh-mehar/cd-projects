import {Component, OnInit} from '@angular/core';
import {LogisticHubDocServiceService} from './service/logistic-hub-doc-service.service';
import {UserRightsService} from '../../services/user-rights.service';
import {Router} from '@angular/router';
import {LhPage} from '../model/lhPage';
import { Sort } from '@angular/material';

@Component({
    selector: 'app-logistic-hub-doc-listing',
    templateUrl: './logistic-hub-doc-listing.component.html',
    styleUrls: ['./logistic-hub-doc-listing.component.scss']
})
export class LogisticHubDocListingComponent implements OnInit {

    lhDetailList: any;

    searchText: any = '';
    pageLogisticHub: LhPage;
    selectedPage: number;
    maxSize: number = 10;

    recordsPerPage: number = 10;
   records: any = [];

    constructor(private logisticHubDocServiceService: LogisticHubDocServiceService,
                private userRightsService: UserRightsService,
                private router: Router) {
    }

    ngOnInit(): void {

   this.records = ['20', '50', '100', '200', '250'];
        this.getLogisticHubPage(0);

    }

    doubleClick(lhDetail) {
        this.router.navigate(['/logistic-hub/doc-listing-edit/', lhDetail.id]);
    }


    onSelect(page: number): void {
        console.log('selected page : ' + page);
        this.selectedPage = page;
        this.getLogisticHubPage(page);
    }

    searchLh() {
        this.selectedPage = 1;
        console.log(this.searchText);
        this.getLogisticHubPage(this.selectedPage - 1);
    }

    getLogisticHubPage(page: number): void {
        this.logisticHubDocServiceService.getLHDetails(page, this.recordsPerPage, this.searchText)
            .subscribe(
                data => {
                    this.pageLogisticHub = data;


                }
            );
    }

    loadData(event: any) {
      console.log('pages ', event.target.value);
      this.recordsPerPage = event.target.value || 10;
      this.logisticHubDocServiceService.getLHDetails(this.selectedPage - 1, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageLogisticHub = page);
    }
  
    
    sortData(sort: Sort) {
        const data = this.pageLogisticHub.content.slice();
        if (!sort.active || sort.direction == '') {
          this.pageLogisticHub.content = data;
          return;
        }
      
        function compare(firstValue, secondValue, isAsc: boolean) {
          return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }
      
        this.pageLogisticHub.content = data.sort((firstValue, secondValue) => {
          const isAsc = sort.direction == 'asc';
          switch (sort.active) {
              case 'logisticHubName':
                return compare(firstValue.logisticHubName, secondValue.logisticHubName, isAsc);
            case 'regionName':
              return compare(firstValue.regionName, secondValue.regionName, isAsc);
            case 'primaryMobile':
              return compare(firstValue.primaryMobile, secondValue.primaryMobile, isAsc);
            default:
              return 0;
          }
        });
      }
      
}

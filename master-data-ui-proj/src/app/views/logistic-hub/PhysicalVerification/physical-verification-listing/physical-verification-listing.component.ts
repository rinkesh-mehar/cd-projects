import {Component, OnInit} from '@angular/core';
import {PhysicalVerificationServiceService} from '../physical-verification-service.service';
import {UserRightsService} from '../../../services/user-rights.service';
import {Router} from '@angular/router';
import {LhPage} from '../../model/lhPage';
import { Sort } from '@angular/material';

@Component({
    selector: 'app-physical-verification-listing',
    templateUrl: './physical-verification-listing.component.html',
    styleUrls: ['./physical-verification-listing.component.scss']
})
export class PhysicalVerificationListingComponent implements OnInit {
    pvCompletedLHDetailList: any;
    searchText: any = '';
    pageLogisticHub: LhPage;
    selectedPage: number;
    maxSize: number = 10;
    recordsPerPage: number = 10;
   records: any = [];


    constructor(private physicalVerificationServiceService: PhysicalVerificationServiceService,
                private userRightsService: UserRightsService,
                private router: Router) {
    }

    ngOnInit(): void {

   this.records = ['20', '50', '100', '200', '250'];
        this.getLogisticHubPage(0);
    }

    doubleClick(lhDetail) {
        this.router.navigate(['/logistic-hub/pv-images-listing-manage/', lhDetail.id]);
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
        this.physicalVerificationServiceService.getLHDetails(page, this.recordsPerPage, this.searchText)
            .subscribe(
                data => {
                    this.pageLogisticHub = data;
                }
            );
    }

    loadData(event: any) {
      console.log('pages ', event.target.value);
      this.recordsPerPage = event.target.value || 10;
      this.physicalVerificationServiceService.getLHDetails(this.selectedPage - 1, this.recordsPerPage, this.searchText)
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

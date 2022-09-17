import {Component, OnInit} from '@angular/core';
import {PageNewsReports} from '../../cropdata-portal/models/pageNewsReports';
import {LogistichubService} from '../service/logistichub.service';
import {LhPage} from '../model/lhPage';
import {UserRightsService} from '../../services/user-rights.service';
import { Sort } from '@angular/material';
import { globalConstants } from '../../global/globalConstants';

@Component({
    selector: 'app-logistichub',
    templateUrl: 'logistichub.component.html',
    styleUrls: ['./logistichub.component.css'],
})

export class LogistichubComponent implements OnInit{
    searchText: any = '';
    pageLogisticHub: LhPage;
    selectedPage: number;
    maxSize: number = 10;

recordsPerPage: number = 10;
records: any = [];
    constructor(private userRightsService: UserRightsService, private loaderService: LogistichubService) {
    }

    ngOnInit(): void {
      this.records = ['20', '50', '100', '200', '250'];
        this.getLogisticHubPage(0);
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
       this.loaderService.getCollectedPageLh(page, this.recordsPerPage, this.searchText)
           .subscribe(
               data => {
                   this.pageLogisticHub = data;


               }
           );
    }

    loadData(event: any) {
      console.log('pages ', event.target.value);
      this.recordsPerPage = event.target.value || 10;
      this.loaderService.getCollectedPageLh(this.selectedPage - 1, this.recordsPerPage, this.searchText)
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
            case globalConstants.ID:
              return compare(+firstValue.id, +secondValue.id, isAsc);
              case 'logisticHubName':
                return compare(firstValue.logisticHubName, secondValue.logisticHubName, isAsc);
            case 'regionName':
              return compare(firstValue.regionName, secondValue.regionName, isAsc);
            case 'primaryMobileNumber':
              return compare(firstValue.primaryMobileNumber, secondValue.primaryMobileNumber, isAsc);
            case 'stageName':
              return compare(firstValue.stageName, secondValue.stageName, isAsc);
            default:
              return 0;
          }
        });
      }
      

}
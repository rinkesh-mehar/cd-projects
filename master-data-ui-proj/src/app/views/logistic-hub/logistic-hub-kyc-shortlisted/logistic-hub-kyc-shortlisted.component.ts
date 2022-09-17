import { Component, OnInit } from "@angular/core";
import { Sort } from "@angular/material";
import { LogistichubService } from "../service/logistichub.service";

@Component({
    selector: 'appLogisticHubKycShortListed',
    templateUrl: './logistic-hub-kyc-shortlisted.component.html',
    styleUrls: ['./logistic-hub-kyc-shortlisted.component.scss']
})
export class LogisticHubShortlistedComponent implements OnInit {

    dashboardKycData: any = [];
    searchText: any = '';
    selectedPage: number = 1;
    maxSize =10;

    recordsPerPage: number = 10;
   records: any = [];

    constructor(private hubService: LogistichubService) { }

    ngOnInit() {
      this.records = ['20', '50', '100', '200', '250'];
        this.getShortlistedKycData(0);
    }

    getShortlistedKycData(page: number): void {
        this.hubService.getShortlistedKycDetails(page, this.recordsPerPage, this.searchText)
            .subscribe(data => {
                if (data) {
                   console.log(JSON.stringify(data));
                    this.dashboardKycData = data;
                }else{
                    console.log('No data found')
                }
            }, error => {
                console.log(error);
            });

    }

    loadData(event: any) {
      console.log('pages ', event.target.value);
      this.recordsPerPage = event.target.value || 10;
      this.hubService.getShortlistedKycDetails(this.selectedPage - 1, this.recordsPerPage, this.searchText)
        .subscribe(page => this.dashboardKycData = page);
    }

    sortData(sort: Sort) {
        const data = this.dashboardKycData.content.slice();
        if (!sort.active || sort.direction == '') {
          this.dashboardKycData.content = data;
          return;
        }
      
        function compare(firstValue, secondValue, isAsc: boolean) {
          return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }
      
        this.dashboardKycData.content = data.sort((firstValue, secondValue) => {
          const isAsc = sort.direction == 'asc';
          switch (sort.active) {
            case 'HubId':
              return compare(+firstValue.HubId, +secondValue.HubId, isAsc);
              case 'LogisticHubName':
                return compare(firstValue.LogisticHubName, secondValue.LogisticHubName, isAsc);
            case 'RegionName':
              return compare(firstValue.RegionName, secondValue.RegionName, isAsc);
            case 'Mobile':
              return compare(firstValue.Mobile, secondValue.Mobile, isAsc);
            default:
              return 0;
          }
        });
      }

      search() {
        this.selectedPage = 1;
        console.log(this.searchText);
        this.getShortlistedKycData(this.selectedPage - 1);
    }
      
    onSelect(page: number): void {
      // console.log('selected page : ' + page);
      this.selectedPage = page;
      this.getShortlistedKycData(page);
  }
  

}


import { Sort } from '@angular/material';
import { globalConstants } from '../../../global/globalConstants';
import {Component, OnInit, ViewChild} from '@angular/core';
import {MonthWbTravelTimeService} from '../../services/month-wb-travel-time.service';
import {PageRegionMonthWBTravelTime} from '../../models/PageRegionMonthWBTravelTime';
import {UserRightsService} from '../../../services/user-rights.service';
import {DrkServiceService} from '../../../services/drk-service.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';

@Component({
    selector: 'app-regional-month-wb-travel-time',
    templateUrl: './month-wb-travel-time.component.html',
    styleUrls: ['./month-wb-travel-time.component.css']
})
export class MonthWbTravelTimeComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;

    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    _statusMsg: string;
    regionMonthWBStatus;
    regionMonthWBTravelTimeList: any = [];
    pageRegionMonthWBTravelTimeList: PageRegionMonthWBTravelTime;
    selectedPage: number;
    searchText: any = '';
    maxSize = 10;
    isValid: number = 1;

    recordsPerPage: number = 10;
   records: any = [];

    constructor(private monthWbTravelTimeService: MonthWbTravelTimeService,
                private userRightsService: UserRightsService, private drkServiceService: DrkServiceService
    ) {
    }

    ngOnInit(): void {
      this.records = ['20', '50', '100', '200', '250'];
        this.getPageRegionMonthWBTravelTime(0, this.isValid);
    }

    getPageRegionMonthWBTravelTime(page: number, isValid: number) {
        this.monthWbTravelTimeService.getMonthWbTravelTimeWithPage(page, this.recordsPerPage, this.searchText, isValid)
            .subscribe(page => this.pageRegionMonthWBTravelTimeList = page);
    }

    loadData(event: any) {
      console.log('pages ', event.target.value);
      this.recordsPerPage = event.target.value || 10;
      this.monthWbTravelTimeService.getMonthWbTravelTimeWithPage(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid)
        .subscribe(page => this.pageRegionMonthWBTravelTimeList = page);
    }
  

    onSelect(page: number): void {
        console.log('selected page : ' + page);
        this.selectedPage = page;
        this.getPageRegionMonthWBTravelTime(page, this.isValid);
    }

    searchBank() {
        this.selectedPage = 1;
        console.log(this.searchText);
        this.getPageRegionMonthWBTravelTime(this.selectedPage - 1, this.isValid);
    }

    fixBug() {
        this.isValid = 0;
        this.drkServiceService.fixBug('regional_monthly_weather_based_travel_time').subscribe(res => {
            this.isSubmitted = true;
            if (res) {
                this.ngOnInit();
                this.isSuccess = res.success;
                if (res.success) {
                    this.successModal.showModal('SUCCESS', res.message, '');
                }
            }
        });
    }

    sortData(sort: Sort) {
        const data = this.pageRegionMonthWBTravelTimeList.content.slice();
        if (!sort.active || sort.direction == '') {
          this.pageRegionMonthWBTravelTimeList.content = data;
          return;
        }
      
        function compare(firstValue, secondValue, isAsc: boolean) {
          return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }
      
        this.pageRegionMonthWBTravelTimeList.content = data.sort((firstValue, secondValue) => {
          const isAsc = sort.direction == 'asc';
          switch (sort.active) {
            case globalConstants.ID:
              return compare(+firstValue.id, +secondValue.id, isAsc);
              case 'regionName':
                return compare(firstValue.regionName, secondValue.regionName, isAsc);
            case 'month':
              return compare(firstValue.month, secondValue.month, isAsc);
              case 'unitName':
              return compare(firstValue.unitName, secondValue.unitName, isAsc);
            default:
              return 0;
          }
        });
      }
}

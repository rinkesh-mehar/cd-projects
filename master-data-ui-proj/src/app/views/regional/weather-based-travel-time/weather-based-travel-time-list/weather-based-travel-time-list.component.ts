import { Sort } from '@angular/material';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { WeatherBasedTravelTimeService } from '../../services/weather-based-travel-time.service';
import { PageWeatherBasedTravelTime } from '../../models/PageWeatherBasedTravelTime';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import { BulkDataService } from '../../../agri/services/bulk-data.service';

@Component({
  selector: 'app-weather-based-travel-time-list',
  templateUrl: './weather-based-travel-time-list.component.html',
  styleUrls: ['./weather-based-travel-time-list.component.scss']
})
export class WeatherBasedTravelTimeListComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  travelTimeList: any = [];
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  varietyStatus;
  
  pageWeatherBasedTravelTime : PageWeatherBasedTravelTime;
  selectedPage : number = 1;
  searchText : any="";
  maxSize = 10;

  recordsPerPage: number = 10;
   records: any = [];

  constructor(public bulkDatas: BulkDataService,public weatherBasedTravelTimeService : WeatherBasedTravelTimeService,
    private userRightsService: UserRightsService,) { }

  ngOnInit(): void {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageWeatherBasedTravelTime(0);
  }


  loadAlllTravelTime() {
    return this.weatherBasedTravelTimeService.getAllTravelTime().subscribe((data: {}) => {
      this.travelTimeList = data;
    })
  }

  onSelect(page: number): void {
    this.getPageWeatherBasedTravelTime(page);
  }
  
  getPageWeatherBasedTravelTime(page:number): void {
    this.weatherBasedTravelTimeService.getPageWeatherBasedTravelTime(page, this.recordsPerPage,this.searchText)
        .subscribe(page => this.pageWeatherBasedTravelTime = page);
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.weatherBasedTravelTimeService.getPageWeatherBasedTravelTime(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageWeatherBasedTravelTime = page);
  }

  searchTravelTime() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageWeatherBasedTravelTime(this.selectedPage - 1);
}

sortData(sort: Sort) {
  const data = this.pageWeatherBasedTravelTime.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageWeatherBasedTravelTime.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageWeatherBasedTravelTime.content = data.sort((firstValue, secondValue) => {
    const isAsc = sort.direction == 'asc';
    switch (sort.active) {
      case globalConstants.ID:
        return compare(+firstValue.id, +secondValue.id, isAsc);
      case globalConstants.NAME:
        return compare(firstValue.name, secondValue.name, isAsc);
        case 'minPerKm':
        return compare(firstValue.minPerKm, secondValue.minPerKm, isAsc);
        case globalConstants.STATUS:
        return compare(firstValue.status, secondValue.status, isAsc);
      default:
        return 0;
    }
  });
}
bulkData(key,tableName){

  let Values = []
  let getValue = document.querySelectorAll<HTMLInputElement>('table tbody input:checked')
 
  getValue.forEach(function(data,i){
    Values.push(data.value)
  })
  let AllData = {status:key, tableName:tableName, ids:Values.toString()}

  this.bulkDatas.getData(AllData)
      .subscribe( data => {
        data
        if(data.success == true){
          this.successModal.showModal('SUCCESS', data.message, '');

        }else {
          this.errorModal.showModal('ERROR', data.error, '');

        }

      })

}
}

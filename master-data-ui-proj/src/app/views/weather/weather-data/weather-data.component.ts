import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { WeatherDataService } from '../services/weather-data.service';

@Component({
  selector: 'app-weather-data',
  templateUrl: './weather-data.component.html',
  styleUrls: ['./weather-data.component.scss']
})
export class WeatherDataComponent implements OnInit {

  yearList: any = [];
  monthList: any = [];
  weekList: any = [];


  ngOnInit() {
    this.getYear();
  }

  constructor(
    private actRoute: ActivatedRoute,
    public weatherDataService: WeatherDataService
  ) {
  }

  getYear() {
    return this.weatherDataService.GetYaer().subscribe((data) => {
      this.yearList = data;
    })
  }

  getMonthByYear(year, index) {
    return this.weatherDataService.GetMonthByYear(year).subscribe((data: {}) => {
      this.monthList = data;
      this.yearList[index].months = data;
    })
  }

  getWeeksByYearAndMonth(year,yearIndex, month,monthIndex) {
    return this.weatherDataService.GetWeekByYearAndMonth(year, month).subscribe((data: {}) => {
      this.weekList = data;
      this.yearList[yearIndex].months[monthIndex].week = data;

      console.log(this.yearList );
    })
  }

}

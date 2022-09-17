import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { GeoStateService } from '../../geo/services/geo-state.service';
import { AgriCommodityService } from '../../agri/services/agri-commodity.service';
import { AgriVarietyService } from '../../agri/services/agri-variety.service';
import { AgriSeasonService } from '../../agri/services/agri-season.service';
import { CropCalendarService } from '../service/crop-calendar-service';
import { GeoRegionService } from '../../geo/services/geo-region.service';
import { from } from 'rxjs';
import { cropCalendarConstants } from '../cropCalendarConstants';
@Component({
  selector: 'app-crop-calendar',
  templateUrl: './crop-calendar.component.html',
  styleUrls: ['./crop-calendar.component.scss']
})
export class CropCalendarComponent implements OnInit {

  calendarFilterForm: FormGroup;
  StateList: any = [];
  RegionList: any = [];
  CommodityList: any = [];
  VarietyList: any = [];
  SeasonList: any = [];

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  cropDataList: any;

  monthsColor: any;
  conduciveWeatherColor: any;
  phenophaseColor: any = []

  constructor(public fb: FormBuilder,
    public geoStateService: GeoStateService,
    public commodityService: AgriCommodityService,
    public agriVarietyService: AgriVarietyService,
    public agriSeasonService: AgriSeasonService,
    public cropCalendarService: CropCalendarService,
    public geoRegionService: GeoRegionService,
  ) {


    this.monthsColor = cropCalendarConstants.monthsColor
    this.conduciveWeatherColor = cropCalendarConstants.conduciveWeatherColor
    this.phenophaseColor = cropCalendarConstants.phenophaseColor

  }

  ngOnInit() {
    this.buildForm();
    this.loadAllState();
    //this.getCommodityByState();
    //this.loadAllVariety();
    //this.loadAllSeason();
  }

  buildForm() {
    this.calendarFilterForm = this.fb.group({
      stateCode: ['', Validators.required],
      // regionId: ['', Validators.required],
      commodityId: ['', Validators.required],
      varietyId: ['', Validators.required],
      seasonId: ['', Validators.required],

    })
  }

  ///-------------- Static Data Start-------------------------------------


  _months = [{ 'name': 'Jan', '_noWeek': 4, '_color': '#e8d0a8' },
  { 'name': 'Feb', '_noWeek': 4, '_color': '#b7afa2' },
  { 'name': 'Mar', '_noWeek': 4, '_color': '#c1dad6' },
  { 'name': 'Apr', '_noWeek': 5, '_color': '#f5fafa' },
  { 'name': 'May', '_noWeek': 4, '_color': '#acd1e9' },
  { 'name': 'Jun', '_noWeek': 4, '_color': '#6d929b' },
  { 'name': 'Jul', '_noWeek': 5, '_color': '#ebf4fa' },
  { 'name': 'Aug', '_noWeek': 4, '_color': '#e7e4d3' },
  { 'name': 'Sep', '_noWeek': 5, '_color': '#e6f1f7' },
  { 'name': 'Oct', '_noWeek': 4, '_color': '#d0ebdd' },
  { 'name': 'Nov', '_noWeek': 4, '_color': '#83bbc1' },
  { 'name': 'Dec', '_noWeek': 5, '_color': '#fec7c0' },
  { 'name': '', '_noWeek': '', '_color': '' },
  { 'name': '', '_noWeek': '', '_color': '' }

  ];

  _param = [{ 'param': 'week' },
  { 'param': 'RF (mm)' },
  { 'param': 'T-Min (°C)' },
  { 'param': 'T-Max (°C)' },
  { 'param': 'T-Mean (°C)' },
  { 'param': 'SSH (hrs)' },
  { 'param': 'EVP (mm)' },
  { 'param': 'RH-Min (%)' },
  { 'param': 'RH-Max (%)' },
  { 'param': 'RH-Mean (%)' },
  { 'param': 'WS (Km/hr)' },
  { 'param': 'DP' },
  { 'param': 'WM (%)' },
  { 'param': 'Phenophase' }
  ];

  arrayOne(n: number): any[] {
    return Array(n);
  }

  getParam(i: number) {
    return Object.values(this._param)[i].param;
  }

  //----------------Static Data End-------------------------------


  print() {
    window.print();
  }


  submitForm() {


    for (let controller in this.calendarFilterForm.controls) {
      this.calendarFilterForm.get(controller).markAsTouched();
    }

    if (this.calendarFilterForm.invalid) {
      return;
    }

    this.cropCalendarService.GetCropCalendarList(this.calendarFilterForm.value).subscribe(data => {

      if (data && data.generalDto) {
        
        this.cropDataList = data;
        let weeks: any = [];
        if (this.cropDataList.generalDto) {
          for (let month in this.cropDataList.generalDto.months) {
            weeks = weeks.concat(this.cropDataList.generalDto.months[month]);
          }
        }
        this.cropDataList.generalDto.weeks = weeks;
        let phophaseList: any = [];
        let favourableWeatherList = [];
        for (let week of this.cropDataList.generalDto.weeks) {
          for (let phophase of this.cropDataList.phophaseList) {
            if (week == phophase.phenophaseStart) {
              phophaseList.push(phophase);
            }
          }
          for (let favourableWeather of this.cropDataList.favourableWeatherList) {
            if (week == favourableWeather.startWeek) {
              favourableWeatherList.push(favourableWeather);
            }
          }
        }
        this.cropDataList.phophaseList = phophaseList;
        this.cropDataList.favourableWeatherList = favourableWeatherList;
        console.log(this.cropDataList)
      } else {
        this.isSubmitted = true;
        this.isSuccess = false;
        this._statusMsg = "Data not avilable for given filters";

        setTimeout(() => {
          this.isSubmitted = false;
          this.isSuccess = false;
          this._statusMsg = ""
        }, 4000);

      }
    })

  }

  //State list
  loadAllState() {
    return this.cropCalendarService.GetAvailableStates().subscribe((data: {}) => {
      this.StateList = data;
    })
  }

  //Commodity list
  getCommodityByState(stateCode) {
    return this.cropCalendarService.getCommodityByState(stateCode).subscribe((data: {}) => {
      this.CommodityList = data;
    })
  }

  //variety list
  getVarietyByStateAndCommodity(stateCode,commodityId) {
    return this.cropCalendarService.getVarietyByStateAndCommodity(stateCode,commodityId).subscribe((data: {}) => {
      this.VarietyList = data;
    })
  }

  // season list
  loadAllSeason(stateCode,commodityId,varietyId) {
    return this.cropCalendarService.GetAvailableSeason(stateCode,commodityId,varietyId).subscribe((data: {}) => {
      this.SeasonList = data;
    })
  }

  // getRegion(_data: number) {
    // this.calendarFilterForm.get('regionId').setValue("");
    // this.calendarFilterForm.get('commodityId').setValue("");
    // this.calendarFilterForm.get('varietyId').setValue("");
    // this.calendarFilterForm.get('seasonId').setValue("");

    // this.geoRegionService.GetAllRegionByStateCode(_data).subscribe((data: {}) => {
      // this.RegionList = data;
    // })
  // }

  checkPhenophse(week, p, flag, weekIndex) {
    if (this.cropDataList.phophaseList && this.cropDataList.phophaseList.length > 0) {
      if (flag) {
        if (p > 0) {
          if ((weekIndex < this.cropDataList.generalDto.weeks.indexOf(this.cropDataList.phophaseList[p].phenophaseStart))
            && (weekIndex > this.cropDataList.generalDto.weeks.indexOf(this.cropDataList.phophaseList[p - 1].phenophaseEnd))) {
            return true;
          } else {
            return false
          }
        } else {
          if (weekIndex < this.cropDataList.generalDto.weeks.indexOf(this.cropDataList.phophaseList[p].phenophaseStart)) {
            return true;
          } else {
            return false;
          }
        }
      } else {
        let phenophaseEndIndex = this.cropDataList.generalDto.weeks.indexOf(this.cropDataList.phophaseList[this.cropDataList.phophaseList.length - 1].phenophaseEnd);
        if (phenophaseEndIndex > -1 && (weekIndex > phenophaseEndIndex)) {
          return true;
        } else {
          return false;
        }
      }
    } else {
      return true;
    }
  }

  checkFavourableWeather(week, p, flag, weekIndex) {


    if (this.cropDataList.favourableWeatherList && this.cropDataList.favourableWeatherList.length > 0) {
      if (flag) {
        if (p > 0) {
          if ((weekIndex < this.cropDataList.generalDto.weeks.indexOf(this.cropDataList.favourableWeatherList[p].startWeek))
            && (weekIndex > this.cropDataList.generalDto.weeks.indexOf(this.cropDataList.favourableWeatherList[p - 1].endWeek))) {
            return true;
          } else {
            return false
          }
        } else {
          if (weekIndex < this.cropDataList.generalDto.weeks.indexOf(this.cropDataList.favourableWeatherList[p].startWeek)) {
            return true;
          } else {
            return false;
          }
        }
      } else {
        let endWeekIndex = this.cropDataList.generalDto.weeks.indexOf(this.cropDataList.favourableWeatherList[this.cropDataList.favourableWeatherList.length - 1].endWeek);
        if (endWeekIndex > -1 && (weekIndex > endWeekIndex)) {
          return true;
        } else {
          return false;
        }
      }
    } else {
      return true
    }


  }


  getKey(obj) {
    return Object.keys(obj);
  }

  getspecificationValue(obj) {

    let result = "";
    if (obj) {
      if (obj.specificationAverage && obj.specificationAverage > 0) {
        result = obj.specificationAverage
      } else {
        result = obj.specificationLower + "-" + obj.specificationUpper
      }
    }
    return result
  }


}

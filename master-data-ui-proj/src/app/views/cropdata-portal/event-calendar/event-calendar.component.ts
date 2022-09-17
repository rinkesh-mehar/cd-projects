import { globalConstants } from './../../global/globalConstants';
import { MatDatepickerInputEvent, Sort } from '@angular/material';
import {Component, Input, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { PageCropdataCalendar } from '../models/page-cropdata-calendar';
import { EventCalendarService } from '../services/event-calendar.service';
import * as moment from 'moment';

@Component({
  selector: 'app-event-calendar',
  templateUrl: './event-calendar.component.html',
  styleUrls: ['./event-calendar.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class EventCalendarComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  eventCalendarForm: FormGroup;

  regionList: any =[];
  holidayList: any = [];
  holidayDateList: string[] = [];

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  pageCropdataCalendar: PageCropdataCalendar;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;

  holidayStatus;

  recordsPerPage: number = 10;
  records: any = [];

  events: string[] = [];
  holidayListnew: any = [];

  mode: string = "add";

  dt : string;

  @Input() max: any;
  previous = new Date();

  constructor(public fb: FormBuilder, private eventCalendarService: EventCalendarService, private userRightsService: UserRightsService) {}

  

  ngOnInit(): void {

    // this.dateCl = (d: Date) => {
    //   return undefined;
    // };

    this.records = ['20', '50', '100', '200', '250'];
    this.getAllGeoRegion();
    this.addEventCalendar();
    this.getHolidayListByPagenation(0);
    this.holidayStatus = globalConstants;
  }

  addEventCalendar() {
    this.eventCalendarForm = this.fb.group({
      id: [],
      regionID: ['', Validators.required],
      holidayDate: [moment(), Validators.required],
      name: ['', Validators.required],
      description: ['']
      
    })
  }

  editEventValendar(data) {
    this.eventCalendarForm.patchValue(data);
    
    this.dt = data.holidayDate;
    let splitDate = this.dt.split('-', 3);
    let day = splitDate[0];
    let month = splitDate[1];
    let year = splitDate[2];
    let date = year + '-' + month + '-' + day;
    // console.log("date : " + date);
    let convertedHolidayDate = new Date(date);
    // console.log("convertedHolidayDate : " + convertedHolidayDate);
    this.eventCalendarForm.patchValue({holidayDate: convertedHolidayDate});
    this.mode = "edit";

}

  submitForm(){

    console.log("inside submitForm");
    for(let controller in this.eventCalendarForm.controls){

      this.eventCalendarForm.get(controller).markAsTouched();

    }


    if(this.eventCalendarForm.invalid){
      console.log("inside 1st if");
      return;
    }
    // console.log("Date : " + this.eventCalendarForm.value.holidayDate);


  
    const date = new Date(this.eventCalendarForm.value.holidayDate);
    const day = date.getDate();
    const month = date.getMonth() + 1;
    const year = date.getFullYear();
    const newHolidayDate = day + "-" + month + "-" + year;
  
    
    // console.log("newHolidayDate : " + newHolidayDate);
  
    if (this.mode == 'add') {
      this.checkHolidayAlreadyExist(this.eventCalendarForm.value.regionID, newHolidayDate);
    } else {
      this.update();
    }

  }

  checkHolidayAlreadyExist(regionID, newHolidayDate){
     this.eventCalendarService.checkHolidayAlreadyExist(regionID, newHolidayDate).subscribe(data => {
      // console.log("data : " + data);
      if(data === 1){
        this.errorModal.showModal('ERROR', 'Holiday Already Exist For Selected Date.', '');
        return;
      }else{
          this.add();
      }
      });
  }

add(){

  return this.eventCalendarService.addHoliday(this.eventCalendarForm.value).subscribe(res => {
  
    this.isSubmitted = true;
   
    if(res) {
      
      this.isSuccess = res.success;
      if (res.success) {
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
      
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  });

}

update() {
  this.eventCalendarService.updateHoliday(this.eventCalendarForm.value.id, this.eventCalendarForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
          this.isSuccess = res.success;
          if (res.success) {
              this.getHolidayListByPagenation(0);
              this.eventCalendarForm.reset();
              this.mode = "add";
              this.successModal.showModal('SUCCESS', res.message, '');
          } else {
              this.errorModal.showModal('ERROR', res.error, '');
          }
      }
  })
}

dateClass = (dt: Date) => {

  // console.log("inside date class");
  const d = new Date(dt);
  const day = d.getDate();
  const month = d.getMonth() + 1;
  const year = d.getFullYear();
  const date = day + "-" + month + "-" + year;

  // console.log("size : " + this.holidayDateList.length);
  
  for (let i = 0; i < this.holidayDateList.length; i++) {
    // console.log (i + " : "+ date + " : " + this.holidayDateList[i]);
    if(date === this.holidayDateList[i]){
    // Highlight the holiday.
    return 'example-custom-date-class';
    }
  }

};


loadData(event: any) {
// console.log('pages ', event.target.value);

this.recordsPerPage = event.target.value || 10;

console.log("Region Id : " + this.eventCalendarForm.value.regionID);

if(this.eventCalendarForm.value.regionID === ''){
  console.log("Inside if");
  this.eventCalendarService.getHolidayListByPagenation(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    .subscribe(page => this.pageCropdataCalendar = page);

}else{
  console.log("Inside else");
  this.eventCalendarService.getHolidayListByRegionId(this.selectedPage - 1, this.recordsPerPage, this.eventCalendarForm.value.regionID, this.searchText)
      .subscribe(page => this.pageCropdataCalendar = page);
  
}
}

onSelect(page: number): void {

  // console.log('selected page : ' + page);
  this.selectedPage = page;
  console.log("Region Id : " + this.eventCalendarForm.value.regionID);
  if(this.eventCalendarForm.value.regionID === ''){
    console.log("Inside if");
  this.getHolidayListByPagenation(page);
}else{
  console.log("Inside else");
  this.selectedPage = 1;
  this.getHolidayListByRegionId(page, this.eventCalendarForm.value.regionID);
}

}

searchHoliday() {
  
  this.selectedPage = 1;
  console.log(this.searchText);
  console.log("Region Id : " + this.eventCalendarForm.value.regionID);
  if(this.eventCalendarForm.value.regionID === ''){
    console.log("Inside if");
    this.getHolidayListByPagenation(this.selectedPage - 1);
  }else{
    console.log("Inside else");
    this.selectedPage = 1;
    this.getHolidayListByRegionId(this.selectedPage - 1, this.eventCalendarForm.value.regionID);
}

}

getHolidayListByPagenation(page: number): void {
  this.eventCalendarService.getHolidayListByPagenation(page, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageCropdataCalendar = page);
}

getHolidayListByRegionId(page: number, regionId: number): void {
  this.eventCalendarService.getHolidayListByRegionId(page, this.recordsPerPage, regionId, this.searchText)
      .subscribe(page => this.pageCropdataCalendar = page);
}

getAllGeoRegion() {
  return this.eventCalendarService.getAllGeoRegion().subscribe((data: {}) => {
      this.regionList = data;

  });
}

getHolidayDateList(regionId: number) {
  this.eventCalendarService.getHolidayDateList(regionId).subscribe(res => {
      this.holidayDateList = res;
  }, err => {
    console.log('error ', err);
  });
}

onChangeRegion(){
   
  this.selectedPage = 1;
  // console.log("region Id : " + this.eventCalendarForm.value.regionID);
  this.getHolidayListByRegionId(this.selectedPage - 1, this.eventCalendarForm.value.regionID);
  this.getHolidayDateList(this.eventCalendarForm.value.regionID);
}

trimValue(formControl) { 
  // console.log("inside trim");
  formControl.setValue(formControl.value.trim()); 
 }

 onChangeHolidayDate(){

  // console.log("region Id : " + this.eventCalendarForm.value.regionID);
  // console.log("holidayDate : " + this.eventCalendarForm.value.holidayDate);
  
  if(this.eventCalendarForm.value.regionID !== ''){

    // console.log("inside if");

  const date = new Date(this.eventCalendarForm.value.holidayDate);
  const day = date.getDate();
  const month = date.getMonth() + 1;
  const year = date.getFullYear();
  const newHolidayDate = day + "-" + month + "-" + year;

  
  // console.log("newHolidayDate : " + newHolidayDate);

  this.eventCalendarService.checkHolidayAlreadyExist(this.eventCalendarForm.value.regionID, newHolidayDate).subscribe(data => {
  // console.log("data : " + data);
  if(data === 1){
    this.errorModal.showModal('ERROR', 'Holiday Already Exist For Selected Date.', '');
    return;
  }
  });
  
  }
}

 active(data, i) {
  data.index = i;
  data.flag = "active"
  this.confirmModal.showModal(globalConstants.activeDataTitle, globalConstants.activeDataMsg, data);
}

deactive(data, i) {
  data.index = i;
  data.flag = "deactive"
  this.confirmModal.showModal(globalConstants.deactiveDataTitle, globalConstants.deactiveDataMsg, data);
}

activateHoliday(event) {
  return this.eventCalendarService.activateHoliday(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.holidayListnew.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}

deactivateHoliday(event) {
  return this.eventCalendarService.deactivateHoliday(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.holidayListnew.splice(event.index, 1);
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
      this.activateHoliday(event);
    } else if (event.flag == "deactive") {
      this.deactivateHoliday(event);
    }
  }
}

modalSuccess($event: any) {
  this.ngOnInit();
 }

sortData(sort: Sort) {
  const data = this.pageCropdataCalendar.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageCropdataCalendar.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    console.log(firstValue  + " : " + secondValue);
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageCropdataCalendar.content = data.sort((firstValue, secondValue) => {
    const isAsc = sort.direction == 'asc';
    switch (sort.active) {
      case globalConstants.ID:
        return compare(+firstValue.id, +secondValue.id, isAsc);
      case 'region':
        return compare(firstValue.region, secondValue.region, isAsc);
      case globalConstants.NAME:
        return compare(firstValue.name, secondValue.name, isAsc);
      case 'description':
        return compare(firstValue.description, secondValue.description, isAsc);
      case 'holidayDate':
        return compare(new Date(firstValue.holidayDate.split('-')[2] + '-' + firstValue.holidayDate.split('-')[1] + '-' + firstValue.holidayDate.split('-')[0]), new Date(secondValue.holidayDate.split('-')[2] + '-' + secondValue.holidayDate.split('-')[1] + '-' + secondValue.holidayDate.split('-')[0]), isAsc);
      // case 'holidayDate':
      // return compare(new Date(firstValue.holidayDate), new Date(secondValue.holidayDate), isAsc);
      // case 'holidayDate':
      //   return compare(firstValue.holidayDate.indexOf('-') == 1 ? (0 + firstValue.holidayDate).lastIndexOf('-') == 4 ? ((0 + firstValue.holidayDate).substr(0,4-1) + 0 + (0 + firstValue.holidayDate).substr(3)) : (0 + firstValue.holidayDate) : firstValue.holidayDate.lastIndexOf('-') == 4 ? (firstValue.holidayDate.substr(0,4-1) + 0 + firstValue.holidayDate.substr(3)) : firstValue.holidayDate 
      //   , secondValue.holidayDate.indexOf('-') == 1 ? (0 + secondValue.holidayDate).lastIndexOf('-') == 4 ? ((0 + secondValue.holidayDate).substr(0,4-1) + 0 + (0 + secondValue.holidayDate).substr(3)) : (0 + secondValue.holidayDate) : secondValue.holidayDate.lastIndexOf('-') == 4 ? (secondValue.holidayDate.substr(0,4-1) + 0 + secondValue.holidayDate.substr(3)) : secondValue.holidayDate, isAsc);
      case globalConstants.STATUS:
        return compare(firstValue.status, secondValue.status, isAsc);
      default:
        return 0;
    }
  });
}

onCancelClick(){
  this.ngOnInit();
  this.mode = "add";
}

}

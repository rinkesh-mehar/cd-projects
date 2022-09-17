import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, NavigationEnd } from "@angular/router";
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { SuccessModalComponent } from '../../../modal-components/success-modal/success-modal.component';
import { VehicleScheduleCalendar, Useraccordion, searchDate } from './vehicle-schedule-list.model';
import { VehicleScheduleListService } from './vehicle-schedule-list.service';
import { ErrorMessages } from '../../../form-validation-messages';
declare var $, slick;


@Component({
  selector: 'app-vehicle-schedule-list',
  templateUrl: './vehicle-schedule-list.component.html',
  styleUrls: ['./vehicle-schedule-list.component.less']
})
export class VehicleScheduleListComponent implements OnInit {
  vehicleScheduleFilterForm: FormGroup;
  public vehicleSchedulelist: VehicleScheduleCalendar[];
  public useraccordion: Useraccordion[];
  status: boolean = false;
  submitted = false;
  modalRef: BsModalRef;
  public taskdateList: searchDate;
  public loggedInUser = {} as any;
  public scheduleDateRange: Date;
  public showError: boolean = false;
  public userId;
  public startDate: string;
  public startDate2: string;
  public endDate: string;
  public currentDate: string;
  noRecords: boolean = false;
  isData: boolean = false;
  minDate: Date;
  public httperrorresponsemessages: string;

   //Error Messages
   public calndererrormessage: string;


  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private vehicleScheduleListService: VehicleScheduleListService,
    private modalService: BsModalService,
  ) { }

  ngOnInit() {

    this.calndererrormessage = ErrorMessages.calnderError;
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

    this.userId = localStorage.getItem('loginUserid');
    this.loggedInUser = JSON.parse(localStorage.getItem("userData"));
    this.scheduleDateRange = new Date(this.loggedInUser.date);
    this.minDate = new Date(this.loggedInUser.date) ; 
    this.minDate.setDate(this.minDate.getDate());
    this.startDate = this.formatDate(this.scheduleDateRange);
    this.endDate = this.formatDate(this.scheduleDateRange.setDate(this.scheduleDateRange.getDate() + 3));
    this.currentDate = this.formatDate(this.minDate);

    this.vehicleScheduleFilterForm = this.formBuilder.group({
      taskdate: ['', Validators.required]
    });
    this.getvehicleScheduledList();

  }



  slideConfig = {
    "autoplay": false,
    "dots": false,
    "infinite": false,
    "arrows": true,
    "slidesToShow": 4,
    "slidesToScroll": 1,
    "prevArrow": '<div class="slider-prev"></div>',
    "nextArrow": '<div class="slider-next"></div>',

    "responsive": [
      {
        breakpoint: 1200,
        settings: {
          slidesToShow: 3,
        }
      },
      {
        breakpoint: 991,
        settings: {
          slidesToShow: 2,
        }
      }
    ]

  };


  get f() { return this.vehicleScheduleFilterForm.controls; }

  vehicleScheduleFilter() {
    this.submitted = true;

    if (!this.vehicleScheduleFilterForm.invalid) {
      this.taskdateList = this.vehicleScheduleFilterForm.value.taskdate;
      this.isData = true;


      let obj = this.vehicleScheduleFilterForm.value;

      if (obj.taskdate != null && obj.taskdate != '' && obj.taskdate.length == 2) {

        if ((obj.taskdate[1].getTime() - obj.taskdate[0].getTime()) >= 259200000 && ((obj.taskdate[1].getTime() - obj.taskdate[0].getTime()) <= 1296000000)) {
          this.showError = false;
          this.startDate = this.formatDate(this.taskdateList[0]);
          this.endDate = this.formatDate(this.taskdateList[1]);
          this.getvehicleScheduledList();

        } else {
          this.showError = true;
          return;
        }
      }
    }else{
      return;
    }


  }




  public getvehicleScheduledList() {

    this.vehicleScheduleListService.getvehicleScheduleList(this.loggedInUser.regionId, this.startDate, this.endDate).subscribe(
      (data) => {

        this.vehicleSchedulelist = data;
        
        setTimeout(() => {
          $('.carousel').slick('refresh');
        }, 220);

        if (this.isData) {
          this.isData = false;
          if (this.vehicleSchedulelist[0].useraccordion.length == 0
            && this.vehicleSchedulelist[1].useraccordion.length == 0
            && this.vehicleSchedulelist[2].useraccordion.length == 0
            && this.vehicleSchedulelist[3].useraccordion.length == 0) {
            this.noRecords = true;
          } else {
            this.noRecords = false;
          }
        }

      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/vehicle-schedule-list"
        }
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false })
      }
    )
  }

  formatDate(date) {
    var d = new Date(date),
      month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear();

    if (month.length < 2)
      month = '0' + month;
    if (day.length < 2)
      day = '0' + day;

    return [day, month, year].join('-');
  }
}

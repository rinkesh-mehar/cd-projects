import { Component, OnInit, TemplateRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { SuccessModalComponent } from '../../../../modal-components/success-modal/success-modal.component';
import { DRKrishiTechnicalForm, Cropinformation, Irrigationdetails, Fertilizer, Seedtreatment, Remedialmeasure, Ndviimage, Schedule, saveSchedule, callingStatusModel, ndviImages } from './technical-form.model';
import { DrKrishiTechnicalFormService } from './technical-form.service';
import { Router, NavigationEnd, ActivatedRoute } from "@angular/router";
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { ErrorMessages } from '../../../../form-validation-messages'
declare var $;



@Component({
  selector: 'app-technical-form',
  templateUrl: './technical-form.component.html',
  styleUrls: ['./technical-form.component.less']
})
export class TechnicalFormComponent implements OnInit {
  dateCustomClasses: DatepickerDateCustomClasses[];
  minDate: Date;
  maxDate: Date;
  // maxDate: Date;
  currentDate: Date = new Date();
  modalRef: BsModalRef;
  Casedetails_sample: Object;
  ScheduleForm: FormGroup;
  myDateValue: Date;
  submitted = false;
  status: string;
  public technicalForm = {} as DRKrishiTechnicalForm;
  public cropinfo = {} as Cropinformation;
  public ndviimages = {} as ndviImages;
  public irrigationdetails = {} as Irrigationdetails;
  public fertilizer = {} as Fertilizer;
  public seedtreatment = {} as Seedtreatment;
  public remedialmeasure = {} as Remedialmeasure;
  public ndviimage = {} as Ndviimage;
  public schedulecalendar = {} as Schedule;
  public savescheduledata = {} as saveSchedule;
  public callingstatus = {} as callingStatusModel;
  public isVisible: boolean = false;

  public taskId; //get this task id from 1st page
  public userId;
  public caseId;
  public currentYear;
  public currentWeek;

  public loggedInUser = {} as any;
  public currentSystemDate: Date;

  dateSelected = [];
  selectedClass = [];

  getDateItem(date: Date): string {
    return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`
  }

  //Error Messages
  public textboxerrormessage: string;
  public dropdownerrormessage: string;
  public checkboxerrormessage: string;
  public invalidvalueerrormessage: string;
  public multiselecterrormessage: string;
  public httperrorresponsemessages: string;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private modalService: BsModalService,
    private drKrishiTechnicalService: DrKrishiTechnicalFormService,
    private route: ActivatedRoute
  ) {
    this.minDate = new Date();
    this.minDate.setDate(this.minDate.getDate());
  }

  ngOnInit() {

    const routeparam = this.route.params.subscribe(params => {
      this.taskId = params['taskId'];
    });
    this.userId = localStorage.getItem('loginUserid');
    //Error Messages
    this.textboxerrormessage = ErrorMessages.textboxError;
    this.dropdownerrormessage = ErrorMessages.dropdownError;
    this.checkboxerrormessage = ErrorMessages.checkboxError;
    this.invalidvalueerrormessage = ErrorMessages.invalidvalueError;
    this.multiselecterrormessage = ErrorMessages.multiselectError;
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;



    this.loggedInUser = JSON.parse(localStorage.getItem("userData"));
    this.currentSystemDate = new Date(this.loggedInUser.date);
    this.minDate = new Date(this.loggedInUser.date);
    this.minDate.setDate(this.minDate.getDate());
    this.dateSelected = [this.currentSystemDate];

    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0)
    });


    this.myDateValue = new Date();

    this.getDrKrishiTechnicalFormService();

    this.savescheduledata.callingstatus = "Scheduled Visit";
    this.ScheduleForm = this.formBuilder.group({
      callingstatus: ["1"],
      comments: ['', [Validators.required, Validators.pattern("[^ ](.*|\n|\r|\r\n)*")]],
      dateschedule: ["", Validators.required],
      visitrequired: [false]
    });
    //this.selectChangeHandler(event);
  }
  get f() { return this.ScheduleForm.controls; }

  public getDrKrishiTechnicalFormService() {

    this.drKrishiTechnicalService.getDrKrishiTechnicalFormService(this.taskId).subscribe(

      (data) => {
        if (data.status != 200) {
          const initialState = {
            title: "Error",
            content: 'Error occurred during Dr.Krishi Technical - Call Center Tele-Caller details, To continue please contact system admin',
            action: "/dr-krishi-technical-form/" + this.taskId
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        }
        this.technicalForm = data.data
        this.caseId = this.technicalForm.caseId;
        this.currentYear = this.technicalForm.currentYear;
        this.currentWeek = this.technicalForm.currentWeek;
        this.cropinfo = this.technicalForm.cropinformation;
        this.irrigationdetails = this.technicalForm.irrigationdetails;
        this.fertilizer = this.technicalForm.fertilizer;
        this.seedtreatment = this.technicalForm.seedtreatment;
        this.remedialmeasure = this.technicalForm.remedialmeasure;
        this.schedulecalendar = this.technicalForm.schedule;
        const scheduledDate = new Date(this.schedulecalendar.dateschedule);

        // Inital code
        // this.minDate = new Date(scheduledDate);
        // this.maxDate = new Date(scheduledDate);
        // this.minDate.setDate(this.minDate.getDate());
        // this.maxDate.setDate(this.maxDate.getDate() + 30);
        //this.dateSelected = [scheduledDate];

        // Chnages done to fix the UAT bug id CDT1-1794690 start
        if(scheduledDate < this.currentSystemDate){
          this.minDate = new Date(this.currentSystemDate);
          this.maxDate = new Date(this.currentSystemDate);
        }else{
          this.minDate = new Date(this.currentSystemDate);
          this.maxDate = new Date(scheduledDate);
        }
        this.minDate.setDate(this.minDate.getDate());
        this.maxDate.setDate(this.maxDate.getDate() + 30);
        this.dateSelected = [this.currentSystemDate];
        // Chnages done to fix the UAT bug id CDT1-1794690 end

        this.isVisible = true;
        //  this.maxDate = new Date(scheduledDate);
        //  this.maxDate.setDate(this.maxDate.getDate() + 30);

        if(this.technicalForm.type == 'Verification'){
          this.dateSelected = [this.currentSystemDate];
          this.minDate = new Date(this.currentSystemDate);
          this.maxDate = new Date(this.minDate);
          this.minDate.setDate(this.minDate.getDate() + 1);
          this.maxDate.setDate(this.maxDate.getDate() + 30);

        }
        this.dateCustomClasses = [
          { date: scheduledDate, classes: ['scheduled-date'] }

        ];
        this.getNDVIimages();

      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/dr-krishi-technical-form/" + this.taskId
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }
  public ndvipopup(ndviimageModal: TemplateRef<any>, url) {
    const initialState = {
      heading: "NDVI Image",
      content: url,
    };
    this.modalRef = this.modalService.show(ndviimageModal, { initialState, class: 'image-popup-modal', backdrop: 'static', keyboard: false });

  }

  onSubmit() {

    // alert("hhsa");




    this.submitted = true;
    //this.savescheduledata.dateschedule = this.formatDate(this.ScheduleForm.value.dateschedule);
    this.savescheduledata.callingstatus = this.ScheduleForm.value.callingstatus;
    this.savescheduledata.visitrequired = this.ScheduleForm.value.visitrequired;
    this.savescheduledata.comments = this.ScheduleForm.value.comments;
    this.savescheduledata.taskid = this.taskId;
    this.savescheduledata.userid = this.userId;
    this.savescheduledata.type = this.technicalForm.type;
    let array = []
    if(this.schedulecalendar.dateschedule != null && this.schedulecalendar.dateschedule != undefined) {
      if( Array.isArray(this.schedulecalendar.dateschedule)) {
        for(let i =0; i<this.ScheduleForm.value.dateschedule.length; i++) {
          array.push(this.formatDate(this.ScheduleForm.value.dateschedule[i]))

        }
      } else {
        array.push(this.formatDate(this.ScheduleForm.value.dateschedule))
      }
    }

    this.savescheduledata.dateschedule = array;

    // stop here if form is invalid
    if (!this.ScheduleForm.invalid) {
      this.drKrishiTechnicalService.submitDrKrishiTechnicalForm(this.savescheduledata).subscribe(
        (data) => {

          if (data.status == 200) {
            if(this.technicalForm.type == 'Verification'){
              const initialState = {
                title: "Success",
                content: data.message,
                action: "/dr-krishi-non-technical-list"
              };
              this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
              return;
            }else{
              const initialState = {
                title: "Success",
                content: data.message,
                action: "/dr-krishi-technical-list"
              };
              this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
              return;
            }


          } else {
            const initialState = {
              title: "Error",
              content: data.message,
              action: "/dr-krishi-technical-form/" + this.taskId
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
            return;
          }
        }, (err) => {
          const initialState = {
            title: "Error",
            content: this.httperrorresponsemessages,
            action: "/dr-krishi-technical-form/" + this.taskId
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        });
    } else {
      var headerHeight = $("header").height();
      $('html,body').animate({ scrollTop: $('.form-control.ng-invalid:visible:first').offset().top - headerHeight - 30 }, 200);
      return;
    }

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
  close() {
    this.modalRef.hide();
  }

  counter = 0;
  onValueChange(event) {

    if (this.dateSelected[0] == this.currentSystemDate) {
      this.dateSelected.splice(0, 1);
    }

    this.counter = this.counter + 1;
    if (this.counter > 2 && event.length != 0) {


      if (event.length === undefined) {
        const date = this.getDateItem(event);

        const index = this.dateSelected.findIndex(item => {
          const testDate = this.getDateItem(item);
          return testDate === date;
        });


        if (index < 0) {
          this.dateSelected.push(event);
        }
        else {
          this.dateSelected.splice(index, 1);
        }
      }


      if (this.dateSelected.length >= 0) {
        this.dateCustomClasses = this.dateSelected.map(date => {
          return {
            date,
            classes: ['custom-selected-date']
          }
        });

      }
      this.ScheduleForm.get('dateschedule').setValue(this.dateSelected);
    }

  }


  //event handler for the select element's change event
  selectChangeHandler(event: any) {
    this.status = event.target.value;
  }
  sliderImage = {
    "slidesToShow": 5,
    "slidesToScroll": 5,
    "arrows": true,
    "nextArrow": "<div class='nav-btn flip-next-slide'></div>",
    "prevArrow": "<div class='nav-btn flip-prev-slide'></div>",
    "dots": false,
    "infinite": false,
  };

  cancelTechForm(){
    if(this.technicalForm.type == 'Verification'){
      this.router.navigate(['/dr-krishi-non-technical-list'])
    }else{
      this.router.navigate(['/dr-krishi-technical-list'])
    }
  }


  public getNDVIimages() {
    this.drKrishiTechnicalService.getNDVIimages(this.caseId, this.currentYear, this.currentWeek, this.loggedInUser.cropDataApiKey).subscribe(
      (data) => {
        this.ndviimages = data;
      }, (err) => {
        const initialState = {
          title: "Error",
          content: 'There is an error loading NDVI images. Please contact your system administrator.',
          action: "/dr-krishi-technical-form/" + this.taskId
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }


}

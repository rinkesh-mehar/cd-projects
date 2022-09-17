import { ActivatedRoute } from '@angular/router';
import { DrKrishiTechnical } from '../technical-list/dr-krishi-cctc-technical.model';
import { SuccessModalComponent } from '../../../../modal-components/success-modal/success-modal.component';
import { HarvestMonitoringForm, RightDeatils, GeneralInformation } from './harvest-monitoring.model';
import { HarvestMonitoringService } from './harvest-monitoring.service';
import {
  Schedule,
  saveSchedule,
  callingStatusModel,
  DRKrishiTechnicalForm,
  Cropinformation,
  ContactInformation, RightInformation, Calender
} from '../technical-form/technical-form.model';
import { Router } from '@angular/router';
import { ErrorMessages } from '../../../../form-validation-messages';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-harvest-monotoring',
  templateUrl: './harvest-monotoring.component.html',
  styleUrls: ['./harvest-monotoring.component.less']
})
export class HarvestMonotoringComponent implements OnInit {

  //Error Messages
  public textboxerrormessage: string;
  public dropdownerrormessage: string;
  public checkboxerrormessage: string;
  public invalidvalueerrormessage: string;
  public multiselecterrormessage: string;
  public httperrorresponsemessages: string;

  dateCustomClasses: DatepickerDateCustomClasses[];
  minDate: Date;
  maxDate: Date;
  // maxDate: Date;
  currentDate: Date = new Date();
  modalRef: BsModalRef;
  Casedetails_sample: Object;
  ScheduleForm: FormGroup;
  RightForm: FormGroup;
  myDateValue: Date;
  submitted = false;
  status: string;
  public userId;

  public drKrishiTechnical = {} as DrKrishiTechnical;
  public cropinfo = {} as Cropinformation;
  public calender = {} as Calender;
  public harvestMonitoringForm = {} as HarvestMonitoringForm;
  public generalInfo = {} as GeneralInformation;
  public contactInfo = {} as ContactInformation;
  public rightInfo = {} as RightInformation;
  public rightDeatils = {} as RightDeatils;
  public schedulecalendar = {} as Schedule;
  public savescheduledata = {} as saveSchedule;
  public callingstatus = {} as callingStatusModel;
  public isVisible: boolean = false;
  public taskId :string;
  public loggedInUser = {} as any;
  public currentSystemDate: Date;

  dateSelected = [];
  selectedClass = [];

  lotCurrentTotalQuantity: number;
  cropName: string;
  variety: string;
  state: string;
  region: number;
  district: string;
  village: string;
  harvestDate: Date;
  currentQuantity: number;
  sowingWeek: number;
  allowableVarianceQtyPos: number;
  allowableVarianceQtyNeg: number;

  getDateItem(date: Date): string {
    return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`
  }

  constructor(private router: Router,
    private formBuilder: FormBuilder,
    private harvestMonitoringService: HarvestMonitoringService,
    private modalService: BsModalService,
    private route: ActivatedRoute) { }

  ngOnInit() {

    const routeparam = this.route.params.subscribe(params => {
      this.taskId = params['taskId'];
    });
    this.userId = localStorage.getItem('loginUserid');
    this.textboxerrormessage = ErrorMessages.textboxError;
    this.dropdownerrormessage = ErrorMessages.dropdownError;
    this.checkboxerrormessage = ErrorMessages.checkboxError;
    this.invalidvalueerrormessage = ErrorMessages.invalidvalueError;
    this.multiselecterrormessage = ErrorMessages.multiselectError;
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

    this.getHarvestMonitoringFormService();

    this.savescheduledata.callingstatus = "Scheduled Visit";
    this.ScheduleForm = this.formBuilder.group({
      callingstatus: ["1"],
      comments: ['', [Validators.required, Validators.pattern("[^ ](.*|\n|\r|\r\n)*")]],
      dateschedule: ["", Validators.required],
      visitrequired: [false]
    });
    this.RightForm = this.formBuilder.group({
      harvestedQuantity: ['', Validators.required],
      deliverableQuantity: ['', Validators.required]
    });

  }
  get f() { return this.ScheduleForm.controls; }
  get g() { return this.RightForm.controls; }

  public getHarvestMonitoringFormService() {

    this.harvestMonitoringService.getHarvestMonitoringFormService(this.taskId).subscribe(

      (data) => {
        if (data.status != 200) {
          const initialState = {
            title: "Error",
            content: 'Error occurred during Dr.Krishi Technical - Call Center Tele-Caller details, To continue please contact system admin',
            action: "/dr-krishi-technical-harvest-monitoring/" + this.taskId
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        }

        this.harvestMonitoringForm = data.data;
        this.calender = this.harvestMonitoringForm.calender;
        this.cropinfo = this.harvestMonitoringForm.cropinformation;
        this.contactInfo = this.harvestMonitoringForm.contactInformation;
        this.rightInfo = this.harvestMonitoringForm.rightInformation;

        // this.cropName = this.cropinfo.crop;
        // this.variety = this.cropinfo.variety;
        // this.state = this.generalInfo.state;
        // this.region = this.generalInfo.region;
        // this.lotCurrentTotalQuantity = this.generalInfo.lotCurrentTotalQuantity;
        // this.district = this.rightDeatils.district;
        // this.village = this.rightDeatils.village;
        // this.harvestDate = this.rightDeatils.harvestDate;
        // this.currentQuantity = this.rightDeatils.currentQuantity;
        // // this.sowingWeek = this.rightDeatils.sowingWeek;
        // this.allowableVarianceQtyPos = this.rightDeatils.allowableVarianceQtyPos;
        // this.allowableVarianceQtyNeg = this.rightDeatils.allowableVarianceQtyNeg;
        this.schedulecalendar = this.harvestMonitoringForm.schedule;
        const scheduledDate = new Date(this.schedulecalendar.dateschedule);

        // Inital code
        // this.minDate = new Date(scheduledDate);
        // this.maxDate = new Date(scheduledDate);
        // this.minDate.setDate(this.minDate.getDate());
        // this.maxDate.setDate(this.maxDate.getDate() + 30);
        //this.dateSelected = [scheduledDate];


        if(scheduledDate < this.currentSystemDate){
          this.minDate = new Date(this.calender.minDate);
          this.maxDate = new Date(this.calender.maxDate);
        }else{
          this.minDate = new Date(this.calender.minDate);
          this.maxDate = new Date(this.calender.maxDate);
        }
        this.minDate.setDate(this.minDate.getDate());
        this.maxDate.setDate(this.maxDate.getDate());
        this.dateSelected = [this.currentSystemDate];
        // Chnages done to fix the UAT bug id CDT1-1794690 end

        this.isVisible = true;
        //  this.maxDate = new Date(scheduledDate);
        //  this.maxDate.setDate(this.maxDate.getDate() + 30);

        if(this.harvestMonitoringForm.type == 'Verification'){
          this.dateSelected = [this.currentSystemDate];
          this.minDate = new Date(this.currentSystemDate);
          this.maxDate = new Date(this.minDate);
          this.minDate.setDate(this.minDate.getDate() + 1);
          this.maxDate.setDate(this.maxDate.getDate() + 30);

        }
        this.dateCustomClasses = [
          { date: scheduledDate, classes: ['scheduled-date'] }

        ];

      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/dr-krishi-technical-harvest-monitoring/" + this.taskId
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
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
  onSubmit() {

    // alert("hhsa");

    this.submitted = true;
    //this.savescheduledata.dateschedule = this.formatDate(this.ScheduleForm.value.dateschedule);
    this.savescheduledata.callingstatus = this.ScheduleForm.value.callingstatus;
    this.savescheduledata.visitrequired = this.ScheduleForm.value.visitrequired;
    this.savescheduledata.comments = this.ScheduleForm.value.comments;
    this.savescheduledata.deliverableQuantity = this.RightForm.value.deliverableQuantity;
    this.savescheduledata.harvestedQuantity = this.RightForm.value.harvestedQuantity;
    this.savescheduledata.taskid = this.taskId;
    this.savescheduledata.userid = this.userId;
    this.savescheduledata.type = this.harvestMonitoringForm.type;
    this.savescheduledata.rightId = this.harvestMonitoringForm.rightId;
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

    console.log( 'save schedule data {}', this.savescheduledata);
    // stop here if form is invalid
    if (!this.ScheduleForm.invalid) {
      this.harvestMonitoringService.submitHarvestMonitoringForm(this.savescheduledata).subscribe(
        (data) => {

          if (data.status == 200) {
            if(this.harvestMonitoringForm.type == 'Verification'){
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
              action: "/dr-krishi-technical-harvest-monitoring/"+ this.taskId
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
            return;
          }
        }, (err) => {
          const initialState = {
            title: "Error",
            content: this.httperrorresponsemessages,
            action: "/dr-krishi-technical-harvest-monitoring/" + this.taskId
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
  cancelTechForm(){
    if(this.harvestMonitoringForm.type == 'Verification'){
      this.router.navigate(['/dr-krishi-non-technical-list'])
    }else{
      this.router.navigate(['/dr-krishi-technical-harvest-monitoring-list'])
    }
  }
  close() {
    this.modalRef.hide();
  }
  selectChangeHandler(event: any) {
    this.status = event.target.value;
  }
}

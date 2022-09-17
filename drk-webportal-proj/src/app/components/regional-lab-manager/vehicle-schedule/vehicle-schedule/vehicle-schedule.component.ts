import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, NavigationEnd, ActivatedRoute } from "@angular/router";
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { SuccessModalComponent } from '../../../modal-components/success-modal/success-modal.component';
import { VechicleSchedule, VechicleScheduleForm } from './vehicle-schedule.model';
import { ErrorMessages } from '../../../form-validation-messages';
import { VehicleScheduleService } from './vehicle-schedule.service';
import { TileVillage } from './vehicle-schedule.model';
import { Jsonp } from '../../../../../../node_modules/@angular/http';

@Component({
  selector: 'app-vehicle-schedule',
  templateUrl: './vehicle-schedule.component.html',
  styleUrls: ['./vehicle-schedule.component.less']
})
export class VehicleScheduleComponent implements OnInit {
  VehicleScheduleForm: FormGroup;
  submitted = false;
  modalRef: BsModalRef;
  scrollbarOptions: any;
  minDate: Date;
  public schedulevillageList= {} as VechicleSchedule;
  public villageList: TileVillage[];
  public vinnumberlist: VechicleSchedule[] = [];
  public selectAll: boolean = false;
  public selectedTiles: string[] = []
  public vehiclescheduleform = {} as VechicleScheduleForm;
  public loggedInUser = {} as any;
  public currentSystemDate: Date;
  public scheduleDate:string;
  public tileVillage: TileVillage[]
  public userId;
  selectedDate: string;
  
  //Error Messages
  public textboxerrormessage: string;
  public dropdownerrormessage: string;
  public multiselecterrormessage: string;
  public httperrorresponsemessages: string;
  public cropdataapierrorresponsemessages: string;
  public cropdatavillagesapierrorresponsemessages: string;

  constructor(private formBuilder: FormBuilder,
    private router: Router,
    private modalService: BsModalService,
    private route: ActivatedRoute,
    private VehicleScheduleVillageService: VehicleScheduleService, ) {
    
  }

  ngOnInit() {
    this.loggedInUser = JSON.parse(localStorage.getItem("userData"));
    this.currentSystemDate = new Date(this.loggedInUser.date);
    this.minDate = new Date(this.loggedInUser.date) ; 
    this.minDate.setDate(this.minDate.getDate() + 1);
    //this.scheduleDate = this.formatDate(this.currentSystemDate);

    this.dropdownerrormessage = ErrorMessages.dropdownError;
    this.textboxerrormessage = ErrorMessages.textboxError;
    this.multiselecterrormessage = ErrorMessages.multiselectError;
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;
    this.cropdataapierrorresponsemessages = ErrorMessages.cropdataAPIErrorResponseMessages;
    this.cropdatavillagesapierrorresponsemessages = ErrorMessages.cropdatavillagesAPIErrorResponseMessages;


    const routeparam = this.route.params.subscribe(params => {
     this.vehiclescheduleform.scheduledate = params['date'];
    });

    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0)
    });

    this.VehicleScheduleForm = this.formBuilder.group({
      vinnum: ['', Validators.required],
      village: ['', Validators.required],
      scheduledate: ['', Validators.required]
    });

    this.loadTile();
    this.loggedInUser = JSON.parse(localStorage.getItem("userData"));
    this.vinNumber();
    
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.VehicleScheduleForm.controls;
  }


  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (!this.VehicleScheduleForm.invalid && this.schedulevillageList.villages.length > 0) {
      //this.currentSystemDate = new Date(this.vehiclescheduleform.scheduledate);
      //this.vehiclescheduleform.scheduledate = this.formatDate(this.currentSystemDate);
      if(!(typeof this.vehiclescheduleform.scheduledate === 'string')) {
        this.vehiclescheduleform.scheduledate = this.formatDate(this.vehiclescheduleform.scheduledate)
      }
      this.vehiclescheduleform.regionId = this.loggedInUser.regionId;
      this.vehiclescheduleform.schedulevillages = this.schedulevillageList.villages;

      
      this.VehicleScheduleVillageService.savevehicleschedule(this.vehiclescheduleform).subscribe(
        (data) => {

          if(data.status == "Success"){
            const initialState = {
              title: "Success",
              content: 'Vehicle had been scheduled successfully.',
              action: "/vehicle-schedule-list"
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
            return;
          }else if(data.status == "Error"){
            const initialState = {
              title: "Error",
              content: data.message,
              action: "/vehicle-schedule"
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
            return;
          }

        }, (err) => {
          const initialState = {
            title: "Error",
            content: this.httperrorresponsemessages,
            action: "/vehicle-schedule"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        });
    }else{
      if( this.schedulevillageList.villages &&  this.schedulevillageList.villages.length == 0){
        this.f.village.setValue("") ;
        this.submitted =true
      }
    }
  }

  //to get villages from tile
  public getTD(event) {
    event.target.classList.toggle("selected")
    this.selectAll = false;

    var target = event.target.dataset.sid || event.srcElement.dataset.sid;

    if (this.selectedTiles.length > 0) {
      if (this.selectedTiles.some(el => el === target)) {
        this.selectedTiles.splice(this.selectedTiles.findIndex(a => a == target), 1)
      } else {
        this.selectedTiles.push(target)
      }
    } else {
      this.selectedTiles.push(target)
    }


    this.VehicleScheduleVillageService.getVillagelist(target, this.loggedInUser.cropDataApiKey).subscribe(
      (data) => {
        if (!this.villageList || this.villageList == []) {
          this.villageList = data;
          data.forEach(item => {
            if (this.schedulevillageList && this.schedulevillageList.villages && this.schedulevillageList.villages.some(el => el.villageCode === item.villageCode)) {
              this.schedulevillageList.villages.splice(this.schedulevillageList.villages.findIndex(a => a.villageCode == item.villageCode), 1)
            }
          })
        } else {
          data.forEach(item => {
            if (this.schedulevillageList && !this.schedulevillageList.villages) {
              this.schedulevillageList.villages = [];
            }

            if (this.schedulevillageList &&  this.schedulevillageList.villages.some(el => el.villageCode === item.villageCode)) {
              this.schedulevillageList.villages.splice(this.schedulevillageList.villages.findIndex(a => a.villageCode == item.villageCode), 1)
            }

            if (this.villageList.some(el => el.villageCode === item.villageCode)) {
              this.villageList.splice(this.villageList.findIndex(a => a.villageCode == item.villageCode), 1)
            } else {
              this.villageList.push(item)
            }

          })
        }
        return;
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.cropdatavillagesapierrorresponsemessages,
          action: "/vehicle-schedule"
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

  public setAllValue(villageList, schedulevillageList) {

    this.selectAll = !this.selectAll
    if ((schedulevillageList && schedulevillageList.villages && villageList && schedulevillageList.villages.length < villageList.length) || (this.selectAll && this.schedulevillageList)) {
      schedulevillageList.villages = [];
      this.VehicleScheduleForm.controls.village.setValue(false);
      villageList.forEach(item => {


        if (!schedulevillageList.villages) {
          schedulevillageList.villages = []
        }
        if (schedulevillageList.villages.includes(item)) {
          schedulevillageList.villages.splice(schedulevillageList.villages.indexOf(item), 1)
        } else {
          schedulevillageList.villages.push(item)
        }
        this.VehicleScheduleForm.controls.village.setValue(true);
      });

    } else {
      schedulevillageList.villages = [];
      this.VehicleScheduleForm.controls.village.setValue(false);
    }

  }



  public loadTile() {
    this.VehicleScheduleVillageService.getRegionTemplete(this.loggedInUser.regionId, this.loggedInUser.cropDataApiKey).subscribe(
      (data) => {
        return;
      }, (err) => {
        if (err.status == 200 && err.statusText == "OK") {
          document.getElementsByClassName("mapTile")[0].innerHTML = err.error.text;
          this.villageList = []
          this.tileVillage = [];
          
          this.selectAll = false;
        } else {
          const initialState = {
            title: "Error",
            content: this.cropdataapierrorresponsemessages,
            action: "/vehicle-schedule"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        }
        return;
      });

  }


  public isChecked(item) {
    if (this.schedulevillageList.villages) {
      return this.schedulevillageList.villages.some(el => el.villageCode === item.villageCode)
    } else {
      return false
    }


  }

   public setCheckedValue(item, schedulevillageList) {

    if (schedulevillageList && !schedulevillageList.villages) {
      schedulevillageList.villages = []
    }
    if (schedulevillageList &&  schedulevillageList.villages.includes(item)) {
      schedulevillageList.villages.splice(schedulevillageList.villages.indexOf(item), 1)
    } else {
      schedulevillageList.villages.push(item)
    }   

    if(this.villageList.length == schedulevillageList.villages.length){
      this.selectAll = true;
    }else{
      this.selectAll = false;
    }
  }



  public vinNumber(){
    this.VehicleScheduleVillageService.getVinnumberlist(this.loggedInUser.regionId).subscribe(
      (data) => {
        this.vinnumberlist = data;
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/vehicle-schedule"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      }
    )
  }





}

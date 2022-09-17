import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, NavigationEnd, ActivatedRoute } from "@angular/router";
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { SuccessModalComponent } from '../../modal-components/success-modal/success-modal.component';
import { AssignVillagesModel, State, District, Village, Prscoutdetails, Region, TileVillage, Week } from './assign-villages.model';
import { AssignVillagesService } from './assign-villages.service';
import { AddUserModel } from '../../user-management/add-user/user.model';
import { ErrorMessages } from '../../form-validation-messages';
import { ListOfUsersService } from '../../user-management/list-of-users/list-of-users.service';
// import { EncryptionDecryptionService } from '../../../encryption-decryption.service';
import { forEach } from '../../../../../node_modules/@angular/router/src/utils/collection';
import { stringify } from '../../../../../node_modules/@angular/core/src/render3/util';


declare var $;


@Component({
  selector: 'app-assign-villages-to-prs',
  templateUrl: './assign-villages-to-prs.component.html',
  styleUrls: ['./assign-villages-to-prs.component.less']
})
export class AssignVillagesToPrsComponent implements OnInit {
  AssignVillagesForm: FormGroup;
  submitted = false;
  modalRef: BsModalRef;
  scrollbarOptions: any;
  public states: State[];
  public regions: any;
  public months: string[];
  public weeks: Week[];
  public weekNumber: number;
  public prscouts: AddUserModel[];
  public villageslist: Village[];
  public state: State;
  public prscoutdetails = {} as Prscoutdetails;
  public loggedInUser = {} as any;
  public tileVillage: TileVillage[]
  public selectAll: boolean = false;
  public selectedTiles: string[] = [];
  public id: number;
  public datasidNumberNew: string[] = [];
  public assigmentId: number;
  public showTile = false;
  isDisabled = null;
  public selectedRegion = null;
  public selectedWeekNumber = null;
  public stateId: number;
  public selectedVillages: any;
  public isSelectedVillage = null;
  public disableFields = false;

  // assign villages to prs model
  public assignvillagesprs = {} as AssignVillagesModel;

  // For disable-enable property changes
  public weekNumberForVillageList : any;
  public yearForVillageList : any;
  public assignedvillageslist: any;
  public checkBooleanValue : any;
  public checkBooleanValue2 : number;
  public checkBooleanValue3 : number;
  public checkColorValue : any;

  //Error Messages
  public textboxerrormessage: string;
  public dropdownerrormessage: string;
  public checkboxerrormessage: string;
  public invalidvalueerrormessage: string;
  public multiselecterrormessage: string;
  public httperrorresponsemessages: string;
  public cropdataapierrorresponsemessages: string;
  public cropdatavillagesapierrorresponsemessages: string;

  public currentSystemDate: Date;


  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private modalService: BsModalService,
    private AssignVillagesService: AssignVillagesService,
    private route: ActivatedRoute,
    private userService: ListOfUsersService,
    // private encryptionDecryption: EncryptionDecryptionService
  ) { }

  ngOnInit() {
    //Error Messages
    this.textboxerrormessage = ErrorMessages.textboxError;
    this.dropdownerrormessage = ErrorMessages.dropdownError;
    this.checkboxerrormessage = ErrorMessages.checkboxError;
    this.invalidvalueerrormessage = ErrorMessages.invalidvalueError;
    this.multiselecterrormessage = ErrorMessages.multiselectError;
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;
    this.cropdataapierrorresponsemessages = ErrorMessages.cropdataAPIErrorResponseMessages;
    this.cropdatavillagesapierrorresponsemessages = ErrorMessages.cropdatavillagesAPIErrorResponseMessages;


    //Back to top functionality
    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0)
    });


    this.loggedInUser = JSON.parse(localStorage.getItem("userData"));

    this.currentSystemDate = new Date(this.loggedInUser.date);
    // this.loggedInUser.stateId = this.encryptionDecryption.decryption(this.loggedInUser.stateId);
    // this.loggedInUser.regionId = this.encryptionDecryption.decryption(this.loggedInUser.regionId);
    // this.loggedInUser.userId = this.encryptionDecryption.decryption(this.loggedInUser.userId);


    var date = new Date(this.loggedInUser.date);
    var year = date.getFullYear();
    var month = date.getMonth();
    var day = date.getDay();

    //var currentWeek = this.getWeeks(year, month, day);
    var currentWeek = this.loggedInUser.weekNumber;
    if (this.assignvillagesprs.weekNumber < currentWeek) {
      this.assignvillagesprs.weekNumber = undefined;
    }
    this.weeks = [];
    // date = new Date(date.setMonth(month+1));
    //var start = date.getDate() - date.getDay() + (date.getDay() === 0 ? -6 : 6)+ (this.ifLeapYear(date.getFullYear) && startDate.getMonth() + 1 == 2 ? 1 : 0);
    var start = date.getDate() - date.getDay() + (date.getDay() === 0 ? -6 : 6);
    var startDate = new Date(date.setDate(start));
    for (var i = 0; i < 4; i++) {
      //start = startDate.getDate() - startDate.getDay() + (startDate.getDay() === 0 ? 6 : 6)+ (this.ifLeapYear(date.getFullYear) && startDate.getMonth() + 1 == 2 ? 1 : 0);
      //startDate = new Date(startDate.setDate(start));
      start = startDate.getDate() - startDate.getDay() + (startDate.getDay() === 0 ? 6 : 6);
      startDate = new Date(date.setDate(start));
      this.weeks.push({ weekNo: currentWeek, range: this.getDateRangeOfWeek(currentWeek) });
      currentWeek = currentWeek + 1;
      if(currentWeek <= 52){
        currentWeek = currentWeek;
      }else if(currentWeek <= 53){
        currentWeek = currentWeek;
      }
    }


    this.AssignVillagesForm = this.formBuilder.group({
      state: ['', Validators.required],
      region: ['', Validators.required],
      village: ['', Validators.required],
      month: ['', Validators.required],
      week: ['', Validators.required],
      PRScout: ['', Validators.required],
    });

    this.route.params.subscribe(params => {
      this.assigmentId = +params['assigment_Id'];
      this.assignvillagesprs.weekNumber = +params['week'];
    });


    //Edit PRM Start
    if (this.assigmentId) {
      this.isDisabled = true;
      this.AssignedPRSDetails();
    }
    //Edit PRM END


    this.getStates();
    //Month Dropdown
    this.months = [
      "Janaury",
      "February",
      "March",
      "April",
      "May",
      "June",
      "July",
      "August",
      "September",
      "October",
      "November",
      "December"
    ]


    // PR Scout Dropdown
    this.weekNumberForVillageList=this.assignvillagesprs.weekNumber;
    var d = new Date();
    this.yearForVillageList= d.getFullYear();

    this.getAssignedVillages()

  }

  public getWeekMonth( number: number) {
    const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
      'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'
    ];

    return monthNames[number - 1];
  }

  public generateWeek(){
    const date = new Date(this.currentSystemDate);
    date.setDate(date.getDate() + 3 - (date.getDay() + 6) % 7);
    // January 4 is always in week 1.
    var week1 = new Date(date.getFullYear(), 0, 4);
    // Adjust to Thursday in week 1 and count number of weeks from date to week1.
    return 1 + Math.round(((date.getTime() - week1.getTime()) / 86400000 - 3 + (week1.getDay() + 6) % 7) / 7);
  }

  public getDateRangeOfWeek(weekNo) {
    const d1 = new Date();
    const numOfdaysPastSinceLastMonday = eval(String(d1.getDay() - 1));
    d1.setDate(d1.getDate() - numOfdaysPastSinceLastMonday);

    const currentWeek = this.generateWeek();
    const weekNoToday = currentWeek;
    const weeksInTheFuture = eval( String(weekNo - weekNoToday) );
    d1.setDate(d1.getDate() + eval( String(7 * weeksInTheFuture) ));
    const rangeIsFrom = this.getWeekMonth(eval(String(d1.getMonth() + 1))) + ' ' + d1.getDate();
    d1.setDate(d1.getDate() + 6);
    const rangeIsTo = this.getWeekMonth(eval(String(d1.getMonth() + 1))) + ' ' + d1.getDate();
    return  rangeIsFrom + ' - ' + rangeIsTo;
  }
  public ifLeapYear(year) {
    if (year % 400 == 0)
      return true;

    // Else If a year is muliplt of 100,
    // then it is not a leap year
    if (year % 100 == 0)
      return false;

    // Else If a year is muliplt of 4,
    // then it is a leap year
    if (year % 4 == 0)
      return true;
    return false;
  }


  getStates() {
    //States Dropdown
    this.AssignVillagesService.getStates().subscribe(
      (data) => {
        this.states = data;
        this.states.forEach(item => {
          if (item.stateId == this.loggedInUser.stateId) {
            this.assignvillagesprs.stateId = item.stateId;
            //this.stateId = this.assignvillagesprs.stateId;
            this.getRegion();
          }
        })

      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/assign-villages-to-prs/" + this.assignvillagesprs.weekNumber
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;

      });


  }

  //get assigned villages
  public getAssignedVillages() {

    this.AssignVillagesService.getAssignedVillage(this.weekNumberForVillageList , this.yearForVillageList).subscribe(
      (data) => {
        this.assignedvillageslist = data;
        this.assignvillagesprs.village = undefined;
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/assign-villages-to-prs/" + this.assignvillagesprs.weekNumber
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }

  checkQuestions(){
    this.tileVillage.forEach(a => {
      this.assignedvillageslist.forEach(b => {
        if(a.villageCode === b){
          this.checkBooleanValue = 1;
        }
      })
    })

    if (this.checkBooleanValue) { // your question said "more than one element"
      this.checkBooleanValue=null;
      return true;
    }
    else {
      return false;
    }
  }
  public checkQuestions2(c){

    this.assignedvillageslist.forEach(b => {
      if(c.villageCode === b){
        this.checkBooleanValue2 = 2;
      }
    })
    if (this.checkBooleanValue2===2) { // your question said "more than one element"
      this.checkBooleanValue2=null;
      return true;
    }
    else{
      return false;
    }
  }

  public checkQuestions3(c){

    this.assignedvillageslist.forEach(b => {
      if(c.villageCode === b){
        this.checkBooleanValue3 = 2;
      }
    })

    if (this.checkBooleanValue3===2) { // your question said "more than one element"
      this.checkBooleanValue3=null;
      return true;
    }
    else {
      return false;
    }
  }

  getColor(a) {
    this.assignedvillageslist.forEach(b => {
      if(a === b){
        this.checkColorValue = 1;
      }
    })
    if(this.checkColorValue){
      this.checkColorValue=null;
      return '#808080';
    }
  }


  getAssignedVillagesForWeek(event:any){

    if(event.target.value.length==4){
      this.weekNumberForVillageList=event.target.value[3];

    } else if(event.target.value.length>4){
      this.weekNumberForVillageList=event.target.value[3]+event.target.value[4];
    }
    // this.getAssignedVillages();
  }


  // Region Dropdown
  public getRegion() {
    this.assignvillagesprs.regionID = undefined;
    this.assignvillagesprs.village = undefined;
    this.assignvillagesprs.prscout = undefined;
    this.regions = [];
    this.prscouts = [];
    this.AssignVillagesService.getRegion(this.assignvillagesprs.stateId).subscribe(
      (data) => {
        this.regions = data;
       /* this.regions.forEach(item => {
          this.assignvillagesprs = item;
        })*/
        this.route.params.subscribe(params => {
          this.assignvillagesprs.weekNumber = +params['week'];
        });
        this.getMonth();
        //this.assignvillagesprs.month = this.assignvillagesprs.weekNumber
        //this.assignvillagesprs = this.regions;
        // this.assignvillagesprs.regionID = undefined;
        // this.assignvillagesprs.village = undefined;
        // this.assignvillagesprs.prscout = undefined;
        this.selectAll = false;

        if (document.getElementsByClassName("mapTile")[0]) {
          document.getElementsByClassName("mapTile")[0].innerHTML = ""
        }

        if (this.regions.length === 0) {
          this.assignvillagesprs.regionID = undefined;
        }

        this.villageslist = []
        this.tileVillage = [];
        this.prscouts = [];
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/assign-villages-to-prs/" + this.assignvillagesprs.weekNumber
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }




  public getVillages(event) {
    this.AssignVillagesService.getVillage(this.assignvillagesprs.tileId).subscribe(
      (data) => {
        this.villageslist = data;
        this.assignvillagesprs.village = undefined;
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/assign-villages-to-prs/" + this.assignvillagesprs.weekNumber
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }


  // convenience getter for easy access to form fields
  get f() {
    return this.AssignVillagesForm.controls;
  }


  onSubmit() {
    this.submitted = true;
    // stop here if form is invalid
    if (!this.AssignVillagesForm.invalid && this.assignvillagesprs.village.length > 0) {
      var d = new Date();
      var n = d.getFullYear();
      this.prscoutdetails = {} as Prscoutdetails;
      this.prscoutdetails.village = this.assignvillagesprs.village;
      this.prscoutdetails.weekNumber = this.assignvillagesprs.weekNumber;
      if (this.loggedInUser) {
        this.prscoutdetails.drkrishiUser1 = this.loggedInUser.userId;
      }

      this.prscoutdetails.stateId = this.assignvillagesprs.stateId
      this.prscoutdetails.regionID = this.assignvillagesprs.regionID
      this.prscoutdetails.month = this.assignvillagesprs.month;
      this.prscoutdetails.datasid = this.assignvillagesprs.datasid;
      this.assignvillagesprs.datasid = this.selectedTiles;


      if(this.assigmentId){
        this.prscoutdetails.assigmentId = this.assigmentId;
      }

      var year = true
      // this.weeks.forEach(item => {
      //   if (item.weekNo == this.assignvillagesprs.weekNumber) {
      //     if (+item.range.split("-")[0].split(" ")[1] - 7 > 0 && this.assignvillagesprs.month == 12) {
      //       year = false
      //     }
      //   }
      // })

      if (this.prscoutdetails.datasid == null) {
        this.selectedTiles = this.datasidNumberNew;
        this.prscoutdetails.datasid = this.datasidNumberNew;
      }


      if (year) {
        this.prscoutdetails.year = n;
      }
      // else {
      //   this.prscoutdetails.year = n - 1;
      // }

      this.prscoutdetails.prscout = this.assignvillagesprs.prscout;

      this.userService.checkUserStatus( {"userId":this.loggedInUser.userId}).subscribe(
        (data) => {
          if (data) {
            this.AssignVillagesService.submitAssignVillagesPRS(this.prscoutdetails).subscribe(
              (data) => {
                var user = data.prsName
                // if (this.assignvillagesprs.prscout.middleName && this.assignvillagesprs.prscout.middleName.replace(" ", "") != "" && this.assignvillagesprs.prscout.middleName != null) {
                //   user = user + " " + this.assignvillagesprs.prscout.middleName;
                // }
                // user = user + " " + this.assignvillagesprs.prscout.lastName;

                if (data.statusCode == 200) {
                  const initialState = {
                    title: "Success",
                    content: 'The Village(s) has been assigned to PR Scout - ' + user + ' successfully.',
                    action: "/assignment-list"
                  };
                  this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
                  return;
                } else {
                  if (this.assigmentId) {
                    const initialState = {
                      title: "Error",
                      content: data.msg,
                      action: "/assign-villages-to-prs/edit/" + this.assigmentId +"/" + this.assignvillagesprs.weekNumber
                    };
                    this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
                  } else {
                    const initialState = {
                      title: "Error",
                      content: data.msg,
                      action: "/assign-villages-to-prs/" + this.assignvillagesprs.weekNumber
                    };
                    this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
                  }
                  return;
                }

              }, (err) => {
                var msg = this.httperrorresponsemessages;
                if (err.status == 406) {
                  msg = "Please Reselect the villages as one village is already assigned for same week"
                }
                if (this.assigmentId) {
                  const initialState = {
                    title: "Error",
                    content: msg,
                    action: "/assign-villages-to-prs/edit/" + this.assigmentId +"/" + this.assignvillagesprs.weekNumber
                  };

                  this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
                } else {
                  const initialState = {
                    title: "Error",
                    content: msg,
                    action: "/assign-villages-to-prs/" + this.assignvillagesprs.weekNumber
                  };

                  this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
                }
                return;
              });
          } else {
            localStorage.setItem("userLocked", "user is locked or Inactive");
            this.router.navigate(['/login']);
          }
        }, (err) => {
          const initialState = {
            title: "Error",
            content: this.httperrorresponsemessages,
            action: "/assign-villages-to-prs/" + this.assignvillagesprs.weekNumber
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        });


    } else {
      if (this.assignvillagesprs.village && this.assignvillagesprs.village.length == 0) {
        this.f.village.setValue("");
        this.submitted = true
      }
    }



  }

  public setCheckedValue(item, assignvillagesprs) {
    if (!assignvillagesprs.village) {
      assignvillagesprs.village = []
    }
    var selectedVillagePosition = null
    var isVillageExists = false;
    for(var i=0; i<assignvillagesprs.village.length; i++){
      if(item.villageCode === assignvillagesprs.village[i].villageCode){
        selectedVillagePosition = i;
        isVillageExists = true;
      }
    }

    if(selectedVillagePosition == null){
      assignvillagesprs.village.push(item)
    }
    else{
      assignvillagesprs.village.splice(selectedVillagePosition,1)
    }

    if (this.tileVillage.length == assignvillagesprs.village.length) {
      this.selectAll = true;
    } else {
      this.selectAll = false;
    }

  }



  public loadTile() {
    this.prscouts = [];
    this.tileVillage = [];
    this.assignvillagesprs.village = [];
    this.assignvillagesprs.prscout = undefined;
    if (this.assignvillagesprs.regionID == undefined) {
      this.showTile = false;
    } else {
      this.showTile = true;
     /* this.AssignVillagesForm.get('PRScout').setValidators([Validators.required]);
      this.AssignVillagesForm.get('PRScout').updateValueAndValidity();*/

    }

    if (this.assignvillagesprs && this.assignvillagesprs.regionID != undefined) {
      this.AssignVillagesService.getRegionTemplete(this.assignvillagesprs.regionID, this.loggedInUser.cropDataApiKey).subscribe(
        (data) => {
          return;
        }, (err) => {
          if (err.status == 200 && err.statusText == "OK") {
            document.getElementsByClassName("mapTile")[0].innerHTML = err.error.text;
            // this.assignvillagesprs.village = undefined;
            // this.villageslist = []
            // this.tileVillage = [];
            this.selectAll = false;
            this.getPRScout();
            if (this.assigmentId) {
              this.AssignedPRSDetails();
              for (var i = 0; i < this.assignvillagesprs.datasid.length; i++) {
                this.AssignVillagesService.getVillagesList(parseInt(this.assignvillagesprs.datasid[i]), this.loggedInUser.cropDataApiKey).subscribe(
                  (data) => {
                    data.forEach(item =>{
                      this.tileVillage.push(item)
                    })
                  }
                )
              }
              this.f.village.setValue("true");
            }

            // if(this.tileVillage.length == this.assignvillagesprs.village.length)  {
            //   this.selectAll = true;
            // }

          } else {
            const initialState = {
              title: "Error",
              content: this.cropdataapierrorresponsemessages,
              action: "/assign-villages-to-prs/" + this.assignvillagesprs.weekNumber
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          }
          return;
        });
    }
  }

  // to get villages from tile
  public newSelectedTiles = [];
  public getTD(event) {
    this.selectAll = false;
    var target = event.target.dataset.sid || event.srcElement.dataset.sid;
    if (this.assignvillagesprs.datasid != null) {
      if(this.assignvillagesprs.datasid.some(el => el == target)){
        this.assignvillagesprs.datasid.splice(this.assignvillagesprs.datasid.findIndex(a => a == target), 1)
      }else{
        this.selectedTiles.push(target)
      }
    } else {
      if (this.datasidNumberNew.indexOf(target) < 0) {
        this.datasidNumberNew.push(target);
        this.selectedTiles.push(target)
      }
      if ($(event.target).hasClass("selected")) {
        this.RemoveElementFromStringArray(target);
        var filtereddatasid: string[] = [];
        for (let i = 0; i < this.datasidNumberNew.length; i++) {
          if (this.datasidNumberNew[i] != target) {
            filtereddatasid.push(this.datasidNumberNew[i]);
          }
        }
        this.datasidNumberNew = filtereddatasid;
      }
    }

    if ($(event.target).hasClass("tile-selected")) {
      event.target.classList.remove("selected")
      event.target.classList.remove("tile-selected")
    } else {
      event.target.classList.toggle("selected")
    }

    // if(!$("table tr td").hasClass( "selected" )){
    //   this.showTileMapError = true;
    // }else{
    //   this.showTileMapError = false;
    // }
    //console.log("Already selected tile ", this.selectedTiles)
    // console.log(this.selectedTiles)
    // console.log(target)
    // if (this.selectedTiles.length > 0) {
    //   if (this.selectedTiles.some(el => el == target)) {
    //     this.selectedTiles.splice(this.selectedTiles.findIndex(a => a == target), 1)
    //   } else {
    //     this.selectedTiles.push(target)
    //   }
    // } else {
    //   this.selectedTiles.push(target)
    // }


    this.AssignVillagesService.getVillagesList(target, this.loggedInUser.cropDataApiKey).subscribe(
      (data) => {
        if (!this.tileVillage || this.tileVillage == []) {
          this.tileVillage = data;
          data.forEach(item => {
            if (this.assignvillagesprs.village && this.assignvillagesprs.village.some(el => el.villageCode === item.villageCode)) {
              this.assignvillagesprs.village.splice(this.assignvillagesprs.village.findIndex(a => a.villageCode == item.villageCode), 1)
            }
          })
        } else {
          data.forEach(item => {
            if (!this.assignvillagesprs.village) {
              this.assignvillagesprs.village = [];
            }

            if (this.assignvillagesprs.village.some(el => el.villageCode === item.villageCode)) {
              this.assignvillagesprs.village.splice(this.assignvillagesprs.village.findIndex(a => a.villageCode == item.villageCode), 1)
            }

            if (this.tileVillage.some(el => el.villageCode === item.villageCode)) {
              this.tileVillage.splice(this.tileVillage.findIndex(a => a.villageCode == item.villageCode), 1)
            } else {
              this.tileVillage.push(item)
            }
          })
        }
        return;
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.cropdatavillagesapierrorresponsemessages,
          action: "/assign-villages-to-prs/" + this.assignvillagesprs.weekNumber
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }


  RemoveElementFromStringArray(element: string) {
    this.selectedTiles.forEach((value, index) => {
      if (value == element) {
        this.selectedTiles.splice(index, 1);
      }
    });
  }

  public isChecked(item) {
    if (this.assignvillagesprs.village) {
      return this.assignvillagesprs.village.some(el => el.villageCode === item.villageCode)
    } else {
      return false
    }



  }


  // to get current week
  public getWeeks(year, month, day) {
    month += 1; //use 1-12
    var a = Math.floor((14 - (month)) / 12);
    var y = year + 4800 - a;
    var m = (month) + (12 * a) - 3;
    var jd = day + Math.floor(((153 * m) + 2) / 5) +
      (365 * y) + Math.floor(y / 4) - Math.floor(y / 100) +
      Math.floor(y / 400) - 32045;

    var d4 = (jd + 31741 - (jd % 7)) % 146097 % 36524 % 1461;
    var L = Math.floor(d4 / 1460);
    var d1 = ((d4 - L) % 365) + L;
    var NumberOfWeek = Math.floor(d1 / 7);

    return NumberOfWeek;
  }

  // to get date range of week
  /* public getrange(date) {  // commented
     var start = date.getDate() - date.getDay() + (date.getDay() === 0 ? -6 : 0);
     var end = date.getDate() - date.getDay() + (date.getDay() === 0 ? -6 : 6)
     var startDate = new Date(date.setDate(start));
     var endDate = new Date(date.setDate(end));
     var range = startDate.getDate() + "/" + (startDate.getMonth() + 1) + "/" + startDate.getFullYear() + " to " + endDate.getDate() + "/" + (endDate.getMonth() + 1) + "/" + endDate.getFullYear();
     return range;
   }  */


  public getrange(date) {

    var monthName = ["Jan", "Feb", "March", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"];
    var start = date.getDate() - date.getDay() + (date.getDay() === 0 ? -6 : 1);
    var end = date.getDate() - date.getDay() + (date.getDay() === 0 ? -6 : 7);
    var startDate = new Date(date.setDate(start));
    var endDate = new Date(date.setDate(end));
    var month = endDate.getMonth();

    if (start < 0) {
      var month = endDate.getMonth();
      if (month == 11) {
        endDate.setMonth(0);
      } else {
        endDate.setMonth(month + 1);
      }

    }
    var range = (monthName[startDate.getMonth()]) + " " + startDate.getDate() + " - " + (monthName[endDate.getMonth()]) + " " + endDate.getDate();
    return range;
  }


  public getPRScout() {
    this.AssignVillagesService.getPRscout(this.loggedInUser.userId, this.assignvillagesprs.regionID).subscribe((data) => {
        this.prscouts = data;
        if (this.prscouts.length === 0) {
          this.prscouts = [];
          this.assignvillagesprs.prscout = undefined;
        }

      },
      (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/assign-villages-to-prs/" + this.assignvillagesprs.weekNumber
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }

  public getMonth() {
    var monthName = ["Jan", "Feb", "March", "Apr", "May", "June", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    var selectedMonth;
    this.weeks.forEach(item => {
      if (item.weekNo == this.assignvillagesprs.weekNumber) {
        selectedMonth = item.range.slice(item.range.indexOf("-")+2, item.range.indexOf("-")+5);
        return this.assignvillagesprs.month = monthName.indexOf(selectedMonth) + 1;
      }
    })

  }


  public setAllValue(tileVillage, assignvillagesprs) {
    this.selectAll = !this.selectAll
    if ((assignvillagesprs.village && tileVillage && assignvillagesprs.village.length < tileVillage.length) || this.selectAll) {
      assignvillagesprs.village = [];
      this.AssignVillagesForm.controls.village.setValue(false);
      tileVillage.forEach(item => {
        if (!assignvillagesprs.village) {
          assignvillagesprs.village = []
        }
        if (assignvillagesprs.village.includes(item)) {
          assignvillagesprs.village.splice(assignvillagesprs.village.indexOf(item), 1)
        } else {
          assignvillagesprs.village.push(item)
        }
        this.AssignVillagesForm.controls.village.setValue(true);
      });

    } else {
      assignvillagesprs.village = [];
      this.AssignVillagesForm.controls.village.setValue(false);
    }

  }


  public AssignedPRSDetails() {
    this.AssignVillagesService.getAssignedPRSDetails(this.assigmentId).subscribe(
      (data) => {
        this.assignvillagesprs = data;
        var currentWeek = this.loggedInUser.weekNumber;
        if(this.assignvillagesprs.weekNumber === currentWeek){
          this.AssignVillagesForm.controls['week'].disable();
          this.AssignVillagesForm.controls['PRScout'].disable();
        }
        this.selectedTiles = this.assignvillagesprs.datasid;
        this.assignvillagesprs.datasid = this.assignvillagesprs.datasid;
        this.selectedRegion = this.assignvillagesprs.regionID;
        if (this.f.village.value == "") {
          this.loadTile();
        }

        for (let i = 0; i < this.assignvillagesprs.datasid.length; i++) {
          $('table tr td[data-sid=' + this.selectedTiles[i] + ']').addClass('selected tile-selected');
        }


        //this.f.region.setValue(this.assignvillagesprs.regionID);

      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/assign-villages-to-prs/edit/" + this.assigmentId +"/" + this.assignvillagesprs.weekNumber
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      }

    )

  }

}



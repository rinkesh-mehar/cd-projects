import { Component, OnInit, HostListener, Renderer } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { State, Region } from '../assign-villages-to-prs/assign-villages.model';
import { AssignVillagesService } from '../assign-villages-to-prs/assign-villages.service';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { SuccessModalComponent } from '../../modal-components/success-modal/success-modal.component';
import { AddUserModel } from '../../user-management/add-user/user.model';
import { AssignmentListService } from './assignment-list.service';
import { AssignMentListModel, Useraccordion, VillageData } from './assignment-list.model';
import { ErrorMessages } from '../../form-validation-messages';



declare var $;

@Component({
  selector: 'app-assignment-list',
  templateUrl: './assignment-list.component.html',
  styleUrls: ['./assignment-list.component.less']
})
export class AssignmentListComponent implements OnInit {


  constructor(
    private router: Router,
    private data: AssignVillagesService,
    private formBuilder: FormBuilder,
    private render: Renderer,
    private modalService: BsModalService,
    private assignmentListService: AssignmentListService,
    private route: ActivatedRoute

  ) { }


  /*
   public getList() {

     this.assignmentListService.setAssignmentListBYPrmId(this.loggedInUser.userId).subscribe(
       (data) => {

         this.useraccordion = {} as Useraccordion;
         this.villageData = {} as VillageData;
         data.forEach(item => {


           if (!this.assignmentSlider || this.assignmentSlider.length == 0) {
             //this.assignmentSlider = []
             this.assignmentlist = {} as AssignMentListModel;
             this.assignmentlist.month = item.prVillageAssigment.month;
             this.assignmentlist.year = item.prVillageAssigment.year;
             this.assignmentlist.week = item.prVillageAssigment.weekNumber;
             this.useraccordion.prsId = item.prVillageAssigment.drkrishiUser2.userId;
             this.useraccordion.regionId = item.prVillageAssigment.drkrishiUser2.region;
             this.useraccordion.stateId = item.prVillageAssigment.drkrishiUser2.state
             this.villageData.villagename = item.villagetask.villageName;
             this.villageData.status = item.isCompleted;

             this.useraccordion.user = item.prVillageAssigment.drkrishiUser2.firstName
             if (item.prVillageAssigment.drkrishiUser2.middleName) {
               this.useraccordion.user = this.useraccordion.user + " " + item.prVillageAssigment.drkrishiUser2.middleName
             }
             this.useraccordion.user = this.useraccordion.user + " " + item.prVillageAssigment.drkrishiUser2.lastName
             this.useraccordion.villages = []
             this.useraccordion.villages.push(this.villageData);
             this.assignmentlist.useraccordion = []
             this.assignmentlist.useraccordion.push(this.useraccordion)
             this.assignmentSlider.push(this.assignmentlist)
             //this.filteredData.push(this.assignmentlist)

           } else {
             var index = this.assignmentSlider.findIndex(a => a.week == item.prVillageAssigment.weekNumber && a.year == item.prVillageAssigment.year);
             if (this.assignmentSlider.find(a => a.week == item.prVillageAssigment.weekNumber && a.year == item.prVillageAssigment.year)) {

               if (this.assignmentSlider[index].useraccordion.find(a => a.prsId == item.prVillageAssigment.drkrishiUser2.userId)) {
                 var accIndex = this.assignmentSlider[index].useraccordion.findIndex(a => a.prsId == item.prVillageAssigment.drkrishiUser2.userId)
                 this.villageData = {} as VillageData;
                 this.villageData.villagename = item.villagetask.villageName;
                 this.villageData.status = item.isCompleted;
                 this.assignmentSlider[index].useraccordion[accIndex].villages.push(this.villageData);
                 //  this.filteredData[index].useraccordion[accIndex].villages.push(this.villageData);
               } else {
                 this.useraccordion = {} as Useraccordion
                 this.useraccordion.prsId = item.prVillageAssigment.drkrishiUser2.userId;
                 this.villageData = {} as VillageData;
                 this.villageData.villagename = item.villagetask.villageName;
                 this.villageData.status = item.isCompleted;
                 this.useraccordion.user = item.prVillageAssigment.drkrishiUser2.firstName;
                 this.useraccordion.regionId = item.prVillageAssigment.drkrishiUser2.region;
                 this.useraccordion.stateId = item.prVillageAssigment.drkrishiUser2.state
                 if (item.prVillageAssigment.drkrishiUser2.middleName) {
                   this.useraccordion.user = this.useraccordion.user + " " + item.prVillageAssigment.drkrishiUser2.middleName
                 }
                 this.useraccordion.user = this.useraccordion.user + " " + item.prVillageAssigment.drkrishiUser2.lastName
                 this.useraccordion.villages = []
                 this.useraccordion.villages.push(this.villageData);
                 this.assignmentSlider[index].month = item.prVillageAssigment.month;
                 this.assignmentSlider[index].useraccordion.push(this.useraccordion)
                 //   this.filteredData[index].useraccordion.push(this.useraccordion)
               }
             } else {
               this.assignmentlist = {} as AssignMentListModel;
               this.villageData = {} as VillageData;
               this.useraccordion = {} as Useraccordion
               this.assignmentlist.month = item.prVillageAssigment.month;
               this.assignmentlist.year = item.prVillageAssigment.year;
               this.assignmentlist.week = item.prVillageAssigment.weekNumber;
               this.useraccordion.prsId = item.prVillageAssigment.drkrishiUser2.userId;
               this.villageData.villagename = item.villagetask.villageName;
               this.villageData.status = item.isCompleted;

               this.useraccordion.user = item.prVillageAssigment.drkrishiUser2.firstName
               if (item.prVillageAssigment.drkrishiUser2.middleName) {
                 this.useraccordion.user = this.useraccordion.user + " " + item.prVillageAssigment.drkrishiUser2.middleName
               }
               this.useraccordion.user = this.useraccordion.user + " " + item.prVillageAssigment.drkrishiUser2.lastName
               this.useraccordion.regionId = item.prVillageAssigment.drkrishiUser2.region;
               this.useraccordion.stateId = item.prVillageAssigment.drkrishiUser2.state
               this.useraccordion.villages = []
               this.useraccordion.villages.push(this.villageData);
               this.assignmentlist.useraccordion = []
               this.assignmentlist.useraccordion.push(this.useraccordion)
               this.assignmentSlider[index] = this.assignmentlist;
               //  this.filteredData[index] = this.assignmentlist;
             }
           }
           this.liststring = JSON.stringify(this.assignmentSlider)
         });
       }, (err) => {

         return;
       });
   }
     */


  // convenience getter for easy access to form fields
  get f() { return this.AssignListForm.controls; }


  assignVillages: Object;
  modalRef: BsModalRef;
  oneAtATime = true;
  noRecords = false;
  AssignListForm: FormGroup;
  submitted = false;
  public states: State[];
  public state: State;
  public regions: Region[];
  public region: Region;
  public prscouts: AddUserModel[];
  public search: string = undefined;
  public assignmentSlider: AssignMentListModel[];
  public assignmentlist = {} as AssignMentListModel;
  public useraccordion = {} as Useraccordion;
  public villageData = {} as VillageData;
  public loggedInUser = {} as any;
  public userList1: AddUserModel[];
  public lastkeydown1 = 0;
  public filteredData: AssignMentListModel[];
  public rangeMonth: string;
  public liststring: string;
  public accoCount = 1;
  // Error Messages
  public textboxerrormessage: string;
  public dropdownerrormessage: string;
  public invalidvalueerrormessage: string;
  public httperrorresponsemessages: string;

  public currentSystemDate: Date;
  public currentweek: number;

  slideConfig = {
    autoplay: false,
    dots: false,
    infinite: false,
    arrows: true,
    slidesToShow: 4,
    slidesToScroll: 1,
    prevArrow: '<div class="slider-prev"></div>',
    nextArrow: '<div class="slider-next"></div>',

    responsive: [
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

/*  public getDateRangeOfWeek(weekNo) {
    this.currentSystemDate = new Date();
    const numOfdaysPastSinceLastMonday = eval(this.currentSystemDate.getDay() - 1);
    this.currentSystemDate.setDate(this.currentSystemDate.getDate() - numOfdaysPastSinceLastMonday);
    const weekNoToday = weekNo;
    const weeksInTheFuture = eval( String(weekNo - weekNoToday) );
    this.currentSystemDate.setDate(this.currentSystemDate.getDate() + eval( String(7 * weeksInTheFuture) ));
    const rangeIsFrom = eval(String(this.currentSystemDate.getMonth() + 1)) + '/' + this.currentSystemDate.getDate() + '/' + this.currentSystemDate.getFullYear();
    this.currentSystemDate.setDate(this.currentSystemDate.getDate() + 6);
    const rangeIsTo = eval(String(this.currentSystemDate.getMonth() + 1)) + '/' + this.currentSystemDate.getDate() + '/' + this.currentSystemDate.getFullYear() ;
    return 'current range is ----> ' + rangeIsFrom + ' to ' + rangeIsTo;
  }*/

  ngOnInit() {

    // Error Messages
    this.textboxerrormessage = ErrorMessages.textboxError;
    this.dropdownerrormessage = ErrorMessages.dropdownError;
    this.invalidvalueerrormessage = ErrorMessages.invalidvalueError;
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

    // Back to top functionality
    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0);
    });

    this.loggedInUser = JSON.parse(localStorage.getItem('userData'));
    this.currentSystemDate = new Date(this.loggedInUser.date); // this.loggedInUser.date;


    this.currentweek = this.loggedInUser.weekNumber;
    this.setDefaultData();

    this.data.getStates().subscribe(
      (data) => {
        this.states = data;
        this.states.forEach(item => {

          if (item.stateId == this.loggedInUser.stateId) {
            this.state = item;
            this.getRegion();
          }
        });
      }, (err) => {
        const initialState = {
          title: 'Error',
          content: this.httperrorresponsemessages,
          action: '/assignment-list'
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });

    // this.getList();
    this.assignVillages = {};

    this.AssignListForm = this.formBuilder.group({
      state: ['', ],
      region: ['', Validators.required],
      search: ['', [Validators.minLength(3), Validators.pattern('^[a-zA-Z-,]+(\s{0,1}[a-zA-Z-, ])*$')]],

    });

  }

  public getRegion() {
    this.region = undefined;

    if (this.state) {

      this.data.getRegion(this.state.stateId).subscribe(

        (data) => {

          this.regions = data;
          if (this.regions.length === 0) {
            this.region = undefined;
          }

        }, (err) => {

          const initialState = {
            title: 'Error',
            content: this.httperrorresponsemessages,
            action: '/assignment-list'
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        });

    } else {
      this.regions = undefined;
    }
  }


  // Assign Location PR SCOUT

  clicked(event) {
    let currentClass = event.target.getAttribute('class');
    if (currentClass == null) {
      currentClass = '';
      this.render.setElementAttribute(event.target, 'class', currentClass + 'location-added');
    } else if (currentClass.includes('location-added')) {
      currentClass = currentClass.replace('location-added', '');
      this.render.setElementAttribute(event.target, 'class', currentClass);
    } else {
      this.render.setElementAttribute(event.target, 'class', currentClass + ' location-added');
    }
  }

  public addAssignment(week: number) {
    this.router.navigate(['assign-villages-to-prs/' + week]);
  }

  public showAddButton(weekNumber, cyear) {

    const date = new Date(this.currentSystemDate);
    const year = date.getFullYear();
    const month = date.getMonth();
    const day = date.getDay();

    const currentWeek = this.getWeeks(year, month, day);

    if (weekNumber < currentWeek && cyear == year) {
      return false;
    } else {
      return true;
    }
  }




  public getUserIdsFirstWay($event) {
    const value = (document.getElementById('userIdFirstWay') as HTMLInputElement).value;
    if (value.length > 2) {
      this.userList1 = [];
      if ($event.timeStamp - this.lastkeydown1 > 200) {
        this.userList1 = this.searchFromArray(this.prscouts, value);
      }
    }
  }

  public searchFromArray(arr, regex) {
    let matches = [], i;
    for (i = 0; i < arr.length; i++) {
      if (arr[i].firstName.match(regex)) {
        matches.push(arr[i]);
      }
    }
    return matches;
  }
  public filterData() {

    this.submitted = true;

    // stop here if form is invalid
    if (!this.AssignListForm.invalid) {

      // this.submitted = false;
      //  this.accoCount = 0;

      const state = this.state.stateId;
      const region = this.region.regionID;
      const search = this.search;


      const searchRequest = { stateId: state, regionId: region, name: search };
      this.setDefaultData();
      this.data.searchPrsAssignTask(searchRequest).subscribe(
        (data) => {

          this.useraccordion = {} as Useraccordion;
          this.villageData = {} as VillageData;


          data.forEach(item => {
            this.noRecords = false;

            if (!this.assignmentSlider || this.assignmentSlider.length == 0) {
              // this.assignmentSlider = []
              this.assignmentlist = {} as AssignMentListModel;
              this.assignmentlist.month = item.prVillageAssigment.month;
              this.assignmentlist.year = item.prVillageAssigment.year;
              this.assignmentlist.week = item.prVillageAssigment.weekNumber;
              this.useraccordion.prsId = item.prVillageAssigment.drkrishiUser2.userId;
              this.useraccordion.regionId = item.prVillageAssigment.drkrishiUser2.region;
              this.useraccordion.stateId = item.prVillageAssigment.drkrishiUser2.state;
              this.villageData.villagename = item.villagetask.villageName;
              this.villageData.status = item.isCompleted;

              this.useraccordion.user = item.prVillageAssigment.drkrishiUser2.firstName;
              if (item.prVillageAssigment.drkrishiUser2.middleName) {
                this.useraccordion.user = this.useraccordion.user + ' ' + item.prVillageAssigment.drkrishiUser2.middleName;
              }
              this.useraccordion.user = this.useraccordion.user + ' ' + item.prVillageAssigment.drkrishiUser2.lastName;
              this.useraccordion.villages = [];
              this.useraccordion.villages.push(this.villageData);
              this.assignmentlist.useraccordion = [];
              this.assignmentlist.useraccordion.push(this.useraccordion);
              this.assignmentSlider.push(this.assignmentlist);
              // this.filteredData.push(this.assignmentlist)

            } else {
              const index = this.assignmentSlider.findIndex(a => a.week == item.prVillageAssigment.weekNumber && a.year == item.prVillageAssigment.year);
              if (this.assignmentSlider.find(a => a.week == item.prVillageAssigment.weekNumber && a.year == item.prVillageAssigment.year)) {

                if (this.assignmentSlider[index].useraccordion.find(a => a.prsId == item.prVillageAssigment.drkrishiUser2.userId)) {
                  const accIndex = this.assignmentSlider[index].useraccordion.findIndex(a => a.prsId == item.prVillageAssigment.drkrishiUser2.userId);
                  this.villageData = {} as VillageData;
                  this.villageData.villagename = item.villagetask.villageName;
                  this.villageData.status = item.isCompleted;
                  this.assignmentSlider[index].useraccordion[accIndex].villages.push(this.villageData);
                  //  this.filteredData[index].useraccordion[accIndex].villages.push(this.villageData);
                } else {
                  this.useraccordion = {} as Useraccordion;
                  this.useraccordion.prsId = item.prVillageAssigment.drkrishiUser2.userId;
                  this.villageData = {} as VillageData;
                  this.villageData.villagename = item.villagetask.villageName;
                  this.villageData.status = item.isCompleted;
                  this.useraccordion.user = item.prVillageAssigment.drkrishiUser2.firstName;
                  this.useraccordion.regionId = item.prVillageAssigment.drkrishiUser2.region;
                  this.useraccordion.stateId = item.prVillageAssigment.drkrishiUser2.state;
                  if (item.prVillageAssigment.drkrishiUser2.middleName) {
                    this.useraccordion.user = this.useraccordion.user + ' ' + item.prVillageAssigment.drkrishiUser2.middleName;
                  }
                  this.useraccordion.user = this.useraccordion.user + ' ' + item.prVillageAssigment.drkrishiUser2.lastName;
                  this.useraccordion.villages = [];
                  this.useraccordion.villages.push(this.villageData);
                  // this.assignmentSlider[index].month = item.prVillageAssigment.month;
                  this.assignmentSlider[index].useraccordion.push(this.useraccordion);
                  //   this.filteredData[index].useraccordion.push(this.useraccordion)
                }
              } else {
                this.assignmentlist = {} as AssignMentListModel;
                this.villageData = {} as VillageData;
                this.useraccordion = {} as Useraccordion;
                // this.assignmentlist.month = item.prVillageAssigment.month;
                this.assignmentlist.year = item.prVillageAssigment.year;
                this.assignmentlist.week = item.prVillageAssigment.weekNumber;
                this.useraccordion.prsId = item.prVillageAssigment.drkrishiUser2.userId;
                this.villageData.villagename = item.villagetask.villageName;
                this.villageData.status = item.isCompleted;

                this.useraccordion.user = item.prVillageAssigment.drkrishiUser2.firstName;
                if (item.prVillageAssigment.drkrishiUser2.middleName) {
                  this.useraccordion.user = this.useraccordion.user + ' ' + item.prVillageAssigment.drkrishiUser2.middleName;
                }
                this.useraccordion.user = this.useraccordion.user + ' ' + item.prVillageAssigment.drkrishiUser2.lastName;
                this.useraccordion.regionId = item.prVillageAssigment.drkrishiUser2.region;
                this.useraccordion.stateId = item.prVillageAssigment.drkrishiUser2.state;
                this.useraccordion.villages = [];
                this.useraccordion.villages.push(this.villageData);
                this.assignmentlist.useraccordion = [];
                this.assignmentlist.useraccordion.push(this.useraccordion);
                this.assignmentSlider[index] = this.assignmentlist;
                //  this.filteredData[index] = this.assignmentlist;
              }
            }
            this.liststring = JSON.stringify(this.assignmentSlider);
          });

          if (data.length == 0) {
            this.assignmentSlider = [];
            this.liststring = JSON.stringify(this.assignmentSlider);
            this.setDefaultData();
            this.noRecords = true;
          }
        }, (err) => {
          const initialState = {
            title: 'Error',
            content: this.httperrorresponsemessages,
            action: '/assignment-list'
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        });


    } else {
      return false;
    }
  }


  public getMonth(number: number) {
    const monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
      'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'
    ];

    return monthNames[number - 1];
  }

  public getMonthFromRange(weekRange: string) {
    return weekRange.slice(weekRange.indexOf('-') + 2, weekRange.indexOf('-') + 5);
  }


  public generateWeek(){
    const date = new Date(this.currentSystemDate);
    date.setDate(date.getDate() + 3 - (date.getDay() + 6) % 7);
    // January 4 is always in week 1.
    var week1 = new Date(date.getFullYear(), 0, 4);
    // Adjust to Thursday in week 1 and count number of weeks from date to week1.
    return 1 + Math.round(((date.getTime() - week1.getTime()) / 86400000 - 3 + (week1.getDay() + 6) % 7) / 7);
  }

  public setDefaultData() {

    const date = new Date(this.currentSystemDate);
    // var date = new Date(2012,12,12);


    let year = date.getFullYear();
    let month = date.getMonth();
    const day = date.getDay();



    month = 5;

    // var currentWeek = this.getWeeks(year, month, day);
    let currentWeek = this.loggedInUser.weekNumber;
    this.filteredData = [];
    this.assignmentSlider = [];

    let start = date.getDate() - date.getDay() + (date.getDay() === 0 ? -6 : 6);

    let startDate = new Date(date.setDate(start));

    for (var i = 0; i < 4; i++) {

      start = startDate.getDate() - startDate.getDay() + (startDate.getDay() === 0 ? 1 : 6) + (this.ifLeapYear(year) && startDate.getMonth() + 1 == 2 ? 1 : 0);
      startDate = new Date(date.setDate(start));
      weekNumber: 6;
      // var currMonth = startDate.getMonth() + 1;
      const start1 = startDate.getDate() - startDate.getDay() + (startDate.getDay() === 0 ? -6 : 1);
      const date1 = startDate;
      const startDate1 = new Date(date1.setDate(start1));
      const currMonth = startDate1.getMonth() + 1;

      weekNumber: 6;
      this.assignmentlist = {} as AssignMentListModel;
      weekNumber: 6;


      // this.assignmentlist.month = currMonth;
      // this.assignmentlist.weekrange = this.getrange(startDate);
      this.assignmentlist.weekrange = this.getDateRangeOfWeek(currentWeek);

      if (currentWeek <= 52) {
        this.assignmentlist.year = year;
        this.assignmentlist.week = currentWeek;
      } else if (currentWeek <= 53) {
        // year = year + 1
        this.assignmentlist.year = year;
        // currentWeek = currentWeek;
        this.assignmentlist.week = currentWeek;
      } else {
        year = year + 1;
        this.assignmentlist.year = year;
        currentWeek = currentWeek - 52;
        this.assignmentlist.week = currentWeek;
      }
      currentWeek = currentWeek + 1;

      if (start - 7 < 0) {

        if (currMonth == 1) {
          this.assignmentlist.month = 12;
          this.assignmentlist.year = year - 1;
        } else {
          this.assignmentlist.month = currMonth;
        }

      } else {

        // if(currMonth == 1){
        //   this.assignmentlist.month = 12;
        //   this.assignmentlist.year =year -1
        // }else{
        //   this.assignmentlist.month =currMonth
        // }
        if (currMonth - 1 == 0) {
          this.assignmentlist.month = (this.getCurrentMonths(currentWeek));
        } else {
          this.assignmentlist.month = (this.getCurrentMonths(currentWeek));
        }


      }

      this.assignmentlist.useraccordion = [];
      this.assignmentSlider.push(this.assignmentlist);
      this.filteredData.push(this.assignmentlist);
      // currentWeek = currentWeek ;
    }
  }

  public getDateRangeOfWeek(weekNo) {
    const d1 = new Date();
    const numOfdaysPastSinceLastMonday = eval(String(d1.getDay() - 1));
    d1.setDate(d1.getDate() - numOfdaysPastSinceLastMonday);

    const currentWeek = this.generateWeek();
    const weekNoToday = currentWeek;
    const weeksInTheFuture = eval( String(weekNo - weekNoToday) );
    d1.setDate(d1.getDate() + eval( String(7 * weeksInTheFuture) ));
    const rangeIsFrom = this.getMonth(eval(String(d1.getMonth() + 1))) + ' ' + d1.getDate();
    d1.setDate(d1.getDate() + 6);
    const rangeIsTo = this.getMonth(eval(String(d1.getMonth() + 1))) + ' ' + d1.getDate();
    return  rangeIsFrom + ' - ' + rangeIsTo;
  }
  public getCurrentMonths(weekNo) {
    const d1 = new Date();
    const numOfdaysPastSinceLastMonday = eval(String(d1.getDay() - 1));
    d1.setDate(d1.getDate() - numOfdaysPastSinceLastMonday);

    const currentWeek = this.generateWeek();
    const weekNoToday = currentWeek;
    const weeksInTheFuture = eval( String(weekNo - weekNoToday) );
    d1.setDate(d1.getDate() + eval( String(7 * weeksInTheFuture) ));
    // const rangeIsFrom = this.getMonth(eval(String(d1.getMonth() + 1))) + ' ' + d1.getDate();
    // d1.setDate(d1.getDate() + 6);
    // const rangeIsTo = this.getMonth(eval(String(d1.getMonth() + 1))) + ' ' + d1.getDate();
    return eval(String(d1.getMonth() + 1));
  }

  public getCurrentMonth(weekNo, count) {

    const d1 = new Date();
    const numOfdaysPastSinceLastMonday = eval(String(d1.getDay() - 1));
    d1.setDate(d1.getDate() - numOfdaysPastSinceLastMonday);

    const currentWeek = this.generateWeek();
    const weekNoToday = currentWeek;
    const weeksInTheFuture = eval( String(weekNo - weekNoToday) );

    if (count === 0){
      return  eval(String((d1.getMonth() + 1)));
    } else {
      d1.setDate(d1.getDate() + eval( String(7 * weeksInTheFuture) ));
      d1.setDate(d1.getDate() + 6);
      return  eval(String((d1.getMonth() + 1)));
    }
  }


  // to check whether a leap year
  public ifLeapYear(year) {
    if (year % 400 == 0) {
      return true;
    }

    // Else If a year is muliplt of 100,
    // then it is not a leap year
    if (year % 100 == 0) {
      return false;
    }

    // Else If a year is muliplt of 4,
    // then it is a leap year
    if (year % 4 == 0) {
      return true;
    }
    return false;
  }


  // to get current week
  public getWeeks(year, month, day) {
    month += 1; // use 1-12
    const a = Math.floor((14 - (month)) / 12);
    const y = year + 4800 - a;
    const m = (month) + (12 * a) - 3;
    const jd = day + Math.floor(((153 * m) + 2) / 5) +
      (365 * y) + Math.floor(y / 4) - Math.floor(y / 100) +
      Math.floor(y / 400) - 32045;

    const d4 = (jd + 31741 - (jd % 7)) % 146097 % 36524 % 1461;
    const L = Math.floor(d4 / 1460);
    const d1 = ((d4 - L) % 365) + L;
    const NumberOfWeek = Math.floor(d1 / 7);

    return NumberOfWeek;
  }

  // to get date range of week
  public getrange(date) {

    const monthName = ['Jan', 'Feb', 'March', 'Apr', 'May', 'June', 'July', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec'];
    const start = date.getDate() - date.getDay() + (date.getDay() === 0 ? -6 : 1);

    const startDate = new Date(date.setDate(start));

    const end = startDate.getDate() - startDate.getDay() + (startDate.getDay() === 0 ? -6 : 7);

    const endDate = new Date(date.setDate(end));
    const range = startDate.getDate() + ' - ' + endDate.getDate();


    return range;
  }

  public getrangeMonth() {
    const date = new Date(this.currentSystemDate);
    const start = date.getDate() - date.getDay() + (date.getDay() === 0 ? -6 : -7);

    const startDate = new Date(date.setDate(start));

    const startMonth = this.getMonth(startDate.getMonth() + 1);
    let endMonth = this.getMonth(startDate.getMonth() + 2);
    if (startDate.getMonth() + 2 > 12) {
      endMonth = this.getMonth(1);
    }

    if (startMonth == endMonth) {
      return startMonth;
    } else {
      return startMonth + '/' + endMonth;
    }


  }

  public getStatus(status) {
    if (status == 1) {
      return 'active';
    } else {
      return '';
    }
  }

  public getName(item) {
    let name = item.firstName;
    name = name + (item.middleName ? ' ' + item.middleName : '');
    name = name + ' ' + item.lastName;
    return name;
  }


}

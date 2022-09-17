import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, NavigationEnd } from "@angular/router";
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { SuccessModalComponent } from '../../modal-components/success-modal/success-modal.component';
import { ListOfUsersService } from '../list-of-users/list-of-users.service';
import { AddUserModel } from './user.model';
import{ ErrorMessages} from '../../form-validation-messages'

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.less']
})
export class AddUserComponent implements OnInit {
  modalRef: BsModalRef;
  addUser: FormGroup;
  lis_of_users: Object;
  submitted : boolean  = false;
  public shouldShow : boolean = false;
  public addUserModel = {} as AddUserModel;
  public responseMessage : any = {};
  public roleName : any = {};
  listOfState: any[];
  listOfRegion : any = [];
  listOfReportingTo: any;
  listOfRole: any[];
  public defaultStatus: string = "Inactive";
  public defaultRole: string = "Joinee";
  public ecosystems: any = [];
  public reportingRoleTo: any = {};

   //Error Messages
  public textboxerrormessage: string;
  public dropdownerrormessage: string;
  public checkboxerrormessage: string;
  public invalidvalueerrormessage: string;
  public httperrorresponsemessages: string;


  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private modalService: BsModalService,
    private userService: ListOfUsersService
  ) { }

  ngOnInit() {
    
    //Error Messages
    this.textboxerrormessage = ErrorMessages.textboxError;
    this.dropdownerrormessage = ErrorMessages.dropdownError;
    this.checkboxerrormessage = ErrorMessages.checkboxError;
    this.invalidvalueerrormessage = ErrorMessages.invalidvalueError;
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;


    //Back to top functionality
    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
          return;
      }
      window.scrollTo(0, 0)
    });

    this.addUser = this.formBuilder.group({
      firstname: ['', [Validators.required, Validators.pattern("^[a-zA-Z\s]*$")]],
      middlename: ['', [Validators.pattern("^[a-zA-Z\s]*$")]],
      lastname: ['', [Validators.required, Validators.pattern("^[a-zA-Z\s]*$")]],
      email: ['', [Validators.pattern(/^[A-Z0-9]+(?:\_[a-z0-9]+)*(?:\.[a-z0-9]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/i)]],
      mobilenumber: ['', [Validators.required, Validators.pattern("^[0-9]*$"), Validators.minLength(10)]],
      role: ['', [Validators.required]],
      state: ['', [Validators.required]],
      region: ['', [Validators.required]],
      reportingRoleName: ['', [Validators.required]],
      reporting: ['', [Validators.required]],
      userid: [''],
      status: [''],
      createdby: [''],
      datetime: [''],
    });


    this.userService.getEcosystems().subscribe(data => {
      this.ecosystems = data;

     // this.addUserModel.roleId = this.joineeRoles[0].roleId;
    });

    this.addUserModel.createdby = localStorage.getItem("loginUsername");
  }


  validateUserStatus( ) {

    this.userService.checkUserStatus( {"userId":parseInt(localStorage.getItem("loginUserid"))}).subscribe(
      (data) => {
        
        if ( !data ) {
          localStorage.setItem("userLocked","user is locked or Inactive");
          this.router.navigate(['/login']);
        }
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/add-user"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
    });

  }

  get f() { return this.addUser.controls; }

  showReportingToList() {
    
    this.listOfReportingTo = [];
    this.reportingRoleTo['state'] = this.addUserModel.state;
    this.reportingRoleTo['region'] = this.addUserModel.region;
    this.reportingRoleTo['reportingRoleId'] = this.addUserModel.reportingRoleId;


    this.userService.listOfReportingTo(this.reportingRoleTo).subscribe(data => {
      this.listOfReportingTo = data;

      if (this.listOfReportingTo.length === 0) {
        this.addUserModel.reportingTo = undefined;
        //this.addUserModel.reportingRoleId = undefined;
      }


      this.validateUserStatus();

    }, (err) => {
      const initialState = {
        title: "Error",
        content: this.httperrorresponsemessages,
        action: "/add-user"
      };
      this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
      return;
    });
  }

  // showReportingRoleToList(){
  //   this.addUserModel.reportingTo = undefined;
  //   this.addUserModel.reportingRoleId = undefined;
  // }


  getStateList(){

    this.listOfRegion = []; 
    this.listOfReportingTo = [];
    this.addUserModel.state = undefined;
    this.addUserModel.region = undefined;
    this.addUserModel.reportingRoleId = undefined;
    this.addUserModel.reportingTo = undefined;

    this.userService.listOfStates({ "roleId": this.addUserModel.roleId }).subscribe(data => {
      this.listOfState = data;
      var length = this.listOfState.length;
      for (var i = 0; i < length; i++) {
        var state = this.listOfState[i];
        if (state.stateName === "ALL") {
          this.addUserModel.state = this.listOfState[i].stateId;
          this.listOfRegion[0] = { "regionId": 0, "regionName": "ALL" };
          this.addUserModel.region = this.listOfState[i].stateId;
        }
      }


      if (this.listOfState.length === 0) {
        this.addUserModel.state = undefined;
        this.addUserModel.region = undefined;
      }

      this.validateUserStatus();

    }, (err) => {
      const initialState = {
        title: "Error",
        content: this.httperrorresponsemessages,
        action: "/add-user"
      };
      this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
      return;
    });
    
  

    this.userService.listOfRoles({"roleId":this.addUserModel.roleId}).subscribe(data => {
      this.listOfRole = data;
      if (this.listOfRole.length === 0) {
        this.addUserModel.reportingRoleId = undefined;
      }
    });  

  } 


  showRegionList() {
    this.listOfRegion = [];
    this.addUserModel.region = undefined;
    this.addUserModel.reportingTo = undefined;
    this.addUserModel.reportingRoleId = undefined;

    if ( this.addUserModel.state == undefined ) {
      return;
    }

    this.userService.listOfRegion({"state":this.addUserModel.state, "roleId":this.addUserModel.roleId}).subscribe(data => {
      this.listOfRegion = data;

      if (this.listOfRegion.length === 0) {
        this.addUserModel.region = undefined;

      }

      this.validateUserStatus();

    }, (err) => {
      const initialState = {
        title: "Error",
        content: this.httperrorresponsemessages,
        action: "/add-user"
      };
      this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
      return;
    });

  }


  onSubmit() {

    this.submitted = true;

    // stop here if form is invalid
    if (this.addUser.invalid) {
      var headerHeight = $("header").height();
      $('html,body').animate({ scrollTop: $('.form-control.ng-invalid').offset().top - headerHeight - 30 }, 200);
      return false;
    } else {

      this.userService.checkUserStatus( {"userId":parseInt(localStorage.getItem("loginUserid"))}).subscribe(
        (data) => {
         
          if ( data ) {
            
              this.addUserModel.createdby = localStorage.getItem("loginUsername");
              this.userService.addUser(this.addUserModel).subscribe(
                (data) => {

                      if (data.hasOwnProperty("status")) {

                        const initialState = {
                          title: "Success",
                          content: 'The user has been registered  successfully. A notification will be sent to the user',
                          action: "/list-of-users"
                        };
                        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });

                      } else if (data.hasOwnProperty("error")) {
                        const initialState = {
                          title: "Error",
                          content: data.error.errorMessage,
                          action: "/add-user"
                        };
                        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
                      }

                }, (err) => {
                  const initialState = {
                    title: "Error",
                    content: this.httperrorresponsemessages,
                    action: "/add-user"
                  };
                  this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
                  return;
                });

          }else{
            localStorage.setItem("userLocked","user is locked or Inactive");
            this.router.navigate(['/login']);
          }
        }, (err) => {
          const initialState = {
            title: "Error",
            content: this.httperrorresponsemessages,
            action: "/add-user"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
      });



      
    }

  }

  cancel() {
    this.router.navigate(['/list-of-users'])
  }


}

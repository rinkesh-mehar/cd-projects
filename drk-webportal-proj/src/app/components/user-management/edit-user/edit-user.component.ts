import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, NavigationEnd } from "@angular/router";
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { SuccessModalComponent } from '../../modal-components/success-modal/success-modal.component';
import { ListOfUsersService } from '../list-of-users/list-of-users.service';
import { ViewUserModel } from '../view-details/view-user-model';
import{ ErrorMessages} from '../../form-validation-messages'

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.less']
})
export class EditUserComponent implements OnInit {

  public viewUserModel = {} as ViewUserModel;


  modalRef: BsModalRef;
  editUser: FormGroup;
  lis_of_users: Object;
  submitted: boolean = false;
  public shouldShow: boolean = false;
  listOfState: any[];
  listOfRegion: any = [];
  listOfReportingTo: any;
  listOfRole: any[];
  listOfreportingRole: any[];
  editRequest: {};
  public reportingRoleTo: any = {};
  public ecosystems: any = [];

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
    private data: ListOfUsersService,
    private userService: ListOfUsersService
  ) {  }

   
  ngOnInit() {

    //Error Messages
    this.textboxerrormessage = ErrorMessages.textboxError;
    this.dropdownerrormessage = ErrorMessages.dropdownError;
    this.checkboxerrormessage = ErrorMessages.checkboxError;
    this.invalidvalueerrormessage = ErrorMessages.invalidvalueError;
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

    
    this.viewUserDetails();


    //Back to top functionality
    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0);

    });

    this.editUser = this.formBuilder.group({

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
    //Data Fetching JSON
    // this.data.getListOfUsers().subscribe(data => {
    //   this.lis_of_users = data;
    // });

    this.userService.listOfStates({}).subscribe(data => {
      
      this.listOfState = data;
    }, (err) => {
      const initialState = {
        title: "Error",
        content: this.httperrorresponsemessages,
        action: "/edit-user"
      };
      this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
      return;
    });


    this.userService.listOfRoles({}).subscribe(data => {
      
      this.listOfreportingRole = data;
    });


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
          action: "/edit-user"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
    });
  }


  checkSameReportingTo(userId){
   
    if ( this.viewUserModel.user_id == userId ) {
 
      const initialState = {
        title: "Error",
        content: 'Reporting To and user Cannot be same.',
        action: "/edit-user"
      };
      this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
    
    }
  }


  viewUserDetails() {

    this.userService.viewUser({ "userId": localStorage.getItem("useridselected") }).subscribe(
      (data) => {
        this.viewUserModel = data;
        

        this.userService.getEcosystems().subscribe(data => {
          this.ecosystems = data;
          // this.addUserModel.roleId = this.joineeRoles[0].roleId;
        });

        this.userService.listOfStates({ "roleId": this.viewUserModel.roleId }).subscribe(data => {
          this.listOfState = data;
        });

        this.userService.listOfRegion({ "state": this.viewUserModel.state, "roleId": this.viewUserModel.roleId }).subscribe(data => {
          this.listOfRegion = data;
        });

        this.userService.listOfRoles({ "roleId": this.viewUserModel.roleId }).subscribe(data => {
          this.listOfRole = data;
        });

        this.reportingRoleTo['state'] = this.viewUserModel.state;
        this.reportingRoleTo['region'] = this.viewUserModel.region;
        this.reportingRoleTo['reportingRoleId'] = this.viewUserModel.reportingRoleId;
        this.userService.listOfReportingTo(this.reportingRoleTo).subscribe(data => {
          this.listOfReportingTo = data;

        });


      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/edit-user"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }

  showReportingToList() {

    this.listOfReportingTo = [];
    this.reportingRoleTo['state'] = this.viewUserModel.state;
    this.reportingRoleTo['region'] = this.viewUserModel.region;
    this.reportingRoleTo['reportingRoleId'] = this.viewUserModel.reportingRoleId;


    this.userService.listOfReportingTo(this.reportingRoleTo).subscribe(data => {
      this.listOfReportingTo = data;

      if (this.listOfReportingTo.length === 0) {
        this.viewUserModel.reportingTo = undefined;
        //this.viewUserModel.reportingRoleId = undefined;
      }

      this.validateUserStatus();

    }, (err) => {
      const initialState = {
        title: "Error",
        content: this.httperrorresponsemessages,
        action: "/edit-user"
      };
      this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
      return;
    });
  }

  // showReportingRoleToList() {
  //   this.viewUserModel.reportingTo = undefined;
  //   this.viewUserModel.reportingRoleId = undefined;
  // }



  getStateList() {

    this.listOfRegion = [];
    this.listOfReportingTo = [];
    this.viewUserModel.state = undefined;
    this.viewUserModel.region = undefined;
    this.viewUserModel.reportingRoleId = undefined;
    this.viewUserModel.reportingTo = undefined;

    this.userService.listOfStates({ "roleId": this.viewUserModel.roleId }).subscribe(data => {
      this.listOfState = data;
      var length = this.listOfState.length;
      for (var i = 0; i < length; i++) {
        var state = this.listOfState[i];
        if (state.stateName === "ALL") {
          this.viewUserModel.state = this.listOfState[i].stateId;
          this.listOfRegion[0] = { "regionId": 0, "regionName": "ALL" };
          this.viewUserModel.region = this.listOfState[i].stateId;
        }
      }


      if (this.listOfState.length === 0) {
        this.viewUserModel.state = undefined;
        this.viewUserModel.region = undefined;
      }

      this.validateUserStatus();

    }, (err) => {
      const initialState = {
        title: "Error",
        content: this.httperrorresponsemessages,
        action: "/edit-user"
      };
      this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
      return;
    });

   

    this.userService.listOfRoles({ "roleId": this.viewUserModel.roleId }).subscribe(data => {
      this.listOfRole = data;
      if (this.listOfRole.length === 0) {
        this.viewUserModel.reportingRoleId = undefined;
      }
    }); 

  }


  listOfReportingRole() {
    
    this.viewUserModel.reportingRoleId = undefined;
    this.viewUserModel.reportingTo = undefined;

        this.userService.listOfRoles({ "roleId": this.viewUserModel.roleId }).subscribe(data => {
          this.listOfRole = data;
          if (this.listOfRole.length === 0) {
            this.viewUserModel.reportingRoleId = undefined;
          }
        });    
  }    


  showRegionList() {
    this.listOfRegion = [];
    this.viewUserModel.region = undefined;
    this.viewUserModel.reportingTo = undefined;
    this.viewUserModel.reportingRoleId = undefined;

    if (this.viewUserModel.state == undefined) {
      return;
    }

    this.userService.listOfRegion({ "state": this.viewUserModel.state, "roleId": this.viewUserModel.roleId }).subscribe(data => {
      this.listOfRegion = data;

      if (this.listOfRegion.length === 0) {
        this.viewUserModel.region = undefined;
      }

      this.validateUserStatus();

    }, (err) => {
      const initialState = {
        title: "Error",
        content: this.httperrorresponsemessages,
        action: "/edit-user"
      };
      this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
      return;
    });

  }



  get f() { return this.editUser.controls; }


  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.editUser.invalid) {
      var headerHeight = $("header").height();
      $('html,body').animate({ scrollTop: $('.form-control.ng-invalid').offset().top - headerHeight - 30 }, 200);
      return;
    }
    else {
      

      this.viewUserModel.firstName = this.viewUserModel.firstName,
      this.viewUserModel.lastName = this.viewUserModel.lastName,
      this.viewUserModel.middleName = this.viewUserModel.middleName,
      this.viewUserModel.mobileNumber = this.viewUserModel.mobileNumber,
      this.viewUserModel.state = this.viewUserModel.state,
      this.viewUserModel.region = this.viewUserModel.region,
      this.viewUserModel.reportingRoleId = this.viewUserModel.reportingRoleId,
      this.viewUserModel.reportingTo = this.viewUserModel.reportingTo
      this.viewUserModel.user_id = this.viewUserModel.user_id;
      this.viewUserModel.createdby = localStorage.getItem("loginUsername");

      if ( this.viewUserModel.user_id == this.viewUserModel.reportingTo ) {
 
        const initialState = {
          title: "Error",
          content: 'Same user cannot be in the Reporting To field.',
          action: "/edit-user"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      }

      this.userService.checkUserStatus( {"userId":parseInt(localStorage.getItem("loginUserid"))}).subscribe(
        (data) => {
          
          if ( data ) {
            
                this.userService.updateUser(this.viewUserModel).subscribe(

                  (data) => {
          
                    
          
                    if (data.hasOwnProperty("status")) {
          
                      const initialState = {
                        title: "Success",
                        content: 'User details had been updated',
                        action: "/list-of-users"
          
                      };
          
                      this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          
                    } else if (data.hasOwnProperty("error")) {
                      const initialState = {
                        title: "Error",
                        content: data.error.errorMessage,
                        action: "/edit-user"
          
                      };
          
                      this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          
                    }
                  }, (err) => {
                    const initialState = {
                      title: "Error",
                      content: this.httperrorresponsemessages,
                      action: "/edit-user"
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
            action: "/edit-user"
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

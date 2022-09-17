import { Component, OnInit, ComponentFactoryResolver } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { TranslateService } from '@ngx-translate/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from "@angular/router";
import { SuccessModalComponent } from '../success-modal/success-modal.component';
import { ListOfUsersService } from '../../user-management/list-of-users/list-of-users.service';
import{ ErrorMessages} from '../../form-validation-messages'

@Component({
  selector: 'app-assign-role-modal',
  templateUrl: './assign-role-modal.component.html',
  styleUrls: ['./assign-role-modal.component.less']
})
export class AssignRoleModalComponent implements OnInit {
  heading: any;
  content: any;
  assignModalForm: FormGroup;
  listOfChangeRoles: any[];
  parameter: number;
  submitted = false;
  modalRef: BsModalRef;
  assignRoleName: string;
  subRoleName: string;
  assignStateId: string;
  sendRequest: object;
  subRoleHidden: boolean = true;
  selectedRoleName: string = "";
  private disableTileId: boolean = false;

  //Error Messages
  public dropdownerrormessage: string;
  public httperrorresponsemessages: string;


  constructor(private bsModalRef: BsModalRef,
    private translateService: TranslateService,
    private formBuilder: FormBuilder,
    private router: Router,
    private modalService: BsModalService,
    private data: ListOfUsersService,
    private userService: ListOfUsersService) { }

  ngOnInit() {


     //Error Messages
     this.dropdownerrormessage = ErrorMessages.dropdownError;
     this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

    this.assignModalForm = this.formBuilder.group({
      selectrole: ['', [Validators.required]]
    });

    this.userService.changeRoles({ "userId": localStorage.getItem("useridselected") }).subscribe(data => {
      this.listOfChangeRoles = data;
    });

  }

  initializationForm() {

    this.assignModalForm = this.formBuilder.group({
      selectrole: ['', [Validators.required]]
    });
  }

  // convenience getter for easy access to form fields
  get f() { return this.assignModalForm.controls; }

  close() {
    this.bsModalRef.hide();
  }


  onSelect() {
    this.submitted = true;
    // stop here if form is invalid
    if (this.assignModalForm.invalid) {
      return;
    } else {


      this.userService.checkUserStatus( {"userId":parseInt(localStorage.getItem("loginUserid"))}).subscribe(
        (data) => {
          this.close();
          if ( data ) {
            
            this.sendRequest = {
              "userId": localStorage.getItem("useridselected"),
              "roleId": this.assignRoleName
            };
      
            
            this.userService.saveUserRole(this.sendRequest).subscribe(
              (data) => {
      
                if (data.hasOwnProperty("status")) {
      
                  const initialState = {
                    title: "Success",
                    //content: 'The user ' + localStorage.getItem("assigningName") + ' has been assigned with the role ' + this.selectedRoleName + ' successfully',
                    content: 'User role had been updated successfully',
                    action: '/list-of-users'
      
                  };
                  this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop : 'static', keyboard : false });
      
                } else if (data.hasOwnProperty("error")) {
      
                  const initialState = {
                    title: "Error",
                    content: data.error.errorMessage,
                    action: '/list-of-users'
      
                  };
                  this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop : 'static', keyboard : false });
      
                }
              }, (err) => {
                this.close();
                const initialState = {
                  title: "Error",
                  content: this.httperrorresponsemessages,
                  action: "/list-of-users"
                };
                this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
                return;
              });

          }else{

            localStorage.setItem("userLocked","user is locked or Inactive");
            this.router.navigate(['/login']);
          }
        }, (err) => {
          this.close();
          const initialState = {
            title: "Error",
            content: this.httperrorresponsemessages,
            action: "/list-of-users"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
      });



      

    }

  }

}

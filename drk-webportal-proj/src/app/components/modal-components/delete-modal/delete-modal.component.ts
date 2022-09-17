import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { TranslateService } from '@ngx-translate/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from "@angular/router";
import { SuccessModalComponent } from '../success-modal/success-modal.component';
import { ErrorMessages } from '../../form-validation-messages'
import { ListOfUsersService } from '../../user-management/list-of-users/list-of-users.service';



@Component({
  selector: 'app-delete-modal',
  templateUrl: './delete-modal.component.html',
  styleUrls: ['./delete-modal.component.less']
})
export class DeleteModalComponent implements OnInit {

  deleteModalForm: FormGroup;
  parameter: number;
  submitted = false;
  modalRef: BsModalRef;
  usercomments: string;
  private responseMessage = {};
  heading: any;
  content: any;

  //Error Messages
  public textboxerrormessage: string;
  public dropdownerrormessage: string;
  public checkboxerrormessage: string;
  public invalidvalueerrormessage: string;


  constructor(
    private bsModalRef: BsModalRef,
    private translateService: TranslateService,
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

    this.deleteModalForm = this.formBuilder.group({
      comments: ['', [Validators.required, Validators.pattern("[^ ](.*|\n|\r|\r\n)*")]],
    });
  }
  // convenience getter for easy access to form fields
  get f() { return this.deleteModalForm.controls; }


  close() {
    this.bsModalRef.hide();

  }


  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.deleteModalForm.invalid) {
      return;
    } else {

      this.userService.deleteUser({ "userId": localStorage.getItem("useridselected"), "comments": this.usercomments }).subscribe(
        (data) => {

          if (data.hasOwnProperty("status")) {

            this.router.navigate(['/list-of-users']);

            const initialState = {
              title: "Success",
              content: 'The user has been deleted successfully',
              action: '/list-of-users'
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
            this.close();


          } else if (data.hasOwnProperty("error")) {
            const initialState = {
              title: "Error",
              content: data.error.errorMessage,
              action: '/list-of-users'
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
            this.close();

          }
        }, (err) => {
        });

    }


  }


  onActivate() {

    this.submitted = true;

    // stop here if form is invalid
    if (this.deleteModalForm.invalid) {
      return;
    }

    this.userService.checkUserStatus({ "userId": parseInt(localStorage.getItem("loginUserid")) }).subscribe(
      (data) => {

        if (data) {

          this.userService.activateUser({ "userId": localStorage.getItem("useridselected"), "comments": this.usercomments }).subscribe(
            (data) => {
              this.responseMessage = data;
              if (this.responseMessage.hasOwnProperty("status")) {

                const initialState = {
                  title: "Success",
                  content: 'User had been activated successfully',
                  action: '/list-of-users'
                };
                this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
                this.close();


              } else if (this.responseMessage.hasOwnProperty("error")) {

                const initialState = {
                  title: "Error",
                  content: data.error.errorMessage,
                  action: '/list-of-users'
                };
                this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
                this.close();
              }
            }, (err) => {
            });

        } else {
          this.close();
          localStorage.setItem("userLocked", "user is locked or Inactive");
          this.router.navigate(['/login']);
        }
      }, (err) => {
      });
  }


  onDeactivate() {

    this.submitted = true;


    // stop here if form is invalid
    if (this.deleteModalForm.invalid) {
      return;
    } else {
      this.userService.checkUserStatus({ "userId": parseInt(localStorage.getItem("loginUserid")) }).subscribe(
        (data) => {

          if (data) {

            this.userService.inActivateUser({ "userId": localStorage.getItem("useridselected"), "comments": this.usercomments }).subscribe(
              (data) => {
                this.responseMessage = data;
                if (this.responseMessage.hasOwnProperty("status")) {

                  const initialState = {
                    title: "Success",
                    content: 'User had been deactivated successfully',
                    action: '/list-of-users'
                  };
                  this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
                  this.close();


                } else if (this.responseMessage.hasOwnProperty("error")) {
                  const initialState = {
                    title: "Error",
                    content: data.error.errorMessage,
                    action: '/list-of-users'
                  };
                  this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
                  this.close();

                }
              }, (err) => {
              });

          } else {
            this.close();
            localStorage.setItem("userLocked", "user is locked or Inactive");
            this.router.navigate(['/login']);
          }
        }, (err) => {
        });
    }
  }

}

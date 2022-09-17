import { Component, OnInit, ɵConsole } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { Router, NavigationEnd } from "@angular/router";
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import {SuccessModalComponent} from '../../modal-components/success-modal/success-modal.component';
import { ChangePasswordService } from './change-password-service';
import { ChangePasswordModel } from './change-password-model';
import{ ErrorMessages} from '../../form-validation-messages'
import { ListOfUsersService } from '../../user-management/list-of-users/list-of-users.service';

declare var $;

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.less']
})
export class ChangePasswordComponent implements OnInit {
  changepasswordForm: FormGroup;
  submitted = false;
  modalRef: BsModalRef;

  public changePasswordModel = {} as ChangePasswordModel;
  responseMessage = {};
  invalidAttempt = '';
  errorMessage = {};
  passwordTitle = 'Change Password';
  passwordLableMessage = 'Old Password';
  passwordErrorMessageMatch = 'Old password and New password cannot be the same.';
  showMyContainer: boolean = false;
  passwordChangeErrroMessage = '';
  public checkPasswordError = false;
  userData = {};
  showCancelButton: Boolean;


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
    private changePasswordService: ChangePasswordService, 
    private userService: ListOfUsersService
  ) { }

  ngOnInit() {

    // Show Hide Cancel Button

    var is_forced_pwd_change = localStorage.getItem("is_forced_pwd_change") != null ? localStorage.getItem("is_forced_pwd_change") : "1";
    var passwordChangeMessage = localStorage.getItem("passwordChangeMessage") != null ? localStorage.getItem("passwordChangeMessage") : "1";

    if ( parseInt (is_forced_pwd_change) === 0 || parseInt (passwordChangeMessage) === 0 ) {
      this.showCancelButton = true;
    }else{
      this.showCancelButton = true;
    }

    //Error Messages
    this.textboxerrormessage = ErrorMessages.textboxError;
    this.dropdownerrormessage = ErrorMessages.dropdownError;
    this.checkboxerrormessage = ErrorMessages.checkboxError;
    this.invalidvalueerrormessage = ErrorMessages.invalidvalueError;
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

    this.checkModelVal();

    //Back to top functionality
    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0)
    });

    var is_forced_pwd_change = localStorage.getItem("is_forced_pwd_change");

    if (parseInt(is_forced_pwd_change) === 0) {
      this.passwordTitle = 'Set Password';
      this.passwordLableMessage = 'One Time Password';
      this.passwordErrorMessageMatch = 'One time password and New password cannot be the same.';
    } else {
      this.passwordLableMessage = 'Old Password';
      this.passwordErrorMessageMatch = 'Old password and New password cannot be the same.';
    }


    this.changepasswordForm = this.formBuilder.group({
      onetimepassword: ['', [Validators.required]],
      password: ['', [Validators.required, PasswordStrengthValidator]],
      confirmPassword: ['', Validators.required]
    },
      {
        validator: MustMatch('password', 'confirmPassword')
      }
    );
  }


  cancel() {

    var is_forced_pwd_change = localStorage.getItem("is_forced_pwd_change") != null ? localStorage.getItem("is_forced_pwd_change") : "1";
    var passwordChangeMessage = localStorage.getItem("passwordChangeMessage") != null ? localStorage.getItem("passwordChangeMessage") : "1";


    if ( parseInt (is_forced_pwd_change) === 0 || parseInt (passwordChangeMessage) === 0 ) {
      localStorage.clear()
      this.router.navigate(['/'])
    }else{
      this.router.navigate([localStorage.getItem("roleUrl")]);
    }


  }

  checkModelVal() {

    if ( this.changePasswordModel.oldPassword === undefined || this.changePasswordModel.oldPassword === '' ) {
      return true;
    }

    return this.changePasswordModel.oldPassword !== this.changePasswordModel.newPassword;
 }


  // convenience getter for easy access to form fields
    get f() { return this.changepasswordForm.controls; }

   

    onSubmit() {
      this.submitted = true;
      
      
      // stop here if form is invalid

      if (this.changepasswordForm.invalid) {
        
        return;
      }else if ($(".password-rule ul li.success").length == $(".password-rule ul li").length) {
       

        if(this.changePasswordModel.oldPassword == this.changePasswordModel.newPassword){
          return;
        }

        this.userService.checkUserStatus( {"userId":parseInt(localStorage.getItem("loginUserid"))}).subscribe(
          (data) => {
            
            if ( data ) {
              
                    this.changePasswordModel['userId'] = localStorage.getItem("loginUserid");

                    if (localStorage.getItem("is_forced_pwd_change") == "0" ) {
                        
                      this.changePasswordService.resetPassword(this.changePasswordModel).subscribe(
                              
                              (data) => {
                                  this.responseMessage = data;
                                
                                  if ( this.responseMessage.hasOwnProperty("status") ) {
                                    
                                    localStorage.setItem("is_forced_pwd_change", "1");

                                    const initialState = {
                                      title: 'Success',
                                      content: 'Your password had been updated successfully.',
                                      action: localStorage.getItem('roleUrl'),
                                    };
                                    this.modalRef = this.modalService.show(SuccessModalComponent, {initialState, backdrop : 'static', keyboard : false});
                                  
                                  //  this.router.navigate([localStorage.getItem('roleUrl')])

                                  }else if ( this.responseMessage.hasOwnProperty("error") ) {
                                    //this.passwordChangeErrroMessage = data.error.errorMessage;
                                    //this.showMyContainer = true;
                                    const initialState = {
                                      title: "Error",
                                      content: (data.error.errorMessage),
                                      action: "/change-password"
                                    };
                                    this.modalRef = this.modalService.show(SuccessModalComponent, {initialState, backdrop : 'static', keyboard : false});
                                  }
                                                    
                              }, (err) => {
                                const initialState = {
                                  title: "Error",
                                  content: this.httperrorresponsemessages,
                                  action: "/change-password"
                                };
                                this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
                                return;
                              });

                    }else{
                      
                      this.changePasswordService.changePassword(this.changePasswordModel).subscribe(
                        (data) => {

                            this.responseMessage = data;
                          
                            if ( this.responseMessage.hasOwnProperty("status") ) {
                              localStorage.setItem("passwordChangeMessage", "1");

                              const initialState = {
                                title: 'Success',
                                content: 'Your password had been changed successfully.',
                                action: localStorage.getItem('roleUrl'),
                              };
                              this.modalRef = this.modalService.show(SuccessModalComponent, {initialState, backdrop : 'static', keyboard : false });
                              //this.router.navigate([localStorage.getItem('roleUrl')])

                            }else if ( this.responseMessage.hasOwnProperty("error") ) {
                              const initialState = {
                                title: "Error",
                                content: (data.error.errorMessage),
                                action: "/change-password"
                              };
                              this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop : 'static', keyboard : false  });
                            }
                                              
                        }, (err) => {
                          const initialState = {
                            title: "Error",
                            content: this.httperrorresponsemessages,
                            action: "/change-password"
                          };
                          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
                          return;
                        });
                  }

            }else{
              localStorage.setItem("userLocked","user is locked or Inactive");
              this.router.navigate(['/login']);
            }
          }, (err) => {
            const initialState = {
              title: "Error",
              content: this.httperrorresponsemessages,
              action: "/change-password"
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
            return;
        });


       




    } else {
      return;
    }

  }

}

// custom validator to check that two fields match
export function MustMatch(controlName: string, matchingControlName: string) {
  return (formGroup: FormGroup) => {
    const control = formGroup.controls[controlName];
    const matchingControl = formGroup.controls[matchingControlName];

    if (matchingControl.errors && !matchingControl.errors.mustMatch) {
      // return if another validator has already found an error on the matchingControl
      return;
    }

    // set error on matchingControl if validation fails
    if (control.value !== matchingControl.value) {
      matchingControl.setErrors({ mustMatch: true });
    } else {
      matchingControl.setErrors(null);
    }
  }

}


// Password strength validator

export const PasswordStrengthValidator = function (control: AbstractControl): ValidationErrors | null {

  let value: string = control.value || '';
  let msg = "";
  if (!value) {
    return null
  }

  let upperCaseCharacters = /[A-Z]+/g;
  let lowerCaseCharacters = /[a-z]+/g;
  let numberCharacters = /[0-9]+/g;
  let specialCharacters = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/;

  if (upperCaseCharacters.test(value) === true) {
    $(".password-rule ul li.uppercase").addClass("success");
  } else if (upperCaseCharacters.test(value) === false) {
    $(".password-rule ul li.uppercase").removeClass("success");
  }

  if (lowerCaseCharacters.test(value) === true) {
    $(".password-rule ul li.lowercase").addClass("success");
  } else if (lowerCaseCharacters.test(value) === false) {
    $(".password-rule ul li.lowercase").removeClass("success");
  }

  if (specialCharacters.test(value) === true) {
    $(".password-rule ul li.non-alphanumeric").addClass("success");
  } else if (specialCharacters.test(value) === false) {
    $(".password-rule ul li.non-alphanumeric").removeClass("success");
  }

  $(".form-box.change-password .form-container .new-password").keyup(function () {
    var value = $(this).val();

    if ($(this).val() === '') {
      $(".password-rule ul li").removeClass("success");
    }
    if (value.length >= 8) {
      $(".password-rule ul li.characters").addClass("success");
    } else {
      $(".password-rule ul li.characters").removeClass("success");
    }
  });

}

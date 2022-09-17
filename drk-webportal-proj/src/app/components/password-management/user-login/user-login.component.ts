import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from "@angular/router";
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { LoginService } from './login.service';
import { LoginModel, menuUrl } from './login.model';
import{ ErrorMessages} from '../../form-validation-messages'
import { LocationStrategy } from '@angular/common';
import {SuccessModalComponent} from '../../modal-components/success-modal/success-modal.component';

@Component({
    selector: 'app-user-login',
    templateUrl: './user-login.component.html',
    styleUrls: ['./user-login.component.less']
})
export class UserLoginComponent implements OnInit {
    loginForm: FormGroup;
    submitted = false;
    modalRef: BsModalRef;
    public loginModel = {} as LoginModel;
    responseMessage = {};
    invalidAttempt = '';
    errorMessage = {};
    userData = {};
    menuUrl: any={};
    public rememberMeMobileNum: boolean = false ;

    //Error Messages
    public textboxerrormessage: string;
    public dropdownerrormessage: string;
    public checkboxerrormessage: string;
    public invalidvalueerrormessage: string;
    public httperrorresponsemessages: string;


    //Hide Div
    showMyContainer: boolean = false;

    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private loginService: LoginService,
        private location: LocationStrategy,
        private modalService: BsModalService,
        
    ) { 

        history.pushState(null, null, window.location.href);  
         this.location.onPopState(() => {
         history.pushState(null, null, window.location.href);
        });

    }

    ngOnInit() {

        for (let i = 1; i <= this.modalService.getModalsCount(); i++) {
            this.modalService.hide(i);
        }

        //Error Messages
        this.textboxerrormessage = ErrorMessages.textboxError;
        this.dropdownerrormessage = ErrorMessages.dropdownError;
        this.checkboxerrormessage = ErrorMessages.checkboxError;
        this.invalidvalueerrormessage = ErrorMessages.invalidvalueError;
        this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

        this.loginForm = this.formBuilder.group({
            mobilenumber: ['', [Validators.required, Validators.pattern("^[0-9]*$"), Validators.minLength(10)]],
            password: ['', [Validators.required]],
        });


        if( localStorage.getItem("userLocked") != null ) {
            this.showMyContainer = true;  
            this.invalidAttempt = "Your account is locked. Please contact admin."; //localStorage.getItem("userLocked"); 
            localStorage.removeItem("userLocked");
        }

       
        if( sessionStorage.getItem("rememberMe") != null ) {
            this.rememberMeMobileNum = true;
            this.loginModel.mobileNumber = sessionStorage.getItem("rememberMe");
        }else{
            sessionStorage.removeItem("rememberMe");
            this.loginModel.mobileNumber = "";
        }

    }

    // convenience getter for easy access to form fields
    get f() { return this.loginForm.controls; }

    rememberMe(event) {
        this.rememberMeMobileNum =event.target.checked;
    }


    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.loginForm.invalid) {
            return;
        } else {
            //  this.loginService.saveUser(this.loginModel).su;
            //this.router.navigate(['/assignment-list'])

            if ( this.rememberMeMobileNum ) {
                sessionStorage.setItem("rememberMe", this.loginModel.mobileNumber );
            }else{
                sessionStorage.removeItem("rememberMe");
            }


            this.loginService.loginUser(this.loginModel).subscribe(
                (data) => {

                    this.responseMessage = data;

                    if (this.responseMessage.hasOwnProperty("status")) {
                        this.showMyContainer = false;
                        this.userData = data.data;

                        if(this.responseMessage['header'] != null && this.responseMessage['header']['token'] != null) {
                            let cTime = new Date().getTime()
                            let session = {
                                at:this.responseMessage['header']['token'], 
                                st:cTime+(1000*60*60),
                                vt:cTime+(1000*60*60)
                        }
                            localStorage.setItem('session', JSON.stringify(session));
                        }


                        localStorage.setItem("loginUserid", this.userData['userId']);
                        localStorage.setItem("loginUsername", this.userData['name']);
                        localStorage.setItem("loginRoleName", this.userData['roleName']);
                        localStorage.setItem("lastLoginTime", this.userData['lastLoginTime']);
                        localStorage.setItem("is_forced_pwd_change", this.userData['is_forced_pwd_change']);
                        localStorage.setItem("userData", JSON.stringify(this.userData));
                        
                        //this.menuUrl = this.userData['menuUrl']
                      
                        
                        var menuArray = this.userData['menuUrl'];

                        var length =  menuArray.length;
                        for ( var j=0 ; j < length ; j++  ) {
                            var obj = menuArray[j];
                            if ( j == 0 ) {                               
                                localStorage.setItem("roleUrl", obj['url']);
                            }
                        }
                        
                        localStorage.setItem("menuArray", JSON.stringify (this.userData['menuUrl'] ));

                        if ( this.userData.hasOwnProperty("passwordChangeMessage")) {
                            localStorage.setItem("passwordChangeMessage", "0");
                            const initialState = {
                                title: "Password Expired",
                                content: 'Your password is expired and must be changed. You will be directed to change password page.',
                                action: "/change-password",
                              };
                              this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
                        
                        }else {

                            if (this.userData['is_forced_pwd_change'] === 0) {
                                this.router.navigate(['/change-password'])
                            }else{
                                this.router.navigate([localStorage.getItem("roleUrl")])
                            } 
                        }

                    } else if (this.responseMessage.hasOwnProperty("error")) {
                        this.showMyContainer = true;
                        this.invalidAttempt = data.error.errorMessage;
                    }
                }, (err) => {
                    const initialState = {
                        title: "Error",
                        content: this.httperrorresponsemessages,
                        action: "/"
                      };
                      this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
                      return;
                });
        }
    }
}

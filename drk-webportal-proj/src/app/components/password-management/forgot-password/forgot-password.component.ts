import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {Router} from "@angular/router";
import {SuccessModalComponent} from '../../modal-components/success-modal/success-modal.component';
import { ForgotPasswordService } from './forgot-password-service';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import{ ErrorMessages} from '../../form-validation-messages'
import { LocationStrategy } from '@angular/common';



declare var $;

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.less']
})
export class ForgotPasswordComponent implements OnInit {

    forogotpasswordForm: FormGroup;
    submitted = false;
  
    public mobileNumber : string;
    modalRef: BsModalRef;
    
    public forgotErrroMessage : string;
    showMyContainer: boolean = false;

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
      private location: LocationStrategy,
      private forgotPasswordService:ForgotPasswordService
     ) {
      
      history.pushState(null, null, window.location.href);  
      this.location.onPopState(() => {
        history.pushState(null, null, window.location.href);
      });
      
     }

    ngOnInit() {

        //Error Messages
        this.textboxerrormessage = ErrorMessages.textboxError;
        this.dropdownerrormessage = ErrorMessages.dropdownError;
        this.checkboxerrormessage = ErrorMessages.checkboxError;
        this.invalidvalueerrormessage = ErrorMessages.invalidvalueError;
        this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

        this.forogotpasswordForm = this.formBuilder.group({
            mobilenumber: ['', [Validators.required, Validators.pattern("^[0-9]*$"), Validators.minLength(10)]],
          });

        if( localStorage.getItem("forgotErrroMessage") != null ) {
          this.showMyContainer = true;  
          this.forgotErrroMessage = localStorage.getItem("forgotErrroMessage"); 
        } 

    }

    // convenience getter for easy access to form fields
    get f() { return this.forogotpasswordForm.controls; }

   
    

    onSubmit() {
        this.submitted = true;
       
        // stop here if form is invalid
        if (this.forogotpasswordForm.invalid) {
            return;
        }else{

          var request = {"mobileNumber": this.mobileNumber, "channel":"web"};
            this.forgotPasswordService.forgotPassword(request).subscribe(
                (data) => {

                    if ( data.hasOwnProperty("status") ) {
                      
                      localStorage.removeItem("forgotErrroMessage");
                      
                      this.startCountdown(10);
                      const initialState = {
                        title: 'Success',
                        content: data.status.detailMessage,
                        action: '/login'
                      };
                      this.modalRef = this.modalService.show(SuccessModalComponent, {initialState, backdrop : 'static', keyboard : false });
                      
                    }else if ( data.hasOwnProperty("error") ) {
                      
                      this.showMyContainer = true;  
                      this.forgotErrroMessage = data.error.errorMessage;   

                      localStorage.setItem("forgotErrroMessage", this.forgotErrroMessage);

                      this.router.routeReuseStrategy.shouldReuseRoute = function () {
                        return false;
                      };
                      this.router.onSameUrlNavigation = 'reload';
                      this.router.navigate(['/forgot-password']);
                    }                    
                }, (err) => {
                  const initialState = {
                    title: "Error",
                    content: this.httperrorresponsemessages,
                    action: "/forgot-password"
                  };
                  this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
                  return;
            });
        }
    }

    cancel () {
      localStorage.removeItem("forgotErrroMessage");
      this.router.navigate(['/login'])    
    }


    startCountdown(seconds){
      var counter = seconds;
    
      var interval = setInterval(() => {
        counter--;
        
        if(counter < 0 ){
          // The code here will run when
          // the timer has reached zero.    
          
          clearInterval(interval);
          this.router.navigate(['/login']);
          this.modalRef.hide();
          
        };
      }, 1000);
    };
}

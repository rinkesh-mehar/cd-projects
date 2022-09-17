import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {Router} from "@angular/router";
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import {DefaultModalComponent} from '../../modal-components/default-modal/default-modal.component';

@Component({
  selector: 'app-otp-verification',
  templateUrl: './otp-verification.component.html',
  styleUrls: ['./otp-verification.component.less']
})
export class OtpVerificationComponent implements OnInit {


  //Input field change
  onInputEntry(event, nextInput) {
    let input = event.target;
    let length = input.value.length;
    let maxLength = input.attributes.maxlength.value;
  
    if (length >= maxLength) {
      nextInput.focus();
    }
  }

    verificationForm: FormGroup;
    submitted = false;
    modalRef: BsModalRef;

    constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      private modalService: BsModalService,
     ) {}

    ngOnInit() {
        this.verificationForm = this.formBuilder.group({
          verificationinputOne: ['', [Validators.required]],
          verificationinputTwo: ['', [Validators.required]],
          verificationinputThree: ['', [Validators.required]],
          verificationinputFour: ['', [Validators.required]],
          verificationinputFive: ['', [Validators.required]],
          verificationinputSix: ['', [Validators.required]],
        });
    }

    // convenience getter for easy access to form fields
    get f() { return this.verificationForm.controls; }

    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.verificationForm.invalid) {
            return;
        }
        const initialState = {
          title: 'New Password',
          content: 'The new password will be sent to you soon. If you do not receive an email, please contact the system administrator. You will be redirected to the login page in 10 seconds. If you do not, then please',
          link: 'Click Here',
          action: '/login'
        };
        this.modalRef = this.modalService.show(DefaultModalComponent, {initialState, backdrop : 'static', keyboard : false});
    }
}

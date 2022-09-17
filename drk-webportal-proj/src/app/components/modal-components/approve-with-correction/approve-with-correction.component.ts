import { Component, OnInit } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { TranslateService } from '@ngx-translate/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from "@angular/router";
import { SuccessModalComponent } from '../success-modal/success-modal.component';
import { approveCorrection } from './approve-with-correction.model';
import { approveCorrectionService } from './approve-with-correction.service';
import{ ErrorMessages} from '../../form-validation-messages'

@Component({
  selector: 'app-approve-with-correction',
  templateUrl: './approve-with-correction.component.html',
  styleUrls: ['./approve-with-correction.component.less']
})
export class ApproveWithCorrectionComponent implements OnInit {
  approveCorrectionForm: FormGroup;
  submitted = false;
  modalRef: BsModalRef;
  public assignrole = {} as approveCorrection;
  title: any;

  
    //Error Messages
    public textboxerrormessage: string;
    public httperrorresponsemessages: string;

  constructor(
    private bsModalRef: BsModalRef,
    private translateService: TranslateService,
    private formBuilder: FormBuilder,
    private router: Router,
    private modalService: BsModalService,
    private ApproveCorrectionService: approveCorrectionService,
  ) { }

  ngOnInit() {

    //Error Messages
    this.textboxerrormessage = ErrorMessages.textboxError;
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

    this.approveCorrectionForm = this.formBuilder.group({
      comments: ['', [Validators.required]],
    });
  }
  get f() { return this.approveCorrectionForm.controls; }
  close() {
    this.bsModalRef.hide();
  }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (!this.approveCorrectionForm.invalid) {

      this.ApproveCorrectionService.approveCorrectionDta(this.assignrole).subscribe(
        (data) => {
          this.assignrole = data;
          const initialState = {
            title: "Approve with Correction",
            content: 'Diagnosis rejected',
            action: "/sample-diagnosis-approval-list"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop : 'static', keyboard : false });
          this.close();
          return;

        }, (err) => {
          const initialState = {
            title: "Error",
            content: this.httperrorresponsemessages,
            action: "/sample-diagnosis-approval-form"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop : 'static', keyboard : false });
          this.close();

          return;

        });
    } else {
      return;
    }
  }

}

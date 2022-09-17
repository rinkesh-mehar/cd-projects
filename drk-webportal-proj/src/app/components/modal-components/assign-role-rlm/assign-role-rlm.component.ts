import { Component, OnInit } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { TranslateService } from '@ngx-translate/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from "@angular/router";
import { SuccessModalComponent } from '../success-modal/success-modal.component';
import { AssignRoleRlm } from './assign-role-rlm.model';
import { assignRoleRlmService } from './assign-role-rlm.service';
import{ ErrorMessages} from '../../form-validation-messages'

@Component({
  selector: 'app-assign-role-rlm',
  templateUrl: './assign-role-rlm.component.html',
  styleUrls: ['./assign-role-rlm.component.less']
})
export class AssignRoleRlmComponent implements OnInit {
  assignForm: FormGroup;
  submitted = false;
  modalRef: BsModalRef;
  public assignrole = {} as AssignRoleRlm;
  title: any;

  //Error Messages
  public textboxerrormessage: string;
  public dropdownerrormessage: string;
  public checkboxerrormessage: string;
  public invalidvalueerrormessage: string;
  public httperrorresponsemessages: string;

  constructor(
    private bsModalRef: BsModalRef,
    private translateService: TranslateService,
    private formBuilder: FormBuilder,
    private router: Router,
    private modalService: BsModalService,
    private AssignRoleRlmService: assignRoleRlmService,
  ) { }

  ngOnInit() {

    //Error Messages
    this.textboxerrormessage = ErrorMessages.textboxError;
    this.dropdownerrormessage = ErrorMessages.dropdownError;
    this.checkboxerrormessage = ErrorMessages.checkboxError;
    this.invalidvalueerrormessage = ErrorMessages.invalidvalueError;
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

    this.assignForm = this.formBuilder.group({
      assign: ['', [Validators.required]],
      comments: ['', [Validators.required]],
    });
  }
  get f() { return this.assignForm.controls; }
  close() {
    this.bsModalRef.hide();
  }
  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (!this.assignForm.invalid) {

      this.AssignRoleRlmService.assignUserRoleRlm(this.assignrole).subscribe(
        (data) => {
          this.assignrole = data;

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

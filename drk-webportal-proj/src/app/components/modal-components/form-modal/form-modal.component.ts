import { Component, OnInit } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { TranslateService } from '@ngx-translate/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {Router} from "@angular/router";
import {SuccessModalComponent} from '../success-modal/success-modal.component';
@Component({
  selector: 'app-form-modal',
  templateUrl: './form-modal.component.html',
  styleUrls: ['./form-modal.component.less']
})
export class FormModalComponent implements OnInit {
  modalForm: FormGroup;
  parameter: number;
  submitted = false;
  modalRef: BsModalRef;
  constructor(private bsModalRef: BsModalRef,
    private translateService: TranslateService,
    private formBuilder: FormBuilder,
    private router: Router,
    private modalService: BsModalService) { }

  ngOnInit() {
    this.modalForm = this.formBuilder.group({
      callingstatus: ['', [Validators.required]],
      Improvement:['accept'],
  });
  }
// convenience getter for easy access to form fields
get f() { return this.modalForm.controls; }
  close() {
    this.bsModalRef.hide();
  }
  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.modalForm.invalid) {
        
        return;
    }

    const initialState = {
      title:"Success",
      content: 'Comments have been saved successfully'
    };
    this.modalRef = this.modalService.show(SuccessModalComponent, {initialState, backdrop : 'static', keyboard : false});
    this.close();
}
CallingStatus: any =[
  'Calling Status 1',
  'Calling Status 2',
  'Calling Status 3',
];

}

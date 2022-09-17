import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Router } from "@angular/router";


@Component({
  selector: 'app-success-modal',
  templateUrl: './success-modal.component.html',
  styleUrls: ['./success-modal.component.less']
})
export class SuccessModalComponent implements OnInit {

  parameter: number;
  title: any;
  content: any;
  action: any;
  constructor(
    private bsModalRef: BsModalRef,
    private router: Router
  ) { }

  ngOnInit() {

  }
  confirm() {
    this.close();
  }
  close() {
    this.bsModalRef.hide();


    var nonreloadpages = ["/add-user", "/edit-user", "/assign-villages-to-prs/", "/change-password", "/sample-diagnosis-approval-list", "/dr-krishi-non-technical-information-form/", "/vehicle-details/", "/vehicle-schedule", "/dr-krishi-technical-form/", "/individual-kyc-details/",
                          "/dr-krishi-non-technical-forward-lead-form/", "/dr-krishi-non-technical-spot-lead-form/"];
    var reloadpages = nonreloadpages.includes(this.bsModalRef.content.action.replace(/[0-9]/g, ''));


    if( !reloadpages ){
      this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
        this.router.navigate([this.bsModalRef.content.action])
      });
    }

    //this.router.navigate(this.bsModalRef.content.action);
  }
}

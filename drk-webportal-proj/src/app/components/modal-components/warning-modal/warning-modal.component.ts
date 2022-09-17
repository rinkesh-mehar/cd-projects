import { Component, OnInit } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Router, NavigationEnd } from "@angular/router";

@Component({
  selector: 'app-warning-modal',
  templateUrl: './warning-modal.component.html',
  styleUrls: ['./warning-modal.component.less']
})

export class WarningModalComponent implements OnInit {
  parameter: number;
  title: any;
  content: any;
  action: any;
  heading: any;

  constructor(
    private bsModalRef: BsModalRef,
    private router: Router,
    private modalService: BsModalService
  ) { }

  ngOnInit() {
    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0)
    });
  }

  confirm() {
    this.close();
  }
  close() {
    this.bsModalRef.hide();
  }

}


import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-default-modal',
  templateUrl: './default-modal.component.html',
  styleUrls: ['./default-modal.component.less']
})
export class DefaultModalComponent implements OnInit {
  title: any;
  content: any;
  action: any;
  link: any;
  parameter: number;
  constructor(
    private bsModalRef: BsModalRef,
    private translateService: TranslateService
  ) { }

  ngOnInit() {
  }
  confirm() {
    this.close();
}
close() {
  this.bsModalRef.hide();
}

}


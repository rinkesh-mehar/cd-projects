import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-recording-modal',
  templateUrl: './recording-modal.component.html',
  styleUrls: ['./recording-modal.component.less']
})
export class RecordingModalComponent implements OnInit {

  parameter: number;
  recording: any;

  constructor(
    private bsModalRef: BsModalRef,
    private translateService: TranslateService) { }

  ngOnInit() {
  }
  close() {
    this.bsModalRef.hide();
  }

}

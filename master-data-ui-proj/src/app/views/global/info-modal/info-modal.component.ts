import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-info-modal',
  templateUrl: './info-modal.component.html',
  styleUrls: ['./info-modal.component.scss']
})
export class InfoModalComponent implements OnInit {

  @ViewChild('infoModal') public infoModal: ModalDirective;

  title: any;
  message: any;
  inputData: any;

  constructor() { }

  ngOnInit() {
  }

  showModal(title, message, inputData) {
    this.title = title;
    this.message = message;
    this.inputData = inputData;
    this.infoModal.show();
  }

  hide() {
    this.infoModal.hide();
  }

}

import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-success-modal',
  templateUrl: './success-modal.component.html',
  // styleUrls: ['./success-modal.component.scss']
})
export class SuccessModalComponent implements OnInit {
  @ViewChild('successModal') public successModal: ModalDirective;

  @Output('hideModal') hideModal = new EventEmitter<any>();
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
    this.successModal.show();
  }

  hide() {
    this.successModal.hide();
    this.hideModal.emit(this.inputData);
  }
}
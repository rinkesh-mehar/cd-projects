import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';

@Component({
    selector: 'app-error-modal',
    templateUrl: './error-modal.component.html',
    // styleUrls: ['./error-modal.component.scss']
})
export class ErrorModalComponent implements OnInit {
    @ViewChild('errorModal') public errorModal: ModalDirective;

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
        this.errorModal.show();
    }

    hide() {
        this.errorModal.hide();
        this.hideModal.emit(this.inputData);
    }
}

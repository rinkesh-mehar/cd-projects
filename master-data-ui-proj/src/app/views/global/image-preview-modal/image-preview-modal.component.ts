import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-image-preview-modal',
  templateUrl: './image-preview-modal.component.html',
  styleUrls: ['./image-preview-modal.component.scss']
})
export class ImagePreviewModalComponent implements OnInit {

  @ViewChild('imagePreviewModal') public infoModal: ModalDirective;

  title: any;
  message: any;
  inputData: any;
  imgSrc: any;

  constructor() { }

  ngOnInit() {
  }

  showModal(title, imgSrc, message?, inputData?) {

    console.log(title + " --- " + imgSrc)
    this.title = title;
    this.message = message;
    this.inputData = inputData;
    this.imgSrc = imgSrc;
    this.infoModal.show();
  }

  hide() {
    this.infoModal.hide();
  }

}

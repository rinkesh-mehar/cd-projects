// Angular
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ModalModule } from 'ngx-bootstrap/modal';

import { ConfirmationMadalComponent } from './confirmation-madal/confirmation-madal.component';
import { InfoModalComponent } from './info-modal/info-modal.component';
import { ImagePreviewModalComponent } from './image-preview-modal/image-preview-modal.component';
import {SuccessModalComponent} from './success-modal/success-modal.component';
import {ErrorModalComponent} from './error-modal/error-modal.component';


@NgModule({
  imports: [
    CommonModule,

    ModalModule.forRoot()
  ],
    declarations: [
        ConfirmationMadalComponent,
        InfoModalComponent,
        ImagePreviewModalComponent,
        SuccessModalComponent,
        ErrorModalComponent
    ]
    ,
    exports: [ConfirmationMadalComponent, InfoModalComponent, ImagePreviewModalComponent, SuccessModalComponent, ErrorModalComponent]
})
export class GlobalModule { }

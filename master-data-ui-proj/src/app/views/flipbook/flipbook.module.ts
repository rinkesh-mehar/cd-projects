import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ClsoComponent} from './clso/clso.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {FlipbookRoutingModule} from './flipbook-routing.module';
import {CarouselComponent} from './carousel/carousel.component';
import {CarouselModule} from 'ngx-bootstrap/carousel';
import {ModalModule} from 'ngx-bootstrap/modal';
import { AddBmImagesComponent } from './add-bm-images/add-bm-images.component';
import {GlobalModule} from '../global/global.module';


@NgModule({
    declarations: [ClsoComponent, CarouselComponent, AddBmImagesComponent],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        FlipbookRoutingModule,
        CarouselModule,
        ModalModule.forRoot(),
        GlobalModule
    ]
})
export class FlipbookModule {
}

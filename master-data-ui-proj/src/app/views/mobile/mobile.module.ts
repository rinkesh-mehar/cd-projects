import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {PopoverModule} from 'ngx-bootstrap/popover';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {GlobalModule} from '../global/global.module';
import {PipeModule} from '../pipes/pipe.module';
import { MobileComponent } from './mobile.component';
import { ApkVersionControlListComponent } from './apk-version-control-list/apk-version-control-list.component';
import { MobileRoutingModule } from './mobile-routing.module';
import { ApkVersionAddComponent } from './apk-version-add/apk-version-add.component';
import { ViewVersionDetailComponent } from './view-version-detail/view-version-detail.component';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MobileRoutingModule,
        ReactiveFormsModule,
        PopoverModule.forRoot(),
        PaginationModule.forRoot(),
        GlobalModule,
        PipeModule
  ],
  declarations: [
    MobileComponent,
    ApkVersionControlListComponent,
    ApkVersionAddComponent,
    ViewVersionDetailComponent

  ]
})
export class MobileModule { }

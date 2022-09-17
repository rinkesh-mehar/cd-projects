import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CropCalendarRoutingModule } from './cropcalender-routing.module';
import { CropCalendarComponent } from './crop-calendar/crop-calendar.component';




@NgModule({
  declarations: [ CropCalendarComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    CropCalendarRoutingModule
  ]
})
export class CropCalendarModule { }

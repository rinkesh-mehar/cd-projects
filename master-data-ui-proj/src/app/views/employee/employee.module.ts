import { MomentDateModule } from '@angular/material-moment-adapter';
import { A11yModule } from '@angular/cdk/a11y';
import { EmployeeRoutingModule } from './employee-routing-module';
import {  DateAdapter, MatDatepickerModule, MatNativeDateModule, MAT_DATE_FORMATS,  MAT_DATE_LOCALE, MatFormFieldModule, MatSortModule, MatSelectModule } from '@angular/material';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PipeModule } from '../pipes/pipe.module';
import { GlobalModule } from '../global/global.module';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LeaveDetailComponent } from './leave-detail/leave-detail.component';
import { MY_DATE_FORMATS } from '../my-date-formats';







@NgModule({
  imports: [
    CommonModule, 
    FormsModule,
    ReactiveFormsModule,
    PopoverModule.forRoot(),
    PaginationModule.forRoot(),
    GlobalModule,
    PipeModule,
    EmployeeRoutingModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MomentDateModule,
    A11yModule,
    MatSelectModule,
    MatSortModule,
    
    
    
    
    
    

    

  ],
  providers: [

    { provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS }
  ],
  declarations: [ 
    LeaveDetailComponent,
]
})
export class EmployeeModule { }

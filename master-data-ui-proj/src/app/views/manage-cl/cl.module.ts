import { EmployeeComponent } from './employee/employee.component';
import { MatCheckboxModule, MatSortModule } from '@angular/material';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PipeModule } from '../pipes/pipe.module';
import { GlobalModule } from '../global/global.module';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ClRoutingModule } from './cl-routing-module';
import { EmployeeListComponent } from './employee-list/employee-list.component';





@NgModule({
  imports: [
    CommonModule, 
    FormsModule,
    ReactiveFormsModule,
    PopoverModule.forRoot(),
    PaginationModule.forRoot(),
    GlobalModule,
    PipeModule,
    ClRoutingModule,
    MatSortModule,
    MatCheckboxModule,

    

  ],
  providers: [
  ],
  declarations: [
  EmployeeComponent,
  EmployeeListComponent
]
})
export class ClModule { }

import { MatCheckboxModule, MatSortModule } from '@angular/material';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PipeModule } from '../pipes/pipe.module';
import { GlobalModule } from '../global/global.module';
import { PaginationModule, PopoverModule } from 'ngx-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DataEntryRoutingModule } from './dataentry-routing-module';
import { ImdComponent } from './imd/imd.component';




@NgModule({
  imports: [
    CommonModule, 
    FormsModule,
    ReactiveFormsModule,
    PopoverModule.forRoot(),
    PaginationModule.forRoot(),
    GlobalModule,
    PipeModule,
    DataEntryRoutingModule,
    MatSortModule,
    MatCheckboxModule,

    

  ],
  providers: [
  ],
  declarations: [
    // SyncConfigComponent,
    // AddEditSyncConfigComponent
  ImdComponent]
})
export class DataEntryModule { }

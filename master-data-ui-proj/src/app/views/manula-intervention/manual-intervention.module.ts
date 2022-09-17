import { MatCheckboxModule, MatSortModule } from '@angular/material';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PipeModule } from '../pipes/pipe.module';
import { GlobalModule } from '../global/global.module';
import { PaginationModule, PopoverModule } from 'ngx-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RightsDataComponent } from './rights-data/rights-data.component';
import { ManualInterventionRoutingModule } from './manual-intervention-routing-module';

@NgModule({
  imports: [
    CommonModule, 
    FormsModule,
    ReactiveFormsModule,
    PopoverModule.forRoot(),
    PaginationModule.forRoot(),
    GlobalModule,
    PipeModule,
    ManualInterventionRoutingModule,
    MatSortModule,
    MatCheckboxModule,

  ],
  declarations: [RightsDataComponent]
})
export class ManualInterventionModule { }

import { MatSortModule } from '@angular/material';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FarmerLeadsComponent } from './farmer-leads/farmer-leads/farmer-leads.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GlobalModule } from '../global/global.module';
import { PipeModule } from '../pipes/pipe.module';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { YDCRoutingModule } from './farmer-leads-routing.module';
import { FarmerYieldComponent } from './farmer-yield/farmer-yield/farmer-yield.component';




@NgModule({
  declarations: [FarmerLeadsComponent, FarmerYieldComponent],
  imports: [
    CommonModule,
    FormsModule,
    YDCRoutingModule,
    ReactiveFormsModule,
    GlobalModule,
    PipeModule,
    PaginationModule.forRoot(),
    MatSortModule
  ]
})
export class YDCModule { }

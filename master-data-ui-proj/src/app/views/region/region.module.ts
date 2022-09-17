import { AddEditRegionTaskTypeSpecificationComponent } from './../regional/region-task-type-specification/add-edit-region-task-type-specification/add-edit-region-task-type-specification.component';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GlobalModule } from '../global/global.module';
import { PipeModule } from '../pipes/pipe.module';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import {MatCheckboxModule, MatSortModule} from '@angular/material';
import { RegionRoutingModule } from './region-routing.module';
import { RegionTaskTypeSpecificationComponent } from '../regional/region-task-type-specification/region-task-type-specification.component';
import { FlsVisitsComponent } from '../regional/fls-visits/fls-visits.component';


@NgModule({
  declarations: [
   
    RegionTaskTypeSpecificationComponent,
    AddEditRegionTaskTypeSpecificationComponent,
    FlsVisitsComponent
  ],
    imports: [
        CommonModule,
        RegionRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        GlobalModule,
        PipeModule,
        PaginationModule.forRoot(),
        MatSortModule,
        MatCheckboxModule,
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class RegionModule {}

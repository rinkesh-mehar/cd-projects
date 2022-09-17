import { MatCheckboxModule, MatSortModule } from '@angular/material';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfigRoutingModule } from './config-routing-module';
import { PipeModule } from '../pipes/pipe.module';
import { GlobalModule } from '../global/global.module';
import { PaginationModule, PopoverModule } from 'ngx-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SyncConfigComponent } from './sync-config/sync-config.component';
import { AddEditSyncConfigComponent } from './sync-config/add-edit-sync-config/add-edit-sync-config.component';


@NgModule({
  imports: [
    CommonModule, 
    FormsModule,
    ReactiveFormsModule,
    PopoverModule.forRoot(),
    PaginationModule.forRoot(),
    GlobalModule,
    PipeModule,
    ConfigRoutingModule,
    MatSortModule,
    MatCheckboxModule,

  ],
  declarations: [
    SyncConfigComponent,
    AddEditSyncConfigComponent
  ]
})
export class ConfigModule { }

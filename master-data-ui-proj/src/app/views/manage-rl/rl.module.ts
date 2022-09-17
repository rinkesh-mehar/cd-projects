import { UploadRlComponent } from './upload-rl/upload-rl.component';
import { ManageRlComponent } from './list-rl-manager/manage-rl.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GlobalModule } from '../global/global.module';
import { PipeModule } from '../pipes/pipe.module';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';


import {MatSortModule} from '@angular/material';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { RlRoutingModule } from './rl-routing.module';
import { ViewRlComponent } from './view-rl/view-rl.component';
import { ExportRlComponent } from './export-rl/export-rl.component';
// NOT RECOMMENDED (Angular 9 doesn't support this kind of import)
@NgModule({
  declarations: [
    ManageRlComponent,
    UploadRlComponent,
    ViewRlComponent,
    ExportRlComponent
  ],
    imports: [
        CommonModule,
        RlRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        GlobalModule,
        PipeModule,
        PaginationModule.forRoot(),
        MatSortModule, PopoverModule.forRoot(),
    ]
})
export class RlModule {}

import { MatSortModule } from '@angular/material';
import {NgModule} from '@angular/core';

import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {PopoverModule} from 'ngx-bootstrap/popover';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {GlobalModule} from '../global/global.module';
import {PipeModule} from '../pipes/pipe.module';
import {ManageComponent} from './manage/manage.component';
import {ModulesRoutingModule} from './cdt-modules-rounting';
import {AddManageComponent} from './add-Manage/add-manage.component';
import {ListModelsComponent} from './list-models/list-models.component';
import {AddNewModelsComponent} from './add-models/add-new-models.component';
import {PanchayatMapListComponent} from './panchayat-map/panchayat-map-list/panchayat-map-list.component';
import {AddEditPanchayatMapComponent} from './panchayat-map/add-edit-panchayat-map/add-edit-panchayat-map.component';




@NgModule({
    declarations: [
       ManageComponent, AddManageComponent, ListModelsComponent, AddNewModelsComponent, PanchayatMapListComponent,
        AddEditPanchayatMapComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        PopoverModule.forRoot(),
        PaginationModule.forRoot(),
        GlobalModule,
        PipeModule,
        ModulesRoutingModule,
        MatSortModule

    ]
})
export class ModulesModule { }

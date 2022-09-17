import { MatSortModule } from '@angular/material';
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {AddEditParametersComponent} from './parameters/add-edit-parameters/add-edit-parameters.component';
import {ParametersComponent} from './parameters/parameters.component';
import {GstmEyeRoutingModule} from './gstm-eye-routing.module';
import {PipeModule} from '../pipes/pipe.module';
import {GlobalModule} from '../global/global.module';


@NgModule({

    declarations: [
        ParametersComponent,
        AddEditParametersComponent
    ],
    imports: [
        CommonModule,
        GstmEyeRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        PaginationModule.forRoot(),
        PipeModule,
        GlobalModule,
        MatSortModule,
    ]
})
export class GstmEyeModule {
}

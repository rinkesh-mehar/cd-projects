import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GlobalModule} from '../global/global.module';
import {PipeModule} from '../pipes/pipe.module';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {ReactiveFormsModule, FormsModule} from '@angular/forms';
import {AgrochemicalsRoutingModule} from './agrochemicals-routing.module';
import {AgriCommodityAgrochemicalComponent} from '../agri/agri-commodity-agrochemical/agri-commodity-agrochemical.component';
import {AddAgriCommodityAgrochemicalComponent} from '../agri/add-agri-agrochemical-master/add-agri-commodity-agrochemical.component';
import {EditAgriCommodityAgrochemicalComponent} from '../agri/edit-agri-agrochemical-master/edit-agri-commodity-agrochemical.component';
import {AgrochemicalTypeComponent} from '../agri/agrochemical-type/agrochemical-type/agrochemical-type.component';
import {AddAgrochemicalTypeComponent} from '../agri/agrochemical-type/add-agrochemical-type/add-agrochemical-type.component';
import {EditAgrochemicalTypeComponent} from '../agri/agrochemical-type/edit-agrochemical-type/edit-agrochemical-type.component';
import {AgriRemedialMeasuresComponent} from '../agri/agri-remedial-measures/agri-remedial-measures.component';
import {AddAgriRemedialMeasuresComponent} from '../agri/add-agri-remedial-measures/add-agri-remedial-measures.component';
import {EditAgriRemedialMeasuresComponent} from '../agri/edit-agri-remedial-measures/edit-agri-remedial-measures.component';
import {MatSortModule} from '@angular/material';
import {AgriAgrochemicalMasterComponent} from '../agri/agri-agrochemical-master/agri-agrochemical-master.component';
import {AddEditAgriAgrochemicalComponent} from '../agri/add-edit-agri-agrochemical-master/add-edit-agri-agrochemical.component';


@NgModule({
    declarations: [
        AgriCommodityAgrochemicalComponent,
        AddAgriCommodityAgrochemicalComponent,
        EditAgriCommodityAgrochemicalComponent,
        AgriAgrochemicalMasterComponent,
        AddEditAgriAgrochemicalComponent,
        AgrochemicalTypeComponent,
        AddAgrochemicalTypeComponent,
        EditAgrochemicalTypeComponent,
        AgriRemedialMeasuresComponent,
        AddAgriRemedialMeasuresComponent,
        EditAgriRemedialMeasuresComponent
    ],
    imports: [
        CommonModule,
        AgrochemicalsRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        GlobalModule,
        PipeModule,
        PaginationModule.forRoot(),
        MatSortModule,
    ]
})
export class AgrochemicalsModule {
}

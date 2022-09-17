import { AddEditAgriStateCommodityComponent } from './agri-state-commodity/add-edit-agri-state-commodity/add-edit-agri-state-commodity.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GlobalModule } from '../global/global.module';
import { PipeModule } from '../pipes/pipe.module';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { AgriCommodityComponent } from '../agri/agri-commodity/agri-commodity.component';
import { AddAgriCommodityComponent } from '../agri/add-agri-commodity/add-agri-commodity.component';
import { EditAgriCommodityComponent } from '../agri/edit-agri-commodity/edit-agri-commodity.component';
import { CommodityRoutingModule } from './commodity-routing.module';
import { AgriPlantPartComponent } from '../agri/agri-plant-part/agri-plant-part.component';
import { AddAgriPlantPartComponent } from '../agri/add-agri-plant-part/add-agri-plant-part.component';
import { EditAgriPlantPartComponent } from '../agri/edit-agri-plant-part/edit-agri-plant-part.component';
import { AgriCommodityPlantPartComponent } from '../agri/agri-commodity-plant-part/agri-commodity-plant-part.component';
import { AddAgriCommodityPlantPartComponent } from '../agri/add-agri-commodity-plant-part/add-agri-commodity-plant-part.component';
import { EditAgriCommodityPlantPartComponent } from '../agri/edit-agri-commodity-plant-part/edit-agri-commodity-plant-part.component';
import { AgriVarietyComponent } from '../agri/agri-variety/agri-variety.component';
import { AddAgriVarietyComponent } from '../agri/add-agri-variety/add-agri-variety.component';
import { EditAgriVarietyComponent } from '../agri/edit-agri-variety/edit-agri-variety.component';
import { AgriHsCodeComponent } from '../agri/agri-hs-code/agri-hs-code.component';
import { AddAgriHsCodeComponent } from '../agri/add-agri-hs-code/add-agri-hs-code.component';
import { EditAgriHsCodeComponent } from '../agri/edit-agri-hs-code/edit-agri-hs-code.component';
import { AgriGeneralCommodityComponent } from '../agri/agri-general-commodity/agri-general-commodity.component';
import { AddAgriGeneralCommodityComponent } from '../agri/add-agri-general-commodity/add-agri-general-commodity.component';
import { EditAgriGeneralCommodityComponent } from '../agri/edit-agri-general-commodity/edit-agri-general-commodity.component';
import { AgriCommodityClassComponent } from '../agri/agri-commodity-class/agri-commodity-class.component';
import { AddAgriCommodityClassComponent } from '../agri/add-agri-commodity-class/add-agri-commodity-class.component';
import { EditAgriCommodityClassComponent } from '../agri/edit-agri-commodity-class/edit-agri-commodity-class.component';
import {MatSortModule} from '@angular/material';
import {AddAgriCommodityAliasComponent} from '../agri/add-agri-commodity-alias/add-agri-commodity-alias.component';
import {AddEditBandComponent} from './band/add-edit-band/add-edit-band.component';
import {BandComponent} from './band/band/band.component';
import { AgriStateCommodityComponent } from './agri-state-commodity/agri-state-commodity.component';
import { AgriCommodityGroupComponent } from './agri-commodity-group/agri-commodity-group.component';
import { AddEditAgriCommodityGroupComponent } from './agri-commodity-group/add-edit-agri-commodity-group/add-edit-agri-commodity-group.component';
import { AgriQualityParameterComponent } from './agri-quality-parameter/agri-quality-parameter.component';
import { AddEditAgriQualityParameterComponent } from './agri-quality-parameter/add-edit-agri-quality-parameter/add-edit-agri-quality-parameter.component';
import { AgriQualityCommodityParameterComponent } from './agri-quality-commodity-parameter/agri-quality-commodity-parameter.component';
import { AddEditAgriQualityCommodityParameterComponent } from './agri-quality-commodity-parameter/add-edit-agri-quality-commodity-parameter/add-edit-agri-quality-commodity-parameter.component';

@NgModule({
  declarations: [
    AgriCommodityComponent,
    AddAgriCommodityComponent,
    EditAgriCommodityComponent,
    AgriPlantPartComponent,
    AddAgriPlantPartComponent,
    EditAgriPlantPartComponent,
    AgriCommodityPlantPartComponent,
    AddAgriCommodityPlantPartComponent,
    EditAgriCommodityPlantPartComponent,
    AgriVarietyComponent,
    AddAgriVarietyComponent,
    EditAgriVarietyComponent,
    AgriHsCodeComponent,
    AddAgriHsCodeComponent,
    EditAgriHsCodeComponent,
    AgriGeneralCommodityComponent,
    AddAgriGeneralCommodityComponent,
    EditAgriGeneralCommodityComponent,
    AgriCommodityClassComponent,
    AddAgriCommodityClassComponent,
    EditAgriCommodityClassComponent,
    AddAgriCommodityAliasComponent,
    AddEditBandComponent,
    BandComponent,
    AgriStateCommodityComponent,
    AddEditAgriStateCommodityComponent,
    AgriCommodityGroupComponent,
    AddEditAgriCommodityGroupComponent,
    AgriQualityParameterComponent,
    AddEditAgriQualityParameterComponent,
    AgriQualityCommodityParameterComponent,
    AddEditAgriQualityCommodityParameterComponent
  ],
    imports: [
        CommonModule,
        CommodityRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        GlobalModule,
        PipeModule,
        PaginationModule.forRoot(),
        MatSortModule,
    ]
})
export class CommodityModule {}

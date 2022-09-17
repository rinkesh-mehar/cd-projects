import { AgriStageComponent } from './agri-stage/agri-stage.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GlobalModule } from '../global/global.module';
import { PipeModule } from '../pipes/pipe.module';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { StressRoutingModule } from './stress-routing.module';
import { AgriStressTypeComponent } from '../agri/agri-stress-type/agri-stress-type.component';
import { AddAgriStressTypeComponent } from '../agri/add-agri-stress-type/add-agri-stress-type.component';
import { EditAgriStressTypeComponent } from '../agri/edit-agri-stress-type/edit-agri-stress-type.component';
import { AgriVarietyStressComponent } from '../agri/agri-variety-stress/agri-variety-stress.component';
import { AddAgriVarietyStressComponent } from '../agri/add-agri-variety-stress/add-agri-variety-stress.component';
import { EditAgriVarietyStressComponent } from '../agri/edit-agri-variety-stress/edit-agri-variety-stress.component';
import { AgriControlMeasuresComponent } from '../agri/agri-control-measures/agri-control-measures/agri-control-measures.component';
import { AddEditAgriControlMeasuresComponent } from '../agri/agri-control-measures/add-edit-agri-control-measures/add-edit-agri-control-measures.component';
import { AgriStressControlMeasuresComponent } from '../agri/agri-stress-control-measures/agri-stress-control-measures/agri-stress-control-measures.component';
import { AddEditAgriStressControlMeasuresComponent } from '../agri/agri-stress-control-measures/add-edit-agri-stress-control-measures/add-edit-agri-stress-control-measures.component';
import { AgrochemicalApplicationComponent } from '../agri/agro-chemical-application-type/agrochemical-application/agrochemical-application.component';
import { AddAgrochemicalApplicationComponent } from '../agri/agro-chemical-application-type/add-agrochemical-application/add-agrochemical-application.component';
import { EditAgrochemicalApplicationComponent } from '../agri/agro-chemical-application-type/edit-agrochemical-application/edit-agrochemical-application.component';
import { AgriSeedTreatmentComponent } from '../agri/agri-seed-treatment/agri-seed-treatment.component';
import { AddAgriSeedTreatmentComponent } from '../agri/add-agri-seed-treatment/add-agri-seed-treatment.component';
import { EditAgriSeedTreatmentComponent } from '../agri/edit-agri-seed-treatment/edit-agri-seed-treatment.component';
import { AgriCommodityStressStageComponent } from '../agri/agri-commodity-stress-stage/agri-commodity-stress-stage.component';
import { AddAgriCommodityStressStageComponent } from '../agri/add-agri-commodity-stress-stage/add-agri-commodity-stress-stage.component';
import { EditAgriCommodityStressStageComponent } from '../agri/edit-agri-commodity-stress-stage/edit-agri-commodity-stress-stage.component';
import { StressSeverityComponent } from '../agri/stress-severity/stress-severity/stress-severity.component';
import { AddStressSeverityComponent } from '../agri/stress-severity/add-stress-severity/add-stress-severity.component';
import { EditStressSeverityComponent } from '../agri/stress-severity/edit-stress-severity/edit-stress-severity.component';
import { AgriDoseFactorComponent } from '../agri/agri-dose-factor/agri-dose-factor/agri-dose-factor.component';
import { AddEditAgriDoseFactorComponent } from '../agri/agri-dose-factor/add-edit-agri-dose-factor/add-edit-agri-dose-factor.component';
import {MatSortModule} from '@angular/material';
import { PopoverModule } from 'ngx-bootstrap/popover';
import {AgriStressComponent} from '../agri/agri-stress/agri-stress.component';
import {AddEditAgriStressComponent} from '../agri/agri-stress/add-edit-agri-stress/add-edit-agri-stress.component';
import { AgriRecommendationComponent } from './agri-recommendation/agri-recommendation.component';
import { AddEditAgriRecommendationComponent } from './agri-recommendation/add-edit-agri-recommendation/add-edit-agri-recommendation.component';
import { AgriStressStageComponent } from './agri-stress-stage/agri-stress-stage.component';
import { AddEditAgriStressStageComponent } from './agri-stress-stage/add-edit-agri-stress-stage/add-edit-agri-stress-stage.component';
import { AgriAgrochemicalInstructionsComponent } from './agri-agrochemical-instructions/agri-agrochemical-instructions.component';
import { AddEditAgriAgrochemicalInnstructionsComponent } from './agri-agrochemical-instructions/add-edit-agri-agrochemical-innstructions/add-edit-agri-agrochemical-innstructions.component';
import { AgriCommodityStressComponent } from './agri-commodity-stress/agri-commodity-stress.component';
import { AddEditAgriCommodityStressComponent } from './agri-commodity-stress/add-edit-agri-commodity-stress/add-edit-agri-commodity-stress.component';
import { AddEditAgriStageComponent } from './agri-stage/add-edit-agri-stage/add-edit-agri-stage.component';
// NOT RECOMMENDED (Angular 9 doesn't support this kind of import)
@NgModule({
  declarations: [
    AgriStressComponent,
    AddEditAgriStressComponent,  
    AgriStressTypeComponent,
    AddAgriStressTypeComponent,
    EditAgriStressTypeComponent,
    AgriVarietyStressComponent,
    AddAgriVarietyStressComponent,
    EditAgriVarietyStressComponent,
    AgriControlMeasuresComponent,
    AddEditAgriControlMeasuresComponent,
    AgriStressControlMeasuresComponent,
    AddEditAgriStressControlMeasuresComponent,
    AgrochemicalApplicationComponent,
    AddAgrochemicalApplicationComponent,
    EditAgrochemicalApplicationComponent,
    AgriSeedTreatmentComponent,
    AddAgriSeedTreatmentComponent,
    EditAgriSeedTreatmentComponent,
    AgriCommodityStressStageComponent,
    AddAgriCommodityStressStageComponent,
    EditAgriCommodityStressStageComponent,
    StressSeverityComponent,
    AddStressSeverityComponent,
    EditStressSeverityComponent,
       AgriDoseFactorComponent,
   AddEditAgriDoseFactorComponent,
   AgriRecommendationComponent,
   AddEditAgriRecommendationComponent,
   AgriStressStageComponent,
   AddEditAgriStressStageComponent,
   AgriAgrochemicalInstructionsComponent,
   AddEditAgriAgrochemicalInnstructionsComponent,
   AgriCommodityStressComponent,
   AddEditAgriCommodityStressComponent,
   AgriStageComponent,
   AddEditAgriStageComponent
    
  ],
    imports: [
        CommonModule,
        StressRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        GlobalModule,
        PipeModule,
        PaginationModule.forRoot(),
        MatSortModule,PopoverModule.forRoot(),
    ]
})
export class StressModule {}

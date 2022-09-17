import { MatSortModule } from '@angular/material';
import { NgModule, Pipe } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { PaginationModule } from 'ngx-bootstrap/pagination';

import { AgriRoutingModule } from './agri-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GlobalModule } from '../global/global.module';
import { PipeModule } from '../pipes/pipe.module';
import { AgriSeasonComponent } from './agri-season/agri-season.component';
import { AddAgriSeasonComponent } from './add-agri-season/add-agri-season.component';
import { EditAgriSeasonComponent } from './edit-agri-season/edit-agri-season.component';
import { AgriActivityComponent } from './agri-activity/agri-activity.component';
import { AddAgriActivityComponent } from './add-agri-activity/add-agri-activity.component';
import { EditAgriActivityComponent } from './edit-agri-activity/edit-agri-activity.component';
import { AgriDisposalMethodComponent } from './agri-disposal-method/agri-disposal-method.component';
import { AddAgriDisposalMethodComponent } from './add-agri-disposal-method/add-agri-disposal-method.component';
import { EditAgriDisposalMethodComponent } from './edit-agri-disposal-method/edit-agri-disposal-method.component';
import { AgriFarmMachineryComponent } from './agri-farm-machinery/agri-farm-machinery.component';
import { AddAgriFarmMachineryComponent } from './add-agri-farm-machinery/add-agri-farm-machinery.component';
import { EditAgriFarmMachineryComponent } from './edit-agri-farm-machinery/edit-agri-farm-machinery.component';
// import { AgriFieldActivityComponent } from './agri-field-activity/agri-field-activity.component';
// import { AddAgriFieldActivityComponent } from './add-agri-field-activity/add-agri-field-activity.component';
// import { EditAgriFieldActivityComponent } from './edit-agri-field-activity/edit-agri-field-activity.component';
import { AgriIrrigationMethodComponent } from './agri-irrigation-method/agri-irrigation-method.component';
import { AddAgriIrrigationMethodComponent } from './add-agri-irrigation-method/add-agri-irrigation-method.component';
import { EditAgriIrrigationMethodComponent } from './edit-agri-irrigation-method/edit-agri-irrigation-method.component';
import { AgriSeedSourceComponent } from './agri-seed-source/agri-seed-source.component';
import { AddAgriSeedSourceComponent } from './add-agri-seed-source/add-agri-seed-source.component';
import { EditAgriSeedSourceComponent } from './edit-agri-seed-source/edit-agri-seed-source.component';
import { AgriSourceOfWaterComponent } from './agri-source-of-water/agri-source-of-water.component';
import { AddAgriSourceOfWaterComponent } from './add-agri-source-of-water/add-agri-source-of-water.component';
import { EditAgriSourceOfWaterComponent } from './edit-agri-source-of-water/edit-agri-source-of-water.component';
import { EcosystemListComponent } from './ecosystem/list/list.component';
import { AddEcosystemComponent } from './ecosystem/add/add.component';
import { EditEcosystemComponent } from './ecosystem/edit/edit.component';
import { AgriMeteorologicalWeekComponent } from './agri-meteorological-week/agri-meteorological-week/agri-meteorological-week.component';
import { AddEditAgriMeteorologicalWeekComponent } from './agri-meteorological-week/add-edit-agri-meteorological-week/add-edit-agri-meteorological-week.component';
import { AgriBenchmarkVarietyComponent } from './agri-benchmark-variety/agri-benchmark-variety/agri-benchmark-variety.component';
import { AddEditAgriBenchmarkVarietyComponent } from './agri-benchmark-variety/add-edit-agri-benchmark-variety/add-edit-agri-benchmark-variety.component';
// import { AddEditAgriQualityChartComponent } from './agri-quality-chart/add-edit-agri-quality-chart/add-edit-agri-quality-chart.component';
// import { AgriQualityChartComponent } from './agri-quality-chart/agri-quality-chart/agri-quality-chart.component';
// import { AgriYieldCorrectionCriteriaComponent } from './agri-yield-correction-criteria/agri-yield-correction-criteria/agri-yield-correction-criteria.component';
// import { AddEditAgriYieldCorrectionCriteriaComponent } from './agri-yield-correction-criteria/add-edit-agri-yield-correction-criteria/add-edit-agri-yield-correction-criteria.component';
// import { AgriPlantHealthIndexComponent } from './agri-plant-health-index/agri-plant-health-index/agri-plant-health-index.component';
// import { AddEditAgriPlantHealthIndexComponent } from './agri-plant-health-index/add-edit-agri-plant-health-index/add-edit-agri-plant-health-index.component';
// import { AgriVarietyQualityComponent } from './agri-variety-quality/agri-variety-quality/agri-variety-quality.component';
// import { AddEditAgriVarietyQualityComponent } from './agri-variety-quality/add-edit-agri-variety-quality/add-edit-agri-variety-quality.component';
// import { AgriDoseFactorComponent } from './agri-dose-factor/agri-dose-factor/agri-dose-factor.component';
// import { AddEditAgriDoseFactorComponent } from './agri-dose-factor/add-edit-agri-dose-factor/add-edit-agri-dose-factor.component';


@NgModule({
  declarations: [
    AgriSeasonComponent,
    AddAgriSeasonComponent,
    EditAgriSeasonComponent,
    AgriActivityComponent,
    AddAgriActivityComponent,
    EditAgriActivityComponent,
    AgriDisposalMethodComponent,
    AddAgriDisposalMethodComponent,
    EditAgriDisposalMethodComponent,
    AgriFarmMachineryComponent,
    AddAgriFarmMachineryComponent,
    EditAgriFarmMachineryComponent,
    // AgriFieldActivityComponent,
    // AddAgriFieldActivityComponent,
    // EditAgriFieldActivityComponent,
    AgriIrrigationMethodComponent,
    AddAgriIrrigationMethodComponent,
    EditAgriIrrigationMethodComponent,
    AgriSeedSourceComponent,
    AddAgriSeedSourceComponent,
    EditAgriSeedSourceComponent,
    AgriSourceOfWaterComponent,
    AddAgriSourceOfWaterComponent,
    EditAgriSourceOfWaterComponent,
   EcosystemListComponent,
   AddEcosystemComponent,
   EditEcosystemComponent,
   AgriMeteorologicalWeekComponent,
   AddEditAgriMeteorologicalWeekComponent,
   AgriBenchmarkVarietyComponent,
   AddEditAgriBenchmarkVarietyComponent,
  //  AddEditAgriQualityChartComponent,
  //  AgriQualityChartComponent,
  //  AgriYieldCorrectionCriteriaComponent,
  //  AddEditAgriYieldCorrectionCriteriaComponent,
  //  AgriPlantHealthIndexComponent,
  //  AddEditAgriPlantHealthIndexComponent,
  //  AgriVarietyQualityComponent,
  //  AddEditAgriVarietyQualityComponent,
  //  AgriDoseFactorComponent,
  //  AddEditAgriDoseFactorComponent
  ],
  imports: [
    CommonModule,
    AgriRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    PopoverModule.forRoot(),
    PaginationModule.forRoot(),
    GlobalModule,
    PipeModule,
    MatSortModule
  ]
})
export class AgriModule { }

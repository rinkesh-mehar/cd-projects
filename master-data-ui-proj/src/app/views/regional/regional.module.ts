import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegionalRoutingModule } from './regional-routing.module';
import { RegionSeasonComponent } from './region-season/region-season.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AddRegionSeasonComponent } from './add-region-season/add-region-season.component';
import { EditRegionSeasonComponent } from './edit-region-season/edit-region-season.component';
import { RegionStressComponent } from './region-stress/region-stress.component';
import { AddRegionStressComponent } from './add-region-stress/add-region-stress.component';
import { EditRegionStressComponent } from './edit-region-stress/edit-region-stress.component';
import { RegionSeasonCommodityComponent } from './region-season-commodity/region-season-commodity.component';
import { AddEditRegionSeasonCommodityComponent } from './region-season-commodity/add-edit-region-season-commodity/add-edit-region-season-commodity.component';
import { AddEditRegionLanguageComponent } from './region-language/add-edit-region-language/add-edit-region-language.component';
import { RegionLanguageComponent } from './region-language/region-language.component';
import { GlobalModule } from '../global/global.module';
import { PipeModule } from '../pipes/pipe.module';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { RegionBankComponent } from './region-bank/region-bank/region-bank.component';
import { AddEditRegionBankComponent } from './region-bank/add-edit-region-bank/add-edit-region-bank.component';
import {MonthWbTravelTimeComponent} from './regional-month-wb-travel-time/month-wb-travel-time/month-wb-travel-time.component';
import {AddEditMonthWbTravelTimeComponent} from './regional-month-wb-travel-time/add-edit-month-wb-travel-time/add-edit-month-wb-travel-time.component';
import { TerrainTypeComponent } from './terrain-type/terrain-type.component';
import {AddEditTerrainComponent} from './terrain-type/add-edit-terrain/add-edit-terrain.component';
import { WeatherBasedTravelTimeListComponent } from './weather-based-travel-time/weather-based-travel-time-list/weather-based-travel-time-list.component';
import { AddWeatherBasedtravelTimeComponent } from './add-weather-based-travel-time/add-weather-based-travel-time.component';
import { RegionTaskTypeSpecificationComponent } from './region-task-type-specification/region-task-type-specification.component';
import { AddEditRegionTaskTypeSpecificationComponent } from './region-task-type-specification/add-edit-region-task-type-specification/add-edit-region-task-type-specification.component';

import {MatCheckboxModule, MatSortModule} from '@angular/material';
import { RegionalConnectivityComponent } from './regional-connectivity/regional-connectivity.component';
import { AddRegionalConnectivity } from './regional-connectivity/add-regional-connectivity/add-regional-connectivity.component';
import { EditRegionalConnectComponent } from './edit-regional-connectivity/edit-regional-connect.component';


@NgModule({
  declarations: [
    RegionSeasonComponent, 
    AddRegionSeasonComponent, 
    EditRegionSeasonComponent,
    RegionStressComponent, 
    AddRegionStressComponent, 
    AddRegionStressComponent, 
    EditRegionStressComponent, 
    RegionSeasonCommodityComponent, 
    AddEditRegionSeasonCommodityComponent, 
    RegionLanguageComponent,
    AddEditRegionLanguageComponent,
    RegionBankComponent,
    AddEditRegionBankComponent,
    WeatherBasedTravelTimeListComponent,
    AddWeatherBasedtravelTimeComponent,
    AddEditRegionBankComponent,
    TerrainTypeComponent,
    AddEditTerrainComponent,
    MonthWbTravelTimeComponent,
    AddEditMonthWbTravelTimeComponent,
    RegionTaskTypeSpecificationComponent,
    AddEditRegionTaskTypeSpecificationComponent,
    RegionalConnectivityComponent,
    AddRegionalConnectivity,
    EditRegionalConnectComponent
  ],
    imports: [
        CommonModule,
        RegionalRoutingModule,
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
export class RegionalModule {}

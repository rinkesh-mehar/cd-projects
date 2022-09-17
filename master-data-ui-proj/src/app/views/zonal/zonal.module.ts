import { OtherVarietyComponent } from './other-variety/other-variety.component';
import { AddEditZonalStandardQuantityChartComponent } from './../agri/zonal-standard-quantity-chart/add-edit-zonal-standard-quantity-chart/add-edit-zonal-standard-quantity-chart.component';
import { ZonalStandardQuantityChartComponent } from './../agri/zonal-standard-quantity-chart/zonal-standard-quantity-chart/zonal-standard-quantity-chart.component';
import { EditZonalVarietyComponent } from '../regional/edit-zonal-variety/edit-zonal-variety.component';
import { AddZonalVarietyComponent } from '../regional/add-zonal-variety/add-zonal-variety.component';
import { ZonalVarietyComponent } from '../regional/zonal-variety/zonal-variety.component';
import { MatCheckboxModule, MatSortModule } from '@angular/material';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ZonalRoutingModule } from './zonal-routing-module';
import { PipeModule } from '../pipes/pipe.module';
import { GlobalModule } from '../global/global.module';
import { PaginationModule, PopoverModule } from 'ngx-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ZonalPhenophaseDurationComponent } from '../agri/zonal-phenophase-duration/zonal-phenophase-duration.component';
import { AddZonalPhenophaseDurationComponent } from '../agri/add-zonal-phenophase-duration/add-zonal-phenophase-duration.component';
import { EditZonalPhenophaseDurationComponent } from '../agri/edit-zonal-phenophase-duration/edit-zonal-phenophase-duration.component';
import { ZonalStressDurationComponent } from '../agri/zonal-stress-duration/zonal-stress-duration.component';
import { AddEditZonalStressDurationComponent } from '../agri/zonal-stress-duration/add-edit-zonal-stress-duration/add-edit-zonal-stress-duration.component';
import { ZonalFertilizerComponent } from '../agri/zonal-fertilizer/zonal-fertilizer.component';
import { AddZonalFertilizerComponent } from '../agri/add-zonal-fertilizer/add-zonal-fertilizer.component';
import { EditZonalFertilizerComponent } from '../agri/edit-zonal-fertilizer/edit-zonal-fertilizer.component';
import { ZonalFieldActivityComponent } from '../agri/zonal-field-activity/zonal-field-activity.component';
import { AddZonalFieldActivityComponent } from '../agri/add-zonal-field-activity/add-zonal-field-activity.component';
import { EditZonalFieldActivityComponent } from '../agri/edit-zonal-field-activity/edit-zonal-field-activity.component';
import { ZonalPlantHealthIndexComponent } from '../agri/zonal-plant-health-index/zonal-plant-health-index/zonal-plant-health-index.component';
import { AddEditZonalPlantHealthIndexComponent } from '../agri/zonal-plant-health-index/add-edit-zonal-plant-health-index/add-edit-zonal-plant-health-index.component';
import { ZonalStressControlRecommendationComponent } from '../agri/zonal-stress-control-recommendation/zonal-stress-control-recommendation/zonal-stress-control-recommendation.component';
import { AddEditZonalStressControlRecommendationComponent } from '../agri/zonal-stress-control-recommendation/add-edit-zonal-stress-control-recommendation/add-edit-zonal-stress-control-recommendation.component';
import { AddEditZonalCommodityCultivationCostComponent } from './zonal-commodity-cultivation-cost/add-edit-zonal-commodity-cultivation-cost/add-edit-zonal-commodity-cultivation-cost.component';
import { ZonalCommodityCultivationCostComponent } from './zonal-commodity-cultivation-cost/zonal-commodity-cultivation-cost.component';
import { ZonalCommodityComponent } from './zonal-commodity/zonal-commodity.component';
import { AddEditZonalCommodityComponent } from './zonal-commodity/add-edit-zonal-commodity/add-edit-zonal-commodity.component';
import { ZonalVarietyQualityComponent } from '../agri/zonal-variety-quality/zonal-variety-quality/zonal-variety-quality.component';
import { AddEditZonalVarietyQualityComponent } from '../agri/zonal-variety-quality/add-edit-zonal-variety-quality/add-edit-zonal-variety-quality.component';
import { ZonalFavourableWeatherComponent } from '../agri/zonal-favourable-weather/zonal-favourable-weather/zonal-favourable-weather.component';
import { AddEditZonalFavourableWeatherComponent } from '../agri/zonal-favourable-weather/add-edit-zonal-favourable-weather/add-edit-zonal-favourable-weather.component';
import { CondusiveWeatherComponent } from '../agri/condusive-weather/condusive-weather.component';
import { AddEditCondusiveWeatherComponent } from '../agri/condusive-weather/add-edit-condusive-weather/add-edit-condusive-weather.component';


@NgModule({
  imports: [
    CommonModule, 
    FormsModule,
    ReactiveFormsModule,
    PopoverModule.forRoot(),
    PaginationModule.forRoot(),
    GlobalModule,
    PipeModule,
    ZonalRoutingModule,
    MatSortModule,
    MatCheckboxModule,

  ],
  declarations: [
    ZonalStandardQuantityChartComponent,
    AddEditZonalStandardQuantityChartComponent,
    ZonalPhenophaseDurationComponent,
    AddZonalPhenophaseDurationComponent,
    EditZonalPhenophaseDurationComponent,
    ZonalVarietyComponent, 
    AddZonalVarietyComponent, 
    EditZonalVarietyComponent,
    ZonalStressDurationComponent,
    AddEditZonalStressDurationComponent,
    ZonalFertilizerComponent,
    AddZonalFertilizerComponent,
    EditZonalFertilizerComponent,
    ZonalFieldActivityComponent,
    AddZonalFieldActivityComponent,
    EditZonalFieldActivityComponent,
    ZonalPlantHealthIndexComponent,
    AddEditZonalPlantHealthIndexComponent,
    ZonalStressControlRecommendationComponent,
    AddEditZonalStressControlRecommendationComponent,
    ZonalCommodityCultivationCostComponent,
    AddEditZonalCommodityCultivationCostComponent,
    ZonalCommodityComponent,
    AddEditZonalCommodityComponent,
    ZonalVarietyQualityComponent,
    AddEditZonalVarietyQualityComponent,
    ZonalFavourableWeatherComponent,
    AddEditZonalFavourableWeatherComponent,
    CondusiveWeatherComponent,
    AddEditCondusiveWeatherComponent,
    OtherVarietyComponent,]
})
export class ZonalModule { }

import { OtherVarietyComponent } from './other-variety/other-variety.component';
import { AddZonalVarietyComponent } from './../regional/add-zonal-variety/add-zonal-variety.component';
import { AddEditZonalStandardQuantityChartComponent } from './../agri/zonal-standard-quantity-chart/add-edit-zonal-standard-quantity-chart/add-edit-zonal-standard-quantity-chart.component';
import { ZonalStandardQuantityChartComponent } from './../agri/zonal-standard-quantity-chart/zonal-standard-quantity-chart/zonal-standard-quantity-chart.component';
import { EditZonalVarietyComponent } from '../regional/edit-zonal-variety/edit-zonal-variety.component';
import { ZonalVarietyComponent } from '../regional/zonal-variety/zonal-variety.component';
import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import { ZonalPhenophaseDurationComponent } from '../agri/zonal-phenophase-duration/zonal-phenophase-duration.component';
import { AddZonalPhenophaseDurationComponent } from '../agri/add-zonal-phenophase-duration/add-zonal-phenophase-duration.component';
import { EditZonalPhenophaseDurationComponent } from '../agri/edit-zonal-phenophase-duration/edit-zonal-phenophase-duration.component';
import { ZonalFertilizerComponent } from '../agri/zonal-fertilizer/zonal-fertilizer.component';
import { AddZonalFertilizerComponent } from '../agri/add-zonal-fertilizer/add-zonal-fertilizer.component';
import { EditZonalFertilizerComponent } from '../agri/edit-zonal-fertilizer/edit-zonal-fertilizer.component';
import { ZonalFieldActivityComponent } from '../agri/zonal-field-activity/zonal-field-activity.component';
import { AddZonalFieldActivityComponent } from '../agri/add-zonal-field-activity/add-zonal-field-activity.component';
import { EditZonalFieldActivityComponent } from '../agri/edit-zonal-field-activity/edit-zonal-field-activity.component';
import { AddEditZonalStressDurationComponent } from '../agri/zonal-stress-duration/add-edit-zonal-stress-duration/add-edit-zonal-stress-duration.component';
import { ZonalStressDurationComponent } from '../agri/zonal-stress-duration/zonal-stress-duration.component';
import { ZonalPlantHealthIndexComponent } from '../agri/zonal-plant-health-index/zonal-plant-health-index/zonal-plant-health-index.component';
import { AddEditZonalPlantHealthIndexComponent } from '../agri/zonal-plant-health-index/add-edit-zonal-plant-health-index/add-edit-zonal-plant-health-index.component';
import { ZonalStressControlRecommendationComponent } from '../agri/zonal-stress-control-recommendation/zonal-stress-control-recommendation/zonal-stress-control-recommendation.component';
import { AddEditZonalStressControlRecommendationComponent } from '../agri/zonal-stress-control-recommendation/add-edit-zonal-stress-control-recommendation/add-edit-zonal-stress-control-recommendation.component';
import { ZonalCommodityCultivationCostComponent } from './zonal-commodity-cultivation-cost/zonal-commodity-cultivation-cost.component';
import { AddEditZonalCommodityCultivationCostComponent } from './zonal-commodity-cultivation-cost/add-edit-zonal-commodity-cultivation-cost/add-edit-zonal-commodity-cultivation-cost.component';
import { ZonalCommodityComponent } from './zonal-commodity/zonal-commodity.component';
import { AddEditZonalCommodityComponent } from './zonal-commodity/add-edit-zonal-commodity/add-edit-zonal-commodity.component';
import { ZonalVarietyQualityComponent } from '../agri/zonal-variety-quality/zonal-variety-quality/zonal-variety-quality.component';
import { AddEditZonalVarietyQualityComponent } from '../agri/zonal-variety-quality/add-edit-zonal-variety-quality/add-edit-zonal-variety-quality.component';
import { ZonalFavourableWeatherComponent } from '../agri/zonal-favourable-weather/zonal-favourable-weather/zonal-favourable-weather.component';
import { AddEditZonalFavourableWeatherComponent } from '../agri/zonal-favourable-weather/add-edit-zonal-favourable-weather/add-edit-zonal-favourable-weather.component';
import { CondusiveWeatherComponent } from '../agri/condusive-weather/condusive-weather.component';
import { AddEditCondusiveWeatherComponent } from '../agri/condusive-weather/add-edit-condusive-weather/add-edit-condusive-weather.component';



const routes: Routes = [
    {
        path: '',
        data: {
            title: 'Zonal',
        },


        children: [

            {
                path: 'phenophase-duration',
                component: ZonalPhenophaseDurationComponent,
                data: {
                  title: 'Phenophase-Duration'
                }
              },
              {
                path: 'phenophase-duration/phenophase-duration-add',
                component: AddZonalPhenophaseDurationComponent,
                data: {
                  title: 'Add Phenophase-Duration'
                }
              },
              {
                path: 'phenophase-duration/phenophase-duration-edit/:id',
                component: EditZonalPhenophaseDurationComponent,
                data: {
                  title: 'Edit Phenophase-Duration'
                }
              },
              {
                path: 'variety',
                component: ZonalVarietyComponent,
                data: {
                  title: 'Variety'
                }
              },
              {
                path: 'variety/variety-add',
                component: AddZonalVarietyComponent,
                data: {
                  title: 'Add Variety'
                }
              },
              {
                path: 'variety/variety-edit/:id',
                component: EditZonalVarietyComponent,
                data: {
                  title: 'Edit Variety'
                }
              },
              {
                path: 'fertilizer',
                component: ZonalFertilizerComponent,
                data: {
                  title: 'Fertilizer'
                }
              },
              {
                path: 'fertilizer/fertilizer-add',
                component: AddZonalFertilizerComponent,
                data: {
                  title: 'Add Fertilizer'
                }
              },
              {
                path: 'fertilizer/fertilizer-edit/:id',
                component: EditZonalFertilizerComponent,
                data: {
                  title: 'Edit Fertilizer'
                }
              },
        
              {
                path: 'field-activity',
                component: ZonalFieldActivityComponent,
                data: {
                  title: 'Field-Activity'
                }
              },
              {
                path: 'field-activity/field-activity-add',
                component: AddZonalFieldActivityComponent,
                data: {
                  title: 'Add Field-Activity'
                }
              },
              {
                path: 'field-activity/field-activity-edit/:id',
                component: EditZonalFieldActivityComponent,
                data: {
                  title: 'Edit Field-Activity'
                }
              },
              {
                path: 'zonal-stress-duration',
                component: ZonalStressDurationComponent,
                data: {
                  title: 'Zonal Stress Duration'
                }
              },
              {
                path: 'zonal-stress-duration/zonal-stress-duration-add',
                component: AddEditZonalStressDurationComponent,
                data: {
                  title: 'Add Zonal Stress Duration'
                }
              },
              {
                path: 'zonal-stress-duration/zonal-stress-duration-edit/:id',
                component: AddEditZonalStressDurationComponent,
                data: {
                  title: 'Edit Zonal Stress Duration'
                }
              },
              {
        
                path: 'standard-quantity-chart',
                component: ZonalStandardQuantityChartComponent,
                data: {
                  title: 'Standard Quantity Chart'
                }
              },
              {
                path: 'standard-quantity-chart/standard-quantity-chart-add',
                component: AddEditZonalStandardQuantityChartComponent,
                data: {
                  title: 'Add Standard Quantity Chart'
                }
              },
              {
                path: 'standard-quantity-chart/standard-quantity-chart-edit/:id',
                component: AddEditZonalStandardQuantityChartComponent,
                data: {
                  title: 'Edit Standard Quantity Chart'
                }
              },
              {
                path: 'plant-health-index',
                component: ZonalPlantHealthIndexComponent,
                data: {
                  title: 'Plant Health Index'
                }
              },
              {
                path: 'plant-health-index/plant-health-index-add',
                component: AddEditZonalPlantHealthIndexComponent,
                data: {
                  title: 'Add Plant Health Index'
                }
              },
              {
                path: 'plant-health-index/plant-health-index-edit/:id',
                component: AddEditZonalPlantHealthIndexComponent,
                data: {
                  title: 'Edit Plant Health Index'
                }
              },
              {
          path: 'stress-control-recommendation',
          component: ZonalStressControlRecommendationComponent,
          data: {
            title: 'Stress Control Recommendation'
          }
        },
        {
          path: 'stress-control-recommendation/stress-control-recommendation-add',
          component: AddEditZonalStressControlRecommendationComponent,
          data: {
            title: 'Add Stress Control Recommendation'
          }
        },
        {
          path: 'stress-control-recommendation/stress-control-recommendation-edit/:id',
          component: AddEditZonalStressControlRecommendationComponent,
          data: {
            title: 'Edit Stress Control Recommendation'
          }
        },
        {
        
          path: 'commodity-cultivation-cost',
          component: ZonalCommodityCultivationCostComponent,
          data: {
            title: 'Zonal Commodity Cultivation Cost'
          }
        },
        {
          path: 'commodity-cultivation-cost/commodity-cultivation-cost-add',
          component: AddEditZonalCommodityCultivationCostComponent,
          data: {
            title: 'Add Zonal Commodity Cultivation Cost'
          }
        },
        {
          path: 'commodity-cultivation-cost/commodity-cultivation-cost-edit/:id',
          component: AddEditZonalCommodityCultivationCostComponent,
          data: {
            title: 'Edit Zonal Commodity Cultivation Cost'
          }
        },{
          path: 'commodity',
          component: ZonalCommodityComponent,
          data: {
            title: 'Commodity'
          }
        }, 
        {
          path: 'commodity/edit/:id',
          component: AddEditZonalCommodityComponent,
          data: {
            title: 'Edit Commodity'
          }
        },
        {
          path: 'commodity/add',
          component: AddEditZonalCommodityComponent,
          data: {
            title: 'Add Commodity'
          }
        },

        {
          path: 'variety-quality',
          component: ZonalVarietyQualityComponent,
          data: {
            title: 'Variety-Quality'
          }
        },
        {
          path: 'variety-quality/variety-quality-add',
          component: AddEditZonalVarietyQualityComponent,
          data: {
            title: 'Add Variety-Quality'
          }
        },
        {
          path: 'variety-quality/variety-quality-edit/:id',
          component: AddEditZonalVarietyQualityComponent,
          data: {
            title: 'Edit Variety-Quality'
          }
        },
        {
          path: 'favourable-weather',
          component: ZonalFavourableWeatherComponent,
          data: {
            title: 'Favourable Weather'
          }
        },
        {
          path: 'favourable-weather-add',
          component: AddEditZonalFavourableWeatherComponent,
          data: {
            title: 'Add Favourable Weather'
          }
        },
        {
          path: 'favourable-weather-edit/:id',
          component: AddEditZonalFavourableWeatherComponent,
          data: {
            title: 'Edit Favourable Weather'
          }
        },{
          path: 'conducive-weather',
          component: CondusiveWeatherComponent,
          data: {
            title: 'Conducive Weather'
          }
        },
        {
          path: 'conducive-weather/condusive-weather-add',
          component: AddEditCondusiveWeatherComponent,
          data: {
            title: 'Add Conducive Weather'
          }
        },
        {
          path: 'conducive-weather/condusive-weather-edit/:id',
          component: AddEditCondusiveWeatherComponent,
          data: {
            title: 'Edit Conducive Weather'
          }
        },
        {
          path: 'other-variety',
          component: OtherVarietyComponent,
          data: {
            title: 'Other Variety'
          }
        },
        {
          path: 'other-variety/add-zonal-variety-for-other-variety/:stateCode/:commodityID/:otherVarietyID',
          component: AddZonalVarietyComponent,
          data: {
            title: 'Add Zonal Variety'
          }
        },
              
        ]
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ZonalRoutingModule { }

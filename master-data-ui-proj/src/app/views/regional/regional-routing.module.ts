import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegionSeasonComponent } from './region-season/region-season.component';
import { AddRegionSeasonComponent } from './add-region-season/add-region-season.component';
import { EditRegionSeasonComponent } from './edit-region-season/edit-region-season.component';
import { RegionStressComponent } from './region-stress/region-stress.component';
import { AddRegionStressComponent } from './add-region-stress/add-region-stress.component';
import { EditRegionStressComponent } from './edit-region-stress/edit-region-stress.component';
import { RegionSeasonCommodityComponent } from './region-season-commodity/region-season-commodity.component';
import { AddEditRegionSeasonCommodityComponent } from './region-season-commodity/add-edit-region-season-commodity/add-edit-region-season-commodity.component';
import { RegionLanguageComponent } from './region-language/region-language.component';
import { AddEditRegionLanguageComponent } from './region-language/add-edit-region-language/add-edit-region-language.component';
import { RegionBankComponent } from './region-bank/region-bank/region-bank.component';
import { AddEditRegionBankComponent } from './region-bank/add-edit-region-bank/add-edit-region-bank.component';
import {MonthWbTravelTimeComponent} from './regional-month-wb-travel-time/month-wb-travel-time/month-wb-travel-time.component';
import {AddEditMonthWbTravelTimeComponent} from './regional-month-wb-travel-time/add-edit-month-wb-travel-time/add-edit-month-wb-travel-time.component';
import { WeatherBasedTravelTimeListComponent } from './weather-based-travel-time/weather-based-travel-time-list/weather-based-travel-time-list.component';
import { AddWeatherBasedtravelTimeComponent } from './add-weather-based-travel-time/add-weather-based-travel-time.component';
import { TerrainTypeComponent } from './terrain-type/terrain-type.component';
import { AddEditTerrainComponent } from './terrain-type/add-edit-terrain/add-edit-terrain.component';
import { RegionTaskTypeSpecificationComponent } from './region-task-type-specification/region-task-type-specification.component';
import { AddEditRegionTaskTypeSpecificationComponent } from './region-task-type-specification/add-edit-region-task-type-specification/add-edit-region-task-type-specification.component';
import { RegionalConnectivityComponent } from './regional-connectivity/regional-connectivity.component';
import { AddRegionalConnectivity } from './regional-connectivity/add-regional-connectivity/add-regional-connectivity.component';
import { EditRegionalConnectComponent } from './edit-regional-connectivity/edit-regional-connect.component';




const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Regional'
    },
    children: [
      {
        path: '',
        redirectTo: 'season'
      },
      {
        path: 'season',
        component: RegionSeasonComponent,
        data: {
          title: 'Seasons'
        }
      },
      {
        path: 'season-add',
        component: AddRegionSeasonComponent,
        data: {
          title: 'Add Season'
        }
      },
      {
        path: 'season-edit/:id',
        component: EditRegionSeasonComponent,
        data: {
          title: 'Edit Season'
        }
      },
      {
        path: 'stress',
        component: RegionStressComponent,
        data: {
          title: 'Stress'
        }
      },
      {
        path: 'stress-add',
        component: AddRegionStressComponent,
        data: {
          title: 'Add Stress'
        }
      },
      {
        path: 'stress-edit/:id',
        component: EditRegionStressComponent,
        data: {
          title: 'Edit Stress'
        }
      },
      {
        path: 'season-commodity',
        component: RegionSeasonCommodityComponent,
        data: {
          title: 'Commodity'
        }
      },
      {
        path: 'season-commodity-add',
        component: AddEditRegionSeasonCommodityComponent,
        data: {
          title: 'Add Commodity'
        }
      },
      {
        path: 'season-commodity-edit/:id',
        component: AddEditRegionSeasonCommodityComponent,
        data: {
          title: 'Edit Commodity'
        }
      },
      {
        path: 'language',
        component: RegionLanguageComponent,
        data: {
          title: 'Language'
        }
      },
      {
        path: 'language-add',
        component: AddEditRegionLanguageComponent,
        data: {
          title: 'Add Language'
        }
      },
      {
        path: 'language-edit/:id',
        component: AddEditRegionLanguageComponent,
        data: {
          title: 'Edit Language'
        }
      },

      {
        path: 'bank',
        component: RegionBankComponent,
        data: {
          title: 'Bank'
        }
      },
      {
        path: 'bank-add',
        component: AddEditRegionBankComponent,
        data: {
          title: 'Add Bank'
        }
      },
      {
        path: 'bank-edit/:id',
        component: AddEditRegionBankComponent,
        data: {
          title: 'Edit Bank'
        }
      },
      {
        path: 'monthly-wb-travel-time',
        component: MonthWbTravelTimeComponent,
        data: {
          title: 'list'
        }
      },
      {
        path: 'monthly-travel-time-add',
        component: AddEditMonthWbTravelTimeComponent,
        data: {
          title: 'add new regional monthly travel time'
        }
      },
      {
        path: 'monthly-travel-time-edit/:id',
        component: AddEditMonthWbTravelTimeComponent,
        data: {
          title: 'edit regional monthly travel time'
        }
      },
      {
        path: 'weather-based-travel-time',
        component: WeatherBasedTravelTimeListComponent,
        data: {
          title: 'Weather Based Travel Time List'
        }
      },
      {
        path: 'add-weather-based-travel-time',
        component: AddWeatherBasedtravelTimeComponent,
        data: {
          title: 'Weather Based Travel Time List'
        }
      },
      {
        path: 'travelTime-edit/:id',
        component: AddWeatherBasedtravelTimeComponent,
        data: {
          title: 'Weather Based Travel Time List'
        }
      },
      {
        path: 'terrain',
        component: TerrainTypeComponent,
        data: {
          title: 'Terrain Type'
        }
      },
      {
        path: 'terrain-edit/:id',
        component: AddEditTerrainComponent,
        data: {
          title: 'Edit Terrain Type'
        }
      },
      {
        path: 'terrain-add',
        component: AddEditTerrainComponent,
        data: {
          title: 'Add Terrain Type'
        }
      },
      {

        path: 'task-type-specification',
        component: RegionTaskTypeSpecificationComponent,
        data: {
          title: 'Task Type Specification'
        }
      },
      {
        path: 'task-type-specification-add',
        component: AddEditRegionTaskTypeSpecificationComponent,
        data: {
          title: 'Add Task Type Specification'
        }
      },
      {
        path: 'task-type-specification-edit/:id',
        component: AddEditRegionTaskTypeSpecificationComponent,
        data: {
          title: 'Edit Task Type Specification'
        }
      },
        {
        path: 'connectivity',
        component: RegionalConnectivityComponent,
        data: {
          title: 'Regional Connectivity'
        }
      },
      {
        path: 'add-regional-connectivity',
        component: AddRegionalConnectivity,
        data: {
          title: 'Add Regional Connectivity'
        }
      },
      {
        path: 'edit-regional-connectivity/:id',
        component:  EditRegionalConnectComponent,
        data: {
          title: 'Edit Regional Connectivity'
        },
      }

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RegionalRoutingModule { }

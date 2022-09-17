import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CondusiveWeatherComponent } from '../agri/condusive-weather/condusive-weather.component';
import { AddEditCondusiveWeatherComponent } from '../agri/condusive-weather/add-edit-condusive-weather/add-edit-condusive-weather.component';
import { ZonalFavourableWeatherComponent } from '../agri/zonal-favourable-weather/zonal-favourable-weather/zonal-favourable-weather.component';
import { AddEditZonalFavourableWeatherComponent } from '../agri/zonal-favourable-weather/add-edit-zonal-favourable-weather/add-edit-zonal-favourable-weather.component';
import { AgriPhenophaseComponent } from '../agri/agri-phenophase/agri-phenophase.component';
import { AddAgriPhenophaseComponent } from '../agri/add-agri-phenophase/add-agri-phenophase.component';
import { EditAgriPhenophaseComponent } from '../agri/edit-agri-phenophase/edit-agri-phenophase.component';
import { AgriCommodityPhenophaseComponent } from '../agri/agri-commodity-phenophase/agri-commodity-phenophase.component';
import { AddAgriCommodityPhenophaseComponent } from '../agri/add-agri-commodity-phenophase/add-agri-commodity-phenophase.component';
import { EditAgriCommodityPhenophaseComponent } from '../agri/edit-agri-commodity-phenophase/edit-agri-commodity-phenophase.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Weather'
    },
    children: [
      {
        path: '',
        redirectTo: 'phenophase'
      },
      {
        path: 'phenophase',
        component: AgriPhenophaseComponent,
        data: {
          title: 'Phenophases'
        }
      },
      {
        path: 'phenophase-add',
        component: AddAgriPhenophaseComponent,
        data: {
          title: 'Add Phenophase'
        }
      },
      {
        path: 'phenophase-edit/:id',
        component: EditAgriPhenophaseComponent,
        data: {
          title: 'Edit Phenophase'
        }
      },
      {
        path: 'commodity-phenophase',
        component: AgriCommodityPhenophaseComponent,
        data: {
          title: 'Commodity-Phenophase'
        }
      },
      {
        path: 'commodity-phenophase-add',
        component: AddAgriCommodityPhenophaseComponent,
        data: {
          title: 'Add Commodity-Phenophase'
        }
      },
      {
        path: 'commodity-phenophase-edit/:id',
        component: EditAgriCommodityPhenophaseComponent,
        data: {
          title: 'Edit Commodity-Phenophase'
        }
      },
   
     
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PhenophaseRoutingModule {}

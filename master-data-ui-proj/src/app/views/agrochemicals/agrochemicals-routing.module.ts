import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AgriPhenophaseComponent } from '../agri/agri-phenophase/agri-phenophase.component';
import { AddAgriPhenophaseComponent } from '../agri/add-agri-phenophase/add-agri-phenophase.component';
import { EditAgriPhenophaseComponent } from '../agri/edit-agri-phenophase/edit-agri-phenophase.component';
import { AgriCommodityPhenophaseComponent } from '../agri/agri-commodity-phenophase/agri-commodity-phenophase.component';
import { AddAgriCommodityPhenophaseComponent } from '../agri/add-agri-commodity-phenophase/add-agri-commodity-phenophase.component';
import { EditAgriCommodityPhenophaseComponent } from '../agri/edit-agri-commodity-phenophase/edit-agri-commodity-phenophase.component';
import { AgriCommodityAgrochemicalComponent } from '../agri/agri-commodity-agrochemical/agri-commodity-agrochemical.component';
import { AddAgriCommodityAgrochemicalComponent } from '../agri/add-agri-agrochemical-master/add-agri-commodity-agrochemical.component';
import { EditAgriCommodityAgrochemicalComponent } from '../agri/edit-agri-agrochemical-master/edit-agri-commodity-agrochemical.component';
import { AgrochemicalTypeComponent } from '../agri/agrochemical-type/agrochemical-type/agrochemical-type.component';
import { AddAgrochemicalTypeComponent } from '../agri/agrochemical-type/add-agrochemical-type/add-agrochemical-type.component';
import { EditAgrochemicalTypeComponent } from '../agri/agrochemical-type/edit-agrochemical-type/edit-agrochemical-type.component';
import { AgriRemedialMeasuresComponent } from '../agri/agri-remedial-measures/agri-remedial-measures.component';
import { AddAgriRemedialMeasuresComponent } from '../agri/add-agri-remedial-measures/add-agri-remedial-measures.component';
import { EditAgriRemedialMeasuresComponent } from '../agri/edit-agri-remedial-measures/edit-agri-remedial-measures.component';
import {AddEditAgriAgrochemicalComponent} from '../agri/add-edit-agri-agrochemical-master/add-edit-agri-agrochemical.component';
import {AgriAgrochemicalMasterComponent} from '../agri/agri-agrochemical-master/agri-agrochemical-master.component';



const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Agrochemicals'
    },
    children: [
      {
        path: '',
        redirectTo: 'phenophase'
      },
      {
        path: 'commodity-agrochemical',
        component: AgriCommodityAgrochemicalComponent,
        data: {
          title: 'Agro Commodity Chemical'
        }
      },
      {
        path: 'agrochemical',
        component: AgriAgrochemicalMasterComponent,
        data: {
          title: 'Agro Chemical'
        }
      },
      {
        path: 'commodity-agrochemical-add',
        component: AddAgriCommodityAgrochemicalComponent,
        data: {
          title: 'Add Agro Commodity Chemical'
        }
      },
      {
        path: 'agrochemical-add',
        component: AddEditAgriAgrochemicalComponent,
        data: {
          title: 'Add Agrochemical'
        }
      },
      {
        path: 'commodity-agrochemical-edit/:id',
        component: EditAgriCommodityAgrochemicalComponent,
        data: {
          title: 'Edit Agro Commodity Chemical'
        }
      },
      {
        path: 'agrochemical-edit/:id',
        component: AddEditAgriAgrochemicalComponent,
        data: {
          title: 'Edit Agrochemical'
        }
      },
      {
        path: 'agrochemical-type',
        component: AgrochemicalTypeComponent,
        data: {
          title: 'Agrochemical-Type'
        }
      },
      {
        path: 'agrochemical-type-add',
        component: AddAgrochemicalTypeComponent,
        data: {
          title: 'Add Agrochemical-Type'
        }
      },
      {
        path: 'agrochemical-type-edit/:id',
        component: EditAgrochemicalTypeComponent,
        data: {
          title: 'Edit Agrochemical-Type'
        }
      },
      {
        path: 'agrochemical-brand',
        component: AgriRemedialMeasuresComponent,
        data: {
          title: 'Agrochemical Brand'
        }
      },
      {
        path: 'agrochemical-brand-add',
        component: AddAgriRemedialMeasuresComponent,
        data: {
          title: 'Add Agrochemical Brand'
        }
      },
      {
        path: 'agrochemical-brand-edit/:id',
        component: AddAgriRemedialMeasuresComponent,
        data: {
          title: 'Edit Agrochemical Brand'
        }
      },
     
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AgrochemicalsRoutingModule {}

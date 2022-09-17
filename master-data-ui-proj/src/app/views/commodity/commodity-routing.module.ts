import { AgriQualityCommodityParameterComponent } from './agri-quality-commodity-parameter/agri-quality-commodity-parameter.component';
import { AddEditAgriQualityParameterComponent } from './agri-quality-parameter/add-edit-agri-quality-parameter/add-edit-agri-quality-parameter.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AgriCommodityComponent } from '../agri/agri-commodity/agri-commodity.component';
import { AddAgriCommodityComponent } from '../agri/add-agri-commodity/add-agri-commodity.component';
import { EditAgriCommodityComponent } from '../agri/edit-agri-commodity/edit-agri-commodity.component';
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
import {AddAgriCommodityAliasComponent} from '../agri/add-agri-commodity-alias/add-agri-commodity-alias.component';
import {BandComponent} from './band/band/band.component';
import {AddEditBandComponent} from './band/add-edit-band/add-edit-band.component';
import { AddEditAgriStateCommodityComponent } from './agri-state-commodity/add-edit-agri-state-commodity/add-edit-agri-state-commodity.component';
import { AgriStateCommodityComponent } from './agri-state-commodity/agri-state-commodity.component';
import { AgriCommodityGroupComponent } from './agri-commodity-group/agri-commodity-group.component';
import { AddEditAgriCommodityGroupComponent } from './agri-commodity-group/add-edit-agri-commodity-group/add-edit-agri-commodity-group.component';
import { AgriQualityParameterComponent } from './agri-quality-parameter/agri-quality-parameter.component';
import { AddEditAgriQualityCommodityParameterComponent } from './agri-quality-commodity-parameter/add-edit-agri-quality-commodity-parameter/add-edit-agri-quality-commodity-parameter.component';



const routes: Routes = [
  {
    path: '',
    data: {
      title: 'commodities'
    },
    children: [
      {
        path: '',
        redirectTo: 'commodities'
      },
      {
        path: 'commodities',
        component: AgriCommodityComponent,
        data: {
          title: 'Commodities'
        }
      },
      {
        path: 'commodities-add',
        component: AddAgriCommodityComponent,
        data: {
          title: 'Add Commodity'
        }
      },
      {
        path: 'commodities-edit/:id',
        component: EditAgriCommodityComponent,
        data: {
          title: 'Edit Commodity'
        }
      },
      {
        path: 'plant-part',
        component: AgriPlantPartComponent,
        data: {
          title: 'Plant-Part'
        }
      },
      {
        path: 'plant-part-add',
        component: AddAgriPlantPartComponent,
        data: {
          title: 'Add Plant-Part'
        }
      },
      {
        path: 'plant-part-edit/:id',
        component: EditAgriPlantPartComponent,
        data: {
          title: 'Edit Plant-Part'
        }
      },
      {
        path: 'commodity-plant-part',
        component: AgriCommodityPlantPartComponent,
        data: {
          title: 'Plant-Part'
        }
      },
      {
        path: 'commodity-plant-part-add',
        component: AddAgriCommodityPlantPartComponent,
        data: {
          title: 'Add Plant-Part'
        }
      },
      {
        path: 'commodity-plant-part-edit/:id',
        component: EditAgriCommodityPlantPartComponent,
        data: {
          title: 'Edit Plant-Part'
        }
      },
      {
        path: 'variety',
        component: AgriVarietyComponent,
        data: {
          title: 'Varieties'
        }
      },
      {
        path: 'variety-add',
        component: AddAgriVarietyComponent,
        data: {
          title: 'Add Variety'
        }
      },
      {
        path: 'variety-edit/:id',
        component: EditAgriVarietyComponent,
        data: {
          title: 'Edit Variety'
        }
      },
      {
        path: 'hs-code',
        component: AgriHsCodeComponent,
        data: {
          title: 'HSN-Code'
        }
      },
      {
        path: 'hs-code-add',
        component: AddAgriHsCodeComponent,
        data: {
          title: 'Add HSN-Code'
        }
      },
      {
        path: 'hs-code-edit/:id',
        component: EditAgriHsCodeComponent,
        data: {
          title: 'Edit HSN-Code'
        }
      },

      {
        path: 'general-commodity',
        component: AgriGeneralCommodityComponent,
        data: {
          title: 'General-Commodity'
        }
      },
      {
        path: 'general-commodity-add',
        component: AddAgriGeneralCommodityComponent,
        data: {
          title: 'Add General-Commodity'
        }
      },
      {
        path: 'general-commodity-edit/:id',
        component: EditAgriGeneralCommodityComponent,
        data: {
          title: 'Edit General-Commodity'
        }
      },

      {
        path: 'commodity-class',
        component: AgriCommodityClassComponent,
        data: {
          title: 'Commodity-Class'
        }
      },
      {
        path: 'commodity-class-add',
        component: AddAgriCommodityClassComponent,
        data: {
          title: 'Add Commodity-Class'
        }
      },
      {
        path: 'commodity-class-edit/:id',
        component: EditAgriCommodityClassComponent,
        data: {
          title: 'Edit Commodity-Class'
        }
      },
      {
        path: 'commodity-Alias',
        component: AddAgriCommodityAliasComponent,
        data: {
          title: 'Add Commodity Alias'
        }
      },
      {
        path: 'band',
        component: BandComponent,
        data: {
          title: 'Band'
        }
      },
      {
        path: 'add-band',
        component: AddEditBandComponent,
        data: {
            title: 'Band'
        }
       },
      {
        path: 'edit-band/:id',
        component: AddEditBandComponent,
        data: {
            title: 'Band'
        }
       },
       {
         path: 'state-commodity',
         component: AgriStateCommodityComponent,
         data: {
           title: 'State Commodity'
         }
       },
       {
         path: 'state-commodity/add-state-commodity',
         component: AddEditAgriStateCommodityComponent,
         data: {
           title: 'Add State Commodity'
         }
       },
       {
         path: 'state-commodity/edit-state-commodity/:id',
         component: AddEditAgriStateCommodityComponent,
         data: {
           title: 'Edit State Commodity'
         }
       },
       {
         path: 'commodity-group',
         component: AgriCommodityGroupComponent,
         data: {
           title: 'Commodity Group'
         }
       },
       {
         path: 'commodity-group/add-commodity-group',
         component: AddEditAgriCommodityGroupComponent,
         data: {
           title: 'Add Commodity Group'
         }
       },
       {
         path: 'commodity-group/edit-commodity-group/:id',
         component: AddEditAgriCommodityGroupComponent,
         data: {
           title: 'Edit Commodity Group'
         }
       },
       {
         path: 'quality-parameter',
         component: AgriQualityParameterComponent,
         data: {
           title: 'Quality Parameter'
         }
       },
       {
         path: 'quality-parameter/add-quality-parameter',
         component: AddEditAgriQualityParameterComponent,
         data: {
           title: 'Add Quality Parameter'
         }
       },
       {
         path: 'quality-parameter/edit-quality-parameter/:id',
         component: AddEditAgriQualityParameterComponent,
         data: {
           title: 'Edit Quality Parameter'
         }
       },
       {
         path: 'quality-commodity-parameter',
         component: AgriQualityCommodityParameterComponent,
         data: {
           title: 'Quality Commodity Parameter'
         }
       },
       {
         path: 'quality-commodity-parameter/add-quality-commodity-parameter',
         component: AddEditAgriQualityCommodityParameterComponent,
         data: {
           title: 'Add Quality Commodity Parameter'
         }
       },
       {
         path: 'quality-commodity-parameter/edit-quality-commodity-parameter/:id',
         component: AddEditAgriQualityCommodityParameterComponent,
         data: {
           title: 'Edit Quality Commodity Parameter'
         }
       }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CommodityRoutingModule {}

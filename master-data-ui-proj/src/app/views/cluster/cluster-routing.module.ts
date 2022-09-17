import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddEditClusterComponent } from './add-edit-cluster/add-edit-cluster.component';
import { ClusterListComponent } from './cluster-list/cluster-list.component';
import { ApyDataComponent } from './apy-data/apy-data.component';
import { IrrigationComponent } from './irrigation/irrigation.component';
import { LucComponent } from './luc/luc.component';
import { GeoAreaComponent } from './geo-area/geo-area.component';
import { LandHoldingAreaComponent } from './land-holding-area/land-holding-area.component';
import { VillageDataComponent } from './village-data/village-data.component';
import { SelectionComponent } from './selection/selection.component';
import { PyarComponent } from './selection/pyar/pyar.component';
import { SIrrigationComponent } from './selection/s-irrigation/s-irrigation.component';
import { BCRatioComponent } from './selection/bc-ratio/bc-ratio.component';
import { DRFVComponent } from './selection/drfv/drfv.component';
import {KpiReportComponent} from './kpi-report/kpi-report.component';
import {SourceOnboardComponent} from './source-onboard/source-onboard.component';
import {AddEditSourceComponent} from './source-onboard/add-edit-source/add-edit-source.component';



const routes: Routes = [
  {
    path: '',
    data: {
      title: 'cluster'
    },
    children: [
      {
        path: '',
        redirectTo: 'cluster-list'
      },
      {
        path: 'cluster-list',
        component: ClusterListComponent,
        data: {
          title: 'Onboarding List'
        }
      },
      {
        path: 'cluster-add',
        component: AddEditClusterComponent,
        data: {
          title: 'Add Cluster'
        }
      },
      {
        path: 'cluster-edit/:id',
        component: AddEditClusterComponent,
        data: {
          title: 'Edit Cluster'
        }
      },
      {
        path: 'apy',
        component: ApyDataComponent,
        data: {
          title: 'Apy Data'
        }
      },
      {
        path: 'irrigation',
        component: IrrigationComponent,
        data: {
          title: 'Irrigation Data'
        }
      },
      {
        path: 'luc',
        component: LucComponent,
        data: {
          title: 'LUC'
        }
      },
      {
        path: 'geo-area',
        component: GeoAreaComponent,
        data: {
          title: 'Total Geo Area'
        }
      },
      {
        path: 'land-holding-area',
        component: LandHoldingAreaComponent,
        data: {
          title: 'Land Holding Area'
        }
      },
      {
        path: 'village-data',
        component: VillageDataComponent,
        data: {
          title: 'Village Data'
        }
      },
      {
        path: 'selection',
        component: SelectionComponent,
        data: {
          title: 'Selection'
        }
      },
      {
        path: 'selection/pyar',
        component: PyarComponent,
        data: {
          title: 'Pyar'
        }
      },
      {
        path: 'selection/irrigation',
        component: SIrrigationComponent,
        data: {
          title: 'Irrigation'
        }
      },
      {
        path: 'selection/drfv',
        component: DRFVComponent,
        data: {
          title: 'DRFV'
        }
      },
      {
        path: 'selection/bd-ratio',
        component: BCRatioComponent,
        data: {
          title: 'BC Ratio'
        }
      },
      {
        path: 'kpi-report',
        component: KpiReportComponent,
        data: {
          title: 'KPI Report'
        }
      },

      /** added source list and add-edit routing -Start*/
      {
        path: 'source-list',
        component: SourceOnboardComponent,
        data: {
          title: 'Source list'
        }
      },
      {
        path: 'add-source',
        component: AddEditSourceComponent,
        data: {
          title: 'Add New Source'
        }
      },
      {
        path: 'edit-source/:id',
        component: AddEditSourceComponent,
        data: {
          title: 'Edit Source'
        }
      }
      /** added source list and add-edit routing -End*/
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClusterRoutingModule {}

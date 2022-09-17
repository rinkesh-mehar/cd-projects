import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AgriQuantityLossChartComponent } from '../agri/agri-quantity-loss-chart/agri-quantity-loss-chart/agri-quantity-loss-chart.component';
import { AddEditAgriQuantityLossChartComponent } from '../agri/agri-quantity-loss-chart/add-edit-agri-quantity-loss-chart/add-edit-agri-quantity-loss-chart.component';
import { AgriHealthComponent } from '../agri/agri-health/agri-health/agri-health.component';
import { AddEditAgriHealthComponent } from '../agri/agri-health/add-edit-agri-health/add-edit-agri-health.component';
import { AgriHealthParameterComponent } from '../agri/agri-health-parameter/agri-health-parameter/agri-health-parameter.component';
import { AddEditAgriHealthParameterComponent } from '../agri/agri-health-parameter/add-edit-agri-health-parameter/add-edit-agri-health-parameter.component';
import { AgriYieldCorrectionCriteriaComponent } from '../agri/agri-yield-correction-criteria/agri-yield-correction-criteria/agri-yield-correction-criteria.component';
import { AddEditAgriYieldCorrectionCriteriaComponent } from '../agri/agri-yield-correction-criteria/add-edit-agri-yield-correction-criteria/add-edit-agri-yield-correction-criteria.component';
import { AddEditAgriQualityChartComponent } from '../agri/agri-quality-chart/add-edit-agri-quality-chart/add-edit-agri-quality-chart.component';
import { AgriQualityChartComponent } from '../agri/agri-quality-chart/agri-quality-chart/agri-quality-chart.component';



const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Weather'
    },
    children: [
      {
        path: '',
        redirectTo: 'standard-quantity-chart'
      },
      {
        path: 'quantity-loss-chart',
        component: AgriQuantityLossChartComponent,
        data: {
          title: 'Quantity Loss Chart'
        }
      },
      {
        path: 'quantity-loss-chart-add',
        component: AddEditAgriQuantityLossChartComponent,
        data: {
          title: 'Add Quantity Loss Chart'
        }
      },
      {
        path: 'quantity-loss-chart-edit/:id',
        component: AddEditAgriQuantityLossChartComponent,
        data: {
          title: 'Edit Quantity Loss Chart'
        }
      },
      {
        path: 'health',
        component: AgriHealthComponent,
        data: {
          title: 'Health'
        }
      },
      {
        path: 'health-add',
        component: AddEditAgriHealthComponent,
        data: {
          title: 'Add Health'
        }
      },
      {
        path: 'health-edit/:id',
        component: AddEditAgriHealthComponent,
        data: {
          title: 'Edit Health'
        }
      },
      {
        path: 'health-parameter',
        component: AgriHealthParameterComponent,
        data: {
          title: 'Health-Parameter'
        }
      },
      {
        path: 'health-parameter-add',
        component: AddEditAgriHealthParameterComponent,
        data: {
          title: 'Add Health-Parameter'
        }
      },
      {
        path: 'health-parameter-edit/:id',
        component: AddEditAgriHealthParameterComponent,
        data: {
          title: 'Edit Health-Parameter'
        }
      },

     
      {
        path: 'yield-correction-criteria',
        component: AgriYieldCorrectionCriteriaComponent,
        data: {
          title: 'Yield Correction Criteria'
        }
      },
      {
        path: 'yield-correction-criteria-add',
        component: AddEditAgriYieldCorrectionCriteriaComponent,
        data: {
          title: 'Add Yield Correction Criteria'
        }
      },
      {
        path: 'yield-correction-criteria-edit/:id',
        component: AddEditAgriYieldCorrectionCriteriaComponent,
        data: {
          title: 'Edit Yield Correction Criteria'
        }
      },

      {
        path: 'quality-chart',
        component: AgriQualityChartComponent,
        data: {
          title: 'Quality Chart'
        }
      },
      {
        path: 'quality-chart-add',
        component: AddEditAgriQualityChartComponent,
        data: {
          title: 'Add Quality Chart'
        }
      },
      {
        path: 'quality-chart-edit/:id',
        component: AddEditAgriQualityChartComponent,
        data: {
          title: 'EditQuality Chart'
        }
      },
     
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class YieldRoutingModule {}

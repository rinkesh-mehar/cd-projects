import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NdviComponent } from './ndvi/ndvi.component';


const routes: Routes = [
  {
    path: '',
    data: {
      title: 'NDVI'
    },
    children: [
      {
        path: '',
        redirectTo: 'ndvi'
      },
      {

        path: 'ndvi',
        component: NdviComponent,
        data: {
          title: 'NDVI'
        }
      },
      // {
      //   path: 'standard-quantity-chart-add',
      //   component: AddEditAgriStandardQuantityChartComponent,
      //   data: {
      //     title: 'Add Standard Quantity Chart'
      //   }
      // },
      // {
      //   path: 'standard-quantity-chart-edit/:id',
      //   component: AddEditAgriStandardQuantityChartComponent,
      //   data: {
      //     title: 'Edit Standard Quantity Chart'
      //   }
      // },
     
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule {}

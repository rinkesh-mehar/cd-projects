import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FarmerLeadsComponent } from './farmer-leads/farmer-leads/farmer-leads.component';
import { FarmerYieldComponent } from './farmer-yield/farmer-yield/farmer-yield.component';




const routes: Routes = [
  {
    path: '',
    data: {
      title: 'YDC'
    },
    children: [
      {
        path: '',
        redirectTo: 'farmer-leads'
      },
      {
        path: 'farmer-leads',
        component: FarmerLeadsComponent ,
        data: {
          title: 'Farmer Leads'
        }
      },
      // {
      //   path: 'uom-add',
      //   component: AddGeneralUomComponent,
      //   data: {
      //     title: 'Add Uom'
      //   }
      // },
      {
        path: 'farmer-yield-edit/:id',
        component: FarmerYieldComponent,
        data: {
          title: 'Farmer Yiled'
        }
      },

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class YDCRoutingModule {}

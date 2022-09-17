import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import { RightsDataComponent } from './rights-data/rights-data.component';

const routes: Routes = [
    {
        path: '',
        data: {
            title: 'Manual Intervention',
        },


        children: [

            {
                path: 'rights-data',
                component: RightsDataComponent,
                data: {
                  title: 'Rights Data'
                }
              }
              
        ]
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ManualInterventionRoutingModule { }

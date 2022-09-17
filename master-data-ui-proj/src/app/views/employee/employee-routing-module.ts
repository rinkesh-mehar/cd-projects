import { LeaveDetailComponent } from './leave-detail/leave-detail.component';


import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';



const routes: Routes = [
    {
        path: '',
        data: {
            title: 'Employee',
        },


        children: [

            {
                path: 'leave-detail',
                component: LeaveDetailComponent,
                data: {
                  title: 'LeaveDetail'
                }
              },
          
              
        ]
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class EmployeeRoutingModule { }

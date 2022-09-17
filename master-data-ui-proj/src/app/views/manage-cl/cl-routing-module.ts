import { EmployeeComponent } from './employee/employee.component';
import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import { EmployeeListComponent } from './employee-list/employee-list.component';



const routes: Routes = [
    {
        path: '',
        data: {
            title: 'Manage-CL',
        },


        children: [
            {
                path: '',
                redirectTo: 'employee-list',
               
              },
              {
                path: 'employee',
                component: EmployeeListComponent,
                data: {
                  title: 'Employee List'
                }
              },
              {
                path: 'add-employee',
                component: EmployeeComponent,
                data: {
                  title: 'Add Employee'
                }
              },
          
              
        ]
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ClRoutingModule { }

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FlsVisitsComponent } from '../regional/fls-visits/fls-visits.component';
import { AddEditRegionTaskTypeSpecificationComponent } from '../regional/region-task-type-specification/add-edit-region-task-type-specification/add-edit-region-task-type-specification.component';
import { RegionTaskTypeSpecificationComponent } from '../regional/region-task-type-specification/region-task-type-specification.component';




const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Region'
    },
    children: [
      {
        path: '',
        redirectTo: ''
      },
      {

        path: 'task-type-specification',
        component: RegionTaskTypeSpecificationComponent,
        data: {
          title: 'Task Type Specification'
        }
      },
      {
        path: 'task-type-specification-add',
        component: AddEditRegionTaskTypeSpecificationComponent,
        data: {
          title: 'Add Task Type Specification'
        }
      },
      {
        path: 'task-type-specification-edit/:id',
        component: AddEditRegionTaskTypeSpecificationComponent,
        data: {
          title: 'Edit Task Type Specification'
        }
      },
      {
        path: 'fls-visits',
        component:  FlsVisitsComponent,
        data: {
          title: 'FLS Visits'
        },
      }

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RegionRoutingModule { }

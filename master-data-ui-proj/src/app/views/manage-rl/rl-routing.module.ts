import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UploadRlComponent } from './upload-rl/upload-rl.component';
import { ManageRlComponent } from './list-rl-manager/manage-rl.component';
import { ViewRlComponent } from './view-rl/view-rl.component';
import { ExportRlComponent } from './export-rl/export-rl.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Manage RL'
    },
    children: [
      {
        path: '',
        redirectTo: 'rl-users'
      },
      {
        path: 'edit-rlUser/:id',
        component: UploadRlComponent,
        data: {
          title: 'Upload RL Employee Data'
        }
      },
      {
        path: 'view-rlUser/:id',
        component: ViewRlComponent,
        data: {
          title: 'View RL Employee Data'
        }
      },
      {
        path: 'rl-users',
        component: ManageRlComponent,
        data: {
          title: 'RL Employee List'
        }
      },
      {
        path: 'add-rlUser',
        component: UploadRlComponent,
        data: {
          title: 'Add New RL Employee'
        }
      },
      {
        path: 'export-rl',
        component: ExportRlComponent,
        data: {
          title: 'Export RL Employee Data'
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RlRoutingModule { }

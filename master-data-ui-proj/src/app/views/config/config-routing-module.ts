import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import { SyncConfigComponent } from './sync-config/sync-config.component';
import { AddEditSyncConfigComponent } from './sync-config/add-edit-sync-config/add-edit-sync-config.component';

const routes: Routes = [
    {
        path: '',
        data: {
            title: 'Config',
        },


        children: [

            {
                path: 'sync-configuration',
                component: SyncConfigComponent,
                data: {
                  title: 'Sync Config'
                }
              },
              {
                path: 'sync-configuration/sync-configuration-add',
                component: AddEditSyncConfigComponent,
                data: {
                  title: 'Add Sync Config'
                }
              },
              {
                path: 'sync-configuration/sync-configuration-edit/:id',
                component: AddEditSyncConfigComponent,
                data: {
                  title: 'Edit Sync Config'
                }
              }
              
        ]
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ConfigRoutingModule { }

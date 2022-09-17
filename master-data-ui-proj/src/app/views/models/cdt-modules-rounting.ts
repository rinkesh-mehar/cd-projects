import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {ManageComponent} from './manage/manage.component';
import {AddManageComponent} from './add-Manage/add-manage.component';
import {ListModelsComponent} from './list-models/list-models.component';
import {AddNewModelsComponent} from './add-models/add-new-models.component';
import {PanchayatMapListComponent} from './panchayat-map/panchayat-map-list/panchayat-map-list.component';
import {AddEditPanchayatMapComponent} from './panchayat-map/add-edit-panchayat-map/add-edit-panchayat-map.component';

const routes: Routes = [
    {
        path: '',
        data: {
            title: 'Models',
        },
        children: [

            {
                path: '',
                redirectTo: 'manage'
            },
            {
                path: 'manage',
                component: ManageComponent,
                data: {
                    title: 'manage'
                }
            },
            {
                path: 'List',
                component: ListModelsComponent,
                data: {
                    title: 'models'
                }
            },
            {
                path: 'add-model',
                component: AddNewModelsComponent,
                data: {
                    title: 'add-model'
                }
            },
            {
                path: 'add-manage',
                component: AddManageComponent,
                data: {
                    title: 'add csv file'
                }
            },
            {
                path: 'manage-edit/:id',
                component: AddManageComponent,
                data: {
                    title: 'Edit csv file'
                }
            },
            {
                path: 'panchayat-map-list',
                component: PanchayatMapListComponent,
                data: {
                    title: 'Panchayat Region List'
                }
            },
            {
                path: 'add-panchayat',
                component: AddEditPanchayatMapComponent,
                data: {
                    title: 'Add new Panchayat'
                }
            }
        ]
    }

];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ModulesRoutingModule {
}

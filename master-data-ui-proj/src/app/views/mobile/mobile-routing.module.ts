import {NgModule} from '@angular/core';

import {RouterModule, Routes} from '@angular/router';
import { ApkVersionControlListComponent } from './apk-version-control-list/apk-version-control-list.component';
import { ApkVersionAddComponent } from '../mobile/apk-version-add/apk-version-add.component';
import { ViewVersionDetailComponent } from './view-version-detail/view-version-detail.component';


const routes: Routes = [
    {
        path: '',
        data: {
            title: 'Mobile'
        },
        children: [
            {
                path: '',
                redirectTo: 'apk-version'
            },
          {
                path: 'apk-version',
                component: ApkVersionControlListComponent,
                data: {
                    title: 'Apk'
                }
            },
          {
                path: 'add-app',
                component: ApkVersionAddComponent,
                data: {
                    title: 'Apk'
                }
            },
            {
                path: 'edit-apk/:id',
                component: ApkVersionAddComponent,
                data: {
                    title: 'edit Apk'
                }
            },
            {
                path: 'view-apk/:id',
                component: ViewVersionDetailComponent,
                data: {
                    title: 'view Apk'
                }
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class MobileRoutingModule {

}

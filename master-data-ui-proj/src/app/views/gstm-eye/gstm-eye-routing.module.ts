import {NgModule} from '@angular/core';

import {RouterModule, Routes} from '@angular/router';
import {ParametersComponent} from './parameters/parameters.component';
import {AddEditParametersComponent} from './parameters/add-edit-parameters/add-edit-parameters.component';

const routes: Routes = [
    {
        path: '',
        data: {
            title: 'GSTM Eye'
        },
        children: [
            {
                path: 'parameters',
                component: ParametersComponent,
                data: {
                    title: 'Parameters'
                }
            },
            {
                path: 'add-parameters',
                component: AddEditParametersComponent,
                data: {
                    title: 'Add Parameters'
                }
            },
            {
                path: 'edit-parameters/:id',
                component: AddEditParametersComponent,
                data: {
                    title: 'Edit Parameters'
                }
            },
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class GstmEyeRoutingModule {

}

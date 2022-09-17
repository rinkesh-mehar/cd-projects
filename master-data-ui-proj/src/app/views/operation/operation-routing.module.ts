import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {OperationComponent} from './operation.component';

export const routes: Routes = [
    {
        path: '',
        component: OperationComponent,
        data: {
            title: 'Operation'
        }
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class OperationRoutingModule {
}

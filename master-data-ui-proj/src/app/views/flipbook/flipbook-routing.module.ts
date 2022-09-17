import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ClsoComponent} from './clso/clso.component';
import {AddBmImagesComponent} from './add-bm-images/add-bm-images.component';

const routes: Routes = [
    {
        path: '',
        data: {
            title: 'Flipbook'
        },
        children: [
            {
                path: '',
                redirectTo: 'benchmark-tag'
            },
            {
                path: 'benchmark-tag',
                component: ClsoComponent ,
                data: {
                    title: 'Benchmark-Tag'
                }
            },
            {
                path: 'add-benchmark-img',
                component: AddBmImagesComponent,
                data: {
                    title: 'Benchmark-Tag'
                }
            },
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class FlipbookRoutingModule {
}

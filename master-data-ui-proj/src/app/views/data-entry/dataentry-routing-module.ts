import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import { ImdComponent } from './imd/imd.component';


const routes: Routes = [
    {
        path: '',
        data: {
            title: 'Data Entry',
        },


        children: [

            {
                path: 'imd',
                component: ImdComponent,
                data: {
                  title: 'Indian Meteorological Department'
                }
              },
          
              
        ]
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DataEntryRoutingModule { }

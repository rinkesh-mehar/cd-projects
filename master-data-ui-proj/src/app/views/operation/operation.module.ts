import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {BsDropdownModule} from 'ngx-bootstrap/dropdown';
import {ButtonsModule} from 'ngx-bootstrap/buttons';

import {CommonModule} from '@angular/common';
import {OperationComponent} from './operation.component';
import {OperationRoutingModule} from './operation-routing.module';

@NgModule({
    imports: [
        FormsModule,
        OperationRoutingModule,
        BsDropdownModule,
        ButtonsModule.forRoot(),
        CommonModule,
    ],
    declarations: [OperationComponent]
})
export class OperationModule {

}

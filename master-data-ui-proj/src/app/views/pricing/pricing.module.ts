import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GlobalModule} from '../global/global.module';
import {PipeModule} from '../pipes/pipe.module';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {ReactiveFormsModule, FormsModule} from '@angular/forms';
import {PricingMspGroupComponent} from './pricing-msp-group/pricing-msp-group/pricing-msp-group.component';
import {AddEditPricingMspGroupComponent} from './pricing-msp-group/add-edit-pricing-msp-group/add-edit-pricing-msp-group.component';
import {PricingRoutingModule} from './pricing-routing.module';
import {
    MatButtonModule,
    MatCardModule,
    MatCheckboxModule,
    MatInputModule,
    MatListModule,
    MatSortModule, MatTooltipModule
} from '@angular/material';
import {MatSelectModule} from '@angular/material/select';
import {AddEditConstantsComponent} from './constants/add-edit-constants/add-edit-constants.component';
import {ConstantsComponent} from './constants/constants/constants.component';
import { MbepAndPmpComponent } from './MbepAndPmp/mbep-and-pmp/mbep-and-pmp.component';
import { AddEditMbepPmpComponent } from './MbepAndPmp/add-edit-mbep-pmp/add-edit-mbep-pmp.component';
import { BuyerConstantComponent } from './buyer-constant/buyer-constant.component';
import {AddEditBuyerConstantComponent} from './buyer-constant/add-edit-buyer-constant/add-edit-buyer-constant.component';

@NgModule({
    declarations: [
        PricingMspGroupComponent,
        AddEditPricingMspGroupComponent,
        AddEditConstantsComponent,
        ConstantsComponent,
        MbepAndPmpComponent,
        AddEditMbepPmpComponent,
        BuyerConstantComponent,
        AddEditBuyerConstantComponent,
    ],
    imports: [
        CommonModule,
        MatSelectModule,
        PricingRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        MatListModule,
        MatButtonModule,
        GlobalModule,
        PipeModule,
        PaginationModule.forRoot(),
        MatSortModule,
        MatCardModule,
        MatCheckboxModule,
        MatInputModule,
        MatTooltipModule,
    ],
    bootstrap: [AddEditConstantsComponent]
})
export class PricingModule {
}

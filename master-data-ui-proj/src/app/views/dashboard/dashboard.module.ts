import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {ChartsModule} from 'ng2-charts';
import {BsDropdownModule} from 'ngx-bootstrap/dropdown';
import {ButtonsModule} from 'ngx-bootstrap/buttons';

import {DashboardComponent} from './dashboard.component';
import {DashboardRoutingModule} from './dashboard-routing.module';
import {CommonModule} from '@angular/common';
import {MatButtonModule, MatCardModule, MatFormFieldModule, MatSelectModule} from '@angular/material';
import {DashboardHeaderComponent} from './header/dashboard-header.component';
import { CommodityAreaComponent } from './commodity-area/commodity-area.component';
import { LeadInformationComponent } from './lead-information/lead-information.component';

@NgModule({
    imports: [
        FormsModule,
        DashboardRoutingModule,
        ChartsModule,
        BsDropdownModule,
        ButtonsModule.forRoot(),
        MatButtonModule,
        CommonModule,
        MatFormFieldModule,
        MatSelectModule,
        MatCardModule
    ],
    declarations: [DashboardComponent, DashboardHeaderComponent, CommodityAreaComponent, LeadInformationComponent]
})
export class DashboardModule {

}

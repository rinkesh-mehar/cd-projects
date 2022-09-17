import { MatSortModule } from '@angular/material';
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AddEditClusterComponent} from './add-edit-cluster/add-edit-cluster.component';
import {ClusterListComponent} from './cluster-list/cluster-list.component';
import {ClusterRoutingModule} from './cluster-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {ApyDataComponent} from './apy-data/apy-data.component';
import {IrrigationComponent} from './irrigation/irrigation.component';
import {LucComponent} from './luc/luc.component';
import {GeoAreaComponent} from './geo-area/geo-area.component';
import {LandHoldingAreaComponent} from './land-holding-area/land-holding-area.component';
import {VillageDataComponent} from './village-data/village-data.component';
import {SelectionComponent} from './selection/selection.component';
import {PyarComponent} from './selection/pyar/pyar.component';
import {DRFVComponent} from './selection/drfv/drfv.component';
import {BCRatioComponent} from './selection/bc-ratio/bc-ratio.component';
import {SIrrigationComponent} from './selection/s-irrigation/s-irrigation.component';
import {ModalModule, PaginationModule} from 'ngx-bootstrap';
import {KpiReportComponent} from './kpi-report/kpi-report.component';
import { SourceOnboardComponent } from './source-onboard/source-onboard.component';
import {PipeModule} from '../pipes/pipe.module';
import { AddEditSourceComponent } from './source-onboard/add-edit-source/add-edit-source.component';
import {GlobalModule} from '../global/global.module';

@NgModule({
    declarations: [AddEditClusterComponent, ClusterListComponent, ApyDataComponent, IrrigationComponent, LucComponent, GeoAreaComponent, LandHoldingAreaComponent, VillageDataComponent, SelectionComponent, PyarComponent, DRFVComponent, BCRatioComponent, SIrrigationComponent, KpiReportComponent, SourceOnboardComponent, AddEditSourceComponent],
    imports: [
        CommonModule,
        ClusterRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        ModalModule.forRoot(),
        PipeModule,
        PaginationModule,
        GlobalModule,

    ]
})
export class ClusterModule {
}

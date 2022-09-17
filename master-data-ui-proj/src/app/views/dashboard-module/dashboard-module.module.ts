import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NdviComponent } from './ndvi/ndvi.component';

import { ChartsModule } from 'ng2-charts';
import { DashboardRoutingModule } from '../dashboard-module/dashboard-routing.module';
import { ModalModule } from 'ngx-bootstrap/modal';



@NgModule({
  
  imports: [
    CommonModule,
    DashboardRoutingModule,
    ChartsModule,
    ModalModule.forRoot()
  ],
  declarations: [NdviComponent],
})
export class DashboardModuleModule { }

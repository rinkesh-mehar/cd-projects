import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GlobalModule } from '../global/global.module';
import { PipeModule } from '../pipes/pipe.module';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { PhenophaseRoutingModule } from './phenophase-routing.module';
import { AgriPhenophaseComponent } from '../agri/agri-phenophase/agri-phenophase.component';
import { AddAgriPhenophaseComponent } from '../agri/add-agri-phenophase/add-agri-phenophase.component';
import { AgriCommodityPhenophaseComponent } from '../agri/agri-commodity-phenophase/agri-commodity-phenophase.component';
import { AddAgriCommodityPhenophaseComponent } from '../agri/add-agri-commodity-phenophase/add-agri-commodity-phenophase.component';
import { EditAgriCommodityPhenophaseComponent } from '../agri/edit-agri-commodity-phenophase/edit-agri-commodity-phenophase.component';
import { EditAgriPhenophaseComponent } from '../agri/edit-agri-phenophase/edit-agri-phenophase.component';

import {MatSortModule} from '@angular/material';

@NgModule({
  declarations: [
    AddAgriPhenophaseComponent,
    AgriPhenophaseComponent, 
    AgriCommodityPhenophaseComponent,
    EditAgriPhenophaseComponent,
    AddAgriCommodityPhenophaseComponent,
    EditAgriCommodityPhenophaseComponent, 
    
  ],
    imports: [
        CommonModule,
        PhenophaseRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        GlobalModule,
        PipeModule,
        PaginationModule.forRoot(),
        MatSortModule,
    ]
})
export class PhenophaseModule {}

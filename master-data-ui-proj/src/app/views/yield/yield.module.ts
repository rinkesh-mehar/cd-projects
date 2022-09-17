import { MatSortModule } from '@angular/material';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GlobalModule } from '../global/global.module';
import { PipeModule } from '../pipes/pipe.module';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { YieldRoutingModule } from './yield-routing.module';
import { AgriQuantityLossChartComponent } from '../agri/agri-quantity-loss-chart/agri-quantity-loss-chart/agri-quantity-loss-chart.component';
import { AddEditAgriQuantityLossChartComponent } from '../agri/agri-quantity-loss-chart/add-edit-agri-quantity-loss-chart/add-edit-agri-quantity-loss-chart.component';
import { AgriHealthComponent } from '../agri/agri-health/agri-health/agri-health.component';
import { AddEditAgriHealthComponent } from '../agri/agri-health/add-edit-agri-health/add-edit-agri-health.component';
import { AgriHealthParameterComponent } from '../agri/agri-health-parameter/agri-health-parameter/agri-health-parameter.component';
import { AddEditAgriHealthParameterComponent } from '../agri/agri-health-parameter/add-edit-agri-health-parameter/add-edit-agri-health-parameter.component';
import { AgriYieldCorrectionCriteriaComponent } from '../agri/agri-yield-correction-criteria/agri-yield-correction-criteria/agri-yield-correction-criteria.component';
import { AddEditAgriYieldCorrectionCriteriaComponent } from '../agri/agri-yield-correction-criteria/add-edit-agri-yield-correction-criteria/add-edit-agri-yield-correction-criteria.component';
import { AddEditAgriQualityChartComponent } from '../agri/agri-quality-chart/add-edit-agri-quality-chart/add-edit-agri-quality-chart.component';
import { AgriQualityChartComponent } from '../agri/agri-quality-chart/agri-quality-chart/agri-quality-chart.component';


@NgModule({
  declarations: [
    AgriQuantityLossChartComponent,
    AddEditAgriQuantityLossChartComponent,
    AgriHealthComponent,
    AddEditAgriHealthComponent,
    AgriHealthParameterComponent,
    AddEditAgriHealthParameterComponent,
    AgriYieldCorrectionCriteriaComponent,
    AddEditAgriYieldCorrectionCriteriaComponent,
    AddEditAgriQualityChartComponent,
    AgriQualityChartComponent
    
  ],
  imports: [
    CommonModule,
    YieldRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    GlobalModule,
    PipeModule,
    PaginationModule.forRoot(),
    MatSortModule
  ]
})
export class YieldModule {}

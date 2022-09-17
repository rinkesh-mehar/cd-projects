import { MatSortModule } from '@angular/material';
import { AddEditVirtualMachinesComponent } from './virtual-machines/add-edit-virtual-machines/add-edit-virtual-machines.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeploymentsComponent } from './deployments/deployments.component';
import { AzureDevopsRoutingModule } from './azure-devops-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PaginationModule, PopoverModule } from 'ngx-bootstrap';
import { GlobalModule } from '../global/global.module';
import { PipeModule } from '../pipes/pipe.module';
import { AddEditDeploymentsComponent } from './deployments/add-edit-deployments/add-edit-deployments.component';
import { VirtualMachinesConfigurationComponent } from './virtual-machines-configuration/virtual-machines-configuration.component';
import { AddEditVirtualMachinesConfigurationComponent } from './virtual-machines-configuration/add-edit-virtual-machines-configuration/add-edit-virtual-machines-configuration.component';
import { VirtualMachinesComponent } from './virtual-machines/virtual-machines.component';

@NgModule({
  imports: [
    CommonModule,
    AzureDevopsRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    PopoverModule.forRoot(),
    PaginationModule.forRoot(),
    GlobalModule,
    PipeModule,
    MatSortModule
  ],
  declarations: [DeploymentsComponent, AddEditDeploymentsComponent, VirtualMachinesConfigurationComponent,
     AddEditVirtualMachinesConfigurationComponent, VirtualMachinesComponent, AddEditVirtualMachinesComponent]
})
export class AzureDevopsModule { }

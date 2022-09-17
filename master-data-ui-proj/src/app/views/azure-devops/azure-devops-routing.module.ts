import { VirtualMachinesComponent } from './virtual-machines/virtual-machines.component';
import { AddEditVirtualMachinesComponent } from './virtual-machines/add-edit-virtual-machines/add-edit-virtual-machines.component';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { DeploymentsComponent } from './deployments/deployments.component';
import { AddEditDeploymentsComponent } from './deployments/add-edit-deployments/add-edit-deployments.component';
import { VirtualMachinesConfigurationComponent } from './virtual-machines-configuration/virtual-machines-configuration.component';
import { AddEditVirtualMachinesConfigurationComponent } from './virtual-machines-configuration/add-edit-virtual-machines-configuration/add-edit-virtual-machines-configuration.component';

const routes: Routes = [
  {
      path: '',
      data: {
          title: 'Azure DevOps',
      },
      children: [
        {
          path: '',
          redirectTo: 'vm'
        },
        {
          path: 'vm',
          component: VirtualMachinesComponent,
          data: {
          title: 'VM'
         }
        },
        {
          path: 'vm/add-vm',
          component: AddEditVirtualMachinesComponent,
          data: {
          title: 'Add VM'
          }
        },
        {
          path: 'vm/edit-vm/:id',
          component: AddEditVirtualMachinesComponent,
          data: {
          title: 'Edit VM'
          }
        },
        {
            path: 'vmc',
            component: VirtualMachinesConfigurationComponent,
            data: {
            title: 'VM Configuration'
           }
        },
        {
            path: 'vmc/add-vmc',
            component: AddEditVirtualMachinesConfigurationComponent,
            data: {
            title: 'Add VM Configuration'
            }
        },
        {
            path: 'vmc/edit-vmc/:id',
            component: AddEditVirtualMachinesConfigurationComponent,
            data: {
            title: 'Edit VM Configuration'
            }
        },
        {
            path: 'deployments',
            component: DeploymentsComponent,
            data: {
              title: 'Deployments'
            }
        },
        {
            path: 'deployments/add-deployment',
            component: AddEditDeploymentsComponent,
            data: {
              title: 'Add Deployment'
            }
        },
       {
            path: 'deployments/edit-deployments/:id',
            component: AddEditDeploymentsComponent,
            data: {
              title: 'Edit Deployment'
            }
       },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AzureDevopsRoutingModule { }

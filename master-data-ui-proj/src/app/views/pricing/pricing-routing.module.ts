import { Routes, RouterModule } from '@angular/router';
import { PricingMspGroupComponent } from '../pricing/pricing-msp-group/pricing-msp-group/pricing-msp-group.component';
import { AddEditPricingMspGroupComponent } from '../pricing/pricing-msp-group/add-edit-pricing-msp-group/add-edit-pricing-msp-group.component';
import { NgModule } from '@angular/core';
import {ConstantsComponent} from './constants/constants/constants.component';
import {AddEditConstantsComponent} from './constants/add-edit-constants/add-edit-constants.component';
import {MbepAndPmpComponent} from './MbepAndPmp/mbep-and-pmp/mbep-and-pmp.component';
import {AddEditMbepPmpComponent} from './MbepAndPmp/add-edit-mbep-pmp/add-edit-mbep-pmp.component';
import {BuyerConstantComponent} from './buyer-constant/buyer-constant.component';
import {AddEditBuyerConstantComponent} from './buyer-constant/add-edit-buyer-constant/add-edit-buyer-constant.component';

export const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Pricing'
    },
    children: [
      {
        path: '',
        redirectTo: 'msp-mfp'
      },
      {

        path: 'msp-mfp',
        component: PricingMspGroupComponent,
        data: {
          title: 'Msp Mfp'
        }
      },
      {
        path: 'msp-mfp-add',
        component: AddEditPricingMspGroupComponent,
        data: {
          title: 'Add Msp Mfp'
        }
      },
      {
        path: 'msp-mfp-edit',
        component: AddEditPricingMspGroupComponent,
        data: {
          title: 'Edit Msp Mfp'
        }
      },
      {
        path: 'constant',
        component: ConstantsComponent,
        data: {
          title: 'Constants'
        }
      },
      {
        path: 'constant-add',
        component: AddEditConstantsComponent,
        data: {
          title: 'Add Constant'
        }
      },
      {
        path: 'mbep-pmp',
        component: MbepAndPmpComponent,
        data: {
          title: 'Mbep, Pmp And Spread'
        }
      },
      {
        path: 'mbep-pmp-add',
        component: AddEditMbepPmpComponent,
        data: {
          title: 'Add Mbep, Pmp And Spread'
        }
      },
      {
        path: 'buyer-constant',
        component: BuyerConstantComponent,
        data: {
          title: 'Buyer Constant'
        }
      },
      {
        path: 'buyer-constant-edit/:id',
        component: AddEditBuyerConstantComponent,
        data: {
          title: 'Edit Buyer Constant'
        }
      },
      {
        path: 'buyer-constant-add',
        component: AddEditBuyerConstantComponent,
        data: {
          title: 'Add Buyer Constant'
        }
      },

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PricingRoutingModule { }



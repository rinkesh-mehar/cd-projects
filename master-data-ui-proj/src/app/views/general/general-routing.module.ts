import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GeneralUomComponent  } from './general-uom/general-uom.component';
import { AddGeneralUomComponent } from './add-general-uom/add-general-uom.component';
import { EditGeneralUomComponent } from './edit-general-uom/edit-general-uom.component';
import { GeneralModeOfPaymentComponent } from './general-mode-of-payment/general-mode-of-payment.component';
import { AddEditGeneralModeOfPaymentComponent } from './general-mode-of-payment/add-edit-general-mode-of-payment/add-edit-general-mode-of-payment.component';
import { GeneralTypeOfServiceComponent } from './general-type-of-service/general-type-of-service.component';
import { AddEditGeneralTypeOfServiceComponent } from './general-type-of-service/add-edit-general-type-of-service/add-edit-general-type-of-service.component';
import { GeneralBankNameComponent } from './general-bank-name/general-bank-name.component';
import { AddEditGeneralBankNameComponent } from './general-bank-name/add-edit-general-bank-name/add-edit-general-bank-name.component';
import { GeneralDropReasonComponent } from './general-drop-reason/general-drop-reason.component';
import { AddEditGeneralDropReasonComponent } from './general-drop-reason/add-edit-general-drop-reason/add-edit-general-drop-reason.component';
import { GeneralPoiComponent } from './general-poi/general-poi.component';
import { AddEditGeneralPoiComponent } from './general-poi/add-edit-general-poi/add-edit-general-poi.component';
import { GeneralCallingStatusComponent } from './general-calling-status/general-calling-status.component';
import { AddEditGeneralCallingStatusComponent } from './general-calling-status/add-edit-general-calling-status/add-edit-general-calling-status.component';
import { GeneralCompanyComponent } from './general-company/general-company.component';
import { AddEditGeneralCompanyComponent } from './general-company/add-edit-general-company/add-edit-general-company.component';
import { WeatherParamsComponent } from './weather-params/weather-params/weather-params.component';
import { AddEditWeatherParamsComponent } from './weather-params/add-edit-weather-params/add-edit-weather-params.component';
import { BankBranchComponent } from './bank-branch/bank-branch/bank-branch.component';
import { AddEditBankBranchComponent } from './bank-branch/add-edit-bank-branch/add-edit-bank-branch.component';
import { BankCategoryComponent } from './bank-category/bank-category/bank-category.component';
import { AddEditBankCategoryComponent } from './bank-category/add-edit-bank-category/add-edit-bank-category.component';
import { GeneralPackagingTypeComponent } from './general-packaging-type/general-packaging-type.component';
import { AddEditGeneralPackagingTypeComponent } from './general-packaging-type/add-edit-general-packaging-type/add-edit-general-packaging-type.component';



const routes: Routes = [
  {
    path: '',
    data: {
      title: 'General'
    },
    children: [
      {
        path: '',
        redirectTo: 'uom'
      },
      {
        path: 'uom',
        component: GeneralUomComponent ,
        data: {
          title: 'Uom'
        }
      },
      {
        path: 'uom-add',
        component: AddGeneralUomComponent,
        data: {
          title: 'Add Uom'
        }
      },
      {
        path: 'uom-edit/:id',
        component: EditGeneralUomComponent,
        data: {
          title: 'Edit Uom'
        }
      },
      {
        path: 'mode-of-payment',
        component: GeneralModeOfPaymentComponent ,
        data: {
          title: 'Mode Of Payment'
        }
      },
      {
        path: 'mode-of-payment-add',
        component: AddEditGeneralModeOfPaymentComponent,
        data: {
          title: 'Add Mode Of Payment'
        }
      },
      {
        path: 'mode-of-payment-edit/:id',
        component: AddEditGeneralModeOfPaymentComponent,
        data: {
          title: 'Edit Mode Of Payment'
        }
      },
      {
        path: 'type-of-service',
        component: GeneralTypeOfServiceComponent,
        data: {
          title: 'Type Of Service'
        }
      },
      {
        path: 'type-of-service-add',
        component: AddEditGeneralTypeOfServiceComponent,
        data: {
          title: 'Add Type Of Service'
        }
      },
      {
        path: 'type-of-service-edit/:id',
        component: AddEditGeneralTypeOfServiceComponent,
        data: {
          title: 'Edit Type Of Service'
        }
      },
      {
        path: 'bank-name',
        component: GeneralBankNameComponent,
        data: {
          title: 'All Bank Name'
        }
      },
      {
        path: 'bank-name-add',
        component: AddEditGeneralBankNameComponent,
        data: {
          title: 'Add Bank Name'
        }
      },
      {
        path: 'bank-name-edit/:id',
        component: AddEditGeneralBankNameComponent,
        data: {
          title: 'Edit Bank Name'
        }
      },
      {
        path: 'drop-reason',
        component: GeneralDropReasonComponent,
        data: {
          title: 'All Drop Reason'
        }
      },
      {
        path: 'drop-reason-add',
        component: AddEditGeneralDropReasonComponent,
        data: {
          title: 'Add Drop Reason'
        }
      },
      {
        path: 'drop-reason-edit/:id',
        component: AddEditGeneralDropReasonComponent,
        data: {
          title: 'Edit Drop Reason'
        }
      },
      {
        path: 'poi',
        component: GeneralPoiComponent,
        data: {
          title: 'Place Of Interest'
        }
      },
      {
        path: 'poi-add',
        component: AddEditGeneralPoiComponent,
        data: {
          title: 'Add POI'
        }
      },
      {
        path: 'poi-edit/:id',
        component: AddEditGeneralPoiComponent,
        data: {
          title: 'Edit POI'
        }
      },
      {
        path: 'calling-status',
        component: GeneralCallingStatusComponent,
        data: {
          title: 'Calling Status'
        }
      },
      {
        path: 'calling-status-add',
        component: AddEditGeneralCallingStatusComponent,
        data: {
          title: 'Add Calling Status'
        }
      },
      {
        path: 'calling-status-edit/:id',
        component: AddEditGeneralCallingStatusComponent,
        data: {
          title: 'Edit Calling Status'
        }
      },
      {
        path: 'company',
        component: GeneralCompanyComponent,
        data: {
          title: 'Company'
        }
      },
      {
        path: 'company-add',
        component: AddEditGeneralCompanyComponent,
        data: {
          title: 'Add Company'
        }
      },
      {
        path: 'company-edit/:id',
        component: AddEditGeneralCompanyComponent,
        data: {
          title: 'Edit Company'
        }
      },
      {
        path: 'weather-params',
        component: WeatherParamsComponent,
        data: {
          title: 'Weather Params'
        }
      },
      {
        path: 'weather-params-add',
        component: AddEditWeatherParamsComponent,
        data: {
          title: 'Add Weather Params'
        }
      },
      {
        path: 'weather-params-edit/:id',
        component: AddEditWeatherParamsComponent,
        data: {
          title: 'Edit Weather Params'
        }
      },

      {
        path: 'bank-branch',
        component: BankBranchComponent,
        data: {
          title: 'All Bank Branch'
        }
      },
      {
        path: 'bank-branch-add',
        component: AddEditBankBranchComponent,
        data: {
          title: 'Add Bank Branch'
        }
      },
      {
        path: 'bank-branch-edit/:id',
        component: AddEditBankBranchComponent,
        data: {
          title: 'Edit Bank Branch'
        }
      },

      {
        path: 'bank-category',
        component: BankCategoryComponent,
        data: {
          title: 'All Bank Category'
        }
      },
      {
        path: 'bank-category-add',
        component: AddEditBankCategoryComponent,
        data: {
          title: 'Add Bank Category'
        }
      },
      {
        path: 'bank-category-edit/:id',
        component: AddEditBankCategoryComponent,
        data: {
          title: 'Edit Bank Category'
        }
      },
      {
        path: 'packaging-type',
        component: GeneralPackagingTypeComponent,
        data: {
          title: 'Packaging Type'
        }
      },
      {
        path: 'packaging-type/add-packaging-type',
        component: AddEditGeneralPackagingTypeComponent,
        data: {
          title: 'Add Packaging Type'
        }
      },
      {
        path: 'packaging-type/edit-packaging-type/:id',
        component: AddEditGeneralPackagingTypeComponent,
        data: {
          title: 'Edit Packaging Type'
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GeneralRoutingModule {}

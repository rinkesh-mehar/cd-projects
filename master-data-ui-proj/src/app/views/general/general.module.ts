import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GeneralUomComponent } from './general-uom/general-uom.component';
import { GeneralRoutingModule } from "./general-routing.module";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
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
import { GlobalModule } from '../global/global.module';
import { WeatherParamsComponent } from './weather-params/weather-params/weather-params.component';
import { AddEditWeatherParamsComponent } from './weather-params/add-edit-weather-params/add-edit-weather-params.component';
import { BankBranchComponent } from './bank-branch/bank-branch/bank-branch.component';
import { AddEditBankBranchComponent } from './bank-branch/add-edit-bank-branch/add-edit-bank-branch.component';
import { PipeModule } from '../pipes/pipe.module';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { AddEditBankCategoryComponent } from './bank-category/add-edit-bank-category/add-edit-bank-category.component';
import { BankCategoryComponent } from './bank-category/bank-category/bank-category.component';
import {MatSortModule} from '@angular/material';
import { GeneralPackagingTypeComponent } from './general-packaging-type/general-packaging-type.component';
import { AddEditGeneralPackagingTypeComponent } from './general-packaging-type/add-edit-general-packaging-type/add-edit-general-packaging-type.component';



@NgModule({
  declarations: [GeneralUomComponent, AddGeneralUomComponent, EditGeneralUomComponent,
    GeneralModeOfPaymentComponent, AddEditGeneralModeOfPaymentComponent, GeneralTypeOfServiceComponent, AddEditGeneralTypeOfServiceComponent, GeneralBankNameComponent,
     AddEditGeneralBankNameComponent, GeneralDropReasonComponent, AddEditGeneralDropReasonComponent, GeneralPoiComponent, 
     AddEditGeneralPoiComponent, GeneralCallingStatusComponent, AddEditGeneralCallingStatusComponent, GeneralCompanyComponent, 
     AddEditGeneralCompanyComponent,WeatherParamsComponent,AddEditWeatherParamsComponent, BankBranchComponent, AddEditBankBranchComponent, AddEditBankCategoryComponent, BankCategoryComponent,
    GeneralPackagingTypeComponent,AddEditGeneralPackagingTypeComponent],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        GeneralRoutingModule,
        GlobalModule,
        PipeModule,
        PaginationModule.forRoot(),
        MatSortModule,
    ]
})
export class GeneralModule { }

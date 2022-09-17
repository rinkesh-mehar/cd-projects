import {NgModule} from '@angular/core';
import {LogisticRegistrationComponent} from './logistic-hub-registration/logistic-registration.component';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {LogisticHubRoutingModule} from './logistic-hub-routing.module';
import {PopoverModule} from 'ngx-bootstrap/popover';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {GlobalModule} from '../global/global.module';
import {PipeModule} from '../pipes/pipe.module';
import {
    MatButtonModule,
    MatCardModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatSortModule,
    MatStepperModule
} from '@angular/material';
import {LogisticHubKycComponent} from './logistic-hub-kyc/logistic-hub-kyc.component';
import {LogistichubComponent} from './logisticHub-list/logistichub.component';
import {STEPPER_GLOBAL_OPTIONS} from '@angular/cdk/stepper';
import { LogisticHubDocListingComponent } from './logistic-hub-doc-listing/logistic-hub-doc-listing.component';
import { LogisticHubDocManageComponent } from './logistic-hub-doc-listing/logistic-hub-doc-manage/logistic-hub-doc-manage.component';
import { LogisticHubPvApprovedListComponent } from './logistic-hub-pv-approved-list/logistic-hub-pv-approved-list.component';
import { DocApprovedListComponent } from './doc-approved-list/doc-approved-list.component';
import { PhysicalVerificationImagesUploadComponent } from './physical-verification-images-upload/physical-verification-images-upload.component';
import { LhCompleteDetailsComponent } from './lh-complete-details/lh-complete-details.component';
import { LogisticHubShortlistedComponent } from './logistic-hub-kyc-shortlisted/logistic-hub-kyc-shortlisted.component';
import { PhysicalVerificationListingComponent } from './PhysicalVerification/physical-verification-listing/physical-verification-listing.component';
import { PhysicalVerificationManageComponent } from './PhysicalVerification/PhysicalVerificationManage/physical-verification-manage.component';
import {RejectedLogisticHubComponent} from './rejected-logisticHub/rejectedLogisticHub.component';
import {SelectCheckAllComponent} from './logistic-hub-registration/select-check-all/select-check-all.component';
import {AddEditEmployeeComponent} from './lh-employee/add-edit-lh-employee/add-edit-employee.component';
import {LhEmployeeListComponent} from './lh-employee/lh-employee-list/lh-employee-list.component';
import { PvLhImagesUploadComponent } from './pv-lh-images-upload/pv-lh-images-upload.component';
import {AddEditRegionLanguageComponent} from '../regional/region-language/add-edit-region-language/add-edit-region-language.component';


@NgModule({
    declarations: [
        LogisticHubKycComponent,
        LogisticRegistrationComponent,
        LogistichubComponent,
        LogisticHubDocListingComponent,
        LogisticHubDocManageComponent,
        LogisticHubPvApprovedListComponent,
        DocApprovedListComponent,
        PhysicalVerificationImagesUploadComponent,
        LhCompleteDetailsComponent,
        LogisticHubShortlistedComponent,
        PhysicalVerificationListingComponent,
        PhysicalVerificationManageComponent,
        RejectedLogisticHubComponent,
        SelectCheckAllComponent,
        AddEditEmployeeComponent,
        LhEmployeeListComponent,
        PvLhImagesUploadComponent,
        AddEditRegionLanguageComponent

    ],
    imports: [
        CommonModule,
        FormsModule,
        LogisticHubRoutingModule,
        ReactiveFormsModule,
        PopoverModule.forRoot(),
        PaginationModule.forRoot(),
        GlobalModule,
        PipeModule,
        MatStepperModule,
        MatFormFieldModule,
        MatButtonModule,
        MatInputModule,
        [MatStepperModule],
        MatCheckboxModule,
        MatSelectModule,
        MatCardModule,
        MatSortModule,
    ],
    providers: [
        {
            provide: STEPPER_GLOBAL_OPTIONS,
            useValue: { showError: true }
        }
    ]

})

export class LogisticHubModule {
}

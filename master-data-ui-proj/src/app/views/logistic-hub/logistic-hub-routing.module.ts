import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LogisticRegistrationComponent } from './logistic-hub-registration/logistic-registration.component';
import { LogistichubComponent } from './logisticHub-list/logistichub.component';
import { LogisticHubPvApprovedListComponent } from './logistic-hub-pv-approved-list/logistic-hub-pv-approved-list.component';
import { LogisticHubKycComponent } from './logistic-hub-kyc/logistic-hub-kyc.component';
import { LogisticHubDocListingComponent } from './logistic-hub-doc-listing/logistic-hub-doc-listing.component';
import { LogisticHubDocManageComponent } from './logistic-hub-doc-listing/logistic-hub-doc-manage/logistic-hub-doc-manage.component';
import { LhCompleteDetailsComponent } from './lh-complete-details/lh-complete-details.component';
import { DocApprovedListComponent } from './doc-approved-list/doc-approved-list.component';
import { PhysicalVerificationImagesUploadComponent } from './physical-verification-images-upload/physical-verification-images-upload.component';
import { LogisticHubShortlistedComponent } from './logistic-hub-kyc-shortlisted/logistic-hub-kyc-shortlisted.component';
import {PhysicalVerificationListingComponent} from './PhysicalVerification/physical-verification-listing/physical-verification-listing.component';
import {PhysicalVerificationManageComponent} from './PhysicalVerification/PhysicalVerificationManage/physical-verification-manage.component';
import {RejectedLogisticHubComponent} from './rejected-logisticHub/rejectedLogisticHub.component';
import {AddEditEmployeeComponent} from './lh-employee/add-edit-lh-employee/add-edit-employee.component';
import {LhEmployeeListComponent} from './lh-employee/lh-employee-list/lh-employee-list.component';
import { PvLhImagesUploadComponent } from './pv-lh-images-upload/pv-lh-images-upload.component';

const routes: Routes = [
    {
        path: '',
        data: {
            title: 'logistic-hub'
        },
        children: [
            {
                path: '',
                redirectTo: 'lh-rejected-hub-list'
            },
            {
                path: 'lh-hub-registration/add',
                component: LogisticRegistrationComponent,
                data: {
                    title: 'Add New LH'
                }
            },
            {
                path: 'lh-hub/view/:id',
                component: LogisticRegistrationComponent,
                data: {
                    title: 'View Collected LH'
                }
            },
            {
                path: 'lh-hub/edit/:id',
                component: LogisticRegistrationComponent,
                data: {
                    title: 'Edit Collected LH'
                }
            },
            {
                path: 'lh-collected-hub-list',
                component: LogistichubComponent,
                data: {
                    title: 'List'
                }
            },
            {
                path: 'lh-rejected-hub-list',
                component: RejectedLogisticHubComponent,
                data: {
                    title: 'List'
                }
            },
            {
                path: 'lh-pv-approved-list',
                component: LogisticHubPvApprovedListComponent,
                data: {
                    title: 'Physical Verification Approved List'
                }
            },
            {
                path: 'lh-kyc/:id',
                component: LogisticHubKycComponent,

            }, {
                path: 'doc-listing',
                component: LogisticHubDocListingComponent,
                data: {
                    title: 'Doc-Listing'
                }
            }, {
                path: 'doc-listing-edit/:id',
                component: LogisticHubDocManageComponent,
                data: {
                    title: 'Doc-Listing-edit'
                }
            },
            {
                path: 'view-lh-complete-details/:id',
                component: LhCompleteDetailsComponent,
                data: {
                    title: 'LH Details'
                }
            },
            {
                path: 'lh-doc-approved-list',
                component: DocApprovedListComponent,
                data: {
                    title: 'LH Doc-Approved List'
                }
            },
            // {// old one for images upload
            //     path: 'lh-pv-images-upload/:id',
            //     component: PhysicalVerificationImagesUploadComponent,
            //     data: {
            //         title: 'LH Images Upload'
            //     }
            // },

            // new one for image upload, multiple
            {
                path: 'pv-images-upload/:id',
                component: PvLhImagesUploadComponent,
                data: {
                    title: 'LH Images Upload'
                }
            },
            {
                path: 'lh-shortlisted-kyc',
                component: LogisticHubShortlistedComponent,
            },
            {
                path: 'pv-images-listing',
                component: PhysicalVerificationListingComponent,
                data: {
                    title: 'Physical Verification Images'
                }
            },
            {
                path: 'pv-images-listing-manage/:id',
                component: PhysicalVerificationManageComponent,
                data: {
                    title: 'Physical Verification Manage'
                }
            },
            {
                path: 'lh-employee-list',
                component: LhEmployeeListComponent,
                data: {
                    title: 'Employee List'
                }
            },
            {
                path: 'lh-employee-add',
                component: AddEditEmployeeComponent,
                data: {
                    title: 'add New Employee'
                }
            },
            {
                path: 'edit-lh-employee/:id',
                component: AddEditEmployeeComponent,
                data: {
                    title: 'edit Employee'
                }
            }

        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LogisticHubRoutingModule {

}

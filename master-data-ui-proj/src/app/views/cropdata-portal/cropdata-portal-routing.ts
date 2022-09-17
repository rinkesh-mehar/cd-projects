import { AddEditPlatformMasterComponent } from './platform-master/add-edit-platform-master/add-edit-platform-master.component';
import { PlatformMasterComponent } from './platform-master/platform-master.component';
import { ToolsComponent } from './tools/tools.component';
import { EnginesComponent } from './engines/engines.component';
import { DepartmentComponent } from './department/department.component';
import {RouterModule, Routes} from '@angular/router';
import {AddEditNewsComponent} from './news/add-edit-news/add-edit-news.component';
import {NgModule} from '@angular/core';
import {NewsComponent} from './news/news/news.component';
import {AddEditReportComponent} from './report/add-edit-reports/add-edit-report.component';
import {ReportsComponent} from './report/report/reports.component';
import { OppoortunityComponent } from './oppoortunity/oppoortunity.component';
import { AddOppotunityComponent } from './oppoortunity/add-oppotunity/add-oppotunity.component';
import { JobApplicationComponent } from './job-application/job-application.component';
import { ViewJobApplicationComponent } from './job-application/view-job-application/view-job-application.component';
import { AddEditDepartmentComponent } from './department/add-edit-department/add-edit-department.component';
import { EducationComponent } from './education/education.component';
import { AddEditEducationComponent } from './education/add-edit-education/add-edit-education.component';
import { PositionComponent } from './position/position.component';
import { AddEditPositionComponent } from './position/add-edit-position/add-edit-position.component';
import { BuyerPreApplicationComponent } from './buyer-pre-application/buyer-pre-application.component';
import { EditBuyerPreApplicationComponent } from './buyer-pre-application/edit-buyer-pre-application/edit-buyer-pre-application.component';
import { ExportBuyerPreApplicationComponent } from './buyer-pre-application/export-buyer-pre-application/export-buyer-pre-application.component';
import { PartnershipRequestComponent } from './partnership-request/partnership-request.component';
import { ViewPartnershipRequestComponent } from './partnership-request/view-partnership-request/view-partnership-request.component';
import { ContactRequestComponent } from './contact-request/contact-request.component';
import { ReportDownloadRequestComponent } from './report-download-request/report-download-request.component';
import { ExportReportDownloadRequestComponent } from './report-download-request/export-report-download-request/export-report-download-request.component';
import { CropdataPriorityNewsComponent } from './news/cropdata-priority-news/cropdata-priority-news.component';
import { CropdataLatestNewsPriorityComponent } from './news/cropdata-latest-news-priority/cropdata-latest-news-priority.component';
import {EventCalendarComponent} from './event-calendar/event-calendar.component';
import { AddEditEnginesComponent } from './engines/add-edit-engines/add-edit-engines.component';
import { AddEditToolsComponent } from './tools/add-edit-tools/add-edit-tools.component';
import { IndicesComponent } from './indices/indices.component';
import { AddEditIndicesComponent } from './indices/add-edit-indices/add-edit-indices.component';
import { VasComponent } from './vas/vas.component';
import { AddEditVasComponent } from './vas/add-edit-vas/add-edit-vas.component';
import { ProductsAndServicesComponent } from './products-and-services/products-and-services.component';
import { AddEditProductsAndServicesComponent } from './products-and-services/add-edit-products-and-services/add-edit-products-and-services.component';
import { TermasAndConditionsComponent } from './termas-and-conditions/termas-and-conditions.component';
import { AddEditTermsAndConditionsComponent } from './termas-and-conditions/add-edit-terms-and-conditions/add-edit-terms-and-conditions.component';
import { ViewBuyerPreApplicationComponent } from './buyer-pre-application/view-buyer-pre-application/view-buyer-pre-application.component';



const routes: Routes = [
    {
        path: '',
        data: {
            title: 'Cropdata Portal',
        },
        children: [

            {
                path: '',
                redirectTo: 'news'
            },
            {
                path: 'news',
                component: NewsComponent,
                data: {
                    title: 'News'
                }
            },
            {
                path: 'reports',
                component: ReportsComponent,
                data: {
                    title: 'Reports'
                }
            },
            {
                path: 'add-news',
                component: AddEditNewsComponent,
                data: {
                    title: 'Add News'
                }
            },
            {
                path: 'edit-news/:id',
                component: AddEditNewsComponent,
                data: {
                    title: 'Edit News'
                }
            },
            {
                path: 'add-report',
                component: AddEditReportComponent,
                data: {
                    title: 'Add Report'
                }
            },
            {
                path: 'edit-report/:id',
                component: AddEditReportComponent,
                data: {
                    title: 'Edit Report'
                }
            },
            {
                path: 'opportunities-list',
                component: OppoortunityComponent,
                data: {
                    title: 'Opportunities'
                }
            },
            {
                path: 'oppotunity/add-oppotunity',
                component: AddOppotunityComponent,
                data: {
                    title: 'Add Opportunity'
                }
            },
            {
                path: 'oppotunity/edit-oppotunity/:id',
                component: AddOppotunityComponent,
                data: {
                    title: 'Edit Opportunity'
                }
            },
            {
                path: 'job-application-list',
                component: JobApplicationComponent,
                data: {
                    title: 'Job Application'
                }
            },
            {
                path: 'job-application/view/:id',
                component: ViewJobApplicationComponent,
                data: {
                    title: 'View Job Application Details'
                }
            },
            {
                path: 'department-list',
                component: DepartmentComponent,
                data: {
                    title: 'Department'
                }
            },
            {
                path: 'department/add-department',
                component: AddEditDepartmentComponent,
                data: {
                    title: 'Add Department'
                }
            },
            {
                path: 'department/edit-department/:id',
                component: AddEditDepartmentComponent,
                data: {
                    title: 'Edit Department'
                }
            },
            {
                path: 'education-list',
                component: EducationComponent,
                data: {
                    title: 'Education'
                }
            },
            {
                path: 'education/add-education',
                component: AddEditEducationComponent,
                data: {
                    title: 'Add Education'
                }
            },
            {
                path: 'education/edit-education/:id',
                component: AddEditEducationComponent,
                data: {
                    title: 'Edit Education'
                }
            },
            {
                path: 'position-list',
                component: PositionComponent,
                data: {
                    title: 'Position'
                }
            },
            {
                path: 'position/add-position',
                component: AddEditPositionComponent,
                data: {
                    title: 'Add Position'
                }
            },
            {
                path: 'position/edit-position/:id',
                component: AddEditPositionComponent,
                data: {
                    title: 'Edit Position'
                }
            },
            {
                path: 'buyer-pre-application-list',
                component: BuyerPreApplicationComponent,
                data: {
                    title: 'Agriota Buyer Registration'
                }
            },
            {
                path: 'buyer-pre-application/edit-buyer-pre-application/:id',
                component: EditBuyerPreApplicationComponent,
                data: {
                    title: 'Edit Agriota Buyer Registration'
                }
            },
            {
                path: 'buyer-pre-application/export-buyer-pre-application',
                component: ExportBuyerPreApplicationComponent,
                data: {
                    title: 'Export Agriota Buyer Registration'
                }
            },
            {
                path: 'buyer-pre-application/view/:id',
                component: ViewBuyerPreApplicationComponent,
                data: {
                    title: 'View Agriota Buyer Registration'
                }
            },
            {
                path: 'partnership-request-list',
                component: PartnershipRequestComponent,
                data: {
                    title: 'Partnership Request'
                }
            },
            {
                path: 'partnership-request/view/:id',
                component: ViewPartnershipRequestComponent,
                data: {
                    title: 'View Partnership Request Details'
                }
            },
            {
                path: 'contact-request-list',
                component: ContactRequestComponent,
                data: {
                    title: 'Contact Request'
                }
            },
            {
                path: 'report-download-request-list',
                component: ReportDownloadRequestComponent,
                data: {
                    title: 'Report Requests'
                }
            },
            {
                path: 'report-download-request-list/export-report-download-request',
                component: ExportReportDownloadRequestComponent,
                data: {
                    title: 'Export Report Requests'
                }
            },
            {
                path: 'news/crpodata-priority-news',
                component: CropdataPriorityNewsComponent,
                data: {
                    title: 'Cropdata News'
                }
            },
            {
                path: 'news/crpodata-latest-news-priority',
                component: CropdataLatestNewsPriorityComponent,
                data: {
                    title: 'Cropdata Latest News'
                }
            },
            {
                path: 'holiday-calendar',
                component: EventCalendarComponent,
                data : {
                    title: 'Holiday Calendar'
                }
            },
            {
                path: 'engines',
                component: EnginesComponent,
                data: {
                    title: 'Engines'
                }
            },
            {
                path: 'engines/add-engine',
                component: AddEditEnginesComponent,
                data: {
                    title: 'Add Engine'
                }
            },
            {
                path: 'engines/edit-engine/:id',
                component: AddEditEnginesComponent,
                data: {
                    title: 'Edit Engine'
                }
            },
            {
                path: 'tools',
                component: ToolsComponent,
                data: {
                    title: 'Tools'
                }
            },
            {
                path: 'tools/add-tool',
                component: AddEditToolsComponent,
                data: {
                    title: 'Add Tool'
                }
            },
            {
                path: 'tools/edit-tool/:id',
                component: AddEditToolsComponent,
                data: {
                    title: 'Edit Tool'
                }
            },
            {
                path: 'indices',
                component: IndicesComponent,
                data: {
                    title: 'Indices'
                }
            },
            {
                path: 'indices/add-indices',
                component: AddEditIndicesComponent,
                data: {
                    title: 'Add Indices'
                }
            },
            {
                path: 'indices/edit-indices/:id',
                component: AddEditIndicesComponent,
                data: {
                    title: 'Edit Indices'
                }
            },
            {
                path: 'vas',
                component: VasComponent,
                data: {
                    title: 'VAS'
                }
            },
            {
                path: 'vas/add-vas',
                component: AddEditVasComponent,
                data: {
                    title: 'Add VAS'
                }
            },
            {
                path: 'vas/edit-vas/:id',
                component: AddEditVasComponent,
                data: {
                    title: 'Edit VAS'
                }
            },
            {
                path: 'products-and-servics',
                component: ProductsAndServicesComponent,
                data: {
                    title: 'Products And Servics'
                }
            },
            {
                path: 'products-and-servics/add-products-and-servics',
                component: AddEditProductsAndServicesComponent,
                data: {
                    title: 'Add Products And Servics'
                }
            },
            {
                path: 'products-and-servics/edit-products-and-servics/:id',
                component: AddEditProductsAndServicesComponent,
                data: {
                    title: 'Edit Products And Servics'
                }
            },
            {
                path: 'platform',
                component: PlatformMasterComponent,
                data: {
                    title: 'Platform'
                }
            },
            {
                path: 'platform/add-platform',
                component: AddEditPlatformMasterComponent,
                data: {
                    title: 'Add Platform'
                }
            },
            {
                path: 'platform/edit-platform/:id',
                component: AddEditPlatformMasterComponent,
                data: {
                    title: 'Edit Platform'
                }
            },
            {
                path: 'terms-and-conditions',
                component: TermasAndConditionsComponent,
                data: {
                    title: 'Terms And Conditions'
                }
            },
            {
                path: 'terms-and-conditions/add',
                component: AddEditTermsAndConditionsComponent,
                data: {
                    title: 'Add Terms And Conditions'
                }
            },
            {
                path: 'terms-and-conditions/edit/:id',
                component: AddEditTermsAndConditionsComponent,
                data: {
                    title: 'Edit Terms And Conditions'
                }
            }
        ]
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class CropdataPortalRoutingModule { }

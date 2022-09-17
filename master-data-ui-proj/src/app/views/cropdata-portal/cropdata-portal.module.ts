import { PlatformMasterComponent } from './platform-master/platform-master.component';
import { ToolsComponent } from './tools/tools.component';
import { EnginesComponent } from './engines/engines.component';
import { MY_DATE_FORMATS } from './../my-date-formats';
import { DepartmentComponent } from './department/department.component';
import {NgModule} from '@angular/core';
import {AddEditNewsComponent} from './news/add-edit-news/add-edit-news.component';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {PopoverModule} from 'ngx-bootstrap/popover';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {GlobalModule} from '../global/global.module';
import {PipeModule} from '../pipes/pipe.module';
import {CropdataPortalRoutingModule} from './cropdata-portal-routing';
import {NewsComponent} from './news/news/news.component';
import {AddEditReportComponent} from './report/add-edit-reports/add-edit-report.component';
import {ReportsComponent} from './report/report/reports.component';
import { OppoortunityComponent } from './oppoortunity/oppoortunity.component';
import { AddOppotunityComponent } from './oppoortunity/add-oppotunity/add-oppotunity.component';
import { JobApplicationComponent } from './job-application/job-application.component';
import { AddEditDepartmentComponent } from './department/add-edit-department/add-edit-department.component';
import { EducationComponent } from './education/education.component';
import { AddEditEducationComponent } from './education/add-edit-education/add-edit-education.component';
import { PositionComponent } from './position/position.component';
import { AddEditPositionComponent } from './position/add-edit-position/add-edit-position.component';
import { BuyerPreApplicationComponent } from './buyer-pre-application/buyer-pre-application.component';
import { EditBuyerPreApplicationComponent } from './buyer-pre-application/edit-buyer-pre-application/edit-buyer-pre-application.component';
import {
    MatAutocompleteModule,
    MatBadgeModule,
    MatBottomSheetModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatDatepickerModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule, MatRippleModule,
    MatSelectModule, MatSidenavModule, MatSliderModule, MatSlideToggleModule, MatSnackBarModule,
    MatSortModule,
    MatStepperModule, MatTableModule, MatTabsModule, MatToolbarModule, MatTooltipModule, MatTreeModule, MAT_DATE_FORMATS
} from '@angular/material';
import { ExportBuyerPreApplicationComponent } from './buyer-pre-application/export-buyer-pre-application/export-buyer-pre-application.component';
import { PartnershipRequestComponent } from './partnership-request/partnership-request.component';
import { ViewPartnershipRequestComponent } from './partnership-request/view-partnership-request/view-partnership-request.component';
import { ContactRequestComponent } from './contact-request/contact-request.component';
import { ReportDownloadRequestComponent } from './report-download-request/report-download-request.component';
import { ExportReportDownloadRequestComponent } from './report-download-request/export-report-download-request/export-report-download-request.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { CropdataPriorityNewsComponent } from './news/cropdata-priority-news/cropdata-priority-news.component';
import { CropdataLatestNewsPriorityComponent } from './news/cropdata-latest-news-priority/cropdata-latest-news-priority.component';
import { EventCalendarComponent } from './event-calendar/event-calendar.component';
import {RegionalRoutingModule} from '../regional/regional-routing.module';
import {A11yModule} from '@angular/cdk/a11y';
import {CdkStepperModule} from '@angular/cdk/stepper';
import {CdkTableModule} from '@angular/cdk/table';
import {CdkTreeModule} from '@angular/cdk/tree';
import {PortalModule} from '@angular/cdk/portal';
import {ScrollingModule} from '@angular/cdk/scrolling';
import { MomentDateModule } from '@angular/material-moment-adapter';
import { AddEditEnginesComponent } from './engines/add-edit-engines/add-edit-engines.component';
import { AddEditToolsComponent } from './tools/add-edit-tools/add-edit-tools.component';
import { IndicesComponent } from './indices/indices.component';
import { AddEditIndicesComponent } from './indices/add-edit-indices/add-edit-indices.component';
import { VasComponent } from './vas/vas.component';
import { AddEditVasComponent } from './vas/add-edit-vas/add-edit-vas.component';
import { ProductsAndServicesComponent } from './products-and-services/products-and-services.component';
import { AddEditProductsAndServicesComponent } from './products-and-services/add-edit-products-and-services/add-edit-products-and-services.component';
import { AddEditPlatformMasterComponent } from './platform-master/add-edit-platform-master/add-edit-platform-master.component';
import { AutocompleteLibModule } from 'angular-ng-autocomplete';
import { TermasAndConditionsComponent } from './termas-and-conditions/termas-and-conditions.component';
import { AddEditTermsAndConditionsComponent } from './termas-and-conditions/add-edit-terms-and-conditions/add-edit-terms-and-conditions.component';
import { ViewBuyerPreApplicationComponent } from './buyer-pre-application/view-buyer-pre-application/view-buyer-pre-application.component';

@NgModule({
    declarations: [
        AddEditNewsComponent, NewsComponent, AddEditReportComponent, ReportsComponent, OppoortunityComponent, AddOppotunityComponent,
        JobApplicationComponent, DepartmentComponent, AddEditDepartmentComponent, EducationComponent, AddEditEducationComponent,
        PositionComponent, AddEditPositionComponent, BuyerPreApplicationComponent,
        EditBuyerPreApplicationComponent, ExportBuyerPreApplicationComponent, PartnershipRequestComponent, ViewPartnershipRequestComponent,
        ContactRequestComponent, ReportDownloadRequestComponent, ExportReportDownloadRequestComponent, CropdataPriorityNewsComponent,
        CropdataLatestNewsPriorityComponent,EventCalendarComponent,EnginesComponent,AddEditEnginesComponent,ToolsComponent,AddEditToolsComponent,
        IndicesComponent,AddEditIndicesComponent,VasComponent,AddEditVasComponent,ProductsAndServicesComponent,AddEditProductsAndServicesComponent,
        PlatformMasterComponent,AddEditPlatformMasterComponent, TermasAndConditionsComponent, AddEditTermsAndConditionsComponent, ViewBuyerPreApplicationComponent
        
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        PopoverModule.forRoot(),
        PaginationModule.forRoot(),
        GlobalModule,
        PipeModule,
        CropdataPortalRoutingModule,
        MatSortModule,
        MatCheckboxModule,
        MatSelectModule,
        DragDropModule,
        CommonModule,
        RegionalRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        GlobalModule,
        PipeModule,
        PaginationModule.forRoot(),
        MatSortModule,
        MatCheckboxModule,
        MatFormFieldModule,
        MatInputModule,
        MatDatepickerModule,
        A11yModule,
        CdkStepperModule,
        CdkTableModule,
        CdkTreeModule,
        DragDropModule,
        MatAutocompleteModule,
        MatBadgeModule,
        MatBottomSheetModule,
        MatButtonModule,
        MatButtonToggleModule,
        MatCardModule,
        MatCheckboxModule,
        MatChipsModule,
        MatStepperModule,
        MatDatepickerModule,
        MatDialogModule,
        MatDividerModule,
        MatExpansionModule,
        MatGridListModule,
        MatIconModule,
        MatInputModule,
        MatListModule,
        MatMenuModule,
        MatNativeDateModule,
        MatPaginatorModule,
        MatProgressBarModule,
        MatProgressSpinnerModule,
        MatRadioModule,
        MatRippleModule,
        MatSelectModule,
        MatSidenavModule,
        MatSliderModule,
        MatSlideToggleModule,
        MatSnackBarModule,
        MatSortModule,
        MatTableModule,
        MatTabsModule,
        MatToolbarModule,
        MatTooltipModule,
        MatTreeModule,
        PortalModule,
        ScrollingModule,
        MomentDateModule,
        AutocompleteLibModule
    ],
    providers: [
      { provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS }
    ],
})

export class CropdataPortalModule { }

import { HarvestMonotoringComponent } from './components/call-center-tele-caller/dr-krishi-cctc/technical-cctc/harvest-monitoring/harvest-monotoring.component';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule , ReactiveFormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common'
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SessionInterceptor } from './session.interceptor'

// Pluggins
import { MalihuScrollbarModule } from 'ngx-malihu-scrollbar';
import { DatepickerModule, BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
// import { AmazingTimePickerModule } from 'amazing-time-picker';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import {NgxPaginationModule} from 'ngx-pagination';
import { SlickCarouselModule } from 'ngx-slick-carousel';
import { ModalModule, BsModalRef } from 'ngx-bootstrap/modal';
import {TranslateModule} from '@ngx-translate/core';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { DataTablesModule } from 'angular-datatables';


// Common Components
import { HeaderComponent } from './components/header/header.component';
import { SidemenuComponent } from './components/sidemenu/sidemenu.component';
import { FooterComponent } from './components/footer/footer.component';

// Password management Module
import { UserLoginComponent } from './components/password-management/user-login/user-login.component';
import { ForgotPasswordComponent } from './components/password-management/forgot-password/forgot-password.component';
import { OtpVerificationComponent } from './components/password-management/otp-verification/otp-verification.component';
import { ChangePasswordComponent } from './components/password-management/change-password/change-password.component';
import { LoginService } from './components/password-management/user-login/login.service';
import { ChangePasswordService } from './components/password-management/change-password/change-password-service';
import { ForgotPasswordService } from './components/password-management/forgot-password/forgot-password-service';

// User management Module

import { AddUserComponent } from './components/user-management/add-user/add-user.component';
import { ViewDetailsComponent } from './components/user-management/view-details/view-details.component';
import { EditUserComponent } from './components/user-management/edit-user/edit-user.component';
import { ListOfUsersComponent } from './components/user-management/list-of-users/list-of-users.component';


// PR Manager Module
import { AssignVillagesToPrsComponent } from './components/public-relations-manager/assign-villages-to-prs/assign-villages-to-prs.component';
import { AssignmentListComponent } from './components/public-relations-manager/assignment-list/assignment-list.component';
import { AssignVillagesService } from './components/public-relations-manager/assign-villages-to-prs/assign-villages.service';
import { AssignmentListService } from './components/public-relations-manager/assignment-list/assignment-list.service';

// Bootsrap Modal Components
import { SuccessModalComponent } from './components/modal-components/success-modal/success-modal.component';
import { FormModalComponent } from './components/modal-components/form-modal/form-modal.component';
import { DeleteModalComponent } from './components/modal-components/delete-modal/delete-modal.component';
import { AssignRoleModalComponent } from './components/modal-components/assign-role-modal/assign-role-modal.component';
import { DefaultModalComponent } from './components/modal-components/default-modal/default-modal.component';
import { ApproveWithCorrectionComponent } from './components/modal-components/approve-with-correction/approve-with-correction.component';
import { approveCorrectionService } from './components/modal-components/approve-with-correction/approve-with-correction.service';
import { AssignRoleRlmComponent } from './components/modal-components/assign-role-rlm/assign-role-rlm.component';
import { assignRoleRlmService } from './components/modal-components/assign-role-rlm/assign-role-rlm.service';
import { RecordingModalComponent } from './components/modal-components/recording-modal/recording-modal.component';
import { SessionExpireModalComponent } from './components/modal-components/session-expire-modal/session-expire-modal.component';
import { WarningModalComponent } from './components/modal-components/warning-modal/warning-modal.component';

// Regional Lab Manager (RLM) Module
import { RlmDashboardComponent } from './components/regional-lab-manager/rlm-dashboard/rlm-dashboard.component';
import { SampleDiagnosisApprovalListComponent } from './components/regional-lab-manager/approve-diagnosis/sample-diagnosis-approval-list/sample-diagnosis-approval-list.component';
import { ManageHardwareListComponent } from './components/regional-lab-manager/manage-hardware/manage-hardware-list/manage-hardware-list.component';
import { MonitorCashListComponent } from './components/regional-lab-manager/monitor-cash/monitor-cash-list/monitor-cash-list.component';
import { sampleDiagnosisService } from './components/regional-lab-manager/approve-diagnosis/sample-diagnosis-approval-list/sample-diagnosis-approval-list.service';
import { ManageHardwareService } from './components/regional-lab-manager/manage-hardware/manage-hardware-list/manage-hardware.service';
import { MonitorCashService } from './components/regional-lab-manager/monitor-cash/monitor-cash-list/monitor-cash.service';
import { VehicleListComponent } from './components/regional-lab-manager/vehicle-master/vehicle-list/vehicle-list.component';
import { VehicleMasterService } from './components/regional-lab-manager/vehicle-master/vehicle-list/vehicle-master.service';
import { VehicleDetailsComponent } from './components/regional-lab-manager/vehicle-master/vehicle-details/vehicle-details.component';
import { vehicleDetailsService } from './components/regional-lab-manager/vehicle-master/vehicle-details/vehicle-details.service';
import { VehicleScheduleListComponent } from './components/regional-lab-manager/vehicle-schedule/vehicle-schedule-list/vehicle-schedule-list.component';
import { VehicleScheduleComponent } from './components/regional-lab-manager/vehicle-schedule/vehicle-schedule/vehicle-schedule.component';

// Regional Lab Tech (RLT) Module
import { RltSampleReceivedComponent } from './components/regional-lab-tech/sample-received/sample-received.component';
import { RltCaseDetailsComponent } from './components/regional-lab-tech/rlt-case-details/rlt-case-details.component';
import { RltSampleReceivedService } from './components/regional-lab-tech/sample-received/sample-received.service';
import { RltCaseDetailsService } from './components/regional-lab-tech/rlt-case-details/rlt-case-details.service';

// Call Center Tele-Caller (CCTC) Module
import { DrKrishiNonTechnicalListComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/non-technical-list/non-technical-list.component';
import { DrKrishiNonTechnicalService } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/non-technical-list/dr-krishi-non-technical.service';
import { NonTechnicalInformationFormComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/non-technical-information-form/non-technical-information-form.component';
import { DrKrishiNonNonTechnicalInformationFormService } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/non-technical-information-form/non-technical-information.service';
import { TechnicalListComponent } from './components/call-center-tele-caller/dr-krishi-cctc/technical-cctc/technical-list/technical-list.component';
import { DrKrishiTechnicalService } from './components/call-center-tele-caller/dr-krishi-cctc/technical-cctc/technical-list/dr-krishi-technical.service';
import { TechnicalFormComponent } from './components/call-center-tele-caller/dr-krishi-cctc/technical-cctc/technical-form/technical-form.component';
import { DrKrishiTechnicalFormService } from './components/call-center-tele-caller/dr-krishi-cctc/technical-cctc/technical-form/technical-form.service';

// Central Lab QA Analyst (CLQAA) Module
import { QualityAssuranceListComponent } from './components/central-lab-qa-analyst/cl-kml-qa/quality-assurance-list/quality-assurance-list.component';
import { QualityAssuranceListService } from './components/central-lab-qa-analyst/cl-kml-qa/quality-assurance-list/quality-assurance-list.service';
import {
  KmlDetailsComponent,
  SafePipe
} from './components/central-lab-qa-analyst/cl-kml-qa/kml-details/kml-details.component';
import { KmlDetailsService } from './components/central-lab-qa-analyst/cl-kml-qa/kml-details/kml-details.service';
import { KycListComponent } from './components/central-lab-qa-analyst/cl-kyc/kyc-list/kyc-list.component';
import { IndividualKycDetailsComponent } from './components/central-lab-qa-analyst/cl-kyc/individual-kyc-details/individual-kyc-details.component';
import { KycListService } from './components/central-lab-qa-analyst/cl-kyc/kyc-list/kyc-list.service';
import { IndividualKycDetailsService } from './components/central-lab-qa-analyst/cl-kyc/individual-kyc-details/individual-kyc-details.service';
import { ImageQualityAssuranceListComponent } from './components/central-lab-qa-analyst/cl-image-qa/image-quality-assurance-list/image-quality-assurance-list.component';
import { ImageQualityAssuranceList } from './components/central-lab-qa-analyst/cl-image-qa/image-quality-assurance-list/image-quality-assurance-list.service';
import { ImageListComponent } from './components/central-lab-qa-analyst/cl-image-qa/image-list/image-list.component';
import { ClImageList } from './components/central-lab-qa-analyst/cl-image-qa/image-list/image-list.service';

// HTTP Loader
import { LoaderComponent } from './components/loader/loader.component';
import { LoaderInterceptorService } from './components/loader-interceptor.service';

// Field Lab Scout(FLS) Module
import { RightsListComponent } from './components/field-lab-scout/rights-screen/rights-list/rights-list.component';
import { RightsListService } from './components/field-lab-scout/rights-screen/rights-list/rights-list.service';
import { IncompleteComponent } from './components/call-center-tele-caller/dr-krishi-cctc/technical-cctc/incomplete/incomplete.component';
import {ForwardleadformComponent} from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/forward-lead-form/forward-lead-form.component';
import {SpotleadformComponent} from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/spot-lead-form/spot-lead-form.component';
import {SpotleadComponent} from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/spot-lead-list/spot-lead.component';
import {ForwardleadComponent} from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/forward-lead-list/forward-lead.component';

import { AgriotastatusComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/agriota-status-list/agriota-status.component';
import { AgriotastatusformComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/agriota-status-form/agriota-status-form.component';
import { HarvestconfirmationComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/harvest-confirmation-list/harvest-confirmation.component';
import { HarvestconfirmationformComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/harvest-confirmation-form/harvest-confirmation-form.component';
import { DeliveryconfirmationformComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/delivery-confirmation-form/delivery-confirmation-form.component';
import { DeliveryconfirmationComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/delivery-confirmation-list/delivery-confirmation.component';
import { DeliverycoordinationformComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/delivery-co-ordination-form/delivery-co-ordination-form.component';
import { DeliverycordinationComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/delivery-co-ordinate-list/delivery-co-ordination.component';
import { IncomingcallsformComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/incoming-calls-form/incoming-calls-form.component';
import { IncomingcallsComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/incoming-calls-list/incoming-calls.component';
import {HarvestMonitoringListComponent} from "./components/call-center-tele-caller/dr-krishi-cctc/technical-cctc/harvest-monitoring-list/harvest-monitoring-list.component";
import {AgriotaStatusListService} from "./components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/agriota-status-list/agriota-status-list.service";
import {HarvestConfirmationListService} from "./components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/harvest-confirmation-list/harvest-confirmation-list.service";
import { IncomingCallsService } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/incoming-calls-list/incoming-calls.service';
import { IncomingCallsFormService } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/incoming-calls-form/incoming-calls-form.service';
import { DeliveryCoordinationService } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/delivery-co-ordinate-list/delivery-co-ordination.service';
import { DeliveryCoordinationFormService } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/delivery-co-ordination-form/delivery-co-ordination-form.service';
import { DeliveryConfirmationService } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/delivery-confirmation-list/delivery-confirmation.service';
import { DeliveryConfirmationFormService } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/delivery-confirmation-form/delivery-confirmation-form.service';



@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SidemenuComponent,
    FooterComponent,
    FormModalComponent,
    UserLoginComponent,
    ForgotPasswordComponent,
    ChangePasswordComponent,
    OtpVerificationComponent,
    SafePipe,
    AddUserComponent,
    ViewDetailsComponent,
    EditUserComponent,
    ListOfUsersComponent,
    AssignVillagesToPrsComponent,
    AssignmentListComponent,
    AssignRoleModalComponent,
    DeleteModalComponent,
    SuccessModalComponent,
    DefaultModalComponent,
    ApproveWithCorrectionComponent,
    AssignRoleRlmComponent,
    RecordingModalComponent,
    RlmDashboardComponent,
    SampleDiagnosisApprovalListComponent,
    ManageHardwareListComponent,
    MonitorCashListComponent,
    RltSampleReceivedComponent,
    RltCaseDetailsComponent,
    SessionExpireModalComponent,
    WarningModalComponent,
    DrKrishiNonTechnicalListComponent,
    ForwardleadComponent,
    SpotleadComponent,
    QualityAssuranceListComponent,
    KmlDetailsComponent,
    NonTechnicalInformationFormComponent,
    ForwardleadformComponent,
    SpotleadformComponent,
    KycListComponent,
    IndividualKycDetailsComponent,
    TechnicalListComponent,
    ImageQualityAssuranceListComponent,
    ImageListComponent,
    TechnicalFormComponent,
    LoaderComponent,
    VehicleListComponent,
    VehicleDetailsComponent,
    VehicleScheduleListComponent,
    VehicleScheduleComponent,
    RightsListComponent,
    IncompleteComponent,
    AgriotastatusComponent,
    AgriotastatusformComponent,
    HarvestconfirmationComponent,
    HarvestconfirmationformComponent,
    DeliveryconfirmationformComponent,
    DeliveryconfirmationComponent,
    DeliverycoordinationformComponent,
    DeliverycordinationComponent,
    IncomingcallsformComponent,
    IncomingcallsComponent,
    HarvestMonotoringComponent,
    HarvestMonitoringListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    MalihuScrollbarModule.forRoot(),
    // AmazingTimePickerModule,
    ReactiveFormsModule,
    BsDatepickerModule.forRoot(),
    DatepickerModule.forRoot(),
    BrowserAnimationsModule,
    HttpClientModule,
    AccordionModule.forRoot(),
    SlickCarouselModule,
    ModalModule.forRoot(),
    TranslateModule.forRoot(),
    TabsModule.forRoot(),
    NgxPaginationModule,
    NgMultiSelectDropDownModule,
    TooltipModule.forRoot(),
    DataTablesModule
  ],
  entryComponents: [
    SuccessModalComponent,
    FormModalComponent,
    DeleteModalComponent,
    AssignRoleModalComponent,
    DefaultModalComponent,
    ApproveWithCorrectionComponent,
    AssignRoleRlmComponent,
    RecordingModalComponent,
    SessionExpireModalComponent,
    WarningModalComponent
  ],
  providers: [
    BsModalRef,
    DatePipe,
    LoginService,
    ChangePasswordService,
    ForgotPasswordService,
    AssignVillagesService,
    AssignmentListService,
    approveCorrectionService,
    assignRoleRlmService,
    sampleDiagnosisService,
    ManageHardwareService,
    MonitorCashService,
    RltSampleReceivedService,
    RltCaseDetailsService,
    DrKrishiNonTechnicalService,
    QualityAssuranceListService,
    KmlDetailsService,
    DrKrishiNonNonTechnicalInformationFormService,
    KycListService,
    IndividualKycDetailsService,
    DrKrishiTechnicalService,
    ImageQualityAssuranceList,
    ClImageList,
    DrKrishiTechnicalFormService,
    VehicleMasterService,
    AgriotaStatusListService,
    HarvestConfirmationListService,
    vehicleDetailsService,
    RightsListService,
    IncomingCallsService,
    IncomingCallsFormService,
    DeliveryCoordinationService,
    DeliveryCoordinationFormService,
    DeliveryConfirmationService,
    DeliveryConfirmationFormService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LoaderInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

import { HarvestMonotoringComponent } from './components/call-center-tele-caller/dr-krishi-cctc/technical-cctc/harvest-monitoring/harvest-monotoring.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './auth.guard';

// Password management Module
import { UserLoginComponent } from './components/password-management/user-login/user-login.component';
import { ForgotPasswordComponent } from './components/password-management/forgot-password/forgot-password.component';
import { OtpVerificationComponent } from './components/password-management/otp-verification/otp-verification.component';
import { ChangePasswordComponent } from './components/password-management/change-password/change-password.component';

// User management Module
import { ListOfUsersComponent } from './components/user-management/list-of-users/list-of-users.component';
import { AddUserComponent } from './components/user-management/add-user/add-user.component';
import { ViewDetailsComponent } from './components/user-management/view-details/view-details.component';
import { EditUserComponent } from './components/user-management/edit-user/edit-user.component';

// PR Manager Module
import { AssignVillagesToPrsComponent } from './components/public-relations-manager/assign-villages-to-prs/assign-villages-to-prs.component';
import { AssignmentListComponent } from './components/public-relations-manager/assignment-list/assignment-list.component';

// Regional Lab Manager (RLM) Module
import { RlmDashboardComponent } from './components/regional-lab-manager/rlm-dashboard/rlm-dashboard.component';
import { SampleDiagnosisApprovalListComponent } from './components/regional-lab-manager/approve-diagnosis/sample-diagnosis-approval-list/sample-diagnosis-approval-list.component';
import { ManageHardwareListComponent } from './components/regional-lab-manager/manage-hardware/manage-hardware-list/manage-hardware-list.component';
import { MonitorCashListComponent } from './components/regional-lab-manager/monitor-cash/monitor-cash-list/monitor-cash-list.component';
import { VehicleListComponent } from './components/regional-lab-manager/vehicle-master/vehicle-list/vehicle-list.component';
import { VehicleDetailsComponent } from './components/regional-lab-manager/vehicle-master/vehicle-details/vehicle-details.component';
import { VehicleScheduleListComponent } from './components/regional-lab-manager/vehicle-schedule/vehicle-schedule-list/vehicle-schedule-list.component';
import { VehicleScheduleComponent } from './components/regional-lab-manager/vehicle-schedule/vehicle-schedule/vehicle-schedule.component';

// Regional Lab Tech (RLT) Module
import { RltSampleReceivedComponent } from './components/regional-lab-tech/sample-received/sample-received.component';
import { RltCaseDetailsComponent } from './components/regional-lab-tech/rlt-case-details/rlt-case-details.component';

// Call Center Tele-Caller (CCTC) Module
import { DrKrishiNonTechnicalListComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/non-technical-list/non-technical-list.component';
import { NonTechnicalInformationFormComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/non-technical-information-form/non-technical-information-form.component';
import { TechnicalListComponent } from './components/call-center-tele-caller/dr-krishi-cctc/technical-cctc/technical-list/technical-list.component';
import { TechnicalFormComponent } from './components/call-center-tele-caller/dr-krishi-cctc/technical-cctc/technical-form/technical-form.component';

// Central Lab QA Analyst (CLQAA) Module
import { QualityAssuranceListComponent } from './components/central-lab-qa-analyst/cl-kml-qa/quality-assurance-list/quality-assurance-list.component';
import { KmlDetailsComponent } from './components/central-lab-qa-analyst/cl-kml-qa/kml-details/kml-details.component';
import { KycListComponent } from './components/central-lab-qa-analyst/cl-kyc/kyc-list/kyc-list.component';
import { IndividualKycDetailsComponent } from './components/central-lab-qa-analyst/cl-kyc/individual-kyc-details/individual-kyc-details.component';
import { ImageQualityAssuranceListComponent } from './components/central-lab-qa-analyst/cl-image-qa/image-quality-assurance-list/image-quality-assurance-list.component';
import { ImageListComponent } from './components/central-lab-qa-analyst/cl-image-qa/image-list/image-list.component';

// Field Lab Scout(FLS) Module
import { RightsListComponent } from './components/field-lab-scout/rights-screen/rights-list/rights-list.component';
import { IncompleteComponent } from './components/call-center-tele-caller/dr-krishi-cctc/technical-cctc/incomplete/incomplete.component';

import { ForwardleadComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/forward-lead-list/forward-lead.component';
import { SpotleadComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/spot-lead-list/spot-lead.component';
import { SpotleadformComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/spot-lead-form/spot-lead-form.component';
import { ForwardleadformComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/forward-lead-form/forward-lead-form.component';
import { AgriotastatusComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/agriota-status-list/agriota-status.component';
import { AgriotastatusformComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/agriota-status-form/agriota-status-form.component';
import { HarvestconfirmationComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/harvest-confirmation-list/harvest-confirmation.component';
import { HarvestconfirmationformComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/harvest-confirmation-form/harvest-confirmation-form.component';
import { DeliveryconfirmationComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/delivery-confirmation-list/delivery-confirmation.component';
import { DeliveryconfirmationformComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/delivery-confirmation-form/delivery-confirmation-form.component';
import { DeliverycoordinationformComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/delivery-co-ordination-form/delivery-co-ordination-form.component';
import { DeliverycordinationComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/delivery-co-ordinate-list/delivery-co-ordination.component';
import { IncomingcallsComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/incoming-calls-list/incoming-calls.component';
import { IncomingcallsformComponent } from './components/call-center-tele-caller/dr-krishi-cctc/non-technical-cctc/incoming-calls-form/incoming-calls-form.component';
import {HarvestMonitoringListComponent} from './components/call-center-tele-caller/dr-krishi-cctc/technical-cctc/harvest-monitoring-list/harvest-monitoring-list.component';


const routes: Routes = [

  //{ path: '', component: IncompleteComponent },
  { path: '', component: UserLoginComponent },
  { path: 'login', component: UserLoginComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent},
  { path: 'otp-verification', component: OtpVerificationComponent, canActivate:[AuthGuard] },
  { path: 'change-password', component: ChangePasswordComponent, canActivate:[AuthGuard] },
  { path: 'list-of-users', component: ListOfUsersComponent, canActivate:[AuthGuard] },
  { path: 'add-user', component: AddUserComponent, canActivate:[AuthGuard] },
  { path: 'view-user-details', component: ViewDetailsComponent , canActivate:[AuthGuard]},
  { path: 'edit-user', component: EditUserComponent , canActivate:[AuthGuard]},

  { path: 'assign-villages-to-prs/:week', component: AssignVillagesToPrsComponent , canActivate:[AuthGuard]},
  { path: 'assignment-list', component: AssignmentListComponent , canActivate:[AuthGuard]},

  { path: 'rlm-dashboard', component: RlmDashboardComponent, canActivate:[AuthGuard] },
  { path: 'sample-diagnosis-approval-list', component: SampleDiagnosisApprovalListComponent, canActivate:[AuthGuard] },
  { path: 'diagnose/:taskId/:flag', component: RltCaseDetailsComponent, canActivate:[AuthGuard]},
  { path: 'manage-hardware-list', component: ManageHardwareListComponent , canActivate:[AuthGuard]},
  { path: 'monitor-cash-list', component: MonitorCashListComponent, canActivate:[AuthGuard] },
  { path: 'vehicle-master', component: VehicleListComponent, canActivate:[AuthGuard]},
  { path: 'vehicle-details/:id', component: VehicleDetailsComponent, canActivate:[AuthGuard]},
  { path: 'vehicle-details', component: VehicleDetailsComponent, canActivate:[AuthGuard]},
  { path: 'rlt-samples', component: RltSampleReceivedComponent, canActivate:[AuthGuard] },
  { path: 'diagnose/:taskId', component: RltCaseDetailsComponent, canActivate:[AuthGuard]},
  { path: 'vehicle-schedule-list', component: VehicleScheduleListComponent, canActivate:[AuthGuard]},
  { path: 'vehicle-schedule/:date', component: VehicleScheduleComponent, canActivate:[AuthGuard]},

  { path: 'dr-krishi-non-technical-list', component: DrKrishiNonTechnicalListComponent, canActivate:[AuthGuard] },
  { path: 'dr-krishi-non-technical-forward-list', component: ForwardleadComponent, canActivate:[AuthGuard] },
  { path: 'dr-krishi-non-technical-spot-list', component: SpotleadComponent},
  { path: 'dr-krishi-non-technical-information-form/:taskId', component: NonTechnicalInformationFormComponent, canActivate:[AuthGuard] },
  { path: 'dr-krishi-non-technical-forward-lead-form/:taskId', component: ForwardleadformComponent, canActivate:[AuthGuard] },
  { path: 'dr-krishi-non-technical-spot-lead-form/:taskId', component: SpotleadformComponent, canActivate:[AuthGuard] },
  { path: 'dr-krishi-technical-list', component: TechnicalListComponent, canActivate:[AuthGuard]  },
  { path: 'dr-krishi-technical-form/:taskId', component: TechnicalFormComponent, canActivate:[AuthGuard]},
  { path: 'dr-krishi-incomplete-data/:taskId', component: IncompleteComponent, canActivate:[AuthGuard]},

  { path: 'kml-quality-assurance-list', component: QualityAssuranceListComponent, canActivate:[AuthGuard] },
  { path: 'kml-details/:taskId', component: KmlDetailsComponent, canActivate:[AuthGuard] },
  { path: 'kyc-list', component: KycListComponent, canActivate:[AuthGuard] },
  { path: 'individual-kyc-details/:farmerId/:taskId', component: IndividualKycDetailsComponent, canActivate:[AuthGuard] },
  { path: 'image-quality-assurance-list', component: ImageQualityAssuranceListComponent, canActivate:[AuthGuard]},
  { path: 'image-list/:qaId/:commodityId/:stateId/:regionId/:commodityName/:stateName/:regionName', component: ImageListComponent, canActivate: [AuthGuard]},
  { path: 'image-list', component: ImageListComponent, canActivate:[AuthGuard]},

  { path: 'rights-list', component: RightsListComponent, canActivate:[AuthGuard]},

  { path: 'dr-krishi-non-technical-forward-list', component: ForwardleadComponent, canActivate:[AuthGuard] },
  { path: 'dr-krishi-non-technical-information-forward-lead-form/:taskId', component: ForwardleadformComponent, canActivate:[AuthGuard] },
  { path: 'dr-krishi-non-technical-spot-list', component: SpotleadComponent, canActivate:[AuthGuard] },
  { path: 'dr-krishi-non-technical-information-spot-lead-form/:taskId', component: SpotleadformComponent, canActivate:[AuthGuard] },


  { path: 'dr-krishi-non-technical-agriota-status-list', component: AgriotastatusComponent, canActivate:[AuthGuard] },
  { path: 'dr-krishi-non-technical-information-agriota-status-form/:taskId', component: AgriotastatusformComponent, canActivate:[AuthGuard] },

  { path: 'dr-krishi-technical-harvest-monitoring-list', component: HarvestMonitoringListComponent, canActivate:[AuthGuard] },
  { path: 'dr-krishi-technical-harvest-monitoring/:taskId', component: HarvestMonotoringComponent, canActivate:[AuthGuard] },

  { path: 'dr-krishi-non-technical-deliver-confirm-list', component: DeliveryconfirmationComponent, canActivate:[AuthGuard] },
  { path: 'dr-krishi-non-technical-information-deliver-confirm-form/:taskId', component: DeliveryconfirmationformComponent, canActivate:[AuthGuard] },

  { path: 'dr-krishi-non-technical-delivery-co-ordinat', component: DeliverycordinationComponent, canActivate:[AuthGuard] },
  { path: 'dr-krishi-non-technical-information-delivery-co-ordinat-form/:taskId', component: DeliverycoordinationformComponent, canActivate:[AuthGuard] },

  { path: 'dr-krishi-non-technical-incoming-calls-list', component: IncomingcallsComponent, canActivate:[AuthGuard] },
  { path: 'dr-krishi-non-technical-information-incoming-calls-form/:taskId', component: IncomingcallsformComponent, canActivate:[AuthGuard] },

  { path: 'dr-krishi-non-technical-harvest-confirm-list', component: HarvestconfirmationComponent, canActivate:[AuthGuard] },
  { path: 'dr-krishi-non-technical-information-harvest-confirm-form/:taskId', component: HarvestconfirmationformComponent, canActivate:[AuthGuard] },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

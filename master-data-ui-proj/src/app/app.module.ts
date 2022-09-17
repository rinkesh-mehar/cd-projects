import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ErrorHandler } from '@angular/core';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { PerfectScrollbarConfigInterface } from 'ngx-perfect-scrollbar';

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true
};

import { AppComponent } from './app.component';

// Import containers
import { DefaultLayoutComponent } from './containers';

import { P404Component } from './views/error/404.component';
import { P500Component } from './views/error/500.component';
import { LoginComponent } from './views/login/login.component';
import { JwtInterceptor } from './views/services/jwt.interceptor';
import { ErrorInterceptor } from './views/services/error.interceptor';
import { LoaderComponent } from './loader/loader.component';


const APP_CONTAINERS = [
  DefaultLayoutComponent
];

import {
  AppAsideModule,
  AppBreadcrumbModule,
  AppHeaderModule,
  AppFooterModule,
  AppSidebarModule,
} from '@coreui/angular';

// Import routing module
import { AppRoutingModule } from './app.routing';

// Import 3rd party components
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { ChartsModule } from 'ng2-charts';
import { ForgotPasswordComponent } from './views/forgot-password/forgot-password.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CustomeErrorComponent } from './views/error/custome-error/custome-error.component';
import { AlertService } from './views/error/alert.service';
import { AlertComponentComponent } from './views/error/alert-component/alert-component.component';
import { ModalModule } from 'ngx-bootstrap/modal';
import { Globalerrorhandler } from './globalerrorhandler';
import { MaintenanceComponent } from './maintenance/maintenance.component';
import {MatButtonModule, MatCheckboxModule, MatInputModule, MatSelectModule, MatStepperModule} from '@angular/material';
import {GlobalModule} from './views/global/global.module';
import {SelectCheckAllComponent} from './views/logistic-hub/logistic-hub-registration/select-check-all/select-check-all.component';






@NgModule({
  imports: [
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppAsideModule,
    AppBreadcrumbModule.forRoot(),
    AppFooterModule,
    AppHeaderModule,
    AppSidebarModule,
    PerfectScrollbarModule,
    BsDropdownModule.forRoot(),
    TabsModule.forRoot(),
    ChartsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    ModalModule.forRoot(),
    MatStepperModule,
    MatSelectModule,
    MatCheckboxModule,
    MatInputModule,
    MatButtonModule,
    GlobalModule,

  ],
  declarations: [
    AppComponent,
    APP_CONTAINERS,
    AlertComponentComponent,
    P404Component,
    P500Component,
    LoginComponent,
    ForgotPasswordComponent,
    CustomeErrorComponent,
    LoaderComponent,
    MaintenanceComponent
  ],
  providers: [
    { provide: ErrorHandler, useClass: Globalerrorhandler },
    { provide: LocationStrategy, useClass: HashLocationStrategy },
    // {  provide:HTTP_INTERCEPTORS, useClass:BasicAuthHtppInterceptorService, multi:true },
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }, AlertService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

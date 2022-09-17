import { ManualInterventionModule } from './views/manula-intervention/manual-intervention.module';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Import Containers
import { DefaultLayoutComponent } from './containers';

import { P404Component } from './views/error/404.component';
import { P500Component } from './views/error/500.component';
import { LoginComponent } from './views/login/login.component';
import { ForgotPasswordComponent } from './views/forgot-password/forgot-password.component';
// import { ProfileComponent } from './views/profile/profile.component';
import { AuthGuard } from './views/services/auth.guard';
import { CustomeErrorComponent } from './views/error/custome-error/custome-error.component';
import { MaintenanceComponent } from './maintenance/maintenance.component';
import {LogisticRegistrationComponent} from './views/logistic-hub/logistic-hub-registration/logistic-registration.component';
import {OperationModule} from './views/operation/operation.module';
// new rotes
export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  // {
  //   path: '',
  //   redirectTo: 'maintenance',
  //   pathMatch: 'full',
  // },
  {
    path: '404',
    component: P404Component,
    data: {
      title: 'Page 404'
    }
  },
  {
    path: '500',
    component: P500Component,
    data: {
      title: 'Page 500'
    }
  },
  {
    path: 'error/:id',
    component: CustomeErrorComponent,
    data: {
      title: 'Error Page'
    }
  },
  {
    path: 'login',
    component: LoginComponent,
    data: {
      title: 'Login Page'
    }
  },
  {
    path: 'maintenance',
    component: MaintenanceComponent,
    data: {
      title: 'Maintenance Page'
    }
  },
  
  {
    path: 'forgot-password',
    component: ForgotPasswordComponent,
    data: {
      title: 'Forgot Password'
    }
  },
  // {
  //   path: 'profile',
  //   component: ProfileComponent,
  //   canActivate: [AuthGuard] ,
  //   data: {
  //     title: 'User Profile'
  //   }
  // },
  {
    path: '',
    component: DefaultLayoutComponent,
    data: {
      title: 'Home'
    },
    children: [
      {
        path: 'acl',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/acl/acl.module').then(m => m.AclModule)
      },
      {
        path: 'agri',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/agri/agri.module').then(m => m.AgriModule)
      },
      {
        path: 'geo',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/geo/geo.module').then(m => m.GeoModule)
      },
      {
        path: 'regional',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/regional/regional.module').then(m => m.RegionalModule)
      },
      {
        path: 'dashboard',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/dashboard/dashboard.module').then(m => m.DashboardModule)
      },
      {
        path: 'operation',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/operation/operation.module').then(m => m.OperationModule)
      },
      {
        path: 'general',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/general/general.module').then(m => m.GeneralModule)
      },
      {
        path: 'farmer',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/farmer/farmer.module').then(m => m.FarmerModule)
      },
      {
        path: 'cropcal',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/cropcal/cropcalendar.module').then(m => m.CropCalendarModule)
      },
      {
        path: 'cluster',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/cluster/cluster.module').then(m => m.ClusterModule)
      },
      {
        path: 'flipbook',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/flipbook/flipbook.module').then(m => m.FlipbookModule)
      },
      {
        path: 'weather',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/weather/weather.module').then(m => m.WeatherModule)
      },
      {
        path: 'phenophase',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/phenophase/phenophase.module').then(m => m.PhenophaseModule)
      },
      {
        path: 'agrochemicals',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/agrochemicals/agrochemicals.module').then(m => m.AgrochemicalsModule)
      },
      {
        path: 'yield',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/yield/yield.module').then(m => m.YieldModule)
      },
      {
        path: 'commodity',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/commodity/commodity.module').then(m => m.CommodityModule)
      },
      {
        path: 'stress',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/stress/stress.module').then(m => m.StressModule)
      },
      {
        path: 'warehouse',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/warehouse/warehouse.module').then(m => m.WarehouseModule)
      },
      {
        path: 'ydc',
        canActivate: [AuthGuard] ,
        loadChildren: () => import('./views/ydc/ydc.module').then(m => m.YDCModule)
      },
      {
        path: 'notification',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/notifications/notification.module').then(m => m.NotificationModule)
      },
      {
        path: 'reports',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/dashboard-module/dashboard-module.module').then(m => m.DashboardModuleModule)
      },
      {
        path: 'pricing',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/pricing/pricing.module').then(m => m.PricingModule)
      },
      {
        path: 'cropdata-portal',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/cropdata-portal/cropdata-portal.module').then(m => m.CropdataPortalModule)
      },
      {
        path: 'tickers',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/tickers/tickers.module').then(m => m.tickersModule)
      },
      {
        path: 'mobile',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/mobile/mobile.module').then(m => m.MobileModule)
      },
      {
        path: 'gstm-eye',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/gstm-eye/gstm-eye.module').then(m => m.GstmEyeModule)
      },
      {
        path: 'models',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/models/cdt-modules.module').then(m => m.ModulesModule)

      },
      {
        path: 'rl',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/manage-rl/rl.module').then(m => m.RlModule)

      },
      {
        path: 'logistic-hub',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/logistic-hub/logistic-hub.module').then(m => m.LogisticHubModule)
      },
      {
        path: 'azure-devops',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/azure-devops/azure-devops.module').then(m => m.AzureDevopsModule)
      },
      {
        path: 'zonal',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/zonal/zonal.module').then(m => m.ZonalModule)
      },
      {
        path: 'region',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/region/region.module').then(m => m.RegionModule)
      },
      {
        path: 'config',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/config/config.module').then(m => m.ConfigModule)
      },
      {
        path: 'data-entry',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/data-entry/dataentry.module').then(m => m.DataEntryModule)
      },
      {
        path: 'manage-cl',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/manage-cl/cl.module').then(m => m.ClModule)
      },
      {
        path: 'employee',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/employee/employee.module').then(m => m.EmployeeModule)
      },
      {
        path: 'manual-intervention',
        canActivate: [AuthGuard],
        loadChildren: () => import('./views/manula-intervention/manual-intervention.module').then(m => m.ManualInterventionModule)
      }
      
    /*  {
        path: 'logistic-hub/lh-hub-registration',
        canActivate: [AuthGuard],
        component: LogisticRegistrationComponent
      }*/
    ]
  },
  { path: '**', component: P404Component }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    onSameUrlNavigation: 'reload'
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }

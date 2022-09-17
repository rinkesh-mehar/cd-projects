import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserKycComponent } from './user-kyc/user-kyc.component';
import { UserKycApprovalComponent } from './user-kyc/user-kyc-approval/user-kyc-approval.component';
import { UsersComponent } from './users/users.component';
import { BookingComponent } from './booking/booking.component';
import { BuyerSupportComponent } from './buyer-support/buyer-support.component';
import { EnquiriesComponent } from './enquiries/enquiries.component';
import { WarehousesComponent } from './warehouses/warehouses.component';
import { BuyerSupportDetailsComponent } from './buyer-support/buyer-support-details/buyer-support-details.component';
import { SendResponseComponent } from './enquiries/send-response/send-response.component';
import { UserDetailsComponent } from './users/user-details/user-details.component';
import { WarehouseDetailsComponent } from './warehouses/warehouse-details/warehouse-details.component';
import { DetailsComponent } from './booking/details/details.component';
import { SlotsComponent } from './warehouses/slots/slots.component';
import { UsersRejectedComponent } from './users-rejected/users-rejected.component';
import { UserRejectedDetailsComponent } from './users-rejected/user-rejected-details/user-rejected-details.component';


const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Warehouse'
    },
    children: [
      {
        path: '',
        redirectTo: 'user-kyc'
      },
      {
        path: 'user-kyc',
        component: UserKycComponent,
        data: {
          title: 'User KYC Requests'
        }
      },
      {
        path: 'user-kyc-approval/:id',
        component: UserKycApprovalComponent,
        data: {
          title: 'User KYC Approval'
        }
      },

      {
        path: 'users',
        component: UsersComponent,
        data: {
          title: 'All Users'
        }
      },
      {
        path: 'user-details/:id',
        component: UserDetailsComponent,
        data: {
          title: 'User Details'
        }
      },
      {
        path: 'users-rejected',
        component: UsersRejectedComponent,
        data: {
          title: 'Rejected Users'
        }
      },
      {
        path: 'user-rejected-details/:id',
        component: UserRejectedDetailsComponent,
        data: {
          title: 'Users Rejected'
        }
      },

      {
        path: 'warehouse',
        component: WarehousesComponent,
        data: {
          title: 'All warehouse'
        }
      },
      {
        path: 'warehouse-details/:id',
        component: WarehouseDetailsComponent,
        data: {
          title: 'All warehouse'
        }
      },
      {
        path: 'slots/:id',
        component: SlotsComponent,
        data: {
          title: 'All warehouse'
        }
      },
      {
        path: 'booking',
        component: BookingComponent,
        data: {
          title: 'All Booking List'
        }
      },
      {
        path: 'booking/:id',
        component: BookingComponent,
        data: {
          title: 'All Booking List'
        }
      },
      {
        path: 'booking-details/:id',
        component: DetailsComponent,
        data: {
          title: 'Booking Details'
        }
      },
      {
        path: 'support',
        component: BuyerSupportComponent,
        data: {
          title: 'Buyer Support'
        }
      },
      {
        path: 'support-details/:id',
        component: BuyerSupportDetailsComponent,
        data: {
          title: 'Buyer Support Details'
        }
      }, {
        path: 'enquiries',
        component: EnquiriesComponent,
        data: {
          title: 'Enquiries'
        }
      },
      {
        path: 'enquiries-send-response/:id',
        component: SendResponseComponent,
        data: {
          title: 'Enquiries Send Response'
        }
      },


    ]
  }
];



@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WarehouseRoutingModule { }

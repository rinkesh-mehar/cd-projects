import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserKycComponent } from './user-kyc/user-kyc.component';
import { UserKycApprovalComponent } from './user-kyc/user-kyc-approval/user-kyc-approval.component';
import { WarehouseRoutingModule } from './warehouse-routing.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { UsersComponent } from './users/users.component';
import { WarehousesComponent } from './warehouses/warehouses.component';
import { BookingComponent } from './booking/booking.component';
import { BuyerSupportComponent } from './buyer-support/buyer-support.component';
import { EnquiriesComponent } from './enquiries/enquiries.component';
import { BuyerSupportDetailsComponent } from './buyer-support/buyer-support-details/buyer-support-details.component';
import { SendResponseComponent } from './enquiries/send-response/send-response.component';
import { UserDetailsComponent } from './users/user-details/user-details.component';
import { WarehouseDetailsComponent } from './warehouses/warehouse-details/warehouse-details.component';
import { GlobalModule } from '../global/global.module';
import { DetailsComponent } from './booking/details/details.component';
import { SlotsComponent } from './warehouses/slots/slots.component';
import { UsersRejectedComponent } from './users-rejected/users-rejected.component';
import { UserRejectedDetailsComponent } from './users-rejected/user-rejected-details/user-rejected-details.component';



@NgModule({
  declarations: [UserKycComponent, UserKycApprovalComponent, UsersComponent, WarehousesComponent,
    BookingComponent, BuyerSupportComponent, EnquiriesComponent, BuyerSupportDetailsComponent, SendResponseComponent,
    UserDetailsComponent, WarehouseDetailsComponent, DetailsComponent, SlotsComponent,
    UsersRejectedComponent, UserRejectedDetailsComponent],
  imports: [
    CommonModule,
    WarehouseRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    GlobalModule
  ]
})
export class WarehouseModule { }

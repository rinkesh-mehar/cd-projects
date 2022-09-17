import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { NotificationComponent } from './notification/notification.component';
import { NotificationRoutingModule } from './notification-routing.module';
import { ListNotificationComponent } from './list-notification/list-notification.component';



@NgModule({
  declarations: [
    NotificationComponent,
    ListNotificationComponent
  ],
  imports: [
    CommonModule,
    NotificationRoutingModule,
    FormsModule,
    ReactiveFormsModule,
  ]
})
export class NotificationModule { }

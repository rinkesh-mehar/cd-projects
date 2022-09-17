import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NotificationComponent } from './notification/notification.component';
import { ListNotificationComponent } from './list-notification/list-notification.component';


const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Notification'
    },
    children: [
      {
        path: 'notification-details/:id',
        component: NotificationComponent,
        data: {
          title: 'Notification'
        }
      },
      {
        path: 'get-all-notification',
        component: ListNotificationComponent,
        data: {
          title: 'Notification List'
        }
      },
    ]
  }
];



@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NotificationRoutingModule { }

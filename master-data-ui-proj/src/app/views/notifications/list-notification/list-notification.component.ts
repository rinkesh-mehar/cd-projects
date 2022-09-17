import { Component, OnInit } from '@angular/core';
import { NotificationService } from '../service/notification.service';
import { Notification } from '../models/notification';
import { AuthenticationService } from '../../services/authentication.service';
import { User } from '../../acl/Models/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-notification',
  templateUrl: './list-notification.component.html',
  styleUrls: ['./list-notification.component.scss']
})
export class ListNotificationComponent implements OnInit {


  notification: Notification;
  currentUser: User;
  constructor(
    private notificationService: NotificationService,
    private authenticationService: AuthenticationService,
    private router: Router
    ) {
      this.authenticationService.currentUser.subscribe(x => {
        this.currentUser = x;
      });
    }

  ngOnInit() {
    this.notificationService.getAllNotification(this.currentUser.id).subscribe(data => {
      this.notification = data;
      console.log(data);
    }, error => {
      console.log('error : ' + error);
    });
  }

  notificationDetails(id: number) {
    this.router.navigate(['/notification/notification-details', id]);
  }

}

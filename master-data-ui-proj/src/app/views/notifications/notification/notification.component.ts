import { Component, OnInit } from '@angular/core';
import { NotificationService } from '../service/notification.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Notification } from '../models/notification';
import { NgIf } from '@angular/common';
import { AuthenticationService } from '../../services/authentication.service';
import { User } from '../../acl/Models/user';


@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnInit {

  id: number;
  notification: Notification;
  currentUser: User;
  res: any;
  viewStatus: any;


  constructor(private route: ActivatedRoute,
    private router: Router,
    private notificationService: NotificationService,
    private authenticationService: AuthenticationService) {
      this.authenticationService.currentUser.subscribe(x => {
        this.currentUser = x;
      });
     }

  ngOnInit() {
    this.notification = new Notification();
    this.id = this.route.snapshot.params['id'];
        this.notificationService.getNotificationById(this.id).subscribe(data => {
          // console.log(data);
          this.notification  = data;
        }, error => {
          console.log(error);
        }),
    this.notificationService.getAllNotificationsByUserId(this.currentUser.id).subscribe(data => {
      this.viewStatus = data;
      if (this.viewStatus[0].viewStatus == 0) {
         this.notificationService.updateViewNotificationStatus(this.currentUser.id, this.id).subscribe(data => {
        this.res = data;
        console.log('res = : ' + this.res);
        location.reload();
      }, error => {
        console.log('error =' + error);
      });
      }
    }, error => {
      console.log('error ' + error);
    });
  }
}

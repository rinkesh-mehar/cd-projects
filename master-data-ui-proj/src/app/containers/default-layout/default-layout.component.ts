import { NavData } from './../../_nav';
import { Component, OnDestroy, Inject, OnInit } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { navItems } from '../../_nav';
import { AuthenticationService } from '../../views/services/authentication.service';
import { Router } from '@angular/router';
import { User } from '../../views/acl/Models/user';
import { NotificationService } from '../../views/notifications/service/notification.service';
import { Notification } from '../../views/notifications/models/notification';
import { UserService } from '../../views/acl/services/user.service';


@Component({
  selector: 'app-dashboard',
  templateUrl: './default-layout.component.html'
})
export class DefaultLayoutComponent implements OnDestroy, OnInit {
  public navItems = navItems;
  public sidebarMinimized = true;
  private changes: MutationObserver;
  public element: HTMLElement;
  currentUser: User;
  notificationCount: any;
  notification: Notification;
  searchText: any = '';
  isSearchBtnHidden: boolean = false;
  isCloseBtnHidden: boolean = true;
  isSearchBtnDisabled: boolean = false;
  constructor(private notificationService: NotificationService,
    private authenticationService: AuthenticationService,
    private router: Router,
    private userService: UserService,
    @Inject(DOCUMENT) _document?: any,
  ) {

    this.changes = new MutationObserver((mutations) => {
      this.sidebarMinimized = _document.body.classList.contains('sidebar-minimized');
    });
    this.element = _document.body;
    this.changes.observe(<Element>this.element, {
      attributes: true,
      attributeFilter: ['class']
    });
    this.authenticationService.currentUser.subscribe(x => {
      this.currentUser = x;
      this.navItems = JSON.parse(sessionStorage.getItem('nav'));
    });
  }
  ngOnInit() {
    // this.notificationService.getNotficationCount(this.currentUser.id).subscribe(data => {
    //   this.notificationCount = data;
    //   console.log('notification count' + this.notificationCount);
    // });
    // this.notificationService.getAllNotificationsByUserId(this.currentUser.id).subscribe(data => {
    //   this.notification = data;
    //   console.log('All Notification with zero status', this.notification);
    // }, error => {
    //   console.log('erro' + error);
    // });
  }

  notificationDetails(id: number) {
    this.router.navigate(['/notification/notification-details', id]);
  }

  ngOnDestroy(): void {
    this.changes.disconnect();
  }

  logOut() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }

  getMenusByRoleAndSearchText(): void {
    this.userService.getMenusByRoleAndSearchText(this.currentUser.roleId,this.searchText)
        .subscribe(data => {
          
          let navMenu : NavData[] = [];
          for (let key in data) {
            let menuItem = data[key];
            for (let ckey in menuItem['children']) {
              let subMenuItem = menuItem['children'][ckey]
              delete subMenuItem['children']
            }
            navMenu.push(menuItem);
          }
          this.navItems = [];
          this.navItems = navMenu;
        });
  }

  searchMenu(){
    this.getMenusByRoleAndSearchText();
    this.isSearchBtnHidden = true;
    this.isCloseBtnHidden = false;
    this.isSearchBtnDisabled = true;
  }

  clearSearchMenu(){
    this.navItems = JSON.parse(sessionStorage.getItem('nav'));
    this.isSearchBtnHidden = false;
    this.isCloseBtnHidden = true;
    this.isSearchBtnDisabled = false;
  }
}

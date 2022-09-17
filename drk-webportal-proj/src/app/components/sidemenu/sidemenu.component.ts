import { Component, OnInit, ElementRef, Renderer } from '@angular/core';
import { Router } from '@angular/router';
import { LocationStrategy } from '@angular/common';
import { ListOfUsersService } from '../user-management/list-of-users/list-of-users.service';


@Component({
  selector: 'app-sidemenu',
  templateUrl: './sidemenu.component.html',
  styleUrls: ['./sidemenu.component.less']
})
export class SidemenuComponent implements OnInit {

  public showMenu: boolean = false;
  public dashboardLink: string;
  public activeClass: string;
  public userLink: string;
  public sideMenu: any;
  public reloadpages;
  public showSideMenu: boolean = true;
  searchText;


  constructor(
    public router: Router, private el: ElementRef, private renderer: Renderer,
    private location: LocationStrategy, private userService: ListOfUsersService
  ) {

    history.pushState(null, null, window.location.href);
    this.location.onPopState(() => {
      history.pushState(null, null, window.location.href);
    });

  }

  ngOnInit() {

    this.userService.checkUserStatus({ "userId": parseInt(localStorage.getItem("loginUserid")) }).subscribe(
      (data) => {

        if (!data) {
          localStorage.setItem("userLocked", "user is locked or Inactive");
          this.router.navigate(['/login']);
        }
      }, (err) => {
      });


    var is_forced_pwd_change = localStorage.getItem("is_forced_pwd_change") != null ? localStorage.getItem("is_forced_pwd_change") : "1";
    var passwordChangeMessage = localStorage.getItem("passwordChangeMessage") != null ? localStorage.getItem("passwordChangeMessage") : "1";

    if (parseInt(is_forced_pwd_change) === 0 || parseInt(passwordChangeMessage) === 0) {

    } else {

      this.sideMenu  = JSON.parse(localStorage.getItem('menuArray'))
      // this.sideMenu = JSON.parse(localStorage.getItem('menuArray'));
      // var length = this.sideMenu.length;
      // for (var i = 0; i < length; i++) {
      //   var obj = this.sideMenu[i];
      // }

      var homepageurls = ["/rlm-dashboard", "/list-of-users", "/assignment-list"];

      var index = document.URL.lastIndexOf("/");
      var spliturl = document.URL.substring(index, document.URL.length);


      var homeLink = homepageurls.includes(spliturl);

      this.dashboardLink = localStorage.getItem("roleUrl");

      if (document.URL.indexOf(this.dashboardLink) >= 0) {

        this.activeClass = "active";
      } else {
        this.activeClass = "";
      }


      if (localStorage.getItem("loginRoleName") === 'Regional Lab Manager') {
        this.showMenu = !this.showMenu;

      }

    }

    //Menu visibility

    if(this.router.url == '/rights-list'){
      this.showSideMenu = false;
    }

  }


  //Menu reload page
  menuItem() {
    //this.reloadpages = ["/assignment-list"];
    if(this.router.url == this.dashboardLink && this.router.url != '/rlm-dashboard'){
      this.router.routeReuseStrategy.shouldReuseRoute = function(){return false;};

      // let currentUrl = this.router.url + '?';

      console.log("currentUrl", this.router.url)

      this.router.navigateByUrl(this.router.url)
        .then(() => {
          this.router.navigated = false;
          this.router.navigate([this.router.url]);
        });
    }
    sessionStorage.removeItem("state");
    sessionStorage.removeItem("region");
    sessionStorage.removeItem("district");
    sessionStorage.removeItem("village");
    sessionStorage.removeItem("searchText");
    $('.datatable-stateSave').DataTable().state.clear();
  }

  //Menu reload page
  menuItem1(url) {
    //this.reloadpages = ["/assignment-list"];
    if(this.router.url == this.dashboardLink && this.router.url != '/rlm-dashboard'){
      this.router.routeReuseStrategy.shouldReuseRoute = function(){return false;};

      let currentUrl = url;

      console.log("currentUrl", currentUrl)

      this.router.navigateByUrl(currentUrl)
        .then(() => {
          this.router.navigated = false;
          this.router.navigate([currentUrl]);
        });
    }
    sessionStorage.removeItem("state");
    sessionStorage.removeItem("region");
    sessionStorage.removeItem("district");
    sessionStorage.removeItem("village");
    sessionStorage.removeItem("searchText");
    $('.datatable-stateSave').DataTable().state.clear();
  }


  changeColor(url) {

    if (document.URL.indexOf(url) >= 0) {

      return "active";
    } else {
      return "";
    }

  }

  homelink(url) {
    this.router.navigate([url]);
  }
  getActiveClass(event: any) {
    this.renderer.setElementClass(event.target, "active", true)
  }

}

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'


declare var $,c3, slick;

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.less']
})
export class HeaderComponent implements OnInit {
  
  loginUsername : string;
  loginRoleName : string;
  lastLoginTime : string;
  showFlyout: boolean;
  showMenu: boolean;

  constructor(private router:Router) { }

  ngOnInit() {
    this.showFlyout = false;
    this.loginUsername = localStorage.getItem("loginUsername");
    this.loginRoleName = localStorage.getItem("loginRoleName");
    this.lastLoginTime = localStorage.getItem("lastLoginTime");

    var is_forced_pwd_change = localStorage.getItem("is_forced_pwd_change") != null ? localStorage.getItem("is_forced_pwd_change") : "1";
    var passwordChangeMessage = localStorage.getItem("passwordChangeMessage") != null ? localStorage.getItem("passwordChangeMessage") : "1";

    if ( parseInt (is_forced_pwd_change) === 0 || parseInt (passwordChangeMessage) === 0 ) {
      this.showMenu = false;
    }else{
      this.showMenu = true;
    }
  }

  toggleMenu(){
    $("body").toggleClass("sidemenu-active");
    //$('.first').slick('refresh');

    setTimeout(() => {
      $('.carousel').slick('refresh');
    }, 100);

    }

  status: boolean = false;
  clickEvent(){
   
  }



  logout() {
    localStorage.clear()
    this.router.navigate(['/'])
    sessionStorage.clear();

  }


}

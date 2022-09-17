import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';


@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  modalRef: BsModalRef;


  constructor(
    private router:Router,
    private bsModalRef: BsModalRef,
  ) {}


  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
      try {
        if(next.data['roles'] != null && next.data['roles'].length > 0) {
          if(next.data['roles'].indexOf(localStorage.getItem('loginRoleName')) < 0) {
            //this.bsModalRef.hide();
            this.router.navigate(['/'])
            return false;
          }
        }
        if(localStorage.getItem("session") != null) {
          let time = JSON.parse(localStorage.getItem("session"))['st']
          if(time-500 > new Date().getTime()) {
            return true;
          } else {
            localStorage.clear();
            sessionStorage.clear();
            //this.bsModalRef.hide();
            this.router.navigate(['/'])
            return false;
          }
        } else {
          localStorage.clear();
          sessionStorage.clear();
          //this.bsModalRef.hide();
          this.router.navigate(['/'])
          return false;
        }
      } catch(err) {
        localStorage.clear();
        sessionStorage.clear();
        //this.bsModalRef.hide();
        this.router.navigate(['/'])
        return false;
      }
  }
  
}

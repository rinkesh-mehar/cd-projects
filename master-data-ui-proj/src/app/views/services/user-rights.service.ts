import { Injectable } from '@angular/core';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class UserRightsService {

  currentUser: any;

  constructor(private authenticationService: AuthenticationService) {
    this.currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
    this.authenticationService.currentUser.subscribe(user => {
      this.currentUser = user;
    });
  }

  canDelete() {
    return false;
  }

  canEdit() {
    return (this.currentUser.roleId == 1 || this.currentUser.roleId == 2 || this.currentUser.roleId == 3 || this.currentUser.roleId == 4
       || this.currentUser.roleId == 9)
  }

  canPrimaryApprove() {
    return (this.currentUser.roleId == 1 || this.currentUser.roleId == 3)
  }
  canFinalize() {
    return (this.currentUser.roleId == 1 || this.currentUser.roleId == 2)
  }

  canReject() {
    return (this.currentUser.roleId == 1 || this.currentUser.roleId == 2 || this.currentUser.roleId == 3 || this.currentUser.roleId == 9)
  }

  canClose() {
    return (this.currentUser.roleId == 1 || this.currentUser.roleId == 9);
  }

  canActive() {
    return (this.currentUser.roleId == 1 || this.currentUser.roleId == 9);
  }

  canDeactive() {
    return (this.currentUser.roleId == 1 || this.currentUser.roleId == 9);
  }

  canShortlist() {
    return (this.currentUser.roleId == 1 || this.currentUser.roleId == 9);
  }

  canHold() {
    return (this.currentUser.roleId == 1 || this.currentUser.roleId == 9);
  }

  canScheduleInterview() {
    return (this.currentUser.roleId == 1 || this.currentUser.roleId == 9);
  }

  canSelect() {
    return (this.currentUser.roleId == 1 || this.currentUser.roleId == 9);
  }

  canMoveToMaster() {
    return (this.currentUser.roleId == 1 || this.currentUser.roleId == 2 || this.currentUser.roleId == 3 || this.currentUser.roleId == 4
       || this.currentUser.roleId == 9)
  }

  canReactivateRegLink() {
    return (this.currentUser.roleId == 1 || this.currentUser.roleId == 9);
  }

  canDeleted() {
    return (this.currentUser.roleId == 1 || this.currentUser.roleId == 10);
  }

  canConvertToFrenchies() {
    return (this.currentUser.roleId == 1 || this.currentUser.roleId == 10);
  }

}

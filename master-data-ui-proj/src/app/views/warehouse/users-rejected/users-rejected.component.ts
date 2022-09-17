import { Component, OnInit } from '@angular/core';
import { UserKycService } from '../service/user-kyc.service';
import { UserRightsService } from '../../services/user-rights.service';

@Component({
  selector: 'app-users-rejected',
  templateUrl: './users-rejected.component.html',
  styleUrls: ['./users-rejected.component.scss']
})
export class UsersRejectedComponent implements OnInit {
  errorMessage: string;
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  userList: any = []
  constructor(public userService: UserKycService,
    private userRightsService: UserRightsService,
  ) { }

  ngOnInit() {

    this.userService.getRejectedUserList().subscribe(data => {
      this.userList = data.userData;
      if (data.userData === undefined) {
        this.errorMessage = 'No More Rejected User';
      }
    });
  }

}

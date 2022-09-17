import { Component, OnInit } from '@angular/core';
import { UserKycService } from '../service/user-kyc.service';
import { UserRightsService } from '../../services/user-rights.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  userList: any = []
  constructor(public userKycService: UserKycService,
    private userRightsService: UserRightsService,
  ) { }

  ngOnInit() {

    this.userKycService.getUserList().subscribe(data => {
      this.userList = data.userData;
    })
  }

}

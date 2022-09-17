import { Component, OnInit } from '@angular/core';
import { UserKycService } from '../service/user-kyc.service';
import { UserRightsService } from '../../services/user-rights.service';
import { GeoRegionService } from '../../geo/services/geo-region.service';

@Component({
  selector: 'app-user-kyc',
  templateUrl: './user-kyc.component.html',
  styleUrls: ['./user-kyc.component.scss']
})
export class UserKycComponent implements OnInit {

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  errorMsg: string;

  userList: any = []
  constructor(public userKycService: UserKycService,
    private userRightsService: UserRightsService,
    public geoRegionService: GeoRegionService,
  ) { }

  ngOnInit() {
    this.userKycService.getPendingKycUserList().subscribe(data => {
      this.userList = data.userData;
      if (this.userList === undefined) {
        this.errorMsg = data.message;
      }
    }, err => {
      console.log(err)
    })
  }

}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserKycService } from '../../service/user-kyc.service';
import { environment } from '../../../../../environments/environment';

@Component({
  selector: 'app-user-rejected-details',
  templateUrl: './user-rejected-details.component.html',
  styleUrls: ['./user-rejected-details.component.scss']
})
export class UserRejectedDetailsComponent implements OnInit {
  userId: any;
  userDetails: any = {};
  userKycImgUrl: any;
  constructor(public userKycService: UserKycService,
    private actRoute: ActivatedRoute) { }

  ngOnInit() {
    this.userKycImgUrl = environment.userKycImgUrl;
    this.userId = this.actRoute.snapshot.paramMap.get('id');

    if (this.userId) {
      this.userKycService.getUser(this.userId).subscribe(data => {

        this.userDetails = data.userData;
        this.userDetails.imageUrl = data.imageUrl;
      })
    }
  }




}

import { Component, OnInit } from '@angular/core';
import { UserRightsService } from '../../services/user-rights.service';
import { BuyerSupportService } from '../service/buyer-support.service';

@Component({
  selector: 'app-buyer-support',
  templateUrl: './buyer-support.component.html',
  styleUrls: ['./buyer-support.component.scss']
})
export class BuyerSupportComponent implements OnInit {

  buyerSupportList: any = []
  constructor(public buyerSupportService: BuyerSupportService,
    private userRightsService: UserRightsService,
  ) { }

  ngOnInit() {
    this.buyerSupportService.getSupportList().subscribe(data => {
      this.buyerSupportList = data.data;
    })

  }

}

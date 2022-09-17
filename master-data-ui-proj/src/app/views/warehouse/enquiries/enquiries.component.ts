import { Component, OnInit } from '@angular/core';
import { UserRightsService } from '../../services/user-rights.service';
import { EnquiriesService } from '../service/enquiries.service';

@Component({
  selector: 'app-enquiries',
  templateUrl: './enquiries.component.html',
  styleUrls: ['./enquiries.component.scss']
})
export class EnquiriesComponent implements OnInit {

  enquiriesList: any = []
  constructor(public enquiriesService: EnquiriesService,
    private userRightsService: UserRightsService,
  ) { }

  ngOnInit() {

    this.enquiriesService.getEnquiriesList().subscribe(data => {
      this.enquiriesList = data.data;
    })


  }

}

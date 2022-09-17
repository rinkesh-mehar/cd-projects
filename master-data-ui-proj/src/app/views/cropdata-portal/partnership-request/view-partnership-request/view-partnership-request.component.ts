import { PartnershipRequestService } from './../../services/partnership-request.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PartnershipRequest } from '../../models/partnership-request';

@Component({
  selector: 'app-view-partnership-request',
  templateUrl: './view-partnership-request.component.html',
  styleUrls: ['./view-partnership-request.component.css']
})
export class ViewPartnershipRequestComponent implements OnInit {

  partnershipRequestId: string;
  viewPartnershipRequestForm: FormGroup;
  partnershipRequest: PartnershipRequest;

  constructor(public formBuilder: FormBuilder, private actRoute: ActivatedRoute,
    public partnershipRequestService: PartnershipRequestService,  public router: Router) { }

  ngOnInit() {


    this.partnershipRequestId = this.actRoute.snapshot.paramMap.get('id');

    if (this.partnershipRequestId) {
      this.partnershipRequestService.getPartnershipRequestById(this.partnershipRequestId).subscribe(data => {
        console.log('data------------>',data)
        this.partnershipRequest=data;
      });
  }

}

onClickCancel(){
  this.router.navigate(['/cropdata-portal/partnership-request-list']);
}

}

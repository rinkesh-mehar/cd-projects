import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { BuyerSupportService } from '../../service/buyer-support.service';

@Component({
  selector: 'app-buyer-support-details',
  templateUrl: './buyer-support-details.component.html',
  styleUrls: ['./buyer-support-details.component.scss']
})
export class BuyerSupportDetailsComponent implements OnInit {


  responseForm: FormGroup;

  mode: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  supportId: string;
  supportDetails: any = {};

  constructor(public formBuilder: FormBuilder, public buyerSupportService: BuyerSupportService,
    private actRoute: ActivatedRoute, ) { }


  ngOnInit() {

    this.responseForm = this.formBuilder.group({
      id: [],
      bookingId: [],
      subjectId: [],
      subject: [],
      query: [],
      response: ['', Validators.required],
      status: ['Reverted'],
    })




    this.supportId = this.actRoute.snapshot.paramMap.get('id');

    if (this.supportId) {
      this.mode = "edit";
      this.buyerSupportService.getSupportDetails(this.supportId).subscribe(data => {
        this.responseForm.patchValue(data.data);

        this.responseForm.patchValue({
          id: data.data.supportID,
          bookingId: data.data.BookingId,
          subjectId: data.data.subjectID,
          query: data.data.supportQuery,
          response: data.data.supportResponse
        })

        console.log(this.responseForm.value)

        this.supportDetails = data.data;
      })
    }
  }



  submitForm() {


    for (let controller in this.responseForm.controls) {
      this.responseForm.get(controller).markAsTouched();
    }
    if (this.responseForm.invalid) {
      return;
    }

    // this.supportDetails.response = this.responseForm.value.response;
    // this.supportDetails.id = this.responseForm.value.id

    // this.supportDetails.status = "Reverted";

    let observalble = this.buyerSupportService.sendResponse(this.supportDetails.ID, this.responseForm.value);

    observalble.subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          // this.responseForm.reset();
          setTimeout(() => {
            this.isSubmitted = false;
            this.isSuccess = false
          }, 4000)

        } else {
          this._statusMsg = res.error;
        }
      }
    }, err => {
      console.log(err);
    })
  }
}
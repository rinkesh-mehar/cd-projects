import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { EnquiriesService } from "../../service/enquiries.service";

@Component({
  selector: "app-send-response",
  templateUrl: "./send-response.component.html",
  styleUrls: ["./send-response.component.scss"]
})
export class SendResponseComponent implements OnInit {
  responseForm: FormGroup;

  mode: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  enquiryId: string;
  enquiryDetails: any = {};

  constructor(
    public formBuilder: FormBuilder,
    public enquiryService: EnquiriesService,
    private actRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.responseForm = this.formBuilder.group({
      id: [],
      name: [],
      contactNumber: [],
      email: [],
      query: [],
      response: ["", Validators.required],
      status: ["Reverted"]
    });

    this.enquiryId = this.actRoute.snapshot.paramMap.get("id");

    if (this.enquiryId) {
      this.mode = "edit";
      this.enquiryService.getEnquiry(this.enquiryId).subscribe(data => {
        this.responseForm.patchValue(data.data);
        console.log(this.responseForm.value);

        this.enquiryDetails = data.data;
      });
    }
  }

  submitForm() {
    for (let controller in this.responseForm.controls) {
      this.responseForm.get(controller).markAsTouched();
    }
    if (this.responseForm.invalid) {
      return;
    }

    this.enquiryDetails.response = this.responseForm.value.response;
    this.enquiryDetails.status = "Reverted";

    let observalble = this.enquiryService.sendResponse(
      this.enquiryId,
      this.enquiryDetails
    );

    observalble.subscribe(
      (res: any) => {
        this.isSubmitted = true;
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
            // this.responseForm.reset();
            setTimeout(() => {
              this.isSubmitted = false;
              this.isSuccess = false;
            }, 4000);
          } else {
            this._statusMsg = res.error;
          }
        }
      },
      err => {
        console.log(err);
      }
    );
  }
}

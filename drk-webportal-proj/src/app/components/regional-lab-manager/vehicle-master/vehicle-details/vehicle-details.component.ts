import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, NavigationEnd, ActivatedRoute } from "@angular/router";
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { SuccessModalComponent } from '../../../modal-components/success-modal/success-modal.component';
import{ ErrorMessages} from '../../../form-validation-messages'
import { vehicleDetailsModel, statusMessage } from './vehicle-details.model';
import { vehicleDetailsService } from './vehicle-details.service';

@Component({
  selector: 'app-vehicle-details',
  templateUrl: './vehicle-details.component.html',
  styleUrls: ['./vehicle-details.component.less']
})
export class VehicleDetailsComponent implements OnInit {

  vehicleDetails: FormGroup;
  submitted: boolean = false;
  public vehicledetails = {} as vehicleDetailsModel;
  modalRef: BsModalRef;
  public id :number;
  public userId;

  //Error Messages
  public textboxerrormessage: string;
  public dropdownerrormessage: string;
  public checkboxerrormessage: string;
  public invalidvalueerrormessage: string;
  public httperrorresponsemessages: string;

  constructor(
    private modalService: BsModalService,
    private formBuilder: FormBuilder,
    private vehicledetailsservice: vehicleDetailsService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {

    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

    this.userId = localStorage.getItem('loginUserid');
    const routeparam = this.route.params.subscribe(params => {
      this.id= +params['id'];
    });

    if(this.id){
      this.viewvehicledetails();
    }

    //Error Messages
    this.textboxerrormessage = ErrorMessages.textboxError;
    this.dropdownerrormessage = ErrorMessages.dropdownError;
    this.checkboxerrormessage = ErrorMessages.checkboxError;
    this.invalidvalueerrormessage = ErrorMessages.invalidvalueError;

    this.vehicleDetails = this.formBuilder.group({
      id: [''],
      vinnumber: ['', [Validators.required, Validators.pattern("^[0-9a-zA-Z]+$")]],
      barcode: ['', [Validators.required, Validators.pattern("^[0-9a-zA-Z]+$")]],
      status: ['', [Validators.required]],
      remarks: ['', [Validators.required, Validators.pattern("[^ ](.*|\n|\r|\r\n)*")]]
    });
  }

  get f() { return this.vehicleDetails.controls; }

  onSubmit() {
    this.submitted = true;

    if (!this.vehicleDetails.invalid) {
    //this.vehicledetails = this.userId;
    this.vehicledetails.userId = this.userId;
    this.vehicledetails.id = this.id;


    this.vehicledetailsservice.submitVehicleDetailsForm(this.vehicledetails).subscribe(
      (data) => {
        if(data.status == "Success"){

          const initialState = {
            title: "Success",
            content: 'Vehicle details had been saved successfully.',
            action: "/vehicle-master"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        
        }else if(data.status == "Error"){
          var routAction = "";
          if(this.id){
            routAction = "/vehicle-details/" + this.id;
          }else{
            routAction = "/vehicle-details/";
          }

          const initialState = {
            title: "Error",
            content: data.message,
            action: routAction
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        
        }
       
        return;
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/vehicle-master"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });

    } else {
      return false;
    }
  }



  viewvehicledetails() {

    this.vehicledetailsservice.getVehicleDetails(this.id).subscribe(
      (data) => {
        this.vehicledetails = data;

      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/vehicle-master"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }


}

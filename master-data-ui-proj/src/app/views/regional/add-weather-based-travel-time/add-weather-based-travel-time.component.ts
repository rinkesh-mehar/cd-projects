import { Component, OnInit, ViewChild } from '@angular/core';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { WeatherBasedTravelTimeService } from '../services/weather-based-travel-time.service';

@Component({
  selector: 'app-add-weather-based-travel-time',
  templateUrl: './add-weather-based-travel-time.component.html',
  styleUrls: ['./add-weather-based-travel-time.component.scss']
})
export class AddWeatherBasedtravelTimeComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  // RegionList: any = [];
  StressList: any = [];
  StateList: any = [];
  travelTimeForm: FormGroup;
  stressArr: any = [];
  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  editId: string;
  mode: string = "add";

  constructor(public fb: FormBuilder,
    private router: Router,
    public weatherBasedTravelTimeService : WeatherBasedTravelTimeService, 
    private actRoute: ActivatedRoute) { }

  ngOnInit(): void {

    this.travelTimeForm = this.fb.group({
      id: [''],
      name: ['',Validators.required],
      minPerKm: ['',Validators.required],
      status: ['Inactive']
   
    })

    this.editId = this.actRoute.snapshot.paramMap.get('id');
    if (this.editId) {
      this.mode = "edit";
      this.weatherBasedTravelTimeService.getTravelTime(this.editId).subscribe(data => {
        this.travelTimeForm.patchValue(data);
      })
    }
  }


  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  

  submitForm() {

    for(let controller in this.travelTimeForm.controls) {
      this.travelTimeForm.get(controller).markAsTouched();
    }

    if (this.travelTimeForm.invalid) {
      return;
    }

    let observable;

    if (this.mode == 'add') {
     console.log(this.travelTimeForm.value);
      observable = this.weatherBasedTravelTimeService.createTravelTime(this.travelTimeForm.value)
    } else {
      observable = this.weatherBasedTravelTimeService.updateTravelTime(this.travelTimeForm.value.id, this.travelTimeForm.value)
    }

    observable.subscribe(res => {
      this.isSubmitted = true;

      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

  modalSuccess($event: any) {
    this.router.navigate(['/regional/weather-based-travel-time']);
  }

}

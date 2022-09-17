import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { GeoCountryService } from '../services/geo-country.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-geo-country',
  templateUrl: './add-geo-country.component.html',
  styleUrls: ['./add-geo-country.component.scss']
})
export class AddGeoCountryComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  countryForm: FormGroup;
  countryArr: any = [];

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  ngOnInit() {
    this.addCountry()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public geoCountryService: GeoCountryService
  ){ }

  addCountry() {
    this.countryForm = this.fb.group({
      name: ['',Validators.required],
      countryCode: [''],
      status:['Inactive']
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  submitForm() {

    for(let controller in this.countryForm.controls){
      this.countryForm.get(controller).markAsTouched();
    }
  
    if(this.countryForm.invalid){
      return;
    }

    this.geoCountryService.CreateCountry(this.countryForm.value).subscribe(res => {
            this.isSubmitted = true;
      if(res) {
        this.isSuccess = res.success;
        if(res.success){

          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

  modalSuccess($event: any) {
    this.router.navigate(['/geo/country']);
  }
}

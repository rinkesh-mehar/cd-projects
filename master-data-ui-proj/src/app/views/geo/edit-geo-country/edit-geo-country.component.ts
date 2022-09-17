import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { GeoCountryService } from '../services/geo-country.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-edit-geo-country',
  templateUrl: './edit-geo-country.component.html',
  styleUrls: ['./edit-geo-country.component.scss']
})
export class EditGeoCountryComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  CountryList: any = [];
  updateCountryForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  ngOnInit() {
    this.updateForm()
  }

  constructor(
    private actRoute: ActivatedRoute,
    public geoCountryService: GeoCountryService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) {
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.geoCountryService.GetCountry(id).subscribe((data) => {
      this.updateCountryForm = this.fb.group({
        countryCode: [data.countryCode],
        name: [data.name, Validators.required],
        status: [data.status]
      })
    })
  }

  updateForm() {
    this.updateCountryForm = this.fb.group({
      countryCode: [''],
      name: ['', Validators.required],
      status: ['Inactive']
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  submitForm() {

    for (let controller in this.updateCountryForm.controls) {
      this.updateCountryForm.get(controller).markAsTouched();
    }

    if (this.updateCountryForm.invalid) {
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.geoCountryService.UpdateCountry(id, this.updateCountryForm.value).subscribe(res => {
      this.isSubmitted = true;

      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          // this._statusMsg = res.message;
          // this.updateCountryForm.reset();

          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
        }
    })
  }

  modalSuccess($event: any) {
    this.router.navigate(['/geo/country']);
  }
}

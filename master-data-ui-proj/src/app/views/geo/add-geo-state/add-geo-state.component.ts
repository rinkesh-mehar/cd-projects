import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { GeoStateService } from '../services/geo-state.service';
import { GeoCountryService } from '../services/geo-country.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-add-geo-state',
  templateUrl: './add-geo-state.component.html',
  styleUrls: ['./add-geo-state.component.scss']
})
export class AddGeoStateComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  CountryList: any =[];
  stateForm: FormGroup;
  stateArr: any = [];

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  ngOnInit() {
    this.addState();
    this.loadAllStateByCountryCode();
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public geoStateService: GeoStateService,
    public geoCountryService: GeoCountryService
  ){ }

  addState() {
    this.stateForm = this.fb.group({
      name: ['',Validators.required],
      stateCode: [''],
      countryCode: ['',Validators.required],
      status: ['Inactive'],
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  submitForm() {

    for(let controller in this.stateForm.controls){
      this.stateForm.get(controller).markAsTouched();
    }
  
    if(this.stateForm.invalid){
      return;
    }


    if(this.stateForm.get('countryCode').value == 0){
      alert('Please Select Country');
      return;
    }

    
    this.geoStateService.CreateState(this.stateForm.value).subscribe(res => {
      this.isSubmitted = true;
     
      if(res) {
        this.isSuccess = res.success;
        if (res.success) {
          // this._statusMsg = res.message;
          // this.stateForm.reset();

          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

//Country list
  loadAllStateByCountryCode(){

      return this.geoCountryService.GetAllCountry().subscribe((data: {}) => {
        this.CountryList = data;
      })
    }

    modalSuccess($event: any) {
        this.router.navigate(['/geo/state']);
    }
}

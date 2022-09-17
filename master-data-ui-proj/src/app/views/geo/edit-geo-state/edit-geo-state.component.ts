import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { GeoStateService } from '../services/geo-state.service';
import { GeoCountryService } from '../services/geo-country.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-edit-geo-state',
  templateUrl: './edit-geo-state.component.html',
  styleUrls: ['./edit-geo-state.component.scss']
})
export class EditGeoStateComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  CountryList: any = [];
  StateList: any = [];
  updateStateForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.updateForm();
    this.loadAllStateByCountryCode()
  }

  constructor(
    private actRoute: ActivatedRoute,    
    public geoStateService: GeoStateService,
    public geoCountryService : GeoCountryService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.geoStateService.GetState(id).subscribe((data) => {
      this.updateStateForm = this.fb.group({
        name: [data.name,Validators.required],
        stateCode: [data.stateCode],
        countryCode: [data.countryCode,Validators.required],
        status: [data.status]
        })
    })
  }

  updateForm(){
    this.updateStateForm = this.fb.group({
      name: ['',Validators.required],
      stateCode: [''],
      countryCode: ['',Validators.required],
      status: ['Inactive']
    })    
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  submitForm(){ 


    for(let controller in this.updateStateForm.controls){
      this.updateStateForm.get(controller).markAsTouched();
    }
  
    if(this.updateStateForm.invalid){
      return;
    }

    if(this.updateStateForm.get('countryCode').value == 0){
      alert('Please Select Country');
      return;
    }

    

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.geoStateService.UpdateState(id, this.updateStateForm.value).subscribe(res => {
      this.isSubmitted = true;
     
      if(res){
        this.isSuccess = res.success;
        if(res.success){
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    })
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

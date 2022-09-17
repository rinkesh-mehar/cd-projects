import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { GeoVillageService } from '../services/geo-village.service';
import { GeoDistrictService } from '../services/geo-district.service';
import { GeoStateService } from '../services/geo-state.service';
import { GeoTehsilService } from '../services/geo-tehsil.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-geo-village',
  templateUrl: './add-geo-village.component.html',
  styleUrls: ['./add-geo-village.component.scss']
})
export class AddGeoVillageComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  DistrictList: any = [];
  StateList: any = [];
  TehsilList: any = [];
  villageForm: FormGroup;
  villageArr: any = [];

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  ngOnInit() {
    this.addVillage();
   // this.loadAllDistrict();
    this.loadAllState();
  //  this.loadAllTehsil()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public geoVillageService: GeoVillageService,
    public geoDistrictService: GeoDistrictService,
    public geoStateService: GeoStateService,
    public geoTehsilService: GeoTehsilService
  ){ }

  addVillage() {
    this.villageForm = this.fb.group({
     
      stateCode: ['',Validators.required],
      districtCode: ['',Validators.required],
      tehsilCode: ['',Validators.required],
      name: ['',Validators.required],
      villageCode: [''],
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

    for(let controller in this.villageForm.controls){
      this.villageForm.get(controller).markAsTouched();
    }
  
    if(this.villageForm.invalid){
      return;
    }

    if(this.villageForm.get('stateCode').value == 0){
      alert('Please Select State');
      return;
    }

    if(this.villageForm.get('districtCode').value == 0){
      alert('Please Select District');
      return;
    }

    if(this.villageForm.get('tehsilCode').value == 0){
      alert('Please Select Tehsil');
      return;
    }

    

    this.geoVillageService.CreateVillage(this.villageForm.value).subscribe(res => {
      this.isSubmitted = true;
     
      if(res){
        this.isSuccess = res.success;
        if(res.success){
          // this._statusMsg = res.message;
          // this.villageForm.reset();

            this.successModal.showModal('SUCCESS', res.message, '');
        } else {
            this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

   //District list
 loadAllDistrict(){
  return this.geoDistrictService.GetAllDistrict().subscribe((data: {}) => {
    this.DistrictList = data;
  })
}

  //State list
  loadAllState(){
    return this.geoStateService.GetAllState().subscribe((data: {}) => {
      this.StateList = data;
    })
  }

   //Tehsil list
   loadAllTehsil(){
    return this.geoTehsilService.GetAllTehsil().subscribe((data: {}) => {
      this.TehsilList = data;
    })
  }

  loadAllDistrictsByState(event:Event) : void {
    let index:number = event.target["selectedIndex"] - 1;
   if(index ==-1) {
       return;
    }
    let stateCode = this.StateList[index].stateCode;
    this.geoDistrictService.GetAllDistrictByStateCode(stateCode).subscribe(
      (data: {}) => {
        this.DistrictList = data;
        console.log(this.DistrictList);
      }
    );
  }

  loadAllTehsilByDistrict(event:Event) : void {
    let index:number = event.target["selectedIndex"] - 1;
   if(index ==-1) {
       return;
    }
    let districtCode = this.DistrictList[index].districtCode;
    this.geoTehsilService.GetAllTehsilByDistrictCode(districtCode).subscribe(
      (data: {}) => {
        this.TehsilList = data;
        console.log(this.TehsilList);
      }
    );
  }

    modalSuccess($event: any) {
        this.router.navigate(['/geo/village']);
    }
}

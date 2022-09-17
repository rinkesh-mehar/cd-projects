import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { GeoTehsilService } from '../services/geo-tehsil.service';
import { GeoDistrictService } from '../services/geo-district.service';
import { GeoStateService } from '../services/geo-state.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-geo-tehsil',
  templateUrl: './edit-geo-tehsil.component.html',
  styleUrls: ['./edit-geo-tehsil.component.scss']
})
export class EditGeoTehsilComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  DistrictList: any = [];
  StateList: any = [];
  TehsilList: any = [];
  updateTehsilForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.updateForm()
    this.loadAllDistrict();
    this.loadAllState()
  }

  constructor(
    private actRoute: ActivatedRoute,    
    public geoTehsilService: GeoTehsilService,
    public geoDistrictService : GeoDistrictService,
    public geoStateService : GeoStateService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.geoTehsilService.GetTehsil(id).subscribe((data) => {
      this.updateTehsilForm = this.fb.group({
         stateCode: [data.stateCode,Validators.required],
        districtCode: [data.districtCode,Validators.required],
        tehsilCode:[data.tehsilCode],
        name: [data.name,Validators.required],
        status :[data.status]
      
      
        })
    })
  }

  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  updateForm(){
    this.updateTehsilForm = this.fb.group({
      stateCode: ['',Validators.required],
      districtCode: ['',Validators.required],
      tehsilCode:[''],
      name: ['',Validators.required],
      status :[]
    })    
  }
  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm(){ 

    for(let controller in this.updateTehsilForm.controls){
      this.updateTehsilForm.get(controller).markAsTouched();
    }
  
    if(this.updateTehsilForm.invalid){
      return;
    }

    if(this.updateTehsilForm.get('stateCode').value == 0){
      alert('Please Select State');
      return;
    }

    if(this.updateTehsilForm.get('districtCode').value == 0){
      alert('Please Select District');
      return;
    }

    


    var id = this.actRoute.snapshot.paramMap.get('id');
    this.geoTehsilService.UpdateTehsil(id, this.updateTehsilForm.value).subscribe(res => {
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

  modalSuccess($event: any) {
    this.router.navigate(['/geo/tehsil']);
  }
}

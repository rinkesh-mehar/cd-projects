import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { GeoPanchayatService } from '../services/geo-panchayat.service';
import { GeoDistrictService } from '../services/geo-district.service';
import { GeoStateService } from '../services/geo-state.service';
import { GeoTehsilService } from '../services/geo-tehsil.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-geo-panchayat',
  templateUrl: './edit-geo-panchayat.component.html',
  styleUrls: ['./edit-geo-panchayat.component.scss']
})
export class EditGeoPanchayatComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  DistrictList: any = [];
  StateList: any = [];
  TehsilList: any = [];
  PanchayatList: any = [];
  updatePanchayatForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.updateForm();
    this.loadAllDistrict();
    this.loadAllState();
    this.loadAllTehsil()
  }

  constructor(
    private actRoute: ActivatedRoute,    
    public geoPanchayatService: GeoPanchayatService,
    public geoDistrictService: GeoDistrictService,
    public geoStateService: GeoStateService,
    public geoTehsilService: GeoTehsilService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.geoPanchayatService.GetPanchayat(id).subscribe((data) => {
      this.updatePanchayatForm = this.fb.group({
        stateCode: [data.stateCode,Validators.required],
        districtCode: [data.districtCode,Validators.required],
        tehsilCode:[data.tehsilCode,Validators.required],
        panchayatCode:[data.panchayatCode,Validators.required],
        name:[data.name,Validators.required],
        status: [data.status]

        })
    })
  }

  updateForm(){
    this.updatePanchayatForm = this.fb.group({
      stateCode: ['',Validators.required],
      districtCode: ['',Validators.required],
      tehsilCode:['',Validators.required],
      panchayatCode:['',Validators.required],
      name:['',Validators.required],
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

    for (let controller in this.updatePanchayatForm.controls) {
      this.updatePanchayatForm.get(controller).markAsTouched();
    }

    if(this.updatePanchayatForm.invalid){
      return;
    }

    if(this.updatePanchayatForm.get('stateCode').value == 0){
      alert('Please Select State');
      return;
    }

    if(this.updatePanchayatForm.get('districtCode').value == 0){
      alert('Please Select District');
      return;
    }

    if(this.updatePanchayatForm.get('tehsilCode').value == 0){
      alert('Please Select Tehsil');
      return;
    }

    if(this.updatePanchayatForm.get('panchayatCode').value == 0){
      alert('Please Select Panchayat');
      return;
    }
    
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.geoPanchayatService.UpdatePanchayat(id, this.updatePanchayatForm.value).subscribe(res => {
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
        this.router.navigate(['/geo/panchayat']);
    }
}

import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { GeoVillageService } from '../services/geo-village.service';
import { GeoDistrictService } from '../services/geo-district.service';
import { GeoStateService } from '../services/geo-state.service';
import { GeoTehsilService } from '../services/geo-tehsil.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-geo-village',
  templateUrl: './edit-geo-village.component.html',
  styleUrls: ['./edit-geo-village.component.scss']
})
export class EditGeoVillageComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  DistrictList: any = [];
  StateList: any = [];
  TehsilList: any = [];
  VillageList: any = [];
  updateVillageForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  ngOnInit() {
    this.updateForm();
    // this.loadAllDistrict();
    // this.loadAllState();
    // this.loadAllTehsil()
  }

  constructor(
    private actRoute: ActivatedRoute,
    public geoVillageService: GeoVillageService,
    public geoDistrictService: GeoDistrictService,
    public geoStateService: GeoStateService,
    public geoTehsilService: GeoTehsilService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) {
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.geoVillageService.GetVillage(id).subscribe((data) => {
      this.updateVillageForm = this.fb.group({
        stateCode: [data.stateCode, Validators.required],
        districtCode: [data.districtCode, Validators.required],
        tehsilCode: [data.tehsilCode, Validators.required],
        villageCode: [data.villageCode],
        name: [data.name, Validators.required],
        status: [data.status]
      })
      this.loadAllState();
      this.loadAllDistrictsByState();
      this.loadAllTehsilByDistrict();
    })
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  

  updateForm() {
    this.updateVillageForm = this.fb.group({
      stateCode: ['', Validators.required],
      districtCode: ['', Validators.required],
      tehsilCode: ['', Validators.required],
      villageCode: [''],
      status: ['Inactive'],
      name: ['', Validators.required]
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {

    for (let controller in this.updateVillageForm.controls) {
      this.updateVillageForm.get(controller).markAsTouched();
    }

    if (this.updateVillageForm.invalid) {
      return;
    }

    if (this.updateVillageForm.get('stateCode').value == 0) {
      alert('Please Select State');
      return;
    }

    if (this.updateVillageForm.get('districtCode').value == 0) {
      alert('Please Select District');
      return;
    }

    if (this.updateVillageForm.get('tehsilCode').value == 0) {
      alert('Please Select Tehsil');
      return;
    }

    

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.geoVillageService.UpdateVillage(id, this.updateVillageForm.value).subscribe(res => {
      this.isSubmitted = true;

      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    })
  }

  //District list
  loadAllDistrict() {
    return this.geoDistrictService.GetAllDistrict().subscribe((data: {}) => {
      this.DistrictList = data;
    })
  }

  //State list
  loadAllState() {
    return this.geoStateService.GetAllState().subscribe((data: {}) => {
      this.StateList = data;
    })
  }

  //Tehsil list
  loadAllTehsil() {
    return this.geoTehsilService.GetAllTehsil().subscribe((data: {}) => {
      this.TehsilList = data;
    })
  }

  loadAllDistrictsByState(): void {
    // let index: number = event.target["selectedIndex"] - 1;
    // if (index == -1) {
    //   return;
    // }
    // let stateCode = this.StateList[index].stateCode;
    let stateCode = this.updateVillageForm.value.stateCode;
    this.geoDistrictService.GetAllDistrictByStateCode(stateCode).subscribe(
      (data: {}) => {
        this.DistrictList = data;
        console.log(this.DistrictList);
      }
    );
  }

  loadAllTehsilByDistrict(): void {
    //   let index:number = event.target["selectedIndex"] - 1;
    //  if(index ==-1) {
    //      return;
    //   }
    let districtCode = this.updateVillageForm.value.districtCode;
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

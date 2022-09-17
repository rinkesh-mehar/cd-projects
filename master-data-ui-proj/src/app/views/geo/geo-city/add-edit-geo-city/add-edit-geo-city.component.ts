import { Component, OnInit, ViewChild } from '@angular/core';

import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ApiUtilService } from '../../../services/api-util.service';
import { GeoStateService } from '../../services/geo-state.service';
import { GeoDistrictService } from '../../services/geo-district.service';
import { GeoCityService } from '../../services/geo-city.service';

@Component({
  selector: 'app-add-edit-geo-city',
  templateUrl: './add-edit-geo-city.component.html',
  styleUrls: ['./add-edit-geo-city.component.scss']
})
export class AddEditGeoCityComponent implements OnInit {

  CityForm: FormGroup;
 
  stateList: any;
  districtList: any;
  editCityId: any;
  mode: any;
  City: any;
  uploadFile: File = null;
  imgPerview: any;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  // isSubmittedBulk: boolean = false;
  // isSuccessBulk: boolean = false;
  // fileUpload: any;

  constructor(public formBuilder: FormBuilder, private geoStateService : GeoStateService, private geoDistrictService : GeoDistrictService,
   public geoCityService : GeoCityService, private actRoute: ActivatedRoute,public apiUtilService: ApiUtilService ) {


    this.createCityForm();


  }
  getChanges() {
    console.log(this.CityForm.value)
  }

  createCityForm() {
    this.CityForm = this.formBuilder.group({
      id: [],
      stateCode: ['', Validators.required],
      districtCode: ['', Validators.required],
      cityCode: ['', Validators.required],
      name: ['', Validators.required],
      status: ['Inactive']
     
   
      })
  }

  ngOnInit() {
    this.loadAllState();
  //  this.loadAllVariety();
 
  
    this.editCityId = this.actRoute.snapshot.paramMap.get('id');

    if (this.editCityId) {

      this.mode = "edit";
      this.geoCityService.GetCity(this.editCityId).subscribe(data => {
        this.City = data;
        this.CityForm = this.formBuilder.group({
          id: [],
          stateCode: [data.stateCode, Validators.required],
          districtCode: [data.districtCode, Validators.required],
          cityCode: [data.cityCode, Validators.required],
          name: [data.name, Validators.required],
          status: ['']
        })

        
        this.CityForm.patchValue(this.City);
        // this.getDistrictByState();
      })


    }

  }

  loadAllState() {
    return this.geoStateService.GetAllState().subscribe((data: any) => {
      this.stateList = data;
    }, err => {
      alert(err)
    })
  }

  loadAllDistrict() {
    return this.geoDistrictService.GetAllDistrict().subscribe((data: any) => {
      this.districtList = data;
    }, err => {
      alert(err)
    })
  }

  submitCityForm() {


    if(this.CityForm.get('stateCode').value == 0){
      alert('Please Select State');
      return;
    }

    if(this.CityForm.get('districtCode').value == 0){
      alert('Please Select District');
      return;
    }
    for (let controller in this.CityForm.controls) {
      this.CityForm.get(controller).markAsTouched();
    }
    if (this.CityForm.invalid) {
      return;
    }
    if (this.mode == "edit") {
      this.updateCity();
    } else {
      this.addCity();
    }
  }
  addCity() {
    this.geoCityService.CreateCity(this.CityForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.City = {};
          this.mode = "add";
          this.CityForm.reset();
          setTimeout(() => {
            this.isSubmitted = false;
            this.isSuccess = false
          }, 4000)

        } else {
          this._statusMsg = res.error;
        }
      }
    }, err => {
      console.log(err);
    })
  }
  updateCity() {
    this.geoCityService.UpdateCity(this.CityForm.value.id, this.CityForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.City = {};
          this.mode = "add";
          this.CityForm.reset();

         this.createCityForm();

          setTimeout(() => {
            this.isSubmitted = false;
            this.isSuccess = false
          }, 4000)
        } else {
          this._statusMsg = res.error;
        }
      }
    }, err => {
      console.log(err);
    })
  }nInit() {
  }


  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }


  // downloadExcelFormat(){
  //   var tableName = "geo_city";
  //   this.apiUtilService.downloadExcelFormat(tableName);
  // }//downloadExcelFormat


  // uploadExcel(element) {
  //   var file: File = element.target.files[0];
  //   this.fileUpload = file;
  // }

  // submitExcelForm() {
  //   this.apiUtilService.uploadExcelFile(this.fileUpload).subscribe(res => {
  //     this.isSubmittedBulk = true;
     
  //     if(res){
  //       this.isSuccessBulk = res.success;
  //       if(res.success){
  //         this._statusMsg = res.message;
         
  //         this.delay(4000).then(any => {
  //             this.isSubmittedBulk = false;
  //             this.isSuccessBulk = false;
  //           });
  //       }else{
  //         this._statusMsg = res.error;
  //       }
  //     }
  //   });
  // }

}

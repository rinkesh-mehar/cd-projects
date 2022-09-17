import {Component, OnInit, Input, Output, EventEmitter, SimpleChanges, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import custom validator to validate that password and confirm password fields match
import {ActivatedRoute, Router} from '@angular/router';
import { AgriCommodityService } from '../../services/agri-commodity.service';
import { AgriPhenophaseService } from '../../services/agri-phenophase.service';
import { ZonalFavourableWeatherService } from '../../services/zonal-favourable-weather.service';
import { GeneralWeatherParamsService } from '../../../general/services/general-weather-params.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { GeoStateService } from '../../../geo/services/geo-state.service';
import { AczService } from '../../../zonal/services/acz.service';
import { ZonalCommodityService } from '../../../zonal/services/zonal-commodity.service';



@Component({
  selector: 'app-add-edit-zonal-favourable-weather',
  templateUrl: './add-edit-zonal-favourable-weather.component.html',
  styleUrls: ['./add-edit-zonal-favourable-weather.component.scss']
})
export class AddEditZonalFavourableWeatherComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  favourableWeatherForm: FormGroup;
  // operationMode: string;

  stateList: any = [];
  // seasonList: any = [];
  aczList: any = [];
  zonalcommodityList: any = [];
  stateCodeAczId : any;
  // commodityList: any;
  phenophaseList: any = [];
  weatherParamsList: any;
  editFavourableWeatherId: any;
  mode: any='add';
  favourableWeather: any;
  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;



  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  constructor(public formBuilder: FormBuilder, private agriCommodityService: AgriCommodityService, private agriPhenophaseService: AgriPhenophaseService,
              private generalWeatherParamsService: GeneralWeatherParamsService, public router: Router,
              public zonalFavourableWeatherService: ZonalFavourableWeatherService, private actRoute: ActivatedRoute, public apiUtilService: ApiUtilService,
              public aczService:AczService,
              public geoStateService:GeoStateService,
              public zonalCommodityService:ZonalCommodityService
            ) {

    this.createFavourableWeatherForm();


  }
  getChanges() {
    console.log(this.favourableWeatherForm.value)
  }

  createFavourableWeatherForm() {
    this.favourableWeatherForm = this.formBuilder.group({
      id: [],
      stateCode :['', Validators.required],
      aczId: ['', Validators.required],
      zonalCommodityId : ['', Validators.required],
      // commodityId: ['', Validators.required],
      phenophaseId: ['', Validators.required],
      weatherParameterId: ['', Validators.required],
      specificationAverage: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
      specificationLower: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
      specificationUpper: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
      status : ['Inactive']
      })
  }

  ngOnInit() {
    this.createFavourableWeatherForm();
    this.loadState();
    // this.loadAllCommodity();
  //  this.loadAllPhenophase();
   this.loadAllWeatherParams();
  
    this.editFavourableWeatherId = this.actRoute.snapshot.paramMap.get('id');

    if (this.editFavourableWeatherId) {

      this.mode = "edit";
      this.zonalFavourableWeatherService.GetFavourableWeather(this.editFavourableWeatherId).subscribe(data => {
        this.favourableWeather = data;
        this.favourableWeatherForm = this.formBuilder.group({
          id: [],
          stateCode : ['', Validators.required],
         aczId : ['', Validators.required],
         zonalCommodityId : ['', Validators.required],
          // commodityId: ['', Validators.required],
          phenophaseId: ['', Validators.required],
          weatherParameterId: ['', Validators.required],
          specificationAverage: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
          specificationLower: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
          specificationUpper: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
          status: ['']
        })
        this.favourableWeatherForm.patchValue(this.favourableWeather);
        this.getStateCodeAczIdByZonalCommodityId();
      })


    }

  }

  //statelist
  loadState() {
    return this.geoStateService.GetAllState().subscribe((data: {}) => {
    this.stateList = data;
    })
  }

  //ACZ list
  loadAczByStateCode() {
    this.favourableWeatherForm.patchValue({aczId:''});
    return this.aczService.getAllAczByStateCode(this.favourableWeatherForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }



  loadAczByStateCodeForEditMode() {
    return this.aczService.getAllAczByStateCode(this.favourableWeatherForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }

  //ZonalCommodity list
  loadZonalCommodityByAczId() {
    this.favourableWeatherForm.patchValue({zonalCommodityId: ''});
    return this.zonalCommodityService.getZonalCommodityListByAczId(this.favourableWeatherForm.value.aczId).subscribe((data: {}) => {
      this.zonalcommodityList = data;
    })
  }

  loadZonalCommodityByAczIdForEditMode() {
    return this.zonalCommodityService.getZonalCommodityListByAczId(this.favourableWeatherForm.value.aczId).subscribe((data: {}) => {
      this.zonalcommodityList = data;
    })
  }

   //Phenophase List by Zonal Commodity
   loadPhenophaseByZonalCommodityId() {
    this.favourableWeatherForm.patchValue({phenophaseId: ''});
     if(this.mode=="add"){
      return this.agriPhenophaseService.getPhenophaseListByZonalCommodityId(this.favourableWeatherForm.value.zonalCommodityId).subscribe((data: {}) => {
        //  this.favourableWeather.patch(this.favourableWeatherForm.value.zonalCommodityId)
          this.phenophaseList = data;
        })
     }
     else{
      
    return this.agriPhenophaseService.getPhenophaseListByZonalCommodityId(this.favourableWeather.zonalCommodityId).subscribe((data: {}) => {
    //  this.favourableWeather.patch(this.favourableWeatherForm.value.zonalCommodityId)
      this.phenophaseList = data;
    })
  }
}



  getStateCodeAczIdByZonalCommodityId() {
    
    //alert("CommodityId = "+this.taskTypeSpecificationForm.value.zonalCommodityID)
    return this.zonalCommodityService.getStateCodeAczIdByZonalCommodityId(this.favourableWeatherForm.value.zonalCommodityId).subscribe((data) => {
      this.stateCodeAczId = data;
      //alert(JSON.stringify(this.stateCodeAczIdZonalVarietyId));
      this.favourableWeatherForm.patchValue(this.stateCodeAczId);
      this.loadAczByStateCodeForEditMode();
      this.loadZonalCommodityByAczIdForEditMode();
      this.loadPhenophaseByZonalCommodityId();
      // this.loadZonalVarietyByZonalCommodity();
      // this.getZonalVarietyIdByStateCodeAczIdCommodityIdPhenophaseId();
      this.favourableWeatherForm.patchValue({zonalCommodityId:this.favourableWeather.zonalCommodityId});
      this.favourableWeatherForm.patchValue({phenophaseId:this.favourableWeather.phenophaseId});
    })
  }










  // loadAllCommodity() {
  //   return this.agriCommodityService.GetAllCommoditise().subscribe((data: any) => {
  //     this.commodityList = data;
  //   }, err => {
  //     alert(err)
  //   })
  // }

  // loadAllPhenophase() {
  //   return this.agriPhenophaseService.GetAllPhenophase().subscribe((data: any) => {
  //     this.phenophaseList = data;
  //   }, err => {
  //     alert(err)
  //   })
  // }

  loadAllWeatherParams() {
    return this.generalWeatherParamsService.GetAllAgriWeatherParams().subscribe((data: any) => {
      this.weatherParamsList = data;
    }, err => {
      alert(err)
    })
  }

  submitFavourableWeatherForm() {

    for (let controller in this.favourableWeatherForm.controls) {
      this.favourableWeatherForm.get(controller).markAsTouched();
    }
    if (this.favourableWeatherForm.invalid) {
      return;
    }

    // if (this.favourableWeatherForm.get('phenophaseId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select Phenophase', '');
    //   return;
    // }

    // if (this.favourableWeatherForm.get('commodityId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select Commodity', '');
    //   return;
    // }
    // if (this.favourableWeatherForm.get('weatherParameterId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select WeatherParameter', '');
    //   return;
    // }

  
    if (this.mode == 'edit') {
      this.updateFavourableWeather();
    } else {
      this.addFavourableWeather();
    }
  }

  addFavourableWeather() {
    this.zonalFavourableWeatherService.CreateFavourableWeather(this.favourableWeatherForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.favourableWeather = {};
          this.mode = 'add';
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    });
  }

  updateFavourableWeather() {
    this.zonalFavourableWeatherService.UpdateFavourableWeather(this.editFavourableWeatherId, this.favourableWeatherForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.favourableWeather = {};
          this.mode = 'add';
          // this.favourableWeatherForm.reset();

          this.createFavourableWeatherForm();

          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    });
  }

  // loadAllCommodityByPhenophase() {
  //   let phenophaseId = this.favourableWeatherForm.value.commodityId;
  //   this.agriCommodityService.getCommodityByPhenophaseId(phenophaseId).subscribe(
  //       (data: {}) => {
  //         this.phenophaseList = data;
  //         console.log(this.phenophaseList);
  //       }
  //   );
  // }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  downloadExcelFormat() {
    var tableName = 'agri_favourable_weather';
    this.apiUtilService.downloadExcelFormat(tableName);
  }//downloadExcelFormat


  uploadExcel(element) {
    var file: File = element.target.files[0];
    this.fileUpload = file;
  }

  submitExcelForm() {
    this.apiUtilService.uploadExcelFile(this.fileUpload).subscribe(res => {
      this.isSubmittedBulk = true;

      if (res) {
        this.isSuccessBulk = res.success;
        if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

  modalSuccess($event: any) {
    this.router.navigate(['/zonal/favourable-weather']);
  }
}

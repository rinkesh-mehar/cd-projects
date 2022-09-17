import { GeoStateService } from './../../../geo/services/geo-state.service';
import { Router, ActivatedRoute } from '@angular/router';
import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { AgriCommodityService } from '../../services/agri-commodity.service';
import { AgriStressTypeService } from '../../services/agri-stress-type.service';
import { ZonalStressDurationService } from '../../services/zonal-stress-duration.service';
import { ZonalCondusiveWeatherService } from '../../services/zonal-condusive-weather.service';
import { GeneralWeatherParamsService } from '../../../general/services/general-weather-params.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { AgriPhenophaseService } from '../../services/agri-phenophase.service';
import { AczService } from '../../../zonal/services/acz.service';
import { ZonalCommodityService } from '../../../zonal/services/zonal-commodity.service';
import { ZonalVarietyService } from '../../../regional/services/zonal-variety.service';


@Component({
  selector: 'app-add-edit-condusive-weather',
  templateUrl: './add-edit-condusive-weather.component.html',
  styleUrls: ['./add-edit-condusive-weather.component.scss']
})
export class AddEditCondusiveWeatherComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  condusiveWeatherForm: FormGroup;
  CommodityList: any = [];
  StressTypeList: any = [];
  StressList: any = [];
  weatherParamList: any = [];
  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  editCondusiveWeatherId: string;
  mode: string = 'add';
  condusiveParam: any;


  showPrimaryAvg: boolean = true;
  showSecondaryAvg: boolean = true;

  stateList : any = [];
  aczList : any = [];
  zonalCommodityList:any = [];
  zonalVarietyList:any = [];
  phenophaseList: any = [];
  commodityId:any;
  stateCodeAczId : any;
  zonalVarietyId:any;
  stressTypeIdByCommodityIdAndStressId: any;

  constructor(
    public FormBuilder: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    private actRoute: ActivatedRoute,
    public commodityService: AgriCommodityService,
    public stressTypeService: AgriStressTypeService,
    public zonalStressDurationService: ZonalStressDurationService,
    public condusiveParamService: ZonalCondusiveWeatherService,
    public generalWeatherParamService: GeneralWeatherParamsService,
    public apiUtilService: ApiUtilService,
    public agriPhenophaseService: AgriPhenophaseService,
    public geoStateService:GeoStateService,
    public aczService :AczService,
    public zonalCommodityService: ZonalCommodityService,
    public zonalVarietyService: ZonalVarietyService
  ) {
    this.CreateCondusiveWeatherForm();
  }
  getChanges() {
    console.log(this.condusiveWeatherForm.value)
  }

  CreateCondusiveWeatherForm() {
    this.condusiveWeatherForm = this.FormBuilder.group({
      id: [],
      stateCode: ['', Validators.required],
      aczId: ['', Validators.required],
      zonalCommodityId: ['', Validators.required],
      stressTypeId: ['', Validators.required],
      stressId: ['', Validators.required],
      weatherParameterId: ['', Validators.required],
      lower: ['',
        [Validators.required,
        Validators.pattern("^[0-9.]*$")]
      ],
      upper: ['',
        [Validators.required,
        Validators.pattern("^[0-9.]*$")]
      ],
      conduciveDuration: ['',Validators.pattern("^[0-9]*$")],
      relaxingDuration: ['',Validators.pattern("^[0-9]*$")],
      // primaryStressDuration: ['', Validators.required],
      // primaryStressDurationPast: ['', [Validators.required,Validators.pattern("^[0-9]*$")]],
      // primaryStressDurationFuture: ['',Validators.pattern("^[0-9]*$")],

      // secondaryWeatherParameterId: [''],
      // secondarySpecificationAverage: ['',Validators.pattern("^[0-9]*$")],
      // secondarySpecificationLower: [''],
      // secondarySpecificationUpper: [''],
      // secondaryStressDuration: ['', Validators.required],
      // secondaryStressDurationPast: ['', Validators.pattern("^[0-9]*$")],
      // secondaryStressDurationFuture: ['',Validators.pattern("^[0-9]*$")],
      status: ['Inactive']

    })
  }

  ngOnInit() {
    // this.loadAllCommodities();
   
    this.getState();
    this.loadAllStressType();
    this.loadAllWeatherParam();
    this.editCondusiveWeatherId = this.actRoute.snapshot.paramMap.get('id');

    if (this.editCondusiveWeatherId) {

      this.mode = "edit";
      this.condusiveParamService.GetCondusiveWeather(this.editCondusiveWeatherId).subscribe(data => {
        this.condusiveParam = data;
        this.condusiveWeatherForm.patchValue(this.condusiveParam);
        this.condusiveWeatherForm.patchValue(data);


        // if (this.condusiveParam.primarySpecificationLower && this.condusiveParam.primarySpecificationUpper) {
        //   this.showPrimaryAvg = false;
        // }
        // if (this.condusiveParam.secondarySpecificationLower && this.condusiveParam.secondarySpecificationUpper) {
        //   this.showSecondaryAvg = false;
        // }
        this.getStateCodeAczIdByZonalCommodityId();
      })


    }
    // this.condusiveWeatherForm.patchValue({stressId:this.condusiveParam.stressId});
    // this.condusiveWeatherForm.controls['stressId'].patchValue(this.condusiveParam.stressId);
    // this.condusiveWeatherForm.controls['stressId'].setValue(this.condusiveParam.stressId);
  }

  // async delay(ms: number) {
  //   await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  // }


//statelist
getState() {
  return this.geoStateService.GetAllState().subscribe((data: {}) => {
  this.stateList = data;
  })
}

//ACZ list
onChangeGetAczByStateCode() {
  
    this.condusiveWeatherForm.patchValue({aczId:''});

  return this.aczService.getAllAczByStateCode(this.condusiveWeatherForm.value.stateCode).subscribe((data: {}) => {
    this.aczList = data;
  })
}

getAczByStateCodeForEdit() {
  return this.aczService.getAllAczByStateCode(this.condusiveWeatherForm.value.stateCode).subscribe((data: {}) => {
    this.aczList = data;
  })
}

onChangeZonalCommodity(){
  this.condusiveWeatherForm.patchValue({stressId:''});
  this.condusiveWeatherForm.patchValue({stressTypeId:''});
}

//ZonalCommodity list
onChangeGetZonalCommodityByAczId() {
  
  this.condusiveWeatherForm.patchValue({zonalCommodityId:''});
  
  return this.zonalCommodityService.getZonalCommodityListByAczId(this.condusiveWeatherForm.value.aczId).subscribe((data: {}) => {
    this.zonalCommodityList = data;
    // if(this.mode == "edit"){
    //   for(let zonalCommodity of this.zonalCommodityList){
    //     if(this.condusiveWeatherForm.value.zonalCommodityId == zonalCommodity.id){
    //       this.commodityId = zonalCommodity.commodityId;
    //       this.getStressTypeIDByCommodityIdAndStressId();
    //     }
    //   }
    // }
  })
}

getZonalCommodityByAczIdForEdit() {
  return this.zonalCommodityService.getZonalCommodityListByAczId(this.condusiveWeatherForm.value.aczId).subscribe((data: {}) => {
    this.zonalCommodityList = data;
    if(this.mode == "edit"){
      for(let zonalCommodity of this.zonalCommodityList){
        if(this.condusiveWeatherForm.value.zonalCommodityId == zonalCommodity.id){
          this.commodityId = zonalCommodity.commodityId;
          this.getStressTypeIDByCommodityIdAndStressId();
        }
      }
    }
  })
}

getStressTypeIDByCommodityIdAndStressId(){
  return this.stressTypeService.getStressTypeIdByCommodityIdAndStressId(this.commodityId,this.condusiveWeatherForm.value.stressId).subscribe((data: {}) => {
    this.stressTypeIdByCommodityIdAndStressId = data;
    this.condusiveWeatherForm.patchValue(this.stressTypeIdByCommodityIdAndStressId);
    this.condusiveWeatherForm.patchValue({stressTypeId:this.stressTypeIdByCommodityIdAndStressId.stressTypeId});
    this.getStressByCommodityIdAndStressTypeIdForEdit();
  })
}

getStateCodeAczIdByZonalCommodityId() {
  return this.zonalCommodityService.getStateCodeAczIdByZonalCommodityId(this.condusiveWeatherForm.value.zonalCommodityId).subscribe((data) => {
    this.stateCodeAczId = data;
    this.condusiveWeatherForm.patchValue(this.stateCodeAczId);
    this.getAczByStateCodeForEdit();
    this.getZonalCommodityByAczIdForEdit();
    this.condusiveWeatherForm.patchValue({zonalCommodityId:this.condusiveParam.zonalCommodityId});
    this.condusiveWeatherForm.patchValue({stressTypeId:this.stressTypeIdByCommodityIdAndStressId.stressTypeId});
  })
}


  submitCondusiveWeatherForm() {

    for (let controller in this.condusiveWeatherForm.controls) {
      this.condusiveWeatherForm.get(controller).markAsTouched();
    }

    if (this.condusiveWeatherForm.invalid) {
      return;
    }

    if (this.condusiveWeatherForm.get('zonalCommodityId').value == 0) {
      alert('Please Select Commodity');
      this.errorModal.showModal('ERROR', 'Please Select Zonal Commodity', '');

      return;
    }
   
    if (this.condusiveWeatherForm.get('stressTypeId').value == 0) {
      alert('Please Select Stress Type');
      this.errorModal.showModal('ERROR', 'Please Select Stress Type', '');

      return;
    }

    if (this.condusiveWeatherForm.get('stressId').value == 0) {
      alert('Please Select Stress Name');
      this.errorModal.showModal('ERROR', 'Please Select Stress Name', '');

      return;
    }

    if (this.condusiveWeatherForm.get('weatherParameterId').value == 0) {
      
      this.errorModal.showModal('ERROR', 'Please Select weather parameter', '');
      return;
    }

    // if (this.condusiveWeatherForm.get('secondaryWeatherParameterId').value == 0) {
    //   alert('Please Select Secondary weather parameter');
    //   return;
    // }

    

    console.log(this.condusiveWeatherForm.value);

    if (this.mode == "edit") {
      this.updateCondusiveWeather();
    } else {
      this.CreateCondusiveWeather();

    }
  }

  CreateCondusiveWeather() {
    this.condusiveParamService.CreateCondusiveWeather(this.condusiveWeatherForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.condusiveParam = {};
          this.mode = "add";
          this.CreateCondusiveWeatherForm();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    })
  }


  updateCondusiveWeather() {
    this.condusiveParamService.UpdateCondusiveWeather(this.editCondusiveWeatherId, this.condusiveWeatherForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.condusiveParam = {};
          this.mode = "add";
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    })
  }


  //Commodity list
  loadAllCommodities() {
    return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
      this.CommodityList = data;
    })
  }

  //Stress Type list
  loadAllStressType() {
    return this.stressTypeService.GetAllStressType().subscribe((data: {}) => {
      this.StressTypeList = data;
    })
  }

  //Stress Name list
  loadAllStress() {
    return this.zonalStressDurationService.GetAllStress().subscribe((data: {}) => {
      this.StressList = data;
    })
  }

  //WeatherParam Name list
  loadAllWeatherParam() {
    return this.generalWeatherParamService.GetAllAgriWeatherParams().subscribe((data: {}) => {
      this.weatherParamList = data;
    })
  }

  checkPrimarySpecification(val) {


    if (val) {

      this.condusiveWeatherForm.get('primarySpecificationAverage').setValidators([Validators.required]);
      this.condusiveWeatherForm.get('primarySpecificationAverage').updateValueAndValidity();


      this.condusiveWeatherForm.get('primarySpecificationLower').clearValidators();
      this.condusiveWeatherForm.get('primarySpecificationLower').updateValueAndValidity();

      this.condusiveWeatherForm.patchValue({ primarySpecificationLower: 0, primarySpecificationUpper: 0 })

      this.condusiveWeatherForm.get('primarySpecificationUpper').clearValidators();
      this.condusiveWeatherForm.get('primarySpecificationUpper').updateValueAndValidity();
    } else {

      this.condusiveWeatherForm.get('primarySpecificationAverage').clearValidators();
      this.condusiveWeatherForm.get('primarySpecificationAverage').updateValueAndValidity();

      this.condusiveWeatherForm.patchValue({ primarySpecificationAverage: 0 })

      this.condusiveWeatherForm.get('primarySpecificationLower').setValidators([Validators.required]);
      this.condusiveWeatherForm.get('primarySpecificationLower').updateValueAndValidity();

      this.condusiveWeatherForm.get('primarySpecificationUpper').setValidators([Validators.required]);
      this.condusiveWeatherForm.get('primarySpecificationUpper').updateValueAndValidity();

    }

  }

  checkSecondarySpecification(val) {

    if (val) {

      this.condusiveWeatherForm.get('secondarySpecificationAverage').setValidators([Validators.required]);
      this.condusiveWeatherForm.get('secondarySpecificationAverage').updateValueAndValidity();


      this.condusiveWeatherForm.get('secondarySpecificationLower').clearValidators();
      this.condusiveWeatherForm.get('secondarySpecificationLower').updateValueAndValidity();

      this.condusiveWeatherForm.patchValue({ secondarySpecificationLower: "", secondarySpecificationUpper: "" })

      this.condusiveWeatherForm.get('secondarySpecificationUpper').clearValidators();
      this.condusiveWeatherForm.get('secondarySpecificationUpper').updateValueAndValidity();
    } else {

      this.condusiveWeatherForm.get('secondarySpecificationAverage').clearValidators();
      this.condusiveWeatherForm.get('secondarySpecificationAverage').updateValueAndValidity();

      this.condusiveWeatherForm.patchValue({ secondarySpecificationAverage: "" })

      this.condusiveWeatherForm.get('secondarySpecificationLower').setValidators([Validators.required]);
      this.condusiveWeatherForm.get('secondarySpecificationLower').updateValueAndValidity();

      this.condusiveWeatherForm.get('secondarySpecificationUpper').setValidators([Validators.required]);
      this.condusiveWeatherForm.get('secondarySpecificationUpper').updateValueAndValidity();

    }

  }


  getCondusiveWeatherByCommodityIdAndStressTypeId() {
    if (this.condusiveWeatherForm.value.commodityId && this.condusiveWeatherForm.value.stressTypeId) {
      this.condusiveParamService.getCondusiveWeatherByCommodityIdAndStressTypeId(this.condusiveWeatherForm.value.commodityId, this.condusiveWeatherForm.value.stressTypeId).subscribe(res => {

        this.StressList = res;
        for (let subRes of this.StressList) {
          let control1: any = new FormControl();
          (this.condusiveWeatherForm.controls.selectedIndex as any).push(control1);
        }
      })
    } else {
      while ((this.condusiveWeatherForm.controls.selectedIndex as any).length !== 0) {
        (this.condusiveWeatherForm.controls.selectedIndex as any).removeAt(0)
      }
      this.StressList = [];
    }
  }

  getStressByCommodityIdAndStressTypeId() {

    
      for(let zonalCommodity of this.zonalCommodityList){
        if(this.condusiveWeatherForm.value.zonalCommodityId == zonalCommodity.id){
          this.commodityId = zonalCommodity.commodityId;
        }
      }
      
   
      return this.condusiveParamService.getStressByCommodityIdAndStressTypeId(this.commodityId, this.condusiveWeatherForm.value.stressTypeId).subscribe((data: {}) => {
        this.StressList = data;
      })
    
    
  }

  getStressByCommodityIdAndStressTypeIdForEdit() {

    // if(this.mode == "add"){
    //   for(let zonalCommodity of this.zonalCommodityList){
    //     if(this.condusiveWeatherForm.value.zonalCommodityId == zonalCommodity.id){
    //       this.commodityId = zonalCommodity.commodityId;
    //     }
    //   }
    // }
   
      return this.condusiveParamService.getStressByCommodityIdAndStressTypeId(this.commodityId, this.condusiveWeatherForm.value.stressTypeId).subscribe((data: {}) => {
        this.StressList = data;
      })
    
    
  }


  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  downloadExcelFormat(){
    var tableName = "agri_conducive_weather";
    this.apiUtilService.downloadExcelFormat(tableName);
  }//downloadExcelFormat


  uploadExcel(element) {
    var file: File = element.target.files[0];
    this.fileUpload = file;
  }

  submitExcelForm() {
    this.apiUtilService.uploadExcelFile(this.fileUpload).subscribe(res => {
      this.isSubmittedBulk = true;
     
      if(res){
        this.isSuccessBulk = res.success;
        if(res.success){
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

  
    modalSuccess($event: any) {
        this.router.navigate(['/zonal/conducive-weather']);
    }
}

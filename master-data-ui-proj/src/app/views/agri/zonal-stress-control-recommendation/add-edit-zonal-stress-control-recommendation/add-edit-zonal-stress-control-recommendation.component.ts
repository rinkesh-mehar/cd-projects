import { AgriAgrochemicalInstructionService } from '../../../stress/services/agri-agrochemical-instructions';
import {Component, OnInit, Input, Output, EventEmitter, SimpleChanges, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {ActivatedRoute, Route, Router} from '@angular/router';
import { AgriCommodityService } from '../../services/agri-commodity.service';
import { ZonalStressDurationService } from '../../services/zonal-stress-duration.service';
import { AgriControlMeasuresService } from '../../services/agri-control-measures.service';
import { ZonalStressControlRecommendationService } from '../../services/zonal-stress-control-recommendation.service';
import { AgriStressTypeService } from '../../services/agri-stress-type.service';
import { AgriCommodityAgrochemicalService } from '../../services/agri-commodity-agrochemical.service';
import { AgriAgroChemicalApplicationTypeService } from '../../services/agri-agro-chemical-application-type.service';
import { GeneralUomService } from '../../../general/services/general-uom.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { GeoDistrictService } from '../../../geo/services/geo-district.service';
import { GeoStateService } from '../../../geo/services/geo-state.service';
import { AgriStressControlMeasuresService } from '../../services/agri-stress-control-measures.service';
import { AgriRecommendationService } from '../../../stress/services/agri-recommendation.service';
import { AczService } from '../../../zonal/services/acz.service';
import { ZonalCommodityService } from '../../../zonal/services/zonal-commodity.service';

@Component({
  selector: 'app-add-edit-zonal-stress-control-recommendation.component',
  templateUrl: './add-edit-zonal-stress-control-recommendation.component.html',
  styleUrls: ['./add-edit-zonal-stress-control-recommendation.component.scss']
})
export class AddEditZonalStressControlRecommendationComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  stressControlRecommendationForm: FormGroup;

  commodityList: any;
  StressList: any;
  StressTypeList: any;
  AgrochemicalList: any;
  AgrochemicalApplictionList: any;
  StressControlMeasureList: any;
  UomList: any;
  editStressControlRecommendationId: any;
  mode: any = 'add';
  StressControlRecommendation: any;
  recommendationList: any;
  recommendarionName:any;
  agrochemicalInst:any;
  DistrictList: any = [];
  StateList: any = [];
  agrochemicalInstructionsList:any = [];
  // uploadFile: File = null;
  // imgPerview: any;

  // isSubmittedBulk: boolean = false;
  // isSuccessBulk: boolean = false;
  // fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  aczList: any = [];
  zonalCommodityList: any = [];
  stateCodeAczIdByZonalCommodityId : any;
  commodityId:any;

  constructor(public formBuilder: FormBuilder, private agriCommodityService: AgriCommodityService, private zonalStressDurationService: ZonalStressDurationService,
              public zonalStressControlRecommendationService: ZonalStressControlRecommendationService, private agriStressTypeService: AgriStressTypeService, private agriStressControlMeasureService: AgriControlMeasuresService,
              private agriAgrochemicalService: AgriCommodityAgrochemicalService, private AgriAgrochemicalApplictionService: AgriAgroChemicalApplicationTypeService, private generalUomService: GeneralUomService, private actRoute: ActivatedRoute,
              private router: Router,private agriRecommendationService: AgriRecommendationService, public geoDistrictService : GeoDistrictService,
              public geoStateService : GeoStateService, private agriAgrochemicalInstructionService: AgriAgrochemicalInstructionService,
              public agriStressControlMeasuresService: AgriStressControlMeasuresService, // public apiUtilService: ApiUtilService
              public aczService: AczService,
              public zonalCommodityService: ZonalCommodityService
  ) {


    this.createStressControlRecommendationForm();


  }
  numberPattern = '^[0-9][\.0-9]*$'

  getChanges() {
    console.log(this.stressControlRecommendationForm.value)
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  createStressControlRecommendationForm() {
    this.stressControlRecommendationForm = this.formBuilder.group({
      id: [],
      stateCode: ['', Validators.required],
      aczId: ['', Validators.required],
      zonalCommodityId: ['', Validators.required],
      stressControlMeasureId: ['', Validators.required],
      // stressTypeId: ['', Validators.required],
      stressId: ['', Validators.required],
      recomendationID: ['', Validators.required],
      // instructions: [''],
      // agroChemicalInstructions: [''],
      agrochemicalInstructionID: [''],
      agrochemicalId: [''],
      agrochemApplicationTypeId: [''],

      // dosePerHectare: [''],
      // dosePerHectareMin: ['',Validators.pattern(this.numberPattern)],
      // dosePerHectareMax: ['',Validators.pattern(this.numberPattern)],

      // perHectareUomId: [''],
      dosePerAcre: [''],
      perAcreUomId: [''],

      // waterPerHectare: [''],
      // waterPerHectareMin: ['',Validators.pattern(this.numberPattern)],
      // waterPerHectareMax: ['',Validators.pattern(this.numberPattern)],
      // perHectareWaterUomId: [''],
      waterPerAcre: [''],
      perAcreWaterUomId: [''],
      // instructions: ['', Validators.required],
      // agroChemicalInstructions: ['', Validators.required],
      // agrochemicalId: ['', Validators.required],
      // agrochemApplicationId: ['', Validators.required],

      // dosePerHectare: ['', Validators.required],
      // dosePerHectareMin: ['', Validators.required],
      // dosePerHectareMax: [''],

      // perHectareUomId: ['', Validators.required],
      // dosePerAcre: ['', Validators.required],
      // perAcreUomId: ['', Validators.required],

      // waterPerHectare: ['', Validators.required],
      // waterPerHectareMin: ['', Validators.required],
      // waterPerHectareMax: [''],
      // perHectareWaterUomId: ['', Validators.required],
      // waterPerAcre: ['', Validators.required],
      // perAcreWaterUomId: ['', Validators.required],
      status: ['Inactive']
    })

  }

  ngOnInit() {
    // this.loadAllCommodity();
    this.loadAllStress();
    // this.loadAllStressType();
    this.loadAllControlMeasure();
    // this.loadAllAgrochemical();
    this.loadAllAgrochemicalApplication();
    this.loadAllUom();
    this.getRecommendationList();
    this.loadAllState();
    this.getAgrochemicalInstructionsList();
    // this.loadAllStressByCommodity();

    this.editStressControlRecommendationId = this.actRoute.snapshot.paramMap.get('id');

    if (this.editStressControlRecommendationId) {

      this.mode = "edit";
      this.zonalStressControlRecommendationService.GetAgriStressControlRecommendation(this.editStressControlRecommendationId).subscribe(data => {
        this.StressControlRecommendation = data;
        this.stressControlRecommendationForm.patchValue(this.StressControlRecommendation);
        // alert(JSON.stringify(this.StressControlRecommendation));
        // if (this.StressControlRecommendation.dosePerHectare) {
        //   let dosePerHectare = this.StressControlRecommendation.dosePerHectare.split("-");
        //   try {
        //     this.stressControlRecommendationForm.patchValue({ dosePerHectareMin: dosePerHectare[0] })
        //     this.stressControlRecommendationForm.patchValue({ dosePerHectareMax: dosePerHectare[1] })

        //   } catch{ }
        // }
        // if (this.StressControlRecommendation.waterPerHectare) {
        //   let waterPerHectare = this.StressControlRecommendation.waterPerHectare.split("-");
        //   try {
        //     this.stressControlRecommendationForm.patchValue({ waterPerHectareMin: waterPerHectare[0] })
        //     this.stressControlRecommendationForm.patchValue({ waterPerHectareMax: waterPerHectare[1] })

        //   } catch{ }
        // }

        // this.getDataByCommodityAndStressType();
        // this.getAgrochemicalDataByCommodityAndStressType();
        // this.loadAllDistrict();
        
        this.getStateCodeAczIdByZonalCommodityId();
      })

    }

  }

   //StateList
   loadAllState(){
    return this.geoStateService.GetAllState().subscribe((data: {}) => {
      this.StateList = data;
      })
    }

    //Acz List
    loadAczByStateCode(){
      this.stressControlRecommendationForm.patchValue({aczId:''});
      return this.aczService.getAllAczByStateCode(this.stressControlRecommendationForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
      })
    }

    loadAczByStateCodeForEdit(){
      return this.aczService.getAllAczByStateCode(this.stressControlRecommendationForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
      })
    }

     //Zonal Commodity List
     loadZonalCommodityByAczId(){
      this.stressControlRecommendationForm.patchValue({zonalCommodityId:''});
      return this.zonalCommodityService.getZonalCommodityListByAczId(this.stressControlRecommendationForm.value.aczId).subscribe((data: {}) => {
       this.zonalCommodityList = data;
      })
    }

    loadZonalCommodityByAczIdForEdit(){
      return this.zonalCommodityService.getZonalCommodityListByAczId(this.stressControlRecommendationForm.value.aczId).subscribe((data: {}) => {
       this.zonalCommodityList = data;
      })
    }

    getCommodityIdByZonalCommodityId(){
     
        return this.zonalCommodityService.getCommodityIdByZonalCommodityId(this.stressControlRecommendationForm.value.zonalCommodityId).subscribe(data => {
          this.commodityId = data.commodityId;
          this.loadAllStressByCommodityId();
          this.findAgriAgrochemicalMasterByCommodityId();
         })
   
      
    }

    getCommodityIdByZonalCommodityIdForEdit(){

        return this.zonalCommodityService.getCommodityIdByZonalCommodityId(this.StressControlRecommendation.zonalCommodityId).subscribe(data => {
          this.commodityId = data.commodityId;
          this.loadAllStressByCommodityId();
          this.findAgriAgrochemicalMasterByCommodityIdForEdit();
         })
    
      
    }
    

    getStateCodeAczIdByZonalCommodityId() {
      return this.zonalCommodityService.getStateCodeAczIdByZonalCommodityId(this.stressControlRecommendationForm.value.zonalCommodityId).subscribe(data => {
        this.stateCodeAczIdByZonalCommodityId = data;
        this.stressControlRecommendationForm.patchValue(this.stateCodeAczIdByZonalCommodityId);
        this.loadAczByStateCodeForEdit();
        this.loadZonalCommodityByAczIdForEdit();
        this.getStressListAndAgrochemicalListByCommodityIdForEdit(); 
        // this.findAgriAgrochemicalMasterByCommodityId();
        this.stressControlRecommendationForm.patchValue({aczId:this.stateCodeAczIdByZonalCommodityId.aczId});
        this.stressControlRecommendationForm.patchValue({zonalCommodityId:this.StressControlRecommendation.zonalCommodityId});
        this.stressControlRecommendationForm.patchValue({stressId:this.StressControlRecommendation.stressId});
      })
    }

  loadAllCommodity() {
    return this.agriCommodityService.GetAllCommoditise().subscribe((data: any) => {
      this.commodityList = data;
    }, err => {
      alert(err)
    })
  }

  loadAllStress() {
    return this.zonalStressDurationService.GetAllStress().subscribe((data: any) => {
      this.StressList = data;
    }, err => {
      alert(err)
    })
  }

  loadAllStressType() {
    return this.agriStressTypeService.GetAllStressType().subscribe((data: any) => {
      this.StressTypeList = data;
    }, err => {
      alert(err)
    })
  }

  loadAllControlMeasure() {
    return this.agriStressControlMeasureService.GetAllAgriControlMeasures().subscribe((data: any) => {
      this.StressControlMeasureList = data;
    }, err => {
      alert(err)
    })
  }

  loadAllAgrochemical() {
    return this.agriAgrochemicalService.GetAllAgrochemicalMaster().subscribe((data: any) => {
      this.AgrochemicalList = data;
    }, err => {
      alert(err)
    })
  }

  loadAllAgrochemicalApplication() {
    return this.AgriAgrochemicalApplictionService.GetAllAgroChemicalApplictionType().subscribe((data: any) => {
      this.AgrochemicalApplictionList = data;
    }, err => {
      alert(err)
    })
  }


  loadAllUom() {
    return this.generalUomService.GetAllUom().subscribe((data: any) => {
      this.UomList = data;
    }, err => {
      alert(err)
    })
  }



  submitStressControlRecommendationForm() {

    

    for (let controller in this.stressControlRecommendationForm.controls) {
      this.stressControlRecommendationForm.get(controller).markAsTouched();
    }
    if (this.stressControlRecommendationForm.invalid) {
      return;
    }
    
    if (this.stressControlRecommendationForm.get('stressId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Stress', '');
      return;
    }

    if (this.stressControlRecommendationForm.get('zonalCommodityId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Zonal Commodity', '');

      return;
    }
    // if (this.stressControlRecommendationForm.get('stressTypeId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select StressSeverity', '');
    //   return;
    // }

    if (this.stressControlRecommendationForm.get('stressControlMeasureId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Stress Control Measure', '');
      return;
    }

    
    if (this.mode == "edit") {
      this.updateStressControlRecommendation();
    } else {
      this.addStressControlRecommendation();
    }
  }//submitStressControlRecommendationForm


  addStressControlRecommendation() {

    let formValue = this.stressControlRecommendationForm.value;

    // delete formValue.waterPerHectareMin;
    // delete formValue.waterPerHectareMax;

    // delete formValue.dosePerHectareMin;
    // delete formValue.dosePerHectareMax;

    console.log(formValue);


    this.zonalStressControlRecommendationService.CreateAgriStressControlRecommendation(this.stressControlRecommendationForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.StressControlRecommendation = {};
          this.mode = "add";
          this.createStressControlRecommendationForm();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    })
  }
  updateStressControlRecommendation() {
    this.stressControlRecommendationForm.patchValue({status:this.StressControlRecommendation.status});
    this.zonalStressControlRecommendationService.UpdateAgriStressControlRecommendation(this.editStressControlRecommendationId, this.stressControlRecommendationForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.StressControlRecommendation = {};
          this.mode = "add";

          this.createStressControlRecommendationForm();

          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    })

  }


  calculateDosePerAcr() {

    let dosePerHectareMin = this.stressControlRecommendationForm.value.dosePerHectareMin;
    let dosePerHectareMax = this.stressControlRecommendationForm.value.dosePerHectareMax;
    let dosePerAcre = "";
    let dosePerHectare = "";
    if (dosePerHectareMin && dosePerHectareMin > 0) {
      dosePerAcre = (dosePerHectareMin / 2.5).toFixed(2) + "";
      dosePerHectare = dosePerHectareMin;
    }
    if (dosePerHectareMax && dosePerHectareMax > 0) {
      dosePerAcre = dosePerAcre + "-" + (dosePerHectareMax / 2.5).toFixed(2) + "";
      dosePerHectare = dosePerHectare + "-" + dosePerHectareMax;
    }
    this.stressControlRecommendationForm.patchValue({ dosePerHectare: dosePerHectare, dosePerAcre: dosePerAcre })
  }

  calculateWaterPerAcr() {

    let waterPerHectareMin = this.stressControlRecommendationForm.value.waterPerHectareMin;
    let waterPerHectareMax = this.stressControlRecommendationForm.value.waterPerHectareMax;
    let waterPerAcre = "";
    let waterPerHectare = "";

    if (waterPerHectareMin && waterPerHectareMin > 0) {
      waterPerAcre = (waterPerHectareMin / 2.5).toFixed(2) + "";
      waterPerHectare = waterPerHectareMin;
    }
    if (waterPerHectareMax && waterPerHectareMax > 0) {
      waterPerAcre = waterPerAcre + "-" + (waterPerHectareMax / 2.5).toFixed(2) + "";
      waterPerHectare = waterPerHectare + "-" + waterPerHectareMax;
    }
    this.stressControlRecommendationForm.patchValue({ waterPerHectare: waterPerHectare, waterPerAcre: waterPerAcre })
  }

  // loadAllStressByCommodity(){
  //   let commodityId = this.stressControlRecommendationForm.value.commodityId;
  //   this.agriStressService.GetAllStressByCommodityId(commodityId).subscribe(
  //         (data: {}) => {
  //           this.StressList = data;
  //           console.logstateCode



  // getDataByCommodity() {
  //   if (this.stressControlRecommendationForm.value.commodityId) {
  //     this.agriStressService.getAllStressByCommodityId(this.stressControlRecommendationForm.value.commodityId).subscribe(res => {
  //       this.StressList = res;
  //     })
  //   } else {
  //     this.StressList = [];
  //   }
  // }

  getDataByCommodityAndStressType() {
    if (this.stressControlRecommendationForm.value.stressTypeId && this.stressControlRecommendationForm.value.commodityId) {
      this.zonalStressDurationService.GetAllStressByStressTypeId(this.stressControlRecommendationForm.value.commodityId, this.stressControlRecommendationForm.value.stressTypeId).subscribe(res => {
        this.StressList = res;
      })
    } else {
      this.StressList = [];
    }
  }

  getStressListAndAgrochemicalListByCommodityId() {
    this.stressControlRecommendationForm.patchValue({stressId:''});
    this.stressControlRecommendationForm.patchValue({agrochemicalId:''});
    this.getCommodityIdByZonalCommodityId();
  }

  getStressListAndAgrochemicalListByCommodityIdForEdit() {
    this.getCommodityIdByZonalCommodityIdForEdit();
  }

  loadAllStressByCommodityId(){
    return this.agriStressControlMeasuresService.GetAllStressByCommodity(this.commodityId).subscribe((data: any) => {
      this.StressList = data;
    }, err => {
      alert(err)
    })
  }

  getAgrochemicalDataByCommodityAndStressType() {
    if (this.stressControlRecommendationForm.value.commodityId && this.stressControlRecommendationForm.value.stressTypeId) {
      this.agriAgrochemicalService.GetAllAgrochemicalByStressTypeId(this.stressControlRecommendationForm.value.commodityId, this.stressControlRecommendationForm.value.stressTypeId).subscribe(res => {
        this.AgrochemicalList = res;
      })
    } else {
      this.AgrochemicalList = [];
    }
  }

  findAgriAgrochemicalMasterByCommodityId() {
    if (this.commodityId) {
      this.agriAgrochemicalService.findAgriAgrochemicalMasterByCommodityId(this.commodityId).subscribe(res => {
        this.AgrochemicalList = res;
        // this.stressControlRecommendationForm.patchValue({stressId:this.StressControlRecommendation.stressId});
      })
    } else {
      this.AgrochemicalList = [];
    }
  }

  findAgriAgrochemicalMasterByCommodityIdForEdit() {
    if (this.commodityId) {
      this.agriAgrochemicalService.findAgriAgrochemicalMasterByCommodityId(this.commodityId).subscribe(res => {
        this.AgrochemicalList = res;
        this.stressControlRecommendationForm.patchValue({stressId:this.StressControlRecommendation.stressId});
      })
    } else {
      this.AgrochemicalList = [];
    }
  }
  
  

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  getRecommendationList() {
    return this.agriRecommendationService.getRecommendationList().subscribe((data: {}) => {
      this.recommendationList = data;
    })
  }

  getAgrochemicalInstructionsList() {
    return this.agriAgrochemicalInstructionService.getAgrochemicalInstructionsList().subscribe((data: {}) => {
      this.agrochemicalInstructionsList = data;
    })
  }

  // downloadExcelFormat(){
  //   var tableName = "agri_stress_control_recommendation";
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

    modalSuccess($event: any) {
  this.router.navigate(['/zonal/stress-control-recommendation']);
    }

    onChangeRecommendationName(){
      for (var recommendation of this.recommendationList) {
        if(this.stressControlRecommendationForm.value.recomendationID == recommendation.id){
          this.recommendarionName = recommendation.name;
        }
        
      }
    }

    onChangeAgroChemicalInstructions(){
      for (var agrochemicalInstructions of this.agrochemicalInstructionsList) {
        if(this.stressControlRecommendationForm.value.agrochemicalInstructionID == agrochemicalInstructions.id){
          this.agrochemicalInst = agrochemicalInstructions.name;
        }
        
      }
    }

      //District list
 loadAllDistrict(){
  return this.geoDistrictService.GetAllDistrict().subscribe((data: {}) => {
    this.DistrictList = data;
  })
}

  //State list
  // loadAllState(){
  //   return this.geoStateService.GetAllState().subscribe((data: {}) => {
  //     this.StateList = data;
  //   })
  // }

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
}

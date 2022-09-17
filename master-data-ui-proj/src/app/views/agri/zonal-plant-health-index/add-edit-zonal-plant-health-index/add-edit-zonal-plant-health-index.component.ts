import { Component, OnInit, NgZone, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ZonalPlantHealthIndexService } from '../../services/zonal-plant-health-index.service';
import { ApiUtilService } from '../../../services/api-util.service';
import { GeoStateService } from '../../../geo/services/geo-state.service';
import { AgriCommodityService } from '../../services/agri-commodity.service';
import { AgriPhenophaseService } from '../../services/agri-phenophase.service';
import { AgriHealthParameterService } from '../../services/agri-health-parameter.service';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { AczService } from '../../../zonal/services/acz.service';
import { ZonalCommodityService } from '../../../zonal/services/zonal-commodity.service';
import { ZonalVarietyService } from '../../../regional/services/zonal-variety.service';

@Component({
  selector: 'app-add-edit-zonal-plant-health-index',
  templateUrl: './add-edit-zonal-plant-health-index.component.html',
  styleUrls: ['./add-edit-zonal-plant-health-index.component.scss']
})
export class AddEditZonalPlantHealthIndexComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  PlantHealthIndexForm: FormGroup;

  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  editId: string;

  StateList: any =[];
  commodityList: any =[];
  varietyList: any =[];
  phenophaseList: any =[];
  healthParameterList: any =[];



  stateList: any = [];
  aczList: any;
  zonalCommodityList: any;
  zonalVarietyList: any;
  stateCodeAczIdZonalCommodityId: any = [];
  mode: string="add";
  editPlantHealthIndexId : any;
  PlantHealthIndex : any;
  constructor(
    public fb: FormBuilder,
    public zonalPlantHealthIndexService: ZonalPlantHealthIndexService,
    private actRoute: ActivatedRoute,
    public apiUtilService: ApiUtilService,
    private geoStateService: GeoStateService, 
    private agriCommodityService: AgriCommodityService,
    private agriHealthParamaterService: AgriHealthParameterService,
    public aczService: AczService,
    private zonalCommodityService: ZonalCommodityService,
     private zonalVarietyService : ZonalVarietyService,
     private agriPhenophaseService :AgriPhenophaseService,
    public router: Router

  ) { }

formGrp(){
  this.PlantHealthIndexForm = this.fb.group({
    id: [],
    // name: ['', Validators.required],
    stateCode: ['', Validators.required],
    aczId: ['', Validators.required],
    zonalCommodityId: ['', Validators.required],
    zonalVarietyId: ['', Validators.required],
    phenophaseId: ['', Validators.required],
    // healthParameter: ['', Validators.required],
    normalValue: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
    idealValue: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
    specifications: ['', Validators.required],
    healthParameterId: ['', Validators.required],
    // value: ['', Validators.required],
    status: ['Inactive']

  })
}


  ngOnInit() {

    this.getState();
    this.getHealthParameterList();
    // this.loadAllCommodity();
    // // this.loadAllPhenophase();
    // this.loadAllHealthParameter();

    this.formGrp();

    this.editPlantHealthIndexId = this.actRoute.snapshot.paramMap.get('id');
    if (this.editPlantHealthIndexId) {
      this.mode = "edit";
      this.zonalPlantHealthIndexService.GetPlantHealthIndex(this.editPlantHealthIndexId).subscribe(data => {

        this.PlantHealthIndex = data;
        this.PlantHealthIndexForm = this.fb.group({
          id: [],
          // name: ['', Validators.required],
          stateCode: ['', Validators.required],
          aczId: ['', Validators.required],
          zonalCommodityId: ['', Validators.required],
          zonalVarietyId: ['', Validators.required],
          phenophaseId: ['', Validators.required],
          // healthParameter: ['', Validators.required],
          normalValue: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
          idealValue: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
          specifications: ['', Validators.required],
          healthParameterId: ['', Validators.required],
          // value: ['', Validators.required],
          status: [''],
        })      
        this.PlantHealthIndexForm.patchValue({ normalValue: this.PlantHealthIndex.normalValue});
        this.PlantHealthIndexForm.patchValue({ idealValue: this.PlantHealthIndex.idealValue});
        this.PlantHealthIndexForm.patchValue({ specifications: this.PlantHealthIndex.specifications});
        // this.PlantHealthIndexForm.patchValue({ healthParameterId: this.PlantHealthIndex.healthParameterId});
        this.PlantHealthIndexForm.patchValue(this.PlantHealthIndex);

        // this.PlantHealthIndexForm.patchValue(data);
        this.getStateCodeAczIdZonalCommodityIdByZonalVarietyId();
        // this.getVarietyListByCommodity();
        // this.getPhenophaseListByCommodityAndVariety();
      })
    }
  }



  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  submitPlantHealthIndexForm() {

    for (let controller in this.PlantHealthIndexForm.controls) {
      this.PlantHealthIndexForm.get(controller).markAsTouched();
    }
    if (this.PlantHealthIndexForm.invalid) {
      return;
    }
    if (this.mode == 'add') {
      this.addPlantHealthIndex();
    } else {
      this.updatePlantHealthIndex();
    }
  }

  addPlantHealthIndex() {

    this.zonalPlantHealthIndexService.CreatePlantHealthIndex(this.PlantHealthIndexForm.value).subscribe(res => {
      this.isSubmitted = true;
      if(res) {
      
        this.isSuccess = res.success;
        if (res.success) {
          this.PlantHealthIndex = {};
         
          // this.PlantHealthIndexForm.reset();
          // this.formGrp();
          this.mode="add";
          this.formGrp();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
      // if (res) {
      //   this.isSuccess = res.success;
      //   if (res.success) {
      //     this._statusMsg = res.message;
      //     this.PlantHealthIndexForm.reset();
      //     this.formGrp();
      //     this.mode="add";
      //     setTimeout(() => {
      //       this.isSubmitted = false;
      //       this.isSuccess = false
      //     }, 4000)
      //   } else {
      //     this._statusMsg = res.error;
      //   }
      // }
    });
  }

  updatePlantHealthIndex() {
    this.PlantHealthIndexForm.patchValue({status:this.PlantHealthIndex.status});
    this.zonalPlantHealthIndexService.UpdatePlantHealthIndex(this.editPlantHealthIndexId, this.PlantHealthIndexForm.value).subscribe(res => {
      this.isSubmitted = true;
      if(res) {
      
        this.isSuccess = res.success;
        if (res.success) {
          this.PlantHealthIndexForm.reset();
          this.formGrp();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
      
      // if (res) {
      //   this.isSuccess = res.success;
      //   if (res.success) {
      //     this._statusMsg = res.message;
      //     this.PlantHealthIndexForm.reset();
      //     this.formGrp();
      //     setTimeout(() => {
      //       this.isSubmitted = false;
      //       this.isSuccess = false
      //     }, 4000)
      //   } else {
      //     this._statusMsg = res.error;
      //   }
      // }
    });
  }

  getState(){

    return this.geoStateService.GetAllState().subscribe((data: {}) => {
      this.stateList = data;
    })
  }
  



  getAllAczByStateCode() {
    this.PlantHealthIndexForm.patchValue({aczId: ''});
    this.aczService.getAllAczByStateCode(this.PlantHealthIndexForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }

  getAllAczByStateCodeForEditMode() {
    this.aczService.getAllAczByStateCode(this.PlantHealthIndexForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }

  getZonalCommodityListByAczId() {
    this.PlantHealthIndexForm.patchValue({zonalCommodityId: ''});
     this.zonalCommodityService.getZonalCommodityListByAczId(this.PlantHealthIndexForm.value.aczId).subscribe((data: {}) => {
      this.zonalCommodityList = data;
    })
  }

  getZonalCommodityListByAczIdForEditMode() {
    this.zonalCommodityService.getZonalCommodityListByAczId(this.PlantHealthIndexForm.value.aczId).subscribe((data: {}) => {
     this.zonalCommodityList = data;
   })
 }


  getZonalVarietyListByZonalCommodityId() {
    this.PlantHealthIndexForm.patchValue({zonalVarietyId: ''});
      this.zonalVarietyService.getZonalVarietyListByZonalCommodityId(this.PlantHealthIndexForm.value.zonalCommodityId).subscribe((data: {}) => {
        this.zonalVarietyList = data;
      })
  }

  getZonalVarietyListByZonalCommodityIdForEditMode() {
    this.zonalVarietyService.getZonalVarietyListByZonalCommodityId(this.PlantHealthIndexForm.value.zonalCommodityId).subscribe((data: {}) => {
      this.zonalVarietyList = data;
    })
}

  getPhenophaseListByZonalVarietyId()
  {
    this.PlantHealthIndexForm.patchValue({phenophaseId: ''});
    if(this.mode=="add")
    {
      this.agriPhenophaseService.getPhenophaseListByZonalVarietyId(this.PlantHealthIndexForm.value.zonalVarietyId).subscribe((data: {}) => {
        this.phenophaseList = data;
      })
    }
    else{
      this.agriPhenophaseService.getPhenophaseListByZonalVarietyId(this.PlantHealthIndex.zonalVarietyId).subscribe((data: {}) => {
        this.phenophaseList = data;
      })
    }
 
  }
  getPhenophaseListByZonalVarietyIdForEditMode()
  {
    this.agriPhenophaseService.getPhenophaseListByZonalVarietyId(this.PlantHealthIndex.zonalVarietyId).subscribe((data: {}) => {
      this.phenophaseList = data;
  })
}
getHealthParameterList()
{
  this.agriHealthParamaterService.GetAllHealthParameter().subscribe((data: {}) => {
    this.healthParameterList =data;
  })
}
  
  

  getStateCodeAczIdZonalCommodityIdByZonalVarietyId(){
    return this.zonalCommodityService.getStateCodeAczIdZonalCommodityIdByZonalVarietyId(this.PlantHealthIndexForm.value.zonalVarietyId).subscribe(data => {
        
        this.stateCodeAczIdZonalCommodityId = data;
        this.PlantHealthIndexForm.patchValue(this.stateCodeAczIdZonalCommodityId);

        this.getAllAczByStateCodeForEditMode();
        this.getZonalCommodityListByAczIdForEditMode();
        this.getZonalVarietyListByZonalCommodityIdForEditMode();
        this.getPhenophaseListByZonalVarietyIdForEditMode();
        this.PlantHealthIndexForm.patchValue({ zonalVarietyId: this.PlantHealthIndex.zonalVarietyId});

   })
  }

  // loadAllCommodity() {
  //   return this.agriCommodityService.GetAllCommoditise().subscribe((data: any) => {
  //     this.commodityList = data;
  //   }, err => {
  //     alert(err)
  //   })
  // }

  // loadAllHealthParameter() {
  //   return this.agriHealthParamaterService.GetAllHealthParameter().subscribe((data: any) => {
  //     this.healthParameterList = data;
  //   }, err => {
  //     alert(err)
  //   })
  // }

  // getVarietyListByCommodity() {
  //   let commodityId : number = this.PlantHealthIndexForm.value.commodityID; console.log(commodityId)
  //   this.agriPlantHealthIndexService.getVarietyListByCommodity(commodityId).subscribe(
  //     (data: {}) => {
  //       this.varietyList = data;
  //       console.log(this.varietyList);
  //     }
  //   );
  // }

  // getPhenophaseListByCommodityAndVariety(){
  //   let commodityId : number = this.PlantHealthIndexForm.value.commodityID; console.log(commodityId)
  //   let varietyId : number = this.PlantHealthIndexForm.value.varietyID; console.log(varietyId)
  //   this.agriPlantHealthIndexService.getPhenophaseListByCommodityAndVariety(commodityId,varietyId).subscribe(
  //     (data: {}) => {
  //       this.phenophaseList = data;
  //       console.log(this.phenophaseList);
  //     }
  //   );
  // }



  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  downloadExcelFormat() {
    var tableName = "agri_plant_health_index";
    this.apiUtilService.downloadExcelFormat(tableName);
  }//downloadExcelFormat


  uploadExcel(element) {
    var file: File = element.target.files[0];
    this.fileUpload = file;
  }

  submitExcelForm() {
    this.apiUtilService.uploadExcelFile(this.fileUpload).subscribe(res => {
      this.isSubmittedBulk = true;
      if(res) {
      
        this.isSuccess = res.success;
        if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
      // if (res) {
      //   this.isSuccessBulk = res.success;
      //   if (res.success) {
      //     this._statusMsg = res.message;

      //     this.delay(4000).then(any => {
      //       this.isSubmittedBulk = false;
      //       this.isSuccessBulk = false;
      //     });
      //   } else {
      //     this._statusMsg = res.error;
      //   }
      // }
    });
  }
  modalSuccess($event: any) {
    this.router.navigate(['/zonal/plant-health-index']);
    }
}

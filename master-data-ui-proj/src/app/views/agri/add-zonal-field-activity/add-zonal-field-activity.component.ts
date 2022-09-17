import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { Router } from '@angular/router';

import { AgriPhenophaseService } from '../services/agri-phenophase.service';
// import { GeoRegionService } from '../../geo/services/geo-region.service';
import { AgriSeasonService } from '../services/agri-season.service';
import { ZonalFieldActivityService } from '../services/zonal-field-activity.service';
import { ApiUtilService } from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { fileSizeValidatorForDoc } from '../../validators/fileSizeValidator.validator';
import { GeoStateService } from '../../geo/services/geo-state.service';
import { AczService } from '../../zonal/services/acz.service';
import { ZonalCommodityService } from '../../zonal/services/zonal-commodity.service';
import { ZonalVarietyService } from '../../regional/services/zonal-variety.service';

@Component({
  selector: 'app-add-zonal-field-activity',
  templateUrl: './add-zonal-field-activity.component.html',
  styleUrls: ['./add-zonal-field-activity.component.scss']
})
export class AddZonalFieldActivityComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  CommodityList: any = [];
  // RegionList: any = [];
  stateList : any = [];
  aczList : any = [];
  SeasonList: any = [];
  zonalCommodityList:any = [];
  // zonalVarietyList:any = [];
  phenophaseList: any = [];
  fieldActivityForm: FormGroup;
  fieldActivityArr: any = [];
  uploadFile: File = null;
  imgPerview: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any; 
  uploadedImage: any;


  ngOnInit() {
    // this.loadAllRegion();
    // this.loadAllCommodities();
    this.getState();
    // this.loadAllPhenophase();
    this.addFieldActivity()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public zonalFieldActivityService: ZonalFieldActivityService,
    public commodityService: AgriCommodityService,
    // public geoRegionService : GeoRegionService,
    public agriPhenophaseService: AgriPhenophaseService,
    public agriSeasonService: AgriSeasonService,
    public apiUtilService: ApiUtilService,
    public geoStateService:GeoStateService,
    public aczService :AczService,
    public zonalCommodityService: ZonalCommodityService,
     public zonalVarietyService: ZonalVarietyService
  ) { }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  addFieldActivity() {
    this.fieldActivityForm = this.fb.group({
      stateCode : ['', Validators.required],
      // stateName : ['', Validators.required],
      // seasonID : ['', Validators.required],
      aczId : ['', Validators.required],
      zonalCommodityId : ['', Validators.required],
      // zonalVarietyId : ['', Validators.required],
      phenophaseId: ['', Validators.required],
      name: ['', Validators.required],
     description: [''],
      imageFile: ['', Validators.required],
      status: ['Inactive']
    })
    
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

 //statelist
 getState() {
  return this.geoStateService.GetAllState().subscribe((data: {}) => {
  this.stateList = data;
  })
}

//ACZ list
getAczByStateCode() {
  this.fieldActivityForm.patchValue({aczId:''});
  return this.aczService.getAllAczByStateCode(this.fieldActivityForm.value.stateCode).subscribe((data: {}) => {
    this.aczList = data;
  })
}


//ZonalCommodity list
getZonalCommodityByAczId() {
  this.fieldActivityForm.patchValue({zonalCommodityId:''});
  return this.zonalCommodityService.getZonalCommodityListByAczId(this.fieldActivityForm.value.aczId).subscribe((data: {}) => {
    this.zonalCommodityList = data;
  })
}

//ZonalVariety List
// getZonalVarietyByZonalCommodity() {
//   return this.zonalVarietyService.getZonalVarietyListByZonalCommodityId(this.fieldActivityForm.value.zonalCommodityId).subscribe((data: {}) => {
//     this.zonalVarietyList = data;
//   })
// }


 //Phenophase List by Zonal Variety
 getPhenophaseListByZonalCommodityId() {
  this.fieldActivityForm.patchValue({phenophaseId:''});
  return this.agriPhenophaseService.getPhenophaseListByZonalCommodityId(this.fieldActivityForm.value.zonalCommodityId).subscribe((data: {}) => {
    this.phenophaseList = data;
  })
}






  submitForm() {

    for (let controller in this.fieldActivityForm.controls) {
      this.fieldActivityForm.get(controller).markAsTouched();
    }

    if (this.fieldActivityForm.invalid) {
      return;
    }


    // if(this.fieldActivityForm.get('regionId').value == 0){
    //   alert('Please Select Region');
    //   return;
    // }

    // if (this.fieldActivityForm.get('commodityId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select Commodity', '');
    //   return;
    // }


    // if (this.fieldActivityForm.get('seasonId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select Season', '');
    //   return;
    // }

    // if (this.fieldActivityForm.get('phenophaseId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select Phenophase', '');
    //   return;
    // }


    
    this.zonalFieldActivityService.CreateFieldActivity(this.fieldActivityForm.value,this.uploadedImage).subscribe(res => {
      this.isSubmitted = true;

      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.addFieldActivity();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

  //  //Region list
  //  loadAllRegion(){
  //   return this.geoRegionService.GetAllRegion().subscribe((data: {}) => {
  //     this.RegionList = data;
  //   })
  // }

  //Commodity list
  // loadAllCommodities() {
  //   return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
  //     this.CommodityList = data;
  //   })
  // }

  //Season list
  // loadAllSeason() {
  //   return this.agriSeasonService.GetAllSeasons().subscribe((data: {}) => {
  //     this.SeasonList = data;
  //   })
  // }

  //Phenophase list
  // loadAllPhenophase() {
  //   return this.agriPhenophaseService.GetAllPhenophase().subscribe((data: {}) => {
  //     this.PhenophaseList = data;
  //   })
  // }

  // loadAllCommodityBySeason() {
  //   let seasonId = this.fieldActivityForm.value.seasonId;
  //   this.commodityService.getCommodityBySeasonId(seasonId).subscribe(
  //     (data: {}) => {
  //       this.CommodityList = data;
  //       console.log(this.CommodityList);
  //     }
  //   );
  // }
  // loadAllCommodityByPhenophase() {
  //   let phenophaseId = this.fieldActivityForm.value.commodityId;
  //   this.commodityService.getCommodityByPhenophaseId(phenophaseId).subscribe(
  //     (data: {}) => {
  //       this.PhenophaseList = data;
  //       console.log(this.PhenophaseList);
  //     }
  //   );
  // }


  downloadExcelFormat(){
    var tableName = "agri_field_activity";
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

  onImageChange(element){
    let file: File = element.target.files[0];
    // console.log("Size : ", this.uploadedLogo.size);
    this.fieldActivityForm.get('imageFile').setValidators([Validators.required, fileSizeValidatorForDoc(element.target.files)]);
    this.fieldActivityForm.get('imageFile').updateValueAndValidity();
    this.uploadedImage = file;
    // this.logo = this.uploadedLogo.name;
  }

    modalSuccess($event: any) {
        this.router.navigate(['/zonal/field-activity']);
    }
}

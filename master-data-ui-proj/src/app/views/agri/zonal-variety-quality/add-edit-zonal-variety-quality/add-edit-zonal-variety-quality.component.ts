import { AczService } from '../../../zonal/services/acz.service';
import { ZonalVarietyService } from '../../../regional/services/zonal-variety.service';
import {Component, OnInit, NgZone, ViewChild, AfterViewChecked, AfterViewInit} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import {ActivatedRoute, Router} from '@angular/router';
import { ZonalVarietyQualityService } from '../../services/zonal-variety-quality.service';
import { AgriCommodityService } from '../../services/agri-commodity.service';
import { AgriVarietyService } from '../../services/agri-variety.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { GeoStateService } from '../../../geo/services/geo-state.service';
import {Observable} from 'rxjs';
import {catchError, retry} from 'rxjs/operators';
import {BandService} from '../../../commodity/service/band.service';
import {GeoRegionService} from '../../../geo/services/geo-region.service';
import { ZonalCommodityService } from '../../../zonal/services/zonal-commodity.service';
import { ZonalCommodity } from '../../../zonal/models/ZonalCommodity';


@Component({
  selector: 'app-add-edit-zonal-variety-quality',
  templateUrl: './add-edit-zonal-variety-quality.component.html',
  styleUrls: ['./add-edit-zonal-variety-quality.component.scss']
})
export class AddEditZonalVarietyQualityComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  VarietyQualityForm: FormGroup;
 

  stateList: any = [];
  aczList: any;
  zonalCommodityList: any;
  zonalVarietyList: any;
  stateCodeAczIdZonalCommodityId: any = [];

  editVarietyQualityId: any;
  mode: any = 'add';
  VarietyQuality: any;
  uploadFile: File = null;
  imgPerview: any;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;
  bandList: any;

  constructor( public formBuilder: FormBuilder,public geoStateService: GeoStateService,public aczService: AczService,
     private zonalCommodityService: ZonalCommodityService, private zonalVarietyService : ZonalVarietyService,
     public zonalVarietyQualityService: ZonalVarietyQualityService, private actRoute: ActivatedRoute,public apiUtilService: ApiUtilService,
     public router: Router, private bandService: BandService, private geoRegionService: GeoRegionService) {


    this.createVarietyQualityForm();


  }
  getChanges() {
    console.log(this.VarietyQualityForm.value)
  }

  // loadRegion() {
  //   if (this.VarietyQualityForm.value.stateCode) {
  //     this.geoStateService.getStateWiseRegion(this.VarietyQualityForm.value.stateCode).subscribe((data: {}) => {
  //       this.regionList = data;
  //     });
  //   } else {
  //     this.varietyList = [];
  //   }
  // }

  // loadAllRegion() {
  //   this.geoRegionService.GetAllRegion().subscribe((data: {}) => {
  //     this.regionList = data;
  //   });
  // }

  getBandList() {
    this.bandService.getBandList().subscribe((data: {}) => {
      this.bandList = data;
    });
  }

  // loadCommodity(id) {
  //   this.geoStateService.getStateWiseCommodity(id).subscribe((data: {}) => {
  //     this.commodityList = data;
  //   })
  // }


  

  createVarietyQualityForm() {
    this.VarietyQualityForm = this.formBuilder.group({
      id: [],
      stateCode: ['', Validators.required],
      aczId: ['', Validators.required],
      zonalCommodityId: ['', Validators.required],
      zonalVarietyId: ['', Validators.required],
      allowableVarianceInQuality: ['', Validators.required],
      // estimatedQualityGrade: ['', Validators.required],
      // allowableVarianceInQualityGradePositive: ['', Validators.required],
      // allowableVarianceInQualityGradeNegative: ['', Validators.required],
      currentQuality: ['', Validators.required],
      estimatedQuality: ['', Validators.required],
      status: ['Inactive'],
      isBenchmark: ['', Validators.required]
     
   
      })
  }

  ngOnInit() {
    this.getAllState();

    // this.loadAllCommodity();
    this.getBandList();
  //  this.loadAllVariety();
 
  
    this.editVarietyQualityId = this.actRoute.snapshot.paramMap.get('id');

    if (this.editVarietyQualityId) {

      this.mode = "edit";
      this.zonalVarietyQualityService.GetVarietyQuality(this.editVarietyQualityId).subscribe(data => {
        this.VarietyQuality = data;
        this.VarietyQualityForm = this.formBuilder.group({
          id: [],
          stateCode: ['', Validators.required],
          aczId: ['', Validators.required],
          zonalCommodityId: ['', Validators.required],
          zonalVarietyId: ['', Validators.required],
          allowableVarianceInQuality: ['', Validators.required],
          // estimatedQualityGrade: ['', Validators.required],
          // allowableVarianceInQualityGradePositive: ['', Validators.required],
          // allowableVarianceInQualityGradeNegative: ['', Validators.required],
          currentQuality: ['', Validators.required],
          estimatedQuality: ['', Validators.required],
          status: [''],
          isBenchmark: ['', Validators.required]
        })      
        
        this.VarietyQualityForm.patchValue({ currentQuality: this.VarietyQuality.currentQuality});
        this.VarietyQualityForm.patchValue({ estimatedQuality: this.VarietyQuality.estimatedQuality});
        this.VarietyQualityForm.patchValue(this.VarietyQuality);

        this.getStateCodeAczIdZonalCommodityIdByZonalVarietyId();
        
      })


    }

  }


  // loadAllVariety() {
  //   return this.agriVarietyService.GetAllVarieties().subscribe((data: any) => {
  //     this.varietyList = data;
  //   }, err => {
  //     alert(err)
  //   })
  // }
  submitVarietyQualityForm() {


    for (let controller in this.VarietyQualityForm.controls) {
      this.VarietyQualityForm.get(controller).markAsTouched();
    }
    if (this.VarietyQualityForm.invalid) {
      return;
    }

    // if(this.VarietyQualityForm.get('zonalVarietyId').value == 0){
    //   this.errorModal.showModal('ERROR', 'Please Select Zonal Variety', '');
    //   return;
    // }

    // if(this.VarietyQualityForm.get('zonalCommodityId').value == 0){
    //   this.errorModal.showModal('ERROR', 'Please Select Zonal Commodity', '');
    //   return;
    // }
    
    if (this.mode == "edit") {
      this.updateVarietyQuality();
    } else {
      this.addVarietyQuality();
    }
  }
  addVarietyQuality() {
    this.zonalVarietyQualityService.CreateVarietyQuality(this.VarietyQualityForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.VarietyQuality = {};
          this.mode = "add";
          this.createVarietyQualityForm();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    });
  }
  updateVarietyQuality() {
    this.VarietyQualityForm.patchValue({status:this.VarietyQuality.status});
    this.zonalVarietyQualityService.UpdateVarietyQuality(this.editVarietyQualityId, this.VarietyQualityForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.VarietyQuality = {};
          this.mode = "add";
          this.createVarietyQualityForm();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    })
  }nInit() {
  }


  getAllState() {
    this.geoStateService.GetAllState().subscribe((data: {}) => {
      this.stateList = data;
    })
  }

  getAllAczByStateCode() {
    this.VarietyQualityForm.patchValue({aczId:''});
    this.aczService.getAllAczByStateCode(this.VarietyQualityForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }

  getAllAczByStateCodeForEdit() {
   
    this.aczService.getAllAczByStateCode(this.VarietyQualityForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }

  getZonalCommodityListByAczId() {
    this.VarietyQualityForm.patchValue({zonalCommodityId:''});
     this.zonalCommodityService.getZonalCommodityListByAczId(this.VarietyQualityForm.value.aczId).subscribe((data: {}) => {
      this.zonalCommodityList = data;
    })
  }

  getZonalCommodityListByAczIdForEdit() {
    this.zonalCommodityService.getZonalCommodityListByAczId(this.VarietyQualityForm.value.aczId).subscribe((data: {}) => {
     this.zonalCommodityList = data;
   })
 }

  getZonalVarietyListByZonalCommodityId() {
    this.VarietyQualityForm.patchValue({zonalVarietyId:''});
      this.zonalVarietyService.getZonalVarietyListByZonalCommodityId(this.VarietyQualityForm.value.zonalCommodityId).subscribe((data: {}) => {
        this.zonalVarietyList = data;
      })
  }

  getZonalVarietyListByZonalCommodityIdForEdit() {
    this.zonalVarietyService.getZonalVarietyListByZonalCommodityId(this.VarietyQualityForm.value.zonalCommodityId).subscribe((data: {}) => {
      this.zonalVarietyList = data;
    })
}

  getStateCodeAczIdZonalCommodityIdByZonalVarietyId(){
    return this.zonalCommodityService.getStateCodeAczIdZonalCommodityIdByZonalVarietyId(this.VarietyQualityForm.value.zonalVarietyId).subscribe(data => {
        
        this.stateCodeAczIdZonalCommodityId = data;
        this.VarietyQualityForm.patchValue(this.stateCodeAczIdZonalCommodityId);

        this.getAllAczByStateCodeForEdit();
        this.getZonalCommodityListByAczIdForEdit();
        this.getZonalVarietyListByZonalCommodityIdForEdit();

        this.VarietyQualityForm.patchValue({ zonalVarietyId: this.VarietyQuality.zonalVarietyId});

   })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }


  downloadExcelFormat(){
    var tableName = "agri_variety_quality";
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
        this.router.navigate(['/zonal/variety-quality']);
    }
}

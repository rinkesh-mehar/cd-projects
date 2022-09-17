import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';

import { AgriCommodityService } from '../services/agri-commodity.service';
import { Router } from '@angular/router';
import { AgriPhenophaseService } from '../services/agri-phenophase.service';
import { AgriCommodityStressStageService } from '../services/agri-commodity-stress-stage.service';
import { ZonalStressDurationService } from '../services/zonal-stress-duration.service';
import { AgriStressTypeService } from '../services/agri-stress-type.service';
import { ApiUtilService } from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { AgriStressControlMeasuresService } from '../services/agri-stress-control-measures.service';

@Component({
  selector: 'app-add-agri-stress-stage',
  templateUrl: './add-agri-commodity-stress-stage.component.html',
  styleUrls: ['./add-agri-commodity-stress-stage.component.scss']
})
export class AddAgriCommodityStressStageComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  CommodityList: any = [];
  PhenophaseList: any = [];
  StressTypeList: any = [];
  StressList: any = [];
  commodityStressStageForm: FormGroup;
  stressStageArr: any = [];
  startWeekList: number[] = [];
  EndWeekList: number[] = [];
  StageList: any = [];
  imgPerview: any;
  uploadFile: File;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  ngOnInit() {
    this.loadAllCommodities();
    // this.loadAllPhenophase();
    this.loadAllStressType();
    this.loadAllStress();
    this.addStressStage()
    for (let i = 1; i <= 52; i++) {
      this.startWeekList.push(i);
      this.EndWeekList.push(i);
    }
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public agriCommodityStressStageService: AgriCommodityStressStageService,
    public commodityService: AgriCommodityService,
    public agriPhenophaseService: AgriPhenophaseService,
    public agriStressTypeService: AgriStressTypeService,
    public zonalStressDurationService: ZonalStressDurationService,
    public apiUtilService: ApiUtilService,
    public agriStressControlMeasuresService: AgriStressControlMeasuresService
  ) { }

  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  addStressStage() {
    this.commodityStressStageForm = this.fb.group({
      id: [''],
      commodityId: ['', Validators.required],
      // stressTypeId: ['', Validators.required],
      stressId: ['', Validators.required],
      stageId: ['', Validators.required],
      // name: ['', Validators.required],
      description: ['', Validators.required],
      startPhenophaseId: ['', Validators.required],
      endPhenophaseId: ['', Validators.required],
      status: ['Inactive']
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {


    for (let controller in this.commodityStressStageForm.controls) {
      this.commodityStressStageForm.get(controller).markAsTouched();
    }

    if (this.commodityStressStageForm.invalid) {
      return;
    }

    if (this.commodityStressStageForm.get('commodityId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Commodity', '');
      return;
    }


    // if (this.stressStageForm.get('stressTypeId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select Stress Type', '');
    //   return;
    // }

    if (this.commodityStressStageForm.get('stressId').value == 0) {

      this.errorModal.showModal('ERROR', 'Please Select Stress', '');
      return;
    }




    this.agriCommodityStressStageService.CreateCommodityStressStage(this.commodityStressStageForm.value).subscribe(res => {
      this.isSubmitted = true;

      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

  getStressStagByCommodityIdAndStressTypeId() {
    if (this.commodityStressStageForm.value.commodityId && this.commodityStressStageForm.value.stressTypeId) {
      this.agriCommodityStressStageService.getStressStagByCommodityIdAndStressTypeId(this.commodityStressStageForm.value.commodityId, this.commodityStressStageForm.value.stressTypeId).subscribe(res => {

        this.StressList = res;
      })
    } else {

      this.StressList = [];
    }
  }

  loadAllStressByCommodity(event) {
    console.log('commodityId',event.target['value']);
    return this.agriStressControlMeasuresService.GetAllStressByCommodity(event.target['value']).subscribe((data: any) => {
      this.StressList = data;
    }, err => {
      alert(err)
    })
  }

    loadAllCommodityByPhenophase() {
      const commodityId = this.commodityStressStageForm.value.commodityId;
      this.commodityService.getCommodityByPhenophaseId(commodityId).subscribe(
          (data: {}) => {
            this.PhenophaseList = data;
            console.log(this.PhenophaseList);
          }
      );
  }


  //Commodity list
  loadAllCommodities() {
    return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
      this.CommodityList = data;
    })
  }

  //Phenophase list
  loadAllPhenophase() {
    return this.agriPhenophaseService.GetAllPhenophase().subscribe((data: {}) => {
      this.PhenophaseList = data;
    })
  }

  //Stress Type list
  loadAllStressType() {
    return this.agriStressTypeService.GetAllStressType().subscribe((data: {}) => {
      this.StressTypeList = data;
    })
  }

  //Stress list
  loadAllStress() {
    return this.zonalStressDurationService.GetAllStress().subscribe((data: {}) => {
      this.StressList = data;
    })
  }


  getStageListByStressId() {
    return this.agriCommodityStressStageService.getStageListByStressId(this.commodityStressStageForm.value.stressId).subscribe((data: any) => {
      this.StageList = data;
    }, err => {
      alert(err)
    })
  }

  downloadExcelFormat(){
    var tableName = "agri_commodity_stress_stage";
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
        this.router.navigate(['/stress/commodity-stress-stage']);
    }
}

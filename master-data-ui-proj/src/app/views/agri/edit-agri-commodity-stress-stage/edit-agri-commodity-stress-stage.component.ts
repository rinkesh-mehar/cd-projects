import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { AgriPhenophaseService } from '../services/agri-phenophase.service';
import { AgriCommodityStressStageService } from '../services/agri-commodity-stress-stage.service';
import { ZonalStressDurationService } from '../services/zonal-stress-duration.service';
import { AgriStressTypeService } from '../services/agri-stress-type.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { AgriStressControlMeasuresService } from '../services/agri-stress-control-measures.service';
import { AgriStageService } from '../../stress/services/agri-stage.service';


@Component({
  selector: 'app-edit-agri-stress-stage',
  templateUrl: './edit-agri-commodity-stress-stage.component.html',
  styleUrls: ['./edit-agri-commodity-stress-stage.component.scss']
})
export class EditAgriCommodityStressStageComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  CommodityList: any = [];
  PhenophaseList: any = [];
  StressTypeList: any = [];
  StressList: any = [];
  stressStageArr: any = [];
  stressStageList: any = [];
  StageList: any = [];
  // startWeekList: number[] = [];
  // EndWeekList: number[] = [];
  updateCommodityStressStageForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  ngOnInit() {
    this.updateForm();
    this.loadAllCommodities();
    // this.loadAllPhenophase();
    // this.loadAllStressType();
    // this.loadAllStress();
    // this.getSrageList();

    // for (let i = 1; i <= 52; i++) {
    //   this.startWeekList.push(i);
    //   this.EndWeekList.push(i);
    // }
  }
  constructor(
    private actRoute: ActivatedRoute,
    public agriCommodityStressStageService: AgriCommodityStressStageService,
    public commodityService: AgriCommodityService,
   public agriPhenophaseService: AgriPhenophaseService,
    public agriStressTypeService: AgriStressTypeService,
    public zonalStressDurationService: ZonalStressDurationService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public agriStressControlMeasuresService: AgriStressControlMeasuresService,
    public agriStageService: AgriStageService
  ) {
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriCommodityStressStageService.GetStressStage(id).subscribe((data) => {
      this.updateCommodityStressStageForm = this.fb.group({
        id: [data.id, Validators.required],
        commodityId: [data.commodityId, Validators.required],
        // stressTypeId: [data.stressTypeId, Validators.required],
        stressId: [data.stressId, Validators.required],
        stageId: [data.stageId, Validators.required],
        // name: [data.name, Validators.required],
        description: [data.description, Validators.required],
        startPhenophaseId: [data.startPhenophaseId, Validators.required],
        endPhenophaseId: [data.endPhenophaseId, Validators.required],
        status: [data.status]
      })
     
      this.loadAllStressByCommodityId(this.updateCommodityStressStageForm.value.commodityId);
      this.loadAllCommodityByPhenophase(this.updateCommodityStressStageForm.value.commodityId);
      this.getStageListByStressId(this.updateCommodityStressStageForm.value.stressId);
    })
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  updateForm() {
    this.updateCommodityStressStageForm = this.fb.group({
      id: ['', Validators.required],
      commodityId: ['', Validators.required],
      phenophaseId: ['', Validators.required],
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

    for (let controller in this.updateCommodityStressStageForm.controls) {
      this.updateCommodityStressStageForm.get(controller).markAsTouched();
    }

    if (this.updateCommodityStressStageForm.invalid) {
      return;
    }

    if (this.updateCommodityStressStageForm.get('commodityId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Commodity', '');
      return;
    }
    
    // if (this.updateStressStageForm.get('stressTypeId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select Stress Type', '');
    //   return;
    // }

    if (this.updateCommodityStressStageForm.get('stressId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Stress', '');
      return;
    }

   
   

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriCommodityStressStageService.UpdateCommodityStressStage(id, this.updateCommodityStressStageForm.value).subscribe(res => {
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

  getStressStagByCommodityIdAndStressTypeId() {
    if (this.updateCommodityStressStageForm.value.stressTypeId) {
      this.agriCommodityStressStageService.getStressStagByCommodityIdAndStressTypeId(this.updateCommodityStressStageForm.value.commodityId,this.updateCommodityStressStageForm.value.stressTypeId).subscribe(res => {

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

  loadAllStressByCommodityId(commidityId) {
    // console.log('commodityId',event.target['value']);
    return this.agriStressControlMeasuresService.GetAllStressByCommodity(commidityId).subscribe((data: any) => {
      this.StressList = data;
    }, err => {
      alert(err)
    })
  }

  loadAllCommodityByPhenophase(commodityId) {
    // let commodityId = this.updateStressStageForm.value.commodityId; 
    console.log("P Commodity id : " + commodityId)
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

  getSrageList() {
    return this.agriStageService.getStageList().subscribe((data: {}) => {
      this.StageList = data;
    })
  }

  getStageListByStressId(stressId) {
    return this.agriCommodityStressStageService.getStageListByStressId(stressId).subscribe((data: any) => {
      this.StageList = data;
    }, err => {
      alert(err)
    })
  }
    modalSuccess($event: any) {
        this.router.navigate(['/stress/commodity-stress-stage']);
    }
}

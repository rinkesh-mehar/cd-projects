import { AgriAgrochemicalMasterService } from './../services/agri-agrochemical-master.service';
import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriCommodityAgrochemicalService } from '../services/agri-commodity-agrochemical.service';
import { AgriAgrochemicalTypeService } from '../services/agri-agrochemical-type.service';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { AgriStressTypeService } from '../services/agri-stress-type.service';
import { ZonalStressDurationService } from '../services/zonal-stress-duration.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-agri-agrochemical-master',
  templateUrl: './edit-agri-commodity-agrochemical.component.html',
  styleUrls: ['./edit-agri-commodity-agrochemical.component.scss']
})
export class EditAgriCommodityAgrochemicalComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  grochemicalMasterList: any = [];
  updateAgrochemicalMasterForm: FormGroup;
  AgrochemicalTypeList: any = [];
  AgroChemicalNameList: any = [];
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  StressTypeList: {};
  CommodityList: {};
  StressList: any = [];

  ngOnInit() {
    this.updateForm();
    this.loadAllAgrochemicalType();
    this.loadAllAgroChemicalNames();
  }

  constructor(
    private actRoute: ActivatedRoute,
    public agriAgrochemicalMasterService: AgriCommodityAgrochemicalService,
    public agriAgrochemicalTypeService: AgriAgrochemicalTypeService,
    public fb: FormBuilder,
    public zonalStressDurationService: ZonalStressDurationService,
    public agriStressTypeService: AgriStressTypeService,
    public commodityService: AgriCommodityService,
    public router: Router,
    public agriAgrochemicalService:AgriAgrochemicalMasterService
  ) {


  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  forGroupDetail()
  {
    this.updateAgrochemicalMasterForm = this.fb.group({
      agrochemicalTypeId: ['', Validators.required],
      // name: ['', Validators.required],
      agrochemicalId: ['', Validators.required],
      waitingPeriod: ['', Validators.required],
      commodityId: ['', Validators.required],
      // stressTypeId: ['', Validators.required],
      cibrcApproved:['No'],
      // stressNameList: [''],
      status: ['Inactive'],
      // stressNameChkBox: new FormArray([]),
    })
  }
  
  updateForm() {
   
    this.forGroupDetail();
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriAgrochemicalMasterService.GetAgrochemicalMaster(id).subscribe((data) => {
      this.updateAgrochemicalMasterForm = this.fb.group({
        agrochemicalTypeId: [data.agrochemicalTypeId, Validators.required],
        // name: [data.name, Validators.required],
        waitingPeriod: [data.waitingPeriod, Validators.required],
        commodityId: [data.commodityId, Validators.required],
        // stressTypeId: [data.stressTypeId, Validators.required],
        agrochemicalId: [data.agrochemicalId, Validators.required],
        cibrcApproved: [data.cibrcApproved, Validators.required],
        status: [data.status],
        // stressNameList: [data.stressNameList],
        // stressNameChkBox: new FormArray([]),
      })
      // if(data.stressNameList.length == 0){
      //   this.StressList = [];
      // }
      this.loadAllStressType();
      this.loadAllCommodities();
      this.getDataByStressType();
      // this.getDataByCommodityIdAndStressTypeId();
    })

  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {
  
    for (let Controller in this.updateAgrochemicalMasterForm.controls) {
      this.updateAgrochemicalMasterForm.get(Controller).markAllAsTouched();
    }

    if (this.updateAgrochemicalMasterForm.invalid) {
      return;
    }
    if (this.updateAgrochemicalMasterForm.get('agrochemicalTypeId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select AgrochemicalType', '');
      return;
    }
    // const stressNameChkBox = this.updateAgrochemicalMasterForm.value.stressNameChkBox.map((v, i) => v ? this.StressList[i] : null)
    //   .filter(v => v !== null);
    // this.updateAgrochemicalMasterForm.patchValue({ stressNameList: stressNameChkBox });


    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriAgrochemicalMasterService.UpdateAgrochemicalMaster(id, this.updateAgrochemicalMasterForm.value).subscribe(res => {
      this.isSubmitted = true;

      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.forGroupDetail();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }
  //Agrochemical list
  loadAllAgrochemicalType() {
    return this.agriAgrochemicalTypeService.GetAllAgrochemicalType().subscribe((data: {}) => {
      this.AgrochemicalTypeList = data;
    })
  }

  //StressType list
  loadAllStressType() {
    return this.agriStressTypeService.GetAllStressType().subscribe((data: {}) => {
      this.StressTypeList = data;
    })
  }
  //Commodity list
  loadAllCommodities() {
    return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
      this.CommodityList = data;
    })
    
  }
  loadAllAgroChemicalNames() {
    return this.agriAgrochemicalService.GetAllAgrochemicalMaster().subscribe((data: {}) => {
        this.AgroChemicalNameList = data;
    });
}

  getDataByStressType() {
    if (this.updateAgrochemicalMasterForm.value.stressTypeId) {
      this.zonalStressDurationService.GetAllStressByStressTypeId(this.updateAgrochemicalMasterForm.value.commodityId,this.updateAgrochemicalMasterForm.value.stressTypeId).subscribe(res => {

        this.StressList = res;
        while ((this.updateAgrochemicalMasterForm.controls.stressNameChkBox as FormArray).length !== 0) {
          (this.updateAgrochemicalMasterForm.controls.stressNameChkBox as FormArray).removeAt(0)
        }
        for (let subRes of this.StressList) {
          let control1: any = new FormControl();

          if (this.updateAgrochemicalMasterForm.value.stressNameList) {
            if (this.updateAgrochemicalMasterForm.value.stressNameList.find(a => a.id == subRes.id)) {
              control1.value = true;
            }
          }


          (this.updateAgrochemicalMasterForm.controls.stressNameChkBox as FormArray).push(control1);
        }
      })
    } else {
      while ((this.updateAgrochemicalMasterForm.controls.stressNameChkBox as FormArray).length !== 0) {
        (this.updateAgrochemicalMasterForm.controls.stressNameChkBox as FormArray).removeAt(0)
      }
      this.StressList = [];
    }
  }

  getDataByCommodityIdAndStressTypeId() {
    if (this.updateAgrochemicalMasterForm.value.commodityId,this.updateAgrochemicalMasterForm.value.stressTypeId) {
      this.agriAgrochemicalMasterService.getAgrochemicalMasterByCommodityIdAndStressTypeId(this.updateAgrochemicalMasterForm.value.commodityId,this.updateAgrochemicalMasterForm.value.stressTypeId).subscribe(res => {

        this.StressList = res;
        while ((this.updateAgrochemicalMasterForm.controls.stressNameChkBox as FormArray).length !== 0) {
          (this.updateAgrochemicalMasterForm.controls.stressNameChkBox as FormArray).removeAt(0)
        }
        for (let subRes of this.StressList) {
          let control1: any = new FormControl();
          
          (this.updateAgrochemicalMasterForm.controls.stressNameChkBox as FormArray).push(control1);
        }
      })
    } else {
      while ((this.updateAgrochemicalMasterForm.controls.stressNameChkBox as FormArray).length !== 0) {
        (this.updateAgrochemicalMasterForm.controls.stressNameChkBox as FormArray).removeAt(0)
      }
      this.StressList = [];
    }
  }

  modalSuccess($event: any) {
    this.router.navigate(['/agrochemicals/commodity-agrochemical']);
  }
}

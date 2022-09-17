import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { GeoStateService } from '../../geo/services/geo-state.service';
import { GeoRegionService } from '../../geo/services/geo-region.service';
import { AgriVarietyStressService } from '../services/agri-variety-stress.service';
import { ZonalStressDurationService } from '../services/zonal-stress-duration.service';
import { AgriStressTypeService } from '../services/agri-stress-type.service';
import { AgriVarietyService } from '../services/agri-variety.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-agri-variety-stress',
  templateUrl: './edit-agri-variety-stress.component.html',
  styleUrls: ['./edit-agri-variety-stress.component.scss']
})
export class EditAgriVarietyStressComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  CommodityList: any = [];
  RegionList: any = [];
  StateList: any = [];
  VarietyList: any = [];
  StressTypeList: any = [];
  StressList: any = [];
  updateVarietyStressForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;


  ngOnInit() {
    this.updateForm();
    this.loadAllRegion();
    this.loadAllState();
    this.loadAllCommodities();
    this.loadAllStressType();
    this.loadAllVariety();
    // this.loadAllStress();


  }
  constructor(
    private actRoute: ActivatedRoute,
    public geoStateService: GeoStateService,
    public geoRegionService: GeoRegionService,
    public agriVarietyStressService: AgriVarietyStressService,
    public commodityService: AgriCommodityService,
    public zonalStressDurationService: ZonalStressDurationService,
    public agriVarietyService: AgriVarietyService,
    public agriStressTypeService: AgriStressTypeService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) {
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriVarietyStressService.GetVarietyStress(id).subscribe((data) => {
      this.updateVarietyStressForm = this.fb.group({

        // stateCode: [data.stateCode, Validators.required],
        // regionId: [data.regionId, Validators.required],
        commodityId: [data.commodityId, Validators.required],
        varietyId: [data.varietyId, Validators.required],
        stressTypeId: [data.stressTypeId, Validators.required],
        resistantStress: [data.resistantStress],
        susceptibleStress: [data.susceptibleStress],
        tolerantStress: [data.tolerantStress],
        resistantStresschkBox: new FormArray([]),
        susceptibleStresschkBox: new FormArray([]),
        tolerantStresschkBox: new FormArray([]),
        description: [data.description],
        status: [data.status]
      })
      this.getDataByStressType();

    })

  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  updateForm() {
    this.updateVarietyStressForm = this.fb.group({

      // stateCode: ['', Validators.required],
      // regionId: ['', Validators.required],
      commodityId: ['', Validators.required],
      varietyId: ['', Validators.required],
      stressTypeId: ['', Validators.required],
      resistantStress: [''],
      susceptibleStress: [''],
      tolerantStress: [''],
      description: [''],
      status: ['Inactive']

    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {


    const resistantStresschkBox = this.updateVarietyStressForm.value.resistantStresschkBox.map((v, i) => v ? this.StressList[i] : null)
      .filter(v => v !== null);
    this.updateVarietyStressForm.patchValue({ resistantStress: resistantStresschkBox });

    const susceptibleStresschkBox = this.updateVarietyStressForm.value.susceptibleStresschkBox.map((v, i) => v ? this.StressList[i] : null)
      .filter(v => v !== null);
    this.updateVarietyStressForm.patchValue({ susceptibleStress: susceptibleStresschkBox });

    const tolerantStresschkBox = this.updateVarietyStressForm.value.tolerantStresschkBox.map((v, i) => v ? this.StressList[i] : null)
      .filter(v => v !== null);
    this.updateVarietyStressForm.patchValue({ tolerantStress: tolerantStresschkBox });

    // if (this.updateVarietyStressForm.get('stateCode').value == 0) {
    //   alert('Please Select State');
    //   return;
    // }

    // if (this.updateVarietyStressForm.get('regionId').value == 0) {
    //   alert('Please Select Region');
    //   return;
    // }

    if (this.updateVarietyStressForm.get('commodityId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Commodity', '');
      return;
    }

    if (this.updateVarietyStressForm.get('stressTypeId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select StressType', '');
      return;
    }

    if (this.updateVarietyStressForm.get('varietyId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Variety', '');
      return;
    }

    // if (this.updateVarietyStressForm.get(' resistantStress').value == 0) {
    //   alert('Please Select Resistant Stress');
    //   return;
    // }
    // if (this.updateVarietyStressForm.get(' susceptibleStress').value == 0) {
    //   alert('Please Select Susceptible Stress');
    //   return;
    // }

    // if (this.updateVarietyStressForm.get(' tolerantStress').value == 0) {
    //   alert('Please Select Tolerant Stress');
    //   return;
    // }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriVarietyStressService.UpdateVarietyStress(id, this.updateVarietyStressForm.value).subscribe(res => {
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

  //State list
  loadAllState() {
    return this.geoStateService.GetAllState().subscribe((data: {}) => {
      this.StateList = data;
    })
  }

  //Region list
  loadAllRegion() {
    return this.geoRegionService.GetAllRegion().subscribe((data: {}) => {
      this.RegionList = data;
    })
  }

  //Commodity list
  loadAllCommodities() {
    return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
      this.CommodityList = data;
    })
  }

  //StressType list
  loadAllStressType() {
    return this.agriStressTypeService.GetAllStressType().subscribe((data: {}) => {
      this.StressTypeList = data;
    })
  }

  // //Stress list
  // loadAllStress() {
  //   return this.agriStressService.GetAllStress().subscribe((data: {}) => {
  //     this.StressList = data;
  //   })
  // }

  //Variety list
  loadAllVariety() {
    return this.agriVarietyService.GetAllVarieties().subscribe((data: {}) => {
      this.VarietyList = data;
    })
  }


  getDataByStressType() {
    if (this.updateVarietyStressForm.value.stressTypeId) {
      this.zonalStressDurationService.GetAllStressByStressTypeId(this.updateVarietyStressForm.value.commodityId,
        this.updateVarietyStressForm.value.stressTypeId).subscribe(res => {
        console.log(res);

        this.StressList = res;
        this.clearFormArray(this.updateVarietyStressForm.controls.resistantStresschkBox as FormArray);
        this.clearFormArray(this.updateVarietyStressForm.controls.susceptibleStresschkBox as FormArray);
        this.clearFormArray(this.updateVarietyStressForm.controls.tolerantStresschkBox as FormArray);

        const formArray = this.updateVarietyStressForm.get('resistantStresschkBox') as FormArray;
        this.updateVarietyStressForm.controls.susceptibleStresschkBox = new FormArray([]);
        this.updateVarietyStressForm.controls.tolerantStresschkBox = new FormArray([]);


        for (let subRes of this.StressList) {
          let resistantStressControl: any = new FormControl();
          let susceptibleStressControl: any = new FormControl();
          let tolerantStressControl: any = new FormControl();

          if (this.updateVarietyStressForm.value.resistantStress) {
            if (this.updateVarietyStressForm.value.resistantStress.find(a => a.id == subRes.id)) {
              resistantStressControl.value = true;
            }
          }

          if (this.updateVarietyStressForm.value.susceptibleStress) {
            if (this.updateVarietyStressForm.value.susceptibleStress.find(a => a.id == subRes.id)) {
              susceptibleStressControl.value = true;
            }
          }


          if (this.updateVarietyStressForm.value.tolerantStress) {
            if (this.updateVarietyStressForm.value.tolerantStress.find(a => a.id == subRes.id)) {
              tolerantStressControl.value = true;
            }
          }


          formArray.push(resistantStressControl);
          (this.updateVarietyStressForm.controls.susceptibleStresschkBox as FormArray).push(susceptibleStressControl);
          (this.updateVarietyStressForm.controls.tolerantStresschkBox as FormArray).push(tolerantStressControl);
        }
        this.updateVarietyStressForm.setControl('resistantStresschkBox', formArray);


      })
    } else {


      this.clearFormArray(this.updateVarietyStressForm.controls.resistantStresschkBox as FormArray);
      this.clearFormArray(this.updateVarietyStressForm.controls.susceptibleStresschkBox as FormArray);
      this.clearFormArray(this.updateVarietyStressForm.controls.tolerantStresschkBox as FormArray);

      this.StressList = [];
    }
  }

  clearFormArray = (formArray: FormArray) => {
    while (formArray.length !== 0) {
      formArray.removeAt(0)
    }
  }

  loadAllVarietyByCommodity(){
    let commodityId = this.updateVarietyStressForm.value.commodityId;
    this.agriVarietyService.GetAllVarietyByCommodityId(commodityId).subscribe(
          (data: {}) => {
            this.VarietyList = data;
            console.log(this.VarietyList);
          }
        );
  }

  modalSuccess($event: any) {
    this.router.navigate(['/stress/variety-stress']);
  }
}

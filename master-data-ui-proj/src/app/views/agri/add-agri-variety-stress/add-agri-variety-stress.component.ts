import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray, FormControl } from '@angular/forms';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { Router } from '@angular/router';
import { ZonalStressDurationService } from '../services/zonal-stress-duration.service';
import { AgriStressTypeService } from '../services/agri-stress-type.service';
import { AgriVarietyStressService } from '../services/agri-variety-stress.service';
import { GeoStateService } from '../../geo/services/geo-state.service';
import { GeoRegionService } from '../../geo/services/geo-region.service';
import { AgriVarietyService } from '../services/agri-variety.service';
import { ApiUtilService } from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-add-agri-variety-stress',
  templateUrl: './add-agri-variety-stress.component.html',
  styleUrls: ['./add-agri-variety-stress.component.scss']
})
export class AddAgriVarietyStressComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  CommodityList: any = [];
  RegionList: any = [];
  StateList: any = [];
  VarietyList: any = [];
  StressTypeList: any = [];
  StressList: any = [];
  varietyStressForm: FormGroup;
  varietyStressArr: any = [];
  uploadFile: File = null;
  imgPerview: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any; 

  ngOnInit() {
    this.loadAllRegion();
    this.loadAllState();
    this.loadAllVariety();
    this.loadAllCommodities();
    this.loadAllStressType();
    // this.loadAllStress();
    this.addVarietyStress();

  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public geoStateService: GeoStateService,
    public geoRegionService: GeoRegionService,
    public agriVarietyStressService: AgriVarietyStressService,
    public agriVarietyService: AgriVarietyService,
    public commodityService: AgriCommodityService,
    public zonalStressService: ZonalStressDurationService,
    public agriStressTypeService: AgriStressTypeService,
    public apiUtilService: ApiUtilService

  ) { }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  addVarietyStress() {
    this.varietyStressForm = this.fb.group({
      // stateCode: ['', Validators.required],
      // regionId: ['', Validators.required],
      commodityId: ['', Validators.required],
      varietyId: ['', Validators.required],
      stressTypeId: ['', Validators.required],
      resistantStress: [''],
      susceptibleStress: [''],
      tolerantStress: [''],
      resistantStresschkBox: new FormArray([]),
      susceptibleStresschkBox: new FormArray([]),
      tolerantStresschkBox: new FormArray([]),
      description: [''],
      status: ['Inactive']

    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {

    const resistantStresschkBox = this.varietyStressForm.value.resistantStresschkBox.map((v, i) => v ? this.StressList[i] : null)
      .filter(v => v !== null);
    this.varietyStressForm.patchValue({ resistantStress: resistantStresschkBox });

    const susceptibleStresschkBox = this.varietyStressForm.value.susceptibleStresschkBox.map((v, i) => v ? this.StressList[i] : null)
      .filter(v => v !== null);
    this.varietyStressForm.patchValue({ susceptibleStress: susceptibleStresschkBox });

    const tolerantStresschkBox = this.varietyStressForm.value.tolerantStresschkBox.map((v, i) => v ? this.StressList[i] : null)
      .filter(v => v !== null);
    this.varietyStressForm.patchValue({ tolerantStress: tolerantStresschkBox });


    // if (this.varietyStressForm.get('stateCode').value == 0) {
    //   alert('Please Select State');
    //   return;
    // }

    // if (this.varietyStressForm.value.regionId == 0) {
    //   alert('Please Select Region');
    //   return;
    // }

    if (this.varietyStressForm.get('commodityId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Commodity', '');
      return;
    }
    if (this.varietyStressForm.get('varietyId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Variety', '');
      return;
    }

    if (this.varietyStressForm.get('stressTypeId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select StressType', '');
      return;
    }

    // if (this.varietyStressForm.get(' resistantStress').value == 0) {
    //   alert('Please Select Resistant Stress');
    //   return;
    // }
    // if (this.varietyStressForm.get(' susceptibleStress').value == 0) {
    //   alert('Please Select Susceptible Stress');
    //   return;
    // }

    // if (this.varietyStressForm.get(' tolerantStress').value == 0) {
    //   alert('Please Select Tolerant Stress');
    //   return;
    // }



    for (let controller in this.varietyStressForm.controls) {
      this.varietyStressForm.get(controller).markAsTouched();
    }

    if (this.varietyStressForm.invalid) {
      return;
    }


    this.agriVarietyStressService.CreateVarietyStress(this.varietyStressForm.value).subscribe(res => {
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

  //Variety list
  loadAllVariety() {
    return this.agriVarietyService.GetAllVarieties().subscribe((data: {}) => {
      this.VarietyList = data;
    })
  }

  //StressType list
  loadAllStressType() {
    return this.agriStressTypeService.GetAllStressType().subscribe((data: {}) => {
      this.StressTypeList = data;
    })
  }

  //Stress list
  // loadAllStress() {
  //   return this.agriStressService.GetAllStress().subscribe((data: {}) => {
  //     this.StressList = data;
  //     console.log(this.StressList);
  //   })
  // }

  getDataByStressType() {
    if (this.varietyStressForm.value.stressTypeId && this.varietyStressForm.value.commodityId) {
      this.zonalStressService.GetAllStressByStressTypeId(this.varietyStressForm.value.commodityId , this.varietyStressForm.value.stressTypeId).subscribe(res => {
        console.log(res);

        this.StressList = res;
        this.clearFormArray(this.varietyStressForm.controls.resistantStresschkBox as FormArray);
        this.clearFormArray(this.varietyStressForm.controls.susceptibleStresschkBox as FormArray);
        this.clearFormArray(this.varietyStressForm.controls.tolerantStresschkBox as FormArray);

        const formArray = this.varietyStressForm.get('resistantStresschkBox') as FormArray;
        this.varietyStressForm.controls.susceptibleStresschkBox = new FormArray([]);
        this.varietyStressForm.controls.tolerantStresschkBox = new FormArray([]);


        for (let subRes of this.StressList) {
          let control1: any = new FormControl();
          let control2: any = new FormControl();
          let control3: any = new FormControl();

          formArray.push(control1);
          (this.varietyStressForm.controls.susceptibleStresschkBox as FormArray).push(control2);
          (this.varietyStressForm.controls.tolerantStresschkBox as FormArray).push(control3);
        }
        this.varietyStressForm.setControl('resistantStresschkBox', formArray);


      })
    } else {


      this.clearFormArray(this.varietyStressForm.controls.resistantStresschkBox as FormArray);
      this.clearFormArray(this.varietyStressForm.controls.susceptibleStresschkBox as FormArray);
      this.clearFormArray(this.varietyStressForm.controls.tolerantStresschkBox as FormArray);

      this.StressList = [];
    }
  }

  clearFormArray = (formArray: FormArray) => {
    while (formArray.length !== 0) {
      formArray.removeAt(0)
    }
  }

  loadAllVarietyByCommodity(){
    let commodityId = this.varietyStressForm.value.commodityId;
    this.agriVarietyService.GetAllVarietyByCommodityId(commodityId).subscribe(
          (data: {}) => {
            this.VarietyList = data;
            console.log(this.VarietyList);
          }
        );
  }

  downloadExcelFormat(){
    var tableName = "agri_variety_stress";
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
        this.router.navigate(['/stress/variety-stress']);
    }
}

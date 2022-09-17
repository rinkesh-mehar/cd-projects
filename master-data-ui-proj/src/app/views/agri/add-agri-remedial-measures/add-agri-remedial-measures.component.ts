import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray, FormControl } from '@angular/forms';
//import { AgriCommodityService } from '../services/agri-commodity.service';
import { Router, ActivatedRoute } from '@angular/router';

//import { AgriVarietyService } from '../services/agri-variety.service';
import { AgriRemedialMeasuresService } from '../services/agri-remedial-measures.service';
import { GeoStateService } from '../../geo/services/geo-state.service';
import { AgriCommodityAgrochemicalService } from '../services/agri-commodity-agrochemical.service';
import { GeneralCompanyService } from '../../general/services/general-campany-service';
// import { AgriStressTypeService } from '../services/agri-commodity-stress-type.service';
import { AgriAgrochemicalTypeService } from '../services/agri-agrochemical-type.service';
import { ApiUtilService } from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
// import { AgriCommodityStressService } from '../services/agri-commodity-stress.service';

@Component({
  selector: 'app-add-agri-remedial-measures',
  templateUrl: './add-agri-remedial-measures.component.html',
  styleUrls: ['./add-agri-remedial-measures.component.scss']
})
export class AddAgriRemedialMeasuresComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  remedialMeasuresForm: FormGroup;
  remedialMeasuresArr: any = [];
  StateList: any = [];
  CompanyNameList: any = [];
  // StressList: any = [];
  AgrochemicalList: any = [];
  uploadFile: File = null;
  imgPerview: any;
  
  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;
  

  operationMode = "add";
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  StressTypeList: {};
  AgrochemicalTypeList: {};


  ngOnInit() {
    this.addRemedialMeasures();


    var id = this.actRoute.snapshot.paramMap.get('id');
    if (id) {
      this.loadEditData(id);
      this.operationMode = "edit";
    } else {
      // this.loadAllStress();
      // this.loadAllAgrochemicalMaster();
      // this.loadAllState();
      this.loadAllCompany();
      // this.loadAllStressType();
      // this.loadAllAgrochemicals();
      this.loadAllAgrochemicalType();
    }
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  loadEditData(id) {
    this.agriRemedialMeasuresService.GetRemedialMeasures(id).subscribe((data: any) => {
      this.remedialMeasuresForm.patchValue(data);
      // this.loadAllAgrochemicalMaster();
      // this.loadAllState();
      // this.loadAllStress();

      this.loadAllCompany();
      // this.loadAllStressType();
      this.loadAllAgrochemicalType();
      // this.getDataByStressType();
      // this.loadAllAgrochemicals();
      this.getDataByAgroChemicalType();
    })
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public agriRemedialMeasuresService: AgriRemedialMeasuresService,
    public geoStateService: GeoStateService,
    // public agriStressService: AgriCommodityStressService,
    public agriAgrochemicalMasterService: AgriCommodityAgrochemicalService,
    private actRoute: ActivatedRoute,
    private companyNameService: GeneralCompanyService,
    // public agriStressTypeService: AgriStressTypeService,
    public agriAgrochemicalTypeService: AgriAgrochemicalTypeService,
    public apiUtilService: ApiUtilService
  ) { }

  addRemedialMeasures() {
    this.remedialMeasuresForm = this.fb.group({
      id: [],
      brandName: ['', Validators.required],
      companyId: ['', Validators.required],
      // stateId: [''],
      // stateChkBox: new FormArray([]),
      // stressTypeId: ['', Validators.required],
      // stress: [''],
      // stressChkBox: new FormArray([]),
      agrochemicalTypeId: ['', Validators.required],
      // agrochemicalChkBox: new FormArray([]),
      agrochemicalId: ['', Validators.required],
      agrochemicalStatus: ['', Validators.required],
      status: ['Inactive']
    })



  }


  onCheckChange(event, component) {
    //component stress/agrochemical
    const formArray: FormArray = this.remedialMeasuresForm.get(component) as FormArray;

    /* Selected */
    if (event.target.checked) {
      // Add a new control in the arrayForm
      formArray.push(new FormControl(event.target.value));
    }
    /* unselected */
    else {
      // find the unselected element
      let i: number = 0;

      formArray.controls.forEach((ctrl: FormControl) => {
        if (ctrl.value == event.target.value) {
          // Remove the unselected element from the arrayForm
          formArray.removeAt(i);
          return;
        }

        i++;
      });
    }

  }//onCheckChangeStress

  // AgrochemicalMaster list
  // loadAllAgrochemicalMaster() {
  //   return this.agriAgrochemicalMasterService.GetAllAgrochemicalMaster().subscribe((data: {}) => {
  //     this.AgrochemicalList = data;
  //     for (let subRes of this.AgrochemicalList) {
  //       let control: any = new FormControl(); // if first item set to true, else false
  //       if (this.remedialMeasuresForm.value.agrochemical) {
  //         if (this.remedialMeasuresForm.value.agrochemical.find(a => a.id == subRes.id)) {
  //           control.value = true;
  //         }
  //       }

  //       (this.remedialMeasuresForm.controls.agrochemicalChkBox as FormArray).push(control);
  //     }
  //   })
  // }

  getDataByAgroChemicalType() {
    if (this.remedialMeasuresForm.value.agrochemicalTypeId) {
      this.agriAgrochemicalMasterService.GetAllAgrochemicalMasterByAgrochemicalTypeId(this.remedialMeasuresForm.value.agrochemicalTypeId).subscribe((data: {}) => {
        this.AgrochemicalList = data;
        // while ((this.remedialMeasuresForm.controls.agrochemicalChkBox as FormArray).length !== 0) {
        //   (this.remedialMeasuresForm.controls.agrochemicalChkBox as FormArray).removeAt(0)
        // }
        // for (let subRes of this.AgrochemicalList) {
        //   let control: any = new FormControl(); // if first item set to true, else false
        //   if (this.remedialMeasuresForm.value.agrochemical) {
        //     if (this.remedialMeasuresForm.value.agrochemical.find(a => a.id == subRes.id)) {
        //       control.value = true;
        //     }
        //   }
        //   (this.remedialMeasuresForm.controls.agrochemicalChkBox as FormArray).push(control);
        // }
      })
    } else {
      // while ((this.remedialMeasuresForm.controls.agrochemicalChkBox as FormArray).length !== 0) {
      //   (this.remedialMeasuresForm.controls.agrochemicalChkBox as FormArray).removeAt(0)
      // }
    }
  }

  // Statelist
  // loadAllState() {
  //   return this.geoStateService.GetAllState().subscribe((data: {}) => {
  //     this.StateList = data;

  //     for (let state of this.StateList) {
  //       let control: any = new FormControl(); // if first item set to true, else false
  //       if (this.remedialMeasuresForm.value.stateId) {
  //         let stArr = this.remedialMeasuresForm.value.stateId.split(",")
  //         if (stArr.find(x => x == state.id)) {
  //           control.value = true;
  //         }
  //       }
  //       (this.remedialMeasuresForm.controls.stateChkBox as FormArray).push(control);
  //     }
  //   })
  // }

  // load All Company List
  loadAllCompany() {
    return this.companyNameService.GetAllCompany().subscribe((data: {}) => {
      this.CompanyNameList = data;
    })
  }

  // //StressType list
  // loadAllStressType() {
  //   return this.agriStressTypeService.GetAllStressType().subscribe((data: {}) => {
  //     this.StressTypeList = data;
  //   })
  // }

  // AgrochemicalType list
  loadAllAgrochemicalType() {
    return this.agriAgrochemicalTypeService.GetAllAgrochemicalType().subscribe((data: {}) => {
      this.AgrochemicalTypeList = data;
    })
    
  }

    // //Agrochemicals list
    // loadAllAgrochemicals() {
    //   return this.agriAgrochemicalMasterService.GetAllAgrochemicalMaster().subscribe((data: {}) => {
    //     this.AgrochemicalList = data;
    //   })
    // }

  // Stress list
  // loadAllStress() {
  //   return this.agriStressService.GetAllStress().subscribe((data: {}) => {
  //     this.StressList = data;
  //     for (let subRes of this.StressList) {
  //       let control: any = new FormControl(); // if first item set to true, else false
  //       if (this.remedialMeasuresForm.value.stress) {
  //         if (this.remedialMeasuresForm.value.stress.find(a => a.id == subRes.id)) {
  //           control.value = true;
  //         }
  //       }
  //       (this.remedialMeasuresForm.controls.stressChkBox as FormArray).push(control);
  //     }

  //   })
  // }


  // getDataByStressType() {
  //   if (this.remedialMeasuresForm.value.stressTypeId) {
  //     this.agriStressService.GetAllStressByStressTypeId(this.remedialMeasuresForm.value.stressTypeId).subscribe(res => {

  //       this.StressList = res;
  //       while ((this.remedialMeasuresForm.controls.stressChkBox as FormArray).length !== 0) {
  //         (this.remedialMeasuresForm.controls.stressChkBox as FormArray).removeAt(0)
  //       }
  //       for (let subRes of this.StressList) {
  //         let control1: any = new FormControl();

  //         if (this.remedialMeasuresForm.value.stress) {
  //           if (this.remedialMeasuresForm.value.stress.find(a => a.id == subRes.id)) {
  //             control1.value = true;
  //           }
  //         }

  //         (this.remedialMeasuresForm.controls.stressChkBox as FormArray).push(control1);
  //       }
  //     })
  //   } else {
  //     while ((this.remedialMeasuresForm.controls.stressChkBox as FormArray).length !== 0) {
  //       (this.remedialMeasuresForm.controls.stressChkBox as FormArray).removeAt(0)
  //     }
  //     this.StressList = [];
  //   }
  // }






  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {



    // const selectedStress = this.remedialMeasuresForm.value.stressChkBox.map((v, i) => v ? this.StressList[i] : null)
    //   .filter(v => v !== null);
    // this.remedialMeasuresForm.patchValue({ stress: selectedStress });

    // const selectedAgrochemicalList = this.remedialMeasuresForm.value.agrochemicalChkBox.map((v, i) => v ? this.AgrochemicalList[i] : null)
    //   .filter(v => v !== null);
    // this.remedialMeasuresForm.patchValue({ agrochemical: selectedAgrochemicalList });

    // const selectedStateList = this.remedialMeasuresForm.value.stateChkBox.map((v, i) => v ? this.StateList[i].id : null)
    //   .filter(v => v !== null);
    // this.remedialMeasuresForm.patchValue({ stateId: selectedStateList });


    // return;

    for (let controller in this.remedialMeasuresForm.controls) {
      this.remedialMeasuresForm.get(controller).markAsTouched();
    }

    if (this.remedialMeasuresForm.invalid) {
      return;
    }

    let formdata = this.remedialMeasuresForm.value;

    // formdata.stateId = formdata.stateId.join(',');
    window.scrollTo(0, 0);
    if (this.operationMode == "edit") {
      this.agriRemedialMeasuresService.UpdateRemedialMeasures(this.remedialMeasuresForm.value.id, formdata).subscribe(res => {
        // window.scrollTo(0, 0);
        this.isSubmitted = true;
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
            // this.remedialMeasuresForm.reset();
            this.operationMode = "add";
            this.addRemedialMeasures();
            this.successModal.showModal('SUCCESS', res.message, '');
          } else {
            this.errorModal.showModal('ERROR', res.error, '');
          }
        }
      });
    } else {
      this.agriRemedialMeasuresService.CreateRemedialMeasures(formdata).subscribe(res => {
        this.isSubmitted = true;
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this.addRemedialMeasures();
            this.successModal.showModal('SUCCESS', res.message, '');
          } else {
            this.errorModal.showModal('ERROR', res.error, '');
          }
        }
      });
    }

  }

  downloadExcelFormat(){
    var tableName = "agri_agrochemical_brand";
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
    this.router.navigate(['/agrochemicals/agrochemical-brand']);
  }
}

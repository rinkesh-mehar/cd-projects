import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { Router } from '@angular/router';
import { AgriSeedTreatmentService } from '../services/agri-seed-treatment.service';
import { AgriVarietyService } from '../services/agri-variety.service';
import { GeneralUomService } from '../../general/services/general-uom.service';
import { ApiUtilService } from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-agri-seed-treatment',
  templateUrl: './add-agri-seed-treatment.component.html',
  styleUrls: ['./add-agri-seed-treatment.component.scss']
})
export class AddAgriSeedTreatmentComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;


  CommodityList: any = [];
  VarietyList: any = [];
  UomList: any = [];
  seedTreatmentForm: FormGroup;
  seedTreatmentArr: any = [];
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
    // this.loadAllVariety();
    this.loadAllUom();
    this.addSeedTreatment()
  }
  numberPattern = '^[0-9][\.0-9]*$'

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public agriSeedTreatmentService: AgriSeedTreatmentService,
    public commodityService: AgriCommodityService,
    public agriVarietyService: AgriVarietyService,
    public generalUomService: GeneralUomService,
    public apiUtilService: ApiUtilService

  ) { }

  addSeedTreatment() {
    this.seedTreatmentForm = this.fb.group({
      commodityId: ['', Validators.required],
      varietyId: ['', Validators.required],
      uomId: ['', Validators.required],
      name: ['', Validators.required],
      status: ['Inactive'],
      dose: new FormControl('', [
        Validators.required,
        Validators.pattern(this.numberPattern),
      ])

    })
  }


  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  submitForm() {

    for (let controller in this.seedTreatmentForm.controls) {
      this.seedTreatmentForm.get(controller).markAsTouched();
    }

    if (this.seedTreatmentForm.invalid) {
      return;
    }

    if (this.seedTreatmentForm.get('commodityId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Commodity', '');
      return;
    }
    if (this.seedTreatmentForm.get('varietyId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Variety', '');
      return;
    }
    if (this.seedTreatmentForm.get('uomId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select UOM', '');
      return;
    }


    

    this.agriSeedTreatmentService.CreateSeedTreatment(this.seedTreatmentForm.value).subscribe(res => {
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

  //Uom list
  loadAllUom() {
    return this.generalUomService.GetAllUom().subscribe((data: {}) => {
      this.UomList = data;
    })
  }

  getVarietyByCommodity() {
    if (this.seedTreatmentForm.value.commodityId) {
      this.agriVarietyService.GetAllVarietyByCommodityId(this.seedTreatmentForm.value.commodityId).subscribe((data: {}) => {
        this.VarietyList = data;
      })
    } else {
      this.VarietyList = []
    }

  }

  downloadExcelFormat(){
    var tableName = "agri_seed_treatment";
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
    this.router.navigate(['/stress/seed-treatment']);
  }
}

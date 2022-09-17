import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AgriSeedTreatmentService } from '../services/agri-seed-treatment.service';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { AgriVarietyService } from '../services/agri-variety.service';
import { GeneralUomService } from '../../general/services/general-uom.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-agri-seed-treatment',
  templateUrl: './edit-agri-seed-treatment.component.html',
  styleUrls: ['./edit-agri-seed-treatment.component.scss']
})
export class EditAgriSeedTreatmentComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;


  CommodityList: any = [];
  VarietyList: any = [];
  seedTreatmentArr: any = [];
  seedTreatmentList: any = [];
  UomList: any = [];
  updateSeedTreatmentForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  ngOnInit() {
    this.updateForm();
    this.loadAllCommodities();
    // this.loadAllVariety();
    this.loadAllUom();


  }
  constructor(
    private actRoute: ActivatedRoute,
    public agriseedTreatmentService: AgriSeedTreatmentService,
    public commodityService: AgriCommodityService,
    public agriVarietyService: AgriVarietyService,
    public generalUomService: GeneralUomService,

    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) {
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriseedTreatmentService.GetSeedTreatment(id).subscribe((data) => {
      this.updateSeedTreatmentForm = this.fb.group({

        commodityId: [data.commodityId, Validators.required],
        varietyId: [data.varietyId, Validators.required],
        uomId: [data.uomId, Validators.required],
        name: [data.name, Validators.required],
        status: [data.status],
        dose: new FormControl(data.dose, [
          Validators.required,
          Validators.pattern("^[0-9]*$"),
        ])        //  [data.dose, Validators.required]

      })
      this.getVarietyByCommodity();
    })
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  updateForm() {
    this.updateSeedTreatmentForm = this.fb.group({

      commodityId: ['', Validators.required],
      varietyId: ['', Validators.required],
      uomId: ['', Validators.required],
      name: ['', Validators.required],
      dose: ['', Validators.required],
      status: ['Inactive']

    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {
    
    for (let controller in this.updateSeedTreatmentForm.controls) {
      this.updateSeedTreatmentForm.get(controller).markAsTouched();
    }

    if (this.updateSeedTreatmentForm.invalid) {
      return;
    }

    if (this.updateSeedTreatmentForm.get('commodityId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Commodity', '');
      return;
    }
    if (this.updateSeedTreatmentForm.get('varietyId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Variety', '');
      return;
    }
    if (this.updateSeedTreatmentForm.get('uomId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select UOM', '');
      return;
    }

    

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriseedTreatmentService.UpdateSeedTreatment(id, this.updateSeedTreatmentForm.value).subscribe(res => {
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

  //Variety list
  loadAllUom() {
    return this.generalUomService.GetAllUom().subscribe((data: {}) => {
      this.UomList = data;
    })

  }

  getVarietyByCommodity() {
    if (this.updateSeedTreatmentForm.value.commodityId) {
      this.agriVarietyService.GetAllVarietyByCommodityId(this.updateSeedTreatmentForm.value.commodityId).subscribe((data: {}) => {
        this.VarietyList = data;
      })
    } else {
      this.VarietyList = []
    }

  }

  modalSuccess($event: any) {
    this.router.navigate(['//stress/seed-treatment']);
  }
}


import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriVarietyService } from '../services/agri-variety.service';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { AgriHsCodeService } from '../services/agri-hs-code.service';
import { PricingMspGroupService } from '../../pricing/services/pricing-msp-group.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-edit-agri-variety',
  templateUrl: './edit-agri-variety.component.html',
  styleUrls: ['./edit-agri-variety.component.scss']
})
export class EditAgriVarietyComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  VarietyList: any = [];
  CommodityList: any = [];
  MspGroupList: any = [];
  varietyList: any = [];
  HsCodeList: any = [];
  updateVarietyForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  ngOnInit() {
    this.updateForm()
    this.loadAllCommodities();
    // this.loadAllMspGroup();
    // this.loadAllHsCode();
  }

  constructor(
    private actRoute: ActivatedRoute,
    public agriVarietyService: AgriVarietyService,
    public commodityService: AgriCommodityService,
    public pricingMspGroup: PricingMspGroupService,
    public agriHsCodeService: AgriHsCodeService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) {
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriVarietyService.GetVariety(id).subscribe((data) => {
      this.updateVarietyForm = this.fb.group({
        commodityId: [data.commodityId, Validators.required],
        name: [data.name, Validators.required],
        varietyCode: [data.varietyCode, Validators.required],
        hsCodeId: [data.hsCodeId, Validators.required],
        domesticRestrictions: [data.domesticRestrictions,Validators.required],
        internationalRestrictions: [data.internationalRestrictions,Validators.required],
        mspGroupId: [data.mspGroupId],
        parentVarietyID: [data.parentVarietyID],
        status: [data.status]

      });

      this.getHsCodeList();
      this.getVarietyListByCommodityId();
    })
  }

  updateForm() {
    this.updateVarietyForm = this.fb.group({
      commodityId: ['', Validators.required],
      name: ['', Validators.required],
      hsCodeId: ['', Validators.required],
      domesticRestrictions: ['',Validators.required],
      internationalRestrictions: ['',Validators.required],
      mspGroupId: ['', Validators.required],
      parentVarietyID : ['',Validators.required],
      status: ['Inactive']
    })
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {


    if (this.updateVarietyForm.get('commodityId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Commodity', '');
      return;
    }
    if (this.updateVarietyForm.get('mspGroupId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select MspGroup', '');
      return;
    }
    if (this.updateVarietyForm.get('hsCodeId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select HsCode', '');
      return;
    }

    for (let controller in this.updateVarietyForm.controls) {
      this.updateVarietyForm.get(controller).markAsTouched();
    }

    if (this.updateVarietyForm.invalid) {
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriVarietyService.UpdateVariety(id, this.updateVarietyForm.value).subscribe(res => {
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

  getHsCodeList() {
    if (this.updateVarietyForm.value.commodityId) {
      this.agriHsCodeService.GetAllAgriHsCodeByCommodityId(this.updateVarietyForm.value.commodityId).subscribe(data => {
        this.HsCodeList = data;
      })
    } else {
      this.HsCodeList = [];
    }

  }
  //Commodity list
  loadAllCommodities() {
    return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
      this.CommodityList = data;
    })
  }

  //HsCode list
  loadAllHsCode() {
    return this.agriHsCodeService.GetAllHsCode().subscribe((data: {}) => {
      this.HsCodeList = data;
    })
  }

   //MspGroupList 
   loadAllMspGroup() {
    return this.pricingMspGroup.GetAllMspGroup().subscribe((data: {}) => {
      this.MspGroupList = data;
    })
  }

  getVarietyListByCommodityId() {
    return this.agriVarietyService.getVarietyListByCommodityId(this.updateVarietyForm.value.commodityId).subscribe((data: {}) => {
      this.varietyList = data;
    })
  }

    modalSuccess($event: any) {
        this.router.navigate(['/commodity/variety']);
    }
}

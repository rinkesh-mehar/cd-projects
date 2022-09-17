import { Router, ActivatedRoute } from '@angular/router';
import { ErrorModalComponent } from './../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from './../../../global/success-modal/success-modal.component';
import { ZonalCommodityService } from './../../services/zonal-commodity.service';
import { AczService } from './../../services/acz.service';
import { GeoStateService } from './../../../geo/services/geo-state.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ZonalCommodityCultivationCostService } from './../../services/zonal-commodity-cultivation-cost.service';
import { Component, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-add-edit-zonal-commodity-cultivation-cost',
  templateUrl: './add-edit-zonal-commodity-cultivation-cost.component.html',
  styleUrls: ['./add-edit-zonal-commodity-cultivation-cost.component.scss']
})
export class AddEditZonalCommodityCultivationCostComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    zonalCommodityList: any = [];
    CutivationCostForm: FormGroup;
    stateCodeAczIdZonalCommodityId:any;

    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    _statusMsg: string;
    editId: any;
    mode: string = 'add';
    zonalCommodityCutivationCost:any;
    aczList:any = [];
    StateList: any = [];

  constructor(public fb: FormBuilder,
    public zonalCommodityCultivationCostService: ZonalCommodityCultivationCostService,
    public router: Router,
    public geoStateService : GeoStateService,
    public aczService: AczService,
    public zonalCommodityService: ZonalCommodityService,
    private actRoute: ActivatedRoute,) {
    this.createCultivationCostForm();
     }

    createCultivationCostForm() {
      this.CutivationCostForm = this.fb.group({
        id: [],
        stateCode: ['', Validators.required],
        aczId: ['', Validators.required],
        zonalCommodityId: ['', Validators.required],
        costOfCultivation: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
        costOfProduction: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
        status: ['']
      })
    }

  ngOnInit(): void {
    this.loadAllState();
    
    this.editId = this.actRoute.snapshot.paramMap.get('id');

    if (this.editId) {

      this.mode = "edit";
      this.zonalCommodityCultivationCostService.GetCultivationCost(this.editId).subscribe(data => {
        this.zonalCommodityCutivationCost = data;
        this.CutivationCostForm = this.fb.group({
          id: [],
          aczId: ['', Validators.required],
          stateCode: ['', Validators.required],
          zonalCommodityId: ['', Validators.required],
          costOfCultivation: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
          costOfProduction: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
          status: ['']
        })
        this.CutivationCostForm.patchValue(this.zonalCommodityCutivationCost);
        this.getStateCodeAczIdByZonalCommodityId();
      })

    }
  }

  loadAllState(){
    return this.geoStateService.GetAllState().subscribe((data: {}) => {
      this.StateList = data;
    })
  }

  //Acz list
 loadAczByStateCode(){
   this.CutivationCostForm.patchValue({aczId:''});
    return this.aczService.getAllAczByStateCode(this.CutivationCostForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }
  loadAczByStateCodeForEdit(){
    return this.aczService.getAllAczByStateCode(this.CutivationCostForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }
    //Zonal Commodity List
  loadZonalCommodityByAczId(){
    this.CutivationCostForm.patchValue({zonalCommodityId:''});
    return this.zonalCommodityService.getZonalCommodityListByAczId(this.CutivationCostForm.value.aczId).subscribe((data: {}) => {
      this.zonalCommodityList = data;
    })
  }
  loadZonalCommodityByAczIdForEdit(){
    return this.zonalCommodityService.getZonalCommodityListByAczId(this.CutivationCostForm.value.aczId).subscribe((data: {}) => {
      this.zonalCommodityList = data;
    })
  }

  submitForm() {

    for (let controller in this.CutivationCostForm.controls) {
      this.CutivationCostForm.get(controller).markAsTouched();
    }
    if (this.CutivationCostForm.invalid) {
      return;
    }

    if (this.CutivationCostForm.get('stateCode').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select State', '');
      return;
    }

    if (this.CutivationCostForm.get('aczId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select ACZ', '');
      return;
    }

    if (this.CutivationCostForm.get('zonalCommodityId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Zonal Commodity', '');
      return;
    }
    if (this.CutivationCostForm.get('costOfCultivation').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Cost Of Cultivation', '');
      return;
    }
    if (this.CutivationCostForm.get('costOfProduction').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Cost Of Production', '');
      return;
    }
    
    if (this.mode == "edit") {
      this.updateCultivationCost();
    } else {
      this.addCultivationCost();
    }
  }

  addCultivationCost() {
    this.zonalCommodityCultivationCostService.CreateCultivationCost(this.CutivationCostForm.value).subscribe(res => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.createCultivationCostForm();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    })
  }

  updateCultivationCost() {
    this.zonalCommodityCultivationCostService.UpdateCultivationCost(this.editId, this.CutivationCostForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.mode = "add";
          this.createCultivationCostForm();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    })
  }

  getStateCodeAczIdByZonalCommodityId() {
    return this.zonalCommodityService.getStateCodeAczIdByZonalCommodityId(this.CutivationCostForm.value.zonalCommodityId).subscribe((data) => {
      this.stateCodeAczIdZonalCommodityId = data;
      this.CutivationCostForm.patchValue(this.stateCodeAczIdZonalCommodityId);
      this.loadAczByStateCodeForEdit();
      this.loadZonalCommodityByAczIdForEdit();
      this.CutivationCostForm.patchValue({zonalCommodityId:this.zonalCommodityCutivationCost.zonalCommodityId});
    })
  }

  modalSuccess($event: any) {
    this.router.navigate(['/zonal/commodity-cultivation-cost']);
}

}

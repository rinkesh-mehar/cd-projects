import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

import { AgriCommodityService } from '../services/agri-commodity.service';
import { ZonalFertilizerService } from '../services/zonal-fertilizer.service';
import { GeoRegionService } from '../../geo/services/geo-region.service';
import { AgriSeasonService } from '../services/agri-season.service';
import { GeoStateService } from '../../geo/services/geo-state.service';
import { GeneralUomService } from '../../general/services/general-uom.service';
import { AgriDoseFactorService } from '../services/agri-dose-factor.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { AczService } from '../../zonal/services/acz.service';
import { ZonalCommodityService } from '../../zonal/services/zonal-commodity.service';

@Component({
  selector: 'app-edit-zonal-fertilizer',
  templateUrl: './edit-zonal-fertilizer.component.html',
  styleUrls: ['./edit-zonal-fertilizer.component.scss']
})
export class EditZonalFertilizerComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  CommodityList: any = [];
  // RegionList: any = [];
  // StateList: any = [];
  SeasonList: any = [];
  UomList: any = [];
  DoseFactorList: any = [];
  fertilizerArr: any = [];
  FertilizerList: any = [];
  updateFertilizerForm: FormGroup;
  fertilizerTypes :  string[] = ['Nitrogen', 'Phosphorus', 'Potassium','FYM/Compost','Vermicompost'];

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  stateList: any = [];
  aczList: any = [];
  zonalCommodityList: any;
  stateCodeAczId: any;
  editFertilizer: any;
  
  ngOnInit() {
    this.updateForm();
    // this.loadAllRegion();
    // this.loadAllCommodities();
    this.getAllState();
    this.loadAllDoseFactor();
    this.loadAllUom();
  
  }
    constructor(
    private actRoute: ActivatedRoute,    
    public agriFertilizerService: ZonalFertilizerService,
    public commodityService : AgriCommodityService,
    public geoRegionService : GeoRegionService,
    public agriDoseFactorService : AgriDoseFactorService,
    public agriSeasonService: AgriSeasonService,
    public geoStateService: GeoStateService,
    public generalUomService:GeneralUomService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    private aczService: AczService,
    private zonalCommodityService: ZonalCommodityService,
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriFertilizerService.GetFertilizer(id).subscribe((data) => {
      this.editFertilizer = data;
      this.updateFertilizerForm = this.fb.group({
        // regionId: [data.regionId,Validators.required],
        stateCode: [data.stateCode, Validators.required],
        aczId: [data.aczId, Validators.required],
        zonalCommodityId: [data.zonalCommodityId,Validators.required],
        doseFactorId: [data.doseFactorId, Validators.required],
        name: [data.name,Validators.required],
        dose: [data.dose,Validators.required],
        uomId: [data.uomId,Validators.required],
        note: [data.note],
        status: [data.status]
        })
        this.getStateCodeAczIdByZonalCommodityId();
    })
  }

  updateForm(){
    this.updateFertilizerForm = this.fb.group({
      // regionId: ['',Validators.required],
      stateCode: ['', Validators.required],
      aczId: ['', Validators.required],
      zonalCommodityId: ['',Validators.required],
      doseFactorId: ['', Validators.required],
      name: ['',Validators.required],
      dose: ['',Validators.required],
      uomId: ['',Validators.required],
      note: [''],
      status: ['Inactive']
     
    })    
    
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm(){ 
    this.updateFertilizerForm.patchValue({status:this.editFertilizer.status});
    for(let controller in this.updateFertilizerForm.controls){
      this.updateFertilizerForm.get(controller).markAsTouched();
    }
  
    if(this.updateFertilizerForm.invalid){
      return;
    }
    
    if (this.updateFertilizerForm.get('stateCode').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select State', '');
      return;
    }
   
    if (this.updateFertilizerForm.get('zonalCommodityId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Zonal Commodity', '');
      return;
    }


    if (this.updateFertilizerForm.get('doseFactorId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select DoseFactor', '');
      return;
    }

    if (this.updateFertilizerForm.get('uomId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select UOM', '');
      return;
    }

    if (this.updateFertilizerForm.get('name').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Fertilizer', '');
      return;
    }

  

  var id = this.actRoute.snapshot.paramMap.get('id');
  this.agriFertilizerService.UpdateFertilizer(id, this.updateFertilizerForm.value).subscribe(res => {
    this.isSubmitted = true;
     
    if(res){
      this.isSuccess = res.success;
      if(res.success){
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  })
  }

  //  //Region list
  //  loadAllRegion(){
  //   return this.geoRegionService.GetAllRegion().subscribe((data: {}) => {
  //     this.RegionList = data;
  //   })
  // }

    //  //Commodity list
    //  loadAllCommodities(){
    //   return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
    //     this.CommodityList = data;
    //   })
    // }

  

  getAllState() {
    this.geoStateService.GetAllState().subscribe((data: {}) => {
      this.stateList = data;
    })
  }

  getAllAczByStateCode() {
    this.updateFertilizerForm.patchValue({aczId:''});
    this.aczService.getAllAczByStateCode(this.updateFertilizerForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }

  getAllAczByStateCodeForEdit() {
    this.aczService.getAllAczByStateCode(this.updateFertilizerForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }

  getZonalCommodityListByAczId() {
    this.updateFertilizerForm.patchValue({zonalCommodityId:''});
     this.zonalCommodityService.getZonalCommodityListByAczId(this.updateFertilizerForm.value.aczId).subscribe((data: {}) => {
      this.zonalCommodityList = data;
    })
  }

  getZonalCommodityListByAczIdForEdit() {
     this.zonalCommodityService.getZonalCommodityListByAczId(this.updateFertilizerForm.value.aczId).subscribe((data: {}) => {
      this.zonalCommodityList = data;
    })
  }

  getStateCodeAczIdByZonalCommodityId(){
    return this.zonalCommodityService.getStateCodeAczIdByZonalCommodityId(this.updateFertilizerForm.value.zonalCommodityId).subscribe(data => {
        
        this.stateCodeAczId = data;
        this.updateFertilizerForm.patchValue(this.stateCodeAczId);

        this.getAllAczByStateCodeForEdit();
        this.getZonalCommodityListByAczIdForEdit();

        this.updateFertilizerForm.patchValue({ zonalCommodityId: this.editFertilizer.zonalCommodityId});

   })
  }

  //DoseFactor list
  loadAllDoseFactor() {
    return this.agriDoseFactorService.GetAllAgriDoseFactor().subscribe((data: {}) => {
      this.DoseFactorList = data;
    })
  }

      //Uom list
  loadAllUom() {
    return this.generalUomService.GetAllUom().subscribe((data: {}) => {
      this.UomList = data;
    })
  }

  loadAllCommodityBySeason() {
    let seasonId = this.updateFertilizerForm.value.seasonId;
    this.commodityService.getCommodityBySeasonId(seasonId).subscribe(
      (data: {}) => {
        this.CommodityList = data;
        console.log(this.CommodityList);
      }
    );
  }

   //Season list
   loadAllSeason() {
    return this.agriSeasonService.GetAllSeasons().subscribe((data: {}) => {
      this.SeasonList = data;
    })
  }

    modalSuccess($event: any) {
        this.router.navigate(['/zonal/fertilizer']);
    }
}

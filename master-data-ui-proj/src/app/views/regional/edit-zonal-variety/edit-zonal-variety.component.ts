import { AczService } from '../../zonal/services/acz.service';
import { ZonalCommodityService } from '../../zonal/services/zonal-commodity.service';
import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ZonalVarietyService } from '../services/zonal-variety.service';
import { AgriVarietyService } from '../../agri/services/agri-variety.service';
import { AgriCommodityService } from '../../agri/services/agri-commodity.service';
import { GeoRegionService } from '../../geo/services/geo-region.service';
import { GeoStateService } from '../../geo/services/geo-state.service';
import { AgriSeasonService } from '../../agri/services/agri-season.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-edit-zonal-variety',
  templateUrl: './edit-zonal-variety.component.html',
  styleUrls: ['./edit-zonal-variety.component.scss']
})
export class EditZonalVarietyComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  // regionList: any = [];
  // seasonList: any = [];
  aczList: any = [];
  zonalCommodityList: any = [];
  varietyList: any = [];
  VarietyList: any = [];
  StateList: any = [];
  sowingHarvestWeekStartEndByZonalCommodityId:any;
  updateVarietyForm: FormGroup;
  stateCode : any;
  zonalVariety:any;
  commodityId:any;
  weekList:any = [];
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  sowingWeekStartList:any = [];
  sowingWeekEndList:any = [];
  harvestWeekStartList:any = [];
  harvestWeekEndList:any = [];

  ngOnInit() {
    this.updateForm();
    // this.loadAllRegion();
    //this.loadAllSeason();
    //this.loadAllCommodities();
    //this.loadAllVariety();
    this.loadAllState();
    
    for (let i = 1; i <= 52; i++) {
      this.weekList.push(i);
    }
  }

  constructor(
    private actRoute: ActivatedRoute,
    public zonalVarietyService: ZonalVarietyService,
    public agriVarietyService: AgriVarietyService,
    public commodityService: AgriCommodityService,
    public agriSeasonService: AgriSeasonService,
    // public geoRegionService : GeoRegionService,
    public geoStateService: GeoStateService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    private zonalCommodityService: ZonalCommodityService,
    private aczService: AczService,
  ) {
    
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.zonalVarietyService.GetVariety(id).subscribe(data => {
      this.zonalVariety = data;
      // this.updateVarietyForm = this.fb.group({
      //   // regionId: [data.regionId,Validators.required],
      //   commodityId: [data.commodityId, Validators.required],
      //   //seasonId: [data.seasonId, Validators.required],
      //   varietyId: [data.varietyId, Validators.required],
      //   stateCode: ['', Validators.required],
      //   aczId: [data.aczId, Validators.required],
      //   status: [data.status],
      //   sowingWeekStart: new FormControl(data.sowingWeekStart, [
      //     Validators.required,
      //     Validators.pattern("^[0-9]*$"),
      //   ]),
      //   sowingWeekEnd: new FormControl(data.sowingWeekEnd, [
      //     Validators.required,
      //     Validators.pattern("^[0-9]*$"),
      //   ]),
      //   harvestWeekStart: new FormControl(data.harvestWeekStart, [
      //     Validators.required,
      //     Validators.pattern("^[0-9]*$"),
      //   ]),
      //   harvestWeekEnd: new FormControl(data.harvestWeekEnd, [
      //     Validators.required,
      //     Validators.pattern("^[0-9]*$"),
      //   ]),
      //})
      this.updateVarietyForm.patchValue(this.zonalVariety);
      

      
  
      // if(this.zonalVariety.sowingWeekStart <= this.zonalVariety.sowingWeekEnd){
      //   for(let i=this.zonalVariety.sowingWeekStart;i<=this.zonalVariety.sowingWeekEnd;i++){
      //     this.sowingWeekEndList.push(i);
      //   }
      // }else{
      //   for(let j=1;j<=this.zonalVariety.sowingWeekEnd;j++){
      //    this.sowingWeekEndList.push(j);
      //  }
      //  for(let i=this.zonalVariety.sowingWeekStart;i > this.zonalVariety.sowingWeekEnd;i++){
      //    if(i!=53){
      //      this.sowingWeekEndList.push(i);
      //    }
      //    else{
      //      break;
      //    }
      //  }
       
      // }

      // if(this.zonalVariety.sowingWeekStart <= this.zonalVariety.sowingWeekEnd){
      //   for(let i=this.zonalVariety.sowingWeekStart;i<=this.zonalVariety.sowingWeekEnd;i++){
      //     this.sowingWeekStartList.push(i);
      //   }
      // }else{
      //   for(let j=1;j<=this.zonalVariety.sowingWeekEnd;j++){
      //    this.sowingWeekStartList.push(j);
      //  }
      //  for(let i=this.zonalVariety.sowingWeekStart;i > this.zonalVariety.sowingWeekEnd;i++){
      //    if(i!=53){
      //      this.sowingWeekStartList.push(i);
      //    }
      //    else{
      //      break;
      //    }
      //  }
       
      // }

      // if(this.zonalVariety.harvestWeekStart <= this.zonalVariety.harvestWeekEnd){
      //   for(let i=this.zonalVariety.harvestWeekStart;i<=this.zonalVariety.harvestWeekEnd;i++){
      //     this.harvestWeekStartList.push(i);
      //   }
      // }else{
      //   for(let j=1;j<=this.zonalVariety.harvestWeekEnd;j++){
      //    this.harvestWeekStartList.push(j);
      //  }
      //  for(let i=this.zonalVariety.harvestWeekStart;i > this.zonalVariety.harvestWeekEnd;i++){
      //    if(i!=53){
      //      this.harvestWeekStartList.push(i);
      //    }
      //    else{
      //      break;
      //    }
      //  }
       
      // }
  
      // if(this.zonalVariety.harvestWeekStart <= this.zonalVariety.harvestWeekEnd){
      //   for(let i=this.zonalVariety.harvestWeekStart;i<=this.zonalVariety.harvestWeekEnd;i++){
      //     this.harvestWeekEndList.push(i);
      //   }
      // }else{
      //   for(let j=1;j<=this.zonalVariety.harvestWeekEnd;j++){
      //    this.harvestWeekEndList.push(j);
      //  }
      //  for(let i=this.zonalVariety.harvestWeekStart;i > this.zonalVariety.harvestWeekEnd;i++){
      //    if(i!=53){
      //      this.harvestWeekEndList.push(i);
      //    }
      //    else{
      //      break;
      //    }
      //  }
       
      // }
      this.getSowingHarvestWeekStartEndByZonalCommodityId();
      this.getStateCodeByAczId();
    })
  }

  updateForm() {
    this.updateVarietyForm = this.fb.group({
      stateCode: ['', Validators.required],
      aczId: ['', Validators.required],
      // regionId: ['',Validators.required],
      commodityId: ['', Validators.required],
      zonalCommodityId: ['', Validators.required],
      varietyId: ['', Validators.required],
      //seasonId: ['', Validators.required],
      sowingWeekStart: ['', Validators.required],
      sowingWeekEnd: ['', Validators.required],
      harvestWeekStart: ['', Validators.required],
      harvestWeekEnd: ['', Validators.required],
      status: ['']

    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {

    for (let controller in this.updateVarietyForm.controls) {
      this.updateVarietyForm.get(controller).markAsTouched();
    }

    if (this.updateVarietyForm.invalid) {
      return;
    }


    if (this.updateVarietyForm.get('stateCode').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select State', '');
      return;
    }

    // if (this.updateVarietyForm.get('seasonId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select Season', '');

    //   return;
    // }
    if (this.updateVarietyForm.get('aczId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Season', '');

      return;
    }

    // if(this.updateVarietyForm.get('regionId').value == 0){
    //   alert('Please Select Region');
    //   return;
    // }
    if (this.updateVarietyForm.get('commodityId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Zonal Commodity', '');

      return;
    }

    if (this.updateVarietyForm.get('varietyId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Variety', '');

      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.zonalVarietyService.UpdateVariety(id, this.updateVarietyForm.value).subscribe(res => {
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

  //StateList
  loadAllState() {
    return this.geoStateService.GetAllState().subscribe((data: {}) => {
      this.StateList = data;
    })
  }

  //  //Regionlist
  //  loadAllRegion(){
  //   return this.geoRegionService.GetAllRegion().subscribe((data: {}) => {
  //     this.regionList = data;
  //     })
  //   }

  //Seasonlist
  // loadAllSeason() {
  //   return this.agriSeasonService.GetAllSeasons().subscribe((data: {}) => {
  //     this.seasonList = data;
  //   })
  // }
  onChangeLoadAczByStateCode() {
    // alert(this.updateVarietyForm.value.stateCode);
    this.updateVarietyForm.patchValue({aczId:''});
    return this.aczService.getAllAczByStateCode(this.updateVarietyForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }

  loadAczByStateCode() {
    // alert(this.updateVarietyForm.value.stateCode);
    return this.aczService.getAllAczByStateCode(this.updateVarietyForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }

  //ZonalCommoditylist
  onChangeLoadZonalCommodityByAczId() {
    this.updateVarietyForm.patchValue({zonalCommodityId:''});
    return this.zonalCommodityService.getZonalCommodityListByAczId(this.updateVarietyForm.value.aczId).subscribe((data: {}) => {
      this.zonalCommodityList = data;
      this.updateVarietyForm.patchValue({sowingWeekStart:this.zonalCommodityList.sowingWeekStart});
       this.updateVarietyForm.patchValue({sowingWeekEnd:this.zonalCommodityList.sowingWeekEnd});
       this.updateVarietyForm.patchValue({harvestWeekStart:this.zonalCommodityList.harvestWeekStart});
       this.updateVarietyForm.patchValue({harvestWeekEnd:this.zonalCommodityList.harvestWeekEnd});
    })
  }

  loadZonalCommodityByAczId() {
    return this.zonalCommodityService.getZonalCommodityListByAczId(this.updateVarietyForm.value.aczId).subscribe((data: {}) => {
      this.zonalCommodityList = data;
    })
  }

  getSowingHarvestWeekStartEndByZonalCommodityId(){
    return this.zonalCommodityService.getSowingHarvestWeekStartEndByZonalCommodityId(this.updateVarietyForm.value.zonalCommodityId).subscribe(data  => {
    this.sowingHarvestWeekStartEndByZonalCommodityId = data;

    if(this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekStart <= this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd){
      for(let i=this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekStart;i<=this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd;i++){
        this.sowingWeekStartList.push(i);
      }
    }else{
      for(let j=1;j<=this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd;j++){
       this.sowingWeekStartList.push(j);
     }
     for(let i=this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekStart;i > this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd;i++){
       if(i!=53){
         this.sowingWeekStartList.push(i);
       }
       else{
         break;
       }
     }
     
    }


    if(this.zonalVariety.sowingWeekStart <= this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd){
      for(let i=this.zonalVariety.sowingWeekStart;i<=this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd;i++){
        this.sowingWeekEndList.push(i);
      }
    }else{
      for(let j=1;j<=this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd;j++){
       this.sowingWeekEndList.push(j);
     }
     for(let i=this.zonalVariety.sowingWeekStart;i > this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd;i++){
       if(i!=53){
         this.sowingWeekEndList.push(i);
       }
       else{
         break;
       }
     }
     
    }

    if(this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekStart <= this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd){
      for(let i=this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekStart;i<=this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd;i++){
        this.harvestWeekStartList.push(i);
      }
    }else{
      for(let j=1;j<=this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd;j++){
       this.harvestWeekStartList.push(j);
     }
     for(let i=this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekStart;i > this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd;i++){
       if(i!=53){
         this.harvestWeekStartList.push(i);
       }
       else{
         break;
       }
     }
     
    }

    if(this.zonalVariety.harvestWeekStart <= this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd){
      for(let i=this.zonalVariety.harvestWeekStart;i<=this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd;i++){
        this.harvestWeekEndList.push(i);
      }
    }else{
      for(let j=1;j<=this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd;j++){
       this.harvestWeekEndList.push(j);
     }
     for(let i=this.zonalVariety.harvestWeekStart;i > this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd;i++){
       if(i!=53){
         this.harvestWeekEndList.push(i);
       }
       else{
         break;
       }
     }
     
    }


  })
}

  onChangeGetSowingHarvestWeekStartEndByZonalCommodityId(){
    return this.zonalCommodityService.getSowingHarvestWeekStartEndByZonalCommodityId(this.updateVarietyForm.value.zonalCommodityId).subscribe(data  => {
    this.sowingWeekStartList = [];
    this.sowingWeekEndList = [];
    this.harvestWeekStartList = [];
    this.harvestWeekEndList = [];
    this.sowingHarvestWeekStartEndByZonalCommodityId = data;
    this.updateVarietyForm.patchValue({sowingWeekStart:this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekStart});
    this.updateVarietyForm.patchValue({sowingWeekEnd:this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd});
    this.updateVarietyForm.patchValue({harvestWeekStart:this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekStart});
    this.updateVarietyForm.patchValue({harvestWeekEnd:this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd});

    if(this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekStart <= this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd){
      for(let i=this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekStart;i<=this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd;i++){
        this.sowingWeekStartList.push(i);
      }
    }else{
      for(let j=1;j<=this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd;j++){
       this.sowingWeekStartList.push(j);
     }
     for(let i=this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekStart;i > this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd;i++){
       if(i!=53){
         this.sowingWeekStartList.push(i);
       }
       else{
         break;
       }
     }
     
    }

    if(this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekStart <= this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd){
      for(let i=this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekStart;i<=this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd;i++){
        this.sowingWeekEndList.push(i);
      }
    }else{
      for(let j=1;j<=this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd;j++){
       this.sowingWeekEndList.push(j);
     }
     for(let i=this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekStart;i > this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd;i++){
       if(i!=53){
         this.sowingWeekEndList.push(i);
       }
       else{
         break;
       }
     }
     
    }

    if(this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekStart <= this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd){
      for(let i=this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekStart;i<=this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd;i++){
        this.harvestWeekStartList.push(i);
      }
    }else{
      for(let j=1;j<=this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd;j++){
       this.harvestWeekStartList.push(j);
     }
     for(let i=this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekStart;i > this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd;i++){
       if(i!=53){
         this.harvestWeekStartList.push(i);
       }
       else{
         break;
       }
     }
     
    }

    if(this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekStart <= this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd){
      for(let i=this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekStart;i<=this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd;i++){
        this.harvestWeekEndList.push(i);
      }
    }else{
      for(let j=1;j<=this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd;j++){
       this.harvestWeekEndList.push(j);
     }
     for(let i=this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekStart;i > this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd;i++){
       if(i!=53){
         this.harvestWeekEndList.push(i);
       }
       else{
         break;
       }
     }
     
    }
   })
 }
 onChangeSowingWeekStart(){
  this.sowingWeekEndList = [];
  if(this.updateVarietyForm.value.sowingWeekStart == this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd){
    this.updateVarietyForm.value.sowingWeekEnd;
    this.sowingWeekEndList.push(parseInt(this.updateVarietyForm.value.sowingWeekStart));
    this.updateVarietyForm.value.sowingWeekEnd;
  }else{
    if(this.updateVarietyForm.value.sowingWeekStart < this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd){
      for(let i=this.updateVarietyForm.value.sowingWeekStart;i<=this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd;i++){
        this.sowingWeekEndList.push(i);
      }
    }else{
      for(let j=1;j<=this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd;j++){
       this.sowingWeekEndList.push(j);
     }
     for(let i=this.updateVarietyForm.value.sowingWeekStart;i > this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd;i++){
       if(i!=53){
         this.sowingWeekEndList.push(i);
       }
       else{
         break;
       }
     }
    }
  }
  
}
onChangeHarvestWeekStart(){
  this.harvestWeekEndList = [];
  if(this.updateVarietyForm.value.harvestWeekStart == this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd){
    this.harvestWeekEndList.push(parseInt(this.updateVarietyForm.value.harvestWeekStart));
  }else{
    if(this.updateVarietyForm.value.harvestWeekStart < this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd){
      for(let i=this.updateVarietyForm.value.harvestWeekStart;i<=this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd;i++){
        this.harvestWeekEndList.push(i);
      }
    }else{
      for(let j=1;j<=this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd;j++){
       this.harvestWeekEndList.push(j);
     }
     for(let i=this.updateVarietyForm.value.harvestWeekStart;i > this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd;i++){
       if(i!=53){
         this.harvestWeekEndList.push(i);
       }
       else{
         break;
       }
     }
    }
  }
}
  //VarietyList
  loadAllVarietyByCommodityId() {
    return this.agriVarietyService.getVarietyListByCommodityId(this.commodityId.commodityId).subscribe((data: {}) => {
      this.varietyList = data;
    })
  }

  // loadAllVarietyByCommodity() {
  //   let commodityId = this.updateVarietyForm.value.commodityId;
  //   this.agriVarietyService.GetAllVarietyByCommodityId(commodityId).subscribe(
  //     (data: {}) => {
  //       this.varietyList = data;
  //       console.log(this.varietyList);
  //     }
  //   );
  // }

  getCommodityIdByZonalCommodityIdOnChange(){
    return this.zonalCommodityService.getCommodityIdByZonalCommodityId(this.updateVarietyForm.value.zonalCommodityId).subscribe(data => {
     this.commodityId = data;
     this.updateVarietyForm.patchValue({commodityId:this.commodityId.commodityId});
     this.loadAllVarietyByCommodityId();
     this.onChangeGetSowingHarvestWeekStartEndByZonalCommodityId();
    })
  }

  loadVarietyAndSowingHarvestWeekStartEndOnChange(){
    this.updateVarietyForm.patchValue({varietyId:''});
    this.getCommodityIdByZonalCommodityIdOnChange();
  }

  getCommodityIdByZonalCommodityId(){
    return this.zonalCommodityService.getCommodityIdByZonalCommodityId(this.updateVarietyForm.value.zonalCommodityId).subscribe(data => {
     this.commodityId = data;
     this.updateVarietyForm.patchValue({commodityId:this.commodityId.commodityId});
     this.loadAllVarietyByCommodityId();
    //  this.getSowingHarvestWeekStartEndByZonalCommodityId();
    })
  }

  loadVarietyListByCommodityIdOnChange(){
    this.getCommodityIdByZonalCommodityId();
  }

  getStateCodeByAczId() {
    return this.aczService.getStateCodeByAczId(this.updateVarietyForm.value.aczId).subscribe(data => {
      this.stateCode = data;
      this.updateVarietyForm.patchValue(this.stateCode);
      this.loadAczByStateCode();
      this.loadZonalCommodityByAczId();
      this.loadVarietyListByCommodityIdOnChange();
      this.updateVarietyForm.patchValue({aczId:this.zonalVariety.aczId});
      this.updateVarietyForm.patchValue({commodityId:this.zonalVariety.commodityId});
      this.updateVarietyForm.patchValue({zonalCommodityId:this.zonalVariety.zonalCommodityId});
      // alert("VarietyId = "+this.zonalVariety.varietyId);
      this.updateVarietyForm.patchValue({varietyId:this.zonalVariety.varietyId});
      
    })
  }

    modalSuccess($event: any) {
        this.router.navigate(['/zonal/variety']);
    }
}

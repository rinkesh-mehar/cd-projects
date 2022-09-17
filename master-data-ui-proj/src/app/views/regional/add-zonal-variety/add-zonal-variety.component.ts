import { ZonalCommodityService } from '../../zonal/services/zonal-commodity.service';
import { AczService } from '../../zonal/services/acz.service';
import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators, FormControl} from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import {ZonalVarietyService} from '../services/zonal-variety.service';
import {AgriVarietyService} from '../../agri/services/agri-variety.service';
import {AgriCommodityService} from '../../agri/services/agri-commodity.service';
import {GeoStateService} from '../../geo/services/geo-state.service';
import {ApiUtilService} from '../../services/api-util.service';
import {AgriSeasonService} from '../../agri/services/agri-season.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
    selector: 'app-add-zonal-variety',
    templateUrl: './add-zonal-variety.component.html',
    styleUrls: ['./add-zonal-variety.component.scss']
})
export class AddZonalVarietyComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    // regionList: any = [];
    //seasonList: any = [];
    stateCode:any;
    commID:any;
    otherVarietyID:any;

    mode = "add";
    aczList: any = [];
    sowingHarvestWeekStartEndByZonalCommodityId:any;
    zonalCommodityList: any = [];
    varietyList: any = [];
    StateList: any = [];
    varietyForm: FormGroup;
    varietyArr: any = [];
    uploadFile: File = null;
    imgPerview: any;
    weekList: number[] = [];
    commodityId:any;
    sowingWeekStartList:any = [];
    sowingWeekEndList:any = [];
    harvestWeekStartList:any = [];
    harvestWeekEndList:any = [];

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

    ngOnInit() {
        this.loadAllState();
        // this.loadAllSeason();
        // this.loadAllCommodities();
        // this.loadAllRegion();
        // this.loadAllVariety();
        this.addVariety();

        this.stateCode = this.actRoute.snapshot.paramMap.get('stateCode');
        this.commID = this.actRoute.snapshot.paramMap.get('commodityID');
        this.otherVarietyID = this.actRoute.snapshot.paramMap.get('otherVarietyID');
        // console.log("before stateCode : " + this.stateCode + " before commodityId : " + this.commID + " before alternateVarietyID : " + this.alternateVarietyID);
        if(this.stateCode != null && this.commID !=null && this.otherVarietyID != null){
          this.mode = "addOtherVarietyWise";
          this.loadVarietyByCommodityId();
          this.varietyForm.patchValue({stateCode:this.stateCode});
          this.varietyForm.patchValue({commodityId:this.commID});
          this.varietyForm.patchValue({varietyId:this.otherVarietyID});
          this.loadAczByStateCode();
        }
        
        // for (let i = 1; i <= 52; i++) {
        //     this.weekList.push(i);
        // }
        // console.log(`week list is ${this.weekList}`);
        // this.loadList();
    }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public zonalVarietyService: ZonalVarietyService,
    public agriVarietyService :   AgriVarietyService,
    public commodityService : AgriCommodityService,
    public agriSeasonService : AgriSeasonService,
    // public geoRegionService : GeoRegionService,
    public geoStateService : GeoStateService,
    public apiUtilService: ApiUtilService,
    public aczService: AczService,
    public zonalCommodityService: ZonalCommodityService,
    public actRoute: ActivatedRoute,
  ){ }

  addVariety() {
      this.varietyForm = this.fb.group({
          stateCode: ['', Validators.required],
          aczId: ['', Validators.required],
          // regionId: ['',Validators.required],
          sowingWeekStart: ['', Validators.required],
          sowingWeekEnd: ['', Validators.required],
          harvestWeekStart: ['', Validators.required],
          harvestWeekEnd: ['', Validators.required],
          //seasonId: ['', Validators.required],
          commodityId: ['', Validators.required],
          zonalCommodityId: ['', Validators.required],
          varietyId: ['', Validators.required],
          status: ['Inactive'],
          // sowingWeekStart:  new FormControl('', [
          //   Validators.required,
          //   Validators.pattern("^[0-9]*$"),
          // ]),
          // sowingWeekEnd: new FormControl('', [
          //   Validators.required,
          //   Validators.pattern("^[0-9]*$"),
          // ]),
          // harvestWeekStart:new FormControl('', [
          //   Validators.required,
          //   Validators.pattern("^[0-9]*$"),
          // ]),
          // harvestWeekEnd: new FormControl('', [
          //   Validators.required,
          //   Validators.pattern("^[0-9]*$"),
          // ]),
      });
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

    submitForm() {
// alert(this.varietyForm.value.commodityId);
// alert(JSON.stringify(this.varietyForm.value));
    for (let controller in this.varietyForm.controls) {
      this.varietyForm.get(controller).markAsTouched();
    }

    if (this.varietyForm.invalid) {
      return;
    }

        if (this.varietyForm.get('stateCode').value == 0) {
            alert('Please Select State');
            return;
        }
        if (this.varietyForm.get('aczId').value == 0) {
          alert('Please Select Acz');
          return;
      }

        // if(this.varietyForm.get('regionId').value == 0){
        //   alert('Please Select Region');
        //   return;
        // }
        if (this.varietyForm.get('sowingWeekStart').value === '') {
            alert('Please Select Sowing Start Week');
            return;
        }
        if (this.varietyForm.get('sowingWeekEnd').value === '') {
            alert('Please Select Sowing End Week');
            return;
        }
        if (this.varietyForm.get('harvestWeekStart').value === '') {
            alert('Please Select Harvest Start Week');
            return;
        }
        if (this.varietyForm.get('harvestWeekEnd').value === '') {
            this.errorModal.showModal('ERROR', 'Please Select Harvest End Week', '');
            return;
        }

        // if (this.varietyForm.get('seasonId').value == 0) {
        //     this.errorModal.showModal('ERROR', 'Please Select Season', '');

        //     return;
        // }
        if (this.varietyForm.get('commodityId').value == 0) {
            this.errorModal.showModal('ERROR', 'Please Select Zonal Commodity', '');

            return;
        }

        if (this.varietyForm.get('varietyId').value == 0) {
            this.errorModal.showModal('ERROR', 'Please Select Variety', '');
            return;
        }

        this.zonalVarietyService.CreateVariety(this.varietyForm.value).subscribe(res => {
            this.isSubmitted = true;
            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                  this.addVariety();
                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            }
        });
    }

   //StateList
   loadAllState(){
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

   //SeasonList
  //  loadAllSeason(){
  //  return this.agriSeasonService.GetAllSeasons().subscribe((data: {}) =>{
  //   this.seasonList = data;
  //  })
  //   }
    //Acz List
    loadAczByStateCode(){
      this.varietyForm.patchValue({aczId:''});
      // console.log("statecode: "+this.varietyForm.value.stateCode);
      return this.aczService.getAllAczByStateCode(this.varietyForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
      })
    }
     //Zonal Commodity List
     loadZonalCommodityByAczId(){
      this.varietyForm.patchValue({zonalCommodityId:''});
      if(this.mode == "add"){
        return this.zonalCommodityService.getZonalCommodityListByAczId(this.varietyForm.value.aczId).subscribe((data: {}) => {
          this.zonalCommodityList = data;
         })
      }else{
        return this.zonalCommodityService.getZonalCommodityByAczIDCommodityID(this.varietyForm.value.aczId,this.commID).subscribe((data: {}) => {
          this.zonalCommodityList = data;
         })
      }
      
    }

    getCommodityIdByZonalCommodityId(){
      return this.zonalCommodityService.getCommodityIdByZonalCommodityId(this.varietyForm.value.zonalCommodityId).subscribe(data => {
       this.commodityId = data;
       this.varietyForm.patchValue({commodityId:this.commodityId.commodityId});
       this.loadVarietyByCommodityId();
       this.getSowingHarvestWeekStartEndByZonalCommodityId();

      })
    }

    loadVarietyAndSowingHarvestWeekStartEnd(){
      if(this.mode=='add'){
        this.varietyForm.patchValue({commodityId:''});
      this.getCommodityIdByZonalCommodityId();
      }else{
        // this.loadVarietyByCommodityId();
        this.getSowingHarvestWeekStartEndByZonalCommodityId();
      }

    }

    getSowingHarvestWeekStartEndByZonalCommodityId(){
       return this.zonalCommodityService.getSowingHarvestWeekStartEndByZonalCommodityId(this.varietyForm.value.zonalCommodityId).subscribe(data  => {
         this.sowingWeekStartList = [];
         this.sowingWeekEndList = [];
         this.harvestWeekStartList = [];
         this.harvestWeekEndList = [];
       this.sowingHarvestWeekStartEndByZonalCommodityId = data;
       this.varietyForm.patchValue({sowingWeekStart:this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekStart});
       this.varietyForm.patchValue({sowingWeekEnd:this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd});
       this.varietyForm.patchValue({harvestWeekStart:this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekStart});
       this.varietyForm.patchValue({harvestWeekEnd:this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd});
       
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
      if(this.varietyForm.value.sowingWeekStart == this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd){
        this.sowingWeekEndList.push(parseInt(this.varietyForm.value.sowingWeekStart));
      }else{
        if(this.varietyForm.value.sowingWeekStart <= this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd){
          for(let i=this.varietyForm.value.sowingWeekStart;i<=this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd;i++){
            this.sowingWeekEndList.push(i);
          }
        }else{
          for(let j=1;j<=this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd;j++){
           this.sowingWeekEndList.push(j);
         }
         for(let i=this.varietyForm.value.sowingWeekStart;i > this.sowingHarvestWeekStartEndByZonalCommodityId.sowingWeekEnd;i++){
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
      if(this.varietyForm.value.harvestWeekStart == this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd){
        this.harvestWeekEndList.push(parseInt(this.varietyForm.value.harvestWeekStart));
      }else{
        if(this.varietyForm.value.harvestWeekStart <= this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd){
          for(let i=this.varietyForm.value.harvestWeekStart;i<=this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd;i++){
            this.harvestWeekEndList.push(i);
          }
        }else{
          for(let j=1;j<=this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd;j++){
           this.harvestWeekEndList.push(j);
         }
         for(let i=this.varietyForm.value.harvestWeekStart;i > this.sowingHarvestWeekStartEndByZonalCommodityId.harvestWeekEnd;i++){
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
    //Variety List By Commodity
    loadVarietyByCommodityId(){
      // this.getSowingHarvestWeekStartEndByZonalCommodityId();
      if(this.mode=='add'){
        this.varietyForm.patchValue({varietyId:''});
        return this.agriVarietyService.getVarietyListByCommodityId(this.commodityId.commodityId).subscribe((data: {}) => {
         this.varietyList = data;
        })
      }else{
        return this.agriVarietyService.getVarietyListByCommodityId(this.commID).subscribe((data: {}) => {
          this.varietyList = data;
          this.varietyForm.patchValue({varietyId:this.otherVarietyID});
         })
      }
     
    }
    
    //VarietyList
    loadAllVariety(){
      return this.agriVarietyService.GetAllVarieties().subscribe((data: {}) => {
        this.varietyList = data;
        })
      }

    // loadAllVarietyByCommodity() {
    //     let commodityId = this.varietyForm.value.commodityId;
    //     this.agriVarietyService.GetAllVarietyByCommodityId(commodityId).subscribe(
    //         (data: {}) => {
    //             this.varietyList = data;
    //             console.log(this.varietyList);
    //           }
    //         );
    //   }

      downloadExcelFormat(){
        var tableName = "regional_variety";
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
      if(this.mode=='add'){
        this.router.navigate(['/zonal/variety']);
      }else{
        this.router.navigate(['/zonal/other-variety']);
      }
        
    }
}

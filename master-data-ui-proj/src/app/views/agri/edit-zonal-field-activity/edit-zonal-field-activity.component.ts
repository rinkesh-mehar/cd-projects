import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { AgriPhenophaseService } from '../services/agri-phenophase.service';
import { ZonalFieldActivityService } from '../services/zonal-field-activity.service';
// import { GeoRegionService } from '../../geo/services/geo-region.service';
import { AgriSeasonService } from '../services/agri-season.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { fileSizeValidatorForDoc } from '../../validators/fileSizeValidator.validator';
import { AczService } from '../../zonal/services/acz.service';
import { GeoStateService } from '../../geo/services/geo-state.service';
import { ZonalCommodityService } from '../../zonal/services/zonal-commodity.service';
import { ZonalVarietyService } from '../../regional/services/zonal-variety.service';

@Component({
  selector: 'app-edit-zonal-field-activity',
  templateUrl: './edit-zonal-field-activity.component.html',
  styleUrls: ['./edit-zonal-field-activity.component.scss']
})
export class EditZonalFieldActivityComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  CommodityList: any = [];
  // RegionList: any = [];
  PhenophaseList: any = [];
   SeasonList: any = [];
  fieldActivityArr: any = [];
  fieldActivityList: any = [];
  updateFieldActivityForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  uploadedImage: any;
  isImageHidden: boolean = false;
  image: any;
  
  stateList: any = [];
  aczList: any = [];
  zonalcommodityList: any = [];
  // zonalvarietyList: any = [];
  stateCodeAczId : any;
  // zonalVarietyId:any;
  zonalFieldActivity : any;
  
  ngOnInit() {
    this.updateForm();
    // this.loadAllRegion();
    // this.loadAllCommodities();
    this.loadState();
    // this.loadAllPhenophase();
   
  
  }
    constructor(
    private actRoute: ActivatedRoute,    
    public zonalFieldActivityService: ZonalFieldActivityService,
    public commodityService : AgriCommodityService,
    // public geoRegionService : GeoRegionService,
    public agriPhenophaseService : AgriPhenophaseService,
    public agriSeasonService : AgriSeasonService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public aczService:AczService,
    public geoStateService:GeoStateService,
    public zonalCommodityService:ZonalCommodityService,
    // public zonalVarietyService:ZonalVarietyService,

  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.zonalFieldActivityService.GetFieldActivity(id).subscribe((data) => {
      this.zonalFieldActivity = data;
      this.updateFieldActivityForm = this.fb.group({
        stateCode : ['', Validators.required],
        aczId : ['', Validators.required],
        zonalCommodityId: [data.zonalCommodityId,Validators.required],
        // zonalVarietyId : ['', Validators.required],
        phenophaseId: [data.phenophaseId,Validators.required],
        name: [data.name,Validators.required],
        description: [data.description],
        imageFile: [''],
        status: [data.status]
        })
        this.image = data.imageUrl;
        this.updateFieldActivityForm.patchValue(this.zonalFieldActivity);
        this.getStateCodeAczIdByZonalCommodityId();
        
        // this.loadAllCommodityBySeason();
        // this.loadAllCommodityByPhenophase() 
    })
  }

  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  updateForm(){
    this.updateFieldActivityForm = this.fb.group({
      stateCode : ['', Validators.required],
        aczId : ['', Validators.required],
        zonalCommodityId: ['',Validators.required],
        // zonalVarietyId : ['', Validators.required],
      phenophaseId: ['',Validators.required],
      // seasonId: ['',Validators.required],
      name: ['',Validators.required],
      description: [''],
      imageFile: [''],
      status: ['Inactive']
  
    })    
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  //statelist
  loadState() {
    return this.geoStateService.GetAllState().subscribe((data: {}) => {
    this.stateList = data;
    })
  }

  //ACZ list
  loadAczByStateCode() {
    this.updateFieldActivityForm.patchValue({aczId:''});
    return this.aczService.getAllAczByStateCode(this.updateFieldActivityForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }

  loadAczByStateCodeForEditMode() {
    return this.aczService.getAllAczByStateCode(this.updateFieldActivityForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }

  //ZonalCommodity list
  loadZonalCommodityByAczId() {
    this.updateFieldActivityForm.patchValue({zonalCommodityId:''});
    return this.zonalCommodityService.getZonalCommodityListByAczId(this.updateFieldActivityForm.value.aczId).subscribe((data: {}) => {
      this.zonalcommodityList = data;
    })
  }

  loadZonalCommodityByAczIdForEditMode() {
    return this.zonalCommodityService.getZonalCommodityListByAczId(this.updateFieldActivityForm.value.aczId).subscribe((data: {}) => {
      this.zonalcommodityList = data;
    })
  }

  //ZonalVariety List
  // loadZonalVarietyByZonalCommodity() {
  //   // alert(this.updateFieldActivityForm.value.zonalCommodityId);
  //   return this.zonalVarietyService.getZonalVarietyListByZonalCommodityId(this.updateFieldActivityForm.value.zonalCommodityId).subscribe((data: {}) => {
  //     this.zonalvarietyList = data;
  //   })
  // }
  //Phenophase List by Zonal Variety
  getPhenophaseListByZonalCommodityId() {
    this.updateFieldActivityForm.patchValue({phenophaseId:''});
    return this.agriPhenophaseService.getPhenophaseListByZonalCommodityId(this.updateFieldActivityForm.value.zonalCommodityId).subscribe((data: {}) => {
      this.PhenophaseList = data;
    })
  }

  getPhenophaseListByZonalCommodityIdForEditMode() {
    return this.agriPhenophaseService.getPhenophaseListByZonalCommodityId(this.updateFieldActivityForm.value.zonalCommodityId).subscribe((data: {}) => {
      this.PhenophaseList = data;
    })
  }

  // getStateCodeAczIdCommodityIdPhenophaseId() {
  //   return this.zonalVarietyService.getZonalVarietyIdByStateCodeAczIdCommodityIdPhenophaseId(this.stateCodeAczId.stateCode,this.updateFieldActivityForm.value.aczId,this.zonalFieldActivity.zonalCommodityId,this.zonalFieldActivity.phenophaseId).subscribe((data: {}) => {
  //       this.zonalVarietyId = data;
  //       this.updateFieldActivityForm.patchValue(this.zonalVarietyId);
  //       this.getPhenophaseListByZonalCommodityId();
  //       this.updateFieldActivityForm.patchValue({stateCode:this.stateCodeAczId.stateCode});
  //   })
  // }


  //
  getStateCodeAczIdByZonalCommodityId() {
    //alert("CommodityId = "+this.taskTypeSpecificationForm.value.zonalCommodityID)
    return this.zonalCommodityService.getStateCodeAczIdByZonalCommodityId(this.updateFieldActivityForm.value.zonalCommodityId).subscribe((data) => {
      this.stateCodeAczId = data;
      //alert(JSON.stringify(this.stateCodeAczIdZonalVarietyId));
      this.updateFieldActivityForm.patchValue(this.stateCodeAczId);
      this.loadAczByStateCodeForEditMode();
      this.loadZonalCommodityByAczIdForEditMode();
      this.updateFieldActivityForm.patchValue({zonalCommodityId:this.zonalFieldActivity.zonalCommodityId});
      this.getPhenophaseListByZonalCommodityIdForEditMode();
      // this.getZonalVarietyIdByStateCodeAczIdCommodityIdPhenophaseId();
      this.updateFieldActivityForm.patchValue({zonalCommodityId:this.zonalFieldActivity.zonalCommodityId});
      this.updateFieldActivityForm.patchValue({phenophaseId:this.zonalFieldActivity.phenophaseId});
    })
  }

  submitForm() {

this.updateFieldActivityForm.patchValue({status:this.zonalFieldActivity.status});
    for (let controller in this.updateFieldActivityForm.controls) {
      this.updateFieldActivityForm.get(controller).markAsTouched();
    }

    if (this.updateFieldActivityForm.invalid) {
      return;
    }

    // if(this.updateFieldActivityForm.get('regionId').value == 0){
    //   alert('Please Select Region');
    //   return;
    // }

    // if (this.updateFieldActivityForm.get('zonalCommodityId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select Zonal Commodity', '');
    //   return;
    // }


    // if (this.updateFieldActivityForm.get('zonalVarietyId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select Zonal Variety', '');
    //   return;
    // }

    // if (this.updateFieldActivityForm.get('phenophaseId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select Phenophase', '');
    //   return;
    // }


    
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.zonalFieldActivityService.UpdateFieldActivity(id, this.updateFieldActivityForm.value,this.uploadedImage).subscribe(res => {
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

  //Season list
  loadAllSeason() {
    return this.agriSeasonService.GetAllSeasons().subscribe((data: {}) => {
      this.SeasonList = data;
    });
  }

  //   //Phenophase list
  //  loadAllPhenophase(){
  //   return this.agriPhenophaseService.GetAllPhenophase().subscribe((data: {}) => {
  //     this.PhenophaseList = data;
  //   })
  // }

  loadAllCommodityBySeason() {
    let seasonId = this.updateFieldActivityForm.value.seasonId;
    this.commodityService.getCommodityBySeasonId(seasonId).subscribe(
        (data: {}) => {
          this.CommodityList = data;
          console.log(this.CommodityList);
        }
    );
  }

  loadAllCommodityByPhenophase() {
    let commodityId = this.updateFieldActivityForm.value.commodityId;
    this.commodityService.getCommodityByPhenophaseId(commodityId).subscribe(
        (data: {}) => {
          this.PhenophaseList = data;
          console.log(this.PhenophaseList);
        }
    );
  }

  modalSuccess($event: any) {
    this.router.navigate(['/zonal/field-activity']);
  }

  onImageChange(element){
    this.isImageHidden = true;
    let file: File = element.target.files[0];
    // console.log("Size : ", this.uploadedLogo.size);
    this.updateFieldActivityForm.get('imageFile').setValidators([Validators.required, fileSizeValidatorForDoc(element.target.files)]);
    this.updateFieldActivityForm.get('imageFile').updateValueAndValidity();
    this.uploadedImage = file;
    // this.logo = this.uploadedLogo.name;
  }
}

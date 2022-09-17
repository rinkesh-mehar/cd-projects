import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { GeoStateService } from '../../../geo/services/geo-state.service';
import { GeoRegionService } from '../../../geo/services/geo-region.service';
import { AgriSeasonService } from '../../../agri/services/agri-season.service';
import { RegionSeasonCommodityService } from '../../services/region-season-commodity.service';
import { AgriCommodityService } from '../../../agri/services/agri-commodity.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { AczService } from '../../../zonal/services/acz.service';
import { ZonalCommodityService } from '../../../zonal/services/zonal-commodity.service';


@Component({
  selector: 'app-add-edit-region-season-commodity',
  templateUrl: './add-edit-region-season-commodity.component.html',
  styleUrls: ['./add-edit-region-season-commodity.component.scss']
})
export class AddEditRegionSeasonCommodityComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  seasonCommodityForm: FormGroup;
  seasonList: any = [];
  commodityList: any = [];
  editId: string;
  mode: string = "add";
  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  stateList: any = [];
  aczList: any = [];
  regionList: any = [];
  zonalCommodityList: any;
  zonalVarietyList: any;
  stateCodeAczIdZonalCommodityId: any = [];
  regionalCommodity: any;

  // ngOnInit() {
  //   this.loadAllState();
  //   this.loadAllRegion();
  //   this.loadAllSeason();
  //   this.loadAllCommodity();
  //   this.SeasonCommodityForm();
  // }

  ngOnInit() {
    this.seasonCommodityForm = this.fb.group({
      id: [],
      stateCode: ['', Validators.required],
      aczId: ['', Validators.required],
      regionId: ['', Validators.required],
      zonalCommodityId: ['', Validators.required],
      targetValue: ['', [Validators.required,Validators.pattern("^[0-9]*$")]],
      minLotSize: ['', [Validators.required,Validators.pattern("^[0-9]*$")]],
      maxRigtsInLot: ['', [Validators.required,Validators.pattern("^[0-9]*$")]],
      harvestRelaxation: ['', [Validators.required,Validators.pattern("^[0-9]*$")]],
      status: ['Inactive']
    });

    this.getAllState();
    // this.loadAllRegion();
    // this.loadAllSeason();
    // this.loadAllCommodity();
    // this.SeasonCommodityForm();


    this.editId = this.actRoute.snapshot.paramMap.get('id');
    if (this.editId) {
      this.mode = "edit";
      this.regionSeasonCommodityService.GetSeasonCommodity(this.editId).subscribe(data => {
        this.regionalCommodity = data;
        this.seasonCommodityForm.patchValue(data);
        this.getStateCodeAczIdByZonalCommodityId();
        // this.loadAllCommodityBySeason();
      })
    }
  }

  constructor(
    private actRoute: ActivatedRoute,
    public fb: FormBuilder,
    public regionSeasonCommodityService: RegionSeasonCommodityService,
    public geoStateService: GeoStateService,
    public aczService: AczService,
    private zonalCommodityService: ZonalCommodityService,
    public geoRegionService: GeoRegionService,
    public agriSeasonService: AgriSeasonService,
    public commodityService: AgriCommodityService,
    public apiUtilService: ApiUtilService,
    public router: Router
  ) { }


  SeasonCommodityForm() {
    this.seasonCommodityForm = this.fb.group({
      stateCode: ['', Validators.required],
      aczId: ['', Validators.required],
      regionId: ['', Validators.required],
      zonalCommodityId: ['', Validators.required],
      targetValue: ['', [Validators.required,Validators.pattern("^[0-9]*$")]],
      minLotSize: ['', [Validators.required,Validators.pattern("^[0-9]*$")]],
      maxRigtsInLot: ['', [Validators.required,Validators.pattern("^[0-9]*$")]],
      harvestRelaxation: ['', [Validators.required,Validators.pattern("^[0-9]*$")]],
      status: ['Inactive']
    })
  }

  getAllState() {
    this.geoStateService.GetAllState().subscribe((data: {}) => {
      this.stateList = data;
    })
  }

  getAllAczByStateCode() {
    this.seasonCommodityForm.patchValue({aczId:''});
    this.aczService.getAllAczByStateCode(this.seasonCommodityForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }

  getAllAczByStateCodeForEdit() {
    this.aczService.getAllAczByStateCode(this.seasonCommodityForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }

  getAllRegionByStateCode() {
    this.seasonCommodityForm.patchValue({regionId:''});
    this.geoRegionService.GetAllRegionByStateCode(this.seasonCommodityForm.value.stateCode).subscribe((data: {}) => {
      this.regionList = data;
    })
  }

  getAllRegionByStateCodeForEdit() {
    this.geoRegionService.GetAllRegionByStateCode(this.seasonCommodityForm.value.stateCode).subscribe((data: {}) => {
      this.regionList = data;
    })
  }

  getZonalCommodityListByAczId() {
    this.seasonCommodityForm.patchValue({zonalCommodityId:''});
     this.zonalCommodityService.getZonalCommodityListByAczId(this.seasonCommodityForm.value.aczId).subscribe((data: {}) => {
      this.zonalCommodityList = data;
    })
  }

  getZonalCommodityListByAczIdForEdit() {
     this.zonalCommodityService.getZonalCommodityListByAczId(this.seasonCommodityForm.value.aczId).subscribe((data: {}) => {
      this.zonalCommodityList = data;
    })
  }

  getStateCodeAczIdByZonalCommodityId(){
    return this.zonalCommodityService.getStateCodeAczIdByZonalCommodityId(this.seasonCommodityForm.value.zonalCommodityId).subscribe(data => {
        
        this.stateCodeAczIdZonalCommodityId = data;
        this.seasonCommodityForm.patchValue(this.stateCodeAczIdZonalCommodityId);

        this.getAllAczByStateCodeForEdit();
        this.getAllRegionByStateCodeForEdit();
        this.getZonalCommodityListByAczIdForEdit();
        this.seasonCommodityForm.patchValue({zonalCommodityId:this.regionalCommodity.zonalCommodityId});

   })
  }

  // //Regionlist
  // loadAllRegion() {
  //   return this.geoRegionService.GetAllRegion().subscribe((data: {}) => {
  //     this.regionList = data;
  //   })
  // }

  //seasonList
  loadAllSeason() {
    return this.agriSeasonService.GetAllSeasons().subscribe((data: {}) => {
      this.seasonList = data;
    })
  }

  //Commodity list
  loadAllCommodity() {
    return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
      this.commodityList = data;
    })
  }


  submitForm() {
    
    for (let controller in this.seasonCommodityForm.controls) {
      this.seasonCommodityForm.get(controller).markAsTouched();
    }

    if (this.seasonCommodityForm.invalid) {
      return;
    }

    // if (this.seasonCommodityForm.get('stateCode').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select State', '');
    //   return;
    // }
    // if (this.seasonCommodityForm.get('regionId').value == 0) {
    //   alert('Please Select Region');
    //   return;
    // }

    // if (this.seasonCommodityForm.get('commodityId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select Commodity', '');
    //   return;
    // }

    // if (this.seasonCommodityForm.get('seasonId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select season', '');
    //   return;
    // }

    
    if (this.mode == 'add') {
      this.add();
    } else {
      this.update();
    }
  }


  add() {
    this.regionSeasonCommodityService.CreateSeasonCommodity(this.seasonCommodityForm.value).subscribe(res => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.SeasonCommodityForm();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

  update() {
    this.seasonCommodityForm.patchValue({status:this.regionalCommodity.status});
    this.regionSeasonCommodityService.UpdateSeasonCommodity(this.editId, this.seasonCommodityForm.value).subscribe(res => {
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

  // loadAllStateByRegionId(): void {

  //   let regionId = this.seasonCommodityForm.value.regionId;

  //   this.geoRegionService.GetAllStateByRegionId(regionId).subscribe(
  //     (data: {}) => {
  //       this.stateList = data;
  //       console.log(this.stateList);
  //     }
  //   )}

  loadAllCommodityBySeason() {
    let seasonId = this.seasonCommodityForm.value.seasonId;
    this.commodityService.getCommodityBySeasonId(seasonId).subscribe(
        (data: {}) => {
          this.commodityList = data;
          console.log(this.commodityList);
        }
    );
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  downloadExcelFormat() {
    var tableName = 'regional_commodity';
    this.apiUtilService.downloadExcelFormat(tableName);
  }//downloadExcelFormat


  uploadExcel(element) {
    var file: File = element.target.files[0];
    this.fileUpload = file;
  }

  submitExcelForm() {
    this.apiUtilService.uploadExcelFile(this.fileUpload).subscribe(res => {
      this.isSubmittedBulk = true;

      if (res) {
        this.isSuccessBulk = res.success;
        if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

  modalSuccess($event: any) {
    this.router.navigate(['/regional/season-commodity']);
  }
}

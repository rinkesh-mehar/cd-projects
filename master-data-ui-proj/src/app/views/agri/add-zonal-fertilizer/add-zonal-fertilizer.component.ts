import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { Router } from '@angular/router';


import { ZonalFertilizerService } from '../services/zonal-fertilizer.service';
import { GeoRegionService } from '../../geo/services/geo-region.service';
import { AgriSeasonService } from '../services/agri-season.service';
import { GeoStateService } from '../../geo/services/geo-state.service';
import { GeneralUomService } from '../../general/services/general-uom.service';
import { AgriDoseFactorService } from '../services/agri-dose-factor.service';
import { ApiUtilService } from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { AczService } from '../../zonal/services/acz.service';
import { ZonalCommodityService } from '../../zonal/services/zonal-commodity.service';


@Component({
  selector: 'app-add-zonal-fertilizer',
  templateUrl: './add-zonal-fertilizer.component.html',
  styleUrls: ['./add-zonal-fertilizer.component.scss']
})
export class AddZonalFertilizerComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  CommodityList: any = [];
  // RegionList: any = [];
  // StateList: any = [];
  SeasonList: any = [];
  UomList : any = [];
  DoseFactorList: any = [];
  fertilizerForm: FormGroup;
  fertilizerArr: any = [];
  fertilizerTypes: string[] = ['Nitrogen', 'Phosphorus', 'Potassium','FYM/Compost','Vermicompost'];
  uploadFile: File = null;
  imgPerview: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  stateList: any = [];
  aczList: any = [];
  zonalCommodityList: any;

  ngOnInit() {
    this.getAllState();
    this.loadAllDoseFactor();
    this.loadAllUom();
    this.addFertilizer();
    
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public agriFertilizerService: ZonalFertilizerService,
    public commodityService: AgriCommodityService,
    public geoStateService: GeoStateService,
    public geoRegionService: GeoRegionService,
    public agriDoseFactorService : AgriDoseFactorService,
    public agriSeasonService: AgriSeasonService,
    public generalUomService:GeneralUomService,
    public apiUtilService: ApiUtilService,
    private aczService: AczService,
    private zonalCommodityService: ZonalCommodityService,
  ) { }

  addFertilizer() {
    this.fertilizerForm = this.fb.group({
      stateCode: ['', Validators.required],
      aczId: ['', Validators.required],
      zonalCommodityId: ['', Validators.required],
      uomId: ['', Validators.required],
      doseFactorId: ['', Validators.required],
      name: ['', Validators.required],
      dose: ['', Validators.required],
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

  submitForm() {

    for (let controller in this.fertilizerForm.controls) {
      this.fertilizerForm.get(controller).markAsTouched();
    }

    if (this.fertilizerForm.invalid) {
      return;
    }

   

    if (this.fertilizerForm.get('stateCode').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select State', '');
      return;
    }
   
    if (this.fertilizerForm.get('zonalCommodityId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Zonal Commodity', '');
      return;
    }


    if (this.fertilizerForm.get('doseFactorId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select DoseFactor', '');
      return;
    }

    if (this.fertilizerForm.get('uomId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select UOM', '');
      return;
    }

    if (this.fertilizerForm.get('name').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Fertilizer', '');
      return;
    }

    

    this.agriFertilizerService.CreateFertilizer(this.fertilizerForm.value).subscribe(res => {
      this.isSubmitted = true;

      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.addFertilizer();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

  getAllState() {
    this.geoStateService.GetAllState().subscribe((data: {}) => {
      this.stateList = data;
    })
  }

  getAllAczByStateCode() {
    this.fertilizerForm.patchValue({aczId:''});
    this.aczService.getAllAczByStateCode(this.fertilizerForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }

  getZonalCommodityListByAczId() {
    this.fertilizerForm.patchValue({zonalCommodityId:''});
     this.zonalCommodityService.getZonalCommodityListByAczId(this.fertilizerForm.value.aczId).subscribe((data: {}) => {
      this.zonalCommodityList = data;
    })
  }

  // //Region list
  // loadAllRegion() {
  //   return this.geoRegionService.GetAllRegion().subscribe((data: {}) => {
  //     this.RegionList = data;
  //   })
  // }

  //Commodity list
  loadAllCommodities() {
    return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
      this.CommodityList = data;
    });
  }

  //Season list
  loadAllSeason() {
    return this.agriSeasonService.GetAllSeasons().subscribe((data: {}) => {
      this.SeasonList = data;
    });
  }

  //DoseFactor List
  loadAllDoseFactor() {
    return this.agriDoseFactorService.GetAllAgriDoseFactor().subscribe((data: {}) => {
      this.DoseFactorList = data;
    });
  }

  //Uom list
  loadAllUom() {
    return this.generalUomService.GetAllUom().subscribe((data: {}) => {
      this.UomList = data;
    });
  }

  loadAllCommodityBySeason() {
    let seasonId = this.fertilizerForm.value.seasonId;
    this.commodityService.getCommodityBySeasonId(seasonId).subscribe(
        (data: {}) => {
          this.CommodityList = data;
          console.log(this.CommodityList);
        }
    );
  }

  downloadExcelFormat() {
    var tableName = 'zonal_fertilizer';
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
    this.router.navigate(['/zonal/fertilizer']);
  }
}

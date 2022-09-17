import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { AgriVarietyService } from '../services/agri-variety.service';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { AgriSeasonService } from '../services/agri-season.service';
import { GeoStateService } from '../../geo/services/geo-state.service';
import { AgriPhenophaseService } from '../services/agri-phenophase.service';
import { ZonalPhenophaseDurationService } from '../services/zonal-phenophase-duration.service';
import { DomSanitizer } from '@angular/platform-browser';
import { environment } from '../../../../environments/environment';
import { ApiUtilService } from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { AczService } from '../../zonal/services/acz.service';
import { ZonalCommodityService } from '../../zonal/services/zonal-commodity.service';
import { ZonalVarietyService } from '../../regional/services/zonal-variety.service';


@Component({
  selector: 'app-add-zonal-phenophase-duration',
  templateUrl: './add-zonal-phenophase-duration.component.html',
  styleUrls: ['./add-zonal-phenophase-duration.component.scss']
})
export class AddZonalPhenophaseDurationComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  zonalCommodityList: any = [];
  zonalVarietyList: any = [];
  PhenophaseList: any = [];
  // SeasonList: any = [];
  StateList: any = [];
  aczList :any=[];
  phenophaseDurationForm: FormGroup;
  phenophaseDurationArr: any = [];
  // imgPerview: any;
  // uploadFile: File;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  ngOnInit() {
    this.getAllState();
    // this.loadAllCommodities();
    // this.loadAllVariety();
     this.getAllPhenophase();
    // this.loadAllState();
    // this.loadAllSeason();
    this.addPhenophaseDuration();
    

  }

  constructor(
    public fb: FormBuilder,
    public agriPhenophaseDurationService: ZonalPhenophaseDurationService,
    public commodityService: AgriCommodityService,
    public agriVarietyService: AgriVarietyService,
    public agriPhenophaseService: AgriPhenophaseService,
    public agriSeasonService: AgriSeasonService,
    public geoStateService: GeoStateService,
    public aczService : AczService,
    public zonalCommodityService : ZonalCommodityService,
    public zonalVarietyService : ZonalVarietyService,
    private sanitizer: DomSanitizer,
    public apiUtilService: ApiUtilService,
    public router: Router
    

  ) { }

  addPhenophaseDuration() {
    this.phenophaseDurationForm = this.fb.group({
      // commodityId: ['', Validators.required],
      // varietyId: ['', Validators.required],
     
      // seasonId: ['', Validators.required],
      stateCode: ['', Validators.required],
      aczId : ['', Validators.required],
      zonalCommodityId :['', Validators.required],
      zonalVarietyId :['', Validators.required],
      phenophaseId: ['', Validators.required],

      
      
      status: ['Inactive'],

      startDas: ['', [
        Validators.required,
        Validators.pattern("^[0-9]*$"),
      ]],
      endDas: ['', [
        Validators.required,
        Validators.pattern("^[0-9]*$"),
      ]],
      // phenophase: new FormControl('', [
      //   Validators.required,
      //   Validators.pattern("^[0-9]*$"),
      // ]),
      phenophaseOrder: ['', [
        Validators.required,
        Validators.pattern("^[0-9]*$"),
      ]],

    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {

    for (let controller in this.phenophaseDurationForm.controls) {
      this.phenophaseDurationForm.get(controller).markAsTouched();
    }

    if (this.phenophaseDurationForm.invalid) {
      return;
    }


    // if (this.phenophaseDurationForm.get('stateCode').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select State', '');
    //   return;
    // }
    // if (this.phenophaseDurationForm.get('aczId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select ACZ', '');
    //   return;
    // }
    // if (this.phenophaseDurationForm.get('zonalCommodityId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select Zonal Commodity', '');
    //   return;
    // }
    // if (this.phenophaseDurationForm.get('zonalVarietyId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select Zonal Viriety', '');
    //   return;
    // }
    // if (this.phenophaseDurationForm.get('seasonId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select Season', '');
    //   return;
    // }


    
    this.agriPhenophaseDurationService.CreatePhenophaseDuration(this.phenophaseDurationForm.value).subscribe(res => {
      this.isSubmitted = true;

      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.addPhenophaseDuration();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }


  //State list
  getAllState() {
    this.geoStateService.GetAllState().subscribe((data: {}) => {
      this.StateList = data;
    });
  }


  getAllAczByStateCode(){
    this.phenophaseDurationForm.patchValue({aczId: ''});
this.aczService.getAllAczByStateCode(this.phenophaseDurationForm.value.stateCode).subscribe((data: {}) => {
    this.aczList=data;
    });
  }

  getZonalCommodityListByAczId(){
    this.phenophaseDurationForm.patchValue({zonalCommodityId: ''});
    this.zonalCommodityService.getZonalCommodityListByAczId(this.phenophaseDurationForm.value.aczId).subscribe((data : {})=> {
      this.zonalCommodityList=data;
      });
  }

  getZonalVarietyListByZonalCommodityId(){
    this.phenophaseDurationForm.patchValue({zonalVarietyId: ''});
    this.zonalVarietyService.getZonalVarietyListByZonalCommodityId(this.phenophaseDurationForm.value.zonalCommodityId).subscribe((data : {})=>{
    this.zonalVarietyList=data;
    });
  }

  getAllPhenophase() {
    return this.agriPhenophaseService.GetAllPhenophase().subscribe((data: {}) => {
      this.PhenophaseList = data;
    });
  }

  //Commodity list
  // loadAllCommodities() {
  //   return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
  //     this.CommodityList = data;
  //   });
  // }

  // //Variety list
  // loadAllVariety() {
  //   return this.agriVarietyService.GetAllVarieties().subscribe((data: {}) => {
  //     this.VarietyList = data;
  //   });
  // }

  //Seasonlist
  // loadAllSeason() {
  //   return this.agriSeasonService.GetAllSeasons().subscribe((data: {}) => {
  //     this.SeasonList = data;
  //   });
  // }




  //Upload file
  // fileChange(element) {
  //   var file: File = element.target.files[0];
  //   var idxDot = file.name.lastIndexOf('.') + 1;
  //   var extFile = file.name.substr(idxDot, file.name.length).toLowerCase();
  //   if (extFile == 'jpeg') {
  //     this.compressImage(element);
  //   } else {
  //     element.target.value = null;
  //     alert('Invalid format File Format, Only jpeg files are allowed!');
  //     return;
  //   }
  // }


  // compressImage(element) {

  //   const width = environment.imageResizeWidth;
  //   let height = environment.imageResizeHeight;

  //   const fileName = element.target.files[0].name;
  //   const reader: FileReader = new FileReader();
  //   reader.readAsDataURL(element.target.files[0]);
  //   reader.onload = event => {
  //     const img: any = new Image();
  //     img.src = (<FileReader>event.target).result;
  //     img.onload = () => {
  //       const elem = document.createElement('canvas');
  //       if (environment.preserveImageAspectRatio) {
  //         const scaleFactor = width / img.width;
  //         height = img.height * scaleFactor;
  //       }

  //       elem.width = width;
  //       elem.height = height;

  //       const ctx = elem.getContext('2d');
  //       ctx.drawImage(img, 0, 0, width, height);
  //       ctx.canvas.toBlob((blob) => {
  //         const file = new File([blob], fileName, {
  //           type: 'image/jpeg',
  //           lastModified: Date.now()
  //         });
  //         this.uploadFile = file;
  //       }, 'image/jpeg', 1);
  //       this.imgPerview = ctx.canvas.toDataURL();
  //     },
  //         reader.onerror = error => console.log(error);
  //   };
  // }


  // loadAllCommodityByStateCodeAndSeasonId() {
  //   let seasonId = this.phenophaseDurationForm.value.seasonId;
  //   let stateCode = this.phenophaseDurationForm.value.stateCode;
  //   this.commodityService.getCommodityByStateCodeAndSeasonId(stateCode, seasonId).subscribe(
  //       (data: {}) => {
  //         this.CommodityList = data;
  //         console.log(this.CommodityList);
  //       }
  //   );
  // }

  // loadAllVarietyByCommodity() {
  //   let commodityId = this.phenophaseDurationForm.value.commodityId;
  //   this.agriVarietyService.GetAllVarietyByCommodityId(commodityId).subscribe(
  //       (data: {}) => {
  //         this.VarietyList = data;
  //         console.log(this.VarietyList);
  //       }
  //   );
  // }

  // loadAllSeasonByStateCode() {
  //   let stateCode = this.phenophaseDurationForm.value.stateCode;
  //   this.agriSeasonService.getSeasonByStateCode(stateCode).subscribe(
  //       (data: {}) => {
  //         this.SeasonList = data;
  //         console.log(this.SeasonList);
  //       }
  //   );
  // }

  // loadAllCommodityBySeason() {
  //   let seasonId = this.phenophaseDurationForm.value.seasonId;
  //   this.commodityService.getCommodityBySeasonId(seasonId).subscribe(
  //     (data: {}) => {
  //       this.CommodityList = data;
  //       console.log(this.CommodityList);
  //     }
  //   );
  // }

  // loadAllCommodityByPhenophase() {
  //   let commodityId = this.phenophaseDurationForm.value.commodityId;
  //   console.log(commodityId);
  //   this.commodityService.getCommodityByPhenophaseId(commodityId).subscribe(
  //       (data: {}) => {
  //         this.PhenophaseList = data;
  //         console.log(this.PhenophaseList);
  //       }
  //   );
  // }

  downloadExcelFormat() {
    var tableName = 'agri_phenophase_duration';
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
    this.router.navigate(['/zonal/phenophase-duration']);
  }
}

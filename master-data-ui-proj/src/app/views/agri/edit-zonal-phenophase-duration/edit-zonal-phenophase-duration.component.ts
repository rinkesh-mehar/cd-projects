import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';

import { ActivatedRoute, Router } from '@angular/router';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { AgriVarietyService } from '../services/agri-variety.service';

import { AgriSeasonService } from '../services/agri-season.service';
import { GeoStateService } from '../../geo/services/geo-state.service';
import { AgriPhenophaseService } from '../services/agri-phenophase.service';
import { ZonalPhenophaseDurationService } from '../services/zonal-phenophase-duration.service';
import { DomSanitizer } from '@angular/platform-browser';
import { environment } from '../../../../environments/environment';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { AczService } from '../../zonal/services/acz.service';
import { ZonalCommodityService } from '../../zonal/services/zonal-commodity.service';
import { ZonalVarietyService } from '../../regional/services/zonal-variety.service';


@Component({
  selector: 'app-edit-zonal-phenophase-duration',
  templateUrl: './edit-zonal-phenophase-duration.component.html',
  styleUrls: ['./edit-zonal-phenophase-duration.component.scss']
})
export class EditZonalPhenophaseDurationComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  zonalCommodityList: any = [];
  zonalVarietyList: any = [];
  PhenophaseList: any = [];
  aczList : any= [];
  // SeasonList: any = [];
  StateList: any = [];
  phenophaseDurationArr: any = [];
  phenophaseDurationList: any = [];
  updatePhenophaseDurationForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  // imgPerview: any;
  // uploadFile: File;
  stateCodeAczIdZonalCommodityId :any = [];
  zonalPhenophaseDuration : any;

  ngOnInit() {
    this.updateForm();
    // this.getAllState();
    // this.getAllPhenophase();
    // this.loadAllCommodities();
    // this.loadAllVariety();
    // this.loadAllPhenophase();
    // this.loadAllState();
    // this.loadAllSeason();


  }
  constructor(
    private actRoute: ActivatedRoute,
    public zonalPhenophaseDurationService: ZonalPhenophaseDurationService,
    public commodityService: AgriCommodityService,
    public agriVarietyService: AgriVarietyService,
    public agriPhenophaseService: AgriPhenophaseService,
    public agriSeasonService: AgriSeasonService,
    public geoStateService: GeoStateService,
    public aczService : AczService,
    public zonalCommodityService :ZonalCommodityService,
    public zonalVarietyService :ZonalVarietyService,
    public router: Router,
    public fb: FormBuilder,
    private sanitizer: DomSanitizer,
  ) {
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.zonalPhenophaseDurationService.GetPhenophaseDuration(id).subscribe((data) => {
      this.zonalPhenophaseDuration=data;
      this.getAllState();
      this.getAllPhenophase();
      this.updatePhenophaseDurationForm = this.fb.group({
        zonalCommodityId: ['', Validators.required],
        zonalVarietyId: [data.zonalVarietyId, Validators.required],
        phenophaseId: [data.phenophaseId, Validators.required],
       
        stateCode: [data.stateCode, Validators.required],
        aczId: ['', Validators.required],
        // imageURL: [data.imageURL],
        status: [data.status],

        startDas: [data.startDas, [
          Validators.required,
          Validators.pattern("^[0-9]*$")
        ]],
        endDas: [data.endDas, [
          Validators.required,
          Validators.pattern("^[0-9]*$"),
        ]],
        phenophaseOrder: [data.phenophaseOrder, [
          Validators.required,
          Validators.pattern("^[0-9]*$"),
        ]],
      })

   
      // this.loadAllCommodityBySeason();
   
     
      this.getStateCodeAczIdZonalCommodityIdByZonalVirietyId()


    })



  }
  

  updateForm() {
    this.updatePhenophaseDurationForm = this.fb.group({
      // commodityId: ['', Validators.required],
      // varietyId: ['', Validators.required],
     
      // seasonId: ['', Validators.required],
      stateCode: ['', Validators.required],
      aczId : ['', Validators.required],
      zonalCommodityId :['', Validators.required],
      zonalVarietyId :['', Validators.required],
      phenophaseId: ['', Validators.required],

      
      
      status: [''],

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
    this.updatePhenophaseDurationForm.patchValue({status:this.zonalPhenophaseDuration.status});
    for (let controller in this.updatePhenophaseDurationForm.controls) {
      this.updatePhenophaseDurationForm.get(controller).markAsTouched();
    }

    if (this.updatePhenophaseDurationForm.invalid) {
      return;
    }

    // if (this.updatePhenophaseDurationForm.get('commodityId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select Commodity', '');

    //   return;
    // }
    // if (this.updatePhenophaseDurationForm.get('varietyId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select Variety', '');

    //   return;
    // }
    // if (this.updatePhenophaseDurationForm.get('phenophaseId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select Phenophase', '');

    //   return;
    // }
    // if (this.updatePhenophaseDurationForm.get('stateCode').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select State', '');

    //   return;
    // }
    // if (this.updatePhenophaseDurationForm.get('seasonId').value == 0) {
    //   this.errorModal.showModal('ERROR', 'Please Select Season', '');

    //   return;
    // }
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.zonalPhenophaseDurationService.UpdateZonalPhenophaseDuration(id, this.updatePhenophaseDurationForm.value).subscribe(res => {
      this.isSubmitted = true;

      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          // this._statusMsg = res.message;
          // this.updatePhenophaseDurationForm.reset();
          // this.imgPerview = '';
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
    this.updatePhenophaseDurationForm.patchValue({aczId: ''});
  this.aczService.getAllAczByStateCode(this.updatePhenophaseDurationForm.value.stateCode).subscribe((data: {}) => {
    this.aczList=data;
    });
  }

  getAllAczByStateCodeForEditMode(){
    this.aczService.getAllAczByStateCode(this.updatePhenophaseDurationForm.value.stateCode).subscribe((data: {}) => {
        this.aczList=data;
        });
      }

  getZonalCommodityListByAczId(){
    this.updatePhenophaseDurationForm.patchValue({zonalCommodityId: ''});
    this.zonalCommodityService.getZonalCommodityListByAczId(this.updatePhenophaseDurationForm.value.aczId).subscribe((data : {})=> {
      this.zonalCommodityList=data;
      });
  }

  getZonalCommodityListByAczIdForEditMode(){
    this.zonalCommodityService.getZonalCommodityListByAczId(this.updatePhenophaseDurationForm.value.aczId).subscribe((data : {})=> {
      this.zonalCommodityList=data;
      });
  }

  getZonalVarietyListByZonalCommodityId(){
    this.updatePhenophaseDurationForm.patchValue({zonalVarietyId: ''});
    this.zonalVarietyService.getZonalVarietyListByZonalCommodityId(this.updatePhenophaseDurationForm.value.zonalCommodityId).subscribe((data : {})=>{
    this.zonalVarietyList=data;
    });
  }

  getZonalVarietyListByZonalCommodityIdForEditMode(){
    this.zonalVarietyService.getZonalVarietyListByZonalCommodityId(this.updatePhenophaseDurationForm.value.zonalCommodityId).subscribe((data : {})=>{
    this.zonalVarietyList=data;
    });
  }

  getAllPhenophase() {
    return this.agriPhenophaseService.GetAllPhenophase().subscribe((data: {}) => {
      this.PhenophaseList = data;
    });
  }
  
  getStateCodeAczIdZonalCommodityIdByZonalVirietyId(){
    return this.zonalCommodityService.getStateCodeAczIdZonalCommodityIdByZonalVarietyId(this.updatePhenophaseDurationForm.value.zonalVarietyId).subscribe(data =>{
  
    this.stateCodeAczIdZonalCommodityId =data;
    this.updatePhenophaseDurationForm.patchValue(this.stateCodeAczIdZonalCommodityId);
    
    this.getAllAczByStateCodeForEditMode();
    this.getZonalCommodityListByAczIdForEditMode();
    this.getZonalVarietyListByZonalCommodityIdForEditMode();

    this.updatePhenophaseDurationForm.patchValue({ zonalVarietyId: this.zonalPhenophaseDuration.zonalVarietyId});

    })
  }





  // //Commodity list
  // loadAllCommodities() {
  //   return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
  //     this.CommodityList = data;
  //   })
  // }

  // //Variety list
  // loadAllVariety() {
  //   return this.agriVarietyService.GetAllVarieties().subscribe((data: {}) => {
  //     this.VarietyList = data;
  //   })
  // }

  //Seasonlist
  // loadAllSeason() {
  //   return this.agriSeasonService.GetAllSeasons().subscribe((data: {}) => {
  //     this.SeasonList = data;
  //   });
  // }

  // //Phenophase list
  // loadAllPhenophase() {
  //   return this.agriPhenophaseService.GetAllPhenophase().subscribe((data: {}) => {
  //     this.PhenophaseList = data;
  //   })
  // }

  //State list
  // loadAllState() {
  //   return this.geoStateService.GetAllState().subscribe((data: {}) => {
  //     this.StateList = data;
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

  //       console.log(img);

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
  //   let seasonId = this.updatePhenophaseDurationForm.value.seasonId;
  //   let stateCode = this.updatePhenophaseDurationForm.value.stateCode;
  //   this.commodityService.getCommodityByStateCodeAndSeasonId(stateCode, seasonId).subscribe(
  //       (data: {}) => {
  //         this.CommodityList = data;
  //         console.log(this.CommodityList);
  //       }
  //   );
  // }

  // loadAllSeasonByStateCode() {
  //   let stateCode = this.updatePhenophaseDurationForm.value.stateCode;
  //   this.agriSeasonService.getSeasonByStateCode(stateCode).subscribe(
  //       (data: {}) => {
  //         this.SeasonList = data;
  //         console.log(this.SeasonList);
  //       }
  //   );
  // }

  // loadAllVarietyByCommodity() {
  //   let commodityId = this.updatePhenophaseDurationForm.value.commodityId;
  //   this.agriVarietyService.GetAllVarietyByCommodityId(commodityId).subscribe(
  //       (data: {}) => {
  //         this.VarietyList = data;
  //       }
  //   );
  // }

  // loadAllCommodityBySeason() {
  //   let seasonId = this.updatePhenophaseDurationForm.value.seasonId;
  //   this.commodityService.getCommodityBySeasonId(seasonId).subscribe(
  //     (data: {}) => {
  //       this.CommodityList = data;
  //       console.log(this.CommodityList);
  //     }
  //   );
  // }
  // loadAllCommodityByPhenophase() {
  //   let commodityId = this.updatePhenophaseDurationForm.value.commodityId;
  //   console.log(commodityId);
  //   this.commodityService.getCommodityByPhenophaseId(commodityId).subscribe(
  //       (data: {}) => {
  //         this.PhenophaseList = data;
  //         console.log(this.PhenophaseList);
  //       }
  //   );
  // }

  modalSuccess($event: any) {
    this.router.navigate(['/zonal/phenophase-duration']);
  }
}

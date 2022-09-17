import { ZonalCommodityService } from '../../../zonal/services/zonal-commodity.service';
import { AczService } from '../../../zonal/services/acz.service';
import { AgriVarietyStress } from '../../models/AgriVarietyStress';
import { AgriSeasonService } from '../../services/agri-season.service';
import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import {FormGroup, FormBuilder, Validators, FormControl, FormArray} from '@angular/forms';
import {ZonalStressDurationService} from '../../services/zonal-stress-duration.service';
import {AgriCommodityService} from '../../services/agri-commodity.service';
import {AgriStressTypeService} from '../../services/agri-stress-type.service';
import {AgriPhenophaseService} from '../../services/agri-phenophase.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DomSanitizer} from '@angular/platform-browser';
import {environment} from '../../../../../environments/environment';
import {ApiUtilService} from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { GeoDistrictService } from '../../../geo/services/geo-district.service';
import { GeoStateService } from '../../../geo/services/geo-state.service';
import { ZonalCondusiveWeatherService } from '../../services/zonal-condusive-weather.service';
import { AgriVarietyService } from '../../services/agri-variety.service';

@Component({
    selector: 'app-add-edit-zonal-stress-duration',
    templateUrl: './add-edit-zonal-stress-duration.component.html',
    styleUrls: ['./add-edit-zonal-stress-duration.component.scss']
})
export class AddEditZonalStressDurationComponent implements OnInit {

    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    zonalCommodityList: any = [];
    PhenophaseList: any = [];
    StressTypeList: any = [];
    StressForm: FormGroup;
    StressArr: any = [];
    stateCodeAczIdZonalCommodityId:any;

    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    _statusMsg: string;
    editId: any;
    mode: string = 'add';
    SymptomsList: any[];
    uploadFile: File;
    imgPerview: any;
    zonalStressDuration:any;

    isSubmittedBulk: boolean = false;
    isSuccessBulk: boolean = false;
    fileUpload: any;

    aczList:any = [];
    DistrictList: any = [];
    StateList: any = [];
    StressList: any = [];
    SeasonList: any = [];
    VarietyList: any = [];
    stressTypeIdByCommodityIdAndStressId: any;
    commodityId:number;

    ngOnInit() {
        this.loadAllState();
        //this.getSeasonList();
        // this.loadAllCommodities();
        this.loadAllStressType();
        this.LoadZonalStressDurationForm();
    }

    constructor(
        public fb: FormBuilder,
        public zonalStressDurationService: ZonalStressDurationService,
        public commodityService: AgriCommodityService,
        public agriStressTypeService: AgriStressTypeService,
        public agriPhenophaseService: AgriPhenophaseService,
        private actRoute: ActivatedRoute,
        private sanitizer: DomSanitizer,
        public apiUtilService: ApiUtilService,
        private router: Router,
        public geoDistrictService : GeoDistrictService,
        public geoStateService : GeoStateService,
        public condusiveParamService: ZonalCondusiveWeatherService,
        public agriSeasonService: AgriSeasonService,
        public agriVarietyService: AgriVarietyService,
        public aczService: AczService,
        public zonalCommodityService: ZonalCommodityService,
    ) {
    }
    trimValue(formControl) { 
        formControl.setValue(formControl.value.trim()); 
      }
      LoadZonalStressDurationForm() {
        this.StressForm = this.fb.group({
            id: [''],
            zonalCommodityId: ['', Validators.required],
            stateCode: ['', Validators.required],
            aczId: ['', Validators.required],
            //districtCode: ['', Validators.required],
            //seasonId: ['', Validators.required],
            //varietyId: ['', Validators.required],
            stressTypeId: ['', Validators.required],
            startDas: ['', Validators.required],
            endDas: ['', Validators.required],
            // startPhenophaseId: ['', Validators.required],
            // endPhenophaseId: ['', Validators.required],
            // name: ['', Validators.required],
            stressId: ['', Validators.required],
            // scientificName: [''],
            // symptomsList: [''],
            // symptomsListChkBox: new FormArray([]),
            //imageURL: [],
            status: ['Inactive']
        });

        this.editId = this.actRoute.snapshot.paramMap.get('id');
        if (this.editId) {
            this.mode = 'edit';
            this.zonalStressDurationService.GetStress(this.editId).subscribe(data => {
                this.zonalStressDuration = data;
                this.StressForm.patchValue(data);
                //this.loadAllDistrictsByStateCode();
                //this.loadAllCommodityBySeason();
                //this.getVarietyListByStateCodeDiscrictCodeSeasonIdAndCommodityId();
                //this.loadAllCommodityByPhenophase();
                this.getStateCodeAczIdByZonalCommodityId();
                // this.loadAllSymptomsList();
            });
        } else {
            // this.loadAllSymptomsList();
        }
    }


    submitForm() {
        
        for (let controller in this.StressForm.controls) {
            this.StressForm.get(controller).markAsTouched();
        }

        if (this.StressForm.invalid) {
            return;
        }

        // const selectedSymptomsList = this.StressForm.value.symptomsListChkBox.map((v, i) => v ? this.SymptomsList[i] : null)
        //     .filter(v => v !== null);
        // this.StressForm.patchValue({symptomsList: selectedSymptomsList});


        let observable: any;
        
        // delete fd2.symptomsListChkBox;

        if (this.mode == 'edit') {

            this.StressForm.patchValue({status:this.zonalStressDuration.status});

            let fd = this.StressForm.value;
            let fd2 = JSON.parse(JSON.stringify(fd));
            
            observable = this.zonalStressDurationService.UpdateZonalStressDuration(this.editId, fd2);
        } else {

            let fd = this.StressForm.value;
            let fd2 = JSON.parse(JSON.stringify(fd));

            observable = this.zonalStressDurationService.CreateZonalStressDuration(fd2);
        }
        observable.subscribe(res => {
            this.isSubmitted = true;
            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                    if(this.mode == 'add'){
                        this.LoadZonalStressDurationForm();
                    }
                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            }
        });
    }


    //Commodity list
    // loadAllCommodities() {
    //     return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
    //         this.CommodityList = data;
    //     });
    // }

    //Phenophase list
    // loadAllPhenophase() {
    //     return this.agriPhenophaseService.GetAllPhenophase().subscribe((data: {}) => {
    //         this.PhenophaseList = data;
    //     });
    // }

    //StressType list
    loadAllStressType() {
        return this.agriStressTypeService.GetAllStressType().subscribe((data: {}) => {
            this.StressTypeList = data;
        });
    }

    //Upload file
    // fileChange(element) {
    //     var file: File = element.target.files[0];
    //     var idxDot = file.name.lastIndexOf('.') + 1;
    //     var extFile = file.name.substr(idxDot, file.name.length).toLowerCase();
    //     if (extFile == 'jpeg') {
    //         this.compressImage(element);
    //     } else {
    //         element.target.value = null;
    //         alert('Invalid format File Format, Only jpeg files are allowed!');
    //         return;
    //     }
    // }


    // compressImage(element) {

    //     const width = environment.imageResizeWidth;
    //     let height = environment.imageResizeHeight;

    //     const fileName = element.target.files[0].name;
    //     const reader: FileReader = new FileReader();
    //     reader.readAsDataURL(element.target.files[0]);
    //     reader.onload = event => {
    //         const img: any = new Image();
    //         img.src = (<FileReader>event.target).result;
    //         img.onload = () => {
    //             const elem = document.createElement('canvas');
    //             if (environment.preserveImageAspectRatio) {
    //                 const scaleFactor = width / img.width;
    //                 height = img.height * scaleFactor;
    //             }

    //             elem.width = width;
    //             elem.height = height;

    //             const ctx = elem.getContext('2d');
    //             ctx.drawImage(img, 0, 0, width, height);
    //             ctx.canvas.toBlob((blob) => {
    //                 const file = new File([blob], fileName, {
    //                     type: 'image/jpeg',
    //                     lastModified: Date.now()
    //                 });
    //                 this.uploadFile = file;
    //             }, 'image/jpeg', 1);
    //             this.imgPerview = ctx.canvas.toDataURL();
    //         },
    //             reader.onerror = error => console.log(error);
    //     };
    // }

    // loadAllCommodityByPhenophase() {
    //     let commodityId = this.StressForm.value.commodityId;
    //     this.commodityService.getCommodityByPhenophaseId(commodityId).subscribe(
    //         (data: {}) => {
    //             this.PhenophaseList = data;
    //             console.log(this.PhenophaseList);
    //         }
    //     );
    // }

    async delay(ms: number) {
        await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
    }

    downloadExcelFormat() {
        var tableName = 'agri_district_commodity_stress';
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

 //State list
 loadAllState(){
    return this.geoStateService.GetAllState().subscribe((data: {}) => {
      this.StateList = data;
    })
  }

  //Acz list
 loadAczByStateCode(){
    this.StressForm.patchValue({aczId:''});
    return this.aczService.getAllAczByStateCode(this.StressForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }

  loadAczByStateCodeForEdit(){
    return this.aczService.getAllAczByStateCode(this.StressForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }

    //Zonal Commodity List
  loadZonalCommodityByAczId(){
    this.StressForm.patchValue({zonalCommodityId:''});
    return this.zonalCommodityService.getZonalCommodityListByAczId(this.StressForm.value.aczId).subscribe((data: {}) => {
      this.zonalCommodityList = data;
    //   if(this.mode == "edit"){
    //     for(let zonalCommodity of this.zonalCommodityList){
    //         if(this.zonalStressDuration.zonalCommodityId == zonalCommodity.id){
    //             this.commodityId = zonalCommodity.commodityId;
    //         }
    //     }
    //     this.getStressTypeIDByCommodityIdAndStressId();
    //   }
    })
  }

  loadZonalCommodityByAczIdForEdit(){
    return this.zonalCommodityService.getZonalCommodityListByAczId(this.StressForm.value.aczId).subscribe((data: {}) => {
      this.zonalCommodityList = data;
     
        for(let zonalCommodity of this.zonalCommodityList){
            if(this.zonalStressDuration.zonalCommodityId == zonalCommodity.id){
                this.commodityId = zonalCommodity.commodityId;
            }
        }
        this.getStressTypeIDByCommodityIdAndStressId();
    
    })
  }

  getStressByCommodityIdAndStressTypeId() {
    this.StressForm.patchValue({stressId:''});
    for(let zonalCommodity of this.zonalCommodityList){
        if(this.StressForm.value.zonalCommodityId == zonalCommodity.id){
            this.commodityId = zonalCommodity.commodityId;
            console.log(this.commodityId);
        }
    }

    return this.condusiveParamService.getStressByCommodityIdAndStressTypeId(this.commodityId, this.StressForm.value.stressTypeId).subscribe((data: {}) => {
      this.StressList = data;
    })
  }

  getStressByCommodityIdAndStressTypeIdForEditMode() {

    return this.condusiveParamService.getStressByCommodityIdAndStressTypeId(this.commodityId, this.stressTypeIdByCommodityIdAndStressId.stressTypeId).subscribe((data: {}) => {
      this.StressList = data;
      if(this.mode == 'edit'){
      this.StressForm.patchValue({stressTypeId:this.stressTypeIdByCommodityIdAndStressId.stressTypeId});
    }
    })
  }

  getStressTypeIDByCommodityIdAndStressId(){
     
  
    return this.agriStressTypeService.getStressTypeIdByCommodityIdAndStressId(this.commodityId,this.StressForm.value.stressId).subscribe((data: {}) => {
        this.stressTypeIdByCommodityIdAndStressId = data;
        this.StressForm.patchValue(this.stressTypeIdByCommodityIdAndStressId);
        this.StressForm.patchValue({stressTypeId:this.stressTypeIdByCommodityIdAndStressId});
        this.getStressByCommodityIdAndStressTypeIdForEditMode();
      })
  }

  getStateCodeAczIdByZonalCommodityId() {
    return this.zonalCommodityService.getStateCodeAczIdByZonalCommodityId(this.StressForm.value.zonalCommodityId).subscribe((data) => {
      this.stateCodeAczIdZonalCommodityId = data;
      //alert(JSON.stringify(this.stateCodeAczIdZonalCommodityId));
      this.StressForm.patchValue(this.stateCodeAczIdZonalCommodityId);
      this.loadAczByStateCodeForEdit();
      this.loadZonalCommodityByAczIdForEdit();
      this.StressForm.patchValue({zonalCommodityId:this.zonalStressDuration.zonalCommodityId});
    })
  }


//   loadAllDistrictsByState(event:Event) : void {
//     let index:number = event.target["selectedIndex"] - 1;
//    if(index ==-1) {
//        return;
//     }
//     let stateCode = this.StateList[index].stateCode;
//     this.geoDistrictService.GetAllDistrictByStateCode(stateCode).subscribe(
//       (data: {}) => {
//         this.DistrictList = data;
//         console.log(this.DistrictList);
//       }
//     );
//   }

//   loadAllDistrictsByStateCode() : void {
// //     let index:number = event.target["selectedIndex"] - 1;
// //    if(index ==-1) {
// //        return;
// //     }
// //     let stateCode = this.StateList[index].stateCode;
//     let stateCode: number = this.StressForm.value.stateCode;
//     this.geoDistrictService.GetAllDistrictByStateCode(stateCode).subscribe(
//       (data: {}) => {
//         this.DistrictList = data;
//         console.log(this.DistrictList);
//       }
//     );
//   }

   //Season list
//    getSeasonList() {
//     return this.agriSeasonService.getSeasonList().subscribe((data: {}) => {
//       this.SeasonList = data;
//     });
//   }

//   loadAllCommodityBySeason() {
//     let seasonId = this.StressForm.value.seasonId;
//     this.commodityService.getCommodityBySeasonId(seasonId).subscribe(
//         (data: {}) => {
//           this.CommodityList = data;
//           console.log(this.CommodityList);
//         }
//     );
//   }

//   getVarietyListByStateCodeDiscrictCodeSeasonIdAndCommodityId() {
//     let stateCode: number = this.StressForm.value.stateCode;
//     let districtCode: number = this.StressForm.value.districtCode;
//     let seasonId: number = this.StressForm.value.seasonId;
//     let commodityId: number = this.StressForm.value.commodityId;
//     return this.agriVarietyService.getVarietyListByStateCodeDiscrictCodeSeasonIdAndCommodityId(stateCode,districtCode,seasonId,commodityId).subscribe((data: {}) => {
//       this.VarietyList = data;
//     })
//   }

    modalSuccess($event: any) {
        this.router.navigate(['/zonal/zonal-stress-duration']);
    }
}

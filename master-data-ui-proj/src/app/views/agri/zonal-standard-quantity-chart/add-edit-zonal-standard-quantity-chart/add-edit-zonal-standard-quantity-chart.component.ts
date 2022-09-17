import { ZonalVarietyService } from '../../../regional/services/zonal-variety.service';
import { ZonalCommodityService } from '../../../zonal/services/zonal-commodity.service';
import { AczService } from '../../../zonal/services/acz.service';
import {Component, OnInit, Input, Output, EventEmitter, SimpleChanges, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import { AgriCommodityService } from '../../services/agri-commodity.service';
import { AgriVarietyService } from '../../services/agri-variety.service';
import { GeoStateService } from '../../../geo/services/geo-state.service';
import { ZonalStandardQuantityChartService } from '../../services/zonal-standard-quantity-chart.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-edit-zonal-standard-quantity-chart',
  templateUrl: './add-edit-zonal-standard-quantity-chart.component.html',
  styleUrls: ['./add-edit-zonal-standard-quantity-chart.component.scss']
})
export class AddEditZonalStandardQuantityChartComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  standardQuantityChartForm: FormGroup;

  //commodityList: any;
  zonalCommodityList: any = [];
  zonalVarietyList: any = [];
  stateList: any;
  aczList:any = [];
  varietyList: any;
  editStandardQuantityChartId: any;
  mode: any = 'add';
  StandardQuantityChart: any;

  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;
  stateCodeAczIdZonalCommodityId:any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  constructor(
    public formBuilder: FormBuilder, 
    private agriCommodityService: AgriCommodityService, 
    private agriVarietyService: AgriVarietyService,
    public zonalStandardQuantityChartService: ZonalStandardQuantityChartService, 
    private geoStateService: GeoStateService,
    private actRoute: ActivatedRoute,
    public apiUtilService: ApiUtilService, 
    public router: Router,
    public aczService: AczService,
    public zonalCommodityService: ZonalCommodityService,
    public zonalVarietyService: ZonalVarietyService, ) {


    this.createStandardQuantityChartForm();


  }
  getChanges() {
    console.log(this.standardQuantityChartForm.value)
  }

  createStandardQuantityChartForm() {
    this.standardQuantityChartForm = this.formBuilder.group({
      id: [],
      aczId: ['', Validators.required],
      zonalCommodityId: ['', Validators.required],
      zonalVarietyId: ['', Validators.required],
      stateCode: ['', Validators.required],
      standardQuantityPerAcre: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
      standardPositiveVariancePercent: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
      standardPositiveVariancePerAcre: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
      standardNegativeVariancePercent: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
      standardNegativeVariancePerAcre: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
      status: ['Inactive']


    })
  }

  ngOnInit() {
    this.loadAllState();
    

    this.editStandardQuantityChartId = this.actRoute.snapshot.paramMap.get('id');

    if (this.editStandardQuantityChartId) {

      this.mode = "edit";
      this.zonalStandardQuantityChartService.GetStandardQuantityChart(this.editStandardQuantityChartId).subscribe(data => {
        this.StandardQuantityChart = data;
        this.standardQuantityChartForm = this.formBuilder.group({
          id: [],
          aczId: ['', Validators.required],
          stateCode: ['', Validators.required],
          zonalCommodityId: ['', Validators.required],
          zonalVarietyId: ['', Validators.required],
          standardQuantityPerAcre: ['', Validators.required],
          standardPositiveVariancePercent: ['', Validators.required],
          standardPositiveVariancePerAcre: ['', Validators.required],
          standardNegativeVariancePercent: ['', Validators.required],
          standardNegativeVariancePerAcre: ['', Validators.required],
          status: ['']
        })
        this.standardQuantityChartForm.patchValue(this.StandardQuantityChart);
        this.getStateCodeAczIdZonalCommodityIdByZonalVarietyId();
      })
// this.loadAllCommodity();
//     this.loadAllVariety();

    }

  }

  loadAllState() {
    return this.geoStateService.GetAllState().subscribe((data: any) => {
      this.stateList = data;
    }, err => {
      alert(err)
    })
  }

  loadAczByStateCode() {
    this.standardQuantityChartForm.patchValue({aczId:''});
    return this.aczService.getAllAczByStateCode(this.standardQuantityChartForm.value.stateCode).subscribe((data: any) => {
      this.aczList = data;
    }, err => {
      alert(err)
    })
  }
  loadAczByStateCodeForEdit() {
    return this.aczService.getAllAczByStateCode(this.standardQuantityChartForm.value.stateCode).subscribe((data: any) => {
      this.aczList = data;
    }, err => {
      alert(err)
    })
  }

  loadZonalCommodityByAczId() {
    this.standardQuantityChartForm.patchValue({zonalCommodityId:''});
    return this.zonalCommodityService.getZonalCommodityListByAczId(this.standardQuantityChartForm.value.aczId).subscribe((data: any) => {
      this.zonalCommodityList = data;
    }, err => {
      alert(err)
    })
  }
  loadZonalCommodityByAczIdForEdit() {
    return this.zonalCommodityService.getZonalCommodityListByAczId(this.standardQuantityChartForm.value.aczId).subscribe((data: any) => {
      this.zonalCommodityList = data;
    }, err => {
      alert(err)
    })
  }
  loadZonalVarietyByZonalCommodityId() {
    this.standardQuantityChartForm.patchValue({zonalVarietyId:''});
    return this.zonalVarietyService.getZonalVarietyListByZonalCommodityId(this.standardQuantityChartForm.value.zonalCommodityId).subscribe((data: any) => {
      this.zonalVarietyList = data;
    }, err => {
      alert(err)
    })
  }
  loadZonalVarietyByZonalCommodityIdForEdit() {
    return this.zonalVarietyService.getZonalVarietyListByZonalCommodityId(this.standardQuantityChartForm.value.zonalCommodityId).subscribe((data: any) => {
      this.zonalVarietyList = data;
    }, err => {
      alert(err)
    })
  }

  getStateCodeAczIdZonalCommodityIdByZonalVarietyId(){
    return this.zonalCommodityService.getStateCodeAczIdZonalCommodityIdByZonalVarietyId(this.standardQuantityChartForm.value.zonalVarietyId).subscribe(data => {
        
        this.stateCodeAczIdZonalCommodityId = data;
        this.standardQuantityChartForm.patchValue(this.stateCodeAczIdZonalCommodityId);

        this.loadAczByStateCodeForEdit();
        this.loadZonalCommodityByAczIdForEdit();
        this.loadZonalVarietyByZonalCommodityIdForEdit();

        this.standardQuantityChartForm.patchValue({ zonalVarietyId: this.StandardQuantityChart.zonalVarietyId});

   })
  }
  // loadAllCommodity() {
  //   return this.agriCommodityService.GetAllCommoditise().subscribe((data: any) => {
  //     this.commodityList = data;
  //   }, err => {
  //     alert(err)
  //   })
  // }

  // loadAllCommodityByState() {
  //   let stateCode = this.standardQuantityChartForm.value.stateCode;
  //   this.agriCommodityService.getCommodityByState(stateCode).subscribe((data: {}) => {
  //       this.commodityList = data;
  //       console.log(this.commodityList);
  //     }, err => {
  //       alert(err);
  //     }
  //   );
  // }

  // loadAllVariety() {
  //   return this.agriVarietyService.GetAllVarieties().subscribe((data: any) => {
  //     this.varietyList = data;
  //   }, err => {
  //     alert(err)
  //   })
  // }

  submitStandardQuantityChartForm() {

    for (let controller in this.standardQuantityChartForm.controls) {
      this.standardQuantityChartForm.get(controller).markAsTouched();
    }
    if (this.standardQuantityChartForm.invalid) {
      return;
    }

    if (this.standardQuantityChartForm.get('stateCode').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select State', '');
      return;
    }

    if (this.standardQuantityChartForm.get('aczId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select ACZ', '');
      return;
    }

    if (this.standardQuantityChartForm.get('zonalCommodityId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Zonal Commodity', '');
      return;
    }
    if (this.standardQuantityChartForm.get('zonalVarietyId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Zonal Variety', '');
      return;
    }
    
    if (this.mode == "edit") {
      this.updateStandardQuantityChart();
    } else {
      this.addStandardQuantityChart();
    }
  }
  addStandardQuantityChart() {
    this.zonalStandardQuantityChartService.CreateStandardQuantityChart(this.standardQuantityChartForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.StandardQuantityChart = {};
          this.mode = "add";
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    })
  }
  updateStandardQuantityChart() {
    this.standardQuantityChartForm.patchValue({status:this.StandardQuantityChart.status});
    this.zonalStandardQuantityChartService.UpdateStandardQuantityChart(this.editStandardQuantityChartId, this.standardQuantityChartForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.StandardQuantityChart = {};
          this.mode = "add";
          // this.standardQuantityChartForm.reset();

          this.createStandardQuantityChartForm();

          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    })
  } nInit() {
  }

  // loadAllVarietyByCommodity(event: Event): void {
  //   let index: number = event.target["selectedIndex"] - 1;
  //   if (index == -1) {
  //     return;
  //   }
  //   let commodityId = this.commodityList[index].commodityId;
  //   this.agriVarietyService.GetAllVarietyByCommodityId(commodityId).subscribe(
  //     (data: {}) => {
  //       this.varietyList = data;
  //       console.log(this.varietyList);
  //     }
  //   );
  // }

  loadAllVarietyByCommodity() {
    let commodityId = this.standardQuantityChartForm.value.commodityId;
    this.agriVarietyService.GetAllVarietyByCommodityId(commodityId).subscribe(
      (data: {}) => {
        this.varietyList = data;
        console.log(this.varietyList);
      }
    );
  }

  loadAllVarietyByStateAndCommodity() {
    let stateCode = this.standardQuantityChartForm.value.stateCode;
    let commodityId = this.standardQuantityChartForm.value.commodityId;
    this.agriVarietyService.getAllVarietyByStateAndCommodity(stateCode, commodityId)
      .subscribe((data: {}) => {
        this.varietyList = data;
        console.log(this.varietyList);
      }, err => {
        alert(err);
      }
    );
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  downloadExcelFormat(){
    var tableName = "agri_standard_quantity_chart";
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
        this.router.navigate(['/zonal/standard-quantity-chart']);
    }
}

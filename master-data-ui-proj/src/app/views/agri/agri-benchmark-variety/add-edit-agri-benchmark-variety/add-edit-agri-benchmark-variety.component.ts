import {Component, OnInit, Input, Output, EventEmitter, SimpleChanges, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import custom validator to validate that password and confirm password fields match
import {ActivatedRoute, Router} from '@angular/router';
import { AgriCommodityService } from '../../services/agri-commodity.service';
import { ApiUtilService } from '../../../services/api-util.service';
import { AgriBenchmarkVarietyService } from '../../services/agri-benchmark-variety.service';
import { AgriSeasonService } from '../../services/agri-season.service';
import { GeoRegionService } from '../../../geo/services/geo-region.service';
import { GeoStateService } from '../../../geo/services/geo-state.service';
import { AgriVarietyService } from '../../services/agri-variety.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-edit-agri-benchmark-variety',
  templateUrl: './add-edit-agri-benchmark-variety.component.html',
  styleUrls: ['./add-edit-agri-benchmark-variety.component.scss']
})
export class AddEditAgriBenchmarkVarietyComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  BenchmarkVarietyForm: FormGroup;

  regionList: any;
  stateList: any;
  seasonList: any;
  commodityList: any;
  benchmarkTypeList: any;
  varietyList: any;
  benchmarkVarietyList: any;
  editBenchmarkVarietyId: any;
  mode: any = 'add';
  BenchmarkVariety: any;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;


  constructor(public formBuilder: FormBuilder, private geoRegionService: GeoRegionService,
    private geoStateService: GeoStateService, private agriSeasonService: AgriSeasonService, 
    private agriCommodityService: AgriCommodityService, public agriBenchmarkVarietyService: AgriBenchmarkVarietyService,
    private agriVarietyService: AgriVarietyService, private actRoute: ActivatedRoute, public apiUtilService: ApiUtilService,
              public router: Router) {



    this.createBenchmarkVarietyForm();


  }
  getChanges() {
    console.log(this.BenchmarkVarietyForm.value)
  }

  createBenchmarkVarietyForm() {
    this.BenchmarkVarietyForm = this.formBuilder.group({
      id: [],
      regionId: ['', Validators.required],
      stateCode: ['', Validators.required],
      seasonId: ['', Validators.required],
      commodityId: ['', Validators.required],
      varietyId: ['', Validators.required],
      isDrkBenchmark: ['', Validators.required],
      isAgmBenchmark: ['', Validators.required],
      status: ['Inactive']


    })
  }

  ngOnInit() {
    this.loadAllRegion();
    this.loadAllState();
    // this.loadAllSeason();
    // this.loadAllCommodity();
    // this.loadAllVariety();

    this.editBenchmarkVarietyId = this.actRoute.snapshot.paramMap.get('id');

    if (this.editBenchmarkVarietyId) {

      this.mode = "edit";
      this.agriBenchmarkVarietyService.GetBenchmarkVariety(this.editBenchmarkVarietyId).subscribe(data => {
        this.BenchmarkVariety = data;
        this.BenchmarkVarietyForm = this.formBuilder.group({
          id: [],
          regionId: ['', Validators.required],
          stateCode: ['', Validators.required],
          seasonId: ['', Validators.required],
          commodityId: ['', Validators.required],
          varietyId: ['', Validators.required],
          isDrkBenchmark: ['', Validators.required],
          isAgmBenchmark: ['', Validators.required],
          status: ['']
        })

        this.BenchmarkVarietyForm.patchValue(this.BenchmarkVariety);
        // this.loadAllCommodityByPhenophase();
        this.loadAllCommodityByStateCodeAndSeasonId();
        this.loadAllVarietyByCommodity();
        this.loadAllSeasonByStateCode();
      })


    }

  }

  loadAllRegion() {
    return this.geoRegionService.GetAllRegion().subscribe((data: any) => {
      this.regionList = data;
    }, err => {
      alert(err)
    })
  }

  loadAllState() {
    return this.geoStateService.GetAllState().subscribe((data: any) => {
      this.stateList = data;
    }, err => {
      alert(err)
    })
  }

  loadAllSeason() {
    return this.agriSeasonService.GetAllSeasons().subscribe((data: any) => {
      this.seasonList = data;
    }, err => {
      alert(err)
    })
  }

  loadAllCommodity() {
    return this.agriCommodityService.GetAllCommoditise().subscribe((data: any) => {
      this.commodityList = data;
    }, err => {
      alert(err)
    })
  }

  loadAllVariety() {
    return this.agriVarietyService.GetAllVarieties().subscribe((data: any) => {
      this.varietyList = data;
    }, err => {
      alert(err)
    })
  }

  loadAllCommodityByStateCodeAndSeasonId() {
    let seasonId = this.BenchmarkVarietyForm.value.seasonId;
    let stateCode = this.BenchmarkVarietyForm.value.stateCode;
    this.agriCommodityService.getCommodityByStateCodeAndSeasonId(stateCode,seasonId).subscribe(
      (data: {}) => {
        this.commodityList = data;
        console.log(this.commodityList);
      }
    );
  }

  loadAllVarietyByCommodity(){
    let commodityId = this.BenchmarkVarietyForm.value.commodityId;
    this.agriVarietyService.GetAllVarietyByCommodityId(commodityId).subscribe(
          (data: {}) => {
            this.varietyList = data;
            console.log(this.varietyList);
          }
        );
  }

  loadAllSeasonByStateCode(){
    let stateCode = this.BenchmarkVarietyForm.value.stateCode;
    this.agriSeasonService.getSeasonByStateCode(stateCode).subscribe(
          (data: {}) => {
            this.seasonList = data;
            console.log(this.seasonList);
          }
        );
  }

  submitBenchmarkVarietyForm() {

    for (let controller in this.BenchmarkVarietyForm.controls) {
      this.BenchmarkVarietyForm.get(controller).markAsTouched();
    }
    
    if (this.BenchmarkVarietyForm.invalid) {
      return;
    }

    if (this.BenchmarkVarietyForm.get('regionId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Region', '');
      return;
    }

    if (this.BenchmarkVarietyForm.get('stateCode').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select State', '');

      return;
    }

    if (this.BenchmarkVarietyForm.get('seasonId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Season', '');
      return;
    }

    if (this.BenchmarkVarietyForm.get('commodityId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Commodity', '');
      return;
    }

    if (this.BenchmarkVarietyForm.get('varietyId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Variety', '');
      return;
    }
    
    if (this.mode == "edit") {
      this.updateBenchmarkVariety();
    } else {
      this.addBenchmarkVariety();
    }
  }
  addBenchmarkVariety() {
    this.agriBenchmarkVarietyService.CreateBenchmarkVariety(this.BenchmarkVarietyForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.BenchmarkVariety = {};
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
  updateBenchmarkVariety() {
    this.agriBenchmarkVarietyService.UpdateBenchmarkVariety(this.BenchmarkVarietyForm.value.id, this.BenchmarkVarietyForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          // this._statusMsg = res.message;
          this.BenchmarkVariety = {};
          this.mode = "add";
          this.BenchmarkVarietyForm.reset();

          this.createBenchmarkVarietyForm();

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

  // loadAllCommodityByPhenophase() {
  //   let commodityId = this.BenchmarkVarietyForm.value.commodityId; console.log(commodityId)
  //   this.agriCommodityService.getCommodityByPhenophaseId(commodityId).subscribe(
  //     (data: {}) => {
  //       this.phenophaseList = data;
  //       console.log(this.phenophaseList);
  //     }
  //   );
  // }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }


    modalSuccess($event: any) {
        this.router.navigate(['/agri/benchmark-variety']);
    }
}



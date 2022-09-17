import {Component, OnInit, Input, Output, EventEmitter, SimpleChanges, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import { AgriCommodityService } from '../../services/agri-commodity.service';
import { AgriStressControlMeasuresService } from '../../services/agri-stress-control-measures.service';
import { ZonalStressDurationService } from '../../services/zonal-stress-duration.service';
import { AgriStressSeverityService } from '../../services/agri-stress-severity.service';
import { AgriControlMeasuresService } from '../../services/agri-control-measures.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-add-edit-agri-recommendation',
  templateUrl: './add-edit-agri-stress-control-measures.component.html',
  styleUrls: ['./add-edit-agri-stress-control-measures.component.scss']
})
export class AddEditAgriStressControlMeasuresComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  stressControlMeasuresForm: FormGroup;

  commodityList: any;
  stressList: any;
  StressSeverityList: any;
  ControlMeasureList: any;
  editStressControlMeasureId: any;
  mode: any = 'add';
  stressControlMeasures: any;
  uploadFile: File = null;
  imgPerview: any;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;


  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  constructor(public formBuilder: FormBuilder, private agriCommodityService: AgriCommodityService, private zonalStressDurationService: ZonalStressDurationService,
              public agriStressControlMeasuresService: AgriStressControlMeasuresService, private agriStressSeverityService: AgriStressSeverityService,
              private agriControlMeasuresService: AgriControlMeasuresService, private actRoute: ActivatedRoute, public apiUtilService: ApiUtilService,
              public router: Router) {


    this.createStressControlMeasuresForm();


  }
  getChanges() {
    console.log(this.stressControlMeasuresForm.value)
  }

  createStressControlMeasuresForm() {
    this.stressControlMeasuresForm = this.formBuilder.group({
      id: [],
      commodityId: ['', Validators.required],
      stressId: ['', Validators.required],
      stressSeverityId: ['', Validators.required],
      stressControlMeasureId: ['', Validators.required],
      status: ['Inactive']

    })
  }

  ngOnInit() {
    this.loadAllCommodity();
    this.loadAllStress();
    this.loadAllStressSeverity();
    this.GetAllAgriControlMeasures();

    this.editStressControlMeasureId = this.actRoute.snapshot.paramMap.get('id');

    if (this.editStressControlMeasureId) {

      this.mode = "edit";
      this.agriStressControlMeasuresService.GetAgriRecommendation(this.editStressControlMeasureId).subscribe(data => {
        this.stressControlMeasures = data;
        this.stressControlMeasuresForm = this.formBuilder.group({
          id: [],
          commodityId: ['', Validators.required],
          stressId: ['', Validators.required],
          stressSeverityId: ['', Validators.required],
          stressControlMeasureId: ['', Validators.required],
          status: ['Inactive']
        })

        this.stressControlMeasuresForm.patchValue(this.stressControlMeasures);
      })
    }
  }

  loadAllCommodity() {
    return this.agriCommodityService.GetAllCommoditise().subscribe((data: any) => {
      this.commodityList = data;
    }, err => {
      alert(err)
    })
  }

  loadAllStressByCommodity(event) {
    console.log('commodityId',event.target['value']);
    return this.agriStressControlMeasuresService.GetAllStressByCommodity(event.target['value']).subscribe((data: any) => {
      this.stressList = data;
    }, err => {
      alert(err)
    })
  }

  loadAllStressSeverity() {
    return this.agriStressSeverityService.GetAllStressSeverity().subscribe((data: any) => {
      this.StressSeverityList = data;
    }, err => {
      alert(err)
    })
  }

  loadAllStress() {
    console.log('commodityId',event.target['value']);
    return this.zonalStressDurationService.GetAllStress().subscribe((data: any) => {
    this.stressList = data;
    }, err => {
    alert(err)
    })
  }

  GetAllAgriControlMeasures() {
    return this.agriControlMeasuresService.GetAllAgriControlMeasures().subscribe((data: any) => {
      this.ControlMeasureList = data;
    }, err => {
      alert(err)
    })
  }

  submitStressControlMeasuresForm() {

    for (let controller in this.stressControlMeasuresForm.controls) {
      this.stressControlMeasuresForm.get(controller).markAsTouched();
    }
    if (this.stressControlMeasuresForm.invalid) {
      return;
    }

    if (this.stressControlMeasuresForm.get('stressId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Stress', '');
      return;
    }

    if (this.stressControlMeasuresForm.get('commodityId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Commodity', '');
      return;
    }
    if (this.stressControlMeasuresForm.get('stressSeverityId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select StressSeverity', '');
      return;
    }

    if (this.stressControlMeasuresForm.get('stressControlMeasureId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select StressControlMeasure', '');
      return;
    }
    
    if (this.mode == 'edit') {
      this.updateRecommendation();
    } else {
      this.addRecommendation();
    }
  }

  addRecommendation() {
    this.agriStressControlMeasuresService.CreateAgriRecommendation(this.stressControlMeasuresForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.stressControlMeasures = {};
          this.mode = 'add';
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    });
  }

  updateRecommendation() {
    this.agriStressControlMeasuresService.UpdateAgriRecommendation(this.stressControlMeasuresForm.value.id, this.stressControlMeasuresForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.stressControlMeasures = {};
          this.mode = 'add';
          // this.recommendationForm.reset();

          this.createStressControlMeasuresForm();

          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    });
  }

  nInit() {
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }


  downloadExcelFormat() {
    var tableName = 'agri_stress_control_measures';
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
        this.router.navigate(['/stress/stress-control-measures']);
    }
}

import {Component, OnInit, Input, Output, EventEmitter, SimpleChanges, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import custom validator to validate that password and confirm password fields match
import {ActivatedRoute, Router} from '@angular/router';
import { AgriCommodityService } from '../../services/agri-commodity.service';
import { AgriPhenophaseService } from '../../services/agri-phenophase.service';
import { AgriHealthParameterService } from '../../services/agri-health-parameter.service';
import { AgriHealthService } from '../../services/agri-health.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-edit-agri-health',
  templateUrl: './add-edit-agri-health.component.html',
  styleUrls: ['./add-edit-agri-health.component.scss']
})
export class AddEditAgriHealthComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  healthForm: FormGroup;

  commodityList: any;
  phenophaseList: any;
  healthParameterList: any;
  editHealthId: any;
  mode: any = 'add';
  health: any;

  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  constructor(public formBuilder: FormBuilder, private agriCommodityService: AgriCommodityService, private agriPhenophaseService: AgriPhenophaseService,
    private agriHealthParamaterService: AgriHealthParameterService, public agriHealthService: AgriHealthService,
              private actRoute: ActivatedRoute, public apiUtilService: ApiUtilService, public router: Router) {


    this.createHealthForm();


  }
  getChanges() {
    console.log(this.healthForm.value)
  }

  createHealthForm() {
    this.healthForm = this.formBuilder.group({
      id: [],
      commodityId: ['', Validators.required],
      phenophaseId: ['', Validators.required],
      healthParameterId: ['', Validators.required],
      specification: ['', Validators.required],
      status: ['Inactive']


    })
  }

  ngOnInit() {
    this.loadAllCommodity();
    this.loadAllPhenophase();
    this.loadAllHealthParameter();

    this.editHealthId = this.actRoute.snapshot.paramMap.get('id');

    if (this.editHealthId) {

      this.mode = "edit";
      this.agriHealthService.GetHealth(this.editHealthId).subscribe(data => {
        this.health = data;
        this.healthForm = this.formBuilder.group({
          id: [],
          commodityId: ['', Validators.required],
          phenophaseId: ['', Validators.required],
          healthParameterId: ['', Validators.required],
          specification: ['', Validators.required],
          status: ['']
        })

        this.healthForm.patchValue(this.health);
        this.loadAllCommodityByPhenophase();
      })


    }

  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  loadAllCommodity() {
    return this.agriCommodityService.GetAllCommoditise().subscribe((data: any) => {
      this.commodityList = data;
    }, err => {
      alert(err)
    })
  }

  loadAllPhenophase() {
    return this.agriPhenophaseService.GetAllPhenophase().subscribe((data: any) => {
      this.phenophaseList = data;
    }, err => {
      alert(err)
    })
  }

  loadAllHealthParameter() {
    return this.agriHealthParamaterService.GetAllHealthParameter().subscribe((data: any) => {
      this.healthParameterList = data;
    }, err => {
      alert(err)
    })
  }

  submitHealthForm() {

    for (let controller in this.healthForm.controls) {
      this.healthForm.get(controller).markAsTouched();
    }
    if (this.healthForm.invalid) {
      return;
    }

    if (this.healthForm.get('healthParameterId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select HealthParameter', '');
      return;
    }

    if (this.healthForm.get('commodityId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Commodity', '');
      return;
    }
    
    if (this.mode == "edit") {
      this.updateHealth();
    } else {
      this.addHealth();
    }
  }
  addHealth() {
    this.agriHealthService.CreateHealth(this.healthForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.health = {};
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
  updateHealth() {
    this.agriHealthService.UpdateHealth(this.healthForm.value.id, this.healthForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.health = {};
          this.mode = "add";
          // this.healthForm.reset();

          this.createHealthForm();

          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    })
  }
  

  loadAllCommodityByPhenophase() {
    let commodityId = this.healthForm.value.commodityId; console.log(commodityId)
    this.agriCommodityService.getCommodityByPhenophaseId(commodityId).subscribe(
      (data: {}) => {
        this.phenophaseList = data;
        console.log(this.phenophaseList);
      }
    );
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  downloadExcelFormat() {
    var tableName = "agri_health";
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
    this.router.navigate(['/yield/health']);
    }
}

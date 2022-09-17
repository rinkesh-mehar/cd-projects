import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import {ActivatedRoute, Router} from '@angular/router';
import { AgriCommodityService } from '../../services/agri-commodity.service';
import { ApiUtilService } from '../../../services/api-util.service';
import { AgriQualityChartService } from '../../services/agri-quality-chart.service';
import { AgriPhenophaseService } from '../../services/agri-phenophase.service';
import { AgriHealthParameterService } from '../../services/agri-health-parameter.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-edit-agri-quality-chart',
  templateUrl: './add-edit-agri-quality-chart.component.html',
  styleUrls: ['./add-edit-agri-quality-chart.component.scss']
})
export class AddEditAgriQualityChartComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  QualityChartForm: FormGroup;
 
  commodityList: any;
  phenophaseList: any;
  healthParameterList: any;
  editQualityChartId: any;
  mode: any = 'add';
  QualityChart: any;
  uploadFile: File = null;
  imgPerview: any;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  constructor(public formBuilder: FormBuilder, private agriCommodityService : AgriCommodityService, private agriPhenophaseService : AgriPhenophaseService,private agriHealthParameterService : AgriHealthParameterService,
   public agriQualityChartService : AgriQualityChartService, private actRoute: ActivatedRoute,
              public apiUtilService: ApiUtilService, public router: Router ) {


    this.createQualityChartForm();


  }
  getChanges() {
    console.log(this.QualityChartForm.value)
  }

  createQualityChartForm() {
    this.QualityChartForm = this.formBuilder.group({
      id: [],
      commodityId: ['', Validators.required],
      phenophaseId: ['', Validators.required],
      healthParameterId: ['', Validators.required],
      gradeI: ['', Validators.required],
      gradeII: ['', Validators.required],
      gradeIII: ['', Validators.required],
      mingradeI: ['', Validators.required],
      maxgradeI: ['', Validators.required],
      mingradeII: ['', Validators.required],
      maxgradeII: ['', Validators.required],
      mingradeIII: ['', Validators.required],
      maxgradeIII: ['', Validators.required],
      status: ['Inactive']
     
   
      })
  }

  ngOnInit() {
    this.loadAllCommodity();
    this.loadAllHealthParameter();
  //  this.loadAllPhenophase();
 
  
    this.editQualityChartId = this.actRoute.snapshot.paramMap.get('id');

    if (this.editQualityChartId) {

      this.mode = "edit";
      this.agriQualityChartService.GetQualityChart(this.editQualityChartId).subscribe(data => {
        this.QualityChart = data;
        this.QualityChartForm = this.formBuilder.group({
          id: [],
          commodityId: [data.commodityId, Validators.required],
          phenophaseId: [data.phenophaseId, Validators.required],
          healthParameterId: ['', Validators.required],
          gradeI: ['', Validators.required],
          gradeII: ['', Validators.required],
          gradeIII: ['', Validators.required],
          mingradeI: ['', Validators.required],
          maxgradeI: ['', Validators.required],
          mingradeII: ['', Validators.required],
          maxgradeII: ['', Validators.required],
          mingradeIII: ['', Validators.required],
          maxgradeIII: ['', Validators.required],
          status: ['']
        })

        
        this.QualityChartForm.patchValue(this.QualityChart);
        // this.getPhenophaseByCommodity();
        this.loadAllPhenophaseByCommodity();
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

  // loadAllPhenophase() {
  //   return this.agriPhenophaseService.GetAllPhenophase().subscribe((data: any) => {
  //     this.phenophaseList = data;
  //   }, err => {
  //     alert(err)
  //   })
  // }

  loadAllHealthParameter() {
    return this.agriHealthParameterService.GetAllHealthParameter().subscribe((data: any) => {
      this.healthParameterList = data;
    }, err => {
      alert(err)
    })
  }

  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  submitQualityChartForm() {

    for (let controller in this.QualityChartForm.controls) {
      this.QualityChartForm.get(controller).markAsTouched();
    }
    if (this.QualityChartForm.invalid) {
      return;
    }

    if(this.QualityChartForm.get('phenophaseId').value == 0){
      this.errorModal.showModal('ERROR', 'Please Select Phenophase', '');
      return;
    }

    if(this.QualityChartForm.get('commodityId').value == 0){
      this.errorModal.showModal('ERROR', 'Please Select Phenophase', '');
      return;
    }

    if(this.QualityChartForm.get('healthParameterId').value == 0){
      this.errorModal.showModal('ERROR', 'Please Select Phenophase', '');
      return;
    }
  
    if (this.mode == "edit") {
      this.updateQualityChart();
    } else {
      this.addQualityChart();
    }
  }
  addQualityChart() {
    this.agriQualityChartService.CreateQualityChart(this.QualityChartForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.QualityChart = {};
          this.mode = "add";
          this.QualityChartForm.reset();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    })
  }
  updateQualityChart() {
    this.agriQualityChartService.UpdateQualityChart(this.QualityChartForm.value.id, this.QualityChartForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.QualityChart = {};
          this.mode = "add";
          // this.QualityChartForm.reset();

         this.createQualityChartForm();

          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    })
  }nInit() {
  }

  // getPhenophaseByCommodity() {
  //   if (this.QualityChartForm.value.commodityId) {
  //     this.agriPhenophaseService.GetAllPhenophaseByCommodityId(this.QualityChartForm.value.commodityId).subscribe((data: {}) => {
  //       this.phenophaseList = data;
  //     })
  //   } else {
  //     this.phenophaseList = []
  //   }

  // }

  loadAllPhenophaseByCommodity() {
    let commodityId : number = this.QualityChartForm.value.commodityId; console.log(commodityId)
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


  downloadExcelFormat(){
    var tableName = "agri_quality_chart";
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
    this.router.navigate(['/yield/quality-chart']);
  }
}

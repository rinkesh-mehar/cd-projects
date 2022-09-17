import {Component, OnInit, Input, Output, EventEmitter, SimpleChanges, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import custom validator to validate that password and confirm password fields match
import {ActivatedRoute, Router} from '@angular/router';
import { AgriCommodityService } from '../../services/agri-commodity.service';
import { AgriPhenophaseService } from '../../services/agri-phenophase.service';
import { AgriHealthParameterService } from '../../services/agri-health-parameter.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-edit-agri-health-parameter',
  templateUrl: './add-edit-agri-health-parameter.component.html',
  styleUrls: ['./add-edit-agri-health-parameter.component.scss']
})
export class AddEditAgriHealthParameterComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  
  healthParameterForm: FormGroup;
  // operationMode: string;

  commodityList: any;
  phenophaseList: any;
  editHealthParameterId: any;
  mode: any = 'add';
  healthParameter: any;

  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;



  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  constructor(public formBuilder: FormBuilder, //private agriCommodityService : AgriCommodityService, private agriPhenophaseService : AgriPhenophaseService,
    public agriHealthParamaterService : AgriHealthParameterService, private actRoute: ActivatedRoute,
              public apiUtilService: ApiUtilService, public router: Router ) {


    this.createHealthParamaterForm();


  }
  getChanges() {
    console.log(this.healthParameterForm.value)
  }

  createHealthParamaterForm() {
    this.healthParameterForm = this.formBuilder.group({
      id: [],
      //commodityId: ['', Validators.required],
      //phenophaseId: ['', Validators.required],
      name: ['', Validators.required],
     status: ['Inactive']
   
      })
  }

  ngOnInit() {
  //   this.loadAllCommodity();
  //  this.loadAllPhenophase();
  
    this.editHealthParameterId = this.actRoute.snapshot.paramMap.get('id');

    if (this.editHealthParameterId) {

      this.mode = "edit";
      this.agriHealthParamaterService.GetHealthParameter(this.editHealthParameterId).subscribe(data => {
        this.healthParameter = data;
        this.healthParameterForm = this.formBuilder.group({
          id: [],
          // commodityId: ['', Validators.required],
          // phenophaseId: ['', Validators.required],
          name: ['', Validators.required],
          status: ['']
        })

        this.healthParameterForm.patchValue(this.healthParameter);
      })


    }

  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
// ngOnChanges(changes: SimpleChanges) {
  //   if (this.mode && this.mode == "edit") {

  //     this.userForm = this.formBuilder.group({
  //       id: [],
  //       name: ['', Validators.required],
  //       email: ['', [Validators.required, Validators.email]],
  //       roleId: ['', Validators.required],
  //       status: ['', Validators.required]
  //     })

  //     this.userForm.patchValue(changes.user.currentValue);
  //   }

  // }

  // loadAllCommodity() {
  //   return this.agriCommodityService.GetAllCommoditise().subscribe((data: any) => {
  //     this.commodityList = data;
  //   }, err => {
  //     alert(err)
  //   })
  // }

  // loadAllPhenophase() {
  //   return this.agriPhenophaseService.GetAllPhenophase().subscribe((data: any) => {
  //     this.phenophaseList = data;
  //   }, err => {
  //     alert(err)
  //   })
  // }

  submitHealthParameterForm() {

    console.log("hhh");
    // if(this.healthParameterForm.get('phenophaseId').value == 0){
    //   alert('Please Select Phenophase');
    //   return;
    // }

    // if(this.healthParameterForm.get('commodityId').value == 0){
    //   alert('Please Select Commodity');
    //   return;
    // }
    for (let controller in this.healthParameterForm.controls) {
      this.healthParameterForm.get(controller).markAsTouched();
    }
    if (this.healthParameterForm.invalid) {
      return;
    }
    if (this.mode == "edit") {
      this.updateHealthParameter();
    } else {
      this.addHealthParameter();
    }
  }
  addHealthParameter() {
    this.agriHealthParamaterService.CreateHealthParameter(this.healthParameterForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.healthParameter = {};
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
  updateHealthParameter() {
    this.agriHealthParamaterService.UpdateHealthParameter(this.healthParameterForm.value.id, this.healthParameterForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.healthParameter = {};
          this.mode = "add";
          // this.healthParameterForm.reset();

         this.createHealthParamaterForm();

          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  downloadExcelFormat(){
    var tableName = "agri_health_parameter";
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
    this.router.navigate(['/yield/health-parameter']);
  }
}

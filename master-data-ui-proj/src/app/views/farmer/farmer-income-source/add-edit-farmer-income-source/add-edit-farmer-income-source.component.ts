import {Component, OnInit, ViewChild} from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { FarmerIncomeSourceService } from '../../services/farmer-income-source.service';
import {ActivatedRoute, Router} from '@angular/router';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-edit-farmer-income-source',
  templateUrl: './add-edit-farmer-income-source.component.html',
  styleUrls: ['./add-edit-farmer-income-source.component.scss']
})
export class AddEditFarmerIncomeSourceComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
 
  incomeSourceForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  editId: string;
  mode: string="add";

  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;


  ngOnInit() {
    this.incomeSourceForm = this.fb.group({
      id: [],
      name: ['', Validators.required],
      status: ['Inactive']

    })

    this.editId = this.actRoute.snapshot.paramMap.get('id');
    if (this.editId) {
      this.mode = "edit";
      this.farmerIncomeSourceService.GetIncomeSource(this.editId).subscribe(data => {
        this.incomeSourceForm.patchValue(data);
      })
    }
  }

  constructor(
    public fb: FormBuilder,
    public farmerIncomeSourceService: FarmerIncomeSourceService,
    private actRoute: ActivatedRoute,
    public apiUtilService: ApiUtilService,
    public router: Router

  ) { }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  submitForm() {

    for (let controller in this.incomeSourceForm.controls) {
      this.incomeSourceForm.get(controller).markAsTouched();
    }
    if (this.incomeSourceForm.invalid) {
      return;
    }
    if (this.mode == 'add') {
      this.add();
    } else {
      this.update();
    }
  }

  add() {
    this.farmerIncomeSourceService.CreateIncomeSource(this.incomeSourceForm.value).subscribe(res => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.mode="add";
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

  update() {
    this.farmerIncomeSourceService.UpdateIncomeSource(this.incomeSourceForm.value.id, this.incomeSourceForm.value).subscribe(res => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }


  downloadExcelFormat(){
    var tableName = "farmer_income_source";
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
    this.router.navigate(['/farmer/income-source']);
  }
}

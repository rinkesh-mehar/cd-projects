import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import {ActivatedRoute, Router} from '@angular/router';
import { AgriControlMeasuresService } from '../../services/agri-control-measures.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-edit-agri-stress-control-measures',
  templateUrl: './add-edit-agri-control-measures.component.html',
  styleUrls: ['./add-edit-agri-control-measures.component.scss']
})
export class AddEditAgriControlMeasuresComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  controlMeasuresForm: FormGroup;

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
    this.controlMeasuresForm = this.fb.group({
      id: [],
      name: ['', Validators.required],
      status: ['Inactive']

    })

    this.editId = this.actRoute.snapshot.paramMap.get('id');
    if (this.editId) {
      this.mode = "edit";
      this.agriControlMeasuresService.GetAgriStressControlMeasures(this.editId).subscribe(data => {
        this.controlMeasuresForm.patchValue(data);
      })
    }
  }

  constructor(
    public fb: FormBuilder,
    public agriControlMeasuresService: AgriControlMeasuresService,
    private actRoute: ActivatedRoute,
    public apiUtilService: ApiUtilService,
    public router: Router

  ) { }

  submitControlMeasuresForm() {

    for (let controller in this.controlMeasuresForm.controls) {
      this.controlMeasuresForm.get(controller).markAsTouched();
    }
    if (this.controlMeasuresForm.invalid) {
      return;
    }
    if (this.mode == 'add') {
      this.add();
    } else {
      this.update();
    }
  }

  add() {
    this.agriControlMeasuresService.CreateAgriStressControlMeasures(this.controlMeasuresForm.value).subscribe(res => {
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

  update() {
    this.agriControlMeasuresService.UpdateAgriStressControlMeasures(this.controlMeasuresForm.value.id, this.controlMeasuresForm.value).subscribe(res => {
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
    var tableName = "agri_control_measures";
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
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
    modalSuccess($event: any) {
        this.router.navigate(['/stress/control-measures']);
    }
}

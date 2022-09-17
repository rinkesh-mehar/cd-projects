import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import {ActivatedRoute, Router} from '@angular/router';
import { AgriMeteorologicalWeekService } from '../../services/agri-meteorological-week.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-add-edit-agri-meteorological-week',
  templateUrl: './add-edit-agri-meteorological-week.component.html',
  styleUrls: ['./add-edit-agri-meteorological-week.component.scss']
})
export class AddEditAgriMeteorologicalWeekComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  meteorologicalWeekForm: FormGroup;

  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  editId: string;
  mode: string = "add";
  monthDays: number = 31;


  months = [
    { 'name': "January", 'value': 1, 'days': 31 },
    { 'name': "February", 'value': 2, 'days': 29 },
    { 'name': "March", 'value': 3, 'days': 31 },
    { 'name': "April", 'value': 4, 'days': 30 },
    { 'name': "May", 'value': 5, 'days': 31 },
    { 'name': "June", 'value': 6, 'days': 30 },
    { 'name': "July", 'value': 7, 'days': 31 },
    { 'name': "August", 'value': 8, 'days': 31 },
    { 'name': "September", 'value': 9, 'days': 30 },
    { 'name': "October", 'value': 10, 'days': 31 },
    { 'name': "November", 'value': 11, 'days': 30 },
    { 'name': "December", 'value': 12, 'days': 31 }]

  ngOnInit() {
    this.meteorologicalWeekForm = this.fb.group({
      id: [],
      weekNo: ['', Validators.required],
      startDay: ['', Validators.required],
      startMonth: ['', Validators.required],
      endDay: ['', Validators.required],
      endMonth: ['', Validators.required],
      status: ['Inactive'],
    })

    this.editId = this.actRoute.snapshot.paramMap.get('id');
    if (this.editId) {
      this.mode = "edit";
      this.agriMeteorologicalWeekService.GetAgriMeteorologicalWeek(this.editId).subscribe(data => {
        this.meteorologicalWeekForm.patchValue(data);
      })
    }
  }

  constructor(
    public fb: FormBuilder,
    public agriMeteorologicalWeekService: AgriMeteorologicalWeekService,
    private actRoute: ActivatedRoute,
    public apiUtilService: ApiUtilService,
    public router: Router
  ) { }

  monthChange() {
    this.monthDays = 0;
    let startMonth = this.meteorologicalWeekForm.value.startMonth;
    this.monthDays = this.months[startMonth - 1].days;
  }

  monthEndChange() {
    this.monthDays = 0;
    let endMonth = this.meteorologicalWeekForm.value.endMonth;
    this.monthDays = this.months[endMonth - 1].days;
  }

  submitMeteorologicalWeekForm() {

    for (let controller in this.meteorologicalWeekForm.controls) {
      this.meteorologicalWeekForm.get(controller).markAsTouched();
    }
    if (this.meteorologicalWeekForm.invalid) {
      return;
    }
    if (this.mode == 'add') {
      this.add();
    } else {
      this.update();
    }
  }

  add() {
    this.agriMeteorologicalWeekService.CreateAgriMeteorologicalWeek(this.meteorologicalWeekForm.value).subscribe(res => {
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
    this.agriMeteorologicalWeekService.UpdateAgriMeteorologicalWeek(this.meteorologicalWeekForm.value.id, this.meteorologicalWeekForm.value).subscribe(res => {
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
    var tableName = "agri_meteorological_weeks";
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
        this.router.navigate(['/agri/meteorological-week']);
    }
}

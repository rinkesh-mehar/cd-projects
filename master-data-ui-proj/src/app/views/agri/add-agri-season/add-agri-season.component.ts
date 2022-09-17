import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AgriSeasonService } from '../services/agri-season.service';
import { ApiUtilService } from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-add-agri-season',
  templateUrl: './add-agri-season.component.html',
  styleUrls: ['./add-agri-season.component.scss']
})
export class AddAgriSeasonComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  seasonForm: FormGroup;
  seasonArr: any = [];

  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  ngOnInit() {
    this.addSeason()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public agriSeasonService: AgriSeasonService,
    public apiUtilService: ApiUtilService 
 
  ){ }

  addSeason() {
    this.seasonForm = this.fb.group({
      name: ['',Validators.required],
      status: ['Inactive']
      
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  submitForm() {

    for (let controller in this.seasonForm.controls) {
      this.seasonForm.get(controller).markAsTouched();
    }

    if (this.seasonForm.invalid) {
      return;
    }


    this.agriSeasonService.CreateSeason(this.seasonForm.value).subscribe(
        res => {
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

  downloadExcelFormat() {
    var tableName = 'agri_season';
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
    this.router.navigate(['/agri/season']);
  }
}

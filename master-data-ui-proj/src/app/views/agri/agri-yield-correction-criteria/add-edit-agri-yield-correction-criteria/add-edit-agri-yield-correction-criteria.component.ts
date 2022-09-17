import { Component, OnInit, NgZone } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ApiUtilService } from '../../../services/api-util.service';
import { AgriYieldCorrectionCriteriaService } from '../../services/agri-yield-correction-criteria.service';


@Component({
  selector: 'app-add-edit-agri-yield-correction-criteria',
  templateUrl: './add-edit-agri-yield-correction-criteria.component.html',
  styleUrls: ['./add-edit-agri-yield-correction-criteria.component.scss']
})
export class AddEditAgriYieldCorrectionCriteriaComponent implements OnInit {

  yieldCorrectionCriteriaForm: FormGroup;

  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  editId: string;
  mode: string="add";



  ngOnInit() {
    this.yieldCorrectionCriteriaForm = this.fb.group({
      id: [],
      name: ['', Validators.required],
      status: ['Inactive']

    })

    this.editId = this.actRoute.snapshot.paramMap.get('id');
    if (this.editId) {
      this.mode = "edit";
      this.agriYieldCorrectionCriteriaService.GetYieldCorrectionCriteria(this.editId).subscribe(data => {
        this.yieldCorrectionCriteriaForm.patchValue(data);
      })
    }
  }

  constructor(
    public fb: FormBuilder,
    public agriYieldCorrectionCriteriaService: AgriYieldCorrectionCriteriaService,
    private actRoute: ActivatedRoute,
    public apiUtilService: ApiUtilService

  ) { }

  submityieldCorrectionCriteriaForm() {

    for (let controller in this.yieldCorrectionCriteriaForm.controls) {
      this.yieldCorrectionCriteriaForm.get(controller).markAsTouched();
    }
    if (this.yieldCorrectionCriteriaForm.invalid) {
      return;
    }
    if (this.mode == 'add') {
      this.add();
    } else {
      this.update();
    }
  }

  add() {
    this.agriYieldCorrectionCriteriaService.CreateYieldCorrectionCriteria(this.yieldCorrectionCriteriaForm.value).subscribe(res => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.yieldCorrectionCriteriaForm.reset();
          this.mode="add";
          setTimeout(() => {
            this.isSubmitted = false;
            this.isSuccess = false
          }, 4000)
        } else {
          this._statusMsg = res.error;
        }
      }
    });
  }

  update() {
    this.agriYieldCorrectionCriteriaService.UpdateYieldCorrectionCriteria(this.yieldCorrectionCriteriaForm.value.id, this.yieldCorrectionCriteriaForm.value).subscribe(res => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.yieldCorrectionCriteriaForm.reset();
          setTimeout(() => {
            this.isSubmitted = false;
            this.isSuccess = false
          }, 4000)
        } else {
          this._statusMsg = res.error;
        }
      }
    });
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  downloadExcelFormat() {
    var tableName = "agri_yield_correction_criteria";
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
          this._statusMsg = res.message;

          this.delay(4000).then(any => {
            this.isSubmittedBulk = false;
            this.isSuccessBulk = false;
          });
        } else {
          this._statusMsg = res.error;
        }
      }
    });
  }

}

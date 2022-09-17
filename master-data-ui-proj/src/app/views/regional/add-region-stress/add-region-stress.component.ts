import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { GeoRegionService } from '../../geo/services/geo-region.service';
import { RegionStressService } from '../services/region-stress.service';
import { ZonalStressDurationService } from '../../agri/services/zonal-stress-duration.service';
import { GeoStateService } from '../../geo/services/geo-state.service';
import { ApiUtilService } from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-add-region-stress',
  templateUrl: './add-region-stress.component.html',
  styleUrls: ['./add-region-stress.component.scss']
})
export class AddRegionStressComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  // RegionList: any = [];
  StressList: any = [];
  StateList: any = [];
  stressForm: FormGroup;
  stressArr: any = [];
  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;


  ngOnInit() {
    // this.loadAllRegion();
    this.loadAllStress();
    this.loadAllState();
    this.addStress()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public regionStressService: RegionStressService,
    // public geoRegionService: GeoRegionService,
    public geoStateService: GeoStateService,
    public zonalStressDurationService: ZonalStressDurationService,
    public apiUtilService: ApiUtilService

  ) { }

  addStress() {
    this.stressForm = this.fb.group({
      stateCode: ['', Validators.required],
      // regionId: ['', Validators.required],
      stressId: ['', Validators.required],
      status: ['Inactive']

    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {

    // if (this.stressForm.get('regionId').value == 0) {
    //   alert('Please Select Region');
    //   return;
    // }

    if (this.stressForm.get('stressId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Stress', '');
      return;
    }

    if (this.stressForm.get('stateCode').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select State', '');
      return;
    }

    for (let controller in this.stressForm.controls) {
      this.stressForm.get(controller).markAsTouched();
    }

    if (this.stressForm.invalid) {
      return;
    }

    this.regionStressService.CreateStress(this.stressForm.value).subscribe(res => {
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

  // //Region list
  // loadAllRegion() {
  //   return this.geoRegionService.GetAllRegion().subscribe((data: {}) => {
  //     this.RegionList = data;
  //   })
  // }

  // Stress list
  loadAllStress() {
    return this.zonalStressDurationService.GetAllStress().subscribe((data: {}) => {
      this.StressList = data;
    });
  }

  // State list
  loadAllState() {
    return this.geoStateService.GetAllState().subscribe((date: {}) => {
      this.StateList = date;
    });
  }

  downloadExcelFormat() {
    var tableName = 'regional_stress';
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
    this.router.navigate(['/regional/stress']);
  }
}

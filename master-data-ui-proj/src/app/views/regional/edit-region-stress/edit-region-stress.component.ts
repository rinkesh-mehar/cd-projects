import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { RegionStressService } from '../services/region-stress.service';
import { GeoRegionService } from '../../geo/services/geo-region.service';
import { ZonalStressDurationService } from '../../agri/services/zonal-stress-duration.service';
import { GeoStateService } from '../../geo/services/geo-state.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-edit-region-stress',
  templateUrl: './edit-region-stress.component.html',
  styleUrls: ['./edit-region-stress.component.scss']
})
export class EditRegionStressComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  StateList: any = [];
  // RegionList: any = [];
  stressArr: any = [];
  StressList: any = [];
  updateStressForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  ngOnInit() {
    this.updateForm();
    // this.loadAllRegion();
    this.loadAllState();
    this.loadAllStress();


  }
  constructor(
    private actRoute: ActivatedRoute,
    public regionStressService: RegionStressService,
    public geoStateService: GeoStateService,
    public zonalStressDurationService: ZonalStressDurationService,
    // public geoRegionService: GeoRegionService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) {
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.regionStressService.GetStress(id).subscribe((data) => {
      this.updateStressForm = this.fb.group({
        stateCode: [data.stateCode, Validators.required],
        // regionId: [data.regionId, Validators.required],
        stressId: [data.stressId, Validators.required],
        status: [data.status]

      })
    })
  }

  updateForm() {
    this.updateStressForm = this.fb.group({
      stateCode: ['', Validators.required],
      // regionId: ['', Validators.required],
      StressId: ['', Validators.required],
      status: ['Inactive']

    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {

    if (this.updateStressForm.get('stateCode').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select State', '');
      return;
    }

    // if (this.updateStressForm.get('regionId').value == 0) {
    //   alert('Please Select Region');
    //   return;
    // }

    if (this.updateStressForm.get('stressId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Stress', '');
      return;
    }


    for (let controller in this.updateStressForm.controls) {
      this.updateStressForm.get(controller).markAsTouched();
    }

    if (this.updateStressForm.invalid) {
      return;
    }
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.regionStressService.UpdateStress(id, this.updateStressForm.value).subscribe(res => {
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

  modalSuccess($event: any) {
    this.router.navigate(['/regional/stress']);
  }
}

import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { GeoRegionService } from '../services/geo-region.service';
import { GeoStateService } from '../services/geo-state.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-geo-region',
  templateUrl: './edit-geo-region.component.html',
  styleUrls: ['./edit-geo-region.component.scss']
})
export class EditGeoRegionComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  StateList: any = [];
  RegionList: any = [];
  updateRegionForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  ngOnInit() {
    this.updateForm();
    this.loadAllState();
  }

  constructor(
    private actRoute: ActivatedRoute,
    public geoRegionService: GeoRegionService,
    public geoStateService: GeoStateService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) {
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.geoRegionService.GetRegion(id).subscribe((data) => {
        this.updateRegionForm = this.fb.group({
        // tileId: [data.tileId, Validators.required],
        stateCode: [data.stateCode, Validators.required],
        name: [data.name, Validators.required],
        description: [data.description],
        status: [data.status]

      })
    })
  }

  updateForm() {
    this.updateRegionForm = this.fb.group({
      // tileId: ['', Validators.required],
      stateCode: ['', Validators.required],
      name: ['', Validators.required],
      description: [''],
      status:['Inactive']
 })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {


    if (this.updateRegionForm.get('stateCode').value == 0) {
      alert('Please Select State');
      return;
    }

    for (let controller in this.updateRegionForm.controls) {
      this.updateRegionForm.get(controller).markAsTouched();
    }

    if (this.updateRegionForm.invalid) {
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.geoRegionService.UpdateRegion(id, this.updateRegionForm.value).subscribe(res => {
      this.isSubmitted = true;
     
      if(res){
        this.isSuccess = res.success;
        if(res.success){
            this.successModal.showModal('Success', res.message, '');
        } else {
            this.errorModal.showModal('Error', res.error, '');
        }
      }
    })
  }

  //State list
  loadAllState() {
    return this.geoStateService.GetAllState().subscribe((data: {}) => {
      this.StateList = data;
    })
  }

    modalSuccess($event: any) {
        this.router.navigate(['/geo/region']);
    }
}

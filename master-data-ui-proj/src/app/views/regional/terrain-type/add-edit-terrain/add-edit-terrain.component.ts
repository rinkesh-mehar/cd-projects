import {Component, OnInit, ViewChild} from '@angular/core';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {RegionTerrainService} from '../../services/region-terrain.service';

@Component({
    selector: 'app-add-edit-terrain',
    templateUrl: './add-edit-terrain.component.html',
    styleUrls: ['./add-edit-terrain.component.scss']
})
export class AddEditTerrainComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    regionTerrainForm: FormGroup;
    editId: string;
    mode: string = 'add';
    geoRegionList: any;
    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    statusMsg: string;

    constructor(public fb: FormBuilder,
                public router: Router,
                private actRoute: ActivatedRoute,
                private regionTerrainService: RegionTerrainService
    ) {
    }

    ngOnInit(): void {
        this.getGeoRegion();

        this.regionTerrainForm = this.fb.group({
            id: [],
            regionId: ['', Validators.required],
            terrainType: ['', Validators.required],
            minPerKm: ['', Validators.required],
            status: ['Inactive']
        });
        this.editId = this.actRoute.snapshot.paramMap.get('id');
        if (this.editId) {
            this.mode = 'edit';
            this.regionTerrainService.getTerrainById(this.editId).subscribe(data => {
                this.regionTerrainForm.patchValue(data);
            });
        }
    }

    getGeoRegion() {
        this.regionTerrainService.getRegion().subscribe(data => {
            if (data) {
                this.geoRegionList = data;
            }
        });
    }
    trimValue(formControl) { 
        formControl.setValue(formControl.value.trim()); 
      }
      
    submitForm() {

        for (const controller in this.regionTerrainForm.controls) {
            this.regionTerrainForm.get(controller).markAsTouched();
        }
        if (this.regionTerrainForm.invalid) {
            return;
        }
        if (this.mode === 'add') {
            this.add();
        } else {
            this.update();
        }
    }

    add() {
        this.regionTerrainService.addRegionTerrain(this.regionTerrainForm.value).subscribe(res => {
            this.isSubmitted = true;
            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                    this.statusMsg = res.message;
                    // this.callingStatusForm.reset();
                    this.mode = 'add';
                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            }
        });
    }

    update() {
        this.regionTerrainService.UpdateRegionTerrain(this.regionTerrainForm.value.id, this.regionTerrainForm.value).subscribe(res => {
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

    modalSuccess($event: any) {
        this.router.navigate(['/regional/terrain']);
    }

}

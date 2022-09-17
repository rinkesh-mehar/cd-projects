import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup, NgForm, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { GeoRegionService } from "../../../geo/services/geo-region.service";
import { ErrorModalComponent } from "../../../global/error-modal/error-modal.component";
import { SuccessModalComponent } from "../../../global/success-modal/success-modal.component";
import { RegionalConnectivityService } from "../../services/regional-connectivity.service";
import { GeoStateService } from '../../../geo/services/geo-state.service';

@Component({
    selector: 'app-add-regional-connect',
    templateUrl: './add-regional-connectivity.component.html'
})
export class AddRegionalConnectivity implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    stateList : any =[];
    regionList: any = [];
    regionalConnectivityForm: FormGroup;

    constructor(public geoRegionService: GeoRegionService,
        public fb: FormBuilder, private regionalConnectService: RegionalConnectivityService,
        private geoStateService: GeoStateService,
        private router:Router) {

    }

    ngOnInit(): void {
        this.regionalConnectivityForm = this.fb.group({
            stateCode: ['', Validators.required],
            regionId: ['', Validators.required],
            surfacedProportion: ['', [Validators.required,
                Validators.pattern("^[0-9.]*$")]],
            unsurfacedProportion: ['',[Validators.required,
                Validators.pattern("^[0-9.]*$")]],
            surfacedTimeMin: ['', [Validators.required,
                Validators.pattern("^[0-9.]*$")]],
            unsurfacedTimeMin: ['', [Validators.required,
                Validators.pattern("^[0-9.]*$")]],
            status: ['Inactive']
        })
        this.getAllState();
    }

    trimValue(formControl) { 
        formControl.setValue(formControl.value.trim()); 
      }


      getAllState()
      {
          this.geoStateService.GetAllState().subscribe((data: {}) =>{
          this.stateList=data;
          })
          
      }
    getAllRegionByStateCode() {
        this.regionalConnectivityForm.patchValue({regionId:''});
        this.geoRegionService.GetAllRegionByStateCode(this.regionalConnectivityForm.value.stateCode)
            .subscribe(region => {
                if (region) {
                    console.log(JSON.stringify(region));
                    this.regionList = region;
                }
            }, error => {
                console.log(error);

            })
    }

    submitForm(f: NgForm) {
        for (let controller in this.regionalConnectivityForm.controls) {
            this.regionalConnectivityForm.get(controller).markAsTouched();
          }
      
          if (this.regionalConnectivityForm.invalid) {
            return;
          }
        console.log(f.form.value);
        this.regionalConnectService.saveRegionalConnectivityData(f.form.value)
            .subscribe(response => {
                if (response.success) {
                    this.successModal.showModal('SUCCESS', response.message, '');
                } else {
                    this.errorModal.showModal('ERROR', response.error, '');
                }
            })
    }


    modalSuccess(event){
        this.router.navigate(['regional/connectivity']);
    }




}

import { ViewChild } from "@angular/core";
import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, NgForm, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { GeoRegionService } from "../../geo/services/geo-region.service";
import { GeoStateService } from "../../geo/services/geo-state.service";
import { ErrorModalComponent } from "../../global/error-modal/error-modal.component";
import { SuccessModalComponent } from "../../global/success-modal/success-modal.component";
import { RegionalConnectivityService } from "../services/regional-connectivity.service";

@Component({
    selector: 'app-edit-regional-connect',
    templateUrl: './edit-regional-connect.component.html'
})
export class EditRegionalConnectComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    stateList : any=[];
    updateRegionalForm: FormGroup
    regionList: any = [];
    regionalConnectivity :any;
    stateCodeByRegionId : any= [];

    constructor(private actRoute: ActivatedRoute,
        private regionalService: RegionalConnectivityService,
        private formBuilder: FormBuilder,
        public geoRegionService: GeoRegionService,
        private geoStateService: GeoStateService,
        private router:Router) {

    }


    ngOnInit() {
        this.getAllState();
        var id = this.actRoute.snapshot.paramMap.get('id');
        console.log(id);
        this.regionalService.getRegionalConnectDataById(id)
            .subscribe(data => {
                this.regionalConnectivity=data;
                if (data) {
                    // console.log("Data:", JSON.stringify(data));
                    this.updateRegionalForm = this.formBuilder.group({
                        stateCode:['', Validators.required],
                        regionId: ['', Validators.required],
                        surfacedProportion: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
                        unsurfacedProportion: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
                        surfacedTimeMin: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
                        unsurfacedTimeMin: ['', [Validators.required,Validators.pattern("^[0-9.]*$")]],
                        status: ['']
                    })
                }
               
                this.updateRegionalForm.patchValue(this.regionalConnectivity);
                this.getStateCodeByRegionId();
            })
    }
    
    trimValue(formControl) { 
        formControl.setValue(formControl.value.trim()); 
      }

      getAllState()
      {
          this.geoStateService.GetAllState().subscribe((data: {}) => {
            this.stateList = data;
          });
      }
      
    getAllRegionByStateCode() {
        this.updateRegionalForm.patchValue({regionId:''});
        this.geoRegionService.GetAllRegionByStateCode(this.updateRegionalForm.value.stateCode)
            .subscribe(region => {
                if (region) {
                    console.log(JSON.stringify(region));
                    this.regionList = region;
                }
            }, error => {
                console.log(error);

            })
    }


    getStateCodeByRegionId(){
        return this.geoRegionService.getStateCodeByRegionId(this.updateRegionalForm.value.regionId).subscribe(data =>{
      
        this.stateCodeByRegionId =data;
        this.updateRegionalForm.patchValue(this.stateCodeByRegionId);
        
        
        this.getAllRegionByStateCode();
        this.updateRegionalForm.patchValue({regionId: this.regionalConnectivity.regionId});
        this.updateRegionalForm.patchValue({status: this.regionalConnectivity.status});
    
        })
      }
    
    


    submitForm() {

        for (let controller in this.updateRegionalForm.controls) {
            this.updateRegionalForm.get(controller).markAsTouched();
          }
      
          if (this.updateRegionalForm.invalid) {
            return;
          }
        console.log();
        var regionId = this.actRoute.snapshot.paramMap.get('id');
        this.regionalService.updateRegionalData(regionId, this.updateRegionalForm.value)
            .subscribe(resp => {
                if (resp.success) {
                    this.successModal.showModal('SUCCESS', resp.message, '');
                    
                } else {
                    this.errorModal.showModal('ERROR', resp.error, '');
                }
            })
    }


    modalSuccess(event){
        this.router.navigate(['regional/connectivity']);
    }

}
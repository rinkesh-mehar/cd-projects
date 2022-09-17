import {Component, OnInit, ViewChild} from '@angular/core';
import {MonthWbTravelTimeService} from '../../services/month-wb-travel-time.service';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {ActivatedRoute, Router} from '@angular/router';


@Component({
    selector: 'app-add-edit-regional-month-wb-travel-time',
    templateUrl: './add-edit-month-wb-travel-time.component.html',
    styleUrls: ['./add-edit-month-wb-travel-time.component.css'],
})
export class AddEditMonthWbTravelTimeComponent implements OnInit{
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    formRegionMonthTravelTime: FormGroup;
    geoRegionList: any;
    unitTypeList: any;
    editId: string;
    mode: string = 'add';
    monthList: any = ['Jan', 'Feb' , 'Mar', 'Apr' , 'May' , 'Jun' , 'Jul' , 'Aug' , 'Sep' , 'Oct' , 'Nov' , 'Dec']
    constructor(public fb: FormBuilder, private actRoute: ActivatedRoute,public router: Router,
                private monthWbTravelTimeService: MonthWbTravelTimeService) {
    }
    ngOnInit(): void {
        this.getGeoRegion();
        this.getUnitTypeList();
        this.formRegionMonthTravelTime = this.fb.group({
            id: [''],
            regionID : ['', Validators.required],
            month : ['', Validators.required],
            unitType : ['', Validators.required]
        })
        this.editId = this.actRoute.snapshot.paramMap.get('id');
        if (this.editId) {
            this.mode = "edit";
            this.monthWbTravelTimeService.getRegionalMonthlyTravelTimeById(this.editId).subscribe(data => {
                this.formRegionMonthTravelTime.patchValue(data);
            })
        }
    }

    getGeoRegion(){
        return this.monthWbTravelTimeService.getGeoRegion()
            .subscribe(data =>{
                this.geoRegionList = data;
            })
    }
getUnitTypeList() {
        return this.monthWbTravelTimeService.getUnitTypeList()
            .subscribe(data => {
                this.unitTypeList = data;
            })
}
    submitForm(){
        for (const controller in this.formRegionMonthTravelTime.controls) {
            this.formRegionMonthTravelTime.get(controller).markAsTouched();
        }
        if (this.formRegionMonthTravelTime.invalid) {
            return;
        }

        let observable;

        if (this.mode == 'add') {
            observable = this.monthWbTravelTimeService.storeRegionalMonthlyTravelTime(this.formRegionMonthTravelTime.value);
        } else {
            observable = this.monthWbTravelTimeService.updateRegionalMonthlyTravelTime(this.formRegionMonthTravelTime.value.id, this.formRegionMonthTravelTime.value);
        }

        observable.subscribe(res => {
            if (res) {
                // this.isSuccess = res.success;
                if (res.success) {
                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            }
        });
    }

    modalSuccess($event: any) {
        this.router.navigate(['/regional/monthly-wb-travel-time']);
    }
}

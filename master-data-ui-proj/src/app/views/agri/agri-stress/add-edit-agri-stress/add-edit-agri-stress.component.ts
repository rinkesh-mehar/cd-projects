import { AgriStressTypeService } from './../../services/agri-stress-type.service';
import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AgriStressServiceService} from '../../services/agri-stress-service.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
    selector: 'app-add-edit-agri-stress',
    templateUrl: './add-edit-agri-stress.component.html',
    styleUrls: ['./add-edit-agri-stress.component.scss']
})
export class AddEditAgriStressComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    agriStressFormGroup: FormGroup;

    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    _statusMsg: string;
    editId: string;
    mode: string = 'add';
    status: 'Inactive';
    stressTypeList: any = [];

    constructor(private formBuilder: FormBuilder,
                private router: Router,
                private agriStressServiceService: AgriStressServiceService,
                private actRoute: ActivatedRoute,
                private agriStressTypeService: AgriStressTypeService
    ) {
    }

    ngOnInit(): void {

        this.getStressTypeList();

        this.agriStressFormGroup = this.formBuilder.group({
            id: [],
            name: ['', Validators.required],
            stressTypeId: ['', Validators.required],
            status: ['Inactive']

        });

        this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
            this.mode = 'edit';
            this.agriStressServiceService.GetAgriStressByID(this.editId).subscribe(data => {
                this.agriStressFormGroup.patchValue(data);
            })
        }
    }
    trimValue(formControl) { 
        formControl.setValue(formControl.value.trim()); 
      }
    public stressError = (controlName: string, errorName: string) => {
        return this.agriStressFormGroup.controls[controlName].hasError(errorName);
    };

    submitDoseFactorForm() {

        for (let controller in this.agriStressFormGroup.controls) {
            this.agriStressFormGroup.get(controller).markAsTouched();
        }
        if (this.agriStressFormGroup.invalid) {
            return;
        }
        if (this.mode == 'add') {
            this.add();
        } else {
            this.update();
        }
    }

    add() {
        this.agriStressServiceService.CreateAgriStress(this.agriStressFormGroup.value).subscribe(res => {
            this.isSubmitted = true;
            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                    this._statusMsg = res.message;
                    // this.doseFactorForm.reset();
                    this.mode = 'add';
                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            }
        });
    }

    update() {
        this.agriStressServiceService.UpdateAgriStress(this.agriStressFormGroup.value.id, this.agriStressFormGroup.value).subscribe(res => {
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

    modalSuccess($event: any) {
        this.router.navigate(['/stress/stress']);
    }

    getStressTypeList(){
        return this.agriStressTypeService.GetAllStressType().subscribe(data => {
         this.stressTypeList = data;
        });
      }
      

}

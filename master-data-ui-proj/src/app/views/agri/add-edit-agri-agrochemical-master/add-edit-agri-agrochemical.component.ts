import { AgriAgrochemicalTypeService } from './../services/agri-agrochemical-type.service';
import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ActivatedRoute, Router} from '@angular/router';
import {AgriAgrochemicalMasterService} from '../services/agri-agrochemical-master.service';

@Component({
    selector: 'app-add-edit-agri-agrochemical',
    templateUrl: './add-edit-agri-agrochemical.component.html',
    styleUrls: ['./add-edit-agri-agrochemical.component.scss']
})
export class AddEditAgriAgrochemicalComponent implements OnInit {

    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    agriAgrochemicalFormGroup: FormGroup;

    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    _statusMsg: string;
    editId: string;
    mode: string = 'add';
    status: 'Inactive';
    agrochemicalTypeList: any = [];

    constructor(private formBuilder: FormBuilder,
                private router: Router,
                private agriAgrochemicalMasterService: AgriAgrochemicalMasterService,
                private agriAgrochemicalTypeService: AgriAgrochemicalTypeService,
                private actRoute: ActivatedRoute,) {
    }

    ngOnInit(): void {
        this.addEditAgroChemical();
        this.GetAllAgrochemicalType();
        

        this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
            this.mode = 'edit';
            this.agriAgrochemicalMasterService.GetAgrochemicalMaster(this.editId).subscribe(data => {
                this.agriAgrochemicalFormGroup.patchValue(data);
            });
        }
    }

    addEditAgroChemical()
    {
        this.agriAgrochemicalFormGroup = this.formBuilder.group({
            id: [],
            name: ['', Validators.required],
            agrochemicalTypeID: ['', Validators.required],
            status: ['Inactive']
        });
     }

    trimValue(formControl) { 
        formControl.setValue(formControl.value.trim()); 
      }
      

    public stressError = (controlName: string, errorName: string) => {
        return this.agriAgrochemicalFormGroup.controls[controlName].hasError(errorName);
    };

    submitAgrochemicalForm() {

        for (let controller in this.agriAgrochemicalFormGroup.controls) {
            this.agriAgrochemicalFormGroup.get(controller).markAsTouched();
        }
        if (this.agriAgrochemicalFormGroup.invalid) {
            return;
        }
        if (this.mode == 'add') {
            this.add();
        } else {
            this.update();
        }
    }

    add() {
        this.agriAgrochemicalMasterService.CreateAgrochemicalMaster(this.agriAgrochemicalFormGroup.value).subscribe(res => {
            this.isSubmitted = true;
            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                    this._statusMsg = res.message;
                    // this.doseFactorForm.reset();
                    this.mode = 'add';
                    this.addEditAgroChemical();
                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            }
        });
    }

    update() {
        this.agriAgrochemicalMasterService.UpdateAgrochemicalMaster(this.agriAgrochemicalFormGroup.value.id, this.agriAgrochemicalFormGroup.value).subscribe(res => {
            this.isSubmitted = true;
            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                    this.addEditAgroChemical();
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
        this.router.navigate(['/agrochemicals/agrochemical']);
    }
    
    GetAllAgrochemicalType() {
        return this.agriAgrochemicalTypeService.GetAllAgrochemicalType().subscribe((data: {}) => {
          this.agrochemicalTypeList = data;
        })
      }

}

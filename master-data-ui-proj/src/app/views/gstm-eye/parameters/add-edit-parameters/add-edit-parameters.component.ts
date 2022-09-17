import {Component, OnInit, ViewChild} from '@angular/core';
import {GstmEyeService} from '../../services/gstm-eye.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';

@Component({
    selector: 'app-add-edit-parameters',
    templateUrl: './add-edit-parameters.component.html',
    styleUrls: ['./add-edit-parameters.component.scss']
})
export class AddEditParametersComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    parameterList: any = [];
    platformList: any = [];
    dataTypeList: any = [];
    categoryList: any = [];
    subCategoryList: any = [];
    isSuccess: boolean = false;
    isSubmitted: boolean = false;
    parameterFrom: FormGroup;
    mode: string = 'add';
    editId: string;


    constructor(
        public formBuilder: FormBuilder,
        private gstmEyeService: GstmEyeService,
        public router: Router, public http: HttpClient,
        private actRoute: ActivatedRoute) {
    }

    ngOnInit(): void {

        this.loadAllPlatform();

        this.parameterFrom = this.formBuilder.group({
            parameterName: ['', Validators.required],
            platformId: ['', Validators.required],
            dataTypeId: ['', Validators.required],
            categoryId: ['', Validators.required],
            subCategoryId: ['', Validators.required],
            status: ['']
        });

        this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
            this.mode = 'edit';

            this.gstmEyeService.getParameterById(this.editId).subscribe(data => {

                this.parameterFrom.patchValue(data);
                this.loadAllDataTypeByPlatform();
                this.loadAllCategory();
                this.loadAllSubcategory();

            });
        }
    }
    trimValue(formControl) { 
        formControl.setValue(formControl.value.trim()); 
      }
      
      
    submitParameterForm() {

        for (const controller in this.parameterFrom.controls) {
            this.parameterFrom.get(controller).markAsTouched();
        }

        if (this.parameterFrom.invalid) {
            return;
        }

        if (this.mode === 'edit') {
            this.editParameter();
        } else {
            this.addParameters();
        }
    }

    addParameters() {

        const data = {
            'parameter': this.parameterFrom.value.parameterName,
            'platform': this.parameterFrom.value.platformId,
            'dataType': this.parameterFrom.value.dataTypeId,
            'category': this.parameterFrom.value.categoryId,
            'subCategory': this.parameterFrom.value.subCategoryId
        };

        this.gstmEyeService.addParameters(data).subscribe(res => {
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

    editParameter() {

        const data = {
            'parameter': this.parameterFrom.value.parameterName,
            'platform': this.parameterFrom.value.platformId,
            'dataType': this.parameterFrom.value.dataTypeId,
            'category': this.parameterFrom.value.categoryId,
            'subCategory': this.parameterFrom.value.subCategoryId,
            'status': this.parameterFrom.value.status
        };

        return this.gstmEyeService.updateParameter(this.editId, data).subscribe(res => {
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


    loadAllPlatform() {
        return this.gstmEyeService.getPlatform().subscribe((data) => {

            this.platformList = data;
            console.log('platform are -> ', this.parameterList);
        });
    }

    loadAllDataTypeByPlatform() {
        return this.gstmEyeService.getDataType(this.parameterFrom.value.platformId).subscribe((data) => {

            this.dataTypeList = data;
            console.log('dataTypes  are -> ', this.dataTypeList);
        });
    }

    loadAllCategory() {
        return this.gstmEyeService.getCategory(this.parameterFrom.value.dataTypeId).subscribe((data) => {

            this.categoryList = data;
            console.log('categories are -> ', this.categoryList);
        });
    }

    loadAllSubcategory() {
        return this.gstmEyeService.getSubcategory(this.parameterFrom.value.categoryId).subscribe((data) => {

            this.subCategoryList = data;
            console.log('subcategories are -> ', this.subCategoryList);
        });
    }

    // loadAllPlatform(parameterId) {
    //     alert(this.parameterFrom.value.parameterId);
    // }

    modalSuccess($event: any) {
        this.router.navigate(['/gstm-eye/parameters']);
    }

}

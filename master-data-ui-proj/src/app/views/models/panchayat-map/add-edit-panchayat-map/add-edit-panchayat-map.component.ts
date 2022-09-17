import {Component, OnInit, Renderer2, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {ApiUtilService} from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {PanchayatMapService} from '../../services/panchayat-map.service';

@Component({
    selector: 'app-add-panchayat-map-models',
    templateUrl: './add-edit-panchayat-map.component.html',
    styleUrls: ['./add-edit-panchayat-map.component.css']
})
export class AddEditPanchayatMapComponent implements OnInit{
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;
    panchayatMapForm: FormGroup;
    fileUpload: any;
    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    isSubmittedBulk: boolean = false;
    isSuccessBulk: boolean = false;

    constructor(private formGroup: FormBuilder,
                private router: Router,
                public apiUtilService: ApiUtilService,
                public panchayatMapService: PanchayatMapService,
                private renderer: Renderer2) {
    }
    ngOnInit(): void {
        this.addPanchayat();
    }

    addPanchayat() {
        this.panchayatMapForm = this.formGroup.group({
            regionID: ['', Validators.required],
            subRegionID: ['', Validators.required],
            villageCode: ['', Validators.required],
            status: ['Inactive']
        });
    }

    submitForm(){
        for (let controller in this.panchayatMapForm.controls){
            this.panchayatMapForm.get(controller).markAllAsTouched();
        }
        if (this.panchayatMapForm.invalid){
            return;
        }
        this.panchayatMapService.addPanchayat(this.panchayatMapForm.value).subscribe(res=>{
            this.isSubmitted = true;
            if (res){
                this.isSuccess = res.success;
                if (res.success) {
                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            }
        });
    }

    submitExcelForm() {
        if (this.fileUpload == undefined || this.fileUpload == ''){
            this.errorModal.showModal('ERROR', 'Please upload file', '');
            return;
        }
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
    downloadExcelFormat() {
        const tableName = 'regional_panchayat_map';
        this.apiUtilService.downloadExcelFormat(tableName);
    }//downloadExcelFormat

    uploadExcel(element) {
        this.fileUpload = element.target.files[0];
    }

    async delay(ms: number) {
        await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
    }
    modalSuccess($event: any) {
        this.router.navigate(['/models/panchayat-map-list']);
    }
}
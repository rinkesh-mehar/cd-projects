import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CdtModulesService} from '../services/cdt-modules.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {ActivatedRoute, Router} from '@angular/router';
declare var $: any;

@Component({
    selector: 'app-add-manage',
    templateUrl: './add-manage.component.html',
    styleUrls: ['./add-manage.component.css']
})
export class AddManageComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;
    @ViewChild('closebutton') closebutton;
    modelsList: any = [];
    modelTemplate: any;
    isSubmitted: boolean = false;
    manageForm: FormGroup;
    csvFile: File;
    editId: any;
    mode: string = 'add';
    isSuccess: boolean = false;
    fileUrl: string;
    showDownload: boolean = false;

    constructor(public formBuilder: FormBuilder, public moduleService: CdtModulesService,
                private router: Router, private actRoute: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.modelTemplate = undefined;
        this.getModels();
        this.manageForm = this.formBuilder.group({
            modelId: ['', Validators.required],
            csvFile: ['', Validators.required],
            // status: ['', Validators.required]
        });
        this.editId = this.actRoute.snapshot.paramMap.get('id');
        if (this.editId){
            this.mode = 'edit';
            this.moduleService.getRecordById(this.editId).subscribe(data => {
                this.fileUrl = data.fileUrl;
                this.manageForm.patchValue(data);
            });
        }
    }

    getModels() {
        return this.moduleService.getModels().subscribe((data: {}) => {
            this.modelsList = Array.from(data['Models']);
        });
    }

    /*Upload CSV file*/
    csvFileChange(element) {
        this.csvFile = element.target.files[0];
        this.showDownload = true;

    }

    submitForm() {
        for (const controller in this.manageForm.controls) {
            this.manageForm.get(controller).markAllAsTouched();
        }
        if (this.manageForm.invalid) {
            return;
        }

        if (this.mode == 'add') {
            this.add();
        } else {
            this.update();
        }
    }

    add(){
        const data = {
            modelId: this.manageForm.value.modelId,
            // status: this.manageForm.value.status
        };
        return this.moduleService.addManage(this.csvFile, data).subscribe(res => {
            this.isSubmitted = true;
            if (res.success) {
                this.successModal.showModal('SUCCESS', res.message, '');
            } else {
                this.errorModal.showModal('ERROR', res.error, '');
            }
        });
    }

    update(){
        const data = {
            modelId: this.manageForm.value.modelId,
            id : this.editId
            // status: this.manageForm.value.status
        };
        return this.moduleService.updateRecord(this.editId, data, this.csvFile).subscribe(res => {
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

    getModelTemplate(event){

        this.moduleService.getModelTemplate(event.target.value).subscribe(data => {
            this.modelTemplate = data.modelTemplates;
        });
    }
    modalSuccess($event: any) {
        this.router.navigate(['/models/manage']);

    }

    hide() {

        this.closebutton.nativeElement.click();
        // this.hideModal.emit(this.manageForm);
    }
}

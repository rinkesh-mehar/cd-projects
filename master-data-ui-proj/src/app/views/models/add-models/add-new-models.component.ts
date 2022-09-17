import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CdtModulesService} from '../services/cdt-modules.service';
import {ActivatedRoute, Router} from '@angular/router';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
    selector: 'app-add-models',
    templateUrl: './add-new-models.component.html',
    styleUrls: ['./add-new-models.component.css']
})

export class AddNewModelsComponent implements OnInit{
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;
    @ViewChild('closebutton') closebutton;
    modelForm: FormGroup;
    csvFile: File;
    mode: string = 'add';
    isSubmitted: boolean = false;
    editId: any;
    isSuccess: boolean = false;

    constructor(public formBuilder: FormBuilder, public moduleService: CdtModulesService,
                private router: Router, private actRoute: ActivatedRoute) {
    }
    ngOnInit(): void {
        this.modelForm = this.formBuilder.group({
            modelName: ['', Validators.required],
            modelDescription: ['', Validators.required],
            csvFile: ['']
        });
    }
    trimValue(formControl) { 
        formControl.setValue(formControl.value.trim()); 
      }
      
      
      
    submitForm() {
        for (const controller in this.modelForm.controls) {
            this.modelForm.get(controller).markAllAsTouched();
        }
        if (this.modelForm.invalid) {
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
            modelName: this.modelForm.value.modelName,
            modelDescription: this.modelForm.value.modelDescription
        };
        return this.moduleService.addModel(this.csvFile, data).subscribe(res => {
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
            // modelId: this.manageForm.value.modelId,
            // id : this.editId
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
    csvFileChange(element) {
        this.csvFile = element.target.files[0];
        // this.showDownload = true;

    }
    modalSuccess($event: any) {
        this.router.navigate(['/models/List']);

    }
}
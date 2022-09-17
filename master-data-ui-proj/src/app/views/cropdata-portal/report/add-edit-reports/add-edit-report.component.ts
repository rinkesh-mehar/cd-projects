import {Component, OnInit, ViewChild} from '@angular/core';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {newsReportsService} from '../../services/news-reports.service';
import { urlValidator } from '../../../validators/urlValidator.validator';
import { fileSizeValidatorForDoc } from '../../../validators/fileSizeValidator.validator';

@Component({
    selector: 'app-add-report',
    templateUrl: './add-edit-report.component.html',
    styleUrls: ['./add-edit-report.component.css']
})
export class AddEditReportComponent implements OnInit {

    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    addReport: FormGroup;
    platFormList: any =[];
    mode: string = 'add';
    editId: string;
    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    uploadedFile: any;
    isAuthenticate: boolean = true;
    isFileUrlDisabled: boolean = false;
    constructor(public formBuilder: FormBuilder, private actRoute: ActivatedRoute,
                public newsService: newsReportsService,  public router: Router) {
    }
    ngOnInit(): void {

        this.getPlatForm();

        this.addReport = this.formBuilder.group({
            platformID: ['', Validators.required],
            setAsAuthenticate: [false],
            title: ['', Validators.required],
            reportFile: [''],
            fileUrl: ['', [Validators.required, urlValidator]]
        });
        this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
            this.mode = 'edit';
            this.newsService.getReportById(this.editId).subscribe(data => {
                this.addReport.patchValue(data);
                if (data.authenticate == 'Yes'){
                    this.addReport.patchValue({setAsAuthenticate: true});
                    // this.isAuthenticate = false;
                    // this.addReport.get('fileUrl').clearValidators();
                    // this.addReport.get('reportFile').setValidators([Validators.required]);
                    // this.addReport.get('fileUrl').updateValueAndValidity();
                    // this.addReport.get('reportFile').updateValueAndValidity();
                }else{
                    this.addReport.patchValue({setAsAuthenticate: false});
                    // this.isAuthenticate = true;
                    // this.addReport.get('fileUrl').setValidators([Validators.required]);
                    // this.addReport.get('reportFile').clearValidators();
                    // this.addReport.get('fileUrl').updateValueAndValidity();
                    // this.addReport.get('reportFile').updateValueAndValidity();
                }
                // alert(data.fileUrl);
                // alert(data.fileUrl.includes('cropdata-portal/report'));
                
                // if(data.fileUrl.includes('cropdata-portal/report')){
                //     this.isFileUrlDisabled = true;
                // }
            });
            console.log('id ' + this.editId);
        }
    }
    get f() { return this.addReport.controls; }

    submitForm() {
        for (const controller in this.addReport.controls) {
            this.addReport.get(controller).markAllAsTouched();
        } if (this.addReport.invalid){
            return;
        }

        if (this.mode == 'add') {
            this.add();
        } else {
            this.update();
        }
    }

    add() {
        return this.newsService.createReport(this.addReport.value, this.uploadedFile).subscribe( res => {
            this.isSubmitted = true;
            if (res.success) {
                this.successModal.showModal('SUCCESS', res.message, '');
            } else {
                this.errorModal.showModal('ERROR', res.error, '');
            }
        });
    }

    update() {

        return this.newsService.updateReport(this.editId, this.addReport.value, this.uploadedFile).subscribe( res => {
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

    getPlatForm() {
        return this.newsService.getPlatFormList().subscribe((data) => {
            for(let platform of data){
            if(platform.platformId === 5){
            }else{
                this.platFormList.push(platform);
            }
            }
        });
    }

    onsetAsAuthenticateChange(){
        console.log("isAuhenticate : " + this.addReport.value.setAsAuthenticate);
        if(this.addReport.value.setAsAuthenticate){
        this.isAuthenticate = false;
        this.addReport.get('fileUrl').clearValidators();
        this.addReport.get('reportFile').setValidators([Validators.required]);
        this.addReport.get('fileUrl').updateValueAndValidity();
        this.addReport.get('reportFile').updateValueAndValidity();
        }else{
        this.isAuthenticate = true;
        this.addReport.get('fileUrl').setValidators([Validators.required]);
        this.addReport.get('reportFile').clearValidators();
        this.addReport.get('fileUrl').updateValueAndValidity();
        this.addReport.get('reportFile').updateValueAndValidity();
        }
    }

    onFileChange(element){
        let file: File = element.target.files[0];
        // console.log("Size : ", this.uploadedFile.size);
        this.addReport.get('fileUrl').clearValidators();
        this.addReport.get('fileUrl').updateValueAndValidity();
        this.addReport.get('reportFile').setValidators([Validators.required, fileSizeValidatorForDoc(element.target.files)]);
        this.addReport.get('reportFile').updateValueAndValidity();
        this.isAuthenticate = false;
        this.uploadedFile = file;
        // console.log('image url--->', this.imageFile);
        // this.image = this.imageFile.name;
        // this.addReport.setValue({reportFile: element.target.value});
        // this.closebutton.nativeElement.click();
    }

    modalSuccess($event: any) {
        this.router.navigate(['/cropdata-portal/reports']);
    }

}

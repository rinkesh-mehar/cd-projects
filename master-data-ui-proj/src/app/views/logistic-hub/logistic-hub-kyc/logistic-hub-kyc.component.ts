import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup, NgForm, ValidationErrors, ValidatorFn, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { ErrorModalComponent } from "../../global/error-modal/error-modal.component";
import { SuccessModalComponent } from "../../global/success-modal/success-modal.component";
import { LogisticHubDocUploadService } from "../service/docupload.service";

@Component({
    selector: 'app-logistic-hub',
    templateUrl: './logistic-hub-kyc.component.html'
})
export class LogisticHubKycComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;
    hubkyc: FormGroup;
    fileToUpload: File = null;
    wareHouseId: any;


    constructor(private fb: FormBuilder,
        private docService: LogisticHubDocUploadService,
        private activatedRoute: ActivatedRoute, private router: Router) {
        this.wareHouseId = this.activatedRoute.snapshot.paramMap.get("id");

        // console.log(this.wareHouseId);
       
    }

    ngOnInit() {

        this.hubkyc = this.fb.group({
            WMS_072: ['', Validators.required],
            WMS_059: [Validators.required],
            WMS_073: ['',Validators.required],
            WMS_061: [Validators.required],
            WMS_026: ['', Validators.required],
            WMS_070: [Validators.required],
            WMS_027: [Validators.required],
            WMS_099: [Validators.required]
        });
       /// window.location.reload();
    }

    trimValue(formControl) { 
        formControl.setValue(formControl.value.trim()); 
      }
      
      
    uploadFiles(files: FileList, docType) {
        // console.log(files);
        this.fileToUpload = files.item(0);
        //   this.validateImageSize(this.fileToUpload);
        if (this.fileToUpload.size / 1024 <= 2048) {
            if (this.fileToUpload) {
                this.docService.uploadHubDocs(this.fileToUpload,
                    docType, this.wareHouseId)
                    .subscribe(data => {
                        console.log(data);
                        if (data.success) {
                            // this.router.navigate(['/logistic-hub/lh-shortlisted-kyc']);
                            this.successModal.showModal('SUCCESS', data.message, '');
                        } else {
                            this.errorModal.showModal('Failed', data.error, '');
                        }
                    }, error => {
                        console.log(error);
                        //  this.errorModal.showModal('Failed', 'File Format Not Supported!!', '');
                    });
            }
        } else {
            this.errorModal.showModal('ERROR', 'File size is more than 2MB', '');

        }

    }


    submitKyc(f: any) {
        console.log(f);
        this.docService.saveHubData(f.value, this.wareHouseId)
            .subscribe(data => {
                console.log(data);
                if (data.success) {
                    this.successModal.showModal('SUCCESS', data.message, '');
                    this.sendToDashboard();
                } else {
                    this.errorModal.showModal('Failed', data.message, '');
                }
            }, error => {
                console.log(error);
            });
    }


    sendToDashboard() {
        setTimeout(() => {
            // console.log("Hello from setTimeout");
            this.router.navigate(['/logistic-hub/lh-shortlisted-kyc']);
        }, 2000);

    }


    


}
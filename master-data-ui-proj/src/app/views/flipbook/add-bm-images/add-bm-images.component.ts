import {Component, OnInit, ViewChild} from '@angular/core';
import {ApiUtilService} from '../../services/api-util.service';
import {FlipbookServiceService} from '../service/flipbook-service.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {Router} from '@angular/router';

@Component({
    selector: 'app-add-bm-images',
    templateUrl: './add-bm-images.component.html',
    styleUrls: ['./add-bm-images.component.scss']
})

export class AddBmImagesComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    isSubmittedBulk: boolean = false;
    isSuccessBulk: boolean = false;
    fileUpload: any;
    zipFile: any;
    checkZipFlag: number = 0;

    constructor(private flipbookService: FlipbookServiceService, private router: Router) {
    }

    ngOnInit(): void {
    }

    getZip(element) {
        const extensionSet = new Set();
        this.checkZipFlag = 0;

        console.log(element.target.files[0]);

        this.zipFile = element.target.files[0];
        const zipFileType = this.zipFile.type;

        extensionSet.add('application/zip');
        extensionSet.add('application/x-zip-compressed');

        extensionSet.forEach(ext => {
            console.log('ext is ->' , ext);
            if (ext === zipFileType) {
                this.checkZipFlag = 1;
                return;
            }
        });

        if (this.checkZipFlag === 0) {
            this.errorModal.showModal('ERROR', 'Please Select Zip File', '');
            this.zipFile = null;
        }
        console.log('file is ', this.zipFile);
    }

    uploadZip() {

        if (this.checkZipFlag === 0 || this.zipFile === null) {
            this.errorModal.showModal('ERROR', 'Please Select Zip File', '');
        } else {
            this.flipbookService.uploadZipFile(this.zipFile).subscribe(res => {
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
    }

    modalSuccess($event: any) {
        this.router.navigate(['/flipbook']);
    }
}

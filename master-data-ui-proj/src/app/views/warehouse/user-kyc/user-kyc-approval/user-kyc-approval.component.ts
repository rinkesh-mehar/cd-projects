import {Component, OnInit} from '@angular/core';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {UserKycService} from '../../service/user-kyc.service';
import {environment} from '../../../../../environments/environment';

@Component({
    selector: 'app-user-kyc-approval',
    templateUrl: './user-kyc-approval.component.html',
    styleUrls: ['./user-kyc-approval.component.scss']
})
export class UserKycApprovalComponent implements OnInit {
    userKycForm: FormGroup;
    mode: any;
    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    _statusMsg: string;

    userId: string;
    userDetails: any = {};
    userKycImgUrl: any;

    constructor(public formBuilder: FormBuilder, public userKycService: UserKycService,
                private actRoute: ActivatedRoute) {
        this.userKycImgUrl = environment.userKycImgUrl;
    }

    getChanges() {
        console.log(this.userKycForm.value);
    }

    ngOnInit() {
        this.userKycForm = this.formBuilder.group({
            id: [],
            firstName: [],
            lastName: [],
            user: [],
            addressLine1: [],
            addressLine2: [],
            city: [],
            tehsil: [],
            district: [],
            state: [],
            primaryNumber: [],
            secondaryNumber: [],
            kycStatus: ['', Validators.required],
            // status: ['Inactive']
        });

        this.userId = this.actRoute.snapshot.paramMap.get('id');
        if (this.userId) {
            this.mode = 'edit';
            this.userKycService.getUser(this.userId).subscribe(data => {
                this.userKycForm.patchValue(data.userData);
                console.log(this.userKycForm.value);

                this.userDetails = data.userData;
                this.userDetails.imageUrl = data.imageUrl;
            });
        }
    }

    submitForm() {
        for (const controller in this.userKycForm.controls) {
            this.userKycForm.get(controller).markAsTouched();
        }
        if (this.userKycForm.invalid) {
            return;
        }
        const formdata = this.userDetails;
        formdata.kycStatus = this.userKycForm.value.kycStatus;
        const observalble = this.userKycService.updateKycStatus(formdata);

        observalble.subscribe((res: any) => {
            this.isSubmitted = true;
            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                    this._statusMsg = res.message;
                    this.userKycForm.reset();
                    setTimeout(() => {
                        this.isSubmitted = false;
                        this.isSuccess = false;
                    }, 3000);

                } else {
                    this._statusMsg = res.error;
                }
            }
        });
    }
}

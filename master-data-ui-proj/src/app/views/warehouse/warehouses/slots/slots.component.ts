import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserKycService} from '../../service/user-kyc.service';
import {ActivatedRoute, Router} from '@angular/router';
import {environment} from '../../../../../environments/environment';
import {globalConstants} from '../../../global/globalConstants';
import {ConfirmationMadalComponent} from '../../../global/confirmation-madal/confirmation-madal.component';
import {WarehouseService} from '../../service/warehouse.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {isBoolean} from 'ngx-bootstrap/chronos/utils/type-checks';

@Component({
    selector: 'app-slots',
    templateUrl: './slots.component.html',
    styleUrls: ['./slots.component.scss']
})
export class SlotsComponent implements OnInit {
    @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
    @ViewChild('successModal') public successModal: SuccessModalComponent;

    userKycForm: FormGroup;
    mode: any;
    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    _statusMsg: string;

    listSlots: any;
    warehouseId: string;
    userDetails: any = {};
    userKycImgUrl: any;
    isSelected: boolean;

    constructor(public formBuilder: FormBuilder, public userKycService: UserKycService,
                private actRoute: ActivatedRoute,
                private router: Router) {
        this.userKycImgUrl = environment.userKycImgUrl;
    }

    getChanges() {
        console.log(this.userKycForm.value);
    }

    ngOnInit() {
        this.userKycForm = this.formBuilder.group({

            kycStatus: ['', Validators.required],
            responseStatus: ['', Validators.required]
            // status: ['Inactive']
        });

        this.warehouseId = this.actRoute.snapshot.paramMap.get('id');
        if (this.warehouseId) {
            this.mode = 'edit';
            this.userKycService.getSlot(this.warehouseId).subscribe(data => {
                this.listSlots = data.data;

            });
        }
        {
            return;
        }
    }

    submit(slot) {
        let flag = false;
        for (const list of this.listSlots) {
            if (list.kycStatus === 'Rejected' || list.kycStatus === 'Approved') {
                flag = true;
            } else {
                flag = false;
                // alert('please selecte another status');
                this.confirmModal.showModal('Error', 'please select another status', '');

            }
            if (!(list.kycStatus === 'Rejected')) {
                list.statusResponse = null;
            }
        }
        if (flag) {
            this.userKycService.updateSlotKycStatus(slot).subscribe(res => {
                this.isSubmitted = true;
                this.successModal.showModal('Success', 'Successfully Updated slots', '');
            });
        }
    }

    modalSuccess(event) {
        this.router.navigate(['/warehouse/warehouse']);
    }
}

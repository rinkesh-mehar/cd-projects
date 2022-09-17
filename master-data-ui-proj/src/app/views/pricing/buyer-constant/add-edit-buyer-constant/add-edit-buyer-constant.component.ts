import {Component, OnInit, ViewChild} from '@angular/core';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {PricingMspGroupService} from '../../services/pricing-msp-group.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
    selector: 'app-add-edit-buyer-constant',
    templateUrl: './add-edit-buyer-constant.component.html',
    styleUrls: ['./add-edit-buyer-constant.component.scss']
})
export class AddEditBuyerConstantComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    buyerConstantForm: FormGroup;
    editId: string;
    mode: string = 'add';
    geoRegionList: any;
    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    statusMsg: string;
    buyerConstantList: string;

    constructor(private pricingMspGroupService: PricingMspGroupService,
                private fb: FormBuilder,
                private actRoute: ActivatedRoute,
                private router: Router) {
    }

    ngOnInit(): void {
        this.getBuyerConstant();

        this.buyerConstantForm = this.fb.group({
            id: [],
            slopeMin: ['', Validators.required],
            slopeMax: ['', Validators.required],
            buyerConstant: ['', Validators.required],
            status: ['Inactive']
        });
        this.editId = this.actRoute.snapshot.paramMap.get('id');
        if (this.editId) {
            this.mode = 'edit';
            this.pricingMspGroupService.getBuyerConstantById(this.editId).subscribe(data => {
                this.buyerConstantForm.patchValue(data);
            });
        }
    }

    getBuyerConstant() {
        this.pricingMspGroupService.getBuyerConstant()
            .subscribe(page => this.buyerConstantList = page);
    }

    submitForm() {
        for (const controller in this.buyerConstantForm.controls) {
            this.buyerConstantForm.get(controller).markAsTouched();
        }
        if (this.buyerConstantForm.invalid) {
            return;
        }
        if (this.mode === 'add') {
            this.add();
        } else {
            this.update();
        }
    }

    add() {
        this.pricingMspGroupService.addBuyerConstant(this.buyerConstantForm.value).subscribe(res => {
            this.isSubmitted = true;
            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                    this.statusMsg = res.message;
                    // this.callingStatusForm.reset();
                    this.mode = 'add';
                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            }
        });
    }

    update() {
        this.pricingMspGroupService.UpdateBuyerConstant(this.buyerConstantForm.value.id, this.buyerConstantForm.value).subscribe(res => {
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

    modalSuccess($event: any) {
        this.router.navigate(['/pricing/buyer-constant']);
    }


}

import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserRightsService} from '../../../services/user-rights.service';
import {LogisticHubDocServiceService} from '../service/logistic-hub-doc-service.service';
import {FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import {ConfirmationMadalComponent} from '../../../global/confirmation-madal/confirmation-madal.component';
import {globalConstants} from '../../../global/globalConstants';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {Sort} from '@angular/material';

@Component({
    selector: 'app-logistic-hub-doc-manage',
    templateUrl: './logistic-hub-doc-manage.component.html',
    styleUrls: ['./logistic-hub-doc-manage.component.scss']
})
export class LogisticHubDocManageComponent implements OnInit {
    @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;
    @ViewChild('rejectAllCloseButton') rejectAllCloseButton: any;
    @ViewChild('rejectCloseButton') rejectCloseButton: any;

    rejectionAllForm: FormGroup;
    rejectionForm: FormGroup;

    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    rejectApproveAll: boolean = false;
    _statusMsg: string;

    editId: any;
    mode: any;
    lhMetaDetails: any;
    imgUrl: any;
    rejectionReasonList: any;
    rejectMetaId: any;

    constructor(private actRoute: ActivatedRoute,
                private router: Router,
                private logisticHubDocServiceService: LogisticHubDocServiceService,
                private userRightsService: UserRightsService,
                public fb: FormBuilder
    ) {
    }

    ngOnInit(): void {
        this.rejectionAllValidation();
        this.rejectionValidation();

        this.getRejectionReason();

        this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
            this.mode = 'edit';
            this.logisticHubDocServiceService.getLHDetailsById(this.editId, 0).subscribe(data => {
                this.lhMetaDetails = data;
                console.log('meta details are ', this.lhMetaDetails);
            });
            console.log('id ' + this.editId);
        }

    }

    rejectionAllValidation() {
        this.rejectionAllForm = this.fb.group({
            rejectReasonId: ['', Validators.required],
        });
    }

    rejectionValidation() {
        this.rejectionForm = this.fb.group({
            rejectReasonId: ['', Validators.required],
        });
    }

    getRejectionReason() {
        this.logisticHubDocServiceService.getRejectionReason().subscribe(data => {
            this.rejectionReasonList = data;
            console.log('meta details are ', this.lhMetaDetails);
        });
    }

    submitRejectionAllForm() {
        this.rejectAll(this.rejectionAllForm.value.rejectReasonId);
        this.rejectAllCloseButton.nativeElement.click();

    }

    submitRejectionForm() {
        console.log(this.rejectionForm.controls);

        console.log('reject reason id : ' + this.rejectionAllForm.value.rejectReasonId);

        this.reject(this.rejectMetaId, this.rejectionForm.value.rejectReasonId);

        this.rejectCloseButton.nativeElement.click();
    }


    reject(data, rejectReasonId) {


        const dataReject = {
            'metaID': this.rejectMetaId,
            'rejectionReasonID': rejectReasonId,
            'flag': 'reject'
        };

        let metaLabel = '';

        for (const x of this.lhMetaDetails) {
            if (x.id === data) {
                metaLabel = x.metaLabel;
            }
        }
        this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + ' ' + metaLabel, dataReject);

    }

    rejectAll(rejectionId) {
        const metaIdList = [];

        for (const x of this.lhMetaDetails) {
            metaIdList.push(x.id);
        }

        const dataApproveAll = {
            'metaIdList': metaIdList,
            'id': rejectionId,
            'flag': 'rejectAll'
        };

        this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + ' ' + this.lhMetaDetails[0].warehouseName + '\'s Documents', dataApproveAll);
    }


    approveAll(data) {
        data.flag = 'approveAll';
        console.log('data to approve ', data);

        const metaIdList = [];

        for (const x of data) {
            metaIdList.push(x.id);
        }

        const dataApproveAll = {
            'warehouseId': data[0].wareHouseId,
            'metaIdList': metaIdList,
            'flag': 'approveAll'
        };

        this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + ' ' + this.lhMetaDetails[0].warehouseName + '\n'
           + '... The Final Approval Can Not Be Undo', dataApproveAll);
    }

    approve(data) {

        console.log('data to approve ', data);

        const dataApprove = {
            'metaId': data,
            'flag': 'approve'
        };

        let metaLabel = '';

        for (const x of this.lhMetaDetails) {
            if (x.id === data) {
                metaLabel = x.metaLabel;
            }
        }

        this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + ' ' + metaLabel, dataApprove);
    }


    rejectClick(data, i) {
        data.index = i;
        data.flag = 'reject';
        this.rejectMetaId = data.id;
    }

    modalConfirmation(event) {

        console.log(event);
        if (event) {
            this.isSubmitted = true;
            if (event.flag === 'approveAll') {
                this.approveAllKYCDocument(event);
            } else if (event.flag === 'reject') {
                this.rejectKYVDocument(event);
            } else if (event.flag === 'rejectAll') {
                this.rejectAllKYVDocument(event);
            } else if (event.flag === 'approve') {
                this.approveKYCDocument(event.metaId);
            }
        }
    }

    approveAllKYCDocument(event: any) {
        return this.logisticHubDocServiceService.approveAllKYCDocument(event).subscribe(res => {
            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                    this.rejectApproveAll = res.success;
                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            }
        }, err => {
            this._statusMsg = err.error;
        });
    }

    approveKYCDocument(event: any) {
        return this.logisticHubDocServiceService.approveKYCDocument(event).subscribe(res => {
            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            }
        }, err => {
            this._statusMsg = err.error;
        });
    }

    rejectAllKYVDocument(event: any) {
        return this.logisticHubDocServiceService.rejectAllKYVDocument(event).subscribe(res => {
            if (res) {
                this.rejectApproveAll = res.success;
                this.isSuccess = res.success;
                if (res.success) {
                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            }
        }, err => {
            this._statusMsg = err.error;
        });
    }

    rejectKYVDocument(event: any) {
        return this.logisticHubDocServiceService.rejectKYVDocument(event).subscribe(res => {
            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            }
        }, err => {
            this._statusMsg = err.error;
        });
    }


    getImageUrl(event: any) {
        this.imgUrl = event.target.src;
        console.log('image found...' + this.imgUrl);
    }

    modalSuccess($event: any) {
        if (this.rejectApproveAll) {
            this.router.navigate(['/logistic-hub/doc-listing']);
        } else {
            this.ngOnInit();
        }
    }

    sortData(sort: Sort) {
        const data = this.lhMetaDetails.slice();

        // if (!sort.active || sort.direction == '') {
        //     this.pagePosition.content = data;
        //     return;
        // }

        function compare(firstValue, secondValue, isAsc: boolean) {
            return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }

        this.lhMetaDetails = data.sort((firstValue, secondValue) => {
            const isAsc = sort.direction == 'asc';
            switch (sort.active) {
                case  globalConstants.DOCUMENT_NAME:
                    return compare(+firstValue.id, +secondValue.id, isAsc);
                case globalConstants.STATUS:
                    return compare(firstValue.status, secondValue.status, isAsc);
                default:
                    return 0;
            }
        });
    }
}

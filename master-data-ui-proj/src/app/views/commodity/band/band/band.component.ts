import {Component, OnInit, ViewChild} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {UserRightsService} from '../../../services/user-rights.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {ConfirmationMadalComponent} from '../../../global/confirmation-madal/confirmation-madal.component';
import {BandService} from '../../service/band.service';

@Component({
    selector: 'app-market-price',
    templateUrl: './band.component.html',
    styleUrls: ['./band.component.scss']
})

export class BandComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;
    @ViewChild('confirmModal', {static: false}) public confirmModal: ConfirmationMadalComponent;

    bandFrom: FormGroup;
    bandList: any;
    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    statusMsg: string;

    ngOnInit(): void {
        this.getBandList();

    }

    constructor(private bandService: BandService, private userRightsService: UserRightsService) {

    }

    getBandList() {
        return this.bandService.getBandList().subscribe((data: {}) => {
            this.bandList = data;
        });
    }

    deleteBand(id) {
        this.confirmModal.showModal('Delete Band ?', 'Are you sure want to Delete Band?', id);
    }

    modalConfirmation(id) {
        this.bandService.deleteBand(id).subscribe(res => {
            this.isSubmitted = true;
            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                    this.statusMsg = res.message;
                    window.scrollTo(0, 0);
                    this.successModal.showModal('SUCCESS', res.message, '');
                }
            }
        }, err => {
            this.isSubmitted = true;
            this.statusMsg = err.message;
            this.isSuccess = false;
        });
    }

    modalSuccess($event: any) {

    }
}

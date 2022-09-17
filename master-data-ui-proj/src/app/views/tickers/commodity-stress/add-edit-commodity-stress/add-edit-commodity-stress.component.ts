import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TickerService} from '../../services/ticker.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {BulkDataService} from '../../../agri/services/bulk-data.service';
import {ActivatedRoute, Router} from '@angular/router';

declare var $;

@Component({
    selector: 'app-commodity-stress',
    templateUrl: './add-edit-commodity-stress.component.html',
    styleUrls: ['./add-edit-commodity-stress.component.scss']
})
export class AddEditCommodityStressComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    commodityStressForm: FormGroup;
    commodityList: any;
    phenophaseList: any = [];
    tickerCommodityStressList: any = [];
    stressList: any;
    commodityId: number;
    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    editId: string;
    mode: string = 'add';
    stress: any;
    stressIDs: any;

    constructor(private actRoute: ActivatedRoute, public formBuilder: FormBuilder, public bulkDatas: BulkDataService, private tickerService: TickerService,
                public router: Router) {

    }

    ngOnInit() {
        this.loadAllCommodity();

        this.commodityStressForm = this.formBuilder.group({

            commodityId: ['', Validators.required],
            phenophaseId: ['', Validators.required],
            status: ['']

        });

        this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
            this.mode = 'edit';

            this.tickerService.getCommodityStressById(this.editId).subscribe(data => {

                this.commodityStressForm.patchValue(data);
                this.stressIDs = data.stressId.split(',');

                this.loadAllPhenophase(this.commodityStressForm.value.commodityId);

                this.commodityStressForm.patchValue({
                    phenophaseId: data.phenophaseId,
                });

                this.loadStressByCommodityAndPhenophase(this.commodityStressForm.value.phenophaseId);

            });

            console.log('in load all commodity', this.commodityStressForm.value);
            console.log('id ' + this.editId);
        }
    }

    loadAllPhenophase(commodityId) {

        return this.tickerService.getPhenophase(this.commodityStressForm.value.commodityId).subscribe(data => {
            this.commodityStressForm.patchValue(data);

            this.phenophaseList = data;
        });
    }

    submitCommodityStressForm() {

        for (const controller in this.commodityStressForm.controls) {
            this.commodityStressForm.get(controller).markAsTouched();
        }
        if (this.commodityStressForm.invalid) {
            return;
        }
        if (this.mode === 'edit') {
            this.updateCommodityStress(this.editId);
        } else {
            this.addCommodityStress();
        }
    }

    addCommodityStress() {
        console.log('list in add', this.tickerCommodityStressList);

        const data = {
            'commodityId': this.commodityStressForm.value.commodityId,
            'phenophaseId': this.commodityStressForm.value.phenophaseId,
            'tickerCommodityStressList': this.tickerCommodityStressList
        };


        this.tickerService.addCommodityStress(data).subscribe(res => {
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

    updateCommodityStress(editId) {
        const selectedCheckBox = [];
        $('.custom-checkbox input:checked').each(function (index) {
            selectedCheckBox.push($(this).val());
        });
        console.log('list in add', this.tickerCommodityStressList);

        const data = {
            'id': editId,
            'commodityId': this.commodityStressForm.value.commodityId,
            'phenophaseId': this.commodityStressForm.value.phenophaseId,
            'tickerCommodityStressList': selectedCheckBox,
            'status': this.commodityStressForm.value.status
        };


        this.tickerService.updateCommodityStress(editId, data).subscribe(res => {
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

    loadAllCommodity() {
        return this.tickerService.getAllCommodity().subscribe((data: {}) => {
            this.commodityList = data;
            this.stressIDs = this.commodityList.stressId.split(',');
        }, err => {
            alert(err);
        });
    }


    loadStressByCommodityAndPhenophase(phenophaseID) {
        console.log('phenophase id is ', this.commodityStressForm.value.phenophaseId);
        console.log('commodity id is ', this.commodityStressForm.value.commodityId);
        return this.tickerService.getStress(this.commodityStressForm.value.phenophaseId, this.commodityStressForm.value.commodityId).subscribe((data: any) => {
            this.stressList = data;

            console.log('stressList is', this.stressList);

        }, err => {
            alert(err);
        });
    }

    addCheckBox(event) {

        if (event.target.checked === true) {
            this.tickerCommodityStressList.push(event.target['value']);
        } else {
            const index = this.tickerCommodityStressList.indexOf(event.target['value']);
            this.tickerCommodityStressList.splice(index, 1);
        }

    }


    modalSuccess($event: any) {
        this.router.navigate(['/tickers/commodity-stress']);
    }
}

import {Component, OnInit, ViewChild} from '@angular/core';
import {globalConstants} from '../../global/globalConstants';
import {BulkDataService} from '../services/bulk-data.service';
import {UserRightsService} from '../../services/user-rights.service';
import {DrkServiceService} from '../../services/drk-service.service';
import {PageAgriStress} from '../models/PageAgriStress';
import {ConfirmationMadalComponent} from '../../global/confirmation-madal/confirmation-madal.component';
import {ImagePreviewModalComponent} from '../../global/image-preview-modal/image-preview-modal.component';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {Sort} from '@angular/material';
import {AgriStressServiceService} from '../services/agri-stress-service.service';

@Component({
    selector: 'app-agri-stress',
    templateUrl: './agri-stress.component.html',
    styleUrls: ['./agri-stress.component.scss']
})
export class AgriStressComponent implements OnInit {

    @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
    @ViewChild('imagePreviewModal') public imagePreviewModal: ImagePreviewModalComponent;
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    pageStress: PageAgriStress;
    selectedPage: number = 1;
    maxSize = 10;
    searchText: any = '';

    stressStatus;
    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    _statusMsg: string;

    recordsPerPage: number = 10;
    records: any = [];
 


    StressList: any = [];

    constructor(public bulkDatas: BulkDataService,
                public agriStressService: AgriStressServiceService,
                private drkServiceService: DrkServiceService,
                private userRightsService: UserRightsService,
) {
    }

    ngOnInit(): void {
        this.records = ['20', '50', '100', '200', '250'];
        this.getPageAgriStress(0);
        this.stressStatus = globalConstants;
    }

    // Stress list
    loadAllStress() {
        return this.agriStressService.GetAllStress().subscribe((data: {}) => {
            this.StressList = data;
        });
    }

    bulkData(key, tableName) {

        const Values = [];
        const getValue = document.querySelectorAll<HTMLInputElement>('table tbody input:checked');

        getValue.forEach(function (data, i) {
            Values.push(data.value);
        });
        const AllData = {status: key, tableName: tableName, ids: Values.toString()};

        this.bulkDatas.getData(AllData)
            .subscribe(data => {
                data;
                if (data.success == true) {
                    this.successModal.showModal('SUCCESS', data.message, '');

                } else {
                    this.errorModal.showModal('ERROR', data.error, '');

                }

            });

    }

    onSelect(page: number): void {
        (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
        this.getPageAgriStress(page);
    }

    getPageAgriStress(page: number): void {
        this.agriStressService.getPageAgriStress(page, this.recordsPerPage, this.searchText)
            .subscribe(page => this.pageStress = page);
    }

    loadData(event: any) {
        console.log('pages ', event.target.value);
        this.recordsPerPage = event.target.value || 10;
        this.agriStressService.getPageAgriStress(this.selectedPage - 1, this.recordsPerPage, this.searchText)
          .subscribe(page => this.pageStress = page);
      }

    searchStress() {
        this.selectedPage = 1;
        this.getPageAgriStress(this.selectedPage - 1);
    }

    // // Delete Stress
    // deleteStress(data){
    //   var index = index = this.StressList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriStressService.DeleteStress(data.id).subscribe(res => {
    //     this.StressList.splice(index, 1)
    //      console.log('Stress deleted!')
    //    })
    // }

    // Delete Commodity
    delete(data, i) {
        data.index = i;
        data.flag = 'delete';
        this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + ' ' + data.name, data);
    }

    // Reject
    reject(data, i) {
        data.index = i;
        data.flag = 'reject';
        this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + ' ' + data.name, data);
    }

    approve(data, i) {
        data.index = i;
        data.flag = 'approve';
        this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + ' ' + data.name, data);
    }

    finalize(data, i) {
        data.index = i;
        data.flag = 'finalize';
        this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + ' ' + data.name, data);
    }

    modalConfirmation(event) {
        console.log(event);
        let observable: any;
        if (event) {
            this.isSubmitted = true;
            if (event.flag == 'approve') {
                observable = this.agriStressService.ApproveStress(event.id);
            } else if (event.flag == 'finalize') {
                observable = this.agriStressService.FinalizeStress(event.id);
            } else if (event.flag == 'delete') {
                observable = this.agriStressService.DeleteStress(event.id);
            } else if (event.flag == 'reject') {
                observable = this.agriStressService.RejectStress(event.id);
            }
            observable.subscribe(res => {
                if (res) {
                    this.isSuccess = res.success;
                    if (res.success) {
                        this.successModal.showModal('SUCCESS', res.message, '');
                    } else {
                        this.errorModal.showModal('ERROR', res.error, '');
                    }
                }
            }, err => {
                this.errorModal.showModal('ERROR', err.error, '');
            });
        }
    }
    modalSuccess($event: any) {
        if (this.selectedPage > 0){

            this.onSelect((this.selectedPage - 1));
            (document.querySelector('thead th input') as HTMLInputElement).checked = false
        } else {
            this.onSelect(this.selectedPage);
            (document.querySelector('thead th input') as HTMLInputElement).checked = false
        }
    }

    sortData(sort: Sort) {
        const data = this.pageStress.content.slice();
        if (!sort.active || sort.direction == '') {
            this.pageStress.content = data;
            return;
        }

        function compare(firstValue, secondValue, isAsc: boolean) {
            return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }

        this.pageStress.content = data.sort((firstValue, secondValue) => {
            const isAsc = sort.direction == 'asc';
            switch (sort.active) {
                case globalConstants.NAME:
                    return compare(firstValue.name, secondValue.name, isAsc);
                case globalConstants.STATUS:
                    return compare(firstValue.status, secondValue.status, isAsc);
                default:
                    return 0;
            }
        });
    }


}

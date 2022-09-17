import {Component, OnInit, ViewChild} from '@angular/core';
import {WarehouseService} from '../service/warehouse.service';
import {UserRightsService} from '../../services/user-rights.service';
import {ConfirmationMadalComponent} from '../../global/confirmation-madal/confirmation-madal.component';
import {globalConstants} from '../../global/globalConstants';

@Component({
    selector: 'app-warehouses',
    templateUrl: './warehouses.component.html',
    styleUrls: ['./warehouses.component.scss']
})
export class WarehousesComponent implements OnInit {
    @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;


    warehouseList: any[];
    isSubmitted: boolean;
    isSuccess: any;
    _statusMsg: any;

    constructor(public warehouseService: WarehouseService,
                private userRightsService: UserRightsService) {
    }

    ngOnInit() {
        this.warehouseService.getWarehouseList().subscribe(data => {
            this.warehouseList = data.data;
        });
    }


    // Reject
    reject(data, i) {
        data.index = i;
        data.flag = 'Rejected';
        this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + ' ' + data.warehouseName, data);
    }

    approve(data, i) {
        data.index = i;
        data.flag = 'Approved';
        this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + ' ' + data.warehouseName, data);
    }


    modalConfirmation(event) {
        console.log(event);
        if (event) {
            this.isSubmitted = true;
        }
    }
}

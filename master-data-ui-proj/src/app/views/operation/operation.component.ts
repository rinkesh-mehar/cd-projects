import {Component, OnInit} from '@angular/core';
import {BandService} from '../commodity/service/band.service';
import {OperationService} from './operation.service';

@Component({
    selector: 'app-operation',
    templateUrl: './operation.component.html',
    styleUrls: ['./operation.component.scss']
})
export class OperationComponent implements OnInit {

    operationList: any;
    allCount: any;
    rightCount: any;
    totalCropAreaAcres: any;
    totalQuantityTons: any;
    totalValueLakhrupees: any;

    constructor(private operationService: OperationService) {

    }

    ngOnInit(): void {
        this.getOperationList();
        this.getSumCount();
    }

    getOperationList() {
        return this.operationService.getOperationList().subscribe((data: {}) => {
            this.operationList = data;
        });
    }

    getSumCount() {
        return this.operationService.getSumCount().subscribe((data: {}) => {
            this.allCount = data;
            console.log('this.allCount', this.allCount);
            this.rightCount = this.allCount[0].RightCount;
            this.totalCropAreaAcres = this.allCount[0].TotalCropAreaAcres;
            this.totalQuantityTons = this.allCount[0].TotalQuantityTons;
            this.totalValueLakhrupees = this.allCount[0].TotalValueLakhrupees;
        });
    }

}

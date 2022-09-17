import {Component, OnInit, ViewChild} from '@angular/core';
import {ConfirmationMadalComponent} from '../../global/confirmation-madal/confirmation-madal.component';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {RegionTerrainService} from '../services/region-terrain.service';
import {PageRegionTerrain} from '../models/PageTerrain';
import {Sort} from '@angular/material';
import {globalConstants} from '../../global/globalConstants';
import {UserRightsService} from '../../services/user-rights.service';
import {DrkServiceService} from '../../services/drk-service.service';
import { BulkDataService } from '../../agri/services/bulk-data.service';

@Component({
    selector: 'app-terrain-type',
    templateUrl: './terrain-type.component.html',
    styleUrls: ['./terrain-type.component.scss']
})
export class TerrainTypeComponent implements OnInit {

    @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    statusMsg: string;
    selectedPage: number=0;
    searchText: any = '';
    maxSize = 10;
    selectedItems: any = null;
    terrainStatus;

    terrainList: any = [];
    pageTerrain: PageRegionTerrain;
    isValid: number = 1;

    recordsPerPage: number = 10;
   records: any = [];

    constructor(public bulkDatas: BulkDataService,private regionTerrainService: RegionTerrainService,
                public userRightsService: UserRightsService,
                private drkServiceService: DrkServiceService) {
    }

    ngOnInit(): void {
        this.records = ['20', '50', '100', '200', '250'];
        this.getPageRegionTerrain(0, this.isValid);
        this.terrainStatus = globalConstants;
    }

    getPageRegionTerrain(page: number, isValid: number): void {
        this.regionTerrainService.getPageTerrain(page, this.recordsPerPage, this.searchText, isValid)
            .subscribe(page => this.pageTerrain = page);
    }

    loadData(event: any) {
        console.log('pages ', event.target.value);
        this.recordsPerPage = event.target.value || 10;
        this.regionTerrainService.getPageTerrain(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid)
          .subscribe(page => this.pageTerrain = page);
      }
    sortData(sort: Sort) {
        const data = this.pageTerrain.content.slice();
        if (!sort.active || sort.direction === '') {
            this.pageTerrain.content = data;
            return;
        }

        function compare(firstValue, secondValue, isAsc) {
            return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }

        this.pageTerrain.content = data.sort((firstValue, secondValue) => {
            const isAsc = sort.direction === 'asc';
            switch (sort.active) {
                case globalConstants.ID:
                    return compare(+firstValue.ID, +secondValue.ID, isAsc);
                case 'regionName':
                    return compare(firstValue.regionName, secondValue.regionName, isAsc);
                case 'terrainType':
                    return compare(firstValue.terrainType, secondValue.terrainType, isAsc);
                case 'minPerKm':
                    return compare(+firstValue.minPerKm, +secondValue.minPerKm, isAsc);
                case globalConstants.STATUS:
                    return compare(firstValue.status, secondValue.status, isAsc);
                default:
                    return 0;
            }
        });
    }


    searchSeason() {
        this.selectedPage = 1;
        console.log(this.searchText);
        this.getPageRegionTerrain(this.selectedPage - 1, this.isValid);
    }

    onSelect(page: number): void {
        (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
        console.log('selected page : ' + page);
        this.selectedPage = page;
        this.getPageRegionTerrain(page, this.isValid);
    }


    delete(data, i) {
        data.index = i;
        data.flag = 'delete';
        this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + ' ' + data.regionName, data);
    }

    // Reject
    reject(data, i) {
        data.index = i;
        data.flag = 'reject';
        this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + ' ' + data.regionName, data);
    }

    approve(data, i) {
        data.index = i;
        data.flag = 'approve';
        this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + ' ' + data.regionName, data);
    }

    finalize(data, i) {
        data.index = i;
        data.flag = 'finalize';
        this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + ' ' + data.regionName, data);
    }

    modalConfirmation(event) {
        console.log(event);
        let observable: any;
        if (event) {
            this.isSubmitted = true;
            if (event.flag == 'approve') {
                observable = this.regionTerrainService.ApproveRegionTerrain(event.id);
            } else if (event.flag == 'finalize') {
                observable = this.regionTerrainService.FinalizeRegionTerrain(event.id);
            } else if (event.flag == 'delete') {
                observable = this.regionTerrainService.DeleteRegionTerrain(event.id);
            } else if (event.flag == 'reject') {
                observable = this.regionTerrainService.RejectRegionTerrain(event.id);
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
        (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
        this.bulkDatas.disbled = true;
        console.log("page : " + this.selectedPage);
        if(this.selectedPage >= 2){
          console.log("Inside if");
        this.getPageRegionTerrain(this.selectedPage - 1,this.isValid);
        this.terrainStatus = globalConstants;
        }else{
          console.log("Inside else");
        this.ngOnInit();
        }
      }

    fixBug() {
        this.isValid = 0;
        this.drkServiceService.fixBug('general_terrain_type').subscribe(res => {
            this.isSubmitted = true;
            if (res) {
                this.ngOnInit();
                this.isSuccess = res.success;
                if (res.success) {
                    this.successModal.showModal('SUCCESS', res.message, '');
                }
            }
        });
    }

    bulkData(key,tableName){

        let Values = []
        let getValue = document.querySelectorAll<HTMLInputElement>('table tbody input:checked')
       
        getValue.forEach(function(data,i){
          Values.push(data.value)
        })
        let AllData = {status:key, tableName:tableName, ids:Values.toString()}
    
        this.bulkDatas.getData(AllData)
            .subscribe( data => {
              data
              if(data.success == true){
                this.successModal.showModal('SUCCESS', data.message, '');
    
              }else {
                this.errorModal.showModal('ERROR', data.error, '');
    
              }
    
            })
    
      }
}

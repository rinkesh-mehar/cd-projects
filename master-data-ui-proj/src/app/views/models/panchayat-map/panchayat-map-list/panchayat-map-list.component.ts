import { globalConstants } from './../../../global/globalConstants';
import {Component, OnInit, ViewChild} from '@angular/core';
import {PanchayatMapService} from '../../services/panchayat-map.service';
import {DrkServiceService} from '../../../services/drk-service.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import { PagePanchyatMap } from '../../model/page-panchyat-map';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';

@Component({
    selector: 'app-panchayat-map-models',
    templateUrl: './panchayat-map-list.component.html',
    styleUrls: ['./panchayat-map-list.component.css']
})
export class PanchayatMapListComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    isSubmitted: boolean = false;
    isValid: number = 1;
    isSuccess: boolean = false;

    searchText: any = '';
    selectedPage: number = 1;
    maxSize: number = 10;
    missing : any="";

    recordsPerPage: number = 10;
   records: any = [];

    panchayatExistRegionList: PagePanchyatMap;

    constructor(private panchayatMapService: PanchayatMapService, private drkServiceService: DrkServiceService
    ) {
    }

    ngOnInit(): void {
   this.records = ['20', '50', '100', '200', '250'];
        this.loadPanchayatExistRegion(0);
    }

    loadPanchayatExistRegion(page: number) {
        return this.panchayatMapService.getPanchayatExistingRegion(page, this.recordsPerPage, this.searchText,this.isValid,this.missing).subscribe(
            page => this.panchayatExistRegionList = page);
    }

    loadData(event: any) {
      console.log('pages ', event.target.value);
      this.recordsPerPage = event.target.value || 10;
      this.panchayatMapService.getPanchayatExistingRegion(this.selectedPage - 1, this.recordsPerPage, this.searchText,this.isValid,this.missing)
        .subscribe(page => this.panchayatExistRegionList = page);
    }

    onSelect(page: number): void {
        console.log('selected page : ' + page);
        this.selectedPage = page;
        this.loadPanchayatExistRegion(page);
    }

    searchPanchyatMap() {
        console.log(this.searchText);
        this.loadPanchayatExistRegion(this.selectedPage - 1);
    }

    fixBug() {
        this.isValid = 0;
        this.drkServiceService.fixBug('regional_panchayat_map').subscribe(res => {
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

    onClickMissing(){
        this.missing = 1;
        this.loadPanchayatExistRegion(0);
      }
    
      moveToMaster(id){
        this.panchayatMapService.moveToMaster(id).subscribe(res => {
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
        this.missing = 0;
        this.ngOnInit();
      }

      modalSuccess($event: any) {
        this.ngOnInit();
     }

     sortData(sort: Sort) {
      const data = this.panchayatExistRegionList.content.slice();
      if (!sort.active || sort.direction == '') {
        this.panchayatExistRegionList.content = data;
        return;
      }
    
      function compare(firstValue, secondValue, isAsc: boolean) {
        return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
      }
    
      this.panchayatExistRegionList.content = data.sort((firstValue, secondValue) => {
        const isAsc = sort.direction == 'asc';
        switch (sort.active) {
            case 'regionId':
              return compare(firstValue.regionId, secondValue.regionId, isAsc);
              case globalConstants.NAME:
              return compare(firstValue.name, secondValue.name, isAsc);
          default:
            return 0;
        }
      });
    }
    

}

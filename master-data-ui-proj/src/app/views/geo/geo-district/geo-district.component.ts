import { Component, OnInit, ViewChild } from '@angular/core';
import { GeoDistrictService } from '../services/geo-district.service';
import { PageGeoRegion } from '../models/PageGeoRegion';
// import {Sort} from '@angular/material';
import { PageGeoDistrict } from '../models/PageGeoDistrict';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { globalConstants } from '../../global/globalConstants';
import { UserRightsService } from '../../services/user-rights.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {Router} from '@angular/router';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { BulkDataService } from '../../agri/services/bulk-data.service';

@Component({
  selector: 'app-geo-district',
  templateUrl: './geo-district.component.html',
  styleUrls: ['./geo-district.component.scss']
})
export class GeoDistrictComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  districtStatus;
  sortedData;
  DistrictList: any = [];
  pageDistrict: PageGeoDistrict;
  selectedPage: number = 1;
  searchText: any = '';
  maxSize: number = 10;

  recordsPerPage: number = 10;
   records: any = [];



  ngOnInit() {
    this.getPageGeoDistrict(0);
    // this.loadAllDistrict();
    this.districtStatus = globalConstants;

   this.records = ['20', '50', '100', '200', '250'];
  }

  constructor(public bulkDatas: BulkDataService,
    public geoDistrictService: GeoDistrictService,
    private userRightsService: UserRightsService,
    private router: Router
  ) { }

  // District list
  loadAllDistrict() {
    return this.geoDistrictService.GetAllDistrict().subscribe((data: {}) => {
      this.DistrictList = data;
    });
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPageGeoDistrict(page);
  }


  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.geoDistrictService.getPageGeoDistrict(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageDistrict = page);
  }

  getPageGeoDistrict(page: number): void {
    this.geoDistrictService.getPageGeoDistrict(page, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageDistrict = page);
  }

  // Delete District
  // deleteDistrict(data) {
  //   var index = index = this.DistrictList.map(x => { return x.name }).indexOf(data.name);
  //   return this.geoDistrictService.DeleteDistrict(data.id).subscribe(res => {
  //     this.DistrictList.splice(index, 1)
  //     console.log('District deleted!')
  //   })
  // }

  // Delete
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
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'approve') {
        this.approveDistrict(event);
      } else if (event.flag == 'finalize') {
        this.finalizeDistrict(event);
      } else if (event.flag == 'reject') {
        this.rejectDistrict(event);
      }

    }
  }

  approveDistrict(event: any) {
    return this.geoDistrictService.ApproveDistrict(event.id).subscribe(res => {
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

  finalizeDistrict(event: any) {
    return this.geoDistrictService.FinalizeDistrict(event.id).subscribe(res => {
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

  rejectDistrict(event) {
    return this.geoDistrictService.RejectDistrict(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.DistrictList.splice(event.index, 1);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
    });
  }

  deleteDistrict(event) {
    return this.geoDistrictService.DeleteDistrict(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.DistrictList.splice(event.index, 1);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
    });
  }

  searchDistrict() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageGeoDistrict(this.selectedPage - 1);
  }

  sortData(sort: any) {
    const data = this.pageDistrict.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageDistrict.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageDistrict.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'state':
          return compare(firstValue.state, secondValue.state, isAsc);
        case 'districtCode':
          return compare(+firstValue.districtCode, +secondValue.districtCode, isAsc);
        case globalConstants.NAME:
          return compare(firstValue.name, secondValue.name, isAsc);
        case globalConstants.STATUS:
          return compare(firstValue.status, secondValue.status, isAsc);
        default:
          return 0;
      }
    });
  }

  modalSuccess($event: any) {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getPageGeoDistrict(this.selectedPage - 1);
    this.districtStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
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

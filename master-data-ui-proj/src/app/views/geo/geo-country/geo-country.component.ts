import { PageGeoCountry } from './../models/PageGeoCountry';
import { Component, OnInit, ViewChild } from '@angular/core';
import { GeoCountryService } from '../services/geo-country.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../agri/services/bulk-data.service';

@Component({
  selector: 'app-geo-country',
  templateUrl: './geo-country.component.html',
  styleUrls: ['./geo-country.component.scss']
})
export class GeoCountryComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  CountryList: any = [];
  countryStatus;


  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];

  pageGeoCountry: PageGeoCountry;


  ngOnInit() {
    // this.loadAllCountry();
    this.records = ['20', '50', '100', '200', '250'];
    this.getCountryPagenatedList(0);
    this.countryStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public geoCountryService: GeoCountryService,
    private userRightsService: UserRightsService,
  ){ }


  getCountryPagenatedList(page: number): void {
    this.geoCountryService.getCountryPagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageGeoCountry = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.geoCountryService.getCountryPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageGeoCountry = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getCountryPagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getCountryPagenatedList(this.selectedPage - 1);
}

   // Country list
   loadAllCountry() {
    return this.geoCountryService.GetAllCountry().subscribe((data: {}) => {
      this.CountryList = data;
    })
  }

    // // Delete Country
    // deleteCountry(data){
    //   var index = index = this.CountryList.map(x => {return x.name}).indexOf(data.name);
    //    return this.geoCountryService.DeleteCountry(data.id).subscribe(res => {
    //     this.CountryList.splice(index, 1)
    //      console.log('Country deleted!')
    //    })
    // }

          // Delete 
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.name, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.name, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.name, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.name, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'approve') {
        observable = this.geoCountryService.ApproveCountry(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.geoCountryService.FinalizeCountry(event.id);
      } else if (event.flag == 'delete') {
        observable = this.geoCountryService.DeleteCountry(event.id);
      } else if (event.flag == 'reject') {
        observable = this.geoCountryService.RejectCountry(event.id);
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
      // console.log("Inside if");
    this.getCountryPagenatedList(this.selectedPage - 1);
    this.countryStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }

  sortData(sort: Sort) {
    const data = this.pageGeoCountry.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageGeoCountry.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageGeoCountry.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case 'countryCode':
            return compare(firstValue.countryCode, secondValue.countryCode, isAsc);
        case globalConstants.NAME:
          return compare(firstValue.name, secondValue.name, isAsc);
          case globalConstants.STATUS:
          return compare(firstValue.status, secondValue.status, isAsc);
        default:
          return 0;
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

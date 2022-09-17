import { Component, OnInit, ViewChild } from '@angular/core';
import { GeoTehsilService } from '../services/geo-tehsil.service';
import { PageGeoTehsil } from '../models/PageGeoTehsil';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { globalConstants } from '../../global/globalConstants';
import { UserRightsService } from '../../services/user-rights.service';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { BulkDataService } from '../../agri/services/bulk-data.service';

@Component({
  selector: 'app-geo-tehsil',
  templateUrl: './geo-tehsil.component.html',
  styleUrls: ['./geo-tehsil.component.scss']
})
export class GeoTehsilComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  tehsilStatus;
  TehsilList: any = [];
  pageTehsil: PageGeoTehsil;
  selectedPage: number = 1;
  searchText: String = "";
  maxSize :number= 10;


recordsPerPage: number = 10;
records: any = [];


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageGeoTehsil(0);
    //this.loadAllTehsil();
    this.tehsilStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public geoTehsilService: GeoTehsilService,
    private userRightsService: UserRightsService,

  ) { }

  // Tehsillist
  loadAllTehsil() {
    return this.geoTehsilService.GetAllTehsil().subscribe((data: {}) => {
      this.TehsilList = data;
    })
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : " + page);
    this.selectedPage = page;
    this.getPageGeoTehsil(page);
  }

  getPageGeoTehsil(page: number): void {
    this.geoTehsilService.getPageGeoTehsil(page, this.recordsPerPage,this.searchText)
      .subscribe(page => this.pageTehsil = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.geoTehsilService.getPageGeoTehsil(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageTehsil = page);
  }

  // Delete Tehsil
  // deleteTehsil(data) {
  //   var index = index = this.TehsilList.map(x => { return x.name }).indexOf(data.name);
  //   return this.geoTehsilService.DeleteTehsil(data.id).subscribe(res => {
  //     this.TehsilList.splice(index, 1)
  //     console.log('Tehsil deleted!')
  //   })
  // }



  // Delete Tehsil
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.name, data);
  }
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
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        this.approveTehsil(event);
      } else if (event.flag == "finalize") {
        this.finalizeTehsil(event);
      } else if (event.flag == "delete") {
        this.deleteTehsil(event);
      } else if (event.flag == "reject") {
        this.rejectTehsil(event);
      }

    }
  }

  approveTehsil(event: any) {
    return this.geoTehsilService.ApproveTehsil(event.id).subscribe(res => {
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

  finalizeTehsil(event: any) {
    return this.geoTehsilService.FinalizeTehsil(event.id).subscribe(res => {
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

  deleteTehsil(event) {
    return this.geoTehsilService.DeleteTehsil(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.TehsilList.splice(event.index, 1);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
    });
  }

  rejectTehsil(event: any) {
    return this.geoTehsilService.RejectTehsil(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.TehsilList.splice(event.index, 1);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
    });
  }

  searchTehsil() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageGeoTehsil(this.selectedPage);
  }

  sortData(sort: Sort) {
    const data = this.pageTehsil.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageTehsil.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageTehsil.content = data.sort((firstValue, secondValue) => {
      let isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'state':
          return compare(firstValue.state, secondValue.state, isAsc);
        case 'district':
          return compare(firstValue.district, secondValue.district, isAsc);
        case 'tehsilCode':
          return compare(+firstValue.tehsilCode, +secondValue.tehsilCode, isAsc);
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
    // this.ngOnInit();
    // this.selectedPage = 1;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getPageGeoTehsil(this.selectedPage - 1);
    this.tehsilStatus = globalConstants;
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

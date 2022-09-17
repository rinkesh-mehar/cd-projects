import { Component, OnInit, ViewChild } from '@angular/core';
import { GeoPanchayatService } from '../services/geo-panchayat.service';
import { PageGeoPanchayat } from '../models/PageGeoPanchayat';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { globalConstants } from '../../global/globalConstants';
import { UserRightsService } from '../../services/user-rights.service';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { BulkDataService } from '../../agri/services/bulk-data.service';


@Component({
  selector: 'app-geo-panchayat',
  templateUrl: './geo-panchayat.component.html',
  styleUrls: ['./geo-panchayat.component.scss']
})
export class GeoPanchayatComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  panchayatStatus;
  PanchayatList: any = [];
  pagePanchayat: PageGeoPanchayat;
  selectedPage: number;
  searchText : any ="";
  maxSize : number= 10;


recordsPerPage: number = 10;
records: any = [];


  ngOnInit() {
    this.getPageGeoPanchayat(0);
    //this.loadAllPanchayat();
    this.panchayatStatus = globalConstants;
    this.records = ['20', '50', '100', '200', '250'];
    
  }

  constructor(public bulkDatas: BulkDataService,
    public geoPanchayatService: GeoPanchayatService,
    private userRightsService: UserRightsService,

  ) { }

  // Panchayatlist
  loadAllPanchayat() {
    return this.geoPanchayatService.GetAllPanchayat().subscribe((data: {}) => {
      this.PanchayatList = data;
    })
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : " + page);
    this.selectedPage = page;
    this.getPageGeoPanchayat(page);
  }

  getPageGeoPanchayat(page: number): void {
    this.geoPanchayatService.getPageGeoPanchayat(page, this.recordsPerPage,this.searchText)
      .subscribe(page => this.pagePanchayat = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.geoPanchayatService.getPageGeoPanchayat(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pagePanchayat = page);
  }

  // Delete Panchayat
  // deletePanchayat(data){
  //   var index = index = this.PanchayatList.map(x => {return x.name}).indexOf(data.name);
  //    return this.geoPanchayatService.DeletePanchayat(data.id).subscribe(res => {
  //     this.PanchayatList.splice(index, 1)
  //      console.log('Panchayat deleted!')
  //    })
  // }


  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.name, data);
  }


  // Delete Panchayat
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.name, data);
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
        this.approvePanchayat(event);
      } else if (event.flag == "finalize") {
        this.finalizePanchayat(event);
      } else if (event.flag == "delete") {
        this.deletePanchayat(event);
      } else if (event.flag == "reject") {
        this.rejectPanchayat(event);
      }

    }
  }

  approvePanchayat(event: any) {
    return this.geoPanchayatService.ApprovePanchayat(event.id).subscribe(res => {
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

  finalizePanchayat(event: any) {
    return this.geoPanchayatService.FinalizePanchayat(event.id).subscribe(res => {
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

  deletePanchayat(event) {
    return this.geoPanchayatService.DeletePanchayat(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.PanchayatList.splice(event.index, 1);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
    });
  }

  rejectPanchayat(event) {
    return this.geoPanchayatService.RejectPanchayat(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.PanchayatList.splice(event.index, 1);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
    });
  }

  searchPanchayat() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageGeoPanchayat(this.selectedPage - 1);
  }

  sortData(sort: Sort) {
    const data = this.pagePanchayat.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pagePanchayat.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pagePanchayat.content = data.sort((firstValue, secondValue) => {
      let isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'state':
          return compare(firstValue.state, secondValue.state, isAsc);
        case 'district':
          return compare(firstValue.district, secondValue.district, isAsc);
        case 'tehsil':
          return compare(firstValue.tehsil, secondValue.tehsil, isAsc);
        case 'panchayatCode':
          return compare(+firstValue.panchayatCode, +secondValue.panchayatCode, isAsc);
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
    this.getPageGeoPanchayat(this.selectedPage - 1);
    this.panchayatStatus = globalConstants;
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

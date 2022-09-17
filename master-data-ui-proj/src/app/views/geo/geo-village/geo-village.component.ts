import { Component, OnInit, ViewChild } from '@angular/core';
import { GeoVillageService } from '../services/geo-village.service';
import { PageGeoVillage } from '../models/PageGeoVillage';
import { globalConstants } from '../../global/globalConstants';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { BulkDataService } from '../../agri/services/bulk-data.service';


@Component({
  selector: 'app-geo-village',
  templateUrl: './geo-village.component.html',
  styleUrls: ['./geo-village.component.scss']
})
export class GeoVillageComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
villageStatus;
  VillageList: any = [];
  pageVillage: PageGeoVillage;
  selectedPage: number;
  searchText : any = "";
  maxSize : number=10;
  recordsPerPage: number = 10;
  records: any = [];

  ngOnInit() {
    this.getPageGeoVillage(0);
    //this.loadAllVillage();
    this.villageStatus = globalConstants;
    this.records = ['20', '50', '100', '200', '250'];
  }

  constructor(public bulkDatas: BulkDataService,
    public geoVillageService: GeoVillageService,
    private userRightsService: UserRightsService,

  ) { }

  // Villagelist
  loadAllVillage() {
    return this.geoVillageService.GetAllVillage().subscribe((data: {}) => {
      this.VillageList = data;
    })
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : " + page);
    this.selectedPage = page;
    this.getPageGeoVillage(page);
  }

  getPageGeoVillage(page: number): void {
    this.geoVillageService.getPageGeoVillage(page, this.recordsPerPage,this.searchText)
      .subscribe(page => this.pageVillage = page)
  }


  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.geoVillageService.getPageGeoVillage(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageVillage = page);
  }


  // Delete Village
  // deleteVillage(data){
  //   var index = index = this.VillageList.map(x => {return x.name}).indexOf(data.name);
  //    return this.geoVillageService.DeleteVillage(data.id).subscribe(res => {
  //     this.VillageList.splice(index, 1)
  //      console.log('Village deleted!')
  //    })
  // }



  // Delete Village
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
        this.approveVillage(event);
      } else if (event.flag == "finalize") {
        this.finalizeVillage(event);
      } else if (event.flag == "delete") {
        this.deleteVillage(event);
      }else if (event.flag == "reject") {
        this.rejectVillage(event);
      }

    }
  }

  approveVillage(event: any) {
    return this.geoVillageService.ApproveVillage(event.id).subscribe(res => {
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

  finalizeVillage(event: any) {
    return this.geoVillageService.FinalizeVillage(event.id).subscribe(res => {
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

  deleteVillage(event) {
    return this.geoVillageService.DeleteVillage(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.VillageList.splice(event.index, 1);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
    });
  }

  rejectVillage(event: any) {
    return this.geoVillageService.RejectVillage(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.VillageList.splice(event.index, 1);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
    });
  }

  searchVillage() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageGeoVillage(this.selectedPage - 1);
  }

  sortData(sort: Sort) {
    const data = this.pageVillage.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageVillage.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageVillage.content = data.sort((firstValue, secondValue) => {
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
        case 'villageCode':
          return compare(+firstValue.villageCode, +secondValue.villageCode, isAsc);
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
    this.getPageGeoVillage(this.selectedPage - 1);
    this.villageStatus = globalConstants;
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

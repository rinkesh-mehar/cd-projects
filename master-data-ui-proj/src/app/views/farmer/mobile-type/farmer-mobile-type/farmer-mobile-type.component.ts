import { Component, OnInit, ViewChild } from '@angular/core';
import { FarmerMobileTypeService } from '../../services/farmer-mobile-type.service';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { globalConstants } from '../../../global/globalConstants';
import { UserRightsService } from '../../../services/user-rights.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../../agri/services/bulk-data.service';
import { PageFarmerMobileType } from '../../models/PageFarmerMobileType';


@Component({
  selector: 'app-farmer-mobile-type',
  templateUrl: './farmer-mobile-type.component.html',
  styleUrls: ['./farmer-mobile-type.component.scss']
})
export class FarmerMobileTypeComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
   isSuccess: boolean = false;
   _statusMsg: string;
  mobileTypeStatus;
  MobileTypeList: any = [];



  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];

  pageFarmerMobileType: PageFarmerMobileType;


  ngOnInit() {

    this.records = ['20', '50', '100', '200', '250'];
    this.getMobileTypePagenatedList(0);
    // this.loadAllMobileType();
    this.mobileTypeStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,private userRightsService: UserRightsService,
    public farmerMobileTypeService: FarmerMobileTypeService
  ) { }

  getMobileTypePagenatedList(page: number): void {
    this.farmerMobileTypeService.getMobileTypePagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageFarmerMobileType = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.farmerMobileTypeService.getMobileTypePagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageFarmerMobileType = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getMobileTypePagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getMobileTypePagenatedList(this.selectedPage - 1);
}


  // MobileType list
  loadAllMobileType() {
    return this.farmerMobileTypeService.GetAllMobileType().subscribe((data: {}) => {
      this.MobileTypeList = data;
    })
  }



  // Delete MobileType
  deleteMobileType(data) {
    var index = index = this.MobileTypeList.map(x => { return x.name }).indexOf(data.name);
    return this.farmerMobileTypeService.DeleteMobileType(data.id).subscribe(res => {
      this.MobileTypeList.splice(index, 1)
      console.log('MobileType deleted!')
    })
  }

  // Delete MobileType
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.mobileType, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.mobileType, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.mobileType, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.mobileType, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        observable = this.farmerMobileTypeService.ApproveMobileType(event.id)
      } else if (event.flag == "finalize") {
        observable = this.farmerMobileTypeService.FinalizeMobileType(event.id)
      } else if (event.flag == "delete") {
        observable = this.farmerMobileTypeService.DeleteMobileType(event.id)
      } else if (event.flag == "reject") {
        observable = this.farmerMobileTypeService.RejectMobileType(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            // this._statusMsg = res.message;
            this.loadAllMobileType();
            this.successModal.showModal('SUCCESS', res.message, '');
          } else {
            this.errorModal.showModal('ERROR', res.error, '');
          }
        }
      }, err => {
        this.errorModal.showModal('ERROR', err.error, '');
      })
    }
  }


  modalSuccess($event: any) {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // this.ngOnInit();
    // this.selectedPage = 1;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getMobileTypePagenatedList(this.selectedPage - 1);
    this.mobileTypeStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }

  sortData(sort: Sort) {
    const data = this.pageFarmerMobileType.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageFarmerMobileType.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageFarmerMobileType.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case 'mobileType':
            return compare(firstValue.mobileType, secondValue.mobileType, isAsc);
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

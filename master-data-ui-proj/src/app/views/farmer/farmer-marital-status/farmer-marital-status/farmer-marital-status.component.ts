import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { globalConstants } from '../../../global/globalConstants';
import { UserRightsService } from '../../../services/user-rights.service';
import { FarmerMaritalStatusService } from '../../services/farmer-marital-status.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../../agri/services/bulk-data.service';
import { PageFarmerMaritalStatus } from '../../models/PageFarmerMaritalStatus';

@Component({
  selector: 'app-farmer-marital-status',
  templateUrl: './farmer-marital-status.component.html',
  styleUrls: ['./farmer-marital-status.component.scss']
})
export class FarmerMaritalStatusComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
   isSuccess: boolean = false;
   _statusMsg: string;
  maritalStatusStatus;
  MaritalStatusList: any = [];
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];

  pageFarmerMaritalStatus: PageFarmerMaritalStatus;


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getMaritalStatusPagenatedList(0);
    // this.loadAllMaritalStatus();
    this.maritalStatusStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,private userRightsService: UserRightsService,
    public farmerMaritalStatusService: FarmerMaritalStatusService
  ) { }

  getMaritalStatusPagenatedList(page: number): void {
    this.farmerMaritalStatusService.getMaritalStatusPagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageFarmerMaritalStatus = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.farmerMaritalStatusService.getMaritalStatusPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageFarmerMaritalStatus = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getMaritalStatusPagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getMaritalStatusPagenatedList(this.selectedPage - 1);
}

  // MaritalStatus list
  loadAllMaritalStatus() {
    return this.farmerMaritalStatusService.GetAllMaritalStatus().subscribe((data: {}) => {
      this.MaritalStatusList = data;
    })
  }



  // Delete MaritalStatus
  deleteMaritalStatus(data) {
    var index = index = this.MaritalStatusList.map(x => { return x.name }).indexOf(data.name);
    return this.farmerMaritalStatusService.DeleteMaritalStatus(data.id).subscribe(res => {
      this.MaritalStatusList.splice(index, 1)
      console.log('MaritalStatus deleted!')
    })
  }

  // Delete MaritalStatus
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.MaritalStatus, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.MaritalStatus, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.MaritalStatus, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.MaritalStatus, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        observable = this.farmerMaritalStatusService.ApproveMaritalStatus(event.id)
      } else if (event.flag == "finalize") {
        observable = this.farmerMaritalStatusService.FinalizeMaritalStatus(event.id)
      } else if (event.flag == "delete") {
        observable = this.farmerMaritalStatusService.DeleteMaritalStatus(event.id)
      } else if (event.flag == "reject") {
        observable = this.farmerMaritalStatusService.RejectMaritalStatus(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
            this.loadAllMaritalStatus();
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
    // this.ngOnInit();
    // this.selectedPage = 1;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getMaritalStatusPagenatedList(this.selectedPage - 1);
    this.maritalStatusStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }
  sortData(sort: Sort) {
    const data = this.pageFarmerMaritalStatus.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageFarmerMaritalStatus.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageFarmerMaritalStatus.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
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

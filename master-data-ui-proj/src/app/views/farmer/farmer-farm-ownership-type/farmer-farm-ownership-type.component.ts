import { PageFarmerFarmOwnershipType } from './../models/PageFarmerEducation copy';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FarmerFarmOwnershipTypeService } from '../services/farmer-farm-ownership-type.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../agri/services/bulk-data.service';


@Component({
  selector: 'app-farmer-farm-ownership-type',
  templateUrl: './farmer-farm-ownership-type.component.html',
  styleUrls: ['./farmer-farm-ownership-type.component.scss']
})
export class FarmerFarmOwnershipTypeComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  farmOwnershipTypeStatus;
FarmOwnershipTypeList: any = [];


searchText: any = '';
selectedPage: number = 1;
maxSize =10;
recordsPerPage: number = 10;
records: any = [];

pageFarmerFarmOwnershipType: PageFarmerFarmOwnershipType;


  ngOnInit() {
    // this.loadAllFarmOwnershipType();
    this.records = ['2', '50', '100', '200', '250'];
    this.getFarmOwnershipTypePagenatedList(0);
    this.farmOwnershipTypeStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public farmerFarmOwnershipTypeService: FarmerFarmOwnershipTypeService,
    private userRightsService: UserRightsService,
  ){ }

  getFarmOwnershipTypePagenatedList(page: number): void {
    this.farmerFarmOwnershipTypeService.getFarmOwnershipTypePagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageFarmerFarmOwnershipType = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.farmerFarmOwnershipTypeService.getFarmOwnershipTypePagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageFarmerFarmOwnershipType = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;

    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getFarmOwnershipTypePagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getFarmOwnershipTypePagenatedList(this.selectedPage - 1);
}


   // FarmOwnershipType list
   loadAllFarmOwnershipType() {
    return this.farmerFarmOwnershipTypeService.GetAllFarmOwnershipType().subscribe((data: {}) => {
      this.FarmOwnershipTypeList = data;
    })
  }

    // // Delete FarmOwnershipType
    // deleteFarmOwnershipType(data){
    //   var index = index = this.FarmOwnershipTypeList.map(x => {return x.name}).indexOf(data.name);
    //    return this.farmerFarmOwnershipTypeService.DeleteFarmOwnershipType(data.id).subscribe(res => {
    //     this.FarmOwnershipTypeList.splice(index, 1)
    //      console.log('FarmOwnershipType deleted!')
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
      if (event.flag == "approve") {
        observable = this.farmerFarmOwnershipTypeService.ApproveFarmOwnershipType(event.id)
      } else if (event.flag == "finalize") {
        observable = this.farmerFarmOwnershipTypeService.FinalizeFarmOwnershipType(event.id)
      } else if (event.flag == "delete") {
        observable = this.farmerFarmOwnershipTypeService.DeleteFarmOwnershipType(event.id)
      } else if (event.flag == "reject") {
        observable = this.farmerFarmOwnershipTypeService.RejectFarmOwnershipType(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
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
    this.getFarmOwnershipTypePagenatedList(this.selectedPage - 1);
    this.farmOwnershipTypeStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }
  sortData(sort: Sort) {
    const data = this.pageFarmerFarmOwnershipType.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageFarmerFarmOwnershipType.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageFarmerFarmOwnershipType.content = data.sort((firstValue, secondValue) => {
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

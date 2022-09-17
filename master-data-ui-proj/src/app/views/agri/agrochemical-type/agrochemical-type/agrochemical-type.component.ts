import { globalConstants } from './../../../global/globalConstants';
import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriAgrochemicalTypeService } from '../../services/agri-agrochemical-type.service';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../../services/user-rights.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../services/bulk-data.service';
import { PageAgriAgrochemicalType } from '../../models/PageAgriAgrochemicalType';


@Component({
  selector: 'app-agrochemical-type',
  templateUrl: './agrochemical-type.component.html',
  styleUrls: ['./agrochemical-type.component.scss']
})
export class AgrochemicalTypeComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  agrochemicalTypeStatus;
  AgrochemicalTypeList: any = [];

  searchText: any = '';
   selectedPage: number = 1;
   maxSize =10;
   recordsPerPage: number = 10;
   records: any = [];
   inactiveCount:number=0;
   approvedCount:number=0;
   finalizedCount:number=0;
   rejectedCount:number=0;
   statusList:any=[];
   pageAgriAgrochemicalType: PageAgriAgrochemicalType;


  ngOnInit() {
    // this.loadAllAgrochemicalType();
    this.records = ['20', '50', '100', '200', '250'];
   this.getAgrochemicalTypePagenatedList(0);
    this.agrochemicalTypeStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public agriAgrochemicalTypeService : AgriAgrochemicalTypeService,
    private userRightsService: UserRightsService,
  ){ }

  getAgrochemicalTypePagenatedList(page: number): void {
    this.agriAgrochemicalTypeService.getAgrochemicalTypePagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => {
          this.inactiveCount=0;
        this.approvedCount=0;
        this.finalizedCount=0;
        this.rejectedCount=0;
          this.pageAgriAgrochemicalType = page
          for(let agrochemicalType of this.pageAgriAgrochemicalType.content){
            if(globalConstants.INACTIVE_STATUS == agrochemicalType.status){
              this.inactiveCount++;
            }
            if(globalConstants.APPROVED_STATUS == agrochemicalType.status){
              this.approvedCount++;
            }
            if(globalConstants.ACTIVE_STATUS == agrochemicalType.status){
              this.finalizedCount++;
            }
            if(globalConstants.REJECTED_STATUS == agrochemicalType.status){
              this.rejectedCount++;
            }
        }
        });
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriAgrochemicalTypeService.getAgrochemicalTypePagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => {
        this.inactiveCount=0;
        this.approvedCount=0;
        this.finalizedCount=0;
        this.rejectedCount=0;
        this.pageAgriAgrochemicalType = page
        for(let agrochemicalType of this.pageAgriAgrochemicalType.content){
          if(globalConstants.INACTIVE_STATUS == agrochemicalType.status){
            this.inactiveCount++;
          }
          if(globalConstants.APPROVED_STATUS == agrochemicalType.status){
            this.approvedCount++;
          }
          if(globalConstants.ACTIVE_STATUS == agrochemicalType.status){
            this.finalizedCount++;
          }
          if(globalConstants.REJECTED_STATUS == agrochemicalType.status){
            this.rejectedCount++;
          }
      }
      
      });
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;  
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getAgrochemicalTypePagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getAgrochemicalTypePagenatedList(this.selectedPage - 1);
}


   // AgrochemicalType list
   loadAllAgrochemicalType() {
    return this.agriAgrochemicalTypeService.GetAllAgrochemicalType().subscribe((data: {}) => {
      this.AgrochemicalTypeList = data;
    })
  }

    // // Delete AgrochemicalType
    // deleteAgrochemicalType(data){
    //   var index = index = this.AgrochemicalTypeList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriAgrochemicalTypeService.DeleteAgrochemicalType(data.id).subscribe(res => {
    //     this.AgrochemicalTypeList.splice(index, 1)
    //      console.log('AgrochemicalType deleted!')
    //    })
    // }

              // Delete Commodity
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        observable = this.agriAgrochemicalTypeService.ApproveAgrochemicalType(event.id)
      } else if (event.flag == "finalize") {
        observable = this.agriAgrochemicalTypeService.FinalizeAgrochemicalType(event.id)
      } else if (event.flag == "delete") {
        observable = this.agriAgrochemicalTypeService.DeleteAgrochemicalType(event.id)
      } else if (event.flag == "reject") {
        observable = this.agriAgrochemicalTypeService.RejectAgrochemicalType(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            console.log("page : " + this.selectedPage);
            if(this.selectedPage >= 2){
              // console.log("Inside if");
            this.getAgrochemicalTypePagenatedList(this.selectedPage - 1);
            this.agrochemicalTypeStatus = globalConstants;
            }else{
              // console.log("Inside else");
            this.ngOnInit();
            }
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
  
   
  }


  sortData(sort: Sort) {
    const data = this.pageAgriAgrochemicalType.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriAgrochemicalType.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageAgriAgrochemicalType.content = data.sort((firstValue, secondValue) => {
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

    var parentCheckbox = (document.querySelector('thead th input') as HTMLInputElement).checked

    if(parentCheckbox){

    if((key == globalConstants.APPROVED_STATUS && this.approvedCount > 0 && this.approvedCount < this.recordsPerPage) || (key == globalConstants.APPROVED_STATUS && this.finalizedCount > 0 && this.finalizedCount < this.recordsPerPage)){
      this.errorModal.showModal('ERROR', 'One or more selected records are found with status Approved or Active. Please uncheck those records.', '');
      return;
    }

   if((key == globalConstants.ACTIVE_STATUS && this.finalizedCount > 0 && this.finalizedCount < this.recordsPerPage) || (key == globalConstants.ACTIVE_STATUS && this.rejectedCount > 0 && this.rejectedCount < this.recordsPerPage) || (key == globalConstants.ACTIVE_STATUS && this.inactiveCount > 0 && this.inactiveCount < this.recordsPerPage)){
      this.errorModal.showModal('ERROR', 'One or more selected records are found with status Active, Inactice or Rejected. Please uncheck those records.', '');
      return;
    }

  }else{

     for(let status of this.statusList){

      if((key == globalConstants.APPROVED_STATUS)){
        if(status == globalConstants.APPROVED_STATUS || status == globalConstants.ACTIVE_STATUS){
          this.errorModal.showModal('ERROR', 'Selected records are with Approved or Active status. You can not Approve those records.', '');
          return;
        }
      }

      if((key == globalConstants.ACTIVE_STATUS)){
        if(status == globalConstants.ACTIVE_STATUS || status == globalConstants.INACTIVE_STATUS|| status == globalConstants.REJECTED_STATUS){
            this.errorModal.showModal('ERROR', 'Selected records are with Active or Inactive or Rejected status. You can not Finalize those records.', '');
            return;
          }
      }
     }
    
  }

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
          this.statusList = [];
          this.successModal.showModal('SUCCESS', data.message, '');

        }else {
          this.errorModal.showModal('ERROR', data.error, '');

        }

      })
  }

}

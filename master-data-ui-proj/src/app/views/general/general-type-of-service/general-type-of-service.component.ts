import { Component, OnInit, ViewChild } from '@angular/core';
import { GeneralTypeOfServiceService } from '../services/general-type-of-service.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../agri/services/bulk-data.service';
import { PageGeneralTypeOfService } from '../models/PageGeneralTypeOfService';

@Component({
  selector: 'app-general-type-of-service',
  templateUrl: './general-type-of-service.component.html',
  styleUrls: ['./general-type-of-service.component.scss']
})
export class GeneralTypeOfServiceComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
   isSuccess: boolean = false;
   _statusMsg: string;
  typeOfServiceStatus;
  typeOfServiceList: any = [];


  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];

  pageGeneralTypeOfService: PageGeneralTypeOfService;


  ngOnInit() {

    this.records = ['20', '50', '100', '200', '250'];
    this.getTypeOfServicePagenatedList(0);
    // this.loadAllTypeOfService();
    this.typeOfServiceStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public generalTypeOfServiceService: GeneralTypeOfServiceService,
    private userRightsService: UserRightsService,
  ){ }

  getTypeOfServicePagenatedList(page: number): void {
    this.generalTypeOfServiceService.getTypeOfServicePagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageGeneralTypeOfService = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.generalTypeOfServiceService.getTypeOfServicePagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageGeneralTypeOfService = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getTypeOfServicePagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getTypeOfServicePagenatedList(this.selectedPage - 1);
}

   // TypeOfService list
   loadAllTypeOfService() {
    return this.generalTypeOfServiceService.GetAllTypeOfService().subscribe((data: {}) => {
      this.typeOfServiceList = data;
    })
  }

    // // Delete TypeOfService
    // deleteTypeOfService(data){
    //   var index = index = this.typeOfServiceList.map(x => {return x.name}).indexOf(data.name);
    //    return this.generalTypeOfServiceService.DeleteTypeOfService(data.id).subscribe(res => {
    //     this.typeOfServiceList.splice(index, 1)
    //      console.log('TypeOfService deleted!')
    //    })
    // }

  // Delete
  delete(data, i) {
    data.index = i;
    data.flag = 'delete';
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + ' ' + data.typeOfService, data);
  }

  // Reject
  reject(data, i) {
    data.index = i;
    data.flag = 'reject';
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + ' ' + data.typeOfService, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = 'approve';
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + ' ' + data.typeOfService, data);
  }

  finalize(data, i) {
    data.index = i;
    data.flag = 'finalize';
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + ' ' + data.typeOfService, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'approve') {
        observable = this.generalTypeOfServiceService.ApproveTypeOfService(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.generalTypeOfServiceService.FinalizeTypeOfService(event.id);
      } else if (event.flag == 'delete') {
        observable = this.generalTypeOfServiceService.DeleteTypeOfService(event.id);
      } else if (event.flag == 'reject') {
        observable = this.generalTypeOfServiceService.RejectTypeOfService(event.id);
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
    this.getTypeOfServicePagenatedList(this.selectedPage - 1);
    this.typeOfServiceStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }
  

  sortData(sort: Sort) {
    const data = this.pageGeneralTypeOfService.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageGeneralTypeOfService.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageGeneralTypeOfService.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case 'typeOfService':
            return compare(firstValue.typeOfService, secondValue.typeOfService, isAsc);
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



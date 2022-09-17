import { Component, OnInit, ViewChild } from '@angular/core';
import { GeneralBankNameService } from '../services/general-bank-name.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { globalConstants } from '../../global/globalConstants';
import { UserRightsService } from '../../services/user-rights.service';
import { PageGeneralBankName } from '../models/PageGeneralBankName';
import { GeneralBankCategoryService } from '../services/general-bank-category.service';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {DrkServiceService} from '../../services/drk-service.service';
import { BulkDataService } from '../../agri/services/bulk-data.service';

@Component({
  selector: 'app-general-bank-name',
  templateUrl: './general-bank-name.component.html',
  styleUrls: ['./general-bank-name.component.scss']
})
export class GeneralBankNameComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
   isSuccess: boolean = false;
   _statusMsg: string;

  generalBankNameStatus;
  bankNameList: any = [];
  pageGeneralBankName: PageGeneralBankName;
  selectedPage: number = 1;
  searchText : any = "";
  maxSize : number=10;
  isValid: number = 1;

  recordsPerPage: number = 10;
   records: any = [];


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    // this.loadAllBankName();
    this.getPageGeneralBankName(0, this.isValid);
    this.generalBankNameStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public generalBankNameService: GeneralBankNameService,
    private generalBankCategoryService: GeneralBankCategoryService,
    private userRightsService: UserRightsService,
    private drkServiceService: DrkServiceService

  ){ }

   // BankName list
   loadAllBankName() {
    return this.generalBankNameService.GetAllBankName().subscribe((data: {}) => {
      this.bankNameList = data;
    })
  }

    // // Delete BankName
    // deleteBankName(data){
    //   var index = index = this.bankNameList.map(x => {return x.name}).indexOf(data.name);
    //    return this.generalBankNameService.DeleteBankName(data.id).subscribe(res => {
    //     this.bankNameList.splice(index, 1)
    //      console.log('BankName deleted!')
    //    })
    // }

    onSelect(page: number): void {

    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    
      console.log("selected page : " + page);
      this.selectedPage = page;
      this.getPageGeneralBankName(page, this.isValid);
    }
  
    getPageGeneralBankName(page: number, isValid: number): void {
      this.generalBankNameService.getPageGeneralBankName(page, this.recordsPerPage,this.searchText, this.isValid)
        .subscribe(page => this.pageGeneralBankName = page)
    }

    loadData(event: any) {
      console.log('pages ', event.target.value);
      this.recordsPerPage = event.target.value || 10;
      this.generalBankNameService.getPageGeneralBankName(this.selectedPage - 1, this.recordsPerPage, this.searchText,this.isValid)
        .subscribe(page => this.pageGeneralBankName = page);
    }

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
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'approve') {
        observable = this.generalBankNameService.ApproveBankName(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.generalBankNameService.FinalizeBankName(event.id);
      } else if (event.flag == 'delete') {
        observable = this.generalBankNameService.DeleteBankName(event.id);
      } else if (event.flag == 'reject') {
        observable = this.generalBankNameService.RejectBankName(event.id);
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

  searchGeneralBankName() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageGeneralBankName(this.selectedPage-1, this.isValid);
  }

  sortData(sort: Sort) {
    const data = this.pageGeneralBankName.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageGeneralBankName.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageGeneralBankName.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'bankCategory':
          return compare(firstValue.bankCategory, secondValue.bankCategory, isAsc);
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
    this.getPageGeneralBankName(this.selectedPage - 1,this.isValid);
    this.generalBankNameStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('general_bank').subscribe(res => {
      this.isSubmitted = true;
      if (res) {
        this.ngOnInit();
        this.isSuccess = res.success;
        if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
        }
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



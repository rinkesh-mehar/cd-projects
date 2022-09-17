import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { GeneralBankBranchService } from '../../services/general-bank-branch.service';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import { PageGeneralBankBranch } from '../../models/PageGeneralBankBranch';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {DrkServiceService} from '../../../services/drk-service.service';
import { BulkDataService } from '../../../agri/services/bulk-data.service';


@Component({
  selector: 'app-bank-branch',
  templateUrl: './bank-branch.component.html',
  styleUrls: ['./bank-branch.component.scss']
})
export class BankBranchComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
   isSuccess: boolean = false;
   _statusMsg: string;
  bankBranchStatus;
  BankBranchList: any = [];
  pageGeneralBankBranch: PageGeneralBankBranch;
  selectedPage: number = 1;
  searchText : any = "";
  maxSize : number=10;
  isValid: number = 1;
  missing : any="";

  recordsPerPage: number = 10;
   records: any = [];


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageGeneralBankBranch(0, this.isValid);
    // this.loadAllBankBranch();
    this.bankBranchStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public generalBankBranchService: GeneralBankBranchService,
    private userRightsService: UserRightsService,
    private drkServiceService: DrkServiceService

  ){ }

   // BankBranch list
   loadAllBankBranch() {
    return this.generalBankBranchService.GetAllBankBranch().subscribe((data: {}) => {
      this.BankBranchList = data;
    })
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    
    console.log("selected page : " + page);
    this.selectedPage = page;
    this.getPageGeneralBankBranch(page, this.isValid);
  }

  getPageGeneralBankBranch(page: number, isValid: number): void {
    this.generalBankBranchService.getPageGeneralBankBranch(page, this.recordsPerPage,this.searchText, isValid,this.missing)
      .subscribe(page => this.pageGeneralBankBranch = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.generalBankBranchService.getPageGeneralBankBranch(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing)
      .subscribe(page => this.pageGeneralBankBranch = page);
  }

    // // Delete BankBranch
    // deleteBankBranch(data){
    //   var index = index = this.BankBranchList.map(x => {return x.name}).indexOf(data.name);
    //    return this.generalBankBranchService.DeleteBankBranch(data.id).subscribe(res => {
    //     this.BankBranchList.splice(index, 1)
    //      console.log('BankBranch deleted!')
    //    })
    // }

  // Delete
  delete(data, i) {
    data.index = i;
    data.flag = 'delete';
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + ' ' + data.branchName, data);
  }

  // Reject
  reject(data, i) {
    data.index = i;
    data.flag = 'reject';
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + ' ' + data.branchName, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = 'approve';
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + ' ' + data.branchName, data);
  }

  finalize(data, i) {
    data.index = i;
    data.flag = 'finalize';
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + ' ' + data.branchName, data);
  }

  onClickMissing(){
    this.missing = 1;
    this.getPageGeneralBankBranch(0,this.isValid);
  }


  moveToMaster(id){
    this.generalBankBranchService.moveToMaster(id).subscribe(res => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
    this.missing = 0;
    this.ngOnInit();
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'approve') {
        observable = this.generalBankBranchService.ApproveBankBranch(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.generalBankBranchService.FinalizeBankBranch(event.id);
      } else if (event.flag == 'delete') {
        observable = this.generalBankBranchService.DeleteBankBranch(event.id);
      } else if (event.flag == 'reject') {
        observable = this.generalBankBranchService.RejectBankBranch(event.id);
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

  searchGeneralBankBranch() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageGeneralBankBranch(this.selectedPage, this.isValid);
  }

  sortData(sort: Sort) {
    const data = this.pageGeneralBankBranch.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageGeneralBankBranch.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageGeneralBankBranch.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'bank':
          return compare(firstValue.bank, secondValue.bank, isAsc);
        case 'ifscCode':
          return compare(firstValue.ifscCode, secondValue.ifscCode, isAsc);
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
    this.getPageGeneralBankBranch(this.selectedPage - 1,this.isValid);
    this.bankBranchStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('general_bank_branch').subscribe(res => {
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

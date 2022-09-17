import { Component, OnInit, ViewChild } from '@angular/core';
import { UserRightsService } from '../../../services/user-rights.service';
import { RegionBankService } from '../../services/region-bank.service';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { PageRegionBank } from '../../models/PageRegionBank';
import { globalConstants } from '../../../global/globalConstants';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {Sort} from '@angular/material';
import {DrkServiceService} from '../../../services/drk-service.service';
import { BulkDataService } from '../../../agri/services/bulk-data.service';


@Component({
  selector: 'app-region-bank',
  templateUrl: './region-bank.component.html',
  styleUrls: ['./region-bank.component.scss']
})
export class RegionBankComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  regionBankStatus;
  BankList: any = [];
  pageRegionBank: PageRegionBank;
  selectedPage: number=1;
  searchText : any="";
  maxSize = 10;
  isValid: number = 1;
  missing : any="";

  recordsPerPage: number = 10;
   records: any = [];


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageRegionBank(0, this.isValid);
    this.loadAllBank();
    this.regionBankStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,private userRightsService: UserRightsService,
    public regionBankService: RegionBankService,
              private drkServiceService: DrkServiceService

  ) { }

  // Bank list
  loadAllBank() {
    return this.regionBankService.GetAllBank().subscribe((data: {}) => {
      this.BankList = data;
    })
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : " + page);
    this.selectedPage = page;
    this.getPageRegionBank(page, this.isValid);
  }

  getPageRegionBank(page: number, isValid: number): void {
    this.regionBankService.getPageRegionBank(page, this.recordsPerPage,this.searchText, isValid,this.missing)
      .subscribe(page => this.pageRegionBank = page)
  }


  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.regionBankService.getPageRegionBank(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing)
      .subscribe(page => this.pageRegionBank = page);
  }



  // Delete Bank
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.bank, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.bank, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.bank, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.bank, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'approve') {
        observable = this.regionBankService.ApproveBank(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.regionBankService.FinalizeBank(event.id);
      } else if (event.flag == 'delete') {
        observable = this.regionBankService.DeleteBank(event.id);
      } else if (event.flag == 'reject') {
        observable = this.regionBankService.RejectBank(event.id);
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
            this.loadAllBank();
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

  searchBank() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageRegionBank(this.selectedPage - 1, this.isValid);
  }

  sortData(sort: Sort) {
    const data = this.pageRegionBank.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageRegionBank.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageRegionBank.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'state':
          return compare(firstValue.state, secondValue.state, isAsc);
        case 'bank':
          return compare(firstValue.bank, secondValue.bank, isAsc);
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
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      console.log("Inside if");
    this.getPageRegionBank(this.selectedPage - 1,this.isValid);
    this.regionBankStatus = globalConstants;
    }else{
      console.log("Inside else");
    this.ngOnInit();
    }
  }

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('regional_bank').subscribe(res => {
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

  onClickMissing(){
    this.missing = 1;
    this.getPageRegionBank(0,this.isValid);
  }

  moveToMaster(id){
    this.regionBankService.moveToMaster(id).subscribe(res => {
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

import { Component, OnInit, ViewChild } from '@angular/core';
import { GeneralModeOfPaymentService } from '../services/general-mode-of-payment.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../agri/services/bulk-data.service';
import { PageGeneralModeOfPayment } from '../models/PageGeneralModeOfPayment';

@Component({
  selector: 'app-general-mode-of-payment',
  templateUrl: './general-mode-of-payment.component.html',
  styleUrls: ['./general-mode-of-payment.component.scss']
})
export class GeneralModeOfPaymentComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  modeOfPaymentStatus;
  modeOfPaymentList: any = [];


  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];

  pageGeneralModeOfPayment: PageGeneralModeOfPayment;


  ngOnInit() {
    this.records = ['1', '50', '100', '200', '250'];
	   this.getModeOfPaymentPagenatedList(0);
    // this.loadAllModeOfPayment();
    this.modeOfPaymentStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public generalModeOfPaymentService: GeneralModeOfPaymentService,
    private userRightsService: UserRightsService,
  ) { }

  getModeOfPaymentPagenatedList(page: number): void {
    this.generalModeOfPaymentService.getModeOfPaymentPagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageGeneralModeOfPayment = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.generalModeOfPaymentService.getModeOfPaymentPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageGeneralModeOfPayment = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getModeOfPaymentPagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getModeOfPaymentPagenatedList(this.selectedPage - 1);
}

  // ModeOfPayment list
  loadAllModeOfPayment() {
    return this.generalModeOfPaymentService.GetAllModeOfPayment().subscribe((data: {}) => {
      this.modeOfPaymentList = data;
    })
  }

  // // Delete ModeOfPayment
  // deleteModeOfPayment(data){
  //   var index = index = this.modeOfPaymentList.map(x => {return x.name}).indexOf(data.name);
  //    return this.generalModeOfPaymentService.DeleteModeOfPayment(data.id).subscribe(res => {
  //     this.modeOfPaymentList.splice(index, 1)
  //      console.log('ModeOfPayment deleted!')
  //    })
  // }

  // Delete 
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.modeOfPayment, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.modeOfPayment, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.modeOfPayment, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.modeOfPayment, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        observable = this.generalModeOfPaymentService.ApproveModeOfPayment(event.id)
      } else if (event.flag == "finalize") {
        observable = this.generalModeOfPaymentService.FinalizeModeOfPayment(event.id)
      } else if (event.flag == "delete") {
        observable = this.generalModeOfPaymentService.DeleteModeOfPayment(event.id)
      } else if (event.flag == "reject") {
        observable = this.generalModeOfPaymentService.RejectModeOfPayment(event.id)
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
    // this.ngOnInit();
    // this.selectedPage = 1;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getModeOfPaymentPagenatedList(this.selectedPage - 1);
    this.modeOfPaymentStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }


  sortData(sort: Sort) {
    const data = this.pageGeneralModeOfPayment.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageGeneralModeOfPayment.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageGeneralModeOfPayment.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case 'modeOfPayment':
            return compare(firstValue.modeOfPayment, secondValue.modeOfPayment, isAsc);
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



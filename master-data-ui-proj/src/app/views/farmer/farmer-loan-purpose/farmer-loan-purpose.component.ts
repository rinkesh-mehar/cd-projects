import { Component, OnInit, ViewChild } from '@angular/core';
import { FarmerLoanPurposeService } from '../services/farmer-loan-purpose.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../agri/services/bulk-data.service';
import { PageFarmerLoanPurpose } from '../models/PageFarmerLoanPurpose';

@Component({
  selector: 'app-farmer-loan-purpose',
  templateUrl: './farmer-loan-purpose.component.html',
  styleUrls: ['./farmer-loan-purpose.component.scss']
})
export class FarmerLoanPurposeComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  loanPurposeStatus;
  LoanPurposeList: any = [];

  searchText: any = '';
   selectedPage: number = 1;
   maxSize =10;
   recordsPerPage: number = 10;
   records: any = [];

   pageFarmerLoanPurpose: PageFarmerLoanPurpose;


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getLoanPurposePagenatedList(0);
    // this.loadAllLoanPurpose();
    this.loanPurposeStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public farmerLoanPurposeService: FarmerLoanPurposeService,
    private userRightsService: UserRightsService,

  ) { }

  getLoanPurposePagenatedList(page: number): void {
    this.farmerLoanPurposeService.getLoanPurposePagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageFarmerLoanPurpose = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.farmerLoanPurposeService.getLoanPurposePagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageFarmerLoanPurpose = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getLoanPurposePagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getLoanPurposePagenatedList(this.selectedPage - 1);
}

  // LoanPurpose list
  loadAllLoanPurpose() {
    return this.farmerLoanPurposeService.GetAllLoanPurpose().subscribe((data: {}) => {
      this.LoanPurposeList = data;
    })
  }

  // Delete LoanPurpose
  // deleteLoanPurpose(data) {
  //   var index = index = this.LoanPurposeList.map(x => { return x.name }).indexOf(data.name);
  //   return this.farmerLoanPurposeService.DeleteLoanPurpose(data.id).subscribe(res => {
  //     this.LoanPurposeList.splice(index, 1)
  //     console.log('LoanPurpose deleted!')
  //   })
  // }



  // Delete LoanPurpose
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
        observable = this.farmerLoanPurposeService.ApproveLoanPurpose(event.id)
      } else if (event.flag == "finalize") {
        observable = this.farmerLoanPurposeService.FinalizeLoanPurpose(event.id)
      } else if (event.flag == "delete") {
        observable = this.farmerLoanPurposeService.DeleteLoanPurpose(event.id)
      } else if (event.flag == "reject") {
        observable = this.farmerLoanPurposeService.RejectLoanPurpose(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
            this.loadAllLoanPurpose();
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
    this.getLoanPurposePagenatedList(this.selectedPage - 1);
    this.loanPurposeStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }

  sortData(sort: Sort) {
    const data = this.pageFarmerLoanPurpose.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageFarmerLoanPurpose.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageFarmerLoanPurpose.content = data.sort((firstValue, secondValue) => {
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

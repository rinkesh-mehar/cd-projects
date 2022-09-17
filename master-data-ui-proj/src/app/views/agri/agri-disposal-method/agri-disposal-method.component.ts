import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriDisposalMethodService } from '../services/agri-disposal-method.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../services/bulk-data.service';
import { PageAgriDisposalMethod } from '../models/PageAgriDisposalMethod';

@Component({
  selector: 'app-agri-disposal-method',
  templateUrl: './agri-disposal-method.component.html',
  styleUrls: ['./agri-disposal-method.component.scss']
})
export class AgriDisposalMethodComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

 isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
    disposalMethodStatus;
  DisposalMethodList: any = [];

  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];

  pageAgriDisposalMethod: PageAgriDisposalMethod;



  ngOnInit() {
    // this.loadAllDisposalMethod();
    this.records = ['20', '50', '100', '200', '250'];
   this.getDisposalMethodPagenatedList(0);
    this.disposalMethodStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public agriDisposalMethodService: AgriDisposalMethodService,
    private userRightsService: UserRightsService,

  ){ }

  getDisposalMethodPagenatedList(page: number): void {
    this.agriDisposalMethodService.getDisposalMethodPagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageAgriDisposalMethod = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriDisposalMethodService.getDisposalMethodPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageAgriDisposalMethod = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getDisposalMethodPagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getDisposalMethodPagenatedList(this.selectedPage - 1);
}

   // DisposalMethod list
   loadAllDisposalMethod() {
    return this.agriDisposalMethodService.GetAllDisposalMethod().subscribe((data: {}) => {
      this.DisposalMethodList = data;
    })
  }

    // Delete DisposalMethod
    // deleteDisposalMethod(data){
    //   var index = index = this.DisposalMethodList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriDisposalMethodService.DeleteDisposalMethod(data.id).subscribe(res => {
    //     this.DisposalMethodList.splice(index, 1)
    //      console.log('DisposalMethod deleted!')
    //    })
    // }


  // Delete DisposalMethod
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
        observable = this.agriDisposalMethodService.ApproveDisposalMethod(event.id)
      } else if (event.flag == "finalize") {
        observable = this.agriDisposalMethodService.FinalizeDisposalMethod(event.id)
      } else if (event.flag == "delete") {
        observable = this.agriDisposalMethodService.DeleteDisposalMethod(event.id)
      } else if (event.flag == "reject") {
        observable = this.agriDisposalMethodService.RejectDisposalMethod(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            // this._statusMsg = res.message;
            this.loadAllDisposalMethod();
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
    this.getDisposalMethodPagenatedList(this.selectedPage - 1);
    this.disposalMethodStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }

    sortData(sort: Sort) {
      const data = this.pageAgriDisposalMethod.content.slice();
      if (!sort.active || sort.direction == '') {
        this.pageAgriDisposalMethod.content = data;
        return;
      }
    
      function compare(firstValue, secondValue, isAsc: boolean) {
        return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
      }
    
      this.pageAgriDisposalMethod.content = data.sort((firstValue, secondValue) => {
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

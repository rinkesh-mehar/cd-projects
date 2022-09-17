import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriHealthParameterService } from '../../services/agri-health-parameter.service';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../services/bulk-data.service';
import { PageAgriHealthParameter } from '../../models/PageAgriHealthParameter';
// import { PageAgriCommodity } from '../models/PageAgriCommodity';
@Component({
  selector: 'app-agri-health-parameter',
  templateUrl: './agri-health-parameter.component.html',
  styleUrls: ['./agri-health-parameter.component.scss']
})
export class AgriHealthParameterComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  healthParameterStatus;
  HealthParameterList: any = [];
  
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];

  pageAgriHealthParameter: PageAgriHealthParameter;
  // pageCommodity :  PageAgriCommodity;
  // selectedPage : number = 0;


  ngOnInit() {
    // this.getPageAgriCommodity(0);
  //  this.loadAllHealthParameter();
  this.records = ['20', '50', '100', '200', '250'];
  this.getHealthParameterPagenatedList(0);
   this.healthParameterStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public agriHealthParameterService : AgriHealthParameterService,
    private userRightsService: UserRightsService,
  ){ }

  getHealthParameterPagenatedList(page: number): void {
    this.agriHealthParameterService.getHealthParameterPagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageAgriHealthParameter = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriHealthParameterService.getHealthParameterPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageAgriHealthParameter = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getHealthParameterPagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getHealthParameterPagenatedList(this.selectedPage - 1);
}


   // HealthParameter list
   loadAllHealthParameter() {
    return this.agriHealthParameterService.GetAllHealthParameter().subscribe((data: {}) => {
      this.HealthParameterList = data;
    })
  }


  // onSelect(page: number): void {
  //   console.log("selected page : "+page);
  //   this.selectedPage=page;
  //   this.getPageAgriCommodity(page);
  // }

  // getPageAgriCommodity(page:number): void {
  //   this.agriHealthParameterService.getPageAgriCommodity(page)
  //       .subscribe(page => this.pageCommodity = page)
  // }

    // // Delete HealthParameter
    // deleteHealthParameter(data){
    //   var index = index = this.HealthParameterList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriHealthParameterService.DeleteHealthParameter(data.id).subscribe(res => {
    //     this.HealthParameterList.splice(index, 1)
    //      console.log('HealthParameter deleted!')
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
        observable = this.agriHealthParameterService.ApproveHealthParameter(event.id)
      } else if (event.flag == "finalize") {
        observable = this.agriHealthParameterService.FinalizeHealthParameter(event.id)
      } else if (event.flag == "delete") {
        observable = this.agriHealthParameterService.DeleteHealthParameter(event.id)
      } else if (event.flag == "reject") {
        observable = this.agriHealthParameterService.RejectHealthParameter(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            // this._statusMsg = res.message;
            this.loadAllHealthParameter();
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
    this.getHealthParameterPagenatedList(this.selectedPage - 1);
    this.healthParameterStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }
    sortData(sort: Sort) {
      const data = this.pageAgriHealthParameter.content.slice();
      if (!sort.active || sort.direction == '') {
        this.pageAgriHealthParameter.content = data;
        return;
      }
    
      function compare(firstValue, secondValue, isAsc: boolean) {
        return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
      }
    
      this.pageAgriHealthParameter.content = data.sort((firstValue, secondValue) => {
        const isAsc = sort.direction == 'asc';
        switch (sort.active) {
          case globalConstants.ID:
            return compare(+firstValue.id, +secondValue.id, isAsc);
            case globalConstants.NAME:
            return compare(+firstValue.id, +secondValue.id, isAsc);
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

import { PageAgriStressServity } from './../../models/PageAgriStressServity';
import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriStressSeverityService } from '../../services/agri-stress-severity.service';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { BulkDataService } from '../../services/bulk-data.service';
import { Sort } from '@angular/material';

@Component({
  selector: 'app-stress-severity',
  templateUrl: './stress-severity.component.html',
  styleUrls: ['./stress-severity.component.scss']
})
export class StressSeverityComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  stressSeverityStatus;
  StressSeverityList: any = [];

  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];

  pageAgriStressServity: PageAgriStressServity;


  ngOnInit() {
    (document.querySelector('thead th input') as HTMLInputElement).checked = false
    // this.loadAllStressSeverity();
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageStressSeverity(0);
    this.stressSeverityStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public agriStressSeverityService: AgriStressSeverityService,
    private userRightsService: UserRightsService,
  ){ }

  getPageStressSeverity(page: number) {
    return this.agriStressSeverityService.getPageStressSeverity(page, this.recordsPerPage,this.searchText).subscribe((data) => {
      this.pageAgriStressServity = data;
    })
  }

   // AgrochemicalApplication list
   loadAllStressSeverity() {
    return this.agriStressSeverityService.GetAllStressSeverity().subscribe((data: {}) => {
      this.StressSeverityList = data;
    })
  }

    // // Delete AgrochemicalApplication
    // deleteStressSeverity(data){
    //   var index = index = this.StressSeverityList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriStressSeverityService.DeleteStressSeverity(data.id).subscribe(res => {
    //     this.StressSeverityList.splice(index, 1)
    //      console.log('StressSeverity deleted!')
    //    })
    // }

          // Delete Commodity
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

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        observable = this.agriStressSeverityService.ApproveStressSeverity(event.id)
      } else if (event.flag == "finalize") {
        observable = this.agriStressSeverityService.FinalizeStressSeverity(event.id)
      } else if (event.flag == "delete") {
        observable = this.agriStressSeverityService.DeleteStressSeverity(event.id)
      } else if (event.flag == "reject") {
        observable = this.agriStressSeverityService.RejectStressSeverity(event.id)
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

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPageStressSeverity(page);
}

  searchStressSeverity() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageStressSeverity(this.selectedPage - 1);
  }

  modalSuccess($event: any) {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // this.ngOnInit();
    // this.selectedPage = 1;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      console.log("Inside if");
    this.getPageStressSeverity(this.selectedPage - 1);
    this.stressSeverityStatus = globalConstants;
    }else{
      console.log("Inside else");
    this.ngOnInit();
    }
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriStressSeverityService.getPageStressSeverity(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageAgriStressServity = page);
  }

  sortData(sort: Sort) {
    const data = this.pageAgriStressServity.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriStressServity.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageAgriStressServity.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case globalConstants.NAME:
            return compare(firstValue.name, secondValue.name, isAsc);
        case 'value':
          return compare(firstValue.value, secondValue.value, isAsc);
          case globalConstants.STATUS:
          return compare(firstValue.status, secondValue.status, isAsc);
        default:
          return 0;
      }
    });
  }

}

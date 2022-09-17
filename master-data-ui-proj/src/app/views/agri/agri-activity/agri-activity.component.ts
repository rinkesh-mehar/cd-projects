import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriActivityService } from '../services/agri-activity.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { globalConstants } from '../../global/globalConstants';
import { UserRightsService } from '../../services/user-rights.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../services/bulk-data.service';
import { PageAgriActivity } from '../models/PageAgriActivity';

@Component({
  selector: 'app-agri-activity',
  templateUrl: './agri-activity.component.html',
  styleUrls: ['./agri-activity.component.scss']
})
export class AgriActivityComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  activityStatus;
  ActivityList: any = [];

  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];

  pageAgriActivity: PageAgriActivity;
  myusername;


  ngOnInit() {
    // this.loadAllActivity();
    this.records = ['20', '50', '100', '200', '250'];
   this.getActivityPagenatedList(0);
    this.activityStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public agriActivityService: AgriActivityService,
    private userRightsService: UserRightsService,
  ){ }

  getActivityPagenatedList(page: number): void {
    this.agriActivityService.getActivityPagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageAgriActivity = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriActivityService.getActivityPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageAgriActivity = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getActivityPagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getActivityPagenatedList(this.selectedPage - 1);
}

   // Activity list
   loadAllActivity() {
    return this.agriActivityService.GetAllActivity().subscribe((data: {}) => {
      this.ActivityList = data;
    })
  }



    // // Delete Activity
    // deleteActivity(data){
    //   var index = index = this.ActivityList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriActivityService.DeleteActivity(data.id).subscribe(res => {
    //     this.ActivityList.splice(index, 1)
    //      console.log('Activity deleted!')
    //    })
    // }

          // Delete Village
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.name, data);
  }
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
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'approve') {
        this.approveActivity(event);
      } else if (event.flag == 'finalize') {
        this.finalizeActivity(event);
      } else if (event.flag == 'delete') {
        this.deleteActivity(event);
      } else if (event.flag == 'reject') {
        this.rejectActivity(event);
      }

    }
  }

  approveActivity(event: any) {
    return this.agriActivityService.ApproveActivity(event.id).subscribe(res => {
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

  finalizeActivity(event: any) {
    return this.agriActivityService.FinalizeActivity(event.id).subscribe(res => {
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

  deleteActivity(event) {
    return this.agriActivityService.DeleteActivity(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.ActivityList.splice(event.index, 1);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
    });
  }

  rejectActivity(event: any) {
    return this.agriActivityService.RejectActivity(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.ActivityList.splice(event.index, 1);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
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
    this.getActivityPagenatedList(this.selectedPage - 1);
    this.activityStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }

  sortData(sort: Sort) {
    const data = this.pageAgriActivity.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriActivity.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageAgriActivity.content = data.sort((firstValue, secondValue) => {
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

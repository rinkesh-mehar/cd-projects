import { Component, OnInit, ViewChild } from '@angular/core';
import { Sort } from '@angular/material';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { globalConstants } from '../../global/globalConstants';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { PageSyncConfiguration } from '../models/PageSyncConfiguration';
import { SyncConfigurationService } from '../services/sync-configuration.service';

@Component({
  selector: 'app-sync-config',
  templateUrl: './sync-config.component.html',
  styleUrls: ['./sync-config.component.scss']
})
export class SyncConfigComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  synchConfigStatus;

  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];

  pageSyncConfiguration: PageSyncConfiguration;
  myusername;


  ngOnInit() {
    // this.loadAllActivity();
    this.records = ['20', '50', '100', '200', '250'];
   this.getSyncConfigPagenatedList(0);
    this.synchConfigStatus = globalConstants;
  }

  constructor(public syncConfigurationService: SyncConfigurationService,
    private userRightsService: UserRightsService,
  ){ }

  getSyncConfigPagenatedList(page: number): void {
    this.syncConfigurationService.getSyncConfigurationPagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageSyncConfiguration = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.syncConfigurationService.getSyncConfigurationPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageSyncConfiguration = page);
  }

  onSelect(page: number): void {
    this.selectedPage = page;
    this.getSyncConfigPagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getSyncConfigPagenatedList(this.selectedPage - 1);
}

  activate(data, i) {
    data.flag = "activate"
    this.confirmModal.showModal(globalConstants.activeDataTitle, globalConstants.activeDataMsg, data);
  }

  reject(data) {
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg, data);
  }

   modalConfirmation(event) {
    console.log(event);
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'activate') {
        this.activateSyncConfig(event);
      }else if (event.flag == 'reject') {
        this.rejectSyncConfig(event);
      }

    }
  }

  activateSyncConfig(event: any) {
    return this.syncConfigurationService.activateSyncConfiguration(event.id).subscribe(res => {
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
  
  rejectSyncConfig(event: any) {
    return this.syncConfigurationService.rejectSyncConfiguration(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          // this.ActivityList.splice(event.index, 1);
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
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getSyncConfigPagenatedList(this.selectedPage - 1);
    this.synchConfigStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }

  sortData(sort: Sort) {
    const data = this.pageSyncConfiguration.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageSyncConfiguration.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageSyncConfiguration.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
        return compare(+firstValue.id, +secondValue.id, isAsc);
      case 'screen':
        return compare(firstValue.screen, secondValue.screen, isAsc);
      case 'roleName':
        return compare(firstValue.roleName, secondValue.roleName, isAsc);
      case 'labelName':
        return compare(firstValue.labelName, secondValue.labelName, isAsc);
      case 'schemaName':
        return compare(firstValue.schemaName, secondValue.schemaName, isAsc);
      case 'zippingLevel':
         return compare(firstValue.zippingLevel, secondValue.zippingLevel, isAsc);
      // case 'syncFrequency':
      //       return compare(firstValue.syncFrequency, secondValue.syncFrequency, isAsc);
      case 'status':
        return compare(firstValue.status, secondValue.status, isAsc);
      default:
        return 0;
      }
    });
  }
}

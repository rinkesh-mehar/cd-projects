import { Component, OnInit, ViewChild } from '@angular/core';
import { ZonalFieldActivityService } from '../services/zonal-field-activity.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import { PageZonalFieldActivity } from '../models/PageZonalFieldActivity';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { BulkDataService } from '../services/bulk-data.service';
import {DrkServiceService} from '../../services/drk-service.service';

@Component({
  selector: 'app-zonal-field-activity',
  templateUrl: './zonal-field-activity.component.html',
  styleUrls: ['./zonal-field-activity.component.scss']
})
export class ZonalFieldActivityComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  imgUrl : string;
a
  FieldActivityList: any = [];
  fieldActivityStatus;
  pageAgriFieldActivity: PageZonalFieldActivity;
  selectedPage: number = 1;
  maxSize: number = 10;
  searchText: any = "";
  isValid: number = 1;
  missing : any="";
  recordsPerPage: number = 10;
  records: any = [];

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    // this.loadAllFieldActivity();
    this.getPageAgriFieldActivity(0, this.isValid);
    this.fieldActivityStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
              public zonalFieldActivityService: ZonalFieldActivityService,
              private userRightsService: UserRightsService,
              private drkServiceService: DrkServiceService
  ) {
  }

   // FieldActivity list
   loadAllFieldActivity() {
    return this.zonalFieldActivityService.GetAllFieldActivity().subscribe((data: {}) => {
      this.FieldActivityList = data;
    })
  }

    // // Delete FieldActivity
    // deleteFieldActivity(data){
    //   var index = index = this.FieldActivityList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriFieldActivityService.DeleteFieldActivity(data.id).subscribe(res => {
    //     this.FieldActivityList.splice(index, 1)
    //      console.log('FieldActivity deleted!')
    //    })
    // }

    onSelect(page: number): void {
      (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
      console.log("selected page : " + page);
      // this.selectedPage = page;
      this.getPageAgriFieldActivity(page, this.isValid);
    }
  
    getPageAgriFieldActivity(page: number, isValid: number): void {
      this.zonalFieldActivityService.getPageAgriFieldActivity(page, this.recordsPerPage, this.searchText, isValid,this.missing)
        .subscribe(page => this.pageAgriFieldActivity = page)
    }


  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.zonalFieldActivityService.getPageAgriFieldActivity(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing)
      .subscribe(page => this.pageAgriFieldActivity = page);
  }

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
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        this.approveFieldActivity(event);
      } else if (event.flag == "finalize") {
        this.finalizeFieldActivity(event);
      } else if (event.flag == "delete") {
        this.deleteFieldActivity(event);
      }else if (event.flag == "reject") {
        this.rejectFieldActivity(event);
      }

    }
  }

  approveFieldActivity(event: any) {
    return this.zonalFieldActivityService.ApproveFieldActivity(event.id).subscribe(res => {
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

  finalizeFieldActivity(event: any) {
    return this.zonalFieldActivityService.FinalizeFieldActivity(event.id).subscribe(res => {
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

  deleteFieldActivity(event) {
    return this.zonalFieldActivityService.DeleteFieldActivity(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.FieldActivityList.splice(event.index, 1);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
    });
  }

  rejectFieldActivity(event: any) {
    return this.zonalFieldActivityService.RejectFieldActivity(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.FieldActivityList.splice(event.index, 1);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
    });
  }

  searchFieldActivity() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageAgriFieldActivity(this.selectedPage - 1, this.isValid);
  }

  sortData(sort: Sort) {
    const data = this.pageAgriFieldActivity.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriFieldActivity.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageAgriFieldActivity.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case globalConstants.ID:
            return compare(+firstValue.id, +secondValue.id, isAsc);
          case 'state':
            return compare(firstValue.state , secondValue.state, isAsc);
          case 'aczName':
            return compare(firstValue.aczName , secondValue.aczName, isAsc);
          case 'zonalCommodity':
            return compare(firstValue.zonalCommodity , +secondValue.zonalCommodity, isAsc);
          // case 'zonalVariety':
          //   return compare(firstValue.zonalVariety , +secondValue.zonalVariety, isAsc);
          case 'phenophaseName':
            return compare(firstValue.phenophaseName , secondValue.phenophaseName, isAsc);
        case 'phenophase':
          return compare(firstValue.phenophase, secondValue.phenophase, isAsc);
        case globalConstants.NAME:
          return compare(firstValue.name, secondValue.name, isAsc);
        case 'description':
          return compare(firstValue.description, secondValue.description, isAsc);
        case globalConstants.STATUS:
          return compare(firstValue.status, secondValue.status, isAsc);
        default:
          return 0;    
      }
    });
  }

  modalSuccess($event: any) {
    if (this.selectedPage > 0){

      this.onSelect((this.selectedPage - 1));
      (document.querySelector('thead th input') as HTMLInputElement).checked = false
    } else {
      this.onSelect(this.selectedPage);
      (document.querySelector('thead th input') as HTMLInputElement).checked = false
    }
  }

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('agri_field_activity').subscribe(res => {
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
    this.getPageAgriFieldActivity(0,this.isValid);
  }

  moveToMaster(id){
    this.zonalFieldActivityService.moveToMaster(id).subscribe(res => {
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


  getImageUrl(event: any){
    this.imgUrl = event.target.src;
    console.log("image found..." + this.imgUrl);
  }
  

}

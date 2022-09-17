import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriStressTypeService } from '../services/agri-stress-type.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { BulkDataService } from '../services/bulk-data.service';
import { PageAgriStressType } from '../models/PageAgriStressType';

@Component({
  selector: 'app-agri-stress-type',
  templateUrl: './agri-stress-type.component.html',
  styleUrls: ['./agri-stress-type.component.scss']
})
export class AgriStressTypeComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  stressTypeStatus;
  StressTypeList: any = [];

  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];

  pageAgriStressType: PageAgriStressType;


  ngOnInit() {
    (document.querySelector('thead th input') as HTMLInputElement).checked = false
    // this.loadAllStressType();
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageStressType(0);
    this.stressTypeStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public agriStressTypeService: AgriStressTypeService,
    private userRightsService: UserRightsService,
  ){ }

  getPageStressType(page: number) {
    return this.agriStressTypeService.getPageStressType(page, this.recordsPerPage,this.searchText).subscribe((data) => {
      this.pageAgriStressType = data;
    })
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPageStressType(page);
}

  searchStressType() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageStressType(this.selectedPage - 1);
  }

  modalSuccess($event: any) {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // this.ngOnInit();
    // this.selectedPage = 1;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      console.log("Inside if");
    this.getPageStressType(this.selectedPage - 1);
    this.stressTypeStatus = globalConstants;
    }else{
      console.log("Inside else");
    this.ngOnInit();
    }
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriStressTypeService.getPageStressType(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageAgriStressType = page);
  }

   // tressType list
   loadAllStressType() {
    return this.agriStressTypeService.GetAllStressType().subscribe((data: {}) => {
      this.StressTypeList = data;
    })
  }

    // // Delete StressType
    // deleteStressType(data){
    //   var index = index = this.StressTypeList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriStressTypeService.DeleteStressType(data.id).subscribe(res => {
    //     this.StressTypeList.splice(index, 1)
    //      console.log('StressType deleted!')
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
        this.approveStressType(event);
      } else if (event.flag == "finalize") {
        this.finalizeStressType(event);
      } else if (event.flag == "delete") {
        this.deleteStressType(event);
      }else if (event.flag == "reject") {
        this.rejectStressType(event);
      }

    }
  }

  approveStressType(event: any) {
    return this.agriStressTypeService.ApproveStressType(event.id).subscribe(res => {
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

  finalizeStressType(event: any) {
    return this.agriStressTypeService.FinalizeStressType(event.id).subscribe(res => {
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

  deleteStressType(event) {
    return this.agriStressTypeService.DeleteStressType(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.StressTypeList.splice(event.index, 1);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
    });
  }

  rejectStressType(event: any) {
    return this.agriStressTypeService.RejectStressType(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.StressTypeList.splice(event.index, 1);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
    });
  }

  sortData(sort: Sort) {
    const data = this.pageAgriStressType.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriStressType.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageAgriStressType.content = data.sort((firstValue, secondValue) => {
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

}

import { Component, OnInit, ViewChild } from '@angular/core';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../agri/services/bulk-data.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { globalConstants } from '../../global/globalConstants';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { PageValidateStress } from '../models/PageValidateStress';
import { ValidateStressService } from '../services/validate-stress.service';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-validate-stress',
  templateUrl: './validate-stress.component.html',
  styleUrls: ['./validate-stress.component.scss']
})
export class ValidateStressComponent implements OnInit {


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
  maxSize = 10;
  recordsPerPage: number = 10;
  records: any = [];

  pageValidateStress: PageValidateStress;


  ngOnInit(): void {
    this.records = ['20', '50', '100', '200', '250'];
    this.getValidateStressPagenatedList(0);
     this.activityStatus = globalConstants;
    

  }
  constructor(
    public bulkDatas: BulkDataService,
    public validateStressService: ValidateStressService,
    private userRightsService: UserRightsService,
  ) { }

  getValidateStressPagenatedList(page: number): void {
    this.validateStressService.getValidateStressPagenatedList(page, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageValidateStress = page);

  }
  search() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getValidateStressPagenatedList(this.selectedPage - 1);
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.validateStressService.getValidateStressPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageValidateStress = page);
  }

  onSelect(page: number): void {
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getValidateStressPagenatedList(page);
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
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.name, data);
  }


approveActivity(event: any) {
  return this.validateStressService.ApproveActivity(event.id).subscribe(res => {
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
  return this.validateStressService.FinalizeActivity(event.id).subscribe(res => {
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

rejectActivity(event: any) {
  return this.validateStressService.RejectActivity(event.id).subscribe(res => {
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
  // this.ngOnInit();
  // this.selectedPage = 1;

  console.log("page : " + this.selectedPage);
  if(this.selectedPage >= 2){
    // console.log("Inside if");
  this.getValidateStressPagenatedList(this.selectedPage - 1);
  this.activityStatus = globalConstants;
  }else{
    // console.log("Inside else");
  this.ngOnInit();
  }
}



modalConfirmation(event) {
  console.log(event);
  if (event) {
    this.isSubmitted = true;
    if (event.flag == 'approve') {
      this.approveActivity(event);
    } else if (event.flag == 'finalize') {
      this.finalizeActivity(event);
    }
    //  else if (event.flag == 'delete') {
    //   this.deleteActivity(event);
     else if (event.flag == 'reject') {
      this.rejectActivity(event);
    }

  }

}




  sortData(sort: Sort) {
    const data = this.pageValidateStress.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageValidateStress.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

  }


  bulkData(key, tableName) {

    let Values = []
    let getValue = document.querySelectorAll<HTMLInputElement>('table tbody input:checked')

    getValue.forEach(function (data, i) {
      Values.push(data.value)
    })
    let AllData = { status: key, tableName: tableName, ids: Values.toString() }

    this.bulkDatas.getData(AllData)
      .subscribe(data => {
        data
        if (data.success == true) {
          this.successModal.showModal('SUCCESS', data.message, '');

        } else {
          this.errorModal.showModal('ERROR', data.error, '');

        }

      })

  }


}

import { PageAgriDoseFactor } from './../../models/PageAgriDoseFactor';
import { Component, OnInit, ViewChild } from '@angular/core';

import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import { AgriDoseFactorService } from '../../services/agri-dose-factor.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {Sort} from '@angular/material';
import { BulkDataService } from '../../services/bulk-data.service';

@Component({
  selector: 'app-agri-dose-factor',
  templateUrl: './agri-dose-factor.component.html',
  styleUrls: ['./agri-dose-factor.component.scss']
})
export class AgriDoseFactorComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  doseFactorListStatus;
  DoseFactorList: any = [];

  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];

  pageAgriDoseFactor: PageAgriDoseFactor;


  ngOnInit() {
    (document.querySelector('thead th input') as HTMLInputElement).checked = false
    // this.loadAllAgriDoseFactor();
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageDoseFactor(0);
    this.doseFactorListStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    private AgriDoseFactorService: AgriDoseFactorService,
    private userRightsService: UserRightsService,
  ){ }

   // loadAllAgriDoseFactor list
   loadAllAgriDoseFactor() {
    return this.AgriDoseFactorService.GetAllAgriDoseFactor().subscribe((data: any) => {
      this.DoseFactorList = data;
    })
  }

  getPageDoseFactor(page: number) {
    return this.AgriDoseFactorService.getPageDoseFactor(page, this.recordsPerPage,this.searchText).subscribe((data) => {
      this.pageAgriDoseFactor = data;
    })
  }


    // // Delete AgriDoseFactor
    // deleteAgriDoseFactor(data){
    //   var index = index = this.EcosystemList.map(x => {return x.name}).indexOf(data.name);
    //    return this.AgriDoseFactorService.DeleteAgriDoseFactor(data.id).subscribe(res => {
    //     this.EcosystemList.splice(index, 1)
    //      console.log('Agri Ecosystem deleted!')
    //    })
    // }

      // Delete 
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.doseFactor, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.doseFactor, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.doseFactor, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.doseFactor, data);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPageDoseFactor(page);
}

  searchStressSeverity() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageDoseFactor(this.selectedPage - 1);
  }

  modalSuccess($event: any) {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // this.ngOnInit();
    // this.selectedPage = 1;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      console.log("Inside if");
    this.getPageDoseFactor(this.selectedPage - 1);
    this.doseFactorListStatus = globalConstants;
    }else{
      console.log("Inside else");
    this.ngOnInit();
    }
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.AgriDoseFactorService.getPageDoseFactor(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageAgriDoseFactor = page);
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
      if (event.flag == 'approve') {
        observable = this.AgriDoseFactorService.ApproveDoseFactor(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.AgriDoseFactorService.FinalizeDoseFactor(event.id);
      } else if (event.flag == 'delete') {
        observable = this.AgriDoseFactorService.DeleteAgriDoseFactor(event.id);
      } else if (event.flag == 'reject') {
        observable = this.AgriDoseFactorService.RejectDoseFactor(event.id);
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

  sortData(sort: Sort) {
    const data = this.pageAgriDoseFactor.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriDoseFactor.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageAgriDoseFactor.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'doseFactor':
          return compare(firstValue.doseFactor, secondValue.doseFactor, isAsc);
        case globalConstants.STATUS:
          return compare(firstValue.status, secondValue.status, isAsc);
        default:
          return 0;
      }
    });
  }

 
}

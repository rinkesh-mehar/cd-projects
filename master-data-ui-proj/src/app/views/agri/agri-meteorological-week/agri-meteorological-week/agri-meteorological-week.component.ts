import { Component, OnInit, ViewChild } from '@angular/core';

import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import { AgriMeteorologicalWeekService } from '../../services/agri-meteorological-week.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {Router} from '@angular/router';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../services/bulk-data.service';

@Component({
  selector: 'app-agri-meteorological-week',
  templateUrl: './agri-meteorological-week.component.html',
  styleUrls: ['./agri-meteorological-week.component.scss']
})
export class AgriMeteorologicalWeekComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  meteorologicalWeekStatus;
  MeteorologicalWeekList: any = [];


  months = [
    { 'name': "January", 'value': 1, 'days': 31 },
    { 'name': "February", 'value': 2, 'days': 29 },
    { 'name': "March", 'value': 3, 'days': 31 },
    { 'name': "April", 'value': 4, 'days': 30 },
    { 'name': "May", 'value': 5, 'days': 31 },
    { 'name': "June", 'value': 6, 'days': 30 },
    { 'name': "July", 'value': 7, 'days': 31 },
    { 'name': "August", 'value': 8, 'days': 31 },
    { 'name': "September", 'value': 9, 'days': 30 },
    { 'name': "October", 'value': 10, 'days': 31 },
    { 'name': "November", 'value': 11, 'days': 30 },
    { 'name': "December", 'value': 12, 'days': 31 }];

  ngOnInit() {
    this.loadAllAgriMeteorologicalWeek();
    this.meteorologicalWeekStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    private agriMeteorologicalWeekService: AgriMeteorologicalWeekService,
    private userRightsService: UserRightsService,
    public router: Router
  ) { }

  // loadAllAgriMeteorologicalWeek list
  loadAllAgriMeteorologicalWeek() {
    return this.agriMeteorologicalWeekService.GetAllAgriMeteorologicalWeek().subscribe((data: any) => {
      this.MeteorologicalWeekList = data;
    })
  }

  // // Delete AgriMeteorologicalWeek
  // deleteAgriMeteorologicalWeek(data){
  //   var index = index = this.EcosystemList.map(x => {return x.name}).indexOf(data.name);
  //    return this.AgriMeteorologicalWeekService.DeleteAgriMeteorologicalWeek(data.id).subscribe(res => {
  //     this.EcosystemList.splice(index, 1)
  //      console.log('Agri Ecosystem deleted!')
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
        observable = this.agriMeteorologicalWeekService.ApproveMeteorologicalWeek(event.id)
      } else if (event.flag == "finalize") {
        observable = this.agriMeteorologicalWeekService.FinalizeMeteorologicalWeek(event.id)
      } else if (event.flag == "delete") {
        observable = this.agriMeteorologicalWeekService.DeleteAgriMeteorologicalWeek(event.id)
      } else if (event.flag == "reject") {
        observable = this.agriMeteorologicalWeekService.RejectMeteorologicalWeek(event.id)
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

  // getMonthName(no){

  //   this.months[no-1].name 

  // }

  modalSuccess($event: any) {
    // this.router.navigate(['/agri/meteorological-week']);
    this.ngOnInit();
  }

  sortData(sort: Sort) {
    const data = this.MeteorologicalWeekList.slice();
    if (!sort.active || sort.direction == '') {
      this.MeteorologicalWeekList = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.MeteorologicalWeekList = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case 'weekNo':
            return compare(firstValue.weekNo, secondValue.weekNo, isAsc);
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

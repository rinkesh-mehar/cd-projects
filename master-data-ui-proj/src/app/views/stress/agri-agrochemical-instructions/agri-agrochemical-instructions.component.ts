import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../agri/services/bulk-data.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { globalConstants } from '../../global/globalConstants';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { PageAgriAgrochemicalInstructions } from '../model/PageAgriAgrochemicalInstructions';
import { AgriAgrochemicalInstructionService } from '../services/agri-agrochemical-instructions';

@Component({
  selector: 'app-agri-agrochemical-instructions',
  templateUrl: './agri-agrochemical-instructions.component.html',
  styleUrls: ['./agri-agrochemical-instructions.component.scss']
})
export class AgriAgrochemicalInstructionsComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  pageAgriAgrochemicalInstructions: PageAgriAgrochemicalInstructions;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  agrochemicalInstructionsStatus;
  departmentList: any = [];

  recordsPerPage: number = 10;
  records: any = [];
  

  constructor(private agriRecommendationService: AgriAgrochemicalInstructionService, private userRightsService: UserRightsService,public bulkDatas: BulkDataService) { }

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageAgrochemicalInstructionsList(0);
    this.agrochemicalInstructionsStatus = globalConstants;
  }

  getPageAgrochemicalInstructionsList(page: number): void {
    this.agriRecommendationService.getPageAgrochemicalInstructionsList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageAgriAgrochemicalInstructions = page);
}

loadData(event: any) {
  console.log('pages ', event.target.value);
  this.recordsPerPage = event.target.value || 10;
  this.agriRecommendationService.getPageAgrochemicalInstructionsList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    .subscribe(page => this.pageAgriAgrochemicalInstructions = page);
}

onSelect(page: number): void {
  (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
  this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPageAgrochemicalInstructionsList(page);
}

searchRecommendation() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageAgrochemicalInstructionsList(this.selectedPage - 1);
}

 // Reject 
 reject(data, i) {
  data.index = i;
  data.flag = "reject"
  this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + '"' + data.name + '"', data);
}

approve(data, i) {
  data.index = i;
  data.flag = "approve"
  this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + '"' + data.name + '"', data);
}

finalize(data, i) {
  data.index = i;
  data.flag = "finalize"
  this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + '"' + data.name + '"', data);
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
        observable = this.agriRecommendationService.approveAgrochemicalInstructions(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.agriRecommendationService.finalizeAgrochemicalInstructions(event.id);
      } else if (event.flag == 'reject') {
        observable = this.agriRecommendationService.rejectAgrochemicalInstructions(event.id);
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
  const data = this.pageAgriAgrochemicalInstructions.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageAgriAgrochemicalInstructions.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageAgriAgrochemicalInstructions.content = data.sort((firstValue, secondValue) => {
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

modalSuccess($event: any) {
  (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
  this.bulkDatas.disbled = true;
  // this.ngOnInit();
  // this.selectedPage = 1;

  console.log("page : " + this.selectedPage);
  if(this.selectedPage >= 2){
    // console.log("Inside if");
  this.getPageAgrochemicalInstructionsList(this.selectedPage - 1);
  this.agrochemicalInstructionsStatus = globalConstants;
  }else{
    // console.log("Inside else");
  this.ngOnInit();
  }
}

}

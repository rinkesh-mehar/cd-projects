import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriRemedialMeasuresService } from '../services/agri-remedial-measures.service';
//import { GeoStateService } from '../../geo/services/geo-state.service';
import { InfoModalComponent } from '../../global/info-modal/info-modal.component';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import { PageAgriRemedialMeasures } from '../models/PageAgriRemedialMeasures';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {DrkServiceService} from '../../services/drk-service.service';
import { BulkDataService } from '../services/bulk-data.service';

@Component({
  selector: 'app-agri-remedial-measures',
  templateUrl: './agri-remedial-measures.component.html',
  styleUrls: ['./agri-remedial-measures.component.scss']
})
export class AgriRemedialMeasuresComponent implements OnInit {
  @ViewChild('infoModal') public infoModal: InfoModalComponent;
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
agrochemicalBrandStatus;
  RemedialMeasuresList: any = [];
  pageAgriRemedialMeasures: PageAgriRemedialMeasures;
  selectedPage: number = 1;
  maxSize : number=10;
  searchText : any= "";
  isValid: number = 1;
  missing : any="";
  recordsPerPage: number = 10;
  records: any = [];

  inactiveCount:number=0;
  approvedCount:number=0;
  finalizedCount:number=0;
  rejectedCount:number=0;
  statusList:any=[];

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    // this.loadAllFertilizer();
    this.getPageAgriRemedialMeasures(0, this.isValid);
    this.agrochemicalBrandStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,  private userRightsService: UserRightsService,
    public agriRemedialMeasuresService: AgriRemedialMeasuresService,
                private drkServiceService: DrkServiceService

  ) { }

  // RemedialMeasures list
  loadAllRemedialMeasures() {
    return this.agriRemedialMeasuresService.GetAllRemedialMeasures().subscribe((data: {}) => {
      this.RemedialMeasuresList = data;
    })
  }

  // Delete RemedialMeasures
  // deleteRemedialMeasures(data) {
  //   var index = index = this.RemedialMeasuresList.map(x => { return x.name }).indexOf(data.name);
  //   return this.agriRemedialMeasuresService.DeleteRemedialMeasures(data.id).subscribe(res => {
  //     this.RemedialMeasuresList.splice(index, 1)
  //     console.log('RemedialMeasures deleted!')
  //   })
  // }


  getRemedialMeasuresStressText(stress) {
    if (stress && stress.length > 0) {
      return (stress.reduce((x, y) => x + y.name + ", ", "")).trim().slice(0, -1);
    } else {
      return " "
    }
  }

  // getAgrochemicalText(stress) {
  //   if (stress && stress.length > 0) {
  //     return (stress.reduce((x, y) => x + y.name + ", ", "")).trim().slice(0, -1);
  //   } else {
  //     return " "
  //   }
  // }

  showInfoModal(title, data) {
    this.infoModal.showModal(title, data, '')
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : "+page);
    this.selectedPage=page;
    this.getPageAgriRemedialMeasures(page, this.isValid);
  }
  
  getPageAgriRemedialMeasures(page:number, isValid: number): void {
    this.agriRemedialMeasuresService.getPageAgriRemedialMeasures(page, this.recordsPerPage,this.searchText, isValid,this.missing)
    .subscribe(page => {
      this.inactiveCount=0;
      this.approvedCount=0;
      this.finalizedCount=0;
      this.rejectedCount=0;
      this.pageAgriRemedialMeasures = page
    
      for(let remedialMeasure of this.pageAgriRemedialMeasures.content){
        if(globalConstants.INACTIVE_STATUS == remedialMeasure.status){
          this.inactiveCount++;
        }
        if(globalConstants.APPROVED_STATUS == remedialMeasure.status){
          this.approvedCount++;
        }
        if(globalConstants.ACTIVE_STATUS == remedialMeasure.status){
          this.finalizedCount++;
        }
        if(globalConstants.REJECTED_STATUS == remedialMeasure.status){
          this.rejectedCount++;
        }
    }

    })
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriRemedialMeasuresService.getPageAgriRemedialMeasures(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing)
      .subscribe(page => {
        this.inactiveCount=0;
        this.approvedCount=0;
        this.finalizedCount=0;
        this.rejectedCount=0;
        this.pageAgriRemedialMeasures = page
        for(let remedialMeasure of this.pageAgriRemedialMeasures.content){
          if(globalConstants.INACTIVE_STATUS == remedialMeasure.status){
            this.inactiveCount++;
          }
          if(globalConstants.APPROVED_STATUS == remedialMeasure.status){
            this.approvedCount++;
          }
          if(globalConstants.ACTIVE_STATUS == remedialMeasure.status){
            this.finalizedCount++;
          }
          if(globalConstants.REJECTED_STATUS == remedialMeasure.status){
            this.rejectedCount++;
          }
      }
      });
  }

  // Delete RemedialMeasures
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'approve') {
        observable = this.agriRemedialMeasuresService.ApproveRemedialMeasures(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.agriRemedialMeasuresService.FinalizeRemedialMeasures(event.id);
      } else if (event.flag == 'delete') {
        observable = this.agriRemedialMeasuresService.DeleteRemedialMeasures(event.id);
      } else if (event.flag == 'reject') {
        observable = this.agriRemedialMeasuresService.RejectRemedialMeasures(event.id);
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getPageAgriRemedialMeasures(this.selectedPage - 1,this.isValid);
    this.agrochemicalBrandStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
            // this._statusMsg = res.message;
            // this.loadAllRemedialMeasures();
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

  searchRemedialMeasures() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageAgriRemedialMeasures(this.selectedPage - 1, this.isValid);
  }

  sortData(sort: Sort) {
    const data = this.pageAgriRemedialMeasures.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriRemedialMeasures.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageAgriRemedialMeasures.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'company':
          return compare(firstValue.company, secondValue.company, isAsc);
        case 'agrochemical':
          return compare(firstValue.agrochemical, secondValue.agrochemical, isAsc);
        case 'agrochemicalStatus':
          return compare(firstValue.agrochemicalStatus, secondValue.agrochemicalStatus, isAsc);
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
  
    
  }
  

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('agri_agrochemical_brand').subscribe(res => {
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
    this.getPageAgriRemedialMeasures(0,this.isValid);
  }

  moveToMaster(id){
    this.agriRemedialMeasuresService.moveToMaster(id).subscribe(res => {
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
  bulkData(key,tableName){

    var parentCheckbox = (document.querySelector('thead th input') as HTMLInputElement).checked

    if(parentCheckbox){

    if((key == globalConstants.APPROVED_STATUS && this.approvedCount > 0 && this.approvedCount < this.recordsPerPage) || (key == globalConstants.APPROVED_STATUS && this.finalizedCount > 0 && this.finalizedCount < this.recordsPerPage)){
      this.errorModal.showModal('ERROR', 'One or more selected records are found with status Approved or Active. Please uncheck those records.', '');
      return;
    }

   if((key == globalConstants.ACTIVE_STATUS && this.finalizedCount > 0 && this.finalizedCount < this.recordsPerPage) || (key == globalConstants.ACTIVE_STATUS && this.rejectedCount > 0 && this.rejectedCount < this.recordsPerPage) || (key == globalConstants.ACTIVE_STATUS && this.inactiveCount > 0 && this.inactiveCount < this.recordsPerPage)){
      this.errorModal.showModal('ERROR', 'One or more selected records are found with status Active, Inactice or Rejected. Please uncheck those records.', '');
      return;
    }

  }else{

     for(let status of this.statusList){

      if((key == globalConstants.APPROVED_STATUS)){
        if(status == globalConstants.APPROVED_STATUS || status == globalConstants.ACTIVE_STATUS){
          this.errorModal.showModal('ERROR', 'Selected records are with Approved or Active status. You can not Approve those records.', '');
          return;
        }
      }

      if((key == globalConstants.ACTIVE_STATUS)){
        if(status == globalConstants.ACTIVE_STATUS || status == globalConstants.INACTIVE_STATUS|| status == globalConstants.REJECTED_STATUS){
            this.errorModal.showModal('ERROR', 'Selected records are with Active or Inactive or Rejected status. You can not Finalize those records.', '');
            return;
          }
      }
     }
    
  }

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
          this.statusList = [];
          this.successModal.showModal('SUCCESS', data.message, '');

        }else {
          this.errorModal.showModal('ERROR', data.error, '');

        }

      })
  }
}

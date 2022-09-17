import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriCommodityStressStageService } from '../services/agri-commodity-stress-stage.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import { PageAgriCommodityStressStage } from '../models/PageAgriCommodityStressStage';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {Sort} from '@angular/material';
import { BulkDataService } from '../services/bulk-data.service';
import {DrkServiceService} from '../../services/drk-service.service';

@Component({
  selector: 'app-agri-stress-stage',
  templateUrl: './agri-commodity-stress-stage.component.html',
  styleUrls: ['./agri-commodity-stress-stage.component.scss']
})
export class AgriCommodityStressStageComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
   isSuccess: boolean = false;
   _statusMsg: string;
   sourceOfWaterList: any = [];
   
  commodityStressStageList: any = [];
  commodityStressStageStatus;
  pageAgriCommodityStressStage: PageAgriCommodityStressStage;
  selectedPage: number = 1;
  maxSize: number = 10;
  searchText: any = "";
  isValid: number = 1;
  missing : any="";
  recordsPerPage: number = 10;
  records: any = [];

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    // this.loadAllStressStage();
    this.getPageAgriCommodityStressStage(0, this.isValid);
    this.commodityStressStageStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService, private userRightsService: UserRightsService,
              public agriCommodityStressStageService: AgriCommodityStressStageService,
              private drkServiceService: DrkServiceService
  ) {
  }

   // StressStage list
   loadAllStressStage() {
    return this.agriCommodityStressStageService.GetAllStressStage().subscribe((data: {}) => {
      this.commodityStressStageList = data;
    })
  }

    // Delete StressStage
    // deleteStressStage(data){
    //   var index = index = this.StressStageList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriStressStageService.DeleteStressStage(data.id).subscribe(res => {
    //     this.StressStageList.splice(index, 1)
    //      console.log('StressStage deleted!')
    //    })
    // }
    

    onSelect(page: number): void {
      (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
      console.log("selected page : " + page);
      // this.selectedPage = page;
      this.getPageAgriCommodityStressStage(page, this.isValid);
    }
  
    getPageAgriCommodityStressStage(page: number, isValid: number): void {
      this.agriCommodityStressStageService.getPageAgriCommodityStressStage(page, this.recordsPerPage, this.searchText, isValid,this.missing)
        .subscribe(page => this.pageAgriCommodityStressStage = page)
    }

    loadData(event: any) {
      console.log('pages ', event.target.value);
      this.recordsPerPage = event.target.value || 10;
      this.agriCommodityStressStageService.getPageAgriCommodityStressStage(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing)
        .subscribe(page => this.pageAgriCommodityStressStage = page);
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

     // Delete StressStage
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
      if (event.flag == 'approve') {
        observable = this.agriCommodityStressStageService.ApproveStressStage(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.agriCommodityStressStageService.FinalizeStressStage(event.id);
      } else if (event.flag == 'delete') {
        observable = this.agriCommodityStressStageService.DeleteStressStage(event.id);
      } else if (event.flag == 'reject') {
        observable = this.agriCommodityStressStageService.RejectStressStage(event.id);
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
            this.loadAllStressStage();
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

  searchCommodityStressStage() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageAgriCommodityStressStage(this.selectedPage - 1, this.isValid);
  }

  sortData(sort: Sort) {
    const data = this.pageAgriCommodityStressStage.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriCommodityStressStage.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageAgriCommodityStressStage.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'commodity':
          return compare(firstValue.commodity, secondValue.commodity, isAsc);
        case 'stressType':
          return compare(firstValue.stressType, secondValue.stressType, isAsc);
        case 'stress':
          return compare(firstValue.stress, secondValue.stress, isAsc);
        case 'startPhenophaseName':
          return compare(firstValue.startPhenophaseName, secondValue.startPhenophaseName, isAsc);
        case 'endPhenophaseName':
          return compare(firstValue.endPhenophaseName, secondValue.endPhenophaseName, isAsc);
        case 'description':
          return compare(firstValue.description, secondValue.description, isAsc);
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
    
    if (this.selectedPage > 0) {

      this.onSelect((this.selectedPage - 1));
      (document.querySelector('thead th input') as HTMLInputElement).checked = false
    } else {
      this.onSelect(this.selectedPage);
      (document.querySelector('thead th input') as HTMLInputElement).checked = false
    }
  }

  onClickMissing(){
    this.missing = 1;
    this.getPageAgriCommodityStressStage(0,this.isValid);
  }

  moveToMaster(id){
    this.agriCommodityStressStageService.moveToMaster(id).subscribe(res => {
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

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('agri_stress_stage').subscribe(res => {
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
}

import {Component, OnInit, ViewChild} from '@angular/core';
import {globalConstants} from '../../global/globalConstants';
import {DrkServiceService} from '../../services/drk-service.service';
import {UserRightsService} from '../../services/user-rights.service';
import {ConfirmationMadalComponent} from '../../global/confirmation-madal/confirmation-madal.component';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {PageAgriAgrochemicalMaster} from '../models/PageAgriAgrochemicalMaster';
import {Sort} from '@angular/material';
import {AgriAgrochemicalMasterService} from '../services/agri-agrochemical-master.service';
import { BulkDataService } from '../services/bulk-data.service';

@Component({
    selector: 'app-agri-agrochemical-master',
    templateUrl: './agri-agrochemical-master.component.html',
    styleUrls: ['./agri-agrochemical-master.component.scss']
})
export class AgriAgrochemicalMasterComponent implements OnInit {

    @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    _statusMsg: string;
    agrochemicalMasterStatus;
    AgrochemicalMasterList: any = [];
    pageAgriAgrochemicalMaster: PageAgriAgrochemicalMaster;
    selectedPage: number = 1;
    maxSize: number = 10;
    searchText: any = '';
    isValid: number = 1;
    recordsPerPage: number = 10;
    records: any = [];
 
    inactiveCount:number=0;
    approvedCount:number=0;
    finalizedCount:number=0;
    rejectedCount:number=0;
    statusList:any=[];


    constructor(public bulkDatas: BulkDataService,
        public agriAgrochemicalMasterService: AgriAgrochemicalMasterService,
        private userRightsService: UserRightsService,
        private drkServiceService: DrkServiceService
    ) {
    }

    ngOnInit(): void {
        this.records = ['20', '50', '100', '200', '250'];
        this.getPageAgriAgrochemicalMaster(0, this.isValid);
        this.agrochemicalMasterStatus = globalConstants;
    }


    onSelect(page: number): void {
        (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
        this.bulkDatas.disbled = true;
        console.log('selected page : ' + page);
        this.selectedPage = page;
        this.getPageAgriAgrochemicalMaster(page, this.isValid);
    }

    getPageAgriAgrochemicalMaster(page: number, isValid: number): void {
        this.agriAgrochemicalMasterService.getPageAgriAgrochemicalMaster(page, this.recordsPerPage, this.searchText, isValid)
            .subscribe(page => { this.inactiveCount=0;
                this.approvedCount=0;
                this.finalizedCount=0;
                this.rejectedCount=0;
                this.pageAgriAgrochemicalMaster = page
                for(let agrochemicalMaster of this.pageAgriAgrochemicalMaster.content){
                    if(globalConstants.INACTIVE_STATUS == agrochemicalMaster.status){
                      this.inactiveCount++;
                    }
                    if(globalConstants.APPROVED_STATUS == agrochemicalMaster.status){
                      this.approvedCount++;
                    }
                    if(globalConstants.ACTIVE_STATUS == agrochemicalMaster.status){
                      this.finalizedCount++;
                    }
                    if(globalConstants.REJECTED_STATUS == agrochemicalMaster.status){
                      this.rejectedCount++;
                    }
                }
               
            
            }
            );
    }

    loadData(event: any) {
        console.log('pages ', event.target.value);
        this.recordsPerPage = event.target.value || 10;
        this.agriAgrochemicalMasterService.getPageAgriAgrochemicalMaster(this.selectedPage - 1, this.recordsPerPage, this.searchText,this.isValid)
          .subscribe(page => {
            this.inactiveCount=0;
            this.approvedCount=0;
            this.finalizedCount=0;
            this.rejectedCount=0;
            this.pageAgriAgrochemicalMaster = page
            for(let agrochemicalMaster of this.pageAgriAgrochemicalMaster.content){
                if(globalConstants.INACTIVE_STATUS == agrochemicalMaster.status){
                  this.inactiveCount++;
                }
                if(globalConstants.APPROVED_STATUS == agrochemicalMaster.status){
                  this.approvedCount++;
                }
                if(globalConstants.ACTIVE_STATUS == agrochemicalMaster.status){
                  this.finalizedCount++;
                }
                if(globalConstants.REJECTED_STATUS == agrochemicalMaster.status){
                  this.rejectedCount++;
                }
            }
           
        
        
        });
      }
    

    // // Delete AgrochemicalMaster
    // deleteAgrochemicalMaster(data){
    //   var index = index = this.AgrochemicalMasterList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriAgrochemicalMasterService.DeleteAgrochemicalMaster(data.id).subscribe(res => {
    //     this.AgrochemicalMasterList.splice(index, 1)
    //      console.log('AgrochemicalMaster deleted!')
    //    })
    // }

    // Delete
    delete(data, i) {
        data.index = i;
        data.flag = 'delete';
        this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg, data);
    }

    // Reject
    reject(data, i) {
        data.index = i;
        data.flag = 'reject';
        this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg, data);
    }

    approve(data, i) {
        data.index = i;
        data.flag = 'approve';
        this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg, data);
    }

    finalize(data, i) {
        data.index = i;
        data.flag = 'finalize';
        this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg, data);
    }

    modalConfirmation(event) {
        console.log(event);
        let observable: any;
        if (event) {
            this.isSubmitted = true;
            if (event.flag == 'approve') {
                observable = this.agriAgrochemicalMasterService.ApproveAgrochemicalMaster(event.id);
            } else if (event.flag == 'finalize') {
                observable = this.agriAgrochemicalMasterService.FinalizeAgrochemicalMaster(event.id);
            } else if (event.flag == 'delete') {
                observable = this.agriAgrochemicalMasterService.DeleteAgrochemicalMaster(event.id);
            } else if (event.flag == 'reject') {
                observable = this.agriAgrochemicalMasterService.RejectAgrochemicalMaster(event.id);
            }
            observable.subscribe(res => {
                if (res) {
                    this.isSuccess = res.success;
                    if (res.success) {
                      console.log("page : " + this.selectedPage);
        if(this.selectedPage >= 2){
          // console.log("Inside if");
        this.getPageAgriAgrochemicalMaster(this.selectedPage - 1,this.isValid);
        this.agrochemicalMasterStatus = globalConstants;
        }else{
          // console.log("Inside else");
        this.ngOnInit();
        }
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

    searchAgrochemicalMaster() {
        this.selectedPage = 1;
        console.log(this.searchText);
        this.getPageAgriAgrochemicalMaster(this.selectedPage - 1, this.isValid);
    }

    sortData(sort: Sort) {
        const data = this.pageAgriAgrochemicalMaster.content.slice();
        if (!sort.active || sort.direction == '') {
            this.pageAgriAgrochemicalMaster.content = data;
            return;
        }

        function compare(firstValue, secondValue, isAsc: boolean) {
            return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }

        this.pageAgriAgrochemicalMaster.content = data.sort((firstValue, secondValue) => {
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

import { PageFarmerVipDesignation } from './../models/PageFarmerVipDesignation';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FarmerVipDesignationService } from '../services/farmer-vip-designation.service';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../agri/services/bulk-data.service';

@Component({
  selector: 'app-farmer-vip-designation',
  templateUrl: './farmer-vip-designation.component.html',
  styleUrls: ['./farmer-vip-designation.component.scss']
})
export class FarmerVIPDesignationComponent implements OnInit {


  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

 isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

    vipDesignationStatus;
  vipDesignationList: any = [];

  searchText: any = '';
   selectedPage: number = 1;
   maxSize =10;
   recordsPerPage: number = 10;
   records: any = [];

   pageFarmerVipDesignation: PageFarmerVipDesignation;


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
	   this.getFarmerVipDesignationPagenatedList(0);
    // this.loadAllVipDesignation();
    this.vipDesignationStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public farmerVipDesignationService: FarmerVipDesignationService,
    private userRightsService: UserRightsService,

  ) { }

  getFarmerVipDesignationPagenatedList(page: number): void {
    this.farmerVipDesignationService.getFarmerVipDesignationPagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageFarmerVipDesignation = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.farmerVipDesignationService.getFarmerVipDesignationPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageFarmerVipDesignation = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getFarmerVipDesignationPagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getFarmerVipDesignationPagenatedList(this.selectedPage - 1);
}

  // VipDesignation list
  loadAllVipDesignation() {
    return this.farmerVipDesignationService.GetAllVipDesignation().subscribe((data: {}) => {
      this.vipDesignationList = data;
    })
  }



  // Delete VipDesignation
  // deleteVipDesignation(data) {
  //   var index = index = this.vipDesignationList.map(x => { return x.name }).indexOf(data.name);
  //   return this.farmerVipDesignationService.DeleteVipDesignation(data.id).subscribe(res => {
  //     this.vipDesignationList.splice(index, 1)
  //     console.log('VipDesignation deleted!')
  //   })
  // }



  // Delete VipDesignation
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
        observable = this.farmerVipDesignationService.ApproveVipDesignation(event.id)
      } else if (event.flag == "finalize") {
        observable = this.farmerVipDesignationService.FinalizeVipDesignation(event.id)
      } else if (event.flag == "delete") {
        observable = this.farmerVipDesignationService.DeleteVipDesignation(event.id)
      } else if (event.flag == "reject") {
        observable = this.farmerVipDesignationService.RejectVipDesignation(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            // this._statusMsg = res.message;
            this.loadAllVipDesignation();
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


  modalSuccess($event: any) {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // this.ngOnInit();
    // this.selectedPage = 1;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getFarmerVipDesignationPagenatedList(this.selectedPage - 1);
    this.vipDesignationStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }

    sortData(sort: Sort) {
      const data = this.pageFarmerVipDesignation.content.slice();
      if (!sort.active || sort.direction == '') {
        this.pageFarmerVipDesignation.content = data;
        return;
      }
    
      function compare(firstValue, secondValue, isAsc: boolean) {
        return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
      }
    
      this.pageFarmerVipDesignation.content = data.sort((firstValue, secondValue) => {
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

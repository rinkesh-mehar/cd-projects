import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriHealthService } from '../../services/agri-health.service';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import { PageAgriHealth } from '../../models/PageAgriHealth';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {DrkServiceService} from '../../../services/drk-service.service';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../services/bulk-data.service';
// import { PageAgriCommodity } from '../models/PageAgriCommodity';

@Component({
  selector: 'app-agri-health',
  templateUrl: './agri-health.component.html',
  styleUrls: ['./agri-health.component.scss']
})
export class AgriHealthComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  HealthList: any = [];
  agriHealthStatus;
  pageAgriHealth : PageAgriHealth;
  selectedPage: number = 1;
  maxSize : number=10;
  searchText : any= "";
  isValid: number = 1;
  missing : any="";
  
  recordsPerPage: number = 10;
   records: any = [];

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageAgriHealth(0, this.isValid);
  //  this.loadAllHealth();
    this.agriHealthStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public agriHealthService : AgriHealthService,
    private userRightsService: UserRightsService,
    private drkServiceService: DrkServiceService
  ){ }

   // Health list
   loadAllHealth() {
    return this.agriHealthService.GetAllHealth().subscribe((data: {}) => {
      this.HealthList = data;
    })
  }


  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : " + page);
    this.selectedPage = page;
    this.getPageAgriHealth(page, this.isValid);
  }

  getPageAgriHealth(page: number, isValid: number): void {
    this.agriHealthService.getPageAgriHealth(page, this.recordsPerPage, this.searchText, isValid,this.missing)
      .subscribe(page => this.pageAgriHealth = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriHealthService.getPageAgriHealth(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing)
      .subscribe(page => this.pageAgriHealth = page);
  }


    // // Delete Health
    // deleteHealth(data){
    //   var index = index = this.HealthList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriHealthService.DeleteHealth(data.id).subscribe(res => {
    //     this.HealthList.splice(index, 1)
    //      console.log('Health deleted!')
    //    })
    // }

     // Delete 
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.healthParameter, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.healthParameter, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.healthParameter, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.healthParameter, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        observable = this.agriHealthService.ApproveHealth(event.id)
      } else if (event.flag == "finalize") {
        observable = this.agriHealthService.FinalizeHealth(event.id)
      } else if (event.flag == "delete") {
        observable = this.agriHealthService.DeleteHealth(event.id)
      } else if (event.flag == "reject") {
        observable = this.agriHealthService.RejectHealth(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
            this.loadAllHealth();
            this.successModal.showModal('SUCCESS', res.message, '');
          } else {
            this.errorModal.showModal('ERROR', res.error, '');
          }
        }
      }, err => {
        this.errorModal.showModal('ERROR', err.error, '');
      })
    }
  }

  searchHealth() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageAgriHealth(this.selectedPage - 1, this.isValid);
  }

  modalSuccess($event: any) {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // this.ngOnInit();
    // this.selectedPage = 1;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getPageAgriHealth(this.selectedPage - 1,this.isValid);
    this.agriHealthStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }
  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('agri_health').subscribe(res => {
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
    this.getPageAgriHealth(0,this.isValid);
  }

  moveToMaster(id){
    this.agriHealthService.moveToMaster(id).subscribe(res => {
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

  sortData(sort: Sort) {
    const data = this.pageAgriHealth.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriHealth.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageAgriHealth.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case 'commodity':
            return compare(firstValue.commodity, secondValue.commodity, isAsc);
        case 'phenophase':
          return compare(firstValue.phenophase, secondValue.phenophase, isAsc);
        case 'healthParameter':
          return compare(firstValue.healthParameter, secondValue.healthParameter, isAsc);
        case 'specification':
          return compare(firstValue.specification, secondValue.specification, isAsc);
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

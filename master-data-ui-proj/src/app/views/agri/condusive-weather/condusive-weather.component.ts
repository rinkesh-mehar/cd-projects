import { Component, OnInit, ViewChild } from '@angular/core';
import { ZonalCondusiveWeatherService } from '../services/zonal-condusive-weather.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { globalConstants } from '../../global/globalConstants';
import { UserRightsService } from '../../services/user-rights.service';
import { PageZonalCondusiveWeather } from '../models/PageZonalCondusiveWeather';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {DrkServiceService} from '../../services/drk-service.service';
import { BulkDataService } from '../services/bulk-data.service';

@Component({
  selector: 'app-condusive-weather',
  templateUrl: './condusive-weather.component.html',
  styleUrls: ['./condusive-weather.component.scss']
})
export class CondusiveWeatherComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  CondusiveWeatherList: any = [];
  condusiveWeatherStatus;
  pageAgriCondusiveWeather: PageZonalCondusiveWeather;
  selectedPage: number = 1;
  maxSize: number = 10;
  searchText: any = "";
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
    this.getPageZonalCondusiveWeather(0, this.isValid);
  //  this.loadAllCondusiveWeather();
    this.condusiveWeatherStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public zonalCondusiveWeatherService : ZonalCondusiveWeatherService,
    private userRightsService: UserRightsService,
    private drkServiceService: DrkServiceService

  ){ }

   // CondusiveWeather list
   loadAllCondusiveWeather() {
    return this.zonalCondusiveWeatherService.GetAllCondusiveWeather().subscribe((data: {}) => {
      this.CondusiveWeatherList = data;
    })
  }


  onSelect(page: number): void {
    
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : " + page);
    this.selectedPage = page;
    this.getPageZonalCondusiveWeather(page, this.isValid);
  }

  getPageZonalCondusiveWeather(page: number, isValid: number): void {

  

    this.zonalCondusiveWeatherService.getPageZonalCondusiveWeather(page, this.recordsPerPage, this.searchText, isValid,this.missing)
      .subscribe(page => {

        this.inactiveCount=0;
        this.approvedCount=0;
        this.finalizedCount=0;
        this.rejectedCount=0;

        this.pageAgriCondusiveWeather = page

        for(let condusiveWeather of this.pageAgriCondusiveWeather.content){
            if(globalConstants.INACTIVE_STATUS == condusiveWeather.status){
              this.inactiveCount++;
            }
            if(globalConstants.APPROVED_STATUS == condusiveWeather.status){
              this.approvedCount++;
            }
            if(globalConstants.ACTIVE_STATUS == condusiveWeather.status){
              this.finalizedCount++;
            }
            if(globalConstants.REJECTED_STATUS == condusiveWeather.status){
              this.rejectedCount++;
            }
        }
       
      })

  }


  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.zonalCondusiveWeatherService.getPageZonalCondusiveWeather(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing)
      .subscribe(page => 
        {
        this.inactiveCount=0;
        this.approvedCount=0;
        this.finalizedCount=0;
        this.rejectedCount=0;
        
        this.pageAgriCondusiveWeather = page

        for(let condusiveWeather of this.pageAgriCondusiveWeather.content){
            if(globalConstants.INACTIVE_STATUS == condusiveWeather.status){
              this.inactiveCount++;
            }
            if(globalConstants.APPROVED_STATUS == condusiveWeather.status){
              this.approvedCount++;
            }
            if(globalConstants.ACTIVE_STATUS == condusiveWeather.status){
              this.finalizedCount++;
            }
            if(globalConstants.REJECTED_STATUS == condusiveWeather.status){
              this.rejectedCount++;
            }
        }
       
        });
  }

    // // Delete CondusiveWeather
    // deleteCondusiveWeather(data){
    //   var index = index = this.CondusiveWeatherList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriCondusiveWeatherService.DeleteCondusiveWeather(data.id).subscribe(res => {
    //     this.CondusiveWeatherList.splice(index, 1)
    //      console.log('CondusiveWeather deleted!')
    //    })
    // }

     // Delete 
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
      if (event.flag == "approve") {
        observable = this.zonalCondusiveWeatherService.ApproveCondusiveWeather(event.id)
      } else if (event.flag == "finalize") {
        observable = this.zonalCondusiveWeatherService.FinalizeCondusiveWeather(event.id)
      } else if (event.flag == "delete") {
        observable = this.zonalCondusiveWeatherService.DeleteCondusiveWeather(event.id)
      } else if (event.flag == "reject") {
        observable = this.zonalCondusiveWeatherService.RejectCondusiveWeather(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
            // this.loadAllCondusiveWeather();
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

  searchCondusiveWeather() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageZonalCondusiveWeather(this.selectedPage - 1, this.isValid);
  }

  sortData(sort: Sort) {
    const data = this.pageAgriCondusiveWeather.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriCondusiveWeather.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageAgriCondusiveWeather.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case 'state':
            return compare(firstValue.state , secondValue.state, isAsc);
          case 'aczName':
            return compare(firstValue.aczName , secondValue.aczName, isAsc);
          case 'zonalCommodity':
            return compare(firstValue.zonalCommodity , +secondValue.zonalCommodity, isAsc);
          case 'lower':
            return compare(firstValue.lower , +secondValue.lower, isAsc);
            case 'upper':
          return compare(firstValue.upper, secondValue.upper, isAsc);
        case 'stress':
          return compare(firstValue.stress, secondValue.stress, isAsc);
        case 'weatherParameter':
          return compare(firstValue.weatherParameter, secondValue.weatherParameter, isAsc);
        case 'conduciveDuration':
          return compare(firstValue.conduciveDuration, secondValue.conduciveDuration, isAsc);
          case 'relaxingDuration':
          return compare(firstValue.relaxingDuration, secondValue.relaxingDuration, isAsc);
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
  this.getPageZonalCondusiveWeather(this.selectedPage - 1,this.isValid);
  this.condusiveWeatherStatus = globalConstants;
  }else{
    // console.log("Inside else");
  this.ngOnInit();
  }
}

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('agri_conducive_weather').subscribe(res => {
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
    this.getPageZonalCondusiveWeather(0,this.isValid);
  }

  moveToMaster(id){
    this.zonalCondusiveWeatherService.moveToMaster(id).subscribe(res => {
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

  onChangeCheckbox(status:string,ele){
    var parentCheckbox = (document.querySelector('thead th input') as HTMLInputElement).checked
    // console.log("parentCheckbox : " + parentCheckbox);
    var checkbox = ele.target.checked
    // console.log("checkbox : " + checkbox);
    // console.log("status : " + status);

    // var checkboxes = document.querySelectorAll('input');
    if(parentCheckbox){
      if(!checkbox){
        if(globalConstants.INACTIVE_STATUS == status){
          this.inactiveCount--;
        }
        if(globalConstants.APPROVED_STATUS == status){
          this.approvedCount--;
        }
        if(globalConstants.ACTIVE_STATUS == status){
          this.finalizedCount--;
        }
        if(globalConstants.REJECTED_STATUS == status){
          this.rejectedCount--;
        }
      }else{
        if(globalConstants.INACTIVE_STATUS == status){
          this.inactiveCount++;
        }
        if(globalConstants.APPROVED_STATUS == status){
          this.approvedCount++;
        }
        if(globalConstants.ACTIVE_STATUS == status){
          this.finalizedCount++;
        }
        if(globalConstants.REJECTED_STATUS == status){
          this.rejectedCount++;
        }
      }
    }else{
      if(checkbox){

        this.statusList.push(status);
        
      }else{
       
        const index = this.statusList.indexOf(status, 0);
        if (index > -1) {
          this.statusList.splice(index, 1);
        }

      }
    }
    // console.log("inactiveCount : " + this.inactiveCount + " approvedCount : " + this.approvedCount + " finalizedCount : " + this.finalizedCount + " rejectedCount : " + this.rejectedCount);
  }

}

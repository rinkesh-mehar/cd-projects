import { Component, OnInit, ViewChild } from '@angular/core';
import { RegionStressService } from '../services/region-stress.service';
import { PageRegionStress } from '../models/PageRegionStress';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {Sort} from '@angular/material';
import {DrkServiceService} from '../../services/drk-service.service';
import { BulkDataService } from '../../agri/services/bulk-data.service';

@Component({
  selector: 'app-region-stress',
  templateUrl: './region-stress.component.html',
  styleUrls: ['./region-stress.component.scss']
})
export class RegionStressComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  regionStressStatus;
  StressList: any = [];
  pageRegionStress: PageRegionStress;
  selectedPage: number = 1;
  searchText: any = "";
  maxSize = 10;
  isValid: number = 1;
  missing : any="";

  recordsPerPage: number = 10;
   records: any = [];


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    let data= this.getPageRegionStress(0, this.isValid);
    this.regionStressStatus = globalConstants;
    console.log("data"+ data);
    //this.loadAllStress();
  }

  constructor(public bulkDatas: BulkDataService,
    public regionStressService: RegionStressService,
    private userRightsService: UserRightsService,
    private drkServiceService: DrkServiceService

  ) { }

  // Stress list
  loadAllStress() {
    return this.regionStressService.GetAllStress().subscribe((data: {}) => {
      this.StressList = data;
    })
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : " + page);
    this.selectedPage = page;
    this.getPageRegionStress(page, this.isValid);
  }

  getPageRegionStress(page: number, isValid: number): void {
    this.regionStressService.getPageRegionStress(page, this.recordsPerPage, this.searchText, isValid,this.missing)
      .subscribe(page => this.pageRegionStress = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.regionStressService.getPageRegionStress(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing)
      .subscribe(page => this.pageRegionStress = page);
  }

  // // Delete Stress
  // deleteStress(data){
  //   var index = index = this.StressList.map(x => {return x.name}).indexOf(data.name);
  //    return this.regionStressService.DeleteStress(data.id).subscribe(res => {
  //     this.StressList.splice(index, 1)
  //      console.log('Stress deleted!')
  //    })
  // }


  // Delete
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.stress, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.stress, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.stress, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.stress, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        observable = this.regionStressService.ApproveStress(event.id)
      } else if (event.flag == "finalize") {
        observable = this.regionStressService.FinalizeStress(event.id)
      } else if (event.flag == "delete") {
        observable = this.regionStressService.DeleteStress(event.id)
      } else if (event.flag == "reject") {
        observable = this.regionStressService.RejectStress(event.id)
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

  searchStress() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageRegionStress(this.selectedPage - 1, this.isValid);
  }

  sortData(sort: Sort) {
    const data = this.pageRegionStress.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageRegionStress.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageRegionStress.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'state':
          return compare(firstValue.state, secondValue.state, isAsc);
        case 'stress':
          return compare(firstValue.stress, secondValue.stress, isAsc);
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
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      console.log("Inside if");
    this.getPageRegionStress(this.selectedPage - 1,this.isValid);
    this.regionStressStatus = globalConstants;
    }else{
      console.log("Inside else");
      this.ngOnInit();
    }
  }

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('regional_stress').subscribe(res => {
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
    this.getPageRegionStress(0,this.isValid);
  }

  moveToMaster(id){
    this.regionStressService.moveToMaster(id).subscribe(res => {
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

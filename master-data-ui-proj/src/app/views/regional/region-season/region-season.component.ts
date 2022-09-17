import { Component, OnInit, ViewChild } from '@angular/core';
import { RegionSeasonService } from '../services/region-season.service';
import { PageRegionSeason } from '../models/PageRegionSeason';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {DrkServiceService} from '../../services/drk-service.service';
import { BulkDataService } from '../../agri/services/bulk-data.service';


@Component({
  selector: 'app-region-season',
  templateUrl: './region-season.component.html',
  styleUrls: ['./region-season.component.scss']
})
export class RegionSeasonComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
seasonStatus;
  SeasonList: any = [];
  pageSeason: PageRegionSeason;
  selectedPage: number=1;
  searchText : any="";
  maxSize = 10;
  selectedItems: any = null;
  isValid: number = 1;
  missing : any="";


recordsPerPage: number = 10;
records: any = [];



  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageRegionSeason(0, this.isValid);
    //this.loadAllSeason();
    this.seasonStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,private userRightsService: UserRightsService,
    public regionSeasonService: RegionSeasonService,
              private drkServiceService: DrkServiceService
  ) { }

  

  // Season list
  loadAllSeason() {
    return this.regionSeasonService.GetAllSeason().subscribe((data: {}) => {
      this.SeasonList = data;
    })
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : " + page);
    this.selectedPage = page;
    this.getPageRegionSeason(page, this.isValid);
  }

  getPageRegionSeason(page: number, isValid: number): void {
    this.regionSeasonService.getPageRegionSeason(page, this.recordsPerPage,this.searchText, isValid,this.missing)
      .subscribe(page => this.pageSeason = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.regionSeasonService.getPageRegionSeason(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing)
      .subscribe(page => this.pageSeason = page);
  }

  // Delete Season
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.season, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    console.log(this.selectedItems);
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.season, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.season, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.season, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        observable = this.regionSeasonService.ApproveSeason(event.id)
      } else if (event.flag == "finalize") {
        observable = this.regionSeasonService.FinalizeSeason(event.id)
      } else if (event.flag == "delete") {
        observable = this.regionSeasonService.DeleteSeason(event.id)
      } else if (event.flag == "reject") {
        observable = this.regionSeasonService.RejectSeason(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
            this.loadAllSeason();
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

  searchSeason(){
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageRegionSeason(this.selectedPage - 1, this.isValid);
  }

  sortData(sort: Sort) {
    const data = this.pageSeason.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageSeason.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageSeason.content = data.sort((firstValue, secondValue) => {
      let isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'state':
          return compare(firstValue.state, secondValue.state, isAsc);
        case globalConstants.NAME:
          return compare(firstValue.season, secondValue.season, isAsc);
        case 'startWeek':
          return compare(+firstValue.startWeek, +secondValue.startWeek, isAsc);
        case 'endWeek':
          return compare(+firstValue.endWeek, +secondValue.endWeek, isAsc);
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
    this.getPageRegionSeason(this.selectedPage - 1,this.isValid);
    this.seasonStatus = globalConstants;
    }else{
      console.log("Inside else");
      this.ngOnInit();
    }
  }

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('regional_season').subscribe(res => {
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
    this.getPageRegionSeason(0,this.isValid);
  }

  moveToMaster(id){
    this.regionSeasonService.moveToMaster(id).subscribe(res => {
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

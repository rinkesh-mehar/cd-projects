import { Component, OnInit, ViewChild } from '@angular/core';
import { RegionSeasonCommodityService } from '../services/region-season-commodity.service';
import { PageRegionSeasonCommodity } from '../models/PageRegionSeasonCommodity';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {Sort} from '@angular/material';
import {DrkServiceService} from '../../services/drk-service.service';
import { BulkDataService } from '../../agri/services/bulk-data.service';

@Component({
  selector: 'app-region-season-commodity',
  templateUrl: './region-season-commodity.component.html',
  styleUrls: ['./region-season-commodity.component.scss']
})
export class RegionSeasonCommodityComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;


  isSubmitted: boolean = false;
   isSuccess: boolean = false;
   _statusMsg: string;
  seasonCommodityStatus;
  SeasonCommodityList: any = [];
  pageSeasonCommodity : PageRegionSeasonCommodity;
  selectedPage: number = 1;
  maxSize : number=10;
  searchText : any="";
  isValid: number = 1;
  missing : any="";
  recordsPerPage: number = 10;
   records: any = [];


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageRegionSeasonCommodity(0, this.isValid);
    // this.loadAllSeasonCommodity();
    this.seasonCommodityStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,private userRightsService: UserRightsService,
    public regionSeasonCommodityService: RegionSeasonCommodityService,
              private drkServiceService: DrkServiceService
  ){ }

   // Season Commodity list
   loadAllSeasonCommodity() {
    return this.regionSeasonCommodityService.GetAllSeasonCommodity().subscribe((data: {}) => {
      this.SeasonCommodityList = data;
    })
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : "+page);
    this.selectedPage=page;
    this.getPageRegionSeasonCommodity(page, this.isValid);
  }
  
  getPageRegionSeasonCommodity(page:number, isValid: number): void {
    this.regionSeasonCommodityService.getPageRegionSeasonCommodity(page, this.recordsPerPage,this.searchText, isValid,this.missing)
    .subscribe(page => this.pageSeasonCommodity = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.regionSeasonCommodityService.getPageRegionSeasonCommodity(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing)
      .subscribe(page => this.pageSeasonCommodity = page);
  }
  // Delete Season Commodity
  // deleteSeasonCommodity(data){
  //   var index = index = this.SeasonCommodityList.map(x => {return x.name}).indexOf(data.name);
  //     return this.regionSeasonCommodityService.DeleteSeasonCommodity(data.id).subscribe(res => {
  //     this.SeasonCommodityList.splice(index, 1)
  //       console.log('Season Commodity deleted!')
  //     })
  // }



  // Delete SeasonCommodity
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
        observable = this.regionSeasonCommodityService.ApproveSeasonCommodity(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.regionSeasonCommodityService.FinalizeSeasonCommodity(event.id);
      } else if (event.flag == 'delete') {
        observable = this.regionSeasonCommodityService.DeleteSeasonCommodity(event.id);
      } else if (event.flag == 'reject') {
        observable = this.regionSeasonCommodityService.RejectSeasonCommodity(event.id);
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
            this.loadAllSeasonCommodity();
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

  searchCommodity() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageRegionSeasonCommodity(this.selectedPage - 1, this.isValid);
  }

  sortData(sort: Sort) {
    const data = this.pageSeasonCommodity.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageSeasonCommodity.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageSeasonCommodity.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'state':
          return compare(firstValue.state, secondValue.state, isAsc);
        case 'aczName':
          return compare(firstValue.aczName, secondValue.aczName, isAsc);
          case 'region':
          return compare(firstValue.region, secondValue.region, isAsc);
        case 'zonalCommodity':
          return compare(firstValue.zonalCommodity, secondValue.zonalCommodity, isAsc);
        case 'targetValue':
          return compare(firstValue.targetValue, secondValue.targetValue, isAsc);
        case 'minLotSize':
          return compare(firstValue.minLotSize, secondValue.minLotSize, isAsc);
        case 'maxRigtsInLot':
          return compare(firstValue.maxRigtsInLot, secondValue.maxRigtsInLot, isAsc);
        case 'harvestRelaxation':
          return compare(firstValue.harvestRelaxation, secondValue.harvestRelaxation, isAsc);
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
    this.getPageRegionSeasonCommodity(this.selectedPage - 1,this.isValid);
    this.seasonCommodityStatus = globalConstants;
    }else{
      console.log("Inside else");
    this.ngOnInit();
    }
  }

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('regional_commodity').subscribe(res => {
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
    this.getPageRegionSeasonCommodity(0,this.isValid);
  }

  moveToMaster(id){
    this.regionSeasonCommodityService.moveToMaster(id).subscribe(res => {
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

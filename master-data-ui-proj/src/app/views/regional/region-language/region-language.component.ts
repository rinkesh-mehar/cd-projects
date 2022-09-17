
import { Component, OnInit, ViewChild } from '@angular/core';
import { RegionLanguageService } from '../services/region-language.service';
import { PageRegionLanguage } from '../models/PageRegionLanguage';
import { UserRightsService } from '../../services/user-rights.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { globalConstants } from '../../global/globalConstants';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {DrkServiceService} from '../../services/drk-service.service';
import { BulkDataService } from '../../agri/services/bulk-data.service';

@Component({
  selector: 'app-region-language',
  templateUrl: './region-language.component.html',
  styleUrls: ['./region-language.component.scss']
})
export class RegionLanguageComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  regionLanguageStatus;
  LanguageList: any = [];
  pageRegionLanguage : PageRegionLanguage;
  selectedPage: number = 1;
  maxSize : number=10;
  searchText : any= "";
  isValid: number = 1;
  missing : any="";


recordsPerPage: number = 10;
records: any = [];

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];

    this.getPageRegionLanguage(0, this.isValid);
    this.loadAllLanguage();
    this.regionLanguageStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,private userRightsService: UserRightsService,
    public regionLanguageService: RegionLanguageService,
              private drkServiceService: DrkServiceService
  ){ }

   // language list
   loadAllLanguage() {
    return this.regionLanguageService.GetAllLanguage().subscribe((data: {}) => {
      this.LanguageList = data;
    })
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : "+page);
    this.selectedPage=page;
    this.getPageRegionLanguage(page, this.isValid);
  }
  
  getPageRegionLanguage(page:number, isValid: number): void {
    this.regionLanguageService.getPageRegionLanguage(page, this.recordsPerPage,this.searchText, isValid,this.missing)
    .subscribe(page => this.pageRegionLanguage = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.regionLanguageService.getPageRegionLanguage(this.selectedPage - 1, this.recordsPerPage, this.searchText,this.isValid,this.missing)
      .subscribe(page => this.pageRegionLanguage = page);
  }

  // Delete Season Commodity
  // deleteSeasonCommodity(data){
  //     return this.regionLanguageService.DeleteLanguage(data.id).subscribe(res => {
  //     this.LanguageList.splice(index, 1)
  //       console.log('Season Commodity deleted!')
  //     })
  // }


  // Delete Season
  delete(data, i) {
    data.index = i;
    data.flag = 'delete';
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + ' ' + data.language, data);
  }

  // Reject
  reject(data, i) {
    data.index = i;
    data.flag = 'reject';
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + ' ' + data.language, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = 'approve';
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + ' ' + data.language, data);
  }

  finalize(data, i) {
    data.index = i;
    data.flag = 'finalize';
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + ' ' + data.language, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'approve') {
        observable = this.regionLanguageService.ApproveLanguage(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.regionLanguageService.FinalizeLanguage(event.id);
      } else if (event.flag == 'delete') {
        observable = this.regionLanguageService.DeleteLanguage(event.id);
      } else if (event.flag == 'reject') {
        observable = this.regionLanguageService.RejectLanguage(event.id);
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

  searchLanguage() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageRegionLanguage(this.selectedPage - 1, this.isValid);
  }

  sortData(sort: Sort) {
    const data = this.pageRegionLanguage.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageRegionLanguage.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageRegionLanguage.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'state':
          return compare(firstValue.state, secondValue.state, isAsc);
        case 'language':
          return compare(firstValue.language, secondValue.language, isAsc);
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
    this.getPageRegionLanguage(this.selectedPage - 1,this.isValid);
    this.regionLanguageStatus = globalConstants;
    }else{
      console.log("Inside else");
    this.ngOnInit();
    }
  }

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('regional_language').subscribe(res => {
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
    this.getPageRegionLanguage(0,this.isValid);
  }

  moveToMaster(id){
    this.regionLanguageService.moveToMaster(id).subscribe(res => {
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

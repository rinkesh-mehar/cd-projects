import { Component, OnInit, ViewChild } from '@angular/core';
import { FarmerLanguageService } from '../services/farmer-language.service';
import { PageFarmerLanguage } from '../models/PageFarmerLanguage';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../agri/services/bulk-data.service';

@Component({
  selector: 'app-farmer-language',
  templateUrl: './farmer-language.component.html',
  styleUrls: ['./farmer-language.component.scss']
})
export class FarmerLanguageComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  languageStatus;
  LanguageList: any = [];
  pageLanguage: PageFarmerLanguage;
  selectedPage: number = 1;
  searchText : any = "";
  maxSize : number=10;

  recordsPerPage: number = 10;
  records: any = [];

  constructor(public bulkDatas: BulkDataService,
    public farmerLanguageService: FarmerLanguageService,
    private userRightsService: UserRightsService,
  ) { }

  ngOnInit() {
 this.records = ['20', '50', '100', '200', '250'];
    this.getPageFarmerLanguage(0);
    //this.loadAllLanguage();
    this.languageStatus = globalConstants;
  }

  loadAllLanguage() {
    return this.farmerLanguageService.GetAllLanguage().subscribe((data: {}) => {
      this.LanguageList = data;
    })
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : " + page);
    this.selectedPage = page;
    this.getPageFarmerLanguage(page);
  }

  getPageFarmerLanguage(page: number): void {
    this.farmerLanguageService.getPageFarmerLanguage(page, this.recordsPerPage,this.searchText)
      .subscribe(page => this.pageLanguage = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.farmerLanguageService.getPageFarmerLanguage(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageLanguage = page);
  }

  // Delete Language
  // deleteLanguage(data) {
  //   var index = index = this.LanguageList.map(x => { return x.name }).indexOf(data.name);
  //   return this.farmerLanguageService.DeleteLanguage(data.id).subscribe(res => {
  //     this.LanguageList.splice(index, 1)
  //     console.log('Language deleted!')
  //   })
  // }



  // Delete Language
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.language, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.language, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.language, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.language, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        observable = this.farmerLanguageService.ApproveLanguage(event.id)
      } else if (event.flag == "finalize") {
        observable = this.farmerLanguageService.FinalizeLanguage(event.id)
      } else if (event.flag == "delete") {
        observable = this.farmerLanguageService.DeleteLanguage(event.id)
      } else if (event.flag == "reject") {
        observable = this.farmerLanguageService.RejectLanguage(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
            this.loadAllLanguage();
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

  searchLanguage(){
    this.selectedPage - 1;
    console.log(this.searchText);
    this.getPageFarmerLanguage(this.selectedPage)
  }

  modalSuccess($event: any) {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // this.ngOnInit();
    // this.selectedPage = 1;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getPageFarmerLanguage(this.selectedPage - 1);
    this.languageStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }
  

  sortData(sort: Sort) {
    const data = this.pageLanguage.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageLanguage.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageLanguage.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case 'language':
            return compare(firstValue.language, secondValue.language, isAsc);
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

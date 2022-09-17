import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriHsCodeService } from '../services/agri-hs-code.service';
import { PageAgriHsCode } from '../models/PageAgriHsCode';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {Sort} from '@angular/material';
import {DrkServiceService} from '../../services/drk-service.service';
import { BulkDataService } from '../services/bulk-data.service';

@Component({
  selector: 'app-agri-hs-code',
  templateUrl: './agri-hs-code.component.html',
  styleUrls: ['./agri-hs-code.component.scss']
})
export class AgriHsCodeComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  hsCodeStatus;
  HsCodeList: any = [];
  pageHsCode: PageAgriHsCode;
  selectedPage : number = 1;
  maxSize = 10;
  searchText: any="";
  isValid: number = 1;
  missing : any="";
  recordsPerPage: number = 10;
   records: any = [];


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageAgriHsCode(0, this.isValid);
    //this.loadAllHsCode();
    this.hsCodeStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public agriHsCodeService: AgriHsCodeService,
    private userRightsService: UserRightsService,
    private drkServiceService: DrkServiceService
  ){ }

   // HsCode list
   loadAllHsCode() {
    return this.agriHsCodeService.GetAllHsCode().subscribe((data: {}) => {
      this.HsCodeList = data;
    })
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : "+page);
    this.selectedPage=page;
    this.getPageAgriHsCode(page, this.isValid);
  }

  getPageAgriHsCode(page:number, isValid: number): void {
    this.agriHsCodeService.getPageAgriHsCode(page, this.recordsPerPage,this.searchText, isValid,this.missing)
        .subscribe(page => this.pageHsCode = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriHsCodeService.getPageAgriHsCode(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing)
      .subscribe(page => this.pageHsCode = page);
  }



    // // Delete HsCode
    // deleteHsCode(data){
    //   var index = index = this.HsCodeList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriHsCodeService.DeleteHsCode(data.id).subscribe(res => {
    //     this.HsCodeList.splice(index, 1)
    //      console.log('HsCode deleted!')
    //    })
    // }

      // Delete Commodity
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.hsCode, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.hsCode, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.hsCode, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.hsCode, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'approve') {
        observable = this.agriHsCodeService.ApproveHsCode(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.agriHsCodeService.FinalizeHsCode(event.id);
      } else if (event.flag == 'delete') {
        observable = this.agriHsCodeService.DeleteHsCode(event.id);
      } else if (event.flag == 'reject') {
        observable = this.agriHsCodeService.RejectHsCode(event.id);
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

  searchHsCode() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageAgriHsCode(this.selectedPage - 1, this.isValid);
  }

  sortData(sort: Sort) {
    const data = this.pageHsCode.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageHsCode.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageHsCode.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'commodity':
          return compare(firstValue.commodity, secondValue.commodity, isAsc);
          case 'commodityClass':
          return compare(firstValue.commodityClass, secondValue.commodityClass, isAsc);
          case 'generalCommodity':
          return compare(firstValue.generalCommodity, secondValue.generalCommodity, isAsc);
        case 'hsCode':
          return compare(firstValue.hsCode, secondValue.hsCode, isAsc);
        case 'uom':
          return compare(firstValue.uom, secondValue.uom, isAsc);
          case 'description':
          return compare(firstValue.description, secondValue.description, isAsc);
        case globalConstants.STATUS:
          return compare(firstValue.status, secondValue.status, isAsc);
        default:
          return 0;
      }
    });
  }

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('agri_hs_code').subscribe(res => {
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
    this.getPageAgriHsCode(0,this.isValid);
  }

  moveToMaster(id){
    this.agriHsCodeService.moveToMaster(id).subscribe(res => {
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


  modalSuccess($event: any) {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      console.log("Inside if");
    this.getPageAgriHsCode(this.selectedPage - 1, this.isValid);
    this.hsCodeStatus = globalConstants;
    }else{
      console.log("Inside else");
    this.ngOnInit();
    }
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

import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { GeneralBankCategoryService } from '../../services/general-bank-category.service';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../../agri/services/bulk-data.service';
import { PageGeneralBankCategory } from '../../models/PageGeneralBankCategory';


@Component({
  selector: 'app-bank-category',
  templateUrl: './bank-category.component.html',
  styleUrls: ['./bank-category.component.scss']
})
export class BankCategoryComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
   isSuccess: boolean = false;
   _statusMsg: string;

  bankCategoryStatus;
  BankCategoryList: any = [];
  // pageGeneralBankCategory: PageGeneralBankCategory;
  // selectedPage: number;
  // searchText : any = "";
  // maxSize : number=10;

  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];

  pageGeneralBankCategory: PageGeneralBankCategory;


  ngOnInit() {

    this.records = ['2', '50', '100', '200', '250'];
    this.getPageGeneralBankCategory(0);
    // this.loadAllBankCategory();
    // this.getPageGeneralBankCategory(0);
    this.bankCategoryStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public generalBankCategoryService: GeneralBankCategoryService,
    private userRightsService: UserRightsService,
  ){ }

  getPageGeneralBankCategory(page: number): void {
    this.generalBankCategoryService.getPageGeneralBankCategory(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageGeneralBankCategory = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.generalBankCategoryService.getPageGeneralBankCategory(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageGeneralBankCategory = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPageGeneralBankCategory(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getPageGeneralBankCategory(this.selectedPage - 1);
}


   // BankCategory list
   loadAllBankCategory() {
    return this.generalBankCategoryService.GetAllBankCategory().subscribe((data: {}) => {
      this.BankCategoryList = data;
    })
  }

    // // Delete BankCategory
    // deleteBankCategory(data){
    //   var index = index = this.BankCategoryList.map(x => {return x.name}).indexOf(data.name);
    //    return this.generalBankCategoryService.DeleteBankCategory(data.id).subscribe(res => {
    //     this.BankCategoryList.splice(index, 1)
    //      console.log('BankCategory deleted!')
    //    })
    // }

    // onSelect(page: number): void {
    //   console.log("selected page : " + page);
    //   this.selectedPage = page;
    //   this.getPageGeneralBankCategory(page);
    // }
  
    // getPageGeneralBankCategory(page: number): void {
    //   this.generalBankCategoryService.getPageGeneralBankCategory(page,this.searchText)
    //     .subscribe(page => this.pageGeneralBankCategory = page)
    // }

    // Delete 
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
          observable = this.generalBankCategoryService.ApproveBankCategory(event.id)
        } else if (event.flag == "finalize") {
          observable = this.generalBankCategoryService.FinalizeBankCategory(event.id)
        } else if (event.flag == "delete") {
          observable = this.generalBankCategoryService.DeleteBankCategory(event.id)
        } else if (event.flag == "reject") {
          observable = this.generalBankCategoryService.RejectBankCategory(event.id)
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

    // searchGeneralBankCategory(){
    //   this.selectedPage = 1;
    //   console.log(this.searchText)
    //   this.getPageGeneralBankCategory(this.selectedPage)
    // }

    modalSuccess($event: any) {
      (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
      // this.ngOnInit();
      // this.selectedPage = 1;
    
      console.log("page : " + this.selectedPage);
      if(this.selectedPage >= 2){
        // console.log("Inside if");
      this.getPageGeneralBankCategory(this.selectedPage - 1);
      this.bankCategoryStatus = globalConstants;
      }else{
        // console.log("Inside else");
      this.ngOnInit();
      }
    }

  sortData(sort: Sort) {
    const data = this.pageGeneralBankCategory.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageGeneralBankCategory.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageGeneralBankCategory.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case globalConstants.NAME:
            return compare(firstValue.name, secondValue.name, isAsc);
        // case 'value':
        //   return compare(firstValue.value, secondValue.value, isAsc);
        //   case globalConstants.STATUS:
        //   return compare(firstValue.status, secondValue.status, isAsc);
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

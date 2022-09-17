import { BulkDataService } from './../../agri/services/bulk-data.service';
import {Component, OnInit, ViewChild} from '@angular/core';
import { GeneralCompanyService } from '../services/general-campany-service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {globalConstants} from '../../global/globalConstants';
import {Sort} from '@angular/material';
import { PageGeneralCompany } from '../models/PageGeneralCompany';
import { UserRightsService } from '../../services/user-rights.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';

@Component({
  selector: 'app-general-company',
  templateUrl: './general-company.component.html',
  styleUrls: ['./general-company.component.scss']
})
export class GeneralCompanyComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
 companyStatus;
  companyList: any = [];

  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];

  pageGeneralCompany: PageGeneralCompany;

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getCompanyPagenatedList(0);
    this.companyStatus = globalConstants;
  
    // this.loadAllCompany();
  }

  constructor(public bulkDatas: BulkDataService,
    public generalCompanyService: GeneralCompanyService,
    private userRightsService: UserRightsService,
  ) { }

  getCompanyPagenatedList(page: number): void {
    this.generalCompanyService.getCompanyPagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageGeneralCompany = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.generalCompanyService.getCompanyPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageGeneralCompany = page);
  }

  onSelect(page: number): void {

    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getCompanyPagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getCompanyPagenatedList(this.selectedPage - 1);
}


  // Company list
  loadAllCompany() {
    return this.generalCompanyService.GetAllCompany().subscribe((data: {}) => {
      this.companyList = data;
    })
  }

  // Delete Company
  deleteCompany(data) {
    var index = index = this.companyList.map(x => { return x.name }).indexOf(data.name);
    return this.generalCompanyService.DeleteCompany(data.id).subscribe(res => {
      this.companyList.splice(index, 1);
      console.log('Company deleted!');
    });
  }

   // Delete
   delete(data, i) {
    data.index = i;
    data.flag = 'delete';
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + ' ' + data.name, data);
  }

  // Reject
  reject(data, i) {
    data.index = i;
    data.flag = 'reject';
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + ' ' + data.name, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = 'approve';
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + ' ' + data.name, data);
  }

  finalize(data, i) {
    data.index = i;
    data.flag = 'finalize';
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + ' ' + data.name, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'approve') {
        observable = this.generalCompanyService.DeleteCompany(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.generalCompanyService.FinalizeCompany(event.id);
      } else if (event.flag == 'delete') {
        observable = this.generalCompanyService.DeleteCompany(event.id);
      } else if (event.flag == 'reject') {
        observable = this.generalCompanyService.RejectCompany(event.id);
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

  sortData(sort: Sort) {
    const data = this.pageGeneralCompany.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageGeneralCompany.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageGeneralCompany.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case globalConstants.NAME:
          return compare(firstValue.name, secondValue.name, isAsc);
        case 'description':
          return compare(firstValue.description, secondValue.description, isAsc);
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
    this.getCompanyPagenatedList(this.selectedPage - 1);
    }else{
      // console.log("Inside else");
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

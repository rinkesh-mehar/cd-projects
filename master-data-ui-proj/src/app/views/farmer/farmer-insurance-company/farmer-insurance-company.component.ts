import { Component, OnInit, ViewChild } from '@angular/core';
import { FarmerInsuranceCompanyService } from '../services/farmer-insurance-company.service';
import { PageFarmerInsuranceCompany } from '../models/PageFarmerInsuranceCompany';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {DrkServiceService} from '../../services/drk-service.service';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../agri/services/bulk-data.service';

@Component({
  selector: 'app-farmer-insurance-company',
  templateUrl: './farmer-insurance-company.component.html',
  styleUrls: ['./farmer-insurance-company.component.scss']
})
export class FarmerInsuranceCompanyComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  insuranceCompanyStatus;
  InsuranceCompanyList: any = [];
  pageInsuranceCompany: PageFarmerInsuranceCompany;
  selectedPage: number = 1;
  searchText : any = "";
  maxSize : number=10;
  isValid: number = 1;
  recordsPerPage: number = 10;
   records: any = [];


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageFarmerInsuranceCompany(0, this.isValid);
    //this.loadAllInsuranceCompany();
    this.insuranceCompanyStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public farmerInsuranceCompanyService: FarmerInsuranceCompanyService,
    private userRightsService: UserRightsService,
    private drkServiceService: DrkServiceService

  ) { }

  // InsuranceCompany list
  loadAllInsuranceCompany() {
    return this.farmerInsuranceCompanyService.GetAllInsuranceCompany().subscribe((data: {}) => {
      this.InsuranceCompanyList = data;
    })
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : " + page);
    this.selectedPage = page;
    this.getPageFarmerInsuranceCompany(page, this.isValid);
  }

  getPageFarmerInsuranceCompany(page: number, isValid: number): void {
    this.farmerInsuranceCompanyService.getPageFarmerInsuranceCompany(page, this.recordsPerPage,this.searchText, isValid)
      .subscribe(page => this.pageInsuranceCompany = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.farmerInsuranceCompanyService.getPageFarmerInsuranceCompany(this.selectedPage - 1, this.recordsPerPage, this.searchText,this.isValid)
      .subscribe(page => this.pageInsuranceCompany = page);
  }

  // Delete InsuranceCompany
  // deleteInsuranceCompany(data){
  //   var index = index = this.InsuranceCompanyList.map(x => {return x.name}).indexOf(data.name);
  //    return this.farmerInsuranceCompanyService.DeleteInsuranceCompany(data.id).subscribe(res => {
  //     this.InsuranceCompanyList.splice(index, 1)
  //      console.log('InsuranceCompany  deleted!')
  //    })
  // }


  // Delete InsuranceCompany
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
        observable = this.farmerInsuranceCompanyService.ApproveInsuranceCompany(event.id)
      } else if (event.flag == "finalize") {
        observable = this.farmerInsuranceCompanyService.FinalizeInsuranceCompany(event.id)
      } else if (event.flag == "delete") {
        observable = this.farmerInsuranceCompanyService.DeleteInsuranceCompany(event.id)
      } else if (event.flag == "reject") {
        observable = this.farmerInsuranceCompanyService.RejectInsuranceCompany(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
            this.loadAllInsuranceCompany();
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

  searchInsuranceCompany(){
    this.selectedPage - 1;
    console.log(this.searchText);
    this.getPageFarmerInsuranceCompany(this.selectedPage, this.isValid);
  }

  modalSuccess($event: any) {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // this.ngOnInit();
    // this.selectedPage = 1;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getPageFarmerInsuranceCompany(this.selectedPage - 1,this.isValid);
    this.insuranceCompanyStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }
  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('farmer_insurance_company').subscribe(res => {
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

  sortData(sort: Sort) {
    const data = this.pageInsuranceCompany.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageInsuranceCompany.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageInsuranceCompany.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case globalConstants.NAME:
            return compare(firstValue.name, secondValue.name, isAsc);
            case 'insuranceType':
            return compare(firstValue.insuranceType, secondValue.insuranceType, isAsc);
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

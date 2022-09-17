import { Component, OnInit, ViewChild } from '@angular/core';

import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import { AgriBenchmarkVarietyService } from '../../services/agri-benchmark-variety.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { PageAgriBenchmarkVariety } from '../../models/PageAgriBenchmarkVariety';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../services/bulk-data.service';
// import { PageAgriBenchmarkVariety } from '../../models/PageAgriBenchmarkVariety';

@Component({
  selector: 'app-agri-benchmark-variety',
  templateUrl: './agri-benchmark-variety.component.html',
  styleUrls: ['./agri-benchmark-variety.component.scss']
})
export class AgriBenchmarkVarietyComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  benchmarkVarietyStatus;
  BenchmarkVarietyList: any = [];
  pageAgriBenchmarkVariety : PageAgriBenchmarkVariety;
  selectedPage: number = 1;
  maxSize : number=10;
  searchText : any= "";
  missing : any="";
  recordsPerPage: number = 10;
   records: any = [];


  ngOnInit() {
    // this.loadAllAgriBenchmarkVariety();
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageAgriBenchmarkVariety(0);
    this.benchmarkVarietyStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    private AgriBenchmarkVarietyService: AgriBenchmarkVarietyService,
    private userRightsService: UserRightsService,
  ){ }

   // loadAllAgriBenchmarkVariety list
   loadAllAgriBenchmarkVariety() {
    return this.AgriBenchmarkVarietyService.GetAllBenchmarkVariety().subscribe((data: any) => {
      this.BenchmarkVarietyList = data;
    })
  }

    // // Delete AgriBenchmarkVariety
    // deleteAgriBenchmarkVariety(data){
    //   var index = index = this.EcosystemList.map(x => {return x.name}).indexOf(data.name);
    //    return this.AgriBenchmarkVarietyService.DeleteAgriBenchmarkVariety(data.id).subscribe(res => {
    //     this.EcosystemList.splice(index, 1)
    //      console.log('Agri Ecosystem deleted!')
    //    })
    // }

    // onSelect(page: number): void {
    //   console.log("selected page : "+page);
    //   this.selectedPage=page;
    //   this.getPageAgriBenchmarkVariety(page);
    // }
    
   getPageAgriBenchmarkVariety(page:number): void {
      this.AgriBenchmarkVarietyService.getPageAgriBenchmarkVariety(page, this.recordsPerPage,this.searchText,this.missing)
      .subscribe(page => this.pageAgriBenchmarkVariety = page)
    }


   loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.AgriBenchmarkVarietyService.getPageAgriBenchmarkVariety(this.selectedPage - 1, this.recordsPerPage, this.searchText,this.missing)
      .subscribe(page => this.pageAgriBenchmarkVariety = page);
  }

      // Delete 
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.variety, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.variety, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.variety, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.variety, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        observable = this.AgriBenchmarkVarietyService.ApproveBenchmarkVariety(event.id)
      } else if (event.flag == "finalize") {
        observable = this.AgriBenchmarkVarietyService.FinalizeBenchmarkVariety(event.id)
      } else if (event.flag == "delete") {
        observable = this.AgriBenchmarkVarietyService.DeleteBenchmarkVariety(event.id)
      } else if (event.flag == "reject") {
        observable = this.AgriBenchmarkVarietyService.RejectBenchmarkVariety(event.id)
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

  searchBenchmarkVariety() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageAgriBenchmarkVariety(this.selectedPage - 1);
  }

  onClickMissing(){
    this.missing = 1;
    this.getPageAgriBenchmarkVariety(0);
  }

  moveToMaster(id){
    this.AgriBenchmarkVarietyService.moveToMaster(id).subscribe(res => {
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

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    
    console.log("selected page : "+page);
    this.selectedPage=page;
    this.getPageAgriBenchmarkVariety(page);
  }

  modalSuccess($event: any) {

    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    

    // this.ngOnInit();
    // this.selectedPage = 1;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getPageAgriBenchmarkVariety(this.selectedPage - 1);
    this.benchmarkVarietyStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }
  

  sortData(sort: Sort) {
    const data = this.pageAgriBenchmarkVariety.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriBenchmarkVariety.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageAgriBenchmarkVariety.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case 'region':
            return compare(firstValue.region, secondValue.region, isAsc);
        case 'state':
          return compare(firstValue.state, secondValue.state, isAsc);
        case 'season':
          return compare(firstValue.season, secondValue.season, isAsc);
        case 'commodity':
          return compare(firstValue.commodity, secondValue.commodity, isAsc);
        case 'variety':
          return compare(firstValue.variety, secondValue.variety, isAsc);
          case 'isDrkBenchmark':
            return compare(firstValue.isDrkBenchmark, secondValue.isDrkBenchmark, isAsc);
          case 'isAgmBenchmark':
          return compare(firstValue.isAgmBenchmark, secondValue.isAgmBenchmark, isAsc);
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

import { Component, OnInit, ViewChild } from '@angular/core';

import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import { PageAgriQualityChart } from '../../models/PageAgriQualityChart';
import { AgriQualityChartService } from '../../services/agri-quality-chart.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../services/bulk-data.service';

@Component({
  selector: 'app-agri-quality-chart',
  templateUrl: './agri-quality-chart.component.html',
  styleUrls: ['./agri-quality-chart.component.scss']
})
export class AgriQualityChartComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  agriQualityChartStatus;
  QualityChartList: any = [];
  pageAgriQualityChart : PageAgriQualityChart;
  selectedPage: number = 1;
  maxSize : number=10;
  searchText : any= "";
  missing : any="";

  recordsPerPage: number = 10;
   records: any = [];


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    // this.loadAllAgriQualityChart();
    this.getPageAgriQualityChart(0);
    this.agriQualityChartStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    private AgriQualityChartService: AgriQualityChartService,
    private userRightsService: UserRightsService,
  ){ }

   // loadAllAgriQualityChart list
   loadAllAgriQualityChart() {
    return this.AgriQualityChartService.GetAllQualityChart().subscribe((data: any) => {
      this.QualityChartList = data;
    })
  }

    // // Delete AgriQualityChart
    // deleteAgriQualityChart(data){
    //   var index = index = this.EcosystemList.map(x => {return x.name}).indexOf(data.name);
    //    return this.AgriQualityChartService.DeleteAgriQualityChart(data.id).subscribe(res => {
    //     this.EcosystemList.splice(index, 1)
    //      console.log('Agri Ecosystem deleted!')
    //    })
    // }

    onSelect(page: number): void {
      (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
      console.log("selected page : "+page);
      this.selectedPage=page;
      this.getPageAgriQualityChart(page);
    }
    
   getPageAgriQualityChart(page:number): void {
      this.AgriQualityChartService.getPageAgriQualityChart(page, this.recordsPerPage,this.searchText,this.missing)
      .subscribe(page => this.pageAgriQualityChart = page)
    }

    loadData(event: any) {
      console.log('pages ', event.target.value);
      this.recordsPerPage = event.target.value || 10;
      this.AgriQualityChartService.getPageAgriQualityChart(this.selectedPage - 1, this.recordsPerPage, this.searchText,this.missing)
        .subscribe(page => this.pageAgriQualityChart = page);
    }

      // Delete 
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
      if (event.flag == "approve") {
        observable = this.AgriQualityChartService.ApproveQualityChart(event.id)
      } else if (event.flag == "finalize") {
        observable = this.AgriQualityChartService.FinalizeQualityChart(event.id)
      } else if (event.flag == "delete") {
        observable = this.AgriQualityChartService.DeleteQualityChart(event.id)
      } else if (event.flag == "reject") {
        observable = this.AgriQualityChartService.RejectQualityChart(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
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

  searchQualityChart(){
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageAgriQualityChart(this.selectedPage - 1);
  }


  modalSuccess($event: any) {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // this.ngOnInit();
    // this.selectedPage = 1;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getPageAgriQualityChart(this.selectedPage - 1);
    this.agriQualityChartStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }

    onClickMissing(){
      this.missing = 1;
      this.getPageAgriQualityChart(0);
    }
  
    moveToMaster(id){
      this.AgriQualityChartService.moveToMaster(id).subscribe(res => {
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

    sortData(sort: Sort) {
      const data = this.pageAgriQualityChart.content.slice();
      if (!sort.active || sort.direction == '') {
        this.pageAgriQualityChart.content = data;
        return;
      }
    
      function compare(firstValue, secondValue, isAsc: boolean) {
        return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
      }
    
      this.pageAgriQualityChart.content = data.sort((firstValue, secondValue) => {
        const isAsc = sort.direction == 'asc';
        switch (sort.active) {
          case globalConstants.ID:
            return compare(+firstValue.id, +secondValue.id, isAsc);
            case 'commodity':
              return compare(firstValue.commodity, secondValue.commodity, isAsc);
          case 'phenophase':
            return compare(firstValue.phenophase, secondValue.phenophase, isAsc);
          case 'healthParameter':
            return compare(firstValue.healthParameter, secondValue.healthParameter, isAsc);
          case 'gradeI':
            return compare(firstValue.gradeI, secondValue.gradeI, isAsc);
          case 'gradeII':
            return compare(firstValue.gradeII, secondValue.gradeII, isAsc);
            case 'gradeIII':
              return compare(firstValue.gradeIII, secondValue.gradeIII, isAsc);
            case 'mingradeI':
            return compare(firstValue.mingradeI, secondValue.mingradeI, isAsc);
            case 'maxgradeI':
            return compare(firstValue.maxgradeI, secondValue.maxgradeI, isAsc);
            case 'mingradeII':
            return compare(firstValue.mingradeII, secondValue.mingradeII, isAsc);
            case 'maxgradeII':
            return compare(firstValue.maxgradeII, secondValue.maxgradeII, isAsc);
            case 'mingradeIII':
            return compare(firstValue.mingradeIII, secondValue.mingradeIII, isAsc);
            case 'maxgradeIII':
            return compare(firstValue.maxgradeIII, secondValue.maxgradeIII, isAsc);
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

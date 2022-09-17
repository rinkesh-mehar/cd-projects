import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import { AgriControlMeasuresService } from '../../services/agri-control-measures.service';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { BulkDataService } from '../../services/bulk-data.service';
import { PageAgriControlMeasures } from '../../models/PageAgriControlMeasures';


@Component({
  selector: 'app-agri-stress-control-measures',
  templateUrl: './agri-control-measures.component.html',
  styleUrls: ['./agri-control-measures.component.scss']
})
export class AgriControlMeasuresComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  strControlMeasuresStatus;

  ControlMeasuresList: any = [];
  searchText: any = "";

  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];

  pageAgriControlMeasures: PageAgriControlMeasures;


  ngOnInit() {
    (document.querySelector('thead th input') as HTMLInputElement).checked = false
    // this.GetAllAgriControlMeasures();
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageControlMeasures(0);
    this.strControlMeasuresStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public agriStressControlMeasuresService : AgriControlMeasuresService,
    private userRightsService: UserRightsService,
  ){ }

  getPageControlMeasures(page: number) {
    return this.agriStressControlMeasuresService.getPageControlMeasures(page, this.recordsPerPage,this.searchText).subscribe((data) => {
      this.pageAgriControlMeasures = data;
    })
  }

   // StressControlMeasures list
   GetAllAgriControlMeasures() {
    return this.agriStressControlMeasuresService.GetAllAgriControlMeasures().subscribe((data: {}) => {
      this.ControlMeasuresList = data;
    })
  }

    // // Delete StressControlMeasures
    // deleteStressControlMeasures(data){
    //   var index = index = this.StressControlMeasuresList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriStressControlMeasuresService.DeleteStressControlMeasures(data.id).subscribe(res => {
    //     this.StressControlMeasuresList.splice(index, 1)
    //      console.log('StressControlMeasures deleted!')
    //    })
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
  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'approve') {
        observable = this.agriStressControlMeasuresService.ApproveStressControlMeasures(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.agriStressControlMeasuresService.FinalizeStressControlMeasures(event.id);
      } else if (event.flag == 'delete') {
        observable = this.agriStressControlMeasuresService.DeleteAgriStressControlMeasures(event.id);
      } else if (event.flag == 'reject') {
        observable = this.agriStressControlMeasuresService.RejectStressControlMeasures(event.id);
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

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPageControlMeasures(page);
}

searchControlMeasures() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageControlMeasures(this.selectedPage - 1);
  }

  modalSuccess($event: any) {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // this.ngOnInit();
    // this.selectedPage = 1;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      console.log("Inside if");
    this.getPageControlMeasures(this.selectedPage - 1);
    this.strControlMeasuresStatus = globalConstants;
    }else{
      console.log("Inside else");
    this.ngOnInit();
    }
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriStressControlMeasuresService.getPageControlMeasures(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageAgriControlMeasures = page);
  }

  sortData(sort: Sort) {
    const data = this.pageAgriControlMeasures.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriControlMeasures.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageAgriControlMeasures.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case globalConstants.NAME:
            return compare(firstValue.name, secondValue.name, isAsc);
          case globalConstants.STATUS:
          return compare(firstValue.status, secondValue.status, isAsc);
        default:
          return 0;
      }
    });
  }

}

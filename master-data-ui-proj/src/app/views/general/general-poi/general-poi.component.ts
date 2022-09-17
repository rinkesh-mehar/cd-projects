import { PageGeneralPoi } from './../models/PageGeneralPoi';
import { Component, OnInit, ViewChild } from '@angular/core';
import { GeneralPoiService } from '../services/general-poi.service';
import { globalConstants } from '../../global/globalConstants';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../agri/services/bulk-data.service';


@Component({
  selector: 'app-general-poi',
  templateUrl: './general-poi.component.html',
  styleUrls: ['./general-poi.component.scss']
})

export class GeneralPoiComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  isSubmitted: boolean = false;
   isSuccess: boolean = false;
   _statusMsg: string;
  poiStatus;
  PoiList: any = [];

  searchText: any = '';
   selectedPage: number = 1;
   maxSize =10;
   recordsPerPage: number = 10;
   records: any = [];

   pageGeneralPoi: PageGeneralPoi;


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
	   this.getPoiPagenatedList(0);
    // this.loadAllPoi();
    this.poiStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public generalPoiService: GeneralPoiService,
    private userRightsService: UserRightsService,

  ) { }

  getPoiPagenatedList(page: number): void {
    this.generalPoiService.getPoiPagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageGeneralPoi = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.generalPoiService.getPoiPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageGeneralPoi = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPoiPagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getPoiPagenatedList(this.selectedPage - 1);
}

  // Poi list
  loadAllPoi() {
    return this.generalPoiService.GetAllPoi().subscribe((data: {}) => {
      this.PoiList = data;
    })
  }

  // Delete Poi
  // deletePoi(data) {
  //   var index = index = this.PoiList.map(x => { return x.name }).indexOf(data.name);
  //   return this.generalPoiService.DeletePoi(data.id).subscribe(res => {
  //     this.PoiList.splice(index, 1)
  //     console.log('Poi deleted!')
  //   })
  // }




  // Delete Poi
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
        observable = this.generalPoiService.ApprovePoi(event.id)
      } else if (event.flag == "finalize") {
        observable = this.generalPoiService.FinalizePoi(event.id)
      } else if (event.flag == "delete") {
        observable = this.generalPoiService.DeletePoi(event.id)
      } else if (event.flag == "reject") {
        observable = this.generalPoiService.RejectPoi(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            // this._statusMsg = res.message;
            this.loadAllPoi();
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


  modalSuccess($event: any) {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getPoiPagenatedList(this.selectedPage - 1);
    this.poiStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }


  sortData(sort: Sort) {
    const data = this.pageGeneralPoi.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageGeneralPoi.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageGeneralPoi.content = data.sort((firstValue, secondValue) => {
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

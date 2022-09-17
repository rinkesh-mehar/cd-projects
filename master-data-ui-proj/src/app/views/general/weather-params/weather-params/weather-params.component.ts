import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import { GeneralWeatherParamsService } from '../../services/general-weather-params.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../../agri/services/bulk-data.service';
import { PageGeneralWeatherParams } from '../../models/PageGeneralWeatherParams';

@Component({
  selector: 'app-weather-params',
  templateUrl: './weather-params.component.html',
  styleUrls: ['./weather-params.component.scss']
})
export class WeatherParamsComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  weatherParamsStatus;
  WeatherParamsList: any = [];

  searchText: any = '';
   selectedPage: number = 1;
   maxSize =10;
   recordsPerPage: number = 10;
   records: any = [];

   pageGeneralWeatherParams: PageGeneralWeatherParams;

  
  // pageCommodity :  PageAgriCommodity;
  // selectedPage : number = 0;


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
	   this.getGeneralWeatherParamPagenatedList(0);
    // this.getPageAgriCommodity(0);
    // this.loadAllWeatherParams();
    this.weatherParamsStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
      public generalWeatherparamsService: GeneralWeatherParamsService,
      private userRightsService: UserRightsService,
  ) {
  }


  getGeneralWeatherParamPagenatedList(page: number): void {
    this.generalWeatherparamsService.getGeneralWeatherParamPagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageGeneralWeatherParams = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.generalWeatherparamsService.getGeneralWeatherParamPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageGeneralWeatherParams = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getGeneralWeatherParamPagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getGeneralWeatherParamPagenatedList(this.selectedPage - 1);
}

  //WeatherParams list
  loadAllWeatherParams() {
    return this.generalWeatherparamsService.GetAllAgriWeatherParams().subscribe((data: {}) => {
      this.WeatherParamsList = data;
    });
  }


  // onSelect(page: number): void {
  //   console.log("selected page : "+page);
  //   this.selectedPage=page;
  //   this.getPageAgriCommodity(page);
  // }

  // getPageAgriCommodity(page:number): void {
  //   this.WeatherParamsService.getPageAgriCommodity(page)
  //       .subscribe(page => this.pageCommodity = page)
  // }

  // // Delete WeatherParams
  // deleteWeatherParams(data){
  //   var index = index = this.WeatherParamsList.map(x => {return x.name}).indexOf(data.name);
  //    return this.weatherParamsService.DeleteAgriWeatherParams(data.id).subscribe(res => {
  //     this.WeatherParamsList.splice(index, 1)
  //      console.log('WeatherParams deleted!')
  //    })
  // }

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
        observable = this.generalWeatherparamsService.ApproveGeneralWeatherParams(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.generalWeatherparamsService.FinalizeGeneralWeatherParams(event.id);
      } else if (event.flag == 'delete') {
        observable = this.generalWeatherparamsService.DeleteGeneralWeatherParams(event.id);
      } else if (event.flag == 'reject') {
        observable = this.generalWeatherparamsService.RejectGeneralWeatherParams(event.id);
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

  modalSuccess($event: any) {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getGeneralWeatherParamPagenatedList(this.selectedPage - 1);
    this.weatherParamsStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }

  sortData(sort: Sort) {
    const data = this.pageGeneralWeatherParams.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageGeneralWeatherParams.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageGeneralWeatherParams.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case globalConstants.NAME:
            return compare(firstValue.name, secondValue.name, isAsc);
            case 'label':
            return compare(firstValue.lable, secondValue.lable, isAsc);
            case 'unit':
            return compare(firstValue.unit, secondValue.unit, isAsc);
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

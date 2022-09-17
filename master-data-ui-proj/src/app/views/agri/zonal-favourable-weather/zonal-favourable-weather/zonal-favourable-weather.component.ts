import { Component, OnInit, ViewChild } from '@angular/core';
import { ZonalFavourableWeatherService } from '../../services/zonal-favourable-weather.service';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {Sort} from '@angular/material';
import {DrkServiceService} from '../../../services/drk-service.service';
import { BulkDataService } from '../../services/bulk-data.service';
import { PageZonalFavourableWeather } from '../../models/PageZonalFavourableWeather';


@Component({
  selector: 'app-zonal-favourable-weather',
  templateUrl: './zonal-favourable-weather.component.html',
  styleUrls: ['./zonal-favourable-weather.component.scss']
})
export class ZonalFavourableWeatherComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  favourableWeatherStatus;
  FavourableWeatherList: any = [];
  pageZonalFavourableWeather: PageZonalFavourableWeather
  selectedPage: number = 1;
  maxSize : number=10;
  searchText : any= "";
  isValid: number = 1;
  missing : any="";

  recordsPerPage: number = 10;
   records: any = [];


  

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageZonalFavourableWeather(0, this.isValid);
  //  this.loadAllFavourableWeather();
    this.favourableWeatherStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public zonalFavourableWeatherService : ZonalFavourableWeatherService,
    private userRightsService: UserRightsService,
    private drkServiceService: DrkServiceService
  ){ }

   // FavourableWeather list
   loadAllFavourableWeather() {
    return this.zonalFavourableWeatherService.GetAllFavourableWeather().subscribe((data: {}) => {
      this.FavourableWeatherList = data;
    })
  }


    onSelect(page: number): void {
      (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
      this.bulkDatas.disbled = true;
    console.log("selected page : "+page);
    this.selectedPage=page;
    this.getPageZonalFavourableWeather(page, this.isValid);
  }
  
  getPageZonalFavourableWeather(page:number, isValid: number): void {
    this.zonalFavourableWeatherService.getPageZonalFavourableWeather(page, this.recordsPerPage,this.searchText, isValid,this.missing)
    .subscribe(page => this.pageZonalFavourableWeather = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.zonalFavourableWeatherService.getPageZonalFavourableWeather(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing)
      .subscribe(page => this.pageZonalFavourableWeather = page);
  }


    // // Delete FavourableWeather
    // deleteFavourableWeather(data){
    //   var index = index = this.FavourableWeatherList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriFavourableWeatherService.DeleteFavourableWeather(data.id).subscribe(res => {
    //     this.FavourableWeatherList.splice(index, 1)
    //      console.log('FavourableWeather deleted!')
    //    })
    // }

    // Delete 
  delete(data, i) {
    data.index = i;
    data.flag = 'delete';
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg, data);
  }

  // Reject
  reject(data, i) {
    data.index = i;
    data.flag = 'reject';
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = 'approve';
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg, data);
  }

  finalize(data, i) {
    data.index = i;
    data.flag = 'finalize';
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'approve') {
        observable = this.zonalFavourableWeatherService.ApproveFavourableWeather(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.zonalFavourableWeatherService.FinalizeFavourableWeather(event.id);
      } else if (event.flag == 'delete') {
        observable = this.zonalFavourableWeatherService.DeleteFavourableWeather(event.id);
      } else if (event.flag == 'reject') {
        observable = this.zonalFavourableWeatherService.RejectFavourableWeather(event.id);
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

  searchFavourableWeather() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageZonalFavourableWeather(this.selectedPage - 1, this.isValid);
  }

  sortData(sort: Sort) {
    const data = this.pageZonalFavourableWeather.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageZonalFavourableWeather.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageZonalFavourableWeather.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case 'state':
            return compare(firstValue.state , secondValue.state, isAsc);
          case 'aczName':
            return compare(firstValue.aczName , secondValue.aczName, isAsc);
          case 'zonalCommodity':
        case 'phenophase':
          return compare(firstValue.phenophase, secondValue.phenophase, isAsc);
        case 'weatherParameter':
          return compare(firstValue.weatherParameter, secondValue.weatherParameter, isAsc);
        case 'specificationAverage':
          return compare(firstValue.specificationAverage, secondValue.specificationAverage, isAsc);
        case 'specificationLower':
          return compare(firstValue.specificationLower, secondValue.specificationLower, isAsc);
        case 'specificationUpper':
          return compare(firstValue.specificationUpper, secondValue.specificationUpper, isAsc);
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
      this.getPageZonalFavourableWeather(0, this.isValid);
      (this.selectedPage - 1);
    this.favourableWeatherStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }
  

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('agri_favourable_weather').subscribe(res => {
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
    this.getPageZonalFavourableWeather(0,this.isValid);
    console.log("missing button is clicked!!");
  }

  moveToMaster(id){
    console.log("move to master is clicked and id is "+id);
    this.zonalFavourableWeatherService.moveToMaster(id).subscribe(res => {
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

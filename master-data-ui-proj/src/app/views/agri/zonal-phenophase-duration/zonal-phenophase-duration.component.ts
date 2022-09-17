import { Component, OnInit, ViewChild } from '@angular/core';
import { ZonalPhenophaseDurationService } from '../services/zonal-phenophase-duration.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import { ImagePreviewModalComponent } from '../../global/image-preview-modal/image-preview-modal.component';
import { PageZonalPhenophaseDuration } from '../models/PageZonalPhenophaseDuration';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {Sort} from '@angular/material';
import {DrkServiceService} from '../../services/drk-service.service';
import { BulkDataService } from '../services/bulk-data.service';


@Component({
  selector: 'app-zonal-phenophase-duration',
  templateUrl: './zonal-phenophase-duration.component.html',
  styleUrls: ['./zonal-phenophase-duration.component.scss']
})
export class ZonalPhenophaseDurationComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('imagePreviewModal') public imagePreviewModal: ImagePreviewModalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  phenophaseDurationStatus;
  pageZonalPhenophaseDuration: PageZonalPhenophaseDuration;
  selectedPage: number = 1;
  maxSize = 10;
  searchText: any="";

  isValid: number = 1;

  PhenophaseDurationList: any = [];
  missing : any="";

  recordsPerPage: number = 10;
   records: any = [];

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    // this.loadAllPhenophaseDuration();
    this.getpageZonalPhenophaseDuration(0, this.isValid);
    this.phenophaseDurationStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public zonalPhenophaseDurationService: ZonalPhenophaseDurationService,
    private userRightsService: UserRightsService,
    private drkServiceService: DrkServiceService
  ) { }

  // PhenophaseDuration list
  loadAllPhenophaseDuration() {
    return this.zonalPhenophaseDurationService.GetAllPhenophaseDuration().subscribe((data: {}) => {
      this.PhenophaseDurationList = data;
    })
  }

  // // Delete PhenophaseDuration
  //     deletePhenophaseDuration(data){
  //       var index = index = this.PhenophaseDurationList.map(x => {return x.name}).indexOf(data.name);
  //        return this.agriPhenophaseDurationService.DeletePhenophaseDuration(data.id).subscribe(res => {
  //         this.PhenophaseDurationList.splice(index, 1)
  //          console.log('Phenophase Duration deleted!')
  //        })
  //     }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    this.getpageZonalPhenophaseDuration(page, this.isValid);
  }

  getpageZonalPhenophaseDuration(page: number, isValid: number): void {
    this.zonalPhenophaseDurationService.getPageZonalPhenophaseDuration(page, this.recordsPerPage,this.searchText, isValid,this.missing)
      .subscribe(page => this.pageZonalPhenophaseDuration = page)
  }


  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.zonalPhenophaseDurationService.getPageZonalPhenophaseDuration(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing)
      .subscribe(page => this.pageZonalPhenophaseDuration = page);
  }


  // Delete 
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg , data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg , data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg , data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg , data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        observable = this.zonalPhenophaseDurationService.ApprovePhenophaseDuration(event.id)
      } else if (event.flag == "finalize") {
        observable = this.zonalPhenophaseDurationService.FinalizePhenophaseDuration(event.id)
      } else if (event.flag == "delete") {
        observable = this.zonalPhenophaseDurationService.DeletePhenophaseDuration(event.id)
      } else if (event.flag == "reject") {
        observable = this.zonalPhenophaseDurationService.RejectPhenophaseDuration(event.id)
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

  imagePreview(title, src) {
    this.imagePreviewModal.showModal(title, src);
  }


  searchPhenophaseDuration() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getpageZonalPhenophaseDuration(this.selectedPage - 1, this.isValid);
  }

  sortData(sort: Sort) {
    const data = this.pageZonalPhenophaseDuration.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageZonalPhenophaseDuration.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageZonalPhenophaseDuration.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'state':
          return compare(firstValue.state, secondValue.state, isAsc);
          case 'aczName':
          return compare(firstValue.aczName, secondValue.aczName, isAsc);
        case 'zonalCommodity':
          return compare(firstValue.zonalCommodity, secondValue.zonalCommodity, isAsc);
        case 'zonalVariety':
          return compare(firstValue.zonalVariety, secondValue.zonalVariety, isAsc);
        case 'phenophase':
          return compare(firstValue.phenophase, secondValue.phenophase, isAsc);
        case 'startDas':
          return compare(firstValue.startDas, secondValue.startDas, isAsc);
        case 'endDas':
          return compare(firstValue.endDas, secondValue.endDas, isAsc);
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
    this.getpageZonalPhenophaseDuration(this.selectedPage - 1, this.isValid);
    this.phenophaseDurationStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }
  

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('zonal_phenophase_duration').subscribe(res => {
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
    this.getpageZonalPhenophaseDuration(0,this.isValid);
  }

  moveToMaster(id){
    this.zonalPhenophaseDurationService.moveToMaster(id).subscribe(res => {
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

import { Component, OnInit, ViewChild } from '@angular/core';

import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import { ZonalVarietyQualityService } from '../../services/zonal-variety-quality.service';
import { PageZonalVarietyQuality } from '../../models/PageZonalVarietyQuality';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {Sort} from '@angular/material';
import {DrkServiceService} from '../../../services/drk-service.service';
import {BulkDataService} from '../../services/bulk-data.service';


@Component({
  selector: 'app-zonal-variety-quality',
  templateUrl: './zonal-variety-quality.component.html',
  styleUrls: ['./zonal-variety-quality.component.scss']
})
export class ZonalVarietyQualityComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  agriVarietyQualityStatus;
  VarietyQualityList: any = [];
  pageAgriVarietyQuality: PageZonalVarietyQuality;
  selectedPage: number = 1;
  maxSize : number=10;
  searchText : any= "";
  isValid: number = 1;
  missing : any="";

  recordsPerPage: number = 10;
  records: any = [];


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    // this.loadAllAgriVarietyQuality();
    this.getPageAgriVarietyQuality(0, this.isValid);
    this.agriVarietyQualityStatus = globalConstants;
  }

  constructor(
      public bulkDatas: BulkDataService,
      // tslint:disable-next-line:no-shadowed-variable
    private zonalVarietyQualityService: ZonalVarietyQualityService,
    private userRightsService: UserRightsService,
      private drkServiceService: DrkServiceService
  ){ }

   // loadAllAgriVarietyQuality list
   loadAllAgriVarietyQuality() {
    return this.zonalVarietyQualityService.GetAllVarietyQuality().subscribe((data: any) => {
      this.VarietyQualityList = data;
    })
  }

    // // Delete AgriVarietyQuality
    // deleteAgriVarietyQuality(data){
    //   var index = index = this.EcosystemList.map(x => {return x.name}).indexOf(data.name);
    //    return this.AgriVarietyQualityService.DeleteAgriVarietyQuality(data.id).subscribe(res => {
    //     this.EcosystemList.splice(index, 1)
    //      console.log('Agri Ecosystem deleted!')
    //    })
    // }

    onSelect(page: number): void {
      (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
      console.log("selected page : "+page);
      this.selectedPage=page;
      this.getPageAgriVarietyQuality(page, this.isValid);
    }

  bulkData(key, tableName) {

    const Values = [];
    const getValue = document.querySelectorAll<HTMLInputElement>('table tbody input:checked');

    getValue.forEach(function (data, i) {
      Values.push(data.value);
    });

    const AllData = {status: key, tableName: tableName, ids: Values.toString()};

    this.bulkDatas.getData(AllData)
        .subscribe(data => {
          data;
          if (data.success == true) {
            this.successModal.showModal('SUCCESS', data.message, '');

          } else {
            this.errorModal.showModal('ERROR', data.error, '');

          }
        });
  }

  getPageAgriVarietyQuality(page: number, isValid: number): void {
      this.zonalVarietyQualityService.getPageAgriVarietyQuality(page, this.recordsPerPage,this.searchText, isValid,this.missing)
      .subscribe(page => this.pageAgriVarietyQuality = page)
    }

    loadData(event: any) {
      console.log('pages ', event.target.value);
      this.recordsPerPage = event.target.value || 10;
      this.zonalVarietyQualityService.getPageAgriVarietyQuality(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing)
        .subscribe(page => this.pageAgriVarietyQuality = page);
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
        observable = this.zonalVarietyQualityService.ApproveVarietyQuality(event.id)
      } else if (event.flag == "finalize") {
        observable = this.zonalVarietyQualityService.FinalizeVarietyQuality(event.id)
      } else if (event.flag == "delete") {
        observable = this.zonalVarietyQualityService.DeleteVarietyQuality(event.id)
      } else if (event.flag == "reject") {
        observable = this.zonalVarietyQualityService.RejectVarietyQuality(event.id)
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

  searchVarietyQuality() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageAgriVarietyQuality(this.selectedPage - 1, this.isValid);
  }

  sortData(sort: Sort) {
    const data = this.pageAgriVarietyQuality.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriVarietyQuality.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageAgriVarietyQuality.content = data.sort((firstValue, secondValue) => {
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
        case 'currentQuality':
          return compare(firstValue.currentQuality, secondValue.currentQuality, isAsc);
        case 'estimatedQuality':
          return compare(firstValue.estimatedQuality, secondValue.estimatedQuality, isAsc);
        case 'allowableVarianceInQuality':
          return compare(firstValue.allowableVarianceInQuality, secondValue.allowableVarianceInQuality, isAsc);
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
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      console.log("Inside if");
    this.getPageAgriVarietyQuality(this.selectedPage - 1,this.isValid);
    this.agriVarietyQualityStatus = globalConstants;
    }else{
      console.log("Inside else");
    this.ngOnInit();
    }
  }

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('agri_variety_quality').subscribe(res => {
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
    this.getPageAgriVarietyQuality(0,this.isValid);
  }

  moveToMaster(id){
    this.zonalVarietyQualityService.moveToMaster(id).subscribe(res => {
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
}

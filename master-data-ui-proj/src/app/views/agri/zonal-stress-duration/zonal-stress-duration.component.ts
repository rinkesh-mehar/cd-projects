import { Component, OnInit, ViewChild } from '@angular/core';
import { PageZonalStressDuration } from '../models/PageZonalStressDuration';
import { ZonalStressDurationService } from '../services/zonal-stress-duration.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import { ImagePreviewModalComponent } from '../../global/image-preview-modal/image-preview-modal.component';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { BulkDataService } from '../services/bulk-data.service';
import {DrkServiceService} from '../../services/drk-service.service';

@Component({
  selector: 'app-zonal-stress-duration',
  templateUrl: './zonal-stress-duration.component.html',
  styleUrls: ['./zonal-stress-duration.component.scss']
})
export class ZonalStressDurationComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('imagePreviewModal') public imagePreviewModal: ImagePreviewModalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  districtCommoditystressStatus;
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;


  StressList: any = [];
  // pageStress: PageAgriStress;

  // selectedPage: number = 1;
  // maxSize = 10;
  // searchText: any="";


  pageAgriDistrictCommodityStress : PageZonalStressDuration 
  selectedPage: number = 1;
  maxSize = 10;
  searchText: any="";
  isValid: number = 1;

  recordsPerPage: number = 10;
  records: any = [];

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageAgriDistrictCommodityStress(0, this.isValid);
    //this.loadAllStress();
    this.districtCommoditystressStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public zonalStressDurationService: ZonalStressDurationService,
    private userRightsService: UserRightsService,
    private drkServiceService: DrkServiceService
  ) { }

  // Stress list
  loadAllStress() {
    return this.zonalStressDurationService.GetAllStress().subscribe((data: {}) => {
      this.StressList = data;
    })
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

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    this.getPageAgriDistrictCommodityStress(page, this.isValid);
  }

  getPageAgriDistrictCommodityStress(page: number, isValid: number): void {
    this.zonalStressDurationService.getPageZonalStressDuration(page, this.recordsPerPage, this.searchText, isValid)
      .subscribe(page => this.pageAgriDistrictCommodityStress = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.zonalStressDurationService.getPageZonalStressDuration(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid)
      .subscribe(page => this.pageAgriDistrictCommodityStress = page);
  }

  // // Delete Stress
  // deleteStress(data){
  //   var index = index = this.StressList.map(x => {return x.name}).indexOf(data.name);
  //    return this.agriStressService.DeleteStress(data.id).subscribe(res => {
  //     this.StressList.splice(index, 1)
  //      console.log('Stress deleted!')
  //    })
  // }

  // Delete Commodity
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
      if (event.flag == 'approve') {
        observable = this.zonalStressDurationService.ApproveStress(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.zonalStressDurationService.FinalizeStress(event.id);
      } else if (event.flag == 'delete') {
        observable = this.zonalStressDurationService.DeleteStress(event.id);
      } else if (event.flag == 'reject') {
        observable = this.zonalStressDurationService.RejectStress(event.id);
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

  searchStress() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageAgriDistrictCommodityStress(this.selectedPage - 1, this.isValid);
  }

  sortData(sort: Sort) {
    const data = this.pageAgriDistrictCommodityStress.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriDistrictCommodityStress.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageAgriDistrictCommodityStress.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'zonalCommodity':
          return compare(firstValue.zonalCommodity, secondValue.zonalCommodity, isAsc);
        case 'stressType':
          return compare(firstValue.stressType, secondValue.stressType, isAsc);
        case 'stressName':
          return compare(firstValue.stressName, secondValue.stressName, isAsc);
        case 'stateName':
          return compare(firstValue.stateName, secondValue.stateName, isAsc);
        case 'acz':
          return compare(firstValue.acz, secondValue.acz, isAsc);       
        // case 'scientificName':
        //   return compare(firstValue.scientificName, secondValue.scientificName, isAsc);
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
    if (this.selectedPage > 0){

      this.onSelect((this.selectedPage - 1));
      (document.querySelector('thead th input') as HTMLInputElement).checked = false
    } else {
      this.onSelect(this.selectedPage);
      (document.querySelector('thead th input') as HTMLInputElement).checked = false
    }
  }

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('agri_stress').subscribe(res => {
      this.isSubmitted = true;
      if (res) {
        this.ngOnInit();
        this.isSuccess = res.success;
        if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
        }
      }
    });
    this.ngOnInit();
  }
}

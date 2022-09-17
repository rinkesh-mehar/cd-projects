import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriCommodityPlantPartService } from '../services/agri-commodityPlantPart.service';
import { PageAgriCommodityPlantPart } from '../models/PageAgriCommodityPlantPart';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import { ImagePreviewModalComponent } from '../../global/image-preview-modal/image-preview-modal.component';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {Sort} from '@angular/material';
import {DrkServiceService} from '../../services/drk-service.service';
import {BulkDataService} from '../services/bulk-data.service';

@Component({
  selector: 'app-agri-commodity-plant-part',
  templateUrl: './agri-commodity-plant-part.component.html',
  styleUrls: ['./agri-commodity-plant-part.component.scss']
})
export class AgriCommodityPlantPartComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('imagePreviewModal') public imagePreviewModal: ImagePreviewModalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  commodityPlantPartStatus;
  CommodityPlantPartList: any = [];
  pageCommodityPlantPart: PageAgriCommodityPlantPart;
  selectedPage: number = 1;
  maxSize = 10;
  searchText: String = "";
  isValid: number = 1;
  missing : any="";
  recordsPerPage: number = 10;
   records: any = [];

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageAgriCommodityPlantPart(0, this.isValid);
    //this.loadAllCommodityPlantPart();
    this.commodityPlantPartStatus = globalConstants;
  }

  constructor(
    public bulkDatas: BulkDataService,
    public agriCommodityPlantPartService: AgriCommodityPlantPartService,
    private userRightsService: UserRightsService,
    private drkServiceService: DrkServiceService
  ) { }

  // CommodityPlantPart list
  loadAllCommodityPlantPart() {
    return this.agriCommodityPlantPartService.GetAllCommodityPlantPart().subscribe((data: {}) => {
      this.CommodityPlantPartList = data;
    })
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : " + page);
    this.selectedPage = page;
    this.getPageAgriCommodityPlantPart(page, this.isValid);
  }

  getPageAgriCommodityPlantPart(page: number, isValid: number): void {
    this.agriCommodityPlantPartService.getPageAgriCommodityPlantPart(page, this.recordsPerPage, this.searchText, isValid,this.missing)
      .subscribe(page => this.pageCommodityPlantPart = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriCommodityPlantPartService.getPageAgriCommodityPlantPart(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing)
      .subscribe(page => this.pageCommodityPlantPart = page);
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


  // // Delete CommodityPlantPart
  // deleteCommodityPlantPart(data){
  //   var index = index = this.CommodityPlantPartList.map(x => {return x.name}).indexOf(data.name);
  //    return this.agriCommodityPlantPartService.DeleteCommodityPlantPart(data.id).subscribe(res => {
  //     this.CommodityPlantPartList.splice(index, 1)
  //      console.log('CommodityPlantPart deleted!')
  //    })
  // }

  // Delete Village
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.plantPart, data);
  }
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.plantPart, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.plantPart, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.plantPart, data);
  }

  modalConfirmation(event) {
    console.log(event);
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        this.approveCommodityPlantPart(event);
      } else if (event.flag == "finalize") {
        this.finalizeCommodityPlantPart(event);
      } else if (event.flag == "delete") {
        this.deleteCommodityPlantPart(event);
      } else if (event.flag == "reject") {
        this.rejectCommodityPlantPart(event);
      }

    }
  }

  approveCommodityPlantPart(event: any) {
    return this.agriCommodityPlantPartService.ApproveCommodityPlantPart(event.id).subscribe(res => {
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

  finalizeCommodityPlantPart(event: any) {
    return this.agriCommodityPlantPartService.FinalizeCommodityPlantPart(event.id).subscribe(res => {
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

  deleteCommodityPlantPart(event) {
    return this.agriCommodityPlantPartService.DeleteCommodityPlantPart(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.CommodityPlantPartList.splice(event.index, 1);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
    });
  }

  rejectCommodityPlantPart(event: any) {
    return this.agriCommodityPlantPartService.RejectCommodityPlantPart(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.CommodityPlantPartList.splice(event.index, 1);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
    });
  }


  //------------------------------------------------------------

  searchCommodityPlantPart() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageAgriCommodityPlantPart(this.selectedPage, this.isValid);
  }

  //------------------------------------------------------------

  imagePreview(title, src) {
    this.imagePreviewModal.showModal(title, src);
  }

  sortData(sort: Sort) {
    const data = this.pageCommodityPlantPart.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageCommodityPlantPart.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageCommodityPlantPart.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'commodity':
          return compare(firstValue.commodity, secondValue.commodity, isAsc);
        case 'phenophase':
          return compare(firstValue.phenophase, secondValue.phenophase, isAsc);
        case 'plantPart':
          return compare(firstValue.plantPart, secondValue.plantPart, isAsc);
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
    this.getPageAgriCommodityPlantPart(this.selectedPage - 1,this.isValid);
    this.commodityPlantPartStatus = globalConstants;
    }else{
      console.log("Inside else");
    this.ngOnInit();
    }
  }

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('agri_commodity_plant_part').subscribe(res => {
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
    this.getPageAgriCommodityPlantPart(0, this.isValid);
  }

  moveToMaster(id){
    this.agriCommodityPlantPartService.moveToMaster(id).subscribe(res => {
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

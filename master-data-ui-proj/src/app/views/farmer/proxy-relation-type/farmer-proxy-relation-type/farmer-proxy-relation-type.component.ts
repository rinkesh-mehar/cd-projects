import { Component, OnInit, ViewChild } from '@angular/core';
import { FarmerProxyRelationTypeService } from '../../services/farmer-proxy-relation-type.service';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../../agri/services/bulk-data.service';
import { PageFarmerProxyRelationType } from '../../models/PageFarmerProxyRelationType';


@Component({
  selector: 'app-farmer-proxy-relation-type',
  templateUrl: './farmer-proxy-relation-type.component.html',
  styleUrls: ['./farmer-proxy-relation-type.component.scss']
})
export class FarmerProxyRelationTypeComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
   isSuccess: boolean = false;
   _statusMsg: string;
  proxyRelationTypeStatus;
  ProxyRelationTypeList: any = [];
  searchText: any = '';
   selectedPage: number = 1;
   maxSize =10;
   recordsPerPage: number = 10;
   records: any = [];

   pageFarmerProxyRelationType: PageFarmerProxyRelationType;


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
	   this.getProxyRelationTypePagenatedList(0);
    // this.loadAllProxyRelationType();
    this.proxyRelationTypeStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,private userRightsService: UserRightsService,
    public farmerProxyRelationTypeService: FarmerProxyRelationTypeService
  ) { }

  getProxyRelationTypePagenatedList(page: number): void {
    this.farmerProxyRelationTypeService.getProxyRelationTypePagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageFarmerProxyRelationType = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.farmerProxyRelationTypeService.getProxyRelationTypePagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageFarmerProxyRelationType = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getProxyRelationTypePagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getProxyRelationTypePagenatedList(this.selectedPage - 1);
}

  // ProxyRelationType list
  loadAllProxyRelationType() {
    return this.farmerProxyRelationTypeService.GetAllProxyRelationType().subscribe((data: {}) => {
      this.ProxyRelationTypeList = data;
    })
  }



  // Delete ProxyRelationType
  // deleteProxyRelationType(data) {
  //   var index = index = this.ProxyRelationTypeList.map(x => { return x.name }).indexOf(data.name);
  //   return this.farmerProxyRelationTypeService.DeleteProxyRelationType(data.id).subscribe(res => {
  //     this.ProxyRelationTypeList.splice(index, 1)
  //     console.log('ProxyRelationType deleted!')
  //   })
  // }


  // Delete ProxyRelationType
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.proxyRelationType, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.proxyRelationType, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.proxyRelationType, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.proxyRelationType, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        observable = this.farmerProxyRelationTypeService.ApproveProxyRelationType(event.id)
      } else if (event.flag == "finalize") {
        observable = this.farmerProxyRelationTypeService.FinalizeProxyRelationType(event.id)
      } else if (event.flag == "delete") {
        observable = this.farmerProxyRelationTypeService.DeleteProxyRelationType(event.id)
      } else if (event.flag == "reject") {
        observable = this.farmerProxyRelationTypeService.RejectProxyRelationType(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
            this.loadAllProxyRelationType();
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
    // this.ngOnInit();
    // this.selectedPage = 1;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getProxyRelationTypePagenatedList(this.selectedPage - 1);
    this.proxyRelationTypeStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }

  sortData(sort: Sort) {
    const data = this.pageFarmerProxyRelationType.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageFarmerProxyRelationType.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageFarmerProxyRelationType.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case 'proxyRelationType':
            return compare(firstValue.proxyRelationType, secondValue.proxyRelationType, isAsc);
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

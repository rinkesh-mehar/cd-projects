import { Component, OnInit, ViewChild } from '@angular/core';
import { FarmerIdProofService } from '../services/farmer-id-proof.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../agri/services/bulk-data.service';
import { PageFarmerIdProof } from '../models/PageFarmerIdProof';

@Component({
  selector: 'app-farmer-id-proof',
  templateUrl: './farmer-id-proof.component.html',
  styleUrls: ['./farmer-id-proof.component.scss']
})
export class FarmerIdProofComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  idProofStatus;
  IdProofList: any = [];

  searchText: any = '';
   selectedPage: number = 1;
   maxSize =10;
   recordsPerPage: number = 10;
   records: any = [];

   pageFarmerIdProof: PageFarmerIdProof;


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
   this.getIdProofPagenatedList(0);
    // this.loadAllIdProof();
    this.idProofStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public farmerIdProofService: FarmerIdProofService,
    private userRightsService: UserRightsService,


  ) { }


  getIdProofPagenatedList(page: number): void {
    this.farmerIdProofService.getIdProofPagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageFarmerIdProof = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.farmerIdProofService.getIdProofPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageFarmerIdProof = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getIdProofPagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getIdProofPagenatedList(this.selectedPage - 1);
}

  // IdProof list
  loadAllIdProof() {
    return this.farmerIdProofService.GetAllIdProof().subscribe((data: {}) => {
      this.IdProofList = data;
    })
  }

  // Delete IdProof
  // deleteIdProof(data){
  //   var index = index = this.IdProofList.map(x => {return x.name}).indexOf(data.name);
  //    return this.farmerIdProofService.DeleteIdProof(data.id).subscribe(res => {
  //     this.IdProofList.splice(index, 1)
  //      console.log('IdProof deleted!')
  //    })
  // }


  // Delete IdProof
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
        observable = this.farmerIdProofService.ApproveIdProof(event.id)
      } else if (event.flag == "finalize") {
        observable = this.farmerIdProofService.FinalizeIdProof(event.id)
      } else if (event.flag == "delete") {
        observable = this.farmerIdProofService.DeleteIdProof(event.id)
      } else if (event.flag == "reject") {
        observable = this.farmerIdProofService.RejectIdProof(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
            this.loadAllIdProof();
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
    this.getIdProofPagenatedList(this.selectedPage - 1);
    this.idProofStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }

  sortData(sort: Sort) {
    const data = this.pageFarmerIdProof.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageFarmerIdProof.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageFarmerIdProof.content = data.sort((firstValue, secondValue) => {
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

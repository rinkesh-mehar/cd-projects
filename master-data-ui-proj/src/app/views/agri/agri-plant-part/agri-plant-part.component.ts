import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriPlantPartService } from '../services/agri-plant-part.service';
import { PageAgriPlantPart } from '../models/PageAgriPlantPart';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {Sort} from '@angular/material';
import { BulkDataService } from '../services/bulk-data.service';
@Component({
  selector: 'app-agri-plant-part',
  templateUrl: './agri-plant-part.component.html',
  styleUrls: ['./agri-plant-part.component.scss']
})
export class AgriPlantPartComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  plantPartStatus;
  PlantPartList: any = [];
  pagePlantPart: PageAgriPlantPart;
  selected : number = 1;
  selectedPage: number=1;
  maxSize = 10;
  searchText: any="";
  recordsPerPage: number = 10;
   records: any = [];


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageAgriPlantPart(0);
    this.plantPartStatus = globalConstants;
    //this.loadAllPlantPart();
  }

  constructor(public bulkDatas: BulkDataService,
    public agriPlantPartService: AgriPlantPartService,
    private userRightsService: UserRightsService,
  ){ }

   // PlantPart list
   loadAllPlantPart() {
    return this.agriPlantPartService.GetAllPlantPart().subscribe((data: {}) => {
      this.PlantPartList = data;
    })
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : "+page);
    this.selectedPage=page;
    this.getPageAgriPlantPart(page);
  }

  getPageAgriPlantPart(page:number): void {
    this.agriPlantPartService.getPageAgriPlantPart(page, this.recordsPerPage,this.searchText)
        .subscribe(page => this.pagePlantPart = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriPlantPartService.getPageAgriPlantPart(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pagePlantPart = page);
  }

    // // Delete PlantPart
    // deletePlantPart(data){
    //   var index = index = this.PlantPartList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriPlantPartService.DeletePlantPart(data.id).subscribe(res => {
    //     this.PlantPartList.splice(index, 1)
    //      console.log('PlantPart deleted!')
    //    })
    // }

         // Delete Commodity
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
      if (event.flag == 'approve') {
        observable = this.agriPlantPartService.ApprovePlantPart(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.agriPlantPartService.FinalizePlantPart(event.id);
      } else if (event.flag == 'delete') {
        observable = this.agriPlantPartService.DeletePlantPart(event.id);
      } else if (event.flag == 'reject') {
        observable = this.agriPlantPartService.RejectPlantPart(event.id);
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

  searchPlantPart() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageAgriPlantPart(this.selectedPage - 1);
  }

  sortData(sort: Sort) {
    const data = this.pagePlantPart.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pagePlantPart.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pagePlantPart.content = data.sort((firstValue, secondValue) => {
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


  modalSuccess($event: any) {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      console.log("Inside if");
    this.getPageAgriPlantPart(this.selectedPage - 1);
    this.plantPartStatus = globalConstants;
    }else{
      console.log("Inside else");
    this.ngOnInit();
    }
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

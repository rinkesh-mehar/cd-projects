import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriVarietyStressService } from '../services/agri-variety-stress.service';
import { PageAgriVarietyStress } from '../models/PageAgriVarietyStress';
import { InfoModalComponent } from '../../global/info-modal/info-modal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {Sort} from '@angular/material';
import { BulkDataService } from '../services/bulk-data.service';
import {DrkServiceService} from '../../services/drk-service.service';

@Component({
  selector: 'app-agri-variety-stress',
  templateUrl: './agri-variety-stress.component.html',
  styleUrls: ['./agri-variety-stress.component.scss']
})
export class AgriVarietyStressComponent implements OnInit {

  @ViewChild('infoModal') public infoModal: InfoModalComponent;
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  varietyStressStatus;
  VarietyStressList: any = [];
  pageVarietyStress: PageAgriVarietyStress;
  selectedPage: number = 1;
  maxSize = 10;
  searchText: any="";
  isValid: number = 1;
  missing : any="";
  recordsPerPage: number = 10;
  records: any = [];

  ngOnInit() {
    this.records = ['1', '2', '100', '200', '250'];
    this.getPageAgriVarietyStress(0, this.isValid);
    //this.loadAllVarietyStress();
    this.varietyStressStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
              public agriVarietyStressService: AgriVarietyStressService,
              private userRightsService: UserRightsService,
              private drkServiceService: DrkServiceService
  ) {
  }

  // VarietyStress list
  loadAllVarietyStress() {
    return this.agriVarietyStressService.GetAllVarietyStress().subscribe((data: {}) => {
      this.VarietyStressList = data;
    })
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : " + page);
    this.selectedPage = page;
    this.getPageAgriVarietyStress(page, this.isValid);
  }

  getPageAgriVarietyStress(page: number, isValid: number): void {
    this.agriVarietyStressService.getPageAgriVarietyStress(page,this.recordsPerPage,this.searchText, isValid,this.missing)
      .subscribe(page => this.pageVarietyStress = page)
  }

  // // Delete VarietyStress
  // deleteVarietyStress(data) {
  //   var index = index = this.VarietyStressList.map(x => { return x.name }).indexOf(data.name);
  //   return this.agriVarietyStressService.DeleteVarietyStress(data.id).subscribe(res => {
  //     this.VarietyStressList.splice(index, 1)
  //     console.log('VarietyStress deleted!')
  //   })
  // }

  // Delete Village
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.variety, data);
  }
  reject(data) {
    // data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.variety, data);
  }

  approve(data) {
    // data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.variety, data);
  }
  finalize(data) {
    // data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.variety, data);
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
  modalConfirmation(event) {
    console.log(event);
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        this.approveVarietyStress(event);
      } else if (event.flag == "finalize") {
        this.finalizeVarietyStress(event);
      } else if (event.flag == "delete") {
        this.deleteVarietyStress(event);
      } else if (event.flag == "reject") {
        this.rejectVarietyStress(event);
      }

    }
  }

  approveVarietyStress(event: any) {
    return this.agriVarietyStressService.ApproveVarietyStress(event.id).subscribe(res => {
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
  finalizeVarietyStress(event: any) {
    return this.agriVarietyStressService.FinalizeVarietyStress(event.id).subscribe(res => {
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

  deleteVarietyStress(event) {
    return this.agriVarietyStressService.DeleteVarietyStress(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.VarietyStressList.splice(event.index, 1);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
    });
  }
  rejectVarietyStress(event: any) {
    return this.agriVarietyStressService.RejectVarietyStress(event.id).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.VarietyStressList.splice(event.index, 1);
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      this.errorModal.showModal('ERROR', err.error, '');
    });
  }

  getResistantStressText(resistantStress) {
    if (resistantStress && resistantStress.length > 0) {
      return resistantStress.reduce((x, y) => x + y.name + ", ", "")
    } else {
      return " "
    }

  }
  getTolerantStress(tolerantStress) {
    if (tolerantStress && tolerantStress.length > 0) {
      return tolerantStress.reduce((x, y) => x + y.name + ", ", "")
    } else {
      return " "
    }
  }

  getSusceptibleStress(susceptibleStress) {
    if (susceptibleStress && susceptibleStress.length > 0) {
      return susceptibleStress.reduce((x, y) => x + y.name + ", ", "")
    } else {
      return " "
    }
  }

  showInfoModal(title, data) {
    this.infoModal.showModal(title, data, '')
  }

  searchVariety(){

      this.selectedPage = 1;
      console.log(this.searchText);
      this.getPageAgriVarietyStress(this.selectedPage - 1, this.isValid);
    }

  sortData(sort: Sort) {
    const data = this.pageVarietyStress.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageVarietyStress.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageVarietyStress.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'commodity':
          return compare(firstValue.commodity, secondValue.commodity, isAsc);
        case 'variety':
          return compare(firstValue.variety, secondValue.variety, isAsc);
        case 'stressType':
          return compare(firstValue.stressType, secondValue.stressType, isAsc);
        case 'resistantStress':
          return compare(firstValue.resistantStress, secondValue.resistantStress, isAsc);
        case 'susceptibleStress':
          return compare(firstValue.susceptibleStress, secondValue.susceptibleStress, isAsc);
        case 'tolerantStress':
          return compare(firstValue.tolerantStress, secondValue.tolerantStress, isAsc);
        case 'description':
          return compare(firstValue.description, secondValue.description, isAsc);
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
    if (this.selectedPage > 0) {

      this.onSelect((this.selectedPage - 1));
      (document.querySelector('thead th input') as HTMLInputElement).checked = false
    } else {
      this.onSelect(this.selectedPage);
      (document.querySelector('thead th input') as HTMLInputElement).checked = false
    }
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriVarietyStressService.getPageAgriVarietyStress(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing)
      .subscribe(page => this.pageVarietyStress = page);
  }


  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('agri_variety_stress').subscribe(res => {
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
    this.getPageAgriVarietyStress(0,this.isValid);
  }

  moveToMaster(id){
    this.agriVarietyStressService.moveToMaster(id).subscribe(res => {
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

import { PageFarmerGovtDepartment } from './../models/PageFarmerGovtDepartment';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FarmerGovtDepartmentService } from '../services/farmer-govt-department.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { Sort } from '@angular/material';
import { BulkDataService } from '../../agri/services/bulk-data.service';

@Component({
  selector: 'app-farmer-govt-department',
  templateUrl: './farmer-govt-department.component.html',
  styleUrls: ['./farmer-govt-department.component.scss']
})
export class FarmerGovtDepartmentComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

 isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
    govtDepartmentStatus;
  govtDepartmentList: any = [];


  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];

  pageFarmerGovtDepartment: PageFarmerGovtDepartment;


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getFarmerGovtDepartmentPagenatedList(0);
    // this.loadAllGovtDepartment();
    this.govtDepartmentStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public farmerGovtDepartmentService: FarmerGovtDepartmentService,
    private userRightsService: UserRightsService,
  ) { }

  getFarmerGovtDepartmentPagenatedList(page: number): void {
    this.farmerGovtDepartmentService.getFarmerGovtDepartmentPagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageFarmerGovtDepartment = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.farmerGovtDepartmentService.getFarmerGovtDepartmentPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageFarmerGovtDepartment = page);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getFarmerGovtDepartmentPagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getFarmerGovtDepartmentPagenatedList(this.selectedPage - 1);
}

  // GovtDepartment list
  loadAllGovtDepartment() {
    return this.farmerGovtDepartmentService.GetAllGovtDepartment().subscribe((data: {}) => {
      this.govtDepartmentList = data;
    })
  }



  // Delete GovtDepartment
  // deleteGovtDepartment(data) {
  //   var index = index = this.govtDepartmentList.map(x => { return x.name }).indexOf(data.name);
  //   return this.farmerGovtDepartmentService.DeleteGovtDepartment(data.id).subscribe(res => {
  //     this.govtDepartmentList.splice(index, 1)
  //     console.log('GovtDepartment deleted!')
  //   })
  // }


  // Delete GovtDepartment
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.departmentName, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.departmentName, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.departmentName, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.departmentName, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        observable = this.farmerGovtDepartmentService.ApproveGovtDepartment(event.id)
      } else if (event.flag == "finalize") {
        observable = this.farmerGovtDepartmentService.FinalizeGovtDepartment(event.id)
      } else if (event.flag == "delete") {
        observable = this.farmerGovtDepartmentService.DeleteGovtDepartment(event.id)
      } else if (event.flag == "reject") {
        observable = this.farmerGovtDepartmentService.RejectGovtDepartment(event.id)
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
            this.loadAllGovtDepartment();
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
    this.getFarmerGovtDepartmentPagenatedList(this.selectedPage - 1);
    this.govtDepartmentStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }

    sortData(sort: Sort) {
      const data = this.pageFarmerGovtDepartment.content.slice();
      if (!sort.active || sort.direction == '') {
        this.pageFarmerGovtDepartment.content = data;
        return;
      }
    
      function compare(firstValue, secondValue, isAsc: boolean) {
        return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
      }
    
      this.pageFarmerGovtDepartment.content = data.sort((firstValue, secondValue) => {
        const isAsc = sort.direction == 'asc';
        switch (sort.active) {
          case globalConstants.ID:
            return compare(+firstValue.id, +secondValue.id, isAsc);
            case 'departmentName':
              return compare(firstValue.departmentName, secondValue.departmentName, isAsc);
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

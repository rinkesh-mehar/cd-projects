import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { PageRegionTaskTypeSpecification } from '../models/PageRegionTaskTypeSpecification';
import { globalConstants } from '../../global/globalConstants';
import { RegionTaskTypeSpecificationService } from "../services/region-task-type-specification.service";
import { UserRightsService } from '../../services/user-rights.service';
import { Sort } from '@angular/material';
import {DrkServiceService} from '../../services/drk-service.service';
import { BulkDataService } from '../../agri/services/bulk-data.service';


@Component({
  selector: 'app-region-task-type-specification',
  templateUrl: './region-task-type-specification.component.html',
  styleUrls: ['./region-task-type-specification.component.scss']
})
export class RegionTaskTypeSpecificationComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  taskTypeSpecStatus;
  records: any = [];
  recordsPerPage: number = 10;
  SeasonList: any = [];
  pageRegionTaskTypeSpecification: PageRegionTaskTypeSpecification;
  selectedPage: number = 1;
  maxSize = 10;
  selectedItems: any = null;
  isValid: number = 1;
  missing : any="";
  searchText:any = "";

  constructor(public bulkDatas: BulkDataService,private regionTaskTypeSpecificationService: RegionTaskTypeSpecificationService,
              private userRightsService: UserRightsService, private drkServiceService: DrkServiceService
  ) { }

  ngOnInit(): void {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageRegionTaskTypeSpecification(0, this.isValid);
    this.taskTypeSpecStatus = globalConstants;
  }

  getPageRegionTaskTypeSpecification(page: number, isValid: number): void {
    this.regionTaskTypeSpecificationService.getPageRegionTaskTypeSpecification(page, this.searchText,this.recordsPerPage, isValid,this.missing)
      .subscribe(page => this.pageRegionTaskTypeSpecification = page)
  }

  onSelect(page: number): void {
    console.log("selected page : " + page);
    this.selectedPage = page;
    this.getPageRegionTaskTypeSpecification(page, this.isValid);
  }

  searchTaskTypeSpecification() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageRegionTaskTypeSpecification(this.selectedPage - 1, this.isValid);
  }

  sortData(sort: Sort) {
    const data = this.pageRegionTaskTypeSpecification.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageRegionTaskTypeSpecification.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageRegionTaskTypeSpecification.content = data.sort((firstValue, secondValue) => {
      let isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'state':
          return compare(firstValue.state , secondValue.state, isAsc);
        case 'aczName':
          return compare(firstValue.aczName , secondValue.aczName, isAsc);
        case 'zonalCommodity':
          return compare(firstValue.zonalCommodity , +secondValue.zonalCommodity, isAsc);
        case 'zonalVariety':
          return compare(firstValue.zonalVariety , +secondValue.zonalVariety, isAsc);
        case 'phenophaseName':
          return compare(firstValue.phenophaseName , secondValue.phenophaseName, isAsc);
        case 'taskTypeName':
          return compare(firstValue.taskTypeName , secondValue.taskTypeName, isAsc);
        case 'pushBackLimit':
          return compare(+firstValue.pushBackLimit , +secondValue.pushBackLimit, isAsc);
        case 'priority':
          return compare(+firstValue.priority , +secondValue.priority, isAsc);
        case 'taskTime':
          return compare(+firstValue.taskTime , secondValue.taskTime, isAsc);
        case 'spotDependency':
          return compare(firstValue.spotDependency , secondValue.spotDependency, isAsc);
        case globalConstants.STATUS:
          return compare(firstValue.status, secondValue.status, isAsc);
        default:
          return 0;
      }
    });
  }

  // Delete SeasonCommodity
  delete(data) {
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg, data);
  }
  // Reject 
  reject(data) {
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg, data);
  }

  approve(data) {
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg, data);
  }
  finalize(data) {
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'approve') {
        observable = this.regionTaskTypeSpecificationService.ApproveTaskTypeSpecification(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.regionTaskTypeSpecificationService.FinalizeTaskTypeSpecification(event.id);
      } else if (event.flag == 'delete') {
        observable = this.regionTaskTypeSpecificationService.DeleteTaskTypeSpecification(event.id);
      } else if (event.flag == 'reject') {
        observable = this.regionTaskTypeSpecificationService.RejectTaskTypeSpecification(event.id);
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
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
    this.ngOnInit();
  }

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('task_type_specifications').subscribe(res => {
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
    this.getPageRegionTaskTypeSpecification(0, this.isValid);
  }

  moveToMaster(id){
    this.regionTaskTypeSpecificationService.moveToMaster(id).subscribe(res => {
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
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.regionTaskTypeSpecificationService.getPageRegionTaskTypeSpecification(this.selectedPage - 1, this.searchText, this.recordsPerPage, this.isValid,this.missing)
      .subscribe(page => this.pageRegionTaskTypeSpecification = page);
  }

}

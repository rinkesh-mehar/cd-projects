import { ViewChild } from '@angular/core';
import { globalConstants } from './../../global/globalConstants';
import {Component, OnInit} from '@angular/core';
import {STEPPER_GLOBAL_OPTIONS} from '@angular/cdk/stepper';
import {LhPage} from '../model/lhPage';
import {UserRightsService} from '../../services/user-rights.service';
import {LogistichubService} from '../service/logistichub.service';
import { Sort } from '@angular/material';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { ApiUtilService } from '../../services/api-util.service';
import { LhApiUtilService } from '../service/lh-api-util.service';

@Component({
    selector: 'app-rejected-logistic-registration',
    templateUrl: './rejectedLogisticHub.component.html',
    styleUrls: ['./rejectedLogisticHub.component.css'],
})
export class RejectedLogisticHubComponent implements OnInit {

  // @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
    searchText: any = '';
    pageRejectedLogisticHub: LhPage;
    selectedPage: number;
    maxSize: number = 10;
    
    isSubmittedBulk: boolean = false;
    isSuccessBulk: boolean = false;
    fileUpload: any;

    recordsPerPage: number = 10;
   records: any = [];

    constructor(public fb: FormBuilder,private userRightsService: UserRightsService, private loaderService: LogistichubService,
      public lhApiUtilService: LhApiUtilService) 
    {

    }

    ngOnInit(): void {
      this.records = ['20', '50', '100', '200', '250'];
        this.getRejectedLogisticHubPage(0);
    }


    submitExcelForm() {
      this.loaderService.whBulkInsertByExcelUpload(this.fileUpload).subscribe(res => {
        this.isSubmittedBulk = true;
  
        if (res) {
          this.isSuccessBulk = res.success;
          if (res.success) {
            this.successModal.showModal('SUCCESS', res.message, '');
          } else {
            this.errorModal.showModal('ERROR', res.error, '');
          }
        }
      });
    }

    downloadExcelFormat() {
      var fileName = 'WH_Bulk_Insert';
      this.lhApiUtilService.downloadExcelFormat(fileName);
    }//downloadExcelFormat


    uploadExcel(element) {
      var file: File = null;
      file = element.target.files[0];
      this.fileUpload = file;
    }

    onSelect(page: number): void {
        console.log('selected page : ' + page);
        this.selectedPage = page;
        this.getRejectedLogisticHubPage(page);
    }
    searchLh() {
        this.selectedPage = 1;
        console.log(this.searchText);
        this.getRejectedLogisticHubPage(this.selectedPage - 1);
    }
    getRejectedLogisticHubPage(page: number): void {
        this.loaderService.getRejectedPageLh(page, this.recordsPerPage, this.searchText)
            .subscribe(
                data => {
                    this.pageRejectedLogisticHub = data;


                }
            );
    }

    loadData(event: any) {
      console.log('pages ', event.target.value);
      this.recordsPerPage = event.target.value || 10;
      this.loaderService.getRejectedPageLh(this.selectedPage - 1, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageRejectedLogisticHub = page);
    }
  

    sortData(sort: Sort) {
        const data = this.pageRejectedLogisticHub.content.slice();
        if (!sort.active || sort.direction == '') {
          this.pageRejectedLogisticHub.content = data;
          return;
        }
      
        function compare(firstValue, secondValue, isAsc: boolean) {
          return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }
      
        this.pageRejectedLogisticHub.content = data.sort((firstValue, secondValue) => {
          const isAsc = sort.direction == 'asc';
          switch (sort.active) {
            case globalConstants.ID:
              return compare(+firstValue.id, +secondValue.id, isAsc);
              case 'logisticHubName':
                return compare(firstValue.logisticHubName, secondValue.logisticHubName, isAsc);
            case 'regionName':
              return compare(firstValue.regionName, secondValue.regionName, isAsc);
            case 'primaryMobileNumber':
              return compare(firstValue.primaryMobileNumber, secondValue.primaryMobileNumber, isAsc);
            case 'stageName':
              return compare(firstValue.stageName, secondValue.stageName, isAsc);
              case globalConstants.STATUS:
              return compare(firstValue.status, secondValue.status, isAsc);
              case 'reason':
              return compare(firstValue.reason, secondValue.reason, isAsc);
            default:
              return 0;
          }
        });
      }
      
      modalSuccess($event: any) {
        // this.ngOnInit();
        // this.selectedPage = 1;
      
      
        if(this.selectedPage >= 2){
        this.getRejectedLogisticHubPage(this.selectedPage - 1);
        }else{
        this.ngOnInit();
        }
      document.getElementById('whBulkInsertModal').click();
      }
      
      onClose(){
        // console.log("inside close...");
        if(this.selectedPage >= 2){
          this.getRejectedLogisticHubPage(this.selectedPage - 1);
          }else{
          this.ngOnInit();
          }
      }
      

}
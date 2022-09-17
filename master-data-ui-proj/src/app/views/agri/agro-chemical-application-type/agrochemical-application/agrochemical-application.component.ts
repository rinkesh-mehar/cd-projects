import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriAgroChemicalApplicationTypeService } from '../../services/agri-agro-chemical-application-type.service';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { BulkDataService } from '../../services/bulk-data.service';
import { PageAgriAgrochemicalType } from '../../models/PageAgriAgrochemicalType';

@Component({
  selector: 'app-agrochemical-application',
  templateUrl: './agrochemical-application.component.html',
  styleUrls: ['./agrochemical-application.component.scss']
})
export class AgrochemicalApplicationComponent implements OnInit {
    @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

 isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  chemicalAppStatus;

  AgrochemicalApplicationList: any = [];

  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];

  pageAgriAgrochemicalType: PageAgriAgrochemicalType;


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    // this.loadAllAgrochemicalApplication();
    this.getPageAgroChemicalApplictionType(0);
    this.chemicalAppStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,  private userRightsService: UserRightsService,
    public agriAgrochemicalApplicationService: AgriAgroChemicalApplicationTypeService
  ){ }

  getPageAgroChemicalApplictionType(page: number) {
    return this.agriAgrochemicalApplicationService.getPageAgroChemicalApplictionType(page, this.recordsPerPage,this.searchText).subscribe((data) => {
      this.pageAgriAgrochemicalType = data;
    })
  }

   // AgrochemicalApplication list
   loadAllAgrochemicalApplication() {
    return this.agriAgrochemicalApplicationService.GetAllAgroChemicalApplictionType().subscribe((data: {}) => {
      this.AgrochemicalApplicationList = data;
    })
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPageAgroChemicalApplictionType(page);
}

searchApplicationType() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getPageAgroChemicalApplictionType(this.selectedPage - 1);
}


    // Delete AgrochemicalApplication
    // deleteAgrochemicalApplication(data){
    //   var index = index = this.AgrochemicalApplicationList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriAgrochemicalApplicationService.DeleteAgroChemicalApplictionType(data.id).subscribe(res => {
    //     this.AgrochemicalApplicationList.splice(index, 1)
    //      console.log('AgrochemicalApplication deleted!')
    //    })
    // }


  // Delete AgroChemicalApplictionType
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
                observable = this.agriAgrochemicalApplicationService.ApproveAgroChemicalApplictionType(event.id);
            } else if (event.flag == 'finalize') {
                observable = this.agriAgrochemicalApplicationService.FinalizeAgroChemicalApplictionType(event.id);
            } else if (event.flag == 'delete') {
                observable = this.agriAgrochemicalApplicationService.DeleteAgroChemicalApplictionType(event.id);
            } else if (event.flag == 'reject') {
                observable = this.agriAgrochemicalApplicationService.RejectAgroChemicalApplictionType(event.id);
            }
            observable.subscribe(res => {
                if (res) {
                    this.isSuccess = res.success;
                    if (res.success) {
                        this._statusMsg = res.message;
                        this.loadAllAgrochemicalApplication();
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

    sortData(sort: Sort) {
        const data = this.pageAgriAgrochemicalType.content.slice();
        if (!sort.active || sort.direction == '') {
            this.pageAgriAgrochemicalType.content = data;
            return;
        }

        function compare(firstValue, secondValue, isAsc: boolean) {
            return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }

        this.pageAgriAgrochemicalType.content = data.sort((firstValue, secondValue) => {
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
  

      modalSuccess($event: any) {
        (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
        this.bulkDatas.disbled = true;
        // this.ngOnInit();
        // this.selectedPage = 1;
      
        console.log("page : " + this.selectedPage);
        if(this.selectedPage >= 2){
          console.log("Inside if");
        this.getPageAgroChemicalApplictionType(this.selectedPage - 1);
        this.chemicalAppStatus = globalConstants;
        }else{
          console.log("Inside else");
        this.ngOnInit();
        }
      }

      loadData(event: any) {
        console.log('pages ', event.target.value);
        this.recordsPerPage = event.target.value || 10;
        this.agriAgrochemicalApplicationService.getPageAgroChemicalApplictionType(this.selectedPage - 1, this.recordsPerPage, this.searchText)
          .subscribe(page => this.pageAgriAgrochemicalType = page);
      }
  
}

import { globalConstants } from './../../global/globalConstants';
import {Component, OnInit, ViewChild} from '@angular/core';
import {ConfirmationMadalComponent} from '../../global/confirmation-madal/confirmation-madal.component';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {CdtModulesService} from '../services/cdt-modules.service';
import {ModelPage} from '../model/modelPage';
import { Sort } from '@angular/material';
declare var $: any;

@Component({
    selector: 'app-manage',
    templateUrl: './manage.component.html',
    styleUrls: ['./manage.component.css']
})
export class ManageComponent implements OnInit {

    @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;
    @ViewChild('closebutton') closebutton;
    isSuccess: boolean = false;
    searchText: any = '';
    manageList: ModelPage;
    selectedPage: number = 1;
    imgUrl: string = '';
    recordsPerPage: number = 10;
    maxSize: number = 10;
    fileUrl: any;
   records: any = [];

    constructor(public cdtModulesService: CdtModulesService) {
    }


    ngOnInit(): void {

   this.records = ['20', '50', '100', '200', '250'];
        this.loadManageList(0);
    }

    loadManageList(page: number) {
        return this.cdtModulesService.getPageWiseManageList(page, this.recordsPerPage, this.searchText).subscribe(
            page => this.manageList = page);
    }

    loadData(event: any) {
      console.log('pages ', event.target.value);
      this.recordsPerPage = event.target.value || 10;
      this.cdtModulesService.getPageWiseManageList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
        .subscribe(page => this.manageList = page);
    }
  

    onSelect(page: number): void {
        console.log('selected page : ' + page);
        this.selectedPage = page;
        this.loadManageList(page);
    }
    delete(id) {
       console.log("model id--------->" + id);
        return this.cdtModulesService.deleteRecords(id).subscribe(res => {
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

    searchModels() {
        console.log(this.searchText);
        this.loadManageList(this.selectedPage - 1);
    }
    downloadConfirm(url){
        this.fileUrl = url;
}
    modalConfirmation($event: any) {
    }

    hide() {
        this.closebutton.nativeElement.click();
    }
    modalSuccess($event: any) {
       this.ngOnInit();
    }

    sortData(sort: Sort) {
        const data = this.manageList.content.slice();
        if (!sort.active || sort.direction == '') {
          this.manageList.content = data;
          return;
        }
      
        function compare(firstValue, secondValue, isAsc: boolean) {
          return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }
      
        this.manageList.content = data.sort((firstValue, secondValue) => {
          const isAsc = sort.direction == 'asc';
          switch (sort.active) {
            case globalConstants.ID:
              return compare(+firstValue.id, +secondValue.id, isAsc);
              case 'modelName':
                return compare(firstValue.modelName, secondValue.modelName, isAsc);
            case 'fileUrl':
              return compare(firstValue.fileUrl, secondValue.fileUrl, isAsc);
            case 'errMsg':
              return compare(firstValue.errMsg, secondValue.errMsg, isAsc);
              case globalConstants.STATUS:
              return compare(firstValue.status, secondValue.status, isAsc);
            default:
              return 0;
          }
        });
      }
}

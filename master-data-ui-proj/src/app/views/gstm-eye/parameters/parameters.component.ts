import {Component, OnInit, ViewChild} from '@angular/core';
import {GstmEyeService} from '../services/gstm-eye.service';
import {UserRightsService} from '../../services/user-rights.service';
import {PageParameter} from '../models/pageParameter';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {ConfirmationMadalComponent} from '../../global/confirmation-madal/confirmation-madal.component';
import { Sort } from '@angular/material';
import { globalConstants } from '../../global/globalConstants';

@Component({
    selector: 'app-parameters',
    templateUrl: './parameters.component.html',
    styleUrls: ['./parameters.component.scss']
})
export class ParametersComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;
    @ViewChild('confirmModal', {static: false}) public confirmModal: ConfirmationMadalComponent;

    MarketPriceList: any;
    searchText: any = '';
    selectedPage: number;
    pageParameter: PageParameter;
    maxSize: number = 10;
    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    statusMsg: string;


    constructor(private gstmEyeService: GstmEyeService, private userRightsService: UserRightsService) {
    }

    ngOnInit(): void {
        // this.getParameterList();
        this.getParameterListByPagination(0);

    }

    getParameterList() {
        return this.gstmEyeService.getParameterList().subscribe((data) => {
            this.MarketPriceList = data;
            console.log('market price list is ', this.MarketPriceList);
        });
    }

    deleteParameter(id) {
        this.confirmModal.showModal('Delete Market Price ?', 'Are you sure want to Delete Parameter ?', id);

    }

    getParameterListByPagination(page: number): void {
        this.gstmEyeService.getParameterListByPagination(page, this.searchText)
            .subscribe(page => this.pageParameter = page);
    }

    onSelect(page: number): void {
        console.log('selected page : ' + page);
        this.selectedPage = page;
        this.getParameterListByPagination(page);
    }

    searchParameter() {
        this.selectedPage = 1;
        console.log(this.searchText);
        this.getParameterListByPagination(this.selectedPage - 1);
    }

    modalConfirmation(id) {

        this.gstmEyeService.deleteParameter(id).subscribe((res) => {

            this.isSubmitted = true;
            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                    this.statusMsg = res.message;
                    window.scrollTo(0, 0);
                    this.successModal.showModal('SUCCESS', res.message, '');
                }
            }
        }, err => {
            this.isSubmitted = true;
            this.statusMsg = err.message;
            this.isSuccess = false;
        });
    }

    modalSuccess($event: any) {

    }

    sortData(sort: Sort) {
        const data = this.pageParameter.content.slice();
        if (!sort.active || sort.direction == '') {
          this.pageParameter.content = data;
          return;
        }
      
        function compare(firstValue, secondValue, isAsc: boolean) {
          return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }
      
        this.pageParameter.content = data.sort((firstValue, secondValue) => {
          const isAsc = sort.direction == 'asc';
          switch (sort.active) {
            case globalConstants.ID:
              return compare(+firstValue.id, +secondValue.id, isAsc);
              case 'parameter':
                return compare(firstValue.parameter, secondValue.parameter, isAsc);
            case 'platform':
              return compare(firstValue.platform, secondValue.platform, isAsc);
            case 'dataType':
              return compare(firstValue.dataType, secondValue.dataType, isAsc);
            case 'category':
              return compare(firstValue.category, secondValue.category, isAsc);
            case 'subCategory':
              return compare(firstValue.subCategory, secondValue.subCategory, isAsc);
              case globalConstants.STATUS:
              return compare(firstValue.status, secondValue.status, isAsc);
            default:
              return 0;
          }
        });
      }
}

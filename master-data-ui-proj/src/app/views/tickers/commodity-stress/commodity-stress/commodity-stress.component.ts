import { globalConstants } from './../../../global/globalConstants';
import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {BulkDataService} from '../../../agri/services/bulk-data.service';
import {TickerService} from '../../services/ticker.service';
import {PageCommodityStress} from '../../models/PageCommodityStress';
import {UserRightsService} from '../../../services/user-rights.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {ConfirmationMadalComponent} from '../../../global/confirmation-madal/confirmation-madal.component';
import { Sort } from '@angular/material';

@Component({
    selector: 'app-commodity-stress',
    templateUrl: './commodity-stress.component.html',
    styleUrls: ['./commodity-stress.component.scss']
})

export class CommodityStressComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;
    @ViewChild('confirmModal', {static: false}) public confirmModal: ConfirmationMadalComponent;

    CommodityStressList: any = [];
    pageCommodityStress: PageCommodityStress;
    searchText: any = '';
    selectedPage: number = 1;
    maxSize: number = 10;
    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    statusMsg: string;

   recordsPerPage: number = 10;
   records: any = [];

   pageCommodityStressTicker: any;

    constructor(private tickerService: TickerService, private userRightsService: UserRightsService) {

    }

    ngOnInit(): void {
      this.records = ['20', '50', '100', '200', '250'];
	   this.getCommodityStressPagenatedList(0);
        // this.getCommodityStressList();
    }


    getCommodityStressPagenatedList(page: number): void {
    this.tickerService.getCommodityStressPagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageCommodityStress = page);
  }
  
  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.tickerService.getCommodityStressPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageCommodityStress = page);
  }

  onSelect(page: number): void {
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getCommodityStressPagenatedList(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.getCommodityStressPagenatedList(this.selectedPage - 1);
}


    getCommodityStressList() {
        return this.tickerService.getCommodityStressList().subscribe((data: {}) => {
            this.CommodityStressList = data;
            console.log('CommodityStressList  is ', this.CommodityStressList);
        });
    }

    deleteCommodityStress(id) {
        this.confirmModal.showModal('Delete Commodity Stress ?', 'Are you sure want to Delete Commodity Stress ?', id);
    }

    modalConfirmation(id) {
        this.tickerService.deleteCommodityStress(id).subscribe(res => {
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
 
      console.log("page : " + this.selectedPage);
      if(this.selectedPage >= 2){
        // console.log("Inside if");
      this.getCommodityStressPagenatedList(this.selectedPage - 1);
      // this.commodityGroupStatus = globalConstants;
      }else{
        // console.log("Inside else");
      this.ngOnInit();
      }
    }

    sortData(sort: Sort) {
        const data = this.CommodityStressList.slice();
        if (!sort.active || sort.direction == '') {
          this.CommodityStressList = data;
          return;
        }
      
        function compare(firstValue, secondValue, isAsc: boolean) {
          return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }
      
        this.CommodityStressList = data.sort((firstValue, secondValue) => {
          const isAsc = sort.direction == 'asc';
          switch (sort.active) {
            case 'TickerCommodityID':
              return compare(+firstValue.TickerCommodityID, +secondValue.TickerCommodityID, isAsc);
              case 'Commodity':
                return compare(firstValue.Commodity, secondValue.Commodity, isAsc);
            case 'Phenophase':
              return compare(firstValue.Phenophase, secondValue.Phenophase, isAsc);
            case 'Stress':
              return compare(firstValue.Stress, secondValue.Stress, isAsc);
              case globalConstants.STATUS:
              return compare(firstValue.status, secondValue.status, isAsc);
            default:
              return 0;
          }
        });
      }
}

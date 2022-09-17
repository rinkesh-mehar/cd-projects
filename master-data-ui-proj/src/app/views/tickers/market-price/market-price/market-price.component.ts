import { globalConstants } from './../../../global/globalConstants';
import {Component, OnInit, ViewChild} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {TickerService} from '../../services/ticker.service';
import {UserRightsService} from '../../../services/user-rights.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {ConfirmationMadalComponent} from '../../../global/confirmation-madal/confirmation-madal.component';
import { Sort } from '@angular/material';

@Component({
    selector: 'app-market-price',
    templateUrl: './market-price.component.html',
    styleUrls: ['./market-price.component.scss']
})

export class MarketPriceComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;
    @ViewChild('confirmModal', {static: false}) public confirmModal: ConfirmationMadalComponent;

    marketPlaceFrom: FormGroup;
    MarketPriceList: any;
    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    statusMsg: string;

    searchText: any = '';
   selectedPage: number = 1;
   maxSize =10;
   recordsPerPage: number = 10;
   records: any = [];

   pageMarketPrice: any;

    ngOnInit(): void {
      this.records = ['20', '50', '100', '200', '250'];
      this.getMarketPricePagenatedList(0);
    
        // this.getMarketPriceList();

    }

    constructor(private tickerService: TickerService, private userRightsService: UserRightsService) {

    }

    getMarketPricePagenatedList(page: number): void {
      this.tickerService.getMarketPricePagenatedList(page, this.recordsPerPage, this.searchText)
          .subscribe(page => this.pageMarketPrice = page);
    }
    
    loadData(event: any) {
      console.log('pages ', event.target.value);
      this.recordsPerPage = event.target.value || 10;
      this.tickerService.getMarketPricePagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageMarketPrice = page);
    }
  
    onSelect(page: number): void {
      // console.log('selected page : ' + page);
      this.selectedPage = page;
      this.getMarketPricePagenatedList(page);
  }
  
  search() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getMarketPricePagenatedList(this.selectedPage - 1);
  }

    getMarketPriceList() {
        return this.tickerService.getMarketPriceList().subscribe((data: {}) => {
            this.MarketPriceList = data;
            console.log('market price list is ', this.MarketPriceList);
        });
    }

    deleteMarketPrice(id) {
        this.confirmModal.showModal('Delete Market Price ?', 'Are you sure want to Delete Market Price ?', id);
    }

    modalConfirmation(id) {
        this.tickerService.deleteMarketPrice(id).subscribe(res => {
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
        const data = this.pageMarketPrice.content.slice();
        if (!sort.active || sort.direction == '') {
          this.pageMarketPrice.content = data;
          return;
        }
      
        function compare(firstValue, secondValue, isAsc: boolean) {
          return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }
      
        this.pageMarketPrice.content = data.sort((firstValue, secondValue) => {
          const isAsc = sort.direction == 'asc';
          switch (sort.active) {
            case 'ID':
              return compare(+firstValue.ID, +secondValue.ID, isAsc);
              case 'Market':
                return compare(firstValue.Market, secondValue.Market, isAsc);
            case 'Commodity':
              return compare(firstValue.Commodity, secondValue.Commodity, isAsc);
            case 'Variety':
              return compare(firstValue.Variety, secondValue.Variety, isAsc);
            case 'MinPrice':
              return compare(firstValue.MinPrice, secondValue.MinPrice, isAsc);
            case 'MaxPrice':
              return compare(firstValue.MaxPrice, secondValue.MaxPrice, isAsc);
              case 'ModalPrice':
                return compare(firstValue.ModalPrice, secondValue.ModalPrice, isAsc);
              case globalConstants.STATUS:
              return compare(firstValue.status, secondValue.status, isAsc);
            default:
              return 0;
          }
        });
      }
      
}

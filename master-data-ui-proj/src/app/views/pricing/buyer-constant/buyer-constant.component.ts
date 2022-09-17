import { Component, OnInit } from '@angular/core';
import {PageBuyerConstant} from '../models/PageBuyerConstant';
import {PricingMspGroupService} from '../services/pricing-msp-group.service';
import {UserRightsService} from '../../services/user-rights.service';
import {Sort} from '@angular/material';
import {globalConstants} from '../../global/globalConstants';

@Component({
  selector: 'app-buyer-constant',
  templateUrl: './buyer-constant.component.html',
  styleUrls: ['./buyer-constant.component.scss']
})
export class BuyerConstantComponent implements OnInit {

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  statusMsg: string;
  selectedPage: number;
  searchText: any = '';
  maxSize = 10;
  selectedItems: any = null;
  terrainStatus;

  terrainList: any = [];
  pageBuyerConstant: PageBuyerConstant;

  constructor(private pricingMspGroupService: PricingMspGroupService, private userRightsService: UserRightsService) { }

  ngOnInit(): void {
    this.getPageBuyerConstant(0);
  }

  getPageBuyerConstant(page: number): void {
    this.pricingMspGroupService.getPageBuyerConstantPaginated(page, this.searchText)
        .subscribe(page => this.pageBuyerConstant = page);
  }

  searchBuyerConstatn() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageBuyerConstant(this.selectedPage - 1);
  }

  onSelect(page: number): void {
    console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPageBuyerConstant(page);
  }

  sortData(sort: Sort) {
    const data = this.pageBuyerConstant.content.slice();
    if (!sort.active || sort.direction === '') {
      this.pageBuyerConstant.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageBuyerConstant.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.ID, +secondValue.ID, isAsc);
        case 'slopeMin':
          return compare(firstValue.slopeMin, secondValue.slopeMin, isAsc);
        case 'slopeMax':
          return compare(firstValue.slopeMax, secondValue.slopeMax, isAsc);
        case 'buyerConstant':
          return compare(+firstValue.buyerConstant, +secondValue.buyerConstant, isAsc);
        case globalConstants.STATUS:
          return compare(firstValue.status, secondValue.status, isAsc);
        default:
          return 0;
      }
    });
  }
}

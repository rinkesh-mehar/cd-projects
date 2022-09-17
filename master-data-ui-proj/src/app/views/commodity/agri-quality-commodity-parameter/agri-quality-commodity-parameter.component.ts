import { AgriQualityCommodityParameterService } from './../service/agri-quality-commodity-parameter.service';
import { Component, OnInit } from '@angular/core';
import { Sort } from '@angular/material';
import { globalConstants } from '../../global/globalConstants';
import { UserRightsService } from '../../services/user-rights.service';
import { PageAgriQualityCommodityParameter } from '../models/PageAgriQualityCommodityParameter';

@Component({
  selector: 'app-agri-quality-commodity-parameter',
  templateUrl: './agri-quality-commodity-parameter.component.html',
  styleUrls: ['./agri-quality-commodity-parameter.component.scss']
})
export class AgriQualityCommodityParameterComponent implements OnInit {

  pageAgriQualityCommodityParameter: PageAgriQualityCommodityParameter;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  commodityGroupStatus;

  recordsPerPage: number = 10;
  records: any = [];
  

  constructor(private agriQualityCommodityParameterService: AgriQualityCommodityParameterService, private userRightsService: UserRightsService) { }

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageQualityCommodityParameterList(0);
    this.commodityGroupStatus = globalConstants;
  }

  getPageQualityCommodityParameterList(page: number): void {
    this.agriQualityCommodityParameterService.getPageQualityCommodityParameterList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageAgriQualityCommodityParameter = page);
}

loadData(event: any) {
  console.log('pages ', event.target.value);
  this.recordsPerPage = event.target.value || 10;
  this.agriQualityCommodityParameterService.getPageQualityCommodityParameterList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    .subscribe(page => this.pageAgriQualityCommodityParameter = page);
}

onSelect(page: number): void {
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPageQualityCommodityParameterList(page);
}

searchQualityCommodityParameter() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageQualityCommodityParameterList(this.selectedPage - 1);
}

sortData(sort: Sort) {
  const data = this.pageAgriQualityCommodityParameter.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageAgriQualityCommodityParameter.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageAgriQualityCommodityParameter.content = data.sort((firstValue, secondValue) => {
    const isAsc = sort.direction == 'asc';
    switch (sort.active) {
      case 'commodityId':
        return compare(+firstValue.commodityId, +secondValue.commodityId, isAsc);
      case 'commodity':
        return compare(firstValue.commodity, secondValue.commodity, isAsc);
        case 'qualityParameter':
        return compare(firstValue.qualityParameter, secondValue.qualityParameter, isAsc);
      default:
        return 0;
    }
  });
}

}

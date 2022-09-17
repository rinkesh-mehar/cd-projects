import { Component, OnInit } from '@angular/core';
import { Sort } from '@angular/material';
import { DocApprovedListService } from '../service/doc-approved-list.service'

@Component({
  selector: 'app-doc-approved-list',
  templateUrl: './doc-approved-list.component.html',
  styleUrls: ['./doc-approved-list.component.scss']
})
export class DocApprovedListComponent implements OnInit {

  logisticHubList : any =[]
  searchText: any = '';
  selectedPage: number = 1;
  maxSize : number =10;


recordsPerPage: number = 10;
records: any = [];

  constructor(private docApprovedListService : DocApprovedListService ) { }

  ngOnInit(): void {
    this.records = ['20', '50', '100', '200', '250'];
    this.loadAllDocApprovedLogisticHub(0);
  }

  loadAllDocApprovedLogisticHub(page:number): void{
    this.docApprovedListService.getAllDocApprovedLogisticHub(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.logisticHubList = page);
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.docApprovedListService.getAllDocApprovedLogisticHub(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.logisticHubList = page);
  }

  onSelect(page: number): void {
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.loadAllDocApprovedLogisticHub(page);
}

search() {
  this.selectedPage = 1;
  console.log(this.searchText);
  this.loadAllDocApprovedLogisticHub(this.selectedPage - 1);
}

sortData(sort: Sort) {
  const data = this.logisticHubList.content.slice();
  if (!sort.active || sort.direction == '') {
    this.logisticHubList.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.logisticHubList.content = data.sort((firstValue, secondValue) => {
    const isAsc = sort.direction == 'asc';
    switch (sort.active) {
        case 'RegionName':
          return compare(firstValue.RegionName, secondValue.RegionName, isAsc);
      case 'LogisticHubName':
        return compare(firstValue.LogisticHubName, secondValue.LogisticHubName, isAsc);
      case 'PrimaryMobile':
        return compare(firstValue.PrimaryMobile, secondValue.PrimaryMobile, isAsc);
      default:
        return 0;
    }
  });
}

}
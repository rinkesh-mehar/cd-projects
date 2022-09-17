import { Component, OnInit } from '@angular/core';
import { Sort } from '@angular/material';
import { LogistichubService } from '../service/logistichub.service';

@Component({
  selector: 'app-logistic-hub-pv-approved-list',
  templateUrl: './logistic-hub-pv-approved-list.component.html',
  styleUrls: ['./logistic-hub-pv-approved-list.component.scss']
})

export class LogisticHubPvApprovedListComponent implements OnInit {

    searchText: any = '';
    selectedPage: number;
    maxSize: number = 10;
    lhPVApprovedList: any = [];

    recordsPerPage: number = 10;
   records: any = [];

  constructor(public service: LogistichubService) { }

  ngOnInit() {

   this.records = ['20', '50', '100', '200', '250'];
    this.loadPVApprovedList(0);
  }

  loadPVApprovedList(page: number) {
     this.service.getPVApprovedList(page, this.recordsPerPage, this.searchText).subscribe(data => {
      this.lhPVApprovedList = data;
    })
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.service.getPVApprovedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.lhPVApprovedList = page);
  }

  onSelect(page: number): void {
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.loadPVApprovedList(page);
}

search() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.loadPVApprovedList(this.selectedPage - 1);
}

sortData(sort: Sort) {
  const data = this.lhPVApprovedList.content.slice();
  if (!sort.active || sort.direction == '') {
    this.lhPVApprovedList.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.lhPVApprovedList.content = data.sort((firstValue, secondValue) => {
    const isAsc = sort.direction == 'asc';
    switch (sort.active) {
        case 'RegionName':
          return compare(firstValue.RegionName, secondValue.RegionName, isAsc);
      case 'LhName':
        return compare(firstValue.LhName, secondValue.LhName, isAsc);
      case 'CapacityMT':
        return compare(firstValue.CapacityMT, secondValue.CapacityMT, isAsc);
      case 'PrimaryMobile':
        return compare(firstValue.PrimaryMobile, secondValue.PrimaryMobile, isAsc);
      case 'Stage':
        return compare(firstValue.Stage, secondValue.Stage, isAsc);
      default:
        return 0;
    }
  });
}

}

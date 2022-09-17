import { RightsDataService } from './../services/rights-data.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Sort } from '@angular/material';
import { throwError } from 'rxjs';
import { BulkDataService } from '../../agri/services/bulk-data.service';
import { globalConstants } from '../../global/globalConstants';
import { UserRightsService } from '../../services/user-rights.service';
import { PageRightsData } from '../models/PageRightsData';
@Component({
  selector: 'app-manual-intervention',
  templateUrl: './rights-data.component.html',
  styleUrls: ['./rights-data.component.scss']
})
export class RightsDataComponent implements OnInit {

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  pageRightsData: PageRightsData;
  selectedPage: number = 1;
  maxSize = 10;
  searchText: any="";
  missing : any="";

  recordsPerPage: number = 10;
   records: any = [];


  ngOnInit(): void {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPaginatedRighDataList(0);
  }

  constructor(public bulkDatas: BulkDataService,
    public rightsDataService: RightsDataService,
    private userRightsService: UserRightsService,
) { }


getPaginatedRighDataList(page: number): void {
  this.rightsDataService.getPaginatedRighDataList(page, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageRightsData = page);
}

loadData(event: any) {
  console.log('pages ', event.target.value);
  this.recordsPerPage = event.target.value || 10;
  this.rightsDataService.getPaginatedRighDataList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    .subscribe(page => this.pageRightsData = page);
}

onSelect(page: number): void {
  this.bulkDatas.disbled = true;
  // console.log('selected page : ' + page);
  this.selectedPage = page;
  this.getPaginatedRighDataList(page);
}

search() {
this.selectedPage = 1;
console.log(this.searchText);
this.getPaginatedRighDataList(this.selectedPage - 1);
}

sortData(sort: Sort) {
  const data = this.pageRightsData.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageRightsData.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageRightsData.content = data.sort((firstValue, secondValue) => {
    let isAsc = sort.direction == 'asc';
    switch (sort.active) {
      case globalConstants.ID:
        return compare(+firstValue.id, +secondValue.id, isAsc);
      case 'stateName':
        return compare(firstValue.stateName, secondValue.stateName, isAsc);
      case 'regionName':
        return compare(firstValue.regionName, secondValue.regionName, isAsc);
      case 'acz':
        return compare(firstValue.acz, secondValue.acz, isAsc);
      case 'commodity':
        return compare(firstValue.commodity, secondValue.commodity, isAsc);
      case 'variety':
        return compare(firstValue.variety, secondValue.variety, isAsc);
      case 'sowingDate':
        return compare(+firstValue.sowingDate, +secondValue.sowingDate, isAsc);
      default:
        return 0;
    }
  });
}

// errorHandl(error) {
//   let errorMessage = '';
//   if (error.error instanceof ErrorEvent) {
//       // Get client-side error
//       errorMessage = error.error.message;
//   } else {
//       // Get server-side error
//       errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
//   }
//   console.log(errorMessage);
//   return throwError(errorMessage);
// }

}

import { Component, OnInit, ViewChild } from '@angular/core';
import { Sort } from '@angular/material';
import { throwError } from 'rxjs';
import { BulkDataService } from '../../agri/services/bulk-data.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { globalConstants } from '../../global/globalConstants';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { PageZonalCommodity } from '../models/PageZonalCommodity';
import { ZonalCommodityService } from '../services/zonal-commodity.service';
@Component({
  selector: 'app-zonal-commodity',
  templateUrl: './zonal-commodity.component.html',
  styleUrls: ['./zonal-commodity.component.scss']
})
export class ZonalCommodityComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  commodityStatus;
  pageZonalCommodity: PageZonalCommodity;
  selectedPage: number = 1;
  maxSize = 10;
  searchText: any="";

  ZonalCommodityList: any = [];
  missing : any="";

  recordsPerPage: number = 10;
   records: any = [];


  ngOnInit(): void {
    this.records = ['20', '50', '100', '200', '250'];
    // this.loadAllPhenophaseDuration();
    this.getZonalCommodityPagenatedList(0);
    this.commodityStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    public zonalCommodityService: ZonalCommodityService,
    private userRightsService: UserRightsService,
) { }


getZonalCommodityPagenatedList(page: number): void {
  this.zonalCommodityService.getZonalCommodityPagenatedList(page, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageZonalCommodity = page);
}

loadData(event: any) {
  console.log('pages ', event.target.value);
  this.recordsPerPage = event.target.value || 10;
  this.zonalCommodityService.getZonalCommodityPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    .subscribe(page => this.pageZonalCommodity = page);
}

onSelect(page: number): void {
  (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
  this.bulkDatas.disbled = true;
  // console.log('selected page : ' + page);
  this.selectedPage = page;
  this.getZonalCommodityPagenatedList(page);
}

search() {
this.selectedPage = 1;
console.log(this.searchText);
this.getZonalCommodityPagenatedList(this.selectedPage - 1);
}

//  // ZonalCommodity list
//  getAllZonalCommodity() {
//   return this.zonalCommodityService.().subscribe((data: {}) => {
//     this.ZonalCommodityList = data;
//   })
// }



// delete(data, i) {
//   data.index = i;
//   data.flag = "delete"
//   this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg, data);
// }
// Reject 
reject(data) {
  // data.index = i;
  data.flag = "reject"
  this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg , data);
}

approve(data) {
  // data.index = i;
  data.flag = "approve"
  this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg , data);
}
finalize(data) {
  // data.index = i;
  data.flag = "finalize"
  this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg, data);
}



approveCommodity(event: any) {
  return this.zonalCommodityService.approveCommodity(event.id).subscribe(res => {
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

finalizeCommodity(event: any) {
  return this.zonalCommodityService.finalizeCommodity(event.id).subscribe(res => {
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

rejectCommodity(event: any) {
  return this.zonalCommodityService.rejectCommodity(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        // this.ZonalCommodityList.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}





modalConfirmation(event) {
  console.log(event);
  let observable: any;
  if (event) {
   // this.isSubmitted = true;
    if (event.flag == "approve") {
      observable = this.zonalCommodityService.approveCommodity(event.id)
    } else if (event.flag == "finalize") {
      observable = this.zonalCommodityService.finalizeCommodity(event.id)
    // } else if (event.flag == "delete") {
    //   observable = this.zonalCommodityService.DeleteCommodity(event.id)
    } else if (event.flag == "reject") {
      observable = this.zonalCommodityService.rejectCommodity(event.id)
    }
    observable.subscribe(res => {
      if (res) {
       // this.isSuccess = res.success;
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
}

modalSuccess($event: any) {
  (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
  this.bulkDatas.disbled = true;
  console.log("page : " + this.selectedPage);
  if(this.selectedPage >= 2){
    // console.log("Inside if");
  this.getZonalCommodityPagenatedList(this.selectedPage - 1);
  this.commodityStatus   = globalConstants;
  }else{
    // console.log("Inside else");
  this.ngOnInit();
  }
}





sortData(sort: Sort) {
  const data = this.pageZonalCommodity.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageZonalCommodity.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageZonalCommodity.content = data.sort((firstValue, secondValue) => {
    let isAsc = sort.direction == 'asc';
    switch (sort.active) {
      case globalConstants.ID:
        return compare(+firstValue.id, +secondValue.id, isAsc);
      case 'state':
        return compare(firstValue.state, secondValue.state, isAsc);
      case 'acz':
        return compare(firstValue.acz, secondValue.acz, isAsc);
      case 'commodity':
        return compare(firstValue.commodity, secondValue.commodity, isAsc);
      case 'sowingWeekStart':
        return compare(firstValue.sowingWeekStart, secondValue.sowingWeekStart, isAsc);
      case 'sowingWeekEnd':
        return compare(firstValue.sowingWeekEnd, secondValue.sowingWeekEnd, isAsc);
      case 'harvestWeekStart':
        return compare(+firstValue.harvestWeekStart, +secondValue.harvestWeekStart, isAsc);
      case 'harvestWeekEnd':
        return compare(+firstValue.harvestWeekEnd, +secondValue.harvestWeekEnd, isAsc);
        case 'noOfDaysForHarvestMonitoring':
          return compare(+firstValue.noOfDaysForHarvestMonitoring, +secondValue.noOfDaysForHarvestMonitoring, isAsc);
      case 'status':
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

errorHandl(error) {
  let errorMessage = '';
  if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
  } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
  }
  console.log(errorMessage);
  return throwError(errorMessage);
}


}

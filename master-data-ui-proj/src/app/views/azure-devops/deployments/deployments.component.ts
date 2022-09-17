import { Sort } from '@angular/material';
import { Component, OnInit, ViewChild } from '@angular/core';
import { globalConstants } from '../../global/globalConstants';
import { UserRightsService } from '../../services/user-rights.service';
import { DeploymentsService } from '../services/deployments.service';
import { PageDeployments } from '../models/page-deployments';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-deployments',
  templateUrl: './deployments.component.html',
  styleUrls: ['./deployments.component.scss']
})
export class DeploymentsComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  pageDeployments: PageDeployments;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  recordsPerPage: number = 10;
  records: any = [];
  

  constructor(private deploymentsService: DeploymentsService, private userRightsService: UserRightsService) { }

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getDelpoymentListByPagenation(0);
  }

  getDelpoymentListByPagenation(page: number): void {
    this.deploymentsService.getDelpoymentListByPagenation(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageDeployments = page);
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.deploymentsService.getDelpoymentListByPagenation(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    .subscribe(page => this.pageDeployments = page);
  }

  onSelect(page: number): void {
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getDelpoymentListByPagenation(page);
  }

  searchDeployment() {
    this.selectedPage = 1;
    // console.log(this.searchText);
    this.getDelpoymentListByPagenation(this.selectedPage - 1);
  }

 deploy(id){
  return this.deploymentsService.deploy(id).subscribe( res => {
    this.isSubmitted = true;
    if (res) {
        this.isSuccess = res.success;
        if (res.success) {
            this.successModal.showModal('SUCCESS', res.message, '');
        } else {
            this.errorModal.showModal('ERROR', res.error, '');
        }
    }
});
 }

 modalSuccess($event: any) {
}

sortData(sort: Sort) {
  const data = this.pageDeployments.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageDeployments.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageDeployments.content = data.sort((firstValue, secondValue) => {
    const isAsc = sort.direction == 'asc';
    switch (sort.active) {
      case globalConstants.ID:
        return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'vmName':
        return compare(firstValue.vmName, secondValue.vmName, isAsc);
      case 'applicationName':
        return compare(firstValue.applicationName, secondValue.applicationName, isAsc);
        case 'applicationInternalPort':
        return compare(firstValue.applicationInternalPort, secondValue.applicationInternalPort, isAsc);
        case 'applicationExternalPort':
        return compare(firstValue.applicationExternalPort, secondValue.applicationExternalPort, isAsc);
        case 'dockerIP':
        return compare(firstValue.dockerIP, secondValue.dockerIP, isAsc);
      default:
        return 0;
    }
  });
}

}

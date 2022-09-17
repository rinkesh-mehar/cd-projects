import { ViewChild } from '@angular/core';
import { VirtualMachinesService } from './../services/virtual-machines.service';
import { Component, OnInit } from '@angular/core';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import { Sort } from '@angular/material';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { PageVirtualMachines } from '../models/page-virtual-machines';

@Component({
  selector: 'app-virtual-machines',
  templateUrl: './virtual-machines.component.html',
  styleUrls: ['./virtual-machines.component.scss']
})
export class VirtualMachinesComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  pageVirtualMachines: PageVirtualMachines;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  recordsPerPage: number = 10;
  records: any = [];
  password: string = '';
  

  constructor(private virtualMachinesService: VirtualMachinesService, private userRightsService: UserRightsService) { }

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getVirtualMachineListByPagenation(0);
  }

  getVirtualMachineListByPagenation(page: number): void {
    this.virtualMachinesService.getVirtualMachineListByPagenation(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageVirtualMachines = page);
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.virtualMachinesService.getVirtualMachineListByPagenation(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    .subscribe(page => this.pageVirtualMachines = page);
  }

  onSelect(page: number): void {
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getVirtualMachineListByPagenation(page);
  }

  searchVirtualMachine() {
    this.selectedPage = 1;
    // console.log(this.searchText);
    this.getVirtualMachineListByPagenation(this.selectedPage - 1);
  }

  showPassword(id) {
    this.getPassoword(id);
  }

  getPassoword(id){
    console.log("vm id : " + id);
    return this.virtualMachinesService.getPassoword(id).subscribe(data => {
      this.password = data.pwd;
    });
  }
 

sortData(sort: Sort) {
  const data = this.pageVirtualMachines.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageVirtualMachines.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageVirtualMachines.content = data.sort((firstValue, secondValue) => {
    const isAsc = sort.direction == 'asc';
    switch (sort.active) {
      case globalConstants.ID:
        return compare(+firstValue.id, +secondValue.id, isAsc);
        case globalConstants.NAME:
        return compare(firstValue.name, secondValue.name, isAsc);
      case 'privateIP':
        return compare(firstValue.privateIP, secondValue.privateIP, isAsc);
        case 'publicIP':
        return compare(firstValue.publicIP, secondValue.publicIP, isAsc);
        case 'vnetName':
        return compare(firstValue.vnetName, secondValue.vnetName, isAsc);
        case 'userName':
        return compare(firstValue.userName, secondValue.userName, isAsc);
        case 'password':
        return compare(firstValue.password, secondValue.password, isAsc);
      default:
        return 0;
    }
  });
}

}

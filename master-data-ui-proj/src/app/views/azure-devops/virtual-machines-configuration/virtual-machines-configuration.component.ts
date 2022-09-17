import { Component, OnInit } from '@angular/core';
import { Sort } from '@angular/material';
import { globalConstants } from '../../global/globalConstants';
import { UserRightsService } from '../../services/user-rights.service';
import { PageVirtualMachinesConfiguration } from '../models/page-virtual-machines-configuration';
import { VirtualMachinesConfigurationService } from '../services/virtual-machines-configuration.service';

@Component({
  selector: 'app-virtual-machines-configuration',
  templateUrl: './virtual-machines-configuration.component.html',
  styleUrls: ['./virtual-machines-configuration.component.scss']
})
export class VirtualMachinesConfigurationComponent implements OnInit {

  pageVirtualMachinesConfiguration: PageVirtualMachinesConfiguration;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  recordsPerPage: number = 10;
  records: any = [];
  

  constructor(private virtualMachinesConfigurationService: VirtualMachinesConfigurationService,
     private userRightsService: UserRightsService) { }

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getVirtualMachineConfigurationListByPagenation(0);
  }

  getVirtualMachineConfigurationListByPagenation(page: number): void {
    this.virtualMachinesConfigurationService.getVirtualMachineConfigurationListByPagenation(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageVirtualMachinesConfiguration = page);
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.virtualMachinesConfigurationService.getVirtualMachineConfigurationListByPagenation(this.selectedPage - 1,
           this.recordsPerPage, this.searchText)
    .subscribe(page => this.pageVirtualMachinesConfiguration = page);
  }

  onSelect(page: number): void {
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getVirtualMachineConfigurationListByPagenation(page);
  }

  searchVirtualMachinesConfiguration() {
    this.selectedPage = 1;
    // console.log(this.searchText);
    this.getVirtualMachineConfigurationListByPagenation(this.selectedPage - 1);
  }

 

sortData(sort: Sort) {
  const data = this.pageVirtualMachinesConfiguration.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageVirtualMachinesConfiguration.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageVirtualMachinesConfiguration.content = data.sort((firstValue, secondValue) => {
    const isAsc = sort.direction == 'asc';
    switch (sort.active) {
      case globalConstants.ID:
        return compare(+firstValue.id, +secondValue.id, isAsc);
      case 'vmName':
        return compare(firstValue.vmName, secondValue.vmName, isAsc);
        case 'cup':
        return compare(firstValue.cpu, secondValue.cpu, isAsc);
        case 'ram':
        return compare(firstValue.ram, secondValue.ram, isAsc);
        case 'internalDisk':
        return compare(firstValue.internalDisk, secondValue.internalDisk, isAsc);
        case 'externalDisk':
        return compare(firstValue.externalDisk, secondValue.externalDisk, isAsc);
      default:
        return 0;
    }
  });
}

}

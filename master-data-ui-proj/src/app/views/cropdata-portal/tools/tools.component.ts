import { Sort } from '@angular/material';
import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { globalConstants } from '../../global/globalConstants';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { PageTools } from '../models/page-tools';
import { ToolsService } from '../services/tools.service';

@Component({
  selector: 'app-tools',
  templateUrl: './tools.component.html',
  styleUrls: ['./tools.component.scss']
})
export class ToolsComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  pageTools: PageTools;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  toolsStatus;
  engineList: any = [];

  recordsPerPage: number = 10;
  records: any = [];

  logoUrl : string = '';

  constructor(private toolsService: ToolsService, private userRightsService: UserRightsService) { }

ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageToolList(0);
    this.toolsStatus = globalConstants;
  }

getPageToolList(page: number): void {
    this.toolsService.getPageToolList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageTools = page);
}

loadData(event: any) {
  console.log('pages ', event.target.value);
  this.recordsPerPage = event.target.value || 10;
  this.toolsService.getPageToolList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    .subscribe(page => this.pageTools = page);
}

onSelect(page: number): void {
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPageToolList(page);
}

searchTool() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageToolList(this.selectedPage - 1);
}

// Deactive Position
deactive(data, i) {
 
      data.index = i;
      data.flag = "deactive"
      this.confirmModal.showModal(globalConstants.deactiveDataTitle, globalConstants.deactiveDataMsg + " " + data.name, data);
}

// active Position
active(data, i) {
    data.index = i;
    data.flag = "active"
    this.confirmModal.showModal(globalConstants.activeDataTitle, globalConstants.activeDataMsg + " " + data.name, data);
}

// delete Department
delete(data, i) { 
      data.index = i;
      data.flag = "delete"
      this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.name, data);
}

deactiveTool(event) {
  return this.toolsService.deactiveTool(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.engineList.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}

activeTool(event) {
  return this.toolsService.activeTool(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.engineList.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}

deleteTool(event) {
  return this.toolsService.deleteTool(event.id).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.engineList.splice(event.index, 1);
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
  if (event) {
    this.isSubmitted = true;
    if (event.flag == "active") {
      this.activeTool(event);
    } else if (event.flag == "deactive") {
      this.deactiveTool(event);
    } else if (event.flag == "delete") {
      this.deleteTool(event);
    } 
  }
}

sortData(sort: Sort) {
  const data = this.pageTools.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageTools.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageTools.content = data.sort((firstValue, secondValue) => {
    const isAsc = sort.direction == 'asc';
    switch (sort.active) {
      case globalConstants.ID:
        return compare(+firstValue.id, +secondValue.id, isAsc);
      case globalConstants.NAME:
        return compare(firstValue.name, secondValue.name, isAsc);
      case 'description':
        return compare(firstValue.description, secondValue.description, isAsc);
      case 'engine':
        return compare(firstValue.engine, secondValue.engine, isAsc);
      case 'platform':
        return compare(firstValue.platform, secondValue.platform, isAsc);
      case globalConstants.STATUS:
        return compare(firstValue.status, secondValue.status, isAsc);
      default:
        return 0;
    }
  });
}

getLogoUrl(event: any){
  this.logoUrl = event.target.src;
  console.log("image found..." + this.logoUrl);
}

modalSuccess($event: any) {
  //  this.ngOnInit();
  // this.selectedPage = 1;

  if(this.selectedPage >= 2){
    this.getPageToolList(this.selectedPage - 1);
    this.toolsStatus = globalConstants;
    }else{
    this.ngOnInit();
    }

}

}

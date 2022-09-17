import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Sort } from '@angular/material';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { globalConstants } from '../../global/globalConstants';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { PageProductsAndServices } from '../models/page-products-and-services';
import { ProductsAndServicesService } from '../services/products-and-services.service';

@Component({
  selector: 'app-products-and-services',
  templateUrl: './products-and-services.component.html',
  styleUrls: ['./products-and-services.component.scss']
})
export class ProductsAndServicesComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  pageProductsAndServices: PageProductsAndServices;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  productsAndServicesStatus;
  engineList: any = [];

  recordsPerPage: number = 10;
  records: any = [];

  logoUrl : string = '';

  constructor(private productsAndServicesService: ProductsAndServicesService, private userRightsService: UserRightsService) { }

ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageProductsAndServicesList(0);
    this.productsAndServicesStatus = globalConstants;
  }

getPageProductsAndServicesList(page: number): void {
    this.productsAndServicesService.getPageProductsAndServicesList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageProductsAndServices = page);
}

loadData(event: any) {
  console.log('pages ', event.target.value);
  this.recordsPerPage = event.target.value || 10;
  this.productsAndServicesService.getPageProductsAndServicesList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
    .subscribe(page => this.pageProductsAndServices = page);
}

onSelect(page: number): void {
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getPageProductsAndServicesList(page);
}

searchTool() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageProductsAndServicesList(this.selectedPage - 1);
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

deactiveProductsAndServices(event) {
  return this.productsAndServicesService.deactiveProductsAndServices(event.id).subscribe(res => {
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

activeProductsAndServices(event) {
  return this.productsAndServicesService.activeProductsAndServices(event.id).subscribe(res => {
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

deleteProductsAndServices(event) {
  return this.productsAndServicesService.deleteProductsAndServices(event.id).subscribe(res => {
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
      this.activeProductsAndServices(event);
    } else if (event.flag == "deactive") {
      this.deactiveProductsAndServices(event);
    } else if (event.flag == "delete") {
      this.deleteProductsAndServices(event);
    } 
  }
}

sortData(sort: Sort) {
  const data = this.pageProductsAndServices.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageProductsAndServices.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageProductsAndServices.content = data.sort((firstValue, secondValue) => {
    const isAsc = sort.direction == 'asc';
    switch (sort.active) {
      case globalConstants.ID:
        return compare(+firstValue.id, +secondValue.id, isAsc);
      case globalConstants.NAME:
        return compare(firstValue.name, secondValue.name, isAsc);
      case 'description':
        return compare(firstValue.description, secondValue.description, isAsc);
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
    this.getPageProductsAndServicesList(this.selectedPage - 1);
    this.productsAndServicesStatus = globalConstants;
    }else{
    this.ngOnInit();
    }

}

}

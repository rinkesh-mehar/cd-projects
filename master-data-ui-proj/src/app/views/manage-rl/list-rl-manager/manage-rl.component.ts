import { ErrorModalComponent } from './../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from './../../global/success-modal/success-modal.component';
import { ConfirmationMadalComponent } from './../../global/confirmation-madal/confirmation-madal.component';
import { Component, OnInit, ViewChild } from '@angular/core';
import { RlUserpage } from '../model/rl-userpage';
import { UserRightsService } from '../../services/user-rights.service';
import { RlService } from '../service/rl.service';
import { Sort } from '@angular/material';
import { globalConstants } from '../../global/globalConstants';

@Component({
  selector: 'app-manage-rl',
  templateUrl: './manage-rl.component.html',
  styleUrls: ['./manage-rl.component.css']
})
export class ManageRlComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  @ViewChild('closebutton') closebutton;
  searchText: any = '';
  rlUserPage: RlUserpage;
  rlUserList: any = [];
  selectedPage: number = 1;
  imgUrl: string = '';
  recordsPerPage: number = 10;
  records: any = [];
  rlStatus;
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  maxSize =10;
  constructor(private userRightsService: UserRightsService, private rlUserService: RlService) { }

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.loadRlUserList(0);
    this.rlStatus = globalConstants;
  }

  loadRlUserList(page: number) {
    this.rlUserService.getRlUserList(page, this.recordsPerPage, this.searchText)
      .subscribe(page => this.rlUserPage = page);
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.rlUserService.getRlUserList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.rlUserPage = page);
  }

  onSelect(page: number): void {
    console.log('selected page : ' + page);
    this.selectedPage = page;
    this.loadRlUserList(page);
  }

  searchRLUsers() {
    console.log(this.searchText);
    this.loadRlUserList(this.selectedPage - 1);
  }

  getImageUrl(event: any) {
    this.imgUrl = event.target.src;
    console.log("image found..." + this.imgUrl);
  }

  sortData(sort: Sort) {
    const data = this.rlUserPage.content.slice();
    if (!sort.active || sort.direction == '') {
      this.rlUserPage.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.rlUserPage.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
      case 'regionName':
          return compare(firstValue.regionName, secondValue.regionName, isAsc);
        case globalConstants.NAME:
          return compare(firstValue.name, secondValue.name, isAsc);
        case 'roleName':
          return compare(firstValue.roleName, secondValue.roleName, isAsc);
        case 'aggrementAccepted':
          return compare(firstValue.aggrementAccepted, secondValue.aggrementAccepted, isAsc);
        case globalConstants.STATUS:
          return compare(firstValue.status, secondValue.status, isAsc);
        default:
          return 0;
      }
    });
  }

  hide() {
    this.closebutton.nativeElement.click();
  }

  reActive(data) {
    data.flag = "reactive"
    this.confirmModal.showModal(globalConstants.reActiveDataTitle, globalConstants.reActiveDataMsg, data);
  }

  reActiveLink(event) {
    console.log("Inside Reactive..!!!!!");
    return this.rlUserService.reActiveLink(event.id).subscribe(res => {
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

  modalConfirmation(event) {
    console.log("Inside Reactive..!!");
    console.log(event);
    if (event) {
      console.log("Inside Reactive..!!!");
      this.isSubmitted = true;
  
      if (event.flag == "reactive") {
        console.log("Inside Reactive..!!!!");
        this.reActiveLink(event);
      } 
    }
  }


modalSuccess($event: any) {
  // this.ngOnInit();
  // this.selectedPage = 1;

  console.log("page : " + this.selectedPage);
  if(this.selectedPage >= 2){
    console.log("Inside if");
  this.loadRlUserList(this.selectedPage - 1);
  this.rlStatus = globalConstants;
  }else{
    console.log("Inside else");
  this.ngOnInit();
  }
}

}

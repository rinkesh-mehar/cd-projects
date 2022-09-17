import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from '../../services/user.service';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { Sort } from '@angular/material';
import { globalConstants } from '../../../global/globalConstants';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { PageUser } from '../../Models/pageUser';



@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;


  UserList: any = [];
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  showEditUserForm: boolean;
  user: any;
  mode: string = "add";
  showAddUserForm: boolean;
  activityStatus;
  ActivityList: any = [];

  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  recordsPerPage: number = 10;
  records: any = [];
 pageUser:PageUser;
  bulkDatas: any;





  ngOnInit() {
    this.loadAllUsers();
    this.records = ['20', '50', '100', '200', '250'];
   this.getUserPagenatedList(0);
    this.activityStatus = globalConstants;
  }

  constructor(
    
    public userService: UserService
  ) { }

  getUserPagenatedList(page: number): void {
    this.userService.getUserPagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageUser = page);
  }

  // Users list
  loadAllUsers() {
    return this.userService.GetAllUser().subscribe((data: {}) => {
      this.UserList = data;
    }, err => {
      this._statusMsg = err.error;

    })
  }

  onSelect(page: number): void {
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getUserPagenatedList(page);
}

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.userService.getUserPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageUser = page);
  }

  search() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getUserPagenatedList(this.selectedPage - 1);
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  // // Delete User
  // deleteUser(data) {

  //   if (confirm("Are you sure to delete " + data.name)) {
  //     var index = index = this.UserList.map(x => { return x.id }).indexOf(data.id);
  //     return this.userService.DeleteUser(data.id).subscribe(res => {
  //       this.isSubmitted = true;
  //       if (res) {
  //         this.UserList.splice(index, 1)
  //         console.log('User deleted!')
  //         this.isSuccess = res.success;
  //         if (res.success) {
  //           this._statusMsg = res.message;
  //           this.delay(4000).then(any => {
  //             this.isSubmitted = false;
  //             this.isSuccess = false;
  //           });
  //         } else {
  //           this._statusMsg = res.error;
  //         }
  //       }
  //     })
  //   }
  // }


  deleteUser(data) {
    var index = this.UserList.map(x => { return x.id }).indexOf(data.id);

    data.index = index;
    this.confirmModal.showModal("Delete Data", "Are you sure to delete " + data.name, data);
  }

  modalConfirmation(event) {
    console.log(event);
    if (event) {
      this.isSubmitted = true;

      return this.userService.DeleteUser(event.id).subscribe(res => {
          
    if(res) {
      
      this.isSuccess = res.success;
      if (res.success) {
        this.UserList.splice(event.index, 1)
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
      
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
        // if (res) {
        //   this.UserList.splice(event.index, 1)
        //   console.log('User deleted!')
        //   this.isSuccess = res.success;
        //   if (res.success) {
        //     this._statusMsg = res.message;
        //     this.delay(4000).then(any => {
        //       this.isSubmitted = false;
        //       this.isSuccess = false;
        //     });
        //   } else {
        //     this._statusMsg = res.error;
        //   }
        // }
      }, err => {
        this._statusMsg = err.error;

      })
    }
  }





  editUser(user) {
    this.showEditUserForm = true;
    this.showAddUserForm = false;

    this.user = user;
    this.mode = "edit";
  }

  sortData(sort: Sort) {
    const data = this.pageUser.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageUser.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageUser.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case globalConstants.NAME:
            return compare(firstValue.name, secondValue.name, isAsc);
          case globalConstants.STATUS:
          return compare(firstValue.status, secondValue.status, isAsc);
          case 'email':
          return compare(firstValue.email, secondValue.email, isAsc);
          case 'role':
          return compare(firstValue.role, secondValue.role, isAsc);


        default:
          return 0;
      }
    });
  }
  modalSuccess($event: any) {
  this.ngOnInit();
  }
}

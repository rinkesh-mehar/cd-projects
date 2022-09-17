import { globalConstants } from './../../global/globalConstants';
import { Component, OnInit, NgZone, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RoleService } from '../services/role.service';
import { Role } from '../Models/role';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { Sort } from '@angular/material';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { GroupService } from '../services/group.service';
import { PageRole } from '../Models/pageRole';
import { ThemeService } from 'ng2-charts';

@Component({
    selector: 'app-role',
    templateUrl: './role.component.html',
    styleUrls: ['./role.component.scss']
})
export class RoleComponent implements OnInit {

    @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    roleForm: FormGroup;
    roleArr: any = [];
    rolelist: Array<Role> = [];
    isSubmitted: boolean = false;
    isSuccess: any;
    _statusMsg: any;

    subResourceForm: FormGroup;
    resourceGroupList: any;
    searchText: any = '';
    selectedPage: number = 1;
    maxSize =10;
    recordsPerPage: number = 10;
    records: any = [];
    pageRole: PageRole;
      bulkDatas: any;

    operationMode = "add";
    roleIndex: Number;

    ngOnInit() {
        this.addrole();
        
        this.records = ['20', '50', '100', '200', '250'];
        this.getRolePagenatedList(0);
    }

    constructor(
        public fb: FormBuilder,
        private ngZone: NgZone,
        private router: Router,
        private roleService: RoleService,
        private groupService: GroupService,


    ) { }

    search() {
        this.selectedPage = 1;
        console.log(this.searchText);
        this.getRolePagenatedList(this.selectedPage - 1);
      }
    
      getRolePagenatedList(page: number): void {
        this.roleService.getRolePagenatedList(page, this.recordsPerPage, this.searchText)
            .subscribe(page => this.pageRole = page);
      }
      onSelect(page: number): void {
        // console.log('selected page : ' + page);
        this.selectedPage = page;
        this.getRolePagenatedList(page);
    }
    
      loadData(event: any) {
        console.log('pages ', event.target.value);
        this.recordsPerPage = event.target.value || 10;
        this.roleService.getRolePagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
          .subscribe(page => this.pageRole = page);
      }
    

    loadAllRole() {
        return this.roleService.GetAllRole().subscribe((data: any) => {
            this.rolelist = data;
            console.log('DATA : ', data);
        }, err => {
            console.log('err loadAllRole', err);

        })
    }

    addrole() {
        this.roleForm = this.fb.group({
            id: [],
            name: ['', [Validators.required, Validators.maxLength(25)]],
            description: ['', Validators.required]

        })
    }

    editRole(role) {
        this.roleForm.patchValue(role);
        this.operationMode = "edit";

    }

    deleteFieldValue(role) {
        var index = this.rolelist.map(x => { return x.id }).indexOf(role.id);
    
        role.index = index;
        this.confirmModal.showModal("Delete Data", "Are you sure to delete " + role.Name, role);
      }

    // deleteFieldValue(data, i) {
    //     data.index = i;
    //     this.confirmModal.showModal("Delete Data", "Do you want to delete this data?", data);
    // }

    modalConfirmation(event) {
        console.log(event);
        if (event) {
            this.roleService.DeleteRole(event.id).subscribe((res: any) => {
                this.isSubmitted = true;
                if(res) {
      
                    this.isSuccess = res.success;
                    if (res.success) {
                        this.rolelist.splice(event.index, 1)
                        this.roleForm.reset();
                      this.successModal.showModal('SUCCESS', res.message, '');
                    } else {
                    
                      this.errorModal.showModal('ERROR', res.error, '');
                    }
                  }
                // if (res) {
                //     this.isSuccess = res.success;
                //     if (res.success) {
                //         this.rolelist.splice(event.index, 1)
                //         this._statusMsg = res.message;
                //         this.roleForm.reset();
                //         setTimeout(() => {
                //             this.isSubmitted = false;
                //             this.isSuccess = false
                //         }, 4000)
                //     } else {
                //         this._statusMsg = res.error;
                //     }
                // }
            }, err => {
                console.log(err);
            })
        }
    }

    submitForm() {

        for (let controller in this.roleForm.controls) {
            this.roleForm.get(controller).markAsTouched();
        }
        if (this.roleForm.invalid) {
            return;
        } else {
            if (this.operationMode == "edit") {
                this.updateRole();

            } else {
                this.addNewRole();
            }
        }
    }

    addNewRole() {
        this.roleService.addRole(this.roleForm.value).subscribe((res: any) => {
            this.isSubmitted = true;
              
    if(res) {
      
        this.isSuccess = res.success;
        if (res.success) {
            this.loadAllRole();
            this.roleForm.reset();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
            // if (res) {
            //     this.isSuccess = res.success;
            //     if (res.success) {
            //         this.loadAllRole();
            //         this._statusMsg = res.message;
            //         this.roleForm.reset();
            //         setTimeout(() => {
            //             this.isSubmitted = false;
            //             this.isSuccess = false
            //         }, 4000)
            //     } else {
            //         this._statusMsg = res.error;
            //     }
            // }
        }, err => {
            console.log(err); this.loadAllRole();
        })
    }

    updateRole() {
        this.roleService.UpdateRole(this.roleForm.value.id, this.roleForm.value).subscribe((res: any) => {
            this.isSubmitted = true;
              
    if(res) {
      
        this.isSuccess = res.success;
        if (res.success) {
            this.loadAllRole();
            this.roleForm.reset();
            this.operationMode = "add";
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
            // if (res) {
            //     this.isSuccess = res.success;
            //     if (res.success) {
            //         this.loadAllRole();
            //         this._statusMsg = res.message;
            //         this.roleForm.reset();
            //         this.operationMode = "add";
            //         setTimeout(() => {
            //             this.isSubmitted = false;
            //             this.isSuccess = false
            //         }, 4000)
            //     } else {
            //         this._statusMsg = res.error;
            //     }
            // }
        }, err => {
            console.log(err);
        })
    }

    test(aaa) {
        console.log(aaa.errors);
        console.log(aaa.hasError())
    }


    sortData(sort: Sort) {
        const data = this.pageRole.content.slice();
        if (!sort.active || sort.direction == '') {
          this.pageRole.content = data;
          return;
        }
      
        function compare(firstValue, secondValue, isAsc: boolean) {
          return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }
      
        this.pageRole.content = data.sort((firstValue, secondValue) => {
          const isAsc = sort.direction == 'asc';
          switch (sort.active) {
            case globalConstants.ID:
              return compare(+firstValue.id, +secondValue.id, isAsc);
              case 'name':
              return compare(firstValue.name, secondValue.name, isAsc);
              case 'description':
              return compare(firstValue.description, secondValue.description, isAsc);
    
    
            default:
              return 0;
          }
        });
      }

      modalSuccess($event: any) {
        this.ngOnInit();
      }
      onCancelClick(){
        this.ngOnInit();
        this.operationMode = "add";
      }
      
}

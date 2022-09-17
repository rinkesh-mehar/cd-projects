import { UserRightsService } from './../../services/user-rights.service';
import { PageAclResourceGroup } from './../Models/PageAclResourceGroup';
import { Component, OnInit, ViewChild, NgZone } from '@angular/core';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ResourceGroupService } from '../services/resource-group.service';
import { Sort } from '@angular/material';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-resource-group',
  templateUrl: './resource-group.component.html',
  styleUrls: ['./resource-group.component.scss']
})
export class ResourceGroupComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  resourceGroupForm: FormGroup;
  resourceGroupList: any = [];
  operationMode = "add";

  isSubmitted: boolean = false;
  isSuccess: any;
  _statusMsg: any;
  pageAclResourceGroup: PageAclResourceGroup;

  recordsPerPage: number = 10;
  selectedPage : number = 1;
  records: any = [];
  searchText: any = '';
  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.loadResourceGroup();
    this.addresourceGroup();
    this.getPageAclResourceGroup(0);
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    private resourceGroupService: ResourceGroupService,
  ) { }

  addresourceGroup() {
    this.resourceGroupForm = this.fb.group({
      id: [''],
      resourceGroupName: ['', Validators.required],
      menuPlacement: ['', [Validators.required,Validators.pattern("^[0-9]*$")]]


    })
  }

  loadResourceGroup() {
    this.resourceGroupService.GetAllResourceGroup().subscribe((data: any) => {
      this.resourceGroupList = data;
    }, err => {
      alert(err)
    })
  }
  getPageAclResourceGroup(page:number): void {
    this.resourceGroupService.getPageAclResourceGroup(page, this.recordsPerPage,this.searchText)
        .subscribe(page => this.pageAclResourceGroup = page)
  }
  searchAclResourceGroup() {
    this.selectedPage = 1;
    console.log("Search Text ="+this.searchText);
    this.getPageAclResourceGroup(this.selectedPage - 1);
  }
  submitForm() {
    for (let controller in this.resourceGroupForm.controls) {
      this.resourceGroupForm.get(controller).markAsTouched();
    }
    if (this.resourceGroupForm.invalid) {
      return;
    }
    if (this.operationMode == "edit") {
      this.updateResourceGroup();
    } else {
      this.addResourceGroup();
    }
  }

  editResourceGroup(data) {
    this.operationMode = "edit"
    this.resourceGroupForm.patchValue(data);
  }

  addResourceGroup() {
    this.resourceGroupService.addResourceGroup(this.resourceGroupForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if(res) {
      
        this.isSuccess = res.success;
        if (res.success) {
          this.loadResourceGroup();
          this.resourceGroupForm.reset();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
      // if (res) {
      //   this.isSuccess = res.success;
      //   if (res.success) {
      //     this.loadResourceGroup();
      //     this._statusMsg = res.message;
      //     this.resourceGroupForm.reset();
      //     setTimeout(() => {
      //       this.isSubmitted = false;
      //       this.isSuccess = false
      //     }, 4000)
      //   } else {
      //     this._statusMsg = res.error;
      //   }
      // }
    }, err => {
      console.log(err);
    })
  }

  updateResourceGroup() {
    this.resourceGroupService.UpdateResourceGroup(this.resourceGroupForm.value.id, this.resourceGroupForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if(res) {
      
        this.isSuccess = res.success;
        if (res.success) {
          this.loadResourceGroup();
          this.resourceGroupForm.reset();
          this.operationMode = "add";
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
      // if (res) {
      //   this.isSuccess = res.success;
      //   if (res.success) {
      //     this.loadResourceGroup();
      //     this._statusMsg = res.message;
      //     this.resourceGroupForm.reset();
      //     this.operationMode = "add";
      //     setTimeout(() => {
      //       this.isSubmitted = false;
      //       this.isSuccess = false
      //     }, 4000)
      //   } else {
      //     this._statusMsg = res.error;
      //   }
      // }
    }, err => {
      console.log(err);
    })
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.resourceGroupService.getPageAclResourceGroup(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageAclResourceGroup = page);
  }

  deleteFieldValue(data, i) {
    data.index = i;
    this.confirmModal.showModal("Delete Data", "Do you want to delete this data?", data);
  }

  modalConfirmation(event) {
    console.log(event);
    if (event) {
      this.resourceGroupService.DeleteResourceGroup(event.id).subscribe((res: any) => {
        this.isSubmitted = true;
        if(res) {
      
          this.isSuccess = res.success;
          if (res.success) {
            this.resourceGroupList.splice(event.index, 1)
            this.resourceGroupForm.reset();
            this.successModal.showModal('SUCCESS', res.message, '');
          } else {
          
            this.errorModal.showModal('ERROR', res.error, '');
          }
        }
        // if (res) {
        //   this.isSuccess = res.success;
        //   if (res.success) {
        //     this.resourceGroupList.splice(event.index, 1)
        //     this._statusMsg = res.message;
        //     this.resourceGroupForm.reset();
        //     setTimeout(() => {
        //       this.isSubmitted = false;
        //       this.isSuccess = false
        //     }, 4000)
        //   } else {
        //     this._statusMsg = res.error;
        //   }
        // }
      }, err => {
        console.log(err);
      })
    }
  }

  sortData(sort: Sort) {
    const data = this.pageAclResourceGroup.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAclResourceGroup.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageAclResourceGroup.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
          case 'resourceGroupName':
            return compare(firstValue.resourceGroupName, secondValue.resourceGroupName, isAsc);
        case 'menuPlacement':
          return compare(firstValue.menuPlacement, secondValue.menuPlacement, isAsc);
        default:
          return 0;
      }
    });
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  modalSuccess($event: any) {
    
  }
  onCancelClick(){
    this.ngOnInit();
    this.operationMode = "add";
  }
  onSelect(page: number): void {
      // console.log('selected page : ' + page);
      this.selectedPage = page;
      this.getPageAclResourceGroup(page);
  }
}
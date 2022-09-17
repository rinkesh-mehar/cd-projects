import { Component, OnInit, NgZone, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ResourceService } from '../services/resource.service';
import { GroupService } from '../services/group.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { ResourceGroupService } from '../services/resource-group.service';
import {ModalDirective} from 'ngx-bootstrap/modal';
import { Sort } from '@angular/material';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { PageResource } from '../Models/pageResource';
import { Resource } from '../Models/resource';
import { globalConstants } from '../../global/globalConstants';

@Component({
  selector: 'app-resource',
  templateUrl: './resource.component.html',
  styleUrls: ['./resource.component.scss']
})
export class ResourceComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('addResourceModal') public resourceModal: ModalDirective;
  @ViewChild('addSubResourceModal') public subResourceModal: ModalDirective;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  ResourceList: any = [];
  resourceForm: FormGroup;
  resourceArr: any = [];
  parentResources: any = [];
  resourceFormMode: any = "add";
  subResourceFormMode: any = "add";
  groupList: any;

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
 pageResource: PageResource;


  ngOnInit() {
    this.addresource();
    this.records = ['20', '50', '100', '200', '250'];
   this.getResourcePagenatedList(0);
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    private resourceService: ResourceService,
    private groupService: GroupService,
    private resourceGroupService: ResourceGroupService,
  ) { this.loadAllResources() }

  addresource() {
    this.resourceForm = this.fb.group({
      id: [''],
      resourceGroupId: [''],
      resourceName: ['', Validators.required],
      resourceUrl: ['', Validators.required]

    })

    this.subResourceForm = this.fb.group({
      id: [''],
      parentId: [''],
      resourceName: ['', Validators.required],
      // groupId: ['', Validators.required],
      resourceUrl: ['', Validators.required]

    })
  }

  search() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getResourcePagenatedList(this.selectedPage - 1);
  }

  getResourcePagenatedList(page: number): void {
    this.resourceService.getResourcePagenatedList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageResource = page);
  }
  onSelect(page: number): void {
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getResourcePagenatedList(page);
}

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.resourceService.getResourcePagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageResource = page);
  }







  loadAllResources() {
    this.resourceService.GetAllResource().subscribe(res => {
      this.resourceArr = res;
    });
    this.resourceService.GetParentsResource().subscribe(res => {
      this.parentResources = res;
    })
    this.groupService.GetAllGroup().subscribe((data: any) => {
      this.groupList = data;
    }, err => {
    })
    this.resourceGroupService.GetAllResourceGroup().subscribe((data: any) => {
      this.resourceGroupList = data;
    }, err => {
    })
  }


  // editResource(data) {
  //   window.scrollTo(0, 0);
  //   if (data.parentId == 0) {
  //     this.resourceForm.patchValue(data);
  //     this.resourceFormMode = "edit";
  //   } else {
  //     this.subResourceForm.patchValue(data);
  //     this.subResourceFormMode = "edit";
  //   }
  // }

  submitForm() {

    for (let controller in this.resourceForm.controls) {
      this.resourceForm.get(controller).markAsTouched();
    }

    if (this.resourceForm.invalid) {
      return;
    }

    // if (this.mode == "edit") {
    //   this.updateResource();
    // } else {
    //   this.addResource();
    // }
  }


  submitResourceForm() {
    for (let controller in this.resourceForm.controls) {
      this.resourceForm.get(controller).markAsTouched();
    }
    if (this.resourceForm.invalid) {
      return;
    }
    if (this.resourceFormMode == "edit") {
      this.updateResource();
    } else {
      this.addResource();
    }
  }


  updateResource() {
    this.resourceService.UpdateResource(this.resourceForm.value.id, this.resourceForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if(res) {
      
        this.isSuccess = res.success;
        if (res.success) {
          document.getElementById('resourceModel').click();
          this.loadAllResources();
          this.resourceForm.reset();
          this.resourceFormMode = "add";
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
      // if (res) {
      //   this.isSuccess = res.success;
      //   if (res.success) {
      //     this.loadAllResources();
      //     this._statusMsg = res.message;
      //     this.resourceForm.reset();
      //     this.resourceFormMode = "add";
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


  addResource() {
    this.resourceService.addResource(this.resourceForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if(res) {
      
        this.isSuccess = res.success;
        if (res.success) {
          document.getElementById('resourceModel').click();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
      // if (res) {
      //   this.isSuccess = res.success;
      //   if (res.success) {
      //     this.loadAllResources();
      //     this._statusMsg = res.message;
      //     this.resourceForm.reset();
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



  submitSubResourceForm() {
    for (let controller in this.subResourceForm.controls) {
      this.subResourceForm.get(controller).markAsTouched();
    }
    if (this.subResourceForm.invalid) {
      return;
    }
    if (this.subResourceFormMode == "edit") {
      this.updateSubResource();
    } else {
      this.addSubResource();
    }
  }


  addSubResource() {
    this.resourceService.addResource(this.subResourceForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if(res) {
      
        this.isSuccess = res.success;
        if (res.success) {
          document.getElementById('subResourceModel').click();
          this.loadAllResources();
          this.subResourceForm.reset();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
      // if (res) {
      //   this.isSuccess = res.success;
      //   if (res.success) {
      //     this.loadAllResources();
      //     this._statusMsg = res.message;
      //     this.subResourceForm.reset();
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

  updateSubResource() {
    this.resourceService.UpdateResource(this.subResourceForm.value.id, this.subResourceForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if(res) {
      
        this.isSuccess = res.success;
        if (res.success) {
          document.getElementById('subResourceModel').click();
          this.loadAllResources();
          this.subResourceForm.reset();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
      // if (res) {
      //   this.isSuccess = res.success;
      //   if (res.success) {
      //     this.loadAllResources();
      //     this._statusMsg = res.message;
      //     this.subResourceForm.reset();
      //     this.subResourceFormMode = "add";
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


  deleteResource(data) {
    var index = this.ResourceList.map(x => { return x.id }).indexOf(data.id);

    data.index = index;
    this.confirmModal.showModal("Delete Data", "Are you sure to delete " + data.resourceName, data);
  }

  modalConfirmation(event) {
    console.log(event);
    if (event) {
      this.resourceService.DeleteResource(event.id).subscribe((res: any) => {
        this.isSubmitted = true;
        if(res) {
      
          this.isSuccess = res.success;
          if (res.success) {
            this.resourceArr.splice(event.index, 1)
            this.successModal.showModal('SUCCESS', res.message, '');
          } else {
          
            this.errorModal.showModal('ERROR', res.error, '');
          }
        }
        // if (res) {
        //   this.isSuccess = res.success;
        //   if (res.success) {
        //     this._statusMsg = res.message;
        //     this.resourceArr.splice(event.index, 1)
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

  showResourceModal(data) {
    if (data != null) {
      if (data.parentId == 0) {
        this.resourceForm.patchValue(data);
        this.resourceFormMode = 'edit';
      } else {
        this.subResourceForm.patchValue(data);
        this.subResourceFormMode = 'edit';
      }
    }
      this.resourceModal.show();
  }

  showSubResourceModal() {
    this.subResourceModal.show();
  }

  hideResourceModal() {
    this.resourceModal.hide();
  }

  hideSubResourceModal() {
    this.subResourceModal.hide();
  }

  sortData(sort: Sort) {
    const data = this.pageResource.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageResource.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageResource.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case 'resourceName':
          return compare(firstValue.resourceName, secondValue.resourceName, isAsc);
          case 'resourceUrl':
          return compare(firstValue.resourceUrl, secondValue.resourceUrl, isAsc);


        default:
          return 0;
      }
    });
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  modalSuccess($event: any) {
    this.ngOnInit();
    
  }
}

import { Component, OnInit, NgZone, ViewChild } from '@angular/core';
import { NgForm, FormArray, FormControl } from '@angular/forms';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { RoleService } from '../../services/role.service';
import { ResourceService } from '../../services/resource.service';
import { GroupService } from '../../services/group.service';
import { ManageRoleService } from '../../services/manage-role.service';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { ResourceGroupService } from '../../services/resource-group.service';
import { Sort } from '@angular/material';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-manage-role',
  templateUrl: './manage-role.component.html',
  styleUrls: ['./manage-role.component.scss']
})
export class ManageRoleComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  resourceData = ['Resource Group', 'Resources', 'Sub Resources'];
  restriction: any;

  isSubmitted: boolean = false;
  isSuccess: any;
  _statusMsg: any;

  manageRoleForm: FormGroup;
  manageRoleArr: any = [];
  rolelist: any;
  resourceList: any;
  subResourceList: any;
  groupList: any;
  resourceGroupList: any;
  subResourceGroupList: any;

  editRoleId: any
  subResourceIdList: any = [];
  constructor(private router: Router,
    public fb: FormBuilder,
    private roleService: RoleService,
    private resourceService: ResourceService,
    private groupService: GroupService,
    private manageRoleService: ManageRoleService,
    private actRoute: ActivatedRoute,
    private resourceGroupService: ResourceGroupService
  ) { this.manageRole(); this.loadData(); }

  ngOnInit() {
    this.editRoleId = this.actRoute.snapshot.paramMap.get('id');
  }


  loadData() {
    this.roleService.GetAllRole().subscribe((data: any) => {
      this.rolelist = data;
      if (this.editRoleId) {
        this.manageRoleForm.patchValue({ roleId: this.editRoleId });
        this.getManageRoleList();
      }
    }, err => {
    })

    this.resourceService.GetParentsResource().subscribe((data: any) => {
      this.resourceList = data;
    }, err => {
    })

    this.groupService.GetAllGroup().subscribe((data: any) => {
      this.groupList = data;
      for (let group of this.groupList) {
        const control = new FormControl(); // if first item set to true, else false
        (this.manageRoleForm.controls.group as FormArray).push(control);
      }
    }, err => {
    })
    this.resourceGroupService.GetAllResourceGroup().subscribe((data: any) => {
      this.resourceGroupList = data;
      for (let resGrp of this.resourceGroupList) {
        const control = new FormControl(); // if first item set to true, else false
        (this.manageRoleForm.controls.resourceGroupFormArray as FormArray).push(control);
        // console.log('resourceGroupList', this.resourceGroupList);
      }
    }, err => {
      console.log('resourceGroupList => err', err);
    });
    this.resourceService.GetParentsResource().subscribe((data: any) => {
      this.subResourceGroupList = data;
      for (let subResGrp of this.subResourceGroupList) {
        const control = new FormControl(); // if first item set to true, else false
        (this.manageRoleForm.controls.subResourceGroupFormArray as FormArray).push(control);
        // console.log('subResourceGroupList', this.subResourceGroupList);
      }
    }, err => {
      console.log('resourceGroupList => err', err);
    });

  }

  getSubResource() {
    this.resourceService.GetAllSubResourceById(this.manageRoleForm.value.resourceId).subscribe((data: any) => {
      this.subResourceList = data;
      this.manageRoleForm.controls.subResourceId = new FormArray([])
      this.subResourceIdList = [];
      for (let subRes of this.subResourceList) {
        let control: any = new FormControl(); // if first item set to true, else false
        (this.manageRoleForm.controls.subResourceId as FormArray).push(control);
      }

    }, err => {
    })
  }

  // loadManageRoleArr() {
  //   this.manageRoleService.GetAllManageRole().subscribe((data: any) => {
  //     this.manageRoleArr = data;
  //   }, err => {
  //   })
  // }

  getManageRoleList() {
    this.manageRoleService.GetAllManageRoleByRoleId(this.manageRoleForm.value.roleId).subscribe((data: any) => {
      this.manageRoleArr = data;
      console.log("manageRoleArr", this.manageRoleArr);
    }, err => {
      
     })

  }


  manageRole() {

    this.manageRoleForm = this.fb.group({
      id: [''],
      roleId: ['', Validators.required],
      resourceId: [''],
      subResourceId: new FormArray([]),
      group: new FormArray([]),
      groupId: [''],

      restrictionId: [''],

      resourceGroupFormArray: new FormArray([]),
      resourceGroupId: [''],


      subResourceGroupFormArray: new FormArray([]),
      subResourceGroupId: ['']

    })

  }

  test(subRes, id) {
    if (subRes.value) {
      this.subResourceIdList.push(id);
    } else {
      this.subResourceIdList.splice(this.subResourceIdList.indexOf(id), 1)
    }
  }
  resGroup(rGrp, id) {
    if (rGrp.value) {
      this.resourceGroupList.push(id);
    } else {
      this.resourceGroupList.splice(this.resourceGroupList.indexOf(id), 1);
    }
  }

  subResGroup(rGrp, id) {
    if (rGrp.value) {
      this.subResourceGroupList.push(id);
    } else {
      this.subResourceGroupList.splice(this.subResourceGroupList.indexOf(id), 1);
    }
  }

  submitForm(val) {

    let ManageRoleList = [];
    for (let controller in this.manageRoleForm.controls) {
      this.manageRoleForm.get(controller).markAsTouched();
    }
    if (this.manageRoleForm.invalid) {
      return;
    }
    const selectedGroupIds = this.manageRoleForm.value.group.map((v, i) => v ? this.groupList[i].id : null)
      .filter(v => v !== null);

    let selectedSubResources = this.manageRoleForm.value.subResourceId.map((v, i) => v ? this.subResourceList[i].id : null)
      .filter(v => v !== null);

    if (selectedSubResources && selectedSubResources.length > 0) { } else {
      selectedSubResources = this.subResourceIdList;
    }

    // Resource Group
    let selectedResGroup = this.manageRoleForm.value.resourceGroupFormArray.map((v, i) => v ? this.resourceGroupList[i].id : null)
    .filter(v => v !== null);

    //Resources From Resource Group
    // Resource Group
    let selectedSubResGroup = this.manageRoleForm.value.subResourceGroupFormArray.map((v, i) => v ? this.subResourceGroupList[i].id : null)
    .filter(v => v !== null);


    for (let id in selectedSubResources) {
      let manageRole: any = {};
      manageRole.groupId = "";
      manageRole.id = "";
      manageRole.resourceGroupId = "";
      manageRole.resourceId = selectedSubResources[id]
      manageRole.subResourceGroupId = "";
      manageRole.roleId = this.manageRoleForm.value.roleId
      ManageRoleList.push(manageRole);
    }


    for (let id in selectedGroupIds) {

      let manageRole: any = {};
      manageRole.groupId = selectedGroupIds[id];
      manageRole.id = "";
      manageRole.resourceId = "";
      manageRole.resourceGroupId = "";
      manageRole.subResourceGroupId = "";
      manageRole.roleId = this.manageRoleForm.value.roleId

      ManageRoleList.push(manageRole);
    }

    // Resource Group
    for (let id in selectedResGroup) {
      let manageRole: any = {};
      manageRole.resourceGroupId = selectedResGroup[id]
      manageRole.subResourceGroupId = "";
      manageRole.id = "";
      manageRole.groupId = "";
      manageRole.resourceId = "";
      manageRole.roleId = this.manageRoleForm.value.roleId;
      ManageRoleList.push(manageRole);
    }

    // Sub Resource get from Resource Group
    for (let id in selectedSubResGroup) {
      let manageRole: any = {};
      manageRole.subResourceGroupId = selectedSubResGroup[id];
      manageRole.resourceGroupId = "";
      manageRole.id = "";
      manageRole.groupId = "";
      manageRole.resourceId = "";
      manageRole.roleId = this.manageRoleForm.value.roleId
      ManageRoleList.push(manageRole);
    }

    if (ManageRoleList && ManageRoleList.length > 0) {
      this.manageRoleService.addListManageRole(ManageRoleList).subscribe((res: any) => {
        this.isSubmitted = true;
        if(res) {
      
          this.isSuccess = res.success;
          if (res.success) {
            this.successModal.showModal('SUCCESS', res.message, '');
          } else {
          
            this.errorModal.showModal('ERROR', res.error, '');
          }
        }
      }, err => {
        console.log(err);
      })
    }
  }

  getGroupDisable(id) {
    let result = false;
    for (let item of this.manageRoleArr) {
      if (item.groupID == id) {
        return true;
      }
    }
    return result;
  }


  getsubResourceDisable(id) {
    let result = false;
    for (let item of this.manageRoleArr) {
      if (item.resourceID == id) {
        return true;

      }
    }
    return result;
  }

  getResourceGroupDisable(id) {
    let result = false;
    for (let item of this.manageRoleArr) {
      if (item.resourceGroupID == id) {
        return true;
      }
    }
    return result;
  }
  getSubResourceGroupDisable(id) {
    let result = false;
    for (let item of this.manageRoleArr) {
      if (item.subResourceID == id) {
        return true;
      }
    }
    return result;
  }


  deleteFieldValue(data, i) {
    data.index = i;
    this.confirmModal.showModal("Delete Data", "Do you want to delete this data?", data);
  }

  modalConfirmation(event) {
    console.log(event);
    if (event) {
      this.manageRoleService.DeleteManageRole(event.id).subscribe((res: any) => {
        this.isSubmitted = true;
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
                this.editRoleId = this.manageRoleForm.value.roleId;
            this.manageRoleForm.reset();
            this.manageRoleForm.patchValue({ roleId: this.editRoleId });
            this.getManageRoleList();
            this.successModal.showModal('SUCCESS', res.message, '');
          } else {
            this.errorModal.showModal('ERROR', res.error, '');
          }
        }
        // if (res) {
        //   this.isSuccess = res.success;
        //   if (res.success) {
        //     this.editRoleId = this.manageRoleForm.value.roleId;
        //     this.manageRoleForm.reset();
        //     this.manageRoleForm.patchValue({ roleId: this.editRoleId });
        //     this.getManageRoleList();
        //     this._statusMsg = res.message;
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
    const data = this.manageRoleArr.slice();
    if (!sort.active || sort.direction == '') {
      this.manageRoleArr = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.manageRoleArr = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
          case 'roleName':
            return compare(firstValue.roleName, secondValue.roleName, isAsc);
        case 'resourceName':
          return compare(firstValue.resourceName, secondValue.resourceName, isAsc);
        case 'resourceURL':
          return compare(firstValue.resourceURL, secondValue.resourceURL, isAsc);
        case 'subResources':
          return compare(firstValue.subResources, secondValue.subResources, isAsc);
        case 'subResourceURL':
          return compare(firstValue.subResourceURL, secondValue.subResourceURL, isAsc);
          case 'resourceGroupName':
            return compare(firstValue.resourceGroupName, secondValue.resourceGroupName, isAsc);
        default:
          return 0;
      }
    });
  }

  modalSuccess($event: any) {
    this.getManageRoleList();
  }

}
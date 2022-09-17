import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RoleService } from '../../../acl/services/role.service';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { RolesService } from '../../services/roles.service';
import { ScreenService } from '../../services/screen.service';
import { SyncConfigurationService } from '../../services/sync-configuration.service';

@Component({
  selector: 'app-add-edit-sync-config',
  templateUrl: './add-edit-sync-config.component.html',
  styleUrls: ['./add-edit-sync-config.component.scss']
})
export class AddEditSyncConfigComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
syncConfigForm: FormGroup;

isSubmitted: boolean = false;
isSuccess: boolean = false;

mode: string = 'add';
editId: string;
roleList: any = [];
screenList: any = [];
role:any;
screen:any;
syncConfig:any;
zippingLevelList: string[] = ['All','State','Region'];
downloadInAndroidList: string[] = ['Yes','No'];
syncFromTsList: string[] = ['Yes','No'];
systemList: string[]= ['DRK','AGM','BOTH'];
  
constructor(private router: Router,
  public fb: FormBuilder,
 public syncConfigurationService: SyncConfigurationService,
 private actRoute: ActivatedRoute,public rolesService :RolesService,
 public screenService:ScreenService) { }

 ngOnInit() {
  
  this.getAllRoleList();
  this.getAllScreenList();
  this.addEditSyncConfig();


  this.editId = this.actRoute.snapshot.paramMap.get('id');

      if (this.editId) {
       
          this.mode = 'edit';
           this.syncConfigurationService.getSyncConfigById(this.editId).subscribe(data => {
            this.syncConfig=data;
          this.syncConfigForm.patchValue(this.syncConfig);
        });
       
      }

      // console.log('id ' + this.editId);
}


addEditSyncConfig() {
  this.syncConfigForm = this.fb.group({
    screenId: ['', Validators.required],
    roleId: ['', Validators.required],
    labelName: ['', Validators.required],
    url: ['', Validators.required],
    schemaName: ['', Validators.required],
    endpointName: ['', Validators.required],
    zippingLevel: ['', Validators.required],
    fieldMapping: ['', Validators.required],
    syncFromTs: ['',Validators.required],
    selectQuery: ['', Validators.required],
    downloadInAndroid: ['', Validators.required],
    apiSelectQuery: ['', Validators.required],
    // entityName: [''],
    conversionFunction: ['', Validators.required],
    system: ['', Validators.required],
    insertQuery: ['', Validators.required]
  })
}

submitForm() {
  // console.log("inside submitForm");
  for(let controller in this.syncConfigForm.controls){

    this.syncConfigForm.get(controller).markAsTouched();

  }

  if(this.syncConfigForm.invalid){
    // console.log("inside 1st if");
    return;
  }

  if (this.mode == 'add') {
    this.add();

 
  } else {
    this.update();
  }

  
}



add(){
  return this.syncConfigurationService.addSyncConfig(this.syncConfigForm.value).subscribe(res => {
  
    this.isSubmitted = true;
   
    if(res) {
      
      this.isSuccess = res.success;
      if (res.success) {
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
      
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  });
  
  }
  
  update(){ 
  return this.syncConfigurationService.updateSyncConfig(this.editId, this.syncConfigForm.value).subscribe( res => {
    this.isSubmitted = true;
    if (res) {
        this.isSuccess = res.success;
        if (res.success) {
            this.successModal.showModal('SUCCESS', res.message, '');
        } else {
            this.errorModal.showModal('ERROR', res.error, '');
        }
    }
  });
  
  }


//Role list
getAllRoleList(){
  return this.rolesService.getAllRoleList().subscribe((data: {}) => {
    this.roleList = data;
  })
  }

  // SceenList
  getAllScreenList(){
    return this.screenService.getAllScreenList().subscribe((data: {}) => {
      this.screenList = data;
    })
    }

    modalSuccess($event: any) {
      this.router.navigate(['/config/sync-configuration']);
    }

    trimValue(formControl) { 
      formControl.setValue(formControl.value.trim()); 
    }

}

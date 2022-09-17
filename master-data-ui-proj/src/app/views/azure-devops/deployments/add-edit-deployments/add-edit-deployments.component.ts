import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { DeploymentsService } from '../../services/deployments.service';
import { VirtualMachinesService } from '../../services/virtual-machines.service';

@Component({
  selector: 'app-add-edit-deployments',
  templateUrl: './add-edit-deployments.component.html',
  styleUrls: ['./add-edit-deployments.component.scss']
})
export class AddEditDeploymentsComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  deploymentForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  mode: string = 'add';
  editId: string;

  vmList: any =[];

  constructor(private router: Router,
       public fb: FormBuilder,
      public deploymentsService: DeploymentsService,
      private actRoute: ActivatedRoute, private virtualMachinesService: VirtualMachinesService) { }

  ngOnInit() {
    
    this.getActiveVirtualMachineList();
    this.addEditDeployment();

    this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
         
            this.mode = 'edit';
            this.deploymentsService.getDeploymentById(this.editId).subscribe(data => {
            this.deploymentForm.patchValue(data);
          });
        }

        console.log('id ' + this.editId);
  }

  addEditDeployment() {
    this.deploymentForm = this.fb.group({
      vmID: ['', Validators.required],
      applicationName: ['', Validators.required],
      applicationInternalPort: ['', Validators.required],
      applicationExternalPort: ['', Validators.required],
      dockerIP: ['', [Validators.required, Validators.pattern('(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])')]],
      commandScript: ['', Validators.required]
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.deploymentForm.controls){

      this.deploymentForm.get(controller).markAsTouched();

    }
  
    if(this.deploymentForm.invalid){
      console.log("inside 1st if");
      return;
    }

    if (this.mode == 'add') {
      this.add();
    } else {
      this.update();
    }

    
  }

  add(){

  return this.deploymentsService.addDeployment(this.deploymentForm.value).subscribe(res => {
  
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

  return this.deploymentsService.updateDeployment(this.editId, this.deploymentForm.value).subscribe( res => {
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

getActiveVirtualMachineList(){

  return this.virtualMachinesService.getActiveVirtualMachineList().subscribe((data: {}) => {
    this.vmList = data;
  })
}

trimValue(formControl) { 
  formControl.setValue(formControl.value.trim()); 
}

modalSuccess($event: any) {
  this.router.navigate(['/azure-devops/deployments']);
}

}

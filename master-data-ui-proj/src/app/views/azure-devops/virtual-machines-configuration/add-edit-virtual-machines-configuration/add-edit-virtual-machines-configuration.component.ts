import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { VirtualMachinesConfigurationService } from '../../services/virtual-machines-configuration.service';
import { VirtualMachinesService } from '../../services/virtual-machines.service';

@Component({
  selector: 'app-add-edit-virtual-machines-configuration',
  templateUrl: './add-edit-virtual-machines-configuration.component.html',
  styleUrls: ['./add-edit-virtual-machines-configuration.component.scss']
})
export class AddEditVirtualMachinesConfigurationComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  vmcForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  mode: string = 'add';
  editId: string;

  vmList: any =[];

  constructor(private router: Router,
       public fb: FormBuilder,
      public virtualMachinesConfigurationService: VirtualMachinesConfigurationService,
      private actRoute: ActivatedRoute, private virtualMachinesService: VirtualMachinesService) { }

  ngOnInit() {
    
    this.getActiveVirtualMachineList();
    this.addEditVMC();

    this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
         
            this.mode = 'edit';
            this.virtualMachinesConfigurationService.getVirtualMachinesConfigurationById(this.editId).subscribe(data => {
            this.vmcForm.patchValue(data);
          });
        }

        console.log('id ' + this.editId);
  }

  addEditVMC() {
    this.vmcForm = this.fb.group({
      vmID: ['', Validators.required],
      cpu: ['', Validators.required],
      ram: ['', Validators.required],
      internalDisk: ['', Validators.required],
      externalDisk: ['', Validators.required]
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.vmcForm.controls){

      this.vmcForm.get(controller).markAsTouched();

    }
  
    if(this.vmcForm.invalid){
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

  return this.virtualMachinesConfigurationService.addVirtualMachinesConfiguration(this.vmcForm.value).subscribe(res => {
  
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

  return this.virtualMachinesConfigurationService.updateVirtualMachinesConfiguration(this.editId, this.vmcForm.value).subscribe( res => {
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
  this.router.navigate(['/azure-devops/vmc']);
}

}

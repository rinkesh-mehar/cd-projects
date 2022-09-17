import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { VirtualMachinesService } from '../../services/virtual-machines.service';

@Component({
  selector: 'app-add-edit-virtual-machines',
  templateUrl: './add-edit-virtual-machines.component.html',
  styleUrls: ['./add-edit-virtual-machines.component.scss']
})
export class AddEditVirtualMachinesComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  vmForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  mode: string = 'add';
  editId: string;

  vmStatusList: any = [];


  constructor(private router: Router,
       public fb: FormBuilder, private virtualMachinesService: VirtualMachinesService, private actRoute: ActivatedRoute) { }

  ngOnInit() {
    
    this.addEditVirtualMachine();

    this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
         
            this.mode = 'edit';
            this.vmStatusList = ['Active', 'Deleted', 'Ready', 'Suspended'];
            this.virtualMachinesService.getVirtualMachineById(this.editId).subscribe(data => {
            this.vmForm.patchValue(data);
          });
        }

        console.log('id ' + this.editId);
  }

  addEditVirtualMachine() {
    this.vmForm = this.fb.group({
      name: ['', Validators.required],
      privateIP: ['', [Validators.required, Validators.pattern('(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])')]],
      publicIP: ['', [Validators.required, Validators.pattern('(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])')]],
      vnetName: ['', Validators.required],
      userName: ['', Validators.required],
      password: ['', Validators.required],
      status: ['Active', Validators.required]
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.vmForm.controls){

      this.vmForm.get(controller).markAsTouched();

    }
  
    if(this.vmForm.invalid){
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

  return this.virtualMachinesService.addVirtualMachines(this.vmForm.value).subscribe(res => {
  
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

  return this.virtualMachinesService.updateVirtualMachines(this.editId, this.vmForm.value).subscribe( res => {
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

trimValue(formControl) { 
  formControl.setValue(formControl.value.trim()); 
}

modalSuccess($event: any) {
  this.router.navigate(['/azure-devops/vm']);
}

}

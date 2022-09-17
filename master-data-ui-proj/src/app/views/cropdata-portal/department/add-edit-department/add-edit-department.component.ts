import { DepartmentService } from './../../services/department.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';

@Component({
  selector: 'app-add-edit-department',
  templateUrl: './add-edit-department.component.html',
  styleUrls: ['./add-edit-department.component.css']
})
export class AddEditDepartmentComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  departmentForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  mode: string = 'add';
  editId: string;

  deptStatus

  constructor(private router: Router,
       public fb: FormBuilder,
      public departmentService: DepartmentService,
      private actRoute: ActivatedRoute) { }

  ngOnInit() {

    this.addEditDepartment();

    this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
         
            this.mode = 'edit';
            this.departmentService.getDepartmentById(this.editId).subscribe(data => {
            this.departmentForm.patchValue(data);
          });
        }

        console.log('id ' + this.editId);
  }

  addEditDepartment() {
    this.departmentForm = this.fb.group({
      name: ['', Validators.required],
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.departmentForm.controls){

      this.departmentForm.get(controller).markAsTouched();

    }
  
    if(this.departmentForm.invalid){
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

  return this.departmentService.addDepartment(this.departmentForm.value).subscribe(res => {
  
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

  return this.departmentService.updateDepartment(this.editId, this.departmentForm.value).subscribe( res => {
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
  this.router.navigate(['/cropdata-portal/department-list']);
}

}

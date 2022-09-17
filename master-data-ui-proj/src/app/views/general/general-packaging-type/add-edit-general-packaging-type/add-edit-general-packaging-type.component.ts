import { GeneralPackagingTypeService } from './../../services/general-packaging-type.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';

@Component({
  selector: 'app-add-edit-general-packaging-type',
  templateUrl: './add-edit-general-packaging-type.component.html',
  styleUrls: ['./add-edit-general-packaging-type.component.scss']
})
export class AddEditGeneralPackagingTypeComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  packagingTypeForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  mode: string = 'add';
  editId: string;

  constructor(private router: Router,
       public fb: FormBuilder,
      public generalPackagingTypeService: GeneralPackagingTypeService,
      private actRoute: ActivatedRoute) { }

  ngOnInit() {

    this.addEditQualityParameter();

    this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
         
            this.mode = 'edit';
            this.generalPackagingTypeService.getPackagingTypeById(this.editId).subscribe(data => {
            this.packagingTypeForm.patchValue(data);
          });
        }

        console.log('id ' + this.editId);
  }

  addEditQualityParameter() {
    this.packagingTypeForm = this.fb.group({
      name: ['', Validators.required],
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.packagingTypeForm.controls){

      this.packagingTypeForm.get(controller).markAsTouched();

    }
  
    if(this.packagingTypeForm.invalid){
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

  return this.generalPackagingTypeService.addPackagingType(this.packagingTypeForm.value).subscribe(res => {
  
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

  return this.generalPackagingTypeService.updatePackagingType(this.editId, this.packagingTypeForm.value).subscribe( res => {
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
  this.router.navigate(['/general/packaging-type']);
}

}

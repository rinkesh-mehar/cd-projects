import { VasService } from './../../services/vas.service';
import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { fileSizeValidatorForDoc } from '../../../validators/fileSizeValidator.validator';

@Component({
  selector: 'app-add-edit-vas',
  templateUrl: './add-edit-vas.component.html',
  styleUrls: ['./add-edit-vas.component.scss']
})
export class AddEditVasComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  vasForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  mode: string = 'add';
  editId: string;

  uploadedLogo: any;
  isLogo: boolean = true;
  logo: any;

  constructor(private router: Router,
       public fb: FormBuilder,
      public vasService: VasService,
      private actRoute: ActivatedRoute) { }

  ngOnInit() {

    this.addEditVas();
    

    this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
            this.isLogo = false;
            this.mode = 'edit';
            this.vasForm.get('logoFile').clearValidators();
            this.vasForm.get('logoFile').updateValueAndValidity();
            this.vasService.getVasById(this.editId).subscribe(data => {
            this.logo = data.logo;
            this.vasForm.patchValue(data);
          });
        }

        console.log('id ' + this.editId);
  }

  addEditVas() {
    this.vasForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      logoFile: ['', Validators.required]
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.vasForm.controls){

      this.vasForm.get(controller).markAsTouched();

    }
  
    if(this.vasForm.invalid){
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

  return this.vasService.addVas(this.vasForm.value,this.uploadedLogo).subscribe(res => {
  
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

  return this.vasService.updateVas(this.editId, this.vasForm.value,this.uploadedLogo).subscribe( res => {
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

onLogoChange(element){
  if(this.mode === 'edit'){
    this.isLogo = true;
}
  let file: File = element.target.files[0];
  // console.log("Size : ", this.uploadedLogo.size);
  this.vasForm.get('logoFile').setValidators([Validators.required, fileSizeValidatorForDoc(element.target.files)]);
  this.vasForm.get('logoFile').updateValueAndValidity();
  this.uploadedLogo = file;
  // this.logo = this.uploadedLogo.name;
}

modalSuccess($event: any) {
  this.router.navigate(['/cropdata-portal/vas']);
}

}

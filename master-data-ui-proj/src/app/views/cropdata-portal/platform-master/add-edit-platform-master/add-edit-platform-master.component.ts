import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { fileSizeValidatorForDoc } from '../../../validators/fileSizeValidator.validator';
import { PlatformMasterService } from '../../services/platform-master.service';

@Component({
  selector: 'app-add-edit-platform-master',
  templateUrl: './add-edit-platform-master.component.html',
  styleUrls: ['./add-edit-platform-master.component.scss']
})
export class AddEditPlatformMasterComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  platformForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  mode: string = 'add';
  editId: string;

  platFormList: any =[];
  engineList: any =[];

  uploadedLogo: any;
  isLogo: boolean = true;
  logo: any;

  constructor(private router: Router,
       public fb: FormBuilder,
      public toolsService: PlatformMasterService,
      private actRoute: ActivatedRoute
      ) { }

  ngOnInit() {

    this.addEditTools();

    this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
            this.isLogo = false;
            this.mode = 'edit';
            this.platformForm.get('logoFile').clearValidators();
            this.platformForm.get('logoFile').updateValueAndValidity();
            this.toolsService.getPlatformById(this.editId).subscribe(data => {
            this.logo = data.logo;
            this.platformForm.patchValue(data);
          });
        }

        console.log('id ' + this.editId);
  }

  addEditTools() {
    this.platformForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      viewOrder: [''],
      logoFile: ['', Validators.required]
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.platformForm.controls){

      this.platformForm.get(controller).markAsTouched();

    }
  
    if(this.platformForm.invalid){
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

  return this.toolsService.addPlatform(this.platformForm.value,this.uploadedLogo).subscribe(res => {
  
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

  return this.toolsService.updatePlatform(this.editId, this.platformForm.value,this.uploadedLogo).subscribe( res => {
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
  this.platformForm.get('logoFile').setValidators([Validators.required, fileSizeValidatorForDoc(element.target.files)]);
  this.platformForm.get('logoFile').updateValueAndValidity();
  this.uploadedLogo = file;
  // this.logo = this.uploadedLogo.name;
}


modalSuccess($event: any) {
  this.router.navigate(['/cropdata-portal/platform']);
}

}

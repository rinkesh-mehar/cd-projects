import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { AgriQualityParameterService } from '../../service/agri-quality-parameter.service';

@Component({
  selector: 'app-add-edit-agri-quality-parameter',
  templateUrl: './add-edit-agri-quality-parameter.component.html',
  styleUrls: ['./add-edit-agri-quality-parameter.component.scss']
})
export class AddEditAgriQualityParameterComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  qualityParamterForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  mode: string = 'add';
  editId: string;

  constructor(private router: Router,
       public fb: FormBuilder,
      public agriQualityParameterService: AgriQualityParameterService,
      private actRoute: ActivatedRoute) { }

  ngOnInit() {

    this.addEditQualityParameter();

    this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
         
            this.mode = 'edit';
            this.agriQualityParameterService.getQualityParameterById(this.editId).subscribe(data => {
            this.qualityParamterForm.patchValue(data);
          });
        }

        console.log('id ' + this.editId);
  }

  addEditQualityParameter() {
    this.qualityParamterForm = this.fb.group({
      name: ['', Validators.required],
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.qualityParamterForm.controls){

      this.qualityParamterForm.get(controller).markAsTouched();

    }
  
    if(this.qualityParamterForm.invalid){
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

  return this.agriQualityParameterService.addQualityParameter(this.qualityParamterForm.value).subscribe(res => {
  
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

  return this.agriQualityParameterService.updateQualityParameter(this.editId, this.qualityParamterForm.value).subscribe( res => {
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
  this.router.navigate(['/commodity/quality-parameter']);
}

}

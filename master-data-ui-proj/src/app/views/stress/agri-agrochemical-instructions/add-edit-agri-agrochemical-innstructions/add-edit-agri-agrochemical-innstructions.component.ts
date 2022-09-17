import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { AgriAgrochemicalInstructionService } from '../../services/agri-agrochemical-instructions';

@Component({
  selector: 'app-add-agri-edit-agrochemical-innstructions',
  templateUrl: './add-edit-agri-agrochemical-innstructions.component.html',
  styleUrls: ['./add-edit-agri-agrochemical-innstructions.component.scss']
})
export class AddEditAgriAgrochemicalInnstructionsComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  agrochemicalInstructionForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  mode: string = 'add';
  editId: string;

  deptStatus

  constructor(private router: Router,
       public fb: FormBuilder,
      public agriAgrochemicalInstructionService: AgriAgrochemicalInstructionService,
      private actRoute: ActivatedRoute) { }

  ngOnInit() {

    this.addEditAgrochemicalInstructions();

    this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
         
            this.mode = 'edit';
            this.agriAgrochemicalInstructionService.getAgrochemicalInstructionsById(this.editId).subscribe(data => {
            this.agrochemicalInstructionForm.patchValue(data);
          });
        }

        console.log('id ' + this.editId);
  }

  addEditAgrochemicalInstructions() {
    this.agrochemicalInstructionForm = this.fb.group({
      name: ['', Validators.required],
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.agrochemicalInstructionForm.controls){

      this.agrochemicalInstructionForm.get(controller).markAsTouched();

    }
  
    if(this.agrochemicalInstructionForm.invalid){
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

  return this.agriAgrochemicalInstructionService.addAgrochemicalInstructions(this.agrochemicalInstructionForm.value).subscribe(res => {
  
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

  return this.agriAgrochemicalInstructionService.updateAgrochemicalInstructions(this.editId, this.agrochemicalInstructionForm.value).subscribe( res => {
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
  this.router.navigate(['/stress/agrochemical-instructions']);
}

}

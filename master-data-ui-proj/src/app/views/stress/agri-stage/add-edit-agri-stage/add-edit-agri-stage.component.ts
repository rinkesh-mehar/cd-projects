import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { AgriStageService } from '../../services/agri-stage.service';

@Component({
  selector: 'app-add-edit-agri-stage',
  templateUrl: './add-edit-agri-stage.component.html',
  styleUrls: ['./add-edit-agri-stage.component.scss']
})
export class AddEditAgriStageComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  recommendationForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  mode: string = 'add';
  editId: string;

  deptStatus

  constructor(private router: Router,
       public fb: FormBuilder,
      public agriStageService: AgriStageService,
      private actRoute: ActivatedRoute) { }

  ngOnInit() {

    this.addEditStage();

    this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
         
            this.mode = 'edit';
            this.agriStageService.getStageById(this.editId).subscribe(data => {
            this.recommendationForm.patchValue(data);
          });
        }

        console.log('id ' + this.editId);
  }

  addEditStage() {
    this.recommendationForm = this.fb.group({
      name: ['', Validators.required],
      description: ['']
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.recommendationForm.controls){

      this.recommendationForm.get(controller).markAsTouched();

    }
  
    if(this.recommendationForm.invalid){
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

  return this.agriStageService.addStage(this.recommendationForm.value).subscribe(res => {
  
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

  return this.agriStageService.updateStage(this.editId, this.recommendationForm.value).subscribe( res => {
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
  this.router.navigate(['/stress/stage']);
}

}

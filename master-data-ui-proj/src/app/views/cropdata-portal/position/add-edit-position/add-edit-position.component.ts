import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { PositionService } from '../../services/position.service';

@Component({
  selector: 'app-add-edit-position',
  templateUrl: './add-edit-position.component.html',
  styleUrls: ['./add-edit-position.component.css']
})
export class AddEditPositionComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  positionForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  mode: string = 'add';
  editId: string;

  constructor(private router: Router,
       public fb: FormBuilder,
      public positionService: PositionService,
      private actRoute: ActivatedRoute) { }

  ngOnInit() {

    this.addEditPosition();

    this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
         
            this.mode = 'edit';
            this.positionService.getPositionById(this.editId).subscribe(data => {
            this.positionForm.patchValue(data);
          });
        }

        console.log('id ' + this.editId);
  }

  addEditPosition() {
    this.positionForm = this.fb.group({
      name: ['', Validators.required],
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.positionForm.controls){

      this.positionForm.get(controller).markAsTouched();

    }
  
    if(this.positionForm.invalid){
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

  return this.positionService.addPosition(this.positionForm.value).subscribe(res => {
  
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

  return this.positionService.updatePosition(this.editId, this.positionForm.value).subscribe( res => {
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
  this.router.navigate(['/cropdata-portal/position-list']);
}

}

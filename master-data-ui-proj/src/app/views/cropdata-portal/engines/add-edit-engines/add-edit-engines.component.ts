import { EnginesService } from './../../services/engines.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { OppotunitiesService } from '../../services/oppotunities.service';

@Component({
  selector: 'app-add-edit-engines',
  templateUrl: './add-edit-engines.component.html',
  styleUrls: ['./add-edit-engines.component.scss']
})
export class AddEditEnginesComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  engineForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  mode: string = 'add';
  editId: string;

  platFormList: any =[];

  constructor(private router: Router,
       public fb: FormBuilder,
      public enginesService: EnginesService,
      private opportunitiesService: OppotunitiesService,
      private actRoute: ActivatedRoute) { }

  ngOnInit() {

    this.addEditEngines();
    this.getPlatForm();

    this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
         
            this.mode = 'edit';
            this.enginesService.getEngineById(this.editId).subscribe(data => {
            this.engineForm.patchValue(data);
          });
        }

        console.log('id ' + this.editId);
  }

  addEditEngines() {
    this.engineForm = this.fb.group({
      name: ['', Validators.required],
      platformID: ['', Validators.required]
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.engineForm.controls){

      this.engineForm.get(controller).markAsTouched();

    }
  
    if(this.engineForm.invalid){
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

  return this.enginesService.addEngine(this.engineForm.value).subscribe(res => {
  
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

  return this.enginesService.updateEngine(this.editId, this.engineForm.value).subscribe( res => {
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

getPlatForm() {
  return this.opportunitiesService.getPlatFormList().subscribe((data: {}) => {
      this.platFormList = data;

  });
}

trimValue(formControl) { 
  formControl.setValue(formControl.value.trim()); 
}

modalSuccess($event: any) {
  this.router.navigate(['/cropdata-portal/engines']);
}

}

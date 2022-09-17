import { IndicesService } from './../../services/indices.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { OppotunitiesService } from '../../services/oppotunities.service';

@Component({
  selector: 'app-add-edit-indices',
  templateUrl: './add-edit-indices.component.html',
  styleUrls: ['./add-edit-indices.component.scss']
})
export class AddEditIndicesComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  indicesForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  mode: string = 'add';
  editId: string;

  platFormList: any =[];

  constructor(private router: Router,
       public fb: FormBuilder,
       private indicesService : IndicesService,
      private opportunitiesService: OppotunitiesService,
      private actRoute: ActivatedRoute) { }

  ngOnInit() {

    this.addEditIndices();
    this.getPlatForm();

    this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
         
            this.mode = 'edit';
            this.indicesService.getIndicesById(this.editId).subscribe(data => {
            this.indicesForm.patchValue(data);
          });
        }

        console.log('id ' + this.editId);
  }

  addEditIndices() {
    this.indicesForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      platformID: ['']
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.indicesForm.controls){

      this.indicesForm.get(controller).markAsTouched();

    }
  
    if(this.indicesForm.invalid){
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

  return this.indicesService.addIndices(this.indicesForm.value).subscribe(res => {
  
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

  return this.indicesService.updateIndices(this.editId, this.indicesForm.value).subscribe( res => {
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
  this.router.navigate(['/cropdata-portal/indices']);
}

}

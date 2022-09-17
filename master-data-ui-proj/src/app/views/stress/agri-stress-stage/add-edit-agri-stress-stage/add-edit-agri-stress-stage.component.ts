import { AgriStageService } from '../../services/agri-stage.service';
import { AgriStressServiceService } from './../../../agri/services/agri-stress-service.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { AgriStressStageService } from '../../services/agri-stress-stage.service';

@Component({
  selector: 'app-add-edit-agri-stress-stage',
  templateUrl: './add-edit-agri-stress-stage.component.html',
  styleUrls: ['./add-edit-agri-stress-stage.component.scss']
})
export class AddEditAgriStressStageComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  stressStageForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  mode: string = 'add';
  editId: string;

  stressList: any = [];
  stageList: any = [];

  constructor(private router: Router,
       public fb: FormBuilder,
      public agriStressStageService: AgriStressStageService,
      private actRoute: ActivatedRoute, private agriStressServiceService: AgriStressServiceService,
      private agriStageService: AgriStageService) { }

  ngOnInit() {
    
    this.getStressList();
    this.getStageList();
    this.addEditStressStage();

    this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
         
            this.mode = 'edit';
            this.agriStressStageService.getStressStageById(this.editId).subscribe(data => {
            this.stressStageForm.patchValue(data);
          });
        }

        console.log('id ' + this.editId);
  }

  addEditStressStage() {
    this.stressStageForm = this.fb.group({
      stressId: ['', Validators.required],
      stageId: ['', Validators.required],
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.stressStageForm.controls){

      this.stressStageForm.get(controller).markAsTouched();

    }
  
    if(this.stressStageForm.invalid){
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

  return this.agriStressStageService.addStressStage(this.stressStageForm.value).subscribe(res => {
  
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

  return this.agriStressStageService.updateStressStage(this.editId, this.stressStageForm.value).subscribe( res => {
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

getStressList(){
  return this.agriStressServiceService.GetAllStress().subscribe(data => {
   this.stressList = data;
  });
}

getStageList(){
  return this.agriStageService.getStageList().subscribe(data => {
   this.stageList = data;
  });
}

trimValue(formControl) { 
  formControl.setValue(formControl.value.trim()); 
}

modalSuccess($event: any) {
  this.router.navigate(['/stress/stress-stage']);
}

}

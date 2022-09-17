import { EnginesService } from './../../services/engines.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { ToolsService } from '../../services/tools.service';
import { OppotunitiesService } from '../../services/oppotunities.service';
import { fileSizeValidatorForDoc } from '../../../validators/fileSizeValidator.validator';

@Component({
  selector: 'app-add-edit-tools',
  templateUrl: './add-edit-tools.component.html',
  styleUrls: ['./add-edit-tools.component.scss']
})
export class AddEditToolsComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  toolsForm: FormGroup;

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
      public toolsService: ToolsService,
      private enginesService: EnginesService,
      private actRoute: ActivatedRoute,
      private opportunitiesService: OppotunitiesService) { }

  ngOnInit() {

    this.addEditTools();
    this.getEngine();
    this.getPlatForm();
    

    this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
            this.isLogo = false;
            this.mode = 'edit';
            this.toolsForm.get('logoFile').clearValidators();
            this.toolsForm.get('logoFile').updateValueAndValidity();
            this.toolsService.getToolById(this.editId).subscribe(data => {
            this.logo = data.logo;
            this.toolsForm.patchValue(data);
          });
        }

        console.log('id ' + this.editId);
  }

  addEditTools() {
    this.toolsForm = this.fb.group({
      name: ['', Validators.required],
      engineID: [''],
      platformID: ['', Validators.required],
      description: ['', Validators.required],
      logoFile: ['', Validators.required]
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.toolsForm.controls){

      this.toolsForm.get(controller).markAsTouched();

    }
  
    if(this.toolsForm.invalid){
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

  return this.toolsService.addTool(this.toolsForm.value,this.uploadedLogo).subscribe(res => {
  
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

  return this.toolsService.updateTool(this.editId, this.toolsForm.value,this.uploadedLogo).subscribe( res => {
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

getEngine() {
  return this.enginesService.getEngineList().subscribe((data: {}) => {
      this.engineList = data;

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
  this.toolsForm.get('logoFile').setValidators([Validators.required, fileSizeValidatorForDoc(element.target.files)]);
  this.toolsForm.get('logoFile').updateValueAndValidity();
  this.uploadedLogo = file;
  // this.logo = this.uploadedLogo.name;
}

onChangeEngine(element){
  let enginrId = element.target.value;
  // console.log(element.target.value);
  let count = 0;
  for(var engine of this.engineList){
    if(enginrId == engine.id){
      this.toolsForm.patchValue({platformID:engine.platformID});
      count++;
    }
  }
  if(count==0){
    this.toolsForm.patchValue({platformID:''});
  }
}

modalSuccess($event: any) {
  this.router.navigate(['/cropdata-portal/tools']);
}

}

import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { AgriCommodityGroupService } from '../../service/agri-commodity-group.service';

@Component({
  selector: 'app-add-edit-agri-commodity-group',
  templateUrl: './add-edit-agri-commodity-group.component.html',
  styleUrls: ['./add-edit-agri-commodity-group.component.scss']
})
export class AddEditAgriCommodityGroupComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  commodityGroupForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  mode: string = 'add';
  editId: string;

  deptStatus

  constructor(private router: Router,
       public fb: FormBuilder,
      public agriCommodityGroupService: AgriCommodityGroupService,
      private actRoute: ActivatedRoute) { }

  ngOnInit() {

    this.addEditRecommendation();

    this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
         
            this.mode = 'edit';
            this.agriCommodityGroupService.getCommodityGroupById(this.editId).subscribe(data => {
            this.commodityGroupForm.patchValue(data);
          });
        }

        console.log('id ' + this.editId);
  }

  addEditRecommendation() {
    this.commodityGroupForm = this.fb.group({
      name: ['', Validators.required],
      viewOrder: ['', Validators.required],
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.commodityGroupForm.controls){

      this.commodityGroupForm.get(controller).markAsTouched();

    }
  
    if(this.commodityGroupForm.invalid){
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

  return this.agriCommodityGroupService.addCommodityGroup(this.commodityGroupForm.value).subscribe(res => {
  
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

  return this.agriCommodityGroupService.updateCommodityGroup(this.editId, this.commodityGroupForm.value).subscribe( res => {
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
  this.router.navigate(['/commodity/commodity-group']);
}
}

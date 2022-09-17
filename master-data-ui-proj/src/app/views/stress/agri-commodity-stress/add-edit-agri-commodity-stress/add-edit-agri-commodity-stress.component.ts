import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AgriCommodityService } from '../../../agri/services/agri-commodity.service';
import { AgriStressServiceService } from '../../../agri/services/agri-stress-service.service';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { AgirCommodityStressService } from '../../services/agir-commodity-stress.service';

@Component({
  selector: 'app-add-edit-agri-commodity-stress',
  templateUrl: './add-edit-agri-commodity-stress.component.html',
  styleUrls: ['./add-edit-agri-commodity-stress.component.scss']
})
export class AddEditAgriCommodityStressComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  commodityStressForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  mode: string = 'add';
  editId: string;

  commodityList: any = [];
  stressList: any = [];

  constructor(private router: Router,
       public fb: FormBuilder,
      public agirCommodityStressService: AgirCommodityStressService,
      private actRoute: ActivatedRoute, private agriStressServiceService: AgriStressServiceService,
      public commodityService: AgriCommodityService) { }

  ngOnInit() {
    
    this.getCommodityList();
    this.getStressList();
    this.addEditCommoditystress();

    this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
         
            this.mode = 'edit';
            this.agirCommodityStressService.getCommodityStressById(this.editId).subscribe(data => {
            this.commodityStressForm.patchValue(data);
          });
        }

        console.log('id ' + this.editId);
  }

  addEditCommoditystress() {
    this.commodityStressForm = this.fb.group({
      commodityId: ['', Validators.required],
      stressId: ['', Validators.required],
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.commodityStressForm.controls){

      this.commodityStressForm.get(controller).markAsTouched();

    }
  
    if(this.commodityStressForm.invalid){
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

  return this.agirCommodityStressService.addCommodityStress(this.commodityStressForm.value).subscribe(res => {
  
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

  return this.agirCommodityStressService.updateCommodityStress(this.editId, this.commodityStressForm.value).subscribe( res => {
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

getCommodityList(){
  return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
    this.commodityList = data;
  })
}

trimValue(formControl) { 
  formControl.setValue(formControl.value.trim()); 
}

modalSuccess($event: any) {
  this.router.navigate(['/stress/commodity-stress']);
}

}

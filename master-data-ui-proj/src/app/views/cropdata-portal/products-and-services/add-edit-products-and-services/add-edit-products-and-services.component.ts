import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { fileSizeValidatorForDoc } from '../../../validators/fileSizeValidator.validator';
import { OppotunitiesService } from '../../services/oppotunities.service';
import { ProductsAndServicesService } from '../../services/products-and-services.service';

@Component({
  selector: 'app-add-edit-products-and-services',
  templateUrl: './add-edit-products-and-services.component.html',
  styleUrls: ['./add-edit-products-and-services.component.scss']
})
export class AddEditProductsAndServicesComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  productsAndServicesForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  mode: string = 'add';
  editId: string;

  platFormList: any =[];

  uploadedLogo: any;
  isLogo: boolean = true;
  logo: any;

  constructor(private router: Router,
       public fb: FormBuilder,
      public productsAndServicesService: ProductsAndServicesService,
      private actRoute: ActivatedRoute,
      private opportunitiesService: OppotunitiesService) { }

  ngOnInit() {

    this.addEditProductsAndServices();
    this.getPlatForm();
    

    this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
            this.isLogo = false;
            this.mode = 'edit';
            this.productsAndServicesForm.get('logoFile').clearValidators();
            this.productsAndServicesForm.get('logoFile').updateValueAndValidity();
            this.productsAndServicesService.getProductsAndServicesById(this.editId).subscribe(data => {
            this.logo = data.logo;
            this.productsAndServicesForm.patchValue(data);
          });
        }

        console.log('id ' + this.editId);
  }

  addEditProductsAndServices() {
    this.productsAndServicesForm = this.fb.group({
      name: ['', Validators.required],
      platformID: [''],
      description: ['', Validators.required],
      logoFile: ['', Validators.required]
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.productsAndServicesForm.controls){

      this.productsAndServicesForm.get(controller).markAsTouched();

    }
  
    if(this.productsAndServicesForm.invalid){
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

  return this.productsAndServicesService.addProductsAndServices(this.productsAndServicesForm.value,this.uploadedLogo).subscribe(res => {
  
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

  return this.productsAndServicesService.updateProductsAndServices(this.editId, this.productsAndServicesForm.value,this.uploadedLogo).subscribe( res => {
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

onLogoChange(element){
  if(this.mode === 'edit'){
    this.isLogo = true;
}
  let file: File = element.target.files[0];
  // console.log("Size : ", this.uploadedLogo.size);
  this.productsAndServicesForm.get('logoFile').setValidators([Validators.required, fileSizeValidatorForDoc(element.target.files)]);
  this.productsAndServicesForm.get('logoFile').updateValueAndValidity();
  this.uploadedLogo = file;
  // this.logo = this.uploadedLogo.name;
}

modalSuccess($event: any) {
  this.router.navigate(['/cropdata-portal/products-and-servics']);
}

}

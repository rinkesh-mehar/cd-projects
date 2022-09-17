import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FarmerFarmOwnershipTypeService } from '../services/farmer-farm-ownership-type.service';
import { ApiUtilService } from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-add-farmer-farm-owenership-type',
  templateUrl: './add-farmer-farm-owenership-type.component.html',
  styleUrls: ['./add-farmer-farm-owenership-type.component.scss']
})
export class AddFarmerFarmOwenershipTypeComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  
  farmOwenershipTypeForm: FormGroup;
  farmOwenershipTypeArr: any = [];

  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.addFarmOwenershipType()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public farmerFarmOwenershipTypeService: FarmerFarmOwnershipTypeService,
    public apiUtilService: ApiUtilService
 
  ){ }

  addFarmOwenershipType() {
    this.farmOwenershipTypeForm = this.fb.group({
      name: ['',Validators.required],
      status: ['Inactive']
      
    })
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {

    for(let controller in this.farmOwenershipTypeForm.controls){
      this.farmOwenershipTypeForm.get(controller).markAsTouched();
    }

    if(this.farmOwenershipTypeForm.invalid){
      return;
    }


    this.farmerFarmOwenershipTypeService.CreateFarmOwnershipType(this.farmOwenershipTypeForm.value).subscribe(res => {
      this.isSubmitted = true;
     
      if(res){
        this.isSuccess = res.success;
        if(res.success){
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

 downloadExcelFormat(){
    var tableName = "farmer_farm_ownership_type";
    this.apiUtilService.downloadExcelFormat(tableName);
  }//downloadExcelFormat


  uploadExcel(element) {
    var file: File = element.target.files[0];
    this.fileUpload = file;
  }

  submitExcelForm() {
    this.apiUtilService.uploadExcelFile(this.fileUpload).subscribe(res => {
      this.isSubmittedBulk = true;
     
      if(res){
        this.isSuccessBulk = res.success;
        if(res.success){
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
}

  modalSuccess($event: any) {
   this.router.navigate(['/farmer/farm-ownership-type']);
  }
}

import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FarmerInsuranceCompanyService } from '../services/farmer-insurance-company.service';
import { FarmerInsuranceService } from '../services/farmer-insurance.service';
import { ApiUtilService } from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-add-farmer-insurance-company',
  templateUrl: './add-farmer-insurance-company.component.html',
  styleUrls: ['./add-farmer-insurance-company.component.scss']
})
export class AddFarmerInsuranceCompanyComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  
  insuranceComapanyForm: FormGroup;
  insuranceCompanyArr: any = [];
  insuranceList : any;

  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.addInsuranceCompany();
    this.loadAllInsurance();
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public farmerInsuranceCompanyService: FarmerInsuranceCompanyService,
    private farmerInsuranceService : FarmerInsuranceService,
    public apiUtilService: ApiUtilService

 
  ){ }

  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  addInsuranceCompany() {
    this.insuranceComapanyForm = this.fb.group({
      name: ['',Validators.required],
      insuranceTypeId: ['',Validators.required],
      status: ['Inactive']
      
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {

    for(let controller in this.insuranceComapanyForm.controls){
      this.insuranceComapanyForm.get(controller).markAsTouched();
    }

    if(this.insuranceComapanyForm.invalid){
      return;
    }

    if (this.insuranceComapanyForm.get('insuranceTypeId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select InsuranceTypeId', '');
      return;
    }

    


    this.farmerInsuranceCompanyService.CreateInsuranceCompany(this.insuranceComapanyForm.value).subscribe(res => {
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

   //Insurance list
   loadAllInsurance() {
    return this.farmerInsuranceService.GetAllInsurance().subscribe((data: {}) => {
      this.insuranceList = data;
    })
  }

  downloadExcelFormat(){
    var tableName = "farmer_insurance_company";
    this.apiUtilService.downloadExcelFormat(tableName);
  }//downloadExcelFormat


  uploadExcel(element) {
    var file: File = element.target.files[0];
    this.fileUpload = file;
  }

  submitExcelForm() {
    this.apiUtilService.uploadExcelFile(this.fileUpload).subscribe(res => {
      this.isSubmittedBulk = true;
      if (res) {
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
    this.router.navigate(['/farmer/insurance-company']);
  }
}

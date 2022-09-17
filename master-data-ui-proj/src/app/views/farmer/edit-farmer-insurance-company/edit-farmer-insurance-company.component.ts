import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { FarmerInsuranceCompanyService } from '../services/farmer-insurance-company.service';
import { FarmerInsuranceService } from '../services/farmer-insurance.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
@Component({
  selector: 'app-edit-farmer-insurance-company',
  templateUrl: './edit-farmer-insurance-company.component.html',
  styleUrls: ['./edit-farmer-insurance-company.component.scss']
})
export class EditFarmerInsuranceCompanyComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;


  InsuranceCompanyList: any = [];
  updateInsuranceCompanyForm: FormGroup;
  insuranceTypeList : any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  ngOnInit() {
    this.updateForm();
    this.loadAllInsurance();
  }

  constructor(
    private actRoute: ActivatedRoute,
    public farmerInsuranceCompanyService: FarmerInsuranceCompanyService,
    private farmerInsuranceService : FarmerInsuranceService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) {
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.farmerInsuranceCompanyService.GetInsuranceCompany(id).subscribe((data) => {
      this.updateInsuranceCompanyForm = this.fb.group({
        name: [data.name, Validators.required],
        insuranceTypeId: [data.insuranceTypeId, Validators.required],
        status : [data.status]
      })
    })
  }

  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  

  
  updateForm() {
    this.updateInsuranceCompanyForm = this.fb.group({
      name: ['', Validators.required],
      insuranceTypeId: ['', Validators.required],
      status: ['Inactive']
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {

    for (let controller in this.updateInsuranceCompanyForm.controls) {
      this.updateInsuranceCompanyForm.get(controller).markAsTouched();
    }

    if (this.updateInsuranceCompanyForm.invalid) {
      return;
    }

    if (this.updateInsuranceCompanyForm.get('insuranceTypeId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select InsuranceTypeId', '');

      return;
    }


    var id = this.actRoute.snapshot.paramMap.get('id');
    this.farmerInsuranceCompanyService.UpdateInsuranceCompany(id, this.updateInsuranceCompanyForm.value).subscribe(res => {
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

    //Insurance list
    loadAllInsurance() {
      return this.farmerInsuranceService.GetAllInsurance().subscribe((data: {}) => {
        this.insuranceTypeList = data;
      })
    }

    modalSuccess($event: any) {
        this.router.navigate(['/farmer/insurance-company']);
    }
}

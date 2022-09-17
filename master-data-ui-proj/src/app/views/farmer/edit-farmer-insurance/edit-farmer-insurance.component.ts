import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { FarmerInsuranceService } from '../services/farmer-insurance.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';



@Component({
  selector: 'app-edit-farmer-insurance',
  templateUrl: './edit-farmer-insurance.component.html',
  styleUrls: ['./edit-farmer-insurance.component.scss']
})
export class EditFarmerInsuranceComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  InsuranceList: any = [];
  updateInsuranceForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.updateForm()
  }

  constructor(
    private actRoute: ActivatedRoute,
    public farmerInsuranceService: FarmerInsuranceService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.farmerInsuranceService.GetInsurance(id).subscribe((data) => {
      this.updateInsuranceForm = this.fb.group({
        name: [data.name,Validators.required],
        status: [data.status]
        })
    })
  }

  updateForm(){
    this.updateInsuranceForm = this.fb.group({
      name: ['',Validators.required],
      status: ['Inactive']
     
    })    
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  
  submitForm(){ 
    
    for(let controller in this.updateInsuranceForm.controls){
      this.updateInsuranceForm.get(controller).markAsTouched();
    }

    if(this.updateInsuranceForm.invalid){
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.farmerInsuranceService.UpdateInsurance(id, this.updateInsuranceForm.value).subscribe(res => {
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

    modalSuccess($event: any) {
        this.router.navigate(['/farmer/insurance-type']);
    }
}

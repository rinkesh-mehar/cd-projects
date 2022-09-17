import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { FarmerLoanPurposeService } from '../services/farmer-loan-purpose.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-farmer-loan-purpose',
  templateUrl: './edit-farmer-loan-purpose.component.html',
  styleUrls: ['./edit-farmer-loan-purpose.component.scss']
})
export class EditFarmerLoanPurposeComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  
  LoanPurposeList: any = [];
  updateLoanPurposeForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  
  ngOnInit() {
    this.updateForm()
  }

  constructor(
    private actRoute: ActivatedRoute,
    public farmerLoanPurposeService: FarmerLoanPurposeService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.farmerLoanPurposeService.GetLoanPurpose(id).subscribe((data) => {
      this.updateLoanPurposeForm = this.fb.group({
        name: [data.name,Validators.required],
        status:[data.status]
        })
    })
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  updateForm(){
    this.updateLoanPurposeForm = this.fb.group({
      name: ['',Validators.required],
      status: ['Inactive']
     
    })    
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm(){ 
    
    for(let controller in this.updateLoanPurposeForm.controls){
      this.updateLoanPurposeForm.get(controller).markAsTouched();
    }

    if(this.updateLoanPurposeForm.invalid){
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.farmerLoanPurposeService.UpdateLoanPurpose(id, this.updateLoanPurposeForm.value).subscribe(res => {
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

  modalSuccess($event: any) {
    this.router.navigate(['/farmer/loan-purpose']);
  }
}

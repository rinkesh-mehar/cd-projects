import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { FarmerLoanSourceService } from '../services/farmer-loan-source.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-farmer-loan-source',
  templateUrl: './edit-farmer-loan-source.component.html',
  styleUrls: ['./edit-farmer-loan-source.component.scss']
})
export class EditFarmerLoanSourceComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  LoanSourceList: any = [];
  updateLoanSourceForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  
  ngOnInit() {
    this.updateForm()
  }

  constructor(
    private actRoute: ActivatedRoute,    
    public farmerLoanSourceService: FarmerLoanSourceService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.farmerLoanSourceService.GetLoanSource(id).subscribe((data) => {
      this.updateLoanSourceForm = this.fb.group({
        name: [data.name,Validators.required],
        status: [data.status]
        })
    })
  }

  updateForm(){
    this.updateLoanSourceForm = this.fb.group({
      name: ['',Validators.required],
     status: []
    })    
  }
  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  submitForm(){ 
    
    for(let controller in this.updateLoanSourceForm.controls){
      this.updateLoanSourceForm.get(controller).markAsTouched();
    }

    if(this.updateLoanSourceForm.invalid){
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.farmerLoanSourceService.UpdateLoanSource(id, this.updateLoanSourceForm.value).subscribe(res => {
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
        this.router.navigate(['/farmer/loan-source']);
    }
}

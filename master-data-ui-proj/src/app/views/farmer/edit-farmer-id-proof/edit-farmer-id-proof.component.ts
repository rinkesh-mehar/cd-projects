import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { FarmerIdProofService } from '../services/farmer-id-proof.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-farmer-id-proof',
  templateUrl: './edit-farmer-id-proof.component.html',
  styleUrls: ['./edit-farmer-id-proof.component.scss']
})
export class EditFarmerIdProofComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  EducationList: any = [];
  updateIdProofForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.updateForm()
  }

  constructor(
    private actRoute: ActivatedRoute,    
    public farmerIdProofService: FarmerIdProofService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.farmerIdProofService.GetIdProof(id).subscribe((data) => {
      this.updateIdProofForm = this.fb.group({
        name: [data.name,Validators.required],
        status: [data.status]
        })
    })
  }

  updateForm(){
    this.updateIdProofForm = this.fb.group({
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
    
    for(let controller in this.updateIdProofForm.controls){
      this.updateIdProofForm.get(controller).markAsTouched();
    }

    if(this.updateIdProofForm.invalid){
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.farmerIdProofService.UpdateIdProof(id, this.updateIdProofForm.value).subscribe(res => {
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
        this.router.navigate(['/farmer/idproof']);
    }
}

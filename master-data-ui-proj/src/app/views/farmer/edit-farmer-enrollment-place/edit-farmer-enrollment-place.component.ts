import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

import { FarmerEnrollmentPlaceService } from '../services/farmer-enrollment-place.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-farmer-enrollment-place',
  templateUrl: './edit-farmer-enrollment-place.component.html',
  styleUrls: ['./edit-farmer-enrollment-place.component.scss']
})
export class EditFarmerEnrollmentPlaceComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  EnrollmentPlaceList: any = [];
  updateEnrollmentPlaceForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.updateForm()
  }

  constructor(
    private actRoute: ActivatedRoute,
    public farmerEnrollmentPlaceService: FarmerEnrollmentPlaceService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.farmerEnrollmentPlaceService.GetEnrollmentPlace(id).subscribe((data) => {
      this.updateEnrollmentPlaceForm = this.fb.group({
        name: [data.name,Validators.required],
        status: [data.status]
        })
    })
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  
  updateForm(){
    this.updateEnrollmentPlaceForm = this.fb.group({
      name: ['',Validators.required],
     status: ['Inactive']
    })    
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm(){ 
    
    for(let controller in this.updateEnrollmentPlaceForm.controls){
      this.updateEnrollmentPlaceForm.get(controller).markAsTouched();
    }

    if(this.updateEnrollmentPlaceForm.invalid){
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.farmerEnrollmentPlaceService.UpdateEnrollmentPlace(id, this.updateEnrollmentPlaceForm.value).subscribe(res => {
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
    this.router.navigate(['/farmer/enrollment-place']);
  }
}

import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { FarmerFarmOwnershipTypeService } from '../services/farmer-farm-ownership-type.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';



@Component({
  selector: 'app-edit-farmer-farm-ownership-type',
  templateUrl: './edit-farmer-farm-ownership-type.component.html',
  styleUrls: ['./edit-farmer-farm-ownership-type.component.scss']
})
export class EditFarmerFarmOwnershipTypeComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  FarmOwnershipTypeList: any = [];
  updateFarmOwnershipTypeForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.updateForm()
  }

  constructor(
    private actRoute: ActivatedRoute,    
    public farmerFarmOwnershipTypeService: FarmerFarmOwnershipTypeService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.farmerFarmOwnershipTypeService.GetFarmOwnershipType(id).subscribe((data) => {
      this.updateFarmOwnershipTypeForm = this.fb.group({
        name: [data.name,Validators.required],
        status: [data.status]
        })
    })
  }

  updateForm(){
    this.updateFarmOwnershipTypeForm = this.fb.group({
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
    
    for(let controller in this.updateFarmOwnershipTypeForm.controls){
      this.updateFarmOwnershipTypeForm.get(controller).markAsTouched();
    }

    if(this.updateFarmOwnershipTypeForm.invalid){
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.farmerFarmOwnershipTypeService.UpdateFarmOwnershipType(id, this.updateFarmOwnershipTypeForm.value).subscribe(res => {
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
     this.router.navigate(['/farmer/farm-ownership-type']);
    }
}

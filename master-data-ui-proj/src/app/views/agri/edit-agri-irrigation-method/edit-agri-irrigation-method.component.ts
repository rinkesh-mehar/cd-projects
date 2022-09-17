import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriIrrigationMethodService } from '../services/agri-irrigation-method.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-agri-irrigation-method',
  templateUrl: './edit-agri-irrigation-method.component.html',
  styleUrls: ['./edit-agri-irrigation-method.component.scss']
})
export class EditAgriIrrigationMethodComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  
  IrrigationMethodList: any = [];
  updateIrrigationMethodForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.updateForm()
  }

  constructor(
    private actRoute: ActivatedRoute,
    public agriIrrigationMethodService: AgriIrrigationMethodService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriIrrigationMethodService.GetIrrigationMethod(id).subscribe((data) => {
      this.updateIrrigationMethodForm = this.fb.group({
        name: [data.name,Validators.required],
        status: [data.status]
        })
    })
  }

  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  updateForm(){
    this.updateIrrigationMethodForm = this.fb.group({
      name: ['',Validators.required],
      status: ['Inactive']
    })    
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm(){ 
    
    for(let controller in this.updateIrrigationMethodForm.controls){
      this.updateIrrigationMethodForm.get(controller).markAsTouched();
    }

    if(this.updateIrrigationMethodForm.invalid){
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriIrrigationMethodService.UpdateIrrigationMethod(id, this.updateIrrigationMethodForm.value).subscribe(res => {
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
    this.router.navigate(['/agri/irrigation-method']);
  }
}

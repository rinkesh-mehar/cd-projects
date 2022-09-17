import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriAgroChemicalApplicationTypeService } from '../../services/agri-agro-chemical-application-type.service';
import { AgriStressSeverityService } from '../../services/agri-stress-severity.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
@Component({
  selector: 'app-edit-stress-severity',
  templateUrl: './edit-stress-severity.component.html',
  styleUrls: ['./edit-stress-severity.component.scss']
})
export class EditStressSeverityComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  StressSeverityList: any = [];
  updateStressSeverityForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.updateForm()
  }

  constructor(
    private actRoute: ActivatedRoute,    
    public agriStressSeverityService: AgriStressSeverityService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriStressSeverityService.GetStressSeveritye(id).subscribe((data) => {
      this.updateStressSeverityForm = this.fb.group({
        name: [data.name,Validators.required],
        value: [data.value,Validators.required],
        status:[data.status]
        // end: [data.end,Validators.required]
        })
    })
  }

  updateForm(){
    this.updateStressSeverityForm = this.fb.group({
      name: ['',Validators.required],
      value:['',Validators.required],
      // end:['',Validators.required]
      status: []
    })    
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  submitForm() {

    for (let controller in this.updateStressSeverityForm.controls) {
      this.updateStressSeverityForm.get(controller).markAsTouched();
    }

    if (this.updateStressSeverityForm.invalid) {
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriStressSeverityService.UpdateStressSeverity(id, this.updateStressSeverityForm.value).subscribe(res => {
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
    this.router.navigate(['/stress/stress-severity']);
  }
}

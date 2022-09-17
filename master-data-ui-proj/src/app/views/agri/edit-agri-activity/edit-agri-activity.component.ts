import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriActivityService } from '../services/agri-activity.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-agri-activity',
  templateUrl: './edit-agri-activity.component.html',
  styleUrls: ['./edit-agri-activity.component.scss']
})
export class EditAgriActivityComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  
  ActivityList: any = [];
  updateActivityForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  ngOnInit() {
    this.updateForm()
  }

  constructor(
    private actRoute: ActivatedRoute,
    public agriActivityService: AgriActivityService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) {
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriActivityService.GetActivity(id).subscribe((data) => {
      this.updateActivityForm = this.fb.group({
        name: [data.name, Validators.required],
        status: [data.status]

      })
    })
  }

  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  updateForm() {
    this.updateActivityForm = this.fb.group({
      name: ['', Validators.required],
      status: ['Inactive']
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {
    for (let Controller in this.updateActivityForm.controls) {
      this.updateActivityForm.get(Controller).markAllAsTouched();
    }

    if (this.updateActivityForm.invalid) {
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriActivityService.UpdateActivity(id, this.updateActivityForm.value).subscribe(res => {
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
    this.router.navigate(['/agri/activity']);
  }
}

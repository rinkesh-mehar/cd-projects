import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriSeasonService } from '../services/agri-season.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-agri-season',
  templateUrl: './edit-agri-season.component.html',
  styleUrls: ['./edit-agri-season.component.scss']
})
export class EditAgriSeasonComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  
  SeasonList: any = [];
  updateSeasonForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.updateForm()
  }

  constructor(
    private actRoute: ActivatedRoute,    
    public agriSeasonService: AgriSeasonService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriSeasonService.GetSeason(id).subscribe((data) => {
      this.updateSeasonForm = this.fb.group({
        name: [data.name,Validators.required],
        status: [data.status]
        })
    })
  }

  updateForm(){
    this.updateSeasonForm = this.fb.group({
      name: ['',Validators.required],
      status:['Inactive']
    })    
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  submitForm() {

    for (let controller in this.updateSeasonForm.controls) {
      this.updateSeasonForm.get(controller).markAsTouched();
    }

    if (this.updateSeasonForm.invalid) {
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriSeasonService.UpdateSeason(id, this.updateSeasonForm.value).subscribe(res => {
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
    this.router.navigate(['/agri/season']);
  }
}

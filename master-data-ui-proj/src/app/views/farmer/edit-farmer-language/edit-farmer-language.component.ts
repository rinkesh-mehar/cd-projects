import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FarmerLanguageService } from '../services/farmer-language.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-farmer-language',
  templateUrl: './edit-farmer-language.component.html',
  styleUrls: ['./edit-farmer-language.component.scss']
})
export class EditFarmerLanguageComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  
  languageList : any [];
  updateLanguageForm : FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
 
  ngOnInit() {
    
    this.updateForm()
  }

  constructor(
    private actRoute : ActivatedRoute,
    public farmerLanguageService : FarmerLanguageService,
    public fb : FormBuilder,
    private ngZone : NgZone,
    private router : Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.farmerLanguageService.GetLanguage(id).subscribe((data) => {
      this.updateLanguageForm = this.fb.group({
        language: [data.language,Validators.required],
        status: [data.status]
        })
    })
  }
  updateForm(){
    this.updateLanguageForm = this.fb.group({
      language:['',Validators.required],
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
    for(let controller in this.updateLanguageForm.controls){
      this.updateLanguageForm.get(controller).markAllAsTouched();
    }
    if(this.updateLanguageForm.invalid){
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.farmerLanguageService.UpdateLanguage(id, this.updateLanguageForm.value).subscribe(res => {
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
   this.router.navigate(['/farmer/language']);
  }
}

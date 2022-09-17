import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriAgroChemicalApplicationTypeService } from '../../services/agri-agro-chemical-application-type.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-edit-agrochemical-application',
  templateUrl: './edit-agrochemical-application.component.html',
  styleUrls: ['./edit-agrochemical-application.component.scss']
})
export class EditAgrochemicalApplicationComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  AgrochemicalApplicationList: any = [];
  updateAgrochemicalApplicationForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.updateForm()
  }

  constructor(
    private actRoute: ActivatedRoute,    
    public agriAgrochemicalApplicationService: AgriAgroChemicalApplicationTypeService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriAgrochemicalApplicationService.GetAgroChemicalApplictionType(id).subscribe((data) => {
      this.updateAgrochemicalApplicationForm = this.fb.group({
        name: [data.name,Validators.required],
        status: [data.status]
        })
    })
  }

  updateForm(){
    this.updateAgrochemicalApplicationForm = this.fb.group({
      name: ['',Validators.required],
      status:['Inactive']
    })    
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm(){ 
    
    for(let controller in this.updateAgrochemicalApplicationForm.controls){
      this.updateAgrochemicalApplicationForm.get(controller).markAsTouched();
    }

    if(this.updateAgrochemicalApplicationForm.invalid){
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriAgrochemicalApplicationService.UpdateAgroChemicalApplicationType(id, this.updateAgrochemicalApplicationForm.value).subscribe(res => {
      this.isSubmitted = true;
      if (res) {
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
        this.router.navigate(['/stress/agrochemical-application-type']);
    }
}

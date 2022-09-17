import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriDisposalMethodService } from '../services/agri-disposal-method.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-agri-disposal-method',
  templateUrl: './edit-agri-disposal-method.component.html',
  styleUrls: ['./edit-agri-disposal-method.component.scss']
})
export class EditAgriDisposalMethodComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  DisposalMethodList: any = [];
  updateDisposalMethodForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.updateForm()
  }

  constructor(
    private actRoute: ActivatedRoute,    
    public agriDisposalMethodService: AgriDisposalMethodService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriDisposalMethodService.GetDisposalMethod(id).subscribe((data) => {
      this.updateDisposalMethodForm = this.fb.group({
        name: [data.name,Validators.required],
        status : [data.status]
        })
    })
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  updateForm(){
    this.updateDisposalMethodForm = this.fb.group({
      name: ['',Validators.required],
      status: ['Inactive']
    })    
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm(){ 
    
    for(let controller in this.updateDisposalMethodForm.controls){
      this.updateDisposalMethodForm.get(controller).markAsTouched();
    }

    if(this.updateDisposalMethodForm.invalid){
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriDisposalMethodService.UpdateDisposalMethod(id, this.updateDisposalMethodForm.value).subscribe(res => {
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
    this.router.navigate(['/agri/disposal-method']);
  }
}

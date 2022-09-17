import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { GeneralUomService } from '../services/general-uom.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';




@Component({
  selector: 'app-edit-general-uom',
  templateUrl: './edit-general-uom.component.html',
  styleUrls: ['./edit-general-uom.component.scss']
})
export class EditGeneralUomComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  UomList: any = [];
  updateUomForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.updateForm();
  }

  constructor(
    private actRoute: ActivatedRoute,
    public generalUomService: GeneralUomService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.generalUomService.GetUom(id).subscribe((data) => {
      this.updateUomForm = this.fb.group({
        name: [data.name,Validators.required],
        description: [data.description],
        status: [data.status]
       })
    })
  }

  updateForm(){
    this.updateUomForm = this.fb.group({
      name: ['',Validators.required],
      description:[''],
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

    for(let controller in this.updateUomForm.controls){
      this.updateUomForm.get(controller).markAsTouched();
    }
  
    if(this.updateUomForm.invalid){
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.generalUomService.UpdateUom(id, this.updateUomForm.value).subscribe(res => {
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
        this.router.navigate(['/general/uom']);
    }
}

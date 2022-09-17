import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriSourceOfWaterService } from '../services/agri-source-of-water.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-agri-source-of-water',
  templateUrl: './edit-agri-source-of-water.component.html',
  styleUrls: ['./edit-agri-source-of-water.component.scss']
})
export class EditAgriSourceOfWaterComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  
  sourceOfWaterList: any = [];
  updateSourceOfWaterForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.updateForm()
  }

  constructor(
    private actRoute: ActivatedRoute,    
    public agriSourceOfWaterService: AgriSourceOfWaterService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriSourceOfWaterService.GetSourceOfWater(id).subscribe((data) => {
      this.updateSourceOfWaterForm = this.fb.group({
        name: [data.name,Validators.required],
        status: [data.status]
        })
    })
  }

  updateForm(){
    this.updateSourceOfWaterForm = this.fb.group({
      name: ['',Validators.required],
      status: ['Inactive']
     
    })    
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm(){ 
    
    for(let controller in this.updateSourceOfWaterForm.controls){
      this.updateSourceOfWaterForm.get(controller).markAsTouched();
    }

    if(this.updateSourceOfWaterForm.invalid){
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriSourceOfWaterService.UpdateSourceOfWater(id, this.updateSourceOfWaterForm.value).subscribe(res => {
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
    this.router.navigate(['/agri/source-of-water']);
  }
}

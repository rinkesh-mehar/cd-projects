import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriFarmMachineryService } from '../services/agri-farm-machinery.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-edit-agri-farm-machinery',
  templateUrl: './edit-agri-farm-machinery.component.html',
  styleUrls: ['./edit-agri-farm-machinery.component.scss']
})
export class EditAgriFarmMachineryComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  
  FarmMachineryList: any = [];
  updateFarmMachineryForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.updateForm()
  }

  constructor(
    private actRoute: ActivatedRoute,    
    public agriFarmMachineryService: AgriFarmMachineryService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriFarmMachineryService.GetFarmMachinery(id).subscribe((data) => {
      this.updateFarmMachineryForm = this.fb.group({
        name: [data.name,Validators.required],
        status: [data.status]
        })
    })
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  updateForm(){
    this.updateFarmMachineryForm = this.fb.group({
      name: ['',Validators.required],
      status: ['Inactive']
    })    
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm(){ 
    
    for(let controller in this.updateFarmMachineryForm.controls){
      this.updateFarmMachineryForm.get(controller).markAsTouched();
    }

    if(this.updateFarmMachineryForm.invalid){
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriFarmMachineryService.UpdateFarmMachinery(id, this.updateFarmMachineryForm.value).subscribe(res => {
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
    this.router.navigate(['/agri/farm-machinery']);
  }
}

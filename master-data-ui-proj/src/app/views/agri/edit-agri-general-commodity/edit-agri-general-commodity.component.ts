import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriGeneralCommodityService } from '../services/agri-general-commodity.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-agri-general-commodity',
  templateUrl: './edit-agri-general-commodity.component.html',
  styleUrls: ['./edit-agri-general-commodity.component.scss']
})
export class EditAgriGeneralCommodityComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;


  GeneralCommodityList: any = [];
  updateGeneralCommodityForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.updateForm()
  }

  constructor(
    private actRoute: ActivatedRoute,    
    public agriGeneralCommodityService: AgriGeneralCommodityService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriGeneralCommodityService.GetGeneralCommodity(id).subscribe((data) => {
      this.updateGeneralCommodityForm = this.fb.group({
        name: [data.name,Validators.required],
        status: [data.status]
        })
    })
  }

  updateForm(){
    this.updateGeneralCommodityForm = this.fb.group({
      name: ['',Validators.required],
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
    
    for(let controller in this.updateGeneralCommodityForm.controls){
      this.updateGeneralCommodityForm.get(controller).markAsTouched();
    }

    if(this.updateGeneralCommodityForm.invalid){
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriGeneralCommodityService.UpdateGeneralCommodity(id, this.updateGeneralCommodityForm.value).subscribe(res => {
      this.isSubmitted = true;
     
      if(res){
        this.isSuccess = res.success;
        if(res.success){
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    })
  }

    modalSuccess($event: any) {
       this.router.navigate(['/commodity/general-commodity']);
    }
}

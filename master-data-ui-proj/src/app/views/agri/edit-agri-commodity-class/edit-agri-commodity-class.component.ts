import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriCommodityClassService } from '../services/agri-commodity-class.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-agri-commodity-class',
  templateUrl: './edit-agri-commodity-class.component.html',
  styleUrls: ['./edit-agri-commodity-class.component.scss']
})
export class EditAgriCommodityClassComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  CommodityClassList: any = [];
  updateCommodityClassForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.updateForm()
  }

  constructor(
    private actRoute: ActivatedRoute,    
    public agriCommodityClassService: AgriCommodityClassService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriCommodityClassService.GetCommodityClass(id).subscribe((data) => {
      this.updateCommodityClassForm = this.fb.group({
        name: [data.name,Validators.required],
        status:[data.status]
        })
    })
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  updateForm(){
    this.updateCommodityClassForm = this.fb.group({
      name: ['',Validators.required],
      status: ['Inactive']
    })    
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm(){ 
    
    for(let controller in this.updateCommodityClassForm.controls){
      this.updateCommodityClassForm.get(controller).markAsTouched();
    }

    if(this.updateCommodityClassForm.invalid){
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriCommodityClassService.UpdateCommodityClass(id, this.updateCommodityClassForm.value).subscribe(res => {
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
    this.router.navigate(['/commodity/commodity-class']);
  }
}

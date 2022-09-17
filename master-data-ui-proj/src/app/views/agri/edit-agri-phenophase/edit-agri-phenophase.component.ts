import { Component, OnInit, NgZone } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriPhenophaseService } from '../services/agri-phenophase.service';

@Component({
  selector: 'app-edit-agri-phenophase',
  templateUrl: './edit-agri-phenophase.component.html',
  styleUrls: ['./edit-agri-phenophase.component.scss']
})
export class EditAgriPhenophaseComponent implements OnInit {
  PhenophaseList: any = [];
  updatePhenophaseForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.updateForm()
  }

  constructor(
    private actRoute: ActivatedRoute,    
    public agriPhenophaseService: AgriPhenophaseService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriPhenophaseService.GetPhenophase(id).subscribe((data) => {
      this.updatePhenophaseForm = this.fb.group({
        name: [data.name,Validators.required],
        status: [data.status]
        })
    })
  }

  updateForm(){
    this.updatePhenophaseForm = this.fb.group({
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
    
    for(let controller in this.updatePhenophaseForm.controls){
      this.updatePhenophaseForm.get(controller).markAsTouched();
    }

    if(this.updatePhenophaseForm.invalid){
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriPhenophaseService.UpdatePhenophase(id, this.updatePhenophaseForm.value).subscribe(res => {
      this.isSubmitted = true;
     
      if(res){
        this.isSuccess = res.success;
        if(res.success){
          this._statusMsg = res.message;
          this.updatePhenophaseForm.reset();

          this.delay(4000).then(any => {
              this.isSubmitted = false;
              this.isSuccess = false;
            });
        }else{
          this._statusMsg = res.error;
        }
      }
    })
  }


}

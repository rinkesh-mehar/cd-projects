import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriAgrochemicalTypeService } from '../../services/agri-agrochemical-type.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-edit-agrochemical-type',
  templateUrl: './edit-agrochemical-type.component.html',
  styleUrls: ['./edit-agrochemical-type.component.scss']
})
export class EditAgrochemicalTypeComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  AgrochemicalTypeList: any = [];
  updateAgrochemicalTypeForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  ngOnInit() {
    this.updateForm()
  }

  constructor(
    private actRoute: ActivatedRoute,
    public agriAgrochemicalTypeService: AgriAgrochemicalTypeService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) {
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriAgrochemicalTypeService.GetAgrochemicalType(id).subscribe((data) => {
      this.updateAgrochemicalTypeForm = this.fb.group({
        name: [data.name, Validators.required],
        status: [data.status]
      })
    })
  }

  updateForm() {
    this.updateAgrochemicalTypeForm = this.fb.group({
      name: ['', Validators.required],
      status: ['']
    })
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {
    for (let Controller in this.updateAgrochemicalTypeForm.controls) {
      this.updateAgrochemicalTypeForm.get(Controller).markAllAsTouched();
    }

    if (this.updateAgrochemicalTypeForm.invalid) {
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriAgrochemicalTypeService.UpdateAgrochemicalType(id, this.updateAgrochemicalTypeForm.value).subscribe(res => {
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
        this.router.navigate(['/agrochemicals/agrochemical-type']);
    }
}

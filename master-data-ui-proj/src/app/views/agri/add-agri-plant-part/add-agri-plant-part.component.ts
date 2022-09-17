import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AgriPlantPartService } from '../services/agri-plant-part.service';
import { ApiUtilService } from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-add-agri-plant-part',
  templateUrl: './add-agri-plant-part.component.html',
  styleUrls: ['./add-agri-plant-part.component.scss']
})
export class AddAgriPlantPartComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  plantPartForm: FormGroup;
  plantPartArr: any = [];

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;
  
  ngOnInit() {
    this.addPlantPart()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public agriPlantPartService: AgriPlantPartService,
    public apiUtilService: ApiUtilService
 
  ){ }

  addPlantPart() {
    this.plantPartForm = this.fb.group({
      name: ['',Validators.required],
      status: ['Inactive']
      
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {

    for(let controller in this.plantPartForm.controls){
      this.plantPartForm.get(controller).markAsTouched();
    }

    if(this.plantPartForm.invalid){
      return;
    }


    this.agriPlantPartService.CreatePlantPart(this.plantPartForm.value).subscribe(res => {
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

  downloadExcelFormat(){
    var tableName = "agri_plant_part";
    this.apiUtilService.downloadExcelFormat(tableName);
  }//downloadExcelFormat


  uploadExcel(element) {
    var file: File = element.target.files[0];
    this.fileUpload = file;
  }

  submitExcelForm() {
    this.apiUtilService.uploadExcelFile(this.fileUpload).subscribe(res => {
      this.isSubmittedBulk = true;
     
      if(res){
        this.isSuccessBulk = res.success;
        if(res.success){
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

    modalSuccess($event: any) {
        this.router.navigate(['/commodity/plant-part']);
    }

    trimValue(formControl) { 
      formControl.setValue(formControl.value.trim()); 
    }
    
    
}

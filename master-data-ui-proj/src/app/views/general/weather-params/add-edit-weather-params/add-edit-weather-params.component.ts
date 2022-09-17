import {Component, OnInit, Input, Output, EventEmitter, SimpleChanges, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {ActivatedRoute, Route, Router} from '@angular/router';


import { GeneralUomService } from '../../../general/services/general-uom.service';
import { GeneralWeatherParamsService } from '../../services/general-weather-params.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-edit-weather-params',
  templateUrl: './add-edit-weather-params.component.html',
  styleUrls: ['./add-edit-weather-params.component.scss']
})
export class AddEditWeatherParamsComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  WeatherparamsForm: FormGroup;
  // operationMode: string; 
  uomList: any;
  weatherParameterList: any;
  editWeatherparamsId: any;
  mode: any = 'add';
  Weatherparams: any;

  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;



  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  constructor(public formBuilder: FormBuilder, private generalUomService : GeneralUomService,
    public generalWeatherparamsService : GeneralWeatherParamsService, private actRoute: ActivatedRoute,
              public apiUtilService: ApiUtilService, public router: Router) {

    this.createWeatherparamsForm();

  }
  getChanges() {
    console.log(this.WeatherparamsForm.value)
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  createWeatherparamsForm() {
    this.WeatherparamsForm = this.formBuilder.group({
      id: [],
      name: ['', Validators.required],
      label: ['', Validators.required],
      unitId: ['', Validators.required],
      status: ['Inactive']
   
   })
  }

  ngOnInit() {
    this.loadAllUom();

 this.editWeatherparamsId = this.actRoute.snapshot.paramMap.get('id');

    if (this.editWeatherparamsId) {

      this.mode = "edit";
      this.generalWeatherparamsService.GetAgriWeatherParams(this.editWeatherparamsId).subscribe(data => {
        this.Weatherparams = data;
        this.WeatherparamsForm = this.formBuilder.group({
          id: [],
          name: ['', Validators.required],
          label: ['', Validators.required],
          unitId: ['', Validators.required],
          status: ['']
         
        })

        this.WeatherparamsForm.patchValue(this.Weatherparams);
      })
}

  }

  loadAllUom() {
    return this.generalUomService.GetAllUom().subscribe((data: any) => {
      this.uomList = data;
    }, err => {
      alert(err)
    })
  }

  submitWeatherparamsForm() {

    for (let controller in this.WeatherparamsForm.controls) {
      this.WeatherparamsForm.get(controller).markAsTouched();
    }
    if (this.WeatherparamsForm.invalid) {
      return;
    }

  if(this.WeatherparamsForm.get('unitId').value == 0){
    this.errorModal.showModal('ERROR', 'Please Select Unit', '');
      return;
    }
  
    
    if (this.mode == "edit") {
      this.updateWeatherparams();
    } else {
      this.addWeatherparams();
    }
  }
  addWeatherparams() {
    this.generalWeatherparamsService.CreateAgriWeatherParams(this.WeatherparamsForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.Weatherparams = {};
          this.mode = "add";
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    })
  }
  updateWeatherparams() {
    this.generalWeatherparamsService.UpdateAgriWeatherParams(this.WeatherparamsForm.value.id, this.WeatherparamsForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.Weatherparams = {};
          this.mode = "add";
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }


  downloadExcelFormat(){
    var tableName = "general_weather_params";
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
        this.router.navigate(['/general/weather-params']);
    }
}

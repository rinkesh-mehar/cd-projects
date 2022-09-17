import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AgriCommodityService } from '../../../agri/services/agri-commodity.service';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { AgriQualityCommodityParameterService } from '../../service/agri-quality-commodity-parameter.service';
import { AgriQualityParameterService } from '../../service/agri-quality-parameter.service';

@Component({
  selector: 'app-add-edit-agri-quality-commodity-parameter',
  templateUrl: './add-edit-agri-quality-commodity-parameter.component.html',
  styleUrls: ['./add-edit-agri-quality-commodity-parameter.component.scss']
})
export class AddEditAgriQualityCommodityParameterComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  qualityCommodityParameterForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  mode: string = 'add';
  editByCommodityId: string;

  QualityParameterList: any = [];
  CommodityList: any = [];
  selection:any = [];
  qualityParamIds = [];
  qualityParamListByCommodityId:any = [];

  constructor(private router: Router,
       public fb: FormBuilder,
      public agriQualityCommodityParameterService: AgriQualityCommodityParameterService,
      private actRoute: ActivatedRoute,
      public commodityService: AgriCommodityService,
      public agriQualityParameterService: AgriQualityParameterService) { }

  ngOnInit() {
    
    this.loadAllCommodities();
    this.loadAllQualityParameter();
    this.addEditQualityCommodityParameter();

    this.editByCommodityId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editByCommodityId) {
         
            this.mode = 'edit';

            this.qualityCommodityParameterForm.patchValue({commodityId:this.editByCommodityId});

            this.agriQualityCommodityParameterService.getParameterListByCommodityId(this.editByCommodityId).subscribe(data => {
            this.selection = data;

            for (var parameter of this.selection) {
              this.qualityParamIds.push(parameter.id);
            }
        
            this.qualityCommodityParameterForm.patchValue({qualityParameterIds: this.qualityParamIds});
          });
        }
        // console.log('id ' + this.editId);
  }

  addEditQualityCommodityParameter() {
    this.qualityCommodityParameterForm = this.fb.group({
      commodityId: ['', Validators.required],
      qualityParameterIds: [''],
    })
  }

  submitForm() {

    // console.log("inside submitForm");
    for(let controller in this.qualityCommodityParameterForm.controls){

      this.qualityCommodityParameterForm.get(controller).markAsTouched();

    }
  
    if(this.qualityCommodityParameterForm.invalid){
      // console.log("inside 1st if");
      return;
    }

// console.log("selected : " + JSON.stringify(this.selection));
this.qualityParamIds = [];
if(this.selection.length == 0){
  this.qualityCommodityParameterForm.patchValue({qualityParameterIds: ''});
}else{
  
for (var parameter of this.selection) {
  this.qualityParamIds.push(parameter.id);
}
  this.qualityCommodityParameterForm.patchValue({qualityParameterIds: ''});
  this.qualityCommodityParameterForm.patchValue({qualityParameterIds: this.qualityParamIds});
}

console.log("qualityParamIds : " + JSON.stringify(this.qualityParamIds));

if(this.qualityCommodityParameterForm.value.qualityParameterIds == null
  || this.qualityCommodityParameterForm.value.qualityParameterIds == ''){
  this.errorModal.showModal('ERROR', 'Quality Parameter is required. Please Select Quality Parameter', '');
  return;
}

    if (this.mode == 'add') {
      this.add();
    } else {
      this.update();
    }

    
  }

  add(){

  return this.agriQualityCommodityParameterService.addQualityCommodityParameter(this.qualityCommodityParameterForm.value).subscribe(res => {
  
    this.isSubmitted = true;
   
    if(res) {
      
      this.isSuccess = res.success;
      if (res.success) {
        this.addEditQualityCommodityParameter();
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
      
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  });

}

update(){

  return this.agriQualityCommodityParameterService.updateQualityCommodityParameter(this.editByCommodityId, this.qualityCommodityParameterForm.value).subscribe( res => {
    this.isSubmitted = true;
    if (res) {
        this.isSuccess = res.success;
        if (res.success) {
            this.addEditQualityCommodityParameter();
            this.successModal.showModal('SUCCESS', res.message, '');
        } else {
            this.errorModal.showModal('ERROR', res.error, '');
        }
    }
});

}

 //Quality list
 loadAllQualityParameter(){
  return this.agriQualityParameterService.getAllQualityParameter().subscribe((data: {}) => {
    this.QualityParameterList = data;
  })
}

 //Commodity list
 loadAllCommodities() {
  return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
    this.CommodityList = data;
  })
}

trimValue(formControl) { 
  formControl.setValue(formControl.value.trim()); 
}

getSelection(data) {
  return this.selection.findIndex(s => s.id === data.id) !== -1;
}

changeHandler(data: any, event: KeyboardEvent) {
  const id = data.id;

  const index = this.selection.findIndex(u => u.id === id);
  if (index === -1) {
    // ADD TO SELECTION
    // this.selection.push(item);
    this.selection = [...this.selection, data];
  } else {
    // REMOVE FROM SELECTION
    this.selection = this.selection.filter(user => user.id !== data.id)
    // this.selection.splice(index, 1)
  }
}

modalSuccess($event: any) {
  this.router.navigate(['/commodity/quality-commodity-parameter']);
}

}

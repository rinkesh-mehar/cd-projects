import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { GeoStateService } from '../../../geo/services/geo-state.service';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { ApiUtilService } from '../../../services/api-util.service';
import { AczService } from '../../services/acz.service';
import { ZonalCommodityService } from '../../services/zonal-commodity.service';
import { ZonalCommodity } from '../../models/ZonalCommodity';
import { AgriCommodityService } from '../../../agri/services/agri-commodity.service';

@Component({
  selector: 'app-add-edit-zonal-commodity',
  templateUrl: './add-edit-zonal-commodity.component.html',
  styleUrls: ['./add-edit-zonal-commodity.component.scss']
})
export class AddEditZonalCommodityComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;
zonalCommodityForm: FormGroup;

isSubmitted: boolean = false;
isSuccess: boolean = false;

mode: string = 'add';
editId: string;

StateList: any = [];
AczList: any = [];
CommodityList: any = [];
weekList: number[] = [];
stateCodeAczId: any;
zonalCommodity:any;

constructor(private router: Router,
     public fb: FormBuilder,
    public zonalCommodityService: ZonalCommodityService,
    private actRoute: ActivatedRoute, public geoStateService : GeoStateService,
    private aczService:AczService,
    public commodityService: AgriCommodityService) { }

ngOnInit() {
  
  this.getAllState();
  this.getAllCommodities();
  this.addEditCommodity();
 

  for (let i = 1; i <= 52; i++) {
    this.weekList.push(i);
  }
  

  this.editId = this.actRoute.snapshot.paramMap.get('id');

      if (this.editId) {
       
          this.mode = 'edit';
          this.zonalCommodityService.getCommodityById(this.editId).subscribe(data => {
            this.zonalCommodity=data;
          this.zonalCommodityForm.patchValue(this.zonalCommodity);
          this.zonalCommodityForm.patchValue({commodityId:data.commodityId});
          this.getStateCodeByAczId();
        });
       
      }

      // console.log('id ' + this.editId);
}

addEditCommodity() {
  this.zonalCommodityForm = this.fb.group({
    aczId: ['', Validators.required],
    stateCode: ['', Validators.required],
    commodityId: ['', Validators.required],
    sowingWeekStart: ['', Validators.required],
    sowingWeekEnd: ['', Validators.required],
    harvestWeekStart: ['', Validators.required],
    harvestWeekEnd: ['', Validators.required],
    noOfDaysForHarvestMonitoring: ['', [Validators.required,Validators.pattern("^[0-9]*$")]]
  })
}

submitForm() {
  // console.log("inside submitForm");
  for(let controller in this.zonalCommodityForm.controls){

    this.zonalCommodityForm.get(controller).markAsTouched();

  }

  if(this.zonalCommodityForm.invalid){
    // console.log("inside 1st if");
    return;
  }

  if (this.mode == 'add') {
    this.add();

 
  } else {
    this.update();
  }

  
}

add(){




return this.zonalCommodityService.addZonalCommodity(this.zonalCommodityForm.value).subscribe(res => {

  this.isSubmitted = true;
 
  if(res) {
    
    this.isSuccess = res.success;
    if (res.success) {
      this.successModal.showModal('SUCCESS', res.message, '');
    } else {
    
      this.errorModal.showModal('ERROR', res.error, '');
    }
  }
});

}

update(){

return this.zonalCommodityService.updateZonalCommodity(this.editId, this.zonalCommodityForm.value).subscribe( res => {
  this.isSubmitted = true;
  if (res) {
      this.isSuccess = res.success;
      if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
      } else {
          this.errorModal.showModal('ERROR', res.error, '');
      }
  }
});

}

//State list
getAllState(){
return this.geoStateService.GetAllState().subscribe((data: {}) => {
  this.StateList = data;
})
}


getAczByStateCode() {
  // alert(this.updateVarietyForm.value.stateCode);
  this.zonalCommodityForm.patchValue({aczId:''});
  return this.aczService.getAllAczByStateCode(this.zonalCommodityForm.value.stateCode).subscribe((data: {}) => {
    this.AczList = data;
  })
}

getAczByStateCodeForEditMode() {
  // alert(this.updateVarietyForm.value.stateCode);
  return this.aczService.getAllAczByStateCode(this.zonalCommodityForm.value.stateCode).subscribe((data: {}) => {
    this.AczList = data;
  })
}

//Commodity list
getAllCommodities() {
return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
  this.CommodityList = data;
})
}




getStateCodeByAczId(){
  return this.aczService.getStateCodeByAczId(this.zonalCommodityForm.value.aczId).subscribe(data =>{

  this.stateCodeAczId =data;
  this.zonalCommodityForm.patchValue(this.stateCodeAczId);
    // alert(this.zonalCommodity.aczId);
    this.getAczByStateCodeForEditMode();
  this.zonalCommodityForm.patchValue({aczId:this.zonalCommodity.aczId});
  })
}

trimValue(formControl) { 
formControl.setValue(formControl.value.trim()); 
}

modalSuccess($event: any) {
this.router.navigate(['/zonal/commodity']);
}

}
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AgriCommodityService } from '../../../agri/services/agri-commodity.service';
import { GeoStateService } from '../../../geo/services/geo-state.service';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { AgriStateCommodityService } from '../../service/agri-state-commodity.service';

@Component({
  selector: 'app-add-edit-agri-state-commodity',
  templateUrl: './add-edit-agri-state-commodity.component.html',
  styleUrls: ['./add-edit-agri-state-commodity.component.scss']
})
export class AddEditAgriStateCommodityComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  stateCommodityForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  mode: string = 'add';
  editId: string;

  StateList: any = [];
  CommodityList: any = [];

  constructor(private router: Router,
       public fb: FormBuilder,
      public agriStateCommodityService: AgriStateCommodityService,
      private actRoute: ActivatedRoute, public geoStateService : GeoStateService,
      public commodityService: AgriCommodityService) { }

  ngOnInit() {
    
    this.loadAllState();
    this.loadAllCommodities();
    this.addEditStressStage();

    this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
         
            this.mode = 'edit';
            this.agriStateCommodityService.getAgriStateCommodityById(this.editId).subscribe(data => {
            this.stateCommodityForm.patchValue(data);
            this.stateCommodityForm.patchValue({commodityId:data.commodityId});
          });
        }

        // console.log('id ' + this.editId);
  }

  addEditStressStage() {
    this.stateCommodityForm = this.fb.group({
      stateCode: ['', Validators.required],
      commodityId: ['', Validators.required],
    })
  }

  submitForm() {
    // console.log("inside submitForm");
    for(let controller in this.stateCommodityForm.controls){

      this.stateCommodityForm.get(controller).markAsTouched();

    }
  
    if(this.stateCommodityForm.invalid){
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

  return this.agriStateCommodityService.addAgriStateCommodity(this.stateCommodityForm.value).subscribe(res => {
  
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

  return this.agriStateCommodityService.updateAgriStateCommodity(this.editId, this.stateCommodityForm.value).subscribe( res => {
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
 loadAllState(){
  return this.geoStateService.GetAllState().subscribe((data: {}) => {
    this.StateList = data;
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

modalSuccess($event: any) {
  this.router.navigate(['/commodity/state-commodity']);
}

}

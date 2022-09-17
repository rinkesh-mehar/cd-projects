import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { GeoDistrictService } from '../../../geo/services/geo-district.service';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { OppotunitiesService } from '../../services/oppotunities.service';


@Component({
  selector: 'app-add-oppotunity',
  templateUrl: './add-oppotunity.component.html',
  styleUrls: ['./add-oppotunity.component.css']
})
export class AddOppotunityComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  DepartmentList: any =[];
  PositionList: any =[];
  EducationList: any =[];
  StateList: any =[];
  DistrictList: any =[];
  platFormList: any =[];
  opportunityForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  mode: string = 'add';
  editId: string;

  eduList : any =[];

  constructor(private router: Router,
       public fb: FormBuilder,
      public geoDistrictService: GeoDistrictService,
      private opportunitiesService: OppotunitiesService,
      private actRoute: ActivatedRoute) { }

  ngOnInit() {
    this.getPlatForm();
    this.getDepartmentList();
    this.getPositionList();
    this.getEducationList();
    // this.getStateList();
    this.addOpportunity();

    this.editId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editId) {
         
            this.mode = 'edit';
            // this.getDistrictList();

            
            
            this.opportunitiesService.getOpportunityById(this.editId).subscribe(data => {
              //  alert(JSON.stringify(data));
                this.opportunityForm.patchValue(data);
                this.getEducationIds();
                //this.eduList = data.educationID.split(',');
                // alert(JSON.stringify(this.eduList));
                // this.opportunityForm.patchValue({ educationID: this.eduList});
            });
        }

        console.log('id ' + this.editId);
  }

  addOpportunity() {
    this.opportunityForm = this.fb.group({

      platformID: ['', Validators.required],
      departmentID: ['', Validators.required],
      positionID: ['', Validators.required],
      educationID: ['', Validators.required],
      experience: ['', Validators.required],
      location: ['', Validators.required],
      // stateCode: ['', Validators.required],
      // districtCode: ['', Validators.required],
      description: ['', Validators.required],
      profile: ['', Validators.required],
      remuneration: [''],
      applyTo: ['jobs@cropdata.in', Validators.required]
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.opportunityForm.controls){

      this.opportunityForm.get(controller).markAsTouched();

    }
  
    if(this.opportunityForm.invalid){
      console.log("inside 1st if");
      return;
    }

    if (this.mode == 'add') {
  
      if(this.opportunityForm.value.educationID[0] === ""){
        this.errorModal.showModal('ERROR', 'Education is required. Please select the Education.', '');
        return;
        }

      console.log("Educ :" , this.opportunityForm.value.educationID);

      this.add();
    } else {

      if(this.opportunityForm.value.educationID[0] === ""){
        this.errorModal.showModal('ERROR', 'Education is required. Please select the Education.', '');
        return;
        }
       console.log("Educ :" , this.opportunityForm.value.educationID);
      this.update();
    }

    
  }

  add(){

  return this.opportunitiesService.addOpportunity(this.opportunityForm.value).subscribe(res => {
  
    this.isSubmitted = true;
   
    if(res) {
      
      this.isSuccess = res.success;
      if (res.success) {
        
        // this._statusMsg = res.message;
        // this.stateForm.reset();

        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
      
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  });

}

update(){
console.log('inside update');
  return this.opportunitiesService.updateOpportunity(this.editId, this.opportunityForm.value).subscribe( res => {
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

getPlatForm() {
  return this.opportunitiesService.getPlatFormList().subscribe((data: {}) => {
      this.platFormList = data;

  });
}


  getDepartmentList(){

    return this.opportunitiesService.getDepartmentList().subscribe((data: {}) => {
      this.DepartmentList = data;
    })
  }

  getPositionList(){

    return this.opportunitiesService.getPositionList().subscribe((data: {}) => {
      this.PositionList = data;
    })
  }

  getEducationList(){

    return this.opportunitiesService.getEducationList().subscribe((data: {}) => {
      this.EducationList = data;
    })
  }

  getStateList(){

    return this.opportunitiesService.getStateList().subscribe((data: {}) => {
      this.StateList = data;
    })
  }

  getDistrictList(){

    return this.opportunitiesService.getDistrictList().subscribe((data: {}) => {
      this.DistrictList = data;
    })
  }

  loadAllDistrictsByState(event:Event) : void {
  
    console.log("Dist By State...");

    let index:number = event.target["selectedIndex"] - 1;
   if(index ==-1) {
       return;
    }
    let stateCode = this.StateList[index].stateCode;
    this.geoDistrictService.GetAllDistrictByStateCode(stateCode).subscribe(
      (data: {}) => {
        this.DistrictList = data;
        console.log(this.DistrictList);
      }
    );
  }

  getEducationIds(){
    return this.opportunitiesService.getEducationIdsByOpportunityId(this.editId).subscribe(data => {
      this.eduList = data;
      this.opportunityForm.patchValue({ educationID: this.eduList});
      // alert(JSON.stringify(this.eduList));
    });
  }

  trimValue(formControl) { 
    console.log('val : ',formControl.value.trim());
    formControl.setValue(formControl.value.trim()); 
  }
  

  modalSuccess($event: any) {
    this.router.navigate(['/cropdata-portal/opportunities-list']);
}

}

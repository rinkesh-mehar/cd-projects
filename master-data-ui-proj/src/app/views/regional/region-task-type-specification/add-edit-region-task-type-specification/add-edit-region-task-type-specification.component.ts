import { AgriPhenophaseService } from '../../../agri/services/agri-phenophase.service';
import { ZonalVarietyService } from '../../services/zonal-variety.service';
import { ZonalCommodityService } from '../../../zonal/services/zonal-commodity.service';
import { GeoStateService } from '../../../geo/services/geo-state.service';
import { AczService } from '../../../zonal/services/acz.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { RegionTaskTypeSpecificationService } from '../../services/region-task-type-specification.service';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';

@Component({
  selector: 'app-add-edit-region-task-type-specification',
  templateUrl: './add-edit-region-task-type-specification.component.html',
  styleUrls: ['./add-edit-region-task-type-specification.component.scss']
})
export class AddEditRegionTaskTypeSpecificationComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  
  taskTypeSpecificationForm: FormGroup; // form name
  
  // dropdown list required
  stateList: any = [];
  // seasonList: any = [];
  aczList: any = [];
  zonalcommodityList: any = [];
  zonalvarietyList: any = [];
  stateCodeAczId : any;
  regionalTaskTypeSpecification : any;
  varietyList: any = [];
  phenophaseList: any = [];
  taskTypeList: any = [];
  zonalVarietyId:any;


  commId :number;
  varID:number;
  
  editId: string;
  mode: string = "add";
   
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  constructor(
    private actRoute: ActivatedRoute,
    public fb: FormBuilder,
    private regionTaskTypeSpecificationService: RegionTaskTypeSpecificationService,
    public router: Router,
    public aczService:AczService,
    public geoStateService:GeoStateService,
    public zonalCommodityService:ZonalCommodityService,
    public zonalVarietyService:ZonalVarietyService,
    public agriPhenophaseService:AgriPhenophaseService
  ) { }

  ngOnInit(): void {
    this.taskTypeSpecificationFG();

    this.loadState();
    // this.loadSeason();
    // this.loadAllAcz();
    // this.loadZonalCommodity();
    //this.loadVarietyByCommodity();
    this.loadTaskType();
    
    
    this.editId = this.actRoute.snapshot.paramMap.get('id');
    if (this.editId) {
      this.mode = "edit";
    }

    if (this.editId) {
      this.mode = "edit";
      this.regionTaskTypeSpecificationService.GetTaskTypeSpecification(this.editId).subscribe(data => {

        // this.commId = data.commodityID;
        // this.varID = data.varietyID;
        //this.loadVarietyByCommodity();
        this.regionalTaskTypeSpecification = data;
        //this.loadPhenophaseByCommodityAndVariety();
        this.taskTypeSpecificationForm.patchValue(this.regionalTaskTypeSpecification);
        this.getStateCodeAczIdByZonalCommodityId();

       
      })
    }

  }//end of ngOnInit

  taskTypeSpecificationFG(){
    this.taskTypeSpecificationForm = this.fb.group({
      id: [],
      stateCode : ['', Validators.required],
      // seasonID : ['', Validators.required],
      aczId : ['', Validators.required],
      zonalCommodityID : ['', Validators.required],
      // zonalVarietyId : ['', Validators.required],
      phenophaseID : ['', Validators.required],
      taskTypeID: ['', Validators.required],
      pushBackLimit : ['', [Validators.required,Validators.pattern('^[0-9]{0,10}$')]],
      priority : ['', [Validators.required,Validators.pattern("^[0-9]*$")]],
      taskTime : ['', [Validators.required,Validators.pattern("^[0-9]*$")]],
      spotDependency : ['', Validators.required],
      status: ['Inactive']
    });

  }

  //statelist
  loadState() {
    return this.geoStateService.GetAllState().subscribe((data: {}) => {
    this.stateList = data;
    })
  }
  //seasonList
  // loadSeason() {
  //   return this.regionTaskTypeSpecificationService.getSeasonList().subscribe((data: {}) => {
  //     this.seasonList = data;
  //   })
  // }

  //ACZ list
  loadAczByStateCode() {
    this.taskTypeSpecificationForm.patchValue({aczId:''});
    return this.aczService.getAllAczByStateCode(this.taskTypeSpecificationForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }

  loadAczByStateCodeForEdit() {
    return this.aczService.getAllAczByStateCode(this.taskTypeSpecificationForm.value.stateCode).subscribe((data: {}) => {
      this.aczList = data;
    })
  }

  //ZonalCommodity list
  loadZonalCommodityByAczId() {
    this.taskTypeSpecificationForm.patchValue({zonalCommodityID:''});
    return this.zonalCommodityService.getZonalCommodityListByAczId(this.taskTypeSpecificationForm.value.aczId).subscribe((data: {}) => {
      this.zonalcommodityList = data;
    })
  }

  loadZonalCommodityByAczIdForEdit() {
    return this.zonalCommodityService.getZonalCommodityListByAczId(this.taskTypeSpecificationForm.value.aczId).subscribe((data: {}) => {
      this.zonalcommodityList = data;
    })
  }

  //ZonalVariety List
  // loadZonalVarietyByZonalCommodity() {
  //   return this.zonalVarietyService.getZonalVarietyListByZonalCommodityId(this.taskTypeSpecificationForm.value.zonalCommodityID).subscribe((data: {}) => {
  //     this.zonalvarietyList = data;
  //   })
  // }
  //Phenophase List by Zonal Variety
  // loadPhenophaseByZonalVarietyId() {
  //   return this.agriPhenophaseService.getPhenophaseListByZonalVarietyId(this.taskTypeSpecificationForm.value.zonalVarietyId).subscribe((data: {}) => {
  //     this.phenophaseList = data;
  //   })
  // }

  //Phenophase List by Zonal Commodity
  loadPhenophaseByZonalCommodityId() {
    this.taskTypeSpecificationForm.patchValue({phenophaseID:''});
    return this.agriPhenophaseService.getPhenophaseListByZonalCommodityId(this.taskTypeSpecificationForm.value.zonalCommodityID).subscribe((data: {}) => {
      this.phenophaseList = data;
    })
  }

  loadPhenophaseByZonalCommodityIdForEdit() {
    return this.agriPhenophaseService.getPhenophaseListByZonalCommodityId(this.taskTypeSpecificationForm.value.zonalCommodityID).subscribe((data: {}) => {
      this.phenophaseList = data;
    })
  }


  // getZonalVarietyIdByStateCodeAczIdCommodityIdPhenophaseId() {
  //   return this.zonalVarietyService.getZonalVarietyIdByStateCodeAczIdCommodityIdPhenophaseId(this.stateCodeAczId.stateCode,this.taskTypeSpecificationForm.value.aczId,this.regionalTaskTypeSpecification.zonalCommodityID,this.regionalTaskTypeSpecification.phenophaseID).subscribe((data: {}) => {
  //       this.zonalVarietyId = data;
  //       this.taskTypeSpecificationForm.patchValue(this.zonalVarietyId);
  //       //this.loadPhenophaseByZonalVarietyId();
  //       this.taskTypeSpecificationForm.patchValue({stateCode:this.stateCodeAczId.stateCode});
  //   })
  // }

  //
  getStateCodeAczIdByZonalCommodityId() {
    //alert("CommodityId = "+this.taskTypeSpecificationForm.value.zonalCommodityID)
    return this.zonalCommodityService.getStateCodeAczIdByZonalCommodityId(this.taskTypeSpecificationForm.value.zonalCommodityID).subscribe((data) => {
      this.stateCodeAczId = data;
      //alert(JSON.stringify(this.stateCodeAczIdZonalVarietyId));
      this.taskTypeSpecificationForm.patchValue(this.stateCodeAczId);
      this.loadAczByStateCodeForEdit();
      this.loadZonalCommodityByAczIdForEdit();
      this.loadPhenophaseByZonalCommodityIdForEdit();
      // this.loadZonalVarietyByZonalCommodity();
      // this.getZonalVarietyIdByStateCodeAczIdCommodityIdPhenophaseId();
      this.taskTypeSpecificationForm.patchValue({zonalCommodityID:this.regionalTaskTypeSpecification.zonalCommodityID});
      this.taskTypeSpecificationForm.patchValue({phenophaseID:this.regionalTaskTypeSpecification.phenophaseID});
    })
  }
  //Phenopahse List
  loadPhenophaseByCommodityAndVariety() {
    if (this.mode == 'add') {
      let commodityID = this.taskTypeSpecificationForm.value.commodityID;
      let varietyID = this.taskTypeSpecificationForm.value.varietyID;
      if(commodityID != null && commodityID!= undefined && varietyID !=null && varietyID!= undefined){
        return this.regionTaskTypeSpecificationService.getPhenophaseList(commodityID,varietyID).subscribe((data: {}) => {
          this.phenophaseList = data;
        });
      }
    } else {
      return this.regionTaskTypeSpecificationService.getPhenophaseList(this.commId,this.varID).subscribe((data: {}) => {
        this.phenophaseList = data;
      });
    }

}
  //Task Type List
  loadTaskType() {
    return this.regionTaskTypeSpecificationService.getTaskTypeList().subscribe((data: {}) => {
      this.taskTypeList = data;
    })
  }

  


  submitForm() {

    for (let controller in this.taskTypeSpecificationForm.controls) {
      this.taskTypeSpecificationForm.get(controller).markAsTouched();
    }

    if (this.taskTypeSpecificationForm.invalid) {
      return;
    }
    
    if (this.mode == 'add') {
      this.add();
    } else {
      this.taskTypeSpecificationForm.patchValue({status:this.regionalTaskTypeSpecification.status});
      this.update();
    }
  }


  add() {
    this.regionTaskTypeSpecificationService.CreateTaskTypeSpecification(this.taskTypeSpecificationForm.value).subscribe(res => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.taskTypeSpecificationFG();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

  update() {
    this.regionTaskTypeSpecificationService.UpdateTaskTypeSpecification(this.editId, this.taskTypeSpecificationForm.value).subscribe(res => {
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

   

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }


  modalSuccess($event: any) {
    this.router.navigate(['/region/task-type-specification']);
  }


}

import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

import { AgriCommodityService } from '../services/agri-commodity.service';
import { AgriVarietyService } from '../services/agri-variety.service';
import { AgriRemedialMeasuresService } from '../services/agri-remedial-measures.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-edit-agri-remedial-measures',
  templateUrl: './edit-agri-remedial-measures.component.html',
  styleUrls: ['./edit-agri-remedial-measures.component.scss']
})
export class EditAgriRemedialMeasuresComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  CommodityList: any = [];
  VarietyList: any = [];
  remedialMeasuresArr: any = [];
  remedialMeasuresList: any = [];
  updateRemedialMeasuresForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  
  ngOnInit() {
    this.updateForm();
    this.loadAllCommodities();
    this.loadAllVariety();
  
  }
    constructor(
    private actRoute: ActivatedRoute,
    public agriRemedialMeasuresService: AgriRemedialMeasuresService,
    public commodityService : AgriCommodityService,
    public agriVarietyService : AgriVarietyService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriRemedialMeasuresService.GetRemedialMeasures(id).subscribe((data) => {
      this.updateRemedialMeasuresForm = this.fb.group({
        commodityId: [data.commodityId,Validators.required],
        varietyId: [data.varietyId,Validators.required],
        name: [data.name,Validators.required],
        status: [data.status],
        agrochemicalStatus: [data.agrochemicalStatus]
       
        })
    })
  }

  updateForm(){
    this.updateRemedialMeasuresForm = this.fb.group({
      commodityId: ['',Validators.required],
      varietyId: ['',Validators.required],
      name: ['',Validators.required],
      status:['Inactive'],
      agrochemicalStatus: ['Banned']
  
     
    })    
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm(){ 
    
    if(this.updateRemedialMeasuresForm.get('commodityId').value == 0){
      this.errorModal.showModal('ERROR', 'Please Select Commodity', '');
      return;
    }


    if(this.updateRemedialMeasuresForm.get('varietyId').value == 0){
      this.errorModal.showModal('ERROR', 'Please Select Variety', '');
      return;
    }


  for(let controller in this.updateRemedialMeasuresForm.controls){
    this.updateRemedialMeasuresForm.get(controller).markAsTouched();
  }

  if(this.updateRemedialMeasuresForm.invalid){
    return;
  }

  var id = this.actRoute.snapshot.paramMap.get('id');
  this.agriRemedialMeasuresService.UpdateRemedialMeasures(id, this.updateRemedialMeasuresForm.value).subscribe(res => {
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

    //Commodity list
     loadAllCommodities(){
      return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
        this.CommodityList = data;
      })
    }

    //Variety list
    loadAllVariety(){
      return this.agriVarietyService.GetAllVarieties().subscribe((data: {}) => {
        this.VarietyList = data;
      })
    }

    modalSuccess($event: any) {
        this.router.navigate(['/agrochemicals/agrochemical-brand']);
    }
}

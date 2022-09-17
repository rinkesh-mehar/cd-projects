import { Component, OnInit, NgZone } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { AgriPhenophaseService } from '../services/agri-phenophase.service';
import { AgriCommodityPhenophaseService } from '../services/agri-commodity-phenophase.service';

@Component({
  selector: 'app-edit-agri-commodity-phenophase',
  templateUrl: './edit-agri-commodity-phenophase.component.html',
  styleUrls: ['./edit-agri-commodity-phenophase.component.scss']
})
export class EditAgriCommodityPhenophaseComponent implements OnInit {

  CommodityList: any = [];
  PhenophaseList: any = [];
  commodityPhenophaseArr: any = [];
  commodityPhenophaseList: any = [];
  updateCommodityPhenophaseForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit(){
    this.updateForm();
    this.loadAllCommodities();
    this.loadAllPhenophase();
  
  }
    constructor(
    private actRoute: ActivatedRoute,    
    public agriCommodityPhenophaseService: AgriCommodityPhenophaseService,
    public commodityService : AgriCommodityService,
    public agriPhenophaseService : AgriPhenophaseService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriCommodityPhenophaseService.GetAgriCommodityPhenophase(id).subscribe((data) => {
      this.updateCommodityPhenophaseForm = this.fb.group({

        commodityId: [data.commodityId,Validators.required],
        phenophaseId: [data.phenophaseId,Validators.required],
        status: [data.status]
        })
    })
  }

  updateForm(){
    this.updateCommodityPhenophaseForm = this.fb.group({

      commodityId: ['',Validators.required],
      phenophaseId: ['',Validators.required],
      status: ['Inactive']
    })    
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm(){ 

    for(let controller in this.updateCommodityPhenophaseForm.controls){
      this.updateCommodityPhenophaseForm.get(controller).markAsTouched();
    }

    if(this.updateCommodityPhenophaseForm.invalid){
      return;
    }

    if(this.updateCommodityPhenophaseForm.get('commodityId').value == 0){
      alert('Please Select Commodity');
      return;
    }
    if(this.updateCommodityPhenophaseForm.get('phenophaseId').value == 0){
      alert('Please Select Phenophase');
      return;
    }

    

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriCommodityPhenophaseService.UpdateCommodityPhenophase(id, this.updateCommodityPhenophaseForm.value).subscribe(res => {
      this.isSubmitted = true;
     
      if(res){
        this.isSuccess = res.success;
        if(res.success){
          this._statusMsg = res.message;
          this.updateCommodityPhenophaseForm.reset();

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

     //Commodity list
     loadAllCommodities(){
      return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
        this.CommodityList = data;
      })
    }

       //Phenophase list
   loadAllPhenophase(){
    return this.agriPhenophaseService.GetAllPhenophase().subscribe((data: {}) => {
      this.PhenophaseList = data;
    })
  }

}

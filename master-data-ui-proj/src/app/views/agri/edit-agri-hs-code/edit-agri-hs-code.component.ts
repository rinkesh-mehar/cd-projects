import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { AgriHsCodeService } from '../services/agri-hs-code.service';
import { AgriCommodityClassService } from '../services/agri-commodity-class.service';
import { AgriGeneralCommodityService } from '../services/agri-general-commodity.service';
import { GeneralUomService } from '../../general/services/general-uom.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-edit-agri-hs-code',
  templateUrl: './edit-agri-hs-code.component.html',
  styleUrls: ['./edit-agri-hs-code.component.scss']
})
export class EditAgriHsCodeComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  HsCodeList: any = [];
  CommodityList: any = [];
  CommodityClassList: any = [];
  GeneralCommodityList: any = [];
  UomList:any = [];
  updateHsCodeForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.updateForm()
    this.loadAllCommodities();
    this.loadAllCommodityClass();
    this.loadAllGeneralCommodity();
    this.loadAllUom();
  }

  constructor(
    private actRoute: ActivatedRoute,    
    public agriHsCodeService: AgriHsCodeService,
    public agriCommodityService: AgriCommodityService,
    public agriCommodityClassService : AgriCommodityClassService,
    public agriGeneralCommodityService : AgriGeneralCommodityService,
    public generalUomService : GeneralUomService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriHsCodeService.GetHsCode(id).subscribe((data) => {
      this.updateHsCodeForm = this.fb.group({
        hsCode: [data.hsCode,Validators.required],
        commodityId: [data.commodityId,Validators.required],
        commodityClassId: [data.commodityClassId,Validators.required],
        generalCommodityId: [data.generalCommodityId,Validators.required],
        uomId:[data.uomId,Validators.required],
        description: [data.description],
        status: [data.status]
        
        
      })
    })
  }

  updateForm(){
    this.updateHsCodeForm = this.fb.group({
      hsCode: ['',Validators.required],
      commodityId: ['',Validators.required],
      commodityClassId: ['',Validators.required],
      generalCommodityId: ['',Validators.required],
      uomId:['',Validators.required],
      description: [''],
      status: ['Inactive']
    
    })    
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm(){ 

    for(let controller in this.updateHsCodeForm.controls){
      this.updateHsCodeForm.get(controller).markAsTouched();
    }
  
    if(this.updateHsCodeForm.invalid){
      return;
    }

    if(this.updateHsCodeForm.get('commodityId').value == 0){
      this.errorModal.showModal('ERROR', 'Please Select Commodity', '');
      return;
    }

    if(this.updateHsCodeForm.get('commodityClassId').value == 0){
      this.errorModal.showModal('ERROR', 'Please Select Commodity Class', '');
      return;
    }
  
    if(this.updateHsCodeForm.get('generalCommodityId').value == 0){
      this.errorModal.showModal('ERROR', 'Please Select General Commodity', '');
      return;
    }

    if(this.updateHsCodeForm.get('uomId').value == 0){
      this.errorModal.showModal('ERROR', 'Please Select Uom', '');
      return;
    }

    

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriHsCodeService.UpdateHsCode(id, this.updateHsCodeForm.value).subscribe(res => {
      this.isSubmitted = true;
     
      if(res){
        this.isSuccess = res.success;
        if(res.success){
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    })
  }


   //Commodity list
   loadAllCommodities(){
    return this.agriCommodityService.GetAllCommoditise().subscribe((data: {}) => {
      this.CommodityList = data;
    })
  }

  loadAllCommodityClass(){
    this.agriCommodityClassService.GetAllCommodityClass().subscribe(
      data =>{
        this.CommodityClassList = data;
      }
    );
  }

  loadAllGeneralCommodity(){
    this.agriGeneralCommodityService.GetAllGeneralCommodity().subscribe(
      data =>{
        this.GeneralCommodityList = data;
      }
    );
  }

  loadAllUom(){
    this.generalUomService.GetAllUom().subscribe(
      data =>{
        this.UomList = data;
      }
    );
  }

    modalSuccess($event: any) {
        this.router.navigate(['/commodity/hs-code']);
    }
}

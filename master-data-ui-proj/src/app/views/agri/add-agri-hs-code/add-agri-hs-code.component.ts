import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { Router } from '@angular/router';
import { AgriCommodityClassService } from '../services/agri-commodity-class.service';
import { AgriHsCodeService } from '../services/agri-hs-code.service';
import { AgriGeneralCommodityService } from '../services/agri-general-commodity.service';
import { ApiUtilService } from '../../services/api-util.service';
import { GeneralUomService } from '../../general/services/general-uom.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-add-agri-hs-code',
  templateUrl: './add-agri-hs-code.component.html',
  styleUrls: ['./add-agri-hs-code.component.scss']
})
export class AddAgriHsCodeComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  CommodityList: any =[];
  CommodityClassList: any =[];
  GeneralCommodityList: any =[];
  UomList :any =[];
  hsCodeForm: FormGroup;
  uploadFile: File = null;
  imgPerview: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;
  
  ngOnInit() {
    this.addHsCode()
    this. loadAllCommodity();
    this. loadAllCommodityClass();
    this. loadAllGeneralCommodity();
    this.loadAllUom();
  
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public agriHsCodeService: AgriHsCodeService,
    public agriCommodityService: AgriCommodityService,
    public agriCommodityClassService : AgriCommodityClassService,
    public agriGeneralCommodityService : AgriGeneralCommodityService,
    public generalUomService : GeneralUomService,
    public apiUtilService: ApiUtilService
  ){ }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  addHsCode() {
    this.hsCodeForm = this.fb.group({
      hsCode: ['',Validators.required],
      commodityId:['',Validators.required],
      commodityClassId:['',Validators.required],
      generalCommodityId:['',Validators.required],
      uomId:['',Validators.required],
      description:[''],
      status: ['Inactive']
      
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {

    for (let controller in this.hsCodeForm.controls) {
      this.hsCodeForm.get(controller).markAsTouched();
    }

   if (this.hsCodeForm.invalid) {
     return;
   }
   
    if( this.hsCodeForm.get('commodityId').value == 0){
      this.hsCodeForm.get('commodityId').invalid;
      this.errorModal.showModal('ERROR', 'please select Commodity.', '');
      return;
    }

    if( this.hsCodeForm.get('commodityClassId').value == 0){
      this.hsCodeForm.get('commodityClassId').invalid;
      this.errorModal.showModal('ERROR', 'please select CommodityClass.', '');
      return;
    }

    if( this.hsCodeForm.get('generalCommodityId').value == 0){
      this.hsCodeForm.get('generalCommodityId').invalid;
      this.errorModal.showModal('ERROR', 'please select GeneralCommodity.', '');
      return;
    }

    if( this.hsCodeForm.get('uomId').value == 0){
      this.hsCodeForm.get('uomId').invalid;
      this.errorModal.showModal('ERROR', 'please select Uom.', '');
      return;
    }


    

   console.log(this.hsCodeForm.value);
    this.agriHsCodeService.CreateHsCode(this.hsCodeForm.value).subscribe(res => {
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

  loadAllCommodity(){
    this.agriCommodityService.GetAllCommoditise().subscribe(
      data =>{
        this.CommodityList = data;
      }
    );
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


  downloadExcelFormat(){
    var tableName = "agri_hs_code";
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
        this.router.navigate(['/commodity/hs-code']);
    }
}

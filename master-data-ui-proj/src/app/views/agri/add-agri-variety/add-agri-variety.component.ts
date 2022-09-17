import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AgriVarietyService } from '../services/agri-variety.service';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { AgriHsCodeService } from '../services/agri-hs-code.service';
import { ApiUtilService } from '../../services/api-util.service';
import { PricingMspGroupService } from '../../pricing/services/pricing-msp-group.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-agri-variety',
  templateUrl: './add-agri-variety.component.html',
  styleUrls: ['./add-agri-variety.component.scss']
})
export class AddAgriVarietyComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  CommodityList: any = [];
  HsCodeList: any = [];
  MspGroupList: any = [];
  varietyList: any = [];
  varietyForm: FormGroup;
  varietyArr: any = [];
  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  ngOnInit() {
    this.loadAllCommodities();
    // this.loadAllHsCode();
    // this.loadAllMspGroup();
    this.addVariety()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public agriVarietyService: AgriVarietyService,
    public commodityService: AgriCommodityService,
    public hsCodeService: AgriHsCodeService,
    public pricingMspGroup: PricingMspGroupService,
    public apiUtilService: ApiUtilService
  ) { }

  addVariety() {
    this.varietyForm = this.fb.group({
      commodityId: ['', Validators.required],
      hsCodeId: ['', Validators.required],
      domesticRestrictions: ['',Validators.required],
      internationalRestrictions: ['',Validators.required],
      name: ['',Validators.required],
      varietyCode : ['',Validators.required],
      parentVarietyID : ['',Validators.required],
      status: ['Inactive']
    })
  }
  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  
  arrayOne(n: number): any[] {
    return Array(n);
  }
  getHsCodeList() {
    if (this.varietyForm.value.commodityId) {
      this.hsCodeService.GetAllAgriHsCodeByCommodityId(this.varietyForm.value.commodityId).subscribe(data => {
        this.HsCodeList = data;
      })
    } else {
      this.HsCodeList = [];
    }
  }

  submitForm() {

    for (let controller in this.varietyForm.controls) {
      this.varietyForm.get(controller).markAsTouched();
    }

    if (this.varietyForm.invalid) {
      return;
    }

    this.isSubmitted = true
    if (this.varietyForm.get('commodityId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Commodity', '');
      return;
    }

  
  
  
  

    if (this.varietyForm.get('hsCodeId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select hsCode', '');
      return;
    }
    

    // this.agriVarietyService.CreateVariety(this.varietyForm.value).subscribe(res => {
    //   console.log('Variety added!')
    //   this.ngZone.run(() => this.router.navigateByUrl('/agri/variety'))
    // });

    for (let index = 0; index < this.varietyArr.length; index++) {
      const form = this.varietyArr[index];

      this.agriVarietyService.CreateVariety(form).subscribe(res => {
        this.isSubmitted = true;

        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this.varietyArr = [];
            this.successModal.showModal('SUCCESS', res.message, '');
          } else {
            this.errorModal.showModal('ERROR', res.error, '');
          }
        }
      });
    }

  }//submitForm

  addToList(event) {
    if ((this.varietyForm.value.name == '' || this.varietyForm.value.name == null) && (this.varietyForm.value.varietyCode == '' || this.varietyForm.value.varietyCode == null)) {
      this.isSubmitted = true
      return
    }
    console.log(this.varietyForm.value);
    this.varietyArr.push(this.varietyForm.value);
    // this.varietyForm.get('name').reset();
    // this.varietyForm.get('varietyCode').reset();
    console.log(this.varietyArr);
  }//addToList

  //Commodity list
  loadAllCommodities() {
    return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
      this.CommodityList = data;
    })
  }

  //HsCodeList 
  loadAllHsCode() {
    return this.hsCodeService.GetAllHsCode().subscribe((data: {}) => {
      this.HsCodeList = data;
    })
  }

   //MspGroupList 
   loadAllMspGroup() {
    return this.pricingMspGroup.GetAllMspGroup().subscribe((data: {}) => {
      this.MspGroupList = data;
    })
  }

  getVarietyListByCommodityId() {
    return this.agriVarietyService.getVarietyListByCommodityId(this.varietyForm.value.commodityId).subscribe((data: {}) => {
      this.varietyList = data;
    })
  }

  downloadExcelFormat(){
    var tableName = "agri_variety";
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
        this.router.navigate(['/commodity/variety']);
    }
}

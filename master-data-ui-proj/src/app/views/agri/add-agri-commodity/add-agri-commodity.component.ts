import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { ApiUtilService } from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { AgriCommodityGroupService } from '../../commodity/service/agri-commodity-group.service';
import { fileSizeValidatorForDoc } from '../../validators/fileSizeValidator.validator';

@Component({
  selector: 'app-add-agri-commodity',
  templateUrl: './add-agri-commodity.component.html',
  styleUrls: ['./add-agri-commodity.component.scss']
})
export class AddAgriCommodityComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  
  commodityForm: FormGroup;
  commodityArr: any = [];

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  _statusMsg : string;
  fileUpload: any;

  uploadedLogo: any;
  isLogo: boolean = false;
  logo: any;

  CommodityGroupList: any = [];

  ngOnInit() {
    this.addCommodity();

    this.GetAllCommodityGroup();
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public agriCommodityService: AgriCommodityService,
    public apiUtilService: ApiUtilService,
    private agriCommodityGroupService: AgriCommodityGroupService
  ){ }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  addCommodity() {
    this.commodityForm = this.fb.group({
      name: ['',Validators.required],
      scientificName: [''],
      description: [''],
      commodityGroupId: [''],
      commodityGroupIndex: [''],
      logoFile: ['', Validators.required],
      status: ['Inactive']
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }


  submitForm() {
    for (let controller in this.commodityForm.controls) {
      this.commodityForm.get(controller).markAsTouched();
    }

    if (this.commodityForm.invalid) {
      return;
    }


    this.agriCommodityService.CreateCommodity(this.commodityForm.value,this.uploadedLogo).subscribe(res => {
      this.isSubmitted = true;

      if(res) {
      
        this.isSuccess = res.success;
        if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }

      // if (res) {
      //   this.isSuccess = res.success;
      //   if (res.success) {
      //     this._statusMsg = res.message;
      //     this.commodityForm.reset();

      //     this.delay(4000).then(any => {
      //       this.isSubmitted = false;
      //       this.isSuccess = false;
      //     });
      //   } else {
      //     this._statusMsg = res.error;
      //   }
      // }
    });
  }//submit

  downloadExcelFormat() {
    var tableName = 'agri_commodity';
    this.apiUtilService.downloadExcelFormat(tableName);
  }//downloadExcelFormat


  uploadExcel(element) {
    var file: File = element.target.files[0];
    this.fileUpload = file;
  }

  submitExcelForm() {
    this.apiUtilService.uploadExcelFile(this.fileUpload).subscribe(res => {
      this.isSubmittedBulk = true;

      if (res) {
        this.isSuccessBulk = res.success;
        if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }


  GetAllCommodityGroup() {
    return this.agriCommodityGroupService.GetAllCommodityGroup().subscribe((data: {}) => {
      this.CommodityGroupList = data;
    })
  }

  onLogoChange(element){
    let file: File = element.target.files[0];
    // console.log("Size : ", this.uploadedLogo.size);
    this.commodityForm.get('logoFile').setValidators([Validators.required, fileSizeValidatorForDoc(element.target.files)]);
    this.commodityForm.get('logoFile').updateValueAndValidity();
    this.uploadedLogo = file;
    // this.logo = this.uploadedLogo.name;
  }

  modalSuccess($event: any) {
    this.router.navigate(['/commodity/commodities']);
  }
}

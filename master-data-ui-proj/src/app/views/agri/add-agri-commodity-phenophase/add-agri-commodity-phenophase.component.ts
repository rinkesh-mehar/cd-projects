import { Component, OnInit, NgZone, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { Router } from '@angular/router';
import { AgriPhenophaseService } from '../services/agri-phenophase.service';
import { AgriCommodityPhenophaseService } from '../services/agri-commodity-phenophase.service';
import { ApiUtilService } from '../../services/api-util.service';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';


@Component({
  selector: 'app-add-agri-commodity-phenophase',
  templateUrl: './add-agri-commodity-phenophase.component.html',
  styleUrls: ['./add-agri-commodity-phenophase.component.scss']
})
export class AddAgriCommodityPhenophaseComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  CommodityList: any = [];
  PhenophaseList: any = [];
  CommodityPhenophaseList: any = [];
  commodityPhenophaseForm: FormGroup;
  commodityPhenophaseArr: any = [];
  imgPerview: any;
  uploadFile: File;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  ngOnInit() {
    this.loadAllCommodities();
    this.loadAllPhenophase();
    this.addCommodityPhenophase()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public agriCommodityPhenophaseService: AgriCommodityPhenophaseService,
    public commodityService: AgriCommodityService,
    public agriPhenophaseService: AgriPhenophaseService,
    public apiUtilService: ApiUtilService
  ) { }

  addCommodityPhenophase() {
    this.commodityPhenophaseForm = this.fb.group({
      commodityId: ['', Validators.required],
      phenophaseId: ['', Validators.required],
      status:['Inactive']
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {

    for (let controller in this.commodityPhenophaseForm.controls) {
      this.commodityPhenophaseForm.get(controller).markAsTouched();
    }

    if (this.commodityPhenophaseForm.invalid) {
      return;
    }

    if (this.commodityPhenophaseForm.get('commodityId').value == 0) {
      alert('Please Select Commodity');
      return;
    }
    if (this.commodityPhenophaseForm.get('phenophaseId').value == 0) {
      alert('Please Select Phenophase');
      return;
    }


    

    this.agriCommodityPhenophaseService.CreateCommodityPhenophase(this.commodityPhenophaseForm.value).subscribe(res => {
      this.isSubmitted = true;
      if(res) {
      
        this.isSuccess = res.success;
        if (res.success) {
          this.commodityPhenophaseForm.reset();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
      // if(res){
      //   this.isSuccess = res.success;
      //   if(res.success){
      //     this._statusMsg = res.message;
      //     this.commodityPhenophaseForm.reset();

      //     this.delay(4000).then(any => {
      //         this.isSubmitted = false;
      //         this.isSuccess = false;
      //       });
      //   }else{
      //     this._statusMsg = res.error;
      //   }
      // }
    });
  }

  //Commodity list
  loadAllCommodities() {
    return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
      this.CommodityList = data;
    })
  }

  //Phenophase list
  loadAllPhenophase() {
    return this.agriPhenophaseService.GetAllPhenophase().subscribe((data: {}) => {
      this.PhenophaseList = data;
    })
  }

  downloadExcelFormat(){
    var tableName = "agri_commodity_phenophase";
    this.apiUtilService.downloadExcelFormat(tableName);
  }//downloadExcelFormat


  uploadExcel(element) {
    var file: File = element.target.files[0];
    this.fileUpload = file;
  }

  submitExcelForm() {
    this.apiUtilService.uploadExcelFile(this.fileUpload).subscribe(res => {
      this.isSubmittedBulk = true;
      if(res) {
      
        this.isSuccess = res.success;
        if (res.success) {
          this.commodityPhenophaseForm.reset();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
      // if(res){
      //   this.isSuccessBulk = res.success;
      //   if(res.success){
      //     this._statusMsg = res.message;
      //     this.commodityPhenophaseForm.reset();

      //     this.delay(4000).then(any => {
      //         this.isSubmittedBulk = false;
      //         this.isSuccessBulk = false;
      //       });
      //   }else{
      //     this._statusMsg = res.error;
      //   }
      // }
    });
  }


modalSuccess($event: any) {
  this.router.navigate(['/phenophase/commodity-phenophase']);
  }


}

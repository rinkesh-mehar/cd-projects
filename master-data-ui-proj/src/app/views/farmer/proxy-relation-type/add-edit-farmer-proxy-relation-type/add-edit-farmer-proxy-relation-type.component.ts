import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import { FarmerProxyRelationTypeService } from '../../services/farmer-proxy-relation-type.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-add-edit-farmer-proxy-relation-type',
  templateUrl: './add-edit-farmer-proxy-relation-type.component.html',
  styleUrls: ['./add-edit-farmer-proxy-relation-type.component.scss']
})
export class AddEditFarmerProxyRelationTypeComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  
  ProxyRelationTypeForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  editId: string;
  mode: string="add";
  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  ngOnInit() {
    this.ProxyRelationTypeForm = this.fb.group({
      id: [],
      proxyRelationType: ['', Validators.required],
      status: ['Inactive']

    })

    this.editId = this.actRoute.snapshot.paramMap.get('id');
    if (this.editId) {
      this.mode = "edit";
      this.farmerProxyRelationTypeService.GetProxyRelationType(this.editId).subscribe(data => {
        this.ProxyRelationTypeForm.patchValue(data);
      })
    }
  }

  constructor(
    public fb: FormBuilder,
    public farmerProxyRelationTypeService: FarmerProxyRelationTypeService,
    private actRoute: ActivatedRoute,
    public apiUtilService: ApiUtilService,
    public router: Router

  ) { }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  submitForm() {

    for (let controller in this.ProxyRelationTypeForm.controls) {
      this.ProxyRelationTypeForm.get(controller).markAsTouched();
    }
    if (this.ProxyRelationTypeForm.invalid) {
      return;
    }
    if (this.mode == 'add') {
      this.add();
    } else {
      this.update();
    }
  }

  add() {
    this.farmerProxyRelationTypeService.CreateProxyRelationType(this.ProxyRelationTypeForm.value).subscribe(res => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          // this.ProxyRelationTypeForm.reset();
          this.mode="add";
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

  update() {
    this.farmerProxyRelationTypeService.UpdateProxyRelationType(this.ProxyRelationTypeForm.value.id, this.ProxyRelationTypeForm.value).subscribe(res => {
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

  downloadExcelFormat(){
    var tableName = "farmer_proxy_relation_type";
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
    this.router.navigate(['/farmer/proxy-relation-type']);
  }
}

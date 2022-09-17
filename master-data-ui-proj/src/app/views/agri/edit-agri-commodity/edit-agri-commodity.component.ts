import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriCommodityService } from '../services/agri-commodity.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { AgriCommodityGroupService } from '../../commodity/service/agri-commodity-group.service';
import { fileSizeValidatorForDoc } from '../../validators/fileSizeValidator.validator';

@Component({
  selector: 'app-edit-agri-commodity',
  templateUrl: './edit-agri-commodity.component.html',
  styleUrls: ['./edit-agri-commodity.component.scss']
})
export class EditAgriCommodityComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  CommodityList: any = [];
  updateCommodityForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  CommodityGroupList: any = [];

  uploadedLogo: any;
  isLogoHidden: boolean = false;
  logo: any;

  ngOnInit() {
    this.updateForm();
    this.GetAllCommodityGroup();
  }

  constructor(
      private actRoute: ActivatedRoute,
      public agriCommodityService: AgriCommodityService,
      public fb: FormBuilder,
      private ngZone: NgZone,
      private router: Router,
      private agriCommodityGroupService: AgriCommodityGroupService
  ) {
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriCommodityService.GetCommodity(id).subscribe((data) => {
      this.updateCommodityForm = this.fb.group({
        name: [data.name, Validators.required],
        scientificName: [data.scientificName],
        description: [data.description],
        commodityGroupId: [data.commodityGroupId],
        commodityGroupIndex: [data.commodityGroupIndex],
        logoFile: [''],
        status: [data.status],
      });
      this.logo = data.logo;
    });
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  updateForm() {
    this.updateCommodityForm = this.fb.group({
      name: ['', Validators.required],
      scientificName: [''],
      description: [''],
      commodityGroupId: [''],
      commodityGroupIndex: [''],
      logoFile: [''],
      status: ['Inactive']
    });
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }


  submitForm() {
    for (let Controller in this.updateCommodityForm.controls) {
      this.updateCommodityForm.get(Controller).markAllAsTouched();
    }

    if (this.updateCommodityForm.invalid) {
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriCommodityService.UpdateCommodity(id, this.updateCommodityForm.value,this.uploadedLogo).subscribe(res => {
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

  GetAllCommodityGroup() {
    return this.agriCommodityGroupService.GetAllCommodityGroup().subscribe((data: {}) => {
      this.CommodityGroupList = data;
    })
  }

  onLogoChange(element){
    this.isLogoHidden = true;
    let file: File = element.target.files[0];
    // console.log("Size : ", this.uploadedLogo.size);
    this.updateCommodityForm.get('logoFile').setValidators([Validators.required, fileSizeValidatorForDoc(element.target.files)]);
    this.updateCommodityForm.get('logoFile').updateValueAndValidity();
    this.uploadedLogo = file;
    // this.logo = this.uploadedLogo.name;
  }

  modalSuccess($event: any) {
    this.router.navigate(['/commodity/commodities']);
  }
}

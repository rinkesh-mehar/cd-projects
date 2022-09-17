import {Component, OnInit, ViewChild} from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { GeoStateService } from '../../../geo/services/geo-state.service';
import {ActivatedRoute, Router} from '@angular/router';
import { GeneralBankNameService } from '../../../general/services/general-bank-name.service';
import { RegionBankService } from '../../services/region-bank.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-edit-region-bank',
  templateUrl: './add-edit-region-bank.component.html',
  styleUrls: ['./add-edit-region-bank.component.scss']
})
export class AddEditRegionBankComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  StateList: any = [];
  BankNameList: any = [];
  BankForm: FormGroup;
  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  editId: string;
  mode: string = "add";

  constructor(public fb: FormBuilder, private actRoute: ActivatedRoute,
    public geoStateService: GeoStateService, public generalBankNameService: GeneralBankNameService,public apiUtilService: ApiUtilService,
    public regionalBankService: RegionBankService, public router: Router) { }

  ngOnInit() {
    this.BankForm = this.fb.group({
      id: [''],
      stateCode: ['', Validators.required],
      // regionId: ['', Validators.required],
      bankId: ['', Validators.required],
      status: ['Inactive']
    
    })
    this.editId = this.actRoute.snapshot.paramMap.get('id');
    if (this.editId) {
      this.mode = "edit";
      this.regionalBankService.GetBank(this.editId).subscribe(data => {
        this.BankForm.patchValue(data);
      })
    }
    this.loadAllState();
    // this.loadAllRegion();
    this.loadAllBankName();
  }


  submitForm() {


    for (let controller in this.BankForm.controls) {
      this.BankForm.get(controller).markAsTouched();
    }

    if (this.BankForm.invalid) {
      return;
    }

    let observable;

    if (this.mode == 'add') {
      observable = this.regionalBankService.CreateBank(this.BankForm.value)
    } else {
      observable = this.regionalBankService.UpdateBank(this.BankForm.value.id, this.BankForm.value)
    }

    observable.subscribe(res => {
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


  // //Region list
  // loadAllRegion() {
  //   return this.geoRegionService.GetAllRegion().subscribe((data: {}) => {
  //     this.RegionList = data;
  //   })
  // }

  // State list
  loadAllState() {
    return this.geoStateService.GetAllState().subscribe((date: {}) => {
      this.StateList = date;
    });
  }

  loadAllBankName() {
    return this.generalBankNameService.GetAllBankName().subscribe((data: {}) => {
      this.BankNameList = data;
    });
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  downloadExcelFormat() {
    var tableName = 'regional_bank';
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

  modalSuccess($event: any) {
    this.router.navigate(['/regional/bank']);
  }
}

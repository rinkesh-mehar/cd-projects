import {Component, OnInit, Input, Output, EventEmitter, SimpleChanges, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import { GeneralBankBranchService } from '../../services/general-bank-branch.service';
import { GeneralBankNameService } from '../../services/general-bank-name.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { GeoDistrictService } from '../../../geo/services/geo-district.service';


@Component({
  selector: 'app-add-edit-bank-branch',
  templateUrl: './add-edit-bank-branch.component.html',
  styleUrls: ['./add-edit-bank-branch.component.scss']
})
export class AddEditBankBranchComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  BankBranchForm: FormGroup;
  
  DistrictList: any = [];
  BankNameList: any;
  editBankBranchId: any;
  mode: any = 'add';
  BankBranch: any;

  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  constructor(public formBuilder: FormBuilder,public generalBankBranchService: GeneralBankBranchService,
    private generalBankNameService: GeneralBankNameService, private actRoute: ActivatedRoute,
    public geoDistrictService : GeoDistrictService,
              public apiUtilService: ApiUtilService, public router: Router) {


    this.createBankBranchForm();


  }
  getChanges() {
    console.log(this.BankBranchForm.value)
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  createBankBranchForm() {
    this.BankBranchForm = this.formBuilder.group({
      id: [],
      bankId: ['', Validators.required],
      districtCode: ['', Validators.required],
      name: ['', Validators.required],
      ifscCode: ['', Validators.required],
      status : ['Inactive']
    })
  }

  ngOnInit() {
    this.loadAllBankName();
    this.loadAllDistrict();

    this.editBankBranchId = this.actRoute.snapshot.paramMap.get('id');

    if (this.editBankBranchId) {

      this.mode = "edit";
      this.generalBankBranchService.GetBankBranch(this.editBankBranchId).subscribe(data => {
        this.BankBranch = data;
        this.BankBranchForm = this.formBuilder.group({
          
          id: [], 
          bankId: ['', Validators.required],
          districtCode:['', Validators.required],
          name: ['', Validators.required],
          ifscCode: ['', Validators.required],
          status: [],
          
        })

        this.BankBranchForm.patchValue(this.BankBranch);
      })


    }

  }

 
  loadAllBankName() {
    return this.generalBankNameService.GetAllBankName().subscribe((data: any) => {
      this.BankNameList = data;
    }, err => {
      alert(err)
    })
  }

  //District list
 loadAllDistrict(){
  return this.geoDistrictService.GetAllDistrict().subscribe((data: {}) => {
    this.DistrictList = data;
  })
}

  submitBankBranchForm() {

    for (let controller in this.BankBranchForm.controls) {
      this.BankBranchForm.get(controller).markAsTouched();
    }
    if (this.BankBranchForm.invalid) {
      return;
    }

    if (this.BankBranchForm.get('bankId').value == 0) {
      alert('Please Select Bank');
      return;
    }
    
    if (this.mode == "edit") {
      this.updateBankBranch();
    } else {
      this.addBankBranch();
    }
  }
  addBankBranch() {
    this.generalBankBranchService.CreateBankBranch(this.BankBranchForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.BankBranch = {};
          this.mode = "add";
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    })
  }
  updateBankBranch() {
    this.generalBankBranchService.UpdateBankBranch(this.BankBranchForm.value.id, this.BankBranchForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.BankBranch = {};
          this.mode = "add";
          // this.BankBranchForm.reset();

          this.createBankBranchForm();

          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    })
  } nInit() {
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }


  downloadExcelFormat(){
    var tableName = "general_bank_branch";
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
    this.router.navigate(['/general/bank-branch']);
  }
}

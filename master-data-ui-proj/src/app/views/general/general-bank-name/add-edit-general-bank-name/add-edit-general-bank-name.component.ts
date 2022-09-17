import {Component, OnInit, ViewChild} from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { GeneralBankNameService } from '../../services/general-bank-name.service';
import {ActivatedRoute, Router} from '@angular/router';
import { GeneralBankCategoryService } from '../../services/general-bank-category.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-edit-general-bank-name',
  templateUrl: './add-edit-general-bank-name.component.html',
  styleUrls: ['./add-edit-general-bank-name.component.scss']
})
export class AddEditGeneralBankNameComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  BankNameForm: FormGroup;

  BankCategoryList: any;
  editBankNameId: any;
  mode: any = "add";
  BankName: any;

  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  constructor(public formBuilder: FormBuilder, public generalBankNameService: GeneralBankNameService,private generalBankCategoryService: GeneralBankCategoryService,
    private actRoute: ActivatedRoute,public apiUtilService: ApiUtilService, public router: Router ) {


    this.createBankNameForm();


  }
  getChanges() {
    console.log(this.BankNameForm.value)
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  createBankNameForm() {
    this.BankNameForm = this.formBuilder.group({
      id: [],
      bankCategoryId: ['', Validators.required],
      name: ['', Validators.required],
      status: ['Inactive']
    })
  }

  ngOnInit() {
    this.loadAllBankCategory();

    this.editBankNameId = this.actRoute.snapshot.paramMap.get('id');

    if (this.editBankNameId) {

      this.mode = "edit";
      this.generalBankNameService.GetBankName(this.editBankNameId).subscribe(data => {
        this.BankName = data;
        this.BankNameForm = this.formBuilder.group({
          id: [],
          bankCategoryId: ['', Validators.required],
          name: ['', Validators.required],
          status: []
        })

        this.BankNameForm.patchValue(this.BankName);
      })


    }

  }


  loadAllBankCategory() {
    return this.generalBankCategoryService.GetAllBankCategory().subscribe((data: any) => {
      this.BankCategoryList = data;
    }, err => {
      alert(err)
    })
  }

  submitBankNameForm() {

    for (let controller in this.BankNameForm.controls) {
      this.BankNameForm.get(controller).markAsTouched();
    }
    if (this.BankNameForm.invalid) {
      return;
    }

    if (this.BankNameForm.get('bankCategoryId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select BankCategory', '');
      return;
    }
    for (let controller in this.BankNameForm.controls) {
      this.BankNameForm.get(controller).markAsTouched();
    }
    if (this.BankNameForm.invalid) {
      return;
    }
    if (this.mode == 'edit') {
      this.updateBankName();
    } else {
      this.addBankName();
    }
  }

  addBankName() {
    this.generalBankNameService.CreateBankName(this.BankNameForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.BankName = {};
          this.mode = 'add';
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    });
  }

  updateBankName() {
    this.generalBankNameService.UpdateBankName(this.BankNameForm.value.id, this.BankNameForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.BankName = {};
          this.mode = 'add';
          // this.BankNameForm.reset();

          this.createBankNameForm();

          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    }, err => {
      console.log(err);
    });
  }

  nInit() {
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }


  downloadExcelFormat() {
    var tableName = 'general_bank';
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
    this.router.navigate(['/general/bank-name']);
  }
}

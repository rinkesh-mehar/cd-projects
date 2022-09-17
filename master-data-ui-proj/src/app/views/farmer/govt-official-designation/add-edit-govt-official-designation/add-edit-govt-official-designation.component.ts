import {Component, OnInit, Input, Output, EventEmitter, SimpleChanges, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import { FarmerGovtDepartmentService } from '../../services/farmer-govt-department.service';
import { FarmerGovtOfficialDesignationService } from '../../services/farmer-govt-official-designation.service';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-edit-govt-official-designation',
  templateUrl: './add-edit-govt-official-designation.component.html',
  styleUrls: ['./add-edit-govt-official-designation.component.scss']
})
export class AddEditGovtOfficialDesignationComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  
  GovtOfficialDesignationForm: FormGroup;

  DepartmentList: any;
  editGovtOfficialDesignationId: any;
  mode: any = 'add';
  GovtOfficialDesignation: any;

  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  constructor(public formBuilder: FormBuilder,public farmerGovtOfficialDesignationService: FarmerGovtOfficialDesignationService,
    private farmerGovtDepartmentService:FarmerGovtDepartmentService, private actRoute: ActivatedRoute,
              public apiUtilService: ApiUtilService, public router: Router) {


    this.createGovtOfficialDesignationForm();


  }
  getChanges() {
    console.log(this.GovtOfficialDesignationForm.value)
  }

  createGovtOfficialDesignationForm() {
    this.GovtOfficialDesignationForm = this.formBuilder.group({
      id: [],
      departmentId: ['', Validators.required],
      name: ['', Validators.required],
      status: ['Inactive']
    })
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }
  
  
  ngOnInit() {
    this.loadAllDepartment();

    this.editGovtOfficialDesignationId = this.actRoute.snapshot.paramMap.get('id');

    if (this.editGovtOfficialDesignationId) {

      this.mode = "edit";
      this.farmerGovtOfficialDesignationService.GetGovtOfficialDesignation(this.editGovtOfficialDesignationId).subscribe(data => {
        this.GovtOfficialDesignation = data;
        this.GovtOfficialDesignationForm = this.formBuilder.group({
          id: [],
          departmentId: ['', Validators.required],
         name: ['', Validators.required],
         status: ['']
        })

        this.GovtOfficialDesignationForm.patchValue(this.GovtOfficialDesignation);
      })


    }

  }

 
  loadAllDepartment() {
    return this.farmerGovtDepartmentService.GetAllGovtDepartment().subscribe((data: any) => {
      this.DepartmentList = data;
    }, err => {
      alert(err)
    })
  }

  submitGovtOfficialDesignationForm() {

    for (let controller in this.GovtOfficialDesignationForm.controls) {
      this.GovtOfficialDesignationForm.get(controller).markAsTouched();
    }
    if (this.GovtOfficialDesignationForm.invalid) {
      return;
    }

    if (this.GovtOfficialDesignationForm.get('departmentId').value == 0) {
      alert('Please Select Department');
      return;
    }
    
    if (this.mode == "edit") {
      this.updateGovtOfficialDesignation();
    } else {
      this.addGovtOfficialDesignation();
    }
  }
  addGovtOfficialDesignation() {
    this.farmerGovtOfficialDesignationService.CreateGovtOfficialDesignation(this.GovtOfficialDesignationForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.GovtOfficialDesignation = {};
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
  updateGovtOfficialDesignation() {
    this.farmerGovtOfficialDesignationService.UpdateGovtOfficialDesignation(this.GovtOfficialDesignationForm.value.id, this.GovtOfficialDesignationForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          this.GovtOfficialDesignation = {};
          this.mode = "add";
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
    var tableName = "farmer_govt_official_designation";
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
    this.router.navigate(['/farmer/govt-official-designation']);
  }
}

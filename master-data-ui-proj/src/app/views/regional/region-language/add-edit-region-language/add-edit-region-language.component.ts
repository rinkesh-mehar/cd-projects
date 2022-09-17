import {Component, OnInit, ViewChild} from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { GeoStateService } from '../../../geo/services/geo-state.service';
import { RegionLanguageService } from '../../services/region-language.service';
import { FarmerLanguageService } from '../../../farmer/services/farmer-language.service';
import {ActivatedRoute, Router} from '@angular/router';
import { ApiUtilService } from '../../../services/api-util.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-edit-region-language',
  templateUrl: './add-edit-region-language.component.html',
  styleUrls: ['./add-edit-region-language.component.scss']
})
export class AddEditRegionLanguageComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  RegionList: any = [];
  StateList: any = [];
  LanguageList: any = [];
  languageForm: FormGroup;
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
    public geoStateService: GeoStateService, public farmerLanguageService: FarmerLanguageService,public apiUtilService: ApiUtilService,
    public regionLanguageService: RegionLanguageService, public router: Router) { }

  ngOnInit() {
    this.languageForm = this.fb.group({
      id: [''],
      stateCode: ['', Validators.required],
      // regionId: ['', Validators.required],
      languageId: ['', Validators.required],
      status: ['Inactive']
    
    })
    this.editId = this.actRoute.snapshot.paramMap.get('id');
    if (this.editId) {
      this.mode = "edit";
      this.regionLanguageService.GetLanguage(this.editId).subscribe(data => {
        this.languageForm.patchValue(data);
      })
    }
    this.loadAllState();
    // this.loadAllRegion();
    this.loadAllLanguage();
  }


  submitForm() {


    for (let controller in this.languageForm.controls) {
      this.languageForm.get(controller).markAsTouched();
    }

    if (this.languageForm.invalid) {
      return;
    }

    let observable;

    if (this.mode == 'add') {
      observable = this.regionLanguageService.CreateLaguage(this.languageForm.value)
    } else {
      observable = this.regionLanguageService.UpdateLanguage(this.languageForm.value.id, this.languageForm.value)
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
    })
  }

  loadAllLanguage() {
    return this.farmerLanguageService.GetAllLanguage().subscribe((data: {}) => {
      this.LanguageList = data;
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  downloadExcelFormat(){
    var tableName = "regional_language";
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
        this.router.navigate(['/regional/language']);
    }
}

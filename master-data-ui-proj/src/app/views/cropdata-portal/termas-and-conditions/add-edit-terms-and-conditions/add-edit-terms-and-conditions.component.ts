import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { fileSizeValidatorForDoc } from '../../../validators/fileSizeValidator.validator';
import { newsReportsService } from '../../services/news-reports.service';
import { TermsAndConditionsService } from '../../services/terms-and-conditions.service';

@Component({
  selector: 'app-add-edit-terms-and-conditions',
  templateUrl: './add-edit-terms-and-conditions.component.html',
  styleUrls: ['./add-edit-terms-and-conditions.component.css']
})
export class AddEditTermsAndConditionsComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  @ViewChild('closebutton') closebutton;

  addEditTACFormGroup: FormGroup;
  platFormList: any =[];
  mode: string = 'add';
  tacFile: any;
  ppFile: any;
  tacUrl: any;
  ppUrl: any;
  isTacUrl: boolean = true;
  isPpUrl: boolean = true;
  editId: string;
  isSuccess: boolean = false;

  constructor(public fb: FormBuilder, private actRoute: ActivatedRoute,
              public newsService: newsReportsService, public termsAndConditionsService: TermsAndConditionsService, public router: Router) {
  }

  ngOnInit(): void {

      this.addEditTAC();
          
      this.getPlatForm();

      this.editId = this.actRoute.snapshot.paramMap.get('id');

      if (this.editId) {
                  
          this.isTacUrl = false;
          this.isPpUrl = false;
          this.mode = 'edit';

          this.addEditTACFormGroup.get('tacFile').clearValidators();
          this.addEditTACFormGroup.get('tacFile').updateValueAndValidity();


          this.addEditTACFormGroup.get('ppFile').clearValidators();
          this.addEditTACFormGroup.get('ppFile').updateValueAndValidity();

          this.termsAndConditionsService.getTermsAndConditionsById(this.editId).subscribe(data => {
                  
                  this.tacUrl = data.tandCUrl;
                  this.ppUrl = data.privacyPolicyUrl;
                  this.addEditTACFormGroup.patchValue(data);
        
              
          });
      }
      
  }

  addEditTAC() {
    this.addEditTACFormGroup = this.fb.group({
      platformId: ['', Validators.required],
      tacFile: ['', Validators.required],
      ppFile: ['', Validators.required],
      version: ['', Validators.required],
    })
  }

  getPlatForm() {
      return this.newsService.getPlatFormList().subscribe((data: {}) => {
          this.platFormList = data;

      });
  }

  /*Upload tac file*/
  tacFileFileChange(element) {
      console.log("inside tacFileFileChange..!!");
      if(this.mode === 'edit'){
          this.isTacUrl = true;
      }

      let file: File = element.target.files[0];
      this.addEditTACFormGroup.get('tacFile').setValidators([Validators.required, fileSizeValidatorForDoc(element.target.files)]);
      this.addEditTACFormGroup.get('tacFile').updateValueAndValidity();
      this.tacFile = file;

  }

    /*Upload pp file*/
    ppFileFileChange(element) {
      console.log("inside ppFileFileChange..!!");
      if(this.mode === 'edit'){
          this.isPpUrl = true;
      }

      let file: File = element.target.files[0];
      this.addEditTACFormGroup.get('ppFile').setValidators([Validators.required, fileSizeValidatorForDoc(element.target.files)]);
      this.addEditTACFormGroup.get('ppFile').updateValueAndValidity();
      this.ppFile = file;

  }

  submitForm() {

      for (const controller in this.addEditTACFormGroup.controls) {
          this.addEditTACFormGroup.get(controller).markAllAsTouched();
      }if (this.addEditTACFormGroup.invalid) {
          return;
      }

      if (this.mode == 'add') {
          this.add();
      } else {
          this.update();
      }
  }

  add() {
      return this.termsAndConditionsService.addTermsAndConditions(this.addEditTACFormGroup.value, this.tacFile, this.ppFile).subscribe(res => {
          
          if (res.success) {
              this.successModal.showModal('SUCCESS', res.message, '');
          } else {
              this.errorModal.showModal('ERROR', res.error, '');
          }
      });
  }

  update() {
    
      return this.termsAndConditionsService.updateTermsAndConditions(this.editId, this.addEditTACFormGroup.value, this.tacFile, this.ppFile).subscribe(res => {
          
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
  

  modalSuccess($event: any) {
      this.router.navigate(['/cropdata-portal/terms-and-conditions']);
  }

}

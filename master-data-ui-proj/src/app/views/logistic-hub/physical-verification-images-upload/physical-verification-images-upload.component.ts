import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { PhysicalVerificationImagesUploadService } from '../service/physical-verification-images-upload.service'
import { Observable } from 'rxjs';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { fileSizeValidator } from '../../validators/fileSizeValidator';


@Component({
  selector: 'app-physical-verification-images-upload',
  templateUrl: './physical-verification-images-upload.component.html',
  styleUrls: ['./physical-verification-images-upload.component.scss']
})
export class PhysicalVerificationImagesUploadComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  hubPhysicalVerficationImg: FormGroup;
  hubId: any;
  WMS_007Image: File = null;
  WMS_009Image: File = null;
  WMS_015Image: File = null;
  WMS_031Image: File = null;
  WMS_041Image: File = null;
  WMS_043Image: File = null;
  WMS_045Image: File = null;
  WMS_047Image: File = null;
  WMS_051Image: File = null;
  WMS_075Image: File = null;
  WMS_076Image: File = null;
  WMS_077Image: File = null;
  WMS_083Image: File = null;
  WMS_085Image: File = null;
  WMS_086Image: File = null;
  WMS_087Image: File = null;
  WMS_089Image: File = null;
  WMS_090Image: File = null;
  WMS_097Image: File = null;
  WMS_098Image: File = null;
  WMS_070Image: File = null;


  filesToUpload = [];

  constructor(
    public formBuilder: FormBuilder,
    private pviuService: PhysicalVerificationImagesUploadService,
    private activatedRoute: ActivatedRoute,
    private route: Router
  ) {
    this.hubId = this.activatedRoute.snapshot.paramMap.get("id");


  }

  ngOnInit(): void {
    this.imageUploadVerification();
  }



  upload_WMS_007Image(element) {
    var file: File = element.target.files[0];
    console.log('file size -> ', file.size);
    this.hubPhysicalVerficationImg.get('WMS_007').setValidators([Validators.required, fileSizeValidator(element.target.files)]);
    this.hubPhysicalVerficationImg.get('WMS_007').updateValueAndValidity();
    this.WMS_007Image = file;
  }

  upload_WMS_009Image(element) {
    var file: File = element.target.files[0];
    this.WMS_009Image = file;
  }

  upload_WMS_015Image(element) {
    var file: File = element.target.files[0];
    this.WMS_015Image = file;
  }

  upload_WMS_031Image(element) {
    var file: File = element.target.files[0];
    this.WMS_031Image = file;
  }

  upload_WMS_041Image(element) {
    var file: File = element.target.files[0];
    this.WMS_041Image = file;
  }

  upload_WMS_043Image(element) {
    var file: File = element.target.files[0];
    this.WMS_043Image = file;
  }

  upload_WMS_045Image(element) {
    var file: File = element.target.files[0];
    this.WMS_045Image = file;
  }

  upload_WMS_047Image(element) {
    var file: File = element.target.files[0];
    this.WMS_047Image = file;
  }

  upload_WMS_051Image(element) {
    var file: File = element.target.files[0];
    this.WMS_051Image = file;
  }

  upload_WMS_075Image(element) {
    var file: File = element.target.files[0];
    this.WMS_075Image = file;
  }

  upload_WMS_076Image(element) {
    var file: File = element.target.files[0];
    this.WMS_076Image = file;
  }

  upload_WMS_077Image(element) {
    var file: File = element.target.files[0];
    this.WMS_077Image = file;
  }

  upload_WMS_083Image(element) {
    var file: File = element.target.files[0];
    this.WMS_083Image = file;
  }

  upload_WMS_085Image(element) {
    var file: File = element.target.files[0];
    this.WMS_085Image = file;
  }

  upload_WMS_086Image(element) {
    var file: File = element.target.files[0];
    this.WMS_086Image = file;
  }

  upload_WMS_087Image(element) {
    var file: File = element.target.files[0];
    this.WMS_087Image = file;
  }

  upload_WMS_089Image(element) {
    var file: File = element.target.files[0];
    this.WMS_089Image = file;
  }

  upload_WMS_090Image(element) {
    var file: File = element.target.files[0];
    this.WMS_090Image = file;
  }

  upload_WMS_097Image(element) {
    var file: File = element.target.files[0];
    this.WMS_097Image = file;
  }

  upload_WMS_098Image(element) {
    var file: File = element.target.files[0];
    this.WMS_098Image = file;
  }

  upload_WMS_070Image(element) {
    var file: File = element.target.files[0];
    this.WMS_098Image = file;
  }

  imageUploadVerification() {
    this.hubPhysicalVerficationImg = this.formBuilder.group({
      WMS_007: [''],
      WMS_009: [''],
      WMS_015: [''],
      WMS_031: [''],
      WMS_041: [''],
      WMS_043: [''],
      WMS_045: [''],
      WMS_047: [''],
      WMS_051: [''],
      WMS_075: [''],
      WMS_076: [''],
      WMS_077: [''],
      WMS_083: [''],
      WMS_085: [''],
      WMS_086: [''],
      WMS_087: [''],
      WMS_089: [''],
      WMS_090: [''],
      WMS_097: [''],
      WMS_098: [''],
      WMS_070: ['']
    });
  }

  update() {
    console.log("in component");
    return this.pviuService.addLogisticHubImages(this.hubId, this.WMS_007Image, this.WMS_009Image, this.WMS_015Image, this.WMS_031Image, this.WMS_041Image, this.WMS_043Image,
      this.WMS_045Image, this.WMS_047Image, this.WMS_051Image, this.WMS_075Image, this.WMS_076Image, this.WMS_077Image,
      this.WMS_083Image, this.WMS_085Image, this.WMS_086Image, this.WMS_087Image, this.WMS_089Image, this.WMS_090Image, this.WMS_097Image, this.WMS_098Image, this.WMS_070Image)
      .subscribe(data => {
        if (data.success) {
          this.successModal.showModal('SUCCESS', data.message, '');
        } else {
          this.errorModal.showModal('ERROR', data.message, '');
        }

      });
  }

  submitBandForm(f: NgForm) {
     this.update();
  }

  modalSuccess($event: any) {
    this.route.navigate(['/logistic-hub/lh-doc-approved-list']);
  }



}
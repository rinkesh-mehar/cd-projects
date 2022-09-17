import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { PhysicalVerificationImagesUploadService } from '../service/physical-verification-images-upload.service'
import { Observable } from 'rxjs';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-pv-lh-images-upload',
  templateUrl: './pv-lh-images-upload.component.html',
  styleUrls: ['./pv-lh-images-upload.component.scss']
})
export class PvLhImagesUploadComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  @ViewChild('inputFile') myInputVariable: ElementRef;

  hubPhysicalVerficationImg: FormGroup;
  hubId: any;
  selectedFiles: FileList;
  lhImageUploadCount: number =0;
  fileList: File[] = [];
  otherLhMetaData: any = {};
  otherPictureId: any = 'undefined';


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


    this.hubPhysicalVerficationImg.get('WMS_109').disable();
    this.getOtherLhMetaData();

  }
  //Validations
  imageUploadVerification() {
    this.hubPhysicalVerficationImg = this.formBuilder.group({
      WMS_100: ['', Validators.required], //
      WMS_101: ['', Validators.required], //
      WMS_102: ['', Validators.required], //
      WMS_110: ['', Validators.required], //
      WMS_111: ['', Validators.required], //
      WMS_103: ['', Validators.required], //
      WMS_104: ['', Validators.required], //
      WMS_105: ['', Validators.required], //
      WMS_106: ['', Validators.required], //
      WMS_107: ['', Validators.required], //
      WMS_108: ['', Validators.required], //
      WMS_109: ['', ]
    });
  }

  uploadLhPictures(element, metaExcerptDescripion) {
    let fileSizeErrorCount = 0;
    this.selectedFiles = element.target.files;
    if (this.selectedFiles.length != 3) {
      alert("3 Images Required.");
      this.hubPhysicalVerficationImg.get(metaExcerptDescripion).setValue('');
    } else {
      for (let i = 0; i < this.selectedFiles.length; i++) {
        if (this.selectedFiles[i].size > 2097152) {
          fileSizeErrorCount++;
        }
      }
      if (fileSizeErrorCount === 0) {
        this.fileList = [];
        this.fileList.push(element.target.files[0]);
        this.fileList.push(element.target.files[1]);
        this.fileList.push(element.target.files[2]);
        return this.pviuService.uploadLogisticHubImages(this.fileList, this.hubId, metaExcerptDescripion)
          .subscribe(data => {
            if (data) {
              this.lhImageUploadCount++;
            }
          });
      } else {
        alert("File Size must be less than 2MB");
        this.hubPhysicalVerficationImg.get(metaExcerptDescripion).setValue('');
      }
    }
  }

  /** Only upload other pictures*/
  uploadOtherPictures(event, pictureId, metaExcerptDescripion){

    let fileSizeErrorCount = 0;
    this.selectedFiles = event.target.files;
    this.otherPictureId = 'undefined';
    if (this.selectedFiles.length != 3) {
      alert('3 Images Required.');
      this.hubPhysicalVerficationImg.get(metaExcerptDescripion).setValue('');
    } else {
      for (let i = 0; i < this.selectedFiles.length; i++) {
        if (this.selectedFiles[i].size > 2097152) {
          fileSizeErrorCount++;
        }
      }
      if (fileSizeErrorCount === 0) {
        this.fileList = [];
        this.fileList.push(event.target.files[0]);
        this.fileList.push(event.target.files[1]);
        this.fileList.push(event.target.files[2]);
        return this.pviuService.uploadOtherImages(this.fileList, this.hubId, pictureId)
            .subscribe(data => {
              if (data) {
                this.lhImageUploadCount++;
                this.reset();
              }
            });
      } else {
        alert('File Size must be less than 2MB');
        this.hubPhysicalVerficationImg.get(metaExcerptDescripion).setValue('');
      }
    }
  }

  reset() {
    this.myInputVariable.nativeElement.value = '';
  }

  selectionAction(event){
    this.otherPictureId = event.target.value;
    if (event.target.value != 'undefined'){
      this.hubPhysicalVerficationImg.get('WMS_109').enable();
    } else {
      this.hubPhysicalVerficationImg.get('WMS_109').disable();
    }
  }
  submitForm(f: NgForm) {
    for (let controller in this.hubPhysicalVerficationImg.controls) {
      this.hubPhysicalVerficationImg.get(controller).markAsTouched();
    }
    if (this.hubPhysicalVerficationImg.invalid) {
      return;
    }
    this.update();
  }
update() {
    return this.pviuService.updateStagePhysicalVerificationComplete(this.hubId)
    .subscribe(data => {
      if (data.success) {
        this.successModal.showModal('SUCCESS', data.message, '');
        // alert(data.message);
      } else {
        this.errorModal.showModal('ERROR', data.message, '');
      }
    });
  }

  modalSuccess($event: any) {
    this.route.navigate(['/logistic-hub/lh-doc-approved-list']);
  }

  getOtherLhMetaData(){
    return this.pviuService.getOtherLhMetaData().subscribe(data => {
      this.otherLhMetaData = data['otherLhData'];
    })
}
}

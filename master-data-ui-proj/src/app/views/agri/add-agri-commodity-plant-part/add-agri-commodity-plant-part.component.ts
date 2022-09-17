import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { Router } from '@angular/router';
import { AgriPhenophaseService } from '../services/agri-phenophase.service';
import { AgriCommodityPlantPartService } from '../services/agri-commodityPlantPart.service';
import { AgriPlantPartService } from '../services/agri-plant-part.service';
import { DomSanitizer } from '@angular/platform-browser';
import { environment } from '../../../../environments/environment';
import { ApiUtilService } from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-agri-commodity-plant-part',
  templateUrl: './add-agri-commodity-plant-part.component.html',
  styleUrls: ['./add-agri-commodity-plant-part.component.scss']
})
export class AddAgriCommodityPlantPartComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  CommodityList: any = [];
  PhenophaseList: any = [];
  PlantPartList: any = [];
  commodityPlantPartForm: FormGroup;
  commodityPlantPartArr: any = [];

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  uploadFile: File = null;
  imgPerview: any;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  ngOnInit() {
    this.loadAllCommodities();
    // this.loadAllPhenophase();
    this.loadAllPlantPart();
    this.addCommodityPlantPart()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public agriCommodityPlantPartService: AgriCommodityPlantPartService,
    public commodityService: AgriCommodityService,
    public agriPhenophaseService: AgriPhenophaseService,
    public agriPlantPartService: AgriPlantPartService,
    private sanitizer: DomSanitizer,
    public apiUtilService: ApiUtilService
  ) { }

  addCommodityPlantPart() {
    this.commodityPlantPartForm = this.fb.group({
      commodityId: ['', Validators.required],
      phenophaseId: ['', Validators.required],
      plantPartId: ['', Validators.required],
      status: ['Inactive']
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {

    for (let controller in this.commodityPlantPartForm.controls) {
      this.commodityPlantPartForm.get(controller).markAsTouched();
    }

    if (this.commodityPlantPartForm.invalid) {
      return;
    }

    if (this.commodityPlantPartForm.get('commodityId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Commodity', '');
      return;
    }
    if (this.commodityPlantPartForm.get('phenophaseId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Phenophase', '');
      return;
    }

    if (this.commodityPlantPartForm.get('plantPartId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select PlantPart', '');
      return;
    }

    

    this.agriCommodityPlantPartService.CreateCommodityPlantPart(this.commodityPlantPartForm.value, this.uploadFile).subscribe(res => {
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

  //Commodity list
  loadAllCommodities() {
    return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
      this.CommodityList = data;
    });
  }

  // //Phenophase list
  // loadAllPhenophase() {
  //   return this.agriPhenophaseService.GetAllPhenophase().subscribe((data: {}) => {
  //     this.PhenophaseList = data;
  //   })
  // }

  //PlantPart list
  loadAllPlantPart() {
    return this.agriPlantPartService.GetAllPlantPart().subscribe((data: {}) => {
      this.PlantPartList = data;
    });
  }

  fileChange(element) {
    var file: File = element.target.files[0];
    var idxDot = file.name.lastIndexOf('.') + 1;
    var extFile = file.name.substr(idxDot, file.name.length).toLowerCase();
    if (extFile == 'jpeg') {
      this.compressImage(element);
    } else {
      element.target.value = null;
      this.errorModal.showModal('ERROR', 'Invalid format File Format, Only jpeg files are allowed!', '');
      return;
    }
  }


  compressImage(element) {

    const width = environment.imageResizeWidth;
    let height = environment.imageResizeHeight;

    const fileName = element.target.files[0].name;
    const reader: FileReader = new FileReader();
    reader.readAsDataURL(element.target.files[0]);
    reader.onload = event => {
      const img: any = new Image();
      img.src = (<FileReader>event.target).result;
      img.onload = () => {
        const elem = document.createElement('canvas');
        if (environment.preserveImageAspectRatio) {
          const scaleFactor = width / img.width;
          height = img.height * scaleFactor;
        }

        elem.width = width;
        elem.height = height;

        const ctx = elem.getContext('2d');
        ctx.drawImage(img, 0, 0, width, height);
        ctx.canvas.toBlob((blob) => {
          const file = new File([blob], fileName, {
            type: 'image/jpeg',
            lastModified: Date.now()
          });
          this.uploadFile = file;
        }, 'image/jpeg', 1);
        this.imgPerview = ctx.canvas.toDataURL();
      },
          reader.onerror = error => console.log(error);
    };
  }

  loadAllCommodityByPhenophase() {
    let phenophaseId = this.commodityPlantPartForm.value.commodityId;
    this.commodityService.getCommodityByPhenophaseId(phenophaseId).subscribe(
        (data: {}) => {
          this.PhenophaseList = data;
          console.log(this.PhenophaseList);
        }
    );
  }


  downloadExcelFormat() {
    var tableName = 'agri_commodity_plant_part';
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
    this.router.navigate(['/commodity/commodity-plant-part']);
  }
}

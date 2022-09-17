import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { AgriPhenophaseService } from '../services/agri-phenophase.service';
import { AgriPlantPartService } from '../services/agri-plant-part.service';
import { AgriCommodityPlantPartService } from '../services/agri-commodityPlantPart.service';
import { DomSanitizer } from '@angular/platform-browser';
import { environment } from '../../../../environments/environment';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-edit-agri-commodity-plant-part',
  templateUrl: './edit-agri-commodity-plant-part.component.html',
  styleUrls: ['./edit-agri-commodity-plant-part.component.scss']
})
export class EditAgriCommodityPlantPartComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  CommodityList: any = [];
  PhenophaseList: any = [];
  PlantPartList: any = [];
  commodityPlantPartArr: any = [];
  commodityPlantPartList: any = [];
  updateCommodityPlantPartForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  uploadFile: File = null;
  imgPerview: any;

  ngOnInit() {
    this.updateForm();
    this.loadAllCommodities();
    // this.loadAllPhenophase();
    this.loadAllPlantPart();

  }
  constructor(
    private actRoute: ActivatedRoute,
    public agriCommodityPlantPartService: AgriCommodityPlantPartService,
    public commodityService: AgriCommodityService,
    public agriPhenophaseService: AgriPhenophaseService,
    public agriPlantPartService: AgriPlantPartService,
    public fb: FormBuilder,
    private sanitizer: DomSanitizer,
    public router: Router
  ) {

  }

  updateForm() {
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriCommodityPlantPartService.GetCommodityPlantPart(id).subscribe((data) => {
      this.updateCommodityPlantPartForm = this.fb.group({

        commodityId: [data.commodityId, Validators.required],
        phenophaseId: [data.phenophaseId, Validators.required],
        plantPartId: [data.plantPartId, Validators.required],
        imageURL: [data.imageURL],
        status: [data.status]
      })

      this.loadAllCommodityByPhenophase();
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }


  submitForm() {

    for (let controller in this.updateCommodityPlantPartForm.controls) {
      this.updateCommodityPlantPartForm.get(controller).markAsTouched();
    }

    if (this.updateCommodityPlantPartForm.invalid) {
      return;
    }

    if (this.updateCommodityPlantPartForm.get('commodityId').value == 0) {
      alert('Please Select Commodity');
      return;
    }
    if (this.updateCommodityPlantPartForm.get('phenophaseId').value == 0) {
      alert('Please Select Phenophase');
      return;
    }

    if (this.updateCommodityPlantPartForm.get('plantPartId').value == 0) {
      alert('Please Select PlantPart');
      return;
    }

    for (let controller in this.updateCommodityPlantPartForm.controls) {
      this.updateCommodityPlantPartForm.get(controller).markAsTouched();
    }

    if (this.updateCommodityPlantPartForm.invalid) {
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.agriCommodityPlantPartService.UpdateCommodityPlantPart(id, this.updateCommodityPlantPartForm.value, this.uploadFile).subscribe(res => {
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
    })
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
    })
  }
  //Upload file
  fileChange(element) {
    var file: File = element.target.files[0];
    var idxDot = file.name.lastIndexOf(".") + 1;
    var extFile = file.name.substr(idxDot, file.name.length).toLowerCase();
    this.compressImage(element);
    
    
    
    
    
    
    
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
    let phenophaseId = this.updateCommodityPlantPartForm.value.commodityId;
    this.commodityService.getCommodityByPhenophaseId(phenophaseId).subscribe(
      (data: {}) => {
        this.PhenophaseList = data;
        console.log(this.PhenophaseList);
      }
    );
  }

    modalSuccess($event: any) {
      this.router.navigate(['/commodity/commodity-plant-part']);
    }
}

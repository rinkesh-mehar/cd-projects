import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegionSeasonService } from '../services/region-season.service';
import { GeoRegionService } from '../../geo/services/geo-region.service';
import { AgriSeasonService } from '../../agri/services/agri-season.service';
import { GeoStateService } from '../../geo/services/geo-state.service';
import { ApiUtilService } from '../../services/api-util.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-region-season',
  templateUrl: './add-region-season.component.html',
  styleUrls: ['./add-region-season.component.scss']
})
export class AddRegionSeasonComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  stateList: any = [];
  seasonList: any = [];
  seasonForm: FormGroup;
  seasonArr: any = [];
  startWeekList: number[] = [];
  EndWeekList: number[] = [];
  uploadFile: File = null;
  imgPerview: any;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  isSubmittedBulk: boolean = false;
  isSuccessBulk: boolean = false;
  fileUpload: any;

  ngOnInit() {
    this.addSeason();
    this.loadAllState();
    this.loadAllSeason();
    for (let i = 1; i <= 52; i++) {
      this.startWeekList.push(i);
      this.EndWeekList.push(i);

    }
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public regionSeasonService: RegionSeasonService,
    public geoStateService: GeoStateService,
    public agriSeasonService: AgriSeasonService,
    public apiUtilService: ApiUtilService
    
  ) { }

  addSeason() {
    this.seasonForm = this.fb.group({
      stateCode: ['', Validators.required],
      seasonId: ['', Validators.required],
      startWeek: ['', Validators.required],
      endWeek: ['', Validators.required],
      status: ['Inactive'],
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {

    if (this.seasonForm.get('stateCode').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select State', '');
      return;
    }
    if (this.seasonForm.get('seasonId').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select Season', '');
      return;
    }

    if (this.seasonForm.get('startWeek').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select StartWeek', '');
      return;
    }

    if (this.seasonForm.get('endWeek').value == 0) {
      this.errorModal.showModal('ERROR', 'Please Select EndWeek', '');
      return;
    }

    for (let controller in this.seasonForm.controls) {
      this.seasonForm.get(controller).markAsTouched();
    }

    if (this.seasonForm.invalid) {
      return;
    }

    this.regionSeasonService.CreateSeason(this.seasonForm.value).subscribe(res => {
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

  //Statelist
  loadAllState() {
    return this.geoStateService.GetAllState().subscribe((data: {}) => {
      this.stateList = data;
    })
  }

  //Regionlist
  loadAllSeason() {
    return this.agriSeasonService.GetAllSeasons().subscribe((data: {}) => {
      this.seasonList = data;
    })
  }

  downloadExcelFormat(){
    var tableName = "regional_season";
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
        this.router.navigate(['/regional/season']);
    }
}

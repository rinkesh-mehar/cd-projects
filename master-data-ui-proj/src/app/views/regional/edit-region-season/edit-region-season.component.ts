import {Component, OnInit, NgZone, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { RegionSeasonService } from '../services/region-season.service';
import { GeoRegionService } from '../../geo/services/geo-region.service';
import { AgriSeasonService } from '../../agri/services/agri-season.service';
import { GeoStateService } from '../../geo/services/geo-state.service';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';


@Component({
  selector: 'app-edit-region-season',
  templateUrl: './edit-region-season.component.html',
  styleUrls: ['./edit-region-season.component.scss']
})
export class EditRegionSeasonComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  stateList : any = [];
  seasonList : any = [];
  SeasonList: any = [];
  startWeekList :  number[] = [];
  EndWeekList :  number[] = [];
  updateSeasonForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;
  
  ngOnInit() {
    this.updateForm();
    this.loadAllState();
    this.loadAllSeason()
    for (let i = 1; i <= 52; i++) {
      this.startWeekList.push(i);
      this.EndWeekList.push(i);

    }
  }

  constructor(
    private actRoute: ActivatedRoute,
    public regionSeasonService: RegionSeasonService,
    public geoStateService: GeoStateService,
    public agriSeasonService: AgriSeasonService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router
  ) { 
    var id = this.actRoute.snapshot.paramMap.get('id');
    this.regionSeasonService.GetSeason(id).subscribe((data) => {
      this.updateSeasonForm = this.fb.group({
        stateCode: [data.stateCode,Validators.required],
        seasonId: [data.seasonId,Validators.required],
        startWeek: [data.startWeek,Validators.required],
        endWeek: [data.endWeek,Validators.required],
        status: [data.status]
       })
    })
  }

  updateForm(){
    this.updateSeasonForm = this.fb.group({
      stateCode: ['',Validators.required],
      seasonId: ['',Validators.required],
      startWeek: ['',Validators.required],
      endWeek: ['',Validators.required],
      status: []
      
      
    })    
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm(){ 

    if(this.updateSeasonForm.get('stateCode').value == 0){
      this.errorModal.showModal('ERROR', 'Please Select State', '');

      return;
    }
    if(this.updateSeasonForm.get('seasonId').value == 0){
      this.errorModal.showModal('ERROR', 'Please Select Season', '');
      return;
    }

    if(this.updateSeasonForm.get('startWeek').value == 0){
      this.errorModal.showModal('ERROR', 'Please Select StartWeek', '');
      return;
    }

    if(this.updateSeasonForm.get('endWeek').value == 0){
      this.errorModal.showModal('ERROR', 'Please Select EndWeek', '');
      return;
    }

    for(let controller in this.updateSeasonForm.controls){
      this.updateSeasonForm.get(controller).markAsTouched();
    }
  
    if(this.updateSeasonForm.invalid){
      return;
    }

    var id = this.actRoute.snapshot.paramMap.get('id');
    this.regionSeasonService.UpdateSeason(id, this.updateSeasonForm.value).subscribe(res => {
      this.isSubmitted = true;
      if(res){
        this.isSuccess = res.success;
        if(res.success){
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    });
  }

    //Regionlist
    loadAllState(){
      return this.geoStateService.GetAllState().subscribe((data: {}) => {
        this.stateList = data;
        })
      }
  
       //Regionlist
    loadAllSeason(){
      return this.agriSeasonService.GetAllSeasons().subscribe((data: {}) => {
        this.seasonList = data;
        });
      }

  modalSuccess($event: any) {
    this.router.navigate(['/regional/season']);
  }
}

import { Component, OnInit, NgZone } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { GeoRegionService } from '../services/geo-region.service';
import { GeoStateService } from '../services/geo-state.service';

@Component({
  selector: 'app-add-geo-region',
  templateUrl: './add-geo-region.component.html',
  styleUrls: ['./add-geo-region.component.scss']
})
export class AddGeoRegionComponent implements OnInit {
  regionForm: FormGroup;
  regionArr: any = [];
  StateList: any = [];

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg : string;

  ngOnInit() {
    this.addRegion();
    this.loadAllState()
  }

  constructor(
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    public geoRegionService: GeoRegionService,
    public geoStateService : GeoStateService
  ){ }

  addRegion() {
    this.regionForm = this.fb.group({
      // tileId: ['',Validators.required],
      stateCode: ['',Validators.required],
      name: ['',Validators.required],
      description: [''],
      status: ['Inactive']
    })
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitForm() {

    if(this.regionForm.get('stateCode').value == 0){
      alert('Please Select State');
      return;
    }

    for(let controller in this.regionForm.controls){
      this.regionForm.get(controller).markAsTouched();
    }
  
    if(this.regionForm.invalid){
      return;
    }

    this.geoRegionService.CreateRegion(this.regionForm.value).subscribe(res => {
      this.isSubmitted = true;
     
      if(res){
        this.isSuccess = res.success;
        if(res.success){
          this._statusMsg = res.message;
          this.regionForm.reset();

          this.delay(4000).then(any => {
              this.isSubmitted = false;
              this.isSuccess = false;
            });
        }else{
          this._statusMsg = res.error;
        }
      }
    });
  }

     //State list
     loadAllState(){
      return this.geoStateService.GetAllState().subscribe((data: {}) => {
        this.StateList = data;
      })
    }

}

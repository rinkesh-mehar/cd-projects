import { Component, ElementRef, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FlsVisitsService } from '../services/fls-visits.service';
import { UserRightsService } from '../../services/user-rights.service';

@Component({
  selector: 'app-fls-visits',
  templateUrl: './fls-visits.component.html',
  styleUrls: ['./fls-visits.component.scss']
})
export class FlsVisitsComponent implements OnInit {

  FLSVisitForm: FormGroup;
  regionList: any =[];
  mapBaseUrls : string = 'http://143.10.0.11:8095/get/task-view?region_id=';
  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  show = true;
  checked: boolean = false;
  

  constructor(public fb: FormBuilder, private FlsVisitsService: FlsVisitsService, private userRightsService: UserRightsService,private hostElement: ElementRef,) {}

  ngOnInit(): void {
    this.getAllGeoRegion();
    this.searchFLSVisit();
  }

  searchFLSVisit() {
    this.FLSVisitForm = this.fb.group({
      region_id: ['', Validators.required],      
    })
  }


  getAllGeoRegion() {
    return this.FlsVisitsService.getAllGeoRegion().subscribe((data: {}) => {
        this.regionList = data;
    });
  }


  submitForm(){

    for(let controller in this.FLSVisitForm.controls){
      this.FLSVisitForm.get(controller).markAsTouched();
    }

    if(this.FLSVisitForm.invalid){
      return;
    }

    console.log("region_id : " + this.FLSVisitForm.value.region_id);
    console.log(this.mapBaseUrls.substr(this.mapBaseUrls.lastIndexOf('=')+1));

    this.show = false;
    this.checked = true;
    setTimeout(() => {
        this.checked = false;
    }, 5000)
    if (this.mapBaseUrls.substr(this.mapBaseUrls.lastIndexOf('=')+1) == ''){
      
      this.mapBaseUrls = this.mapBaseUrls + this.FLSVisitForm.value.region_id;   
      const iframe = this.hostElement.nativeElement.querySelector('iframe');
      iframe.src = this.mapBaseUrls;
    } else if (this.mapBaseUrls.substr(this.mapBaseUrls.lastIndexOf('=')+1) != '' && this.mapBaseUrls.substr(this.mapBaseUrls.lastIndexOf('=')+1) == this.FLSVisitForm.value.region_id){
   
      const iframe = this.hostElement.nativeElement.querySelector('iframe');
      iframe.src = this.mapBaseUrls;
    } else if (this.mapBaseUrls.substr(this.mapBaseUrls.lastIndexOf('=')+1) != '' && this.mapBaseUrls.substr(this.mapBaseUrls.lastIndexOf('=')+1) != this.FLSVisitForm.value.region_id){
    
      this.mapBaseUrls = this.mapBaseUrls.substr(0,this.mapBaseUrls.lastIndexOf('=')+1) + this.FLSVisitForm.value.region_id;
      console.log(this.mapBaseUrls);
      
      const iframe = this.hostElement.nativeElement.querySelector('iframe');
      
      iframe.src = this.mapBaseUrls;
    }


    // if(this.urlName != ''){
    //   console.log("urlName 2" + this.urlName);
    //   this.urlNull = true;
    // }
  }




  // getUrl(region_id: number){

  //   return this.FlsVisitsService.getUrl(region_id).subscribe(res => {
    
  //     this.isSubmitted = true;    

  //     console.log(res.data.file_url);
  //     if(res) {
        
  //       this.urlName = res.data.file_url;
  //       console.log("new: " + this.urlName);
  //     }
  //   });
  
  // }

}

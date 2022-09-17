import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import * as moment from 'moment';
import { ImdService } from '../services/imd.service';
declare var $;

@Component({
  selector: 'app-imd',
  templateUrl: './imd.component.html',
  styleUrls: ['./imd.component.scss']
})
export class ImdComponent implements OnInit {
  isDetailSectionHidden: boolean = true;
  BlockInfoSectionHidden : boolean = true;
  imdForm: FormGroup;

  pdfUrl : string = '';
  pdfdownloadLink : string = '';

  currentYear :any;
  

  // stateList: any =[{stateId:101,name:"Maharashtra"},{stateId:102,name:"Goa"}];
  stateList: any =[];
  districtList: any = [];
  blockList: any = [];
  yearList: any =[];
  monthList: any = [];
  // monthList: any = [{ month:1,name:"January"},{  month:2,name:"February"},{  month:3,name:"March"},{  month:4,name:"April"},{  month:5,name:"May"},{  month:6,name:"June"},{  month:7,name:"July"},{  month:8,name:"August"},{  month:9,name:"September"},{  month:10,name:"October"},{  month:11,name:"November"},{  month:12,name:"December"}];
  // imdDetailList: any =[{dt:"1",english:"https://vadimdez.github.io/ng2-pdf-viewer/assets/pdf-test.pdf",regional:"R"},{dt:"2",english:"E",regional:"R"},{dt:"3",english:"E",regional:"R"},{dt:"4",english:"E",regional:"R"},{dt:"5",english:"E",regional:"R"},{dt:"6",english:"E",regional:"R"},{dt:"7",english:"E",regional:"R"},{dt:"8",english:"E",regional:"R"},{dt:"9",english:"E",regional:"R"},{dt:"11",english:"E",regional:"R"},{dt:"12",english:"E",regional:"R"},{dt:"13",english:"E",regional:"R"},{dt:"14",english:"E",regional:"R"},{dt:"15",english:"E",regional:"R"},{dt:"16",english:"E",regional:"R"},{dt:"17",english:"E",regional:"R"},{dt:"18",english:"E",regional:"R"},{dt:"19",english:"E",regional:"R"},{dt:"20",english:"E",regional:"R"},{dt:"21",english:"E",regional:"R"},{dt:"22",english:"E",regional:"R"},{dt:"23",english:"E",regional:"R"},{dt:"24",english:"E",regional:"R"},{dt:"25",english:"E",regional:"R"},{dt:"26",english:"E",regional:"R"},{dt:"27",english:"E",regional:"R"},{dt:"28",english:"E",regional:"R"},{dt:"29",english:"E",regional:"R"},{dt:"30",english:"E",regional:"R"},{dt:"31",english:"E",regional:"R"}]; 
  imdDetailList: any =[]

  @Input() max: any;
  previous = new Date();

  constructor(public fb: FormBuilder,private imdService: ImdService) { }

  ngOnInit(): void {
    this.imdFG();

    this.getStateList();

    // this.currentYear = new Date().getFullYear();

    // for (var i = 2020; i <= this.currentYear; i++) {
    //   this.yearList.push(i);
    // }
  }

  imdFG() {
    this.imdForm = this.fb.group({
      stateId: ['', Validators.required],
      districtId: [''],
      blockId: [''],
      year: ['',Validators.required],
      month: ['' ,Validators.required],
    
      
    })
  }

  getImdDetails(): void {
    this.imdService.getImdDetails(this.imdForm.value.stateId,this.imdForm.value.districtId,this.imdForm.value.blockId,this.imdForm.value.year,this.imdForm.value.month)
        .subscribe(data => this.imdDetailList = data);
  }

  getStateList(): void {
    this.imdService.getStateList()
        .subscribe(data => this.stateList = data);
  }

  getDistrictListByStateId(): void {

    this.isDetailSectionHidden=true;

    this.imdForm.patchValue({ districtId: '', blockId: '', year: '', month: ''})

    this.monthList = [];
    this.imdService.getDistrictListByStateId(this.imdForm.value.stateId)
        .subscribe(data => this.districtList = data);
  }

  getBlockListByStateIdAndDistrictId(): void {

    this.isDetailSectionHidden=true;

    this.imdForm.patchValue({blockId: '', year: '', month: ''});
    this.imdService.getBlockListByStateIdAndDistrictId(this.imdForm.value.stateId,this.imdForm.value.districtId)
        .subscribe(data => this.blockList = data);
  }

  getYearListByStateIdAndDistrictIdAndBlockId(): void {

    this.isDetailSectionHidden=true;

    this.imdForm.patchValue({year: '', month: ''});
    if(this.imdForm.value.stateId == null){
      this.imdForm.patchValue({stateId: ''});
    }

    if(this.imdForm.value.districtId == null){
      this.imdForm.patchValue({districtId: ''});
    }

    if(this.imdForm.value.blockId == null){
      this.imdForm.patchValue({blockId: ''});
    }
  
    this.imdService.getYearListByStateIdAndDistrictIdAndBlockId(this.imdForm.value.stateId,this.imdForm.value.districtId,this.imdForm.value.blockId)
        .subscribe(data => this.yearList = data);
  }

  getMonthListByStateIdAndDistrictIdAndBlockIdAndYear(): void {

    this.isDetailSectionHidden=true;

    if(this.imdForm.value.stateId == null){
      this.imdForm.patchValue({stateId: ''});
    }

    if(this.imdForm.value.districtId == null){
      this.imdForm.patchValue({districtId: ''});
    }

    if(this.imdForm.value.blockId == null){
      this.imdForm.patchValue({blockId: ''});
    }
    this.imdForm.patchValue({month: ''});
    this.imdService.getMonthListByStateIdAndDistrictIdAndBlockIdAndYear(this.imdForm.value.stateId,this.imdForm.value.districtId,this.imdForm.value.blockId,this.imdForm.value.year)
        .subscribe(data => this.monthList = data);
  }

  onChangeMonth(){
    this.isDetailSectionHidden=true;
  }

  submitForm(){

    console.log("inside submitForm");
    for(let controller in this.imdForm.controls){

      this.imdForm.get(controller).markAsTouched();

    }


    if(this.imdForm.invalid){
      console.log("inside 1st if");
      return;
    }

    // const date = new Date(this.imdForm.value.monthYear);
    // // const day = date.getDate();
    // const month = date.getMonth() + 1;
    // const year = date.getFullYear();
    // this.imdForm.value.monthYear =  year + "/" + month;

    // console.log(JSON.stringify(this.imdForm.value));

    if(this.imdForm.value.stateId == null){
      this.imdForm.patchValue({stateId: ''});
    }

    if(this.imdForm.value.districtId == null){
      this.imdForm.patchValue({districtId: ''});
    }

    if(this.imdForm.value.blockId == null){
      this.imdForm.patchValue({blockId: ''});
    }

    this.isDetailSectionHidden = false;

    this.getImdDetails();

  }

  onClickPdf(pdfUrl){  
    console.log("pdfUrl : " + pdfUrl); 
    // document.getElementById('pdfModal').click();
    
  //  this.pdfUrl = '';
  //  this.pdfUrl = "https://docs.google.com/viewer?embedded=true&url=" + pdfUrl;

  
  this.pdfdownloadLink = pdfUrl;
  this.pdfUrl = pdfUrl + "#pagemode=thumbs&scrollbar=1&toolbar=1&statusbar=1&messages=1&navpanes=1";
   console.log(this.pdfUrl); 

   $('#pdfViewer').attr("data", this.pdfUrl); 


   var objValue ="<object data='"+this.pdfUrl+"' type='application/pdf' width='100%' height='750' id='pdfViewer'><p>This browser does not support inline PDFs. Please download the PDF to view it: <a href='"+this.pdfdownloadLink+"'>Download PDF</a></p></object>"

   $('.pdfAdd').html(objValue);

  }

  onReset(){
    this.isDetailSectionHidden=true;
  }

}

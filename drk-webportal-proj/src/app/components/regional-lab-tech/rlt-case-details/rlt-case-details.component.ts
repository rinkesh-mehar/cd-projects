import { Component, OnInit, TemplateRef, IterableDiffers, OnDestroy } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl } from '@angular/forms';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { SuccessModalComponent } from '../../modal-components/success-modal/success-modal.component';
import { WarningModalComponent } from '../../modal-components/warning-modal/warning-modal.component';
import { RltCaseDetailsService } from '../rlt-case-details/rlt-case-details.service'
import { AssignRoleRlmComponent } from '../../modal-components/assign-role-rlm/assign-role-rlm.component';
import { RegionalLabData, Comment, Results, Spot, SpotStress, StressSeverity, GeneralInformation, StressDetail, StressSymptomsModel, CropInfo, IrrigationDetails, FertilizerDetails, SeedTreatment, RltUser, RlmReassign } from './rlt-case-details.model';
import { RltformModel } from './rlt-case-details-form.model';

import { ErrorMessages } from '../../form-validation-messages';
import { DiagnoseDetails, GeneralInfoMaster , ResultsMaster, Symptom } from './rlt-new-case-details.model';
import {AgriQualityParameterList, CommodityInfo} from './rlt.model';

declare var $: any, c3, slick;
@Component({
  selector: 'app-rlt-case-details',
  templateUrl: './rlt-case-details.component.html',
  styleUrls: ['./rlt-case-details.component.less']
})
export class RltCaseDetailsComponent implements OnInit, OnDestroy {


  CaseDetailsForm: FormGroup;
  qualityAssessmentForm: FormGroup;
  spotSubmitted: boolean[] = [];
  assessmentSubmitted: boolean[] = [];
  SpotLevelForm = new FormArray([]);
  RecommendLevelForm = new FormArray([]);
  healthData: any[];
  healthFormArray = new FormArray([]);
  resultFormArray = new FormArray([]);
  CaseDetailsFormContent: FormGroup;
  SpotDetailsForm: FormGroup;
  recommendSubmit: boolean = false;
  private crops: any;
  modalRef: BsModalRef;
  public saveSpotStatus:boolean = false;
  submitted = false;
  public showSaveSpotMSG: boolean = true;
  slickModal: any;
  minDate: Date;
  maxDate: Date;
  myDateValue: Date;
  oneAtATime: boolean = true;
  public casedetails = {} as DiagnoseDetails;
  public generalInfoMaster = {} as GeneralInfoMaster;
  public generalInfo = {} as GeneralInformation;
//public symptomMaster = {} as SymptomMaster[];

  public resultsMaster = {} as ResultsMaster;


  public cropInfo = {} as CropInfo;
  public irrigationDetails = {} as IrrigationDetails;
  public fertilizerDetails = {} as FertilizerDetails;
  public seedTreatment = {} as SeedTreatment;
  public remedialMeasures = {} as SeedTreatment;
  public cropdetail: string[];
  public Variety: string[];
  public Phenophase: string[];
  public incidencedata: any[];
  public severitydata: StressSeverity[] = [];
  public stressdata: StressDetail[] = [];
  public notification: string[];
  public slides: string[];
  public spothealthdetails: Spot[];
  public recommendationdetails = {} as Results;
  public comment: Comment;
  public stressdetails: string[];
  public healthdetails: string[];
  public stressTypeArray: string[];
  //Error Messages
  public dropdownerrormessage: string;
  public textboxError: string;
  public multiselectError: string;
  public iterableDiffer: any;
  public spotChanges: any[] = [];
  public taskId: number;
  private flag: number;
  public logedInUser = {} as any;
  public stressSymptomsList: Symptom[];
  public viewOnly: boolean = false;
  public rltUserlist: RltUser[];
  public rltUser: number;
  public rlmReassign: RlmReassign;
  public savedSpots:any[] =[];
  public selectedItemvalue;
  public httperrorresponsemessages: string;
  public bandNotFoundMessages: string;
  public recommendationStatus = false;
  public invalidvalueerrormessage: string;
  public invalidComments = false;
  public qualityAssurance: [];
  finalQualityAssurance: any[] =[];
  public agriQualityParameterList: AgriQualityParameterList[] = [];
  public commodityInfo: CommodityInfo;
  public fetchedBand: any;
  public bandScreenSelected: boolean = false;
  private getBandButtonFlag: boolean = true;

  constructor(
    private formBuilder: FormBuilder,
    private rltCaseDetailsService: RltCaseDetailsService,
    private modalService: BsModalService,
    private router: Router,
    private route: ActivatedRoute,
    private _iterableDiffers: IterableDiffers,

  ) {

    this.iterableDiffer = this._iterableDiffers.find([]).create(null)
  }

  ngOnInit(){

    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;
    this.bandNotFoundMessages = ErrorMessages.bandNotFound;

    this.route.params.subscribe(params => {
      this.taskId = params['taskId'];
      this.flag = params['flag'];
    });

    var status = localStorage.getItem("diagnosisStatus");
    if(status && status == "1"){
      this.viewOnly =true;
    }else{
      this.viewOnly =false;
    }


    this.logedInUser = JSON.parse(localStorage.getItem("userData"));

    this.dropdownerrormessage = ErrorMessages.dropdownError;
    this.textboxError = ErrorMessages.textboxError;
    this.invalidvalueerrormessage = ErrorMessages.invalidvalueError;
    this.multiselectError = ErrorMessages.multiselectError;
    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0)
    });


    //================================ Digonise Details Data ========================== //
    this.rltCaseDetailsService.getRltGeneraldetails(this.taskId).subscribe((data2) => {

      this.generalInfoMaster = data2;
      this.generalInfo = this.generalInfoMaster.generalInformation;

    }, (err) => {
      this.errorMsg(this.httperrorresponsemessages, "/diagnose/" + this.taskId)
    });



    //================================ Digonise Details Data ========================== //


    //================================ Digonise Details Data ========================== //
    this.rltCaseDetailsService.getRltcasedetails(this.taskId,this.logedInUser.userId).subscribe((data) => {

      this.casedetails = data;


      //this.casedetails.spots;
      //this.casedetails.results
      //this.slides = this.casedetails.ariealPhotos;
      this.severitydata = this.casedetails.masterData.stressSeverity;
      this.stressdata = this.casedetails.masterData.stressDetails;
      this.incidencedata = [];
      this.incidencedata.push({ id: 0, value: "No" });
      this.incidencedata.push({ id: 1, value: "Yes" });
      this.stressSymptomsList = this.casedetails.masterData.stressSymptomsModel;
      //this.generalInfo = this.casedetails.generalInformation

      this.getspothealthList(this.casedetails.spots);
      //this.getrecommendeList(this.casedetails.results);

    }, (err) => {
      this.errorMsg(this.httperrorresponsemessages, "/diagnose/" + this.taskId)
    });



    //================================ Digonise Details Data ========================== //


    //================================ Recommendation Details Data ========================== //
    this.rltCaseDetailsService.getRltRecommendationdetails(this.taskId,this.logedInUser.userId).subscribe((data3) => {

      this.resultsMaster = data3;
      this.getrecommendeList(this.resultsMaster);

    }, (err) => {
      this.errorMsg(this.httperrorresponsemessages, "/diagnose/" + this.taskId)
    });



    //================================ Recommendation Details Data ========================== //




  }



  public selectedItem(value){
    this.selectedItemvalue = value;
  }
  public selectedFrontItem(event){

  }
  public selectedRightItem(event){

  }

  public validateQuality(minValue, maxValue, event, qualityAssuranceSelectedValue) {
    console.log(minValue, maxValue, event.target.value);
    var minValueNew = Number(minValue);
    var maxValueNew = Number(maxValue);
    var value = Number(event.target.value);
    if (value >= minValueNew && value <= maxValueNew) {
      $(event.target).removeClass('is-invalid');
      $(event.target).parent().find('.text-danger').text('');
      console.log('success');
      this.setQualityAssessmentForm(qualityAssuranceSelectedValue, value);
    }else{
      this.getBandButtonFlag = true;
      $(event.target).addClass('is-invalid');
      console.log('error')
      $(event.target).parent().find('.text-danger').text('Please enter between ' + minValueNew + '-' + maxValueNew+' value')
    }
  }

  setQualityAssessmentForm(qualityAssuranceSelectedValue, valueEntered) {

    for (let i = 0; i < this.finalQualityAssurance.length; i++) {
      if (this.finalQualityAssurance[i].parameterID == qualityAssuranceSelectedValue.parameterId) {
        this.finalQualityAssurance.splice(i, 1);
        break;
      }
    }
    var data: AgriQualityParameterList = {
      'parameterID': qualityAssuranceSelectedValue.parameterId,
      'value': valueEntered,
    };

    this.finalQualityAssurance.push(data);

    console.log('setQualityAssessmentForm', this.finalQualityAssurance);
    if (this.finalQualityAssurance.length > 0) {
      this.getBandButtonFlag = false;
    }
  }

  submitValidateBand() {
    console.log('validate form is ', this.finalQualityAssurance);
  }

  //Quality Assessment
  public getParameterList() {
    this.rltCaseDetailsService.getParameterList(this.taskId).subscribe(
      (data) => {
        this.qualityAssurance = data;
        console.log(this.qualityAssurance);
      }, (err) => {
        this.errorMsg(this.httperrorresponsemessages, "/diagnose/" + this.taskId)
      });
  }

  public getCommodityInfo() {

    this.rltCaseDetailsService.getCommodityInfo(this.taskId).subscribe(
      (data) => {
        this.commodityInfo = data;
        console.log('commodity info ', this.commodityInfo);
      }, (err) => {
        this.errorMsg(this.httperrorresponsemessages, "/diagnose/" + this.taskId)
      });
  }

  getBand(showBandModal: TemplateRef<any>) {

    // define a date object variable that will take the current system date
    const todaydate = new Date();

    // find the year of the current date
    const oneJan =  new Date(todaydate.getFullYear(), 0, 1);

    // calculating number of days in given year before a given date
    const numberOfDays =  Math.floor((Number(todaydate) - Number(oneJan)) / (24 * 60 * 60 * 1000));

    // adding 1 since to current date and returns value starting from 0
    const currentWeek = Math.ceil(( todaydate.getDay() + 1 + numberOfDays) / 7);

    console.log('current week is ----------> ', currentWeek);
    if (this.finalQualityAssurance.length == 0) {
      const showContent = {
        title: 'Warning',
        content: 'Please Enter Value In Field',
        action: '/diagnose/' + this.taskId + '/' + this.flag
        // heading: 'Found Band',
        // content: 'Band Name is :' + bandName
      };
      this.modalRef = this.modalService.show(SuccessModalComponent, {initialState: showContent, backdrop: 'static', keyboard: false});
      return;
    }


    const data = {
      'agriQualityParameterList': this.finalQualityAssurance,
      'zonalCommodityID': this.commodityInfo.zonalCommodityId,
      'sowingWeek': currentWeek,
    };

    console.log('validate form is agriQualityParameterList', data);

    this.rltCaseDetailsService.getBand(data).subscribe(
      (data) => {
        this.fetchedBand = data;
        const bandName = this.fetchedBand.bandName;

        if (bandName == undefined){
          const showContent = {
            title: 'Warning',
            content: 'Band Not Found',
            action: '/diagnose/' + this.taskId + '/' + this.flag
            // heading: 'Found Band',
            // content: 'Band Name is :' + bandName
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, {initialState: showContent, backdrop: 'static', keyboard: false});
          // this.modalRef = this.modalService.show(showBandModal, {initialState: showContent});
          // this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          console.log('fetched Band is', this.fetchedBand);
        } else {
          const showContent = {
            title: 'Band Found ',
            content: 'Band Name is : ' + bandName,
            action: '/diagnose/' + this.taskId + '/' + this.flag
            // heading: 'Found Band',
            // content: 'Band Name is :' + bandName
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, {initialState: showContent, backdrop: 'static', keyboard: false});
          // this.modalRef = this.modalService.show(showBandModal, {initialState: showContent});
          // this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          console.log('fetched Band is', this.fetchedBand);
        }
      }, (err) => {
        this.errorMsg(this.bandNotFoundMessages, "/diagnose/" + this.taskId + '/' + this.flag)
      });
  }

  // public getSymptom(stressId) {

  //   if (stressId) {
  //     if (this.stressSymptomsList.find(x => x.stressId === stressId)) {

  //       return this.stressSymptomsList.find(x => x.stressId === stressId).symptoms;
  //     } else {
  //       return [];
  //     }
  //   } else {
  //     return [];
  //   }

  // }


  public changeSymptoms(control) {
    control.desc.patchValue(undefined);
    if(control.stressId.value != undefined){
      this.rltCaseDetailsService.getRltSymtoms(control.stressId.value).subscribe(
        (data) => {
          this.stressSymptomsList = data;
        }, (err) => {
          this.errorMsg(this.httperrorresponsemessages, "/diagnose/" + this.taskId)
        });
    }else{
      this.stressSymptomsList = [];
    }

  }

  public disableField(index) {
    let ind = index + 1;
    $( "select[id*='stress-"+ind+"']").parent().parent().parent().parent().parent().siblings('ul').addClass('currentSelector');

    $('.currentSelector').find('select').prop('disabled', true);
    // $(this).parentsUntil("ul").parent().addClass('currentSelector')
    // // $(this).prevUntil("ul").addClass('currentSelector')
    this.SpotLevelForm.controls['stressId'].disable();
  }

  public Reset() {
    if ($( "ul" ).hasClass( "currentSelector" )){
      $('ul').find('select').prop('disabled', false);
      $("ul").removeClass("currentSelector")
    } else {
      console.log("Nothing found")
    }
  }

  get spotstressname(): FormArray {
    return this.CaseDetailsForm.get('spotstressname') as FormArray;
  }

  get f() { return this.CaseDetailsForm.controls; }

  get s() { return this.SpotLevelForm.controls }

  get h() { return this.healthFormArray.controls }

  get q() {return this.qualityAssessmentForm.controls }

  public removeStress(b: number, a: number) {
    const array = <FormArray>this.SpotLevelForm.controls[b];

    const from = <FormGroup>array.controls[a];

    from.controls.status.patchValue(!from.controls.status.value)
  }


  public onSave(b: number, confirmModal: TemplateRef<any>) {
    this.spotSubmitted[b] = true;
    var flag = true;

    if(this.healthFormArray.controls[b].invalid){
      this.showSaveSpotMSG = false;
      flag = false;
    }else{
      this.showSaveSpotMSG = true;
    }


    const array = <FormArray>this.SpotLevelForm.controls[b];
    array.controls.forEach(element => {
      // alert(JSON.stringify(element));
      if (element.invalid) {
        flag = false;
      }

    });

    if (flag) {

      var deltCount = 0;
      array.controls.forEach(element => {
        const form = <FormGroup>element
        if (!form.controls.status.value) {
          deltCount = deltCount + 1
        }
      });

      if (deltCount > 0) {
        const user = {
          heading: "Confirmation",
          content: 'Do you want to delete : ' + deltCount + ' stress?' + b
        };

        this.modalRef = this.modalService.show(confirmModal, { initialState: user });
      } else {

        this.saveSpot(b)
      }

    } else {

      var headerHeight = $("header").height();
      $('html,body').animate({ scrollTop: $('.form-control.ng-invalid:visible:first').offset().top - headerHeight - 30 }, 200);

    }

  }

  public saveSpot(b: number) {
    this.spotChanges[b].changes = false;
    const array = <FormArray>this.SpotLevelForm.controls[b];
    var spotData: Spot;
    var stresses: SpotStress[] = [];

    array.controls.forEach((item, index) => {
      const form = item as FormGroup;
      let stress: SpotStress;
      const incidenceOne: any = ((form.controls.severityone.value) == null  ? 0 : 1);
      const incidenceTwo: any = ((form.controls.severitytwo.value) == null ? 0 : 1);
      const incidenceThree: any = ((form.controls.severitythree.value) == null ? 0 : 1);
      stress = {
        stressId: form.controls.stressId.value,
        desc: form.controls.desc.value,
        status: form.controls.status.value,
        type: form.controls.type.value,
        right: {
          id: form .controls.rightSpotId.value,
          incidence: incidenceThree,
          severity: form.controls.severitythree.value,
          image: form.controls.rightimageURL.value,
          benchmark: form.controls.rightbenchmark.value
        },
        front: {
          id: form .controls.frontSpotId.value,
          severity: form.controls.severitytwo.value,
          incidence: incidenceTwo,
          image: form.controls.frontimageURL.value,
          benchmark: form.controls.frontbenchmark.value
        },
        left: {
          id: form .controls.leftSpotId.value,
          incidence: incidenceOne,
          severity: form.controls.severityone.value,
          image: form.controls.leftimageURL.value,
          benchmark: form.controls.leftbenchmark.value
        }
      };
      stresses.push(stress);

    });

    var healthForm = <FormGroup>this.healthFormArray.controls[b];
    var ques: any[] = [];
    this.healthData[b].questions.forEach(element => {
      var que = {
        formControlName: element.formControlName,
        name: element.name,
        type: element.type,
        values: element.values,
        selected: healthForm.controls[element.formControlName].value
      }
      ques.push(que)
    });

    spotData = {
      userId: this.logedInUser.userId,
      isSpotBenchmark:this.spothealthdetails[0][b].isSpotBenchmark,
      name: this.spothealthdetails[0][b].name,
      stresses: stresses,
      health: {
        left: this.healthData[b].left,
        right: this.healthData[b].left,
        front: this.healthData[b].left,
        questions: ques
      }
    }

    this.rltCaseDetailsService.saveSpotData(spotData).subscribe(
      (data) => {
        this.savedSpots[b] = true;
        this.rltCaseDetailsService.getRltRecommendationdetails(this.taskId,this.logedInUser.userId).subscribe((data) => {
          this.resultsMaster =data;

          this.getrecommendeList(this.resultsMaster);

        }, (err) => {
          this.errorMsg(this.httperrorresponsemessages, "/diagnose/" + this.taskId)
        });

        // this.successMsg('Spot saved', "/diagnose/" + this.taskId)
      }, (err) => {
        this.errorMsg(this.httperrorresponsemessages, "/diagnose/" + this.taskId)
      });
    if (this.modalRef) {
      this.modalRef.hide();
    }

  }

  public setMultiplereomData(a, item) {
    var recData = <FormGroup>this.resultFormArray.controls[a];
    if (recData.controls.selectedRecommendations.value.length > 0) {
      var pactchValue = recData.controls.selectedRecommendations.value;
      if (recData.controls.selectedRecommendations.value.includes(item.id)) {
        pactchValue.splice(recData.controls.selectedRecommendations.value.indexOf(item.id), 1)
      } else {
        pactchValue.push(item.id)
      }
      recData.controls.selectedRecommendations.patchValue(pactchValue);
    } else {
      var pachArray: any[] = []
      pachArray.push(item.id)
      recData.controls.selectedRecommendations.patchValue(pachArray);

    }
    var count = this.isRecommendationAvialable()

    if (count > 0) {
      this.recommendSubmit = true;
    } else {
      this.recommendSubmit = false;
    }

  }

  public isRecommendationAvialable() {
    var count = 0

    this.resultFormArray.controls.forEach(element => {
      var recomForm = <FormGroup>element;
      if (recomForm.controls.selectedRecommendations.value.length > 0) {
        count = count + 1;
      }
    });
    if (count == 0) {
      count = 1
      this.recommendationdetails.stresses.forEach(element => {
        if (element.recommendations.length > 0) {
          count = 0;
        }

      });
    }


    return count;
  }

  public onSubmit() {
    var result = this.getResult();
    if (result) {
      this.saveResult(result);
    }
  }

  slideflipbookConfig ={
    "slidesToShow": 1,
    "slidesToScroll": 1,
    "arrows": true,
    "nextArrow": "<div class='nav-btn flip-next-slide'></div>",
    "prevArrow": "<div class='nav-btn flip-prev-slide'></div>",
    "dots": false,
    "infinite": false,

  };

  slideflipbookConfigTwo = {
    "slidesToShow": 1,
    "slidesToScroll": 1,
    "arrows": true,
    "nextArrow": "<div class='nav-btn next-slide'></div>",
    "prevArrow": "<div class='nav-btn prev-slide'></div>",
    "dots": false,
    "infinite": false,
    "lazyLoad": 'ondemand',
  };

  //Benchmark slides
  slideConfigBench = {
    "slidesToShow": 1,
    "slidesToScroll": 1,
    "arrows": true,
    "nextArrow": "<div class='nav-btn next-slide'></div>",
    "prevArrow": "<div class='nav-btn prev-slide'></div>",
    "dots": false,
    "infinite": false,

  };


  slideConfigBlast = {
    "slidesToShow": 1,
    "slidesToScroll": 1,
    "arrows": true,
    "nextArrow": "<div class='nav-btn next-slide blastprev'></div>",
    "prevArrow": "<div class='nav-btn prev-slide blastnext'></div>",
    "dots": false,
    "infinite": true,
    "enabled": true,
  }


  slickInit(e) {
  }
  issueSlider() {
    $('.blastIssue').slick('refresh');
  }


  toggleAccordian() {
    $('.spotslider, .health-slider').trigger('resize');
    setTimeout(() => {
      $('.spotslider, .health-slider').slick('refresh');
    }, 0);
    $('.panel .panel .panel-heading').click(function (e) {
      var headerHeight = $("header").height();
      $('html,body').animate({ scrollTop: $('.panel-open').offset().top - headerHeight }, 50);
      e.preventDefault();
    });
    $('.panel:first-child .panel .panel-heading, .panel-open .panel .panel-heading').click(function (e) {
      $('html,body').stop();
      e.preventDefault();
    });
  }







  show = false;
  toggle() {
    this.show = !this.show
  }

  public setCheckedValue(event, caseDetails) {

    if (!caseDetails.sampleTwo) {
      caseDetails.sampleTwo = []
    }
    if (caseDetails.sampleTwo.includes(event.target.value)) {
      caseDetails.sampleTwo.splice(caseDetails.sampleTwo.indexOf(event.target.value), 1)
    } else {
      caseDetails.sampleTwo.push(event.target.value)
    }


  }
  public setCheckedValueThree(event, caseDetails) {

    if (!caseDetails.sampleThree) {
      caseDetails.sampleThree = []
    }
    if (caseDetails.sampleThree.includes(event.target.value)) {
      caseDetails.sampleThree.splice(caseDetails.sampleThree.indexOf(event.target.value), 1)
    } else {
      caseDetails.sampleThree.push(event.target.value)
    }


  }

  public setCheckedValueOne(event, caseDetails) {

    if (!caseDetails.sampleOne) {
      caseDetails.sampleOne = []
    }
    if (caseDetails.sampleOne.includes(event.target.value)) {
      caseDetails.sampleOne.splice(caseDetails.sampleOne.indexOf(event.target.value), 1)
    } else {
      caseDetails.sampleOne.push(event.target.value)
    }


  }
  public setCheckedValueFour(event, caseDetails) {

    if (!caseDetails.foundin) {
      caseDetails.foundin = []
    }
    if (caseDetails.foundin.includes(event.target.value)) {
      caseDetails.foundin.splice(caseDetails.foundin.indexOf(event.target.value), 1)
    } else {
      caseDetails.foundin.push(event.target.value)
    }


  }

  public getspothealthList(data) {
    this.healthData = [];
    data = [data]
    data.forEach((spotdataItem, index1) => {
      spotdataItem.forEach((spotsItem, index) => {
        var SpotLevelArray = new FormArray([])

        this.spotChanges.push({ name: spotsItem.name, changes: false })
        spotsItem.stresses.forEach((element, elementIndex) => {


          this.savedSpots.push(false);
          var SpotLevel: FormGroup;
          if (this.stressTypeArray) {
            if (!this.stressTypeArray.includes(element.type)) {
              this.stressTypeArray.push(element.type);
            }

          } else {
            this.stressTypeArray = [];
            this.stressTypeArray.push(element.type);
          }

          SpotLevel = this.formBuilder.group({
            id: [element.id],
            stressId: [element.stressId, Validators.required],
            incidenceone: [element.left.incidence],
            desc: [element.desc, Validators.required],
            type: [element.type, Validators.required],
            leftImagename: ['L', Validators.required],
            frontImagename: ['F', Validators.required],
            rightImagename: ['R', Validators.required],
            leftimageURL: [element.left.image],
            leftSpotId: [element.left.id],
            leftbenchmark: [element.left.benchmark,],
            frontimageURL: [element.front.image],
            frontSpotId: [element.front.id],
            frontbenchmark: [element.front.benchmark,],
            rightimageURL: [element.right.image],
            rightSpotId: [element.right.id],
            rightbenchmark: [element.right.benchmark,],
            severityone: [element.left.severity],
            incidencetwo: [element.front.incidence],
            severitytwo: [element.front.severity],
            incidencethree: [element.right.incidence],
            severitythree: [element.right.severity],
            status: [element.status, Validators.required],
          });
          SpotLevelArray.push(SpotLevel)


        });


        var healthForm = new FormGroup({});
        spotsItem.health.questions.forEach(queItem => {
          var controlName = queItem.formControlName;
          healthForm.addControl(queItem.formControlName, new FormControl(queItem.selected, Validators.required));

        });

        this.healthFormArray.push(healthForm);

        this.spotSubmitted.push(false);
        this.SpotLevelForm.push(SpotLevelArray);

        var health = { front: spotsItem.health.front, left: spotsItem.health.left, right: spotsItem.health.right, questions: spotsItem.health.questions };
        this.healthData.push(health)
      })
    })

    this.spothealthdetails = data;
  }

  public getrecommendeList(data) {
    var resultdata = data;
    this.resultFormArray = new FormArray([]);
    resultdata.stresses.forEach(stressItem => {
      var resultForm = new FormGroup({});
      resultForm.addControl("stressName", new FormControl(stressItem.name));
      resultForm.addControl("severity", new FormControl(stressItem.severity));
      resultForm.addControl("id", new FormControl(stressItem.id));
      resultForm.addControl("recommendation", new FormControl(''));
      resultForm.addControl("selectedRecommendations", new FormControl(stressItem.selectedRecommendations));

      this.resultFormArray.push(resultForm);

    });
    this.recommendationdetails = data;


  }


  public gettabl(event) {

    event.target
    if (event.srcElement.innerText == "Crop Information") {
      this.getCropInformation();
      this.isNotBandScreen();
    } else if (event.srcElement.innerText == "Irrigation Details") {
      this.getIrrigationDetails();
      this.isNotBandScreen();
    } else if (event.srcElement.innerText == "Fertilizer") {
      this.getFertilizer();
      this.isNotBandScreen();
    } else if (event.srcElement.innerText == "Seed Treatment") {
      this.getSeedTreatment();
      this.isNotBandScreen();
    } else if (event.srcElement.innerText == "Remedial Measures") {
      this.getRemedialMeasures();
      this.isNotBandScreen();
    } else if (event.srcElement.innerText == "Quality Assessment") {
      this.isBandScreen();
      this.getParameterList();
      this.getCommodityInfo();
    } else if (event.srcElement.innerText == "General Information") {
      this.isNotBandScreen();
    }

    // $(".rlt-tech-case-details .case-details-tab .nav.nav-tabs li.nav-item:nth-child(2)").click(function(){
    //   this.getCropInformation();
    // });
  }

  //Crop Information
  public getCropInformation() {
    if (this.cropInfo) {
      this.rltCaseDetailsService.getCropInformation(this.taskId).subscribe(
        (data) => {


          this.cropInfo = data;

        }, (err) => {
          this.errorMsg(this.httperrorresponsemessages, "/diagnose/" + this.taskId)
        });
    }



  }
  //Irrigation Details
  public getIrrigationDetails() {
    this.rltCaseDetailsService.getIrrigationDetails(this.taskId).subscribe(
      (data) => {
        this.irrigationDetails = data;

      }, (err) => {
        this.errorMsg(this.httperrorresponsemessages, "/diagnose/" + this.taskId)
      });
  }

  //Fertilizer Details
  public getFertilizer() {
    this.rltCaseDetailsService.getFertilizer(this.taskId).subscribe(
      (data) => {
        this.fertilizerDetails = data;

      }, (err) => {
        this.errorMsg(this.httperrorresponsemessages, "/diagnose/" + this.taskId)
      });
  }

  //Seed Treatment
  public getSeedTreatment() {
    this.rltCaseDetailsService.getSeedTreatment(this.taskId).subscribe(
      (data) => {
        this.seedTreatment = data;

      }, (err) => {
        this.errorMsg(this.httperrorresponsemessages, "/diagnose/" + this.taskId)
      });
  }

  //Remedial Measures
  public getRemedialMeasures() {
    this.rltCaseDetailsService.getRemedialMeasures(this.taskId).subscribe(
      (data) => {
        this.remedialMeasures = data;

      }, (err) => {

        this.errorMsg(this.httperrorresponsemessages, "/diagnose/" + this.taskId)

      });
  }


  public chages(b) {
    this.spotChanges[b].changes = true;

  }



  reassign(assignModal) {

    this.rltCaseDetailsService.getRLTUsers(this.logedInUser.userId).subscribe(
      (data) => {
        this.rltUserlist = data;
        const initialState = {
          title: "Assign",
          action: "/sample-diagnosis-approval-form"
        };
        this.modalRef = this.modalService.show(assignModal, { initialState, backdrop: 'static', keyboard: false });
      }, (err) => {
        this.errorMsg(this.httperrorresponsemessages, "/diagnose/" + this.taskId)

      });




  }

  public approve() {
    var result = this.getResult();
    if (result) {
      this.approveResult(result)
    }
  }


  public reassignTask() {
    this.submitted = true;

    if (this.recommendationdetails.comment[0] === " ") {
      this.invalidComments = true;
      return;
    }else{
      this.invalidComments = false;
    }

    if (this.rltUser && this.recommendationdetails.comment) {
      this.rlmReassign = {
        rlmUserId:this.logedInUser.userId, taskId: this.recommendationdetails.taskId, rltUserId: this.rltUser, comment: this.recommendationdetails.comment
      }

      this.modalRef.hide();
      this.rltCaseDetailsService.reassignTask(this.rlmReassign).subscribe(
        (data) => {

          this.successMsg('Sample Reassigned Successfully.', "/sample-diagnosis-approval-list")

        }, (err) => {

          this.errorMsg(this.httperrorresponsemessages, "/diagnose/" + this.taskId);
        });

    }
  }

  /**
   * Check Values if they are checked
   * @param value
   * @param formdataValue
   */
  public checkForSelectedValues(a, formdataValue: any): boolean {
    var value = this.resultsMaster.stresses[a].selectedRecommendations

    if (value != null && value != undefined && value.length > 0) {
      for (let i = 0; i < value.length; i++) {
        if (formdataValue == value[i]) {
          return true;
        }
      }
    }
  }



  public errorMsg(content, action) {
    const initialState = {
      title: "Error",
      content: content,
      action: action
    };
    this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
    return;
  }

  public successMsg(content, action) {
    const initialState = {
      title: "Success",
      content: content,
      action: action
    };
    this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
    return;
  }

  invalidCommentsError(){
    if (this.submitted == true && this.recommendationdetails.comment != '' && this.recommendationdetails.comment[0] != " ") {
      this.invalidComments = false;
      return;
    }

  }


  public getResult() {
    this.submitted = true;
    if (this.recommendationdetails.comment[0] === " ") {
      this.invalidComments = true;
      return;
    }else{
      this.invalidComments = false;
    }

    if (this.recommendationdetails.severityType != 'very_low') {
      var count = this.isRecommendationAvialable()

      if (count > 0) {
        this.recommendationStatus = false;
        this.recommendSubmit = true;
      } else {
        this.recommendationStatus = true;
        this.recommendSubmit = false;
      }
    } else {
      this.recommendSubmit = true;
    }
    if (this.recommendationdetails.comment == undefined || this.recommendationdetails.comment.replace(" ", "") == "") {
      this.recommendSubmit = false;
    }

    if (this.recommendSubmit) {
      var changeString = "";
      this.spotChanges.forEach(item => {
        if (item.changes) {
          if (changeString == "") {
            changeString = item.name;
          } else {
            changeString = changeString + ", " + item.name;
          }
        }
      })

      // if (changeString != "") {
      //   const initialState = {
      //     heading: 'Error',
      //     content: 'Spot is not saved successfully',
      //   };
      //   this.modalRef = this.modalService.show(WarningModalComponent, { initialState, backdrop: 'static', keyboard: false });
      // } else {

      var result = {} as Results;
      result.label = this.recommendationdetails.label;
      result.severityType = this.recommendationdetails.severityType;
      result.severityValue = this.recommendationdetails.severityValue;
      result.comment = this.recommendationdetails.comment;
      result.taskId = this.recommendationdetails.taskId;
      result.userId = this.logedInUser.userId
      result.stresses = [];
      this.resultFormArray.controls.forEach((item, index) => {
        var resultForm = <FormGroup>item;
        result.stresses.push({
          id: resultForm.controls.id.value, name: resultForm.controls.stressName.value,
          severity: resultForm.controls.severity.value,
          recommendations: this.recommendationdetails.stresses[index].recommendations,
          selectedRecommendations: resultForm.controls.selectedRecommendations.value == "" ? [] : resultForm.controls.selectedRecommendations.value
        })
      })

      return result;

      // }
    }
  }


  public saveResult(result) {

    this.rltCaseDetailsService.submitRecommendation(result).subscribe(
      (data) => {
        this.successMsg('Sample has been categorized as '+result.label +'. It is sent for further approval to the RLM', this.logedInUser.menuUrl[0].url)
      }, (err) => {
        this.errorMsg(this.httperrorresponsemessages, "/diagnose/" + this.taskId)
      });

  }

  public approveResult(result) {
    this.rltCaseDetailsService.approveRecommendation(result).subscribe(
      (data) => {
        this.successMsg('Sample Approved Successfully.', "/sample-diagnosis-approval-list")
      }, (err) => {
        this.errorMsg(this.httperrorresponsemessages, "/diagnose/" + this.taskId)
      });
  }

  public getStressData(stressType){
    var stressdataBytype =[];
    this.stressdata.forEach(item =>{
      if(item.stressTypeName == stressType){
        stressdataBytype.push(item);
      }
    })
    return stressdataBytype;
  }



  ngOnDestroy(){
  }

  isBandScreen() {
    this.bandScreenSelected =true;
  }

  isNotBandScreen() {
    this.bandScreenSelected =false;
  }
}

import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { BuyerPreApplicationService } from '../../services/buyer-pre-application.service';
import $ from 'jquery';
import { CountriesService } from '../../../world-data/services/countries.service';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { Countries } from '../../../world-data/models/countries';
import { CitiesService } from '../../../world-data/services/cities.service';
import { StatesService } from '../../../world-data/services/states.service';

@Component({
  selector: 'app-edit-buyer-pre-application',
  templateUrl: './edit-buyer-pre-application.component.html',
  styleUrls: ['./edit-buyer-pre-application.component.css']
})
export class EditBuyerPreApplicationComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  @ViewChild('cinCloseButton') cinCloseButton: any;
  @ViewChild('udyogAadharCloseButton') udyogAadharCloseButton: any;
  @ViewChild('panCloseButton') panCloseButton: any;
  @ViewChild('gstCertificateCloseButton') gstCertificateCloseButton: any;
  @ViewChild('tanCloseButton') tanCloseButton: any;
  

  applicantTypeList: any =[];
  applicationTypeList: any =[];
  bussinessTypeList: any =[];
  // firmTypeList: any =[];
  commodityList: any =[];
  currencyList: any =[];
  designationList: any =[];
  regCountriesList: any =[];
  regStatesList: any =[];
  regCitiesList: any =[];
  bussiCountriesList: any =[];
  bussiStatesList: any =[];
  bussiCitiesList: any =[];
  buyerPreApplicationForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  editId: string;

  //busiAddress: string = '';
  otherDesignation: string = '';
  nonAgriBussinessName: string = '';

  // isBusinessAddressDisabled: boolean = false;
  isOtherDesignationNameHidden: boolean = false;
  isPanNumberHide: boolean = false;
  isCinNumberHide: boolean = false;
  isGstNumberHide: boolean = false;
  isVatHide: boolean = false;
  isCompanyRegNumberHide: boolean = false;
  isNonAgriBusinessNameHide: boolean = false;

  // officeSpaceYesNo: string = 'No';
  // otherBusinessSamePremisesYesNo: string = 'No';

  // presentBussinessSelectedVal: string = '';

  commList : any = [];
  panno : string = '';
  cinno : string = '';
  gstno : string = '';
  // companyAuthContactNo : string = '';
  // companyTelephoneNo : string = '';
  // companyMobileNo : string = '';
  // companyRepresentativeContactNo : string = '';
  telephoneNo : string = '';
  mobileNo : string = '';
  applicantApplicationId: any;

  isCinImg: boolean;
  cinImgFile: any;
  cinUrl: any;

  isUdyogAadharImg: boolean;
  udyogAadharImgFile: any;
  udyogAadharUrl: any;

  isPanImg: boolean;
  panImgFile: any;
  panUrl: any;
  
  isGstCertificateImg: boolean;
  gstCertificateImgFile: any;
  gstCertificateUrl: any;

  isTanImg: boolean;
  tanImgFile: any;
  tanUrl: any;

  isCurrentOrInterestedProducts: boolean = true;

  filteredOptions: Observable<Countries[]>;

  keyword = 'name';

  regCountry : string = '';
  bussiCountry : string = '';
  regState : string = '';
  bussiState : string = '';
  regCity : string = '';
  bussiCity : string = '';

  constructor(private router: Router,
       public fb: FormBuilder,
      public buyerPreApplicationService: BuyerPreApplicationService, public countriesService: CountriesService,
      public statesService: StatesService,public citiesService: CitiesService,
      private actRoute: ActivatedRoute) { }

  ngOnInit() {

 

    // this.filteredOptions = this.myControl.valueChanges
    //   .pipe(
    //     startWith(''),
    //     map(value => this._filter(value))
    //   );

    this.getApplicantTypeList();
    this.getApplicationTypeList();
    this.getBusinessTypeList();
    // this.getFirmTypeList();
    this.getCommodityList();
    // this.getCurrencyList();
    this.getDesignationList();
    this.getCountriesList();
    this.getStatesList();
    this.getCitiesList();
    this.updateBuyerPreApplication();


    this.editId = this.actRoute.snapshot.paramMap.get('id');
        if (this.editId) {


            this.getApplicantApplicationId(this.editId);

            this.buyerPreApplicationService.getBuyerPreApplicationById(this.editId).subscribe(data => {
              //  alert(JSON.stringify(data));

             
              this.cinUrl = data.cinUrl;
              this.udyogAadharUrl = data.udyogAadharUrl;
              this.panUrl = data.panUrl; 
              this.gstCertificateUrl = data.gstUrl;
              this.tanUrl = data.tanUrl;
              this.regCountry = data.regCountry;
              this.regState = data.regState;
              this.regCity = data.regCity;
              this.bussiCountry = data.bussinessCountry;
              this.bussiState = data.bussinessState;
              this.bussiCity = data.bussinessCity;

              if(this.regCountry != null){
                let startIndex = this.regCountry.lastIndexOf("(") + 1;
                let endIndex = this.regCountry.lastIndexOf(")");
                let regCountryCode = this.regCountry.substring(startIndex,endIndex);
                console.log("regCountryCode : " + regCountryCode);
              }

              if(this.regState != null){
                let startIndex = this.regState.lastIndexOf("(") + 1;
                let endIndex = this.regState.lastIndexOf(")");
                let regStateCode = this.regState.substring(startIndex,endIndex);
                console.log("regStateCode : " + regStateCode);
              }
              
              let splitedString = data.directorsProprietorPartnerName.split(" ");
              let totalNoOfSpaces = splitedString.length - 1;
              
              if(totalNoOfSpaces == 1){

                this.buyerPreApplicationForm.patchValue({compSignatoryFirstName: splitedString[0]});
                this.buyerPreApplicationForm.patchValue({compSignatoryLastName: splitedString[1]});

              }else if(totalNoOfSpaces == 2){

                this.buyerPreApplicationForm.patchValue({compSignatoryFirstName: splitedString[0]});
                this.buyerPreApplicationForm.patchValue({compSignatoryMiddleName: splitedString[1]});
                this.buyerPreApplicationForm.patchValue({compSignatoryLastName: splitedString[2]});

              }else if(totalNoOfSpaces > 2){

                this.buyerPreApplicationForm.patchValue({compSignatoryFirstName: splitedString[0]});
                this.buyerPreApplicationForm.patchValue({compSignatoryMiddleName: splitedString[1]});
                this.buyerPreApplicationForm.patchValue({compSignatoryLastName: splitedString.slice(2).join(' ')});

              }

              this.buyerPreApplicationForm.patchValue({udyogAadharUrl: data.udyogAadharUrl});
              this.buyerPreApplicationForm.patchValue({cinUrl: data.cinUrl});
              this.buyerPreApplicationForm.patchValue({tanUrl: data.tanUrl});

              if (this.cinUrl != undefined && this.cinUrl != "") {
                this.isCinImg = false;
                this.buyerPreApplicationForm.get('cinImg').disable();
              }else if(this.cinUrl == null){
                this.isCinImg = true;
              }
              
              if (this.udyogAadharUrl != undefined && this.udyogAadharUrl != "") {
                this.isUdyogAadharImg = false;
                this.buyerPreApplicationForm.get('udyogAadharImg').disable();
              }else if(this.udyogAadharUrl == null){
                this.isUdyogAadharImg = true;
              }

              if (this.panUrl != undefined && this.panUrl != "") {
                this.isPanImg = false;
                this.buyerPreApplicationForm.get('panImg').disable();
              }else if(this.panUrl == null){
                this.isPanImg = true;
              }

              if (this.gstCertificateUrl != undefined && this.gstCertificateUrl != "") {
                this.isGstCertificateImg = false;
                this.buyerPreApplicationForm.get('gstCertificateImg').disable();
              }else if(this.gstCertificateUrl == null){
                this.isGstCertificateImg = true;
              }

              if (this.tanUrl != undefined && this.tanUrl != "") {
                this.isTanImg = false;
                this.buyerPreApplicationForm.get('tanImg').disable();
              }else if(this.tanUrl == null){
                this.isTanImg = true;
              }

              // console.log("currentOrInterestedProducts : " + data.currentOrInterestedProducts);
              if (data.currentOrInterestedProducts != null && data.currentOrInterestedProducts != "") {
                this.isCurrentOrInterestedProducts = false;
                this.buyerPreApplicationForm.get('currentOrInterestedProducts').setValidators([Validators.required]);
                this.buyerPreApplicationForm.get('currentOrInterestedProducts').updateValueAndValidity();
              }else{
                this.isCurrentOrInterestedProducts = true;
              }
              
              console.log("data.otherDesignation : " + data.otherDesignation);
              if (data.otherDesignation !== null && data.otherDesignation !== "") {
                this.isOtherDesignationNameHidden = false;
                this.buyerPreApplicationForm.get('otherDesignation').setValidators([Validators.required]);
                this.buyerPreApplicationForm.get('otherDesignation').updateValueAndValidity();
              }else{
                this.isOtherDesignationNameHidden = true;
              }

              this.buyerPreApplicationForm.patchValue(data);
              // this.commList = data.commoditiesId.split(',');
              //  alert(JSON.stringify(this.commList));
              // this.buyerPreApplicationForm.patchValue({ commoditiesId: this.commList});  
              // console.log("Application Applicant id 2 : " + this.applicantApplicationId);
              // this.getCommodityIds();
              
                // **** Commented on 8-dec-2021 ****

                // if(this.buyerPreApplicationForm.value.nationality === 'Indian'){
                //   this.isPanNumberHide = true;
                //   this.isCinNumberHide = true;
                //   this.isGstNumberHide = true;
                //   this.isCompanyRegNumberHide = false;
                //   this.isVatHide = false;
                // }else{
                //   this.isPanNumberHide = false;
                //   this.isCinNumberHide = false;  
                //   this.isGstNumberHide = false;
                //   this.isCompanyRegNumberHide = true;
                //   this.isVatHide = true;
                // }

                // console.log("applicantTypeID :" + this.buyerPreApplicationForm.value.applicantTypeID);
                // if(this.buyerPreApplicationForm.value.applicantTypeID !== 6 && this.buyerPreApplicationForm.value.nationality === 'Indian'){
                // console.log("Inside if of onClickApplicantType");
                // this.isCinNumberHide = true;
  
                // }else{
                //  console.log("Inside else of onClickApplicantType");

                // this.isCinNumberHide = false;
                // }
                // if(this.buyerPreApplicationForm.value.natureOfBusiness === 'Agriculture'){
                //   this.isNonAgriBusinessNameHide = false;
                // }else{
                //   this.isNonAgriBusinessNameHide = true;
                // }
                // if(this.buyerPreApplicationForm.value.designationID === 6){
                  // console.log("designationID inside if" + this.buyerPreApplicationForm.value.designationID);
                // this.isOtherDesignationNameDisabled = true;
                // }else{
                  // console.log("designationID inside else" + this.buyerPreApplicationForm.value.designationID);
                //   this.isOtherDesignationNameDisabled = false;
                // }
                
                // this.officeSpaceYesNo = this.buyerPreApplicationForm.value.officeSpace;
                // this.otherBusinessSamePremisesYesNo = this.buyerPreApplicationForm.value.otherBusinessSamePremises;

                // console.log("applicant application id on edit: " + this.buyerPreApplicationForm.value.applicantApplicationId);
            });
        }

        // alert(this.isUsableAreaDisabled);
        // console.log('id ' + this.editId);
  }

  updateBuyerPreApplication() {
    this.buyerPreApplicationForm = this.fb.group({
      

      natureOfBusiness: ['', Validators.required],
      applicationTypeID: ['', Validators.required],
      bussinessTypeID: ['', Validators.required],
      commoditiesId: ['', Validators.required],
      currentOrInterestedProducts: [''],
      commodityChangeCount: [''],
      companyTypeId: ['', Validators.required],
      website: ['', [Validators.required, Validators.pattern('(http://|https://)(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?')]],
      companyName: [''],

      registeredAddressID: [''],
      regCountry: ['', Validators.required],
      regCompanyOrigin: ['', Validators.required],
      regState: ['', Validators.required],
      regCity: ['', Validators.required],
      regStreetName: ['', Validators.required],
      regBuildingName: ['', Validators.required],
      regPostalCode: ['', Validators.required],

      bussinessAddressID: [''],
      sameAsRegisteredAddress: [''],
      bussinessCountry: ['', Validators.required],
      bussinessState: ['', Validators.required],
      bussinessCity: ['', Validators.required],
      bussinessStreetName: ['', Validators.required],
      bussinessBuildingName: ['', Validators.required],
      bussinessPostalCode: ['', Validators.required],

      cinNumber: ['',[Validators.required,Validators.pattern('([Ll|Uu]{1})([0-9]{5})([A-Za-z]{2})([0-9]{4})([A-Za-z]{3})([0-9]{6})')]],
      cinImg: [''],
      cinUrl: [''],
      dateOfIncorporation: ['', Validators.required],
      cinLiscenceExpiryDate: ['', Validators.required],
      udyogAadharNo: ['', Validators.required],
      udyogAadharImg: [''],
      udyogAadharUrl: [''],
      panNo: ['', [Validators.required, Validators.pattern('[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}')]],
      panImg: ['', Validators.required],
      gstNumber: ['',Validators.pattern('([0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-7]{1})([a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9a-zA-Z]{1}[zZ]{1}[0-9a-zA-Z]{1})+')],
      gstCertificateImg: ['', Validators.required],
      tanNo: ['', Validators.required],
      tanImg: [''],
      tanUrl: [''],

      directorsProprietorPartnerName: [''],
      compSignatoryFirstName: ['', Validators.required],
      compSignatoryMiddleName: [''],
      compSignatoryLastName: ['', Validators.required],
      companyAuthSignDesignationID: ['', Validators.required],
      otherDesignation: [''],
      companyAuthSignEmail: ['', [Validators.required, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$')]],
      telephoneNumber: ['', Validators.pattern('^[0-9 + ( ) -]*$')],
      sameAsTelephoneNumber: [''],
      companyMobileNumber: ['', Validators.pattern('^[0-9 + ( ) -]*$')],

      adminFirstName: ['', Validators.required],
      adminMiddleName: [''],
      adminLastName: ['', Validators.required],
      adminEmailAddress: ['',[Validators.required, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$')]],
      adminMobileNumber: ['', [Validators.required, Validators.pattern('^[0-9 + ( ) -]*$')]],

      bankName: ['', Validators.required],
      branch: ['', Validators.required],
      accountType: ['', Validators.required],
      accountNumber: ['', Validators.required],
      isOperationalSince: ['', Validators.required],
      ifsccode: ['', Validators.required],

      applicantApplicationId: [''],

    })
  }


  submitForm() {

    console.log("inside submitForm");

    this.buyerPreApplicationForm.patchValue({regCountry: this.regCountry});
    this.buyerPreApplicationForm.patchValue({bussinessCountry: this.bussiCountry});
    this.buyerPreApplicationForm.patchValue({regState: this.regState});
    this.buyerPreApplicationForm.patchValue({bussinessState: this.bussiState}); 
    this.buyerPreApplicationForm.patchValue({regCity: this.regCity});
    this.buyerPreApplicationForm.patchValue({bussinessCity: this.bussiCity});

    if(this.buyerPreApplicationForm.value.compSignatoryMiddleName == ''){
      
      let fullname = this.buyerPreApplicationForm.value.compSignatoryFirstName + " " + this.buyerPreApplicationForm.value.compSignatoryLastName;
      this.buyerPreApplicationForm.patchValue({directorsProprietorPartnerName: fullname});

    }else{
      
      let fullname = this.buyerPreApplicationForm.value.compSignatoryFirstName + " " + this.buyerPreApplicationForm.value.compSignatoryMiddleName + " " +  this.buyerPreApplicationForm.value.compSignatoryLastName;
      this.buyerPreApplicationForm.patchValue({directorsProprietorPartnerName: fullname});

    }

    if(this.buyerPreApplicationForm.value.applicationTypeID == null
      || this.buyerPreApplicationForm.value.applicationTypeID == ''){
      this.errorModal.showModal('ERROR', 'Application Type is required. Please Select Application Type.', '');
      return;
    }

    if(this.buyerPreApplicationForm.value.bussinessTypeID == null
      || this.buyerPreApplicationForm.value.bussinessTypeID == ''){
      this.errorModal.showModal('ERROR', 'Present Buisness / Occupation is required. Please Select Present Buisness / Occupation.', '');
      return;
    }

    if(this.buyerPreApplicationForm.value.companyTypeId == null
      || this.buyerPreApplicationForm.value.companyTypeId == ''){
      this.errorModal.showModal('ERROR', 'Company Type is required. Please Select Company Type.', '');
      return;
    }

    if(this.buyerPreApplicationForm.value.regCompanyOrigin == null
      || this.buyerPreApplicationForm.value.regCompanyOrigin == ''){
      this.errorModal.showModal('ERROR', 'Company Type is required. Please Select Company Type.', '');
      return;
    }

    if(this.buyerPreApplicationForm.value.companyAuthSignDesignationID == null
      || this.buyerPreApplicationForm.value.companyAuthSignDesignationID == ''){
      this.errorModal.showModal('ERROR', 'Designation is required. Please Select Designation.', '');
      return;
    }

    for(let controller in this.buyerPreApplicationForm.controls){

      this.buyerPreApplicationForm.get(controller).markAsTouched();

    }
  
    // console.log("applicant application id on submit: " + this.buyerPreApplicationForm.value.applicantApplicationId);

    if(this.buyerPreApplicationForm.invalid){
      console.log("inside 1st if");
      return;
    }
   
// Comment Start **** Commented on 8-dec-2021 *****

// ******************************************************************************



  //   if(this.buyerPreApplicationForm.value.nationality == null
  //     || this.buyerPreApplicationForm.value.nationality == ''){
  //     this.errorModal.showModal('ERROR', 'Origin is required. Please Select Origin', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.nationality === 'Indian'){
  //     this.buyerPreApplicationForm.value.companyRegistrationNumber = null;
  //     this.buyerPreApplicationForm.value.vat = null;
  //   }
  //   if(this.buyerPreApplicationForm.value.nationality === 'International'){
  //     this.buyerPreApplicationForm.value.panNumber = null;
  //     this.buyerPreApplicationForm.value.cinNumber = null;
  //     this.buyerPreApplicationForm.value.gstNumber = null;
  //   }

  //   if(this.buyerPreApplicationForm.value.applicantTypeID == null
  //     || this.buyerPreApplicationForm.value.applicantTypeID == ''){
  //     this.errorModal.showModal('ERROR', 'Applicant Type is required. Please Select Applicant Type', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.applicantTypeID === 6
  //     && this.buyerPreApplicationForm.value.nationality === 'Indian'){
  //       this.buyerPreApplicationForm.value.cinNumber = null;
  //   }

  //   if(this.buyerPreApplicationForm.value.companyName == null
  //     || this.buyerPreApplicationForm.value.companyName == ''){
  //     this.errorModal.showModal('ERROR', 'Company Name is required. Please Enter Company Name', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.natureOfBusiness == null
  //     || this.buyerPreApplicationForm.value.natureOfBusiness == ''){
  //     this.errorModal.showModal('ERROR', 'Nature of Business is required. Please Enter Nature of Business', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.natureOfBusiness === 'Agriculture'){
  //     this.buyerPreApplicationForm.value.nonAgriculturalBusinessName = null;
  //   }

  //   if(this.buyerPreApplicationForm.value.natureOfBusiness === 'Non-Agriculture' &&
  //     (this.buyerPreApplicationForm.value.nonAgriculturalBusinessName == null
  //     || this.buyerPreApplicationForm.value.nonAgriculturalBusinessName == '')){
  //       this.errorModal.showModal('ERROR', 'Non-Agriculture Business Name is required. Please Select Non-Agriculture Business Name', '');
  //       return;
  //   }




  //   if(this.buyerPreApplicationForm.value.dateOfIncorporation == null
  //     || this.buyerPreApplicationForm.value.dateOfIncorporation == ''){
  //     this.errorModal.showModal('ERROR', 'Date of Incorporation/Registration is required. Please Select Date of Incorporation/Registration', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.nationality === 'Indian' && (this.buyerPreApplicationForm.value.panNumber == null
  //     || this.buyerPreApplicationForm.value.panNumber == '')){
  //     this.errorModal.showModal('ERROR', 'Income Tax Permanent Account No. (PAN) is required. Please Enter Income Tax Permanent Account No. (PAN)', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.panNumber != null){
  //   this.panno =  this.buyerPreApplicationForm.value.panNumber;
  //   console.log("pan : " + this.panno.length);
  //   if(this.panno.length != 10){
  //     this.errorModal.showModal('ERROR', 'Length of Income Tax Permanent Account No. (PAN) Should be 10', '');
  //     return;
  //   }
  // }

  //   if(this.buyerPreApplicationForm.value.nationality === 'Indian' && this.buyerPreApplicationForm.value.applicantTypeID != 6){
  //   if(this.buyerPreApplicationForm.value.cinNumber == null
  //     || this.buyerPreApplicationForm.value.cinNumber == ''){
  //     this.errorModal.showModal('ERROR', 'Corporate Identity No. (CIN) is required. Please Enter Corporate Identity No. (CIN)', '');
  //     return;
  //   }
  // }
    
  //   if(this.buyerPreApplicationForm.value.nationality === 'Indian' && this.buyerPreApplicationForm.value.applicantTypeID != 6){
  //   if(this.buyerPreApplicationForm.value.cinNumber != null){
  //     this.cinno =  this.buyerPreApplicationForm.value.cinNumber;
  //   console.log("cinno : " + this.cinno.length);
  //   if(this.cinno.length != 21){
  //     this.errorModal.showModal('ERROR', 'Length of Corporate Identity No. (CIN) number Should be 21', '');
  //     return;
  //   }
  // }
  // }

  //   if(this.buyerPreApplicationForm.value.nationality === 'Indian' && (this.buyerPreApplicationForm.value.gstNumber == null
  //     || this.buyerPreApplicationForm.value.gstNumber == '')){
  //     this.errorModal.showModal('ERROR', 'GST No. is required. Please Enter GST No.', '');
  //     return;
  //   }
    
  //   if(this.buyerPreApplicationForm.value.gstNumber !=null){
  //   this.gstno =  this.buyerPreApplicationForm.value.gstNumber;
  //   console.log("gstno : " + this.gstno.length);
  //   if(this.gstno.length != 15){
  //     this.errorModal.showModal('ERROR', 'Length of GST No. Should be 15', '');
  //     return;
  //   }
  // }

  //   if(this.buyerPreApplicationForm.value.nationality === 'International' && 
  //     (this.buyerPreApplicationForm.value.companyRegistrationNumber == null
  //     || this.buyerPreApplicationForm.value.companyRegistrationNumber == '')){
  //     this.errorModal.showModal('ERROR', 'Company Registration Number is required. Please Enter Company Registration Number', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.nationality === 'International' && 
  //     (this.buyerPreApplicationForm.value.vat == null
  //     || this.buyerPreApplicationForm.value.vat == '')){
  //     this.errorModal.showModal('ERROR', 'Value Added Tax No.(VAT)  is required. Please Enter Value Added Tax No.(VAT)', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.currencyID == null
  //     || this.buyerPreApplicationForm.value.currencyID == ''){
  //     this.errorModal.showModal('ERROR', 'Currency is required. Please Select Currency', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.averageRevenue == null
  //     || this.buyerPreApplicationForm.value.averageRevenue == ''){
  //     this.errorModal.showModal('ERROR', 'Average Revenue (Last 3 Years) is required. Please Enter Average Revenue (Last 3 Years)', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.name == null
  //     || this.buyerPreApplicationForm.value.name == ''){
  //     this.errorModal.showModal('ERROR', 'Contact Person Name is required. Please Enter Contact Person Name', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.designationID == null
  //     || this.buyerPreApplicationForm.value.designationID == ''){
  //     this.errorModal.showModal('ERROR', 'Designation is required. Please Select Designation', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.designationID != 6){
  //     this.buyerPreApplicationForm.value.otherDesignationName = null;
  //   }

  //   if(this.buyerPreApplicationForm.value.designationID == 6 && this.buyerPreApplicationForm.value.otherDesignationName == null
  //     || this.buyerPreApplicationForm.value.otherDesignationName == ''){
  //     this.errorModal.showModal('ERROR', 'Other Designation is required. Please Enter Other Designation', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.telephoneNumber == null
  //     || this.buyerPreApplicationForm.value.telephoneNumber == ''){
  //     this.errorModal.showModal('ERROR', 'Telephone Number is required. Please Enter Telephone Number', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.telephoneNumber != null){
  //     this.telephoneNo =  this.buyerPreApplicationForm.value.telephoneNumber;
  //   }

  //   console.log("telephoneNo : " + this.telephoneNo.length);
  //   if(this.telephoneNo.length > 18){
  //     this.errorModal.showModal('ERROR', 'Length of Telephone Number Should Not Be Greater Than 18', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.mobileNumber == null
  //     || this.buyerPreApplicationForm.value.mobileNumber == ''){
  //     this.errorModal.showModal('ERROR', 'Mobile Number is required. Please Enter Mobile Number', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.mobileNumber != null){
  //     this.mobileNo =  this.buyerPreApplicationForm.value.mobileNumber;
  //   }

  //   console.log("mobileNo : " + this.mobileNo.length);
  //   if(this.mobileNo.length > 18){
  //     this.errorModal.showModal('ERROR', 'Length of Mobile Number Should Not Be Greater Than 18', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.emailAddress == null
  //     || this.buyerPreApplicationForm.value.emailAddress == ''){
  //     this.errorModal.showModal('ERROR', 'Email Address is required. Please Enter Email Address', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.commoditiesId == null
  //     || this.buyerPreApplicationForm.value.commoditiesId == ''){
  //     this.errorModal.showModal('ERROR', 'Commodities of Interest is required. Please Select Commodities of Interest', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.country == null
  //     || this.buyerPreApplicationForm.value.country == ''){
  //     this.errorModal.showModal('ERROR', 'Country Name is required. Please Enter Country Name', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.state == null
  //     || this.buyerPreApplicationForm.value.state == ''){
  //     this.errorModal.showModal('ERROR', 'State Name is required. Please Enter State Name', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.city == null
  //     || this.buyerPreApplicationForm.value.city == ''){
  //     this.errorModal.showModal('ERROR', 'City Name is required. Please Enter City Name', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.buildingName == null
  //     || this.buyerPreApplicationForm.value.buildingName == ''){
  //     this.errorModal.showModal('ERROR', 'Building Name is required. Please Enter Building Name', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.streetName == null
  //     || this.buyerPreApplicationForm.value.streetName == ''){
  //     this.errorModal.showModal('ERROR', 'Street Name is required. Please Enter Street Name', '');
  //     return;
  //   }

  //   if(this.buyerPreApplicationForm.value.postalCode == null
  //     || this.buyerPreApplicationForm.value.postalCode == ''){
  //     this.errorModal.showModal('ERROR', 'Postal Code is required. Please Enter Postal Code', '');
  //     return;
  //   }
    
// ****************************************************************************************************
  
  // Comment End **** Commented on 8-dec-2021 *****





    // console.log('locationOfInterest jq : ' + $("#locationOfInterest").val());
   
    // this.buyerPreApplicationForm.value.locationOfInterest = $("#locationOfInterest").val();
    // console.log("locationOfInterest : " + this.buyerPreApplicationForm.value.locationOfInterest);
    // if(this.buyerPreApplicationForm.value.locationOfInterest == null
    //   || this.buyerPreApplicationForm.value.locationOfInterest == ''){
    //   this.errorModal.showModal('ERROR', 'City / Location of Interest is required. Please Enter City / Location of Interest', '');
    //   return;
    // }

    // alert($("#registeredAddress").val());

    // console.log('registeredAddress jq : ' + $("#registeredAddress").val());
   
    // this.buyerPreApplicationForm.value.registeredAddress = $("#registeredAddress").val();
    // console.log("registeredAddress : " + this.buyerPreApplicationForm.value.registeredAddress);
    // if(this.buyerPreApplicationForm.value.registeredAddress == null
    //   || this.buyerPreApplicationForm.value.registeredAddress == ''){
    //   this.errorModal.showModal('ERROR', 'Registered Address is required. Please Enter Registered Address', '');
    //   return;
    // }

    // console.log('businessAddress jq : ' + $("#businessAddress").val());
   
    // this.buyerPreApplicationForm.value.businessAddress = $("#businessAddress").val();
    // console.log("businessAddress : " + this.buyerPreApplicationForm.value.businessAddress);
    // if(this.buyerPreApplicationForm.value.businessAddress == null
    //   || this.buyerPreApplicationForm.value.businessAddress == ''){
    //   this.errorModal.showModal('ERROR', 'Business Address is required. Please Enter Business Address', '');
    //   return;
    // }

    // if(this.buyerPreApplicationForm.value.companyAuthContactNumber != null){
    //   this.companyAuthContactNo =  this.buyerPreApplicationForm.value.companyAuthContactNumber;
    // }
    // console.log("companyAuthContactNo : " + this.companyAuthContactNo.length);
    // if(this.companyAuthContactNo.length > 15){
    //   console.log('inside companyAuthContactNo');
    //   this.errorModal.showModal('ERROR', 'Length of Company Authorised Contact Number Should Not Be Greater Than 15', '');
    //   return;
    // }

    // if(this.buyerPreApplicationForm.value.companyTelephoneNumber != null){
    //   this.companyTelephoneNo =  this.buyerPreApplicationForm.value.companyTelephoneNumber;
    //   }
    // console.log("companyTelephoneNo : " + this.companyTelephoneNo.length);
    // if(this.companyTelephoneNo.length > 15){
    //   this.errorModal.showModal('ERROR', 'Length of Company Telephone Number Should Not Be Greater Than 15', '');
    //   return;
    // }

    // if(this.buyerPreApplicationForm.value.companyMobileNumber != null){
    //   this.companyMobileNo =  this.buyerPreApplicationForm.value.companyMobileNumber;
    //   }
    // console.log("companyMobileNo : " + this.companyMobileNo.length);
    // if(this.companyMobileNo.length > 15){
    //   this.errorModal.showModal('ERROR', 'Length of Company Mobile Number Should Not Be Greater Than 15', '');
    //   return;
    // }

    // if(this.buyerPreApplicationForm.value.companyRepresentativeContactNumber != null){
    //   this.companyRepresentativeContactNo =  this.buyerPreApplicationForm.value.companyRepresentativeContactNumber;
    //   }
    // console.log("companyRepresentativeContactNo : " + this.companyRepresentativeContactNo.length);
    // if(this.companyRepresentativeContactNo.length > 15){
    //   this.errorModal.showModal('ERROR', 'Length of Company Representative Contact Number Should Not Be Greater Than 15', '');
    //   return;
    // }

    
    // if(this.officeSpaceYesNo == 'No'){
    // this.buyerPreApplicationForm.value.usableArea = null;
    // this.buyerPreApplicationForm.value.officeAddress = null;
    // this.buyerPreApplicationForm.value.infrastructureFacilitiesInOffice = null;
    // }

   

    // if(this.officeSpaceYesNo == 'Yes' && this.buyerPreApplicationForm.value.usableArea == null
    //   || this.buyerPreApplicationForm.value.usableArea == ''){
    //   this.errorModal.showModal('ERROR', 'Usable Area is required. Please Enter Usable Area', '');
    //   return;
    // }

    // console.log('officeAddress jq : ' + $("#officeAddress").val());
   
    //this.buyerPreApplicationForm.value.officeAddress = $("#officeAddress").val();
    // console.log("officeAddress : " + this.buyerPreApplicationForm.value.officeAddress);

  //   if(this.officeSpaceYesNo == 'Yes' && this.buyerPreApplicationForm.value.officeAddress == null
  //   || this.buyerPreApplicationForm.value.officeAddress == ''){
  //   this.errorModal.showModal('ERROR', 'Office Address is required. Please Enter Office Address', '');
  //   return;
  // }


  // if(this.otherBusinessSamePremisesYesNo == 'No'){

  //   this.buyerPreApplicationForm.value.businessTypeID = null;
  //   this.buyerPreApplicationForm.value.firmName = null;
  //   this.buyerPreApplicationForm.value.product = null;
  //   this.buyerPreApplicationForm.value.firmTypeID = null;
  //   this.buyerPreApplicationForm.value.natureOfBusiness = null;
  //   this.buyerPreApplicationForm.value.yearOfEstablishment = null;
  //   this.buyerPreApplicationForm.value.currencyID = null;
  //   this.buyerPreApplicationForm.value.presentNetWorth = null;
  //   this.buyerPreApplicationForm.value.city = null;

  // }

  

// if(this.otherBusinessSamePremisesYesNo == 'Yes' && this.buyerPreApplicationForm.value.firmName == null
// || this.buyerPreApplicationForm.value.firmName == ''){
// this.errorModal.showModal('ERROR', 'Firm Name / Present Employer / Professional Name is required. Please Enter Firm Name / Present Employer / Professional Name', '');
// return;
// }

// if(this.otherBusinessSamePremisesYesNo == 'Yes' && this.buyerPreApplicationForm.value.product == null
// || this.buyerPreApplicationForm.value.product == ''){
// this.errorModal.showModal('ERROR', 'Product is required. Please Enter Product', '');
// return;
// }
// if(this.otherBusinessSamePremisesYesNo == 'Yes' && this.buyerPreApplicationForm.value.firmTypeID == null
// || this.buyerPreApplicationForm.value.firmTypeID == ''){
// this.errorModal.showModal('ERROR', 'Type of Firm is required. Please Select Type of Firm', '');
// return;
// }

// if(this.otherBusinessSamePremisesYesNo == 'Yes' && this.buyerPreApplicationForm.value.yearOfEstablishment == null
// || this.buyerPreApplicationForm.value.yearOfEstablishment == ''){
// this.errorModal.showModal('ERROR', 'Year of Establishmentis required. Please Enter Year of Establishment', '');
// return;
// }

// if(this.otherBusinessSamePremisesYesNo == 'Yes' && this.buyerPreApplicationForm.value.presentNetWorth == null
// || this.buyerPreApplicationForm.value.presentNetWorth == ''){
// this.errorModal.showModal('ERROR', 'Present Net Worth as on Application Date is required. Please Enter Present Net Worth as on Application Date', '');
// return;
// }

// console.log('city jq : ' + $("#city").val());
   
// this.buyerPreApplicationForm.value.city = $("#city").val();
// console.log("city : " + this.buyerPreApplicationForm.value.city);
// if(this.otherBusinessSamePremisesYesNo == 'Yes' && this.buyerPreApplicationForm.value.city == null
// || this.buyerPreApplicationForm.value.city == ''){
// this.errorModal.showModal('ERROR', 'City / Location is required. Please Enter City / Location', '');
// return;
// }



      this.update();
 
  }

update(){
  // console.log("udyogAadharImageFile : " + this.udyogAadharImgFile + " panImageFile : " + this.panImgFile + " gstCertificateImgFile : " + this.gstCertificateImgFile);
  return this.buyerPreApplicationService.updateBuyerPreApplication(this.editId, this.buyerPreApplicationForm.value,this.udyogAadharImgFile,this.cinImgFile,this.panImgFile,this.gstCertificateImgFile,this.tanImgFile).subscribe( res => {
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

getApplicantTypeList(){

    return this.buyerPreApplicationService.getApplicantTypeList().subscribe((data: {}) => {
      this.applicantTypeList = data;
    })
  }

  getApplicationTypeList(){

    return this.buyerPreApplicationService.getApplicationTypeList().subscribe((data: {}) => {
      this.applicationTypeList = data;
    })
  }

  getBusinessTypeList(){

    return this.buyerPreApplicationService.getBusinessTypeList().subscribe((data: {}) => {
      this.bussinessTypeList = data;
    })
  }

  // getFirmTypeList(){

  //   return this.buyerPreApplicationService.getFirmTypeList().subscribe((data: {}) => {
  //     this.firmTypeList = data;
  //   })
  // }

  getCommodityList(){

    return this.buyerPreApplicationService.getCommodityList().subscribe((data: {}) => {
      this.commodityList = data;
    })
  }

  getCurrencyList(){

    return this.buyerPreApplicationService.getCurrencyList().subscribe((data: {}) => {
      this.currencyList = data;
    })
  }

  getDesignationList(){

    return this.buyerPreApplicationService.getDesignationList().subscribe((data: {}) => {
      this.designationList = data;
  
    })
  }

  getApplicantApplicationId(applicantId){
    return this.buyerPreApplicationService.getApplicantApplicationIdByApplicantId(applicantId).subscribe(data => {
      this.applicantApplicationId = data;
        //  console.log("Application Applicant id 1 : " + this.applicantApplicationId);
      // alert(JSON.stringify(this.applicantApplicationId));
       this.getCommodityIds();

    });
  }

  getCommodityIds(){
    //  console.log("Application Applicant id 3 : " + this.applicantApplicationId);
    return this.buyerPreApplicationService.getCommodityIdsByApplicantApplicationId(this.applicantApplicationId).subscribe(data => {
      this.commList = data;
      this.buyerPreApplicationForm.patchValue({ commoditiesId: this.commList});
      
    });
  }

  getCountriesList(){

    return this.countriesService.getCountriesList().subscribe((data : {}) => {
      this.regCountriesList = data;
      this.bussiCountriesList = data;
    })
  }

  getStatesList(){
    return this.statesService.getStatesList().subscribe((data: {}) => {
      this.regStatesList = data;
      this.bussiStatesList = data;
    })
  }

  getRegStatesListByCountry(countryCode){
    return this.statesService.getStatesListByCountry(countryCode).subscribe((data: {}) => {
      this.regStatesList = data;
    })
  }

  getBussiStatesListByCountry(countryCode){
    return this.statesService.getStatesListByCountry(countryCode).subscribe((data: {}) => {
      this.bussiStatesList = data;
    })
  }

  getCitiesList(){
    return this.citiesService.getCitiesList().subscribe((data: {}) => {
      this.regCitiesList = data;
      this.bussiCitiesList = data;
    })
  }

  getRegCitiesListByCountryAndState(countryCode,stateCode){
    return this.citiesService.getCitiesListByCountryAndState(countryCode,stateCode).subscribe((data: {}) => {
      this.regCitiesList = data;
    })
  }

  getBussiCitiesListByCountryAndState(countryCode,stateCode){
    return this.citiesService.getCitiesListByCountryAndState(countryCode,stateCode).subscribe((data: {}) => {
      this.bussiCitiesList = data;
    })
  }

  onClickDesignation(){

    // console.log("designationID :" + this.buyerPreApplicationForm.value.designationID);
  if(this.buyerPreApplicationForm.value.companyAuthSignDesignationID == 6){
     console.log("Inside if");
     this.isOtherDesignationNameHidden = false;
     this.buyerPreApplicationForm.get('otherDesignation').setValidators([Validators.required]);
     this.buyerPreApplicationForm.get('otherDesignation').updateValueAndValidity();
  }else{
     console.log("Inside else");
     this.isOtherDesignationNameHidden = true;
     this.buyerPreApplicationForm.patchValue({otherDesignation: null});
     this.buyerPreApplicationForm.get('otherDesignation').clearValidators();
     this.buyerPreApplicationForm.get('otherDesignation').updateValueAndValidity();
  }

}
onClickApplicantType(){

  console.log("applicantTypeID :" + this.buyerPreApplicationForm.value.applicantTypeID);
if(this.buyerPreApplicationForm.value.applicantTypeID == 6){
  console.log("Inside if of onClickApplicantType");
 this.isCinNumberHide = false;

 this.buyerPreApplicationForm.value.cinNumber = null;
 
}else{
  console.log("Inside else of onClickApplicantType");

 this.isCinNumberHide = true;
}
}

onClickOrigin(){
  console.log("origin : " + this.buyerPreApplicationForm.value.nationality);
  if(this.buyerPreApplicationForm.value.nationality === 'Indian'){

    this.isPanNumberHide = true;
    if(this.buyerPreApplicationForm.value.applicantTypeID == 6){
    this.isCinNumberHide = false;

    this.buyerPreApplicationForm.value.cinNumber = null;
    }else{
    this.isCinNumberHide = true;
    }
    this.isGstNumberHide = true;
    this.isVatHide = false;
    this.isCompanyRegNumberHide = false;

    this.buyerPreApplicationForm.value.vat = null;
    this.buyerPreApplicationForm.value.companyRegistrationNumber = null;
  }else{
    this.isPanNumberHide = false;
    this.isCinNumberHide = false;
    this.isGstNumberHide = false;

    this.buyerPreApplicationForm.value.panNumber = null;
    this.buyerPreApplicationForm.value.cinNumber = null;
    this.buyerPreApplicationForm.value.gstNumber = null;

    this.isVatHide = true;
    this.isCompanyRegNumberHide = true;
  }
  }

onClickNatureOfBussiness(event:any){
console.log("onClickNatureOfBussiness : " + event.target.value);
if(event.target.value === 'Agriculture'){
  this.isNonAgriBusinessNameHide = false;
}else{
  this.isNonAgriBusinessNameHide = true;
}
}

trimValue(formControl) { 
 formControl.setValue(formControl.value.trim()); 
}

 modalSuccess($event: any) {
   this.router.navigate(['/cropdata-portal/buyer-pre-application-list']);
}

cinImage(element) {
  var file: File = element.target.files[0];
  console.log("file : " + file);
  this.cinImgFile = file;
}

cinHide() {
  this.buyerPreApplicationForm.get('cinImg').enable();
  this.cinCloseButton.nativeElement.click();
  this.isCinImg = true;
  this.buyerPreApplicationForm.patchValue({cinUrl: null});
}

udyogAadharImage(element) {
  var file: File = element.target.files[0];
  console.log("file : " + file);
  this.udyogAadharImgFile = file;
}

udyogAadharHide() {
  this.buyerPreApplicationForm.get('udyogAadharImg').enable();
  this.udyogAadharCloseButton.nativeElement.click();
  this.isUdyogAadharImg = true;
  this.buyerPreApplicationForm.patchValue({udyogAadharUrl: null});
}

panImage(element) {
  var file: File = element.target.files[0];
  this.panImgFile = file;
}

panHide() {
  this.buyerPreApplicationForm.get('panImg').enable();
  this.panCloseButton.nativeElement.click();
  this.isPanImg = true;
}

gstCertificateImage(element) {
  var file: File = element.target.files[0];
  this.gstCertificateImgFile = file;
}

gstCertificateHide() {
  this.buyerPreApplicationForm.get('gstCertificateImg').enable();
  this.gstCertificateCloseButton.nativeElement.click();
  this.isGstCertificateImg = true;
  
}

tanImage(element) {
  var file: File = element.target.files[0];
  console.log("file : " + file);
  this.tanImgFile = file;
}

tanHide() {
  this.buyerPreApplicationForm.get('tanImg').enable();
  this.tanCloseButton.nativeElement.click();
  this.isTanImg = true;
  this.buyerPreApplicationForm.patchValue({tanUrl: null});
}

onClickSameAsRegAddress(event:any){
  console.log("onClickSameAsRegAddress : " + event.target.value);
  if(event.target.value === 'Yes'){
    // console.log(this.buyerPreApplicationForm.value.regCountry,this.buyerPreApplicationForm.value.regState,this.buyerPreApplicationForm.value.regCity,this.buyerPreApplicationForm.value.regStreetName,this.buyerPreApplicationForm.value.regBuildingName,this.buyerPreApplicationForm.value.regPostalCode);
    // this.buyerPreApplicationForm.value.bussinessCountry = this.buyerPreApplicationForm.value.regCountry;
    // this.buyerPreApplicationForm.value.bussinessState = this.buyerPreApplicationForm.value.regState;
    // this.buyerPreApplicationForm.value.bussinessCity = this.buyerPreApplicationForm.value.regCity;
    // this.buyerPreApplicationForm.value.bussinessStreetName = this.buyerPreApplicationForm.value.regStreetName;
    // this.buyerPreApplicationForm.value.bussinessBuildingName = this.buyerPreApplicationForm.value.regBuildingName;
    // this.buyerPreApplicationForm.value.bussinessPostalCode = this.buyerPreApplicationForm.value.regPostalCode;
    this.bussiCountry = this.regCountry;
    this.bussiState = this.regState;
    this.bussiCity = this.regCity;
    this.buyerPreApplicationForm.patchValue({bussinessCountry: this.buyerPreApplicationForm.value.regCountry});
    this.buyerPreApplicationForm.patchValue({bussinessState: this.buyerPreApplicationForm.value.regState});
    this.buyerPreApplicationForm.patchValue({bussinessCity: this.buyerPreApplicationForm.value.regCity});
    this.buyerPreApplicationForm.patchValue({bussinessStreetName: this.buyerPreApplicationForm.value.regStreetName});
    this.buyerPreApplicationForm.patchValue({bussinessBuildingName: this.buyerPreApplicationForm.value.regBuildingName});
    this.buyerPreApplicationForm.patchValue({bussinessPostalCode: this.buyerPreApplicationForm.value.regPostalCode});
  }else{
    // this.buyerPreApplicationForm.value.bussinessCountry = null;
    // this.buyerPreApplicationForm.value.bussinessState = null;
    // this.buyerPreApplicationForm.value.bussinessCity = null;
    // this.buyerPreApplicationForm.value.bussinessStreetName = null;
    // this.buyerPreApplicationForm.value.bussinessBuildingName = null;
    // this.buyerPreApplicationForm.value.bussinessPostalCode = null;
    this.bussiCountry = null;
    this.bussiState = null;
    this.bussiCity = null;
    this.buyerPreApplicationForm.patchValue({bussinessCountry: null});
    this.buyerPreApplicationForm.patchValue({bussinessState: null});
    this.buyerPreApplicationForm.patchValue({bussinessCity: null});
    this.buyerPreApplicationForm.patchValue({bussinessStreetName: null});
    this.buyerPreApplicationForm.patchValue({bussinessBuildingName: null});
    this.buyerPreApplicationForm.patchValue({bussinessPostalCode: null});

    this.buyerPreApplicationForm.patchValue({regCountry: this.regCountry});
    this.buyerPreApplicationForm.patchValue({regState: this.regState});
    this.buyerPreApplicationForm.patchValue({regCity: this.regCity});
  }
  }

  onClickSameAsTelephoneNumber(event:any){
    console.log("onClickSameAsTelephoneNumber : " + event.target.value);
    if(event.target.value === 'Y'){
      
      this.buyerPreApplicationForm.patchValue({companyMobileNumber: this.buyerPreApplicationForm.value.telephoneNumber});
    }else{
  
      this.buyerPreApplicationForm.patchValue({companyMobileNumber: null});
    }
    }

 

    onClickCommodities(event:{
      isUserInput: any;
      source: { value: any; selected: any };
    }){
      if (event.isUserInput) {
        if (event.source.value === 0 && event.source.selected === true) {
          console.log("inside if")
          this.isCurrentOrInterestedProducts = false;
          this.buyerPreApplicationForm.get('currentOrInterestedProducts').setValidators([Validators.required]);
          this.buyerPreApplicationForm.get('currentOrInterestedProducts').updateValueAndValidity();
        } else if(event.source.value === 0 && event.source.selected === false){
          console.log("Inside else if")
          this.isCurrentOrInterestedProducts = true;
          this.buyerPreApplicationForm.patchValue({currentOrInterestedProducts: null});
          this.buyerPreApplicationForm.get('currentOrInterestedProducts').clearValidators();
          this.buyerPreApplicationForm.get('currentOrInterestedProducts').updateValueAndValidity();
        }else{
          console.log("Inside else")
        }
      }

      // console.log("onClickCommodities : " + event.target.value);
      // if(event.target.value === 'Y'){
        
      //   this.buyerPreApplicationForm.patchValue({companyMobileNumber: this.buyerPreApplicationForm.value.telephoneNumber});
      // }else{
    
      //   
      // }
      }

      // onFilterOptionSelected(optionSelected: string) {
      //   console.log('optionSelected : ', optionSelected);
      // }

      // private _filter(value: string): Countries[] {
      //   const filterValue = value.toLowerCase();
      //   return this.countriesList.filter(option => option.name.toLowerCase().includes(filterValue));
      // }

      selectEventOnRegCountry(item) {
        // do something with selected item
        this.getRegStatesListByCountry(item.countryCode);
        this.regCountry = item.name;
        
      }

      selectEventOnBussiCountry(item) {
        // do something with selected item
        this.getBussiStatesListByCountry(item.countryCode);
        this.bussiCountry = item.name;
       
      }

      selectEventOnRegState(item) {
        // do something with selected item
        this.getRegCitiesListByCountryAndState(item.countryCode,item.stateCode);
        this.regState = item.name;
        
      }

      selectEventOnBussiState(item) {
        // do something with selected item
        this.getBussiCitiesListByCountryAndState(item.countryCode,item.stateCode);
        this.bussiState = item.name;
        
      }

      selectEventOnRegCity(item) {
        // do something with selected item
        this.regCity = item.name;
        
      }

      selectEventOnBussiCity(item) {
        // do something with selected item
        this.bussiCity = item.name;
      }


// onClickBusinessAddressSameAsRegAddress(event:any){

//    console.log("Business Address Same As Reg Address  : " + event.target.value);

//    if(event.target.value == 'Yes'){

//     // console.log("if yes");

//     this.buyerPreApplicationForm.value.businessAddressSameAsRegAddress = 'Yes';
//     console.log("bussi addr : " + this.buyerPreApplicationForm.value.businessAddress);
//     console.log("reg addr : " + this.buyerPreApplicationForm.value.registeredAddress);
//     this.buyerPreApplicationForm.value.businessAddress = $("#registeredAddress").val();
//     this.busiAddress = $("#registeredAddress").val();
//     console.log("bussi addr after: " + this.buyerPreApplicationForm.value.businessAddress);
//     this.isBusinessAddressDisabled = true;

//    }else{
//     // console.log("if no");
//     this.buyerPreApplicationForm.value.businessAddressSameAsRegAddress = 'No';
//     this.buyerPreApplicationForm.value.businessAddress = null;
//     this.busiAddress = null;
//      this.isBusinessAddressDisabled = false;
// }
// // console.log("after : " + this.buyerPreApplicationForm.value.businessAddressSameAsRegAddress)
  
// }

// onClickOwnRentOfficeSpace(event : any){
//   // console.log("office space  : " + event.target.value);

//   if(event.target.value == 'Yes'){
//     // console.log("if yes");
//     this.officeSpaceYesNo = event.target.value;
//     this.buyerPreApplicationForm.value.officeSpace = 'Yes';

//   }else{

//     // console.log("if no");
//     this.officeSpaceYesNo = event.target.value;

//     this.buyerPreApplicationForm.value.officeSpace = 'No';
//   }
//   // console.log('after :' + this.buyerPreApplicationForm.value.officeSpace);

// }

// onClickOtherBusinessInSamePremises(event : any){

//   console.log("Other Business In Same Premises  : " + event.target.value);

//   if(event.target.value == 'Yes'){

//     console.log("if yes");
//     this.otherBusinessSamePremisesYesNo = event.target.value;

//     this.buyerPreApplicationForm.value.otherBusinessSamePremises = 'Yes';

//   }else{

//     console.log("if no");
//     this.otherBusinessSamePremisesYesNo = event.target.value;
    
//     this.buyerPreApplicationForm.value.otherBusinessSamePremises = 'No';
//   }
//   console.log('after :' + this.buyerPreApplicationForm.value.otherBusinessSamePremises);

// }

}

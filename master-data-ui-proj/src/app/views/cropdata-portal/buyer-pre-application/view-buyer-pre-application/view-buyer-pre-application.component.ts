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
  selector: 'view-buyer-pre-application',
  templateUrl: './view-buyer-pre-application.component.html',
  styleUrls: ['./view-buyer-pre-application.component.css']
})
export class ViewBuyerPreApplicationComponent implements OnInit {

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  @ViewChild('cinCloseButton') cinCloseButton: any;
  @ViewChild('udyogAadharCloseButton') udyogAadharCloseButton: any;
  @ViewChild('panCloseButton') panCloseButton: any;
  @ViewChild('gstCertificateCloseButton') gstCertificateCloseButton: any;
  @ViewChild('tanCloseButton') tanCloseButton: any;
  @ViewChild('companyregNoCloseButton') companyregNoCloseButton: any;
  @ViewChild('vatCloseButton') vatCloseButton: any;
  @ViewChild('incomeTaxCloseButton') incomeTaxCloseButton: any;
  @ViewChild('marketLicenseCloseButton') marketLicenseCloseButton: any;
  

  applicantTypeList: any =[];
  applicationTypeList: any =[];
  businessTypeList: any =[];
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
  signatoryList: any =[];
  viewBuyerPreApplicationForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;

  editId: string;

  //busiAddress: string = '';
  otherDesignation: string = '';
  nonAgriBusinessName: string = '';

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
  companyRegistrationDocumentUrl: any;
  vatUrl: any;
  incomeTaxUrl: any;
  marketLicenseDocumentUrl: any;
  businessCountry: any;

  isCurrentOrInterestedProducts: boolean = true;

  filteredOptions: Observable<Countries[]>;

  keyword = 'name';

  regCountry : string = '';
  bussiCountry : string = '';
  regState : string = '';
  bussiState : string = '';
  regCity : string = '';
  bussiCity : string = '';

  viewBuyerPreAppliation: any;

  pdfUrl : string = '';
  pdfdownloadLink : string = '';

  currentOrInterestedProducts : string = '';
  
  cinUrlList : any = [];
  gstUrlList : any = [];
  udyogAadharUrlList : any = [];
  panUrlList : any = [];
  tanUrlList : any = [];
  marketLicenseUrlList : any = [];

  compRegUrlList : any = [];
  vatUrlList : any = [];
  incomeTaxNoUrlList : any = [];


  constructor(private router: Router,
      public buyerPreApplicationService: BuyerPreApplicationService,
      private actRoute: ActivatedRoute) { }

  ngOnInit() {

    this.editId = this.actRoute.snapshot.paramMap.get('id');
        if (this.editId) {

            this.buyerPreApplicationService.getBuyerPreApplicationByIdForViewMode(this.editId).subscribe(data => {
              //  alert(JSON.stringify(data));

              this.viewBuyerPreAppliation = data;

              if(data.cinUrl != null){
                this.cinUrlList = data.cinUrl.split(',');
              }
              
              if(data.udyogAadharUrl != null){
                this.udyogAadharUrlList = data.udyogAadharUrl.split(',');
              }

              if(data.panUrl != null){
                this.panUrlList = data.panUrl.split(',');
              }

              if(data.gstUrl != null){
                this.gstUrlList = data.gstUrl.split(',');
              }
              
              if(data.tanUrl != null){
                this.tanUrlList = data.tanUrl.split(',');
              }

              if(data.marketLicenseDocumentUrl != null){
                this.marketLicenseUrlList = data.marketLicenseDocumentUrl.split(',');
              }

              if(data.companyRegistrationDocumentUrl != null){
                this.compRegUrlList = data.companyRegistrationDocumentUrl.split(',');
              }

              if(data.vatUrl != null){
                this.vatUrlList = data.vatUrl.split(',');
              }

              if(data.incomeTaxUrl != null){
                this.incomeTaxNoUrlList = data.incomeTaxUrl.split(',');
              }
              
              this.businessCountry = this.titleCase(data.bussinessCountry);
              this.regCountry = this.titleCase(data.regCountry);

              if((data.currentOrInterestedProducts != null) && (data.currentOrInterestedProducts.substr(data.currentOrInterestedProducts.lastIndexOf(',')) === ', ')){
                this.currentOrInterestedProducts = data.currentOrInterestedProducts.substr(0,data.currentOrInterestedProducts.lastIndexOf(','));
              }else{
                this.currentOrInterestedProducts = data.currentOrInterestedProducts;
              }

              this.getSignatoryListByApplicantId(data.id);
              
            });
        }
  }
  

  submitForm() {

    console.log("inside submitForm");
    
    // this.update();
 
  }

update(){
  // console.log("udyogAadharImageFile : " + this.udyogAadharImgFile + " panImageFile : " + this.panImgFile + " gstCertificateImgFile : " + this.gstCertificateImgFile);
  return this.buyerPreApplicationService.updateBuyerPreApplication(this.editId, this.viewBuyerPreApplicationForm.value,this.udyogAadharImgFile,this.cinImgFile,this.panImgFile,this.gstCertificateImgFile,this.tanImgFile).subscribe( res => {
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

titleCase(string) {
  let sentence = string.toLowerCase().split(" ");
  for (let i = 0; i < sentence.length; i++) {
    sentence[i] = sentence[i][0].toUpperCase() + sentence[i].slice(1);
  }
  
  return sentence.join(" ");
}


 modalSuccess($event: any) {
   this.router.navigate(['/cropdata-portal/buyer-pre-application-list']);
}

getSignatoryListByApplicantId(applcantId){
  return this.buyerPreApplicationService.getSignatoryListByApplicantId(applcantId).subscribe((data: {}) => {
    this.signatoryList = data;
  })
}

cinImage(element) {
  var file: File = element.target.files[0];
  console.log("file : " + file);
  this.cinImgFile = file;
}

cinHide() {
  this.viewBuyerPreApplicationForm.get('cinImg').enable();
  this.cinCloseButton.nativeElement.click();
  this.isCinImg = true;
  this.viewBuyerPreApplicationForm.patchValue({cinUrl: null});
}

udyogAadharImage(element) {
  var file: File = element.target.files[0];
  console.log("file : " + file);
  this.udyogAadharImgFile = file;
}

udyogAadharHide() {
  this.viewBuyerPreApplicationForm.get('udyogAadharImg').enable();
  this.udyogAadharCloseButton.nativeElement.click();
  this.isUdyogAadharImg = true;
  this.viewBuyerPreApplicationForm.patchValue({udyogAadharUrl: null});
}

panImage(element) {
  var file: File = element.target.files[0];
  this.panImgFile = file;
}

panHide() {
  this.viewBuyerPreApplicationForm.get('panImg').enable();
  this.panCloseButton.nativeElement.click();
  this.isPanImg = true;
}

gstCertificateImage(element) {
  var file: File = element.target.files[0];
  this.gstCertificateImgFile = file;
}

gstCertificateHide() {
  this.viewBuyerPreApplicationForm.get('gstCertificateImg').enable();
  this.gstCertificateCloseButton.nativeElement.click();
  this.isGstCertificateImg = true;
  
}

tanImage(element) {
  var file: File = element.target.files[0];
  console.log("file : " + file);
  this.tanImgFile = file;
}

tanHide() {
  this.viewBuyerPreApplicationForm.get('tanImg').enable();
  this.tanCloseButton.nativeElement.click();
  this.isTanImg = true;
  this.viewBuyerPreApplicationForm.patchValue({tanUrl: null});
}

onClickPdf(pdfUrl){  

console.log("pdfUrl : " + pdfUrl); 

this.pdfdownloadLink = pdfUrl;
this.pdfUrl = pdfUrl + "#pagemode=thumbs&scrollbar=1&toolbar=1&statusbar=1&messages=1&navpanes=1";
 console.log(this.pdfUrl); 

 $('#pdfViewer').attr("data", this.pdfUrl); 


 var objValue ="<object data='"+this.pdfUrl+"' type='application/pdf' width='100%' height='750' id='pdfViewer'><p>This browser does not support inline PDFs. Please download the PDF to view it: <a href='"+this.pdfdownloadLink+"'>Download PDF</a></p></object>"

 $('.pdfAdd').html(objValue);

}

onClickCin(cinUrl){
this.cinUrl = cinUrl;
}

onClickGst(gstCertificateUrl){
  this.gstCertificateUrl = gstCertificateUrl;
}

onClickUdyogAadhar(udyogAadharUrl){
  this.udyogAadharUrl = udyogAadharUrl;
}

onClickPan(panUrl){
  this.panUrl = panUrl;
}

onClickTan(tanUrl){
  this.tanUrl = tanUrl;
}

onClickMarketlicense(marketLicenseDocumentUrl){
  this.marketLicenseDocumentUrl = marketLicenseDocumentUrl;
}

onClickCompRegNo(companyRegistrationDocumentUrl){
  this.companyRegistrationDocumentUrl = companyRegistrationDocumentUrl;
}

onClickVat(vatUrl){
  this.vatUrl = vatUrl;
}

onClickIncomeTaxNo(incomeTaxUrl){
  this.incomeTaxUrl = incomeTaxUrl;
}
}

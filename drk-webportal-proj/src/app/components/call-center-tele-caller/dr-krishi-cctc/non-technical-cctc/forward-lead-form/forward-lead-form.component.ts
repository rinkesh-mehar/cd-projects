import {Component, OnInit, TemplateRef} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {DatepickerDateCustomClasses} from 'ngx-bootstrap/datepicker';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {ErrorMessages} from 'src/app/components/form-validation-messages';
import {SuccessModalComponent} from 'src/app/components/modal-components/success-modal/success-modal.component';
import {
  DRKrishiNonTechnicalInformationForm,
  FormResponse,
  MasterDataList,
  SubmitNontechincal
} from '../non-technical-information-form/non-technical-information.model';
import {DrKrishiNonNonTechnicalInformationFormService} from '../non-technical-information-form/non-technical-information.service';
import {DateServiceService} from '../../../../service/date-service.service';
import {el} from '@angular/platform-browser/testing/src/browser_util';

@Component({
  selector: 'app-forward-lead-form',
  templateUrl: './forward-lead-form.component.html',
  styleUrls: ['./forward-lead-form.component.less']
})
export class ForwardleadformComponent implements OnInit {


  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private modalService: BsModalService,
    private dateServiceService: DateServiceService,
    private drKrishiNonTechnicalInformationFormService: DrKrishiNonNonTechnicalInformationFormService,
  ) {

  }

  get f() {
    return this.informationForm.controls;
  }

  get fc() {
    return this.farmerCrop.controls;
  }

  dateCustomClasses: DatepickerDateCustomClasses[] = [];
  minDate: Date;
  harvestMinDate: Date;
  showingMinDate: Date;
  public informationForm: FormGroup;
  modalRef: BsModalRef;
  cropModalRef: BsModalRef;
  submitted = false;
  public information: DRKrishiNonTechnicalInformationForm;
  public submitinformation = {} as SubmitNontechincal;
  public masterData = {} as MasterDataList;
  public cropsdropdownList = [];
  public languagedropdownList = [];
  public mlvisitDate: Date[];
  public settings = {};
  public userid;
  public isSelectAll = false;
  taskId; // get this task id from 1st page
  addEditFlag = false;
  public loggedInUser = {} as any;
  public currentSystemDate: Date;
  public editNumber: number;

  // Error Messages
  public textboxerrormessage: string;
  public dropdownerrormessage: string;
  public checkboxerrormessage: string;
  public invalidvalueerrormessage: string;
  public multiselecterrormessage: string;
  public comparenumbermessage: string;
  public calndererrormessage: string;
  public httperrorresponsemessages: string;

  public calenderDateMLVisit = false;
  public calenderDateFLSMLVisit = false;

  dateSelected = [];
  selectedClass = [];

  // added for farmer major crop: CDT-Ujwal
  farmerCrop: FormGroup;
  public varietyList: string[];
  public commodity: number;
  // public existingFarmerCropInfo: FarmerCropInfo;

  public existingFarmerCropInfo: any;
  farmerCropIdList: Array<string> = [];
  commodityIds: Array<number> = [];
  html: any;
  leadResponse: any;

  flag: boolean; // change farmer major crop heading: CDT-Ujwal
  // seasonId: number;
  sowing: string;
  sowingDate: string;
  harvest: string;
  commodityId: number;
  varietyId: number;
  croppingArea: number;
  yield: number;
  hasIrrigation: number;
  alternateVariety: string;
  sellerGivenQtyTon: number;
  dateOfAvailability: string;
  commodityIs = true;
  commodityName: string;
  varietyName: string;
  cropType: any;
  irrigation: any = {
    irri: [
      {
        id: 1,
        name: 'Yes'
      },
      {
        id: 0,
        name: 'No'
      }
    ]
  };

  cropTypes: any = {
    type: [
      {
        id: 1,
        name: 'Few Months'
      },
      {
        id: 2,
        name: 'Few Weeks'
      }
    ]
  };

  counter = 0;


  getDateItem(date: Date): string {
    return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
  }

  ngOnInit() {

    // added for farmer major crop: CDT-Ujwal
    this.farmerCrop = this.formBuilder.group({
      cropInfoId: [undefined],
      userId: [''],
      farmerId: [''],
      commodityId: [undefined],
      alternateVariety: [''],
      // seasonId: ['', [Validators.required]],
      // sowing: ['', [Validators.required]],
      sowingDate: [''],
      harvest: [''],
      varietyId: [''],
      yield: [''],
      hasIrrigation: [''],
      croppingArea: [0.00],
      sellerGivenQtyTon: [''],
      dateOfAvailability: [''],
      cropType: [''],
      leadResponse: ['', [Validators.required]]
    });


    // Error Messages
    this.textboxerrormessage = ErrorMessages.textboxError;
    this.dropdownerrormessage = ErrorMessages.dropdownError;
    this.checkboxerrormessage = ErrorMessages.checkboxError;
    this.invalidvalueerrormessage = ErrorMessages.invalidvalueError;
    this.multiselecterrormessage = ErrorMessages.multiselectError;
    this.calndererrormessage = ErrorMessages.calnderError;
    this.comparenumbermessage = ErrorMessages.compareNumberError;
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

    this.loggedInUser = JSON.parse(localStorage.getItem('userData'));
    this.currentSystemDate = new Date(this.loggedInUser.date); // this.loggedInUser.date;
    this.minDate = new Date(this.loggedInUser.date);
    this.minDate.setDate(this.minDate.getDate() + 1);
    this.harvestMinDate = new Date(this.loggedInUser.date);
    this.harvestMinDate.setDate(this.harvestMinDate.getDate() + 1);
    // this.minDate.setDate(this.minDate.getDate());
    this.showingMinDate = new Date(this.loggedInUser.date);
    this.showingMinDate.setDate(this.showingMinDate.getDate());
    this.dateSelected = [this.currentSystemDate];

    this.cropType = '';
    const routeparam = this.route.params.subscribe(params => {
      this.taskId = params.taskId;
    });

    this.userid = localStorage.getItem('loginUserid');

    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0);
    });


    this.DrKrishiNonNonTechnicalInformationFormService();
    this.informationForm = this.formBuilder.group({
      state: [{value: '', disabled: true}],
      district: [{value: '', disabled: true}],
      tehsil: [{value: '', disabled: true}],
      village: [{value: '', disabled: true}],
      sellerType: [{value: '', disabled: true}],
      name: [{value: ''}, [Validators.required, Validators.pattern('^[a-zA-Z-,]+(\s{0,1}[a-zA-Z-, ])*$')]],
      fathername: [{value: ''}, [Validators.pattern('^[a-zA-Z-,]+(\s{0,1}[a-zA-Z-, ])*$')]],
      relationshipToReferenceName: [{value: ''}, [Validators.pattern('^[a-zA-Z-,]+(\s{0,1}[a-zA-Z-, ])*$')]],
      primarymobilemumber: [{value: '', disabled: true}, [Validators.required]],
      alternatemobilenumber: [{value: ''}, [Validators.pattern('^[0-9]*$'), Validators.minLength(10)]],
      referenceperson: [{value: ''}, [Validators.pattern('^[a-zA-Z-,]+(\s{0,1}[a-zA-Z-, ])*$')]],
      referencepersonmobilenumber: [{value: ''}, [Validators.pattern('^[0-9]*$'), Validators.minLength(10)]],
      governmentidproof: [{value: true}, [Validators.required]],
      ownland: [{value: false}, [Validators.required]],
      irrigatedland: [{value: true}, [Validators.required]],
      farmsize: [{value: ''}, [Validators.required, Validators.pattern('^-?[1-9]\\d*(\\.\\d{1,2})?$')]],
      majorcropsgrown: [{value: ''}, [Validators.required]],
      // croppingarea: [{ value: '' }, [Validators.required, Validators.pattern('^-?[1-9]\\d*(\\.\\d{1,2})?$')]],
      speakinglanguage: [{value: ''}, [Validators.required]],
      mobiletype: [{value: ''}],
      prscoutname: [{value: '', disabled: true}],
      willingnesstomarketwithcropdata: [{value: false}],
      vip: [{value: false}],
      status: [{value: ''}, [Validators.required]],
      designation: [{value: ''}, [Validators.required]],
      // otherdesignation: [{ value: '' }, [Validators.required, Validators.pattern('^[a-zA-Z-,]+(\s{0,1}[a-zA-Z-, ])*$')]],
      scheduledate: [{value: ''}, [Validators.required]],
      mlvisit: [{value: ''}, [Validators.required]],
      meetingpoint: [{value: ''}, [Validators.required]], // for POI - Pranay
      comments: [{value: ''}, [Validators.required, Validators.pattern('[^ ](.*|\n|\r|\r\n)*')]]
    });
    this.settings = {
      singleSelection: false,
      idField: 'id',
      textField: 'name',
      enableCheckAll: true,
      selectAllText: 'Select All',
      unSelectAllText: 'All selected',
      allowSearchFilter: true,
      limitSelection: -1,
      clearSearchFilter: true,
      maxHeight: 197,
      itemsShowLimit: 3,
      searchPlaceholderText: 'Search',
      noDataAvailablePlaceholderText: 'No Data Available',
      closeDropDownOnSelection: false,
      showSelectedItemsAtTop: false,
      defaultOpen: false
    };
    if (window.matchMedia('(max-width: 767px)').matches) {
      this.settings = {
        itemsShowLimit: 1,
      };
    }

  }

  public vipstatus() {
    if (this.information != null && this.information.vip == true) {
      return [Validators.required];
    }
  }

  public DrKrishiNonNonTechnicalInformationFormService() {

    this.drKrishiNonTechnicalInformationFormService.getDrKrishiNonTechnicalInformationFormService(this.taskId).subscribe(
      (data: FormResponse) => {
        if (data.status == 200) {
          this.information = data.data;

          this.information.masterData.farmerCropInfoList = this.information.masterData.farmerCropInfoList.filter(
            non_technical_list => non_technical_list.cropTypeId == 1 || non_technical_list.cropTypeId == 2
          );

          if (this.information.mobiletype == null) {
            this.information.mobiletype = undefined;
          }

          // for POI - Pranay
          if (this.information.meetingpoint == null) {
            this.information.meetingpoint = undefined;
          }

          // VIP checkbox
          if (this.information.vip == false) {
            this.information.status = undefined;
            this.information.designation = undefined;
            this.informationForm.get('status').disable();
            this.informationForm.get('designation').disable();
            // this.informationForm.get('otherdesignation').disable();

          } else {
            if (this.information.designation.toString() == 'Others') {
              this.informationForm.get('otherdesignation').enable();
            } else {
              this.informationForm.get('otherdesignation').disable();
            }
          }

          // based on seller type validation are update: CDT-Ujwal
          /*if (this.information.sellerType == 'Marchant') {
            this.farmerCrop.get('sellerGivenQtyTon').setValidators([Validators.required]);
            this.farmerCrop.get('dateOfAvailability').setValidators([Validators.required]);
            this.farmerCrop.get('yield').clearValidators();
            this.farmerCrop.get('croppingArea').clearValidators();

            this.farmerCrop.get('sellerGivenQtyTon').updateValueAndValidity();
            this.farmerCrop.get('dateOfAvailability').updateValueAndValidity();
            this.farmerCrop.get('yield').updateValueAndValidity();
            this.farmerCrop.get('croppingArea').updateValueAndValidity();
            this.informationForm.get('farmsize').clearValidators();
            this.informationForm.get('farmsize').updateValueAndValidity();
            this.informationForm.get('fathername').clearValidators();
            this.informationForm.get('referenceperson').clearValidators();
            this.informationForm.get('referencepersonmobilenumber').clearValidators();

            this.informationForm.get('fathername').updateValueAndValidity();
            this.informationForm.get('referenceperson').updateValueAndValidity();
            this.informationForm.get('referencepersonmobilenumber').updateValueAndValidity();

            // this.farmerCrop.get('sowing').clearValidators();
            this.farmerCrop.get('sowingDate').clearValidators();
            this.farmerCrop.get('harvest').clearValidators();

            // this.farmerCrop.get('sowing').updateValueAndValidity();
            this.farmerCrop.get('sowingDate').updateValueAndValidity();
            this.farmerCrop.get('harvest').updateValueAndValidity();

          }*/

          this.cropsdropdownList = data.data.masterData.majorcropsgrownlist;
          this.languagedropdownList = data.data.masterData.speakinglanguagelist;
          this.information.mlvisit.forEach(item => {
            this.dateCustomClasses.push({date: new Date(item), classes: ['ml-visit-bg']});
          });
        } else {
          const initialState = {
            title: 'Error',
            content: 'Error occurred during Dr.Krishi Technical - Call Center Tele-Caller details, To continue please contact system admin',
            action: '/dr-krishi-non-technical-forward-lead-form'
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, {initialState, backdrop: 'static', keyboard: false});
        }
      }, (err) => {
        const initialState = {
          title: 'Error',
          content: this.httperrorresponsemessages,
          action: '/dr-krishi-non-technical-forward-lead-form/' + this.taskId
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, {initialState, backdrop: 'static', keyboard: false});
        return;
      });
  }


  vipChange() {
    this.informationForm.get('status').disable();
    this.informationForm.get('designation').disable();
    this.information.status = undefined;
    this.information.designation = undefined;
    if (this.information.vip == true) {
      this.informationForm.get('status').enable();
      this.informationForm.get('designation').enable();
    } else {
      this.informationForm.get('status').disable();
      this.informationForm.get('designation').disable();
    }
  }

  getCommodityListByCropType(cropType) {

    this.drKrishiNonTechnicalInformationFormService.getCommodityListByCropType(cropType, this.information.regionId).subscribe(
      (data) => {
        this.information.masterData.majorcropsgrownlist = data;
      });
  }

  onSubmit() {
    this.submitted = true;
    if (this.information.vip == true) {
      this.informationForm.get('status').enable();
      this.informationForm.get('designation').enable();
    } else {
      this.informationForm.get('status').disable();
      this.informationForm.get('designation').disable();
      // this.informationForm.get('otherdesignation').disable();
    }

    if (this.information.sellerType == 'Marchant') {
      this.informationForm.get('farmsize').clearValidators();
      this.informationForm.get('farmsize').updateValueAndValidity();
    }
    this.submitinformation.userid = this.userid;
    this.submitinformation.taskid = this.taskId;
    this.submitinformation.fathername = this.information.fathername;
    this.submitinformation.fatherRelationshipToReferenceName = this.information.relationshipToReferenceName;
    this.submitinformation.alternatemobileno = this.information.alternatemobileno;
    this.submitinformation.referenceperson = this.information.referenceperson;
    this.submitinformation.referencepersonmobileno = this.information.referencepersonmobileno;
    this.submitinformation.governmentidproof = this.information.governmentidproof;
    this.submitinformation.ownland = this.information.ownland;
    this.submitinformation.irrigatedland = this.information.irrigatedland;
    this.submitinformation.farmsize = this.information.farmsize;
    this.submitinformation.majorcropsgrown = this.information.majorcropsgrown;
    this.submitinformation.croppingarea = 0;
    this.submitinformation.speakinglanguage = this.information.speakinglanguage;
    this.submitinformation.mobiletype = this.information.mobiletype;
    this.submitinformation.willingnessoffarmer = this.information.willingnessoffarmer;
    this.submitinformation.vip = this.information.vip;
    this.submitinformation.status = this.information.status;
    this.submitinformation.designation = this.information.designation;
    // this.submitinformation.otherDesignation = this.information.otherDesignation;
    // added for POI - Pranay
    this.submitinformation.meetingpoint = this.information.meetingpoint;

    this.submitinformation.comments = this.information.comments;
    this.submitinformation.sellerName = this.information.name;

    const array = [];
    if (this.information.scheduledate != null && this.information.scheduledate != undefined) {
      for (let i = 0; i < this.information.scheduledate.length; i++) {
        array.push(this.dateFormatChange(this.information.scheduledate[i]));

      }
    }

    this.submitinformation.scheduledate = array;

    for (let i = 1; i < this.submitinformation.scheduledate.length; i++) {
      if (this.information.mlvisit.find(x => x.toString() === this.submitinformation.scheduledate[i])) {
        this.calenderDateMLVisit = true;
        return;
      } else {
        if (this.information.mlvisit.some(x => this.submitinformation.scheduledate.indexOf(x.toString()) != -1)) {
          this.calenderDateFLSMLVisit = true;

          return;
        }
      }
    }

    if (this.addEditFlag) {
      this.addEditFlag = false;
      const headerHeight = $('header').height();
      return;
    }
    // stop here if form is invalid
    if (!this.informationForm.invalid) {

      if (this.information.primarymobilemo === this.information.alternatemobileno) {
        const headerHeight = $('header').height();
        $('html,body').animate({scrollTop: $('.form-control.is-invalid:visible:first').offset().top - headerHeight - 30}, 200);
        return;
      } else {
        if (this.information.masterData.farmerCropInfoList.length == 0) {
          const headerHeight = $('header').height();
          $('html,body').animate({scrollTop: $('.form-control.is-invalid:visible:first').offset().top - headerHeight - 30}, 200);
          return;
        } else {
          if (this.farmerCropIdList.length > 0) {
            this.submitinformation.cropInfoId = this.farmerCropIdList.filter(function(elem, index, self) {
              return index === self.indexOf(elem);
            });
          } else {
            const initialState = {
              title: 'Error',
              content: 'Please select At list one crop',
              action: '/dr-krishi-non-technical-forward-lead-form/' + this.taskId
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, {initialState, backdrop: 'static', keyboard: false});
            return;
          }
          this.drKrishiNonTechnicalInformationFormService.submitDrKrishiNonTechnicalInformationForm(this.submitinformation).subscribe(
            (data) => {

              if (data.status == 200) {
                const initialState = {
                  title: 'Success',
                  content: data.message,
                  action: '/dr-krishi-non-technical-forward-list'
                };
                this.modalRef = this.modalService.show(SuccessModalComponent, {initialState, backdrop: 'static', keyboard: false});
                return;
              } else {
                const initialState = {
                  title: 'Error',
                  content: data.message,
                  action: '/dr-krishi-non-technical-forward-lead-form/' + this.taskId
                };
                this.modalRef = this.modalService.show(SuccessModalComponent, {initialState, backdrop: 'static', keyboard: false});
                return;
              }
            }, (err) => {
              const initialState = {
                title: 'Error',
                content: this.httperrorresponsemessages,
                action: '/dr-krishi-non-technical-forward-lead-form/' + this.taskId
              };
              this.modalRef = this.modalService.show(SuccessModalComponent, {initialState, backdrop: 'static', keyboard: false});
              return;
            });
        }
      }
    } else {
      const headerHeight = $('header').height();
      $('html,body').animate({scrollTop: $('.form-control.ng-invalid, .form-control.is-invalid:visible:first, .multiselect-dropdown-list.ng-invalid:visible:first').offset().top - headerHeight - 30}, 200);
      return;
    }

  }


  designationChange() {
    this.information.otherDesignation = undefined;
    if (this.informationForm.get('designation').value == 'Others') {
      this.informationForm.get('otherdesignation').enable();
    } else {
      this.informationForm.get('otherdesignation').disable();
    }
  }

  onValueChange(event) {

    if (this.dateSelected[0] == this.currentSystemDate) {
      this.dateSelected.splice(0, 1);
    }

    this.counter = this.counter + 1;
    if (this.counter > 2 && event.length != 0) {

      this.calenderDateMLVisit = false;
      this.calenderDateFLSMLVisit = false;
      if (event.length === undefined) {
        const date = this.getDateItem(event);

        const index = this.dateSelected.findIndex(item => {
          const testDate = this.getDateItem(item);
          return testDate === date;
        });


        if (index < 0) {
          this.dateSelected.push(event);
        } else {
          this.dateSelected.splice(index, 1);
        }
      }

      if (this.dateSelected.length >= 0) {
        this.dateCustomClasses = this.dateSelected.map(date => {
          return {
            date,
            classes: ['custom-selected-date']
          };
        });
        this.information.mlvisit.forEach(item => {
          this.dateCustomClasses.push({date: new Date(item), classes: ['ml-visit-bg']});
        });
      }
      this.informationForm.get('scheduledate').setValue(this.dateSelected);
    }

  }

  public dateFormatChange(value: string) {
    let date = new Date(value),
      month = '' + (date.getMonth() + 1),
      day = '' + date.getDate(),
      year = date.getFullYear();

    if (month.length < 2) {
      month = '0' + month;
    }
    if (day.length < 2) {
      day = '0' + day;
    }

    const selectedDate = [year, month, day].join('-');
    return selectedDate;

  }

  // save farmer major crop info: CDT-ujwal
  onSaveFarmerCropInfo() {
    this.setValidation();
    if (this.checkExistFarmerCrop()) {
      const initialState = {
        title: 'Error',
        content: 'Given records already exist.',
        action: '/dr-krishi-non-technical-forward-lead-form/' + this.taskId
      };
      this.modalRef = this.modalService.show(SuccessModalComponent, {initialState, backdrop: 'static', keyboard: false});
      return;
    }
    if (this.information.sellerType == 'Marchant') {
      this.farmerCrop.get('hasIrrigation').setValue(-1);
      // this.farmerCrop.get('seasonId').setValue(-1);
      this.farmerCrop.get('croppingArea').setValue(0.00);
      this.farmerCrop.get('yield').setValue(0.00);
      this.farmerCrop.get('leadResponse').setValue(0);
      // this.farmerCrop.get('sowingWeek')
    }
    this.farmerCrop.get('userId').setValue(this.userid);
    this.farmerCrop.get('farmerId').setValue(this.information.farmerId);
    for (const controller in this.farmerCrop.controls) {
      this.farmerCrop.get(controller).markAsTouched();
    }
    if (this.farmerCrop.invalid) {
      // const headerHeight = $('header').height();
      // $('html,body').animate({ scrollTop: $('.form-control.ng-invalid, .form-control.is-invalid:visible:first, .multiselect-dropdown-list.ng-invalid:visible:first').offset().top - headerHeight - 30 }, 200);
      return;
    }
    this.drKrishiNonTechnicalInformationFormService.saveAndUpdateFarmerCropInfo(this.farmerCrop.value).subscribe(
      (data) => {

        if (data.status == 200) {
          this.modalClose();
          const initialState = {
            title: 'Success',
            content: data.message,
            action: '/dr-krishi-non-technical-forward-lead-form/' + this.taskId
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, {initialState, backdrop: 'static', keyboard: false});
          this.getUpdatedMajorCropList();
          return;
        } else {
          this.modalClose();
          const initialState = {
            title: 'Error',
            content: data.message,
            action: '/dr-krishi-non-technical-forward-lead-form/' + this.taskId
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, {initialState, backdrop: 'static', keyboard: false});
          // this.getUpdatedMajorCropList();
          return;
        }
      }, (err) => {
        const initialState = {
          title: 'Error',
          content: this.httperrorresponsemessages,
          action: '/dr-krishi-non-technical-forward-lead-form/' + this.taskId
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, {initialState, backdrop: 'static', keyboard: false});
        return;
      });


  }

  // open popup for major crop info: CDT-ujwal
  public onCall(farmercrops: TemplateRef<any>) {
    this.clearValidation();
    this.addEditFlag = true;
    this.flag = true;
    this.commodityIs = true;
    this.commodityId = null;
    this.varietyId = null;
    this.varietyList = [];
    // this.seasonId = null;
    this.sowing = null;
    this.sowingDate = null;
    this.harvest = null;
    this.croppingArea = null;
    this.hasIrrigation = null;
    this.yield = null;
    this.alternateVariety = null;
    this.dateOfAvailability = null;
    this.sellerGivenQtyTon = null;
    this.cropType = null;
    this.leadResponse = null;
    this.farmerCrop.get('cropInfoId').setValue(undefined);
    this.cropModalRef = this.modalService.show(farmercrops, {backdrop: 'static', keyboard: false});
    return;
  }

  // edit existing farmer major crop info: CDT-ujwal
  public onEditFarmerCropInfo(farmercrops: TemplateRef<any>, farmerCropId: string, i: number) {
    this.clearValidation();
    this.addEditFlag = true;
    this.editNumber = i;
    this.drKrishiNonTechnicalInformationFormService.getFarmerCropInfo(farmerCropId).subscribe(
      (data) => {

        if (data.status == 200) {
          this.flag = false;
          this.existingFarmerCropInfo = data.data;
          this.commodityId = this.existingFarmerCropInfo[0].commodityId;
          this.varietyId = this.existingFarmerCropInfo[0].varietyId;
          this.dateOfAvailability = this.existingFarmerCropInfo[0].dateOfAvailability;
          this.sellerGivenQtyTon = this.existingFarmerCropInfo[0].sellerGivenQtyTon;
          // this.seasonId = this.existingFarmerCropInfo.seasonId;
          // this.sowing = this.existingFarmerCropInfo[0].sowing;
          this.sowingDate = this.existingFarmerCropInfo[0].sowingDate;
          this.harvest = this.existingFarmerCropInfo[0].harvest;
          this.croppingArea = this.existingFarmerCropInfo[0].croppingArea;
          this.hasIrrigation = this.existingFarmerCropInfo[0].hasIrrigation;
          this.yield = this.existingFarmerCropInfo[0].yield;
          this.alternateVariety = this.existingFarmerCropInfo[0].alternateVariety;
          this.commodityName = this.existingFarmerCropInfo[0].commodityName;
          this.varietyName = this.existingFarmerCropInfo[0].varietyName;
          this.cropType = this.existingFarmerCropInfo[0].cropTypeId;
          this.leadResponse = this.existingFarmerCropInfo[0].leadResponse;
          this.farmerCrop.get('cropInfoId').setValue(this.existingFarmerCropInfo[0].cropInfoId);

          this.drKrishiNonTechnicalInformationFormService.getCommodityListByCropType(this.cropType, this.information.regionId).subscribe(
            (data) => {
              this.information.masterData.majorcropsgrownlist = data;

              this.information.masterData.majorcropsgrownlist.forEach(c => {
                this.commodityIds.push(c.id);
              });
              if(this.commodityIds.indexOf(this.commodityId) == -1){
                this.commodityId = undefined;
              }
              if (this.commodityId != undefined) {
                this.drKrishiNonTechnicalInformationFormService.getVarietyList(this.commodityId).subscribe(
                  (data) => {
                    if (data.status = 200) {
                      this.varietyList = data.data;
                    }
                  });
              } else {
                this.varietyId = undefined;
              }
            });
          this.commodityIds.pop();
          // this.DrKrishiNonNonTechnicalInformationFormService();

          // this.modalRef = this.modalService.show(farmercrops, { backdrop: 'static', keyboard: false });
          this.cropModalRef = this.modalService.show(farmercrops, {backdrop: 'static', keyboard: false});

          return;
        }
      });
  }

  // delete farmer major crop info: CDT-ujwal
  public onDeleteFarmerCropInfo(cropInfoId: string) {
    this.addEditFlag = true;
    this.drKrishiNonTechnicalInformationFormService.deleteFarmerCropInfo(cropInfoId).subscribe(
      (data) => {

        if (data.status == 200) {
          const initialState = {
            title: 'Success',
            content: data.message,
            action: '/dr-krishi-non-technical-forward-lead-form/' + this.taskId
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, {initialState, backdrop: 'static', keyboard: false});
          this.getUpdatedMajorCropList();
          return;
        } else {
          const initialState = {
            title: 'Error',
            content: data.message,
            action: '/dr-krishi-non-technical-forward-lead-form/' + this.taskId
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, {initialState, backdrop: 'static', keyboard: false});
          return;
        }
      }, (err) => {
        const initialState = {
          title: 'Error',
          content: this.httperrorresponsemessages,
          action: '/dr-krishi-non-technical-forward-lead-form/' + this.taskId
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, {initialState, backdrop: 'static', keyboard: false});
        return;
      });


  }

  getUpdatedMajorCropList() {
    this.addEditFlag = false;
    this.drKrishiNonTechnicalInformationFormService.geFarmerCropListByTaskId(this.taskId).subscribe(
      (data) => {
        if (data.status = 200) {
          this.information.masterData.farmerCropInfoList = data.data;

          this.information.masterData.farmerCropInfoList = this.information.masterData.farmerCropInfoList.filter(
            non_technical_list => non_technical_list.cropTypeId == 1 || non_technical_list.cropTypeId == 2
          );

        } else {
          const initialState = {
            title: 'Error',
            content: data.message,
            action: '/dr-krishi-non-technical-list'
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, {initialState, backdrop: 'static', keyboard: false});
          return;
        }
      });

    // this.DrKrishiNonNonTechnicalInformationFormService();

  }

  // get variety list based on commodityId: CDT-ujwal

  getVarietyList() {
    console.log(this.farmerCrop.get('commodityId').value);
    this.drKrishiNonTechnicalInformationFormService.getVarietyList(this.commodityId).subscribe(
      (data) => {
        if (data.status = 200) {
          this.varietyList = data.data;
          this.commodityIs = false;

        } else {
          const initialState = {
            title: 'Error',
            content: data.message,
            action: '/dr-krishi-non-technical-list'
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, {initialState, backdrop: 'static', keyboard: false});
          return;
        }
      });
  }


  // it is used to select individual record - CDT-Ujwal
  onChange(event) {
    if (event.target.checked) {
      this.farmerCropIdList.push(event.target.value);
    } else {
      // this.farmerCropIdList.indexOf(event.target.value);
      this.RemoveElementFromStringArray(event.target.value);
      this.isSelectAll = false;
    }
    this.information.masterData.farmerCropInfoList.forEach(c => {
      if (event.target.value == c.cropInfoId){
        c.selected = event.target.checked;
      }

    });

    this.farmerCropIdList = this.farmerCropIdList.filter((data, index) => {
      return this.farmerCropIdList.indexOf(data)  === index;

    })
    if(this.farmerCropIdList.length == this.information.masterData.farmerCropInfoList.length){
      this.isSelectAll = true;
    } else {
      this.isSelectAll = false;
    }
    console.log(this.isSelectAll);
  }

  // it is used to select all records - CDT-Ujwal
  selectAll(event) {

    const checked = event.target.checked;
    this.information.masterData.farmerCropInfoList.forEach(item => item.selected = checked);

    this.information.masterData.farmerCropInfoList.forEach(c => {
      if (c.selected) {
        console.log('farmer crop list is selected ->' + c.selected);
        this.farmerCropIdList.push(c.cropInfoId);
        this.isSelectAll = true;
      } else {
        console.log('farmer crop list is not selected->' + c.selected);
        c.selected = false;
        this.farmerCropIdList = [];
        this.isSelectAll = false;
      }
    });
    console.log(this.isSelectAll);
  }

  // Based on action check and uncheck record - CDT-Ujwal
  RemoveElementFromStringArray(element: string) {
    this.farmerCropIdList.forEach((value, index) => {
      if (value == element) {
        this.farmerCropIdList.splice(index, 1);
      }
    });
  }

  modalClose() {
    this.cropModalRef.hide();
  }

  checkExistFarmerCrop(): boolean {
    let isFound = false;
    let k = 0;
    for (let i of this.information.masterData.farmerCropInfoList.filter(a => {
      if (a.cropTypeId == this.cropType) {
        return a;
      }
    })) {
      if ((this.flag == false && k != this.editNumber) || (this.flag != false)) {
        if (i.commodityId == this.commodityId && i.varietyId == this.varietyId && i.sowingDate == this.dateServiceService.convertLongDateToShort(this.sowingDate) && i.croppingArea == this.croppingArea) {
          return isFound = true;
        }
      }
      k++;
    }
    return isFound;
  }

  setValidation() {

    this.farmerCrop.get('varietyId').setValidators([Validators.required]);
    this.farmerCrop.get('harvest').setValidators([Validators.required]);
    this.farmerCrop.get('commodityId').setValidators([Validators.required]);
    this.farmerCrop.get('sowingDate').setValidators([Validators.required]);
    this.farmerCrop.get('yield').setValidators([Validators.required, Validators.pattern('^-?[1-9]\\d*(\\.\\d{1,2})?$')]);
    this.farmerCrop.get('hasIrrigation').setValidators([Validators.required]);
    this.farmerCrop.get('croppingArea').setValidators([Validators.required, Validators.pattern('^-?[1-9]\\d*(\\.\\d{1,2})?$')]);
    this.farmerCrop.get('cropType').setValidators([Validators.required]);
    this.farmerCrop.get('leadResponse').setValidators([Validators.required]);
    this.updateValidation();
  }

  clearValidation() {
    this.farmerCrop.get('varietyId').clearValidators();
    this.farmerCrop.get('harvest').clearValidators();
    this.farmerCrop.get('commodityId').clearValidators();
    this.farmerCrop.get('sowingDate').clearValidators();
    this.farmerCrop.get('yield').clearValidators();
    this.farmerCrop.get('hasIrrigation').clearValidators();
    this.farmerCrop.get('croppingArea').clearValidators();
    this.farmerCrop.get('cropType').clearValidators();
    this.farmerCrop.get('leadResponse').clearValidators();
    this.updateValidation();
  }

  updateValidation() {
    this.farmerCrop.get('varietyId').updateValueAndValidity();
    this.farmerCrop.get('harvest').updateValueAndValidity();
    this.farmerCrop.get('commodityId').updateValueAndValidity();
    this.farmerCrop.get('sowingDate').updateValueAndValidity();
    this.farmerCrop.get('yield').updateValueAndValidity();
    this.farmerCrop.get('hasIrrigation').updateValueAndValidity();
    this.farmerCrop.get('croppingArea').updateValueAndValidity();
    this.farmerCrop.get('cropType').updateValueAndValidity();
    this.farmerCrop.get('leadResponse').updateValueAndValidity();
  }

  validateNumber($event: KeyboardEvent) {
    if ($event.keyCode == 189 || $event.keyCode == 69 || $event.keyCode == 109|| $event.keyCode == 107 || $event.keyCode == 187) {
      return false;
    }
  }
}
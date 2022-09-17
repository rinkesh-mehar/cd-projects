import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, NgModel, Validators} from '@angular/forms';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {STEPPER_GLOBAL_OPTIONS} from '@angular/cdk/stepper';

import {LogistichubService} from '../service/logistichub.service';
import {ActivatedRoute, Router, UrlSegment} from '@angular/router';
import {LogisticHubDocServiceService} from '../logistic-hub-doc-listing/service/logistic-hub-doc-service.service';
import {ConfirmationMadalComponent} from '../../global/confirmation-madal/confirmation-madal.component';
import {globalConstants} from '../../global/globalConstants';


@Component({
    selector: 'app-logistic-registration',
    templateUrl: './logistic-registration.component.html',
    styleUrls: ['./logistic-registration.component.scss'],
    providers: [
        {
            provide: STEPPER_GLOBAL_OPTIONS,
            useValue: {showError: true}
        }
    ],
})
export class LogisticRegistrationComponent implements OnInit {

    @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
    @ViewChild('successModal', {static: false}) public successModal: SuccessModalComponent;
    @ViewChild('errorModal', {static: false}) public errorModal: ErrorModalComponent;
    @ViewChild('closebutton') closebutton;
    @Input() model: FormControl;

    rejectionReasonList: any = {};
    rejectionAllForm: FormGroup;
    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    _statusMsg: string;
    isLinear = false;
    mode: string = 'add';
    editId: string;
    editType: UrlSegment;
    lhFormGroup: FormGroup;
    userFormGroup: FormGroup;
    dimesionFormGroup: FormGroup;
    hubDistanceFormGroup: FormGroup;
    hubStructureFormGroup: FormGroup;
    stepFiveRecords: any;
    geoStateCodes: any;

    districtCodeOwner: any = {};
    commodity: any = [];
    commodityList: any;
    geoDistrictCodes: any = {};

    structuralInfo: any = {
        chkBox: [
            {name: 'Fencing', code: 'WMS_020'},
            {name: 'Wall Compound', code: 'WMS_011'},
            {name: 'Storage worthy', code: 'WMS_049'},
            {name: 'Drainage', code: 'WMS_016'},
            {name: 'Live wire inside the warehouse', code: 'WMS_028'},
            {name: 'Electricity Supply', code: 'WMS_018'},
            {name: 'Insurance Facility', code: 'WMS_025'},
            {name: 'Muded Floor', code: 'WMS_030'},
            {name: 'Tiles Cemented Floor', code: 'WMS_050'},
            {name: 'Roof (Asbestos Sheet)', code: 'WMS_040'},
            {name: 'Roof (GI Sheet)', code: 'WMS_042'},
            {name: 'Plinth', code: 'WMS_035'},
            {name: 'Cemented Walls ', code: 'WMS_008'},
            {name: 'Cemented & GI Sheet Walls', code: 'WMS_006'},
            {name: 'In Premises Weigh Bridge', code: 'WMS_113'},
            {name: 'Has Parking', code: 'WMS_116'},
            {name: 'Has Security', code: 'WMS_117'},
            {name: 'Has CCTV', code: 'WMS_118'},
            {name: 'Has Water supply', code: 'WMS_119'}

        ]
    };

    constructor(private _formBuilder: FormBuilder, private actRoute: ActivatedRoute,
                private logisticHubDocServiceService: LogisticHubDocServiceService,
                private hubService: LogistichubService, private router: Router) {
    }

    ngOnInit() {
        this.getGeoStateCodes();
        this.getActiveCommodityList();
        this.getRejectionReason();
        this.lhFormGroup = this._formBuilder.group({
            stateCode: ['', Validators.required],
            regionID: ['', Validators.required],
            name: ['', Validators.required],
            contactPersonName: ['', Validators.required],
            primaryMobileNumber: ['', [Validators.required, Validators.pattern('^((\\+91-?)|0)?[0-9]{10}$')]],
            secondaryMobileNumber: [''],
            address: ['', Validators.required],
            alternateLhAddress: [''],
            districtCode: ['', Validators.required],
            latitude: ['', Validators.required],
            longitude: ['', Validators.required],
        });
        this.userFormGroup = this._formBuilder.group({
            firstName: ['', Validators.required],
            lastName: ['', Validators.required],
            primaryMobileNumber: ['', [Validators.required, Validators.pattern('^((\\+91-?)|0)?[0-9]{10}$')]],
            secondaryMobileNumber: [''],
            address: ['', Validators.required],
            alternateAddress: [''],
            stateCode: ['', Validators.required],
            districtCode: ['', Validators.required],
            latitude: [''],
            longitude: [''],
        });
        this.hubDistanceFormGroup = this._formBuilder.group({
            WMS_071: ['', Validators.required],
            WMS_091: ['', Validators.required],
            WMS_092: ['', Validators.required],
            WMS_093: ['', Validators.required],
            WMS_094: ['', Validators.required]
        });
        this.hubStructureFormGroup = this._formBuilder.group({
            WMS_020: [false],
            WMS_011: [false],
            WMS_024: ['', Validators.required],
            WMS_049: [false],
            WMS_022: ['', Validators.required],
            WMS_046: ['', Validators.required],
            WMS_030: [false],
            WMS_050: [false],
            WMS_042: [false],
            WMS_014: ['', Validators.required],
            WMS_021: ['', Validators.required],
            WMS_053: ['', Validators.required],
            WMS_001: ['', Validators.required],
            WMS_028: [false],
            WMS_018: [false],
            WMS_016: [false],
            WMS_035: [false],
            WMS_008: [false],
            WMS_006: [false],
            WMS_040: [false],
            WMS_025: [false],
            WMS_010: ['', Validators.required],
            WMS_113: [false],
            WMS_116: [false],
            WMS_117: [false],
            WMS_118: [false],
            WMS_119: [false]
        });
        this.dimesionFormGroup = this._formBuilder.group({
            length: ['', Validators.required],
            breadth: ['', Validators.required],
            height: ['', Validators.required],
            capacity: ['', Validators.required]
        });
        this.rejectionAllForm = this._formBuilder.group({
            rejectReasonId: ['', Validators.required],
        });


        this.editId = this.actRoute.snapshot.paramMap.get('id');
        this.editType = this.actRoute.snapshot.url[1];
        if (this.editType.path == 'view') {
            if (this.editId) {
                this.mode = 'collected';
                this.lhFormGroup.disable();
                this.userFormGroup.disable();
                this.hubDistanceFormGroup.disable();
                this.hubStructureFormGroup.disable();
                this.dimesionFormGroup.disable();

                this.lhFormGroup.updateValueAndValidity();
                this.getCollectedLhDetails();
            }
        } else if (this.editType.path == 'edit') {
            this.mode = 'edit';
            this.getCollectedLhDetails();
        }
    }
    trimValue(formControl) { 
        formControl.setValue(formControl.value.trim()); 
      }
      

    public lhError = (controlName: string, errorName: string) => {
        return this.lhFormGroup.controls[controlName].hasError(errorName);
    };
    public userError = (controlName: string, errorName: string) => {
        return this.userFormGroup.controls[controlName].hasError(errorName);
    };
    public distanceError = (controlName: string, errorName: string) => {
        return this.hubDistanceFormGroup.controls[controlName].hasError(errorName);
    };
    public structureError = (controlName: string, errorName: string) => {
        return this.hubStructureFormGroup.controls[controlName].hasError(errorName);
    };
    public dimesionError = (controlName: string, errorName: string) => {
        return this.dimesionFormGroup.controls[controlName].hasError(errorName);
    };

    /*It is use to get only successfully registrar LH*/
    getCollectedLhDetails() {
        return this.hubService.getCollectedLhById(this.editId).subscribe(data => {
            this.geoDistrictCodes = data;
            this.lhFormGroup.patchValue(data['lhWarehouse']);
            this.userFormGroup.patchValue(data['owner']);
            this.hubDistanceFormGroup.patchValue(data['lhStructure']);
            this.dimesionFormGroup.patchValue(data['lhDimension']);
            this.dimesionFormGroup.patchValue({length: data['lhDimension'].length});
            if (data['lhStructure']) {
                for (const meta of data['lhStructure']) {
                    if (meta.MetaDescription == 'WMS_071') {
                        this.hubDistanceFormGroup.patchValue({WMS_071: meta.MetaValue});

                    } else if (meta.MetaDescription == 'WMS_091') {
                        this.hubDistanceFormGroup.patchValue({WMS_091: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_092')) {
                        this.hubDistanceFormGroup.patchValue({WMS_092: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_093')) {
                        this.hubDistanceFormGroup.patchValue({WMS_093: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_094')) {
                        this.hubDistanceFormGroup.patchValue({WMS_094: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_020')) {
                        this.hubStructureFormGroup.patchValue({WMS_020: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_011')) {
                        this.hubStructureFormGroup.patchValue({WMS_011: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_042')) {
                        this.hubStructureFormGroup.patchValue({WMS_042: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_022')) {
                        this.hubStructureFormGroup.patchValue({WMS_022: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_024')) {
                        this.hubStructureFormGroup.patchValue({WMS_024: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_049')) {
                        this.hubStructureFormGroup.patchValue({WMS_049: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_022')) {
                        this.hubStructureFormGroup.patchValue({WMS_022: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_046')) {
                        this.hubStructureFormGroup.patchValue({WMS_046: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_014')) {
                        this.hubStructureFormGroup.patchValue({WMS_014: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_053')) {
                        this.hubStructureFormGroup.patchValue({WMS_053: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_021')) {
                        this.hubStructureFormGroup.patchValue({WMS_021: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_053')) {
                        this.hubStructureFormGroup.patchValue({WMS_053: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_001')) {
                        this.hubStructureFormGroup.patchValue({WMS_001: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_028')) {
                        this.hubStructureFormGroup.patchValue({WMS_028: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_018')) {
                        this.hubStructureFormGroup.patchValue({WMS_018: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_016')) {
                        this.hubStructureFormGroup.patchValue({WMS_016: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_035')) {
                        this.hubStructureFormGroup.patchValue({WMS_035: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_008')) {
                        this.hubStructureFormGroup.patchValue({WMS_008: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_006')) {
                        this.hubStructureFormGroup.patchValue({WMS_006: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_040')) {
                        this.hubStructureFormGroup.patchValue({WMS_040: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_025')) {
                        this.hubStructureFormGroup.patchValue({WMS_025: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_010')) {
                        const commodity = meta.MetaValue.split(',');
                        for (const c of commodity) {
                            const i = this.commodityList.findIndex(x => x.id == c);
                        }
                        const commodityNum = commodity.map(Number);
                        this.hubStructureFormGroup.patchValue({WMS_010: commodityNum});
                        console.log('WMS_0010');
                    } else if ((meta.MetaDescription == 'WMS_113')){
                        this.hubStructureFormGroup.patchValue({WMS_113: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_116')){
                        this.hubStructureFormGroup.patchValue({WMS_116: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_117')){
                        this.hubStructureFormGroup.patchValue({WMS_117: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_118')){
                        this.hubStructureFormGroup.patchValue({WMS_118: meta.MetaValue});
                    } else if ((meta.MetaDescription == 'WMS_119')){
                        this.hubStructureFormGroup.patchValue({WMS_119: meta.MetaValue});
                    }

                }
            }
        });
    }

    /*checkAll() {
        this.hubStructureFormGroup = this._formBuilder.group({
            WMS_020: [true],
            WMS_011: [true],
            WMS_049: [true],
            WMS_030: [true],
            WMS_050: [true],
            WMS_042: [true],
            WMS_028: [true],
            WMS_018: [true],
            WMS_016: [true],
            WMS_035: [true],
            WMS_008: [true],
            WMS_006: [true],
            WMS_040: [true],
            WMS_025: [true],
            checkAll: [false]
        });
    }*/

    equals(objOne, objTwo) {
        if (typeof objOne !== 'undefined' && typeof objTwo !== 'undefined') {
            return objOne.id === objTwo.id;
        }
    }

    selectAll(select: NgModel, values) {
        select.update.emit(values);
    }

    deselectAll(select: NgModel) {
        select.update.emit([]);
    }

    backButton() {

        // this.isLinear = false;
    }

    submitForm() {
        /*check lhForm all mandatory field are valid or not */
        for (const controller in this.lhFormGroup.controls) {
            this.lhFormGroup.get(controller).markAllAsTouched();
        }
        if (this.lhFormGroup.invalid) {
            this.isLinear = true;
            return;
        }

        /*check userForm all mandatory field are valid or not */
        for (const controller in this.userFormGroup.controls) {
            this.userFormGroup.get(controller).markAllAsTouched();
        }
        if (this.userFormGroup.invalid) {
            this.isLinear = true;
            return;
        }

        /*check hubDistanceForm all mandatory field are valid or not */
        for (const controller in this.hubDistanceFormGroup.controls) {
            this.hubDistanceFormGroup.get(controller).markAllAsTouched();
        }
        if (this.hubDistanceFormGroup.invalid) {
            this.isLinear = true;
            return;
        }

        /*check hubStructureForm all mandatory field are valid or not */
        for (const controller in this.hubStructureFormGroup.controls) {
            this.hubStructureFormGroup.get(controller).markAllAsTouched();
        }
        if (this.hubStructureFormGroup.invalid) {
            this.isLinear = true;
            return;
        }

        this.stepFiveRecords = this.dimesionFormGroup.value;
        /*check dimensionForm all mandatory field are valid or not */
        for (const controller in this.dimesionFormGroup.controls) {
            this.dimesionFormGroup.get(controller).markAllAsTouched();
        }
        if (this.dimesionFormGroup.invalid) {
            this.isLinear = true;
            return;
        }

        /*this literal object is store LH address*/
        const lhAddress = {
            'addressLine1': this.lhFormGroup.value.address,
            'addressLine2': this.lhFormGroup.value.alternateLhAddress,
            'districtCode': this.lhFormGroup.value.districtCode,
            'stateCode': this.lhFormGroup.value.stateCode,
            'primaryMobileNumber': this.lhFormGroup.value.primaryMobileNumber,
            'secondaryMobileNumber': this.lhFormGroup.value.secondaryMobileNumber,
            'latitude': this.lhFormGroup.value.latitude,
            'longitude': this.lhFormGroup.value.longitude
        };

        /*this literal object is store user address*/
        const userAddress = {
            'addressLine1': this.userFormGroup.value.address,
            'addressLine2': this.userFormGroup.value.alternateAddress,
            'districtCode': this.userFormGroup.value.districtCode,
            'stateCode': this.userFormGroup.value.stateCode,
            'primaryMobileNumber': this.userFormGroup.value.primaryMobileNumber,
            'secondaryMobileNumber': this.userFormGroup.value.secondaryMobileNumber,
            'latitude': this.userFormGroup.value.latitude,
            'longitude': this.userFormGroup.value.longitude
        };
        /*this literal object is store LH details and bind LH address*/
        const logisticHub = {
            'name': this.lhFormGroup.value.name,
            'contactPersonName': this.lhFormGroup.value.contactPersonName,
            'address': lhAddress,
            'regionID': this.lhFormGroup.value.regionID,
            'capacity': this.dimesionFormGroup.value.capacity,
            'length': this.dimesionFormGroup.value.length,
            'height': this.dimesionFormGroup.value.height,
            'breadth': this.dimesionFormGroup.value.breadth
        };
        /*this literal object is store owner details and bind owner address*/
        const ownerDetails = {
            'firstName': this.userFormGroup.value.firstName,
            'lastName': this.userFormGroup.value.lastName,
            'address': userAddress
        };
        if (this.editType.path == 'edit') {
            this.update(logisticHub, ownerDetails, this.editId);
        } else if (this.editType.path == 'add') {
            this.add(logisticHub, ownerDetails);
        }

    }

    /*it use to store basic details of LH and owner*/
    add(logisticHub, ownerDetails) {
        this.hubService.storeLhDetails(logisticHub, this.hubStructureFormGroup.value, ownerDetails, this.hubDistanceFormGroup.value)
            .subscribe(res => {
                if (res.success) {
                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            });
    }

    /*it is use to update LH details and Owner*/
    update(logisticHub, ownerDetails, id) {
        this.hubService.updateLhDetails(logisticHub, this.hubStructureFormGroup.value, ownerDetails, this.hubDistanceFormGroup.value, id)
            .subscribe(res => {
                if (res.success) {
                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            });
    }

    getGeoStateCodes() {
        console.log('Inside method geoStateCodes');
        this.hubService
            .getStateCodes()
            .subscribe(geoCodes => {
                    this.geoStateCodes = geoCodes;
                // alert(JSON.stringify(this.geoStateCodes));
            });

    }

    getDistrictCode(statecode: any) {
        console.log('Inside method geoDistrictCode');
        this.hubService
            .getDistrictCodes(statecode, '')
            .subscribe(geoDistrict => {
                if (geoDistrict) {
                    this.geoDistrictCodes = geoDistrict;
                } else {
                    console.log('state codes not present');
                }
            });
    }

    getDistrictCodeForOwnerDetails(statecode: any) {
        console.log('Inside method geoDistrictCode');
        this.hubService
            .getDistrictCodes(statecode, 'owner')
            .subscribe(geoDistrict => {
                if (geoDistrict) {
                    this.districtCodeOwner = geoDistrict;
                } else {
                    console.log('District codes not present');
                }
            });
    }

    getActiveCommodityList() {
        this.hubService.getCommoditys().subscribe(data => {
            if (data) {
                this.commodityList = data;
            }
        });
    }

    approveCollectedLh() {
        this.hubService.approveCollectedLh(this.editId)
            .subscribe(res => {
                if (res.success) {
                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            });
    }

    modalConfirmation(event) {

        console.log(event);
        if (event) {
            this.isSubmitted = true;
            this.rejectWarehouse(event);
        }
    }


    submitRejectionAllForm() {
        this.closebutton.nativeElement.click();
        const rejectWarehouse = {
            'id': this.editId,
            'rejectedReasonID': this.rejectionAllForm.value.rejectReasonId
        };

        this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + ' ', rejectWarehouse);

    }

    getRejectionReason() {
        this.logisticHubDocServiceService.getRejectionReason().subscribe(data => {
            this.rejectionReasonList = data;
            console.log('meta details are ', this.rejectionReasonList);
        });
    }

    rejectWarehouse(event: any) {
        return this.hubService.rejectWarehouse(event).subscribe(res => {
            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            }
        }, err => {
            this._statusMsg = err.error;
        });
    }

    modalSuccess($event: any) {
        if (this.editType.path == 'view'){

            this.router.navigate(['/logistic-hub/lh-collected-hub-list']);
        } else {
            this.router.navigate(['/logistic-hub/lh-rejected-hub-list']);
        }
    }

}

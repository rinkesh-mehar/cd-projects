import {Component, OnInit, ViewChild} from '@angular/core';
import {FormGroup, Validators, FormBuilder} from '@angular/forms';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {STEPPER_GLOBAL_OPTIONS} from '@angular/cdk/stepper';
import {LogistichubService} from '../service/logistichub.service';
import {ActivatedRoute, Router} from '@angular/router';
import {log} from 'util';
import {ConfirmationMadalComponent} from '../../global/confirmation-madal/confirmation-madal.component';
import {LogisticHubDocServiceService} from '../logistic-hub-doc-listing/service/logistic-hub-doc-service.service';
import {UserRightsService} from '../../services/user-rights.service';
import {globalConstants} from '../../global/globalConstants';
import {AgriCommodityPlantPartService} from '../../agri/services/agri-commodityPlantPart.service';
import {ImagePreviewModalComponent} from '../../global/image-preview-modal/image-preview-modal.component';

@Component({
    selector: 'app-lh-complete-details',
    templateUrl: './lh-complete-details.component.html',
    styleUrls: ['./lh-complete-details.component.scss']
})
export class LhCompleteDetailsComponent implements OnInit {
    @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;
    @ViewChild('imagePreviewModal') public imagePreviewModal: ImagePreviewModalComponent;
  @ViewChild('closebutton') closebutton;

    constructor(private formBuilder: FormBuilder,
                private actRoute: ActivatedRoute,
                private hubService: LogistichubService,
                private router: Router,
                private logisticHubDocServiceService: LogisticHubDocServiceService,
                private userRightsService: UserRightsService,
                public agriCommodityPlantPartService: AgriCommodityPlantPartService,
    ) {
    }

    structuralInfo: any = {
        chkBox: [
            {name: 'Fencing', code: 'WMS_020', value: ''},
            {name: 'Wall Compound', code: 'WMS_011', value: ''},
            {name: 'Storage worthy', code: 'WMS_049', value: ''},
            {name: 'Drainage', code: 'WMS_016', value: ''},
            {name: 'Live wire inside the warehouse', code: 'WMS_028', value: ''},
            {name: 'Electricity Supply', code: 'WMS_018', value: ''},
            {name: 'Insurance Facility', code: 'WMS_025', value: ''},
            {name: 'Muded Floor', code: 'WMS_030'},
            {name: 'Tiles Cemented Floor', code: 'WMS_050'},
            {name: 'Roof (Asbestos Sheet)', code: 'WMS_040'},
            {name: 'Roof (GI Sheet)', code: 'WMS_042'},
            {name: 'Plinth', code: 'WMS_035'},
            {name: 'Cemented Walls ', code: 'WMS_008'},
            {name: 'Cemented & GI Sheet Walls', code: 'WMS_006'}

        ]
    };

  rejectionReasonList: any = {};
  rejectionAllForm: FormGroup;
    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    _statusMsg: string;
    isLinear = false;
    viewForm: FormGroup;
    userFormGroup: FormGroup;
    dimesionFormGroup: FormGroup;
    hubDistanceFormGroup: FormGroup;
    hubStructureFormGroup: FormGroup;
    stepOneRecords: any;
    stepTwoRecords: any;
    stepThirdRecords: any;
    stepForthRecords: any;
    stepFiveRecords: any;
    viewId: string;
    geoStateCodes: any;
    geoDistrictCodes: any;
    lhbasicDetailsList: any = {};
    otherDetails: any = [];
    distanceDetails: any;
    booleanList: any;
    getPersonalKycDetails: any;
    getCompanyKycDetails: any;
    logisticHubName: {};
    districtCodeOwner: any;
    private map = new Map<string, object>();
    disOfList: any;
    pvImagesList: any;

    // structuralInfo: any = {
    //   chkBox: this.distanceDetails
    // }

    ngOnInit() {

      this.getRejectionReason();
        this.viewForm = this.formBuilder.group({
            // logisticHubName: [''],
            primaryContact: ['']
        });
      this.rejectionAllForm = this.formBuilder.group({
        rejectReasonId: ['', Validators.required],
      });


        this.viewId = this.actRoute.snapshot.paramMap.get('id');
        console.log('---id----', this.viewId);
        this.getLhBasicDetails(this.viewId);
    }

    markWarehouseComplete(type: number) {
      
        return this.hubService.markWarehouseComplete(this.viewId, type, this.rejectionAllForm.value.rejectReasonId).subscribe(data => {
            if (data) {
                this.isSuccess = data.success;
                if (data.success) {
                    this.successModal.showModal('SUCCESS', data.message, '');
                } else {
                    this.errorModal.showModal('ERROR', data.error, '');
                }
            }
        }, err => {
            this._statusMsg = err.error;
        });
    }


    getLhBasicDetails(viewId: string) {

        return this.hubService.getLhBasicDetails(this.viewId).subscribe(data => {
            this.lhbasicDetailsList = data['basicDetails'];
            this.otherDetails = data['otherDetails'];
            this.distanceDetails = data['distanceDetails'];
            this.booleanList = data['booleanList'];
            this.getPersonalKycDetails = data['getPersonalKycDetails'];
            this.getCompanyKycDetails = data['getCompanylKycDetails'];
            this.pvImagesList = data['pvImages'];
        });
    }
  submitRejectionAllForm(){
    for (const controller in this.rejectionAllForm.controls) {
      this.rejectionAllForm.get(controller).markAllAsTouched();
    } if (this.rejectionAllForm.invalid) {
      return;
    }
    this.closebutton.nativeElement.click();
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + ' ' , this.rejectionAllForm.value.rejectReasonId);

  }
  getRejectionReason() {
    this.logisticHubDocServiceService.getRejectionReason().subscribe(data => {
      this.rejectionReasonList = data;
      console.log('meta details are ', this.rejectionReasonList);
    });
  }

  modalConfirmation(event) {

    console.log(event);
    if (event) {
      this.isSubmitted = true;
      this.markWarehouseComplete(2);
    }
  }

    imagePreview(title, src) {
        this.imagePreviewModal.showModal(title, src);
    }

  modalSuccess($event: any) {
    this.router.navigate(['/logistic-hub/lh-pv-approved-list']);
  }

}

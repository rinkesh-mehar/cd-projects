import {Component, OnInit, Input, Output, EventEmitter, SimpleChanges, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AgriCommodityService} from '../../services/agri-commodity.service';
import {AgriQuantityLossChartService} from '../../services/agri-quantity-loss-chart.service';
import {ZonalStressDurationService} from '../../services/zonal-stress-duration.service';
import {AgriPhenophaseService} from '../../services/agri-phenophase.service';
import {AgriCommodityPhenophaseService} from '../../services/agri-commodity-phenophase.service';
import {ApiUtilService} from '../../../services/api-util.service';
import {AgriYieldCorrectionCriteriaService} from '../../services/agri-yield-correction-criteria.service';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import {AgriStressSeverityService} from '../../services/agri-stress-severity.service';

@Component({
    selector: 'app-add-edit-agri-quantity-loss-chart',
    templateUrl: './add-edit-agri-quantity-loss-chart.component.html',
    styleUrls: ['./add-edit-agri-quantity-loss-chart.component.scss']
})
export class AddEditAgriQuantityLossChartComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    quantityLossChartForm: FormGroup;

    commodityList: any;
    stressList: any;
    phenophaseList: any;
    criteriaList: any;
    editQuantityLossChartId: any;
    mode: any = 'add';
    QuantityLossChart: any;
    _cmd: any;

    uploadFile: File = null;
    imgPerview: any;

    isSubmittedBulk: boolean = false;
    isSuccessBulk: boolean = false;
    fileUpload: any;

    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    _statusMsg: string;
    bandNames: any = [];

    constructor(public formBuilder: FormBuilder, private agriCommodityService: AgriCommodityService,
                private zonalStressDurationService: ZonalStressDurationService, public agriQuantityLossChartService: AgriQuantityLossChartService,
                private agriPhenophaseService: AgriPhenophaseService, private actRoute: ActivatedRoute,
                private agriCommodityPhenophaseService: AgriCommodityPhenophaseService,
                // private agriYieldCorrectionCriteriaService : AgriYieldCorrectionCriteriaService,
                public apiUtilService: ApiUtilService, public agriStressSeverityService: AgriStressSeverityService, public router: Router
    ) {

        // this.bandNames = [{name:'Nil'},{name:'VeryLow'},{name:'Medium'},{name:'High'},{name:'VeryHigh'}]

        this.createQuantityLossChartForm();


    }

    getChanges() {
        console.log(this.quantityLossChartForm.value);
    }

    createQuantityLossChartForm() {
        this.quantityLossChartForm = this.formBuilder.group({
            id: [],
            commodityId: ['', Validators.required],
            stressId: ['', Validators.required],
            phenophaseId: ['', Validators.required],
            // stressSeverityID: ['', Validators.required],
            // criteriaId: ['', Validators.required],
            minQuantityCorrectionPercent: ['', [Validators.required,Validators.pattern("^[0-9]*$")]],
            maxQuantityCorrectionPercent: ['', [Validators.required,Validators.pattern("^[0-9]*$")]],
            maxBandValue: ['', [Validators.required,Validators.pattern("^[0-9]*$")]],
            minBandValue: ['', [Validators.required,Validators.pattern("^[0-9]*$")]],
            status: ['Inactive']
        });
    }

    ngOnInit() {
        this.loadAllCommodity();
        this.loadAllStressSeverity();
        // this.loadAllStress();
        // this.loadAllPhenophase();
        // this.loadAllgetByCommodityId();

        this.editQuantityLossChartId = this.actRoute.snapshot.paramMap.get('id');

        if (this.editQuantityLossChartId) {

            this.mode = 'edit';
            this.agriQuantityLossChartService.GetQuantityLossChart(this.editQuantityLossChartId).subscribe(data => {
                this.QuantityLossChart = data;
                this.quantityLossChartForm = this.formBuilder.group({
                    id: [],
                    commodityId: ['', Validators.required],
                    stressId: ['', Validators.required],
                    phenophaseId: ['', Validators.required],
                    // stressSeverityID: ['', Validators.required],
                    // criteriaId: ['', Validators.required],
                    minQuantityCorrectionPercent: ['', Validators.required],
                    maxQuantityCorrectionPercent: ['', Validators.required],
                    maxBandValue: ['', Validators.required],
                    minBandValue: ['', Validators.required],
                    status: ['Inactive']
                });

                this.quantityLossChartForm.patchValue(this.QuantityLossChart);
                this.loadAllCommodityByPhenophase();
                this._cmd = this.quantityLossChartForm.value.commodityId;
                this.phn(this.quantityLossChartForm.value.phenophaseId);
            });


        }

    }

    loadAllCommodity() {
        return this.agriCommodityService.GetAllCommoditise().subscribe((data: any) => {
            this.commodityList = data;
            this._cmd = this.quantityLossChartForm.value.commodityId;
        }, err => {
            alert(err);
        });
    }

    loadAllStressSeverity() {
        return this.agriStressSeverityService.GetAllStressSeverity().subscribe((data: any) => {
            this.bandNames = data;
        }, err => {
            alert(err);
        });
    }

    // loadAllStress() {
    //   return this.agriStressService.GetAllStress().subscribe((data: any) => {
    //     this.stressList = data;
    //   }, err => {
    //     alert(err)
    //   })
    // }

    // loadAllPhenophase() {
    //   return this.agriPhenophaseService.GetAllPhenophase().subscribe((data: any) => {
    //     this.phenophaseList = data;
    //   }, err => {
    //     alert(err)
    //   })
    // }

    // loadAllYieldCorrectionCriteria() {
    //   return this.agriYieldCorrectionCriteriaService.GetAllYieldCorrectionCriteria().subscribe((data: any) => {
    //     this.criteriaList = data;
    //   }, err => {
    //     alert(err)
    //   })
    // }

    submitQuantityLossChartForm() {

        for (let controller in this.quantityLossChartForm.controls) {
            this.quantityLossChartForm.get(controller).markAsTouched();
        }
        if (this.quantityLossChartForm.invalid) {
            return;
        }

        if (this.quantityLossChartForm.get('stressId').value == 0) {
            this.errorModal.showModal('ERROR', 'Please Select Stress', '');

            return;
        }

        if (this.quantityLossChartForm.get('commodityId').value == 0) {
            this.errorModal.showModal('ERROR', 'Please Select Commodity', '');

            return;
        }
        if (this.quantityLossChartForm.get('phenophaseId').value == 0) {
            this.errorModal.showModal('ERROR', 'Please Select Phenophase', '');
            return;
        }
        // if (this.quantityLossChartForm.get('criteriaId').value == 0) {
        //   alert('Please Select Criteria');
        //   return;
        // }
        
        if (this.mode == 'edit') {
            this.updateQuantityLossChart();
        } else {
            this.addQuantityLossChart();
        }
    }

    addQuantityLossChart() {
        this.agriQuantityLossChartService.CreateQuantityLossChart(this.quantityLossChartForm.value).subscribe((res: any) => {
            this.isSubmitted = true;
            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                    this._statusMsg = res.message;
                    this.QuantityLossChart = {};
                    this.mode = 'add';
                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            }
        }, err => {
            console.log(err);
        });
    }

    updateQuantityLossChart() {
        this.agriQuantityLossChartService.UpdateQuantityLossChart(this.quantityLossChartForm.value.id, this.quantityLossChartForm.value).subscribe((res: any) => {
            this.isSubmitted = true;
            if (res) {
                this.isSuccess = res.success;
                if (res.success) {
                    this._statusMsg = res.message;
                    this.QuantityLossChart = {};
                    this.mode = 'add';
                    // this.quantityLossChartForm.reset();

                    this.createQuantityLossChartForm();

                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            }
        }, err => {
            console.log(err);
        });
    }

    nInit() {
    }

    // loadAllGetByCommodityId(event:Event) : void {
    //   let index:number = event.target["selectedIndex"] - 1;
    //  if(index ==-1) {
    //      return;
    //   }
    //   let commodityId = this.commodityList[index].commodityId;
    //   this.agriPhenophaseService.GetAllPhenophaseByCommodityId(commodityId).subscribe(
    //     (data: {}) => {
    //       this.phenophaseList = data;
    //       console.log(this.phenophaseList);
    //     }
    //   );
    // }
    loadAllGetByCommodityId() {
        let commodityId = this.quantityLossChartForm.value.commodityId;
        this.agriCommodityPhenophaseService.GetByCommodityId(commodityId).subscribe(
            (data: {}) => {
                this.phenophaseList = data;
                console.log(this.phenophaseList);
            }
        );
    }

    cmd(_id: number) {
        this._cmd = _id;
    }

    phn(_data: number) {
        this.zonalStressDurationService.getAllStressByCommoditiesId(this._cmd, _data).subscribe(
            (data: {}) => {
                this.stressList = data;
                console.log(this.stressList);
            }
        );
    }


    loadAllCommodityByPhenophase() {
        let commodityId = this.quantityLossChartForm.value.commodityId;
        console.log(commodityId);
        this.agriCommodityService.getCommodityByPhenophaseId(commodityId).subscribe(
            (data: {}) => {
                this.phenophaseList = data;
                console.log(this.phenophaseList);
            }
        );
    }

    async delay(ms: number) {
        await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
    }


    downloadExcelFormat() {
        var tableName = 'agri_quantity_loss_chart';
        this.apiUtilService.downloadExcelFormat(tableName);
    }//downloadExcelFormat


    uploadExcel(element) {
        var file: File = element.target.files[0];
        this.fileUpload = file;
    }

    submitExcelForm() {
        this.apiUtilService.uploadExcelFile(this.fileUpload).subscribe(res => {
            this.isSubmittedBulk = true;

            if (res) {
                this.isSuccessBulk = res.success;
                if (res.success) {
                    this.successModal.showModal('SUCCESS', res.message, '');
                } else {
                    this.errorModal.showModal('ERROR', res.error, '');
                }
            }
        });
    }

    modalSuccess($event: any) {
        this.router.navigate(['/yield/quantity-loss-chart']);
    }
}

import { AgriStressControlMeasuresService } from '../../services/agri-stress-control-measures.service';
import { Component, OnInit, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import { ZonalStressControlRecommendationService } from '../../services/zonal-stress-control-recommendation.service';
import { PageAgriStressControlRecommendation } from '../../models/PageAgriStressControlRecommendation';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { BulkDataService } from '../../services/bulk-data.service';
import {DrkServiceService} from '../../../services/drk-service.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AgriRecommendationService } from '../../../stress/services/agri-recommendation.service';
import { GeoStateService } from '../../../geo/services/geo-state.service';
import { GeoDistrictService } from '../../../geo/services/geo-district.service';
import { AgriCommodityAgrochemicalService } from '../../services/agri-commodity-agrochemical.service';
import { AgriCommodityService } from '../../services/agri-commodity.service';
import { AgriStressTypeService } from '../../services/agri-stress-type.service';
import { ZonalStressDurationService } from '../../services/zonal-stress-duration.service';
import { AgriControlMeasuresService } from '../../services/agri-control-measures.service';

@Component({
  selector: 'app-zonal-stress-control-recommendation',
  templateUrl: './zonal-stress-control-recommendation.component.html',
  styleUrls: ['./zonal-stress-control-recommendation.component.scss']
})
export class ZonalStressControlRecommendationComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  strControlRecStatus;
  pageAgriStressControlRecommendation: PageAgriStressControlRecommendation;
  selectedPage: number = 1;
  maxSize: number = 10;
  searchText: any = "";
  recordsPerPage: number = 10;
  records: any = [];


  StressControlRecommendationList: any = [];
  checkedList: any = [];
  isValid: number = 1;
  missing : any="";

  filterFG: FormGroup;
  filter : any = "";
  searchFieldList = [{id:1,name:'State'},{id:2,name:'District'},{id:3,name:'Commodity'},{id:4,name:'Control Measure'},{id:5,name:'Stress'},{id:6,name:'Recommendation Name'},{id:7,name:'Agrochemical'}];
  selection:any = [];
  stateList: any = [];
  districtList: any = [];
  commodityList: any = [];
  controlMeasureList: any = [];
  stressList: any = [];
  recommendationList: any = [];
  agrochemicalList: any = [];
  isStateHidden: boolean = true;
  isDistrictHidden: boolean = true;
  isCommodityHidden: boolean = true;
  isControlMeasureHidden: boolean = true;
  isStressHidden: boolean = true;
  isRecommendationNameHidden: boolean = true;
  isAgrochemicalHidden: boolean = true;


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    // this.loadAllStressControlRecommendation();
    this.getPageAgriStressControlRecommendation(0, this.isValid,'','','','','','','','');
    this.strControlRecStatus = globalConstants;
    // this.bulkDatas.disbled
    this.filterFormControl();
    this.loadAllState();
    this.GetAllDistrict();
    this.loadAllCommodity();
    this.loadAllControlMeasure();
    this.loadAllStress();
    this.getRecommendationList();
    this.getAgrochemicalList();
  }

  constructor(public bulkDatas: BulkDataService, el:ElementRef,private rendere: Renderer2,private rendere2: Renderer2,
    public zonalStressControlRecommendationService : ZonalStressControlRecommendationService,
    private userRightsService: UserRightsService,
    private drkServiceService: DrkServiceService,
    public fb: FormBuilder, public geoStateService : GeoStateService,public geoDistrictService : GeoDistrictService, private agriCommodityService: AgriCommodityService,
    private zonalStressDurationService: ZonalStressDurationService, private agriStressControlMeasureService: AgriControlMeasuresService,
    private agriRecommendationService: AgriRecommendationService,private agriAgrochemicalService: AgriCommodityAgrochemicalService

  ){ }
  


   // StressControlRecommendation list
  //  loadAllStressControlRecommendation() {
  //   return this.agriStressControlRecommendationService.GetAllAgriStressControlRecommendation().subscribe((data: {}) => {
  //     this.StressControlRecommendationList = data;
  //   })
  // }

    // // Delete StressControlRecommendation
    // deleteStressControlRecommendation(data){
    //   var index = index = this.StressControlRecommendationList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriStressControlRecommendationService.DeleteStressControlRecommendation(data.id).subscribe(res => {
    //     this.StressControlRecommendationList.splice(index, 1)
    //      console.log('StressControlRecommendation deleted!')
    //    })
    // }
 
    onSelect(page: number): void {
      (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
      console.log("selected page : " + page);
      this.getPageAgriStressControlRecommendation(page, this.isValid,this.filterFG.value.stateCode,this.filterFG.value.districtCode,this.filterFG.value.commodityId,this.filterFG.value.controlMeasureId,this.filterFG.value.stressId,this.filterFG.value.recomendationID,this.filterFG.value.agrochemicalId,this.filter);
    }
  
    getPageAgriStressControlRecommendation(page: number, isValid: number,stateCode,districtCode,commodityId,controlMeasureId,stressId,recomendationID,agrochemicalId,filter): void {
      this.zonalStressControlRecommendationService.getPageAgriStressControlRecommendation(page, this.recordsPerPage, this.searchText, isValid,this.missing,stateCode,districtCode,commodityId,controlMeasureId,stressId,recomendationID,agrochemicalId,filter)
        .subscribe(page => this.pageAgriStressControlRecommendation = page)
    }

    loadData(event: any) {
      console.log('pages ', event.target.value);
      this.recordsPerPage = event.target.value || 10;
      this.zonalStressControlRecommendationService.getPageAgriStressControlRecommendation(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing,this.filterFG.value.stateCode,this.filterFG.value.districtCode,this.filterFG.value.commodityId,this.filterFG.value.controlMeasureId,this.filterFG.value.stressId,this.filterFG.value.recomendationID,this.filterFG.value.agrochemicalId,this.filter)
        .subscribe(page => this.pageAgriStressControlRecommendation = page);
    }

  // Delete 
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg, data);
  }

  recordPerPage(){
    
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'approve') {
        observable = this.zonalStressControlRecommendationService.ApproveStressControlRecommendation(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.zonalStressControlRecommendationService.FinalizeStressControlRecommendation(event.id);
      } else if (event.flag == 'delete') {
        observable = this.zonalStressControlRecommendationService.DeleteAgriStressControlRecommendation(event.id);
      } else if (event.flag == 'reject') {
        observable = this.zonalStressControlRecommendationService.RejectStressControlRecommendation(event.id);
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this.successModal.showModal('SUCCESS', res.message, '');
          } else {
            this.errorModal.showModal('ERROR', res.error, '');
          }
        }
      }, err => {
        this.errorModal.showModal('ERROR', err.error, '');
      });
    }
  }

  searchStressControlRecommendation() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageAgriStressControlRecommendation(this.selectedPage - 1, this.isValid,this.filterFG.value.stateCode,this.filterFG.value.districtCode,this.filterFG.value.commodityId,this.filterFG.value.controlMeasureId,this.filterFG.value.stressId,this.filterFG.value.recomendationID,this.filterFG.value.agrochemicalId,this.filter);
  }
  bulkData(key,tableName){

    let Values = []
    let getValue = document.querySelectorAll<HTMLInputElement>('table tbody input:checked')
   
    getValue.forEach(function(data,i){
      Values.push(data.value)
    })
    let AllData = {status:key, tableName:tableName, ids:Values.toString()}

    this.bulkDatas.getData(AllData)
        .subscribe( data => {
          data
          if(data.success == true){
            this.successModal.showModal('SUCCESS', data.message, '');

          }else {
            this.errorModal.showModal('ERROR', data.error, '');

          }

        })

  }

  sortData(sort: Sort) {
    const data = this.pageAgriStressControlRecommendation.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriStressControlRecommendation.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageAgriStressControlRecommendation.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case 'state':
          return compare(firstValue.state, secondValue.state, isAsc);
          case 'aczName':
          return compare(firstValue.aczName, secondValue.aczName, isAsc);
        case 'zonalCommodity':
          return compare(firstValue.zonalCommodity, secondValue.zonalCommodity, isAsc);
        case 'zonalVariety':
          return compare(firstValue.zonalVariety, secondValue.zonalVariety, isAsc);
        case 'stress':
          return compare(firstValue.stress, secondValue.stress, isAsc);
        case 'stressControlMeasure':
          return compare(firstValue.stressControlMeasure, secondValue.stressControlMeasure, isAsc);
        case 'instruction':
          return compare(firstValue.instructions, secondValue.instructions, isAsc);
        case 'agrochemical':
          return compare(firstValue.agrochemical, secondValue.agrochemical, isAsc);
        // case 'dosePerHectare':
        //   return compare(firstValue.dosePerHectare, secondValue.dosePerHectare, isAsc);
        // case 'perHectareUOM':
        //   return compare(firstValue.perHectareUOM, secondValue.perHectareUOM, isAsc);
        case 'dosePerAcre':
          return compare(firstValue.dosePerAcre, secondValue.dosePerAcre, isAsc);
        case 'perAcreUOM':
          return compare(firstValue.perAcreUOM, secondValue.perAcreUOM, isAsc);
        // case 'waterPerHectare':
        //   return compare(firstValue.waterPerHectare, secondValue.waterPerHectare, isAsc);
        // case 'perHectareWaterUOM':
        //   return compare(firstValue.perHectareWaterUOM, secondValue.perHectareWaterUOM, isAsc);
        case 'waterPerAcre':
          return compare(firstValue.waterPerAcre, secondValue.waterPerAcre, isAsc);
        case 'perAcreWaterUOM':
          return compare(firstValue.perAcreWaterUOM, secondValue.perAcreWaterUOM, isAsc);
        case 'agroApplicationType':
          return compare(firstValue.agroApplicationType, secondValue.agroApplicationType, isAsc);
        case 'agroChemicalInstructions':
          return compare(firstValue.agroChemicalInstructions, secondValue.agroChemicalInstructions, isAsc);
        case globalConstants.STATUS:
          return compare(firstValue.status, secondValue.status, isAsc);
        default:
          return 0;
      }
    });
  }

  modalSuccess($event: any) {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    if (this.selectedPage > 0) {
      this.onSelect((this.selectedPage - 1));
      (document.querySelector('thead th input') as HTMLInputElement).checked = false
    } else {
      this.onSelect(this.selectedPage);
      (document.querySelector('thead th input') as HTMLInputElement).checked = false
    }
  }

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('agri_stress_control_recommendation').subscribe(res => {
      this.isSubmitted = true;
      if (res) {
        this.ngOnInit();
        this.isSuccess = res.success;
        if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
        }
      }
    });
  }

  onClickMissing(){
    this.missing = 1;
    this.getPageAgriStressControlRecommendation(0,this.isValid,this.filterFG.value.stateCode,this.filterFG.value.districtCode,this.filterFG.value.commodityId,this.filterFG.value.controlMeasureId,this.filterFG.value.stressId,this.filterFG.value.recomendationID,this.filterFG.value.agrochemicalId,this.filter);
  }

  moveToMaster(id){
    this.zonalStressControlRecommendationService.moveToMaster(id).subscribe(res => {
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
    this.missing = 0;
    this.ngOnInit();
  }

  filterFormControl() {
    this.filterFG = this.fb.group({
      // filter: ['',Validators.required],
      stateCode: [''],
      districtCode: [''],
      commodityId: [''],
      controlMeasureId: [''],
      stressId: [''],
      recomendationID: [''],
      agrochemicalId: ['']
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.filterFG.controls){

      this.filterFG.get(controller).markAsTouched();

    }
    
    // if(this.filterFG.value.filter == null || this.filterFG.value.filter == ''){
    //   this.errorModal.showModal('ERROR', 'Filter  is required. Please Select Filter.', '');
    //   return;
    // }

    this.onClickSearchFilters();
  }

  onClose(){
    // console.log("inside close...");
    if(this.selectedPage >= 2){
      this.getPageAgriStressControlRecommendation(this.selectedPage - 1,this.isValid,this.filterFG.value.stateCode,this.filterFG.value.districtCode,this.filterFG.value.commodityId,this.filterFG.value.controlMeasureId,this.filterFG.value.stressId,this.filterFG.value.recomendationID,this.filterFG.value.agrochemicalId,this.filter);
      this.strControlRecStatus = globalConstants;
      }else{
      this.ngOnInit();
      }
  }

  getSelection(data) {
    return this.selection.findIndex(s => s.id === data.id) !== -1;
  }
  
  changeHandler(data: any, event: KeyboardEvent) {
    const id = data.id;
  
    const index = this.selection.findIndex(u => u.id === id);
    if (index === -1) {
      // ADD TO SELECTION
      // this.selection.push(item);
      this.selection = [...this.selection, data];
      if(id === 1){
        this.isStateHidden = false;
        this.loadAllState();
      }else if(id === 2){
        this.isDistrictHidden = false;
        this.GetAllDistrict();
      }else if(id === 3){
        this.isCommodityHidden = false;
        this.loadAllCommodity();
      }else if(id === 4){
        this.isControlMeasureHidden = false;
        this.loadAllControlMeasure();
      }else if(id === 5){
        this.isStressHidden = false;
        this.loadAllStress();
      }else if(id === 6){
        this.isRecommendationNameHidden = false;
        this.getRecommendationList();
      }else if(id === 7){
        this.isAgrochemicalHidden = false;
        this.getAgrochemicalList();
      }
    } else {
      // REMOVE FROM SELECTION
      this.selection = this.selection.filter(obj => obj.id !== data.id)
      // this.selection.splice(index, 1)
      if(id === 1){
        this.isStateHidden = true;
        this.filterFG.value.stateCode == '';
      }else if(id === 2){
        this.isDistrictHidden = true;
        this.filterFG.value.districtCode == '';
      }else if(id === 3){
        this.isCommodityHidden = true;
        this.filterFG.value.commodityId == '';
      }else if(id === 4){
        this.isControlMeasureHidden = true;
        this.filterFG.value.controlMeasureId == '';
      }else if(id === 5){
        this.isStressHidden = true;
        this.filterFG.value.stressId == '';
      }else if(id === 6){
        this.isRecommendationNameHidden = true;
        this.filterFG.value.recomendationID == '';
      }else if(id === 7){
        this.isAgrochemicalHidden = true;
        this.filterFG.value.agrochemicalId == '';
      }
    }
  }

  //State list
  loadAllState(){
    return this.geoStateService.GetAllState().subscribe((data: {}) => {
      this.stateList = data;
    })
  }

    //State list
    GetAllDistrict(){
      return this.geoDistrictService.GetAllDistrict().subscribe((data: {}) => {
        this.districtList = data;
      })
    }

  loadAllDistrictsByState(event:Event) : void {
    this.geoDistrictService.GetAllDistrictByStateCode(this.filterFG.value.stateCode).subscribe(
      (data: {}) => {
        this.districtList = data;
        console.log(this.districtList);
      }
    );
  }

  loadAllCommodity() {
    return this.agriCommodityService.GetAllCommoditise().subscribe((data: any) => {
      this.commodityList = data;
    }, err => {
      alert(err)
    })
  }

  loadAllControlMeasure() {
    return this.agriStressControlMeasureService.GetAllAgriControlMeasures().subscribe((data: any) => {
      this.controlMeasureList = data;
    }, err => {
      alert(err)
    })
  }

  loadAllStress() {
    return this.zonalStressDurationService.GetAllStress().subscribe((data: any) => {
      this.stressList = data;
    }, err => {
      alert(err)
    })
  }

  getRecommendationList() {
    return this.agriRecommendationService.getRecommendationList().subscribe((data: {}) => {
      this.recommendationList = data;
    })
  }

  getAgrochemicalList() {
    return this.agriAgrochemicalService.getAgrochemicalList().subscribe((data: any) => {
      this.agrochemicalList = data;
    }, err => {
      alert(err)
    })
  }

  onChangeList(){
    
  }

  onClickFilter(){
      this.isStateHidden = true;
      this.isDistrictHidden = true;
      this.isCommodityHidden = true;
      this.isControlMeasureHidden = true;
      this.isStressHidden = true;
      this.isRecommendationNameHidden = true;
      this.isAgrochemicalHidden = true;
      this.filterFormControl();
      for (var searchField of this.searchFieldList) {
        const id = searchField.id;
        this.selection = this.selection.filter(obj => obj.id !== searchField.id)
      }
  }

  onClickSearchFilters(){
      
      this.filter = 1;
  
      this.getPageAgriStressControlRecommendation(0,this.isValid,this.filterFG.value.stateCode,this.filterFG.value.districtCode,this.filterFG.value.commodityId,this.filterFG.value.controlMeasureId,this.filterFG.value.stressId,this.filterFG.value.recomendationID,this.filterFG.value.agrochemicalId,this.filter);
      document.getElementById('filterModal').click();
  }

}

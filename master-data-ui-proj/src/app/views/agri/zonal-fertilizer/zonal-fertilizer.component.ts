import { Component, OnInit, ViewChild } from '@angular/core';
import { ZonalFertilizerService } from '../services/zonal-fertilizer.service';
import { PageZonalFertilizer } from '../models/PageZonalFertilizer';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {Sort} from '@angular/material';
import { BulkDataService } from '../services/bulk-data.service';
import {DrkServiceService} from '../../services/drk-service.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { GeoStateService } from '../../geo/services/geo-state.service';
import { AgriDoseFactorService } from '../services/agri-dose-factor.service';
import { AgriSeasonService } from '../services/agri-season.service';

@Component({
  selector: 'app-zonal-fertilizer',
  templateUrl: './zonal-fertilizer.component.html',
  styleUrls: ['./zonal-fertilizer.component.scss']
})
export class ZonalFertilizerComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  fertilizerStatus;
  FertilizerList: any = [];
  pageFertilizer: PageZonalFertilizer;
  selectedPage: number = 1;
  maxSize = 10;
  searchText: any="";
  isValid: number = 1;
  missing : any="";
  recordsPerPage: number = 10;
  records: any = [];

  filterFG: FormGroup;
  filter : any = "";
  searchFieldList = [{id:1,name:'State'},{id:2,name:'Season'},{id:3,name:'Dose Factor'},{id:4,name:'Commodity'},{id:5,name:'Fertilizer'}];
  selection:any = [];
  stateList: any = [];
  seasonList: any = [];
  doseFactorList: any = [];
  commodityList: any = [];
  fertilizerList: string[] = ['Nitrogen', 'Phosphorus', 'Potassium'];
  isStateHidden: boolean = true;
  isSeasonHidden: boolean = true;
  isDoseFactorHidden: boolean = true;
  isCommodityHidden: boolean = true;
  isFertilizerHidden: boolean = true;


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageAgriFertilizer(0, this.isValid,'','','','','','');
    this.fertilizerStatus = globalConstants;
    //this.loadAllFertilizer();
    this.filterFormControl();
  }

  constructor(public bulkDatas: BulkDataService,
    public agriFertilizerService: ZonalFertilizerService,
    private userRightsService: UserRightsService,
              private drkServiceService: DrkServiceService,
              public fb: FormBuilder,
              public commodityService: AgriCommodityService,
              public geoStateService: GeoStateService,
              public agriDoseFactorService : AgriDoseFactorService,
              public agriSeasonService: AgriSeasonService

  ) { }

  // Fertilizer list
  loadAllFertilizer() {
    return this.agriFertilizerService.GetAllFertilizer().subscribe((data: {}) => {
      this.FertilizerList = data;
    })
  }


  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;

    console.log("selected page : " + page);
    // this.selectedPage = page;
    this.getPageAgriFertilizer(page, this.isValid,this.filterFG.value.stateCode,this.filterFG.value.seasonId,this.filterFG.value.doseFactorId,this.filterFG.value.commodityId,this.filterFG.value.name,this.filter);
  }

  getPageAgriFertilizer(page: number, isValid: number,stateCode,seasonId,doseFactorId,commodityId,name,filter): void {
    this.agriFertilizerService.getPageAgriFertilizer(page, this.recordsPerPage, this.searchText, isValid,this.missing,stateCode,seasonId,doseFactorId,commodityId,name,filter)
      .subscribe(page => this.pageFertilizer = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriFertilizerService.getPageAgriFertilizer(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing,this.filterFG.value.stateCode,this.filterFG.value.seasonId,this.filterFG.value.doseFactorId,this.filterFG.value.commodityId,this.filterFG.value.name,this.filter)
      .subscribe(page => this.pageFertilizer = page);
  }

  // Delete Fertilizer
  deleteFertilizer(data) {
    var index = index = this.FertilizerList.map(x => { return x.name }).indexOf(data.name);
    return this.agriFertilizerService.DeleteFertilizer(data.id).subscribe(res => {
      this.FertilizerList.splice(index, 1)
      console.log('Fertilizer deleted!')
    })
  }


  // Delete Fertilizer
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

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == 'approve') {
        observable = this.agriFertilizerService.ApproveFertilizer(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.agriFertilizerService.FinalizeFertilizer(event.id);
      } else if (event.flag == 'delete') {
        observable = this.agriFertilizerService.DeleteFertilizer(event.id);
      } else if (event.flag == 'reject') {
        observable = this.agriFertilizerService.RejectFertilizer(event.id);
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
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

  searchFertilizer() {

    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageAgriFertilizer(this.selectedPage - 1, this.isValid,this.filterFG.value.stateCode,this.filterFG.value.seasonId,this.filterFG.value.doseFactorId,this.filterFG.value.commodityId,this.filterFG.value.name,this.filter);
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
    const data = this.pageFertilizer.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageFertilizer.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageFertilizer.content = data.sort((firstValue, secondValue) => {
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
        case 'doseFactor':
          return compare(firstValue.doseFactor, secondValue.doseFactor, isAsc);
        case globalConstants.NAME:
          return compare(firstValue.name, secondValue.name, isAsc);
        case 'dose':
          return compare(firstValue.dose, secondValue.dose, isAsc);
        case 'uom':
          return compare(firstValue.uom, secondValue.uom, isAsc);
        case 'note':
          return compare(firstValue.note, secondValue.note, isAsc);
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

    if (this.selectedPage > 0){
        this.onSelect((this.selectedPage - 1));
    } else {
        this.onSelect(this.selectedPage);
    }
  }
  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('agri_fertilizer').subscribe(res => {
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
    this.getPageAgriFertilizer(0,this.isValid,this.filterFG.value.stateCode,this.filterFG.value.seasonId,this.filterFG.value.doseFactorId,this.filterFG.value.commodityId,this.filterFG.value.name,this.filter);
  }

  moveToMaster(id){
    this.agriFertilizerService.moveToMaster(id).subscribe(res => {
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
      seasonId: [''],
      doseFactorId: [''],
      commodityId: [''],
      name: [''],
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
      this.getPageAgriFertilizer(this.selectedPage - 1,this.isValid,this.filterFG.value.stateCode,this.filterFG.value.seasonId,this.filterFG.value.doseFactorId,this.filterFG.value.commodityId,this.filterFG.value.name,this.filter);
      this.fertilizerStatus = globalConstants;
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
        this.isSeasonHidden = false;
        this.loadAllSeason();
      }else if(id === 3){
        this.isDoseFactorHidden = false;
        this.loadAllDoseFactor();
      }else if(id === 4){
        this.isCommodityHidden = false;
        this.loadAllCommodities();
      }else if(id === 5){
        this.isFertilizerHidden = false;
      }
    } else {
      // REMOVE FROM SELECTION
      this.selection = this.selection.filter(obj => obj.id !== data.id)
      // this.selection.splice(index, 1)
      if(id === 1){
        this.isStateHidden = true;
        this.filterFG.value.stateCode == '';
      }else if(id === 2){
        this.isSeasonHidden = true;
        this.filterFG.value.seasonId == '';
      }else if(id === 3){
        this.isDoseFactorHidden = true;
        this.filterFG.value.doseFactorId == '';
      }else if(id === 4){
        this.isCommodityHidden = true;
        this.filterFG.value.commodityId == '';
      }else if(id === 5){
        this.isFertilizerHidden = true;
        this.filterFG.value.name == '';
      }
    }
  }

 //State list
 loadAllState() {
  return this.geoStateService.GetAllState().subscribe((data: {}) => {
    this.stateList = data;
  });
}

//Commodity list
loadAllCommodities() {
  return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
    this.commodityList = data;
  });
}

//Season list
loadAllSeason() {
  return this.agriSeasonService.GetAllSeasons().subscribe((data: {}) => {
    this.seasonList = data;
  });
}

//DoseFactor List
loadAllDoseFactor() {
  return this.agriDoseFactorService.GetAllAgriDoseFactor().subscribe((data: {}) => {
    this.doseFactorList = data;
  });
}

  onChangeList(){
    
  }

  onClickFilter(){
      this.isStateHidden = true;
      this.isSeasonHidden = true;
      this.isDoseFactorHidden = true;
      this.isCommodityHidden = true;
      this.isFertilizerHidden = true;
      this.filterFormControl();
      for (var searchField of this.searchFieldList) {
        const id = searchField.id;
        this.selection = this.selection.filter(obj => obj.id !== searchField.id)
      }
  }

  onClickSearchFilters(){
    
     
      this.filter = 1;
  
      this.getPageAgriFertilizer(0,this.isValid,this.filterFG.value.stateCode,this.filterFG.value.seasonId,this.filterFG.value.doseFactorId,this.filterFG.value.commodityId,this.filterFG.value.name,this.filter);
      document.getElementById('filterModal').click();
  }


}

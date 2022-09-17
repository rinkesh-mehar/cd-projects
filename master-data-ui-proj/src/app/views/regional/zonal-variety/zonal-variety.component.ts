import { Validators } from '@angular/forms';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ZonalVarietyService } from '../services/zonal-variety.service';
import { PageZonalVariety } from '../models/PageZonalVariety';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {DrkServiceService} from '../../services/drk-service.service';
import {BulkDataService} from '../../agri/services/bulk-data.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { GeoStateService } from '../../geo/services/geo-state.service';
import { AgriSeasonService } from '../../agri/services/agri-season.service';
import { AgriCommodityService } from '../../agri/services/agri-commodity.service';
import { AgriVarietyService } from '../../agri/services/agri-variety.service';


@Component({
  selector: 'app-zonal-variety',
  templateUrl: './zonal-variety.component.html',
  styleUrls: ['./zonal-variety.component.scss']
})
export class ZonalVarietyComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  varietyStatus;
  VarietyList: any = [];
  pageVariety : PageZonalVariety;
  selectedPage : number = 1;
  searchText : any="";
  maxSize = 10;
  isValid: number = 1;
  missing : any="";
  whereClause : string="";
  filter : any = "";
  

  filterFG: FormGroup;
  filterList = [{id:1,name:'AND'},{id:2,name:'OR'}];
  searchFieldList = [{id:1,name:'State'},{id:2,name:'Season'},{id:3,name:'Commodity'},{id:4,name:'Variety'}];
  selection:any = [];
  stateList: any = [];
  seasonList: any = [];
  commodityList: any = [];
  varietyList: any = [];
  isStateHidden: boolean = true;
  isSeasonHidden: boolean = true;
  isCommodityHidden: boolean = true;
  isVarietyHidden: boolean = true;

  recordsPerPage: number = 10;
   records: any = [];
   


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageZonalVariety(0, this.isValid,'','','','','');
    //this.loadAllVariety();
    this.varietyStatus = globalConstants;
    this.filterFormControl();
  }

  constructor(
      public bulkDatas: BulkDataService,
      public zonalVarietyService: ZonalVarietyService,
    private userRightsService: UserRightsService,
    private drkServiceService: DrkServiceService,
    public geoStateService : GeoStateService,
    public agriSeasonService : AgriSeasonService,
    public commodityService : AgriCommodityService,
    public agriVarietyService :   AgriVarietyService,
    public fb: FormBuilder

  ){ }

  bulkData(key, tableName) {

    const Values = [];
    const getValue = document.querySelectorAll<HTMLInputElement>('table tbody input:checked');

    getValue.forEach(function (data, i) {
      Values.push(data.value);
    });

    const AllData = {status: key, tableName: tableName, ids: Values.toString()};

    this.bulkDatas.getData(AllData)
        .subscribe(data => {
          data;
          if (data.success == true) {
            this.successModal.showModal('SUCCESS', data.message, '');

          } else {
            this.errorModal.showModal('ERROR', data.error, '');

          }
        });
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : "+page);
    // this.selectedPage=page;
    this.getPageZonalVariety(page, this.isValid,this.filterFG.value.stateCode,this.filterFG.value.seasonId,this.filterFG.value.commodityId,this.filterFG.value.varietyId,this.filter);
  }
  
  getPageZonalVariety(page:number, isValid: number,stateCode,seasonId,commodityId,varietyId,filter): void {
    this.zonalVarietyService.getPageZonalVariety(page, this.recordsPerPage,this.searchText, isValid,this.missing,stateCode,seasonId,commodityId,varietyId,filter)
        .subscribe(page => this.pageVariety = page);
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.zonalVarietyService.getPageZonalVariety(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing,this.filterFG.value.stateCode,this.filterFG.value.seasonId,this.filterFG.value.commodityId,this.filterFG.value.varietyId,this.filter)
      .subscribe(page => this.pageVariety = page);
  }

    // // Delete Variety
    // deleteVariety(data){
    //   var index = index = this.VarietyList.map(x => {return x.name}).indexOf(data.name);
    //    return this.zonalVarietyService.DeleteVariety(data.id).subscribe(res => {
    //     this.VarietyList.splice(index, 1)
    //      console.log('Variety deleted!')
    //    })
    // }

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
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg , data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg , data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg , data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        observable = this.zonalVarietyService.ApproveVariety(event.id)
      } else if (event.flag == "finalize") {
        observable = this.zonalVarietyService.FinalizeVarietys(event.id)
      } else if (event.flag == "delete") {
        observable = this.zonalVarietyService.DeleteVariety(event.id)
      } else if (event.flag == "reject") {
        observable = this.zonalVarietyService.RejectVariety(event.id)
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

  searchVariety(){
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageZonalVariety(this.selectedPage -1, this.isValid,this.filterFG.value.stateCode,this.filterFG.value.seasonId,this.filterFG.value.commodityId,this.filterFG.value.varietyId,this.filter);
  
  }

  sortData(sort: Sort) {
    const data = this.pageVariety.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageVariety.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageVariety.content = data.sort((firstValue, secondValue) => {
      let isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'state':
          return compare(firstValue.state, secondValue.state, isAsc);
        case 'acz':
          return compare(firstValue.acz, secondValue.acz, isAsc);
        case 'zonalCommodity':
          return compare(firstValue.zonalCommodity, secondValue.zonalCommodity, isAsc);
        case 'zonalVariety':
            return compare(firstValue.zonalVariety, secondValue.zonalVariety, isAsc);
        case globalConstants.NAME:
          return compare(firstValue.variety, secondValue.variety, isAsc);
        case 'zvSowingWeekStart':
          return compare(firstValue.zvSowingWeekStart, secondValue.zvSowingWeekStart, isAsc);
        case 'zvSowingWeekEnd':
          return compare(firstValue.zvSowingWeekEnd, secondValue.zvSowingWeekEnd, isAsc);
        case 'zvHarvestWeekStart':
          return compare(+firstValue.zvHarvestWeekStart, +secondValue.zvHarvestWeekStart, isAsc);
        case 'zvHarvestWeekEnd':
          return compare(+firstValue.zvHarvestWeekEnd, +secondValue.zvHarvestWeekEnd, isAsc);
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
    // this.ngOnInit();
    // this.selectedPage = 1;
  
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      // console.log("Inside if");
    this.getPageZonalVariety(this.selectedPage - 1,this.isValid,this.filterFG.value.stateCode,this.filterFG.value.seasonId,this.filterFG.value.commodityId,this.filterFG.value.varietyId,this.filter);
    this.varietyStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
  }
  

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('regional_variety').subscribe(res => {
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
    this.getPageZonalVariety(0,this.isValid,this.filterFG.value.stateCode,this.filterFG.value.seasonId,this.filterFG.value.commodityId,this.filterFG.value.varietyId,this.filter);
  }


  moveToMaster(id){
    this.zonalVarietyService.moveToMaster(id).subscribe(res => {
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
      commodityId: [''],
      varietyId: [''],
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
      this.getPageZonalVariety(this.selectedPage - 1,this.isValid,this.filterFG.value.stateCode,this.filterFG.value.seasonId,this.filterFG.value.commodityId,this.filterFG.value.varietyId,this.filter);
      this.varietyStatus = globalConstants;
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
        this.isCommodityHidden = false;
        this.loadAllCommodities();
      }else if(id === 4){
        this.isVarietyHidden = false;
        this.loadAllVariety();
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
        this.isCommodityHidden = true;
        this.filterFG.value.commodityId == '';
      }else if(id === 4){
        this.isVarietyHidden = true;
        this.filterFG.value.varietyId == '';
      }
    }
    // this.whereClause = "";
  }

  loadAllState(){
    return this.geoStateService.GetAllState().subscribe((data: {}) => {
      this.stateList = data;
      })
    }

  loadAllSeason(){
      return this.agriSeasonService.GetAllSeasons().subscribe((data: {}) =>{
       this.seasonList = data;
      })
    }
   
        
  loadAllCommodities(){
      return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
      this.commodityList = data;
      })
    }
   
      
  loadAllVariety(){
        return this.agriVarietyService.GetAllVarieties().subscribe((data: {}) => {
        this.varietyList = data;
        })
    }

  onChangeList(){
    // this.whereClause = "";
  }

  onClickFilter(){
      this.isStateHidden = true;
      this.isSeasonHidden = true;
      this.isSeasonHidden = true;
      this.isVarietyHidden = true;
      this.filterFormControl();
      for (var searchField of this.searchFieldList) {
        const id = searchField.id;
        this.selection = this.selection.filter(obj => obj.id !== searchField.id)
      }
  }

  onClickSearchFilters(){
    
      console.log("StateCode : " + this.filterFG.value.stateCode + " Season : " + this.filterFG.value.seasonId + " Commodity : " 
      + this.filterFG.value.commodityId + " Variety : " + this.filterFG.value.varietyId);
      
      this.filter = 1;
  
      this.getPageZonalVariety(0,this.isValid,this.filterFG.value.stateCode,this.filterFG.value.seasonId,this.filterFG.value.commodityId,this.filterFG.value.varietyId,this.filter);
      document.getElementById('filterModal').click();
  }

}

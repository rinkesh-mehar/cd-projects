import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriVarietyService } from '../services/agri-variety.service';
import { PageAgriVariety } from '../models/PageAgriVariety';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {DrkServiceService} from '../../services/drk-service.service';
import {BulkDataService} from '../services/bulk-data.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { AgriHsCodeService } from '../services/agri-hs-code.service';


@Component({
  selector: 'app-agri-variety',
  templateUrl: './agri-variety.component.html',
  styleUrls: ['./agri-variety.component.scss']
})
export class AgriVarietyComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  
  VarietyList: any = [];
  
  //For Pagination
  varietyStatus;
  pageVariety : PageAgriVariety ;
  selectedPage : number = 1;
  maxSize : number=10;
  searchText : any="";
  isValid: number = 1;
  missing : any="";

  recordsPerPage: number = 10;
   records: any = [];

   filterFG: FormGroup;
  filter : any = "";
  searchFieldList = [{id:1,name:'Commodity'},{id:2,name:'HSN Code'},{id:3,name:'Domestic Restrictions'},{id:4,name:'International Restrictions'}];
  selection:any = [];
  commodityList: any = [];
  hsnCodeList: any = [];
  isCommodityHidden: boolean = true;
  ishsnCodeHidden: boolean = true;
  isDomesticRestrictionsHidden: boolean = true;
  isInternationalRestrictionsHidden: boolean = true;


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageAgriVariety(0, this.isValid,'','','','','');
    //this.loadAllVarieties();
    this.varietyStatus = globalConstants;
    this.filterFormControl();

  }

  constructor(
      public bulkDatas: BulkDataService,
    public agriVarietyService: AgriVarietyService,
    private userRightsService: UserRightsService,
    private drkServiceService: DrkServiceService,public fb: FormBuilder,
    public commodityService: AgriCommodityService,public hsCodeService: AgriHsCodeService
  ){ }
  

   // Variety list
   loadAllVarieties() {
    return this.agriVarietyService.GetAllVarieties().subscribe((data: {}) => {
      this.VarietyList = data;
    })
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : "+page);
    this.selectedPage=page;
    this.getPageAgriVariety(page, this.isValid,this.filterFG.value.commodityId,this.filterFG.value.hsCodeId,this.filterFG.value.domesticRestrictions,this.filterFG.value.internationalRestrictions,this.filter);
  }

  getPageAgriVariety(page:number, isValid: number,commodityId,hsCodeId,domesticRestrictions,internationalRestrictions,filter): void {
    this.agriVarietyService.getPageAgriVariety(page, this.recordsPerPage,this.searchText, isValid,this.missing,commodityId,hsCodeId,domesticRestrictions,internationalRestrictions,filter)
        .subscribe(page => this.pageVariety = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriVarietyService.getPageAgriVariety(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing,this.filterFG.value.commodityId,this.filterFG.value.hsCodeId,this.filterFG.value.domesticRestrictions,this.filterFG.value.internationalRestrictions,this.filter)
      .subscribe(page => this.pageVariety = page);
  }

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

  // // Delete Variety
    // deleteVariety(data){
    //   var index = index = this.VarietyList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriVarietyService.DeleteVariety(data.id).subscribe(res => {
    //     this.VarietyList.splice(index, 1)
    //      console.log('Variety deleted!')
    //    })
    // }
  
    // Delete 
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.name, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.name, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.name, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.name, data);
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        observable = this.agriVarietyService.ApproveVariety(event.id)
      } else if (event.flag == "finalize") {
        observable = this.agriVarietyService.FinalizeVariety(event.id)
      } else if (event.flag == "delete") {
        observable = this.agriVarietyService.DeleteVariety(event.id)
      } else if (event.flag == "reject") {
        observable = this.agriVarietyService.RejectVariety(event.id)
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
    this.getPageAgriVariety(this.selectedPage - 1, this.isValid,this.filterFG.value.commodityId,this.filterFG.value.hsCodeId,this.filterFG.value.domesticRestrictions,this.filterFG.value.internationalRestrictions,this.filter);
  }

  sortData(sort: Sort) {
    const data = this.pageVariety.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageVariety.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageVariety.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case globalConstants.NAME:
          return compare(firstValue.name, secondValue.name, isAsc);
        case 'commodity':
          return compare(firstValue.commodity, secondValue.commodity, isAsc);
        case 'hsCode':
          return compare(firstValue.hsCode, secondValue.hsCode, isAsc);
        case 'domesticRestrictions':
          return compare(firstValue.domesticRestrictions, secondValue.domesticRestrictions, isAsc);
        case 'internationalRestrictions':
          return compare(firstValue.internationalRestrictions, secondValue.internationalRestrictions, isAsc);
        case 'varietyCode':
          return compare(firstValue.varietyCode, secondValue.varietyCode, isAsc);
        case 'mspGroup':
          return compare(firstValue.mspGroup, secondValue.mspGroup, isAsc);
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
    console.log("page : " + this.selectedPage);
    if(this.selectedPage >= 2){
      console.log("Inside if");
    this.getPageAgriVariety(this.selectedPage - 1,this.isValid,this.filterFG.value.commodityId,this.filterFG.value.hsCodeId,this.filterFG.value.domesticRestrictions,this.filterFG.value.internationalRestrictions,this.filter);
    this.varietyStatus = globalConstants;
    }else{
      console.log("Inside else");
    this.ngOnInit();
    }
  }

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('agri_variety').subscribe(res => {
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
    this.getPageAgriVariety(0,this.isValid,this.filterFG.value.commodityId,this.filterFG.value.hsCodeId,this.filterFG.value.domesticRestrictions,this.filterFG.value.internationalRestrictions,this.filter);
  }

  moveToMaster(id){
    this.agriVarietyService.moveToMaster(id).subscribe(res => {
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
      commodityId: [''],
      hsCodeId: [''],
      domesticRestrictions: [''],
      internationalRestrictions: [''],
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
      this.getPageAgriVariety(this.selectedPage - 1,this.isValid,this.filterFG.value.stateCode,this.filterFG.value.seasonId,this.filterFG.value.commodityId,this.filterFG.value.varietyId,this.filter);
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
        this.isCommodityHidden = false;
        this.loadAllCommodities();
      }else if(id === 2){
        this.ishsnCodeHidden = false;
        this.loadAllHsCode();
      }else if(id === 3){
        this.isDomesticRestrictionsHidden = false;
      }else if(id === 4){
        this.isInternationalRestrictionsHidden = false;
      }
    } else {
      // REMOVE FROM SELECTION
      this.selection = this.selection.filter(obj => obj.id !== data.id)
      // this.selection.splice(index, 1)
      if(id === 1){
        this.isCommodityHidden = true;
        this.filterFG.value.commodityId == '';
      }else if(id === 2){
        this.ishsnCodeHidden = true;
        this.filterFG.value.hsCodeId == '';
      }else if(id === 3){
        this.isDomesticRestrictionsHidden = true;
        this.filterFG.value.domesticRestrictions == '';
      }else if(id === 4){
        this.isInternationalRestrictionsHidden = true;
        this.filterFG.value.internationalRestrictions == '';
      }
    }
  }

  //Commodity list
  loadAllCommodities() {
    return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
      this.commodityList = data;
    })
  }

  //HsCodeList 
  loadAllHsCode() {
    return this.hsCodeService.GetAllHsCode().subscribe((data: {}) => {
      this.hsnCodeList = data;
    })
  }

  onChangeList(){
    
  }

  onClickFilter(){
      this.isCommodityHidden = true;
      this.ishsnCodeHidden = true;
      this.isDomesticRestrictionsHidden = true;
      this.isInternationalRestrictionsHidden = true;
      this.filterFormControl();
      for (var searchField of this.searchFieldList) {
        const id = searchField.id;
        this.selection = this.selection.filter(obj => obj.id !== searchField.id)
      }
  }

  onClickSearchFilters(){
    
     
      this.filter = 1;
  
      this.getPageAgriVariety(0,this.isValid,this.filterFG.value.commodityId,this.filterFG.value.hsCodeId,this.filterFG.value.domesticRestrictions,this.filterFG.value.internationalRestrictions,this.filter);
      document.getElementById('filterModal').click();
  }
}

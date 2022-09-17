import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriSeedTreatmentService } from '../services/agri-seed-treatment.service';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { globalConstants } from '../../global/globalConstants';
import { PageAgriSeedTreatment } from '../models/PageAgriSeedTreatment';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import { BulkDataService } from '../services/bulk-data.service';
import {DrkServiceService} from '../../services/drk-service.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AgriCommodityService } from '../services/agri-commodity.service';
import { AgriVarietyService } from '../services/agri-variety.service';
import { GeneralUomService } from '../../general/services/general-uom.service';


@Component({
  selector: 'app-agri-seed-treatment',
  templateUrl: './agri-seed-treatment.component.html',
  styleUrls: ['./agri-seed-treatment.component.scss']
})
export class AgriSeedTreatmentComponent implements OnInit {
  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;


  isSubmitted: boolean = false;
   isSuccess: boolean = false;
   _statusMsg: string;
  seedTreatmentStatus;
  SeedTreatmentList: any = [];

  pageAgriSeedTreatment: PageAgriSeedTreatment;
  selectedPage: number = 0;
  maxSize: number = 10;
  searchText: any = "";
  isValid: number = 1;
  missing : any="";
  recordsPerPage: number = 10;
  records: any = [];

  filterFG: FormGroup;
  filter : any = "";
  searchFieldList = [{id:1,name:'Commodity'},{id:2,name:'Variety'},{id:3,name:'Seed Treatment Agent Name'}];
  selection:any = [];
  commodityList: any = [];
  varietyList: any = [];
  uomList: any = [];
  isCommodityHidden: boolean = true;
  isVarietyHidden: boolean = true;
  isSeedTreatmentAgentName: boolean = true;

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    // this.loadAllSeedTreatment();
    this.getPageAgriSeedTreatment(0, this.isValid,'','','','');
    this.seedTreatmentStatus = globalConstants;
    this.filterFormControl();
  }

  constructor(public bulkDatas: BulkDataService, private userRightsService: UserRightsService,
              public agriSeedTreatmentService: AgriSeedTreatmentService,
              private drkServiceService: DrkServiceService,public fb: FormBuilder,
              public commodityService: AgriCommodityService,
              public agriVarietyService: AgriVarietyService,
              public generalUomService: GeneralUomService
  ) {
  }

   // SeedTreatment list
   loadAllSeedTreatment() {
    return this.agriSeedTreatmentService.GetAllSeedTreatment().subscribe((data: {}) => {
      this.SeedTreatmentList = data;
    })
  }

    // Delete SeedTreatment
    // deleteSeedTreatment(data){
    //   var index = index = this.SeedTreatmentList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriSeedTreatmentService.DeleteSeedTreatment(data.id).subscribe(res => {
    //     this.SeedTreatmentList.splice(index, 1)
    //      console.log('SeedTreatment deleted!')
    //    })
    // }

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
    trimValue(formControl) { 
      formControl.setValue(formControl.value.trim()); 
    }

    onSelect(page: number): void {
      (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
      console.log("selected page : " + page);
      // this.selectedPage = page;
      this.getPageAgriSeedTreatment(page, this.isValid,this.filterFG.value.commodityId,this.filterFG.value.varietyId,this.filterFG.value.name,this.filter);
    }
  
    getPageAgriSeedTreatment(page: number, isValid: number,commodityId,varietyId,name,filter): void {
      this.agriSeedTreatmentService.getPageAgriSeedTreatment(page, this.recordsPerPage, this.searchText, isValid,this.missing,commodityId,varietyId,name,filter)
        .subscribe(page => this.pageAgriSeedTreatment = page)
    }

    loadData(event: any) {
      console.log('pages ', event.target.value);
      this.recordsPerPage = event.target.value || 10;
      this.agriSeedTreatmentService.getPageAgriSeedTreatment(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing,this.filterFG.value.commodityId,this.filterFG.value.varietyId,this.filterFG.value.name,this.filter)
        .subscribe(page => this.pageAgriSeedTreatment = page);
    }


  // Delete SeedTreatment
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
      if (event.flag == 'approve') {
        observable = this.agriSeedTreatmentService.ApproveSeedTreatment(event.id);
      } else if (event.flag == 'finalize') {
        observable = this.agriSeedTreatmentService.FinalizeSeedTreatment(event.id);
      } else if (event.flag == 'delete') {
        observable = this.agriSeedTreatmentService.DeleteSeedTreatment(event.id);
      } else if (event.flag == 'reject') {
        observable = this.agriSeedTreatmentService.RejectSeedTreatment(event.id);
      }
      observable.subscribe(res => {
        if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            this._statusMsg = res.message;
            this.loadAllSeedTreatment();
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

  searchSeedTreatment() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageAgriSeedTreatment(this.selectedPage - 1, this.isValid,this.filterFG.value.commodityId,this.filterFG.value.varietyId,this.filterFG.value.name,this.filter);
  }

  sortData(sort: Sort) {
    const data = this.pageAgriSeedTreatment.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageAgriSeedTreatment.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageAgriSeedTreatment.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
        case 'commodity':
          return compare(firstValue.commodity, secondValue.commodity, isAsc);
        case 'variety':
          return compare(firstValue.variety, secondValue.variety, isAsc);
        case globalConstants.NAME:
          return compare(firstValue.name, secondValue.name, isAsc);
        case 'dose':
          return compare(firstValue.dose, secondValue.dose, isAsc);
        case 'uom':
          return compare(firstValue.uom, secondValue.uom, isAsc);
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
      (document.querySelector('thead th input') as HTMLInputElement).checked = false
    }else {
    this.onSelect(this.selectedPage);
    (document.querySelector('thead th input') as HTMLInputElement).checked = false
    }
  }

  fixBug() {
    this.isValid = 0;
    this.drkServiceService.fixBug('agri_seed_treatment').subscribe(res => {
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
    this.getPageAgriSeedTreatment(0,this.isValid,this.filterFG.value.commodityId,this.filterFG.value.varietyId,this.filterFG.value.name,this.filter);
  }

  moveToMaster(id){
    this.agriSeedTreatmentService.moveToMaster(id).subscribe(res => {
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
      commodityId: [''],
      varietyId: [''],
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
      this.getPageAgriSeedTreatment(this.selectedPage - 1,this.isValid,this.filterFG.value.commodityId,this.filterFG.value.varietyId,this.filterFG.value.name,this.filter);
      this.seedTreatmentStatus = globalConstants;
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
        this.isVarietyHidden = false;
        this.loadAllVariety();
      }else if(id === 3){
        this.isSeedTreatmentAgentName = false;
      }
    } else {
      // REMOVE FROM SELECTION
      this.selection = this.selection.filter(obj => obj.id !== data.id)
      // this.selection.splice(index, 1)
      if(id === 1){
        this.isCommodityHidden = true;
        this.filterFG.value.commodityId == '';
      }else if(id === 2){
        this.isVarietyHidden = true;
        this.filterFG.value.varietyId == '';
      }else if(id === 3){
        this.isSeedTreatmentAgentName = true;
        this.filterFG.value.name == '';
      }
    }
  }

  //Commodity list
  loadAllCommodities() {
    return this.commodityService.GetAllCommoditise().subscribe((data: {}) => {
      this.commodityList = data;
    })
  }

  //Variety list
  loadAllVariety() {
    return this.agriVarietyService.GetAllVarieties().subscribe((data: {}) => {
      this.varietyList = data;
    })
  }

  onChangeList(){
    
  }

  onClickFilter(){
      this.isCommodityHidden = true;
      this.isVarietyHidden = true;
      this.isSeedTreatmentAgentName = true;
      this.filterFormControl();
      for (var searchField of this.searchFieldList) {
        const id = searchField.id;
        this.selection = this.selection.filter(obj => obj.id !== searchField.id)
      }
  }

  onClickSearchFilters(){
    
     
      this.filter = 1;
  
      this.getPageAgriSeedTreatment(0,this.isValid,this.filterFG.value.commodityId,this.filterFG.value.varietyId,this.filterFG.value.name,this.filter);
      document.getElementById('filterModal').click();
  }


}

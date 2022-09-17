import { Component, OnInit, ViewChild } from '@angular/core';
import { AgriStressControlMeasuresService } from '../../services/agri-stress-control-measures.service';
import { UserRightsService } from '../../../services/user-rights.service';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { globalConstants } from '../../../global/globalConstants';
import { PageAgriStressControlMeasures } from '../../models/PageAgriStressControlMeasures';
import {Sort} from '@angular/material';
import {SuccessModalComponent} from '../../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../../global/error-modal/error-modal.component';
import { BulkDataService } from '../../services/bulk-data.service';
import {DrkServiceService} from '../../../services/drk-service.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AgriStressSeverityService } from '../../services/agri-stress-severity.service';
import { AgriCommodityService } from '../../services/agri-commodity.service';
import { AgriControlMeasuresService } from '../../services/agri-control-measures.service';
import { ZonalStressDurationService } from '../../services/zonal-stress-duration.service';



@Component({
  selector: 'app-agri-stress-control-measures',
  templateUrl: './agri-stress-control-measures.component.html',
  styleUrls: ['./agri-stress-control-measures.component.scss']
})
export class AgriStressControlMeasuresComponent implements OnInit {

    @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
    agriRecStatus;
  stressControlMeasuresList: any = [];
    isValid: number = 1;

    pageAgriStressControlMeasures: PageAgriStressControlMeasures;
  selectedPage: number = 1;
  maxSize: number = 10;
  searchText: any = "";
  missing : any="";

  recordsPerPage: number = 10;
  records: any = [];

  filterFG: FormGroup;
  filter : any = "";
  searchFieldList = [{id:1,name:'Commodity'},{id:2,name:'Stress'},{id:3,name:'Stress Severity'},{id:4,name:'Control Measure'}];
  selection:any = [];
  commodityList: any = [];
  stressList: any = [];
  stressSeverityList: any = [];
  controlMeasureList: any = [];
  isCommodityHidden: boolean = true;
  isStressHidden: boolean = true;
  isStressSeverityHidden: boolean = true;
  isControlMeasureHidden: boolean = true;


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageAgriStressControlMeasures(0, this.isValid,'','','','',this.filter);
  //  this.loadAllRecommendation();
      this.agriRecStatus = globalConstants;
      this.filterFormControl();
  }

  constructor(public bulkDatas: BulkDataService,
    public agriStressControlMeasuresService : AgriStressControlMeasuresService,
    private userRightsService: UserRightsService,
    private drkServiceService: DrkServiceService,
    private agriCommodityService: AgriCommodityService,
    private zonalStressDurationService: ZonalStressDurationService,
    private agriStressSeverityService: AgriStressSeverityService,
    private agriControlMeasuresService: AgriControlMeasuresService,
    public fb: FormBuilder

){ }

   // Recommendation list
   loadAllStressControlMeasures() {
    return this.agriStressControlMeasuresService.loadAllStressControlMeasures().subscribe((data: {}) => {
      this.stressControlMeasuresList = data;
    })
  }


  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : " + page);
    // this.selectedPage = page;
    this.getPageAgriStressControlMeasures(page, this.isValid,this.filterFG.value.commodityId,this.filterFG.value.stressId,this.filterFG.value.stressSeverityId,this.filterFG.value.controlMeasureId,this.filter);
  }

  getPageAgriStressControlMeasures(page: number, isValid: number,commodityId,stressId,stressSeverityId,controlMeasureId,filter): void {
    this.agriStressControlMeasuresService.getPageAgriStressControlMeasures(page, this.recordsPerPage, this.searchText, isValid,this.missing,commodityId,stressId,stressSeverityId,controlMeasureId,filter)
      .subscribe(page => this.pageAgriStressControlMeasures = page)
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.agriStressControlMeasuresService.getPageAgriStressControlMeasures(this.selectedPage - 1, this.recordsPerPage, this.searchText, this.isValid,this.missing,this.filterFG.value.commodityId,this.filterFG.value.stressId,this.filterFG.value.stressSeverityId,this.filterFG.value.controlMeasureId,this.filter)
      .subscribe(page => this.pageAgriStressControlMeasures = page);
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

    // // Delete Recommendation
    // deleteRecommendation(data){
    //   var index = index = this.RecommendationList.map(x => {return x.name}).indexOf(data.name);
    //    return this.agriRecommendationService.DeleteRecommendation(data.id).subscribe(res => {
    //     this.RecommendationList.splice(index, 1)
    //      console.log('Recommendation deleted!')
    //    })
    // }

     // Delete 
  delete(data, i) {
    data.index = i;
    data.flag = "delete"
    this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg + " " + data.stressControlMeasure, data);
  }
  // Reject 
  reject(data, i) {
    data.index = i;
    data.flag = "reject"
    this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " " + data.stressControlMeasure, data);
  }

  approve(data, i) {
    data.index = i;
    data.flag = "approve"
    this.confirmModal.showModal(globalConstants.approveDataTitle, globalConstants.approveDataMsg + " " + data.stressControlMeasure, data);
  }
  finalize(data, i) {
    data.index = i;
    data.flag = "finalize"
    this.confirmModal.showModal(globalConstants.finalizeDataTitle, globalConstants.finalizeDataMsg + " " + data.stressControlMeasure, data);
  }

    modalConfirmation(event) {
        console.log(event);
        let observable: any;
        if (event) {
            this.isSubmitted = true;
            if (event.flag == 'approve') {
                observable = this.agriStressControlMeasuresService.ApproveRecommendation(event.id);
            } else if (event.flag == 'finalize') {
                observable = this.agriStressControlMeasuresService.FinalizeRecommendation(event.id);
            } else if (event.flag == 'delete') {
                observable = this.agriStressControlMeasuresService.DeleteAgriRecommendation(event.id);
            } else if (event.flag == 'reject') {
                observable = this.agriStressControlMeasuresService.RejectRecommendation(event.id);
            }
            observable.subscribe(res => {
                if (res) {
                    this.isSuccess = res.success;
                    if (res.success) {
                        this._statusMsg = res.message;
                        this.loadAllStressControlMeasures();
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

    search() {
        this.selectedPage = 1;
        console.log(this.searchText);
        this.getPageAgriStressControlMeasures(this.selectedPage - 1, this.isValid,this.filterFG.value.commodityId,this.filterFG.value.stressId,this.filterFG.value.stressSeverityId,this.filterFG.value.controlMeasureId,this.filter);
    }

    sortData(sort: Sort) {
        const data = this.pageAgriStressControlMeasures.content.slice();
        if (!sort.active || sort.direction == '') {
            this.pageAgriStressControlMeasures.content = data;
            return;
        }

        function compare(firstValue, secondValue, isAsc: boolean) {
            return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }

        this.pageAgriStressControlMeasures.content = data.sort((firstValue, secondValue) => {
            const isAsc = sort.direction == 'asc';
            switch (sort.active) {
                case globalConstants.ID:
                    return compare(+firstValue.id, +secondValue.id, isAsc);
                case 'commodity':
                    return compare(firstValue.commodity, secondValue.commodity, isAsc);
                case 'stress':
                    return compare(firstValue.stress, secondValue.stress, isAsc);
                case 'stressSeverity':
                    return compare(firstValue.stressSeverity, secondValue.stressSeverity, isAsc);
                case 'stressControlMeasure':
                    return compare(firstValue.stressControlMeasure, secondValue.stressControlMeasure, isAsc);
                case globalConstants.STATUS:
                    return compare(firstValue.status, secondValue.status, isAsc);
                default:
                    return 0;
            }
        });
    }

    modalSuccess($event: any) {
      if (this.selectedPage > 0){
          this.onSelect((this.selectedPage - 1));
          (document.querySelector('thead th input') as HTMLInputElement).checked = false
      } else {
          this.onSelect(this.selectedPage);
          (document.querySelector('thead th input') as HTMLInputElement).checked = false
      }
    }
    fixBug() {
        this.isValid = 0;
        this.drkServiceService.fixBug('agri_recommendation').subscribe(res => {
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
      this.getPageAgriStressControlMeasures(0,this.isValid,this.filterFG.value.commodityId,this.filterFG.value.stressId,this.filterFG.value.stressSeverityId,this.filterFG.value.controlMeasureId,this.filter);
    }
  
    moveToMaster(id){
      this.agriStressControlMeasuresService.moveToMaster(id).subscribe(res => {
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
        stressId: [''],
        stressSeverityId: [''],
        controlMeasureId: [''],
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
        this.getPageAgriStressControlMeasures(this.selectedPage - 1,this.isValid,this.filterFG.value.commodityId,this.filterFG.value.stressId,this.filterFG.value.stressSeverityId,this.filterFG.value.controlMeasureId,this.filter);
        this.agriRecStatus = globalConstants;
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
          this.loadAllCommodity();
        }else if(id === 2){
          this.isStressHidden = false;
          this.loadAllStress();
        }else if(id === 3){
          this.isStressSeverityHidden = false;
          this.loadAllStressSeverity();
        }else if(id === 4){
          this.isControlMeasureHidden = false;
          this.GetAllAgriControlMeasures();
        }
      } else {
        // REMOVE FROM SELECTION
        this.selection = this.selection.filter(obj => obj.id !== data.id)
        // this.selection.splice(index, 1)
        if(id === 1){
          this.isCommodityHidden = true;
          this.filterFG.value.commodityId == '';
        }else if(id === 2){
          this.isStressHidden = true;
          this.filterFG.value.stressId == '';
        }else if(id === 3){
          this.isStressSeverityHidden = true;
          this.filterFG.value.stressSeverityId == '';
        }else if(id === 4){
          this.isControlMeasureHidden = true;
          this.filterFG.value.controlMeasureId == '';
        }
      }
    }
  
    loadAllCommodity() {
      return this.agriCommodityService.GetAllCommoditise().subscribe((data: any) => {
        this.commodityList = data;
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

    loadAllStressSeverity() {
      return this.agriStressSeverityService.GetAllStressSeverity().subscribe((data: any) => {
        this.stressSeverityList = data;
      }, err => {
        alert(err)
      })
    }

    GetAllAgriControlMeasures() {
      return this.agriControlMeasuresService.GetAllAgriControlMeasures().subscribe((data: any) => {
        this.controlMeasureList = data;
      }, err => {
        alert(err)
      })
    }
  
    onChangeList(){
      
    }
  
    onClickFilter(){
        this.isCommodityHidden = true;
        this.isStressHidden = true;
        this.isStressSeverityHidden = true;
        this.isControlMeasureHidden = true;
        this.filterFormControl();
        for (var searchField of this.searchFieldList) {
          const id = searchField.id;
          this.selection = this.selection.filter(obj => obj.id !== searchField.id)
        }
    }
  
    onClickSearchFilters(){
        
        this.filter = 1;
    
        this.getPageAgriStressControlMeasures(0,this.isValid,this.filterFG.value.commodityId,this.filterFG.value.stressId,this.filterFG.value.stressSeverityId,this.filterFG.value.controlMeasureId,this.filter);
        document.getElementById('filterModal').click();
    }


}

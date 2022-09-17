import { Sort } from '@angular/material';
import { ViewChild } from "@angular/core";
import { Component, OnInit } from "@angular/core";
import { ConfirmationMadalComponent } from "../../global/confirmation-madal/confirmation-madal.component";
import { ErrorModalComponent } from "../../global/error-modal/error-modal.component";
import { globalConstants } from "../../global/globalConstants";
import { SuccessModalComponent } from "../../global/success-modal/success-modal.component";
import { UserRightsService } from "../../services/user-rights.service";
import { RegionalConnectivity } from "../models/RegionalConnectivity";
import { RegionalConnectivityService } from "../services/regional-connectivity.service";
import { BulkDataService } from '../../agri/services/bulk-data.service';
import { PageRegionalConnectivity } from '../models/PageRegionalConnectivity';
import { GeoStateService } from '../../geo/services/geo-state.service';

@Component({
    selector: 'app-regional-connectivity',
    templateUrl:'./regional-connectivity.component.html',
    styleUrls:['./regional-connectivity.component.scss']
})
export class RegionalConnectivityComponent implements OnInit{
    @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    // stateList :any = [];
    regionalConnectivityList:any = [];
    regionalStatus;

    searchText: any = '';
   selectedPage: number = 1;
   maxSize =10;
   recordsPerPage: number = 10;
   records: any = [];

   pageRegionalConnectivity: PageRegionalConnectivity;
   
    constructor(public bulkDatas: BulkDataService,private regConnectService:RegionalConnectivityService,
                private geoStateService:GeoStateService,
                private userRightsService:UserRightsService){ }

    ngOnInit(): void {
      this.records = ['20', '50', '100', '200', '250'];
	   this.getRegionalConnectivityPagenatedList(0);
        // this.getRegionalConnectivityList();
        this.regionalStatus = globalConstants;
    }

    getRegionalConnectivityPagenatedList(page: number): void {
      this.regConnectService.getRegionalConnectivityPagenatedList(page, this.recordsPerPage, this.searchText)
          .subscribe(page => this.pageRegionalConnectivity = page);
    }
    
    loadData(event: any) {
      console.log('pages ', event.target.value);
      this.recordsPerPage = event.target.value || 10;
      this.regConnectService.getRegionalConnectivityPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageRegionalConnectivity = page);
    }
  
    onSelect(page: number): void {
      (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
      this.bulkDatas.disbled = true;
      // console.log('selected page : ' + page);
      this.selectedPage = page;
      this.getRegionalConnectivityPagenatedList(page);
  }
  
  search() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getRegionalConnectivityPagenatedList(this.selectedPage - 1);
  }
  
//   getAllState()
//   {
//     this.geoStateService.GetAllState().subscribe((data: {}) =>{
//       this.stateList=data;
//   })
// }


     getRegionalConnectivityList() : void {
       this.regConnectService.getRegionalConnectivityList()
             .subscribe(list =>{
                 if(list){
                     this.regionalConnectivityList = list;
                    //  console.log("Data:",this.regionalConnectivityList);
                 }
             },error=> {
                 console.log('Error in getting list', error);
             })
     } 

     delete(data, i) {
        data.index = i;
        data.flag = "delete"
        this.confirmModal.showModal(globalConstants.deleteDataTitle, globalConstants.deleteDataMsg, data);
      }
      // Reject 
      reject(data, i) {
        data.index = i;
        data.flag = "reject"
        this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg,data);
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
         // this.isSubmitted = true;
          if (event.flag == "approve") {
            observable = this.regConnectService.ApproveVariety(event.id)
          } else if (event.flag == "finalize") {
            observable = this.regConnectService.FinalizeVarietys(event.id)
          } else if (event.flag == "delete") {
            observable = this.regConnectService.DeleteVariety(event.id)
          } else if (event.flag == "reject") {
            observable = this.regConnectService.RejectVariety(event.id)
          }
          observable.subscribe(res => {
            if (res) {
             // this.isSuccess = res.success;
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

      modalSuccess($event: any) {
        (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
        this.bulkDatas.disbled = true;
        console.log("page : " + this.selectedPage);
        if(this.selectedPage >= 2){
          // console.log("Inside if");
        this.getRegionalConnectivityPagenatedList(this.selectedPage - 1);
        this.regionalStatus   = globalConstants;
        }else{
          // console.log("Inside else");
        this.ngOnInit();
        }
      }





      sortData(sort: Sort) {
        const data = this.pageRegionalConnectivity.content.slice();
        if (!sort.active || sort.direction == '') {
          this.pageRegionalConnectivity.content = data;
          return;
        }
      
        function compare(firstValue, secondValue, isAsc: boolean) {
          return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }
      
        this.pageRegionalConnectivity.content = data.sort((firstValue, secondValue) => {
          const isAsc = sort.direction == 'asc';
          switch (sort.active) {
            case globalConstants.ID:
              return compare(+firstValue.id, +secondValue.id, isAsc);
            case 'state':
                return compare(firstValue.stateCode, secondValue.stateCode, isAsc);
            case 'regionName':
                  return compare(firstValue.regionName, secondValue.regionName, isAsc);
            case 'surfacedProportion':
              return compare(firstValue.surfacedProportion, secondValue.surfacedProportion, isAsc);
            case 'unsurfacedProportion':
              return compare(firstValue.unsurfacedProportion, secondValue.unsurfacedProportion, isAsc);
            case 'surfacedTimeMinPerKm':
              return compare(firstValue.surfacedTimeMin, secondValue.surfacedTimeMin, isAsc);
            case 'unsurfacedTimeMinPerKm':
              return compare(firstValue.unsurfacedTimeMin, secondValue.unsurfacedTimeMin, isAsc);
              case globalConstants.STATUS:
              return compare(firstValue.status, secondValue.status, isAsc);
            default:
              return 0;
          }
        });
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
}
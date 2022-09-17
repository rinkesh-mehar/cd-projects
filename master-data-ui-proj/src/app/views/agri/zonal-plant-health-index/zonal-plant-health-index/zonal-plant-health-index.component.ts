import { Component, OnInit, ViewChild } from '@angular/core';

import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import { ZonalPlantHealthIndexService } from '../../services/zonal-plant-health-index.service';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { PageZonalPlantHealthIndex } from '../../models/page-zonal-plant-health-index';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { BulkDataService } from '../../services/bulk-data.service';
import { Sort } from '@angular/material/sort';
import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';


@Component({
  selector: 'app-zonal-plant-health-index',
  templateUrl: './zonal-plant-health-index.component.html',
  styleUrls: ['./zonal-plant-health-index.component.scss']
})
export class ZonalPlantHealthIndexComponent implements OnInit {


  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  
  PlantHealthIndexList: any = [];
  pageZonalPlantHealthIndex : PageZonalPlantHealthIndex;
  searchText : any= "";
  selectedPage: number = 0;
  maxSize : number=10;
  missing : any="";
  plantPartIndexStatus;

  recordsPerPage: number = 10;
   records: any = [];


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    // this.loadAllAgriPlantHealthIndex();
    this.getPageZonalPlantHealthIndex(0);
    this.plantPartIndexStatus = globalConstants;
  }

  constructor(public bulkDatas: BulkDataService,
    private zonalPlantHealthIndexService: ZonalPlantHealthIndexService,
    private userRightsService: UserRightsService,
  ){ }


  getPageZonalPlantHealthIndex(page: number): void {
    this.zonalPlantHealthIndexService.getZonalPlantHealthIndexPagenatedList(page, this.recordsPerPage, this.searchText,this.missing)
      .subscribe(page => this.pageZonalPlantHealthIndex = page)
  }


  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.zonalPlantHealthIndexService.getZonalPlantHealthIndexPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText,this.missing)
      .subscribe(page => this.pageZonalPlantHealthIndex = page);
  }

   // loadAllAgriPlantHealthIndex list
   loadAllZonalPlantHealthIndex() {
    return this.zonalPlantHealthIndexService.GetAllPlantHealthIndex().subscribe((data: any) => {
      this.PlantHealthIndexList = data;
    })
  }

    // // Delete AgriPlantHealthIndex
    // deleteAgriPlantHealthIndex(data){
    //   var index = index = this.EcosystemList.map(x => {return x.name}).indexOf(data.name);
    //    return this.AgriPlantHealthIndexService.DeleteAgriPlantHealthIndex(data.id).subscribe(res => {
    //     this.EcosystemList.splice(index, 1)
    //      console.log('Agri Ecosystem deleted!')
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

  searchHealthIndex() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageZonalPlantHealthIndex(this.selectedPage - 1);
  }

  onSelect(page: number): void {
    (<HTMLInputElement>document.getElementById("masterChkBox")).checked = false;
    this.bulkDatas.disbled = true;
    console.log("selected page : " + page);
    this.selectedPage = page;
    this.getPageZonalPlantHealthIndex(page);
  }

  onClickMissing(){
    this.missing = 1;
    this.getPageZonalPlantHealthIndex(0);
  }

  moveToMaster(id){
    this.zonalPlantHealthIndexService.moveToMaster(id).subscribe(res => {
      this.isSubmitted = true;
      if(res) {
      
        this.isSuccess = res.success;
        if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
    //   if (res) {
    //     this.isSuccess = res.success;
    //     if (res.success) {
    //       this._statusMsg = res.message;
    //     // this.ngOnInit();
    //     setTimeout(() => {
    //       this.isSubmitted = false;
    //       this.isSuccess = false;
    //       this._statusMsg = "";
    //     }, 4000);
    //   } else {
    //     this._statusMsg = res.error;
    //   }
    // }
    });
    this.missing = 0;
    this.ngOnInit();
  }

  modalConfirmation(event) {
    console.log(event);
    let observable: any;
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        observable = this.zonalPlantHealthIndexService.ApprovePlantHealthIndex(event.id)
      } else if (event.flag == "finalize") {
        observable = this.zonalPlantHealthIndexService.FinalizePlantHealthIndex(event.id)
      } else if (event.flag == "delete") {
        observable = this.zonalPlantHealthIndexService.DeletePlantHealthIndex(event.id)
      } else if (event.flag == "reject") {
        observable = this.zonalPlantHealthIndexService.RejectPlantHealthIndex(event.id)
      }
      observable.subscribe(res => {
        if(res) {
      
          this.isSuccess = res.success;
          if (res.success) {
            this.ngOnInit();
            this.successModal.showModal('SUCCESS', res.message, '');
          } else {
          
            this.errorModal.showModal('ERROR', res.error, '');
          }
        }
        // if (res) {
        //   this.isSuccess = res.success;
        //   if (res.success) {
        //     this._statusMsg = res.message;
        //     this.ngOnInit();
        //     setTimeout(() => {
        //       this.isSubmitted = false;
        //       this.isSuccess = false;
        //       this._statusMsg = "";
        //     }, 4000);
        //   } else {
        //     this._statusMsg = res.error;
        //   }
        // }
      }, err => {
        this._statusMsg = err.error;
      })
    }

  }








sortData(sort: Sort) {
    const data = this.pageZonalPlantHealthIndex.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageZonalPlantHealthIndex.content = data;
      return;
    }

    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }

    this.pageZonalPlantHealthIndex.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case globalConstants.ID:
          return compare(+firstValue.id, +secondValue.id, isAsc);
          case globalConstants.ID:
            return compare(+firstValue.id, +secondValue.id, isAsc);
          case 'state':
            return compare(firstValue.state , secondValue.state, isAsc);
            case 'name':
              return compare(firstValue.name , secondValue.name, isAsc);
          case 'aczName':
            return compare(firstValue.aczName , secondValue.aczName, isAsc);
          case 'zonalCommodity':
            return compare(firstValue.zonalCommodity , +secondValue.zonalCommodity, isAsc);
          case 'zonalVariety':
            return compare(firstValue.zonalVariety , +secondValue.zonalVariety, isAsc);
          case 'phenophaseName':
            return compare(firstValue.phenophaseName , secondValue.phenophaseName, isAsc);
        case 'phenophase':
          return compare(firstValue.phenophase, secondValue.phenophase, isAsc);
        case globalConstants.NAME:
          return compare(firstValue.name, secondValue.name, isAsc);
        case 'idealValue':
          return compare(firstValue.idealValue, secondValue.idealValue, isAsc);
       case 'normalValue':
          return compare(firstValue.idealValue, secondValue.idealValue, isAsc);
       case 'healthParameterId':
          return compare(firstValue.healthParameterId, secondValue.healthParameterId, isAsc);
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
    this.getPageZonalPlantHealthIndex(this.selectedPage - 1);
    this.plantPartIndexStatus = globalConstants;
    }else{
      // console.log("Inside else");
    this.ngOnInit();
    }
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

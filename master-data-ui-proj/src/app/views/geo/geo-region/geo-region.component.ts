import { Component, OnInit, ViewChild } from '@angular/core';
import { GeoRegionService } from '../services/geo-region.service';
import { PageGeoRegion } from '../models/PageGeoRegion';
import { globalConstants } from '../../global/globalConstants';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { Sort } from '@angular/material';

@Component({
  selector: 'app-geo-region',
  templateUrl: './geo-region.component.html',
  styleUrls: ['./geo-region.component.scss']
})
export class GeoRegionComponent implements OnInit {


  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;


  RegionList: any = [];
  selectedPage: number;
  pageRegion: PageGeoRegion;
  searchText : any ="";
  maxSize :number= 10;
  recordsPerPage: number = 10;
   records: any = [];


  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.getPageGeoRegion(0);
    // this.loadAllRegion();
  }

  constructor(
    public geoRegionService: GeoRegionService,
    private userRightsService: UserRightsService,

  ) { }

  // Regionlist
  loadAllRegion() {
    return this.geoRegionService.GetAllRegion().subscribe((data: {}) => {
      this.RegionList = data;
    })
  }

  onSelect(page: number): void {
    console.log("selected page : " + page);
    this.selectedPage = page;
    this.getPageGeoRegion(page);
  }

  getPageGeoRegion(page: number): void {
    this.geoRegionService.getPageGeoRegion(page, this.recordsPerPage,this.searchText)
      .subscribe(page => this.pageRegion = page)
  }


  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.geoRegionService.getPageGeoRegion(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageRegion = page);
  }

  // // Delete Region
  // deleteRegion(data){
  //   var index = index = this.RegionList.map(x => {return x.name}).indexOf(data.name);
  //    return this.geoRegionService.DeleteRegion(data.regionId).subscribe(res => {
  //     this.RegionList.splice(index, 1)
  //      console.log('Region deleted!')
  //    })
  // }


  // Delete Region
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
    if (event) {
      this.isSubmitted = true;
      if (event.flag == "approve") {
        this.approveRegion(event);
      } else if (event.flag == "finalize") {
        this.finalizeRegion(event);
      } else if (event.flag == "delete") {
        this.deleteRegion(event);
      } else if (event.flag == "reject"){
        this.rejectRegion(event);
      }
  }
  }
  rejectRegion(event: any) {
    return this.geoRegionService.RejectRegion(event.regionId).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.RegionList.splice(event.index, 1)
          this._statusMsg = res.message;
          setTimeout(() => {
            this.isSubmitted = false;
            this.isSuccess = false;
            this._statusMsg = ""
          }, 4000);
        } else {
          this._statusMsg = res.error;
        }
      }
    }, err => {
      this._statusMsg = err.error;
    })
  }
  approveRegion(event: any) {
    return this.geoRegionService.ApproveRegion(event.regionId).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          setTimeout(() => {
            this.isSubmitted = false;
            this.isSuccess = false;
            this._statusMsg = "";
          }, 4000);
        } else {
          this._statusMsg = res.error;
        }
      }
    }, err => {
      this._statusMsg = err.error;
    })
  }
  finalizeRegion(event: any) {
    return this.geoRegionService.FinalizeRegion(event.regionId).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this._statusMsg = res.message;
          setTimeout(() => {
            this.isSubmitted = false;
            this.isSuccess = false;
            this._statusMsg = "";
          }, 4000);
        } else {
          this._statusMsg = res.error;
        }
      }
    }, err => {
      this._statusMsg = err.error;
    })
  }

  deleteRegion(event) {
    return this.geoRegionService.DeleteRegion(event.regionId).subscribe(res => {
      if (res) {
        this.isSuccess = res.success;
        if (res.success) {
          this.RegionList.splice(event.index, 1)
          this._statusMsg = res.message;
          setTimeout(() => {
            this.isSubmitted = false;
            this.isSuccess = false;
            this._statusMsg = ""
          }, 4000);
        } else {
          this._statusMsg = res.error;
        }
      }
    }, err => {
      this._statusMsg = err.error;
    })
  }

  searchRegion(){
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getPageGeoRegion(this.selectedPage - 1);
  }

  sortData(sort: Sort) {
    const data = this.pageRegion.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageRegion.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageRegion.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case 'regionId':
          return compare(+firstValue.regionId, +secondValue.regionId, isAsc);
          case 'state':
            return compare(firstValue.state, secondValue.state, isAsc);
        case globalConstants.NAME:
          return compare(firstValue.name, secondValue.name, isAsc);
        case 'description':
          return compare(firstValue.description, secondValue.description, isAsc);
        default:
          return 0;
      }
    });
  }
  

}

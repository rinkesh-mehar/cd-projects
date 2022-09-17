import { Component, OnInit, ViewChild } from '@angular/core';

import { ConfirmationMadalComponent } from '../../../global/confirmation-madal/confirmation-madal.component';
import { UserRightsService } from '../../../services/user-rights.service';
import { globalConstants } from '../../../global/globalConstants';
import { PageGeoCity } from '../../models/PageGeoCity';
import { GeoCityService } from '../../services/geo-city.service';

@Component({
  selector: 'app-geo-city',
  templateUrl: './geo-city.component.html',
  styleUrls: ['./geo-city.component.scss']
})
export class GeoCityComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;
  
  CityList: any = [];
  pageGeoCity : PageGeoCity;
  selectedPage: number = 0;
  maxSize : number=10;
  searchText : any= "";


  ngOnInit() {
    // this.loadAllGeoCity();
    this.getPageGeoCity(0);
  }

  constructor(
    private GeoCityService: GeoCityService,
    private userRightsService: UserRightsService,
  ){ }

   // loadAllGeoCity list
   loadAllGeoCity() {
    return this.GeoCityService.GetAllCity().subscribe((data: any) => {
      this.CityList = data;
    })
  }

    // // Delete GeoCity
    // deleteGeoCity(data){
    //   var index = index = this.EcosystemList.map(x => {return x.name}).indexOf(data.name);
    //    return this.GeoCityService.DeleteGeoCity(data.id).subscribe(res => {
    //     this.EcosystemList.splice(index, 1)
    //      console.log('Agri Ecosystem deleted!')
    //    })
    // }

    onSelect(page: number): void {
      console.log("selected page : "+page);
      this.selectedPage=page;
      this.getPageGeoCity(page);
    }
    
   getPageGeoCity(page:number): void {
      this.GeoCityService.getPageGeoCity(page,this.searchText)
      .subscribe(page => this.pageGeoCity = page)
    }

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
        observable = this.GeoCityService.ApproveCity(event.id)
      } else if (event.flag == "finalize") {
        observable = this.GeoCityService.FinalizeCity(event.id)
      } else if (event.flag == "delete") {
        observable = this.GeoCityService.DeleteCity(event.id)
      } else if (event.flag == "reject") {
        observable = this.GeoCityService.RejectCity(event.id)
      }
      observable.subscribe(res => {
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

  }

  searchCity(){
    this.selectedPage = 1
    console.log(this.searchText);
    this.getPageGeoCity(this.selectedPage - 1);
  }


}

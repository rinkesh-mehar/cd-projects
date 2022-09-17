import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from "@angular/router";
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { KycListService } from './kyc-list.service';
import { KycList } from './kyc-list.model';
import { SuccessModalComponent } from '../../../modal-components/success-modal/success-modal.component';
import { ErrorMessages } from '../../../form-validation-messages';
declare var $,datatable;
@Component({
  selector: 'app-kyc-list',
  templateUrl: './kyc-list.component.html',
  styleUrls: ['./kyc-list.component.less']
})
export class KycListComponent implements OnInit {

  modalRef: BsModalRef;
  public kyc_list: KycList[];
  public loggedInUser = {} as any;
  public oldView :string;
  public search :string;

  public commodities :string[] =[];
  public verieties :string[] =[];
  public states :string[] =[];
  public regions :string[] =[];
  public commodity :string;
  public state :string;
  public region :string;
  searchText;
  public httperrorresponsemessages: string;


  constructor(
    private modalService: BsModalService,
    private router: Router,
    private kycListService: KycListService,
  ) { }

  ngOnInit() {

    //Error Messages
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

    this.loggedInUser = JSON.parse(localStorage.getItem("userData"));
    this.getKycListService(this.loggedInUser.userId);
    this.router.events.subscribe((evt) => {
    if (!(evt instanceof NavigationEnd)) {
        return;
    }
    window.scrollTo(0, 0)
      });


      if (sessionStorage.getItem('state') != undefined) {
        // this.state = sessionStorage.getItem('state');
        //this.SearchRegion();
      }

      if (sessionStorage.getItem('region') != undefined) {
        // this.region = sessionStorage.getItem('region');
        //this.SearchCommodity();
      }

      if (sessionStorage.getItem('commodity') != undefined) {
        // this.commodity = sessionStorage.getItem('commodity');
      }

      if (sessionStorage.getItem('searchText') != undefined) {
        this.searchText = sessionStorage.getItem('searchText');
      }




  }



  View() {
    if (this.state != undefined) {
      sessionStorage.setItem('state', this.state.toString());
    }

    if (this.region != undefined) {
      sessionStorage.setItem('region', this.region.toString());
    }

    if (this.commodity != undefined) {
      sessionStorage.setItem('commodity', this.commodity.toString());
    }

    if (this.searchText != undefined) {
      sessionStorage.setItem('searchText', this.searchText);
    }
  }


datatable(){
  setTimeout(function(){
    $('#kyc-list').DataTable({
      "bPaginate": true,
      "bInfo": true,
      "searching": true,
      "lengthChange": true,
      columnDefs: [{
        orderable: false,
        targets: [0]
      }],
      lengthMenu: [
          [ 5, 10, 15, -1 ],
          [ '5', '10', '15', 'All' ]
      ],
      "dom": "<'row'<'col-sm-12'tr>>" +
        "<'table-footer'<'table-length'l><'table-info'i><'table-pagination'p>>",
        "fnDrawCallback": function(oSettings) {
          if ($('table.table-bordered.dataTable td').hasClass("dataTables_empty")) {
             $(".paginate_button.active").hide();
          }
      }
    });
    $.fn.dataTable.ext.errMode = 'none';
  }, 40);

}
public getKycListService(id) {
  this.kycListService.getKycListService(id).subscribe(
    (data) => {
      if(data.statusCode == "success"){
        this.kyc_list = data.data;
        this.oldView = JSON.stringify(this.kyc_list);
        $("#kyc-list").dataTable().fnDestroy();
        this.datatable();
        this.kyc_list.forEach(item =>{
          if (item.state != null && this.states.indexOf(item.state) == -1 && item.state.trim() != '') this.states.push(item.state)
          if (item.region != null && this.regions.indexOf(item.region) == -1 && item.region.trim() != '') this.regions.push(item.region)
          if (item.commodity != null && this.commodities.indexOf(item.commodity) == -1 && item.commodity.trim() != '') this.commodities.push(item.commodity)
        })
      }else if(data.statusCode == "error"){
        const initialState = {
          title: "Error",
          content: data.message,
          action: "/kyc-list"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      }

    }, (err) => {
      const initialState = {
        title: "Error",
        content: this.httperrorresponsemessages,
        action: "/kyc-list"
      };
      this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
      return;
    });
}


SearchState() {
  this.regions = [];
  this.commodities = [];
  this.region = undefined;
  this.commodity = undefined;
  this.searchText = undefined;

  if (this.state == undefined) {
    this.getKycListService(this.loggedInUser.userId);
  } else {

    //Filter functionality
    this.stateData();
    //Dropdown data functionality
    this.stateDropdownData();
  }


}

SearchRegion() {
  this.commodities = [];
  this.commodity = undefined;
  this.searchText = undefined;

  if(this.state == undefined && this.region == undefined){
    this.getKycListService(this.loggedInUser.userId);
  }else if (this.region == undefined) {
    this.stateData();
    this.stateDropdownData();
  } else {
    //Filter functionality
    this.regionData();
    //Dropdown data functionality
    this. regionDropdownData();

  }
}

SearchCommodity() {
  this.searchText = undefined;

  if(this.state == undefined && this.region == undefined && this.commodity == undefined){
    this.getKycListService(this.loggedInUser.userId);
  }else if (this.commodity == undefined) {
    if(this.region == undefined){
      this.stateData();
      this.stateDropdownData();
    }else{
      this.regionData();
      this.regionDropdownData();
    }
  }else {
    //Filter functionality
    this.commodityData();
  }
}


searchTableData() {
  var table = $('#kyc-list').DataTable();
  $('#searchText').on( 'keyup', function () {
      table.search( this.value ).draw();
  } );
}

//Reusable Data
stateData() {
  this.kyc_list = JSON.parse(this.oldView);

  if (this.state) {
    let newarr = this.kyc_list.filter(a => {
      if (a.state == this.state) {
        return a;
      }
    })
    this.kyc_list = newarr;
  }

  $("#kyc-list").dataTable().fnDestroy();
  this.datatable();
}

// State Dropdown Data
stateDropdownData(){
  for (let i = 0; i < this.kyc_list.length; i++) {

    if (this.kyc_list[i].state == this.state) {

      if (this.regions.indexOf(this.kyc_list[i].region) < 0) {
        this.regions.push(this.kyc_list[i].region);
      }
      if (this.commodities.indexOf(this.kyc_list[i].commodity) < 0) {
        this.commodities.push(this.kyc_list[i].commodity);
      }
    }
  }
}

//Region Data
regionData() {
  this.kyc_list = JSON.parse(this.oldView);
  if (this.region) {
    let newarr = this.kyc_list.filter(a => {
      if (a.region == this.region) {
        return a;
      }
    })
    this.kyc_list = newarr;
  }

  $("#kyc-list").dataTable().fnDestroy();
  this.datatable();
}

// Region Dropdown Data
regionDropdownData(){
  for (let i = 0; i < this.kyc_list.length; i++) {

    if (this.kyc_list[i].region == this.region) {

      if (this.commodities.indexOf(this.kyc_list[i].commodity) < 0) {
        this.commodities.push(this.kyc_list[i].commodity);
      }
    }
  }
}

//Commodity Data
commodityData() {
  this.kyc_list = JSON.parse(this.oldView);

  if (this.commodity) {
    let newarr = this.kyc_list.filter(a => {
      if (a.commodity == this.commodity && a.state == this.state || a.commodity == this.commodity && a.region == this.region) {
        return a;
      }else if(a.commodity == this.commodity && this.state == undefined && a.commodity == this.commodity && this.region == undefined){
        return a;
      }
    })
    this.kyc_list = newarr;
  }

  $("#kyc-list").dataTable().fnDestroy();
  this.datatable();
}



//Clear Filter
clearFilter(){
  if(this.state != undefined || this.region != undefined || this.commodity != undefined || this.searchText.length != 0){
    this.state = undefined;
    this.region = undefined;
    this.commodity = undefined;
    this.searchText = undefined;
    this.getKycListService(this.loggedInUser.userId);
  }

    sessionStorage.removeItem("state");
    sessionStorage.removeItem("region");
    sessionStorage.removeItem("commodity");

}





public filterData(){
   this.kyc_list = JSON.parse(this.oldView);
   if(this.commodity || this.state || this.region ||this.search){
     let newarr = this.kyc_list.filter(a =>
       {
         if (a.commodity == this.commodity || a.region == this.region || a.state == this.state || (this.search && a.farmerName.toUpperCase().includes(this.search.toUpperCase())) ) {
           return a;
         }
       })
       this.kyc_list = newarr;
   }

   $("#kyc-list").dataTable().fnDestroy();
   this.datatable();


 }

//  public searchData(){
//    this.assurance_list = JSON.parse(this.oldView);
//    if(this.search){
//      let newarr = this.assurance_list.filter(a =>
//        {
//          if (this.search && (a.commodity.toUpperCase().includes(this.search.toUpperCase()) || a.region.toUpperCase().includes(this.search.toUpperCase()) || a.state.toUpperCase().includes(this.search.toUpperCase()) || a.farmerName.toUpperCase().includes(this.search.toUpperCase())) ) {
//            return a;
//          }
//        })
//        this.assurance_list = newarr;
//    }

//    $("#kyc-list").dataTable().fnDestroy();
//    this.datatable();


//  }


public SearchData(){
  this.kyc_list = JSON.parse(this.oldView);
  if(this.search && this.search != '' && this.search.replace(' ','')){

   let newarr = this.kyc_list.filter(a =>
     {
       if((this.commodity || this.state || this.region) && (a.commodity == this.commodity || a.state == this.state || a.region == this.region) ) {
         return a;
       }
     })
     if(newarr && newarr.length==0){
       newarr = this.kyc_list;
     }
    newarr = newarr.filter(a =>
      {
        if(this.search && (a.commodity != null && a.commodity.toUpperCase().includes(this.search.toUpperCase()) || a.state != null && a.state.toUpperCase().includes(this.search.toUpperCase()) || a.region != null && a.region.toUpperCase().includes(this.search.toUpperCase()) || a.farmerName != null && a.farmerName.toUpperCase().includes(this.search.toUpperCase()) || a.mobileNumber != null && a.mobileNumber.toString().toUpperCase().includes(this.search.toString().toUpperCase()) || a.village != null && a.village.toUpperCase().includes(this.search.toUpperCase())) ) {
          return a;
        }
      })
      this.kyc_list = newarr;
  }else{
   this.filterData();
  }

  $("#kyc-list").dataTable().fnDestroy();
  this.datatable();
}



}




import { Component, OnInit, ViewChild } from '@angular/core';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { SuccessModalComponent } from '../../../modal-components/success-modal/success-modal.component';
import { KMLListModel } from './quality-assurance-list.model';
import { QualityAssuranceListService } from './quality-assurance-list.service';
import { Router, NavigationEnd } from "@angular/router";
import { ErrorMessages } from '../../../form-validation-messages';
import { DataTableDirective } from 'angular-datatables';


declare var $, datatable;

@Component({
  selector: 'app-quality-assurance-list',
  templateUrl: './quality-assurance-list.component.html',
  styleUrls: ['./quality-assurance-list.component.less']
})
export class QualityAssuranceListComponent implements OnInit {
  @ViewChild(DataTableDirective) datatableElement: DataTableDirective;

  modalRef: BsModalRef;
  public assurance_list: KMLListModel[];
  public loggedInUser  = {} as any;

  public commodities :string[];
  public states :string[];
  public varieties :string[];
  public regions :string[];
  public oldView :string;

  public commodity :string;
  public state :string;
  public variety :string;
  public region:string;
  public search:string;
  searchText;
  public httperrorresponsemessages: string;


  constructor(
    private router: Router,
    private qualityAssuranceListService: QualityAssuranceListService,
    private modalService: BsModalService
  ) { }

  ngOnInit() {

    //Error Messages
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

    this.loggedInUser = JSON.parse(localStorage.getItem("userData"));
    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0)
    });
   
    this.QualityAssuranceListService();
    //this.SearchState;


    if (sessionStorage.getItem('state') != undefined) {
      this.state = sessionStorage.getItem('state');
      //this.SearchRegion();
    }

    if (sessionStorage.getItem('region') != undefined) {
      this.region = sessionStorage.getItem('region');
      //this.SearchCommodity();      
    }

    if (sessionStorage.getItem('commodity') != undefined) {
      this.commodity = sessionStorage.getItem('commodity');
     // this.SearchVariety();
    }

    if (sessionStorage.getItem('variety') != undefined) {
      this.variety = sessionStorage.getItem('variety');
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

    if (this.variety != undefined) {
      sessionStorage.setItem('variety', this.variety.toString());
    }

    if (this.searchText != undefined) {
      sessionStorage.setItem('searchText', this.searchText);
    }

    
  }





  public QualityAssuranceListService() {
    this.loggedInUser.userId
    this.qualityAssuranceListService.getQualityAssuranceListService(this.loggedInUser.userId ).subscribe(
      (data) => {
        if(data.statusCode == "success"){
          this.assurance_list = data.data;
          $("#assurance-list").dataTable().fnDestroy();
          this.datatable();

          this.commodities = [];
          this.states = [];
          this.varieties = [];
          this.regions = [];
          this.assurance_list.forEach(item =>{
            if (item.state != null && this.states.indexOf(item.state) == -1 && item.state.trim() != '') this.states.push(item.state)
            if (item.region != null && this.regions.indexOf(item.region) == -1 && item.region.trim() != '') this.regions.push(item.region)
            if (item.commodity != null && this.commodities.indexOf(item.commodity) == -1 && item.commodity.trim() != '') this.commodities.push(item.commodity)
            if (item.variety != null && this.varieties.indexOf(item.variety) == -1 && item.variety.trim() != '') this.varieties.push(item.variety)
          })
          this.oldView = JSON.stringify(this.assurance_list)
          
        }else if(data.statusCode == "error"){
          const initialState = {
            title: "Error",
            content: data.message,
            action: "/kml-quality-assurance-list"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        }

      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/kml-quality-assurance-list"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }

  datatable() {
    setTimeout(function () {
      $('#assurance-list').DataTable({
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



  SearchState() {
    this.regions = [];
    this.commodities = [];
    this.varieties = [];
    this.region = undefined;
    this.commodity = undefined;
    this.variety = undefined;
    this.searchText = undefined;

    if (this.state == undefined) {
      this.QualityAssuranceListService();
    } else {

      //Filter functionality
      this.stateData();
      //Dropdown data functionality
      this.stateDropdownData();
    }

    this.datatableElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.ajax.reload()
    });


  }

  SearchRegion() {
    this.commodities = [];
    this.varieties = [];
    this.commodity = undefined;
    this.variety = undefined;
    this.searchText = undefined;
    
    if(this.state == undefined && this.region == undefined){
      this.QualityAssuranceListService();
    }else if (this.region == undefined) {
      this.stateData();
      this.stateDropdownData();
    } else {
      //Filter functionality
      this.regionData();
      //Dropdown data functionality
      this. regionDropdownData();

    }

    
    this.datatableElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.ajax.reload()
    });





  }

  SearchCommodity() {
    this.varieties = [];
    this.variety = undefined;
    this.searchText = undefined;

    if(this.state == undefined && this.region == undefined && this.commodity == undefined){
      this.QualityAssuranceListService();
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
      //Dropdown data functionality
      this.commodityDropdownData();
    }

    this.datatableElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.ajax.reload()
    });

  }


  SearchVariety() {
    this.searchText = undefined;
    if (this.state == undefined && this.region == undefined && this.commodity == undefined && this.variety == undefined) {
      this.QualityAssuranceListService();
    }else if (this.variety == undefined) {
      if(this.region == undefined && this.commodity == undefined){
        this.stateData();
        this.stateDropdownData();
      }else if(this.commodity == undefined){
        this.regionData();
        this.regionDropdownData();
      }else{
        this.commodityData();
        this.commodityDropdownData();
      }
    } else {
      //Filter functionality
      this.varietyData();
    }

    this.datatableElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.ajax.reload()
    });

  }



  searchTableData() {
    var table = $('#assurance-list').DataTable();
    $('#searchText').on( 'keyup', function () {
        table.search( this.value ).draw();
    } );
  }


  //Reusable Data
  stateData() {
    this.assurance_list = JSON.parse(this.oldView);

    if (this.state) {
      let newarr = this.assurance_list.filter(a => {
        if (a.state == this.state) {
          return a;
        }
      })
      this.assurance_list = newarr;
    }

    $("#assurance-list").dataTable().fnDestroy();
    this.datatable();
  }

  // State Dropdown Data
  stateDropdownData(){
    for (let i = 0; i < this.assurance_list.length; i++) {

      if (this.assurance_list[i].state == this.state) {

        if (this.regions.indexOf(this.assurance_list[i].region) < 0) {
          this.regions.push(this.assurance_list[i].region);
        }
        if (this.commodities.indexOf(this.assurance_list[i].commodity) < 0) {
          this.commodities.push(this.assurance_list[i].commodity);
        }
        if (this.varieties.indexOf(this.assurance_list[i].variety) < 0) {
          this.varieties.push(this.assurance_list[i].variety);
        }
      }
    }
  }

  //Region Data
  regionData() {
    this.assurance_list = JSON.parse(this.oldView);
    if (this.region) {
      let newarr = this.assurance_list.filter(a => {
        if (a.region == this.region) {
          return a;
        }
      })
      this.assurance_list = newarr;
    }

    $("#assurance-list").dataTable().fnDestroy();
    this.datatable();
  }

  // Region Dropdown Data
  regionDropdownData(){
    for (let i = 0; i < this.assurance_list.length; i++) {

      if (this.assurance_list[i].region == this.region) {

        if (this.commodities.indexOf(this.assurance_list[i].commodity) < 0) {
          this.commodities.push(this.assurance_list[i].commodity);
        }
        if (this.varieties.indexOf(this.assurance_list[i].variety) < 0) {
          this.varieties.push(this.assurance_list[i].variety);
        }
      }
    }
  }

  //Commodity Data
  commodityData() {
    this.assurance_list = JSON.parse(this.oldView);

    if (this.commodity) {
      let newarr = this.assurance_list.filter(a => {
        if (a.commodity == this.commodity && a.state == this.state || a.commodity == this.commodity && a.region == this.region) {
          return a;
        }else if(a.commodity == this.commodity && this.state == undefined && a.commodity == this.commodity && this.region == undefined){
          return a;
        }
      })
      this.assurance_list = newarr;
    }

    $("#assurance-list").dataTable().fnDestroy();
    this.datatable();
  }

  //Commodity Dropdown Data
  commodityDropdownData(){
    for (let i = 0; i < this.assurance_list.length; i++) {

      if (this.assurance_list[i].commodity == this.commodity) {

        if (this.varieties.indexOf(this.assurance_list[i].variety) < 0) {
          this.varieties.push(this.assurance_list[i].variety);
        }
      }
    }
  }

  //Variety Data
  varietyData() {
    this.assurance_list = JSON.parse(this.oldView);
    if (this.variety) {
      let newarr = this.assurance_list.filter(a => {
        if(a.variety == this.variety && a.state == this.state && a.variety == this.variety && a.region == this.region && a.variety == this.variety && a.commodity == this.commodity){
          return a;
        }else if(a.variety == this.variety && a.region == this.region && a.variety == this.variety && a.commodity == this.commodity && this.state == undefined){
          return a;
        }else if(a.variety == this.variety && a.state == this.state && this.region == undefined && this.commodity == undefined){
          return a;
        }else if(a.variety == this.variety && a.region == this.region && this.commodity == undefined){
          return a;
        }else if(a.variety == this.variety && a.commodity == this.commodity && this.state == undefined && this.region == undefined){
          return a;
        }else if(a.variety == this.variety && a.state == this.state && a.variety == this.variety && a.commodity == this.commodity && this.region == undefined){
          return a;
        }else if(a.variety == this.variety && this.state == undefined && a.variety == this.variety && this.region == undefined && a.variety == this.variety && this.commodity == undefined){
          return a;
        }
      })
      this.assurance_list = newarr;
    }

    $("#assurance-list").dataTable().fnDestroy();
    this.datatable();
  }

  //Clear Filter
  clearFilter(){
    if(this.state != undefined || this.region != undefined || this.commodity != undefined || this.variety != undefined || this.searchText.length != 0){
      this.state = undefined;
      this.region = undefined;
      this.commodity = undefined;
      this.variety = undefined;
      this.searchText = undefined;
      this.QualityAssuranceListService();
    }

    sessionStorage.removeItem("state");
    sessionStorage.removeItem("region");
    sessionStorage.removeItem("commodity");
    sessionStorage.removeItem("variety");
    sessionStorage.removeItem("searchText");



  }




  public filterData(){
     this.assurance_list = JSON.parse(this.oldView);
     if(this.commodity || this.state || this.region || this.variety){
       let newarr = this.assurance_list.filter(a => 
         {
           if (a.commodity == this.commodity || a.state == this.state || a.region == this.region || a.variety == this.variety) {
             return a;
           }
         })
         this.assurance_list = newarr;
     }

     $("#assurance-list").dataTable().fnDestroy();
     this.datatable();
  
      
   }

  //  public SearchData(){
  //    this.assurance_list = JSON.parse(this.oldView);
  //    if(this.search){
  //      let newarr = this.assurance_list.filter(a => 
  //        {
  //          if(this.search && (a.commodity.toUpperCase().includes(this.search.toUpperCase()) || a.state.toUpperCase().includes(this.search.toUpperCase()) || a.region.toUpperCase().includes(this.search.toUpperCase()) || a.variety.toUpperCase().includes(this.search.toUpperCase()) || a.farmerName.toUpperCase().includes(this.search.toUpperCase())) ) {
  //            return a;
  //          }
  //        })
  //        this.assurance_list = newarr;
  //    }

  //    $("#assurance-list").dataTable().fnDestroy();
  //    this.datatable();
  
      
  //  }


  public SearchData(){
    this.assurance_list = JSON.parse(this.oldView);
    if(this.search && this.search != '' && this.search.replace(' ','')){

     let newarr = this.assurance_list.filter(a => 
       {
         if((this.commodity || this.state || this.region || this.variety) && (a.commodity == this.commodity || a.state == this.state || a.region == this.region || a.variety == this.variety) ) {
           return a;
         }
       })
       if(newarr && newarr.length==0){
         newarr = this.assurance_list;
       }
      newarr = newarr.filter(a => 
        {
          if(this.search && (a.commodity != null && a.commodity.toUpperCase().includes(this.search.toUpperCase()) || a.state != null && a.state.toUpperCase().includes(this.search.toUpperCase()) || a.region != null && a.region.toUpperCase().includes(this.search.toUpperCase()) || a.variety != null && a.variety.toUpperCase().includes(this.search.toUpperCase()) || a.farmerName != null && a.farmerName.toUpperCase().includes(this.search.toUpperCase()) || a.mobileNumber != null && a.mobileNumber.toString().toUpperCase().includes(this.search.toString().toUpperCase()) || a.village != null && a.village.toUpperCase().includes(this.search.toUpperCase())) ) {
            return a;
          }
        })
        this.assurance_list = newarr;
    }else{
     this.filterData();
    }

    $("#assurance-list").dataTable().fnDestroy();
    this.datatable();
  }


}

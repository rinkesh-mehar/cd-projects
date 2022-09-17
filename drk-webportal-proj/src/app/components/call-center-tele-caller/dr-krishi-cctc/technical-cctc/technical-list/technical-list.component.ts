import { Component, OnInit} from '@angular/core';
import { Router, NavigationEnd } from "@angular/router";
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import {SuccessModalComponent} from '../../../../modal-components/success-modal/success-modal.component';
import { DrKrishiTechnical } from './dr-krishi-cctc-technical.model';
import{ DrKrishiTechnicalService } from './dr-krishi-technical.service';
import { ErrorMessages } from '../../../../form-validation-messages';

declare var $,datatable;

@Component({
  selector: 'app-technical-list',
  templateUrl: './technical-list.component.html',
  styleUrls: ['./technical-list.component.less']
})
export class TechnicalListComponent implements OnInit {

  public technical_list: DrKrishiTechnical[];
  modalRef: BsModalRef;
  primary: any;
  secondary: any;
  searchText;


  public villages :string[];
  public states :string[];
  public districts :string[];
  public regions :string[];
  public oldView :string;

  public village :string;
  public state :string;
  public district :string;
  public region:string;
  public search:string;
  public httperrorresponsemessages: string;


  constructor(
    private modalService: BsModalService, 
    private router: Router, 
    private drKrishiTechnicalService: DrKrishiTechnicalService
  ) { }

  ngOnInit() {

    //Error Messages
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0)
    });
    
    this.getDrKrishiTechnicalService();
  }


  datatable(){
    setTimeout(function(){ 
      $('#dr-krishi-technical').DataTable({
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

  public getDrKrishiTechnicalService() {
    this.drKrishiTechnicalService.getDrKrishiTechnicalService().subscribe(
      (data) => {
        this.technical_list = data.data;
        $("#dr-krishi-technical").dataTable().fnDestroy();
        this.datatable();

        this.villages = [];
        this.states = [];
        this.districts = [];
        this.regions = [];
        this.technical_list.forEach(item =>{
          if (item.state != null && this.states.indexOf(item.state) == -1 && item.state.trim() != '') this.states.push(item.state)
          if (item.region != null && this.regions.indexOf(item.region) == -1 && item.region.trim() != '') this.regions.push(item.region)
          if (item.district != null && this.districts.indexOf(item.district) == -1 && item.district.trim() != '') this.districts.push(item.district)
          if (item.village != null && this.villages.indexOf(item.village) == -1 && item.village.trim() != '') this.villages.push(item.village)
        })
        
        this.oldView = JSON.stringify(this.technical_list)
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/dr-krishi-technical-list"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }


  SearchState() {
    this.regions = [];
    this.districts = [];
    this.villages = [];
    this.region = undefined;
    this.district = undefined;
    this.village = undefined;
    this.searchText = undefined;

    if (this.state == undefined) {
      this.getDrKrishiTechnicalService();
    } else {

      //Filter functionality
      this.stateData();
      //Dropdown data functionality
      this.stateDropdownData();
    }


  }

  SearchRegion() {
    this.districts = [];
    this.villages = [];
    this.district = undefined;
    this.village = undefined;
    this.searchText = undefined;
    
    if(this.state == undefined && this.region == undefined){
      this.getDrKrishiTechnicalService();
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

  SearchDistrict() {
    this.villages = [];
    this.village = undefined;
    this.searchText = undefined;

    if(this.state == undefined && this.region == undefined && this.district == undefined){
      this.getDrKrishiTechnicalService();
    }else if (this.district == undefined) {
      if(this.region == undefined){
        this.stateData();
        this.stateDropdownData();
      }else{
        this.regionData();
        this.regionDropdownData();
      }
    }else {
      //Filter functionality
      this.districtData();
      //Dropdown data functionality
      this.districtDropdownData();
    }
  }


  SearchVillage() {
    this.searchText = undefined;
    if (this.state == undefined && this.region == undefined && this.district == undefined && this.village == undefined) {
      this.getDrKrishiTechnicalService();
    }else if (this.village == undefined) {
      if(this.region == undefined && this.district == undefined){
        this.stateData();
        this.stateDropdownData();
      }else if(this.district == undefined){
        this.regionData();
        this.regionDropdownData();
      }else{
        this.districtData();
        this.districtDropdownData();
      }
    } else {
      //Filter functionality
      this.villageData();
    }
  }


  searchTableData() {
    var table = $('#dr-krishi-technical').DataTable();
    $('#searchText').on( 'keyup', function () {
        table.search( this.value ).draw();
    } );
  }


  //Reusable Data
  stateData() {
    this.technical_list = JSON.parse(this.oldView);

    if (this.state) {
      let newarr = this.technical_list.filter(a => {
        if (a.state == this.state) {
          return a;
        }
      })
      this.technical_list = newarr;
    }

    $("#dr-krishi-technical").dataTable().fnDestroy();
    this.datatable();
  }

  // State Dropdown Data
  stateDropdownData(){
    for (let i = 0; i < this.technical_list.length; i++) {

      if (this.technical_list[i].state == this.state) {

        if (this.regions.indexOf(this.technical_list[i].region) < 0) {
          this.regions.push(this.technical_list[i].region);
        }
        if (this.districts.indexOf(this.technical_list[i].district) < 0) {
          this.districts.push(this.technical_list[i].district);
        }
        if (this.villages.indexOf(this.technical_list[i].village) < 0) {
          this.villages.push(this.technical_list[i].village);
        }
      }
    }
  }

  //Region Data
  regionData() {
    this.technical_list = JSON.parse(this.oldView);
    if (this.region) {
      let newarr = this.technical_list.filter(a => {
        if (a.region == this.region) {
          return a;
        }
      })
      this.technical_list = newarr;
    }

    $("#dr-krishi-technical").dataTable().fnDestroy();
    this.datatable();
  }

  // Region Dropdown Data
  regionDropdownData(){
    for (let i = 0; i < this.technical_list.length; i++) {

      if (this.technical_list[i].region == this.region) {

        if (this.districts.indexOf(this.technical_list[i].district) < 0) {
          this.districts.push(this.technical_list[i].district);
        }
        if (this.villages.indexOf(this.technical_list[i].village) < 0) {
          this.villages.push(this.technical_list[i].village);
        }
      }
    }
  }

  //District Data
  districtData() {
    this.technical_list = JSON.parse(this.oldView);

    if (this.district) {
      let newarr = this.technical_list.filter(a => {
        if (a.district == this.district && a.state == this.state || a.district == this.district && a.region == this.region) {
          return a;
        }else if(a.district == this.district && this.state == undefined && a.district == this.district && this.region == undefined){
          return a;
        }
      })
      this.technical_list = newarr;
    }

    $("#dr-krishi-technical").dataTable().fnDestroy();
    this.datatable();
  }

  //District Dropdown Data
  districtDropdownData(){
    for (let i = 0; i < this.technical_list.length; i++) {

      if (this.technical_list[i].district == this.district) {

        if (this.villages.indexOf(this.technical_list[i].village) < 0) {
          this.villages.push(this.technical_list[i].village);
        }
      }
    }
  }

  //Village Data
  villageData() {
    this.technical_list = JSON.parse(this.oldView);
    if (this.village) {
      let newarr = this.technical_list.filter(a => {
        if(a.village == this.village && a.state == this.state && a.village == this.village && a.region == this.region && a.village == this.village && a.district == this.district){
          return a;
        }else if(a.village == this.village && a.region == this.region && a.village == this.village && a.district == this.district && this.state == undefined){
          return a;
        }else if(a.village == this.village && a.state == this.state && this.region == undefined && this.district == undefined){
          return a;
        }else if(a.village == this.village && a.region == this.region && this.district == undefined){
          return a;
        }else if(a.village == this.village && a.district == this.district && this.state == undefined && this.region == undefined){
          return a;
        }else if(a.village == this.village && a.state == this.state && a.village == this.village && a.district == this.district && this.region == undefined){
          return a;
        }else if(a.village == this.village && this.state == undefined && a.village == this.village && this.region == undefined && a.village == this.village && this.district == undefined){
          return a;
        }
      })
      this.technical_list = newarr;
    }

    $("#dr-krishi-technical").dataTable().fnDestroy();
    this.datatable();
  }

  //Clear Filter
  clearFilter(){
    if(this.state != undefined || this.region != undefined || this.district != undefined || this.village != undefined || this.searchText.length != 0){
      this.state = undefined;
      this.region = undefined;
      this.district = undefined;
      this.village = undefined;
      this.searchText = undefined;
      this.getDrKrishiTechnicalService();
    }
  }



  public filterData(){
     this.technical_list = JSON.parse(this.oldView);
     if(this.village || this.state || this.region || this.district){
       let newarr = this.technical_list.filter(a => 
         {
           if (a.village == this.village || a.state == this.state || a.region == this.region || a.district == this.district) {
             return a;
           }
         })
         this.technical_list = newarr;
     }

     $("#dr-krishi-technical").dataTable().fnDestroy(); 
     this.datatable();
  
      
   }

   public SearchData(){
    this.technical_list = JSON.parse(this.oldView);
    if(this.search && this.search != '' && this.search.replace(' ','')){

     let newarr = this.technical_list.filter(a => 
       {
         if((this.village || this.state || this.region || this.district) && (a.village == this.village || a.state == this.state || a.region == this.region || a.district == this.district) ) {
           return a;
         }
       })
       if(newarr && newarr.length==0){
         newarr = this.technical_list;
       }
      newarr = newarr.filter(a => 
        {
          if(this.search && (a.village != null && a.village.toUpperCase().includes(this.search.toUpperCase()) || a.state != null && a.state.toUpperCase().includes(this.search.toUpperCase()) || a.region != null && a.region.toUpperCase().includes(this.search.toUpperCase()) || a.district != null && a.district.toUpperCase().includes(this.search.toUpperCase()) || a.name != null && a.name.toUpperCase().includes(this.search.toUpperCase()) || a.caseid != null && a.caseid.toUpperCase().includes(this.search.toUpperCase()) || a.expecteddate != null && a.expecteddate.toUpperCase().includes(this.search.toUpperCase())) ) {
            return a;
          }
        })
        this.technical_list = newarr;
    }else{
     this.filterData();
    }

    $("#dr-krishi-technical").dataTable().fnDestroy();
    this.datatable();
 
     
  }
}

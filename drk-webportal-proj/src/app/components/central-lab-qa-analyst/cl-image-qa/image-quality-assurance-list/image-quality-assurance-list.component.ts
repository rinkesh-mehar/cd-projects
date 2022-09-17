import { Component, OnInit } from '@angular/core';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ClImage } from './image-quality-assurance-list.model';
import { ImageQualityAssuranceList } from './image-quality-assurance-list.service';
import { SuccessModalComponent } from '../../../modal-components/success-modal/success-modal.component';
import { Router, NavigationEnd } from "@angular/router";
import { ErrorMessages } from '../../../form-validation-messages';
declare var $,datatable;

@Component({
  selector: 'app-image-quality-assurance-list',
  templateUrl: './image-quality-assurance-list.component.html',
  styleUrls: ['./image-quality-assurance-list.component.less']
})
export class ImageQualityAssuranceListComponent implements OnInit {

  modalRef: BsModalRef;
  public image_assurance_list: ClImage[];
  public loggedInUser = {} as any;

  public commodities :string[];
  public states :string[];
  public regions :string[];
  public oldView :string;

  public commodity :string;
  public state :string;
  public region:string;
  public search:string;
  searchText;
  public httperrorresponsemessages: string;

  constructor(
    private router: Router,
    private modalService: BsModalService, 
    private imageQualityAssuranceList: ImageQualityAssuranceList
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
    this.getImageQualityAssuranceListService(this.loggedInUser.userId);


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
      $('#image-list').DataTable({
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
  public getImageQualityAssuranceListService(id) {
    this.imageQualityAssuranceList.getImageQualityAssuranceListService(id).subscribe(
      (data) => {
        if(data.statusCode == "success"){
          this.image_assurance_list = data.data;
          this.oldView = JSON.stringify(this.image_assurance_list);
          $("#image-list").dataTable().fnDestroy();
          this.datatable();
          this.commodities = [];
          this.states = [];
          this.regions = [];
          this.image_assurance_list.forEach(item =>{
            if (item.stateName != null && this.states.indexOf(item.stateName) == -1 && item.stateName.trim() != '') this.states.push(item.stateName)
            if (item.regionName != null && this.regions.indexOf(item.regionName) == -1 && item.regionName.trim() != '') this.regions.push(item.regionName)
            if (item.commodityName != null && this.commodities.indexOf(item.commodityName) == -1 && item.commodityName.trim() != '') this.commodities.push(item.commodityName)
          })
        }else if(data.statusCode == "error"){
          const initialState = {
            title: "Error",
            content: data.message,
            action: "/image-quality-assurance-list"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        }
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/image-quality-assurance-list"
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
    this.getImageQualityAssuranceListService(this.loggedInUser.userId);
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
    this.getImageQualityAssuranceListService(this.loggedInUser.userId);
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
    this.getImageQualityAssuranceListService(this.loggedInUser.userId);
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
  var table = $('#image-list').DataTable();
  $('#searchText').on( 'keyup', function () {
      table.search( this.value ).draw();
  } );
}


//Reusable Data
stateData() {
  this.image_assurance_list = JSON.parse(this.oldView);

  if (this.state) {
    let newarr = this.image_assurance_list.filter(a => {
      if (a.stateName == this.state) {
        return a;
      }
    })
    this.image_assurance_list = newarr;
  }

  $("#image-list").dataTable().fnDestroy();
  this.datatable();
}

// State Dropdown Data
stateDropdownData(){
  for (let i = 0; i < this.image_assurance_list.length; i++) {

    if (this.image_assurance_list[i].stateName == this.state) {

      if (this.regions.indexOf(this.image_assurance_list[i].regionName) < 0) {
        this.regions.push(this.image_assurance_list[i].regionName);
      }
      if (this.commodities.indexOf(this.image_assurance_list[i].commodityName) < 0) {
        this.commodities.push(this.image_assurance_list[i].commodityName);
      }
    }
  }
}

//Region Data
regionData() {
  this.image_assurance_list = JSON.parse(this.oldView);
  if (this.region) {
    let newarr = this.image_assurance_list.filter(a => {
      if (a.regionName == this.region) {
        return a;
      }
    })
    this.image_assurance_list = newarr;
  }

  $("#image-list").dataTable().fnDestroy();
  this.datatable();
}

// Region Dropdown Data
regionDropdownData(){
  for (let i = 0; i < this.image_assurance_list.length; i++) {

    if (this.image_assurance_list[i].regionName == this.region) {

      if (this.commodities.indexOf(this.image_assurance_list[i].commodityName) < 0) {
        this.commodities.push(this.image_assurance_list[i].commodityName);
      }
    }
  }
}

//Commodity Data
commodityData() {
  this.image_assurance_list = JSON.parse(this.oldView);

  if (this.commodity) {
    let newarr = this.image_assurance_list.filter(a => {
      if (a.commodityName == this.commodity && a.stateName == this.state || a.commodityName == this.commodity && a.regionName == this.region) {
        return a;
      }else if(a.commodityName == this.commodity && this.state == undefined && a.commodityName == this.commodity && this.region == undefined){
        return a;
      }
    })
    this.image_assurance_list = newarr;
  }

  $("#image-list").dataTable().fnDestroy();
  this.datatable();
}



//Clear Filter
clearFilter(){
  if(this.state != undefined || this.region != undefined || this.commodity != undefined || this.searchText.length != 0){
    this.state = undefined;
    this.region = undefined;
    this.commodity = undefined;
    this.searchText = undefined;
    this.getImageQualityAssuranceListService(this.loggedInUser.userId);
  }

  sessionStorage.removeItem("state");
    sessionStorage.removeItem("region");
    sessionStorage.removeItem("commodity");
    sessionStorage.removeItem("searchText");

}




  public filterData(){
     this.image_assurance_list = JSON.parse(this.oldView);
     if(this.commodity || this.state || this.region ){
       let newarr = this.image_assurance_list.filter(a => 
         {
           if (a.commodityName == this.commodity || a.stateName == this.state || a.regionName == this.region ) {
             return a;
           }
         })
         this.image_assurance_list = newarr;
     }

     $("#image-list").dataTable().fnDestroy();
     this.datatable();
  
      
   }

  //  public SearchData(){
  //    this.assurance_list = JSON.parse(this.oldView);
  //    if(this.search){
  //      let newarr = this.assurance_list.filter(a => 
  //        {
  //          if(this.search && (a.commodityName.toUpperCase().includes(this.search.toUpperCase()) || a.stateName.toUpperCase().includes(this.search.toUpperCase()) || a.regionName.toUpperCase().includes(this.search.toUpperCase())) ) {
  //            return a;
  //          }
  //        })
  //        this.assurance_list = newarr;
  //    }

  //    $("#image-list").dataTable().fnDestroy();
  //    this.datatable();
  
      
  //  }


  public SearchData(){
    this.image_assurance_list = JSON.parse(this.oldView);
    if(this.search && this.search != '' && this.search.replace(' ','')){
  
     let newarr = this.image_assurance_list.filter(a => 
       {
         if((this.commodity || this.state || this.region) && (a.commodityName == this.commodity || a.stateName == this.state || a.regionName == this.region) ) {
           return a;
         }
       })
       if(newarr && newarr.length==0){
         newarr = this.image_assurance_list;
       }
      newarr = newarr.filter(a => 
        {
          if(this.search && (a.commodityName != null && a.commodityName.toUpperCase().includes(this.search.toUpperCase()) || a.stateName != null && a.stateName.toUpperCase().includes(this.search.toUpperCase()) || a.regionName != null && a.regionName.toUpperCase().includes(this.search.toUpperCase())) ) {
            return a;
          }
        })
        this.image_assurance_list = newarr;
    }else{
     this.filterData();
    }
  
    $("#image-list").dataTable().fnDestroy();
    this.datatable();
  }

}

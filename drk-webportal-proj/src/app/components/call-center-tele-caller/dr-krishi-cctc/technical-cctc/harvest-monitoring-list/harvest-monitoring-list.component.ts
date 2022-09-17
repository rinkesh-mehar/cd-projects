import { ErrorMessages } from 'src/app/components/form-validation-messages';
import { DrKrishiTechnicalService } from './../technical-list/dr-krishi-technical.service';
import { NavigationEnd } from '@angular/router';
import { SuccessModalComponent } from 'src/app/components/modal-components/success-modal/success-modal.component';
import { Router } from '@angular/router';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { HarvestMonitoringList } from './harvest-monitoring-list.model';
import {Component, OnInit, TemplateRef} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {callingStatusModel, ResponseMessage} from '../../non-technical-cctc/non-technical-list/dr-krishi-cctc-non-technical.model';
import {DrKrishiNonTechnicalService} from '../../non-technical-cctc/non-technical-list/dr-krishi-non-technical.service';
import {DrKrishiTechnical} from '../technical-list/dr-krishi-cctc-technical.model';

@Component({
  selector: 'app-harvest-monitoring-list',
  templateUrl: './harvest-monitoring-list.component.html',
  styleUrls: ['./harvest-monitoring-list.component.scss']
})
export class HarvestMonitoringListComponent implements OnInit {
  public harvestMonitoring_list: any;
  modalRef: BsModalRef;
  primary: any;
  secondary: any;
  searchText;
  farmerCalling: FormGroup;
  callingStatusList: any;
  public callingStatusOption = {} as callingStatusModel;
  submitted = false;
  public userid;

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
    private drKrishiTechnicalService: DrKrishiTechnicalService,
    private formBuilder: FormBuilder,
    private drKrishiNonTechnicalService: DrKrishiNonTechnicalService,
  ) { }

  ngOnInit() {

    this.userid = localStorage.getItem('loginUserid');

    this.farmerCalling = this.formBuilder.group({
      callingstatus: ["10"],
      taskid: ['']
    });

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
    // setTimeout(function(){
    //   $('#dr-krishi-technical').DataTable({
    //     "bPaginate": true,
    //     "bInfo": true,
    //     "searching": true,
    //     "lengthChange": true,
    //     columnDefs: [{
    //       orderable: false,
    //       targets: [0]
    //     }],
    //     lengthMenu: [
    //         [ 5, 10, 15, -1 ],
    //         [ '5', '10', '15', 'All' ]
    //     ],
    //     "dom": "<'row'<'col-sm-12'tr>>" +
    //       "<'table-footer'<'table-length'l><'table-info'i><'table-pagination'p>>",
    //       "fnDrawCallback": function(oSettings) {
    //         if ($('table.table-bordered.dataTable td').hasClass("dataTables_empty")) {
    //            $(".paginate_button.active").hide();
    //         }
    //     }
    //   });
    //   $.fn.dataTable.ext.errMode = 'none';
    // }, 40);



  }

  public getDrKrishiTechnicalService() {
    this.drKrishiTechnicalService.getDrKrishiTechnicalService().subscribe(
      (data) => {
        this.harvestMonitoring_list = data.data;
        $("#dr-krishi-technical").dataTable();
        this.datatable();

        this.villages = [];
        this.states = [];
        this.districts = [];
        this.regions = [];
        this.harvestMonitoring_list.forEach(item =>{
          if (item.state != null && this.states.indexOf(item.state) == -1 && item.state.trim() != '') this.states.push(item.state)
          if (item.region != null && this.regions.indexOf(item.region) == -1 && item.region.trim() != '') this.regions.push(item.region)
          if (item.district != null && this.districts.indexOf(item.district) == -1 && item.district.trim() != '') this.districts.push(item.district)
          if (item.village != null && this.villages.indexOf(item.village) == -1 && item.village.trim() != '') this.villages.push(item.village)
        })

        this.oldView = JSON.stringify(this.harvestMonitoring_list)
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/dr-krishi-technical-harvest-monitoring"
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
        table.search();
    } );
  }


  //Reusable Data
  stateData() {
    this.harvestMonitoring_list = JSON.parse(this.oldView);

    if (this.state) {
      let newarr = this.harvestMonitoring_list.filter(a => {
        if (a.state == this.state) {
          return a;
        }
      })
      this.harvestMonitoring_list = newarr;
    }

    $("#dr-krishi-technical").dataTable();
    this.datatable();
  }

  // State Dropdown Data
  stateDropdownData(){
    for (let i = 0; i < this.harvestMonitoring_list.length; i++) {

      if (this.harvestMonitoring_list[i].state == this.state) {

        if (this.regions.indexOf(this.harvestMonitoring_list[i].region) < 0) {
          this.regions.push(this.harvestMonitoring_list[i].region);
        }
        if (this.districts.indexOf(this.harvestMonitoring_list[i].district) < 0) {
          this.districts.push(this.harvestMonitoring_list[i].district);
        }
        if (this.villages.indexOf(this.harvestMonitoring_list[i].village) < 0) {
          this.villages.push(this.harvestMonitoring_list[i].village);
        }
      }
    }
  }

  //Region Data
  regionData() {
    this.harvestMonitoring_list = JSON.parse(this.oldView);
    if (this.region) {
      let newarr = this.harvestMonitoring_list.filter(a => {
        if (a.region == this.region) {
          return a;
        }
      })
      this.harvestMonitoring_list = newarr;
    }

    $("#dr-krishi-technical").dataTable();
    this.datatable();
  }

  // Region Dropdown Data
  regionDropdownData(){
    for (let i = 0; i < this.harvestMonitoring_list.length; i++) {

      if (this.harvestMonitoring_list[i].region == this.region) {

        if (this.districts.indexOf(this.harvestMonitoring_list[i].district) < 0) {
          this.districts.push(this.harvestMonitoring_list[i].district);
        }
        if (this.villages.indexOf(this.harvestMonitoring_list[i].village) < 0) {
          this.villages.push(this.harvestMonitoring_list[i].village);
        }
      }
    }
  }

  //District Data
  districtData() {
    this.harvestMonitoring_list = JSON.parse(this.oldView);

    if (this.district) {
      let newarr = this.harvestMonitoring_list.filter(a => {
        if (a.district == this.district && a.state == this.state || a.district == this.district && a.region == this.region) {
          return a;
        }else if(a.district == this.district && this.state == undefined && a.district == this.district && this.region == undefined){
          return a;
        }
      })
      this.harvestMonitoring_list = newarr;
    }

    $("#dr-krishi-technical").dataTable();
    this.datatable();
  }

  //District Dropdown Data
  districtDropdownData(){
    for (let i = 0; i < this.harvestMonitoring_list.length; i++) {

      if (this.harvestMonitoring_list[i].district == this.district) {

        if (this.villages.indexOf(this.harvestMonitoring_list[i].village) < 0) {
          this.villages.push(this.harvestMonitoring_list[i].village);
        }
      }
    }
  }

  public onCall(callingstatus: TemplateRef<any>, primarynumber, secondarynumber, taskid, type) {
    // if (type == "Registration") {
      this.primary = primarynumber;
      this.secondary = secondarynumber;
      this.farmerCalling.controls['taskid'].setValue(taskid);
      this.getCallingStatus();
      this.modalRef = this.modalService.show(callingstatus, { backdrop: 'static', keyboard: false });
    // } else if (type == "Verification") {
    //   this.router.navigate(['/dr-krishi-technical-form/' + taskid])
    // }
    // else if (type == "Incomplete Monitoring") {
    //   this.router.navigate(['/dr-krishi-incomplete-data/' + taskid])
    // }
    // else if (type == "Incomplete Verification") {
    //   this.router.navigate(['/dr-krishi-incomplete-data/' + taskid])
    // }

  }

  getCallingStatus() {
    this.drKrishiTechnicalService.getCallingStatusList().subscribe(
      (data) => {
        if(data.status = 200) {
          this.callingStatusList = data.data;
        } else {
          const initialState = {
            title: "Error",
            content: data.message,
            action: '/dr-krishi-technical-harvest-monitoring-list'
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        }
      })
  }


  onSave() {

    if (this.state != undefined) {
      sessionStorage.setItem('state', this.state.toString());
    }

    if (this.region != undefined) {
      sessionStorage.setItem('region', this.region.toString());
    }

    if (this.district != undefined) {
      sessionStorage.setItem('district', this.district.toString());
    }

    if (this.village != undefined) {
      sessionStorage.setItem('village', this.village.toString());
    }

    if (this.searchText != undefined) {
      sessionStorage.setItem('searchText', this.searchText);
    }



    this.submitted = true;
    this.callingStatusOption.callingstatus = this.farmerCalling.value.callingstatus;
    this.callingStatusOption = this.farmerCalling.value;
    this.callingStatusOption.userid = this.userid


    if (this.farmerCalling.value['callingstatus'] == 'undefined') {
      this.farmerCalling.controls['callingstatus'].setErrors({ required: true });
      return;
    }

    // stop here if form is invalid
    if (!this.farmerCalling.invalid) {
      this.modalRef.hide();
      if (this.farmerCalling.value['callingstatus'] == '10') {
        this.router.navigate(['/dr-krishi-technical-harvest-monitoring/' + this.farmerCalling.value.taskid])
        return
      }

      this.drKrishiNonTechnicalService.submitUserGI(this.callingStatusOption).subscribe(
        (data: ResponseMessage) => {
          if (data.status == 200) {
            const initialState = {
              title: "Success",
              content: 'The calling status has been updated',
              action: "/dr-krishi-technical-harvest-monitoring-list"
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          } else {
            const initialState = {
              title: "Error",
              content: 'Error occurred during saving calling status, To continue please contact system admin',
              action: "/dr-krishi-technical-harvest-monitoring-list"
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          }
          return;
        }, (err) => {
          const initialState = {
            title: "Error",
            content: this.httperrorresponsemessages,
            action: "/dr-krishi-technical-harvest-monitoring-list"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        });

    } else {
      return;
    }
  }


  modalClose(){
    this.modalRef.hide();
    this.farmerCalling.controls['callingstatus'].setValue("10")
  }


  //Village Data
  villageData() {
    this.harvestMonitoring_list = JSON.parse(this.oldView);
    if (this.village) {
      let newarr = this.harvestMonitoring_list.filter(a => {
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
      this.harvestMonitoring_list = newarr;
    }

    $("#dr-krishi-technical").dataTable();
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
     this.harvestMonitoring_list = JSON.parse(this.oldView);
     if(this.village || this.state || this.region || this.district){
       let newarr = this.harvestMonitoring_list.filter(a =>
         {
           if (a.village == this.village || a.state == this.state || a.region == this.region || a.district == this.district) {
             return a;
           }
         })
         this.harvestMonitoring_list = newarr;
     }

     $("#dr-krishi-technical").dataTable();
     this.datatable();


   }

   public SearchData(){
    this.harvestMonitoring_list = JSON.parse(this.oldView);
    if(this.search && this.search != '' && this.search.replace(' ','')){

     let newarr = this.harvestMonitoring_list.filter(a =>
       {
         if((this.village || this.state || this.region || this.district) && (a.village == this.village || a.state == this.state || a.region == this.region || a.district == this.district) ) {
           return a;
         }
       })
       if(newarr && newarr.length==0){
         newarr = this.harvestMonitoring_list;
       }
      newarr = newarr.filter(a =>
        {
          if(this.search && (a.village != null && a.village.toUpperCase().includes(this.search.toUpperCase()) || a.state != null && a.state.toUpperCase().includes(this.search.toUpperCase()) || a.region != null && a.region.toUpperCase().includes(this.search.toUpperCase()) || a.district != null && a.district.toUpperCase().includes(this.search.toUpperCase()) || a.name != null && a.name.toUpperCase().includes(this.search.toUpperCase()) || a.caseid != null && a.caseid.toUpperCase().includes(this.search.toUpperCase()) || a.expecteddate != null && a.expecteddate.toUpperCase().includes(this.search.toUpperCase())) ) {
            return a;
          }
        })
        this.harvestMonitoring_list = newarr;
    }else{
     this.filterData();
    }

    $("#dr-krishi-technical").dataTable();
    this.datatable();


  }
}

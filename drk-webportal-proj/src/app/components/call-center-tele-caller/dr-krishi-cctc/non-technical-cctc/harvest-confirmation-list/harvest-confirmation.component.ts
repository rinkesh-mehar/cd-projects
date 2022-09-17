import { HttpClient } from '@angular/common/http';
import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NavigationEnd, Router } from '@angular/router';
import { DataTableDirective } from 'angular-datatables';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subject } from 'rxjs';
import { ErrorMessages } from 'src/app/components/form-validation-messages';
import { SuccessModalComponent } from 'src/app/components/modal-components/success-modal/success-modal.component';
import { callingStatusModel, DrKrishiNonTechnical, filterValueModel, ResponseMessage, SubmitNontechincalCallingStatus } from '../non-technical-list/dr-krishi-cctc-non-technical.model';
import {HarvestConfirmationListModel} from "./harvest-confirmation-list.model";
import {HarvestConfirmationListService} from "./harvest-confirmation-list.service";
import {AgriotaStatus} from '../agriota-status-list/agriota-status.model';

@Component({
  selector:'app-harvest-confirmation',
  templateUrl:'./harvest-confirmation.component.html',
  styleUrls: ['./harvest-confirmation.component.less']
})
export class HarvestconfirmationComponent implements OnInit{

  @ViewChild(DataTableDirective) datatableElement: DataTableDirective;
  public dtOptions: DataTables.Settings = {};
  public dtTrigger: Subject<any> = new Subject();
  public non_technical_list: HarvestConfirmationListModel[];
  public non_technical_list_temp = [] as HarvestConfirmationListModel[];
  public callingstatus = {} as callingStatusModel;
  public submitcallingstatus = {} as SubmitNontechincalCallingStatus;
  modalRef: BsModalRef;
  farmerCalling: FormGroup;
  primary: any;
  secondary: any;
  searchText;
  submitted = false;
  public callingStatusOption = {} as callingStatusModel;
  public filterValues = {} as filterValueModel;
  public userid;
  public loggedInUser = {} as any;
  public villages: string[];
  public states: string[];
  public districts: string[];
  public regions: string[];
  public callingStatusList: string[]; // added for calling status
  public oldView: string;
  status: string;

  public village: number;
  public state: number;
  public district: number;
  public region: number;
  public search: string;
  public taskidformodal: string;
  shouldShow: boolean = false;

  //Error Messages
  public textboxerrormessage: string;
  public dropdownerrormessage: string;
  public httperrorresponsemessages: string;
  public datatableemptymessage: string;


  constructor(
    private modalService: BsModalService,
    private router: Router,
    private http: HttpClient,
    private harvestConfirmationListService: HarvestConfirmationListService,
    private formBuilder: FormBuilder,

  ) { }

  ngOnInit() {

    //Error Messages
    this.textboxerrormessage = ErrorMessages.textboxError;
    this.dropdownerrormessage = ErrorMessages.dropdownError;
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;
    this.datatableemptymessage = ErrorMessages.dataTableEmptyMessage;

    this.userid = localStorage.getItem('loginUserid');

    this.farmerCalling = this.formBuilder.group({
      callingstatus: ["10"],
      taskid: ['']
    });

    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0)
    });

    //this.getDrKrishiNonTechnicalService();

    this.getCCTCNonTechnicalList();
    this.getStates();


    if (sessionStorage.getItem('state') != undefined) {
      this.state = parseInt(sessionStorage.getItem('state'));
      this.searchRegionData();
    }

    if (sessionStorage.getItem('region') != undefined) {
      this.region = parseInt(sessionStorage.getItem('region'));
      this.searchDistrictsData();
    }

    if (sessionStorage.getItem('district') != undefined) {
      this.district = parseInt(sessionStorage.getItem('district'));
      this.searchVillagesData();
    }

    if (sessionStorage.getItem('village') != undefined) {
      this.village = parseInt(sessionStorage.getItem('village'));
    }

    if (sessionStorage.getItem('searchText') != undefined) {
      this.searchText = sessionStorage.getItem('searchText');
    }


  }

  get f() { return this.farmerCalling.controls; }


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
        this.router.navigate(['/dr-krishi-non-technical-information-harvest-confirm-form/' + this.farmerCalling.value.taskid])
        return
      }

      this.harvestConfirmationListService.submitUserGI(this.callingStatusOption).subscribe(
        (data: ResponseMessage) => {
          if (data.status == 200) {
            const initialState = {
              title: "Success",
              content: 'The calling status has been updated',
              action: "/dr-krishi-non-technical-harvest-confirm-list"
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          } else {
            const initialState = {
              title: "Error",
              content: 'Error occurred during saving calling status, To continue please contact system admin',
              action: "/dr-krishi-non-technical-harvest-confirm-list"
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          }
          return;
        }, (err) => {
          const initialState = {
            title: "Error",
            content: this.httperrorresponsemessages,
            action: "/dr-krishi-non-technical-harvest-confirm-list"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        });

    } else {
      return;
    }
  }

  public getCCTCNonTechnicalList(){
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10,
      serverSide: true,
      processing: false,
      stateSave: true,
      //order: [[ 1, "desc" ]],
      lengthMenu: [
        [10, 30, 50, 100, 200],
        [10, 30, 50, 100, 200,]
      ],
      autoWidth: true,
      searching: true,
      // TODO - Please remove this after discussion of implementation
      // "dom": "<'row'<'col-sm-12'tr>>" +
      //   "<'table-footer'<'table-search's><'table-length'l><'table-info'i><'table-pagination'p>>",

      ajax: (parameters: any, callback) => {

        if(this.state == undefined){
          this.filterValues.stateId = 0;
          this.filterValues.regionId = 0;
          this.filterValues.districtId = 0;
          this.filterValues.villageId = 0;
        }else{
          this.filterValues.stateId = this.state;
        }

        if(this.region == undefined){
          this.filterValues.regionId = 0;
          this.filterValues.districtId = 0;
          this.filterValues.villageId = 0;
        }else{
          this.filterValues.stateId = this.state;
          this.filterValues.regionId = this.region;
        }
        if(this.district == undefined){
          this.filterValues.districtId = 0;
          this.filterValues.villageId = 0;
        }else{
          this.filterValues.stateId = this.state;
          this.filterValues.regionId = this.region;
          this.filterValues.districtId = this.district;
        }
        if(this.village == undefined){
          this.filterValues.villageId = 0;
        }else{
          this.filterValues.stateId = this.state;
          this.filterValues.regionId = this.region;
          this.filterValues.districtId = this.district;
          this.filterValues.villageId = this.village
        }

        this.harvestConfirmationListService.getLeadCallingListForSpot(this.filterValues.stateId, this.filterValues.regionId, this.filterValues.districtId, this.filterValues.villageId, parameters).subscribe(resp => {
          if(resp.status == 200){
            // TODO - Please remove this after discussion of implementation
            // this.non_technical_list = resp.data.data;
            this.non_technical_list = this.non_technical_list_temp;

            this.non_technical_list = this.non_technical_list.filter(
              non_technical_list => non_technical_list.cropType === 'Harvested' || non_technical_list.cropType === 'Warehoused'
            );

            console.log('filtered non_technical_list for spot ', this.non_technical_list);

            // TODO - Please remove this after discussion of implementation
            // callback({
            //   recordsTotal: resp.data.recordsTotal,
            //   recordsFiltered: resp.data.recordsFiltered,
            //   data: []
            // });
          }else{
            const initialState = {
              title: "Error",
              content: resp.message,
              action: "/dr-krishi-non-technical-harvest-confirm-list"
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
            return;
          }

        }, (err) => {
          const initialState = {
            title: "Error",
            content: this.httperrorresponsemessages,
            action: "/dr-krishi-non-technical-harvest-confirm-list"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        });
      },
      columns: [
        { data: 'ordering', searchable: false }, { data: 'serialNumber', searchable: false }, { data: 'farmerName',  searchable: true}, { data: 'stateName',  searchable: true}, { data: 'regionName',  searchable: true}, { data: 'districtName',  searchable: true}, { data: 'villageName',  searchable: true}, { data: 'type',  searchable: true },
        {data:'sellerType', searchable: true} // added seller type-CDT-Ujwal
      ],

    };
  }

  getStates(){
    this.harvestConfirmationListService.getCCTCNonTechnicalState().subscribe(
      (data) => {
        if(data.status = 200){
          this.states = data.data
        }else{
          const initialState = {
            title: "Error",
            content: data.message,
            action: "/dr-krishi-non-technical-harvest-confirm-list"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        }
      })
  }



  SearchRegion(){
    this.region = undefined;
    this.district = undefined;
    this.village = undefined;
    this.regions = [];
    this.districts = [];
    this.villages = [];


    if(this.state != undefined){
      this.searchRegionData();
    }

    this.datatableElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.ajax.reload()
    });

  }


  SearchDistrict(){
    this.district = undefined;
    this.village = undefined;
    this.districts = [];
    this.villages = [];

    if(this.region != undefined){
      this.searchDistrictsData();
    }

    this.datatableElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.ajax.reload()
    });
  }


  SearchVillage(){
    this.village = undefined;
    this.villages = [];

    if(this.district != undefined){
      this.searchVillagesData();
    }

    this.datatableElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.ajax.reload()
    });
  }



  SearchCombinedData(){
    this.datatableElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.ajax.reload()
    });
  }


  modalClose(){
    this.modalRef.hide();
    this.farmerCalling.controls['callingstatus'].setValue("10")

  }

  //OnCall Method

  public onCall(callingstatus: TemplateRef<any>, primarynumber, secondarynumber, taskid, type) {
    if (type == "Registration") {
      this.primary = primarynumber;
      this.secondary = secondarynumber;
      this.farmerCalling.controls['taskid'].setValue(taskid);
      // added for calling status list - Pranay
      this.getCallingStatus();
      this.modalRef = this.modalService.show(callingstatus, { backdrop: 'static', keyboard: false });
    } else if (type == "Verification") {
      this.router.navigate(['/dr-krishi-technical-form/' + taskid])
    }
    else if (type == "Incomplete Monitoring") {
      this.router.navigate(['/dr-krishi-incomplete-data/' + taskid])
    }
    else if (type == "Incomplete Verification") {
      this.router.navigate(['/dr-krishi-incomplete-data/' + taskid])
    }

  }

  //Global Search Filter

  searchTableData() {
    var table = $('#dr-krishi-non-technical').DataTable();
    $('#searchText').on( 'keyup', function () {
      // table.search( this.value ).draw();
    } );
  }

  //Clear Filter
  clearFilter(){
    if(this.state != undefined || this.region != undefined || this.district != undefined || this.village != undefined || this.searchText.length != 0){
      this.state = undefined;
      this.region = undefined;
      this.district = undefined;
      this.village = undefined;
      this.searchText = undefined;
      this.getStates();
      var table = $('#dr-krishi-non-technical').DataTable();
      table.search('').draw();
    }

    sessionStorage.removeItem("state");
    sessionStorage.removeItem("region");
    sessionStorage.removeItem("district");
    sessionStorage.removeItem("village");
    sessionStorage.removeItem("searchText");

  }


  searchRegionData(){

    this.harvestConfirmationListService.getCCTCNonTechnicalRegion(this.state).subscribe(
      (data) => {
        if(data.status = 200){
          this.regions = data.data
        }else{
          const initialState = {
            title: "Error",
            content: data.message,
            action: "/dr-krishi-non-technical-harvest-confirm-list"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        }
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/dr-krishi-non-technical-harvest-confirm-list"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }


  searchDistrictsData() {
    this.harvestConfirmationListService.getCCTCNonTechnicalDistricts(this.region).subscribe(
      (data) => {
        if(data.status = 200){
          this.districts = data.data
        }else{
          const initialState = {
            title: "Error",
            content: data.message,
            action: "/dr-krishi-non-technical-harvest-confirm-list"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        }
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/dr-krishi-non-technical-harvest-confirm-list"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });

  }


  searchVillagesData() {
    this.harvestConfirmationListService.getCCTCNonTechnicalVillages(this.district).subscribe(
      (data) => {
        if(data.status = 200){
          this.villages = data.data
        }else{
          const initialState = {
            title: "Error",
            content: data.message,
            action: "/dr-krishi-non-technical-harvest-confirm-list"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        }
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/dr-krishi-non-technical-harvest-confirm-list"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }

  // added for calling status list - Pranay
  getCallingStatus() {
    this.harvestConfirmationListService.getCallingStatusList().subscribe(
      (data) => {
        if(data.status = 200) {
          this.callingStatusList = data.data;
        } else {
          const initialState = {
            title: "Error",
            content: data.message,
            action: "/dr-krishi-non-technical-harvest-confirm-list"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        }
      })
  }
}


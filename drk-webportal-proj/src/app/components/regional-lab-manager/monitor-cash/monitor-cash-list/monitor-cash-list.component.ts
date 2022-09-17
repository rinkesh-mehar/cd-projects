import { Component, OnInit } from '@angular/core';
import {Router, NavigationEnd} from "@angular/router";
import{FormGroup, FormBuilder, Validators} from "@angular/forms"
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { SuccessModalComponent } from '../../../modal-components/success-modal/success-modal.component';
import { FLSNameModel, viewDetailsModel } from './monitor-cash-list.model';
import { MonitorCashService } from './monitor-cash.service';
import{ ErrorMessages} from '../../../form-validation-messages';
declare var $, datatable;

@Component({
  selector: 'app-monitor-cash-list',
  templateUrl: './monitor-cash-list.component.html',
  styleUrls: ['./monitor-cash-list.component.less']
})
export class MonitorCashListComponent implements OnInit {

  modalRef: BsModalRef;
  public monitorcash: FLSNameModel[];
  public monitorviewDetails: viewDetailsModel[];
  public search:string;
  public loggedInUser = {} as any;
  namemobilesearch: FormGroup;
  submitted = false;

  //Error Messages
  public textboxerrormessage: string;
  public dropdownerrormessage: string;
  public checkboxerrormessage: string;
  public invalidvalueerrormessage: string;
  public multiselecterrormessage: string;
  public calndererrormessage: string;
  public httperrorresponsemessages: string;

  constructor(
    private monitorCashService: MonitorCashService,
    private router: Router,
    private modalService: BsModalService,
    private formBuilder: FormBuilder, 
  ) { }

  ngOnInit() {

    //Error Messages
    this.textboxerrormessage = ErrorMessages.textboxError;
    this.dropdownerrormessage = ErrorMessages.dropdownError;
    this.checkboxerrormessage = ErrorMessages.checkboxError;
    this.invalidvalueerrormessage = ErrorMessages.invalidvalueError;
    this.multiselecterrormessage = ErrorMessages.multiselectError;
    this.calndererrormessage = ErrorMessages.calnderError;
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

    this.loggedInUser = JSON.parse(localStorage.getItem("userData"));
    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0)
    });
    this.datatable();

    this.namemobilesearch = this.formBuilder.group({
      namemobile: ['', [Validators.required, Validators.pattern("^[A-Za-z0-9]*[A-Za-z0-9][A-Za-z0-9 ]*$")]]
    });
    
  }
  close() {
    this.modalRef.hide();
  }
  unTag() {
    const initialState = {
      title: "Success",
      content: "Hardware untagged from FLS Name or ID",
      action: "/monitor-cash-list"
    };
    this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
  }

  datatable() {
    setTimeout(function () {
      $('#monitor-cash-table').DataTable({
        "bPaginate": true,
        "bInfo": true,
        "searching": false,
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

  receiveModal(flsId) {

    this.monitorCashService.setRecived(flsId,this.loggedInUser.userId).subscribe(
      (data) => {
      if(data == true){
        const initialState = {
          title: "Success",
          content:"You have successfully received",
          action: "/monitor-cash-list"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
      } else{
        const initialState = {
          title: "Error",
          content: 'Error occurred during receiving proccess, To continue please contact system admin',
          action: "/monitor-cash-list"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
      }   
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/monitor-cash-list"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
    
  }


 public viewDetails(viewDetailsModal,flsId) {
  this.monitorCashService.getFLSMonitorCashList(flsId).subscribe(
    (data) => {
      this.monitorviewDetails = data;
      const initialState = {
        heading: "View Details",
        action:"/monitor-cash-list"
      };
      this.modalRef = this.modalService.show(viewDetailsModal, { initialState, backdrop: 'static', keyboard: false, class: 'viewdetails-popup' });
      
    }, (err) => {
      const initialState = {
        title: "Error",
        content: this.httperrorresponsemessages,
        action: "/monitor-cash-list"
      };
      this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
      return;
    });

  
  }


  get f() { return this.namemobilesearch.controls; }

  public searchData(){
    this.submitted = true;
    if (!this.namemobilesearch.invalid) {
      $("#monitor-cash-table").dataTable().fnDestroy();
      this.monitorCashService.searchData(this.loggedInUser.userId,this.search).subscribe(
        (data) => {
          this.monitorcash = data;
          this.datatable();
        }, (err) => {
          const initialState = {
            title: "Error",
            content: this.httperrorresponsemessages,
            action: "/monitor-cash-list"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        });
    }else{
      return;
    }
    
  }

  public showAllDetails(){

  }


}

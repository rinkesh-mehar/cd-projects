import { Component, OnInit } from '@angular/core';
import {Router, NavigationEnd} from "@angular/router";
import{FormGroup, FormBuilder, Validators} from "@angular/forms"
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { SuccessModalComponent } from '../../../modal-components/success-modal/success-modal.component';
import { RightsListModel, ViewRightListModel } from './rights-list.model';
import { RightsListService } from './rights-list.service';
import{ ErrorMessages} from '../../../form-validation-messages';
declare var $, datatable;

@Component({
  selector: 'app-rights-list',
  templateUrl: './rights-list.component.html',
  styleUrls: ['./rights-list.component.less']
})

export class RightsListComponent implements OnInit {

  modalRef: BsModalRef;
  public rightslist: RightsListModel[];
  public viewrightlist: ViewRightListModel[];
  public searchcaseId:string;
  public loggedInUser = {} as any;
  caseidsearch: FormGroup;
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
    private rightslistservice: RightsListService,
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
    
    this.datatable();

    this.caseidsearch = this.formBuilder.group({
      caseid: ['', [Validators.required]],
    });
    
  }

 
  datatable() {
    setTimeout(function () {
      $('#rights-list-table, #view-rights-list-table').DataTable({
        "bPaginate": true,
        "bInfo": true,
        "searching": false,
        "lengthChange": true,
        columnDefs: [{
          orderable: false,
          targets: [0]
        }],
        lengthMenu: [
            [ -1 ],
            [ 'All' ]
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


  get f() { return this.caseidsearch.controls; }

  public searchData(){
    this.submitted = true;
    if (!this.caseidsearch.invalid) {
      $("#rights-list-table").dataTable().fnDestroy();
      $("#view-rights-list-table").dataTable().fnDestroy();
      this.rightslistservice.searchData(this.searchcaseId).subscribe(
        (data) => {
          this.rightslist = data.rightsList;
          this.viewrightlist = data.viewRightList;

          
          for(var i = 0; i<this.rightslist.length; i++) {
            if(this.rightslist[i].deliveryDateTime != null){
              var date = new Date(this.rightslist[i].deliveryDateTime),
              month = '' + (date.getMonth() + 1),
              day = '' + date.getDate(),
              year = date.getFullYear(),
              hour = date.getHours(),
              minute = date.getMinutes(),
              seconds = date.getSeconds(),
              ampm = hour >= 12 ? 'PM' : 'AM',
              hour = hour % 12,
              hour = hour ? hour : 12; 
          
              var formatteddate =  [day + "-" + month + "-" + year + " " + hour + ":" + minute + " " + ampm];
            
              this.rightslist[i].deliveryDateTime = formatteddate;
            }


            if(this.rightslist[i].recordDateTime != null){
              var date = new Date(this.rightslist[i].recordDateTime),
              month = '' + (date.getMonth() + 1),
              day = '' + date.getDate(),
              year = date.getFullYear(),
              hour = date.getHours(),
              minute = date.getMinutes(),
              seconds = date.getSeconds(),
              ampm = hour >= 12 ? 'PM' : 'AM',
              hour = hour % 12,
              hour = hour ? hour : 12; 
          
              var formatteddate =  [day + "-" + month + "-" + year + " " + hour + ":" + minute + " " + ampm];
            
              this.rightslist[i].recordDateTime = formatteddate;
            }


            if(this.rightslist[i].statusReceivingDate != null){
              var date = new Date(this.rightslist[i].statusReceivingDate),
              month = '' + (date.getMonth() + 1),
              day = '' + date.getDate(),
              year = date.getFullYear(),
              hour = date.getHours(),
              minute = date.getMinutes(),
              seconds = date.getSeconds(),
              ampm = hour >= 12 ? 'PM' : 'AM',
              hour = hour % 12,
              hour = hour ? hour : 12; 
          
              var formatteddate =  [day + "-" + month + "-" + year + " " + hour + ":" + minute + " " + ampm];
                    
              this.rightslist[i].statusReceivingDate = formatteddate;
            }

          }
          
         
          

          this.datatable();
        }, (err) => {
          const initialState = {
            title: "Error",
            content: this.httperrorresponsemessages,
            action: "/rights-list"
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


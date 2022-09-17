import { Component, OnInit } from '@angular/core';
import {Router, NavigationEnd} from "@angular/router";
import{FormGroup, FormBuilder, Validators} from "@angular/forms"
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { SuccessModalComponent } from '../../../modal-components/success-modal/success-modal.component';
import { ManageHarwareModel } from './manage-hardware.model';
import { ManageHardwareService } from './manage-hardware.service';
import{ ErrorMessages} from '../../../form-validation-messages';
declare var $, datatable;

@Component({
  selector: 'app-manage-hardware-list',
  templateUrl: './manage-hardware-list.component.html',
  styleUrls: ['./manage-hardware-list.component.less']
})
export class ManageHardwareListComponent implements OnInit {

  modalRef: BsModalRef;
  public  managehardware: ManageHarwareModel[];
  public userId;
  public searchKeyward = '';
  namemobilesearch: FormGroup;
  submitted = false;
  public reverseDate: string;
  //Error Messages
  public textboxerrormessage: string;
  public dropdownerrormessage: string;
  public checkboxerrormessage: string;
  public invalidvalueerrormessage: string;
  public multiselecterrormessage: string;
  public calndererrormessage: string;
  public httperrorresponsemessages: string;

  constructor(
    private managehardwareservice: ManageHardwareService,
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

    this.namemobilesearch = this.formBuilder.group({
      namemobile: ['', [Validators.pattern("^[A-Za-z0-9]*[A-Za-z0-9][A-Za-z0-9 ]*$")]]
    });

    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0)
    });
    this.userId = localStorage.getItem('loginUserid');
    this.manageHardwareList();
  }

  unTag(id) {
    let data = {"id":id, "userId":this.userId};
    this.managehardwareservice.unTag(data).subscribe(
      (data) => {
        if(data.statusCode == 200) {
          const initialState = {
            title: "Success",
            content: "Hardware untagged successfully",
            action: "/manage-hardware-list"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        } else {
          const initialState = {
            title: "Error",
            content: 'Error occurred during getting list of manage hardware, To continue please contact system admin',
            action: "/manage-hardware-list"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        }
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/manage-hardware-list"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }

  datatable() {
    setTimeout(function () {
      $('#categorized-samples').DataTable({
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
    }, 0);

  }

  get f() { return this.namemobilesearch.controls; }
  
  public search() {
    this.submitted = true;
    if (!this.namemobilesearch.invalid) {
      $("#categorized-samples").dataTable().fnDestroy();
      this.manageHardwareList();
    }else{
      return;
    }
    

  }

  public manageHardwareList() {
    this.managehardwareservice.getManageHardwarelist(this.userId, this.searchKeyward).subscribe(
      (data) => {
        if(data.statusCode == 200) {
          this.managehardware = data.data;

          for(let i =0; i< this.managehardware.length; i++) {
            var date = this.managehardware[i].date;
            this.managehardware[i].reverseDate = date.split("-").reverse().join("-");
          }

          $("#categorized-samples").dataTable().fnDestroy();
          this.datatable();

        } else {
          const initialState = {
            title: "Error",
            content: 'Error occurred during getting list of manage hardware, To continue please contact system admin',
            action: "/manage-hardware-list"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        }

      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/manage-hardware-list"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });

  }


}

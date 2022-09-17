import { Component, OnInit } from '@angular/core';
import {Router, NavigationEnd} from "@angular/router";
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { SuccessModalComponent } from '../../../modal-components/success-modal/success-modal.component';
import { VehicleMasterModel } from './vehicle-master.model';
import { VehicleMasterService } from './vehicle-master.service';
import { ErrorMessages } from '../../../form-validation-messages';
declare var $, datatable;


@Component({
  selector: 'app-vehicle-list',
  templateUrl: './vehicle-list.component.html',
  styleUrls: ['./vehicle-list.component.less']
})
export class VehicleListComponent implements OnInit {

  modalRef: BsModalRef;
  public loggedInUser = {} as any;
  public vehiclemaster: VehicleMasterModel[];
  public Active;
  public Inactive;
  public userId;
  public searchKeyward: number;
  public httperrorresponsemessages: string;

  constructor(
    private vehiclemasterservice: VehicleMasterService,
    private router: Router,
    private modalService: BsModalService,
  ) { }

  ngOnInit() {

    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;
    this.searchKeyward = 1;

    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0)
    });

    this.loggedInUser = JSON.parse(localStorage.getItem("userData"));
    this.userId = localStorage.getItem('loginUserid');
    this.vehicleMasterList();

  }

  datatable() {
    setTimeout(function () {
      $('#vehicle-master').DataTable({
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

  public vehicleMasterList() {
    this.vehiclemasterservice.getVehicleMaster(this.userId, this.searchKeyward).subscribe(
      (data) => {
        this.vehiclemaster = data;
        this.datatable();

      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/vehicle-master"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }


  public searchData(){
    $("#vehicle-master").dataTable().fnDestroy();
    this.vehicleMasterList();
   // this.datatable();

  }


}
import { Component, OnInit } from '@angular/core';

import { ListOfUsersService } from './list-of-users.service';
import {Router} from "@angular/router";
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import {DeleteModalComponent} from '../../modal-components/delete-modal/delete-modal.component';
import {AssignRoleModalComponent} from '../../modal-components/assign-role-modal/assign-role-modal.component';
import { SuccessModalComponent } from '../../modal-components/success-modal/success-modal.component';
import { ErrorMessages } from '../../form-validation-messages';

declare var $,datatable;

@Component({
  selector: 'app-list-of-users',
  templateUrl: './list-of-users.component.html',
  styleUrls: ['./list-of-users.component.less']
})
export class ListOfUsersComponent implements OnInit {

  lis_of_users: Object;
  submitted: boolean = false;
  modalRef: BsModalRef;
  constructor(
    private userService: ListOfUsersService,
    private modalService: BsModalService,private router: Router
   
  ) {
    
   }

  public responseMessage: object = {};
  public enableChangeRole: boolean = true;
  public enableAddUser: boolean = true;
  public enableEdit: boolean = true;
  public enableDeactivate: boolean = true;
  public enableActivate: boolean = true;
  public enableView: boolean = true;
  public showButton: boolean = true ;
  public httperrorresponsemessages: string;

  public userIdArray: any [];  
  public listOfChangeRoles: any [];
  useridselected: string;
  expression: string;
  selectedRow: boolean = false;
  //totalRecords : BigInteger;

  ngOnInit() {

    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

      this.userService.userList({"userId": localStorage.getItem("loginUserid")}).subscribe(
        (data) => {
            this.responseMessage = data;
            $("#list-of-user-table").dataTable().fnDestroy();
            this.datatable();
       
            if ( this.responseMessage.hasOwnProperty("status") ) {
              this.lis_of_users = data.data;   
            }else if ( this.responseMessage.hasOwnProperty("error") ) {                            
            }                    
        }, (err) => {
          const initialState = {
            title: "Error",
            content: this.httperrorresponsemessages,
            action: "/list-of-users"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
      });

    if (localStorage.getItem("loginRoleName") === 'System Admin') {
      this.enableAddUser = false;
    }else if (localStorage.getItem("loginRoleName") === 'Regional Lab Manager') {
      this.showButton = ! this.showButton;
    }
    

  }


  delete(){
  
      const initialState = {
        heading:"Delete",
        content: 'Do You want to delete the user?'
        
      };
      this.modalRef = this.modalService.show(DeleteModalComponent, {initialState, backdrop : 'static', keyboard : false});
      

  }

  activate(){
      const initialState = {
        heading:"Activate",
        content: 'Do You want to activate the user?',
        action : '/list-of-users'
      };
      this.modalRef = this.modalService.show(DeleteModalComponent, {initialState,  class: 'activate-in', backdrop : 'static', keyboard : false});  
  }


  deactivate(){
    const initialState = {
      heading:"Deactivate",
      content: 'Do You want to deactivate the user?',
      action : '/list-of-users'
    };
    this.modalRef = this.modalService.show(DeleteModalComponent, {initialState,  class: 'deactivate-in', backdrop : 'static', keyboard : false});
  }

  assignRole(){

    const initialState = {
      heading:"Change Role",
    };
    this.modalRef = this.modalService.show(AssignRoleModalComponent, {initialState, backdrop : 'static', keyboard : false});
  }
  
  view() {
    this.router.navigate(['/view-user-details'])
  }
  editUser() {
    this.router.navigate(['/edit-user'])
  }

  clickselected(id) {

    this.useridselected = id.user_id;
    localStorage.setItem("useridselected",id.user_id);


    if(localStorage.getItem("loginRoleName") === "System Admin" ) {
      this.enableDeactivate = false;
      this.enableActivate = false;
      this.enableEdit = false;
      this.enableView = false;
    }else if(localStorage.getItem("loginRoleName") === "Regional Lab Manager"){
      this.enableView = false;
    }

    this.userService.changeRoles({ "userId": id.user_id }).subscribe(data => {
      this.listOfChangeRoles = data;
      
      if( this.listOfChangeRoles.length != 0 ) {
        this.enableChangeRole = false;
      }else{
        this.enableChangeRole = true;
      }

    });




  }
  
  setClickedRow(index){
    this.selectedRow = index;    
  }
  
  datatable(){
    
    setTimeout(function(){ 
      $('#list-of-user-table').DataTable({
        "bPaginate": true,
        "bInfo": true,
        "searching": false,
        "lengthChange": true,
        lengthMenu: [
            [ 5, 10, 15, -1 ],
            [ '5', '10', '15', 'All' ]
        ],
        order: [[ 1, 'asc' ]],
        "dom": "<'row'<'col-sm-12'tr>>" +
          "<'table-footer'<'table-length'l><'table-info'i><'table-pagination'p>>",

        "fnDrawCallback": function(oSettings) {
            if ($('table.table-bordered.dataTable td').hasClass("dataTables_empty")) {
               $(".paginate_button.active").hide();
            }
        },
      });
      $.fn.dataTable.ext.errMode = 'none';
    }, 40);
}


}

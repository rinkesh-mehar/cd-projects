import { Component, OnInit } from '@angular/core';
import { ListOfUsersService } from '../list-of-users/list-of-users.service';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ViewUserModel } from './view-user-model'
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import {Router, NavigationEnd} from "@angular/router";
import { SuccessModalComponent } from '../../modal-components/success-modal/success-modal.component';
import { ErrorMessages } from '../../form-validation-messages';

@Component({
  selector: 'app-view-details',
  templateUrl: './view-details.component.html',
  styleUrls: ['./view-details.component.less']
})
export class ViewDetailsComponent implements OnInit {

  public viewUserModel  = {} as ViewUserModel;
  modalRef: BsModalRef;
  responseMessage: object = {} ;
  firstName: string =  "test";
  viewUser: FormGroup;
  public showButton: boolean = true ;
  public showComment: boolean = true ;
  public httperrorresponsemessages: string;


  constructor(
    private userService: ListOfUsersService,
    private formBuilder: FormBuilder, 
    private modalService: BsModalService,
    private router: Router
  ) { }

  ngOnInit() {

    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

    //Back to top functionality
    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0)
    });

    if (localStorage.getItem("loginRoleName") === 'Regional Lab Manager') {
      this.showButton = ! this.showButton;
    }


    this.viewUser = this.formBuilder.group({
      firstname: ['', [Validators.required]],
      middlename: ['', [Validators.required]],
      lastname: ['', [Validators.required]],
      email: ['', [Validators.pattern(/^[A-Z0-9!#$%*+=?^_`{|}~-]+(?:\.[a-z0-9!#$%*+=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/i)]],
      mobilenumber: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
      role: ['', [Validators.required]],
      state: ['', [Validators.required]],
      region: ['', [Validators.required]],
      reportingRoleName: ['', [Validators.required]],
      reporting: ['', [Validators.required]],
      userid: [''],
      status: [''],
      createdby: [''],
      datetime: [''],
  });
  
  this.display();

  }

  display() {

    this.userService.viewUser({"userId" : localStorage.getItem("useridselected")  }).subscribe(
      (data) => {
        this.viewUserModel = data;

        if( this.viewUserModel.comments == ""){
         this.showComment = false ;

        }
        
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/view-user-details"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
    });

  }


  ok () {
    this.router.navigate(['/list-of-users'])    
  }

  

}

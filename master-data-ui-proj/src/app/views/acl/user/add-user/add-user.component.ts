import { Component, OnInit, Input, Output, EventEmitter, SimpleChanges, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import custom validator to validate that password and confirm password fields match
import { MustMatch } from '../../../_helpers/must-match';
import { RoleService } from '../../services/role.service';
import { UserService } from '../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SuccessModalComponent } from '../../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent implements OnInit {

  // @Input('mode') mode: string;
  // @Input('user') user: any;
  // @Output('formSubmitEvent') formSubmitEvent = new EventEmitter<any>();

  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;


  userForm: FormGroup;
  // operationMode: string;
  rolelist: any;
  editUserId: any;
  mode: any = "add";
  user: any;


  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  constructor(private router: Router,public formBuilder: FormBuilder, private roleService: RoleService, public userService: UserService, private actRoute: ActivatedRoute, ) {


    this.createUserForm();


  }
  getChanges() {
    console.log(this.userForm.value)
  }

  createUserForm() {
    this.userForm = this.formBuilder.group({
      id: [],
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      roleId: ['', Validators.required],
      status: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, {
        validator: MustMatch('password', 'confirmPassword')
      })
  }

  ngOnInit() {
    this.loadAllRole();
    this.editUserId = this.actRoute.snapshot.paramMap.get('id');

    if (this.editUserId) {

      this.mode = "edit";
      this.userService.GetUser(this.editUserId).subscribe(data => {
        this.user = data;
        this.userForm = this.formBuilder.group({
          id: [],
          name: ['', Validators.required],
          email: ['', [Validators.required, Validators.email]],
          roleId: ['', Validators.required],
          status: ['', Validators.required]
        })

        this.userForm.patchValue(this.user);
      })


    }

  }



  // ngOnChanges(changes: SimpleChanges) {
  //   if (this.mode && this.mode == "edit") {

  //     this.userForm = this.formBuilder.group({
  //       id: [],
  //       name: ['', Validators.required],
  //       email: ['', [Validators.required, Validators.email]],
  //       roleId: ['', Validators.required],
  //       status: ['', Validators.required]
  //     })

  //     this.userForm.patchValue(changes.user.currentValue);
  //   }

  // }

  loadAllRole() {
    return this.roleService.GetAllRole().subscribe((data: any) => {
      this.rolelist = data;
    }, err => {
      alert(err)
    })
  }

  submitUserForm() {
    for (let controller in this.userForm.controls) {
      this.userForm.get(controller).markAsTouched();
    }
    if (this.userForm.invalid) {
      return;
    }
    if (this.mode == "edit") {
      this.updateUser();
    } else {
      this.addUser();
    }
  }
  addUser() {
    this.userService.CreateUser(this.userForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if(res) {
        // this.user = {};
        //     this.mode = "add";
        //     this.userForm.reset();
        // this.isSuccess = res.success;
        if (res.success) {
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
      // if (res) {
      //   this.isSuccess = res.success;
      //   if (res.success) {
      //     this._statusMsg = res.message;
      //     this.user = {};
      //     this.mode = "add";
      //     this.userForm.reset();
      //     setTimeout(() => {
      //       this.isSubmitted = false;
      //       this.isSuccess = false
      //     }, 4000)

      //   } else {
      //     this._statusMsg = res.error;
      //   }
      // }
    }, err => {
      console.log(err);
    })
  }
  updateUser() {
    this.userService.UpdateUser(this.userForm.value.id, this.userForm.value).subscribe((res: any) => {
      this.isSubmitted = true;
      if(res) {
      
        this.isSuccess = res.success;
        if (res.success) {
              // this.user = {};
              // this.mode = "add";
              // this.userForm.reset();
    
              // this.createUserForm();
          this.successModal.showModal('SUCCESS', res.message, '');
        } else {
        
          this.errorModal.showModal('ERROR', res.error, '');
        }
      }
      // if (res) {
      //   this.isSuccess = res.success;
      //   if (res.success) {
      //     this._statusMsg = res.message;
      //     this.user = {};
      //     this.mode = "add";
      //     this.userForm.reset();

      //     this.createUserForm();

      //     setTimeout(() => {
      //       this.isSubmitted = false;
      //       this.isSuccess = false
      //     }, 4000)
      //   } else {
      //     this._statusMsg = res.error;
      //   }
      // }
    }, err => {
      console.log(err);
    })
  }
  trimValue(formControl) { 
    formControl.setValue(formControl.value.trim()); 
  }  
  modalSuccess($event: any) {
    this.router.navigate(['/acl/user']);
  }
}

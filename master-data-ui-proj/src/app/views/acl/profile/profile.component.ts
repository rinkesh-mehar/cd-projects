import { Component, OnInit, NgZone } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from '../services/user.service';
import { AuthenticationService } from '../../services/authentication.service';
import { User } from '../Models/user';

// import custom validator to validate that password and confirm password fields match
import { MustMatch } from '../../_helpers/must-match';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  userList: any = [];
  updateUserForm: FormGroup;
  changePasswordForm: FormGroup;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  isSubmittedP: boolean = false;
  isSuccessP: boolean = false;
  _statusMsg : string;
  _statusMsgP: string;
  
  user: User;

  ngOnInit() {
   
  }

  constructor(
    private actRoute: ActivatedRoute,
    public userService: UserService,
    public fb: FormBuilder,
    private ngZone: NgZone,
    private router: Router,
    private authenticationService: AuthenticationService
  ) {
    
     this.user = authenticationService.currentUserValue;
     console.log("User:"+this.user.email);
     
     this.updateUserForm = this.fb.group({
      userName: [this.user.name, Validators.required],
      email: new FormControl({value: this.user.email, disabled: true}, Validators.required),
      role: new FormControl({value: this.user.role, disabled: true}, Validators.required),
      status: new FormControl({value: this.user.status, disabled: true}, Validators.required),
    })

     this.buildChangePasswordForm();


  }


  buildChangePasswordForm() {
    this.changePasswordForm = this.fb.group({
      id: ['', Validators.required],
      old_pass: ['',Validators.required],
      new_pass: ['',Validators.required],
      conf_pass: ['',Validators.required],
    },{
      validator: MustMatch('new_pass', 'conf_pass')
    })
  }

  get passwordForm (){
    return this.changePasswordForm.controls;
  }
  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => console.log('fired'));
  }

  submitUpdateForm(){ 

    for(let controller in this.updateUserForm.controls){
      this.updateUserForm.get(controller).markAsTouched();
    }
  
    if(this.updateUserForm.invalid){
      return;
    }

    // var id = this.actRoute.snapshot.paramMap.get('id');
    var id = this.user.id;
    this.userService.UpdateUserName(id, this.updateUserForm.value.userName).subscribe(res => {
      this.isSubmitted = true;
     
      if(res){
        this.isSuccess = res.success;
        if(res.success){
          this._statusMsg = res.message;
          this.updateUserForm.reset();

          this.delay(4000).then(any => {
              this.isSubmitted = false;
              this.isSuccess = false;
            });
        }else{
          this._statusMsg = res.error;
        }
      }
    })
  }


  submitPasswordForm(){

    // for(let controller in this.changePasswordForm.controls){
    //   this.changePasswordForm.get(controller).markAsTouched();
    // }
  
    // if(this.changePasswordForm.invalid){
    //   return;
    // }

    // var id = this.actRoute.snapshot.paramMap.get('id');
    console.log("OLDP: "+ this.changePasswordForm.value.old_pass);
    console.log("Password: "+ this.changePasswordForm.value.new_pass);
    
    var id = this.user.id;
    this.userService.ChangeProfilePassword(id, this.changePasswordForm.value.old_pass,this.changePasswordForm.value.new_pass).subscribe(res => {
      this.isSubmittedP = true;
     
      if(res){
        this.isSuccessP = res.success;
        if(res.success){
          this._statusMsgP = res.message;
          this.changePasswordForm.reset();

          this.delay(4000).then(any => {
              this.isSubmittedP = false;
              this.isSuccessP = false;
            });
        }else{
          this._statusMsgP = res.error;
        }
      }
    })

  }
}

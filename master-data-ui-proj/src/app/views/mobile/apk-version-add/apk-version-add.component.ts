import { Component, OnInit, ViewChild } from '@angular/core';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ApkVersionService } from '../services/apk-version.service';
import { ModalDirective } from 'ngx-bootstrap/modal';
declare var $: any;

@Component({
  selector: 'app-apk-version-add',
  templateUrl: './apk-version-add.component.html',
  styleUrls: ['./apk-version-add.component.css']
})
export class ApkVersionAddComponent implements OnInit {
  @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;
    @ViewChild('myModal') public myModal: ModalDirective;

    addApp: FormGroup;
    mode: string = 'add';
    editId: string;
    isSubmitted: boolean = false;
    isSuccess: boolean = false;
    keyStoreFile : any;
    apkUrl : any;
    encryptedKey : any;
    editobj : any;
    message: any;

  constructor(public formBuilder: FormBuilder, private actRoute: ActivatedRoute,
    public apkVersionService: ApkVersionService,  public router: Router) { }

  ngOnInit() {

    this.addApp = this.formBuilder.group({
      appName: ['', Validators.required],
      versionNumber: [''],
      versionName: [''],
      versionLog: [''],
      releaseDate: ['', Validators.required],
      apkUrl: [''],
      appUrl: [''],
      keyStoreFile: [''],
      encryptedKey: ['']
  });

     this.editId = this.actRoute.snapshot.paramMap.get('id');
 
        if (this.editId) {
         
            this.mode = 'edit';
            this.apkVersionService.getApkById(this.editId).subscribe(data => {
              this.editobj=data;
                //this.addApp.patchValue(data);

                this.addApp.patchValue({
                  appName:(data.appName)
                 });  
                 this.addApp.patchValue({
                  releaseDate:(data.releaseDate)
                 });
                 this.addApp.patchValue({
                  versionName:(data.versionName)
                 }); 
                this.addApp.patchValue({
                  versionNumber:(data.versionNumber)
                 });  
                 this.addApp.patchValue({
                  versionLog:(data.versionLog)
                 });
                 this.addApp.patchValue({
                  appUrl:(data.appUrl)
                 });
                 this.addApp.patchValue({
                  apkUrl:(data.apkUrl)
                 }); 
            });
        }
  }

  add() {
    return this.apkVersionService.addNewApp(this.addApp.value,this.apkUrl,this.keyStoreFile,this.encryptedKey).subscribe( res => {
        this.isSubmitted = true;
        
        console.log('res',res)
        if (res.success) {
          this.message = res.message;
          $("#msg").text(this.message);
          $("#myModal").show();
          $('.modal-header').addClass('bg-green');
          $('.modal-title').text('SUCCESS');
          // this.successModal.showModal('SUCCESS', res.message, '');
        } else {
          this.message = res.error;
          $("#msg").text(this.message);
          $("#myModal").show();
          $('.modal-header, #onBtn').addClass('bg-red');
          $('.modal-title').text('ERROR!');
            // this.errorModal.showModal('ERROR', res.error, '');
        }
    });
}

modalClose(){
  $('#myModal').hide();
  this.modalSuccess('msg');
}

update() {
  return this.apkVersionService.updateNewApp(this.editId, this.addApp.value,this.apkUrl,this.keyStoreFile,this.encryptedKey).subscribe( res => {
    this.isSubmitted = true;
    console.log('res',res)
      if (res) {
          this.isSuccess = res.success;
          if (res.success) {
            
              this.successModal.showModal('SUCCESS', res.message, '');
          } else {
              this.errorModal.showModal('ERROR', res.error, '');
          }
      }
  });
}

downloadApkUrl(){
  $("#apkUrlModal").show();
  $('#myModal').hide();
}

hideModal(){
  $("#apkUrlModal").hide();
}

uploadApkUrl(element) {
  var file: File = element.target.files[0];
  this.apkUrl = file;
}

uploadKeyStoreFile(element) {
  var file: File = element.target.files[0];
  this.keyStoreFile = file;
}

uploadEncryptedKey(element) {
  var file: File = element.target.files[0];
  this.encryptedKey = file;
}

  submitForm() {
    for (const controller in this.addApp.controls) {
        this.addApp.get(controller).markAllAsTouched();
    }

      if (this.addApp.invalid) {
          return;
      }
    if (this.mode == 'add') {
        this.add();
    } else {
        this.update();
    }
}

modalSuccess($event: any) {

  this.router.navigate(['/mobile/apk-version']);
}

}

import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd} from "@angular/router";
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { SuccessModalComponent } from '../../../modal-components/success-modal/success-modal.component';
import { sampleDiagnosis } from './sample-diagnosis-approval-list.model';
import { sampleDiagnosisService } from './sample-diagnosis-approval-list.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import{ ErrorMessages} from '../../../form-validation-messages';

@Component({
  selector: 'app-sample-diagnosis-approval-list',
  templateUrl: './sample-diagnosis-approval-list.component.html',
  styleUrls: ['./sample-diagnosis-approval-list.component.less']
})
export class SampleDiagnosisApprovalListComponent implements OnInit {

  public sample_data: sampleDiagnosis[] = [];
  public diagnosis = {} as sampleDiagnosis;
  modalRef: BsModalRef;
  maxDate: Date;
  p:number;
  userId = null;
  //minDate: Date;
  DiagnosisFilterForm: FormGroup;
  barCode: FormGroup;
  submitted = false;
  barcode:string = '';
  public searchmessage: string;
  public nodatamessages: string;
  public showError: boolean = false;
  public showNoDataMessage: boolean = false;
  public showSearchMessage: boolean = false;
  public textboxerrormessage: string;
  public calndererrormessage: string;
  public invalidvalueerrormessage: string;
  public httperrorresponsemessages: string;
  public searchcode:string = ''
  public searchStatus:number = 1;
  public dateError: boolean = false;

  constructor(
    private modalService: BsModalService,
    private router: Router,
    private SampleDiagnosisService: sampleDiagnosisService,
    private formBuilder: FormBuilder,
  ) { 
    
    this.maxDate = new Date();
    this.maxDate.setDate(this.maxDate.getDate() + 0);
     //this.minDate = new Date();
    //this.minDate.setDate(this.minDate.getDate() - 7);
  }

  ngOnInit() {
    localStorage.removeItem("diagnosisStatus");
    //Error Messages
    this.textboxerrormessage = ErrorMessages.textboxError;
    this.invalidvalueerrormessage = ErrorMessages.invalidvalueError;
    this.calndererrormessage = ErrorMessages.calnderError;
    this.searchmessage = ErrorMessages.searchMessages;
    this.nodatamessages = ErrorMessages.noDataMessages;
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;
    this.userId = localStorage.getItem('loginUserid');
    
    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0)
    });
    let param = {taskstatus:'pending'}
    this.getsampleDiagnosisService(param);

    this.diagnosis.taskstatus = '1';

    
    if(this.getsampleDiagnosisService.length == 0){
      this.showNoDataMessage = true;
    }else{
      this.showNoDataMessage = false;
    }

    this.DiagnosisFilterForm = this.formBuilder.group({
      taskdate: [''],
      status: [''],
    });

    this.barCode = this.formBuilder.group({
      barcode: ['', [Validators.pattern("^[A-Za-z0-9]*[A-Za-z0-9][A-Za-z0-9]*$")]]

    });
  }

   // convenience getter for easy access to form fields
   get f() { return this.DiagnosisFilterForm.controls, this.barCode.controls }

  FilterDiagnosis(){
    this.submitted = true;
    
    let obj = this.DiagnosisFilterForm.value; 
    this.searchStatus = obj.status;
    this.barcode = '';
    this.searchcode = '';
    
    if(obj.taskdate == '' && this.searchStatus != 1){
      this.dateError = true;
      return;
    }else{
      this.dateError = false;
    }

    let param = {taskstatus:obj.status == 1? 'pending':obj.status == 2?'approved':'reassigned'};
     if(obj.taskdate != null  && obj.taskdate != '' && obj.taskdate.length == 2) {
      
      if((obj.taskdate[1].getTime() - obj.taskdate[0].getTime()) > 518400000) {

        this.showError = true;
        return;
      }else{
        this.showError = false;
      }
        param['startdate'] = ("0" + obj.taskdate[0].getDate()).slice(-2)+'/'+("0" + (obj.taskdate[0].getMonth()+1)).slice(-2)+'/'+obj.taskdate[0].getFullYear();
        param['enddate'] = ("0" + obj.taskdate[1].getDate()).slice(-2)+'/'+("0" + (obj.taskdate[1].getMonth()+1)).slice(-2)+'/'+obj.taskdate[1].getFullYear();
    } 


    this.getsampleDiagnosisService(param);

  }


  public searchBarcode() {
    this.submitted = true;
    // stop here if form is invalid
    if (!this.barCode.invalid) {
      this.searchcode = '';
      if(this.barcode.trim() != '') {
        this.searchcode = this.barcode.trim();
        let param = {barcode:this.barcode};
        this.getsampleDiagnosisService(param)
      }
    }else{
      return;
    }
    
  }

  public getsampleDiagnosisService(param) {
    this.SampleDiagnosisService.getsampleDiagnosisService(param).subscribe(
      (data) => {
        if(data.statusCode == 200) {
          this.sample_data = data.data;
          this.p = 1;
        } else {
          //error message modal
          this.sample_data = []
        }

        if(this.sample_data.length == 0){
              this.showSearchMessage = true;
        }else{
          this.showSearchMessage = false;
       }

      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/sample-diagnosis-approval-list"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }

  public assigned(taskId, status){
    localStorage.setItem("diagnosisStatus",status)
    this.router.navigate(["/diagnose/"+taskId])
  }

  public selection(taskId, status){
    let data = {"id":taskId, "assigneeId":this.userId}
    this.SampleDiagnosisService.assignTask(data).subscribe(
      (data) => {
        localStorage.setItem("diagnosisStatus",status)
        this.router.navigate(["/diagnose/"+taskId])
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/sample-diagnosis-approval-list"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }

}

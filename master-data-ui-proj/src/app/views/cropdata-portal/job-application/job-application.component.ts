import { Component, OnInit, ViewChild } from '@angular/core';
import { Sort } from '@angular/material';
import { ConfirmationMadalComponent } from '../../global/confirmation-madal/confirmation-madal.component';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { globalConstants } from '../../global/globalConstants';
import { SuccessModalComponent } from '../../global/success-modal/success-modal.component';
import { UserRightsService } from '../../services/user-rights.service';
import { PageJobApplication } from '../models/PageJobApplication';
import { JobApplicationService } from '../services/job-application.service';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-job-application',
  templateUrl: './job-application.component.html',
  styleUrls: ['./job-application.component.css']
})
export class JobApplicationComponent implements OnInit {

  @ViewChild('confirmModal') public confirmModal: ConfirmationMadalComponent;
  @ViewChild('successModal') public successModal: SuccessModalComponent;
  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  pageJobApplication: PageJobApplication;
  searchText: any = '';
  selectedPage: number = 1;
  maxSize =10;
  jobApplicationId: number = 0;

  isSubmitted: boolean = false;
  isSuccess: boolean = false;
  _statusMsg: string;

  jobApplicationStatus;

  jobApplicationList: any = [];
  jobApplicationForId: any;

  recordsPerPage: number = 10;
  records: any = [];

  isForm: FormGroup;

  constructor(private jobApplicationService: JobApplicationService, private userRightsService: UserRightsService,
    public fb: FormBuilder) { }

  ngOnInit() {
    this.records = ['20', '50', '100', '200', '250'];
    this.interviewScheculeFormControl();

    this.getJobApplicationList(0);
    this.jobApplicationStatus = globalConstants;
  }

  interviewScheculeFormControl() {
    this.isForm = this.fb.group({
      interviewScheduledDate: [''],
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.isForm.controls){

      this.isForm.get(controller).markAsTouched();

    }
    
    console.log("this.isForm.value.interviewScheduledDate : " + this.isForm.value.interviewScheduledDate);

    if(this.isForm.value.interviewScheduledDate == null || this.isForm.value.interviewScheduledDate == ''){
      this.errorModal.showModal('ERROR', 'Interview Schedule Date & Time  is required. Please Select Interview Schedule Date & Time', '');
      return;
    }

    this.scheduleInterviewWithDate(this.jobApplicationForId, this.isForm.value.interviewScheduledDate);
  }

  getJobApplicationList(page: number): void {
    this.jobApplicationService.getPageJobApplicationList(page, this.recordsPerPage, this.searchText)
        .subscribe(page => this.pageJobApplication = page);
  }

  loadData(event: any) {
    console.log('pages ', event.target.value);
    this.recordsPerPage = event.target.value || 10;
    this.jobApplicationService.getPageJobApplicationList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
      .subscribe(page => this.pageJobApplication = page);
  }

  onSelect(page: number): void {
    console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getJobApplicationList(page);
  }

searchJobApplication() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getJobApplicationList(this.selectedPage - 1);
}

sortData(sort: Sort) {
  const data = this.pageJobApplication.content.slice();
  if (!sort.active || sort.direction == '') {
    this.pageJobApplication.content = data;
    return;
  }

  function compare(firstValue, secondValue, isAsc: boolean) {
    return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
  }

  this.pageJobApplication.content = data.sort((firstValue, secondValue) => {
    const isAsc = sort.direction == 'asc';
    switch (sort.active) {
      case 'jobApplicationId':
        return compare(+firstValue.jobApplicationId, +secondValue.jobApplicationId, isAsc);
      case 'applicantName':
        return compare(firstValue.applicantName, secondValue.applicantName, isAsc);
        case 'mobile':
        return compare(firstValue.mobile, secondValue.mobile, isAsc);
        case 'platform':
        return compare(firstValue.platform, secondValue.platform, isAsc);
        case 'department':
        return compare(firstValue.department, secondValue.department, isAsc);
        case 'position':
        return compare(firstValue.position, secondValue.position, isAsc);
        // case 'education':
        // return compare(firstValue.education, secondValue.education, isAsc);
        case 'createdAt':
        return compare(firstValue.createdAt, secondValue.createdAt, isAsc);
        case 'location':
        return compare(firstValue.location, secondValue.location, isAsc);
        case 'status':
        return compare(firstValue.status, secondValue.status, isAsc);
        case 'interviewScheduledDate':
        return compare(firstValue.interviewScheduledDate, secondValue.interviewScheduledDate, isAsc);
      default:
        return 0;
    }
  });
}

shortlist(data, i) {

  data.index = i;
  data.flag = "shortlist";
  this.confirmModal.showModal(globalConstants.shortlistDataTitle, globalConstants.shortlistDataMsg + " '" + data.applicantName + "'", data);

}

hold(data, i) {

  data.index = i;
  data.flag = "hold";
  this.confirmModal.showModal(globalConstants.holdDataTitle, globalConstants.holdDataMsg + " '" + data.applicantName + "'", data);

}

schedule(data, i){
  data.index = i;
  data.flag = "schedule";
  this.jobApplicationForId = data;
}

scheduleInterviewWithDate(data,interviewScheduledDate) {
  // console.log(interviewScheduleDate);
  // console.log(data.jobApplicationId);
  // console.log("index : " + data.index);
  // console.log(data.flag);
  
  data.interviewScheduledDate = interviewScheduledDate;
  // console.log(data.interviewScheduledDate);
  this.confirmModal.showModal(globalConstants.interviewScheduleDataTitle, globalConstants.interviewScheduleDataMsg + " '" 
  + data.applicantName + "'", data);

}

selection(data,i) {

  data.index = i;
  data.flag = "selection";
  this.confirmModal.showModal(globalConstants.selectDataTitle, globalConstants.selectDataMsg + " '" + data.applicantName + "'", data);

}

reject(data, i) {

  data.index = i;
  data.flag = "reject";
  this.confirmModal.showModal(globalConstants.rejectDataTitle, globalConstants.rejectDataMsg + " '" + data.applicantName + "'", data);

}

shortlistApplication(event) {
  return this.jobApplicationService.shortlistApplication(event.jobApplicationId).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.jobApplicationList.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}

holdApplication(event) {
  return this.jobApplicationService.holdApplication(event.jobApplicationId).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.jobApplicationList.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}

scheduleInterview(event) {
  return this.jobApplicationService.scheduleInterview(event.jobApplicationId, event.interviewScheduledDate).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.jobApplicationList.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}

interviewSelection(event) {
  return this.jobApplicationService.interviewSelection(event.jobApplicationId).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.jobApplicationList.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}

rejectApplication(event) {
  return this.jobApplicationService.rejectApplication(event.jobApplicationId).subscribe(res => {
    if (res) {
      this.isSuccess = res.success;
      if (res.success) {
        this.jobApplicationList.splice(event.index, 1);
        this.successModal.showModal('SUCCESS', res.message, '');
      } else {
        this.errorModal.showModal('ERROR', res.error, '');
      }
    }
  }, err => {
    this.errorModal.showModal('ERROR', err.error, '');
  });
}

modalConfirmation(event) {
  console.log(event);
  if (event) {
    this.isSubmitted = true;

    if (event.flag == "shortlist") {
      this.shortlistApplication(event);
    } else if (event.flag == "hold") {
      this.holdApplication(event);
    } else if (event.flag == "schedule") {
      this.scheduleInterview(event);
    }else if (event.flag == "selection") {
      this.interviewSelection(event);
    }else if (event.flag == "reject") {
      this.rejectApplication(event);
    }
  }
}

modalSuccess($event: any) {
  // this.ngOnInit();
  // this.selectedPage = 1;


  if(this.selectedPage >= 2){
  this.getJobApplicationList(this.selectedPage - 1);
  this.jobApplicationStatus = globalConstants;
  }else{
  this.ngOnInit();
  }
document.getElementById('interviewScheduleModal').click();
}

onClose(){
  // console.log("inside close...");
  if(this.selectedPage >= 2){
    this.getJobApplicationList(this.selectedPage - 1);
    this.jobApplicationStatus = globalConstants;
    }else{
    this.ngOnInit();
    }
}

}

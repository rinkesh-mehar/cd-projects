import { globalConstants } from './../../global/globalConstants';
import { Sort } from '@angular/material';
// import { ModalDirective } from 'ngx-bootstrap/modal';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit, Input} from '@angular/core';
import { PageLeaveDetail } from '../models/PageLeaveDetail';
import * as moment from 'moment';

@Component({
  selector: 'app-leave-detail',
  templateUrl: './leave-detail.component.html',
  styleUrls: ['./leave-detail.component.scss']
})
export class LeaveDetailComponent implements OnInit {

  // @ViewChild('applyLeaveModal') public leaveModal: ModalDirective;
  applyleaveForm: FormGroup;
  
  @Input() max: any;
  previous = new Date();
  pageLeaveDetail: PageLeaveDetail;
  searchText: any = '';
  leaveDetailStatus;
  selectedPage: number = 1;
  records: any = [];
  maxSize =10;
  selectedAll: any;

  dt : string;
  tagName = 'Manager  HR  TeamLead';
  

  leaveDetailList: any = [{leaveType:"Earned",totalLeave:6,takenLeave:3,remainingLeave:3},{ leaveType:"Casual",totalLeave:12,takenLeave:5,remainingLeave:7},
                        { leaveType:"Sick",totalLeave:6,takenLeave:3,remainingLeave:3}];
  balanceLeaveList: any = [{leaveByMonth:"Earned",leaveInJan:1,leaveInFeb:1,leaveInMarch:1,leaveInApr:2,leaveInMay:2,leaveInJune:2,leaveInJuly:3,leaveInAug:2.5},{leaveByMonth:"Taken",leaveInJan:2,leaveInFeb:1,leaveInMarch:0,leaveInApr:0,leaveInMay:1,leaveInJune:1,leaveInJuly:0,leaveInAug:1.5},{leaveByMonth:"Unpaid",leaveInJan:1,leaveInFeb:0,leaveInMarch:0,leaveInApr:0,leaveInMay:0,leaveInJune:0,leaveInJuly:0,leaveInAug:0},]
  leaveList: any = [{ id:1,name:"Casual leave"},{ id:2,name:"Sick leave"},{ id:3,name:"Earned leave"}];
  taglist: any = [{id:1,name:"HR"},{ id:2,name:"Manager"},{ id:3,name:"Team Lead"}];
  reasonList: any =[{ id:1,name:"There is some emergency with your home"},{ id:2,name:"Have a Family Emergency"},{ id:3,name:"Someone in your family is Sick"},{ id:4,name:"Have a Doctor’s Appointment"},{ id:5,name:"Have to take part in a religious ceremony"}];
  constructor(public fb: FormBuilder) { }

  ngOnInit(): void {

    this.applyleaveFG();
    this.records = ['20', '50', '100', '200', '250'];
    this.getLeaveDetailPagenatedList(0);  
    this.leaveDetailStatus = globalConstants;
    
  }


  
  getLeaveDetailPagenatedList(page: number): void {
    // this.agriQualityParameterService.getQualityParameterPagenatedList(page, this.recordsPerPage, this.searchText)
    //     .subscribe(page => this.pageAgriQualityParameter = page);
 
    this.pageLeaveDetail = {
      "content": [
      {
      "appliedOn": "12-04-2021",
      "leavePeriod": "12-04-2021 TO 13-04-2021",
      "leaveType": "Earned leave",
      "approvedBy": "Nilanjan Sinha(HR),Samik Chattopadhyay(Manager)",
      "status": "Approved"
      },
      {
        "appliedOn": "15-04-2021",
        "leavePeriod": "15-04-2021 TO 20-04-2021",
        "leaveType": "Casual leave",
        "approvedBy": "Nilanjan Sinha(HR),Samik Chattopadhyay(Manager)",
        "status": "Approved"
      },
      {
        "appliedOn": "20-05-2021",
        "leavePeriod": "20-05-2021 TO 22-05-2021",
        "leaveType": "Earned leave",
        "approvedBy": "Nilanjan Sinha(HR),Samik Chattopadhyay(Manager)",
        "status": "Rejected"
      },
      {
        "appliedOn": "31-05-2021",
        "leavePeriod": "12-04-2021 TO 15-04-2021",
        "leaveType": "Sick leave",
        "approvedBy": "Nilanjan Sinha(HR),Samik Chattopadhyay(Manager)",
        "status": "Rejected"
      },
      {
      "appliedOn": "04-06-2021",
      "leavePeriod": "04-06-2021 TO 06-06-2021",
      "leaveType": "Sick leave",
      "approvedBy": "Nilanjan Sinha(HR),Samik Chattopadhyay(Manager)",
      "status": "Approved"
      },
      {
        "appliedOn": "18-06-2021",
        "leavePeriod": "18-06-2021 TO 20-06-2021",
        "leaveType": "Sick leave",
        "approvedBy": "Nilanjan Sinha(HR),Samik Chattopadhyay(Manager)",
        "status": "Approved"
      },
      {
          "appliedOn": "30-07-2021",
          "leavePeriod": "30-07-2021 TO 1-08-2021",
          "leaveType": "Casual leave",
          "approvedBy": "Nilanjan Sinha(HR),Samik Chattopadhyay(Manager)",
          "status": "Pending"
      }
        
      ],
      "pageable": {
      "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
      },
      "offset": 0,
      "pageSize": 10,
      "pageNumber": 0,
      "paged": true,
      "unpaged": false
      },
      "totalPages": 4,
      "totalElements": 38,
      "last": false,
      "number": 0,
      "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
      },
      "size": 10,
      "first": true,
      "numberOfElements": 10,
      "empty": false
      }
}

onSelect(page: number): void {
    
    // console.log('selected page : ' + page);
    this.selectedPage = page;
    this.getLeaveDetailPagenatedList(page);
}


  dateClass = (dt: Date) => {

 
  };

  applyleaveFG()
  {
    this.applyleaveForm = this.fb.group({
      reasonTypes: ['', Validators.required],
      startDate: [moment(), Validators.required],
      endDate: [moment()],
      leaveTypes: ['', Validators.required],
      // tagto : [ ],
      description: ['',Validators.required]
      
    })
  }

  submitForm()
  {
    console.log("inside submitForm");
  
    for(let controller in this.applyleaveForm.controls){

      this.applyleaveForm.get(controller).markAsTouched();

    }
   
    if(this.applyleaveForm.invalid){
      console.log("inside 1st if");
      return;
    }
    alert("startDate :" + this.applyleaveForm.value.startDate  + "\nEndDate :" + this.applyleaveForm.value.endDate  + "\nLeaveTypes :" + this.applyleaveForm.value.leaveTypes + "\nDescription :" + this.applyleaveForm.value.description )
    this.applyleaveFG();
  }
  
  
  trimValue(formControl) { 
    // console.log("inside trim");
    formControl.setValue(formControl.value.trim()); 
   }
  
   
  //  onReset(){
  //   this.applyleaveFG();
  // }

 

  // showApplyleaveModal() {
  
  //     this.leaveModal.show();
  // }

  sortData(sort: Sort) {
    const data = this.pageLeaveDetail.content.slice();
    if (!sort.active || sort.direction == '') {
      this.pageLeaveDetail.content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageLeaveDetail.content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case 'appliedOn':
          return compare(firstValue.appliedOn, secondValue.appliedOn, isAsc);
        case 'leaveType':
          return compare(firstValue.leaveType, secondValue.leaveType, isAsc);
          case 'approvedBy':
          return compare(firstValue.approvedBy, secondValue.approvedBy, isAsc);
          case globalConstants.STATUS:
          return compare(firstValue.status, secondValue.status, isAsc);
        default:
          return 0;
      }
    });
  }

  selectAll() {
    for (var i = 0; i < this.taglist.length; i++) {
      this.taglist[i].selected = this.selectedAll;
    }
  }


}

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { JobApplication } from '../../models/JobApplication';
import { JobApplicationService } from '../../services/job-application.service';

@Component({
  selector: 'app-view-job-application',
  templateUrl: './view-job-application.component.html',
  styleUrls: ['./view-job-application.component.css']
})
export class ViewJobApplicationComponent implements OnInit {

  jobApplicationId: string;
  viewJobApplicationForm: FormGroup;
  jobApplication: JobApplication;
  fileExtention: string = '';

  constructor(public formBuilder: FormBuilder, private actRoute: ActivatedRoute,
    public jobApplicationService: JobApplicationService,  public router: Router) { }

  ngOnInit() {


    this.jobApplicationId = this.actRoute.snapshot.paramMap.get('id');

    if (this.jobApplicationId) {
      this.jobApplicationService.getJobApplicationById(this.jobApplicationId).subscribe(data => {
        console.log('data------------>',data)
        this.jobApplication=data;
      });
  }

}

onClickCancel(){
  this.router.navigate(['/cropdata-portal/job-application-list']);
}

}

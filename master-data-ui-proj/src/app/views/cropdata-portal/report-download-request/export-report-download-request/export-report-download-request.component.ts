import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { ReportDownloadRequestService } from '../../services/report-download-request.service';

@Component({
  selector: 'app-export-report-download-request',
  templateUrl: './export-report-download-request.component.html',
  styleUrls: ['./export-report-download-request.component.css']
})
export class ExportReportDownloadRequestComponent implements OnInit {

  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  listOfPageNo : any = [];

  exportReportDownloadRequestForm: FormGroup;

  constructor(public fb: FormBuilder, private reportDownloadRequestService: ReportDownloadRequestService) { }

  ngOnInit() {

      this.reportDownloadRequestService.listOfPageNoOfReportDownloadRequest().subscribe(pageNos => {
      console.log("pageNos : " + pageNos);
       this.listOfPageNo = pageNos;
   });
   this.exportReportDownloadRequest();
  }

  exportReportDownloadRequest() {
    this.exportReportDownloadRequestForm = this.fb.group({
      pageNo: ['', Validators.required]
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.exportReportDownloadRequestForm.controls){

      this.exportReportDownloadRequestForm.get(controller).markAsTouched();

    }

console.log("pageNo : " + this.exportReportDownloadRequestForm.value.pageNo);

    if(this.exportReportDownloadRequestForm.value.pageNo === ""){
      this.errorModal.showModal('ERROR', 'Page Number is required. Please select the Page Number.', '');
      return;
      }

    this.download();

  }

  download(){
  console.log("Inside download");
  console.log(this.exportReportDownloadRequestForm.value.pageNo);
  this.reportDownloadRequestService.exportToExcelReportDownloadRequest(this.exportReportDownloadRequestForm.value.pageNo)
  .subscribe(data=>{
    var downloadURL = window.URL.createObjectURL(data);
    var link = document.createElement('a');
    link.href = downloadURL;
    link.download = "Report Request.xlsx";
    link.click();
    });
  }

}

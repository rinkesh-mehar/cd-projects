import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorModalComponent } from '../../../global/error-modal/error-modal.component';
import { BuyerPreApplicationService } from '../../services/buyer-pre-application.service';

@Component({
  selector: 'app-export-buyer-pre-application',
  templateUrl: './export-buyer-pre-application.component.html',
  styleUrls: ['./export-buyer-pre-application.component.css']
})
export class ExportBuyerPreApplicationComponent implements OnInit {

  @ViewChild('errorModal') public errorModal: ErrorModalComponent;

  listOfPageNo : any = [];

  exportBuyerPreApplicationForm: FormGroup;

  blob;

  constructor(public fb: FormBuilder, private buyerPreApplicationService: BuyerPreApplicationService) { }

  ngOnInit() {

      this.buyerPreApplicationService.listOfPageNoOfPreApplication().subscribe(pageNos => {
      console.log("pageNos : " + pageNos);
       this.listOfPageNo = pageNos;
   });
   this.exportBuyerPreApplication();
  }

  exportBuyerPreApplication() {
    this.exportBuyerPreApplicationForm = this.fb.group({
      pageNo: ['', Validators.required]
    })
  }

  submitForm() {
    console.log("inside submitForm");
    for(let controller in this.exportBuyerPreApplicationForm.controls){

      this.exportBuyerPreApplicationForm.get(controller).markAsTouched();

    }

    if(this.exportBuyerPreApplicationForm.value.pageNo === ""){
      this.errorModal.showModal('ERROR', 'Page Number is required. Please select the Page Number.', '');
      return;
      }

    this.download();

  }

  download(){
  console.log("Inside download");
  console.log(this.exportBuyerPreApplicationForm.value.pageNo);
  this.buyerPreApplicationService.exportToExcelPreApplication(this.exportBuyerPreApplicationForm.value.pageNo).subscribe(data=>{
    // const blob = new Blob([data], { type: '.xlsx' });
    // const url= window.URL.createObjectURL(blob);
    // window.open(url);

    // this.blob = new Blob([data], {type: 'application/pdf'});

    var downloadURL = window.URL.createObjectURL(data);
    var link = document.createElement('a');
    link.href = downloadURL;
    link.download = "Agriota Buyer Registration.xlsx";
    link.click();


  });
}

}

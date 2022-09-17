import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorModalComponent } from '../../global/error-modal/error-modal.component';
import { RlService } from '../service/rl.service';

@Component({
  selector: 'app-export-rl',
  templateUrl: './export-rl.component.html',
  styleUrls: ['./export-rl.component.scss']
})
export class ExportRlComponent implements OnInit {

  @ViewChild('errorModal') public errorModal: ErrorModalComponent;
  constructor(private rlService: RlService, private formBuilder: FormBuilder) { }

  listOfPageNo: any = [];
  exportRLGroup: FormGroup;

  ngOnInit(): void {
    this.rlService.listOfPageNumbers().subscribe(pages => {
      console.log("pages : " + pages);
      this.listOfPageNo = pages;
    });
    this.exportRL();
  }

  exportRL() {
    this.exportRLGroup = this.formBuilder.group({
      pageNo: ['', Validators.required]
    });
  }

  submitForm() {
    for (const controller in this.exportRLGroup.controls) {
      this.exportRLGroup.get(controller).markAllAsTouched();
    }
    if (this.exportRLGroup.invalid) {
      return;
    }
    this.download();
  }

  download() {
    this.rlService.exportRlDataToExcel(this.exportRLGroup.value.pageNo)
    .subscribe(data=>{
      var downloadURL = window.URL.createObjectURL(data);
      var link = document.createElement('a');
      link.href = downloadURL;
      link.download = "RL-Employee-Data.xlsx";
      link.click();
      });
  }

}

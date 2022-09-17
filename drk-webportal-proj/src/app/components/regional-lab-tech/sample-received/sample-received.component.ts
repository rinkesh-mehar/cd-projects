import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, NavigationEnd } from "@angular/router";
import { RltSampleReceivedService } from './sample-received.service';
import { RltSampleReceivedModel, RtlResponse, BarcodeRequestModel } from './sample-received.model';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { SuccessModalComponent } from '../../modal-components/success-modal/success-modal.component';
import { ErrorMessages } from '../../form-validation-messages';
declare var $, c3, datatable;

@Component({
  selector: 'app-sample-received',
  templateUrl: './sample-received.component.html',
  styleUrls: ['./sample-received.component.less']
})
export class RltSampleReceivedComponent implements OnInit {


  barcodeInput:string = '';
  barcodeInfo:RltSampleReceivedModel = null;
  submitted = false;
  modalRef: BsModalRef;
  barCode: FormGroup;
  public rltsamplereceivedscandata: RltSampleReceivedModel[];
  public rltsamplereceivedlist: RltSampleReceivedModel[];
  public barcodeRequest: BarcodeRequestModel;
  public logedInUser = {} as any;
  public reverseDate: string;
  public textboxerrormessage: string;
  public invalidvalueerrormessage: string;
  public httperrorresponsemessages: string;
  cropType: string = '';
  sellerType: string = '';

  constructor(
    private router: Router,
    private rltsamplereceivedservice: RltSampleReceivedService,
    private modalService: BsModalService,
    private formBuilder: FormBuilder,
  ) {

  }

  ngOnInit() {

    //Error Messages
    this.textboxerrormessage = ErrorMessages.textboxError;
    this.invalidvalueerrormessage = ErrorMessages.invalidvalueError;
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0)
    });
    this.logedInUser = JSON.parse(localStorage.getItem("userData"));
    this.rltSampleReceivedList();

    this.barCode = this.formBuilder.group({
      barcode: ['', [Validators.pattern("^[A-Za-z0-9]*[A-Za-z0-9][A-Za-z0-9]*$")]]

    });

 }

  // convenience getter for easy access to form fields
  get f() { return this.barCode.controls; }


  onSubmit() {


  }


  getBarcodeData() {
    let cropTypeMap = new Map([
      [1, "Few Months"],
      [2, "Few Weeks"],
      [3, "Harvested"],
      [3, "Warehoused"]
    ]);
  let sellerTypeMap = new Map([
      [1, "Farmer"],
      [2, "Merchant"],
      [3, "Merchant"]
]);

    this.submitted = true;
    // stop here if form is invalid
    if (!this.barCode.invalid) {
      this.rltsamplereceivedservice.getRltSampleData(this.barcodeInput,+localStorage.getItem("loginUserid")).subscribe((res:RtlResponse) => {
        if(res.statusCode == 200) {
            this.cropType = cropTypeMap.get(res.data.cropType);
            this.sellerType = sellerTypeMap.get(res.data.sellerType);
            this.barcodeInfo = res.data;
            this.barcodeInput = ''
        } else {
          const initialState = {
            title: "Error",
            content: res.message,
            action: "/rlt-samples"
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        }
      }, err => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/rlt-samples"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });

      })
    }else{
      return
    }
  }

  saveBarcodeData() {
    this.barcodeRequest={taskId :this.barcodeInfo.taskId,userId :+localStorage.getItem("loginUserid"),date :new Date(this.barcodeInfo.receivedDate.replace( /(\d{2})-(\d{2})-(\d{4})/, "$2/$1/$3"))}
    this.rltsamplereceivedservice.saveRltSampleData(this.barcodeRequest).subscribe(res => {
      const initialState = {
        title: "Success",
        content: res.message,
        action: "/rlt-samples"
      };
      $("#categorized-samples").dataTable().fnDestroy();
      this.datatable();
      this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
    }, err => {
      const initialState = {
        title: "Error",
        content: this.httperrorresponsemessages,
        action: "/rlt-samples"
      };
      this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
    })
  }


  datatable() {
    setTimeout(function () {
      $('#categorized-samples').DataTable({
        "bPaginate": true,
        "bInfo": true,
        "searching": false,
        "lengthChange": true,
        columnDefs: [{
          orderable: false,
          targets: [0]
        }],
        lengthMenu: [
            [ 5, 10, 15, -1 ],
            [ '5', '10', '15', 'All' ]
        ],
        "dom": "<'row'<'col-sm-12'tr>>" +
          "<'table-footer'<'table-length'l><'table-info'i><'table-pagination'p>>",
          "fnDrawCallback": function(oSettings) {
            if ($('table.table-bordered.dataTable td').hasClass("dataTables_empty")) {
               $(".paginate_button.active").hide();
            }
        }
      });
      $.fn.dataTable.ext.errMode = 'none';
    }, 40);

  }



  public rltSampleReceivedList() {

    this.rltsamplereceivedservice.getRltSampleReceivedList(+localStorage.getItem("loginUserid")).subscribe(
      (data) => {
        this.rltsamplereceivedlist = data;

        for(let i =0; i< this.rltsamplereceivedlist.length; i++) {
          var date = this.rltsamplereceivedlist[i].receivedDate;
          this.rltsamplereceivedlist[i].reverseDate = date.split("-").reverse().join("-");
        }

        $("#dr-krishi-technical").dataTable().fnDestroy();
        this.datatable();

      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/rlt-samples"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }
 public reassignModal (confirmModal,id) {
    const user = {
      heading: "Confirmation",
      content: "Do you want to reassign for more sample is required to be collected for analyzing the sample. Task will be generated for CCTC ?"+id
    };

    this.modalRef = this.modalService.show(confirmModal, { initialState: user });
  }

  public reassignTask(id){
    this.modalRef.hide()
    this.rltsamplereceivedservice.reassignTask(id,this.logedInUser.userId).subscribe(
      (data) => {
        const initialState = {
          title: "Success",
          content: 'Task reassigned',
          action: "/rlt-samples"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/rlt-samples"
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }

}

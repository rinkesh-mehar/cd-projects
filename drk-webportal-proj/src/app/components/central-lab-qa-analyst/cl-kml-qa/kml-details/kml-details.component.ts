import {Component, OnInit, Pipe, PipeTransform} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { SuccessModalComponent } from '../../../modal-components/success-modal/success-modal.component';
import {CoordinatesList, KmlDetails} from './kml-details.model';
import { KmlDetailsService } from './kml-details.service';
import { Router, NavigationEnd, ActivatedRoute } from "@angular/router";
import { ErrorMessages } from '../../../form-validation-messages';
import { DomSanitizer } from '@angular/platform-browser';
import {DatepickerDateCustomClasses} from 'ngx-bootstrap/datepicker';
declare var $;
declare function myMetho(k):any
declare function getAreaAndLength(k):any

@Pipe({ name: 'safe' })
export class SafePipe implements PipeTransform {
  constructor(private sanitizer: DomSanitizer) { }
  transform(url) {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }
}

@Component({
  selector: 'app-kml-details',
  templateUrl: './kml-details.component.html',
  styleUrls: ['./kml-details.component.less']
})
export class KmlDetailsComponent implements OnInit {
  public kml_details: KmlDetails;
  public updated_kml_details: KmlDetails;
  public coordinatesList: CoordinatesList[] = [];
  modalRef: BsModalRef;
  uploadFile: FormGroup;
  fileInfo: string;
  public taskId :string;
  fileType: string;
  extension:string;
  public fileFormate:boolean = false;
  public loggedInUser = {} as any;
  public httperrorresponsemessages: string;

  public polygon: string;
  private kmlUrl: string;

  constructor(
    private router: Router,
    private modalService: BsModalService,
    private kmlDetailsService: KmlDetailsService,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
  ) { }

  ngOnInit() {
    //Error Messages
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

    this.loggedInUser = JSON.parse(localStorage.getItem("userData"));
    const routeparam = this.route.params.subscribe(params => {

      this.taskId = params['taskId'];


    });

    this.uploadFile = this.formBuilder.group({
      uploadfile: ['', [Validators.required]],
    });

    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0)
    });
    this.getKmlDetailsService();
  }

  afterLoad(kmlUrl) {

    this.polygon = 'https://cdtuatts.blob.core.windows.net/cdtuat/drk/kml-file-data/showPolygon_temp.html';
    this.polygon = this.polygon + '?kmlURL=' + kmlUrl;
    const randNo = this.getRandomInt(1000);
    this.polygon = this.polygon + '?rnd=' + randNo;
  }

  getRandomInt(max) {
    return Math.floor(Math.random() * max);
  }

  get f() { return this.uploadFile.controls; }

  onUpdate() {
    // stop here if form is invalid
    if (this.uploadFile.invalid) {
      this.fileFormate = true;
      return;
    }else{
      this.kmlDetailsService.submitKmlDetailsService(this.taskId, this.updated_kml_details.area, this.loggedInUser.userId).subscribe(
        (data) => {

          if(data.statusCode == "success"){
            const initialState = {
              title: "Success",
              content: data.message,
              action: "/kml-quality-assurance-list"
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });

          }else if(data.statusCode == "error"){
            const initialState = {
              title: "Error",
              content: data.message,
              action: "/kml-details/" + this.taskId
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
            return;
          }
        }, (err) => {
          const initialState = {
            title: "Error",
            content: this.httperrorresponsemessages,
            action: "/kml-details/" + this.taskId
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        });
    }
  }


  public getKmlDetailsService() {
    this.kmlDetailsService.getKmlDetailsService(this.taskId).subscribe(
      (data) => {
        if(data.statusCode == "success"){
          this.kml_details = data.data;
          this.kmlUrl = this.kml_details.fileName;
          console.log('this.kmlUrl 1', this.kmlUrl);
          this.afterLoad(this.kmlUrl);

        }else if(data.statusCode == "error"){
          const initialState = {
            title: "Error",
            content: data.message,
            action: "/kml-details/" + this.taskId
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        }
      }, (err) => {
        const initialState = {
          title: "Error",
          content: this.httperrorresponsemessages,
          action: "/kml-details/"+this.taskId
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }




  onFileSelect(input: HTMLInputElement): void {

    function formatBytes(bytes: number): string {
      const UNITS = ['Bytes', 'kB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
      const factor = 1024;
      let index = 0;

      while (bytes >= factor) {
        bytes /= factor;
        index++;
      }
      return `${parseFloat(bytes.toFixed(2))} ${UNITS[index]}`;

    }
    const file = input.files[0];


    const formData = new FormData();
    formData.append('kmlFile', file);
    formData.append('taskId', this.taskId);

    this.fileInfo = `${file.name} (${formatBytes(file.size)})`;


    this.extension = file.name.replace(/^.*\./, '');

    if(this.extension == 'kml'){
      this.fileFormate = false;
      this.kmlDetailsService.uploadKmlFile(formData).subscribe(
        (data) => {
          if(data.statusCode == "success"){
            this.updated_kml_details = data.data;
            this.kml_details.area = this.updated_kml_details.area;
            this.kmlUrl = this.updated_kml_details.kmlUrl;
            console.log('this.kmlUrl');
            this.afterLoad(this.kmlUrl);
          }else if(data.statusCode == "error"){
            const initialState = {
              title: "Error",
              content: data.message,
              action: "/kml-details/" + this.taskId
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
            return;
          }

        }, (err) => {
          const initialState = {
            title: "Error",
            content: this.httperrorresponsemessages,
            action: "/kml-details/"+this.taskId
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        });
    }else{
      this.fileFormate = true;
      return;
    }

  }

  public downloadFile(name){
    this.kmlDetailsService.downloadFile(name).subscribe(data=>{
      if ( data ) {
        var blob = new Blob([data], {type: "kml"});

        //const blob = new Blob([stage.elaboration], { type: "text/csv" });
        if(window.navigator.msSaveOrOpenBlob) //IE & Edge
        {
          //msSaveBlob only available for IE & Edge
          window.navigator.msSaveBlob(blob,name);
        }
        else //Chrome & FF
        {
          const url = window.URL.createObjectURL(blob);
          const anchor = document.createElement("a");
          anchor.href = url;
          anchor.download = name;
          document.body.appendChild(anchor); //For FF
          anchor.click();
          //It's better to remove the elem
          document.body.removeChild(anchor);
        }
      }})
  }

}

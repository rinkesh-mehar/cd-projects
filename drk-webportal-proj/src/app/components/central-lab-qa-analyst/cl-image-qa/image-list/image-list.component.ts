import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {
  BenchmarkedImageModel, CompleteTask,
  GeneralInfo,
  Health,
  RejectImage, SpotHealthModels,
  SpotStressModels,
  Stress,
  SubmitImages, TaskSpotModels
} from './image-list.model';
import {ClImageList} from './image-list.service';
import {SuccessModalComponent} from '../../../modal-components/success-modal/success-modal.component';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {ErrorMessages} from '../../../form-validation-messages';
import 'tui-image-editor/dist/svg/icon-a.svg';
import 'tui-image-editor/dist/svg/icon-b.svg';
import 'tui-image-editor/dist/svg/icon-c.svg';
import 'tui-image-editor/dist/svg/icon-d.svg';
import ImageEditor = require('tui-image-editor');
import {environment} from "../../../../../environments/environment";

declare var $, datatable;

@Component({
  selector: 'app-image-list',
  templateUrl: './image-list.component.html',
  styleUrls: ['./image-list.component.less']
})
export class ImageListComponent implements OnInit {

  modalRef: BsModalRef;
  p: any;
  public generalinfo: GeneralInfo;
  public spotStressModels: SpotStressModels[];
  public healthImages: Health[];
  public spotHealthModels: SpotHealthModels;
  public taskSpotModel: TaskSpotModels[];
  public imageList = {} as BenchmarkedImageModel;
  public submitbenchmarkimage = {} as SubmitImages;
  public rejectBenchmarkedImage = {} as RejectImage;
  public completeTaskModel = {} as CompleteTask;
  public textboxError: string;
  public invalidvalueerrormessage: string;
  public httperrorresponsemessages: string;
  public btnDisabled = true;

  SERVER_URL = 'http://localhost:4200/upload';
  uploadForm: FormGroup;
  oneAtATime = true;
  submitted = false;
  shouldShow = false;
  imageId;
  imageName;
  taskId;
  qaId;
  commodityName;
  stateName;
  regionName;
  commodityId;
  stateId;
  regionId;
  instance: ImageEditor;
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private modalService: BsModalService,
    private clImageList: ClImageList,
    private formBuilder: FormBuilder,
    private httpClient: HttpClient,
    private bsModalRef: BsModalRef,
  ) { }
  // saveSpotDetails;
  ngOnInit() {

    // Error Messages
    this.textboxError = ErrorMessages.textboxError;
    this.invalidvalueerrormessage = ErrorMessages.invalidvalueError;
    this.httperrorresponsemessages = ErrorMessages.httpErrorResponseMessages;

    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0);

    });


    const routeparam = this.route.params.subscribe(params => {
      this.taskId = params.taskId;
      this.qaId = params.qaId;
      this.commodityName = params.commodityName;
      this.stateName = params.stateName;
      this.regionName = params.regionName;
      this.commodityId = params.commodityId;
      this.stateId = params.stateId;
      this.regionId = params.regionId;

    });

    this.getClImageListService(this.qaId, this.commodityId, this.stateId, this.regionId);

    this.uploadForm = this.formBuilder.group({
      profile: [''],
      comment: ['', [Validators.required, Validators.pattern('[^ ](.*|\n|\r|\r\n)*')]],
      id: [this.submitbenchmarkimage.id],
      imagename: [this.submitbenchmarkimage.imageName]
    });

  }



  /*public getClImageListService(qaId, commodityId, stateId, regionId) {
    this.clImageList.getTaskSpotList(commodityId, stateId, regionId).subscribe(
      (data) => {
        if (data.statusCode == 'success') {
          this.generalinfo = data.data;
          // this.spotStressModels = this.generalinfo.spotStressModels;
          this.taskSpotModel = this.generalinfo.taskSpotModels;
        } else if (data.statusCode == 'error') {
          const initialState = {
            title: 'Error',
            content: data.message,
            action: '/image-list/' + this.qaId + '/' + this.commodityId + '/' + this.stateId + '/' + this.regionId + '/' + this.commodityName + '/' + this.stateName + '/' + this.regionName
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        }
      }, (err) => {
        const initialState = {
          title: 'Error',
          content: this.httperrorresponsemessages,
          action: '/image-list/' + this.qaId + '/' + this.commodityId + '/' + this.stateId + '/' + this.regionId + '/' + this.commodityName + '/' + this.stateName + '/' + this.regionName
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }*/
  public getClImageListService(qaId, commodityId, stateId, regionId) {
    this.clImageList.getClImageListService(qaId, commodityId, stateId, regionId).subscribe(
      (data) => {
        if (data.statusCode == 'success') {
          this.generalinfo = data.data;
          this.taskSpotModel = this.generalinfo.taskSpotModels;
          // this.taskSpotModel = this.generalinfo.taskSpotModels;
        } else if (data.statusCode == 'error') {
          const initialState = {
            title: 'Error',
            content: data.message,
            action: '/image-list/' + this.qaId + '/' + this.commodityId + '/' + this.stateId + '/' + this.regionId + '/' + this.commodityName + '/' + this.stateName + '/' + this.regionName
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        }
      }, (err) => {
        const initialState = {
          title: 'Error',
          content: this.httperrorresponsemessages,
          action: '/image-list/' + this.qaId + '/' + this.commodityId + '/' + this.stateId + '/' + this.regionId + '/' + this.commodityName + '/' + this.stateName + '/' + this.regionName
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }

  /*public getFindStressDetails(spotId) {
    this.clImageList.getSpotStressDetails(spotId, this.commodityId).subscribe(
      (data) => {
        if (data.statusCode == 'success') {
          this.generalinfo = data.data;
          this.spotStressModels = this.generalinfo.stressModelList;
          alert(JSON.stringify(this.spotStressModels));
        } else if (data.statusCode == 'error') {
          const initialState = {
            title: 'Error',
            content: data.message,
            action: '/image-list/' + this.qaId + '/' + this.commodityId + '/' + this.stateId + '/' + this.regionId + '/' + this.commodityName + '/' + this.stateName + '/' + this.regionName
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        }
      }, (err) => {
        const initialState = {
          title: 'Error',
          content: this.httperrorresponsemessages,
          action: '/image-list/' + this.qaId + '/' + this.commodityId + '/' + this.stateId + '/' + this.regionId + '/' + this.commodityName + '/' + this.stateName + '/' + this.regionName
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }*/

  public getFindHealthDetails(spotId) {
    this.clImageList.getHealthDetails(spotId).subscribe(
      (data) => {
        if (data.statusCode == 'success') {
          // this.generalinfo = data.data;
          // this.healthImages = this.generalinfo.healthImageModelList;
          this.spotHealthModels = data.data;

        } else if (data.statusCode == 'error') {
          const initialState = {
            title: 'Error',
            content: data.message,
            action: '/image-list/' + this.qaId + '/' + this.commodityId + '/' + this.stateId + '/' + this.regionId + '/' + this.commodityName + '/' + this.stateName + '/' + this.regionName
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        }
      }, (err) => {
        const initialState = {
          title: 'Error',
          content: this.httperrorresponsemessages,
          action: '/image-list/' + this.qaId + '/' + this.commodityId + '/' + this.stateId + '/' + this.regionId + '/' + this.commodityName + '/' + this.stateName + '/' + this.regionName
        };
        this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
        return;
      });
  }

  public imageEditorModal(basepath, imagename, id) {
    // localStorage.setItem("basepath", basepath);
    // localStorage.setItem("imagename", imagename);
    this.imageName = imagename;
    this.imageId = id;
    const imgPath = imagename;
    this.submitbenchmarkimage.comment = '';
    this.instance = new ImageEditor(document.querySelector('#tui-image-editor'), {
      includeUI: {
        loadImage: {
          path: imgPath,
          name: imagename.split('/').pop(),
        },
        menu: ['shape', 'crop', 'filter', 'flip', 'rotate'],
        menuBarPosition: 'bottom',
        theme: {
          'menu.normalIcon.path': '../../../../../assets/img/svg/icon-d.svg',
          'menu.activeIcon.path': '../../../../../assets/img/svg/icon-b.svg',
          // @ts-ignore
          'menu.disabledIcon.path': '../../../../../assets/img/svg/icon-a.svg',
          'menu.hoverIcon.path': '../../../../../assets/img/svg/icon-c.svg',
          'submenu.normalIcon.path': '../../../../../assets/img/svg/icon-d.svg',
          'submenu.activeIcon.path': '../../../../../assets/img/svg/icon-c.svg',
        },
      },
      cssMaxWidth: 500,
      cssMaxHeight: 300,
      selectionStyle: {
        cornerSize: 20,
        rotatingPointOffset: 30
      }
    });
    $('body').addClass('modal-open');

  }
  toDataURL(url, callback) {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
      var reader = new FileReader();
      reader.onloadend = function () {
        callback(reader.result);
      }
      reader.readAsDataURL(xhr.response);
    };
    xhr.open('GET', url);
    xhr.responseType = 'blob';
    xhr.send();
  }

  get f() { return this.uploadForm.controls; }

  // basepath, imagename, id
  close() {
    this.uploadForm.controls.comment.clearValidators();
    this.uploadForm.controls.comment.updateValueAndValidity();
    $('body').removeClass('modal-open');
  }
  onAccept() {
    this.submitted = true;

    this.uploadForm.controls.comment.setValidators([Validators.required, Validators.pattern('[^ ](.*|\n|\r|\r\n)*')]);
    this.uploadForm.controls.comment.updateValueAndValidity();

    if (!this.uploadForm.invalid) {
      // this.submitbenchmarkimage.taskid = this.taskId;
      this.submitbenchmarkimage.userId = this.qaId;
      this.submitbenchmarkimage.imageName = this.imageName.split('/').pop();
      this.submitbenchmarkimage.id = this.imageId;
      this.submitbenchmarkimage.image = this.instance.toDataURL();
      this.submitbenchmarkimage.comment = this.submitbenchmarkimage.comment;
      this.submitbenchmarkimage.status = 'Verified';
      $('.image-editor-modal, .modal-backdrop').removeClass('show');
      $('body').removeClass('modal-open');

      this.clImageList.getsubmitBenchmarkImages(this.submitbenchmarkimage).subscribe(
        (data) => {
          if (data.statusCode == 'success') {
            const initialState = {
              title: 'Success',
              content: data.message,
              action: '/image-list/' + this.qaId + '/' + this.commodityId + '/' + this.stateId + '/' + this.regionId + '/' + this.commodityName + '/' + this.stateName + '/' + this.regionName
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
            return;
          } else if (data.statusCode == 'error') {
            const initialState = {
              title: 'Error',
              content: data.message,
              action: '/image-list/' + this.qaId + '/' + this.commodityId + '/' + this.stateId + '/' + this.regionId + '/' + this.commodityName + '/' + this.stateName + '/' + this.regionName
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
            return;
          }
        }, (err) => {
          const initialState = {
            title: 'Error',
            content: this.httperrorresponsemessages,
            action: '/image-list/' + this.qaId + '/' + this.commodityId + '/' + this.stateId + '/' + this.regionId + '/' + this.commodityName + '/' + this.stateName + '/' + this.regionName
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        });

    } else {
      return;
    }

  }

  onReject() {
    this.submitted = true;
    this.uploadForm.controls.comment.setValidators([Validators.required, Validators.pattern('[^ ](.*|\n|\r|\r\n)*')]);
    this.uploadForm.controls.comment.updateValueAndValidity();

    if (!this.uploadForm.invalid) {

      this.rejectBenchmarkedImage.userId = this.qaId;
      this.rejectBenchmarkedImage.benchmarkedImageId = this.imageId;
      this.rejectBenchmarkedImage.comment = this.submitbenchmarkimage.comment;
      this.rejectBenchmarkedImage.status = 'Rejected';
      $('.image-editor-modal, .modal-backdrop').removeClass('show');
      $('body').removeClass('modal-open');

      this.clImageList.rejectBenchmarkedImage(this.rejectBenchmarkedImage).subscribe(
        (data) => {
          if (data.statusCode == 'success') {
            const initialState = {
              title: 'Reject',
              content: data.message,
              action: '/image-list/' + this.qaId + '/' + this.commodityId + '/' + this.stateId + '/' + this.regionId + '/' + this.commodityName + '/' + this.stateName + '/' + this.regionName
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, class: '', backdrop: 'static', keyboard: false });
            return;
          } else if (data.statusCode == 'error') {
            const initialState = {
              title: 'Error',
              content: data.message,
              action: '/image-list/' + this.qaId + '/' + this.commodityId + '/' + this.stateId + '/' + this.regionId + '/' + this.commodityName + '/' + this.stateName + '/' + this.regionName
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
            return;
          }
        }, (err) => {
          const initialState = {
            title: 'Error',
            content: this.httperrorresponsemessages,
            action: '/image-list/' + this.qaId + '/' + this.commodityId + '/' + this.stateId + '/' + this.regionId + '/' + this.commodityName + '/' + this.stateName + '/' + this.regionName
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        });

    } else {
      return;
    }
  }

  public gettabl(event, spotId) {

    event.target;
    if (event.srcElement.innerText == 'Health Details') {
      // this.getFindHealthDetails(this.qaId, this.commodityId, this.stateId, this.regionId);
      this.getFindHealthDetails(spotId);
    }
    // else if (event.srcElement.innerText == 'Stress Details') {
    // this.getIrrigationDetails();
    // this.getFindStressDetails(spotId);
    // }

    // $(".rlt-tech-case-details .case-details-tab .nav.nav-tabs li.nav-item:nth-child(2)").click(function(){
    //   this.getCropInformation();
    // });
  }

  public completeTask(taskId){

    if (taskId != null) {
      this.completeTaskModel.taskId = taskId;
      this.completeTaskModel.userId = this.qaId;
      this.clImageList.completeTask(this.completeTaskModel).subscribe(
        (data) => {
          if (data.statusCode == 'success') {
            const initialState = {
              title: 'Completed',
              content: data.message,
              action: '/image-list/' + this.qaId + '/' + this.commodityId + '/' + this.stateId + '/' + this.regionId + '/' + this.commodityName + '/' + this.stateName + '/' + this.regionName
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, class: '', backdrop: 'static', keyboard: false });
            return;
          } else if (data.statusCode == 'error') {
            const initialState = {
              title: 'Error',
              content: data.message,
              action: '/image-list/' + this.qaId + '/' + this.commodityId + '/' + this.stateId + '/' + this.regionId + '/' + this.commodityName + '/' + this.stateName + '/' + this.regionName
            };
            this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
            return;
          }
        }, (err) => {
          const initialState = {
            title: 'Error',
            content: this.httperrorresponsemessages,
            action: '/image-list/' + this.qaId + '/' + this.commodityId + '/' + this.stateId + '/' + this.regionId + '/' + this.commodityName + '/' + this.stateName + '/' + this.regionName
          };
          this.modalRef = this.modalService.show(SuccessModalComponent, { initialState, backdrop: 'static', keyboard: false });
          return;
        });
    }
  }

}

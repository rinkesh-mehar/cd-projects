import {Component, Injectable, OnInit, ViewChild} from '@angular/core';
import {CarouselConfig} from 'ngx-bootstrap/carousel';
import {FlipbookServiceService} from '../service/flipbook-service.service';
import {environment} from '../../../../environments/environment';
import {ModalDirective} from 'ngx-bootstrap/modal';
import {DataService} from '../dataSync/data-service';
import {CarouselImage} from '../model/carousel-image';
import {OutputResponse} from '../model/output-response';
import {QuestionModel} from '../model/question-model';
import {NgForm} from '@angular/forms';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';

@Component({
    selector: 'app-carousel',
    templateUrl: './carousel.component.html',
    styleUrls: ['./carousel.component.scss'],
    providers: [
        {provide: CarouselConfig, useValue: {interval: 65000, noPause: true}},
    ]
})
@Injectable({
    providedIn: 'root'
})
export class CarouselComponent implements OnInit {
    @ViewChild('infoModal') public infoModal: ModalDirective;
    @ViewChild('delModal') public delModal: ModalDirective;
    @ViewChild('tagImage') public tagImage: ModalDirective;
    @ViewChild('editSymptom') public editSymptom: ModalDirective;
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    _baseURL = environment.benchmarkImagesURL;
     _imageURL: String;
     _imageName: String;
     _symptom: String;
     _symptomID: String;
     _imageID: CarouselImage;
     _isFiltered: boolean = false;
     _metaData: any;
     _maxID: number;
     _qID: any;
     i: any;
     j: any;
     _genericMsg1: String;
     _genericMsg2: String;
     _genericMsg3: String;
    // Image Itteration
     _que: Array<QuestionModel> = [];
     _generic: any;
    isSuccess: boolean = false;

    constructor(private _apiservice: FlipbookServiceService, private dataservice: DataService) {
    }

    imageClick(_name: String, _qID: any, qIndex: number, _iIndex: number, _f: any) {
        this._imageName = _name;
        this._qID = _qID;
        this.i = qIndex;
        this.j = _iIndex;
        if (_f === '_img') {
            // this._imageURL = this._baseURL + _name;
            this._imageURL = _name;
            this._genericMsg1 = 'Untagging Confirmation';
            this._genericMsg2 = 'Do you want to Delete Tagged Image ? ';
            this._genericMsg3 = 'Image';
        } else {
            this._genericMsg1 = 'Symptom Deletion Confirmation';
            this._genericMsg2 = 'Do you want to Delete Symptom ? ';
            this._genericMsg3 = 'Symptom';
        }
    }

    infoTag(_symptomID: String, _symptom: String) {
        this._symptom = _symptom;
        this._symptomID = _symptomID;
    }

    infoEdit(_symptomID: String, _symptom: String) {
        this._symptom = _symptom;
        this._symptomID = _symptomID;
    }

    ngOnInit() {
        this.dataservice.dataSync.subscribe(_data => {
            this._imageID = _data as CarouselImage;
            if(Object.keys(this._imageID).length > 0)
             { this._isFiltered = true; }
        });
        this.dataservice.metaSync.subscribe(_data => {
            this._metaData = _data;
        });
        this.dataservice.queSync.subscribe(_data => {
            this._que = [];
            _data.forEach((item, index) => {
                // tslint:disable-next-line:max-line-length
                this._generic = {'_symptomId': item._symptomId, '_genericImageId': item._genericImageId, '_symptom': item._symptom, '_img': (item._imageId).split(',')};
                this._que.push(this._generic);
                this._maxID = index;
                console.log(item);
            });
        });
        console.log(this._que);
    }

    saveQuery(imageTag: NgForm) {
        this._apiservice.tagImages(Object.assign(this._metaData, imageTag.value)).subscribe((_data: any) => {
            this.dataservice._op(_data);
            if (_data.status) {
            this._apiservice.getImages(this._metaData).subscribe((data: any) => {
                this._maxID = data._que.length - 1;
                this._generic = {
                    '_symptomId': data._que[this._maxID]._symptomId,
                    '_genericImageId': data._que[this._maxID]._genericImageId,
                    '_symptom': data._que[this._maxID]._symptom,
                    '_img': (data._que[this._maxID]._imageId).split(',')
                };
                this._que.push(this._generic);
                imageTag.onReset();
            });
        }
        });
    }

    // Tag Image to Already Present Symptom
    tagImg(imageTag: NgForm) {
        const _genericT = {'_symptomID': this._symptomID, '_symptom': this._symptom};
        this._apiservice.tagToSypmtom(Object.assign(_genericT, imageTag.value)).subscribe((_data: any) => {
            this.dataservice._op(_data);
            this.tagImage.hide();
            this._apiservice.getImages(this._metaData).subscribe((data: any) => {
                data._que.forEach((item, index) => {
                    if (item._symptomId === this._symptomID && item._symptom === this._symptom) {
                        this._que[index]._img = (item._imageId.split(','));
                    }
                });
            });
        });
        imageTag.onReset();
    }

    // Edit Already Present Symptom
    editSymp(imageTag: NgForm) {
        // alert(JSON.stringify(imageTag));
        console.log(imageTag);
        const _genericT = {'_symptomID': this._symptomID, '_symptom': this._symptom};
        this._apiservice.editSymptom(Object.assign(_genericT, imageTag.value)).subscribe((_data: any) => {
            // this.dataservice._op(_data);
            this.editSymptom.hide();
            if (_data) {
                this.isSuccess = _data.status;
                if (_data.status) {
                    this.successModal.showModal('SUCCESS', _data.message, '');
                    this._apiservice.getImages(this._metaData).subscribe((data: any) => {
                        data._que.forEach((item, index) => {
                            if (item._symptomId === this._symptomID) {
                                // this._que[index]._img = (item._imageId.split(','));
                                this._que[index]._symptom = (item._symptom);
                            }
                        });
                    });
                } else {
                    this.errorModal.showModal('ERROR', _data.error, '');
                }
            }
        });
        imageTag.onReset();
    }
    // Image Untagging
    untagImage(_name: String, _qId: any, qIndex: number, _iIndex: number, _flag: String) {
        const _op = new OutputResponse();
        if (_flag === 'Image') {
            this._apiservice.untagImage(_name, _qId).subscribe((_data: boolean) => {
                _op.status = _data;
                this.delModal.hide();
                if (_data === true) {
                    _op.msg = 'Untagging Successful !';
                    this._que[qIndex]._img.splice(_iIndex, 1);
                    if (this._que[qIndex]._img.length < 1) {
                        this._que.splice(qIndex, 1);
                    }
                } else {
                    _op.msg = 'Untagging Failed !';
                    _op.errCode = '';
                }
                this.dataservice._op(_op);
            });
        } else if (_flag === 'Symptom') {
            this._apiservice.removeSymptom(_name, _qId).subscribe((_data: boolean) => {
                _op.status = _data;
                this.delModal.hide();
                if (_data === true) {
                    _op.msg = 'Symptom Removal Successful !';
                    this._que.splice(qIndex, 1);

                } else {
                    _op.msg = 'Symptom Removal Failed !';
                    _op.errCode = '';
                }
                this.dataservice._op(_op);
            });
        } else if (_flag === 'GenImg') {
            this._apiservice.tagGenericImage(_name, _qId).subscribe((_data: boolean) => {
                _op.status = _data;
                this.delModal.hide();
                if (_data === true) {
                    _op.msg = 'Generic Image tagging Done';
                } else {
                    _op.msg = 'Generic Image tagging failed ';
                    _op.errCode = '';
                }
                this.dataservice._op(_op);
            });
        }
    }

    modalSuccess($event: any) {
    }

}

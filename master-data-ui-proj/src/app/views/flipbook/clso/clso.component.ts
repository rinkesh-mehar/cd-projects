import { Component, OnInit, ViewChild } from '@angular/core';
import { FlipbookServiceService } from '../service/flipbook-service.service';
import { DropDown } from '../model/drop-down';
import { NgForm } from '@angular/forms';
// @ts-ignore
import { DataService } from '../dataSync/data-service';
import { CarouselImage } from '../model/carousel-image';
import { OutputResponse } from '../model/output-response';
import { ModalDirective } from 'ngx-bootstrap/modal';

@Component({
    selector: 'app-clso',
    templateUrl: './clso.component.html',
    styleUrls: ['./clso.component.scss']
})
export class ClsoComponent implements OnInit {
    _data: DropDown;
    _stress: DropDown;
    _imageID: CarouselImage;
    _ques: any;
    _class: String;
    _metaData: any;
    _moduleS: boolean;
    _responseMsg: String;
    _op: OutputResponse;
    _cmd: number;
    _strType: number = 0;
    _phnp: number = 0;
    _strs: number = 0;
    _plntpart: number = 0;
    type: any;
    _strbool: boolean;

    /* Variables for displaying dropdown data */
    _phenoData: DropDown;
    _plantpData: DropDown;
    _stressStage: DropDown;
    _stressType: DropDown;

    @ViewChild('resp') public resp: ModalDirective;
    constructor(private _api: FlipbookServiceService, private dataservice: DataService) {
        this._stress = new DropDown();
        this._phenoData = new DropDown();
        this._plantpData = new DropDown();
        this._stressStage = new DropDown();
        this._stressType = new DropDown();
    }

    ngOnInit() { this.getDropData(); }

    async delay(ms: number) {
        await new Promise(resolve => setTimeout(() => resolve(), ms));
    }
    cmd(_data: number) {
        if (_data < 1) { this._strbool = false; }
        this._cmd = _data;
    }

    strType(_data: number, _flg: number) {
        if (_flg === 1) { this._cmd = _data; }
        // if (_flg === 2) { this._phnp = _data; }
        if (_flg === 2) { this._plntpart = _data; }
        if (_flg === 3) { this._strType = _data; }
        if (_flg === 4) { this._strs = _data; }
        this._api.getStressData(this._cmd, this._strType, this._phnp, this._strs, this._plntpart, _flg).subscribe((data: DropDown) => {
            // if (_flg === 1) { this._phenoData = data as DropDown; }
            if (_flg === 1) { this._plantpData = data as DropDown; if (this._strType == 4) { this._stress = data as DropDown; } }
            if (_flg === 2) { this._stressType = data as DropDown; }
            if (_flg === 3) { this._stress = data as DropDown; }
            if (_flg === 4) { this._stressStage = data as DropDown; }
            // this._stress = data as DropDown;
            this._strbool = true;
            // if (Object.keys(this._stress['_str']).length > 0 && _flg === 3) { this._strbool = true; }
        });
    }

    getDropData() {
        this._data = new DropDown();
        this._api.getDropDownData().subscribe((data: DropDown) => {
            this._data = data as DropDown;
        });
    }

    getSymptoms(symptoms: NgForm) {
        this._imageID = new CarouselImage();
        this._op = new OutputResponse();
        this._metaData = symptoms.value;
        this._api.getImages(symptoms.value).subscribe((data: any) => {
            this._imageID = data._imgID as CarouselImage;
            this._ques = data._que;
            this.dataservice.sendData(this._imageID);
            this.dataservice.sendMeta(symptoms.value);
            this.dataservice.sendQue(this._ques);

            // Getting Response from Carousel
            this.dataservice.outPut.subscribe(_data => {
                this._op = _data;
                //this._ishow = true;
                this._moduleS = this._op.status;
                if (this._moduleS === false) {
                    this._class = 'modal-dialog modal-danger';
                    this._responseMsg = this._op.msg + ' ' + this._op.errCode;
                    this.resp.show();
                } else {
                    this._responseMsg = this._op.msg;
                    this._class = 'modal-dialog modal-success';
                    this.resp.show();
                }
            });
        });
    }

    getTypeValue(value: string) {
        this.type = value;
    }

}

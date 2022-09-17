import { Component, OnInit, ViewChild } from '@angular/core';
import { ClusterService } from '../services/cluster.service';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { environment } from '../../../../environments/environment';
import { Sort } from '@angular/material';
declare var $

@Component({
    selector: 'app-cluster-list',
    templateUrl: './cluster-list.component.html',
    styleUrls: ['./cluster-list.component.scss']
})
export class ClusterListComponent implements OnInit {
    @ViewChild('delModal', { static: false }) public delModal: ModalDirective;
   
    // isMmpkFils: File;
    getnewfile: any = [];
    // getmpkfile: any;
    getcsvfile: any;
    validURL: boolean = false;

    constructor(private _apiservice: ClusterService,
        ) {


    }

    ClusterList: any = [];
    _genericMsg2: String;
    _genericMsg1: String;
    _class: String;
    _idx: number;
    _state: String;
    address: String;
    pin: any;
    phoneNumber: String;
    _district: number;
    _stateCode: number;
    _stateList: any;
    _districtList: any;
    _imageID: String;
    _baseURL = environment.benchmarkImagesURL;
    uploadFile: any = [];
    totalRow: any;
    isColumn: boolean;
    totalColumn: any;
    isSubmited: boolean;
    isRow: boolean;
    isCsvFile: boolean;
    isMmpkFile: boolean;
    csvFile: File;
    // mmpkFile: File;
    image: File;
    shapeFile: File;
    isRegionImageFile: boolean;
    isShapeFile: boolean;
    // mmpkFileUrl: String;
    graphMlUrl: String;
    regionName: String;
    contactPerson: String;
    lat: String;
    log: String;
    extension: String;

    ngOnInit() {
        this.getList();
        // this.getState();
    }

    getList() {
        this._apiservice.getList().subscribe((data: any) => {
            this.ClusterList = data;
        
        });
    }



    getState() {
        this._apiservice.getState().subscribe((data: any) => {
            this._stateList = data;
        });
    }

    getDistrict(stateCode: number){
        this._apiservice.getAllDistrict(stateCode).subscribe((data: any) => {
            this._districtList = data;
        })
    }

    Msg(_msg: string, _idx: number, _state: string) {
        let stateCode: number;
        if (_msg === 'edit') {
            this.getState();
            // $('input').val('')
            this._genericMsg2 = 'Are you sure you want to edit Region ??';
            this._genericMsg1 = 'Edit Region';
            this._class = 'modal-dialog  modal-warning modal-xl';
            this._idx = _idx;
            this._state = _state;
        } else {
            this._genericMsg2 = 'Are you sure you want to delete?';
            this._genericMsg1 = 'Delete';
            this._class = 'modal-dialog  modal-danger';
            this._idx = _idx;
        }
        this._apiservice.getImage(_idx).subscribe((data: any) => {
            this._imageID = data.msg.fileUrl;
            // this.mmpkFileUrl = data.msg.mmpkUrl;
            this.regionName = data.msg.name;
            this._district = data.msg.districtCode;
            this._stateCode = data.msg.stateCode;
            this.address = data.msg.address;
            this.pin = data.msg.pin;
            this.phoneNumber = data.msg.phoneNumber;
            this.contactPerson = data.msg.contactPerson;
            this.lat = data.msg.rlLatitude;
            this.log = data.msg.rlLongitude;
            this.graphMlUrl = data.msg.graphMlUrl;

            this.getDistrict(this._stateCode);

        });
    }

    chncd(ent: Event) {
        let index : number = event.target['selectedIndex'];
        if(index ==-1) {
            return;
        }
        this._stateCode = this._stateList[index].stateCode;
        console.log(this._stateCode);
        return this._apiservice.getAllDistrict(this._stateCode).subscribe((data: {}) => {
            this._districtList = data;

        });
    }

    // Upload file
//   fileChange(element) {
//     this.mmpkFile = element.target.files[0];
// this.getnewfile = file
   
//   }

    /*Upload CSV file*/
  csvFileChange(element) {
    this.csvFile = element.target.files[0];
       
  }

    /*Upload CSV file*/
    imageChange(element) {
        this.image = element.target.files[0];
           console.log(this.image);
           
      }

      shapeFiles(element){

          this.shapeFile = element.target.files[0];
          this.extension = this.shapeFile.name.replace(/^.*\./, '');

          if (!(this.extension == 'zip')){
              this.isShapeFile = false;
              return;
          } else {
              this.isShapeFile = true;
          }

      }
  reset(){
    // $('input').val('');
    this.isSubmited = false;
    this.isCsvFile = false;
    this.isMmpkFile = false;
    this.isRow = false;
    this.isColumn = false;
    this.csvFile = null;
    this.image = null;
    // this.mmpkFile = null;
    // this.isMmpkFils = null;
    this.totalRow = '';
    this.totalColumn ='';
    this.validURL = false

  }


    regionAction(_idx: number, _edit: string) {
        if (_edit === 'del') {
            const _data = {
                '_id': _idx,
                '_act': _edit
            };
            /*this._apiservice.getData(_data, null, this.image).subscribe((data: boolean) => {
                if (data) {
                    this.delModal.hide()

                    this._genericMsg1 = 'Done';
                    this._class = 'modal-dialog  modal-success';
                    this._genericMsg2 = 'Region Deleted Successfully .';
                    this.getList();
                } else {
                    this.delModal.hide()

                    this._genericMsg1 = 'Failed';
                    this._class = 'modal-dialog  modal-danger';
                    this._genericMsg2 = 'Failed to Delete Region .';
                    this.getList();
                }
            });*/
        } else {
            if (this.pin == ''){
                this.pin = null;
            }
            // this.spinnerService.show();
            const _data = {
                '_id': this._stateCode,
                '_act': _edit,
                '_regionId': _idx,
                'totalRow': this.totalRow,
                'totalColumn': this.totalColumn,
                // 'mmpkFileUrl': this.mmpkFileUrl,
                // 'regionName': this.regionName
                'regionName': this.regionName,
                    'stateCode' : this._stateCode,
                    'districtCode': this._district,
                    'address': this.address,
                    'pin': this.pin,
                    'phoneNumber': this.phoneNumber,
                    'contactPerson': this.contactPerson,
                    'rlLatitude': this.lat,
                    'rlLongitude': this.log,
                    'graphMlUrl': this.graphMlUrl
            };
            // this.isSubmited = true;

            // if(this.mmpkFileUrl == null || this.mmpkFileUrl == ''){
            //     this.validURL = true; 
            //     return false;
            // }
            if(this.shapeFile ==undefined){
                this.isShapeFile = false;
                return;
            }
            if (this.lat == null || this.lat == ''){
                return;
            } if (this.log == null || this.log == ''){
                return;
            } if (this.address == null || this.address == ''){
                return;
            }
            if(this.csvFile){
                if((this.totalColumn == null || this.totalColumn == '') || (this.totalRow == null || this.totalRow == '')){
                    this.isRow = true;
                    this.isColumn = true;
                    return false;
                }
            }
   
            this.delModal.hide()
            /*this.spinnerService.show();*/
            this._apiservice.getData(_data, this.csvFile, this.image, this.shapeFile).subscribe((data) => {
                console.log(_data);
                
                if (data.success == true) {
                    this.delModal.show()
                    this._genericMsg1 = 'Done';
                    this._class = 'modal-dialog  modal-success';
                    this._genericMsg2 = data.errorMsg;
                    this.getList();
                    /*this.spinnerService.hide();*/
                    console.log("hide=---------------------");

                } else {
                    this.delModal.show()
                    this._genericMsg1 = 'Failed';
                    this._class = 'modal-dialog  modal-danger';
                    this._genericMsg2 = data.errorMsg;
                    this.getList();
                    /*this.spinnerService.hide();*/
                    console.log('hide=---------------------');

                }
            });


        //   this._apiservice.getData(_data, this.getnewfile,this.getcsvfile).subscribe((data: boolean) => {
            // if (data) {
                // this.delModal.hide()

                // this._genericMsg1 = 'Done';
            //   this._class = 'modal-dialog  modal-success';
            //   this._genericMsg2 = 'State update Successfully.';
            //   this.getList();
            // } else {
                // this.delModal.hide()

                // this._genericMsg1 = 'Failed';
            //   this._class = 'modal-dialog  modal-danger';
            //   this._genericMsg2 = 'Failed to update State.';
            //   this.getList();
            // }
        //   });

        }
    }

    sortData(sort: Sort) {
        const data = this.ClusterList.slice();
        if (!sort.active || sort.direction == '') {
          this.ClusterList = data;
          return;
        }
      
        function compare(firstValue, secondValue, isAsc: boolean) {
          return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }
      
        this.ClusterList = data.sort((firstValue, secondValue) => {
          const isAsc = sort.direction == 'asc';
          switch (sort.active) {
              case 'region':
                return compare(firstValue.region, secondValue.region, isAsc);
            case 'regionName':
              return compare(firstValue.regionName, secondValue.regionName, isAsc);
            case 'state':
              return compare(firstValue.state, secondValue.state, isAsc);
            case 'district':
              return compare(firstValue.district, secondValue.district, isAsc);
            default:
              return 0;
          }
        });
      }
      

}

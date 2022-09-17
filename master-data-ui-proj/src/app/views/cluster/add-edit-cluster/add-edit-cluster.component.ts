import {Component, OnInit, ViewChild} from '@angular/core';
import {ClusterService} from '../services/cluster.service';
import {NgForm} from '@angular/forms';
import {ModalDirective} from 'ngx-bootstrap/modal';


@Component({
    selector: 'app-add-edit-cluster',
    templateUrl: './add-edit-cluster.component.html',
    styleUrls: ['./add-edit-cluster.component.scss']
})
export class AddEditClusterComponent implements OnInit{

    constructor(private _apiservice: ClusterService) { }

    regionId: any;
    sourceId: any;
     _regionDetails: Array<number> = [];
     sourceList: any;
     _details: any;
     _isShow: boolean = false;
     _statusMsg: String;
     isSubmitted: boolean = false;
     _responseMsg: String;
     _class: String;
     stateList: {};
     districtList: {};


    @ViewChild('resp') public resp: ModalDirective;

    ngOnInit(): void {
        this._apiservice.getSources().subscribe( data =>{
            this.sourceList = data;
        })
    }
    getDetails( _data: NgForm) {
        this.sourceId = _data.value.sourceId;
        this._regionDetails = _data.value['subregion'].split('\n');
        console.log(this._regionDetails);
        if (Object.keys(this._regionDetails).length > 0) {
            if (Object.keys(this._regionDetails).length  === 256) {

                this._apiservice.getDetails(this.regionId, this._regionDetails).subscribe((data: any) => {
                    this._details = data;
                    if (Object.keys(this._details).length > 0) {
                        this._isShow = true;
                    } else {
                        this._isShow = false;
                    }
                });
            } else {
                this.resp.show();
                this._responseMsg = 'Please Select 256 Sub-regions';
                this._class = 'modal-dialog  modal-danger';
                this.delay(2000).then(any => {
                    this.resp.hide();
                });
            }
        } else {
            this.resp.show();
            this._responseMsg = 'Please Select Sub-regions';
            this._class = 'modal-dialog  modal-danger';
            this.delay(2000).then(any => {
                this.resp.hide();
            });
        }
    }
zz
    saveRegion(region: NgForm ) {

        console.log("Helllooooe");
        const pst = {
            '_regionName' : region.value.regionName ,
            '_desc'       : region.value.regionDesc ,
            '_subregions' : this._regionDetails,
            'regionId' : this.regionId,
            'state' : region.value.stateCode,
            'district' : region.value.districtCode,
            'address' : region.value.address,
            'pin' : region.value.pin,
            'mobileNo' : region.value.mobileNo,
            'contactPerson' : region.value.contactPerson,
            'lat' : region.value.lats,
            'log' : region.value.loges,
            'sourceId': this.sourceId,
            'workingHours' : region.value.workingHours,
            'percentageOfAbsence': region.value.percentageOfAbsence,
            'percentageOfInefficency': region.value.percentageOfInefficency,
            'mmpkPartCount' : region.value.mmpkPartCount
                   };
                   console.log(JSON.stringify(pst));
                   // alert(JSON.stringify(pst));
         this._apiservice.saveRegion(this.regionId, pst).subscribe((data: any) => {
             if (data.success) {
                 this._responseMsg = 'Region created successfully';
                 this._class = 'modal-dialog modal-success';
                 this.resp.show();
                 this._isShow = false;
                 this._regionDetails.splice(0, this._regionDetails.length);
                 region.reset();
             } else {
                     region.reset();
                     this._isShow = false;
                     this._regionDetails.splice(0, this._regionDetails.length);
                     this._responseMsg = data.errorMsg ;
                     this._class = 'modal-dialog  modal-danger';
                     this.resp.show();
             }
         });

    }

    getActiveStates() {

        return this._apiservice.GetAllState().subscribe((data: {}) => {
            this.stateList = data;
        });
    }
    getActiveDistrictByStateCode(event: Event) {
        let index : number = event.target['selectedIndex'] - 1;
        if(index ==-1) {
            return;
        }
        let stateCode = this.stateList[index].stateCode;
        console.log(stateCode);
        return this._apiservice.getAllDistrict(stateCode).subscribe((data: {}) => {
            this.districtList = data;

        });
    }

    async delay(ms: number) {
        await new Promise(resolve => setTimeout(() => resolve(), ms));
    }

}

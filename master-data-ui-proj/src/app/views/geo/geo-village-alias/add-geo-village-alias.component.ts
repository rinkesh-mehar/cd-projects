import {Component, OnInit, ViewChild} from '@angular/core';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {VillageAlias} from './model/VillageAlias';
import {UserRightsService} from '../../services/user-rights.service';
import {GeoTehsilService} from '../services/geo-tehsil.service';
import {GeoDistrictService} from '../services/geo-district.service';
import {Router} from '@angular/router';
import {GeoVillageService} from '../services/geo-village.service';
import { Sort } from '@angular/material';

declare var $: any;

@Component({
    selector: 'app-village-alias',
    templateUrl: './add-geo-village-alias.component.html',
    styleUrls: ['./add-geo-village-alias.component.css']
})

export class AddGeoVillageAliasComponent implements OnInit {

    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    isSubmitted: boolean = false;
    stateList: any = [];
    html: any;
    disableRow: boolean = false;
    districtList: any = [];
    tehsilList: any = [];
    villageList: any = [];
    isVillage: boolean = true;
    isAddNewRow: boolean = false;

    row = [
        {
            id: '',
            name: '',
            email: ''

        }
    ];
    aliasName: any;
    indexId: any;
    pageVillageAlias: VillageAlias;
    selectedPage: number;
    maxSize = 10;
    getVillages: any = [];

    constructor(public userRightsService: UserRightsService, public tehsilService: GeoTehsilService,
                public geoDistrictService: GeoDistrictService, public router: Router,
                public villageService: GeoVillageService) {
    }

    ngOnInit(): void {
        this.disableRow = false;
        this.isVillage = true;
        this.isAddNewRow = false;
        this.getListOfVillageAlias(0);
        this.row = [];
    }

    addNewRow() {
        this.disableRow = true;
        this.loadAllState();
        this.isVillage = false;
        this.isAddNewRow = true;
        const obj = {
            id: '',
            name: '',
            email: ''
        };
        this.aliasName = '';
        this.row.push(obj);
        // this.service.success('New row added successfully', 'New Row');

        return true;
    }

    deleteRow(x) {

        this.disableRow = false;
        if (x >= 0) {
            this.row.splice(x, 1);
        } else {
            this.getListOfVillageAlias(0);

        }

    }

    onSelect(page: number): void {
        console.log('selected page : ' + page);
        this.selectedPage = page;
        this.getListOfVillageAlias(page);
    }

    getListOfVillageAlias(page: number) {
        this.isVillage = true;
        return this.villageService.getPageGeoVillageAlias(page).subscribe(
            page => {
                this.pageVillageAlias = page;
                this.getVillages = page[0].Villages;

                this.villageService.addToStore(this.pageVillageAlias.Villages);
            });


    }

    getData(stateCode, districtCode, id, event) {
        if (event.target.value) {
            return;
        }
        var result = this.getVillages.filter(function (v, i) {
            // if (v.districtCode == id && v.stateCode == stateCode){

            return v.stateCode == stateCode && v.districtCode == districtCode && v.tehsilCode == id;
            // }
        });
        var data = `<option value=" ">--Select Village--</option>`;
        $.each(result, function (index, value) {
            data += `<option value="${value.villageCode}">${value.name}</option>`;
        });
        $(event.target).html(data);

    }

    loadAllState() {
        this.geoDistrictService.GetAllState().subscribe((data: {}) => {
            // this.stateList = Array.from(data['States']);
            this.stateList = data;

        });
    }

    saveAliasVillage(aliasId, stateCode, districtCode, tehsilCode, index, event, validAlias) {
        let data: any;
        let code;
        let aliasName: string;
        this.html = $(event.target);
        if (validAlias == 'addAlias') {
            aliasName = $(event.target).closest('tr').find('input').val();
            districtCode = $(event.target).closest('tr').find('.district').val();
            stateCode = $(event.target).closest('tr').find('.state').val();
            tehsilCode = $(event.target).closest('tr').find('.tehsil').val();
            code = $(event.target).closest('tr').find('.village').val();
            if (aliasName == '' || aliasName == null || aliasName.trim() == '') {
                $(event.target).closest('tr').find('span').text(`Please insert Alias Name`);
                return;
            }
            aliasName = aliasName.trim();
        }
        
        this.indexId = index;
        console.log('Districts Id ------>' + aliasId);
        console.log('Districts Id ------>' + stateCode);
        if (code == undefined) {

            data = {
                'stateCode': stateCode,
                'aliasId': aliasId,
                'aliasName': aliasName,
                'districtCode': districtCode,
                'tehsilCode': tehsilCode,
                'villageCode': event.target.value
            };
        } else {
            data = {
                'stateCode': stateCode,
                'aliasId': aliasId,
                'aliasName': aliasName,
                'districtCode': districtCode,
                'tehsilCode': tehsilCode,
                'villageCode': code
            };
        }
        this.villageService.saveAliasVillage(data).subscribe(res => {
            this.isSubmitted = true;
            if (res.success) {
                if (this.indexId >= 0 && this.indexId != null) {
                    this.deleteRow(this.indexId);
                } else {
                    if (aliasId == 0) {
                        this.successModal.showModal('SUCCESS', res.message, '');
                    }
                    // this.ngOnInit();
                    // console.log(this.html.closest('tr').find('.addAction'));
                    this.html.closest('tr').find('.addAction').html(`<span>Saved</span>`);
                }
            } else {
                this.errorModal.showModal('ERROR', res.error, '');
            }
        });

    }

    loadDistrictByStateCode(event): void {
        // const index: number = event.target['selectedIndex'] - 1;
        // // tslint:disable-next-line:triple-equals
        // if (index == -1) {
        //     return;
        // }
        // const stateCode = this.stateList[index].StateCode;
        console.log(event.target.value);
        this.geoDistrictService.GetAllDistrictAliasByStateCode(event.target.value).subscribe(
            (data: {}) => {
                // this.districtList = Array.from(data['Districts']);
                this.districtList = data;
                console.log('District list' + this.districtList);

                console.log(this.html);
            });
    }

    loadTehsilByDistrictCode(event): void {
        // const index: number = event.target['selectedIndex'] - 1;
        // // tslint:disable-next-line:triple-equals
        // if (index == -1) {
        //     return;
        // }
        // const stateCode = this.stateList[index].StateCode;
        console.log(event.target.value);
        this.tehsilService.GetAllTehsilByDistrictCode(event.target.value).subscribe(
            (data: {}) => {
                // this.districtList = Array.from(data['Districts']);
                this.tehsilList = data;
                console.log('District list' + this.districtList);

                console.log(this.html);
            });
    }

    loadVillageByTehsilCode(event): void {
        // const index: number = event.target['selectedIndex'] - 1;
        // // tslint:disable-next-line:triple-equals
        // if (index == -1) {
        //     return;
        // }
        // const stateCode = this.stateList[index].StateCode;
        console.log(event.target.value);
        this.villageService.GetAllVillageByTehsil(event.target.value).subscribe(
            (data: {}) => {
                // this.districtList = Array.from(data['Districts']);
                this.villageList = data;
                console.log('District list' + this.villageList);

                console.log(this.html);
            });
    }

    validAliasName() {
        const aliasName = $(event.target).val();

        if (aliasName != '') {
            $(event.target).closest('tr').find('span').text(``);
        }
    }

    modalSuccess($event: any) {
        this.ngOnInit();
    }

    sortData(sort: Sort) {
        const data = this.pageVillageAlias[1].content.slice();
        if (!sort.active || sort.direction == '') {
          this.pageVillageAlias[1].content = data;
          return;
        }
      
        function compare(firstValue, secondValue, isAsc: boolean) {
          return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }
      
        this.pageVillageAlias[1].content = data.sort((firstValue, secondValue) => {
          const isAsc = sort.direction == 'asc';
          switch (sort.active) {
            case 'stateName':
                return compare(+firstValue.stateName, +secondValue.stateName, isAsc);
                case 'stateName':
                    return compare(+firstValue.districtName, +secondValue.districtName, isAsc);
                    case 'stateName':
                        return compare(+firstValue.tehsilName, +secondValue.tehsilName, isAsc);
            case 'alias':
              return compare(+firstValue.alias, +secondValue.alias, isAsc);
            default:
              return 0;
          }
        });
      }
}
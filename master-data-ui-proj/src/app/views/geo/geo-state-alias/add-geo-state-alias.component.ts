import {Component, OnInit, ViewChild} from '@angular/core';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {UserRightsService} from '../../services/user-rights.service';
import {AgriCommodityService} from '../../agri/services/agri-commodity.service';
import {Router} from '@angular/router';
import {GeoStateService} from '../services/geo-state.service';
import {globalConstants} from '../../global/globalConstants';
import {Alias} from '../geo-district-alias/model/Alias';
import {DistrictAlias} from '../geo-district-alias/model/DistrictAlias';
import {StateAlias} from './model/StateAlias';
import { Sort } from '@angular/material';
declare var $: any;

@Component({
    selector: 'app-state-alias',
    templateUrl: './add-geo-state-alias.component.html',
    styleUrls: ['./add-geo-state-alias.component.css']
})
export class AddGeoStateAliasComponent implements OnInit{
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    isSubmitted: boolean = false;
    stateList: any = [];
    aliasList: any = [];
    isState: boolean = true;
    isAddNewRow: boolean = false;
    tempName: any;
    aliasID: number;
    html: any;
    indexId: any;
    disableRow: boolean = false;
    pageStateAlias: StateAlias;
    selectedPage: number;
    searchText : any = "";
    maxSize : number=10;
    flag:number=0;
    row = [
        {
            id : '',
            name: '',
            email: ''
        },
    ];
    aliasName: any;



    ngOnInit(): void {
        this.disableRow = false;
        this.isState = true;
        this.isAddNewRow = false;
        this.row = [
        ];
        this.getListOfStateAlias(0);
    }

    constructor(public userRightsService: UserRightsService,
                public router: Router, public geoStateService: GeoStateService) {
    }

    addNewRow() {
        this.flag=1;
        this.disableRow = true;
        this.isState = false;
        this.isAddNewRow = true;
        const obj = {
            id: '',
            name: '',
            email: ''
        };
        this.row.push(obj);
        // this.service.success('New row added successfully', 'New Row');
        this.aliasName = '';
        return true;
    }
    deleteRow(x) {

        this.disableRow = false;
        if (x >= 0) {
            this.row.splice(x, 1);
        } else {
            this.getListOfStateAlias(0);
        }


    }
    onSelect(page: number): void {
        console.log("selected page : " + page);
        this.selectedPage = page;
        this.getListOfStateAlias(page);
    }

    getListOfStateAlias(page: number) {
        this.isState = true;
        return this.geoStateService.getPageGeoStateAlias(page).subscribe(
            page=> this.pageStateAlias = page)
    }

    saveAliasState(aliasId, event, index, validAlias) {
        let aliasName : string;
        let code;
        if (validAlias == 'addAlias') {
            aliasName =  $(event.target).closest('tr').find('input').val();
            // let stateCode = $('select.state').children('option:selected').;
            code = $(event.target).closest('tr').find('select').val();
            if ( aliasName == '' || aliasName == null || aliasName.trim() == '') {

                $(event.target).closest('tr').find('span').text(`Please insert Alias Name`);
                return;
            }
            aliasName = aliasName.trim();
        }
      
        this.indexId = index;
        this.html = $(event.target);
        let data: any;
        console.log('Alias Id ------>' + event.target.value);
        // console.log('state Id ------>' + stateCode);
        // console.log('state Name ------>' + );
        if (code == undefined) {
            data = {
                'stateCode': event.target.value,
                'aliasId': aliasId,
                'aliasName': aliasName
            };
        } else {
            data = {
                'stateCode': code,
                'aliasId': 0,
                'aliasName': aliasName
            };
        }

        this.geoStateService.saveAliasState(data).subscribe(res => {
            this.isSubmitted = true;
            if (res.success) {

                    if (aliasId == 0){

                        this.successModal.showModal('SUCCESS', res.message, '');
                    }
                    // this.ngOnInit();
                    console.log(this.html.closest('tr').find('.addAction'));
                    this.html.closest('tr').find('.addAction').html(`<span>Saved</span>`);

            } else {
                this.errorModal.showModal('ERROR', res.error, '');
            }
        });
    }

  /*  sortData(sort: any) {
        const data = this.aliasList.slice();
        if (!sort.active || sort.direction == '') {
            this.aliasList = data;
            return;
        }

        function compare(firstValue, secondValue, isAsc: boolean) {
            return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }

        this.aliasList = data.sort((firstValue, secondValue) => {
            const isAsc = sort.direction == 'asc';
            switch (sort.active) {
                case 'Alias':
                    return compare(firstValue.Alias, secondValue.Alias, isAsc);
                default:
                    return 0;
            }
        });
    }*/

  validAliasName() {
      const aliasName = $(event.target).val();

      if (aliasName != '') {
          $(event.target).closest('tr').find('span').text(``);
      }
  }
    modalSuccess($event: any) {
        this.flag=0;
this.ngOnInit();
    }

    sortData(sort: Sort) {
        const data = this.pageStateAlias[1].content.slice();
        if (!sort.active || sort.direction == '') {
          this.pageStateAlias[1].content = data;
          return;
        }
      
        function compare(firstValue, secondValue, isAsc: boolean) {
          return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }
      
        this.pageStateAlias[1].content = data.sort((firstValue, secondValue) => {
          const isAsc = sort.direction == 'asc';
          switch (sort.active) {
            case 'alias':
              return compare(+firstValue.alias, +secondValue.alias, isAsc);
            default:
              return 0;
          }
        });
      }
      
}

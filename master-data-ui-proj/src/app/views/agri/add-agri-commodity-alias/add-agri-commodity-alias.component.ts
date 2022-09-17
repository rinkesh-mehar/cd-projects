import {Component, OnInit, ViewChild} from '@angular/core';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {UserRightsService} from '../../services/user-rights.service';
import {AgriCommodityService} from '../services/agri-commodity.service';
import {Alias} from './model/Alias';
import {Commodity} from './model/Commodity';
import {SuccessModalComponent} from '../../global/success-modal/success-modal.component';
import {ErrorModalComponent} from '../../global/error-modal/error-modal.component';
import {Router} from '@angular/router';
import {isNumber} from 'util';
import {PageAgriCommodity} from '../models/PageAgriCommodity';
import {CommodityAlias} from './model/CommodityAlias';
import { Sort } from '@angular/material';
declare var $: any;

@Component({
    selector: 'app-agri-commodity-alias',
    templateUrl: './add-agri-commodity-alias.component.html',
    styleUrls: ['./add-agri-commodity-alias.component.css']
})
export class AddAgriCommodityAliasComponent implements OnInit {
    @ViewChild('successModal') public successModal: SuccessModalComponent;
    @ViewChild('errorModal') public errorModal: ErrorModalComponent;

    pageCommodityAlias: CommodityAlias;
    isSubmitted: boolean = false;
    commodityList: any = [];
    aliasList: any = [];
    isAddNewRow: boolean = false;
    isCommodity: boolean = true;
    html: any;
    disableRow: boolean = false;
    isAliasName: boolean = false;
    indexId: any;
    selectedPage: number;
    maxSize = 10;
    flag: number = 0;
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
        this.isCommodity = true;
        this.isAddNewRow = false;
        this.row = [
        ];
        this.getListOfCommodityAlias(0);
    }

    constructor(public userRightsService: UserRightsService, public agriCommodityService: AgriCommodityService,
                public router: Router) {
    }

    addNewRow() {
        this.flag = 1;
        this.disableRow = true;
        this.isCommodity = false;
        this.isAddNewRow = true;
        const obj = {
            id: '',
            name: '',
            email: ''
        };
        this.row.push(obj);
        // this.service.success('New row added successfully', 'New Row');
        this. aliasName = '';
        return true;
    }
    deleteRow(x) {
        this.disableRow = false;
        if (x >= 0) {
            this.row.splice(x, 1);
        } else {
            this.getListOfCommodityAlias(0);
        }

    }
    onSelect(page: number): void {
        console.log('selected page : '+ page);
        this.selectedPage = page;
        this.getListOfCommodityAlias(page);
    }
    getListOfCommodityAlias(page: number) {

        this.isCommodity = true;
        return this.agriCommodityService.getPageAgriCommodityAlias(page).subscribe(
            page=> this.pageCommodityAlias = page)
    }

    saveAliasCommodity(aliasId, event, index, validAlias) {
        let aliasName: string;
        let code;
        if (validAlias == 'addAlias') {
             aliasName =  $(event.target).closest('tr').find('input').val();
            code = $(event.target).closest('tr').find('select').val();
            if ( aliasName == '' || aliasName == null || aliasName.trim() == '') {

                $(event.target).closest('tr').find('span').text(`Please insert Alias Name`);
                return;
            }
            aliasName = aliasName.trim();
        }

        
        this.indexId = index;

        this.html = $(event.target);
        console.log('alias Id ------>' + event.target.value);
        let data: any;
        // console.log('Districts Id ------>' + aliasId);
        // console.log('Districts Id ------>' + commodityId);
        if (code == undefined) {
             data = {
                 'commodityId': event.target.value,
                 'aliasName': aliasName,
                 'aliasId': aliasId
            };
        } else {
            data = {
                'commodityId': code,
                'aliasName': aliasName,
                'aliasId': aliasId
            };
        }
        this.agriCommodityService.saveCommodityAlias(data).subscribe(res => {
            this.isSubmitted = true;
            if (res.success) {
                this.isAliasName = false;
                    if (aliasId == 0){

                        this.successModal.showModal('SUCCESS', res.message, '');
                    }
                    // console.log(this.html.closest('tr').find('.addAction'));
                    this.html.closest('tr').find('.addAction').html(`<span>Saved</span>`);

            } else {
                this.errorModal.showModal('ERROR', res.error, '');
            }
        });
    }
validAliasName(){
    const aliasName = $(event.target).val();

    if (aliasName != ''){
        $(event.target).closest('tr').find('span').text(``);
    }
}

sortData(sort: Sort) {
    const data = this.pageCommodityAlias[1].content.slice();
    console.log("data : " + data);
    if (!sort.active || sort.direction == '') {
      this.pageCommodityAlias[1].content = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.pageCommodityAlias[1].content = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
        case 'alias':
          return compare(+firstValue.alias, +secondValue.alias, isAsc);
        default:
          return 0;
      }
    });
  }
  
    modalSuccess($event: any) {
        this.flag=0;
        this.ngOnInit();
    }
}

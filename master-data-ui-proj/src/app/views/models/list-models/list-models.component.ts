import { ModelPage } from './../model/modelPage';
import { globalConstants } from './../../global/globalConstants';
import {Component, OnInit} from '@angular/core';
import {CdtModulesService} from '../services/cdt-modules.service';
import { Sort } from '@angular/material';

@Component({
    selector: 'app-models',
    templateUrl: './list-models.component.html',
    styleUrls: ['./list-models.component.css']
})

export class ListModelsComponent implements OnInit {

    modelList: any = [];
    searchText: any = '';
    selectedPage: number = 1;
    maxSize =10;
    recordsPerPage: number = 10;
    records: any = [];
 
    modelPage: ModelPage;
    constructor(public cdtModulesService: CdtModulesService) {
    }
    ngOnInit(): void {
      this.records = ['2', '50', '100', '200', '250'];
      this.getModelPagenatedList(0);
      // this.loadModelList();
    }

    getModelPagenatedList(page: number): void {
      this.cdtModulesService.getModelPagenatedList(page, this.recordsPerPage, this.searchText)
          .subscribe(page => this.modelPage = page);
    }
    
    loadData(event: any) {
      console.log('pages ', event.target.value);
      this.recordsPerPage = event.target.value || 10;
      this.cdtModulesService.getModelPagenatedList(this.selectedPage - 1, this.recordsPerPage, this.searchText)
        .subscribe(page => this.modelPage = page);
    }
  
    onSelect(page: number): void {
      // console.log('selected page : ' + page);
      this.selectedPage = page;
      this.getModelPagenatedList(page);
  }
  
  search() {
    this.selectedPage = 1;
    console.log(this.searchText);
    this.getModelPagenatedList(this.selectedPage - 1);
  }

    loadModelList() {
        return this.cdtModulesService.getModelList().subscribe((data: {}) => {
            this.modelList = data;
        });
    }

    sortData(sort: Sort) {
        const data = this.modelList.slice();
        if (!sort.active || sort.direction == '') {
          this.modelList = data;
          return;
        }
      
        function compare(firstValue, secondValue, isAsc: boolean) {
          return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }
      
        this.modelList = data.sort((firstValue, secondValue) => {
          const isAsc = sort.direction == 'asc';
          switch (sort.active) {
            case globalConstants.ID:
              return compare(+firstValue.id, +secondValue.id, isAsc);
              case 'modelName':
                return compare(firstValue.modelName, secondValue.modelName, isAsc);
            case 'modelDescription':
              return compare(firstValue.modelDescription, secondValue.modelDescription, isAsc);
            default:
              return 0;
          }
        });
      }
}
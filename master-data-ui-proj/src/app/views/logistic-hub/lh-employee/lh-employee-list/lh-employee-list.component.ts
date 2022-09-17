import { globalConstants } from './../../../global/globalConstants';
import {Component, OnInit, ViewChild} from '@angular/core';
import {RlUserpage} from '../../../manage-rl/model/rl-userpage';
import {EmployeeService} from '../../service/employee.service';
import {UserRightsService} from '../../../services/user-rights.service';
import { Sort } from '@angular/material';

@Component({
    selector: 'app-logistic-employee-list',
    templateUrl: './lh-employee-list.component.html',
    styleUrls: ['./lh-employee-list.component.css']
})

export class LhEmployeeListComponent implements OnInit {
    @ViewChild('closebutton') closebutton;
    searchText: any = '';
    lhUserPage: RlUserpage;
    selectedPage: number = 1;
    imgUrl: string = '';
    recordsPerPage: number = 10;
    maxSize: number = 10;
    records: any = [];

    constructor(private employeeService: EmployeeService, private userRightsService: UserRightsService) {
    }
    ngOnInit(): void {
      this.records = ['20', '50', '100', '200', '250'];
        this.loadLhUserList(0);
    }

    loadLhUserList(page: number) {
        this.employeeService.getAssignLhUser(page, this.recordsPerPage, this.searchText)
            .subscribe(page => this.lhUserPage = page);
    }

    loadData(event: any) {
      console.log('pages ', event.target.value);
      this.recordsPerPage = event.target.value || 10;
      this.employeeService.getAssignLhUser(this.selectedPage - 1, this.recordsPerPage, this.searchText)
        .subscribe(page => this.lhUserPage = page);
    }

    onSelect(page: number): void {
        console.log('selected page : ' + page);
        this.selectedPage = page;
        this.loadLhUserList(page);
    }

    searchLhUsers() {
        console.log(this.searchText);
        this.loadLhUserList(this.selectedPage - 1);
    }

    getImageUrl(event: any) {
        this.imgUrl = event.target.src;
        console.log("image found..." + this.imgUrl);
    }

    sortData(sort: Sort) {
        const data = this.lhUserPage.content.slice();
        if (!sort.active || sort.direction == '') {
          this.lhUserPage.content = data;
          return;
        }
      
        function compare(firstValue, secondValue, isAsc: boolean) {
          return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
        }
      
        this.lhUserPage.content = data.sort((firstValue, secondValue) => {
          const isAsc = sort.direction == 'asc';
          switch (sort.active) {
            case globalConstants.ID:
              return compare(+firstValue.id, +secondValue.id, isAsc);
              case 'regionName':
                return compare(firstValue.regionName, secondValue.regionName, isAsc);
            case 'roleName':
              return compare(firstValue.roleName, secondValue.roleName, isAsc);
            case 'name':
              return compare(firstValue.name, secondValue.name, isAsc);
            case 'email':
              return compare(firstValue.email, secondValue.email, isAsc);
            case 'contactNumber':
              return compare(firstValue.contactNumber, secondValue.contactNumber, isAsc);
              case 'aadharNo':
                return compare(firstValue.aadharNo, secondValue.aadharNo, isAsc);
              case 'empType':
              return compare(firstValue.empType, secondValue.empType, isAsc);
            default:
              return 0;
          }
        });
      }
}
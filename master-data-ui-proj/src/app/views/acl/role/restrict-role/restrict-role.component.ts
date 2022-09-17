import { Component, OnInit } from '@angular/core';
import { RoleService } from '../../services/role.service';
import { Role } from '../../Models/role';
import { ManageRoleService } from '../../services/manage-role.service';
import { Sort } from '@angular/material';
import { globalConstants } from '../../../global/globalConstants';
@Component({
  selector: 'app-restrict-role',
  templateUrl: './restrict-role.component.html',
  styleUrls: ['./restrict-role.component.scss']
})
export class RestrictRoleComponent implements OnInit {
  rolelist: Array<Role> = [];

  constructor(private roleService: RoleService, private manageRoleService: ManageRoleService,
  ) { }

  ngOnInit() {
    this.loadAllRole()
  }


  loadAllRole() {
    this.roleService.GetAllRole().subscribe((data: any) => {
      this.rolelist = data;
      console.log(this.rolelist)
    }, err => { })
  }

  sortData(sort: Sort) {
    const data = this.rolelist.slice();
    if (!sort.active || sort.direction == '') {
      this.rolelist = data;
      return;
    }
  
    function compare(firstValue, secondValue, isAsc: boolean) {
      return (firstValue < secondValue ? -1 : 1) * (isAsc ? 1 : -1);
    }
  
    this.rolelist = data.sort((firstValue, secondValue) => {
      const isAsc = sort.direction == 'asc';
      switch (sort.active) {
          case globalConstants.NAME:
            return compare(firstValue.name, secondValue.name, isAsc);
        case 'description':
          return compare(firstValue.description, secondValue.description, isAsc);
        default:
          return 0;
      }
    });
  }

  // editRole(data)

}

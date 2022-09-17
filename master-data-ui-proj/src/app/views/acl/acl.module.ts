import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListComponent } from './user/list/list.component';
import { AclRoutingModule } from './acl-routing.module';
import { RoleComponent } from './role/role.component';
import { GroupComponent } from './group/group.component';
import { ResourceComponent } from './resource/resource.component';
import { AddUserComponent } from './user/add-user/add-user.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ManageRoleComponent } from './role/manage-role/manage-role.component';
import { RestrictRoleComponent } from './role/restrict-role/restrict-role.component';

import { ProfileComponent } from './profile/profile.component';
import { ModalModule } from 'ngx-bootstrap/modal';
import { GlobalModule } from '../global/global.module';
import { ResourceGroupComponent } from './resource-group/resource-group.component';
import { MatSortModule } from '@angular/material';
import { SearchFilterPipe } from '../pipes/search-filter.pipe';
import { PipeModule } from '../pipes/pipe.module';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { PopoverModule } from 'ngx-bootstrap';


@NgModule({
  declarations: [ListComponent, ProfileComponent, RoleComponent, GroupComponent,
    ResourceComponent, AddUserComponent,
      ManageRoleComponent, RestrictRoleComponent, ResourceGroupComponent,SearchFilterPipe],
  imports: [
    CommonModule,
    AclRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    ModalModule.forRoot(),
    GlobalModule,
    MatSortModule,
    PipeModule,
    PaginationModule.forRoot(),
    PopoverModule.forRoot(),
  ],
  exports: [SearchFilterPipe],

})
export class AclModule { }
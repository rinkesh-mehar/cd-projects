import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListComponent } from './user/list/list.component';
import { RoleComponent } from './role/role.component';
import { GroupComponent } from './group/group.component';
import { ResourceComponent } from './resource/resource.component';
import { AddUserComponent } from './user/add-user/add-user.component';
import { ManageRoleComponent } from './role/manage-role/manage-role.component';
import { ProfileComponent } from './profile/profile.component';
import { RestrictRoleComponent } from './role/restrict-role/restrict-role.component';
import { ResourceGroupComponent } from './resource-group/resource-group.component';
const routes: Routes = [
  {
    path: '',
    data: {
      title: 'ACL'
    },
    children: [
      // uUer Management
      {
        path: 'user',
        redirectTo: 'user'
      },
      {
        path: '',
        redirectTo: 'user'
      },
      {
        path: 'user',
        component: ListComponent,
        data: {
          title: 'List All Users'
        }
      },
      {
        path: 'user/add-user',
        component: AddUserComponent,
        data: {
          title: 'Add User'
        }
      },
      {
        path: 'user/add-user/:id',
        component: AddUserComponent,
        data: {
          title: 'Edit User'
        }
      },
      {
        path: 'user/profile',
        component: ProfileComponent,
        data: {
          title: 'User Profile'
        }
      },
      // Role Management
      {
        path: 'role',
        redirectTo: 'role'
      },
      {
        path: 'role',
        component: RoleComponent,
        data: {
          title: 'List All roles'
        }
      },

      {
        path: 'role/manage-role/:id',
        component: ManageRoleComponent,
        data: {
          title: 'Manage role'
        }
      },
      {
        path: 'role/manage-role',
        component: ManageRoleComponent,
        data: {
          title: 'Manage role'
        }
      },
      {


        path: 'role/restrict-role',
        component: RestrictRoleComponent,
        data: {
          title: 'Manage role'
        }
      },
      // Group Management
      {
        path: 'group',
        redirectTo: 'group'
      },
      {
        path: 'group',
        component: GroupComponent,
        data: {
          title: 'List All group'
        }
      },

      // Resources Management
      {
        path: 'resource',
        redirectTo: 'resource'
      },
      {
        path: 'resource',
        component: ResourceComponent,
        data: {
          title: 'List All resource'
        }
      },
      {
        path: 'resource-group',
        component: ResourceGroupComponent,
        data: {
          title: 'List All Resource Group'
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AclRoutingModule { }

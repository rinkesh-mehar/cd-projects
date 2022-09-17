import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GeoCountryComponent } from './geo-country/geo-country.component';
import { GeoStateComponent } from './geo-state/geo-state.component';
import { GeoRegionComponent } from './geo-region/geo-region.component';
import { GeoDistrictComponent } from './geo-district/geo-district.component';
import { GeoTehsilComponent } from './geo-tehsil/geo-tehsil.component';
import { GeoVillageComponent } from './geo-village/geo-village.component';
import { GeoPanchayatComponent } from './geo-panchayat/geo-panchayat.component';
import { AddGeoCountryComponent } from './add-geo-country/add-geo-country.component';
import { EditGeoCountryComponent } from './edit-geo-country/edit-geo-country.component';
import { AddGeoStateComponent } from './add-geo-state/add-geo-state.component';
import { EditGeoStateComponent } from './edit-geo-state/edit-geo-state.component';
import { AddGeoRegionComponent } from './add-geo-region/add-geo-region.component';
import { EditGeoRegionComponent } from './edit-geo-region/edit-geo-region.component';
import { AddGeoDistrictComponent } from './add-geo-district/add-geo-district.component';
import { EditGeoDistrictComponent } from './edit-geo-district/edit-geo-district.component';
import { AddGeoTehsilComponent } from './add-geo-tehsil/add-geo-tehsil.component';
import { EditGeoTehsilComponent } from './edit-geo-tehsil/edit-geo-tehsil.component';
import { AddGeoVillageComponent } from './add-geo-village/add-geo-village.component';
import { EditGeoVillageComponent } from './edit-geo-village/edit-geo-village.component';
import { AddGeoPanchayatComponent } from './add-geo-panchayat/add-geo-panchayat.component';
import { EditGeoPanchayatComponent } from './edit-geo-panchayat/edit-geo-panchayat.component';
import { AddEditGeoCityComponent } from './geo-city/add-edit-geo-city/add-edit-geo-city.component';
import {AddGeoStateAliasComponent} from './geo-state-alias/add-geo-state-alias.component';
import {AddGeoDistrictAliasComponent} from './geo-district-alias/add-geo-district-alias.component';
import {AddGeoTehsilAliasComponent} from './geo-tehsil-alias/add-geo-tehsil-alias.component';
import {AddGeoVillageAliasComponent} from './geo-village-alias/add-geo-village-alias.component';



const routes: Routes = [
  { 
    path: '',
    data: {
      title: 'Geographical'
    },
    children: [
      {
        path: '',
        redirectTo: 'country'
      },
      {
        path: 'country',
        component: GeoCountryComponent,
        data: {
          title: 'Country'
        }
      },
      {
        path: 'country-add',
        component: AddGeoCountryComponent,
        data: {
          title: 'Add Country'
        }
      },
      {
        path: 'country-edit/:id',
        component: EditGeoCountryComponent,
        data: {
          title: 'Edit Country'
        }
      },
      {
        path: 'state',
        component: GeoStateComponent,
        data: {
          title: 'States'
        }
      },
      {
        path: 'state-add',
        component: AddGeoStateComponent,
        data: {
          title: 'Add State'
        }
      },
      {
        path: 'state-edit/:id',
        component: EditGeoStateComponent,
        data: {
          title: 'Edit State'
        }
      },
      {
        path: 'region',
        component: GeoRegionComponent,
        data: {
          title: 'Regions'
        }
      },
      {
        path: 'region-add',
        component: AddGeoRegionComponent,
        data: {
          title: 'Add Region'
        }
      },
      {
        path: 'region-edit/:id',
        component: EditGeoRegionComponent,
        data: {
          title: 'Edit Region'
        }
      },
      {
        path: 'district',
        component: GeoDistrictComponent,
        data: {
          title: 'Districtes'
        }
      },
      {
        path: 'district-add',
        component: AddGeoDistrictComponent,
        data: {
          title: 'Add District'
        }
      },
      {
        path: 'district-edit/:id',
        component: EditGeoDistrictComponent,
        data: {
          title: 'Edit District'
        }
      },
      {
        path: 'tehsil',
        component: GeoTehsilComponent,
        data: {
          title: 'Tehsil'
        }
      },
      {
        path: 'tehsil-add',
        component: AddGeoTehsilComponent,
        data: {
          title: 'Add Tehsil'
        }
      },
      {
        path: 'tehsil-edit/:id',
        component: EditGeoTehsilComponent,
        data: {
          title: 'Edit Tehsil'
        }
      },
      {
        path: 'village',
        component: GeoVillageComponent,
        data: {
          title: 'Villages'
        }
      },
      {
        path: 'village-add',
        component: AddGeoVillageComponent,
        data: {
          title: 'Add Village'
        }
      },
      {
        path: 'village-edit/:id',
        component: EditGeoVillageComponent,
        data: {
          title: 'Edit Village'
        }
      },
      {
        path: 'panchayat',
        component: GeoPanchayatComponent,
        data: {
          title: 'Panchayat'
        }
      },
      {
        path: 'panchayat-add',
        component: AddGeoPanchayatComponent,
        data: {
          title: 'Add Panchayat'
        }
      },
      {
        path: 'panchayat-edit/:id',
        component: EditGeoPanchayatComponent,
        data: {
          title: 'Edit Panchayat'
        }
      },

      {
        path: 'city',
        component: GeoPanchayatComponent,
        data: {
          title: 'City'
        }
      },
      {
        path: 'city-add',
        component: AddEditGeoCityComponent,
        data: {
          title: 'Add City'
        }
      },
      {
        path: 'city-edit/:id',
        component: AddEditGeoCityComponent,
        data: {
          title: 'Edit City'
        }
      },
      {
        path: 'state-alias',
        component: AddGeoStateAliasComponent,
        data: {
          title: 'States Alias'
        }
      },
      {
        path: 'district-alias',
        component: AddGeoDistrictAliasComponent,
        data: {
          title: 'Districts Alias'
        }
      },
      {
        path: 'tehsil-alias',
        component: AddGeoTehsilAliasComponent,
        data: {
          title: 'Tehsil Alias'
        }
      },
      {
        path: 'village-alias',
        component: AddGeoVillageAliasComponent,
        data: {
          title: 'Village Alias'
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GeoRoutingModule {}

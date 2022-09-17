import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GeoRoutingModule } from './geo-routing.module';
import { GeoCountryComponent } from './geo-country/geo-country.component';
import { GeoStateComponent } from './geo-state/geo-state.component';
import { GeoRegionComponent } from './geo-region/geo-region.component';
import { GeoDistrictComponent } from './geo-district/geo-district.component';
import { GeoTehsilComponent } from './geo-tehsil/geo-tehsil.component';
import { GeoVillageComponent } from './geo-village/geo-village.component';
import { GeoPanchayatComponent } from './geo-panchayat/geo-panchayat.component';
import { AddGeoCountryComponent } from './add-geo-country/add-geo-country.component';
import { EditGeoCountryComponent } from './edit-geo-country/edit-geo-country.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
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

import { GlobalModule } from '../global/global.module';
import { PipeModule } from '../pipes/pipe.module';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import {MatSortModule} from '@angular/material';
import { AddEditGeoCityComponent } from './geo-city/add-edit-geo-city/add-edit-geo-city.component';
import { GeoCityComponent } from './geo-city/geo-city/geo-city.component';
import {AddGeoStateAliasComponent} from './geo-state-alias/add-geo-state-alias.component';
import {AddGeoDistrictAliasComponent} from './geo-district-alias/add-geo-district-alias.component';
import {AddGeoTehsilAliasComponent} from './geo-tehsil-alias/add-geo-tehsil-alias.component';
import {AddGeoVillageAliasComponent} from './geo-village-alias/add-geo-village-alias.component';
import { RegionalModule } from '../regional/regional.module';


@NgModule({
  declarations: [GeoCountryComponent, GeoStateComponent,GeoStateComponent, GeoRegionComponent, GeoDistrictComponent,
      GeoTehsilComponent, GeoVillageComponent, GeoPanchayatComponent, AddGeoCountryComponent, EditGeoCountryComponent,
      AddGeoStateComponent, EditGeoStateComponent, AddGeoRegionComponent, EditGeoRegionComponent,
      AddGeoDistrictComponent, EditGeoDistrictComponent, AddGeoTehsilComponent, EditGeoTehsilComponent,
      AddGeoVillageComponent, EditGeoVillageComponent, AddGeoPanchayatComponent, EditGeoPanchayatComponent,
      AddEditGeoCityComponent,GeoCityComponent, AddGeoStateAliasComponent, AddGeoDistrictAliasComponent,
      AddGeoTehsilAliasComponent, AddGeoVillageAliasComponent ],
    imports: [
        CommonModule,
        GeoRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        GlobalModule,
        PipeModule,
        PaginationModule.forRoot(),
        MatSortModule,
        RegionalModule
    ]
})
export class GeoModule { }

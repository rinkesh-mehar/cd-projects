import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GlobalModule} from '../global/global.module';
import {PipeModule} from '../pipes/pipe.module';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {WeatherRoutingModule} from './weather-routing.module';
import {ReactiveFormsModule, FormsModule} from '@angular/forms';
import {AddEditCondusiveWeatherComponent} from '../agri/condusive-weather/add-edit-condusive-weather/add-edit-condusive-weather.component';
import {WeatherDataComponent} from './weather-data/weather-data.component';
import {MatSortModule} from '@angular/material';

@NgModule({
    declarations: [
        WeatherDataComponent,

    ],
    imports: [
        CommonModule,
        WeatherRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        GlobalModule,
        PipeModule,
        PaginationModule.forRoot(),
        MatSortModule,
    ]
})
export class WeatherModule {
}

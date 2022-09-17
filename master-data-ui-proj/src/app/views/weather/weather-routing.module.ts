import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { WeatherDataComponent } from './weather-data/weather-data.component';



const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Weather'
    },
    children: [
      
      {
        path: 'weather-data',
        component: WeatherDataComponent,
        data: {
          title: 'Weather Data'
        }
      },
     
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WeatherRoutingModule {}

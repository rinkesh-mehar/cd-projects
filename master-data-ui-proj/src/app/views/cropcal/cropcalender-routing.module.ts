import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CropCalendarComponent } from './crop-calendar/crop-calendar.component';




const routes: Routes = [
  {
    path: '',
    data: {
      title: 'CropCalendar'
    },
    children: [
      {
        path: '',
        redirectTo: 'cropcalendar'
      },
      {
        path: 'crop-calendar',
        component: CropCalendarComponent,
        data: {
          title: 'CropCalendar'
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CropCalendarRoutingModule { }

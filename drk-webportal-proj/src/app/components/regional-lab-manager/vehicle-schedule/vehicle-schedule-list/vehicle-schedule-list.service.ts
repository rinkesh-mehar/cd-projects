import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient } from '@angular/common/http';
import 'rxjs/Rx';
import { VehicleScheduleCalendar} from './vehicle-schedule-list.model'
import { environment } from '../../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class VehicleScheduleListService {

  constructor(private http: HttpClient) { }

   // Method to GET vehicle schedule list
   public getvehicleScheduleList(regionId, startDate, endDate): Observable<VehicleScheduleCalendar[]> {
    return this.http.get<any>(environment.VehicleMasterURL+'listVechicalSchedule/'+regionId,{params:{"startDate":startDate, "endDate":endDate}})
        .map((response) => response)
        .catch((error: any) => {
            return Observable.throw(error);
        });

    }
}

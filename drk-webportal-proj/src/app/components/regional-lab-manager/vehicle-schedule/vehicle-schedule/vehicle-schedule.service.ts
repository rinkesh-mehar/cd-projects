import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient} from '@angular/common/http';
import 'rxjs/Rx';
import { VechicleSchedule, VechicleScheduleForm } from './vehicle-schedule.model';
import { TileVillage, statusMessage } from './vehicle-schedule.model';
import { environment } from '../../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class VehicleScheduleService {

  constructor(private http: HttpClient) { }

 
  public savevehicleschedule(data:VechicleScheduleForm): Observable<statusMessage> {
    //return this.http.post<statusMessage>('api/adduser', data)
    return this.http.post<statusMessage>(environment.VehicleMasterURL+'saveVechicalSchedule/', data)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  public getVillagelist(tileId :number, cropDataApiKey): Observable<TileVillage[]> {
    return this.http.get<any>(environment.GSTMURL+'get-villages-by-sub-region-id/'+tileId+'?apiKey='+cropDataApiKey+'')
    .map((response) =>  response)
    .catch((error: any) => {
        return Observable.throw(error);
    });
}

public getRegionTemplete(regionId: number, cropDataApiKey): Observable<any> {
  return this.http.get<any>(environment.GSTMURL+'tile-assignment/'+regionId+'?apiKey='+cropDataApiKey+'')
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

   //Method to getRegion user
  public getVinnumberlist(regionId: Number): Observable<VechicleSchedule[]> {
    return this.http.get<any>(environment.VehicleMasterURL+'vechicalNumber/'+regionId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });

  }


}

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient} from '@angular/common/http';
import 'rxjs/Rx';
import { vehicleDetailsModel, statusMessage } from './vehicle-details.model';
import { environment } from '../../../../../environments/environment';

@Injectable()
export class vehicleDetailsService {

    constructor(private http: HttpClient) {
    }

    public submitVehicleDetailsForm(data: vehicleDetailsModel): Observable<statusMessage> {
        return this.http.post<statusMessage>(environment.VehicleMasterURL+'addEditVehicleDetails', data)
          .map((response) => response)
          .catch((error: any) => {
            return Observable.throw(error);
          });
      }
    // Method to get Non Technical List
    public getVehicleDetails(id): Observable<any> {
        return this.http.get<any>(environment.VehicleMasterURL+'viewVehicleDetalis/'+id)
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }
    
}
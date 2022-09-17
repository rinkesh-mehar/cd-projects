import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient} from '@angular/common/http';
import 'rxjs/Rx';
import { VehicleMasterModel } from './vehicle-master.model';
import { environment } from '../../../../../environments/environment';


@Injectable()
export class VehicleMasterService {

    constructor(private http: HttpClient) {
    }

    // Method to get Vehicle Master List
    public getVehicleMaster(userId, searchKeyward): Observable<VehicleMasterModel[]> {
        return this.http.get<any>(environment.VehicleMasterURL+'vehicleMasterList/'+userId, {params:{"searchKeyward":searchKeyward}})
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }

    

}
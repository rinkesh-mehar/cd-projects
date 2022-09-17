import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient} from '@angular/common/http';
import 'rxjs/Rx';
import { environment } from '../../../../../environments/environment';


@Injectable()
export class MonitorCashService {

    constructor(private http: HttpClient) {
    }


    public searchData(id, search): Observable<any> {
        return this.http.get<any>(environment.caseMoURL + "getmonitorcashlist/" + id + "/" + search)
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }

    public getFLSMonitorCashList(flsId): Observable<any[]> {
        return this.http.get<any>(environment.caseMoURL + "getflsmonitorcashlist/" + flsId)
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }
    public setRecived(flsId, rmlId): Observable<any> {
        return this.http.get<any>(environment.caseMoURL + "setrecived/" + flsId + "/" + rmlId)
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }
}
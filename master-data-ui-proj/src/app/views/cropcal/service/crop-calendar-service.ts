// crop-calendar-service

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { GeoState } from '../../geo/models/GeoState';
import { AgriSeason } from '../../agri/models/AgriSeason';
import { AgriCommodity } from '../../agri/models/AgriCommodity';
import { AgriVariety } from '../../agri/models/Agrivariety';
import { retry, catchError } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../general/models/ResponseMessage';

@Injectable({
    providedIn: 'root'
})
export class CropCalendarService {

    // Base url
    baseUrl = environment.apiUrl + '/crop/calendar';

    constructor(private http: HttpClient) { }

    // Http Headers
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    }

    // POST
    GetCropCalendarList(param): Observable<any> {

        // let url = "http://192.168.0.47:8080/crop/calendar/list?regionId=1&commodityId=1&varietyId=19&stateCode=29&seasonId=1";

        let url = this.baseUrl;
        url = url + "/list?";
        url = url + "&commodityId=" + param.commodityId;
        url = url + "&varietyId=" + param.varietyId;
        url = url + "&stateCode=" + param.stateCode;
        url = url + "&seasonId=" + param.seasonId;

        return this.http.get<any>(url)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            )
    }

    // GET
  GetAvailableStates(): Observable<GeoState> {
    return this.http.get<GeoState>(this.baseUrl + '/getAvailableStateList')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


// GET
GetAvailableSeason(stateCode,commodityId,varietyId): Observable<AgriSeason> {
    return this.http.get<AgriSeason>(this.baseUrl + '/getAvailableSeasonList?stateCode='+stateCode+'&commodityId='+commodityId+'&varietyId='+varietyId)
        .pipe(
        retry(1),
        catchError(this.errorHandl)
        )
    }


// GET
getCommodityByState(stateCode): Observable<AgriCommodity> {
    return this.http.get<AgriCommodity>(this.baseUrl + '/getCommodityByStateList?stateCode='+stateCode)
        .pipe(
        retry(1),
        catchError(this.errorHandl)
        )
    }

    // GET
    getVarietyByStateAndCommodity(stateCode, commodityId): Observable<AgriVariety> {
    return this.http.get<AgriVariety>(this.baseUrl + '/getVarietyByStateAndCommodity?stateCode='+stateCode+'&commodityId='+commodityId)
        .pipe(
        retry(1),
        catchError(this.errorHandl)
        )
    }


    // Error handling
    errorHandl(error) {
        let errorMessage = '';
        if (error.error instanceof ErrorEvent) {
            // Get client-side error
            errorMessage = error.error.message;
        } else {
            // Get server-side error
            errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
        }
        console.log(errorMessage);
        return throwError(errorMessage);
    }
}

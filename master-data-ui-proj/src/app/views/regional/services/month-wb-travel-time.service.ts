import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {PageRegionMonthWBTravelTime} from '../models/PageRegionMonthWBTravelTime';
import {catchError, map, retry} from 'rxjs/operators';
import {ResponseMessage} from '../models/ResponseMessage';

@Injectable({
    providedIn: 'root'
})
export class MonthWbTravelTimeService {
    // Base url
    baseUrl = environment.apiUrl + '/regional/monthly-wb-travel-time';
    maxSize: number = 10;

    constructor(private http: HttpClient) {
    }
    httpOptions = {
        headers: new HttpHeaders({
            'Content-type': 'application/json'
        })
    };

    getMonthWbTravelTimeWithPage(page: number, rowSize: number, searchText, isValid: number): Observable<PageRegionMonthWBTravelTime> {
        this.maxSize = rowSize || this.maxSize;
        return this.http.get<PageRegionMonthWBTravelTime>(this.baseUrl + '?page=' + page + '&size=' + this.maxSize+ '&isValid=' + isValid
            + '&searchText=' + searchText).
            pipe(map(response => {
            return response;
        }));
    }
    storeRegionalMonthlyTravelTime(formRegionMonthTravelTime): Observable<ResponseMessage> {
        return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(formRegionMonthTravelTime), this.httpOptions)
            .pipe(retry(1),
                catchError(this.errorHandl));
    }
    updateRegionalMonthlyTravelTime(id, data): Observable<ResponseMessage> {
        return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            )
    }
    getRegionalMonthlyTravelTimeById(id): Observable<any> {
        return this.http.get<any>(this.baseUrl + '/' + id)
            .pipe(retry(1),
                catchError(this.errorHandl));
    }
    getGeoRegion(): Observable<any>{
        return this.http.get<any>(environment.apiUrl + '/geo/region/list')
            .pipe(retry(1),
                catchError(this.errorHandl));
    }
    getUnitTypeList(): Observable<any>{
        return this.http.get<any>(environment.apiUrl + '/weather-based-travel-time/list')
            .pipe(retry(1),
                catchError(this.errorHandl));
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

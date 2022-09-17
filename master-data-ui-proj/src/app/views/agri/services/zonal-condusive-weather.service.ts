import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {retry, catchError, map} from 'rxjs/operators';
import {environment} from '../../../../environments/environment';
import {ResponseMessage} from '../../general/models/ResponseMessage';
import {ZonalCondusiveWeather} from '../models/ZonalCondusiveWeather';
import {PageZonalCondusiveWeather} from '../models/PageZonalCondusiveWeather';


@Injectable({
    providedIn: 'root'
})
export class ZonalCondusiveWeatherService {


    // Base url
    baseUrl = environment.apiUrl + '/zonal/conducive-weather';
    maxSize: number = 10;


    constructor(private http: HttpClient) {
    }

    // Http Headers
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    // POST
    CreateCondusiveWeather(data): Observable<ResponseMessage> {
        return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    //  GET By ID
    GetCondusiveWeather(id): Observable<ZonalCondusiveWeather> {
        return this.http.get<ZonalCondusiveWeather>(this.baseUrl + '/' + id)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // For-Pagination
    getPageZonalCondusiveWeather(page: number, rowSize: number, searchText, isValid: number,missing): Observable<PageZonalCondusiveWeather> {
        this.maxSize = rowSize || this.maxSize;
        const url = this.baseUrl + '?page=' + page + '&size=' + this.maxSize + '&isValid=' + isValid + '&searchText=' + searchText + "&missing=" + missing;
        return this.http.get<PageZonalCondusiveWeather>(url)
            .pipe(
                map(response => {
                    const data = response;
                    console.log(data.content);
                    return data;
                }));
    }

    // Get
    GetAllCondusiveWeatherByCommodityId(commodityId): Observable<ZonalCondusiveWeather> {
        return this.http.get<ZonalCondusiveWeather>(this.baseUrl + '/commodity-id/' + commodityId)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

// get
    GetAllCondusiveWeatherByStressTypeId(stressTypeId): Observable<ZonalCondusiveWeather> {
        return this.http.get<ZonalCondusiveWeather>(this.baseUrl + '/stressType-id/' + stressTypeId)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

// Get
    GetAllCondusiveWeatherByStressId(stressId): Observable<ZonalCondusiveWeather> {
        return this.http.get<ZonalCondusiveWeather>(this.baseUrl + '/stress-id/' + stressId)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

// Get
    GetAllCondusiveWeatherByWeatherParamsId(weatherParamId): Observable<ZonalCondusiveWeather> {
        return this.http.get<ZonalCondusiveWeather>(this.baseUrl + '/weather-param-id/' + weatherParamId)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    getCondusiveWeatherByCommodityIdAndStressTypeId(commodityId, stressTypeId): Observable<ZonalCondusiveWeather> {
        return this.http.get<ZonalCondusiveWeather>(this.baseUrl + '/' + commodityId + '/' + stressTypeId + '/')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    getStressByCommodityIdAndStressTypeId(commodityId, stressTypeId): Observable<ZonalCondusiveWeather> {
        return this.http.get<ZonalCondusiveWeather>(this.baseUrl + '/stress-list/' + commodityId + '/' + stressTypeId + '/')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // GET
    GetAllCondusiveWeather(): Observable<ZonalCondusiveWeather> {
        return this.http.get<ZonalCondusiveWeather>(this.baseUrl + '/list')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // //For-Pagination
    // getPageAgriCondusiveWeather(page: number): Observable<PageAgriCondusiveWeather> {
    //   var url = this.baseUrl + "?page=" + page + "&size=" + environment.pageSize;
    //   return this.http.get<PageAgriCondusiveWeather>(url)
    //     .pipe(
    //       map(response => {
    //         const data = response;
    //         //console.log(data.content);
    //         return data;
    //       }));
    // }

    // PUT
    UpdateCondusiveWeather(id, data): Observable<ResponseMessage> {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/update', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // DELETE
    DeleteCondusiveWeather(id) {
        return this.http.delete<ResponseMessage>(this.baseUrl + '/' + id + '/delete', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // Reject
    RejectCondusiveWeather(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/reject', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    //  Finalize
    FinalizeCondusiveWeather(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/final-approve', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }


    // Approve
    ApproveCondusiveWeather(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/primary-approve', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // POST
  moveToMaster(id): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/move-to-master/' + id, this.httpOptions)
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

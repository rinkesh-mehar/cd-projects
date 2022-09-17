import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {retry, catchError, map} from 'rxjs/operators';
import {ResponseMessage} from '../models/ResponseMessage';
import {GeoTehsil} from '../models/GeoTehsil';
import {GeoDistrictService} from './geo-district.service';
import {GeoStateService} from './geo-state.service';
import {environment} from '../../../../environments/environment';
import {PageGeoTehsil} from '../models/PageGeoTehsil';
import {Districts} from '../geo-district-alias/model/Districts';
import {Tehsils} from '../geo-tehsil-alias/model/Tehsils';

@Injectable({
    providedIn: 'root'
})
export class GeoTehsilService {

    // Base url
    baseUrl = environment.apiUrl + '/geo/tehsil';
    maxSize: number = 10;


    constructor(private http: HttpClient,
                geoDistrictService: GeoDistrictService,
                geoStateService: GeoStateService) {
    }

    storeDistrict = [];

    // Http Headers
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    addToStore(tehsils: Tehsils[]) {
        this.storeDistrict.push(tehsils);
    }

    // POST
    CreateTehsil(data): Observable<ResponseMessage> {
        return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // GET By ID
    GetTehsil(id): Observable<GeoTehsil> {
        return this.http.get<GeoTehsil>(this.baseUrl + '/' + id)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // GET
    GetAllTehsil(): Observable<GeoTehsil> {
        return this.http.get<GeoTehsil>(this.baseUrl + '/list')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // GET
    GetAllTehsilByDistrictCode(districtCode): Observable<any> {
        return this.http.get<any>(this.baseUrl + '/district-code/' + districtCode)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    //For-Pagination
    getPageGeoTehsil(page: number, rowSize: number, searchText): Observable<PageGeoTehsil> {
        this.maxSize = rowSize || this.maxSize;
        var url = this.baseUrl + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
        return this.http.get<PageGeoTehsil>(url)
            .pipe(
                map(response => {
                    const data = response;
                    //console.log(data.content);
                    return data;
                }));
    }

    //For-Pagination
    getPageGeoTehsilAlias(page: number): Observable<any> {
        var url = this.baseUrl + '/alias' + '?page=' + page + '&size=' + environment.pageSize;
        return this.http.get<any>(url)
            .pipe(
                map(response => {
                    const data = response;
                    //console.log(data.content);
                    return data;
                }));
    }

    // POST
    saveAliasTehsil(data): Observable<ResponseMessage> {
        return this.http.post<ResponseMessage>(this.baseUrl + '/alias/tag', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // PUT
    UpdateTehsil(id, data): Observable<ResponseMessage> {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/update', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // DELETE
    DeleteTehsil(id) {
        return this.http.delete<ResponseMessage>(this.baseUrl + '/' + id + '/delete', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }


    // Reject
    RejectTehsil(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/reject', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    //  Finalize
    FinalizeTehsil(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/final-approve', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }


    // Approve
    ApproveTehsil(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/primary-approve', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
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

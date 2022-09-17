import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {retry, catchError, map} from 'rxjs/operators';
import {ResponseMessage} from '../models/ResponseMessage';
import {GeoVillage} from '../models/GeoVillage';
import {environment} from '../../../../environments/environment';
import {PageGeoVillage} from '../models/PageGeoVillage';
import {Villages} from '../geo-village-alias/model/Villages';
import {VillageAlias} from '../geo-village-alias/model/VillageAlias';

@Injectable({
    providedIn: 'root'
})
export class GeoVillageService {


    // Base url
    baseUrl = environment.apiUrl + '/geo/village';
    maxSize: number = 10;


    constructor(private http: HttpClient) {
    }

    storeVillages = [];

    // Http Headers
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    // store all village
    addToStore(villages: Villages[]) {
        this.storeVillages.push(villages);
    }

    // POST
    CreateVillage(data): Observable<ResponseMessage> {
        return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // GET By ID
    GetVillage(id): Observable<GeoVillage> {
        return this.http.get<GeoVillage>(this.baseUrl + '/' + id)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // GET
    GetAllVillage(): Observable<GeoVillage> {
        return this.http.get<GeoVillage>(this.baseUrl + '/list')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    //For-Pagination
    getPageGeoVillage(page: number, rowSize: number, searchText): Observable<PageGeoVillage> {
        this.maxSize = rowSize || this.maxSize;
        var url = this.baseUrl + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
        return this.http.get<PageGeoVillage>(url)
            .pipe(
                map(response => {
                    const data = response;
                    //console.log(data.content);
                    return data;
                }));
    }

    //For-Pagination
    getPageGeoVillageAlias(page: number): Observable<VillageAlias> {
        var url = this.baseUrl + '/alias' + '?page=' + page + '&size=' + environment.pageSize;
        return this.http.get<VillageAlias>(url)
            .pipe(
                map(response => {
                    const data = response;
                    //console.log(data.content);
                    return data;
                }));
    }

    // GET
    GetAllVillageByTehsil(tehsilCode): Observable<any> {
        return this.http.get<any>(this.baseUrl + '/list/' + tehsilCode)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // POST
    saveAliasVillage(data): Observable<ResponseMessage> {
        return this.http.post<ResponseMessage>(this.baseUrl + '/alias/tag', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // PUT
    UpdateVillage(id, data): Observable<ResponseMessage> {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/update', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // DELETE
    DeleteVillage(id) {
        return this.http.delete<ResponseMessage>(this.baseUrl + '/' + id + '/delete', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    //  Finalize
    FinalizeVillage(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/final-approve', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // Reject
    RejectVillage(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/reject', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // Approve
    ApproveVillage(id) {
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

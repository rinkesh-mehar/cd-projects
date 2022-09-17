import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {environment} from '../../../../environments/environment';
import {catchError, map, retry} from 'rxjs/operators';
import {PageRegionTerrain} from '../models/PageTerrain';
import {ResponseMessage} from '../../general/models/ResponseMessage';

@Injectable({
    providedIn: 'root'
})
export class RegionTerrainService {

    baseUrl = environment.apiUrl + '/regional/general-terrain';
    maxSize: number = 10;

    constructor(private http: HttpClient) {
    }

    // Http Headers
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    // For-Pagination
    getPageTerrain(page: number, rowSize: number, searchText, isValid: number): Observable<PageRegionTerrain> {
        this.maxSize = rowSize || this.maxSize;
        const url = this.baseUrl + '?page=' + page + '&size=' + this.maxSize + '&isValid=' + isValid + '&searchText=' + searchText;
        return this.http.get<PageRegionTerrain>(url)
            .pipe(
                map(response => {
                    const data = response;
                    return data;
                }));
    }

    // GET By ID
    getTerrainById(id): Observable<any> {
        return this.http.get<any>(this.baseUrl + '/' + id)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    getRegion(): Observable<any> {
        return this.http.get<any>(environment.apiUrl + '/geo/region/list')
            .pipe(retry(1),
                catchError(this.errorHandle));
    }

    addRegionTerrain(data): Observable<ResponseMessage> {
        return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    UpdateRegionTerrain(id, data): Observable<ResponseMessage> {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/update', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    ApproveRegionTerrain(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/primary-approve', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    // DELETE
    DeleteRegionTerrain(id) {
        return this.http.delete<ResponseMessage>(this.baseUrl + '/' + id + '/delete', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    // Reject
    RejectRegionTerrain(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/reject', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    //  Finalize
    FinalizeRegionTerrain(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/final-approve', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    errorHandle(error) {
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

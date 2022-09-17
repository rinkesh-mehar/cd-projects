import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {throwError} from 'rxjs/internal/observable/throwError';
import {catchError, map, retry} from 'rxjs/operators';
import {environment} from '../../../../environments/environment';
import { PageRegionalConnectivity } from '../models/PageRegionalConnectivity';
import {RegionalConnectivity} from '../models/RegionalConnectivity';
import {ResponseMessage} from '../models/ResponseMessage';

@Injectable({
    providedIn: 'root'
})
export class RegionalConnectivityService {

    // Base url
    baseUrl = environment.apiUrl + '/regional/connectivity';
    maxSize: number = 10;

    constructor(private httpClient: HttpClient) {
    }

    // Http Headers
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    getRegionalConnectivityList(): Observable<RegionalConnectivity> {

        return this.httpClient.get<RegionalConnectivity>
        (this.baseUrl + '/list', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }


//GET
getRegionalConnectivityPagenatedList(page: number, rowSize: number, searchText): Observable<PageRegionalConnectivity> {
    this.maxSize = rowSize || this.maxSize;
    let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
    return this.httpClient.get<PageRegionalConnectivity>(url)
        .pipe(
            map(response => {
                const data = response;
                return data;
            }));
  }

    saveRegionalConnectivityData(data: any): Observable<any> {

        return this.httpClient.post
        (this.baseUrl + '/addRegionalConnect', JSON.stringify(data), this.httpOptions)
            .pipe(retry(1),
                catchError(this.errorHandl));
    }

    getRegionalConnectDataById(id): Observable<RegionalConnectivity> {

        return this.httpClient.get<RegionalConnectivity>
        (this.baseUrl + '/getRegionalData/' + id, this.httpOptions)
            .pipe(retry(1),
                catchError(this.errorHandl));

    }

    updateRegionalData(id, data): Observable<any> {

        return this.httpClient.put<RegionalConnectivity>
        (this.baseUrl + '/updateRegionalData/' + id, data, this.httpOptions)
            .pipe(retry(1),
                catchError(this.errorHandl));
    }

    // PUT
    UpdateVariety(id, data): Observable<ResponseMessage> {
        return this.httpClient.put<ResponseMessage>(this.baseUrl + '/' + id + '/update', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // DELETE
    DeleteVariety(id) {
        return this.httpClient.delete<ResponseMessage>(this.baseUrl + '/' + id + '/delete', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // Reject
    RejectVariety(id) {
        return this.httpClient.put<ResponseMessage>(this.baseUrl + '/' + id + '/reject', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    //  Finalize
    FinalizeVarietys(id) {
        return this.httpClient.put<ResponseMessage>(this.baseUrl + '/' + id + '/final-approve', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }


    // Approve
    ApproveVariety(id) {
        return this.httpClient.put<ResponseMessage>(this.baseUrl + '/' + id + '/primary-approve', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }



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

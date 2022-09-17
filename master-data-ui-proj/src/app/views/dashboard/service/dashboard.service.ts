import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {AgriActivity} from '../../agri/models/AgriActivity';
import {catchError, retry} from 'rxjs/operators';
import {ResponseMessage} from '../../agri/models/ResponseMessage';

@Injectable({
    providedIn: 'root'
})
export class DashboardService {

    // Base url
    baseUrl = environment.apiUrl + '/dashboard';

    constructor(private http: HttpClient) {

    }

    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    // getLeadData(data: any): Observable<any> {
    //     return this.http.get<any>(this.baseUrl + '/lead-data' + '?edit' + data)
    //         .pipe(
    //             retry(1),
    //             catchError(this.errorHandle)
    //         );
    // }

    // getLeadData(data: any): Observable<any> {
    //     return this.http.post<any>(this.baseUrl + '/lead-data', JSON.stringify(data), this.httpOptions)
    //         .pipe(
    //             retry(1),
    //             catchError(this.errorHandle)
    //         );
    // }

    // getOverallChart(requestData: any): Observable<any> {
    //     return this.http.post<any>(this.baseUrl + '/over-data', JSON.stringify(requestData), this.httpOptions)
    //         .pipe(
    //             retry(1),
    //             catchError(this.errorHandle)
    //         );
    // }

    // getHeaderChart(editData): Observable<any> {
    //     return this.http.post<any>(this.baseUrl + '/header-data', JSON.stringify(editData), this.httpOptions)
    //         .pipe(
    //             retry(1),
    //             catchError(this.errorHandle)
    //         );
    // }

    // loadRegion(regionList): Observable<any> {
    //     return this.http.get<any>(this.baseUrl + '/region' + '?regionId' + regionList)
    //         .pipe(
    //             retry(1),
    //             catchError(this.errorHandle)
    //         );
    // }

    errorHandle(error) {
        let errorMessage = '';
        if(error.error instanceof ErrorEvent) {
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

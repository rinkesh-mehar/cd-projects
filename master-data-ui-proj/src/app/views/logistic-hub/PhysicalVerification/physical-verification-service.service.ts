import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, retry} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class PhysicalVerificationServiceService {

    private baseUrl = environment.logisticHubApiUrl + '/document';
    private maxSize: number = 10;

    // Http Headers
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    constructor(private httpClient: HttpClient) {

    }

    getLHDetails(page: number, rowSize: number, searchText): Observable<any> {
        this.maxSize = rowSize || this.maxSize;
        return this.httpClient.get<any>(this.baseUrl + '/pv-complete-warehouse' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText)
            .pipe(retry(1),
                catchError(this.errorHandl));
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

import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {catchError, retry} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class OperationService {

    baseUrl = environment.apiUrl;

    constructor(private httpClient: HttpClient) {
    }

    getOperationList(): Observable<any> {
        return this.httpClient.get<any>(this.baseUrl + '/operation-list')
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

    getSumCount() {
        return this.httpClient.get<any>(this.baseUrl + '/sum-count')
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

}

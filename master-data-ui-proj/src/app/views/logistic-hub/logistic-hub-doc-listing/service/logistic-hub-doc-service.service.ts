import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {catchError, retry} from 'rxjs/operators';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../../../../environments/environment';
import {ResponseMessage} from '../../../agri/models/ResponseMessage';

@Injectable({
    providedIn: 'root'
})
export class LogisticHubDocServiceService {

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
        return this.httpClient.get<any>(this.baseUrl + '/kyc-verification' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText)
            .pipe(retry(1),
                catchError(this.errorHandl));
    }

    getRejectionReason(): Observable<any> {
        return this.httpClient.get<any>(this.baseUrl + '/rejection-reason')
            .pipe(retry(1),
                catchError(this.errorHandl));
    }

    getLHDetailsById(id: any, flag: number): Observable<any> {
        return this.httpClient.get<any>(this.baseUrl + '/lh-detail/' + id + '/' + flag)
            .pipe(retry(1),
                catchError(this.errorHandl));
    }

    approveAllKYCDocument(data): Observable<any> {
        return this.httpClient.post<any>(this.baseUrl + '/lh-approve-all/', JSON.stringify(data), this.httpOptions)
            .pipe(retry(1),
                catchError(this.errorHandl));
    }

    rejectAllKYVDocument(data): Observable<any> {
        return this.httpClient.post<any>(this.baseUrl + '/lh-reject-all/', JSON.stringify(data), this.httpOptions)
            .pipe(retry(1),
                catchError(this.errorHandl));
    }

    rejectKYVDocument(data): Observable<any> {
        return this.httpClient.post<any>(this.baseUrl + '/lh-reject/', JSON.stringify(data), this.httpOptions)
            .pipe(retry(1),
                catchError(this.errorHandl));
    }

    approveKYCDocument(id: any): Observable<any> {
        console.log('lt approve uri', this.baseUrl + '/lh-approve/',id);
        return this.httpClient.get<any>(this.baseUrl + '/lh-approve/' + id)
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

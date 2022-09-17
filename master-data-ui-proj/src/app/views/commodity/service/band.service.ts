import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, retry} from 'rxjs/operators';
import {ResponseMessage} from '../../agri/models/ResponseMessage';

@Injectable({
    providedIn: 'root'
})

export class BandService {

    // Base url
    baseUrl = environment.apiUrl + '/commodity';

    constructor(private httpClient: HttpClient) {

    }

    // Http Headers
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    /*Update Band*/
    updateBand(id, data): Observable<ResponseMessage> {
        return this.httpClient.put<ResponseMessage>(this.baseUrl + '/updateBand?id=' + id, JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    /*Delete band  */
    deleteBand(id): Observable<ResponseMessage> {
        return this.httpClient.put<ResponseMessage>(this.baseUrl + '/delete-band?id=' + id, this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    getBandList(): Observable<any> {
        return this.httpClient.get<any>(this.baseUrl + '/get-band-list')
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    getBandById(id): Observable<any> {
        return this.httpClient.get<any>(this.baseUrl + '/getBandById/' + id)
            .pipe(
                catchError(this.errorHandle)
            );
    }

    addBand(data): Observable<ResponseMessage> {
        return this.httpClient.post<ResponseMessage>(this.baseUrl + '/addBand', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }


    // Error handling
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

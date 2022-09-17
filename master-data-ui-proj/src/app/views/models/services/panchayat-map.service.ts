import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, retry} from 'rxjs/operators';
import {ResponseMessage} from '../../geo/models/ResponseMessage';
import { PagePanchyatMap } from '../model/page-panchyat-map';

@Injectable({
    providedIn: 'root'
})
export class PanchayatMapService {
    baseUrls = environment.apiUrl;
    maxSize: number = 10;

    constructor(private http: HttpClient) {
    }

    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    getPanchayatExistingRegion(page: number, rowSize: number, searchText: string,isValid: number,missing): Observable<PagePanchyatMap> {
        console.log("missing : " + missing);
        this.maxSize = rowSize || this.maxSize;
        return this.http.get<PagePanchyatMap>(this.baseUrls + '/panchayat-region' + '?page=' + page + '&size=' + this.maxSize  + '&isValid=' + isValid + '&searchText=' + searchText + '&missing=' + missing)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    addPanchayat(data): Observable<ResponseMessage> {
        return this.http.post<ResponseMessage>(this.baseUrls + '/add-panchayat', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

     // POST
  moveToMaster(id): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrls + '/move-to-master/' + id, this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
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

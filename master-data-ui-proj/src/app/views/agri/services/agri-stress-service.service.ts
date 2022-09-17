import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {ResponseMessage} from '../models/ResponseMessage';
import {catchError, map, retry} from 'rxjs/operators';
import {PageAgriStress} from '../models/PageAgriStress';
import {ZonalStressDuration} from '../models/ZonalStressDuration';
import {datepickerAnimation} from 'ngx-bootstrap/datepicker/datepicker-animations';

@Injectable({
    providedIn: 'root'
})
export class AgriStressServiceService {

    // Base url
    baseUrl = environment.apiUrl + '/agri/stress';
    maxSize: number = 10;

    constructor(private http: HttpClient) {
    }

    // Http Headers
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };


    CreateAgriStress(data: any) {
        return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }


    UpdateAgriStress(id: any, data: any) {
        console.log('update data ' , this.baseUrl + '/' + id + '/update', JSON.stringify(data));
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/update', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    GetAgriStressByID(id: any) {
        return this.http.get<ResponseMessage>(this.baseUrl + '/' + id)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    GetAllStress(): Observable<ZonalStressDuration> {
        return this.http.get<ZonalStressDuration>(this.baseUrl + '/list')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            )
    }

    getPageAgriStress(page: number, rowSize: number, searchText): Observable<PageAgriStress> {
        this.maxSize = rowSize || this.maxSize;
        var url = this.baseUrl + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
        return this.http.get<PageAgriStress>(url)
            .pipe(
                map(response => {
                    const data = response;
                    return data;
                }));
    }

    // DELETE
    DeleteStress(id) {
        return this.http.delete<ResponseMessage>(this.baseUrl + '/' + id + '/delete', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // Reject
    RejectStress(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/reject', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }


    //  Finalize
    FinalizeStress(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/final-approve', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }


    // Approve
    ApproveStress(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/primary-approve', this.httpOptions)
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

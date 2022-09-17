import { PageAgriStressServity } from './../models/PageAgriStressServity';
import {Injectable} from '@angular/core';

import {environment} from '../../../../environments/environment';
import {HttpHeaders, HttpClient} from '@angular/common/http';
import {Observable, throwError, pipe} from 'rxjs';
import {ResponseMessage} from '../../geo/models/ResponseMessage';
import {retry, catchError, map} from 'rxjs/operators';
import {AgriStresSeverity} from '../models/AgriStressSeverity';


@Injectable({
    providedIn: 'root'
})
export class AgriStressSeverityService {

    baseUrl = environment.apiUrl + '/agri/stress-severity';
    maxSize: number = 10;

    constructor(private http: HttpClient) {
    }

    // Http Headers
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    //Post
    CreateStressSeverity(data): Observable<ResponseMessage> {
        return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    //Get
    GetStressSeveritye(id): Observable<AgriStresSeverity> {
        return this.http.get<AgriStresSeverity>(this.baseUrl + '/' + id)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

//Get All
    GetAllStressSeverity(): Observable<AgriStresSeverity> {
        return this.http.get<AgriStresSeverity>(this.baseUrl + '/list')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

//Put
    UpdateStressSeverity(id, data): Observable<ResponseMessage> {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/update', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

//Delete
    DeleteStressSeverity(id): Observable<ResponseMessage> {
        return this.http.delete<ResponseMessage>(this.baseUrl + '/' + id + ' /delete', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // Reject
    RejectStressSeverity(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/reject', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    //  Finalize
    FinalizeStressSeverity(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/final-approve', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }


    // Approve
    ApproveStressSeverity(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/primary-approve', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

     //For-Pagination
   getPageStressSeverity(page: number, rowSize: number,searchText): Observable<PageAgriStressServity> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "/paginatedList?page=" + page + "&size=" + this.maxSize  + "&searchText=" + searchText;
    return this.http.get<PageAgriStressServity>(url)
      .pipe(
        map(response => {
          const data = response;
          console.log(data.content);
          return data;
        }));
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

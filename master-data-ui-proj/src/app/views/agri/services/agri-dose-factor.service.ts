import { PageAgriDoseFactor } from './../models/PageAgriDoseFactor';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ResponseMessage } from '../models/ResponseMessage';
import { environment } from '../../../../environments/environment';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { AgriDoseFactor } from '../models/AgriDoseFactor';

@Injectable({
  providedIn: 'root'
})
export class AgriDoseFactorService {

  baseUrl = environment.apiUrl + '/agri/dose-factor';
  maxSize: number = 10;

  constructor(private http: HttpClient) { }


  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateAgriDoseFactor(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetAgriDoseFactor(id): Observable<AgriDoseFactor> {
    return this.http.get<AgriDoseFactor>(this.baseUrl + '/' + id)
      .pipe(
        catchError(this.errorHandl)
      )
  }


  // GET
  GetAllAgriDoseFactor(): Observable<AgriDoseFactor> {
    return this.http.get<AgriDoseFactor>(this.baseUrl + '/list')
      .pipe(
        catchError(this.errorHandl)
      )
  }

  // PUT
  UpdateAgriDoseFactor(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteAgriDoseFactor(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + '/' + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // Reject
  RejectDoseFactor(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeDoseFactor(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveDoseFactor(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

     //For-Pagination
     getPageDoseFactor(page: number, rowSize: number,searchText): Observable<PageAgriDoseFactor> {
      this.maxSize = rowSize || this.maxSize;
      var url = this.baseUrl + "/paginatedList?page=" + page + "&size=" + this.maxSize  + "&searchText=" + searchText;
      return this.http.get<PageAgriDoseFactor>(url)
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

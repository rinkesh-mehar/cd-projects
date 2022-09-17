import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { GeoPanchayat } from '../models/GeoPanchayat';
import { environment } from '../../../../environments/environment';
import { PageGeoPanchayat } from '../models/PageGeoPanchayat';

@Injectable({
  providedIn: 'root'
})
export class GeoPanchayatService {

  // Base url
  baseUrl = environment.apiUrl + '/geo/panchayat';
  maxSize: number = 10;


  constructor(private http: HttpClient) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreatePanchayat(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetPanchayat(id): Observable<GeoPanchayat> {
    return this.http.get<GeoPanchayat>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllPanchayat(): Observable<GeoPanchayat> {
    return this.http.get<GeoPanchayat>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //For-Pagination
  getPageGeoPanchayat(page: number, rowSize: number,searchText): Observable<PageGeoPanchayat> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + "&searchText=" + searchText;
    return this.http.get<PageGeoPanchayat>(url)
      .pipe(
        map(response => {
          const data = response;
          //console.log(data.content);
          return data;
        }));
  }

  // PUT
  UpdatePanchayat(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeletePanchayat(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // Reject
  RejectPanchayat(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  //  Finalize
  FinalizePanchayat(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApprovePanchayat(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
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

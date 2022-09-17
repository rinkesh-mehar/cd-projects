import { environment } from './../../../../environments/environment';
import { catchError, map } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { retry } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { PageIndices } from '../models/page-indices';
import { Indices } from '../models/indices';
import { ResponseMessage } from '../../agri/models/ResponseMessage';

@Injectable({
  providedIn: 'root'
})
export class IndicesService {

  baseUrls = environment.apiUrl + '/site/indices';

  maxSize: number = 10;
  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    })
};

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

//GET
getIndicesList(): Observable<any> {
  return this.http.get(this.baseUrls + '/list')
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

//GET
getPageIndicesList(page: number, rowSize: number, searchText): Observable<PageIndices> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrls + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageIndices>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

//GET
getIndicesById(id): Observable<Indices> {
  return this.http.get<Indices>(this.baseUrls + '/id' + '/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
}

// POST
addIndices(data): Observable<ResponseMessage> {

  console.log("Inside addIndices service");

  return this.http.post<ResponseMessage>(this.baseUrls + '/add', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

//PUT
updateIndices(id, data): Observable<ResponseMessage> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/update/' + id, JSON.stringify(data), this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

// PUT
deactiveIndices(id) {
  return this.http.put<ResponseMessage>(this.baseUrls + '/deactive' + '/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// PUT
activeIndices(id) {
  return this.http.put<ResponseMessage>(this.baseUrls + '/active/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// PUT
deleteIndices(id): Observable<any> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/delete/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

}

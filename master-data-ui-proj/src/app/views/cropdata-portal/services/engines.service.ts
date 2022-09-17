import { Engines } from './../models/engines';
import { PageEngines } from './../models/page-engines';
import { environment } from './../../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map, retry } from 'rxjs/operators';
import { ResponseMessage } from '../../agri/models/ResponseMessage';

@Injectable({
  providedIn: 'root'
})
export class EnginesService {

  baseUrls = environment.apiUrl + '/site/engines';

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
getEngineList(): Observable<any> {
  return this.http.get(this.baseUrls + '/list')
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

//GET
getPageEngineList(page: number, rowSize: number, searchText): Observable<PageEngines> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrls + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageEngines>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

//GET
getEngineById(id): Observable<Engines> {
  return this.http.get<Engines>(this.baseUrls + '/id' + '/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
}

// POST
addEngine(data): Observable<ResponseMessage> {

  // console.log("Inside add opp service");

  return this.http.post<ResponseMessage>(this.baseUrls + '/add', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

//PUT
updateEngine(id, data): Observable<ResponseMessage> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/update/' + id, JSON.stringify(data), this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

// PUT
deactiveEngine(id) {
  return this.http.put<ResponseMessage>(this.baseUrls + '/deactive' + '/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// PUT
activeEngine(id) {
  return this.http.put<ResponseMessage>(this.baseUrls + '/active/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// PUT
deleteEngine(id): Observable<any> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/delete/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

}

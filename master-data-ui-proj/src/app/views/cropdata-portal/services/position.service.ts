import { ResponseMessage } from '../../regional/models/ResponseMessage';
import { catchError, map, retry } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../../../environments/environment';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { PagePosition } from '../models/page-position';
import { Position } from '../models/position';

@Injectable({
  providedIn: 'root'
})
export class PositionService {

  baseUrls = environment.apiUrl + '/site/position';

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
getPagePositionList(page: number, rowSize: number, searchText): Observable<PagePosition> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrls + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PagePosition>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

//GET
getPositionById(id): Observable<Position> {
  return this.http.get<Position>(this.baseUrls + '/id' + '/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
}

// POST
addPosition(data): Observable<ResponseMessage> {

  // console.log("Inside add opp service");

  return this.http.post<ResponseMessage>(this.baseUrls + '/add', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

//PUT
updatePosition(id, data): Observable<ResponseMessage> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/update/' + id, JSON.stringify(data), this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

// PUT
deactivePosition(id) {
  return this.http.put<ResponseMessage>(this.baseUrls + '/deactive' + '/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// PUT
activePosition(id) {
  return this.http.put<ResponseMessage>(this.baseUrls + '/active/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

//GET
opportunityCountByPosition(positionId): Observable<any> {
  return this.http.get<any>(this.baseUrls + '/opportunityCount' + '/' + positionId)
      .pipe(
          catchError(this.errorHandl)
      );
}

// PUT
deletePosition(id): Observable<any> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/delete/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

}

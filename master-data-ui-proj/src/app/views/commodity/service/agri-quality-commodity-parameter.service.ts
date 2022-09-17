import { ResponseMessage } from './../../farmer/models/ResponseMessage';
import { catchError, map, retry } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../../../environments/environment';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { PageAgriQualityCommodityParameter } from '../models/PageAgriQualityCommodityParameter';
import { AgriQualityCommodityParameter } from '../models/AgriQualityCommodityParameter';

@Injectable({
  providedIn: 'root'
})
export class AgriQualityCommodityParameterService {

// Base url
baseUrl = environment.apiUrl + '/agri/quality-commodity-parameter';
maxSize: number = 10;

constructor(private http: HttpClient) { }

// Http Headers
httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
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

//GET
getPageQualityCommodityParameterList(page: number, rowSize: number, searchText): Observable<PageAgriQualityCommodityParameter> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageAgriQualityCommodityParameter>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

//GET
getParameterListByCommodityId(commodityId): Observable<AgriQualityCommodityParameter> {
    return this.http.get<AgriQualityCommodityParameter>(this.baseUrl + '/parameter-list' + '/' + commodityId)
        .pipe(
            catchError(this.errorHandl)
        );
}

// POST
addQualityCommodityParameter(data): Observable<ResponseMessage> {

  // console.log("Inside add opp service");

  return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

//PUT
updateQualityCommodityParameter(id, data): Observable<ResponseMessage> {
  return this.http.put<ResponseMessage>(this.baseUrl + '/update/' + id, JSON.stringify(data), this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

}

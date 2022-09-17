import { environment } from './../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map, retry } from 'rxjs/operators';
import { ResponseMessage } from '../../agri/models/ResponseMessage';
import { AgriQualityParameter } from '../models/AgriQualityParameter';
import { PageAgriQualityParameter } from '../models/PageAgriQuality-Parameter';

@Injectable({
  providedIn: 'root'
})
export class AgriQualityParameterService {

// Base url
baseUrl = environment.apiUrl + '/agri/quality-parameter';
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

 // GET
 getAllQualityParameter(): Observable<AgriQualityParameter> {
  return this.http.get<AgriQualityParameter>(this.baseUrl + '/list')
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    );
}


//GET
getQualityParameterPagenatedList(page: number, rowSize: number, searchText): Observable<PageAgriQualityParameter> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageAgriQualityParameter>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

 // Approve
 approveQualityParameter(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + '/primary-approve/' + id , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

 //  Finalize
 finalizeQualityParameter(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + '/final-approve/'  + id , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

  // Reject
  rejectQualityParameter(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + '/reject/'  + id , this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //GET
  getQualityParameterById(id): Observable<AgriQualityParameter> {
  return this.http.get<AgriQualityParameter>(this.baseUrl + '/id' + '/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
}

// POST
addQualityParameter(data): Observable<ResponseMessage> {

  // console.log("Inside add opp service");

  return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

//PUT
updateQualityParameter(id, data): Observable<ResponseMessage> {
  return this.http.put<ResponseMessage>(this.baseUrl + '/update/' + id, JSON.stringify(data), this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

}

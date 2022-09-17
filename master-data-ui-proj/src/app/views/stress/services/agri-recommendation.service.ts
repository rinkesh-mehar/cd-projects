import { AgriRecommendation } from './../model/AgriRecommendation';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../../../environments/environment';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map, retry } from 'rxjs/operators';
import { PageAgriRecommendation } from '../model/PageAgriRecommendation';
import { ResponseMessage } from '../../agri/models/ResponseMessage';

@Injectable({
  providedIn: 'root'
})
export class AgriRecommendationService {

// Base url
baseUrl = environment.apiUrl + '/agri/recommendation';
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
 getRecommendationList(): Observable<AgriRecommendation> {
  return this.http.get<AgriRecommendation>(this.baseUrl + '/list')
  .pipe(
    retry(1),
    catchError(this.errorHandl)
  )
}

//GET
getPageRecommendationList(page: number, rowSize: number, searchText): Observable<PageAgriRecommendation> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageAgriRecommendation>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

 // Approve
 approveRecommendation(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + '/primary-approve/' + id , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

 //  Finalize
 finalizeRecommendation(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + '/final-approve/'  + id , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

  // Reject
  rejectRecommendation(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + '/reject/'  + id , this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //GET
getRecommendationById(id): Observable<AgriRecommendation> {
  return this.http.get<AgriRecommendation>(this.baseUrl + '/id' + '/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
}

// POST
addRecommendation(data): Observable<ResponseMessage> {

  // console.log("Inside add opp service");

  return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

//PUT
updateRecommendation(id, data): Observable<ResponseMessage> {
  return this.http.put<ResponseMessage>(this.baseUrl + '/update/' + id, JSON.stringify(data), this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

}

import { environment } from './../../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { PageAgriStressStage } from '../model/PageAgriStressStage';
import { catchError, map, retry } from 'rxjs/operators';
import { ResponseMessage } from '../../agri/models/ResponseMessage';
import { AgriStressStage } from '../model/AgriStressStage';

@Injectable({
  providedIn: 'root'
})
export class AgriStressStageService {

  // Base url
baseUrl = environment.apiUrl + '/agri/stress-stage';
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
getPageStressStageList(page: number, rowSize: number, searchText): Observable<PageAgriStressStage> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageAgriStressStage>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

 // Approve
 approveStressStage(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + '/primary-approve/' + id , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

 //  Finalize
 finalizeStressStage(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + '/final-approve/'  + id , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

  // Reject
  rejectStressStage(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + '/reject/'  + id , this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //GET
getStressStageById(id): Observable<AgriStressStage> {
  return this.http.get<AgriStressStage>(this.baseUrl + '/id' + '/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
}

// POST
addStressStage(data): Observable<ResponseMessage> {

  // console.log("Inside add opp service");

  return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

//PUT
updateStressStage(id, data): Observable<ResponseMessage> {
  return this.http.put<ResponseMessage>(this.baseUrl + '/update/' + id, JSON.stringify(data), this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

}

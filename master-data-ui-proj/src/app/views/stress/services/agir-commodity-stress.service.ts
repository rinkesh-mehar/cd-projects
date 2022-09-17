import { PageAgriCommodityStress } from './../model/PageAgriCommodityStress';
import { PageAgriCommodityStressStage } from './../../agri/models/PageAgriCommodityStressStage';
import { AgriCommodityStress } from './../model/AgriCommodityStress';
import { ResponseMessage } from './../../farmer/models/ResponseMessage';
import { catchError, map, retry } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../../../environments/environment';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AgirCommodityStressService {

// Base url
baseUrl = environment.apiUrl + '/agri/commodity-stress';
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
getPageCommodityStressList(page: number, rowSize: number, searchText): Observable<PageAgriCommodityStress> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageAgriCommodityStress>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

 // Approve
 approveCommodityStress(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + '/primary-approve/' + id , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

 //  Finalize
 finalizeCommodityStress(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + '/final-approve/'  + id , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

  // Reject
  rejectCommodityStress(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + '/reject/'  + id , this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //GET
getCommodityStressById(id): Observable<AgriCommodityStress> {
  return this.http.get<AgriCommodityStress>(this.baseUrl + '/id' + '/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
}

// POST
addCommodityStress(data): Observable<ResponseMessage> {

  // console.log("Inside add opp service");

  return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

//PUT
updateCommodityStress(id, data): Observable<ResponseMessage> {
  return this.http.put<ResponseMessage>(this.baseUrl + '/update/' + id, JSON.stringify(data), this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

}

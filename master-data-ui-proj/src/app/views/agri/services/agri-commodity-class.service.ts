import { PageAgriCommodityClass } from './../models/PageAgriCommodityClass';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { AgriCommodityClass } from '../models/AgriCommodityClass';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AgriCommodityClassService {

  
   // Base url
   baseUrl = environment.apiUrl+'/agri/commodity-class';
   maxSize: number = 10;

   constructor(private http: HttpClient) { }
 
   // Http Headers
   httpOptions = {
     headers: new HttpHeaders({
       'Content-Type': 'application/json'
     })
   }
 
   // POST
   CreateCommodityClass(data): Observable<ResponseMessage> {
     return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }  
 
   //  GET By ID
   GetCommodityClass(id): Observable<AgriCommodityClass> {
     return this.http.get<AgriCommodityClass>(this.baseUrl + '/' + id)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }
 
   // GET
   GetAllCommodityClass(): Observable<AgriCommodityClass> {
     return this.http.get<AgriCommodityClass>(this.baseUrl+'/list')
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }
 
   // PUT
   UpdateCommodityClass(id, data): Observable<ResponseMessage> {
     return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }
 
   // DELETE
   DeleteCommodityClass(id){
     return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id +  '/delete', this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }

       // Reject
  RejectCommodityClass(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeCommodityClass(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveCommodityClass(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //GET
getCommodityClassPagenatedList(page: number, rowSize: number, searchText): Observable<PageAgriCommodityClass> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageAgriCommodityClass>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}
 
   // Error handling
   errorHandl(error) {
      let errorMessage = '';
      if(error.error instanceof ErrorEvent) {
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
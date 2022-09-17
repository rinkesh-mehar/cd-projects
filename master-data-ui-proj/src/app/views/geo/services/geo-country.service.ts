import { PageGeoCountry } from './../models/PageGeoCountry';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { GeoCountry } from '../models/GeoCountry';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GeoCountryService {

 
   // Base url
   baseUrl = environment.apiUrl+'/geo/country';
   maxSize: number = 10;

   constructor(private http: HttpClient) { }
 
   // Http Headers
   httpOptions = {
     headers: new HttpHeaders({
       'Content-Type': 'application/json'
     })
   }
 
   // POST
   CreateCountry(data): Observable<ResponseMessage> {
     return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }  
 
   // GET By ID
   GetCountry(id): Observable<GeoCountry> {
     return this.http.get<GeoCountry>(this.baseUrl + '/' + id)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }
 
   // GET
   GetAllCountry(): Observable<GeoCountry> {
     return this.http.get<GeoCountry>(this.baseUrl+'/list')
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }
 
   // PUT
   UpdateCountry(id, data): Observable<ResponseMessage> {
     return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }
 
   // DELETE
   DeleteCountry(id){
     return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id +  '/delete', this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }

     // Reject
  RejectCountry(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeCountry(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveCountry(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //GET
getCountryPagenatedList(page: number, rowSize: number, searchText): Observable<PageGeoCountry> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageGeoCountry>(url)
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
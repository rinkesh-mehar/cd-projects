import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { AgriSeedSource } from '../models/AgriSeedSource';
import { environment } from '../../../../environments/environment';
import { PageAgriSeedSource } from '../models/PageAgriSeedSource';

@Injectable({
  providedIn: 'root'
})
export class AgriSeedSourceService {

   // Base url
   baseUrl = environment.apiUrl+'/agri/seed-source';
   maxSize: number = 10;

   constructor(private http: HttpClient) { }
 
   // Http Headers
   httpOptions = {
     headers: new HttpHeaders({
       'Content-Type': 'application/json'
     })
   }
 
   // POST
   CreateSeedSource(data): Observable<ResponseMessage> {
     return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }  
 
   // GET By ID
   GetSeedSource(id): Observable<AgriSeedSource> {
     return this.http.get<AgriSeedSource>(this.baseUrl + '/' + id)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }
 
   // GET
   GetAllSeedSource(): Observable<AgriSeedSource> {
     return this.http.get<AgriSeedSource>(this.baseUrl+'/list')
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }
 
   // PUT
   UpdateSeedSource(id, data): Observable<ResponseMessage> {
     return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }
 
   // DELETE
   DeleteSeedSource(id){
     return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id +  '/delete', this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }


  // Reject
  RejectSeedSource(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeSeedSource(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveSeedSource(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //GET
getSeedSourcePagenatedList(page: number, rowSize: number, searchText): Observable<PageAgriSeedSource> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageAgriSeedSource>(url)
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

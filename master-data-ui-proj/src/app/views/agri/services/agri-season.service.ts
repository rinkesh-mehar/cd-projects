import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { AgriSeason } from '../models/AgriSeason';
import { environment } from '../../../../environments/environment';
import { PageAgriSeason } from '../models/PageAgriSeason';

@Injectable({
  providedIn: 'root'
})
export class AgriSeasonService {
  SeasonByStateCode(stateCode: any) {
    throw new Error("Method not implemented.");
  }


   // Base url
   baseUrl = environment.apiUrl+'/agri/season';
   maxSize: number = 10;

   constructor(private http: HttpClient) { }
 
   // Http Headers
   httpOptions = {
     headers: new HttpHeaders({
       'Content-Type': 'application/json'
     })
   }
 
   // POST
   CreateSeason(data): Observable<ResponseMessage> {
     return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }  
 
   // GET By ID
   GetSeason(id): Observable<AgriSeason> {
     return this.http.get<AgriSeason>(this.baseUrl + '/' + id)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }
 
   // GET
   GetAllSeasons(): Observable<AgriSeason> {
     return this.http.get<AgriSeason>(this.baseUrl+'/list')
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }

   // GET
   getSeasonList(): Observable<AgriSeason> {
    return this.http.get<AgriSeason>(this.baseUrl+'/season-list')
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }
 
   // PUT
   UpdateSeason(id, data): Observable<ResponseMessage> {
     return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }
 
   // DELETE
   DeleteSeason(id){
     return this.http.delete<ResponseMessage>(this.baseUrl +  "/" + id +  '/delete', this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }


  // Reject
  RejectSeason(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeSeason(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveSeason(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get stateCode
  getSeasonByStateCode(stateCode):Observable<AgriSeason>{
    return this.http.get<AgriSeason>(this.baseUrl + '/season?stateCode=' +   stateCode)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

  //GET
getSeasonPagenatedList(page: number, rowSize: number, searchText): Observable<PageAgriSeason> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageAgriSeason>(url)
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

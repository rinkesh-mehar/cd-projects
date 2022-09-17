import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { AgriGeneralCommodity } from '../models/AgriGeneralCommodity';
import { environment } from '../../../../environments/environment';
import { PageAgriGeneralCommodity } from '../models/PageAgriGeneralCommodity';

@Injectable({
  providedIn: 'root'
})
export class AgriGeneralCommodityService {

   // Base url
   baseUrl = environment.apiUrl+'/agri/general-commodity';
   maxSize: number = 10;

   constructor(private http: HttpClient) { }
 
   // Http Headers
   httpOptions = {
     headers: new HttpHeaders({
       'Content-Type': 'application/json'
     })
   }
 
   // POST
   CreateGeneralCommodity(data): Observable<ResponseMessage> {
     return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }  
 
   // GET By ID
   GetGeneralCommodity(id): Observable<AgriGeneralCommodity> {
     return this.http.get<AgriGeneralCommodity>(this.baseUrl + '/' + id)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }
 
   // GET
   GetAllGeneralCommodity(): Observable<AgriGeneralCommodity> {
     return this.http.get<AgriGeneralCommodity>(this.baseUrl+'/list')
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }
 
   // PUT
   UpdateGeneralCommodity(id, data): Observable<ResponseMessage> {
     return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }
 
   // DELETE
   DeleteGeneralCommodity(id){
     return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id +  '/delete', this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }

       // Reject
  RejectGeneralCommodity(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeGeneralCommodity(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveGeneralCommodity(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //GET
getGeneralCommodityPagenatedList(page: number, rowSize: number, searchText): Observable<PageAgriGeneralCommodity> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageAgriGeneralCommodity>(url)
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
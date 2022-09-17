import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { environment } from '../../../../environments/environment';
import { AgriYieldCorrectionCriteria } from '../models/AgriYieldCorrectionCriteria';



@Injectable({
  providedIn: 'root'
})
export class AgriYieldCorrectionCriteriaService {

  // Base url
  baseUrl = environment.apiUrl+'/agri/yield-correction-criteria';

  constructor(private http: HttpClient) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateYieldCorrectionCriteria(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }  

  // GET By ID
  GetYieldCorrectionCriteria(id): Observable<AgriYieldCorrectionCriteria> {
    return this.http.get<AgriYieldCorrectionCriteria>(this.baseUrl + '/' + id)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

  // GET
  GetAllYieldCorrectionCriteria(): Observable<AgriYieldCorrectionCriteria> {
    return this.http.get<AgriYieldCorrectionCriteria>(this.baseUrl+'/list')
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

  // PUT
  UpdateYieldCorrectionCriteria(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl +"/" + id + '/update', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

  // DELETE
  DeleteYieldCorrectionCriteria(id){
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete' , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

      // Reject
 RejectYieldCorrectionCriteria(id) {
   return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
 }

 //  Finalize
 FinalizeYieldCorrectionCriteria(id) {
   return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
 }


 // Approve
 ApproveYieldCorrectionCriteria(id) {
   return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
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

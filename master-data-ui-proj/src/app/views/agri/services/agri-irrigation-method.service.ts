import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { environment } from '../../../../environments/environment';
import { AgriIrrigationMethod } from '../models/AgriIrrigationMethod';
import { PageAgriIrrigationMethod } from '../models/PageAgriIrrigationMethod';

@Injectable({
  providedIn: 'root'
})
export class AgriIrrigationMethodService {

     // Base url
     baseUrl = environment.apiUrl+'/agri/irrigation-method';
     maxSize: number = 10;

     constructor(private http: HttpClient) { }
   
     // Http Headers
     httpOptions = {
       headers: new HttpHeaders({
         'Content-Type': 'application/json'
       })
     }
   
     // POST
     CreateIrrigationMethod(data): Observable<ResponseMessage> {
       return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
     }  
   
     // GET By ID
     GetIrrigationMethod(id): Observable<AgriIrrigationMethod> {
       return this.http.get<AgriIrrigationMethod>(this.baseUrl + '/' + id)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
     }
   
     // GET
     GetAllIrrigationMethod(): Observable<AgriIrrigationMethod> {
       return this.http.get<AgriIrrigationMethod>(this.baseUrl+'/list')
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
     }
   
     // PUT
     UpdateIrrigationMethod(id, data): Observable<ResponseMessage> {
       return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
     }
   
     // DELETE
     DeleteIrrigationMethod(id){
       return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id +  '/delete', this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
     }


  // Reject
  RejectIrrigationMethod(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeIrrigationMethod(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveIrrigationMethod(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //GET
getIrrigationMethodPagenatedList(page: number, rowSize: number, searchText): Observable<PageAgriIrrigationMethod> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageAgriIrrigationMethod>(url)
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

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { GeneralDropReason } from '../models/GeneralDropReason';
import { environment } from '../../../../environments/environment';
import { PageGeneralDropReason } from '../models/PageGeneralDropReason';

@Injectable({
  providedIn: 'root'
})
export class GeneralDropReasonService {

    // Base url
    baseUrl = environment.apiUrl+'/general/drop-reason';
    maxSize: number = 10;

    constructor(private http: HttpClient) { }
  
    // Http Headers
    httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }
  
    // POST
    CreateDropReason(data): Observable<ResponseMessage> {
      return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }  
  
    // GET By ID
    GetDropReason(id): Observable<GeneralDropReason> {
      return this.http.get<GeneralDropReason>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // GET
    GetAllDropReason(): Observable<GeneralDropReason> {
      return this.http.get<GeneralDropReason>(this.baseUrl+'/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // PUT
    UpdateDropReason(id, data): Observable<ResponseMessage> {
      return this.http.put<ResponseMessage>(this.baseUrl +  "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // DELETE
    DeleteDropReason(id){
      return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id +  '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }

            // Reject
  RejectDropReason(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeDropReason(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveDropReason(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //GET
getDropReasonPagenatedList(page: number, rowSize: number, searchText): Observable<PageGeneralDropReason> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageGeneralDropReason>(url)
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

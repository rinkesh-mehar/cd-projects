import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { environment } from '../../../../environments/environment';
import { AgriAgrochemicalType } from '../models/AgriAgrochemicalType';
import { PageAgriAgrochemicalType } from '../models/PageAgriAgrochemicalType';


@Injectable({
  providedIn: 'root'
})
export class AgriAgrochemicalTypeService {

    // Base url
    baseUrl = environment.apiUrl+'/agri/agrochemical-type';
    maxSize: number = 10;

    constructor(private http: HttpClient) { }
  
    // Http Headers
    httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }
  
    // POST
    CreateAgrochemicalType(data): Observable<ResponseMessage> {
      return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }  
  
    // GET By ID
    GetAgrochemicalType(id): Observable<AgriAgrochemicalType> {
      return this.http.get<AgriAgrochemicalType>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // GET
    GetAllAgrochemicalType(): Observable<AgriAgrochemicalType> {
      return this.http.get<AgriAgrochemicalType>(this.baseUrl+'/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // PUT
    UpdateAgrochemicalType(id, data): Observable<ResponseMessage> {
      return this.http.put<ResponseMessage>(this.baseUrl +"/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // DELETE
    DeleteAgrochemicalType(id){
      return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete' , this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }

      // Reject
  RejectAgrochemicalType(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeAgrochemicalType(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveAgrochemicalType(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //GET
getAgrochemicalTypePagenatedList(page: number, rowSize: number, searchText): Observable<PageAgriAgrochemicalType> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageAgriAgrochemicalType>(url)
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

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ResponseMessage } from '../models/ResponseMessage';
import { environment } from '../../../../environments/environment';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { AgriEcosystem } from '../models/AgriEcosystem';
import { PageAgriEcosystem } from '../models/PageAgriEcosystem';

@Injectable({
  providedIn: 'root'
})
export class AgriEcosystemService {

  baseUrl = environment.apiUrl + '/agri/ecosystem';
  maxSize: number = 10;

  constructor(private http: HttpClient) { }


  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateAgriEcosystem(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetAgriEcosystem(id): Observable<AgriEcosystem> {
    return this.http.get<AgriEcosystem>(this.baseUrl + '/' + id)
      .pipe(
        catchError(this.errorHandl)
      )
  }


  // GET
  GetAllAgriEcosystem(): Observable<AgriEcosystem> {
    return this.http.get<AgriEcosystem>(this.baseUrl + '/list')
      .pipe(
        catchError(this.errorHandl)
      )
  }

  // PUT
  UpdateAgriEcosystem(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteAgriEcosystem(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + '/' + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // Reject
  RejectEcosystem(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeEcosystem(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveEcosystem(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //GET
getEcosystemPagenatedList(page: number, rowSize: number, searchText): Observable<PageAgriEcosystem> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageAgriEcosystem>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
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

}
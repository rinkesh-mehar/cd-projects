import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { ResponseMessage } from '../../agri/models/ResponseMessage';
import { retry, catchError } from 'rxjs/operators';
import { ManageRole } from '../Models/manage-role';

@Injectable({
  providedIn: 'root'
})
export class ManageRoleService {

  // Base url
  baseUrl = environment.apiUrl + '/acl/restriction';
  constructor(private http: HttpClient) { }
  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  // POST
  addManageRole(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(retry(1), catchError(this.errorHandl));
  }
  // POST
  addListManageRole(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add-all', JSON.stringify(data), this.httpOptions)
      .pipe(retry(1), catchError(this.errorHandl));
  }

  // GET By ID
  GetManageRole(id): Observable<ManageRole> {
    return this.http.get<ManageRole>(this.baseUrl + '/' + id)
      .pipe(retry(1), catchError(this.errorHandl));
  }
  // GET
  GetAllManageRole(): Observable<ManageRole> {
    return this.http.get<ManageRole>(this.baseUrl + '/list')
      .pipe(retry(1), catchError(this.errorHandl));
  }

  // GET
  GetAllManageRoleByRoleId(id): Observable<ManageRole> {
    return this.http.get<ManageRole>(this.baseUrl + '/' + id + '/role')
      .pipe(retry(1), catchError(this.errorHandl));
  }

  // PUT
  UpdateManageRole(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(retry(1), catchError(this.errorHandl));
  }
  // DELETE
  DeleteManageRole(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(retry(1), catchError(this.errorHandl));
  }
  // Error handling
  errorHandl(error) {
    console.log(error);
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    }
    else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(errorMessage);
  }
}

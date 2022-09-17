import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../agri/models/ResponseMessage';
import { Group } from '../Models/group';
@Injectable({
  providedIn: 'root'
})
export class GroupService {
  // Base url
  baseUrl = environment.apiUrl + '/acl/group';
  constructor(private http: HttpClient) { }
  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  // POST
  addGroup(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(retry(1), catchError(this.errorHandl));
  }

  // GET By ID
  GetGroup(id): Observable<Group> {
    return this.http.get<Group>(this.baseUrl + '/' + id)
      .pipe(retry(1), catchError(this.errorHandl));
  }
  // GET
  GetAllGroup(): Observable<Group> {
    return this.http.get<Group>(this.baseUrl+'/list')
      .pipe(retry(1), catchError(this.errorHandl));
  }
  // PUT
  UpdateGroup(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(retry(1), catchError(this.errorHandl));
  }
  // DELETE
  DeleteGroup(id) {
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

import { NavData } from './../../../_nav';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../agri/models/ResponseMessage';
import { User } from '../Models/user';
import { PageUser } from '../Models/pageUser';

@Injectable({
  providedIn: 'root'
})
export class UserService {


  // Base url
  baseUrl = environment.apiUrl + '/acl/user';
  maxSize: number = 10;

  constructor(private http: HttpClient) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }


  //GET
getUserPagenatedList(page: number, rowSize: number, searchText): Observable<PageUser> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageUser>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

  // POST
  CreateUser(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetUser(id): Observable<User> {
    return this.http.get<User>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllUser(): Observable<User> {
    return this.http.get<User>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // PUT
  UpdateUser(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // PUT
  UpdateUserName(id: number, name: string) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', { 'name': name }, this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteUser(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // PUT
  ChangeProfilePassword(id, oldPassword,password): Observable<ResponseMessage> {
    console.log("OLDP: "+ oldPassword);
    console.log("Password: "+ password);
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/changePassword?password='+password+'&oldPassword='+oldPassword, this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  getMenusByRoleAndSearchText(roleId,searchText): Observable<NavData[]> {
    return this.http.get<NavData[]>(this.baseUrl + '/menu-list-by-search' + '?roleId=' + roleId + '&searchText=' + searchText)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
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

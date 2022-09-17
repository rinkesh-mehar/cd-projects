import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../agri/models/ResponseMessage';
import { Role } from '../Models/role';
import { PageRole } from '../Models/pageRole';


@Injectable({
  providedIn: 'root'
})
export class RoleService {

  // Base url
  baseUrl = environment.apiUrl + '/acl/role';
  maxSize: number = 10;

  constructor(private http: HttpClient) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }




  //GET
  getRolePagenatedList(page: number, rowSize: number, searchText): Observable<PageRole> {
    this.maxSize = rowSize || this.maxSize;
    let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
    return this.http.get<PageRole>(url)
        .pipe(
            map(response => {
                const data = response;
                return data;
            }));
  }

  // POST
  addRole(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  addRole111(data): Observable<any> {
    return this.http.post<any>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
  }


  // GET By ID
  GetRole(id): Observable<Role> {
    return this.http.get<Role>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllRole(): Observable<Role> {
    return this.http.get<Role>(this.baseUrl+'/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // PUT
  UpdateRole(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }



  // DELETE
  DeleteRole(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // Error handling
  errorHandl(error) {

    console.log(error);


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

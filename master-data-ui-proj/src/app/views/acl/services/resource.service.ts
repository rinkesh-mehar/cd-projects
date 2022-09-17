import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { ResponseMessage } from '../../agri/models/ResponseMessage';
import { retry, catchError, map } from 'rxjs/operators';
import { Resource } from '../Models/resource';
import { PageResource } from '../Models/pageResource';

@Injectable({
  providedIn: 'root'
})
export class ResourceService {

  // Base url
  baseUrl = environment.apiUrl + '/acl/resource';
  maxSize: number = 10;
  constructor(private http: HttpClient) { }
  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  //GET
  getResourcePagenatedList(page: number, rowSize: number, searchText): Observable<PageResource> {
    this.maxSize = rowSize || this.maxSize;
    let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
    return this.http.get<PageResource>(url)
        .pipe(
            map(response => {
                const data = response;
                return data;
            }));
  }





  // POST
  addResource(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(retry(1), catchError(this.errorHandl));
  }

  // GET By ID
  GetResource(id): Observable<Resource> {
    return this.http.get<Resource>(this.baseUrl + '/' + id)
      .pipe(retry(1), catchError(this.errorHandl));
  }
  // GET
  GetAllResource(): Observable<Resource> {
    return this.http.get<Resource>(this.baseUrl + '/list')
      .pipe(retry(1), catchError(this.errorHandl));
  }

  // GET
  GetParentsResource(): Observable<Resource> {
    return this.http.get<Resource>(this.baseUrl + '/parents')
      .pipe(retry(1), catchError(this.errorHandl));
  }

  // GET
  GetAllSubResourceById(id): Observable<Resource> {
    return this.http.get<Resource>(this.baseUrl + '/parents/' + id)
      .pipe(retry(1), catchError(this.errorHandl));
  }

  // PUT
  UpdateResource(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(retry(1), catchError(this.errorHandl));
  }
  // DELETE
  DeleteResource(id) {
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

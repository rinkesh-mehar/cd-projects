import { PageAclResourceGroup } from './../Models/PageAclResourceGroup';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { ResponseMessage } from '../../agri/models/ResponseMessage';
import { retry, catchError, map } from 'rxjs/operators';
import { ResourceGroup } from '../Models/resource-group';

@Injectable({
  providedIn: 'root'
})
export class ResourceGroupService {

  // Base url
  baseUrl = environment.apiUrl + '/acl/resource-group';
  maxSize: number = 10;
  
  constructor(private http: HttpClient) { }
  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
  // POST
  addResourceGroup(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(retry(1), catchError(this.errorHandl));
  }

  // GET By ID
  GetResourceGroup(id): Observable<ResourceGroup> {
    return this.http.get<ResourceGroup>(this.baseUrl + '/' + id)
      .pipe(retry(1), catchError(this.errorHandl));
  }
  // GET
  GetAllResourceGroup(): Observable<ResourceGroup> {
    return this.http.get<ResourceGroup>(this.baseUrl + '/list')
      .pipe(retry(1), catchError(this.errorHandl));
  }
  //paginatedList
  getPageAclResourceGroup(page: number, rowSize: number, searchText): Observable<PageAclResourceGroup> {
    this.maxSize = rowSize || this.maxSize;
    let url = this.baseUrl + '/paginatedList' +  '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
    return this.http.get<PageAclResourceGroup>(url)
      .pipe(
        map(response => {
          const data = response;
          // console.log(data.content);
          return data;
        }));
  }
  // GET
  GetParentsResourceGroup(): Observable<ResourceGroup> {
    return this.http.get<ResourceGroup>(this.baseUrl + '/parents')
      .pipe(retry(1), catchError(this.errorHandl));
  }

  // GET
  GetAllSubResourceGroupById(id): Observable<ResourceGroup> {
    return this.http.get<ResourceGroup>(this.baseUrl + '/parents/' + id)
      .pipe(retry(1), catchError(this.errorHandl));
  }

  // PUT
  UpdateResourceGroup(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(retry(1), catchError(this.errorHandl));
  }
  // DELETE
  DeleteResourceGroup(id) {
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

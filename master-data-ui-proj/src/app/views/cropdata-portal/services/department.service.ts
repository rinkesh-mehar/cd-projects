import { map, catchError, retry } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import {environment} from '../../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { PageDepartment } from '../models/PageDepartment';
import { ResponseMessage } from '../../agri/models/ResponseMessage';
import { Department } from '../models/department';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  baseUrls = environment.apiUrl + '/site/department';

  maxSize: number = 10;
  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    })
};

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

//GET
getPageDepartmentList(page: number, rowSize: number, searchText): Observable<PageDepartment> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrls + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageDepartment>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

//GET
getDepartmentById(id): Observable<Department> {
  return this.http.get<Department>(this.baseUrls + '/id' + '/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
}

// POST
addDepartment(data): Observable<ResponseMessage> {

  // console.log("Inside add opp service");

  return this.http.post<ResponseMessage>(this.baseUrls + '/add', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

//PUT
updateDepartment(id, data): Observable<ResponseMessage> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/update/' + id, JSON.stringify(data), this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

// DELETE
deactiveDepartment(id): Observable<any> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/deactive/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// PUT
activeDepartment(id): Observable<any> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/active/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

//GET
opportunityCountByDepartment(departmentId): Observable<any> {
  return this.http.get<any>(this.baseUrls + '/opportunityCount' + '/' + departmentId)
      .pipe(
          catchError(this.errorHandl)
      );
}

// PUT
deleteDepartment(id): Observable<any> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/delete/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

}

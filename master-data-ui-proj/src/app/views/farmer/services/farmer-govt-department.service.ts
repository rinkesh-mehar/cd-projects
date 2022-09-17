import { PageFarmerGovtDepartment } from './../models/PageFarmerGovtDepartment';


import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { environment } from '../../../../environments/environment';
import { FarmerGovtDepartment } from '../models/FarmerGovtDepartment';

@Injectable({
  providedIn: 'root'
})
export class FarmerGovtDepartmentService {


  // Base url
  baseUrl = environment.apiUrl + '/farmer/govt-department';
  maxSize: number = 10;


  constructor(private http: HttpClient) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateGovtDepartment(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetGovtDepartment(id): Observable<FarmerGovtDepartment> {
    return this.http.get<FarmerGovtDepartment>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllGovtDepartment(): Observable<FarmerGovtDepartment> {
    return this.http.get<FarmerGovtDepartment>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //For-Pagination
  // getPageFarmerGovtDepartment(page: number): Observable<PageFarmerGovtDepartment> {
  //   var url = this.baseUrl + "?page=" + page + "&size=" + environment.pageSize;

  //   return this.http.get<PageFarmerGovtDepartment>(url)
  //     .pipe(
  //       map(response => {
  //         const data = response;
  //         //console.log(data.content);
  //         return data;
  //       }));
  // }

  //GET
getFarmerGovtDepartmentPagenatedList(page: number, rowSize: number, searchText): Observable<PageFarmerGovtDepartment> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageFarmerGovtDepartment>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

  // PUT
  UpdateGovtDepartment(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteGovtDepartment(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

   // Reject
   RejectGovtDepartment(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeGovtDepartment(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveGovtDepartment(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
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

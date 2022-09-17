import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { environment } from '../../../../environments/environment';
import { FarmerLoanSource } from '../models/FarmerLoanSource';
import { PageFarmerLoanSource } from '../models/PageFarmerLoanSource';

@Injectable({
  providedIn: 'root'
})
export class FarmerLoanSourceService {


  // Base url
  baseUrl = environment.apiUrl + '/farmer/loan-source';
  maxSize: number = 10;


  constructor(private http: HttpClient) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateLoanSource(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetLoanSource(id): Observable<FarmerLoanSource> {
    return this.http.get<FarmerLoanSource>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllLoanSource(): Observable<FarmerLoanSource> {
    return this.http.get<FarmerLoanSource>(this.baseUrl+'/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //For-Pagination
  getPageFarmerLoanSource(page: number,rowSize: number,searchText): Observable<PageFarmerLoanSource> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + "&searchText=" + searchText;
   
    return this.http.get<PageFarmerLoanSource>(url)
      .pipe(
        map(response => {
          const data = response;
          //console.log(data.content);
          return data;
    }));
  }

  // PUT
  UpdateLoanSource(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl +  "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteLoanSource(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id +  '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

   // Reject
   RejectLoanSource(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeLoanSource(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveLoanSource(id) {
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

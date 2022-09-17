import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { environment } from '../../../../environments/environment';
import { GeneralCompany } from '../models/GeneralCompany';
import { PageGeneralCompany } from '../models/PageGeneralCompany';

@Injectable({
  providedIn: 'root'
})
export class GeneralCompanyService {

  // Base url
  baseUrl = environment.apiUrl + '/general/company';
  maxSize: number = 10;

  constructor(private http: HttpClient) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateCompany(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
  
  // GET By ID
  GetCompany(id): Observable<GeneralCompany> {
    return this.http.get<GeneralCompany>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllCompany(): Observable<GeneralCompany> {
    return this.http.get<GeneralCompany>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // PUT
  UpdateCompany(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }



  //GET
getCompanyPagenatedList(page: number, rowSize: number, searchText): Observable<PageGeneralCompany> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageGeneralCompany>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

  // DELETE
  DeleteCompany(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

       // Reject
RejectCompany(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

//  Finalize
FinalizeCompany(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}


// Approve
ApproveCompany(id) {
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
